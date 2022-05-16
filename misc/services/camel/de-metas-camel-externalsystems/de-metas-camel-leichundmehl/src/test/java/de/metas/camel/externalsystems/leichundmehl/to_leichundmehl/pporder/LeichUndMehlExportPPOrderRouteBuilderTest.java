/*
 * #%L
 * de-metas-camel-leichundmehl
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import de.metas.camel.externalsystems.common.v2.RetrieveProductCamelRequest;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonPPOrder;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.ftp.DispatchMessageRequest;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.ftp.FTPCredentials;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceSearch;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_PP_ORDER_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_SEARCH_PRODUCT_PRICES_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.ftp.SendToFTPRouteBuilder.SEND_TO_FTP_ROUTE_ID;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.LeichUndMehlExportPPOrderRouteBuilder.EXPORT_PPORDER_ROUTE_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LeichUndMehlExportPPOrderRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_RETRIEVE_PP_ORDER_ENDPOINT = "mock:RetrievePPOrderEndpoint";
	private static final String MOCK_RETRIEVE_PRODUCT_INFO_ENDPOINT = "mock:RetrieveProductInfoEndpoint";
	private static final String MOCK_RETRIEVE_BPARTNER_ENDPOINT = "mock:RetrieveBPartnerEndpoint";
	private static final String MOCK_SEARCH_PRODUCT_PRICES_ENDPOINT = "mock:SearchProductPricesEndpoint";
	private static final String MOCK_FTP_ENDPOINT = "mock:FTPEndpoint";

	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = "0_JsonExternalSystemRequest.json";
	private static final String JSON_MANUFACTURING_ORDER_RESPONSE = "10_JsonResponseManufacturingOrder.json";
	private static final String JSON_RETRIEVE_PRODUCT_CAMEL_REQUEST = "20_RetrieveProductCamelRequest.json";
	private static final String JSON_PRODUCT_RESPONSE = "20_JsonProduct.json";
	private static final String JSON_RETRIEVE_BPARTNER_CAMEL_REQUEST = "30_BPRetrieveCamelRequest.json";
	private static final String JSON_COMPOSITE_RESPONSE = "30_JsonResponseComposite.json";
	private static final String JSON_SEARCH_PRODUCT_PRICES_REQUEST = "40_JsonRequestProductPriceSearch.json";
	private static final String JSON_PRICE_LIST_RESPONSE = "40_JsonResponsePriceList.json";
	private static final String JSON_DISPATCH_MESSAGE_REQUEST = "50_DispatchMessageRequest_Payload.json";

	private static final Integer ppOrderMetasfreshId = 11111;

	private static final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new LeichUndMehlExportPPOrderRouteBuilder();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(LeichUndMehlExportPPOrderRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void happyFlow() throws Exception
	{
		final MockRetrievePPOrderProcessor mockRetrievePPOrderProcessor = new MockRetrievePPOrderProcessor();
		final MockRetrieveProductInfoProcessor mockRetrieveProductInfoProcessor = new MockRetrieveProductInfoProcessor();
		final MockRetrieveBPartnerProcessor mockRetrieveBPartnerProcessor = new MockRetrieveBPartnerProcessor();
		final MockSearchProductPricesProcessor mockSearchProductPricesProcessor = new MockSearchProductPricesProcessor();
		final MockFTPProcessor mockFTPProcessor = new MockFTPProcessor();

		prepareRouteForTesting(mockRetrievePPOrderProcessor, mockRetrieveProductInfoProcessor, mockRetrieveBPartnerProcessor, mockSearchProductPricesProcessor, mockFTPProcessor);

		context.start();

		final MockEndpoint retrievePPOrderMockEndpoint = getMockEndpoint(MOCK_RETRIEVE_PP_ORDER_ENDPOINT);
		retrievePPOrderMockEndpoint.expectedBodiesReceived(ppOrderMetasfreshId);

		final InputStream expectedRetrieveProductCamelRequestIS = this.getClass().getResourceAsStream(JSON_RETRIEVE_PRODUCT_CAMEL_REQUEST);
		final MockEndpoint retrieveProductInfoMockEndpoint = getMockEndpoint(MOCK_RETRIEVE_PRODUCT_INFO_ENDPOINT);
		retrieveProductInfoMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedRetrieveProductCamelRequestIS, RetrieveProductCamelRequest.class));

		// validate the BPRetrieveCamelRequest
		final InputStream expectedBPRetrieveCamelRequestIS = this.getClass().getResourceAsStream(JSON_RETRIEVE_BPARTNER_CAMEL_REQUEST);
		final MockEndpoint retrieveBPartnerMockEndpoint = getMockEndpoint(MOCK_RETRIEVE_BPARTNER_ENDPOINT);
		retrieveBPartnerMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedBPRetrieveCamelRequestIS, BPRetrieveCamelRequest.class));

		// validate the JsonRequestProductPriceSearch
		final InputStream expectedSearchProductPricesRequestIS = this.getClass().getResourceAsStream(JSON_SEARCH_PRODUCT_PRICES_REQUEST);
		final MockEndpoint searchProductPricesMockEndpoint = getMockEndpoint(MOCK_SEARCH_PRODUCT_PRICES_ENDPOINT);
		searchProductPricesMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedSearchProductPricesRequestIS, JsonRequestProductPriceSearch.class));

		//input request
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//when
		template.sendBody("direct:" + EXPORT_PPORDER_ROUTE_ID, invokeExternalSystemRequest);

		//then
		assertThat(mockRetrievePPOrderProcessor.called).isEqualTo(1);
		assertThat(mockRetrieveProductInfoProcessor.called).isEqualTo(1);
		assertThat(mockRetrieveBPartnerProcessor.called).isEqualTo(1);
		assertThat(mockSearchProductPricesProcessor.called).isEqualTo(1);
		assertThat(mockFTPProcessor.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(
			@NonNull final MockRetrievePPOrderProcessor mockRetrievePPOrderProcessor,
			@NonNull final MockRetrieveProductInfoProcessor mockRetrieveProductInfoProcessor,
			@NonNull final MockRetrieveBPartnerProcessor mockRetrieveBPartnerProcessor,
			@NonNull final MockSearchProductPricesProcessor mockSearchProductPricesProcessor,
			@NonNull final MockFTPProcessor mockFTPProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, EXPORT_PPORDER_ROUTE_ID,
							  advice -> {
								  advice.interceptSendToEndpoint("direct:" + MF_RETRIEVE_PP_ORDER_V2_CAMEL_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_RETRIEVE_PP_ORDER_ENDPOINT)
										  .process(mockRetrievePPOrderProcessor);

								  advice.interceptSendToEndpoint("direct:" + MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_RETRIEVE_PRODUCT_INFO_ENDPOINT)
										  .process(mockRetrieveProductInfoProcessor);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_RETRIEVE_BPARTNER_ENDPOINT)
										  .process(mockRetrieveBPartnerProcessor);

								  advice.interceptSendToEndpoint("direct:" + MF_SEARCH_PRODUCT_PRICES_V2_CAMEL_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_SEARCH_PRODUCT_PRICES_ENDPOINT)
										  .process(mockSearchProductPricesProcessor);

								  advice.interceptSendToEndpoint("direct:" + SEND_TO_FTP_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_FTP_ENDPOINT)
										  .process(mockFTPProcessor);
							  });
	}

	private static class MockRetrievePPOrderProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream expectedJsonResponseManufacturingOrderIS = this.getClass().getResourceAsStream(JSON_MANUFACTURING_ORDER_RESPONSE);
			exchange.getIn().setBody(expectedJsonResponseManufacturingOrderIS);
		}
	}

	private static class MockRetrieveProductInfoProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream expectedJsonProductIS = this.getClass().getResourceAsStream(JSON_PRODUCT_RESPONSE);
			exchange.getIn().setBody(expectedJsonProductIS);
		}
	}

	private static class MockRetrieveBPartnerProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream expectedJsonResponseCompositeIS = this.getClass().getResourceAsStream(JSON_COMPOSITE_RESPONSE);
			exchange.getIn().setBody(expectedJsonResponseCompositeIS);
		}
	}

	private static class MockSearchProductPricesProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream expectedJsonResponsePriceListIS = this.getClass().getResourceAsStream(JSON_PRICE_LIST_RESPONSE);
			exchange.getIn().setBody(expectedJsonResponsePriceListIS);
		}
	}

	private static class MockFTPProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange) throws IOException
		{
			final DispatchMessageRequest request = exchange.getIn().getBody(DispatchMessageRequest.class);
			final JsonPPOrder jsonPPOrder = objectMapper.readValue(request.getFtpPayload(), JsonPPOrder.class);

			final InputStream expectedDispatchMessageRequestIS = this.getClass().getResourceAsStream(JSON_DISPATCH_MESSAGE_REQUEST);
			final JsonPPOrder expectedJson = objectMapper.readValue(expectedDispatchMessageRequestIS, JsonPPOrder.class);

			assertThat(jsonPPOrder).isEqualTo(expectedJson);

			final InputStream externalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
			final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(externalSystemRequestIS, JsonExternalSystemRequest.class);
			final Map<String, String> params = invokeExternalSystemRequest.getParameters();

			final FTPCredentials ftpCredentials = request.getFtpCredentials();
			assertThat(ftpCredentials.getFtpHost()).isEqualTo(params.get(ExternalSystemConstants.PARAM_FTP_HOST));
			assertThat(ftpCredentials.getFtpPort()).isEqualTo(params.get(ExternalSystemConstants.PARAM_FTP_PORT));
			assertThat(ftpCredentials.getFtpUsername()).isEqualTo(params.get(ExternalSystemConstants.PARAM_FTP_USERNAME));
			assertThat(ftpCredentials.getFtpPassword()).isEqualTo(params.get(ExternalSystemConstants.PARAM_FTP_PASSWORD));
			assertThat(ftpCredentials.getFtpDirectory()).isEqualTo(params.get(ExternalSystemConstants.PARAM_FTP_DIRECTORY));
			assertThat(ftpCredentials.getFtpFilename()).isEqualTo(ExportPPOrderHelper.computeFileName(params.get(ExternalSystemConstants.PARAM_PP_ORDER_ID)));

			called++;
		}
	}
}
