package com.gshmalyukh.demo.entities;

import com.gshmalyukh.demo.core.TreatmentAction;
import com.gshmalyukh.demo.core.TreatmentTask;
import jakarta.persistence.*;
import org.quartz.CronExpression;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "TREATMENT_PLANS")
public class TreatmentPlanEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TREATMENT_ACTION")
    private TreatmentAction action;
    @Column(name = "PATIENT")
    private String patient;
    @Column(name = "START_TIME")
    private Date startTime;
    @Column(name = "END_TIME")
    private Date endTime;
    @Column(name = "RECURRENCE_PATTERN")
    @Convert(converter = com.gshmalyukh.demo.util.CronExpressionConverter.class)
    private CronExpression recurrencePattern;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "treatmentPlan")
//    private final Set<TreatmentTaskEntity> treatmentTasks = new HashSet<>();


    public TreatmentPlanEntity(){

    }

    public TreatmentPlanEntity(TreatmentAction action, String patient,
                               Date startTime, Date endTime, CronExpression recurrencePattern) {
        this.action = action;
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
        this.recurrencePattern = recurrencePattern;
    }

    public Long getId() {
        return id;
    }

    // Getters and setters
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public CronExpression getRecurrencePattern() {
        return recurrencePattern;
    }

    public void setRecurrencePattern(CronExpression recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }

//    public Set<TreatmentTaskEntity> getTreatmentTasks() {
//        return treatmentTasks;
//    }

    @Override
    public String toString() {
        return "TreatmentPlan{" +
                "action=" + action +
                ", patient='" + patient + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", recurrencePattern=" + recurrencePattern.getCronExpression() +
                '}';
    }
}
