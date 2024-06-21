package com.gshmalyukh.demo.repos;

import com.gshmalyukh.demo.entities.TreatmentPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlanEntity, Long> {

    @Query("SELECT t FROM TreatmentPlanEntity t WHERE (t.endTime >= :date OR t.endTime IS NULL) AND t.startTime <= :date")
    List<TreatmentPlanEntity> findByEndTimeAfterOrNullAndStartTimeBeforeEquals(@Param("date") Date date);
}
