package de.metas.camel.ebay;

import org.apache.camel.CamelContext;
import org.apache.camel.component.jackson.JacksonDataFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.NonNull;

/**
 * TODO: refactor -> copied from sw6 api.
 * @author werner
 *
 */
public class CamelRouteUtil {

	@NonNull
	public static JacksonDataFormat setupJacksonDataFormatFor(
			@NonNull final CamelContext context,
			@NonNull final Class<?> unmarshalType)
	{
		final ObjectMapper objectMapper = (new ObjectMapper())
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);

		final JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
		jacksonDataFormat.setCamelContext(context);
		jacksonDataFormat.setObjectMapper(objectMapper);
		jacksonDataFormat.setUnmarshalType(unmarshalType);
		return jacksonDataFormat;
	}
	
}
