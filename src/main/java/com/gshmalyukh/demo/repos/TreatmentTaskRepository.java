package com.gshmalyukh.demo.repos;

import com.gshmalyukh.demo.core.TreatmentStatus;
import com.gshmalyukh.demo.entities.TreatmentTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TreatmentTaskRepository extends JpaRepository<TreatmentTaskEntity, Long> {
    @Query("SELECT t FROM TreatmentTaskEntity t WHERE t.treatmentStatus = :treatmentStatus")
    List<TreatmentTaskEntity> findByTreatmentStatus(@Param("treatmentStatus") TreatmentStatus treatmentStatus);
}
