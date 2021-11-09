/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.invoice.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.currency.CurrencyCode;
import de.metas.util.lang.Percent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class JSONInvoiceInfoResponseTest
{
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach()
	{
		objectMapper = newJsonObjectMapper();
	}

	private static ObjectMapper newJsonObjectMapper()
	{
		return new ObjectMapper();
	}

	@Test
	public void test() throws Exception
	{
		final JSONInvoiceInfoResponse request = JSONInvoiceInfoResponse.builder()
				.invoiceId(JsonMetasfreshId.of(1))
				.lineInfo(JSONInvoiceLineInfo.builder()
								  .lineNumber(1234)
								  .productName("the product name")
								  .qtyInvoiced(BigDecimal.valueOf(12))
								  .price(BigDecimal.valueOf(34))
								  .taxRate(Percent.of(56))
								  .lineNetAmt(BigDecimal.valueOf(78))
								  .currency(CurrencyCode.ofThreeLetterCode("ZAR"))
								  .build())
				.build();

		testSerializeDeserialize(request);
	}

	private void testSerializeDeserialize(final Object obj) throws IOException
	{
		System.out.println("Object: " + obj);

		final String json = objectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final Object objDeserialized = objectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}
}
