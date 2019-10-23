package org.adempiere.ad.dao.cache;

import java.io.IOException;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;
import de.metas.cache.model.CacheInvalidateMultiRequest;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class CacheInvalidateMultiRequestSerializer
{
	private final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	public String toJson(final CacheInvalidateMultiRequest request)
	{
		try
		{
			return jsonObjectMapper.writeValueAsString(request);
		}
		catch (final JsonProcessingException ex)
		{
			throw new AdempiereException("Failed converting request to json: " + request, ex);
		}
	}

	public CacheInvalidateMultiRequest fromJson(final String jsonRequest)
	{
		try
		{
			return jsonObjectMapper.readValue(jsonRequest, CacheInvalidateMultiRequest.class);
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed converting json to request: " + jsonRequest, ex);
		}
	}

}
