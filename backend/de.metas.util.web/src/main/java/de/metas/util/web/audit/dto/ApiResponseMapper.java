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
import de.metas.JsonObjectMapperHolder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@UtilityClass
public class ApiResponseMapper
{
	@NonNull
	public ApiResponse map(@NonNull final ContentCachingResponseWrapper responseWrapper)
	{
		return new ApiResponse(responseWrapper.getStatus(), getHeaders(responseWrapper), getBody(responseWrapper));
	}

	@NonNull
	public ApiResponse map(final int statusCode, @Nullable final HttpHeaders httpHeaders, @Nullable final String bodyCandidate)
	{
		return new ApiResponse(statusCode, httpHeaders, getBody(httpHeaders, bodyCandidate));
	}

	@NonNull
	private HttpHeaders getHeaders(@NonNull final ContentCachingResponseWrapper responseWrapper)
	{
		final HttpHeaders responseHeaders = new HttpHeaders();

		if (responseWrapper.getHeaderNames() == null || responseWrapper.getHeaderNames().isEmpty())
		{
			return responseHeaders;
		}

		responseWrapper.getHeaderNames()
				.forEach(headerName -> responseHeaders.addAll(headerName, new ArrayList<>(responseWrapper.getHeaders(headerName))));

		return responseHeaders;
	}

	@Nullable
	private Object getBody(@NonNull final ContentCachingResponseWrapper responseWrapper)
	{
		if (responseWrapper.getContentSize() <= 0 || !responseWrapper.getContentType().contains(APPLICATION_JSON_VALUE))
		{
			return null;
		}

		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper()
					.readValue(responseWrapper.getContentAsByteArray(), Object.class);
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@Nullable
	private Object getBody(@Nullable final HttpHeaders httpHeaders, @Nullable final String bodyCandidate)
	{
		final boolean isJsonBody = bodyCandidate != null
				&& httpHeaders != null
				&& httpHeaders.getContentType() != null
				&& httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON);

		if (!isJsonBody)
		{
			return null;
		}

		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper()
					.readValue(bodyCandidate, Object.class);
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
