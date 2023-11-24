package com.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ConvertUtils {

    public static LocalDateTime getCurrentTashkentTime() {

        return LocalDateTime.now().atZone(ZoneId.of("Asia/Tashkent")).toLocalDateTime();
    }


    public static <T> T fromJson(String json, Class<T> valueType) {
        try {
            return new ObjectMapper().readValue(json, valueType);
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to Java object", e);
        }
    }

    public static String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Error converting Java object to JSON string", e);
        }
    }

    public static String listToJsonArray(List<?> list) {
        try {
            return new ObjectMapper().writeValueAsString(list);
        } catch (Exception e) {
            throw new RuntimeException("Error converting List to JSON array string", e);
        }
    }


    public static <T> List<T> jsonArrayToList(String jsonArray, Class<T> elementType) {
        try {
            if (jsonArray == null || jsonArray.length() == 0) return new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonArray, objectMapper.getTypeFactory().constructCollectionType(List.class, elementType));
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON array string to List", e);
        }
    }


}
