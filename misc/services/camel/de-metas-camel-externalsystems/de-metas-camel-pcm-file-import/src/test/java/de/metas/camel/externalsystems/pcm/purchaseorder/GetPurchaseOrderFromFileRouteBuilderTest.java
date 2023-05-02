package de.metas.camel.externalsystems.pcm.purchaseorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.PurchaseCandidateCamelRequest;
import de.metas.camel.externalsystems.pcm.service.OnDemandRoutesPCMController;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.pcm.purchaseorder.GetPurchaseOrderFromFileRouteBuilder.UPSERT_ORDER_ENDPOINT_ID;
import static org.assertj.core.api.Assertions.*;

public class GetPurchaseOrderFromFileRouteBuilderTest extends CamelTestSupport
{
	private static final String PURCHASE_ORDER_SYNC_DIRECT_ROUTE_ENDPOINT = "ProCareManagement-mockPurchaseOrderSyncRoute";

	private static final String MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT = "mock:externalSystemStatusEndpoint";
	private static final String MOCK_UPSERT_PURCHASE_ORDER = "mock:UpsertPurchaseOrder";
	private static final String MOCK_ERROR_CONSUMER = "mock:ErrorConsumer";

	private static final String JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStartExternalSystemRequestPurchaseOrder_LocalFile.json";
	private static final String JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStopExternalSystemRequestPurchaseOrder_LocalFile.json";
	private static final String PURCHASE_ORDER_IMPORT_FILE_CSV = "10_PurchaseOrder_ImportFile.csv";
	private static final String JSON_UPSERT_PURCHASE_ORDER_REQUEST_20 = "20_CamelUpsertPurchaseOrderRequest.json";
	private static final String JSON_UPSERT_PURCHASE_ORDER_REQUEST_30 = "30_CamelUpsertPurchaseOrderRequest.json";

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
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest externalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockUpsertPurchaseOrderProcessor mockUpsertPurchaseOrderProcessor = new MockUpsertPurchaseOrderProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, purchaseOrderServiceRouteBuilder.getStartPurchaseOrderRouteId());

		context.start();

		//when
		template.sendBody("direct:" + purchaseOrderServiceRouteBuilder.getStartPurchaseOrderRouteId(), externalSystemRequest);

		prepareSyncRouteForTesting(mockUpsertPurchaseOrderProcessor, LocalFilePurchaseOrderSyncServicePCMRouteBuilder.getPurchaseOrdersFromLocalFileRouteId(externalSystemRequest));

		final InputStream expectedUpsertPurchaseOrderRequest_20 = this.getClass().getResourceAsStream(JSON_UPSERT_PURCHASE_ORDER_REQUEST_20);
		final PurchaseCandidateCamelRequest purchaseOrderUpsertCamelRequest_20 = objectMapper.readValue(expectedUpsertPurchaseOrderRequest_20, PurchaseCandidateCamelRequest.class);

		final InputStream expectedUpsertPurchaseOrderRequest_30 = this.getClass().getResourceAsStream(JSON_UPSERT_PURCHASE_ORDER_REQUEST_30);
		final PurchaseCandidateCamelRequest purchaseOrderUpsertCamelRequest_30 = objectMapper.readValue(expectedUpsertPurchaseOrderRequest_30, PurchaseCandidateCamelRequest.class);

		final MockEndpoint purcahseOrderSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_PURCHASE_ORDER);
		purcahseOrderSyncMockEndpoint.expectedBodiesReceived(purchaseOrderUpsertCamelRequest_20, purchaseOrderUpsertCamelRequest_30);

		final InputStream purchaseOrderImportFile = this.getClass().getResourceAsStream(PURCHASE_ORDER_SAMPLE_RESOURCE_PATH);

		//and
		template.sendBodyAndHeader("direct:" + PURCHASE_ORDER_SYNC_DIRECT_ROUTE_ENDPOINT, purchaseOrderImportFile, Exchange.FILE_NAME_ONLY, PURCHASE_ORDER_IMPORT_FILE_CSV);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockUpsertPurchaseOrderProcessor.called).isEqualTo(2);
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
	}

	@Test
	public void disable_SyncPurchaseOrders_LocalFile() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, purchaseOrderServiceRouteBuilder.getStartPurchaseOrderRouteId());
		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, purchaseOrderServiceRouteBuilder.getStopPurchaseOrderRouteId());

		final InputStream invokeStartExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest startExternalSystemRequest = objectMapper.readValue(invokeStartExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final InputStream invokeStopExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest stopExternalSystemRequest = objectMapper.readValue(invokeStopExternalSystemRequestIS, JsonExternalSystemRequest.class);

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
			@NonNull final MockUpsertPurchaseOrderProcessor mockUpsertPurchaseOrderProcessor,
			@NonNull final String purchaseOrderSyncRouteId) throws Exception
	{
		AdviceWith.adviceWith(context, purchaseOrderSyncRouteId,
							  advice -> {
								  advice.replaceFromWith("direct:" + PURCHASE_ORDER_SYNC_DIRECT_ROUTE_ENDPOINT);

								  advice.weaveById(UPSERT_ORDER_ENDPOINT_ID)
										  .replace()
										  .to(MOCK_UPSERT_PURCHASE_ORDER)
										  .process(mockUpsertPurchaseOrderProcessor);

								  advice.interceptSendToEndpoint("direct:" + MF_ERROR_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_ERROR_CONSUMER)
										  .process(exchange -> {
											  System.out.println("Mocked Error-Consumer invoked!");
											  System.out.println("exchange="+exchange);
											  System.out.println("exchange.exception="+exchange.getException());
											  
										  });
							  });
	}

	private static class MockExternalSystemStatusProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	@Getter
	private static class MockUpsertPurchaseOrderProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
//			throw new RuntimeCamelException("MockUpsertPurchaseOrderProcessor");
			called++;
		}
	}
}
