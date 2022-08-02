/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.shopware6;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Properties;

import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.BUILD_ORDERS_CONTEXT_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.GET_ORDERS_PAGE_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.GET_ORDERS_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.PROCESS_OLCAND_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.PROCESS_ORDERS_PAGE_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.PROCESS_ORDER_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.UPSERT_RUNTIME_PARAMS_ROUTE_ID;
import static org.assertj.core.api.Assertions.*;

public class GetOrdersRouteBuilderTestScenarios extends CamelTestSupport
{
	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(GetOrdersRouteBuilderTestScenarios.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new GetOrdersRouteBuilder(Mockito.mock(ProcessLogger.class), Mockito.mock(ProducerTemplate.class));
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	public void failOnBPartnerUpsert() throws Exception
	{
		final FailingMockUpsertBPartnerProcessor failingMockUpsertBPartnerProcessor = new FailingMockUpsertBPartnerProcessor();
		final MockErrorRouteEndpointProcessor mockErrorRouteEndpointProcessor = new MockErrorRouteEndpointProcessor();
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyProcessOLCandProcessor successfullyClearOrdersProcessor = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyProcessOLCandProcessor();
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyUpsertRuntimeParamsProcessor runtimeParamsProcessor = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyUpsertRuntimeParamsProcessor();
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCalledGetOrderPage successfullyCalledGetOrderPage = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCalledGetOrderPage();

		final JsonExternalSystemRequest externalSystemRequest = GetOrdersRouteBuilder_HappyFlow_Tests.createJsonExternalSystemRequestBuilder().build();

		prepareRouteForTesting(failingMockUpsertBPartnerProcessor,
							   mockErrorRouteEndpointProcessor,
							   successfullyClearOrdersProcessor,
							   runtimeParamsProcessor,
							   successfullyCalledGetOrderPage,
							   externalSystemRequest);

		context.start();

		template.sendBody("direct:" + GET_ORDERS_ROUTE_ID, "Not relevant!");

		assertThat(mockErrorRouteEndpointProcessor.called).isEqualTo(1);
		assertThat(successfullyClearOrdersProcessor.getCalled()).isEqualTo(1);
		assertThat(runtimeParamsProcessor.getCalled()).isEqualTo(1);
		assertThat(successfullyCalledGetOrderPage.called).isEqualTo(1);
	}

	private void prepareRouteForTesting(
			final FailingMockUpsertBPartnerProcessor failingMockUpsertBPartnerProcessor,
			final MockErrorRouteEndpointProcessor mockErrorRouteEndpointProcessor,
			final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyProcessOLCandProcessor olCandClearProcessor,
			final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyUpsertRuntimeParamsProcessor runtimeParamsProcessor,
			final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCalledGetOrderPage successfullyCalledGetOrderPage,
			final JsonExternalSystemRequest externalSystemRequest) throws Exception
	{
		AdviceWith.adviceWith(context, GET_ORDERS_ROUTE_ID,
							  advice -> advice.weaveById(BUILD_ORDERS_CONTEXT_PROCESSOR_ID)
									  .replace()
									  .process(new GetOrdersRouteBuilder_HappyFlow_Tests.MockBuildOrdersContextProcessor(externalSystemRequest, 1, GetOrdersRouteBuilder_HappyFlow_Tests.JSON_ORDERS_RESOURCE_PATH, ExternalSystemConstants.DEFAULT_SW6_ORDER_PAGE_SIZE)));

		AdviceWith.adviceWith(context, PROCESS_ORDERS_PAGE_ROUTE_ID,
							  advice -> advice.weaveById(GET_ORDERS_PAGE_PROCESSOR_ID)
									  .after()
									  .process(successfullyCalledGetOrderPage));

		AdviceWith.adviceWith(context, PROCESS_ORDER_ROUTE_ID,
							  advice -> {
								  advice.weaveById(CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID)
										  .replace()
										  .process(failingMockUpsertBPartnerProcessor);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(mockErrorRouteEndpointProcessor);
							  });

		AdviceWith.adviceWith(context, PROCESS_OLCAND_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_PROCESS_OL_CANDIDATES_ROUTE_ID)
									  .skipSendToOriginalEndpoint()
									  .process(olCandClearProcessor));

		AdviceWith.adviceWith(context, UPSERT_RUNTIME_PARAMS_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID)
									  .skipSendToOriginalEndpoint()
									  .process(runtimeParamsProcessor));
	}

	private static class FailingMockUpsertBPartnerProcessor implements Processor
	{
		@Override
		public void process(final Exchange exchange)
		{
			throw new RuntimeException("Any exception!");
		}
	}

	private static class MockErrorRouteEndpointProcessor implements Processor
	{
		int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
