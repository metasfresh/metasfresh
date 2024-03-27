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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.metas.common.product.v2.request.JsonRequestUtil.getJsonRequestProductTaxCategoryUpsert;
import static org.assertj.core.api.Assertions.*;

public class JsonRequestProductTaxCategoryUpsertTest
{
	ObjectMapper mapper = new ObjectMapper();

	private static ObjectMapper newJsonObjectMapper()
	{
		return new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@BeforeEach
	public void beforeEach()
	{
		mapper = newJsonObjectMapper();
	}

	@Test
	void serializeDeserialize() throws IOException
	{
		final JsonRequestProductTaxCategoryUpsert request = getJsonRequestProductTaxCategoryUpsert();
		final String valueAsString = mapper.writeValueAsString(request);

		final JsonRequestProductTaxCategoryUpsert readValue = mapper.readValue(valueAsString, JsonRequestProductTaxCategoryUpsert.class);

		assertThat(readValue).isEqualTo(request);
	}
}
