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
import de.metas.RestUtils;
import de.metas.organization.OrgId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

import static de.metas.common.product.v2.request.constants.SwaggerDocConstants.ORG_CODE_PARAMETER_DOC;
import static de.metas.common.rest_api.v2.APIConstants.ENDPOINT_MATERIAL;

@RequestMapping(value = { HandlingUnitsManagerRestController.HU_MANAGER_REST_CONTROLLER_PATH })
@RestController
@Profile(Profiles.PROFILE_App)
public class HandlingUnitsManagerRestController
{
	public static final String HU_MANAGER_REST_CONTROLLER_PATH = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + ENDPOINT_MATERIAL + "/handlingunits/manager-config";

	private final HandlingUnitsManagerRestService handlingUnitsManagerRestService;

	public HandlingUnitsManagerRestController(@NonNull final HandlingUnitsManagerRestService handlingUnitsManagerRestService)
	{
		this.handlingUnitsManagerRestService = handlingUnitsManagerRestService;
	}

	@GetMapping("{orgCode}")
	public ResponseEntity<JsonResponseHUManager> retrieveHUManagerConfig(
			@ApiParam(required = true, value = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode) // may be null if called from other metasfresh-code
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		return ResponseEntity.ok(handlingUnitsManagerRestService.getHUManagerConfig(orgId));
	}
}
