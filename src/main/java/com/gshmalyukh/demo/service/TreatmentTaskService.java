package com.gshmalyukh.demo.service;

import com.gshmalyukh.demo.core.TreatmentStatus;
import com.gshmalyukh.demo.core.TreatmentTask;

import java.util.List;

public interface TreatmentTaskService {
    void saveAll(List<TreatmentTask> treatmentTasks);

    List<TreatmentTask> findByTreatmentStatus(TreatmentStatus treatmentStatus);
}
