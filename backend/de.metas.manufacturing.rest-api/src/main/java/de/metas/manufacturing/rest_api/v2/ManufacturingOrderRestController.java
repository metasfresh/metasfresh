/*
 * #%L
 * de.metas.manufacturing.rest-api
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

package de.metas.manufacturing.rest_api.v2;

import de.metas.Profiles;
import de.metas.common.manufacturing.v2.JsonRequestManufacturingOrdersReport;
import de.metas.common.manufacturing.v2.JsonRequestSetOrdersExportStatusBulk;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrdersBulk;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrdersReport;
import de.metas.common.util.time.SystemTime;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.compiere.util.Env;
import org.eevolution.api.PPOrderId;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.time.Instant;

@RequestMapping(value = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/manufacturing/orders")
@RestController
@Profile(Profiles.PROFILE_App)
public class ManufacturingOrderRestController
{
	private final ManufacturingOrderAPIService manufacturingOrderAPIService;

	public ManufacturingOrderRestController(
			@NonNull final ManufacturingOrderAPIService manufacturingOrderAPIService)
	{
		this.manufacturingOrderAPIService = manufacturingOrderAPIService;
	}

	@GetMapping
	public ResponseEntity<JsonResponseManufacturingOrdersBulk> exportOrders(
			@ApiParam("Max number of items to be returned in one request.") //
			@RequestParam(name = "limit", required = false, defaultValue = "500") //
			@Nullable final Integer limit)
	{
		final Instant canBeExportedFrom = SystemTime.asInstant();
		final QueryLimit limitEffective = QueryLimit.ofNullableOrNoLimit(limit).ifNoLimitUse(500);
		final String adLanguage = Env.getADLanguageOrBaseLanguage();
		final JsonResponseManufacturingOrdersBulk result = manufacturingOrderAPIService.exportOrders(
				canBeExportedFrom,
				limitEffective,
				adLanguage);

		return ResponseEntity.ok(result);
	}

	@PostMapping("/exportStatus")
	public ResponseEntity<String> setExportStatus(@RequestBody @NonNull final JsonRequestSetOrdersExportStatusBulk request)
	{
		try (final MDC.MDCCloseable ignore = MDC.putCloseable("TransactionIdAPI", request.getTransactionKey()))
		{
			manufacturingOrderAPIService.setExportStatus(request);
			return ResponseEntity.accepted().body("Manufacturing orders updated");
		}
	}

	@PostMapping("/report")
	public ResponseEntity<JsonResponseManufacturingOrdersReport> report(@RequestBody @NonNull final JsonRequestManufacturingOrdersReport request)
	{
		final JsonResponseManufacturingOrdersReport response = manufacturingOrderAPIService.report(request);
		return response.isOK()
				? ResponseEntity.ok(response)
				: ResponseEntity.unprocessableEntity().body(response);
	}

	@GetMapping("/{ppOrderMetasfreshId}")
	public ResponseEntity<?> getManufacturingOrderByMetasfreshId(@PathVariable("ppOrderMetasfreshId") final int ppOrderMetasfreshId)
	{
		try
		{
			final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrderMetasfreshId);

			final JsonResponseManufacturingOrder response = manufacturingOrderAPIService.retrievePPOrder(ppOrderId);
			return ResponseEntity.ok(response);
		}
		catch (final Exception ex)
		{
			return ResponseEntity
					.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(ex);
		}
	}
}
