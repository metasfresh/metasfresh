package de.metas.camel.externalsystems.pcm.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
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

import static de.metas.camel.externalsystems.pcm.product.GetProductFromFileRouteBuilder.UPSERT_PRODUCT_ENDPOINT_ID;
import static org.assertj.core.api.Assertions.*;

public class GetProductsFromFileRouteBuilderTest extends CamelTestSupport
{
	private static final String PRODUCT_SYNC_DIRECT_ROUTE_ENDPOINT = "ProCareManagement-mockProductSyncRoute";

	private static final String MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT = "mock:externalSystemStatusEndpoint";
	private static final String MOCK_UPSERT_PRODUCT = "mock:UpsertProduct";

	private static final String JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStartExternalSystemRequestProduct_LocalFile.json";
	private static final String JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStopExternalSystemRequestProduct_LocalFile.json";
	private static final String PRODUCT_IMPORT_FILE_CSV = "10_Product_ImportFile.csv";
	private static final String JSON_UPSERT_PRODUCT_REQUEST_20 = "20_CamelUpsertProductRequest.json";
	private static final String JSON_UPSERT_PRODUCT_REQUEST_30 = "30_CamelUpsertProductRequest.json";

	private static final String MATERIAL_SAMPLE_RESOURCE_PATH = "/de/metas/camel/externalsystems/pcm/product/" + PRODUCT_IMPORT_FILE_CSV;

	private final LocalFileProductSyncServicePCMRouteBuilder productServiceRouteBuilder =
			new LocalFileProductSyncServicePCMRouteBuilder(Mockito.mock(ProcessLogger.class));

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder[] createRouteBuilders()
	{
		return new RouteBuilder[] { productServiceRouteBuilder, new OnDemandRoutesPCMController() };
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(GetProductsFromFileRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
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
		final MockUpsertProductProcessor mockUpsertProductProcessor = new MockUpsertProductProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, productServiceRouteBuilder.getStartProductRouteId());

		context.start();

		//when
		template.sendBody("direct:" + productServiceRouteBuilder.getStartProductRouteId(), externalSystemRequest);

		prepareSyncRouteForTesting(mockUpsertProductProcessor, LocalFileProductSyncServicePCMRouteBuilder.getProductsFromLocalFileRouteId(externalSystemRequest));

		final InputStream expectedUpsertProductRequest_20 = this.getClass().getResourceAsStream(JSON_UPSERT_PRODUCT_REQUEST_20);
		final ProductUpsertCamelRequest productUpsertCamelRequest_20 = objectMapper.readValue(expectedUpsertProductRequest_20, ProductUpsertCamelRequest.class);

		final InputStream expectedUpsertProductRequest_30 = this.getClass().getResourceAsStream(JSON_UPSERT_PRODUCT_REQUEST_30);
		final ProductUpsertCamelRequest productUpsertCamelRequest_30 = objectMapper.readValue(expectedUpsertProductRequest_30, ProductUpsertCamelRequest.class);

		final MockEndpoint productSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_PRODUCT);
		productSyncMockEndpoint.expectedBodiesReceived(productUpsertCamelRequest_20, productUpsertCamelRequest_30);

		final InputStream productImportFile = this.getClass().getResourceAsStream(MATERIAL_SAMPLE_RESOURCE_PATH);

		//and
		template.sendBodyAndHeader("direct:" + PRODUCT_SYNC_DIRECT_ROUTE_ENDPOINT, productImportFile, Exchange.FILE_NAME_ONLY, PRODUCT_IMPORT_FILE_CSV);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockUpsertProductProcessor.called).isEqualTo(2);
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
	}

	@Test
	public void disable_SyncProducts_LocalFile() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, productServiceRouteBuilder.getStartProductRouteId());
		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, productServiceRouteBuilder.getStopProductRouteId());

		final InputStream invokeStartExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest startExternalSystemRequest = objectMapper.readValue(invokeStartExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final InputStream invokeStopExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest stopExternalSystemRequest = objectMapper.readValue(invokeStopExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final String routeId = LocalFileProductSyncServicePCMRouteBuilder.getProductsFromLocalFileRouteId(stopExternalSystemRequest);

		context.start();

		//when
		template.sendBody("direct:" + productServiceRouteBuilder.getStartProductRouteId(), startExternalSystemRequest);

		assertThat(context.getRoute(routeId)).isNotNull();
		assertThat(context.getRouteController().getRouteStatus(routeId).isStarted()).isEqualTo(true);

		//and
		template.sendBody("direct:" + productServiceRouteBuilder.getStopProductRouteId(), stopExternalSystemRequest);

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
			@NonNull final MockUpsertProductProcessor mockUpsertProductProcessor,
			@NonNull final String productSyncRouteId) throws Exception
	{
		AdviceWith.adviceWith(context, productSyncRouteId,
							  advice -> {
								  advice.replaceFromWith("direct:" + PRODUCT_SYNC_DIRECT_ROUTE_ENDPOINT);

								  advice.weaveById(UPSERT_PRODUCT_ENDPOINT_ID)
										  .replace()
										  .to(MOCK_UPSERT_PRODUCT)
										  .process(mockUpsertProductProcessor);
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
	private static class MockUpsertProductProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
