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

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonQuantity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class JsonDeliveryOrderLineContentsTest
{
	private static ObjectMapper mapper()
	{
		final ObjectMapper m = new ObjectMapper();
		m.findAndRegisterModules();
		return m;
	}

	private static JsonDeliveryOrderLineContents content(final String totalWeightKg, final String qty)
	{
		return JsonDeliveryOrderLineContents.builder()
				.shipmentOrderItemId("10")
				.unitPrice(JsonMoney.builder().amount(BigDecimal.ONE).currencyCode("EUR").build())
				.totalValue(JsonMoney.builder().amount(BigDecimal.ONE).currencyCode("EUR").build())
				.productName("Prod")
				.productValue("P-1")
				.totalWeightInKg(new BigDecimal(totalWeightKg))
				.shippedQuantity(JsonQuantity.builder().value(new BigDecimal(qty)).uomCode("PCE").build())
				.build();
	}

	@Test
	void getUnitWeightKg_divides_totalWeight_by_quantity()
	{
		assertThat(content("1", "2").getUnitWeightKg()).isEqualByComparingTo("0.5");
		assertThat(content("10", "10").getUnitWeightKg()).isEqualByComparingTo("1");
		assertThat(content("1", "3").getUnitWeightKg()).isEqualByComparingTo("0.333333");
	}

	@Test
	void getUnitWeightKg_zero_quantity_returns_zero()
	{
		assertThat(content("5", "0").getUnitWeightKg()).isEqualByComparingTo("0");
	}

	@Test
	void getValue_unitWeight_kg_and_g()
	{
		final JsonDeliveryOrderLineContents c = content("1", "2"); // 0.5 kg = 500 g

		assertThat(c.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_KG)).contains("0.5");
		assertThat(c.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_G)).contains("500");
	}

	@Test
	void getValue_totalWeight_kg_and_g()
	{
		final JsonDeliveryOrderLineContents c = content("2.5", "1");

		assertThat(c.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_WEIGHT_KG)).contains("2.5");
		assertThat(c.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_WEIGHT_G)).contains("2500");
	}

	@Test
	void getValue_uom_code()
	{
		assertThat(content("1", "1").getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UOM_CODE)).contains("PCE");
	}

	@Test
	void json_does_not_serialize_computed_unitWeight() throws Exception
	{
		final String json = mapper().writeValueAsString(content("1", "2"));

		assertThat(json).doesNotContain("unitWeightKg").doesNotContain("UnitWeightKg");
	}

	@Test
	void json_roundtrip_preserves_values() throws Exception
	{
		final JsonDeliveryOrderLineContents c = content("0.5", "2");

		final String json = mapper().writeValueAsString(c);
		final JsonDeliveryOrderLineContents back = mapper().readValue(json, JsonDeliveryOrderLineContents.class);

		assertThat(back).isEqualTo(c);
	}
}
