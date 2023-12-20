/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.common.rest_api.v2.conversionRate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class JsonCurrencyRateCreateRequestTest
{
	private final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	@Test
	public void serializeDeserialize() throws IOException
	{
		final JsonCurrencyRateCreateRequestItem requestItem = JsonCurrencyRateCreateRequestItem
				.builder()
				.currencyCodeTo("currencyCodeTo")
				.conversionType("conversionType")
				.divideRate(BigDecimal.valueOf(1.55))
				.validFrom(LocalDate.of(2023, 2, 1))
				.validTo(LocalDate.of(2023, 5, 1))
				.build();

		final JsonCurrencyRateCreateRequest currencyRateCreateRequest = JsonCurrencyRateCreateRequest.builder()
				.orgCode("orgCode")
				.currencyCodeFrom("currencyCodeFrom")
				.requestItems(ImmutableList.of(requestItem))
				.build();

		final String string = mapper.writeValueAsString(currencyRateCreateRequest);

		final JsonCurrencyRateCreateRequest result = mapper.readValue(string, JsonCurrencyRateCreateRequest.class);

		assertThat(result).isEqualTo(currencyRateCreateRequest);
	}
}
