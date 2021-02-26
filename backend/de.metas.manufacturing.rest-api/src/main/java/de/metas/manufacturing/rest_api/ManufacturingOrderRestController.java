package de.metas.manufacturing.rest_api;

import java.time.Instant;

import javax.annotation.Nullable;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.QueryLimit;
import org.compiere.util.Env;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.common.manufacturing.JsonRequestManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonRequestSetOrdersExportStatusBulk;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersBulk;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersReport;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;

/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

@RequestMapping(ManufacturingOrderRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
public class ManufacturingOrderRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/manufacturing/orders";

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
}
