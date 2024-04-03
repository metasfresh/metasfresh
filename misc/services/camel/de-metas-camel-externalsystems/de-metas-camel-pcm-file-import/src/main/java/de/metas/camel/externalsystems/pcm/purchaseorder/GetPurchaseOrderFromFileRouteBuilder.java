package de.metas.camel.externalsystems.pcm.purchaseorder;

import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.PInstanceUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.PurchaseCandidateCamelRequest;
import de.metas.camel.externalsystems.pcm.SkipFirstLinePredicate;
import de.metas.camel.externalsystems.pcm.config.PurchaseOrderFileEndpointConfig;
import de.metas.camel.externalsystems.pcm.purchaseorder.model.PurchaseOrderRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_CREATE_PURCHASE_CANDIDATE_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

public class GetPurchaseOrderFromFileRouteBuilder extends IdAwareRouteBuilder
{
	public static final String UPSERT_ORDER_PROCESSOR_ID = "GetPurchaseOrderFromFileRouteBuilder.UPSERT_ORDER_PROCESSOR_ID";
	public static final String UPSERT_ORDER_ENDPOINT_ID = "GetPurchaseOrderFromFileRouteBuilder.UPSERT_ORDER_ENDPOINT_ID";

	@NonNull
	private final PurchaseOrderFileEndpointConfig fileEndpointConfig;

	@Getter
	@NonNull private final String routeId;
	@NonNull private final JsonExternalSystemRequest enabledByExternalSystemRequest;
	@NonNull private final PInstanceLogger pInstanceLogger;

	@Builder
	private GetPurchaseOrderFromFileRouteBuilder(
			@NonNull final PurchaseOrderFileEndpointConfig fileEndpointConfig,
			@NonNull final CamelContext camelContext,
			@NonNull final String routeId,
			@NonNull final JsonExternalSystemRequest enabledByExternalSystemRequest,
			@NonNull final ProcessLogger processLogger)
	{
		super(camelContext);
		this.fileEndpointConfig = fileEndpointConfig;
		this.routeId = routeId;
		this.enabledByExternalSystemRequest = enabledByExternalSystemRequest;
		this.pInstanceLogger = PInstanceLogger.builder()
				.processLogger(processLogger)
				.pInstanceId(enabledByExternalSystemRequest.getAdPInstanceId())
				.build();
	}

	@Override
	public void configure()
	{
		//@formatter:off
		from(fileEndpointConfig.getPurchaseOrderFileEndpoint())
				.id(routeId)
				.streamCaching()
				.log("Purchase Order Sync Route Started with Id=" + routeId)
				.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
				.split(body().tokenize("\n"))
					.streaming()
					.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
					.filter(new SkipFirstLinePredicate())
					.doTry()
						.unmarshal(new BindyCsvDataFormat(PurchaseOrderRow.class))
						.process(getPurchaseOrderUpsertProcessor()).id(UPSERT_ORDER_PROCESSOR_ID)
						.choice()
							.when(bodyAs(PurchaseCandidateCamelRequest.class).isNull())
								.log(LoggingLevel.INFO, "Nothing to do! No order to upsert!")
							.otherwise()
								.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert purchase order: ${body}")
								.to(direct(MF_CREATE_PURCHASE_CANDIDATE_V2_CAMEL_URI)).id(UPSERT_ORDER_ENDPOINT_ID)
							.endChoice()
						.end()
					.endDoTry()
					.doCatch(Throwable.class)
						.to(direct(MF_ERROR_ROUTE_ID))
					.end()
				.end();
		//@formatter:on
	}

	@NonNull
	private PurchaseOrderUpsertProcessor getPurchaseOrderUpsertProcessor()
	{
		return PurchaseOrderUpsertProcessor.builder()
				.externalSystemRequest(enabledByExternalSystemRequest)
				.pInstanceLogger(pInstanceLogger)
				.build();
	}
}
