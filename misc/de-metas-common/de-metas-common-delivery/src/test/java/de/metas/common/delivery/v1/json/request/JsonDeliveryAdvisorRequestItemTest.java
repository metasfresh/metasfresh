/*
 * #%L
 * de-metas-common-delivery
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.common.delivery.v1.json.request;

import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonQuantity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class JsonDeliveryAdvisorRequestItemTest
{
	@Test
	void getValue_countryOfOrigin()
	{
		final JsonDeliveryAdvisorRequestItem withCountry = JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(1)
				.productName("Prod")
				.productValue("P-1")
				.countryOfOrigin("DE")
				.build();

		assertThat(withCountry.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_COUNTRY_OF_ORIGIN)).contains("DE");

		final JsonDeliveryAdvisorRequestItem withoutCountry = JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(1)
				.productName("Prod")
				.productValue("P-1")
				.build();

		assertThat(withoutCountry.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_COUNTRY_OF_ORIGIN)).isEmpty();
	}

	@Test
	void getValue_productNameAndValue()
	{
		final JsonDeliveryAdvisorRequestItem item = JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(1)
				.productName("Prod")
				.productValue("P-1")
				.build();

		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_NAME)).contains("Prod");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_VALUE)).contains("P-1");
	}

	@Test
	void getValue_commercialContent()
	{
		final JsonDeliveryAdvisorRequestItem item = JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(1)
				.productName("Prod")
				.productValue("P-1")
				.unitPrice(JsonMoney.builder().amount(new BigDecimal("12.50")).currencyCode("EUR").build())
				.totalValue(JsonMoney.builder().amount(new BigDecimal("25.00")).currencyCode("EUR").build())
				.shippedQuantity(JsonQuantity.builder().value(new BigDecimal("2")).uomCode("PCE").build())
				.customsTariff("12345678")
				.totalWeightInKg(new BigDecimal("4"))
				.build();

		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_PRICE)).contains("12.50");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_VALUE)).contains("25.00");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_CURRENCY_CODE)).contains("EUR");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPED_QUANTITY)).contains("2");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UOM_CODE)).contains("PCE");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_CUSTOMS_TARIFF)).contains("12345678");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_WEIGHT_KG)).contains("4");
		// per-unit weight = totalWeightInKg / shippedQuantity = 4 / 2 = 2
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_KG)).contains("2");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_G)).contains("2000");
	}
}
