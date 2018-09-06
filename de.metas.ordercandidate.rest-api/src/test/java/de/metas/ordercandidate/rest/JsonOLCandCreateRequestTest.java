package de.metas.ordercandidate.rest;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class JsonOLCandCreateRequestTest
{
	private ObjectMapper jsonObjectMapper;

	@Before
	public void init()
	{
		jsonObjectMapper = new ObjectMapper();
		jsonObjectMapper.findAndRegisterModules();
	}

	@Test
	public void test_JsonOLCandCreateRequest() throws Exception
	{
		testSerializeDeserialize(createDummyJsonOLCandCreateRequest());
	}

	@Test
	public void test_JsonOLCandCreateBulkRequest() throws Exception
	{
		testSerializeDeserialize(JsonOLCandCreateBulkRequest.builder()
				.request(createDummyJsonOLCandCreateRequest())
				.request(createDummyJsonOLCandCreateRequest())
				.build());
	}

	private JsonOLCandCreateRequest createDummyJsonOLCandCreateRequest()
	{
		return JsonOLCandCreateRequest.builder()
				.bpartner(JsonBPartnerInfo.builder()
						.bpartner(JsonBPartner.builder()
								.code("bp1")
								.name("bp1 name")
								.build())
						.location(JsonBPartnerLocation.builder()
								.address1("address1")
								.address2("address2")
								.postal("12345")
								.city("city")
								.countryCode("DE")
								.build())
						.contact(JsonBPartnerContact.builder()
								.name("john doe")
								.email("john@doe.com")
								.phone("+123456789")
								.build())
						.build())
				.dateRequired(LocalDate.of(2018, 03, 20))
				.build();
	}

	private void testSerializeDeserialize(@NonNull final Object obj) throws IOException
	{
		System.out.println("object: " + obj);
		final String json = jsonObjectMapper.writeValueAsString(obj);
		System.out.println("json: " + json);

		final Object objDeserialized = jsonObjectMapper.readValue(json, obj.getClass());
		System.out.println("object deserialized: " + objDeserialized);

		Assert.assertEquals(obj, objDeserialized);
	}
}
