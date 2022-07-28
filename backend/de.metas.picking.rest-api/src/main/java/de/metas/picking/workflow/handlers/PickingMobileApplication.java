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
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.rest_api.json.JsonPickingEventsList;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.PickingWFProcessStartParams;
import de.metas.picking.workflow.handlers.activity_handlers.ActualPickingWFActivityHandler;
import de.metas.picking.workflow.handlers.activity_handlers.CompletePickingWFActivityHandler;
import de.metas.picking.workflow.handlers.activity_handlers.SetPickingSlotWFActivityHandler;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperty;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.service.MobileApplication;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.function.UnaryOperator;

import static de.metas.picking.workflow.handlers.activity_handlers.PickingWFActivityHelper.getPickingJob;

@Component
public class PickingMobileApplication implements MobileApplication
{
	static final MobileApplicationId HANDLER_ID = MobileApplicationId.ofString("picking");

	private static final AdMessageKey MSG_Caption = AdMessageKey.of("mobileui.picking.appName");
	private static final MobileApplicationInfo APPLICATION_INFO = MobileApplicationInfo.builder()
			.id(HANDLER_ID)
			.caption(TranslatableStrings.adMessage(MSG_Caption))
			.build();

	private final PickingJobRestService pickingJobRestService;

	private final PickingWorkflowLaunchersProvider wfLaunchersProvider;

	public PickingMobileApplication(
			@NonNull final PickingJobRestService pickingJobRestService)
	{
		this.pickingJobRestService = pickingJobRestService;
		this.wfLaunchersProvider = new PickingWorkflowLaunchersProvider(pickingJobRestService);
	}

	@Override
	@NonNull
	public MobileApplicationInfo getApplicationInfo() {return APPLICATION_INFO;}

	@Override
	public WorkflowLaunchersList provideLaunchers(
			@NonNull final UserId userId,
			@NonNull final QueryLimit suggestedLimit,
			@NonNull final Duration maxStaleAccepted)
	{
		return wfLaunchersProvider.provideLaunchers(userId, suggestedLimit, maxStaleAccepted);
	}

	@Override
	public WFProcess getWFProcessById(@NonNull final WFProcessId wfProcessId)
	{
		final PickingJobId pickingJobId = wfProcessId.getRepoId(PickingJobId::ofRepoId);
		final PickingJob pickingJob = pickingJobRestService.getPickingJobById(pickingJobId);
		return toWFProcess(pickingJob);
	}

	@Override
	public WFProcess changeWFProcessById(
			@NonNull final WFProcessId wfProcessId,
			@NonNull final UnaryOperator<WFProcess> remappingFunction)
	{
		final WFProcess wfProcess = getWFProcessById(wfProcessId);
		return remappingFunction.apply(wfProcess);
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
		final PickingJob pickingJob = pickingJobRestService.createPickingJob(params, invokerId);

		wfLaunchersProvider.invalidateCacheByUserId(invokerId);

		return toWFProcess(pickingJob);
	}

	@Override
	public void abort(@NonNull final WFProcessId wfProcessId, @NonNull final UserId callerId)
	{
		final PickingJobId pickingJobId = wfProcessId.getRepoId(PickingJobId::ofRepoId);
		final PickingJob pickingJob = pickingJobRestService.getPickingJobById(pickingJobId);
		final WFProcess wfProcess = toWFProcess(pickingJob);
		abort(wfProcess, callerId);
	}

	private void abort(@NonNull final WFProcess wfProcess, final @NonNull UserId callerId)
	{
		wfProcess.assertHasAccess(callerId);
		pickingJobRestService.abort(getPickingJob(wfProcess));
		wfLaunchersProvider.invalidateCacheByUserId(wfProcess.getInvokerId());
	}

	@Override
	public void abortAll(final UserId callerId)
	{
		pickingJobRestService.getDraftJobsByPickerId(callerId)
				.stream()
				.map(PickingMobileApplication::toWFProcess)
				.forEach(wfProcess -> abort(wfProcess, callerId));
	}

	private static WFProcess toWFProcess(final PickingJob pickingJob)
	{
		final UserId lockedBy = pickingJob.getLockedBy();

		return WFProcess.builder()
				.id(WFProcessId.ofIdPart(HANDLER_ID, pickingJob.getId()))
				.invokerId(lockedBy)
				.caption(PickingWFProcessUtils.workflowCaption()
						.salesOrderDocumentNo(pickingJob.getSalesOrderDocumentNo())
						.customerName(pickingJob.getCustomerName())
						.build())
				.document(pickingJob)
				.activities(ImmutableList.of(
						WFActivity.builder()
								.id(WFActivityId.ofString("A1"))
								.caption(TranslatableStrings.anyLanguage("Scan picking slot"))
								.wfActivityType(SetPickingSlotWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.build(),
						WFActivity.builder()
								.id(WFActivityId.ofString("A2"))
								.caption(TranslatableStrings.anyLanguage("Pick"))
								.wfActivityType(ActualPickingWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.build(),
						WFActivity.builder()
								.id(WFActivityId.ofString("A3"))
								.caption(TranslatableStrings.anyLanguage("Complete picking"))
								.wfActivityType(CompletePickingWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.build()))
				.build();
	}

	public void processStepEvents(
			@NonNull final JsonPickingEventsList eventsList,
			@NonNull final UserId callerId)
	{
		final ImmutableListMultimap<WFProcessId, JsonPickingStepEvent> eventsByWFProcessId = eventsList
				.getEvents()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						event -> WFProcessId.ofString(event.getWfProcessId()),
						event -> event));

		eventsByWFProcessId
				.asMap()
				.forEach((wfProcessId, events) -> processStepEvents(wfProcessId, callerId, events));
	}

	private static PickingJobStepEvent fromJson(@NonNull final JsonPickingStepEvent json)
	{
		return PickingJobStepEvent.builder()
				.timestamp(SystemTime.asInstant())
				.pickingStepId(PickingJobStepId.ofString(json.getPickingStepId()))
				.eventType(fromJson(json.getType()))
				.huBarcode(HUBarcode.ofBarcodeString(json.getHuBarcode()))
				.qtyPicked(json.getQtyPicked())
				.qtyRejectedReasonCode(QtyRejectedReasonCode.ofNullableCode(json.getQtyRejectedReasonCode()).orElse(null))
				.build();
	}

	public static PickingJobStepEventType fromJson(JsonPickingStepEvent.EventType json)
	{
		switch (json)
		{
			case PICK:
				return PickingJobStepEventType.PICK;
			case UNPICK:
				return PickingJobStepEventType.UNPICK;
			default:
				throw new AdempiereException("Unknown event type: " + json);
		}
	}

	private void processStepEvents(
			@NonNull final WFProcessId wfProcessId,
			@NonNull final UserId callerId,
			@NonNull final Collection<JsonPickingStepEvent> jsonEvents)
	{
		changeWFProcessById(
				wfProcessId,
				wfProcess -> {
					wfProcess.assertHasAccess(callerId);
					assertPickingActivityType(jsonEvents, wfProcess);

					final ImmutableList<PickingJobStepEvent> events = jsonEvents.stream()
							.map(PickingMobileApplication::fromJson)
							.collect(ImmutableList.toImmutableList());

					return wfProcess.<PickingJob>mapDocument(
							pickingJob -> pickingJobRestService.processStepEvents(pickingJob, events)
					);
				});
	}

	private static void assertPickingActivityType(
			final @NonNull Collection<JsonPickingStepEvent> events,
			final @NonNull WFProcess wfProcess)
	{
		events.stream()
				.map(event -> WFActivityId.ofString(event.getWfActivityId()))
				.distinct()
				.map(wfProcess::getActivityById)
				.map(WFActivity::getWfActivityType)
				.forEach(ActualPickingWFActivityHandler.HANDLED_ACTIVITY_TYPE::assertActual);
	}
}
