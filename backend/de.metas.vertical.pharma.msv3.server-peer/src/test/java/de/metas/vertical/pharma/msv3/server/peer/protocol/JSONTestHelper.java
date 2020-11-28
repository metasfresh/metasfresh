package de.metas.vertical.pharma.msv3.server.peer.protocol;

import java.io.IOException;

import org.junit.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.commons
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

public class JSONTestHelper
{
	private final ObjectMapper jsonObjectMapper;

	public JSONTestHelper()
	{
		jsonObjectMapper = createJsonObjectMapper();
	}

	public static ObjectMapper createJsonObjectMapper()
	{
		final ObjectMapper jsonObjectMapper = new ObjectMapper();
		jsonObjectMapper.findAndRegisterModules();
		return jsonObjectMapper;

	}

	public void testSerializeDeserialize(@NonNull final Object obj) throws IOException
	{
		final Class<? extends Object> type = obj.getClass();

		final String json = jsonObjectMapper.writeValueAsString(obj);
		final Object obj2 = jsonObjectMapper.readValue(json, type);
		Assert.assertEquals(obj, obj2);
	}

}
