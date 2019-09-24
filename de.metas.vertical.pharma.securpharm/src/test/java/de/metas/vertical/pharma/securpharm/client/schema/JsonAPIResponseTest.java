package de.metas.vertical.pharma.securpharm.client.schema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;

import de.metas.JsonObjectMapperHolder;

/*
 * #%L
 * metasfresh-pharma.securpharm
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

public class JsonAPIResponseTest
{
	private ObjectMapper jsonObjectMapper;

	@Before
	public void init()
	{
		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	@Test
	public void test() throws Exception
	{
		final String json = readResourceAsString("JsonAPIResponse.json");
		jsonObjectMapper.readValue(json, JsonAPIResponse.class);
	}

	private String readResourceAsString(final String resourceName) throws IOException
	{
		final InputStream in = getClass().getResourceAsStream(resourceName);
		if (in == null)
		{
			throw new AdempiereException("No resource found for " + resourceName);
		}

		try (BufferedReader br = new BufferedReader(new InputStreamReader(in, Charsets.UTF_8)))
		{
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}
}
