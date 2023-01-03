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

package de.metas.camel.externalsystems.shopware6.customer;

import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_BPARTNER_UPSERT;
import static de.metas.camel.externalsystems.shopware6.customer.GetCustomersRouteBuilder.GET_CUSTOMERS_ROUTE_ID;
import static org.assertj.core.api.Assertions.*;

public class GetCustomersRouteBuilderTest_withCustomFields extends GetCustomersRouteBuilderTest
{
	private static final String WITH_CUSTOM_FIELDS_PATH = "withCustomFields";

	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = WITH_CUSTOM_FIELDS_PATH + "/10_JsonExternalSystemRequest.json";
	private static final String JSON_CUSTOMER_RESOURCE_CUSTOM = WITH_CUSTOM_FIELDS_PATH + "/20_JsonCustomer.json";
	private static final String JSON_UPSERT_CUSTOMER_REQUEST_CUSTOM = WITH_CUSTOM_FIELDS_PATH + "/50_BPUpsertCamelRequest.json";

	@Test
	void happyFlow_withCustomFields() throws Exception
	{
		final String customersMockResponse = loadAsString(JSON_CUSTOMER_RESOURCE_CUSTOM);

		final MockPrepareContextProcessor mockPrepareContextProcessor = new MockPrepareContextProcessor(customersMockResponse);
		final MockUpsertBPartnerProcessor mockUpsertBPartnerProcessor = new MockUpsertBPartnerProcessor();
		final MockSuccessfullyUpsertRuntimeParamsProcessor mockRuntimeParamsProcessor = new MockSuccessfullyUpsertRuntimeParamsProcessor();

		//given
		prepareRouteForTesting(mockPrepareContextProcessor, mockUpsertBPartnerProcessor, mockRuntimeParamsProcessor);

		context.start();

		final InputStream expectedUpsertCustomerRequestIS = this.getClass().getResourceAsStream(JSON_UPSERT_CUSTOMER_REQUEST_CUSTOM);
		final MockEndpoint bpartnerMockEndpoint = getMockEndpoint(MOCK_BPARTNER_UPSERT);
		bpartnerMockEndpoint.expectedBodiesReceived(mapper.readValue(expectedUpsertCustomerRequestIS, BPUpsertCamelRequest.class));

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = mapper
				.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//when
		template.sendBody("direct:" + GET_CUSTOMERS_ROUTE_ID, invokeExternalSystemRequest);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockPrepareContextProcessor.called).isEqualTo(1);
		assertThat(mockUpsertBPartnerProcessor.called).isEqualTo(1);
		// dev-note: when `JsonExternalSystemRequest` does not contain `UpdatedAfterOverride` param, `UPSERT_RUNTIME_PARAMS_ROUTE_ID` route is called
		assertThat(mockRuntimeParamsProcessor.called).isEqualTo(1);
	}
}
