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

import de.metas.JsonObjectMapperHolder;
import de.metas.audit.apirequest.config.ApiAuditConfig;
import de.metas.audit.apirequest.request.ApiRequestAudit;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonApiResponse;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.util.Loggables;
import de.metas.util.web.audit.dto.ApiResponse;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.util.Env;
import org.springframework.http.HttpHeaders;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static de.metas.util.web.audit.ApiAuditService.API_RESPONSE_HEADER_REQUEST_AUDIT_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@UtilityClass
public class ResponseHandler
{
	public void writeHttpResponse(
			@NonNull final ApiResponse apiResponse,
			@NonNull final ApiAuditConfig apiAuditConfig,
			@NonNull final ApiRequestAudit apiRequestAudit,
			@NonNull final HttpServletResponse httpServletResponse) throws IOException
	{
		if (!resetServletResponse(httpServletResponse))
		{
			//dev-note: it basically means the response was already written and committed
			return;
		}

		forwardSomeResponseHttpHeaders(httpServletResponse, apiResponse.getHttpHeaders());

		addCustomHeaders(httpServletResponse, apiAuditConfig, apiRequestAudit);

		final Object responseBody = wrapBodyIfNeeded(apiAuditConfig, apiRequestAudit, apiResponse.getBody());

		writeResponse(httpServletResponse, responseBody, apiResponse.getStatusCode());
	}

	public void writeErrorResponse(
			@NonNull final Throwable throwable,
			@NonNull final HttpServletResponse httpServletResponse,
			@Nullable final ApiRequestAudit apiRequestAudit,
			@Nullable final ApiAuditConfig apiAuditConfig) throws IOException
	{
		if (!resetServletResponse(httpServletResponse))
		{
			//dev-note: it basically means the response was already written and committed
			return;
		}

		final String language = Env.getADLanguageOrBaseLanguage();
		final JsonErrorItem error = JsonErrors.ofThrowable(throwable, language);

		addCustomHeaders(httpServletResponse, apiAuditConfig, apiRequestAudit);

		final Object responseBody = wrapBodyIfNeeded(apiAuditConfig, apiRequestAudit, error);

		writeResponse(httpServletResponse, responseBody, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	private void addCustomHeaders(
			@NonNull final HttpServletResponse httpServletResponse,
			@Nullable final ApiAuditConfig apiAuditConfig,
			@Nullable final ApiRequestAudit apiRequestAudit)
	{
		if (apiAuditConfig == null || apiRequestAudit == null)
		{
			return;
		}

		if (!apiAuditConfig.isWrapApiResponse())
		{
			httpServletResponse.addHeader(API_RESPONSE_HEADER_REQUEST_AUDIT_ID, String.valueOf(apiRequestAudit.getIdNotNull().getRepoId()));
		}
	}

	private void writeResponse(
			@NonNull final HttpServletResponse httpServletResponse,
			@Nullable final Object apiResponse,
			final int statusCode) throws IOException
	{
		httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
		httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
		httpServletResponse.setStatus(statusCode);

		if (apiResponse != null)
		{
			final String stringToForward = JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(apiResponse);
			httpServletResponse.getWriter().write(stringToForward);
		}

		httpServletResponse.flushBuffer();
	}

	private void forwardSomeResponseHttpHeaders(@NonNull final HttpServletResponse servletResponse, @Nullable final HttpHeaders httpHeaders)
	{
		if (httpHeaders == null)
		{
			return;
		}

		httpHeaders.keySet()
				.stream()
				.filter(key -> !key.equals(HttpHeaders.CONNECTION))
				.filter(key -> !key.equals(HttpHeaders.CONTENT_LENGTH))
				.filter(key -> !key.equals(HttpHeaders.CONTENT_TYPE))
				.filter(key -> !key.equals(HttpHeaders.TRANSFER_ENCODING)) // if we forwarded this without knowing what we do, we would annoy a possible nginx reverse proxy
				.forEach(key -> {
					final List<String> values = httpHeaders.get(key);
					if (values != null)
					{
						values.forEach(value -> servletResponse.addHeader(key, value));
					}
				});
	}

	@Nullable
	private Object wrapBodyIfNeeded(
			@Nullable final ApiAuditConfig apiAuditConfig,
			@Nullable final ApiRequestAudit apiRequestAudit,
			@Nullable final Object unwrappedResponse)
	{
		if (apiAuditConfig == null || apiRequestAudit == null || !apiAuditConfig.isWrapApiResponse())
		{
			return unwrappedResponse;
		}

		return JsonApiResponse.builder()
				.requestId(JsonMetasfreshId.of(apiRequestAudit.getIdNotNull().getRepoId()))
				.endpointResponse(unwrappedResponse)
				.build();
	}

	private boolean resetServletResponse(@NonNull final HttpServletResponse response)
	{
		if (!response.isCommitted())
		{
			response.reset();
			return true;
		}

		Loggables.addLog("HttpServletResponse has already been committed -> cannot be altered! response status = {}", response.getStatus());
		return false;
	}
}
