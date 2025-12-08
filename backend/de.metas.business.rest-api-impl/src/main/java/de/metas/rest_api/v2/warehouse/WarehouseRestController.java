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

package de.metas.rest_api.v2.warehouse;

import de.metas.Profiles;
import de.metas.common.rest_api.v2.JsonResponseUpsert;
import de.metas.common.rest_api.v2.warehouse.JsonOutOfStockNoticeRequest;
import de.metas.common.rest_api.v2.warehouse.JsonOutOfStockResponse;
import de.metas.common.rest_api.v2.warehouse.JsonRequestWarehouseUpsert;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.rest_api.v2.warehouse.json.JsonResolveLocatorResponse;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.Loggables;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

import static de.metas.common.product.v2.request.constants.SwaggerDocConstants.ORG_CODE_PARAMETER_DOC;
import static de.metas.common.rest_api.v2.APIConstants.ENDPOINT_MATERIAL;

@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + ENDPOINT_MATERIAL + "/warehouses" })
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class WarehouseRestController
{
	@NonNull private final WarehouseService warehouseService;
	@NonNull private final WarehouseRestService warehouseRestService;

	private @NotNull String getAdLanguage() {return Env.getADLanguageOrBaseLanguage();}

	@PutMapping("/{warehouseId}/outOfStockNotice")
	public ResponseEntity<?> createShippingPackages(
			@PathVariable("warehouseId") @NonNull final String warehouseId,
			@RequestBody final JsonOutOfStockNoticeRequest request)
	{
		try
		{
			final JsonOutOfStockResponse response = warehouseService.handleOutOfStockRequest(warehouseId, request);
			return ResponseEntity.ok(response);
		}
		catch (final Exception ex)
		{
			Loggables.addLog(ex.getLocalizedMessage(), ex);

			return ResponseEntity.badRequest().body(JsonErrors.ofThrowable(ex, getAdLanguage()));
		}
	}

	@Operation(summary = "Create or update warehouses.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully created or updated warehouse(s)"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
	})
	@PutMapping("{orgCode}")
	public ResponseEntity<JsonResponseUpsert> upsertWarehouses(
			@Parameter(required = true, description = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") @Nullable final String orgCode, // may be null if called from other metasfresh-code
			@RequestBody @NonNull final JsonRequestWarehouseUpsert request)
	{
		final JsonResponseUpsert responseUpsert = warehouseRestService.upsertWarehouses(orgCode, request);
		return ResponseEntity.ok(responseUpsert);
	}

	@GetMapping("/resolveLocator")
	@NonNull
	public JsonResolveLocatorResponse resolveLocatorScannedCode(
			@RequestParam("scannedBarcode") final String scannedBarcode)
	{
		try
		{
			return JsonResolveLocatorResponse.ok(warehouseRestService.resolveLocatorScannedCode(ScannedCode.ofString(scannedBarcode)));
		}
		catch (final Exception ex)
		{
			return JsonResolveLocatorResponse.error(ex, getAdLanguage());
		}
	}
}
