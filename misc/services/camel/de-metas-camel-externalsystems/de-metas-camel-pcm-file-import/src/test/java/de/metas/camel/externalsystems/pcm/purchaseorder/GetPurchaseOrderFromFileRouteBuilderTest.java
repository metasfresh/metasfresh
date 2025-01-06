package de.metas.camel.externalsystems.pcm.purchaseorder;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.PurchaseCandidateCamelRequest;
import de.metas.camel.externalsystems.pcm.service.OnDemandRoutesPCMController;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonPurchaseCandidate;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateCreateItem;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateResponse;
import de.metas.common.rest_api.v2.JsonPurchaseCandidatesRequest;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ERROR_WRITE_TO_ADISSUE;
import static de.metas.camel.externalsystems.common.TestUtil.loadJSON;
import static de.metas.camel.externalsystems.pcm.purchaseorder.ImportConstants.ENQUEUE_PURCHASE_CANDIDATES_ENDPOINT_ID;
import static de.metas.camel.externalsystems.pcm.purchaseorder.ImportConstants.UPSERT_PURCHASE_CANDIDATE_ENDPOINT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_BPARTNER_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_PURCHASE_ORDER_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_ROOT_LOCATION;
import static org.assertj.core.api.Assertions.*;

public class GetPurchaseOrderFromFileRouteBuilderTest extends CamelTestSupport
{
	private static final String PURCHASE_ORDER_SYNC_DIRECT_ROUTE_ENDPOINT = "ProCareManagement-mockPurchaseOrderSyncRoute";

	private static final String MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT = "mock:externalSystemStatusEndpoint";
	private static final String MOCK_UPSERT_PURCHASE_CANDIDATE = "mock:UpsertPurchaseCandidate";
	private static final String MOCK_PROCESS_PURCHASE_CANDIDATES = "mock:ProcessPurchaseCandidate";
	private static final String MOCK_ERROR_CONSUMER = "mock:ErrorConsumer";

	private static final String JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "startStop/0_JsonStartExternalSystemRequestPurchaseOrder_LocalFile.json";
	private static final String JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "startStop/0_JsonStopExternalSystemRequestPurchaseOrder_LocalFile.json";
	private static final String PURCHASE_ORDER_IMPORT_FILE_CSV = "happyFlow/10_PurchaseOrder_ImportFile.csv";
	private static final String JSON_UPSERT_PURCHASE_ORDER_REQUEST_20 = "happyFlow/20_CamelUpsertPurchaseOrderRequest.json";
	private static final String JSON_UPSERT_PURCHASE_ORDER_REQUEST_30 = "happyFlow/30_CamelUpsertPurchaseOrderRequest.json";
	private static final String JSON_PROCESS_PURCHASE_ORDER_REQUEST_40 = "happyFlow/40_CamelProcessPurchaseOrderRequest.json";

	private static final String PURCHASE_ORDER_SAMPLE_RESOURCE_PATH = "/de/metas/camel/externalsystems/pcm/purchaseorder/" + PURCHASE_ORDER_IMPORT_FILE_CSV;

	private final LocalFilePurchaseOrderSyncServicePCMRouteBuilder purchaseOrderServiceRouteBuilder =
			new LocalFilePurchaseOrderSyncServicePCMRouteBuilder(Mockito.mock(ProcessLogger.class));

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder[] createRouteBuilders()
	{
		return new RouteBuilder[] { purchaseOrderServiceRouteBuilder, new OnDemandRoutesPCMController() };
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(GetPurchaseOrderFromFileRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void happyFlow_SyncPurchaseOrders_LocalFile() throws Exception
	{
		final JsonExternalSystemRequest startRequest = loadJSON(this, JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE, JsonExternalSystemRequest.class);

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockUpsertPurchaseCandicateProcessor mockUpsertPurchaseCandicateProcessor = new MockUpsertPurchaseCandicateProcessor();
		final MockProcessPurchaseCandicatesProcessor mockProcessPurchaseCandicatesProcessor = new MockProcessPurchaseCandicatesProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, purchaseOrderServiceRouteBuilder.getStartPurchaseOrderRouteId());

		final PurchaseCandidateCamelRequest purchaseOrderUpsertCamelRequest_20 = loadJSON(this, JSON_UPSERT_PURCHASE_ORDER_REQUEST_20, PurchaseCandidateCamelRequest.class);
		final PurchaseCandidateCamelRequest purchaseOrderUpsertCamelRequest_30 = loadJSON(this, JSON_UPSERT_PURCHASE_ORDER_REQUEST_30, PurchaseCandidateCamelRequest.class);
		final JsonPurchaseCandidatesRequest purchaseOrderProcessCamelRequest_40 = loadJSON(this, JSON_PROCESS_PURCHASE_ORDER_REQUEST_40, JsonPurchaseCandidatesRequest.class);

		final MockEndpoint purchaseOrderSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_PURCHASE_CANDIDATE);
		purchaseOrderSyncMockEndpoint.expectedBodiesReceived(purchaseOrderUpsertCamelRequest_20, purchaseOrderUpsertCamelRequest_30);

		final MockEndpoint purchaseOrderProcessMockEndpoint = getMockEndpoint(MOCK_PROCESS_PURCHASE_CANDIDATES);
		purchaseOrderProcessMockEndpoint.expectedBodiesReceived(purchaseOrderProcessCamelRequest_40);

		final MockEndpoint errorMockEndpoint = getMockEndpoint(MOCK_ERROR_CONSUMER);
		errorMockEndpoint.expectedMessageCount(0);
		
		context.start();

		//when
		template.sendBody("direct:" + purchaseOrderServiceRouteBuilder.getStartPurchaseOrderRouteId(), startRequest);

		prepareSyncRouteForTesting(true,
								   mockUpsertPurchaseCandicateProcessor,
								   mockProcessPurchaseCandicatesProcessor,
								   LocalFilePurchaseOrderSyncServicePCMRouteBuilder.getPurchaseOrdersFromLocalFileRouteId(startRequest));

		final InputStream purchaseOrderImportFile = this.getClass().getResourceAsStream(PURCHASE_ORDER_SAMPLE_RESOURCE_PATH);

		//and
		template.sendBodyAndHeader("direct:" + PURCHASE_ORDER_SYNC_DIRECT_ROUTE_ENDPOINT, purchaseOrderImportFile, Exchange.FILE_NAME_ONLY, PURCHASE_ORDER_IMPORT_FILE_CSV);
		
		//then
		assertMockEndpointsSatisfied();
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
		assertThat(mockUpsertPurchaseCandicateProcessor.called).isEqualTo(2);
		assertThat(mockProcessPurchaseCandicatesProcessor.called).isEqualTo(1);
	}

	/**
	 * Tests mostly {@link StallWhileMasterdataFilesExistPolicy}, to make sure that it ignores an order-file if there are still master data file lying around
	 * <p>
	 * Note: actually lets the route under test read the input file from disk    
	 */
	@Test
	public void masterDataFileExists_SyncPurchaseOrders_LocalFile() throws Exception
	{
		// ..assuming that we run within the de-metas-camel-pcm-file-import folder
		final String rootFolderStr = "src/test/resources/de/metas/camel/externalsystems/pcm/purchaseorder/masterDataFileExists";
		final Path path = Paths.get(rootFolderStr);

		final MockEndpoint errorMockEndpoint = getMockEndpoint(MOCK_ERROR_CONSUMER);
		errorMockEndpoint.expectedMessageCount(0);
		
		test_NothingCreated(path);

		assertThat(new File(rootFolderStr + "/processedDirectory")).doesNotExist();
		assertThat(new File(rootFolderStr + "/errorDirectory")).doesNotExist();
	}

	/**
	 * Note: actually lets the route under test read the input file from disk.
	 */
	@Test
	public void csvFileWithError_SyncPurchaseOrders_LocalFile() throws Exception
	{
		// ..assuming that we run within the de-metas-camel-pcm-file-import folder
		final String rootFolderStr = "src/test/resources/de/metas/camel/externalsystems/pcm/purchaseorder/csvFileWithError";
		final Path path = Paths.get(rootFolderStr);

		final MockEndpoint errorMockEndpoint = getMockEndpoint(MOCK_ERROR_CONSUMER);
		errorMockEndpoint.expectedMessageCount(1);

		test_NothingCreated(path);
		
		assertThat(new File(rootFolderStr + "/errorDirectory")).isNotEmptyDirectory();
		assertThat(new File(rootFolderStr + "/processedDirectory")).doesNotExist();
	}

	private void test_NothingCreated(@NonNull final Path path) throws Exception
	{
		final File requestRootFolder = path.toFile();
		assertThat(requestRootFolder).isDirectory();

		final JsonExternalSystemRequest startRequest = loadJSON(this, JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE, JsonExternalSystemRequest.class);
		startRequest.getParameters().put(PARAM_LOCAL_FILE_ROOT_LOCATION, requestRootFolder.getAbsolutePath());
		startRequest.getParameters().put(PARAM_LOCAL_FILE_PURCHASE_ORDER_FILE_NAME_PATTERN, "*PurchaseOrder_ImportFile.csv");
		startRequest.getParameters().put(PARAM_LOCAL_FILE_BPARTNER_FILE_NAME_PATTERN, "*_bPartnerMasterData.csv");

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockUpsertPurchaseCandicateProcessor mockUpsertPurchaseCandicateProcessor = new MockUpsertPurchaseCandicateProcessor();
		final MockProcessPurchaseCandicatesProcessor mockProcessPurchaseCandicatesProcessor = new MockProcessPurchaseCandicatesProcessor();

		final MockEndpoint purchaseOrderSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_PURCHASE_CANDIDATE);
		purchaseOrderSyncMockEndpoint.expectedMessageCount(0);
		purchaseOrderSyncMockEndpoint.setAssertPeriod(2000);

		final MockEndpoint purchaseOrderProcessMockEndpoint = getMockEndpoint(MOCK_PROCESS_PURCHASE_CANDIDATES);
		purchaseOrderProcessMockEndpoint.expectedMessageCount(0);
		purchaseOrderProcessMockEndpoint.setAssertPeriod(2000);

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, purchaseOrderServiceRouteBuilder.getStartPurchaseOrderRouteId());

		context.start();

		//when
		template.sendBody("direct:" + purchaseOrderServiceRouteBuilder.getStartPurchaseOrderRouteId(), startRequest);

		// cant set this up this earlier, because they route that we modify exists only now
		prepareSyncRouteForTesting(false,
								   mockUpsertPurchaseCandicateProcessor,
								   mockProcessPurchaseCandicatesProcessor,
								   LocalFilePurchaseOrderSyncServicePCMRouteBuilder.getPurchaseOrdersFromLocalFileRouteId(startRequest));

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
		assertThat(mockUpsertPurchaseCandicateProcessor.called).isEqualTo(0);
		assertThat(mockProcessPurchaseCandicatesProcessor.called).isEqualTo(0);
	}

	@Test
	public void startStop_SyncPurchaseOrders_LocalFile() throws Exception
	{
		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, purchaseOrderServiceRouteBuilder.getStartPurchaseOrderRouteId());
		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, purchaseOrderServiceRouteBuilder.getStopPurchaseOrderRouteId());

		final JsonExternalSystemRequest startExternalSystemRequest = loadJSON(this, JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE, JsonExternalSystemRequest.class);
		final JsonExternalSystemRequest stopExternalSystemRequest = loadJSON(this, JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE, JsonExternalSystemRequest.class);
		
		final String routeId = LocalFilePurchaseOrderSyncServicePCMRouteBuilder.getPurchaseOrdersFromLocalFileRouteId(stopExternalSystemRequest);

		context.start();

		//when
		template.sendBody("direct:" + purchaseOrderServiceRouteBuilder.getStartPurchaseOrderRouteId(), startExternalSystemRequest);

		assertThat(context.getRoute(routeId)).isNotNull();
		assertThat(context.getRouteController().getRouteStatus(routeId).isStarted()).isEqualTo(true);

		//and
		template.sendBody("direct:" + purchaseOrderServiceRouteBuilder.getStopPurchaseOrderRouteId(), stopExternalSystemRequest);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(2);

		assertThat(context.getRoute(routeId)).isNull();
	}

	private void prepareStartStopRouteForTesting(
			@NonNull final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor,
			@NonNull final String routeId) throws Exception
	{
		AdviceWith.adviceWith(context, routeId,
							  advice -> advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT)
									  .process(mockExternalSystemStatusProcessor));
	}

	private void prepareSyncRouteForTesting(
			final boolean replaceFileEndpoint,
			@NonNull final MockUpsertPurchaseCandicateProcessor mockUpsertPurchaseCandicateProcessor,
			@NonNull final MockProcessPurchaseCandicatesProcessor mockProcessPurchaseCandicatesProcessor,
			@NonNull final String purchaseOrderSyncRouteId) throws Exception
	{
		AdviceWith.adviceWith(context, purchaseOrderSyncRouteId,
							  advice -> {
								  if (replaceFileEndpoint)
								  {
									  advice.replaceFromWith("direct:" + PURCHASE_ORDER_SYNC_DIRECT_ROUTE_ENDPOINT);
								  }
								  advice.weaveById(UPSERT_PURCHASE_CANDIDATE_ENDPOINT_ID)
										  .replace()
										  .to(MOCK_UPSERT_PURCHASE_CANDIDATE)
										  .process(mockUpsertPurchaseCandicateProcessor);

								  advice.weaveById(ENQUEUE_PURCHASE_CANDIDATES_ENDPOINT_ID)
										  .replace()
										  .to(MOCK_PROCESS_PURCHASE_CANDIDATES)
										  .process(mockProcessPurchaseCandicatesProcessor);

								  advice.interceptSendToEndpoint("direct:" + ERROR_WRITE_TO_ADISSUE)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_ERROR_CONSUMER)
										  .process(exchange -> {
											  System.out.println("Mocked Error-Consumer invoked!");
											  System.out.println("exchange=" + exchange);
											  System.out.println("exchange.exception=" + exchange.getException());

										  });
							  });
	}

	private static class MockExternalSystemStatusProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(@NonNull final Exchange exchange)
		{
			called++;
		}
	}

	@Getter
	private static class MockUpsertPurchaseCandicateProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(@NonNull final Exchange exchange)
		{
			final PurchaseCandidateCamelRequest request = exchange.getIn().getBody(PurchaseCandidateCamelRequest.class);
			final JsonPurchaseCandidateResponse.JsonPurchaseCandidateResponseBuilder response = JsonPurchaseCandidateResponse.builder();

			for (final JsonPurchaseCandidateCreateItem purchaseCandidate : request.getJsonPurchaseCandidateCreateRequest().getPurchaseCandidates())
			{
				final JsonPurchaseCandidate responseItem = JsonPurchaseCandidate.builder()
						.externalHeaderId(JsonExternalId.of(purchaseCandidate.getExternalHeaderId()))
						.externalLineId(JsonExternalId.of(purchaseCandidate.getExternalLineId()))
						.metasfreshId(JsonMetasfreshId.of("123"))
						.build();
				response.purchaseCandidate(responseItem);
			}

			exchange.getIn().setBody(response.build());
			called++;
		}
	}

	@Getter
	private static class MockProcessPurchaseCandicatesProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(@NonNull final Exchange exchange)
		{
			called++;
		}
	}
}
