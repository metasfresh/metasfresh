package de.metas.camel.externalsystems.core.to_mf.v2;

import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.rest_api.v2.JsonApiResponse;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UnpackV2ResponseRouteBuilder extends EndpointRouteBuilder
{
	public final static String UNPACK_V2_API_RESPONSE = "UnpackV2ApiResponse";

	@Override
	public void configure() throws Exception
	{
		from(direct(UNPACK_V2_API_RESPONSE))
				.routeId(UNPACK_V2_API_RESPONSE)
				.unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonApiResponse.class))
				.process(UnpackV2ResponseRouteBuilder::extractResponseContent)
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), Object.class));
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
