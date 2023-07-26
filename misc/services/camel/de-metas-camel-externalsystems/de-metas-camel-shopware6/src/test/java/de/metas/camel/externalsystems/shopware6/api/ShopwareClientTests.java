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

package de.metas.camel.externalsystems.shopware6.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrder;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderCandidate;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class ShopwareClientTests
{

	private final ShopwareClient dumbShopwareClient = ShopwareClient.of("does", "not", "https://www.matter.com");
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testGetJsonOrderCandidate_WithoutCustomPaths() throws IOException
	{
		objectMapper.registerModule(new JavaTimeModule());

		InputStream jsonOrderIS = ShopwareClientTests.class.getResourceAsStream("JsonOrder.json");
		final JsonNode rootJsonNode = objectMapper.readValue(jsonOrderIS, JsonNode.class);

		jsonOrderIS = ShopwareClientTests.class.getResourceAsStream("JsonOrder.json");
		final JsonOrder jsonOrder = objectMapper.readValue(jsonOrderIS, JsonOrder.class);

		final Optional<OrderCandidate> orderCandidateOptional
				= dumbShopwareClient.getJsonOrderCandidate(rootJsonNode, null, null);

		assertThat(orderCandidateOptional.isPresent()).isTrue();
		final OrderCandidate orderCandidate = orderCandidateOptional.get();

		assertThat(objectMapper.writeValueAsString(orderCandidate.getJsonOrder())).isEqualTo(objectMapper.writeValueAsString(jsonOrder));
		assertThat(orderCandidate.getCustomBPartnerId()).isNull();
		assertThat(orderCandidate.getSalesRepId()).isNull();
	}

	@Test
	public void testGetJsonOrderCandidate_WithCustomPaths() throws IOException
	{
		objectMapper.registerModule(new JavaTimeModule());

		InputStream jsonOrderIS = ShopwareClientTests.class.getResourceAsStream("JsonOrder.json");
		final JsonNode rootOrderJsonNode = objectMapper.readValue(jsonOrderIS, JsonNode.class);

		jsonOrderIS = ShopwareClientTests.class.getResourceAsStream("JsonOrder.json");
		final JsonOrder standardFieldsOrder = objectMapper.readValue(jsonOrderIS, JsonOrder.class);

		final Optional<OrderCandidate> orderCandidateOptional
				= dumbShopwareClient.getJsonOrderCandidate(rootOrderJsonNode,
														   "/orderCustomer/customFields/metasfreshId",
														   "/orderCustomer/customFields/customUserId");

		assertThat(orderCandidateOptional.isPresent()).isTrue();
		final OrderCandidate orderCandidate = orderCandidateOptional.get();

		assertThat(objectMapper.writeValueAsString(orderCandidate.getJsonOrder())).isEqualTo(objectMapper.writeValueAsString(standardFieldsOrder));
		assertThat(orderCandidate.getCustomBPartnerId()).isEqualTo("customBPartnerId");
		assertThat(orderCandidate.getSalesRepId()).isEqualTo("customSalesRepId");
	}

	@Test
	public void testGetJsonOrderCandidate_WithCustomPaths_NegativeFlow() throws IOException
	{
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream jsonOrderIS = ShopwareClientTests.class.getResourceAsStream("JsonOrder.json");
		final JsonNode rootJsonNode = objectMapper.readValue(jsonOrderIS, JsonNode.class);

		final Optional<OrderCandidate> orderCandidateOptional
				= dumbShopwareClient.getJsonOrderCandidate(rootJsonNode,
														   "/bad/path",
														   "/orderCustomer/customFields/customUserId");

		assertThat(orderCandidateOptional.isPresent()).isFalse();
	}
}
