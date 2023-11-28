package rp.testing.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@UtilityClass
@Slf4j
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static String asPrettyJsonString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("An error occurred while turning to JSON string:\n" + e.getMessage());
        }
    }

    public static String asPrettyJsonString(HttpEntity httpEntity) {
        try {
            return asPrettyJsonString(EntityUtils.toString(httpEntity));
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while turning to JSON string:\n" + e.getMessage());
        }
    }

    public static String asPrettyJsonString(String uglyJson) {
        try {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(uglyJson);
            return OBJECT_MAPPER.writeValueAsString(jsonNode);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while turning to JSON string:\n" + e.getMessage());
        }
    }

    public static StringEntity asStringEntity(Object object) {
        try {
            return new StringEntity(asPrettyJsonString(object));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("An error occurred while turning to StringEntity:\n" + e.getMessage());
        }
    }

    public static <T> T fromJsonToObject(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("An error occurred while reading from json to Object:\n" + e.getMessage());
        }
    }

}
