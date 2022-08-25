/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonESRuntimeParameterUpsertRequest;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandProcessRequest;
import de.metas.common.rest_api.v2.order.JsonOrderPaymentCreateRequest;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_BPARTNER_UPSERT;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_CREATE_PAYMENT;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_OL_CAND_CREATE;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_OL_CAND_PROCESS;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_UPSERT_RUNTIME_PARAMETERS;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.BUILD_ORDERS_CONTEXT_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.BUILD_ORDERS_CONTEXT_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.GET_ORDERS_PAGE_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.GET_ORDERS_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.OLCAND_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.PROCESS_ORDERS_PAGE_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.PROCESS_ORDER_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.UPSERT_RUNTIME_PARAMS_ROUTE_ID;
import static org.assertj.core.api.Assertions.*;

public class GetOrdersRouteBuilder_HappyFlow_RenumberLines_Tests extends GetOrdersRouteBuilder_HappyFlow_Tests
{
	private static final String HAPPY_FLOW_RENUMBER_LINES = "happyFlowRenumberLines/";
	private static final String DO_NOT_RENUMBER = HAPPY_FLOW_RENUMBER_LINES + "doNotRenumber/";
	private static final String DO_RENUMBER = HAPPY_FLOW_RENUMBER_LINES + "doRenumber/";

	private static final String JSON_ORDER_LINES_DO_NOT_RENUMBER = DO_NOT_RENUMBER + "40_JsonOrderLines.json";
	private static final String JSON_OL_CAND_CREATE_REQUEST_DO_NOT_RENUMBER = DO_NOT_RENUMBER + "60_JsonOLCandCreateBulkRequest.json";

	private static final String JSON_ORDER_LINES_DO_RENUMBER = DO_RENUMBER + "40_JsonOrderLines.json";
	private static final String JSON_OL_CAND_CREATE_REQUEST_DO_RENUMBER = DO_RENUMBER + "60_JsonOLCandCreateBulkRequest.json";

	@Override
	@Test
	void happyFlow() throws Exception
	{
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockUpsertBPartnerProcessor createdBPartnerProcessor = new GetOrdersRouteBuilder_HappyFlow_Tests.MockUpsertBPartnerProcessor(JSON_UPSERT_BPARTNER_RESPONSE);
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCreatedOLCandProcessor successfullyCreatedOLCandProcessor = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCreatedOLCandProcessor();
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyProcessOLCandProcessor mockSuccessfullyProcessOLCandProcessor = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyProcessOLCandProcessor();
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCreatePaymentProcessor createPaymentProcessor = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCreatePaymentProcessor();
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyUpsertRuntimeParamsProcessor runtimeParamsProcessor = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyUpsertRuntimeParamsProcessor();
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCalledGetOrderPage successfullyCalledGetOrderPage = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCalledGetOrderPage();

		prepareRouteForTesting(createdBPartnerProcessor,
							   successfullyCreatedOLCandProcessor,
							   mockSuccessfullyProcessOLCandProcessor,
							   runtimeParamsProcessor,
							   createPaymentProcessor,
							   successfullyCalledGetOrderPage,
							   GetOrdersRouteBuilder_HappyFlow_Tests.createJsonExternalSystemRequestBuilder().build(),
							   JSON_ORDER_LINES_DO_NOT_RENUMBER);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		// validate BPUpsertCamelRequest
		final InputStream expectedUpsertBPartnerRequestIS = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST);

		final MockEndpoint bPartnerMockEndpoint = getMockEndpoint(MOCK_BPARTNER_UPSERT);
		bPartnerMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedUpsertBPartnerRequestIS, BPUpsertCamelRequest.class));

		//validate JsonOLCandCreateBulkRequest
		final InputStream olCandCreateRequestIS = this.getClass().getResourceAsStream(JSON_OL_CAND_CREATE_REQUEST_DO_NOT_RENUMBER);

		final MockEndpoint olCandMockEndpoint = getMockEndpoint(MOCK_OL_CAND_CREATE);
		olCandMockEndpoint.expectedBodiesReceived(objectMapper.readValue(olCandCreateRequestIS, JsonOLCandCreateBulkRequest.class));

		//validate JsonOLCandProcessRequest
		final InputStream jsonOLCandProcessRequestIS = this.getClass().getResourceAsStream(JSON_OL_CAND_PROCESS_REQUEST);

		final MockEndpoint olCandProcessEndpoint = getMockEndpoint(MOCK_OL_CAND_PROCESS);
		olCandProcessEndpoint.expectedBodiesReceived(objectMapper.readValue(jsonOLCandProcessRequestIS, JsonOLCandProcessRequest.class));

		//validate create payment request
		final InputStream jsonCreatePaymentRequest = this.getClass().getResourceAsStream(JSON_ORDER_PAYMENT_CREATE_REQUEST);

		final MockEndpoint createPaymentEndpoint = getMockEndpoint(MOCK_CREATE_PAYMENT);
		createPaymentEndpoint.expectedBodiesReceived(objectMapper.readValue(jsonCreatePaymentRequest, JsonOrderPaymentCreateRequest.class));

		//validate upsert runtime parameters request
		final InputStream jsonUpsertRuntimeParamsRequest = this.getClass().getResourceAsStream(JSON_UPSERT_RUNTIME_PARAMS_REQUEST);

		final MockEndpoint upsertRuntimeParametersEndpoint = getMockEndpoint(MOCK_UPSERT_RUNTIME_PARAMETERS);
		upsertRuntimeParametersEndpoint.expectedBodiesReceived(objectMapper.readValue(jsonUpsertRuntimeParamsRequest, JsonESRuntimeParameterUpsertRequest.class));

		template.sendBody("direct:" + GET_ORDERS_ROUTE_ID, "Body not relevant!");

		assertThat(createdBPartnerProcessor.called).isEqualTo(1);
		assertThat(successfullyCreatedOLCandProcessor.called).isEqualTo(1);
		assertThat(mockSuccessfullyProcessOLCandProcessor.called).isEqualTo(1);
		assertThat(runtimeParamsProcessor.called).isEqualTo(1);
		assertThat(createPaymentProcessor.called).isEqualTo(1);
		assertThat(successfullyCalledGetOrderPage.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	@Test
	public void happyFlow_doRenumber() throws Exception
	{
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockUpsertBPartnerProcessor createdBPartnerProcessor = new GetOrdersRouteBuilder_HappyFlow_Tests.MockUpsertBPartnerProcessor(JSON_UPSERT_BPARTNER_RESPONSE);
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCreatedOLCandProcessor successfullyCreatedOLCandProcessor = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCreatedOLCandProcessor();
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyProcessOLCandProcessor successfullyProcessOLCandProcessor = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyProcessOLCandProcessor();
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCreatePaymentProcessor createPaymentProcessor = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCreatePaymentProcessor();
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyUpsertRuntimeParamsProcessor runtimeParamsProcessor = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyUpsertRuntimeParamsProcessor();
		final GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCalledGetOrderPage mockSuccessfullyCalledGetOrderPage = new GetOrdersRouteBuilder_HappyFlow_Tests.MockSuccessfullyCalledGetOrderPage();

		prepareRouteForTesting(createdBPartnerProcessor,
							   successfullyCreatedOLCandProcessor,
							   successfullyProcessOLCandProcessor,
							   runtimeParamsProcessor,
							   createPaymentProcessor,
							   mockSuccessfullyCalledGetOrderPage,
							   GetOrdersRouteBuilder_HappyFlow_Tests.createJsonExternalSystemRequestBuilder().build(),
							   JSON_ORDER_LINES_DO_RENUMBER);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		// validate BPUpsertCamelRequest
		final InputStream expectedUpsertBPartnerRequestIS = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST);

		final MockEndpoint bPartnerMockEndpoint = getMockEndpoint(MOCK_BPARTNER_UPSERT);
		bPartnerMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedUpsertBPartnerRequestIS, BPUpsertCamelRequest.class));

		//validate JsonOLCandCreateBulkRequest
		final InputStream olCandCreateRequestIS = this.getClass().getResourceAsStream(JSON_OL_CAND_CREATE_REQUEST_DO_RENUMBER);

		final MockEndpoint olCandMockEndpoint = getMockEndpoint(MOCK_OL_CAND_CREATE);
		olCandMockEndpoint.expectedBodiesReceived(objectMapper.readValue(olCandCreateRequestIS, JsonOLCandCreateBulkRequest.class));

		//validate JsonOLCandProcessRequest
		final InputStream jsonOLCandProcessRequestIS = this.getClass().getResourceAsStream(JSON_OL_CAND_PROCESS_REQUEST);

		final MockEndpoint olCandProcessEndpoint = getMockEndpoint(MOCK_OL_CAND_PROCESS);
		olCandProcessEndpoint.expectedBodiesReceived(objectMapper.readValue(jsonOLCandProcessRequestIS, JsonOLCandProcessRequest.class));

		//validate create payment request
		final InputStream jsonCreatePaymentRequest = this.getClass().getResourceAsStream(JSON_ORDER_PAYMENT_CREATE_REQUEST);

		final MockEndpoint createPaymentEndpoint = getMockEndpoint(MOCK_CREATE_PAYMENT);
		createPaymentEndpoint.expectedBodiesReceived(objectMapper.readValue(jsonCreatePaymentRequest, JsonOrderPaymentCreateRequest.class));

		//validate upsert runtime parameters request
		final InputStream jsonUpsertRuntimeParamsRequest = this.getClass().getResourceAsStream(JSON_UPSERT_RUNTIME_PARAMS_REQUEST);

		final MockEndpoint upsertRuntimeParametersEndpoint = getMockEndpoint(MOCK_UPSERT_RUNTIME_PARAMETERS);
		upsertRuntimeParametersEndpoint.expectedBodiesReceived(objectMapper.readValue(jsonUpsertRuntimeParamsRequest, JsonESRuntimeParameterUpsertRequest.class));

		template.sendBody("direct:" + GET_ORDERS_ROUTE_ID, "Body not relevant!");

		assertThat(createdBPartnerProcessor.called).isEqualTo(1);
		assertThat(successfullyCreatedOLCandProcessor.called).isEqualTo(1);
		assertThat(successfullyProcessOLCandProcessor.called).isEqualTo(1);
		assertThat(runtimeParamsProcessor.called).isEqualTo(1);
		assertThat(createPaymentProcessor.called).isEqualTo(1);
		assertThat(mockSuccessfullyCalledGetOrderPage.called).isEqualTo(1);
		assertMockEndpointsSatisfied();

	}

	private void prepareRouteForTesting(
			final MockUpsertBPartnerProcessor upsertBPartnerProcessor,
			final MockSuccessfullyCreatedOLCandProcessor olCandProcessor,
			final MockSuccessfullyProcessOLCandProcessor processOLCandProcessor,
			final MockSuccessfullyUpsertRuntimeParamsProcessor runtimeParamsProcessor,
			final MockSuccessfullyCreatePaymentProcessor createPaymentProcessor,
			final MockSuccessfullyCalledGetOrderPage successfullyCalledGetOrderPage,
			final JsonExternalSystemRequest request,
			final String jsonOLPath) throws Exception
	{
		AdviceWith.adviceWith(context, GET_ORDERS_ROUTE_ID,
							  advice -> advice.weaveById(BUILD_ORDERS_CONTEXT_PROCESSOR_ID)
									  .replace()
									  .process(new MockBuildOrdersContextProcessor(request, jsonOLPath, 1, JSON_ORDERS_RESOURCE_PATH, ExternalSystemConstants.DEFAULT_SW6_ORDER_PAGE_SIZE)));

		AdviceWith.adviceWith(context, PROCESS_ORDERS_PAGE_ROUTE_ID,
							  advice -> advice.weaveById(GET_ORDERS_PAGE_PROCESSOR_ID)
									  .after()
									  .process(successfullyCalledGetOrderPage));

		AdviceWith.adviceWith(context, PROCESS_ORDER_ROUTE_ID,
							  advice -> {
								  advice.weaveById(CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID)
										  .after()
										  .to(MOCK_BPARTNER_UPSERT);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .process(upsertBPartnerProcessor);

								  advice.weaveById(OLCAND_REQ_PROCESSOR_ID)
										  .after()
										  .to(MOCK_OL_CAND_CREATE);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(olCandProcessor);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_CREATE_ORDER_PAYMENT_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_CREATE_PAYMENT)
										  .process(createPaymentProcessor);
							  });

		AdviceWith.adviceWith(context, PROCESS_ORDER_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_PROCESS_OL_CANDIDATES_ROUTE_ID)
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_OL_CAND_PROCESS)
									  .process(processOLCandProcessor));

		AdviceWith.adviceWith(context, UPSERT_RUNTIME_PARAMS_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID)
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_UPSERT_RUNTIME_PARAMETERS)
									  .process(runtimeParamsProcessor));
	}
}
