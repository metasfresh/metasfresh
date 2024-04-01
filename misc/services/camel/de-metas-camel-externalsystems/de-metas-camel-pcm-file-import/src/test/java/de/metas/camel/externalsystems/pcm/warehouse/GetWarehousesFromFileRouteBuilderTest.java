package de.metas.camel.externalsystems.pcm.warehouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.common.v2.WarehouseUpsertCamelRequest;
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

import static de.metas.camel.externalsystems.pcm.warehouse.GetWarehouseFromFileRouteBuilder.UPSERT_BPARTNER_ENDPOINT_ID;
import static de.metas.camel.externalsystems.pcm.warehouse.GetWarehouseFromFileRouteBuilder.UPSERT_WAREHOUSE_ENDPOINT_ID;
import static org.assertj.core.api.Assertions.*;

public class GetWarehousesFromFileRouteBuilderTest extends CamelTestSupport
{
	private static final String WAREHOUSE_SYNC_DIRECT_ROUTE_ENDPOINT = "ProCareManagement-mockWarehouseSyncRoute";

	private static final String MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT = "mock:externalSystemStatusEndpoint";
	private static final String MOCK_UPSERT_BPARTNER = "mock:UpsertBPartner";
	private static final String MOCK_UPSERT_WAREHOUSE = "mock:UpsertWarehouse";

	private static final String JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStartExternalSystemRequestWarehouse_LocalFile.json";
	private static final String JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStopExternalSystemRequestWarehouse_LocalFile.json";
	private static final String WAREHOUSE_IMPORT_FILE_CSV = "10_Warehouse_ImportFile.csv";
	private static final String JSON_UPSERT_PARTNER_REQUEST_20 = "20_CamelUpsertBPartnerRequest.json";
	private static final String JSON_UPSERT_PARTNER_REQUEST_30 = "30_CamelUpsertBPartnerRequest.json";
	private static final String JSON_UPSERT_WAREHOUSE_REQUEST_40 = "40_CamelUpsertWarehouseRequest.json";
	private static final String JSON_UPSERT_WAREHOUSE_REQUEST_50 = "50_CamelUpsertWarehouseRequest.json";
	private static final String JSON_MF_UPSERT_BPARTNERS = "UpsertBPartnersMetasfreshResponse.json";

	private final LocalFileWarehouseSyncServicePCMRouteBuilder warehouseServiceRouteBuilder =
			new LocalFileWarehouseSyncServicePCMRouteBuilder(Mockito.mock(ProcessLogger.class));

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder[] createRouteBuilders()
	{
		return new RouteBuilder[] { warehouseServiceRouteBuilder, new OnDemandRoutesPCMController() };
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(GetWarehousesFromFileRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void happyFlow_SyncProducts_LocalFile() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest externalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockUpsertBPartnerProcessor mockUpsertBPartnerProcessor = new MockUpsertBPartnerProcessor();
		final MockUpsertWarehouseProcessor mockUpsertWarehouseProcessor = new MockUpsertWarehouseProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, warehouseServiceRouteBuilder.getStartWarehouseRouteId());

		context.start();

		//when
		template.sendBody("direct:" + warehouseServiceRouteBuilder.getStartWarehouseRouteId(), externalSystemRequest);

		prepareSyncRouteForTesting(mockUpsertBPartnerProcessor,
								   mockUpsertWarehouseProcessor,
								   LocalFileWarehouseSyncServicePCMRouteBuilder.getWarehousesFromLocalFileRouteId(externalSystemRequest));
		//
		final InputStream expectedUpsertPartnerRequest_20 = this.getClass().getResourceAsStream(JSON_UPSERT_PARTNER_REQUEST_20);
		final BPUpsertCamelRequest partnerUpsertCamelRequest_20 = objectMapper.readValue(expectedUpsertPartnerRequest_20, BPUpsertCamelRequest.class);
		//
		final InputStream expectedUpsertPartnerRequest_30 = this.getClass().getResourceAsStream(JSON_UPSERT_PARTNER_REQUEST_30);
		final BPUpsertCamelRequest partnerUpsertCamelRequest_30 = objectMapper.readValue(expectedUpsertPartnerRequest_30, BPUpsertCamelRequest.class);
		//
		final MockEndpoint partnerSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER);
		partnerSyncMockEndpoint.expectedBodiesReceived(partnerUpsertCamelRequest_20, partnerUpsertCamelRequest_30);
		//
		final InputStream expectedUpsertWarehouseRequest_40 = this.getClass().getResourceAsStream(JSON_UPSERT_WAREHOUSE_REQUEST_40);
		final WarehouseUpsertCamelRequest warehouseUpsertCamelRequest_40 = objectMapper.readValue(expectedUpsertWarehouseRequest_40, WarehouseUpsertCamelRequest.class);
		//
		final InputStream expectedUpsertWarehouseRequest_50 = this.getClass().getResourceAsStream(JSON_UPSERT_WAREHOUSE_REQUEST_50);
		final WarehouseUpsertCamelRequest warehouseUpsertCamelRequest_50 = objectMapper.readValue(expectedUpsertWarehouseRequest_50, WarehouseUpsertCamelRequest.class);
		//
		final MockEndpoint warehouseSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_WAREHOUSE);
		warehouseSyncMockEndpoint.expectedBodiesReceived(warehouseUpsertCamelRequest_40, warehouseUpsertCamelRequest_50);
		//
		final InputStream warehouseImportFile = this.getClass().getResourceAsStream(WAREHOUSE_IMPORT_FILE_CSV);

		// and
		template.sendBodyAndHeader("direct:" + WAREHOUSE_SYNC_DIRECT_ROUTE_ENDPOINT, warehouseImportFile, Exchange.FILE_NAME_ONLY, WAREHOUSE_IMPORT_FILE_CSV);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockUpsertBPartnerProcessor.called).isEqualTo(2);
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
	}

	@Test
	public void disable_SyncWarehouses_LocalFile() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, warehouseServiceRouteBuilder.getStartWarehouseRouteId());
		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, warehouseServiceRouteBuilder.getStopWarehouseRouteId());

		final InputStream invokeStartExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest startExternalSystemRequest = objectMapper.readValue(invokeStartExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final InputStream invokeStopExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest stopExternalSystemRequest = objectMapper.readValue(invokeStopExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final String routeId = LocalFileWarehouseSyncServicePCMRouteBuilder.getWarehousesFromLocalFileRouteId(stopExternalSystemRequest);

		context.start();

		//when
		template.sendBody("direct:" + warehouseServiceRouteBuilder.getStartWarehouseRouteId(), startExternalSystemRequest);

		assertThat(context.getRoute(routeId)).isNotNull();
		assertThat(context.getRouteController().getRouteStatus(routeId).isStarted()).isEqualTo(true);

		//and
		template.sendBody("direct:" + warehouseServiceRouteBuilder.getStopWarehouseRouteId(), stopExternalSystemRequest);

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
			@NonNull final GetWarehousesFromFileRouteBuilderTest.MockUpsertBPartnerProcessor mockUpsertBPartnerProcessor,
			@NonNull final GetWarehousesFromFileRouteBuilderTest.MockUpsertWarehouseProcessor mockUpsertWarehouseProcessor,
			@NonNull final String warehouseSyncRouteId) throws Exception
	{
		AdviceWith.adviceWith(context, warehouseSyncRouteId,
							  advice -> {

								  advice.replaceFromWith("direct:" + WAREHOUSE_SYNC_DIRECT_ROUTE_ENDPOINT);

								  advice.weaveById(UPSERT_BPARTNER_ENDPOINT_ID)
										  .replace()
										  .to(MOCK_UPSERT_BPARTNER)
										  .process(mockUpsertBPartnerProcessor);

								  advice.weaveById(UPSERT_WAREHOUSE_ENDPOINT_ID)
										  .replace()
										  .to(MOCK_UPSERT_WAREHOUSE)
										  .process(mockUpsertWarehouseProcessor);
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
	private static class MockUpsertBPartnerProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			final InputStream jsonUpsertBPartnersResponse = GetWarehousesFromFileRouteBuilderTest.class.getResourceAsStream(JSON_MF_UPSERT_BPARTNERS);

			exchange.getIn().setBody(jsonUpsertBPartnersResponse);

			called++;
		}
	}

	@Getter
	private static class MockUpsertWarehouseProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
