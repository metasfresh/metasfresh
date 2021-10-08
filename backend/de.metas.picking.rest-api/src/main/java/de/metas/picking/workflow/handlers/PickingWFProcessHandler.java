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
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.picking.rest_api.json.JsonPickingEventsList;
import de.metas.picking.workflow.PickingJobService;
import de.metas.picking.workflow.PickingJobStepEvent;
import de.metas.picking.workflow.handlers.activity_handlers.ActualPickingWFActivityHandler;
import de.metas.picking.workflow.handlers.activity_handlers.CompletePickingWFActivityHandler;
import de.metas.picking.workflow.handlers.activity_handlers.SetPickingSlotWFActivityHandler;
import de.metas.picking.workflow.model.PickingJob;
import de.metas.picking.workflow.model.PickingJobCandidate;
import de.metas.picking.workflow.model.PickingWFProcessStartParams;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHandlerId;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperty;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.service.WFProcessHandler;
import de.metas.workflow.rest_api.service.WFProcessesIndex;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

import static de.metas.picking.workflow.handlers.activity_handlers.PickingWFActivityHelper.extractShipmentScheduleIds;
import static de.metas.picking.workflow.handlers.activity_handlers.PickingWFActivityHelper.getPickingJob;

@Component
public class PickingWFProcessHandler implements WFProcessHandler
{
	private static final WFProcessHandlerId PROVIDER_ID = WFProcessHandlerId.ofString("picking");

	private final PickingJobService pickingJobService;

	private final WFProcessesIndex wfProcesses = new WFProcessesIndex();

	public PickingWFProcessHandler(
			@NonNull final PickingJobService pickingJobService)
	{
		this.pickingJobService = pickingJobService;
	}

	@Override
	public WFProcessHandlerId getId()
	{
		return PROVIDER_ID;
	}

	@Override
	public List<WorkflowLauncher> provideLaunchers(@NonNull final UserId userId)
	{
		final ArrayList<WorkflowLauncher> result = new ArrayList<>();

		//
		// Already started launchers
		final ImmutableList<WFProcess> existingWFProcesses = wfProcesses.getByInvokerId(userId);
		existingWFProcesses.stream()
				.map(PickingWFProcessHandler::toExistingWorkflowLauncher)
				.forEach(result::add);

		//
		// New launchers
		final Set<ShipmentScheduleId> shipmentScheduleIdsAlreadyInPickingJobs = extractShipmentScheduleIds(existingWFProcesses);
		pickingJobService.streamPickingJobCandidates(userId, shipmentScheduleIdsAlreadyInPickingJobs)
				.map(PickingWFProcessHandler::toNewWorkflowLauncher)
				.forEach(result::add);

		return result;
	}

	private static WorkflowLauncher toNewWorkflowLauncher(@NonNull final PickingJobCandidate pickingJobCandidate)
	{
		return WorkflowLauncher.builder()
				.handlerId(PROVIDER_ID)
				.caption(workflowCaption()
						.salesOrderDocumentNo(pickingJobCandidate.getSalesOrderDocumentNo())
						.customerName(pickingJobCandidate.getCustomerName())
						.build())
				.startedWFProcessId(null)
				.wfParameters(pickingJobCandidate.getWfProcessStartParams().toParams())
				.build();
	}

	private static WorkflowLauncher toExistingWorkflowLauncher(@NonNull final WFProcess wfProcess)
	{
		return WorkflowLauncher.builder()
				.handlerId(PROVIDER_ID)
				.caption(wfProcess.getCaption())
				.startedWFProcessId(wfProcess.getId())
				.build();
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
						.caption(TranslatableStrings.adElementOrMessage("SalesOrderDocumentNo"))
						.value(pickingJob.getSalesOrderDocumentNo())
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("CustomerName"))
						.value(pickingJob.getCustomerName())
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("PreparationDate"))
						.value(pickingJob.getPreparationDate())
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("DeliveryAddress"))
						.value(pickingJob.getDeliveryAddress())
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

		return wfProcess;
	}

	@Override
	public void abort(@NonNull final WFProcessId wfProcessId, @NonNull final UserId callerId)
	{
		final WFProcess wfProcess = wfProcesses.getById(wfProcessId);
		wfProcess.assertHasAccess(callerId);

		// TODO: call the activity handlers

		final PickingJob pickingJob = getPickingJob(wfProcess);
		pickingJobService.abort(pickingJob);

		wfProcesses.remove(wfProcess);
	}

	private WFProcess createWFProcess(final PickingJob pickingJob)
	{
		final UserId lockedBy = pickingJob.getLockedBy();

		return WFProcess.builder()
				.id(WFProcessId.random(PROVIDER_ID))
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

	@Builder(builderMethodName = "workflowCaption", builderClassName = "$WorkflowCaptionBuilder")
	private static ITranslatableString buildWorkflowCaption(
			@NonNull final String salesOrderDocumentNo,
			@NonNull final String customerName)
	{
		return TranslatableStrings.join(" | ", salesOrderDocumentNo, customerName);
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
