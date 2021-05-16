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
public class CustomHttpRequestWrapper
{
	@NonNull
	String fullPath;

	@NonNull
	String httpMethod;

	@NonNull
	HttpHeaders httpHeaders;

	@Nullable
	Object requestBody;

	@Nullable
	String remoteAddr;

	@Nullable
	String remoteHost;

	@NonNull
	ObjectMapper objectMapper;

	public CustomHttpRequestWrapper(@NonNull final HttpServletRequest request, @NonNull final ObjectMapper objectMapper)
	{
		final ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		this.objectMapper = objectMapper;

		this.fullPath = extractFullPath(requestWrapper);
		this.requestBody = extractRequestBody(requestWrapper);
		this.httpHeaders = extractHttHeaders(requestWrapper);
		this.httpMethod = requestWrapper.getMethod();
		this.remoteAddr = requestWrapper.getRemoteAddr();
		this.remoteHost = requestWrapper.getRemoteHost();
	}

	@NonNull
	public HttpMethod getHttpMethod()
	{
		return HttpMethod.resolve(this.httpMethod);
	}

	@NonNull
	public String getHttpMethodString()
	{
		return httpMethod;
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

	@NonNull
	public URI getURI()
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(getFullPath());
		return uriBuilder.build().toUri();
	}

	@NonNull
	private String extractFullPath(final ContentCachingRequestWrapper requestWrapper)
	{
		String fullPath = requestWrapper.getRequestURL().toString();

		if (requestWrapper.getQueryString() != null)
		{
			fullPath += "?" + requestWrapper.getQueryString();
		}

		return fullPath;
	}

	@Nullable
	private Object extractRequestBody(final ContentCachingRequestWrapper requestWrapper)
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

	@NonNull
	private HttpHeaders extractHttHeaders(final ContentCachingRequestWrapper requestWrapper)
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
}
