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
import de.metas.common.handlingunits.JsonAllowedHUClearanceStatuses;
import de.metas.common.handlingunits.JsonDisposalReasonsList;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.common.handlingunits.JsonSetClearanceStatusRequest;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.mobileui.config.HUManagerProfile;
import de.metas.handlingunits.mobileui.config.HUManagerProfileLayoutSectionList;
import de.metas.handlingunits.mobileui.config.HUManagerProfileRepository;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.rest_api.GetByIdRequest;
import de.metas.handlingunits.rest_api.GetByQRCodeRequest;
import de.metas.handlingunits.rest_api.HandlingUnitsService;
import de.metas.handlingunits.rest_api.JsonBulkMoveHURequest;
import de.metas.handlingunits.rest_api.JsonGetByQRCodeRequest;
import de.metas.handlingunits.rest_api.JsonHULabelPrintingOption;
import de.metas.handlingunits.rest_api.JsonHUQtyChangeRequest;
import de.metas.handlingunits.rest_api.JsonMoveHURequest;
import de.metas.handlingunits.rest_api.JsonPrintHULabelRequest;
import de.metas.handlingunits.rest_api.JsonPrintHULabelResponse;
import de.metas.handlingunits.rest_api.move_hu.BulkMoveHURequest;
import de.metas.handlingunits.rest_api.move_hu.MoveHURequest;
import de.metas.inventory.InventoryCandidateService;
import de.metas.mobile.application.service.MobileApplicationService;
import de.metas.organization.OrgId;
import de.metas.printing.frontend.FrontendPrinter;
import de.metas.printing.frontend.FrontendPrinterData;
import de.metas.scannable_code.ScannedCode;
import de.metas.security.mobile_application.MobileApplicationPermissions;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static de.metas.common.rest_api.v2.APIConstants.ENDPOINT_MATERIAL;
import static de.metas.handlingunits.rest_api.HandlingUnitsRestController.toJsonDisposalReasonsList;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + ENDPOINT_MATERIAL + "/handlingunits/hu-manager")
@RestController
@RequiredArgsConstructor
@Profile(Profiles.PROFILE_App)
public class HUManagerRestController
{
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	@NonNull private final MobileApplicationService mobileApplicationService;
	@NonNull private final HUManagerProfileRepository profileRepository;
	@NonNull private final HandlingUnitsService handlingUnitsService;
	@NonNull private final InventoryCandidateService inventoryCandidateService;

	private void assertApplicationAccess()
	{
		final MobileApplicationPermissions permissions = Env.getUserRolePermissions().getMobileApplicationPermissions();
		mobileApplicationService.assertAccess(HUManagerMobileApplication.APPLICATION_ID, permissions);
	}

	private void assertActionAccess(@NonNull final HUManagerAction action)
	{
		final MobileApplicationPermissions permissions = Env.getUserRolePermissions().getMobileApplicationPermissions();
		mobileApplicationService.assertActionAccess(HUManagerMobileApplication.APPLICATION_ID, action.getInternalName(), permissions);
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

	@GetMapping("/allowedClearanceStatuses")
	public ResponseEntity<JsonAllowedHUClearanceStatuses> getAllowedClearanceStatuses(@RequestParam("huId") final int huId)
	{
		assertActionAccess(HUManagerAction.SetClearanceStatus);

		return ResponseEntity.ok().body(handlingUnitsService.getAllowedClearanceStatusesForHUId(HuId.ofRepoId(huId)));
	}

	@PutMapping("/clearance")
	public ResponseEntity<?> setHUClearanceStatus(@RequestBody @NonNull final JsonSetClearanceStatusRequest request)
	{
		assertActionAccess(HUManagerAction.SetClearanceStatus);

		handlingUnitsService.setClearanceStatus(request);
		return ResponseEntity.ok().body(null);
	}

	@GetMapping("/disposalReasons")
	public JsonDisposalReasonsList getDisposalReasons()
	{
		assertActionAccess(HUManagerAction.Dispose);

		final String adLanguage = Env.getADLanguageOrBaseLanguage();
		return toJsonDisposalReasonsList(inventoryCandidateService.getDisposalReasons(), adLanguage);
	}

	@PostMapping("/dispose")
	public void disposeHU(
			@RequestParam("huId") final int huRepoId,
			@RequestParam("reasonCode") final String reasonCodeStr)
	{
		assertActionAccess(HUManagerAction.Dispose);

		final HuId huId = HuId.ofRepoId(huRepoId);
		final QtyRejectedReasonCode reasonCode = QtyRejectedReasonCode.ofCode(reasonCodeStr);
		inventoryCandidateService.createDisposeCandidates(huId, reasonCode);
	}

	@PostMapping("/changeQty")
	public ResponseEntity<JsonGetSingleHUResponse> changeHUQty(@RequestBody @NonNull final JsonHUQtyChangeRequest request)
	{
		assertActionAccess(HUManagerAction.ChangeQty);

		final HuId huId = handlingUnitsService.updateQty(request);

		return handlingUnitsService.getByIdSupplier(
				() -> GetByIdRequest.builder()
						.huId(huId)
						.expectedQRCode(HUQRCode.fromNullable(request.getHuQRCode()))
						.build()
		);
	}

	@PostMapping("/move")
	public void moveHU(@RequestBody @NonNull final JsonMoveHURequest request)
	{
		assertActionAccess(HUManagerAction.Move);

		handlingUnitsService.move(MoveHURequest.builder()
				.huId(request.getHuId())
				.huQRCode(HUQRCode.fromNullable(request.getHuQRCode()))
				.numberOfTUs(request.getNumberOfTUs())
				.targetQRCode(request.getTargetQRCode())
				.build());
	}

	@PostMapping("/bulk/move")
	public void bulkMove(@RequestBody @NonNull final JsonBulkMoveHURequest request)
	{
		assertActionAccess(HUManagerAction.BulkActions);

		handlingUnitsService.bulkMove(
				BulkMoveHURequest.builder()
						.huQrCodes(request.getHuQRCodes().stream()
								.map(HUQRCode::fromGlobalQRCodeJsonString)
								.collect(ImmutableList.toImmutableList()))
						.targetQRCode(ScannedCode.ofString(request.getTargetQRCode()))
						.build()
		);
	}

	@PostMapping("/huLabels/print")
	public JsonPrintHULabelResponse printHULabels(@RequestBody @NonNull final JsonPrintHULabelRequest request)
	{
		assertActionAccess(HUManagerAction.PrintLabels);

		try (final FrontendPrinter frontendPrinter = FrontendPrinter.start())
		{
			handlingUnitsService.printHULabels(request);

			final FrontendPrinterData printData = frontendPrinter.getDataAndClear();
			return JsonPrintHULabelResponse.builder()
					.printData(JsonPrintHULabelResponse.JsonPrintDataItem.of(printData))
					.build();
		}
	}

	@GetMapping("/huLabels/printingOptions")
	public List<JsonHULabelPrintingOption> getPrintingOptions()
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();
		return handlingUnitsService.getLabelPrintingOptions(adLanguage);
	}
}
