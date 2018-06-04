package de.metas.ordercandidate.rest;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	public void test() throws Exception
	{
		testSerializeDeserialize(JsonOLCandCreateRequest.builder()
				.bpartner(JsonBPartnerInfo.builder()
						.bpartnerId(1)
						.build())
				.dateRequired(LocalDate.of(2018, 03, 20))
				.build());
	}

	private void testSerializeDeserialize(final JsonOLCandCreateRequest obj) throws IOException
	{
		System.out.println("object: " + obj);
		final String json = jsonObjectMapper.writeValueAsString(obj);
		System.out.println("json: " + json);

		final JsonOLCandCreateRequest obj2 = jsonObjectMapper.readValue(json, JsonOLCandCreateRequest.class);
		System.out.println("object deserialized: " + obj2);

		Assert.assertEquals(obj, obj2);
	}
}
