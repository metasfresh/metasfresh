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
import de.metas.handlingunits.mobileui.config.HUManagerProfile;
import de.metas.handlingunits.mobileui.config.HUManagerProfileLayoutSectionList;
import de.metas.handlingunits.mobileui.config.HUManagerProfileRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.rest_api.GetByQRCodeRequest;
import de.metas.handlingunits.rest_api.HandlingUnitsService;
import de.metas.handlingunits.rest_api.JsonGetByQRCodeRequest;
import de.metas.mobile.application.service.MobileApplicationService;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.util.Env;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.metas.common.rest_api.v2.APIConstants.ENDPOINT_MATERIAL;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + ENDPOINT_MATERIAL + "/handlingunits/hu-manager")
@RestController
@RequiredArgsConstructor
@Profile(Profiles.PROFILE_App)
public class HUManagerRestController
{
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	@NonNull private final MobileApplicationService mobileApplicationService;
	@NonNull private final HUManagerProfileRepository profileRepository;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final HandlingUnitsService handlingUnitsService;

	private void assertApplicationAccess()
	{
		mobileApplicationService.assertAccess(HUManagerMobileApplication.APPLICATION_ID, Env.getUserRolePermissions());
	}

	@PostMapping("/byQRCode")
	public ResponseEntity<JsonGetSingleHUResponse> retrieveHUManagerConfig(@RequestBody @NonNull final JsonGetByQRCodeRequest request)
	{
		assertApplicationAccess();

		return handlingUnitsService.getByQRCode(
				GetByQRCodeRequest.builderFrom(request)
						.orderedAttributeCodes(getDisplayedAttributeCodes())
						.layoutSections(getLayoutSections())
						.build()
		);
	}

	private ImmutableList<AttributeCode> getDisplayedAttributeCodes()
	{
		final HUManagerProfile profile = getProfile();
		final ImmutableList<AttributeId> displayedAttributeIdsInOrder = profile.getDisplayedAttributeIdsInOrder();

		return displayedAttributeIdsInOrder != null && !displayedAttributeIdsInOrder.isEmpty()
				? attributeDAO.getOrderedAttributeCodesByIds(displayedAttributeIdsInOrder)
				: ImmutableList.of();
	}

	private @NonNull HUManagerProfileLayoutSectionList getLayoutSections()
	{
		return getProfile().getLayoutSections();
	}

	private @NotNull HUManagerProfile getProfile()
	{
		final OrgId orgId = Env.getOrgId();
		return profileRepository.getProfile(orgId);
	}
}
