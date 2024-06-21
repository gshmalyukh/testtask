package com.gshmalyukh.demo.service;

import com.gshmalyukh.demo.core.TreatmentPlan;

import java.util.Date;
import java.util.stream.Stream;

public interface TreatmentPlanService {
    Stream<TreatmentPlan> findAll();

    Stream<TreatmentPlan> findByEndTimeAfterOrNullAndStartTimeBeforeEquals(Date date);
}
