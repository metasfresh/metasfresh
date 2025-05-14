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
import de.metas.common.handlingunits.JsonHUList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.rest_api.HandlingUnitsService;
import de.metas.mobile.application.service.MobileApplicationService;
import de.metas.picking.rest_api.json.JsonLUPickingTarget;
import de.metas.picking.rest_api.json.JsonPickingEventsList;
import de.metas.picking.rest_api.json.JsonPickingJobAvailableTargets;
import de.metas.picking.rest_api.json.JsonPickingLineCloseRequest;
import de.metas.picking.rest_api.json.JsonPickingLineOpenRequest;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.rest_api.json.JsonTUPickingTarget;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.user.UserId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.workflow.rest_api.controller.v2.WorkflowRestController;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.util.List;

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

	private void assertApplicationAccess()
	{
		mobileApplicationService.assertAccess(pickingMobileApplication.getApplicationId(), Env.getUserRolePermissions());
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
		final TUPickingTarget target = jsonTarget != null ? jsonTarget.unbox() : null;
		final WFProcess wfProcess = pickingMobileApplication.setTUPickingTarget(wfProcessId, lineId, target, getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@GetMapping("/job/{wfProcessId}/closed-lu")
	public JsonHUList getClosedLUs(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@RequestParam(value = "lineId", required = false) @Nullable final String lineIdStr)
	{
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
}
