package com.gshmalyukh.demo.entities;

import com.gshmalyukh.demo.core.TreatmentAction;
import com.gshmalyukh.demo.core.TreatmentStatus;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;
@Entity
@Table(name = "TREATMENT_TASKS", indexes = {
        @Index(name = "TASKS_UNIQUE_IDX", columnList = "treatmentPlanId, startTime, treatmentStatus", unique = true)
})
public class TreatmentTaskEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "TREATMENT_PLAN")
//    private TreatmentPlanEntity treatmentPlan;
    //in this task there are no need to use be directional relationship at least from TreatmentTask
    //it still not good practice but it will speedup interaction with DB
    @Column(name = "TREATMENT_PLAN")
    private Long treatmentPlanId;
    @Column(name = "TREATMENT_ACTION")
    private TreatmentAction action;
    @Column(name = "PATIENT")
    private String patient;
    @Column(name = "START_TIME")
    private Date startTime;
    @Column(name = "TREATMENT_STATUS")
    private TreatmentStatus treatmentStatus;

//    public TreatmentPlanEntity getTreatmentPlan() {
//        return treatmentPlan;
//    }
//
//    public void setTreatmentPlan(TreatmentPlanEntity treatmentPlan) {
//        this.treatmentPlan = treatmentPlan;
//    }

    public Long getTreatmentPlanId() {
        return treatmentPlanId;
    }

    public void setTreatmentPlanId(Long treatmentPlanId) {
        this.treatmentPlanId = treatmentPlanId;
    }

    public TreatmentAction getAction() {
        return action;
    }

    public void setAction(TreatmentAction action) {
        this.action = action;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public TreatmentStatus getTreatmentStatus() {
        return treatmentStatus;
    }

    public void setTreatmentStatus(TreatmentStatus treatmentStatus) {
        this.treatmentStatus = treatmentStatus;
    }
}
