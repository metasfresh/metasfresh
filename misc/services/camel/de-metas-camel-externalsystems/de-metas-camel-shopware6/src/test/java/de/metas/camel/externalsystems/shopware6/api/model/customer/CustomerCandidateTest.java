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

package de.metas.camel.externalsystems.shopware6.api.model.customer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.shopware6.api.model.order.Customer;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonCustomer;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderCustomer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.*;

public class CustomerCandidateTest
{
	private static final String CUSTOMER_METASFRESH_ID = "1_Customer_JsonNode.json";

	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void givenExistingPath_whenGetCustomFieldMetasfreshId_thenReturnValue() throws IOException
	{
		//given
		final String bPartnerCustomJsonPath = "/customFields/metasfreshId";

		final String customFieldValue = "metasfreshId";

		final InputStream jsonCustomerIS = this.getClass().getResourceAsStream(CUSTOMER_METASFRESH_ID);
		final JsonNode jsonNode = objectMapper.readValue(jsonCustomerIS, JsonNode.class);

		//when
		final JsonCustomer jsonCustomer = objectMapper.treeToValue(jsonNode, JsonCustomer.class);

		final Customer customer = Customer.of(jsonNode, jsonCustomer);
		final String customField = customer.getCustomField(bPartnerCustomJsonPath);

		//then
		assertThat(customField).isNotNull();
		assertThat(customField).isEqualTo(customFieldValue);
	}

	@Test
	public void givenExistingPath_whenGetCustomField_thenReturnValue() throws IOException
	{
		//given
		final String bPartnerCustomJsonPath = "/customFields/someCustomId";

		final String customFieldValue = "someCustomId";

		final InputStream jsonCustomerIS = this.getClass().getResourceAsStream(CUSTOMER_METASFRESH_ID);
		final JsonNode jsonNode = objectMapper.readValue(jsonCustomerIS, JsonNode.class);

		//when
		final JsonCustomer jsonCustomer = objectMapper.treeToValue(jsonNode, JsonCustomer.class);

		final Customer customer = Customer.of(jsonNode, jsonCustomer);
		final String customField = customer.getCustomField(bPartnerCustomJsonPath);

		//then
		assertThat(customField).isNotNull();
		assertThat(customField).isEqualTo(customFieldValue);
	}

	@Test
	public void givenMissingPath_whenGetCustomField_thenReturnValue() throws IOException
	{
		//given
		final String bPartnerCustomJsonPath = "/customFields/metasfreshId_missing";

		final InputStream jsonCustomerIS = this.getClass().getResourceAsStream(CUSTOMER_METASFRESH_ID);
		final JsonNode jsonNode = objectMapper.readValue(jsonCustomerIS, JsonNode.class);

		//when
		final JsonCustomer jsonCustomer = objectMapper.treeToValue(jsonNode, JsonCustomer.class);

		final Customer customer = Customer.of(jsonNode, jsonCustomer);
		final String customField = customer.getCustomField(bPartnerCustomJsonPath);

		//then
		assertThat(customField).isNull();
	}

	@Test
	public void givenMappedToJsonCustomer_whenGetId_thenReturnId() throws IOException
	{
		//given
		final InputStream jsonCustomerIS = this.getClass().getResourceAsStream(CUSTOMER_METASFRESH_ID);
		final JsonNode jsonNode = objectMapper.readValue(jsonCustomerIS, JsonNode.class);

		//when
		final JsonCustomer jsonCustomer = objectMapper.treeToValue(jsonNode, JsonCustomer.class);

		final Customer customer = Customer.of(jsonNode, jsonCustomer);

		//then
		assertThat(customer).isNotNull();
		assertThat(customer.getShopwareId()).isEqualTo("id");
		assertThat(customer.getDefaultShippingAddressId()).isEqualTo("defaultShippingAddressId");
		assertThat(customer.getDefaultBillingAddressId()).isEqualTo("defaultBillingAddressId");
	}

	@Test
	public void givenMappedToJsonOrderCustomer_whenGetId_thenReturnCustomerId() throws IOException
	{
		//given
		final InputStream jsonCustomerIS = this.getClass().getResourceAsStream(CUSTOMER_METASFRESH_ID);
		final JsonNode jsonNode = objectMapper.readValue(jsonCustomerIS, JsonNode.class);

		//when
		final JsonOrderCustomer jsonOrderCustomer = objectMapper.treeToValue(jsonNode, JsonOrderCustomer.class);

		final Customer customer = Customer.of(jsonNode, jsonOrderCustomer);

		//then
		assertThat(customer).isNotNull();
		assertThat(customer.getShopwareId()).isEqualTo("customerId");
		assertThat(customer.getDefaultShippingAddressId()).isNull();
		assertThat(customer.getDefaultBillingAddressId()).isNull();
	}
}
