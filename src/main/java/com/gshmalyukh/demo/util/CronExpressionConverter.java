package com.gshmalyukh.demo.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.quartz.CronExpression;
import java.text.ParseException;

@Converter(autoApply = false)
public class CronExpressionConverter implements AttributeConverter<CronExpression, String> {
    @Override
    public String convertToDatabaseColumn(CronExpression cronExpression) {
        return (cronExpression == null ? null : cronExpression.getCronExpression());
    }

    @Override
    public CronExpression convertToEntityAttribute(String dbData) {
        try {
            return (dbData == null ? null : new CronExpression(dbData));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid CronExpression: " + dbData, e);
        }
    }
}
