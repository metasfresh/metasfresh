/*
 * #%L
 * de.metas.workflow.rest-api
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

package de.metas.workflow.rest_api.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeRequest;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeSupport;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationRequest;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationSupport;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

@Service
public class WorkflowRestAPIService
{
	private static final Logger logger = LogManager.getLogger(WorkflowRestAPIService.class);

	private final WFProcessHandlersMap wfProcessHandlers;
	private final WFActivityHandlersRegistry wfActivityHandlersRegistry;

	WorkflowRestAPIService(
			@NonNull final Optional<List<WFProcessHandler>> wfProcessHandlers,
			@NonNull final WFActivityHandlersRegistry wfActivityHandlersRegistry)
	{
		this.wfProcessHandlers = WFProcessHandlersMap.of(wfProcessHandlers);
		logger.info("wfProcessHandlers: {}", this.wfProcessHandlers);

		this.wfActivityHandlersRegistry = wfActivityHandlersRegistry;
	}

	public List<WorkflowLauncher> getLaunchers(@NonNull final UserId userId)
	{
		return wfProcessHandlers.stream()
				.map(handler -> provideLaunchersNoFail(handler, userId))
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList());
	}

	private static List<WorkflowLauncher> provideLaunchersNoFail(
			@NonNull final WFProcessHandler handler,
			@NonNull final UserId userId)
	{
		try
		{
			final List<WorkflowLauncher> launchers = handler.provideLaunchers(userId);
			return launchers != null ? launchers : ImmutableList.of();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed fetching launchers from {} for {}. Skipped", handler, userId, ex);
			return ImmutableList.of();
		}
	}

	public WFProcess getWFProcessById(@NonNull final WFProcessId wfProcessId)
	{
		return wfProcessHandlers
				.getById(wfProcessId.getHandlerId())
				.getWFProcessById(wfProcessId);
	}

	public WFProcess changeWFProcessById(
			@NonNull final WFProcessId wfProcessId,
			@NonNull final UnaryOperator<WFProcess> remappingFunction)
	{
		return wfProcessHandlers
				.getById(wfProcessId.getHandlerId())
				.changeWFProcessById(wfProcessId, remappingFunction);
	}

	public WFProcess startWorkflow(@NonNull final WorkflowStartRequest request)
	{
		return wfProcessHandlers
				.getById(request.getHandlerId())
				.startWorkflow(request);
	}

	public void abortWFProcess(@NonNull final WFProcessId wfProcessId, @NonNull final UserId callerId)
	{
		wfProcessHandlers
				.getById(wfProcessId.getHandlerId())
				.abort(wfProcessId, callerId);
	}

	public void abortAllWFProcesses(@NonNull final UserId callerId)
	{
		wfProcessHandlers
				.stream()
				.forEach(handler -> handler.abortAll(callerId));
	}

	public WFProcessHeaderProperties getHeaderProperties(@NonNull final WFProcess wfProcess)
	{
		return wfProcessHandlers
				.getById(wfProcess.getId().getHandlerId())
				.getHeaderProperties(wfProcess);
	}

	public ImmutableMap<WFActivityId, UIComponent> getUIComponents(
			@NonNull final WFProcess wfProcess,
			@NonNull final JsonOpts jsonOpts)
	{
		final ImmutableMap.Builder<WFActivityId, UIComponent> uiComponents = ImmutableMap.builder();
		for (final WFActivity wfActivity : wfProcess.getActivities())
		{
			final UIComponent uiComponent = wfActivityHandlersRegistry
					.getHandler(wfActivity.getWfActivityType())
					.getUIComponent(wfProcess, wfActivity, jsonOpts);

			uiComponents.put(wfActivity.getId(), uiComponent);
		}

		return uiComponents.build();
	}

	public WFProcess setScannedBarcode(
			@NonNull final UserId invokerId,
			@NonNull final WFProcessId wfProcessId,
			@NonNull final WFActivityId wfActivityId,
			@Nullable final String scannedBarcode)
	{
		return processWFActivity(
				invokerId,
				wfProcessId,
				wfActivityId,
				(wfProcess, wfActivity) -> setScannedBarcode0(wfProcess, wfActivity, scannedBarcode));
	}

	private WFProcess setScannedBarcode0(
			@NonNull final WFProcess wfProcess,
			@NonNull final WFActivity wfActivity,
			@Nullable final String scannedBarcode)
	{
		return wfActivityHandlersRegistry
				.getFeature(wfActivity.getWfActivityType(), SetScannedBarcodeSupport.class)
				.setScannedBarcode(SetScannedBarcodeRequest.builder()
						.wfProcess(wfProcess)
						.wfActivity(wfActivity)
						.scannedBarcode(scannedBarcode)
						.build());
	}

	public WFProcess setUserConfirmation(
			@NonNull final UserId invokerId,
			@NonNull final WFProcessId wfProcessId,
			@NonNull final WFActivityId wfActivityId)
	{
		return processWFActivity(
				invokerId,
				wfProcessId,
				wfActivityId,
				this::setUserConfirmation0);
	}

	private WFProcess setUserConfirmation0(
			@NonNull final WFProcess wfProcess,
			@NonNull final WFActivity wfActivity)
	{
		return wfActivityHandlersRegistry
				.getFeature(wfActivity.getWfActivityType(), UserConfirmationSupport.class)
				.userConfirmed(UserConfirmationRequest.builder()
						.wfProcess(wfProcess)
						.wfActivity(wfActivity)
						.build());
	}

	private WFProcess processWFActivity(
			@NonNull final UserId invokerId,
			@NonNull final WFProcessId wfProcessId,
			@NonNull final WFActivityId wfActivityId,
			@NonNull final BiFunction<WFProcess, WFActivity, WFProcess> processor)
	{
		return changeWFProcessById(
				wfProcessId,
				wfProcess -> {
					wfProcess.assertHasAccess(invokerId);

					final WFActivity wfActivity = wfProcess.getActivityById(wfActivityId);

					WFProcess wfProcessChanged = processor.apply(wfProcess, wfActivity);
					if (!Objects.equals(wfProcess, wfProcessChanged))
					{
						wfProcessChanged = withUpdatedActivityStatuses(wfProcessChanged);
					}

					return wfProcessChanged;
				});

	}

	private WFProcess withUpdatedActivityStatuses(@NonNull final WFProcess wfProcess)
	{
		WFProcess wfProcessChanged = wfProcess;

		for (final WFActivity wfActivity : wfProcess.getActivities())
		{
			final WFActivityStatus newActivityStatus = wfActivityHandlersRegistry
					.getHandler(wfActivity.getWfActivityType())
					.computeActivityState(wfProcessChanged, wfActivity);

			wfProcessChanged = wfProcessChanged.withChangedActivityStatus(wfActivity.getId(), newActivityStatus);
		}

		return wfProcessChanged;
	}
}
