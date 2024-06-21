package com.gshmalyukh.demo.service;

import com.gshmalyukh.demo.core.TreatmentPlan;
import com.gshmalyukh.demo.core.TreatmentStatus;
import com.gshmalyukh.demo.core.TreatmentTask;
import com.gshmalyukh.demo.entities.TreatmentPlanEntity;
import com.gshmalyukh.demo.entities.TreatmentTaskEntity;
import com.gshmalyukh.demo.repos.TreatmentTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service("treatmentTaskService")
@Transactional
public class TreatmentTaskServiceImpl implements TreatmentTaskService{
    private final TreatmentTaskRepository treatmentTaskRepository;
    private final TreatmentTaskMapper mapper;

    public TreatmentTaskServiceImpl(TreatmentTaskRepository treatmentTaskRepository, TreatmentTaskMapper mapper){
        this.treatmentTaskRepository = treatmentTaskRepository;
        this.mapper = mapper;

    }
    @Override
    @Transactional
    public void saveAll(List<TreatmentTask> treatmentTasks) {
        List<TreatmentTaskEntity> entityList = mapper.apiListToEntityList(treatmentTasks);
        treatmentTaskRepository.saveAll(entityList);
    }
    @Override
    @Transactional
    public  List<TreatmentTask> findByTreatmentStatus(TreatmentStatus treatmentStatus){
        List<TreatmentTaskEntity> entityList = treatmentTaskRepository.findByTreatmentStatus(treatmentStatus);
        return mapper.entityListToApiList(entityList);
    }
}
