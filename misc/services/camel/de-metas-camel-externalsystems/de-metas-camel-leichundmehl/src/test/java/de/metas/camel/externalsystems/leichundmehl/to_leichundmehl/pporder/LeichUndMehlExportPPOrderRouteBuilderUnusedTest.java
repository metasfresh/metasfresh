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
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceQuery;
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
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_PP_ORDER_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_SEARCH_PRODUCT_PRICES_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.LeichUndMehlExportPPOrderRouteBuilderUnused.EXPORT_PPORDER_UNUSED_ROUTE_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Deprecated
public class LeichUndMehlExportPPOrderRouteBuilderUnusedTest extends CamelTestSupport
{
	private static final String MOCK_RETRIEVE_PP_ORDER_ENDPOINT = "mock:RetrievePPOrderEndpoint";
	private static final String MOCK_RETRIEVE_PRODUCT_INFO_ENDPOINT = "mock:RetrieveProductInfoEndpoint";
	private static final String MOCK_RETRIEVE_BPARTNER_ENDPOINT = "mock:RetrieveBPartnerEndpoint";
	private static final String MOCK_QUERY_PRODUCT_PRICES_ENDPOINT = "mock:QueryProductPricesEndpoint";

	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = "0_JsonExternalSystemRequest.json";
	private static final String JSON_MANUFACTURING_ORDER_RESPONSE = "10_JsonResponseManufacturingOrder.json";
	private static final String JSON_RETRIEVE_PRODUCT_CAMEL_REQUEST = "20_RetrieveProductCamelRequest.json";
	private static final String JSON_PRODUCT_RESPONSE = "20_JsonProduct.json";
	private static final String JSON_RETRIEVE_BPARTNER_CAMEL_REQUEST = "30_BPRetrieveCamelRequest.json";
	private static final String JSON_COMPOSITE_RESPONSE = "30_JsonResponseComposite.json";
	private static final String JSON_QUERY_PRODUCT_PRICES_REQUEST = "40_JsonRequestProductPriceQuery.json";
	private static final String JSON_QUERY_PRODUCT_PRICES_RESPONSE = "40_JsonResponseProductPriceQuery.json";

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
		return new LeichUndMehlExportPPOrderRouteBuilderUnused();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(LeichUndMehlExportPPOrderRouteBuilderUnusedTest.class.getClassLoader().getResourceAsStream("application.properties"));
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

		prepareRouteForTesting(mockRetrievePPOrderProcessor, mockRetrieveProductInfoProcessor, mockRetrieveBPartnerProcessor, mockSearchProductPricesProcessor);

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

		// validate the JsonRequestProductPriceQuery
		final InputStream expectedQueryProductPricesRequestIS = this.getClass().getResourceAsStream(JSON_QUERY_PRODUCT_PRICES_REQUEST);
		final MockEndpoint queryProductPricesMockEndpoint = getMockEndpoint(MOCK_QUERY_PRODUCT_PRICES_ENDPOINT);
		queryProductPricesMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedQueryProductPricesRequestIS, JsonRequestProductPriceQuery.class));

		//input request
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//when
		template.sendBody("direct:" + EXPORT_PPORDER_UNUSED_ROUTE_ID, invokeExternalSystemRequest);

		//then
		assertThat(mockRetrievePPOrderProcessor.called).isEqualTo(1);
		assertThat(mockRetrieveProductInfoProcessor.called).isEqualTo(1);
		assertThat(mockRetrieveBPartnerProcessor.called).isEqualTo(1);
		assertThat(mockSearchProductPricesProcessor.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(
			@NonNull final MockRetrievePPOrderProcessor mockRetrievePPOrderProcessor,
			@NonNull final MockRetrieveProductInfoProcessor mockRetrieveProductInfoProcessor,
			@NonNull final MockRetrieveBPartnerProcessor mockRetrieveBPartnerProcessor,
			@NonNull final MockSearchProductPricesProcessor mockSearchProductPricesProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, EXPORT_PPORDER_UNUSED_ROUTE_ID,
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
										  .to(MOCK_QUERY_PRODUCT_PRICES_ENDPOINT)
										  .process(mockSearchProductPricesProcessor);
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
			final InputStream expectedJsonResponsePriceListIS = this.getClass().getResourceAsStream(JSON_QUERY_PRODUCT_PRICES_RESPONSE);
			exchange.getIn().setBody(expectedJsonResponsePriceListIS);
		}
	}
}
