package com.gshmalyukh.demo.scheduling;

import com.gshmalyukh.demo.core.TreatmentAction;
import com.gshmalyukh.demo.core.TreatmentPlan;
import com.gshmalyukh.demo.core.TreatmentStatus;
import com.gshmalyukh.demo.core.TreatmentTask;
import com.gshmalyukh.demo.service.TreatmentPlanService;
import com.gshmalyukh.demo.service.TreatmentTaskService;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * we have two schedules - one is updating plans, as they are not expected to be changed often, does it once in 10 sec
 * second updates tasks, runs every 5 sec, it will not generate new task for the same plan and same time and status.
 * It can generate duplicate task for same plan and same time if status have been changed before startTime - but that is
 * not expected behaviour
 */
@Component
public class TreatmentPlanUpdateTask {
    //renew treatment plans ones in 10 sec
    //todo later need to move to env variables
    private static final long TREATMENT_PLAN_INTERVAL = 10000;
    //generate tasks ones in 5 sec
    //todo later need to move to env variables
    private static final long TREATMENT_TASK_INTERVAL = 5000;
    private final Logger LOGGER = LoggerFactory.getLogger(TreatmentPlanUpdateTask.class);
    private final ConcurrentHashMap<Long, TreatmentPlan> treatmentPlanMap;
    private final TreatmentPlanService treatmentPlanService;
    private final TreatmentTaskService treatmentTaskService;

    public TreatmentPlanUpdateTask(TreatmentPlanService treatmentPlanService, TreatmentTaskService treatmentTaskService) {
        this.treatmentPlanService = treatmentPlanService;
        this.treatmentTaskService = treatmentTaskService;
        treatmentPlanMap = new ConcurrentHashMap<>();
    }

    @Scheduled(fixedDelay = TREATMENT_PLAN_INTERVAL)
    public void updateTreatmentPlans() {
        removeExpiredTreatmentPlans();
        mergeTreatmentPlansFromDB();

        LOGGER.info("TreatmentPlan updated successfully");
    }

    private void removeExpiredTreatmentPlans() {
        final Date currentTime = new Date();
        EndTimeCondition endTimeCondition = new EndTimeCondition(currentTime);

        treatmentPlanMap.entrySet().removeIf(endTimeCondition);
    }

    private void mergeTreatmentPlansFromDB() {
        Stream<TreatmentPlan> plansFromDB = treatmentPlanService.findByEndTimeAfterOrNullAndStartTimeBeforeEquals(new Date());
        //presume that plans are immutable in db
        //we use id as key - not good - it would be better if we have some unique business identification in db
        plansFromDB.forEach(treatmentPlan -> treatmentPlanMap.
                putIfAbsent(treatmentPlan.getId(), treatmentPlan));
    }

    @Scheduled(fixedDelay = TREATMENT_TASK_INTERVAL)
    public void generateTreatmentTasks() {
        final Date currentTime = new Date();

        TreatmentTaskFactory treatmentTaskFactory = new TreatmentTaskFactory(currentTime);
        //remove expired TreatmentPlans and remove if next task
        List<TreatmentTask> treatmentTaskList = treatmentPlanMap.values().parallelStream().
                filter(treatmentPlan -> {
                    return (treatmentPlan.getEndTime() == null) || (treatmentPlan.getEndTime().after(currentTime));
                }).
                map(treatmentTaskFactory).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

        List<TreatmentTask> existingActiveTasks = treatmentTaskService.findByTreatmentStatus(TreatmentStatus.ACTIVE);
        treatmentTaskList.removeAll(existingActiveTasks);

        treatmentTaskService.saveAll(treatmentTaskList);


        LOGGER.info("TreatmentTasks updated successfully");
    }
    private class TreatmentTaskFactory implements Function<TreatmentPlan, Optional<TreatmentTask>> {
        private final Date date;

        public TreatmentTaskFactory(Date date) {
            this.date = date;
        }

        @Override
        public Optional<TreatmentTask> apply(TreatmentPlan treatmentPlan) {
            Long treatmentPlanId = treatmentPlan.getId();
            TreatmentAction action = treatmentPlan.getAction();
            String patient = treatmentPlan.getPatient();
            Date startTime = treatmentPlan.getStartTime();

            Optional<Date> startTaskTime = getNextTaskTime(treatmentPlan.getRecurrencePattern(), startTime, date);
            if(startTaskTime.isPresent()){
                TreatmentTask treatmentTask  = new TreatmentTask(treatmentPlanId, action, patient, startTaskTime.get(),
                        TreatmentStatus.ACTIVE);
                return Optional.of(treatmentTask);
            } else {
                return Optional.empty();
            }
        }
    }

    private class EndTimeCondition implements Predicate<Map.Entry<Long, TreatmentPlan>> {
        private final Date date;

        public EndTimeCondition(Date date) {
            this.date = date;
        }

        @Override
        public boolean test(Map.Entry<Long, TreatmentPlan> entries) {
            Date endTime = entries.getValue().getEndTime();
            return endTime != null && endTime.before(date);
        }
    }

    private Optional<Date> getNextTaskTime(CronExpression recurrencePattern, Date startTime, Date currentTime) {
        Date nextValidDate = recurrencePattern.getNextValidTimeAfter(startTime);

        while (nextValidDate != null && nextValidDate.before(currentTime)) {
            nextValidDate = recurrencePattern.getNextValidTimeAfter(nextValidDate);
        }

        return Optional.ofNullable(nextValidDate);
    }
}
