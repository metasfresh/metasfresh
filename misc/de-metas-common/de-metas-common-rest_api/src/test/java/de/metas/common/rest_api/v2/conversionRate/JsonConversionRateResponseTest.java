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
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

public class JsonConversionRateResponseTest
{
	private final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	@Test
	public void serializeDeserialize() throws IOException
	{
		final JsonConversionRateResponseItem responseItem = JsonConversionRateResponseItem.builder()
				.conversionRateId(JsonMetasfreshId.of(123))
				.currencyCodeFrom("currencyCodeFrom")
				.currencyCodeTo("currencyCodeTo")
				.divideRate(BigDecimal.valueOf(1.5))
				.validFrom(Instant.now())
				.build();

		final JsonConversionRateResponse response = JsonConversionRateResponse.builder()
				.responseItems(ImmutableList.of(responseItem))
				.build();

		final String string = mapper.writeValueAsString(response);

		final JsonConversionRateResponse result = mapper.readValue(string, JsonConversionRateResponse.class);

		assertThat(result).isEqualTo(response);
	}
}
