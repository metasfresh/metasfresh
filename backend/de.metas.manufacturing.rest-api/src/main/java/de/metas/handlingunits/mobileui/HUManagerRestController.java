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

package de.metas.handlingunits.mobileui;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.mobileui.config.HUManagerProfileRepository;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.rest_api.GetByIdRequest;
import de.metas.handlingunits.rest_api.HandlingUnitsService;
import de.metas.handlingunits.rest_api.JsonGetByQRCodeRequest;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static de.metas.common.rest_api.v2.APIConstants.ENDPOINT_MATERIAL;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + ENDPOINT_MATERIAL + "/handlingunits/hu-manager")
@RestController
@RequiredArgsConstructor
@Profile(Profiles.PROFILE_App)
public class HUManagerRestController
{
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final HUManagerProfileRepository profileRepository;
	private final HUQRCodesService huQRCodesService;
	private final HandlingUnitsService handlingUnitsService;

	@PostMapping("/byQRCode")
	public ResponseEntity<JsonGetSingleHUResponse> retrieveHUManagerConfig(@RequestBody @NonNull final JsonGetByQRCodeRequest request)
	{
		return handlingUnitsService.getByIdSupplier(
				() -> GetByIdRequest.builder()
						.huId(getHuId(request.getQrCode()))
						.includeAllowedClearanceStatuses(request.isIncludeAllowedClearanceStatuses())
						.orderedAttributeCodes(getDisplayedAttributeCodes())
						.build()
		);
	}

	private ImmutableList<AttributeCode> getDisplayedAttributeCodes()
	{
		final OrgId orgId = Env.getOrgId();
		final ImmutableList<AttributeId> displayedAttributeIdsInOrder = profileRepository.getProfile(orgId).getDisplayedAttributeIdsInOrder();

		return displayedAttributeIdsInOrder != null && !displayedAttributeIdsInOrder.isEmpty()
				? attributeDAO.getOrderedAttributeCodesByIds(displayedAttributeIdsInOrder)
				: ImmutableList.of();
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
