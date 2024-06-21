package com.gshmalyukh.demo.service;

import com.gshmalyukh.demo.core.TreatmentPlan;
import com.gshmalyukh.demo.entities.TreatmentPlanEntity;
import com.gshmalyukh.demo.repos.TreatmentPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service("treatmentPlanService")
@Transactional
public class TreatmentPlanServiceImpl implements TreatmentPlanService{

    private final TreatmentPlanRepository treatmentPlanRepository;
    private final TreatmentPlanMapper mapper;

    public TreatmentPlanServiceImpl(TreatmentPlanRepository treatmentPlanRepository, TreatmentPlanMapper mapper){
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly=true)
    public Stream<TreatmentPlan> findAll() {
        List<TreatmentPlanEntity> entityList = treatmentPlanRepository.findAll();
        List<TreatmentPlan> treatmentPlanList = mapper.entityListToApiList(entityList);
        return treatmentPlanList.stream();
    }
    @Override
    @Transactional(readOnly=true)
    public Stream<TreatmentPlan> findByEndTimeAfterOrNullAndStartTimeBeforeEquals(Date date){
        List<TreatmentPlanEntity> entityList = treatmentPlanRepository.findByEndTimeAfterOrNullAndStartTimeBeforeEquals(date);
        List<TreatmentPlan> treatmentPlanList = mapper.entityListToApiList(entityList);
        return treatmentPlanList.stream();
    }
}
