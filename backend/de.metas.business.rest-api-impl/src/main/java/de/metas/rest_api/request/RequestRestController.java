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

package de.metas.rest_api.request;

import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.request.RequestId;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_DEPRECATED + "/request",
		MetasfreshRestAPIConstants.ENDPOINT_API_V1 + "/request",
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/request" })
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class RequestRestController
{
	private static final Logger logger = LogManager.getLogger(RequestRestController.class);

	@NonNull private final RequestRestService requestRestService;

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created or updated request"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
	})
	@PostMapping
	public ResponseEntity<JsonRRequestUpsertResponse> createRequest(@RequestBody @NonNull final JsonRRequestUpsertRequest request)
	{
		try
		{
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(requestRestService.upsert(request));
		}
		catch (final Exception ex)
		{
			logger.error("Create request failed for {}", request, ex);
			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			return ResponseEntity.unprocessableEntity()
					.body(JsonRRequestUpsertResponse.builder()
							.error(JsonErrors.ofThrowable(ex, adLanguage))
							.build());
		}
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully created or updated request"),
			@ApiResponse(responseCode = "400", description = "The provided requestId is not a number"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to get the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The request entity could not be found")
	})
	@GetMapping("/{requestId}")
	public ResponseEntity getById(@PathVariable(name = "requestId") @NonNull final String requestIdStr)
	{
		try
		{
			final RequestId requestId = RequestId.ofRepoId(Integer.parseInt(requestIdStr));

			final JsonRRequest response = requestRestService.getByIdOrNull(requestId);
			return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
		}
		catch (final NumberFormatException ex)
		{
			logger.error("Invalid requestId: {}", requestIdStr);
			return ResponseEntity.badRequest().build();
		}
		catch (final Exception ex)
		{
			logger.error("Get request failed for {}", requestIdStr, ex);
			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			return ResponseEntity.unprocessableEntity()
					.body(JsonErrors.ofThrowable(ex, adLanguage));
		}
	}
}
