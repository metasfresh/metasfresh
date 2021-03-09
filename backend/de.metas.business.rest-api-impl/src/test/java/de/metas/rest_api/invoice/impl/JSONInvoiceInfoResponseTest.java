/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.invoice.impl;

import de.metas.currency.CurrencyCode;
import de.metas.util.JSONObjectMapper;
import de.metas.util.lang.Percent;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class JSONInvoiceInfoResponseTest
{
	@Test
	void serialiseDeserialise()
	{
		final JSONInvoiceInfoResponse expected = JSONInvoiceInfoResponse.builder()
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

		final JSONObjectMapper<JSONInvoiceInfoResponse> mapper = JSONObjectMapper.forClass(JSONInvoiceInfoResponse.class);
		final String s = mapper.writeValueAsString(expected);
		final JSONInvoiceInfoResponse actual = mapper.readValue(s);

		assertThat(actual).isEqualTo(expected);
	}
}
