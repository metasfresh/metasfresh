package de.metas.camel.externalsystems.pcm.warehouse;

import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.PInstanceUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.pcm.SkipFirstLinePredicate;
import de.metas.camel.externalsystems.pcm.config.WarehouseFileEndpointConfig;
import de.metas.camel.externalsystems.pcm.warehouse.model.WarehouseRow;
import de.metas.camel.externalsystems.pcm.warehouse.processor.CreateBPartnerUpsertRequestProcessor;
import de.metas.camel.externalsystems.pcm.warehouse.processor.CreateWarehouseUpsertRequestProcessor;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ERROR_WRITE_TO_ADISSUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_WAREHOUSE_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.RouteBuilderHelper.setupJacksonDataFormatFor;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_BPARTNER_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

public class GetWarehouseFromFileRouteBuilder extends IdAwareRouteBuilder
{
	public static final String CREATE_UPSERT_BPARTNER_REQUEST_PROCESSOR_ID = "ProCareManagement-CreateBPartnerUpsertReqProcessorId";
	public static final String CREATE_WAREHOUSE_CONTEXT_PROCESSOR_ID = "ProCareManagement-WarehouseRouteContextProcessorId";

	public static final String UPSERT_BPARTNER_ENDPOINT_ID = "ProCareManagement-BPartner-upsertBPartnerEndpointId";
	public static final String UPSERT_WAREHOUSE_PROCESSOR_ID = "ProCareManagement-UpsertWarehouseProcessorId";
	public static final String UPSERT_WAREHOUSE_ENDPOINT_ID = "ProCareManagement-Warehouse-upsertWarehouseEndpointId";

	@NonNull
	private final WarehouseFileEndpointConfig fileEndpointConfig;

	@Getter
	@NonNull
	private final String routeId;

	@NonNull
	private final JsonExternalSystemRequest enabledByExternalSystemRequest;

	@NonNull
	private final PInstanceLogger pInstanceLogger;

	@Builder
	private GetWarehouseFromFileRouteBuilder(
			@NonNull final WarehouseFileEndpointConfig fileEndpointConfig,
			@NonNull final CamelContext camelContext,
			@NonNull final String routeId,
			@NonNull final JsonExternalSystemRequest enabledByExternalSystemRequest,
			@NonNull final ProcessLogger processLogger)
	{
		super(camelContext);
		validateEnableExternalSystemRequest(enabledByExternalSystemRequest);

		this.fileEndpointConfig = fileEndpointConfig;
		this.routeId = routeId;
		this.enabledByExternalSystemRequest = enabledByExternalSystemRequest;
		this.pInstanceLogger = PInstanceLogger.builder()
				.pInstanceId(enabledByExternalSystemRequest.getAdPInstanceId())
				.processLogger(processLogger)
				.build();
	}

	@Override
	public void configure()
	{
		//@formatter:off
		from(fileEndpointConfig.getWarehouseFileEndpoint())
				.id(routeId)
				.streamCaching()
				.log("Warehouse Sync Route Started with Id=" + routeId)
				.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
				.split(body().tokenize("\n"))
					.streaming()
					.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
					.filter(new SkipFirstLinePredicate())
					.doTry()
						.unmarshal(new BindyCsvDataFormat(WarehouseRow.class))
						.process(getWarehouseRouteContextProcessor()).id(CREATE_WAREHOUSE_CONTEXT_PROCESSOR_ID)
						.process(new CreateBPartnerUpsertRequestProcessor()).id(CREATE_UPSERT_BPARTNER_REQUEST_PROCESSOR_ID)
						.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert Business Partners: ${body}")
							.to("{{" + MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}").id(UPSERT_BPARTNER_ENDPOINT_ID)
							.unmarshal(setupJacksonDataFormatFor(getContext(), JsonResponseBPartnerCompositeUpsert.class))
						.choice()
							.when(bodyAs(JsonResponseBPartnerCompositeUpsert.class).isNull())
								.log(LoggingLevel.INFO, "Nothing to do! No location was created or updated !")
							.otherwise()
								.log(LoggingLevel.DEBUG, "Processing warehouse request !")
								.process(new CreateWarehouseUpsertRequestProcessor()).id(UPSERT_WAREHOUSE_PROCESSOR_ID)
								.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert Warehouses: ${body}")
									.to("{{" + MF_UPSERT_WAREHOUSE_V2_CAMEL_URI + "}}").id(UPSERT_WAREHOUSE_ENDPOINT_ID)
						.endChoice()
					.endDoTry()
					.doCatch(Throwable.class)
						.to(direct(ERROR_WRITE_TO_ADISSUE))
					.end()
				.end();
		//@formatter:on
	}

	@NonNull
	private WarehouseRouteContextProcessor getWarehouseRouteContextProcessor()
	{
		return WarehouseRouteContextProcessor.builder()
				.orgCode(enabledByExternalSystemRequest.getOrgCode())
				.orgBPartnerId(JsonMetasfreshId.of(enabledByExternalSystemRequest.getParameters().get(PARAM_BPARTNER_ID)))
				.pInstanceLogger(pInstanceLogger)
				.build();
	}

	private static void validateEnableExternalSystemRequest(@NonNull final JsonExternalSystemRequest request)
	{
		Check.assumeNotNull(request.getParameters().get(PARAM_BPARTNER_ID),
							"Org BPartner ID cannot be missing! ExternalSystem_Config_Id: " + request.getExternalSystemConfigId() + "!");
	}
}
