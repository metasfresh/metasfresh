/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.rest_api;

import de.metas.Profiles;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.organization.OrgId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static de.metas.common.rest_api.v2.APIConstants.ENDPOINT_MATERIAL;

@RequestMapping(value = { HandlingUnitsManagerRestController.HU_MANAGER_REST_CONTROLLER_PATH })
@RestController
@RequiredArgsConstructor
@Profile(Profiles.PROFILE_App)
public class HandlingUnitsManagerRestController
{
	public static final String HU_MANAGER_REST_CONTROLLER_PATH = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + ENDPOINT_MATERIAL + "/handlingunits/hu-manager";

	private final HandlingUnitsManagerRestService handlingUnitsManagerRestService;
	private final HUQRCodesService huQRCodesService;
	private final HandlingUnitsService handlingUnitsService;

	@PostMapping("/byQRCode")
	public ResponseEntity<JsonGetSingleHUResponse> retrieveHUManagerConfig(@RequestBody @NonNull final JsonGetByQRCodeRequest request)
	{
		final OrgId orgId = Env.getOrgId();

		return handlingUnitsService.getByIdSupplier(() -> getHuId(request.getQrCode()),
													request.isIncludeAllowedClearanceStatuses(),
													jsonHU -> handlingUnitsManagerRestService.getHUTailoredByHUManager(jsonHU, orgId));
	}

	@NonNull
	private HuId getHuId(@NonNull final String qrCode)
	{
		final GlobalQRCode globalQRCode = GlobalQRCode.parse(qrCode).orNullIfError();

		return Optional.ofNullable(globalQRCode)
				.map(HUQRCode::fromGlobalQRCode)
				.flatMap(huQRCodesService::getHuIdByQRCodeIfExists)
				.orElseGet(() -> HuId.ofHUValue(qrCode));
	}
}
