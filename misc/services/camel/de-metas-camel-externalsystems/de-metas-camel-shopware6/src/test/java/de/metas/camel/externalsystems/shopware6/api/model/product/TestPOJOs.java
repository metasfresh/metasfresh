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

package de.metas.camel.externalsystems.shopware6.api.model.product;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.shopware6.api.model.JsonTax;
import de.metas.camel.externalsystems.shopware6.api.model.product.price.JsonPrice;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.*;

public class TestPOJOs
{
	private final ObjectMapper objectMapper = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

	@Test
	public void givenJsonProducts_whenSerializeDeserialize_thenSuccess() throws IOException
	{
		testSerializeDeserializeObject(getMockJsonProducts());
	}

	private void testSerializeDeserializeObject(final Object value) throws IOException
	{
		final Class<?> valueClass = value.getClass();
		final String json = objectMapper.writeValueAsString(value);
		final Object value2 = objectMapper.readValue(json, valueClass);
		assertThat(value2).isEqualTo(value);
	}

	private JsonProducts getMockJsonProducts()
	{
		return JsonProducts.builder()
				.productList(ImmutableList.of(
						JsonProduct.builder()
								.id("productId")
								.name("productName")
								.ean("ean")
								.unitId("unitId")
								.jsonTax(JsonTax.builder()
												 .taxRate(BigDecimal.TEN)
												 .build())
								.productNumber("productNumber")
								.createdAt(ZonedDateTime.now(ZoneId.of("UTC")))
								.updatedAt(ZonedDateTime.now(ZoneId.of("UTC")))
								.prices(ImmutableList.of(
										JsonPrice.builder()
												.currencyId("currencyId1")
												.net(BigDecimal.valueOf(154.76999999999998))
												.gross(BigDecimal.valueOf(165.6))
												.build(),
										JsonPrice.builder()
												.currencyId("currencyId2")
												.net(BigDecimal.valueOf(154.76999999999998))
												.gross(BigDecimal.valueOf(165.6))
												.build()
								))
								.build())
				).build();
	}
}