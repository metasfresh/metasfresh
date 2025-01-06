package de.metas.camel.externalsystems.pcm.purchaseorder;

import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.PInstanceUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.PurchaseCandidateCamelRequest;
import de.metas.camel.externalsystems.pcm.SkipFirstLinePredicate;
import de.metas.camel.externalsystems.pcm.config.LocalFileConfig;
import de.metas.camel.externalsystems.pcm.purchaseorder.model.PurchaseOrderRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateReference;
import de.metas.common.rest_api.v2.JsonPurchaseCandidatesRequest;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ERROR_WRITE_TO_ADISSUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_CREATE_PURCHASE_CANDIDATE_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ENQUEUE_PURCHASE_CANDIDATES_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.pcm.purchaseorder.ImportConstants.ENQUEUE_PURCHASE_CANDIDATES_ENDPOINT_ID;
import static de.metas.camel.externalsystems.pcm.purchaseorder.ImportConstants.PROPERTY_CURRENT_CSV_ROW;
import static de.metas.camel.externalsystems.pcm.purchaseorder.ImportConstants.UPSERT_ORDER_PROCESSOR_ID;
import static de.metas.camel.externalsystems.pcm.purchaseorder.ImportConstants.UPSERT_PURCHASE_CANDIDATE_ENDPOINT_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * Will check if there are master-data files in the root-directory and only attempt to import purchase orders if that is **not** the case.
 */
public class GetPurchaseOrderFromFileRouteBuilder extends IdAwareRouteBuilder
{
	@NonNull
	private final LocalFileConfig fileEndpointConfig;

	@Getter
	@NonNull
	private final String routeId;
	@NonNull
	private final JsonExternalSystemRequest enabledByExternalSystemRequest;
	@NonNull
	private final PInstanceLogger pInstanceLogger;

	/**
	 * @param fileEndpointConfig we need all the configs, because we need to also check if master data files exist
	 */
	@Builder
	private GetPurchaseOrderFromFileRouteBuilder(
			@NonNull final LocalFileConfig fileEndpointConfig,
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
				.routePolicy(new StallWhileMasterdataFilesExistPolicy(fileEndpointConfig, pInstanceLogger))
				.id(routeId)
				.streamCaching()
				.log("Purchase Order Sync Route Started with Id=" + routeId)
				.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
				.split(body().tokenize("\n"), new GetPurchaseOrderFromFileRouteAggregationStrategy())
					.streaming()
					.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
					.filter(new SkipFirstLinePredicate())
					.doTry()
						.unmarshal(new BindyCsvDataFormat(PurchaseOrderRow.class))
						.process(getUpsertPurchaseCahndidateProcessor()).id(UPSERT_ORDER_PROCESSOR_ID)
						.choice()
							.when(bodyAs(PurchaseCandidateCamelRequest.class).isNull())
								.log(LoggingLevel.INFO, "Nothing to do! No order to upsert!")
							.otherwise()
								.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert purchase order: ${body}")
								.to(direct(MF_CREATE_PURCHASE_CANDIDATE_V2_CAMEL_URI)).id(UPSERT_PURCHASE_CANDIDATE_ENDPOINT_ID)
								.process(this::updateContextAfterSuccess)
							.endChoice()
						.end()
					.endDoTry()
					.doCatch(Throwable.class)
						.process(this::updateContextAfterError)
						.to(direct(ERROR_WRITE_TO_ADISSUE))
					.end()
				.end().end() // split
				.process(this::enqueueCandidatesProcessor)
				.to(direct(MF_ENQUEUE_PURCHASE_CANDIDATES_V2_CAMEL_URI)).id(ENQUEUE_PURCHASE_CANDIDATES_ENDPOINT_ID);
		//@formatter:on
	}

	@NonNull
	private UpsertPurchaseCandidateProcessor getUpsertPurchaseCahndidateProcessor()
	{
		return UpsertPurchaseCandidateProcessor.builder()
				.externalSystemRequest(enabledByExternalSystemRequest)
				.pInstanceLogger(pInstanceLogger)
				.build();
	}

	private void updateContextAfterSuccess(@NonNull final Exchange exchange)
	{
		final ImportOrdersRouteContext importOrdersRouteContext = ImportUtil.getOrCreateImportOrdersRouteContext(exchange);
		
		final PurchaseOrderRow purchaseOrderRow = exchange.getProperty(PROPERTY_CURRENT_CSV_ROW, PurchaseOrderRow.class);
		final JsonExternalId externalHeaderId = JsonExternalId.of(purchaseOrderRow.getExternalHeaderId());
		if (!importOrdersRouteContext.getPurchaseCandidatesWithError().contains(externalHeaderId))
		{
			importOrdersRouteContext.getPurchaseCandidatesToProcess().add(externalHeaderId);
		}
	}

	private void updateContextAfterError(@NonNull final Exchange exchange)
	{
		final ImportOrdersRouteContext importOrdersRouteContext = ImportUtil.getOrCreateImportOrdersRouteContext(exchange);

		final PurchaseOrderRow csvRow = exchange.getProperty(PROPERTY_CURRENT_CSV_ROW, PurchaseOrderRow.class);

		final Exception ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		final RuntimeCamelException augmentedEx = new RuntimeCamelException("Exception processing file " + exchange.getIn().getHeader(Exchange.FILE_NAME_ONLY) + " and row '" + csvRow + "'", ex);
		exchange.setProperty(Exchange.EXCEPTION_CAUGHT, augmentedEx);

		if (csvRow == null || Check.isBlank(csvRow.getExternalHeaderId()))
		{
			importOrdersRouteContext.errorInUnknownRow();
			return;
		}

		final JsonExternalId externalHeaderId = JsonExternalId.of(csvRow.getExternalHeaderId());
		importOrdersRouteContext.getPurchaseCandidatesToProcess().remove(externalHeaderId);
		importOrdersRouteContext.getPurchaseCandidatesWithError().add(externalHeaderId);
	}

	private void enqueueCandidatesProcessor(@NonNull final Exchange exchange)
	{
		final ImportOrdersRouteContext importOrdersRouteContext = ImportUtil.getOrCreateImportOrdersRouteContext(exchange);

		if (importOrdersRouteContext.isDoNotProcessAtAll())
		{
			final Object fileName = exchange.getIn().getHeader(Exchange.FILE_NAME_ONLY);
			throw new RuntimeCamelException("No purchase order candidates from file " + fileName.toString() + " can be imported to metasfresh");
		}

		final JsonPurchaseCandidatesRequest.JsonPurchaseCandidatesRequestBuilder builder = JsonPurchaseCandidatesRequest.builder();

		for (final JsonExternalId externalId : importOrdersRouteContext.getPurchaseCandidatesToProcess())
		{
			final JsonPurchaseCandidateReference reference = JsonPurchaseCandidateReference.builder().externalHeaderId(externalId).build();
			builder.purchaseCandidate(reference);
		}

		exchange.getIn().setBody(builder.build());
	}
}
