/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.rest_api.v2.audit;

import de.metas.JsonObjectMapperHolder;
import de.metas.Profiles;
import de.metas.audit.apirequest.common.HttpHeadersWrapper;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.audit.apirequest.response.ApiResponseAudit;
import de.metas.util.Check;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.audit.ApiAuditService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/audit" })
@RestController
@RequiredArgsConstructor
@Profile(Profiles.PROFILE_App)
public class ApiAuditController
{
	@NonNull private final ApiAuditService apiAuditService;

	@ApiOperation("Retrieve and mimic the original API response by request audit ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved and mimicked original response"),
			@ApiResponse(code = 404, message = "API request audit record not found"),
			@ApiResponse(code = 500, message = "Internal server error while processing the response")
	})
	@GetMapping("/requests/{requestId}/response")
	public ResponseEntity<Object> mimicOriginalResponse(
			@ApiParam(required = true, value = "The API request audit ID to mimic response for")
			@PathVariable("requestId")
			@NonNull final String requestId)
	{
		try
		{
			final ApiResponseAudit apiResponse = apiAuditService
					.getLatestByRequestId(ApiRequestAuditId.ofRepoId(Integer.parseInt(requestId)))
					.orElse(null);

			if (apiResponse == null)
			{
				return ResponseEntity.notFound().build();
			}

			final HttpStatus httpStatus = parseHttpStatus(apiResponse.getHttpCode());
			final HttpHeaders headers = reconstructHeaders(apiResponse.getHttpHeaders());
			final Object responseBody = reconstructResponseBody(apiResponse.getBody());

			return new ResponseEntity<>(responseBody, headers, httpStatus);
		}
		catch (final Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while mimicking original response: " + e.getMessage());
		}
	}

	@NonNull
	private HttpStatus parseHttpStatus(@Nullable final String httpCode)
	{
		try
		{
			if (Check.isBlank(httpCode))
			{
				throw new AdempiereException("HTTP Code is blank")
						.appendParametersToMessage()
						.setParameter("HttpCode", httpCode);
			}

			final int statusCode = Integer.parseInt(httpCode.trim());
			return HttpStatus.valueOf(statusCode);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Error while parsing HTTP Code", e)
					.appendParametersToMessage()
					.setParameter("HttpCode", httpCode);
		}
	}

	@NonNull
	private HttpHeaders reconstructHeaders(@Nullable final String httpHeadersJson)
	{
		final HttpHeaders headers = new HttpHeaders();

		if (Check.isNotBlank(httpHeadersJson))
		{
			final HttpHeadersWrapper headersWrapper = HttpHeadersWrapper
					.fromJson(httpHeadersJson, JsonObjectMapperHolder.sharedJsonObjectMapper());

			headersWrapper.streamHeaders()
					.forEach(entry -> headers.add(entry.getKey(), entry.getValue()));
		}

		return headers;
	}

	@Nullable
	private Object reconstructResponseBody(@Nullable final String bodyJson)
	{
		if (Check.isEmpty(bodyJson))
		{
			return null;
		}

		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper()
					.readTree(bodyJson);
		}
		catch (final Exception e)
		{
			return bodyJson;
		}
	}
}
