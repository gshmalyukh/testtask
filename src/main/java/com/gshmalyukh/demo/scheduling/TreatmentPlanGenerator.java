package com.gshmalyukh.demo.scheduling;

import com.gshmalyukh.demo.core.TreatmentPlan;
import com.gshmalyukh.demo.core.TreatmentAction;
import com.gshmalyukh.demo.util.PrimitiveFileWriter;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TreatmentPlanGenerator {
    private int numOfTreatmentPlans;
    public TreatmentPlanGenerator(int numOfTreatmentPlans){
        this.numOfTreatmentPlans = numOfTreatmentPlans;
    }

    public void generateTestSqlFile(){
        String newLine = System.getProperty("line.separator");
        String separator = "," + newLine;

        List<TreatmentPlan> treatmentPlanEntityList = generateTreatmentPlans();
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO TREATMENT_PLANS (TREATMENT_ACTION, PATIENT, START_TIME, END_TIME, RECURRENCE_PATTERN) VALUES");
        sqlBuilder.append(newLine);
        String data = treatmentPlanEntityList.stream().parallel().map(TreatmentPlan::toSQL).collect(Collectors.joining(separator));
        sqlBuilder.append(data);
        sqlBuilder.append(";");
        PrimitiveFileWriter.writeToFile(sqlBuilder.toString(), "test.txt");
    }

    private List<TreatmentPlan> generateTreatmentPlans() {
        return IntStream.range(0, numOfTreatmentPlans).parallel().mapToObj(this::generateTreatmentPlan).collect(Collectors.toList());
    }

    private TreatmentPlan generateTreatmentPlan(int counter){
        TreatmentAction treatmentAction = (counter % 2 == 0) ? TreatmentAction.ACTION_A : TreatmentAction.ACTION_B;
        String patient = "patient" + counter;
        LocalDateTime localDateTime = LocalDateTime.now();

        LocalDateTime startTimeLocalDateTime = localDateTime.plusMinutes(counter * 4);
        Date startTime = Date.from(startTimeLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        LocalDateTime endTimeLocalDateTime = localDateTime.plusMinutes(counter * 10);
        Date endTime = Date.from(endTimeLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());



        CronExpression recurencePattern = null;
        try {
            recurencePattern = new CronExpression(String.format("0 */%d * * * ?", (counter + 1) * 3));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new TreatmentPlan(treatmentAction, patient, startTime, endTime, recurencePattern);
    }


}
