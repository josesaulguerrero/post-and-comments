package co.com.post_comments.alpha.application.commons.json;

import co.com.post_comments.alpha.application.commons.exception.JSONDeserializationException;
import co.com.post_comments.alpha.application.commons.exception.JSONSerializationException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JSONMapperImpl implements JSONMapper {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public String writeToJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JSONSerializationException(
                    String.format("Couldn't parse %s to a JSON string.", object.toString())
            );
        }
    }

    @Override
    public Object readFromJson(String json, Class<?> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new JSONDeserializationException(
                    String.format("Couldn't map %s to a valid instance of %s.", json, clazz.getName())
            );
        }
    }
}
