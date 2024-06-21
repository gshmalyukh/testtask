package com.gshmalyukh.demo.service;

import com.gshmalyukh.demo.core.TreatmentPlan;
import com.gshmalyukh.demo.entities.TreatmentPlanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TreatmentPlanMapper {
    List<TreatmentPlan> entityListToApiList(List<TreatmentPlanEntity> entity);
}
