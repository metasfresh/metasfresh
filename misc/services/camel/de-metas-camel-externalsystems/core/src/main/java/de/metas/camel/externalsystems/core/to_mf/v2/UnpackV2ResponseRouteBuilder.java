package de.metas.camel.externalsystems.core.to_mf.v2;

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
	public final static String UNPACK_V2_API_RESPONSE_PROCESSOR_ID = "UnpackV2ApiResponse_Processor_id";

	@Override
	public void configure()
	{
		//@formatter:off
		from(direct(UNPACK_V2_API_RESPONSE))
				.routeId(UNPACK_V2_API_RESPONSE)
				.streamCaching()
				.doTry()
				  .unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonApiResponse.class))
				  .process(UnpackV2ResponseRouteBuilder::extractResponseContent).id(UNPACK_V2_API_RESPONSE_PROCESSOR_ID)
				  .marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), Object.class))
				.doCatch(Throwable.class)
				  .log(LoggingLevel.DEBUG, "Failed to unpack V2 response! Assuming that is was not wrapped into "+JsonApiResponse.class.getName()+" to begin with.")
				.endDoTry();
		//@formatter:on
	}

	private static void extractResponseContent(@NonNull final Exchange exchange)
	{
		final JsonApiResponse jsonApiResponse = exchange.getIn().getBody(JsonApiResponse.class);

		if (jsonApiResponse == null)
		{
			throw new RuntimeCamelException("Empty exchange body! No JsonApiResponse present!");
		}

		exchange.getIn().setBody(jsonApiResponse.getEndpointResponse());
	}
}
