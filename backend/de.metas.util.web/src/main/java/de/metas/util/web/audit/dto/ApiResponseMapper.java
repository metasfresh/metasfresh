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

<<<<<<< HEAD
import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.JsonObjectMapperHolder;
=======
import de.metas.JsonObjectMapperHolder;
import de.metas.util.StringUtils;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.annotation.Nullable;
import java.io.IOException;
<<<<<<< HEAD
import java.util.ArrayList;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
=======
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

@UtilityClass
public class ApiResponseMapper
{
	@NonNull
	public ApiResponse map(@NonNull final ContentCachingResponseWrapper responseWrapper)
	{
<<<<<<< HEAD
		return new ApiResponse(responseWrapper.getStatus(), getHeaders(responseWrapper), getBody(responseWrapper));
=======
		return ApiResponse.builder()
				.statusCode(responseWrapper.getStatus())
				.httpHeaders(getHeaders(responseWrapper))
				.contentType(getContentType(responseWrapper))
				.charset(getCharset(responseWrapper))
				.body(getBody(responseWrapper))
				.build();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@NonNull
	public ApiResponse map(final int statusCode, @Nullable final HttpHeaders httpHeaders, @Nullable final String bodyCandidate)
	{
<<<<<<< HEAD
		return new ApiResponse(statusCode, httpHeaders, getBody(httpHeaders, bodyCandidate));
=======
		return ApiResponse.builder()
				.statusCode(statusCode)
				.httpHeaders(httpHeaders)
				.contentType(getContentType(httpHeaders))
				.body(getBody(httpHeaders, bodyCandidate))
				.build();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@NonNull
	private HttpHeaders getHeaders(@NonNull final ContentCachingResponseWrapper responseWrapper)
	{
		final HttpHeaders responseHeaders = new HttpHeaders();

<<<<<<< HEAD
		if (responseWrapper.getHeaderNames() == null || responseWrapper.getHeaderNames().isEmpty())
=======
		final Collection<String> headerNames = responseWrapper.getHeaderNames();
		if (headerNames == null || headerNames.isEmpty())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return responseHeaders;
		}

<<<<<<< HEAD
		responseWrapper.getHeaderNames()
				.forEach(headerName -> responseHeaders.addAll(headerName, new ArrayList<>(responseWrapper.getHeaders(headerName))));
=======
		headerNames.forEach(headerName -> responseHeaders.addAll(headerName, new ArrayList<>(responseWrapper.getHeaders(headerName))));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		return responseHeaders;
	}

	@Nullable
<<<<<<< HEAD
	private Object getBody(@NonNull final ContentCachingResponseWrapper responseWrapper)
	{
		if (responseWrapper.getContentSize() <= 0 || !responseWrapper.getContentType().contains(APPLICATION_JSON_VALUE))
=======
	private static MediaType getContentType(final @NonNull ContentCachingResponseWrapper responseWrapper)
	{
		final String contentType = StringUtils.trimBlankToNull(responseWrapper.getContentType());
		return contentType != null ? MediaType.parseMediaType(contentType) : null;
	}

	@Nullable
	private static MediaType getContentType(final @Nullable HttpHeaders httpHeaders)
	{
		return httpHeaders != null ? httpHeaders.getContentType() : null;
	}

	@NonNull
	private static Charset getCharset(final @NonNull ContentCachingResponseWrapper responseWrapper)
	{
		final String charset = StringUtils.trimBlankToNull(responseWrapper.getCharacterEncoding());
		return charset != null ? Charset.forName(charset) : StandardCharsets.UTF_8;
	}

	@Nullable
	private Object getBody(@NonNull final ContentCachingResponseWrapper responseWrapper)
	{
		if (responseWrapper.getContentSize() <= 0)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return null;
		}

<<<<<<< HEAD
		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper()
					.readValue(responseWrapper.getContentAsByteArray(), Object.class);
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
=======
		final MediaType contentType = getContentType(responseWrapper);
		if (contentType == null)
		{
			return null;
		}
		else if (contentType.includes(MediaType.TEXT_PLAIN))
		{
			return new String(responseWrapper.getContentAsByteArray());
		}
		else if (contentType.includes(MediaType.APPLICATION_JSON))
		{
			try
			{
				return JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(responseWrapper.getContentAsByteArray(), Object.class);
			}
			catch (final IOException e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}
		else
		{
			return responseWrapper.getContentAsByteArray();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}

	@Nullable
	private Object getBody(@Nullable final HttpHeaders httpHeaders, @Nullable final String bodyCandidate)
	{
<<<<<<< HEAD
		final boolean isJsonBody = bodyCandidate != null
				&& httpHeaders != null
				&& httpHeaders.getContentType() != null
				&& httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON);

		if (!isJsonBody)
=======
		if (bodyCandidate == null)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return null;
		}

<<<<<<< HEAD
		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper()
					.readValue(bodyCandidate, Object.class);
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
=======
		final MediaType contentType = getContentType(httpHeaders);
		if (contentType == null)
		{
			return null;
		}
		else if (contentType.includes(MediaType.TEXT_PLAIN))
		{
			return bodyCandidate;
		}
		else if (contentType.includes(MediaType.APPLICATION_JSON))
		{
			try
			{
				return JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(bodyCandidate, Object.class);
			}
			catch (final IOException e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}
		else
		{
			return bodyCandidate;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}
}
