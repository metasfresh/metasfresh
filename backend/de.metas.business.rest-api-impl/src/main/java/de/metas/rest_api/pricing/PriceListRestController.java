/*
 * #%L
 * de.metas.business.rest-api
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

package de.metas.rest_api.pricing;

import de.metas.Profiles;
import de.metas.common.bpartner.response.JsonResponseUpsert;
import de.metas.common.bpartner.response.JsonResponseUpsertItem;
import de.metas.common.rest_api.SyncAdvise;
import de.metas.common.rest_api.pricing.pricelist.JsonRequestPriceListVersionUpsert;
import de.metas.common.rest_api.pricing.productprice.JsonRequestProductPriceUpsert;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_DEPRECATED + "/prices",
		MetasfreshRestAPIConstants.ENDPOINT_API_V1 + "/prices",
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/prices" })
@RestController
@Profile(Profiles.PROFILE_App)
public class PriceListRestController
{
	private final PriceListRestService priceListRestService;
	private final ProductPriceRestService productPriceRestService;

	public PriceListRestController(@NonNull final PriceListRestService priceListRestService, final ProductPriceRestService productPriceRestService)
	{
		this.priceListRestService = priceListRestService;
		this.productPriceRestService = productPriceRestService;
	}

	@ApiOperation("Create or update price list version by price list id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully processed the request"),
			@ApiResponse(code = 401, message = "You are not authorized to consume this resource"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request body could not be processed")
	})
	@PutMapping(path = "/priceListVersions", consumes = "application/json", produces = "application/json")
	public ResponseEntity<JsonResponseUpsertItem> putPriceListVersions(@RequestBody @NonNull final JsonRequestPriceListVersionUpsert request)
	{
		final JsonResponseUpsertItem jsonResponseUpsertItem = priceListRestService.putPriceListVersions(request);
		return ResponseEntity.ok(jsonResponseUpsertItem);
	}

	@ApiOperation("Create or update product price by price list version identifier")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully processed the request"),
			@ApiResponse(code = 401, message = "You are not authorized to consume this resource"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request body could not be processed")
	})
	@PutMapping("/priceListVersions/{priceListVersionIdentifier}/productPrices")
	public ResponseEntity<JsonResponseUpsert> putProductPriceByPriceListVersionIdentifier(
			@ApiParam(required = true, value = "String")
			@PathVariable("priceListVersionIdentifier") //
			@NonNull final String priceListVersionIdentifier,

			@RequestBody @NonNull final JsonRequestProductPriceUpsert request)
	{
		final SyncAdvise syncAdvise = request.getSyncAdvise();
		final List<JsonResponseUpsertItem> responseList =
				request.getRequestItems()
						.stream()
						.map(reqItem -> productPriceRestService.putProductPriceByPriceListVersionIdentifier(priceListVersionIdentifier, reqItem, syncAdvise))
						.collect(Collectors.toList());

		return ResponseEntity.ok().body(JsonResponseUpsert.builder().responseItems(responseList).build());
	}
}


