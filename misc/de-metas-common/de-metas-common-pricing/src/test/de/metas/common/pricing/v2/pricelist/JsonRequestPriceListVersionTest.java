/*
 * #%L
 * de-metas-common-pricing
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

package de.metas.common.pricing.v2.productprice;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.common.pricing.v2.pricelist.request.JsonRequestPriceListVersion;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.Builder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

public class JsonRequestPriceListVersionTest
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

		testSerializeDeserialize(createJsonRequestPriceListVersionBuilder()
										 .orgCode("test-code")
										 .priceListIdentifier("ext-123")
										 .description("test-description")
										 .validFrom(Instant.parse("2019-11-22T00:00:00Z"))
										 .active(true)
										 .syncAdvise(SyncAdvise.CREATE_OR_MERGE)
										 .build());
	}

	@Builder(builderMethodName = "createJsonRequestPriceListVersionBuilder",
			builderClassName = "JsonRequestPriceListVersionBuilder")
	private JsonRequestPriceListVersion createJsonRequestPriceListVersion(
			final String priceListIdentifier,
			final String orgCode,
			final Instant validFrom,
			final boolean active,
			final String description,
			final SyncAdvise syncAdvise
	)
	{
		final JsonRequestPriceListVersion jsonRequestPriceListVersion = new JsonRequestPriceListVersion();
		jsonRequestPriceListVersion.setOrgCode(orgCode);
		jsonRequestPriceListVersion.setPriceListIdentifier(priceListIdentifier);
		jsonRequestPriceListVersion.setDescription(description);
		jsonRequestPriceListVersion.setValidFrom(validFrom);
		jsonRequestPriceListVersion.setActive(active);
		jsonRequestPriceListVersion.setSyncAdvise(syncAdvise);

		return jsonRequestPriceListVersion;
	}

	private void testSerializeDeserialize(final JsonRequestPriceListVersion obj) throws IOException
	{
		System.out.println("Object: " + obj);

		final String json = objectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final JsonRequestPriceListVersion objDeserialized = objectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}
}
