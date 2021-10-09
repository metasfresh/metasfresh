/*
 * #%L
 * de-metas-common-manufacturing
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

package de.metas.common.manufacturing.v2;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import de.metas.common.JsonTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.NonNull;

public class JsonRequestManufacturingOrdersReportTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void beforeEach()
	{
		jsonObjectMapper = JsonTestHelper.newJsonObjectMapper();
	}

	@Test
	public void empty() throws Exception
	{
		testSerializeDeserialize(JsonRequestManufacturingOrdersReport.builder()
				.build());
	}

	private void testSerializeDeserialize(@NonNull final JsonRequestManufacturingOrdersReport json) throws IOException
	{
		final String jsonString = jsonObjectMapper.writeValueAsString(json);
		final JsonRequestManufacturingOrdersReport jsonDeserialized = jsonObjectMapper.readValue(jsonString, JsonRequestManufacturingOrdersReport.class);
		assertThat(jsonDeserialized).isEqualTo(json);
	}
}
