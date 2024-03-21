/*
 * #%L
 * de-metas-common-product
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

package de.metas.common.product.v2.request;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.common.pricing.v2.productprice.TaxCategory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

public class JsonRequestProductTaxCategoryUpsertTest
{

	private final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	//@Test TODO see why Instant deserialization returns {"epochSecond":1711053523,"nano":348000000}
	public void serializeDeserialize() throws IOException
	{
		final JsonRequestProductTaxCategoryUpsert jsonRequestProductTaxCategoryUpsert = JsonRequestProductTaxCategoryUpsert.builder()

				.taxCategory(TaxCategory.NORMAL)

				.validFrom(Instant.now())
				.countryCode("DE")
				.build();

		final String string = mapper.writeValueAsString(jsonRequestProductTaxCategoryUpsert);

		final JsonRequestProductTaxCategoryUpsert result = mapper.readValue(string, JsonRequestProductTaxCategoryUpsert.class);

		assertThat(result).isEqualTo(jsonRequestProductTaxCategoryUpsert);
	}
}

