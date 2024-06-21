package com.gshmalyukh.demo.service;

import com.gshmalyukh.demo.core.TreatmentPlan;
import com.gshmalyukh.demo.core.TreatmentTask;
import com.gshmalyukh.demo.entities.TreatmentPlanEntity;
import com.gshmalyukh.demo.entities.TreatmentTaskEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TreatmentTaskMapper {

    List<TreatmentTaskEntity> apiListToEntityList(List<TreatmentTask> apiList);
    List<TreatmentTask> entityListToApiList(List<TreatmentTaskEntity> entity);
}
