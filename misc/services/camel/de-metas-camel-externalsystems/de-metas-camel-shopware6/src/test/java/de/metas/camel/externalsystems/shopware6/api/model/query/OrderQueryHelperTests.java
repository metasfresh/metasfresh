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

package de.metas.camel.externalsystems.shopware6.api.model.query;

import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.shopware6.api.model.Shopware6QueryRequest;
import de.metas.camel.externalsystems.shopware6.order.query.OrderQueryHelper;
import de.metas.camel.externalsystems.shopware6.order.query.PageAndLimit;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.*;

public class OrderQueryHelperTests
{
	private static final String EXTERNAL_SYSTEM_REQUEST_ORDER_NO = "JsonExtenalSystemRequest_OrderNo.json";
	private static final String EXTERNAL_SYSTEM_REQUEST_ORDER_ID = "JsonExtenalSystemRequest_OrderId.json";
	private static final String EXTERNAL_SYSTEM_REQUEST_UPDATED = "JsonExtenalSystemRequest_Updated.json";
	private static final String EXTERNAL_SYSTEM_REQUEST_UPDATED_OVERRIDE = "JsonExtenalSystemRequest_UpdatedOverride.json";

	//search by order number
	@Test
	public void givenOrderNo_whenBuildShopware6QueryRequest_thenReturnFilterByOrderNo() throws IOException
	{
		final InputStream externalSystemRequest = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST_ORDER_NO);
		final JsonExternalSystemRequest jsonExternalSystemRequest = JsonObjectMapperHolder.newJsonObjectMapper().readValue(externalSystemRequest, JsonExternalSystemRequest.class);

		final Shopware6QueryRequest shopware6QueryRequest = OrderQueryHelper.buildShopware6QueryRequest(jsonExternalSystemRequest, PageAndLimit.of(1, 1));

		final String shopware6QueryRequestAsJSON = JsonObjectMapperHolder.newJsonObjectMapper().writeValueAsString(shopware6QueryRequest);

		assertThat(shopware6QueryRequestAsJSON).isEqualTo("{\"filter\":[{\"type\":\"multi\",\"operator\":\"and\",\"queries\":[{\"field\":\"orderNumber\",\"type\":\"equals\",\"value\":\"testOrderNo\"}]}],\"limit\":1,\"page\":1}");
	}

	//search by order id
	@Test
	public void givenOrderNo_whenBuildShopware6QueryRequest_thenReturnFilterByOrderId() throws IOException
	{
		final InputStream externalSystemRequest = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST_ORDER_ID);
		final JsonExternalSystemRequest jsonExternalSystemRequest = JsonObjectMapperHolder.newJsonObjectMapper().readValue(externalSystemRequest, JsonExternalSystemRequest.class);

		final Shopware6QueryRequest shopware6QueryRequest = OrderQueryHelper.buildShopware6QueryRequest(jsonExternalSystemRequest, PageAndLimit.of(1, 1));

		final String shopware6QueryRequestAsJSON = JsonObjectMapperHolder.newJsonObjectMapper().writeValueAsString(shopware6QueryRequest);

		assertThat(shopware6QueryRequestAsJSON).isEqualTo("{\"filter\":[{\"type\":\"multi\",\"operator\":\"and\",\"queries\":[{\"field\":\"id\",\"type\":\"equals\",\"value\":\"testOrderId\"}]}],\"limit\":1,\"page\":1}");
	}

	//search by updated
	@Test
	public void givenOrderNo_whenBuildShopware6QueryRequest_thenReturnFilterByUpdated() throws IOException
	{
		final InputStream externalSystemRequest = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST_UPDATED);
		final JsonExternalSystemRequest jsonExternalSystemRequest = JsonObjectMapperHolder.newJsonObjectMapper().readValue(externalSystemRequest, JsonExternalSystemRequest.class);

		final Shopware6QueryRequest shopware6QueryRequest = OrderQueryHelper.buildShopware6QueryRequest(jsonExternalSystemRequest, PageAndLimit.of(1, 1));

		final String shopware6QueryRequestAsJSON = JsonObjectMapperHolder.newJsonObjectMapper().writeValueAsString(shopware6QueryRequest);

		assertThat(shopware6QueryRequestAsJSON).isEqualTo("{\"filter\":[{\"type\":\"multi\",\"operator\":\"or\",\"queries\":[{\"field\":\"updatedAt\",\"type\":\"range\",\"parameters\":{\"gte\":\"1999-08-09T13:35:17Z\"}},{\"field\":\"createdAt\",\"type\":\"range\",\"parameters\":{\"gte\":\"1999-08-09T13:35:17Z\"}}]}],\"limit\":1,\"page\":1}");
	}

	//search by updated override
	@Test
	public void givenOrderNo_whenBuildShopware6QueryRequest_thenReturnFilterByUpdatedOverride() throws IOException
	{
		final InputStream externalSystemRequest = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST_UPDATED_OVERRIDE);
		final JsonExternalSystemRequest jsonExternalSystemRequest = JsonObjectMapperHolder.newJsonObjectMapper().readValue(externalSystemRequest, JsonExternalSystemRequest.class);

		final Shopware6QueryRequest shopware6QueryRequest = OrderQueryHelper.buildShopware6QueryRequest(jsonExternalSystemRequest, PageAndLimit.of(1, 1));

		final String shopware6QueryRequestAsJSON = JsonObjectMapperHolder.newJsonObjectMapper().writeValueAsString(shopware6QueryRequest);

		assertThat(shopware6QueryRequestAsJSON).isEqualTo("{\"filter\":[{\"type\":\"multi\",\"operator\":\"or\",\"queries\":[{\"field\":\"updatedAt\",\"type\":\"range\",\"parameters\":{\"gte\":\"2000-08-09T13:35:17Z\"}},{\"field\":\"createdAt\",\"type\":\"range\",\"parameters\":{\"gte\":\"2000-08-09T13:35:17Z\"}}]}],\"limit\":1,\"page\":1}");
	}
}
