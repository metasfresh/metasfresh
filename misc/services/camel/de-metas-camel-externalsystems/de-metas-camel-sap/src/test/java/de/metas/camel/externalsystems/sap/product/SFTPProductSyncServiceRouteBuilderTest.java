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

import static de.metas.camel.externalsystems.sap.product.GetProductsSFTPRouteBuilder.UPSERT_PRODUCT_ENDPOINT_ID;
import static de.metas.camel.externalsystems.sap.product.SFTPProductSyncServiceRouteBuilder.START_PRODUCTS_SYNC_ROUTE_ID;
import static org.assertj.core.api.Assertions.*;

public class SFTPProductSyncServiceRouteBuilderTest extends CamelTestSupport
{
	private static final String PRODUCT_SYNC_DIRECT_ROUTE_ENDPOINT = "SAP-mockProductSyncRoute";

	private static final String MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT = "mock:externalSystemStatusEndpoint";
	private static final String MOCK_UPSERT_PRODUCT = "mock:UpsertProduct";

	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = "0_JsonExternalSystemRequest.json";
	private static final String MATERIAL_SAMPLE_DAT_FILE = "10_MaterialSample.dat";
	private static final String JSON_UPSERT_PRODUCT_REQUEST = "20_CamelUpsertProductRequest.json";

	private static final String MATERIAL_SAMPLE_RESOURCE_PATH = "/de/metas/camel/externalsystems/sap/product/" + MATERIAL_SAMPLE_DAT_FILE;

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new SFTPProductSyncServiceRouteBuilder(Mockito.mock(ProcessLogger.class));
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(SFTPProductSyncServiceRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void happyFlow_SyncProducts() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest externalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockUpsertProductProcessor mockUpsertProductProcessor = new MockUpsertProductProcessor();

		prepareRouteForTesting(mockExternalSystemStatusProcessor);

		context.start();

		//when
		template.sendBody("direct:" + START_PRODUCTS_SYNC_ROUTE_ID, externalSystemRequest);

		prepareSyncRouteForTesting(mockUpsertProductProcessor, getSFTPProductsSyncRouteId(externalSystemRequest));

		final InputStream expectedUpsertProductRequest = this.getClass().getResourceAsStream(JSON_UPSERT_PRODUCT_REQUEST);
		final MockEndpoint productSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_PRODUCT);
		productSyncMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedUpsertProductRequest, ProductUpsertCamelRequest.class));

		final InputStream materialSampleInputStream = this.getClass().getResourceAsStream(MATERIAL_SAMPLE_RESOURCE_PATH);

		//and
		template.sendBodyAndHeader("direct:" + PRODUCT_SYNC_DIRECT_ROUTE_ENDPOINT, materialSampleInputStream, Exchange.FILE_NAME_ONLY, MATERIAL_SAMPLE_DAT_FILE);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockUpsertProductProcessor.called).isEqualTo(1);
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
	}

	private String getSFTPProductsSyncRouteId(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		return "GetProductsSFTPRouteBuilder#" + externalSystemRequest.getExternalSystemChildConfigValue();
	}

	private void prepareRouteForTesting(
			@NonNull final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, START_PRODUCTS_SYNC_ROUTE_ID,
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
