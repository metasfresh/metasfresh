/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2022 metas GmbH
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
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.inventory.InventoryCandidateService;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RequestMapping(value = { DeprecatedHandlingUnitsRestController.HU_REST_CONTROLLER_PATH })
@RestController
@Profile(Profiles.PROFILE_App)
public class DeprecatedHandlingUnitsRestController extends HandlingUnitsRestController
{
	public static final String HU_REST_CONTROLLER_PATH = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/hu";

	public DeprecatedHandlingUnitsRestController(
			final @NonNull InventoryCandidateService inventoryCandidateService,
			final @NonNull HandlingUnitsService handlingUnitsService,
			@NonNull final HUQRCodesService huQRCodesService)
	{
		super(inventoryCandidateService, handlingUnitsService, huQRCodesService);
	}
}
