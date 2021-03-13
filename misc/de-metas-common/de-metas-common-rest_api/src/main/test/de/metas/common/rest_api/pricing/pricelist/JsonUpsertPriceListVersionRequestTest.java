/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.pricing.pricelist;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.common.rest_api.SyncAdvise;
import lombok.Builder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class JsonUpsertPriceListVersionRequestTest
{
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach()
	{
		objectMapper = newJsonObjectMapper();
	}

	private static ObjectMapper newJsonObjectMapper()
	{
		return new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@Test
	public void test() throws Exception
	{
		testSerializeDeserialize(createJsonUpsertPriceListVersionRequestBuilder()
										 .orgCode("test-code")
										 .priceListIdentifier("ext-123")
										 .description("test-description")
										 .validFrom("true")
										 .active("true")
										 .syncAdvise(SyncAdvise.CREATE_OR_MERGE)
										 .build());
	}

	@Builder(builderMethodName = "createJsonUpsertPriceListVersionRequestBuilder",
			builderClassName = "JsonUpsertPriceListVersionRequestBuilder")
	private JsonUpsertPriceListVersionRequest createJsonUpsertPriceListVersionRequest(
			final String priceListIdentifier,
			final String orgCode,
			final String validFrom,
			final String active,
			final String description,
			final SyncAdvise syncAdvise
	)
	{
		final JsonUpsertPriceListVersionRequest jsonUpsertPriceListVersionRequest = new JsonUpsertPriceListVersionRequest();
		jsonUpsertPriceListVersionRequest.setOrgCode(orgCode);
		jsonUpsertPriceListVersionRequest.setPriceListIdentifier(priceListIdentifier);
		jsonUpsertPriceListVersionRequest.setDescription(description);
		jsonUpsertPriceListVersionRequest.setValidFrom(validFrom);
		jsonUpsertPriceListVersionRequest.setActive(active);
		jsonUpsertPriceListVersionRequest.setSyncAdvise(syncAdvise);

		return jsonUpsertPriceListVersionRequest;
	}

	private void testSerializeDeserialize(final JsonUpsertPriceListVersionRequest obj) throws IOException
	{
		System.out.println("Object: " + obj);

		final String json = objectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final JsonUpsertPriceListVersionRequest objDeserialized = objectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}
}
