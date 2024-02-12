/*
 * #%L
 * de.metas.util.web
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

package de.metas.util.web.audit.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMultimap;
import de.metas.JsonObjectMapperHolder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

@UtilityClass
public class ApiRequestMapper
{
	@NonNull
	public ApiRequest map(@NonNull final CachedBodyHttpServletRequest cachedBodyHttpServletRequest)
	{
		return ApiRequest.builder()
				.body(getRequestBody(cachedBodyHttpServletRequest))
				.fullPath(getFullPath(cachedBodyHttpServletRequest))
				.headers(getHeaders(cachedBodyHttpServletRequest))
				.httpMethod(cachedBodyHttpServletRequest.getMethod())
				.remoteAddr(cachedBodyHttpServletRequest.getRemoteAddr())
				.remoteHost(cachedBodyHttpServletRequest.getRemoteHost())
				.requestURI(cachedBodyHttpServletRequest.getRequestURI())
				.build();
	}

	@NonNull
	public String getFullPath(@NonNull final HttpServletRequest requestWrapper)
	{
		String fullPath = requestWrapper.getRequestURL().toString();

		if (requestWrapper.getQueryString() != null)
		{
			fullPath += "?" + requestWrapper.getQueryString();
		}

		return fullPath;
	}

	@NonNull
	private ImmutableMultimap<String, String> getHeaders(@NonNull final HttpServletRequest request)
	{
		final ImmutableMultimap.Builder<String, String> requestHeaders = ImmutableMultimap.builder();

		final Enumeration<String> allHeaderNames = request.getHeaderNames();

		while (allHeaderNames != null && allHeaderNames.hasMoreElements())
		{
			final String currentHeaderName = allHeaderNames.nextElement();
			requestHeaders.putAll(currentHeaderName, Collections.list(request.getHeaders(currentHeaderName)));
		}

		return requestHeaders.build();
	}

	@Nullable
	private String getRequestBody(@NonNull final CachedBodyHttpServletRequest requestWrapper)
	{
		try
		{
			final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

			if (requestWrapper.getContentLength() <= 0)
			{
				return null;
			}

			final Object body = objectMapper.readValue(requestWrapper.getInputStream(), Object.class);

			return toJson(body);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed to parse request body!", e);
		}
	}

	@Nullable
	private String toJson(@Nullable final Object obj)
	{
		if (obj == null)
		{
			return null;
		}

		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(obj);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Failed to parse object!", e);
		}
	}
}
