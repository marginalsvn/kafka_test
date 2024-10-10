package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JacksonUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String toJson(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T fromJson(String value, Class<T> clas) throws JsonProcessingException {
        return objectMapper.readValue(value, clas);
    }
}
