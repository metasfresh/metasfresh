package de.metas.shipper.gateway.nshift.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NShiftClientConfig {

    public static final String NSHIFT_REST_TEMPLATE = "nShiftRestTemplate";
    public static final String NSHIFT_OBJECT_MAPPER = "nShiftObjectMapper";

    @Bean(NSHIFT_REST_TEMPLATE)
    public RestTemplate nShiftRestTemplate(final RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean(NSHIFT_OBJECT_MAPPER)
    public ObjectMapper nShiftObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
