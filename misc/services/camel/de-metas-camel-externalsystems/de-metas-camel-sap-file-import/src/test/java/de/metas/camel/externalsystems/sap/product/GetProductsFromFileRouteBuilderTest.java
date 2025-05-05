/*
 * #%L
 * de-metas-camel-sap
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.camel.externalsystems.sap.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.sap.service.OnDemandRoutesController;
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

import static de.metas.camel.externalsystems.sap.product.GetProductsFromFileRouteBuilder.UPSERT_PRODUCT_ENDPOINT_ID;
import static de.metas.camel.externalsystems.sap.product.LocalFileProductSyncServiceRouteBuilder.START_PRODUCTS_SYNC_LOCAL_FILE_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.product.LocalFileProductSyncServiceRouteBuilder.STOP_PRODUCTS_SYNC_LOCAL_FILE_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.product.SFTPProductSyncServiceRouteBuilder.START_PRODUCTS_SYNC_SFTP_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.product.SFTPProductSyncServiceRouteBuilder.STOP_PRODUCTS_SYNC_SFTP_ROUTE_ID;
import static org.assertj.core.api.Assertions.*;

public class GetProductsFromFileRouteBuilderTest extends CamelTestSupport
{
	private static final String PRODUCT_SYNC_DIRECT_ROUTE_ENDPOINT = "SAP-mockProductSyncRoute";

	private static final String MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT = "mock:externalSystemStatusEndpoint";
	private static final String MOCK_UPSERT_PRODUCT = "mock:UpsertProduct";

	private static final String JSON_START_EXTERNAL_SYSTEM_REQUEST_SFTP = "0_JsonStartExternalSystemRequestProduct_SFTP.json";
	private static final String JSON_STOP_EXTERNAL_SYSTEM_REQUEST_SFTP = "0_JsonStopExternalSystemRequestProduct_SFTP.json";
	private static final String JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStartExternalSystemRequestProduct_LocalFile.json";
	private static final String JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStopExternalSystemRequestProduct_LocalFile.json";
	private static final String MATERIAL_SAMPLE_DAT_FILE = "10_MaterialSample.dat";
	private static final String JSON_UPSERT_PRODUCT_REQUEST_20 = "20_CamelUpsertProductRequest.json";
	private static final String JSON_UPSERT_PRODUCT_REQUEST_30 = "30_CamelUpsertProductRequest.json";

	private static final String MATERIAL_SAMPLE_RESOURCE_PATH = "/de/metas/camel/externalsystems/sap/product/" + MATERIAL_SAMPLE_DAT_FILE;

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder[] createRouteBuilders()
	{
		return new RouteBuilder[] {
				new SFTPProductSyncServiceRouteBuilder(Mockito.mock(ProcessLogger.class)),
				new LocalFileProductSyncServiceRouteBuilder(Mockito.mock(ProcessLogger.class)),
				new OnDemandRoutesController() };
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
	public void happyFlow_SyncProducts_SFTP() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_SFTP);
		final JsonExternalSystemRequest externalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockUpsertProductProcessor mockUpsertProductProcessor = new MockUpsertProductProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, START_PRODUCTS_SYNC_SFTP_ROUTE_ID);

		context.start();

		//when
		template.sendBody("direct:" + START_PRODUCTS_SYNC_SFTP_ROUTE_ID, externalSystemRequest);

		prepareSyncRouteForTesting(mockUpsertProductProcessor, SFTPProductSyncServiceRouteBuilder.getProductsFromSFTPServerRouteId(externalSystemRequest));

		final InputStream expectedUpsertProductRequest_20 = this.getClass().getResourceAsStream(JSON_UPSERT_PRODUCT_REQUEST_20);
		final ProductUpsertCamelRequest productUpsertCamelRequest_20 = objectMapper.readValue(expectedUpsertProductRequest_20, ProductUpsertCamelRequest.class);

		final InputStream expectedUpsertProductRequest_30 = this.getClass().getResourceAsStream(JSON_UPSERT_PRODUCT_REQUEST_30);
		final ProductUpsertCamelRequest productUpsertCamelRequest_30 = objectMapper.readValue(expectedUpsertProductRequest_30, ProductUpsertCamelRequest.class);

		final MockEndpoint productSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_PRODUCT);
		productSyncMockEndpoint.expectedBodiesReceived(productUpsertCamelRequest_20, productUpsertCamelRequest_30);

		final InputStream materialSampleInputStream = this.getClass().getResourceAsStream(MATERIAL_SAMPLE_RESOURCE_PATH);

		//and
		template.sendBodyAndHeader("direct:" + PRODUCT_SYNC_DIRECT_ROUTE_ENDPOINT, materialSampleInputStream, Exchange.FILE_NAME_ONLY, MATERIAL_SAMPLE_DAT_FILE);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockUpsertProductProcessor.called).isEqualTo(2);
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
	}

	@Test
	public void happyFlow_SyncProducts_LocalFile() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest externalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockUpsertProductProcessor mockUpsertProductProcessor = new MockUpsertProductProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, START_PRODUCTS_SYNC_LOCAL_FILE_ROUTE_ID);

		context.start();

		//when
		template.sendBody("direct:" + START_PRODUCTS_SYNC_LOCAL_FILE_ROUTE_ID, externalSystemRequest);

		prepareSyncRouteForTesting(mockUpsertProductProcessor, LocalFileProductSyncServiceRouteBuilder.getProductsFromLocalFileRouteId(externalSystemRequest));

		final InputStream expectedUpsertProductRequest_20 = this.getClass().getResourceAsStream(JSON_UPSERT_PRODUCT_REQUEST_20);
		final ProductUpsertCamelRequest productUpsertCamelRequest_20 = objectMapper.readValue(expectedUpsertProductRequest_20, ProductUpsertCamelRequest.class);

		final InputStream expectedUpsertProductRequest_30 = this.getClass().getResourceAsStream(JSON_UPSERT_PRODUCT_REQUEST_30);
		final ProductUpsertCamelRequest productUpsertCamelRequest_30 = objectMapper.readValue(expectedUpsertProductRequest_30, ProductUpsertCamelRequest.class);

		final MockEndpoint productSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_PRODUCT);
		productSyncMockEndpoint.expectedBodiesReceived(productUpsertCamelRequest_20, productUpsertCamelRequest_30);

		final InputStream materialSampleInputStream = this.getClass().getResourceAsStream(MATERIAL_SAMPLE_RESOURCE_PATH);

		//and
		template.sendBodyAndHeader("direct:" + PRODUCT_SYNC_DIRECT_ROUTE_ENDPOINT, materialSampleInputStream, Exchange.FILE_NAME_ONLY, MATERIAL_SAMPLE_DAT_FILE);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockUpsertProductProcessor.called).isEqualTo(2);
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
	}

	@Test
	public void disable_SyncProducts_SFTP() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, START_PRODUCTS_SYNC_SFTP_ROUTE_ID);
		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, STOP_PRODUCTS_SYNC_SFTP_ROUTE_ID);

		final InputStream invokeStartExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_SFTP);
		final JsonExternalSystemRequest startExternalSystemRequest = objectMapper.readValue(invokeStartExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final InputStream invokeStopExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_STOP_EXTERNAL_SYSTEM_REQUEST_SFTP);
		final JsonExternalSystemRequest stopExternalSystemRequest = objectMapper.readValue(invokeStopExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final String routeId = SFTPProductSyncServiceRouteBuilder.getProductsFromSFTPServerRouteId(stopExternalSystemRequest);

		context.start();

		//when
		template.sendBody("direct:" + START_PRODUCTS_SYNC_SFTP_ROUTE_ID, startExternalSystemRequest);

		assertThat(context.getRoute(routeId)).isNotNull();
		assertThat(context.getRouteController().getRouteStatus(routeId).isStarted()).isEqualTo(true);

		//and
		template.sendBody("direct:" + STOP_PRODUCTS_SYNC_SFTP_ROUTE_ID, stopExternalSystemRequest);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(2);

		assertThat(context.getRoute(routeId)).isNull();
	}

	@Test
	public void disable_SyncProducts_LocalFile() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, START_PRODUCTS_SYNC_LOCAL_FILE_ROUTE_ID);
		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, STOP_PRODUCTS_SYNC_LOCAL_FILE_ROUTE_ID);

		final InputStream invokeStartExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest startExternalSystemRequest = objectMapper.readValue(invokeStartExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final InputStream invokeStopExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest stopExternalSystemRequest = objectMapper.readValue(invokeStopExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final String routeId = LocalFileProductSyncServiceRouteBuilder.getProductsFromLocalFileRouteId(stopExternalSystemRequest);

		context.start();

		//when
		template.sendBody("direct:" + START_PRODUCTS_SYNC_LOCAL_FILE_ROUTE_ID, startExternalSystemRequest);

		assertThat(context.getRoute(routeId)).isNotNull();
		assertThat(context.getRouteController().getRouteStatus(routeId).isStarted()).isEqualTo(true);

		//and
		template.sendBody("direct:" + STOP_PRODUCTS_SYNC_LOCAL_FILE_ROUTE_ID, stopExternalSystemRequest);

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

	private static class MockUpsertProductProcessor implements Processor
	{
		@Getter
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
