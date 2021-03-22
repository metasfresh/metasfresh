package de.metas.camel.ebay;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.metas.camel.ebay.processor.CreateMetasModelUpsertReqProcessor;
import de.metas.camel.ebay.processor.GetEbayOrdersProcessor;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import lombok.NonNull;

/**
 * Route to fetch ebay orders and put them as order line candidates into metasfresh.
 * 
 * 
 * @author Werner Gaulke
 *
 */
@Component
public class GetEbayOrdersRouteBuilder extends RouteBuilder{
	
	private static final String EXT_ID_PREFIX = "ebay";
	
	public static final String GET_ORDERS_ROUTE_ID = "Ebay-getOrders";
	public static final String PROCESS_ORDER_ROUTE_ID = "Ebay-processOrder";

	public static final String GET_ORDERS_PROCESSOR_ID = "GetEbayOrdersProcessorId";
	public static final String CREATE_ESR_QUERY_REQ_PROCESSOR_ID = "CreateBPartnerESRQueryProcessorId";
	public static final String CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID = "CreateBPartnerUpsertReqProcessorId";

	

	@Override
	public void configure() {
		
		log.trace("Configure ebay order route");
		
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(StaticEndpointBuilders.direct(MF_ERROR_ROUTE_ID));
		
		
		//@formatter:off
		from(StaticEndpointBuilders.direct(GET_ORDERS_ROUTE_ID))
			.routeId(GET_ORDERS_ROUTE_ID)
			.log("Route invoked")
			.process(new GetEbayOrdersProcessor()).id(GET_ORDERS_PROCESSOR_ID)
			.split(body())
			.to(StaticEndpointBuilders.direct(PROCESS_ORDER_ROUTE_ID));
		
		
		from(StaticEndpointBuilders.direct(PROCESS_ORDER_ROUTE_ID))
			.routeId(PROCESS_ORDER_ROUTE_ID)
			.log("Route invoked")
			.process(new CreateMetasModelUpsertReqProcessor()).id(CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID)
	
			.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert BPartners: ${body}")
			.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_CAMEL_URI + "}}");
			//@formatter:on
	}
	
	
	@NonNull
	private JacksonDataFormat setupJacksonDataFormatFor(
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
