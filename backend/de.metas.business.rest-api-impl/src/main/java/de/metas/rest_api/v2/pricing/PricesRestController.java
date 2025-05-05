/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.pricing;

import de.metas.Profiles;
import de.metas.common.pricing.v2.pricelist.request.JsonRequestPriceListVersionUpsert;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceQuery;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceUpsert;
import de.metas.common.pricing.v2.productprice.JsonResponseProductPriceQuery;
import de.metas.common.rest_api.v2.JsonResponseUpsert;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

import static de.metas.common.pricing.v2.constants.SwaggerDocConstants.PRICE_LIST_IDENTIFIER;
import static de.metas.common.pricing.v2.constants.SwaggerDocConstants.PRICE_LIST_VERSION_IDENTIFIER;

@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/prices" })
@RestController
@Profile(Profiles.PROFILE_App)
public class PricesRestController
{
	private final PriceListRestService priceListRestService;
	private final ProductPriceRestService productPriceRestService;

	public PricesRestController(@NonNull final PriceListRestService priceListRestService, final ProductPriceRestService productPriceRestService)
	{
		this.priceListRestService = priceListRestService;
		this.productPriceRestService = productPriceRestService;
	}

	@Operation(summary = "Create or update price list versions")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully processed the request"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to consume this resource"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request body could not be processed")
	})
	@PutMapping(path = "/priceListVersions", consumes = "application/json", produces = "application/json")
	public ResponseEntity<JsonResponseUpsert> putPriceListVersions(@RequestBody @NonNull final JsonRequestPriceListVersionUpsert request)
	{
		final JsonResponseUpsert responseUpsert = priceListRestService.upsertPriceListVersion(request);

		return ResponseEntity.ok(responseUpsert);
	}

	@Operation(summary = "Create or update product price by price list version identifier")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully processed the request"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to consume this resource"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request body could not be processed")
	})
	@PutMapping("/priceListVersions/{priceListVersionIdentifier}/productPrices")
	public ResponseEntity<JsonResponseUpsert> putProductPriceByPriceListVersionIdentifier(
			@Parameter(required = true, description = PRICE_LIST_VERSION_IDENTIFIER)
			@PathVariable("priceListVersionIdentifier") //
			@NonNull final String priceListVersionIdentifier,

			@RequestBody @NonNull final JsonRequestProductPriceUpsert request)
	{
		final JsonResponseUpsert responseUpsert = productPriceRestService.upsertProductPrices(priceListVersionIdentifier, request);

		return ResponseEntity.ok().body(responseUpsert);
	}

	@Operation(summary = "Create or update product price to the latest price list version of the given price list identifier")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully processed the request"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to consume this resource"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request body could not be processed")
	})
	@PutMapping("/priceList/{priceListIdentifier}/productPrices")
	public ResponseEntity<JsonResponseUpsert> putProductPriceByPriceListIdentifier(
			@Parameter(required = true, description = PRICE_LIST_IDENTIFIER)
			@PathVariable("priceListIdentifier")
			@NonNull final String priceListIdentifier,

			@RequestBody @NonNull final JsonRequestProductPriceUpsert request)
	{
		final JsonResponseUpsert responseUpsert = productPriceRestService.upsertProductPricesForPriceList(priceListIdentifier, request);

		return ResponseEntity.ok().body(responseUpsert);
	}

	@Operation(summary = "Search product prices by a given `JsonProductPriceSearchRequest`")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully processed the request"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to consume this resource"),
			@ApiResponse(responseCode = "403", description = "Accessing a related resource is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request body could not be processed")
	})
	@PostMapping("{orgCode}/product/search")
	public ResponseEntity<?> productPriceSearch(
			@PathVariable("orgCode") @Nullable final String orgCode,
			@RequestBody @NonNull final JsonRequestProductPriceQuery request)
	{
		try
		{
			final JsonResponseProductPriceQuery result = productPriceRestService.productPriceSearch(request, orgCode);

			return ResponseEntity.ok(result);
		}
		catch (final Exception ex)
		{
			return ResponseEntity
					.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(ex.getMessage());
		}
	}
}


