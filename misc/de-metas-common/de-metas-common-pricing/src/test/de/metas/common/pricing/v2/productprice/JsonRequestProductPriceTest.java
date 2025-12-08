/*
 * #%L
 * de-metas-common-pricing
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

package de.metas.common.pricing.v2.productprice;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.Builder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class JsonRequestProductPriceTest
{
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach()
	{
		objectMapper = newJsonObjectMapper();
	}

	private static ObjectMapper newJsonObjectMapper()
	{
		return new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@Test
	public void test() throws Exception
	{
		testSerializeDeserialize(createJsonRequestProductPriceBuilder()
										 .orgCode("test-code")
										 .productIdentifier("123")
										 .taxCategory(TaxCategory.NORMAL)
										 .priceLimit(BigDecimal.valueOf(10))
										 .priceList(BigDecimal.valueOf(10))
										 .priceStd(BigDecimal.valueOf(0))
										 .seqNo(0)
										 .active(true)
										 .syncAdvise(SyncAdvise.CREATE_OR_MERGE)
										 .uomCode("PCE")
										 .build());
	}

	@Builder(builderMethodName = "createJsonRequestProductPriceBuilder",
			builderClassName = "JsonRequestProductPriceBuilder")
	private JsonRequestProductPrice createJsonRequestProductPrice(
			final String orgCode,
			final String productIdentifier,
			final TaxCategory taxCategory,
			final BigDecimal priceLimit,
			final BigDecimal priceList,
			final BigDecimal priceStd,
			final Integer seqNo,
			final boolean active,
			final SyncAdvise syncAdvise,
			final String uomCode
	)
	{
		final JsonRequestProductPrice jsonRequestProductPrice = new JsonRequestProductPrice();
		jsonRequestProductPrice.setOrgCode(orgCode);
		jsonRequestProductPrice.setProductIdentifier(productIdentifier);
		jsonRequestProductPrice.setTaxCategory(TaxCategory.NORMAL);
		jsonRequestProductPrice.setPriceLimit(priceLimit);
		jsonRequestProductPrice.setPriceList(priceList);
		jsonRequestProductPrice.setPriceStd(priceStd);
		jsonRequestProductPrice.setSeqNo(seqNo);
		jsonRequestProductPrice.setActive(active);
		jsonRequestProductPrice.setSyncAdvise(syncAdvise);
		jsonRequestProductPrice.setUomCode(uomCode);

		return jsonRequestProductPrice;
	}

	private void testSerializeDeserialize(final JsonRequestProductPrice obj) throws IOException
	{
		System.out.println("Object: " + obj);

		final String json = objectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final JsonRequestProductPrice objDeserialized = objectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}
}
