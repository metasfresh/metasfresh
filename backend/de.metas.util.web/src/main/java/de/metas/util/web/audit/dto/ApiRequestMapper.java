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
import de.metas.JsonObjectMapperHolder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

@UtilityClass
public class ApiRequestMapper
{
	@NonNull
	public ApiRequest map(@NonNull final ContentCachingRequestWrapper contentCachingRequestWrapper)
	{
		return ApiRequest.builder()
				.body(getRequestBody(contentCachingRequestWrapper))
				.fullPath(getFullPath(contentCachingRequestWrapper))
				.headers(getHeaders(contentCachingRequestWrapper))
				.httpMethod(contentCachingRequestWrapper.getMethod())
				.remoteAddr(contentCachingRequestWrapper.getRemoteAddr())
				.remoteHost(contentCachingRequestWrapper.getRemoteHost())
				.requestURI(contentCachingRequestWrapper.getRequestURI())
				.build();
	}

	@NonNull
	public String getFullPath(@NonNull final ContentCachingRequestWrapper requestWrapper)
	{
		String fullPath = requestWrapper.getRequestURL().toString();

		if (requestWrapper.getQueryString() != null)
		{
			fullPath += "?" + requestWrapper.getQueryString();
		}

		return fullPath;
	}

	@NonNull
	private LinkedMultiValueMap<String, String> getHeaders(@NonNull final ContentCachingRequestWrapper requestWrapper)
	{
		final LinkedMultiValueMap<String, String> requestHeadersMultiValueMap = new LinkedMultiValueMap<>();

		final Enumeration<String> allHeaderNames = requestWrapper.getHeaderNames();

		while (allHeaderNames != null && allHeaderNames.hasMoreElements())
		{
			final String currentHeaderName = allHeaderNames.nextElement();
			requestHeadersMultiValueMap.addAll(currentHeaderName, Collections.list(requestWrapper.getHeaders(currentHeaderName)));
		}

		return requestHeadersMultiValueMap;
	}

	@Nullable
	private String getRequestBody(@NonNull final ContentCachingRequestWrapper requestWrapper)
	{
		try
		{
			final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

			if (requestWrapper.getContentLength() <= 0)
			{
				return null;
			}

			final Object body = requestWrapper.getContentAsByteArray().length > 0
					? objectMapper.readValue(requestWrapper.getContentAsByteArray(), Object.class)
					: objectMapper.readValue(requestWrapper.getReader(), Object.class);

			return mapToString(body);
		}
		catch (final IOException e)
		{
			throw new RuntimeException("Failed to parse request body!", e);
		}
	}

	@Nullable
	private String mapToString(@Nullable final Object obj)
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
			throw new RuntimeException("Failed to parse object!", e);
		}
	}
}
