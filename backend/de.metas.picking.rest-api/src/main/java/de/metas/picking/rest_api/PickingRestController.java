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
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.picking.rest_api.json.JsonHUIdList;
import de.metas.picking.rest_api.json.JsonLUPickingTarget;
import de.metas.picking.rest_api.json.JsonPickingEventsList;
import de.metas.picking.rest_api.json.JsonPickingJobAvailableTargets;
import de.metas.picking.rest_api.json.JsonPickingLineCloseRequest;
import de.metas.picking.rest_api.json.JsonPickingLineOpenRequest;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.rest_api.json.JsonTUPickingTarget;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.workflow.rest_api.controller.v2.WorkflowRestController;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessId;
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

import javax.annotation.Nullable;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/picking")
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class PickingRestController
{
	@NonNull private final PickingMobileApplication pickingMobileApplication;
	@NonNull private final WorkflowRestController workflowRestController;

	@GetMapping("/job/{wfProcessId}/target/available")
	public JsonPickingJobAvailableTargets getAvailableTargets(@PathVariable("wfProcessId") final String wfProcessIdStr)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		return pickingMobileApplication.getAvailableTargets(wfProcessId, Env.getLoggedUserId());
	}

	@PostMapping("/job/{wfProcessId}/target")
	public JsonWFProcess setTarget(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@RequestBody(required = false) @Nullable final JsonLUPickingTarget jsonTarget)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final LUPickingTarget target = jsonTarget != null ? jsonTarget.unbox() : null;
		final WFProcess wfProcess = pickingMobileApplication.setPickTarget(wfProcessId, target, Env.getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/job/{wfProcessId}/target/tu")
	public JsonWFProcess setTUTarget(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr,
			@RequestBody(required = false) @Nullable final JsonTUPickingTarget jsonTarget)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final TUPickingTarget target = jsonTarget != null ? jsonTarget.unbox() : null;
		final WFProcess wfProcess = pickingMobileApplication.setPickTarget(wfProcessId, target, Env.getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/job/{wfProcessId}/target/close")
	public JsonWFProcess closeTarget(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final WFProcess wfProcess = pickingMobileApplication.closeLUPickTarget(wfProcessId, Env.getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/job/{wfProcessId}/target/tu/close")
	public JsonWFProcess closeTUTarget(
			@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final WFProcess wfProcess = pickingMobileApplication.closeTUPickTarget(wfProcessId, Env.getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/events")
	public void postEvents(
			@RequestBody @NonNull final JsonPickingEventsList eventsList)
	{
		pickingMobileApplication.processStepEvents(eventsList, Env.getLoggedUserId());
	}

	@PostMapping("/event")
	public JsonWFProcess postEvent(
			@RequestBody @NonNull final JsonPickingStepEvent event)
	{
		final WFProcess wfProcess = pickingMobileApplication.processStepEvent(event, Env.getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/closeLine")
	public JsonWFProcess closeLine(@RequestBody @NonNull JsonPickingLineCloseRequest request)
	{
		final WFProcess wfProcess = pickingMobileApplication.closeLine(request, Env.getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@PostMapping("/openLine")
	public JsonWFProcess openLine(@RequestBody @NonNull JsonPickingLineOpenRequest request)
	{
		final WFProcess wfProcess = pickingMobileApplication.openLine(request, Env.getLoggedUserId());
		return workflowRestController.toJson(wfProcess);
	}

	@GetMapping("/job/{wfProcessId}/closed-lu")
	public JsonHUIdList getClosedHUs(@PathVariable("wfProcessId") @NonNull final String wfProcessIdStr)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		return JsonHUIdList.builder()
				.huIds(pickingMobileApplication.getClosedLUs(wfProcessId, Env.getLoggedUserId()))
				.build();
	}
}
