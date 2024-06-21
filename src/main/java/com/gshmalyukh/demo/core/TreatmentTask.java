package com.gshmalyukh.demo.core;

import java.util.Date;
import java.util.Objects;

public class TreatmentTask {
    private Long treatmentPlanId;
    private TreatmentAction action;
    private String patient;
    private Date startTime;
    private TreatmentStatus treatmentStatus;
    private TreatmentTask(){

    }

    public TreatmentTask(Long treatmentPlanId, TreatmentAction action,
                               String patient, Date startTime, TreatmentStatus treatmentStatus){
        this.treatmentPlanId = treatmentPlanId;
        this.action = action;
        this.patient = patient;
        this.startTime = startTime;
        this.treatmentStatus = treatmentStatus;
    }

    public Long getTreatmentPlanId() {
        return treatmentPlanId;
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

    public void setTreatmentPlanId(Long treatmentPlanId) {
        this.treatmentPlanId = treatmentPlanId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreatmentTask treatmentTask = (TreatmentTask) o;
        return (Objects.equals(treatmentPlanId, treatmentTask.treatmentPlanId) && startTime.equals(treatmentTask.getStartTime()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(treatmentPlanId, startTime);
    }

    @Override
    public String toString() {
        return "TreatmentPlan{" +
                "treatmentPlanId=" + treatmentPlanId +
                ", action=" + action +
                ", patient='" + patient +
                ", startTime=" + startTime +
                ", status='" + treatmentStatus +
                '}';
    }
}
