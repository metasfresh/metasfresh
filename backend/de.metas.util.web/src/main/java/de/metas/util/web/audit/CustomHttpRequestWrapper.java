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

package de.metas.util.web.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;

@Value
@Builder
public class CustomHttpRequestWrapper
{
	@NonNull
	ContentCachingRequestWrapper requestWrapper;

	@NonNull
	ObjectMapper objectMapper;

	public CustomHttpRequestWrapper(@NonNull final HttpServletRequest request, @NonNull final ObjectMapper objectMapper)
	{
		this.requestWrapper = new ContentCachingRequestWrapper(request);
		this.objectMapper = objectMapper;
	}

	@NonNull
	public String getFullPath()
	{
		String fullPath = requestWrapper.getRequestURL().toString();

		if (requestWrapper.getQueryString() != null)
		{
			fullPath += "?" + requestWrapper.getQueryString();
		}

		return fullPath;
	}

	@NonNull
	public HttpMethod getHttpMethod()
	{
		return HttpMethod.resolve(requestWrapper.getMethod());
	}

	@NonNull
	public String getHttpMethodString()
	{
		return requestWrapper.getMethod();
	}

	@NonNull
	public HttpHeaders getAllHeaders()
	{
		final HttpHeaders allHeaders = new HttpHeaders();
		final Enumeration<String> allHeaderNames = requestWrapper.getHeaderNames();

		while (allHeaderNames != null && allHeaderNames.hasMoreElements())
		{
			final String currentHeaderName = allHeaderNames.nextElement();
			allHeaders.add(currentHeaderName, requestWrapper.getHeader(currentHeaderName));
		}

		return allHeaders;
	}

	@Nullable
	public Object getRequestBody()
	{
		if (requestWrapper.getContentAsByteArray().length == 0)
		{
			return null;
		}

		try
		{
			return objectMapper.readValue(requestWrapper.getContentAsByteArray(), Object.class);
		}
		catch (final IOException e)
		{
			throw new RuntimeException("Failed to parse request body!", e);
		}
	}

	@Nullable
	public String getRequestBodyAsString()
	{
		final Object requestBody = getRequestBody();

		if (requestBody == null)
		{
			return null;
		}

		try
		{
			return objectMapper.writeValueAsString(requestBody);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException("Failed to parse request body!", e);
		}
	}

	@Nullable
	public String getRemoteAddr()
	{
		return requestWrapper.getRemoteAddr();
	}

	@Nullable
	public String getRemoteHost()
	{
		return requestWrapper.getRemoteHost();
	}

	@NonNull
	public URI getURI()
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(getFullPath());
		return uriBuilder.build().toUri();
	}
}
