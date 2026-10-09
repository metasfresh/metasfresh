package de.metas.camel.externalsystems.core.to_mf.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.rest_api.v2.JsonApiResponse;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UnpackV2ResponseRouteBuilder extends EndpointRouteBuilder
{
	public final static String UNPACK_V2_API_RESPONSE = "UnpackV2ApiResponse";
	private final static String PROPERTY_RAW_RESPONSE_BODY = "UnpackV2ApiResponse_RawResponseBody";
	public final static String UNPACK_V2_API_RESPONSE_PROCESSOR_ID = "UnpackV2ApiResponse_Processor_id";

	@Override
	public void configure()
	{
		//@formatter:off
		from(direct(UNPACK_V2_API_RESPONSE))
				.routeId(UNPACK_V2_API_RESPONSE)
				.streamCaching()
				.doTry()
				  .setProperty(PROPERTY_RAW_RESPONSE_BODY, bodyAs(String.class))
				  .unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonApiResponse.class))
				  .process(UnpackV2ResponseRouteBuilder::extractResponseContent).id(UNPACK_V2_API_RESPONSE_PROCESSOR_ID)
				  .marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), Object.class))
				.doCatch(Throwable.class)
				  .log(LoggingLevel.DEBUG, "Failed to unpack V2 response! Assuming that is was not wrapped into "+JsonApiResponse.class.getName()+" to begin with.")
				.endDoTry();
		//@formatter:on
	}

	private static void extractResponseContent(@NonNull final Exchange exchange) throws JsonProcessingException
	{
		final JsonApiResponse jsonApiResponse = exchange.getIn().getBody(JsonApiResponse.class);

		if (jsonApiResponse == null)
		{
			throw new RuntimeCamelException("Empty exchange body! No JsonApiResponse present!");
		}

		if (jsonApiResponse.getEndpointResponse() == null && jsonApiResponse.getRequestId() == null)
		{
			// body was not wrapped into a JsonApiResponse => restore it; as Object (decimals kept exact), so the following marshal doesn't emit it as a JSON string
			final String rawResponseBody = exchange.getProperty(PROPERTY_RAW_RESPONSE_BODY, String.class);
			exchange.getIn().setBody(JsonObjectMapperHolder.sharedJsonObjectMapper()
											 .readerFor(Object.class)
											 .with(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
											 .readValue(rawResponseBody));
			return;
		}

		exchange.getIn().setBody(jsonApiResponse.getEndpointResponse());
	}
}
