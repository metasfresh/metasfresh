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
import de.metas.common.rest_api.v2.warehouse.JsonOutOfStockNoticeRequest;
import de.metas.common.rest_api.v2.warehouse.JsonOutOfStockResponse;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.util.Loggables;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/warehouses" })
@RestController
@Profile(Profiles.PROFILE_App)
public class WarehouseRestController
{
	@NonNull
	private final WarehouseService warehouseService;

	public WarehouseRestController(final @NonNull WarehouseService warehouseService)
	{
		this.warehouseService = warehouseService;
	}

	@PutMapping("/{warehouseId}/outOfStockNotice")
	public ResponseEntity<?> createShippingPackages(
			@PathVariable("warehouseId") @NonNull final String warehouseId,
			@RequestBody final JsonOutOfStockNoticeRequest request)
	{
		try
		{
			final JsonOutOfStockResponse response = warehouseService.handleOutOfStockRequest(warehouseId, request);

			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		catch (final Exception ex)
		{
			Loggables.addLog(ex.getLocalizedMessage(), ex);

			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			return ResponseEntity.badRequest()
					.body(JsonErrors.ofThrowable(ex, adLanguage));
		}
	}
}
