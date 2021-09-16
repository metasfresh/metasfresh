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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.Profiles;
import de.metas.picking.rest_api.json.JsonPickingEventsList;
import de.metas.picking.rest_api.json.JsonQtyPickedEvent;
import de.metas.picking.workflow.model.PickingJob;
import de.metas.picking.workflow.model.PickingJobStepId;
import de.metas.picking.workflow.model.QtyPickedEvent;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.service.WorkflowRestAPIService;
import lombok.NonNull;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static de.metas.picking.workflow.handlers.activity_handlers.PickingWFActivityHelper.getPickingJob;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/picking")
@RestController
@Profile(Profiles.PROFILE_App)
public class PickingRestController
{
	private final WorkflowRestAPIService workflowRestAPIService;

	public PickingRestController(
			@NonNull final WorkflowRestAPIService workflowRestAPIService)
	{
		this.workflowRestAPIService = workflowRestAPIService;
	}

	private JsonOpts newJsonOpts()
	{
		return JsonOpts.builder()
				.adLanguage(Env.getADLanguageOrBaseLanguage())
				.build();
	}

	@PostMapping("/events")
	public void postEvents(
			@RequestBody @NonNull final JsonPickingEventsList eventsList)
	{
		final ImmutableListMultimap<WFProcessId, JsonQtyPickedEvent> eventsByWFProcessId = Multimaps.index(
				eventsList.getEvents(),
				jsonEvent -> WFProcessId.ofString(jsonEvent.getWfProcessId()));

		eventsByWFProcessId.asMap().forEach(this::postEvents);
	}

	private void postEvents(
			@NonNull final WFProcessId wfProcessId,
			@NonNull final Collection<JsonQtyPickedEvent> events)
	{
		final WFProcess wfProcess = workflowRestAPIService.getWFProcessById(wfProcessId);
		wfProcess.assertHasAccess(Env.getLoggedUserId());

		final ImmutableListMultimap<WFActivityId, JsonQtyPickedEvent> eventsByWFActivityId = Multimaps.index(
				events,
				jsonEvent -> WFActivityId.ofString(jsonEvent.getWfActivityId()));

		eventsByWFActivityId.asMap()
				.forEach((wfActivityId, eventsForActivity) -> postEvents(wfProcess, wfActivityId, eventsForActivity));
	}

	private void postEvents(
			@NonNull final WFProcess wfProcess,
			@NonNull final WFActivityId wfActivityId,
			@NonNull final Collection<JsonQtyPickedEvent> events)
	{
		final WFActivity wfActivity = wfProcess.getActivityById(wfActivityId);
		// TODO: just validate that we are on the right activity

		final PickingJob pickingJob = getPickingJob(wfProcess);
		pickingJob.applyChanges(toQtyPickedEventsList(events));
	}

	private static List<QtyPickedEvent> toQtyPickedEventsList(@NonNull final Collection<JsonQtyPickedEvent> events)
	{
		return events.stream()
				.map(PickingRestController::toQtyPickedEvent)
				.collect(ImmutableList.toImmutableList());
	}

	private static QtyPickedEvent toQtyPickedEvent(@NonNull final JsonQtyPickedEvent event)
	{
		return QtyPickedEvent.builder()
				.stepId(PickingJobStepId.ofString(event.getPickingStepId()))
				.qtyPicked(event.getQtyPicked())
				.build();
	}

}
