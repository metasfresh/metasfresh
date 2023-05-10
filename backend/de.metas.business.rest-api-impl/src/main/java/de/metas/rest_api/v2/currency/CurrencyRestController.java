/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.rest_api.v2.currency;

import de.metas.common.rest_api.v2.conversionRate.JsonConversionRateResponse;
import de.metas.common.rest_api.v2.conversionRate.JsonCurrencyRateCreateRequest;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/currency" })
@RestController
public class CurrencyRestController
{
	@NonNull
	private final ConversionRateService conversionRateService;

	public CurrencyRestController(@NonNull final ConversionRateService conversionRateService)
	{
		this.conversionRateService = conversionRateService;
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Conversion rate successfully created"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to invoke the endpoint"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request could not be processed")
	})
	@PostMapping("/rates")
	public ResponseEntity<JsonConversionRateResponse> createConversionRate(@RequestBody @NonNull final JsonCurrencyRateCreateRequest jsonCurrencyRateCreateRequest)
	{
		return ResponseEntity.ok(conversionRateService.createConversionRate(jsonCurrencyRateCreateRequest));
	}
}
