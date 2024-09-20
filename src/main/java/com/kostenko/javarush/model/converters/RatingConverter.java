package com.kostenko.javarush.model.converters;

import com.kostenko.javarush.model.enums.Rating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RatingConverter implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(Rating attribute) {
        return attribute == null ? null : attribute.name().replace("_", "-");
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Rating.valueOf(dbData.replace("-", "_"));
    }
}
