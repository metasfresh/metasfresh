/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.rest_api;

import de.metas.Profiles;
import de.metas.common.handlingunits.JsonGRAICodesRequest;
import de.metas.common.handlingunits.JsonGRAICodesResponse;
import de.metas.common.handlingunits.JsonHU;
import de.metas.common.handlingunits.JsonHUList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobQtyAvailable;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.rest_api.HandlingUnitsService;
import de.metas.handlingunits.rest_api.JsonGetByQRCodeRequest;
import de.metas.mobile.application.service.MobileApplicationService;
import de.metas.picking.rest_api.json.JsonGetHUInfoByScannedCodeRequest;
import de.metas.picking.rest_api.json.JsonGetNextEligibleLineRequest;
import de.metas.picking.rest_api.json.JsonGetNextEligibleLineResponse;
import de.metas.picking.rest_api.json.JsonHUInfo;
import de.metas.picking.rest_api.json.JsonLUPickingTarget;
import de.metas.picking.rest_api.json.JsonPickingEventsList;
import de.metas.picking.rest_api.json.JsonPickingJobAvailableTargets;
import de.metas.picking.rest_api.json.JsonPickingJobQtyAvailable;
import de.metas.picking.rest_api.json.JsonPickingLineCloseRequest;
import de.metas.picking.rest_api.json.JsonPickingLineOpenRequest;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.rest_api.json.JsonTUPickingTarget;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.scannable_code.ScannedCode;
import de.metas.security.mobile_application.MobileApplicationPermissions;
import de.metas.user.UserId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.workflow.rest_api.controller.v2.WorkflowRestController;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/picking")
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class PickingRestController
{
	@NonNull private final MobileApplicationService mobileApplicationService;
	@NonNull private final PickingMobileApplication pickingMobileApplication;
	@NonNull private final WorkflowRestController workflowRestController;
	@NonNull private final HandlingUnitsService handlingUnitsService;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final PickingJobService pickingJobService;

	private void assertApplicationAccess()
	{
		final MobileApplicationPermissions permissions = Env.getUserRolePermissions().getMobileApplicationPermissions();
		mobileApplicationService.assertAccess(pickingMobileApplication.getApplicationId(), permissions);
	}

	private static @NotNull UserId getLoggedUserId() {return Env.getLoggedUserId();}

	@GetMapping("/job/{wfProcessId}/target/available")
	public JsonPickingJobAvailableTargets getAvailableTargets(
			@PathVariable("wfProcessId") final String wfProcessIdStr,
			@RequestParam(value = "lineId", required = false) @Nullable final String lineIdStr)
	{
		assertApplicationAccess();

		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final PickingJobLineId lineId = PickingJobLineId.ofNullableString(lineIdStr);

		return pickingMobileApplication.getAvailableTargets(wfProcessId, lineId, getLoggedUserId());
	}

	@PostMapping("/job/{wfProcessId}/target")
	public JsonWFProcess setLUPickingTarget(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@RequestParam(value = "lineId", required = false) @Nullable final String lineIdStr,
			@RequestBody(required = false) @Nullable final JsonLUPickingTarget jsonTarget)
	{
		assertApplicationAccess();

		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final PickingJobLineId lineId = PickingJobLineId.ofNullableString(lineIdStr);
		final LUPickingTarget target = jsonTarget != null ? jsonTarget.unbox() : null;
		final WFProcess wfProcess = pickingMobileApplication.setLUPickingTarget(wfProcessId, lineId, target, getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/job/{wfProcessId}/target/tu")
	public JsonWFProcess setTUPickingTarget(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@RequestParam(value = "lineId", required = false) @Nullable final String lineIdStr,
			@RequestBody(required = false) @Nullable final JsonTUPickingTarget jsonTarget)
	{
		assertApplicationAccess();

		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final PickingJobLineId lineId = PickingJobLineId.ofNullableString(lineIdStr);

		final WFProcess wfProcess;
		if (jsonTarget != null && jsonTarget.getGrai() != null)
		{
			// GRAI-scan flow: delegate to the mobile application to resolve + create the TU.
			wfProcess = pickingMobileApplication.setTUPickingTargetFromGRAI(wfProcessId, lineId, jsonTarget.getGrai(), getLoggedUserId());
		}
		else
		{
			final TUPickingTarget target = jsonTarget != null ? jsonTarget.unbox() : null;
			if (target != null && !target.isNewTU())
			{
				throw new AdempiereException("Only New-TU targets are allowed");
			}
			wfProcess = pickingMobileApplication.setTUPickingTarget(wfProcessId, lineId, target, getLoggedUserId());
		}
		return workflowRestController.toJson(wfProcess);
	}

	@GetMapping("/job/{wfProcessId}/has-closed-lu")
	public boolean hasClosedLUs(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@RequestParam(value = "lineId", required = false) @Nullable final String lineIdStr)
	{
		assertApplicationAccess();

		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final PickingJobLineId lineId = PickingJobLineId.ofNullableString(lineIdStr);
		return pickingMobileApplication.hasClosedLUs(wfProcessId, lineId, getLoggedUserId());
	}

	@GetMapping("/job/{wfProcessId}/closed-lu")
	public JsonHUList getClosedLUs(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@RequestParam(value = "lineId", required = false) @Nullable final String lineIdStr)
	{
		assertApplicationAccess();

		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final PickingJobLineId lineId = PickingJobLineId.ofNullableString(lineIdStr);
		final List<HuId> luIds = pickingMobileApplication.getClosedLUs(wfProcessId, lineId, getLoggedUserId());
		return handlingUnitsService.getFullHUsList(luIds, Env.getADLanguageOrBaseLanguage());
	}

	@PostMapping("/job/{wfProcessId}/target/close")
	public JsonWFProcess closeLUPickingTarget(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@RequestParam(value = "lineId", required = false) @Nullable final String lineIdStr)
	{
		assertApplicationAccess();

		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final PickingJobLineId lineId = PickingJobLineId.ofNullableString(lineIdStr);
		final WFProcess wfProcess = pickingMobileApplication.closeLUPickingTarget(wfProcessId, lineId, getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/job/{wfProcessId}/target/tu/close")
	public JsonWFProcess closeTUPickingTarget(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@RequestParam(value = "lineId", required = false) @Nullable final String lineIdStr)
	{
		assertApplicationAccess();

		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final PickingJobLineId lineId = PickingJobLineId.ofNullableString(lineIdStr);
		final WFProcess wfProcess = pickingMobileApplication.closeTUPickingTarget(wfProcessId, lineId, getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/events")
	public void postEvents(
			@RequestBody @NonNull final JsonPickingEventsList eventsList)
	{
		assertApplicationAccess();

		pickingMobileApplication.processStepEvents(eventsList, getLoggedUserId());
	}

	@PostMapping("/event")
	public JsonWFProcess postEvent(
			@RequestBody @NonNull final JsonPickingStepEvent event)
	{
		assertApplicationAccess();

		final WFProcess wfProcess = pickingMobileApplication.processStepEvent(event, getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/closeLine")
	public JsonWFProcess closeLine(@RequestBody @NonNull JsonPickingLineCloseRequest request)
	{
		assertApplicationAccess();

		final WFProcess wfProcess = pickingMobileApplication.closeLine(request, getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/openLine")
	public JsonWFProcess openLine(@RequestBody @NonNull JsonPickingLineOpenRequest request)
	{
		assertApplicationAccess();

		final WFProcess wfProcess = pickingMobileApplication.openLine(request, getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/hu/byScannedCode")
	public @NonNull JsonHUInfo getHUInfoByQRCode(@RequestBody @NonNull final JsonGetHUInfoByScannedCodeRequest request)
	{
		assertApplicationAccess();

		final ScannedCode scannedCode = ScannedCode.ofString(request.getScannedCode());
		final HUQRCode qrCode = toHUQRCode(scannedCode);

		final List<JsonHU> hus = handlingUnitsService.getHUsByQrCode(
				JsonGetByQRCodeRequest.builder().qrCode(qrCode.toGlobalQRCodeString()).build(),
				Env.getADLanguageOrBaseLanguage()
		);

		if (hus.isEmpty())
		{
			throw new AdempiereException(MobileQRCodeMessages.HU_NOT_FOUND);
		}
		else if (hus.size() > 1)
		{
			throw new AdempiereException(MobileQRCodeMessages.HU_AMBIGUOUS);
		}
		final JsonHU hu = hus.get(0);

		final JsonHUInfo.JsonHUInfoBuilder builder = JsonHUInfo.builder()
				.id(hu.getId())
				.unitType(hu.getUnitType())
				.qtyTUs(hu.getQtyTUs())
				.huQRCode(hu.getQrCode());

		// Return product info for overdelivery detection (see PickLineScanScreen)
		final String productNo = request.getProductNo();
		if (productNo != null)
		{
			hu.getProducts().stream()
					.filter(p -> productNo.equals(p.getProductValue()))
					.findFirst()
					.ifPresent(p -> {
						builder.productNo(p.getProductValue());
						try
						{
							builder.productQty(new BigDecimal(p.getQty()));
						}
						catch (final NumberFormatException e)
						{
							log.warn("Cannot parse HU product qty '{}' for product {}. Overdelivery prompt will not fire.", p.getQty(), productNo, e);
						}
						builder.productUom(p.getUom());
					});
		}

		return builder.build();
	}

	private HUQRCode toHUQRCode(final @NotNull ScannedCode scannedCode)
	{
		final IHUQRCode parsedHUQRCode = huQRCodesService.parse(scannedCode);
		if (parsedHUQRCode instanceof HUQRCode)
		{
			return (HUQRCode)parsedHUQRCode;
		}
		else
		{
			throw new AdempiereException(MobileQRCodeMessages.WRONG_TYPE, parsedHUQRCode.getClass().getSimpleName());
		}
	}

	@PostMapping("/nextEligibleLineToPack")
	public JsonGetNextEligibleLineResponse getNextEligibleLineToPack(@RequestBody @NonNull final JsonGetNextEligibleLineRequest request)
	{
		assertApplicationAccess();
		return pickingMobileApplication.getNextEligibleLineToPack(request, getLoggedUserId());
	}

	@PostMapping("/job/{wfProcessId}/pickAll")
	public WFProcess pickAllAndComplete(@PathVariable("wfProcessId") final String wfProcessIdStr)
	{
		assertApplicationAccess();
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		return pickingMobileApplication.pickAll(wfProcessId, getLoggedUserId());
	}

	@GetMapping("/job/{wfProcessId}/qtyAvailable")
	public JsonPickingJobQtyAvailable getQtyAvailable(@PathVariable("wfProcessId") final String wfProcessIdStr)
	{
		assertApplicationAccess();
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final PickingJobQtyAvailable qtyAvailable = pickingMobileApplication.getQtyAvailable(wfProcessId, getLoggedUserId());
		return JsonPickingJobQtyAvailable.of(qtyAvailable);
	}

	@PostMapping("/job/{wfProcessId}/complete")
	public WFProcess complete(@PathVariable("wfProcessId") final String wfProcessIdStr)
	{
		assertApplicationAccess();
		return pickingMobileApplication.complete(WFProcessId.ofString(wfProcessIdStr), getLoggedUserId());
	}

	/**
	 * Returns the GRAIs currently captured on the picking job's picked LU, together with
	 * {@code tuCount} (the LU's crate count = the number of GRAIs the picker is expected to scan,
	 * one per TU).
	 * <p>
	 * Authorized via the picking application (NOT the HU-Manager {@code ScanGRAI} action). The LU is
	 * resolved from the job's pick target (NOT the path {@code huId}), so a picking operator can only
	 * read the GRAIs of the LU they actually picked onto. {@code tuCount} comes from the LU snapshot, so
	 * a Flow-Through pick of N crates into one aggregate TU reports N (not 1).
	 *
	 * @param wfProcessIdStr the picking job's workflow process id (authorization + data scope)
	 * @param huId           the picked LU's HuId (kept for URL symmetry with the PUT; not used for data)
	 * @return the current GRAI codes captured on the picked LU and the LU's crate count
	 */
	@GetMapping("/job/{wfProcessId}/lu/{huId}/grai")
	public JsonGRAICodesResponse getGRAIs(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@PathVariable("huId") final int huId)
	{
		assertApplicationAccess();

		final HuId luId = resolvePickedLuId(wfProcessIdStr);
		return handlingUnitsService.getGRAIs(luId);
	}

	/**
	 * Captures the given GRAIs on the picking job's picked LU and returns the refreshed picking
	 * workflow process. Authorized via the picking application (NOT the HU-Manager {@code ScanGRAI}
	 * action). The LU is resolved from the job's pick target; the LU snapshot distributes the GRAIs
	 * across the LU's crate slots (including the N slots of a Flow-Through aggregate TU).
	 *
	 * @param wfProcessIdStr the picking job's workflow process id (authorization + data scope, returned refreshed)
	 * @param huId           the picked LU's HuId (kept for URL symmetry; not used for data)
	 * @param request        the GRAI codes to capture on the picked LU
	 * @return the refreshed picking workflow process after the GRAIs were stamped
	 */
	@PutMapping("/job/{wfProcessId}/lu/{huId}/grai")
	public JsonWFProcess setGRAIs(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@PathVariable("huId") final int huId,
			@RequestBody @NonNull final JsonGRAICodesRequest request)
	{
		assertApplicationAccess();

		final HuId luId = resolvePickedLuId(wfProcessIdStr);
		final GRAISet graiSet = GRAISet.parseStrings(request.getGraiCodes());
		handlingUnitsService.setGRAIs(luId, graiSet);

		return workflowRestController.getWFProcessById(wfProcessIdStr);
	}

	/**
	 * Resolves the concrete LU the picker has picked onto, from the job's LU pick targets.
	 * <p>
	 * When picking onto a <em>new</em> LU the pick target initially carries only the LU packing
	 * instruction (no {@code luId}); the physical LU is materialised on the first pick and the
	 * <em>line-level</em> pick target is then updated to an existing-LU target carrying the concrete
	 * {@code luId} (the header target keeps the new-LU instruction). So the concrete LU is found by
	 * scanning the effective pick target of the header and every line for the first one that has a
	 * materialised {@code luId}.
	 */
	@NonNull
	private HuId resolvePickedLuId(@NonNull final String wfProcessIdStr)
	{
		final PickingJobId pickingJobId = WFProcessId.ofString(wfProcessIdStr).getRepoId(PickingJobId::ofRepoId);
		final PickingJob pickingJob = pickingJobService.getById(pickingJobId);

		return Stream.concat(
						Stream.of(pickingJob.getLuPickingTarget(null)),
						pickingJob.streamLines().map(line -> pickingJob.getLuPickingTargetEffective(line.getId())))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.filter(LUPickingTarget::isExistingLU)
				.map(LUPickingTarget::getLuIdNotNull)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No picked LU found on picking job " + pickingJobId));
	}
}
