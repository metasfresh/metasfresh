/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.common.rest_api.v2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.common.rest_api.common.JsonExternalId;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class JsonPurchaseCandidatesRequestTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void beforeEach()
	{
		jsonObjectMapper = new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@Test
	public void jsonOutOfStockNoticeResponse() throws Exception
	{
		final JsonPurchaseCandidateReference reference1 = JsonPurchaseCandidateReference.builder().externalHeaderId(JsonExternalId.of("abc")).build();
		final JsonPurchaseCandidateReference reference2 = JsonPurchaseCandidateReference.builder().externalHeaderId(JsonExternalId.of("123")).externalLineId(JsonExternalId.of("456")).build();

		final JsonPurchaseCandidatesRequest jsonPurchaseCandidatesRequest = JsonPurchaseCandidatesRequest.builder()
				.purchaseCandidate(reference1)
				.purchaseCandidate(reference2)
				.build();

		testSerializeDeserialize(jsonPurchaseCandidatesRequest, JsonPurchaseCandidatesRequest.class);
	}

	private <T> void testSerializeDeserialize(@NonNull final T json, @NonNull final Class<T> clazz) throws IOException
	{
		final String jsonString = jsonObjectMapper.writeValueAsString(json);
		final T jsonDeserialized = jsonObjectMapper.readValue(jsonString, clazz);
		assertThat(jsonDeserialized).isEqualTo(json);
	}
}