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

package de.metas.rest_api.v2.workplace;

import de.metas.Profiles;
import de.metas.rest_api.v2.warehouse.WarehouseService;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceAssignmentCreateRequest;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.qrcode.WorkplaceQRCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/workplace" })
@RestController
@Profile(Profiles.PROFILE_App)
public class WorkplaceRestController
{
	@NonNull private final WorkplaceService workplaceService;
	@NonNull private final WarehouseService warehouseService;

	@GetMapping
	public JsonWorkplaceSettings getStatus()
	{
		return JsonWorkplaceSettings.builder()
				.workplaceRequired(workplaceService.isAnyWorkplaceActive())
				.assignedWorkplace(workplaceService.getWorkplaceByUserId(Env.getLoggedUserId())
						.map(this::toJson)
						.orElse(null))
				.build();
	}

	@PostMapping("/{workplaceId}/assign")
	public JsonWorkplace assignWorkplace(@PathVariable("workplaceId") @NonNull final Integer workplaceIdInt)
	{
		final WorkplaceId workplaceId = WorkplaceId.ofRepoId(workplaceIdInt);

		workplaceService.assignWorkplace(WorkplaceAssignmentCreateRequest.builder()
				.workplaceId(workplaceId)
				.userId(Env.getLoggedUserId())
				.build());

		return getWorkplaceById(workplaceId);
	}

	@PostMapping("/byQRCode")
	public JsonWorkplace getWorkplaceByQRCode(@RequestBody @NonNull final JsonGetWorkplaceByQRCodeRequest request)
	{
		final WorkplaceQRCode qrCode = WorkplaceQRCode.ofGlobalQRCodeJsonString(request.getQrCode());
		return getWorkplaceById(qrCode.getWorkplaceId());
	}

	private JsonWorkplace getWorkplaceById(@NonNull final WorkplaceId workplaceId)
	{
		final Workplace workplace = workplaceService.getById(workplaceId);
		return toJson(workplace);
	}

	private JsonWorkplace toJson(final Workplace workplace)
	{
		return JsonWorkplace.builder()
				.id(workplace.getId())
				.name(workplace.getName())
				.qrCode(WorkplaceQRCode.ofWorkplace(workplace).toGlobalQRCodeJsonString())
				.warehouseName(workplace.getWarehouseId() != null ? warehouseService.getWarehouseName(workplace.getWarehouseId()) : null)
				.isUserAssigned(workplaceService.isUserAssigned(Env.getLoggedUserId(), workplace.getId()))
				.build();
	}
}
