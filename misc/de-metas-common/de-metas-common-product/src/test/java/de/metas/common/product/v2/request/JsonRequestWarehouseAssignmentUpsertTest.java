/*
 * #%L
 * de-metas-common-product
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

package de.metas.common.product.v2.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.metas.common.product.v2.request.JsonRequestUtil.getJsonRequestWarehouseAssignmentUpsert;
import static org.assertj.core.api.Assertions.*;

public class JsonRequestWarehouseAssignmentUpsertTest
{
	final ObjectMapper mapper = new ObjectMapper();

	@Test
	void serializeDeserialize() throws IOException
	{
		final JsonRequestProductWarehouseAssignmentCreate request = getJsonRequestWarehouseAssignmentUpsert();

		final String valueAsString = mapper.writeValueAsString(request);

		final JsonRequestProductWarehouseAssignmentCreate readValue = mapper.readValue(valueAsString, JsonRequestProductWarehouseAssignmentCreate.class);

		assertThat(readValue).isEqualTo(request);
	}

	@Test
	void deserializeExpectException()
	{
		final String valueAsString = "{\"requestItems\":[{\"warehouseIdentifier\":null,\"name\":null}],\"syncAdvise\":{\"ifNotExists\":\"CREATE\",\"ifExists\":\"REPLACE\"}}";

		assertThatExceptionOfType(ValueInstantiationException.class)
				.isThrownBy(() -> mapper.readValue(valueAsString, JsonRequestProductWarehouseAssignmentCreate.class))
				.withMessageContaining("At least one of warehouseIdentifier or name has to be specified")
				.withCauseInstanceOf(RuntimeException.class);
	}
}
