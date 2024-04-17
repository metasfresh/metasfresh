package de.metas.camel.externalsystems.pcm.purchaseorder;

import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.PInstanceUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.PurchaseCandidateCamelRequest;
import de.metas.camel.externalsystems.pcm.SkipFirstLinePredicate;
import de.metas.camel.externalsystems.pcm.config.LocalFileConfig;
import de.metas.camel.externalsystems.pcm.purchaseorder.model.PurchaseOrderRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.v2.JsonPurchaseCandidate;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateReference;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateResponse;
import de.metas.common.rest_api.v2.JsonPurchaseCandidatesRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_CREATE_PURCHASE_CANDIDATE_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ENQUEUE_PURCHASE_CANDIDATES_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * Will check if there are master-data files in the root-directory and only attempt to import purchase orders if that is **not** the case.
 */
public class GetPurchaseOrderFromFileRouteBuilder extends IdAwareRouteBuilder
{
	public static final String UPSERT_ORDER_PROCESSOR_ID = "GetPurchaseOrderFromFileRouteBuilder.UPSERT_ORDER_PROCESSOR_ID";
	public static final String UPSERT_ORDER_ENDPOINT_ID = "GetPurchaseOrderFromFileRouteBuilder.UPSERT_ORDER_ENDPOINT_ID";
	public static final String ENQUEUE_CANDIDATES_PROCESSOR_ID = "GetPurchaseOrderFromFileRouteBuilder.ENQUEUE_CANDIDATES_PROCESSOR_ID";

	public static final String PROPERTY_CURRENT_CSV_ROW = "CurrentCsvRow";
	public static final String PROPERTY_FOUND_MASTER_DATA_FILENAME = "FoundMasterDataFileName";
	public static final String PROPERTY_FOUND_MASTER_DATA_FILE = "FoundMasterDataFile";
	public static final String PROPERTY_IMPORT_ORDERS_CONTEXT = "ImportOrdersContext";

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
				.id(routeId)
				.streamCaching()
				.log("Purchase Order Sync Route Started with Id=" + routeId)
				.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
				.process(this::checkIfMasterDateFilesExist)
				.choice()
					.when(exchangeProperty(PROPERTY_FOUND_MASTER_DATA_FILE).isEqualTo(true)) // there is at least one masterdata file
						.log(LoggingLevel.INFO, "There is at least the masterdata file ${exchangeProperty." + PROPERTY_FOUND_MASTER_DATA_FILE + "} which has to be processed first => doing nothing for now.")
					.otherwise()
						.log("No master data file found => continuing")
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
										.process(this::updateContextAfterSuccess)
									.endChoice()
								.end()
							.endDoTry()
							.doCatch(Throwable.class)
								.process(this::updateContextAfterError)
								.to(direct(MF_ERROR_ROUTE_ID))
							.end()
						.end() // split
					    .to(direct(routeId+"-processEnqueueCandidates"))
					.endChoice()
				.end();
		//@formatter:on

		from(direct(routeId + "-processEnqueueCandidates"))
				.routeId(routeId + "-processEnqueueCandidates")
				.process(this::enqueueCandidatesProcessor).id(ENQUEUE_CANDIDATES_PROCESSOR_ID)
				.to(direct(MF_ENQUEUE_PURCHASE_CANDIDATES_V2_CAMEL_URI));
	}

	@NonNull
	private PurchaseOrderUpsertProcessor getPurchaseOrderUpsertProcessor()
	{
		return PurchaseOrderUpsertProcessor.builder()
				.externalSystemRequest(enabledByExternalSystemRequest)
				.pInstanceLogger(pInstanceLogger)
				.build();
	}

	private void checkIfMasterDateFilesExist(@NonNull final Exchange exchange)
	{
		final PathMatcher bpartnerFileMatcher = FileSystems.getDefault().getPathMatcher("glob:" + fileEndpointConfig.getFileNamePatternBPartner());
		final PathMatcher warehouseFileMatcher = FileSystems.getDefault().getPathMatcher("glob:" + fileEndpointConfig.getFileNamePatternWarehouse());
		final PathMatcher productFileMatcher = FileSystems.getDefault().getPathMatcher("glob:" + fileEndpointConfig.getFileNamePatternProduct());

		try
		{ // we could have the following with less lines, but IMO this way it's easier to debug
			Files.walkFileTree(Paths.get(fileEndpointConfig.getRootLocation()), new SimpleFileVisitor<>()
			{
				@Override
				public FileVisitResult visitFile(@NonNull final Path file, final BasicFileAttributes attrs)
				{
					if (bpartnerFileMatcher.matches(file))
					{
						exchange.setProperty(PROPERTY_FOUND_MASTER_DATA_FILENAME, file);
						return FileVisitResult.TERMINATE;
					}
					if (warehouseFileMatcher.matches(file))
					{
						exchange.setProperty(PROPERTY_FOUND_MASTER_DATA_FILENAME, file);
						return FileVisitResult.TERMINATE;
					}
					if (productFileMatcher.matches(file))
					{
						exchange.setProperty(PROPERTY_FOUND_MASTER_DATA_FILENAME, file);
						return FileVisitResult.TERMINATE;
					}

					return FileVisitResult.CONTINUE;
				}
			});

			final boolean atLEastOneFileFound = exchange.getProperty(PROPERTY_FOUND_MASTER_DATA_FILENAME) != null;
			exchange.setProperty(PROPERTY_FOUND_MASTER_DATA_FILE, atLEastOneFileFound);
		}
		catch (final IOException e)
		{
			throw new RuntimeCamelException("Caught exception while checking for existing master data files", e);
		}
	}

	private void updateContextAfterSuccess(@NonNull final Exchange exchange)
	{
		final ImportOrdersRouteContext importOrdersRouteContext = getOrCreateImportOrdersRouteContext(exchange);

		final JsonPurchaseCandidateResponse jsonPurchaseCandidateResponse = exchange.getIn().getBody(JsonPurchaseCandidateResponse.class);
		for (final JsonPurchaseCandidate purchaseCandidates : jsonPurchaseCandidateResponse.getPurchaseCandidates())
		{
			final JsonExternalId externalHeaderId = purchaseCandidates.getExternalHeaderId();
			if (!importOrdersRouteContext.getPurchaseCandidatesWithError().contains(externalHeaderId))
			{
				importOrdersRouteContext.getPurchaseCandidatesToProcess().add(externalHeaderId);
			}
		}
	}

	private void updateContextAfterError(@NonNull final Exchange exchange)
	{
		final ImportOrdersRouteContext importOrdersRouteContext = getOrCreateImportOrdersRouteContext(exchange);

		final PurchaseOrderRow csvRow = exchange.getProperty(PROPERTY_CURRENT_CSV_ROW, PurchaseOrderRow.class);

		if (csvRow == null)
		{
			importOrdersRouteContext.doNotProcessAtAll();
			return;
		}

		final JsonExternalId externalHeaderId = JsonExternalId.of(csvRow.getExternalHeaderId());
		importOrdersRouteContext.getPurchaseCandidatesToProcess().remove(externalHeaderId);
		importOrdersRouteContext.getPurchaseCandidatesWithError().add(externalHeaderId);
	}

	private static ImportOrdersRouteContext getOrCreateImportOrdersRouteContext(@NonNull final Exchange exchange)
	{
		ImportOrdersRouteContext importOrdersRouteContext = exchange.getProperty(PROPERTY_IMPORT_ORDERS_CONTEXT, ImportOrdersRouteContext.class);
		if (importOrdersRouteContext == null)
		{
			importOrdersRouteContext = new ImportOrdersRouteContext();
			exchange.setProperty(PROPERTY_IMPORT_ORDERS_CONTEXT, importOrdersRouteContext);
		}
		return importOrdersRouteContext;
	}

	private void enqueueCandidatesProcessor(@NonNull final Exchange exchange)
	{
		final ImportOrdersRouteContext importOrdersRouteContext =
				ProcessorHelper.getPropertyOrThrowError(exchange, PROPERTY_IMPORT_ORDERS_CONTEXT, ImportOrdersRouteContext.class);

		final JsonPurchaseCandidatesRequest.JsonPurchaseCandidatesRequestBuilder builder = JsonPurchaseCandidatesRequest.builder();

		for (final JsonExternalId externalId : importOrdersRouteContext.getPurchaseCandidatesToProcess())
		{
			final JsonPurchaseCandidateReference reference = JsonPurchaseCandidateReference.builder().externalHeaderId(externalId).build();
			builder.purchaseCandidate(reference);
		}

		exchange.getIn().setBody(builder.build());
	}
}
