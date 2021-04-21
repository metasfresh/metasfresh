package de.metas.rest_api.v1.ordercandidates.impl;

import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateBulkRequest;
import de.metas.util.JSONObjectMapper;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class JsonOLCandCreateBulkRequestTest
{
	@Test
	public void test_realJson() throws IOException
	{
		final JsonOLCandCreateBulkRequest bulkRequest = JsonOLCandUtil.loadJsonOLCandCreateBulkRequest("/de/metas/rest_api/v1/ordercandidates/impl/" + "JsonOLCandCreateBulkRequest.json");
		testSerializeDeserialize(bulkRequest, JSONObjectMapper.forClass(JsonOLCandCreateBulkRequest.class));
	}

	private <T> void testSerializeDeserialize(
			@NonNull final T obj,
			@NonNull JSONObjectMapper<T> jsonObjectMapper) throws IOException
	{
		// System.out.println("object: " + obj);
		final String json = jsonObjectMapper.writeValueAsString(obj);
		// System.out.println("json: " + json);

		final Object objDeserialized = jsonObjectMapper.readValue(json);
		// System.out.println("object deserialized: " + objDeserialized);

		assertThat(objDeserialized).isEqualTo(obj);
	}
}
