package com.xhh.demo.lettuce.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    public static final ObjectMapper JSON_MAPPER = initJsonObjectMapper();

    private static ObjectMapper initJsonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return mapper;
    }

    public static <T> String object2json(T t) throws JsonProcessingException {
        return JSON_MAPPER.writeValueAsString(t);
    }

    public static <T> T json2Object(String json, Class<T> t) throws JsonProcessingException {
        return JSON_MAPPER.readValue(json, t);
    }

}
