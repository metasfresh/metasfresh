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

package de.metas.picking.workflow.handlers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.i18n.TranslatableStrings;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.picking.rest_api.json.JsonPickingEventsList;
import de.metas.picking.workflow.PickingJobService;
import de.metas.picking.workflow.PickingJobStepEvent;
import de.metas.picking.workflow.handlers.activity_handlers.ActualPickingWFActivityHandler;
import de.metas.picking.workflow.handlers.activity_handlers.CompletePickingWFActivityHandler;
import de.metas.picking.workflow.handlers.activity_handlers.SetPickingSlotWFActivityHandler;
import de.metas.picking.workflow.model.PickingJob;
import de.metas.picking.workflow.model.PickingWFProcessStartParams;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHandlerId;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperty;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.service.WFProcessHandler;
import de.metas.workflow.rest_api.service.WFProcessesIndex;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.Set;
import java.util.function.UnaryOperator;

import static de.metas.picking.workflow.handlers.PickingWFProcessUtils.workflowCaption;
import static de.metas.picking.workflow.handlers.activity_handlers.PickingWFActivityHelper.extractShipmentScheduleIds;
import static de.metas.picking.workflow.handlers.activity_handlers.PickingWFActivityHelper.getPickingJob;

@Component
public class PickingWFProcessHandler implements WFProcessHandler
{
	static final WFProcessHandlerId HANDLER_ID = WFProcessHandlerId.ofString("picking");

	private final PickingJobService pickingJobService;

	private final WFProcessesIndex wfProcesses;
	private final PickingWorkflowLaunchersProvider wfLaunchersProvider;

	public PickingWFProcessHandler(
			@NonNull final PickingJobService pickingJobService)
	{
		this.pickingJobService = pickingJobService;

		this.wfProcesses = new WFProcessesIndex();
		this.wfLaunchersProvider = new PickingWorkflowLaunchersProvider(pickingJobService, wfProcesses);
	}

	@Override
	public WFProcessHandlerId getId()
	{
		return HANDLER_ID;
	}

	@Override
	public WorkflowLaunchersList provideLaunchers(
			@NonNull final UserId userId,
			@NonNull final Duration maxStaleAccepted)
	{
		return wfLaunchersProvider.provideLaunchers(userId, maxStaleAccepted);
	}

	@Override
	public WFProcess getWFProcessById(@NonNull final WFProcessId wfProcessId)
	{
		return wfProcesses.getById(wfProcessId);
	}

	@Override
	public WFProcess changeWFProcessById(
			@NonNull final WFProcessId wfProcessId,
			@NonNull final UnaryOperator<WFProcess> remappingFunction)
	{
		return wfProcesses.compute(wfProcessId, remappingFunction);
	}

	@Override
	public WFProcessHeaderProperties getHeaderProperties(@NonNull final WFProcess wfProcess)
	{
		final PickingJob pickingJob = getPickingJob(wfProcess);

		return WFProcessHeaderProperties.builder()
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("DocumentNo"))
						.value(pickingJob.getSalesOrderDocumentNo())
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("C_BPartner_Customer_ID"))
						.value(pickingJob.getCustomerName())
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("PreparationDate"))
						.value(pickingJob.getPreparationDate())
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("DeliveryToAddress"))
						.value(pickingJob.getDeliveryRenderedAddress())
						.build())
				.build();
	}

	@Override
	public WFProcess startWorkflow(@NonNull final WorkflowStartRequest request)
	{
		final UserId invokerId = request.getInvokerId();
		final PickingWFProcessStartParams params = PickingWFProcessStartParams.ofParams(request.getWfParameters());

		final Set<ShipmentScheduleId> shipmentScheduleIdsAlreadyInPickingJobs = extractShipmentScheduleIds(wfProcesses.getByInvokerId(invokerId));
		final PickingJob pickingJob = pickingJobService.createPickingJob(params, invokerId, shipmentScheduleIdsAlreadyInPickingJobs);

		final WFProcess wfProcess = createWFProcess(pickingJob);
		wfProcesses.add(wfProcess);

		wfLaunchersProvider.invalidateCacheByUserId(invokerId);

		return wfProcess;
	}


	@Override
	public void abort(@NonNull final WFProcessId wfProcessId, @NonNull final UserId callerId)
	{
		final WFProcess wfProcess = wfProcesses.getById(wfProcessId);
		abort(wfProcess, callerId);
	}

	private void abort(@NonNull final WFProcess wfProcess, final @NonNull UserId callerId)
	{
		wfProcess.assertHasAccess(callerId);

		// TODO: call the activity handlers

		final PickingJob pickingJob = getPickingJob(wfProcess);
		pickingJobService.abort(pickingJob);

		wfProcesses.remove(wfProcess);

		wfLaunchersProvider.invalidateCacheByUserId(wfProcess.getInvokerId());
	}

	@Override
	public void abortAll(final UserId callerId)
	{
		wfProcesses.getByInvokerId(callerId)
				.forEach(wfProcess -> abort(wfProcess, callerId));
	}

	private WFProcess createWFProcess(final PickingJob pickingJob)
	{
		final UserId lockedBy = pickingJob.getLockedBy();

		return WFProcess.builder()
				.id(WFProcessId.random(HANDLER_ID))
				.invokerId(lockedBy)
				.caption(workflowCaption()
						.salesOrderDocumentNo(pickingJob.getSalesOrderDocumentNo())
						.customerName(pickingJob.getCustomerName())
						.build())
				.document(pickingJob)
				.activities(ImmutableList.of(
						WFActivity.builder()
								.id(WFActivityId.ofString("1"))
								.caption(TranslatableStrings.anyLanguage("Scan picking slot"))
								.wfActivityType(SetPickingSlotWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.build(),
						WFActivity.builder()
								.id(WFActivityId.ofString("2"))
								.caption(TranslatableStrings.anyLanguage("Pick"))
								.wfActivityType(ActualPickingWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.build(),
						WFActivity.builder()
								.id(WFActivityId.ofString("3"))
								.caption(TranslatableStrings.anyLanguage("Complete picking"))
								.wfActivityType(CompletePickingWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.build()))
				.build();
	}

	public void processStepEvents(
			@NonNull final JsonPickingEventsList eventsList,
			@NonNull final UserId callerId)
	{
		final ImmutableListMultimap<WFProcessId, PickingJobStepEvent> eventsByWFProcessId = eventsList
				.getEvents()
				.stream()
				.map(PickingJobStepEvent::ofJson)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						PickingJobStepEvent::getWfProcessId,
						event -> event));

		eventsByWFProcessId
				.asMap()
				.forEach((wfProcessId, events) -> processStepEvents(wfProcessId, callerId, events));
	}

	private void processStepEvents(
			@NonNull final WFProcessId wfProcessId,
			@NonNull final UserId callerId,
			@NonNull final Collection<PickingJobStepEvent> events)
	{
		changeWFProcessById(
				wfProcessId,
				wfProcess -> {
					wfProcess.assertHasAccess(callerId);
					assertPickingActivityType(events, wfProcess);

					return wfProcess.<PickingJob>mapDocument(
							pickingJob -> pickingJobService.processStepEvents(pickingJob, events)
					);
				});
	}

	private static void assertPickingActivityType(
			final @NonNull Collection<PickingJobStepEvent> events,
			final @NonNull WFProcess wfProcess)
	{
		events.stream()
				.map(PickingJobStepEvent::getWfActivityId)
				.distinct()
				.map(wfProcess::getActivityById)
				.map(WFActivity::getWfActivityType)
				.forEach(ActualPickingWFActivityHandler.HANDLED_ACTIVITY_TYPE::assertActual);
	}
}
