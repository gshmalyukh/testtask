package com.gshmalyukh.demo.core;

import org.quartz.CronExpression;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

public class TreatmentPlan {
    private Long id;

    private TreatmentAction action;

    private String patient;

    private Date startTime;

    private Date endTime;

    private CronExpression recurrencePattern;

    public TreatmentPlan() {

    }

    public TreatmentPlan(TreatmentAction action, String patient, Date startTime,
                         Date endTime, CronExpression recurrencePattern) {
        this.action = action;
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
        this.recurrencePattern = recurrencePattern;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreatmentPlan treatmentPlan = (TreatmentPlan) o;
        return (Objects.equals(id, treatmentPlan.id));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TreatmentPlan{" +
                "id=" + id +
                ", action=" + action +
                ", patient='" + patient +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", recurrencePattern=" + recurrencePattern.getCronExpression() +
                '}';
    }

    public String toSQL() {
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StringJoiner sqlJoiner = new StringJoiner("', '", "('", "')");
        sqlJoiner.add(Integer.toString(getAction().ordinal()));
        sqlJoiner.add(getPatient());
        sqlJoiner.add(dateTimeFormat.format(getStartTime()));
        sqlJoiner.add(dateTimeFormat.format(getEndTime()));
        sqlJoiner.add(getRecurrencePattern().getCronExpression());
        return sqlJoiner.toString();
    }


}
