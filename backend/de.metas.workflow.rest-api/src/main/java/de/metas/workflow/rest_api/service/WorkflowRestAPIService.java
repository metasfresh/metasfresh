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
import de.metas.workflow.WFState;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeRequest;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeSupport;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationRequest;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationSupport;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@Service
public class WorkflowRestAPIService
{
	private static final Logger logger = LogManager.getLogger(WorkflowRestAPIService.class);

	private final WorkflowLauncherProvidersMap launcherProviders;
	private final WFActivityHandlersRegistry wfActivityHandlersRegistry;

	WorkflowRestAPIService(
			@NonNull final Optional<List<WorkflowLauncherProvider>> providers,
			@NonNull final WFActivityHandlersRegistry wfActivityHandlersRegistry)
	{
		this.launcherProviders = WorkflowLauncherProvidersMap.of(providers);
		logger.info("launcherProviders: {}", launcherProviders);

		this.wfActivityHandlersRegistry = wfActivityHandlersRegistry;
	}

	public List<WorkflowLauncher> getLaunchers(@NonNull final UserId userId)
	{
		return launcherProviders.stream()
				.map(provider -> provideLaunchersNoFail(provider, userId))
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList());
	}

	private List<WorkflowLauncher> provideLaunchersNoFail(
			@NonNull final WorkflowLauncherProvider provider,
			@NonNull final UserId userId)
	{
		try
		{
			final List<WorkflowLauncher> launchers = provider.provideLaunchers(userId);
			return launchers != null ? launchers : ImmutableList.of();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed fetching launchers from {} for {}. Skipped", provider, userId, ex);
			return ImmutableList.of();
		}
	}

	public WFProcess getWFProcessById(@NonNull final WFProcessId wfProcessId)
	{
		return launcherProviders
				.getById(wfProcessId.getProviderId())
				.getWFProcessById(wfProcessId);
	}

	public WFProcess startWorkflow(@NonNull final WorkflowStartRequest request)
	{
		return launcherProviders
				.getById(request.getProviderId())
				.startWorkflow(request);
	}

	public WFProcessHeaderProperties getHeaderProperties(@NonNull final WFProcess wfProcess)
	{
		return wfProcess.getActivitiesInOrder()
				.stream()
				.map(wfActivity -> getHeaderProperties(wfProcess, wfActivity))
				.reduce(WFProcessHeaderProperties::composeWith)
				.orElse(WFProcessHeaderProperties.EMPTY);
	}

	private WFProcessHeaderProperties getHeaderProperties(
			@NonNull final WFProcess wfProcess,
			@NonNull final WFActivity wfActivity)
	{
		final WFProcessHeaderProperties properties = launcherProviders.getById(wfProcess.getId().getProviderId())
				.getHeaderProperties(wfProcess, wfActivity);

		final WFProcessHeaderProperties propertiesFromActivities = wfActivityHandlersRegistry
				.getHandler(wfActivity.getWfActivityType())
				.getHeaderProperties(wfProcess, wfActivity);

		return properties.composeWith(propertiesFromActivities);
	}

	public ImmutableMap<WFActivityId, UIComponent> getUIComponents(
			@NonNull final WFProcess wfProcess,
			@NonNull final JsonOpts jsonOpts)
	{
		final ImmutableMap.Builder<WFActivityId, UIComponent> uiComponents = ImmutableMap.builder();
		for (final WFActivity wfActivity : wfProcess.getActivitiesInOrder())
		{
			final UIComponent uiComponent = wfActivityHandlersRegistry
					.getHandler(wfActivity.getWfActivityType())
					.getUIComponent(wfProcess, wfActivity, jsonOpts);

			uiComponents.put(wfActivity.getId(), uiComponent);
		}

		return uiComponents.build();
	}

	public void setScannedBarcode(
			@NonNull final UserId invokerId,
			@NonNull final WFProcessId wfProcessId,
			@NonNull final WFActivityId wfActivityId,
			@Nullable final String scannedBarcode)
	{
		processWFActivity(
				invokerId,
				wfProcessId,
				wfActivityId,
				(wfProcess, wfActivity) -> setScannedBarcode0(wfProcess, wfActivity, scannedBarcode));
	}

	private void setScannedBarcode0(
			@NonNull final WFProcess wfProcess,
			@NonNull final WFActivity wfActivity,
			@Nullable final String scannedBarcode)
	{
		wfActivityHandlersRegistry
				.getFeature(wfActivity.getWfActivityType(), SetScannedBarcodeSupport.class)
				.setScannedBarcode(SetScannedBarcodeRequest.builder()
						.wfProcess(wfProcess)
						.wfActivity(wfActivity)
						.scannedBarcode(scannedBarcode)
						.build());
	}

	public void setUserConfirmation(
			@NonNull final UserId invokerId,
			@NonNull final WFProcessId wfProcessId,
			@NonNull final WFActivityId wfActivityId)
	{
		processWFActivity(
				invokerId,
				wfProcessId,
				wfActivityId,
				this::setUserConfirmation0);
	}

	private void setUserConfirmation0(
			@NonNull final WFProcess wfProcess,
			@NonNull final WFActivity wfActivity)
	{
		wfActivityHandlersRegistry
				.getFeature(wfActivity.getWfActivityType(), UserConfirmationSupport.class)
				.userConfirmed(UserConfirmationRequest.builder()
						.wfProcess(wfProcess)
						.wfActivity(wfActivity)
						.build());
	}

	private void processWFActivity(
			@NonNull final UserId invokerId,
			@NonNull final WFProcessId wfProcessId,
			@NonNull final WFActivityId wfActivityId,
			@NonNull final BiConsumer<WFProcess, WFActivity> processor)
	{
		final WFProcess wfProcess = getWFProcessById(wfProcessId);
		wfProcess.assertHasAccess(invokerId);

		final WFActivity wfActivity = wfProcess.getActivityById(wfActivityId);

		processor.accept(wfProcess, wfActivity);

		updateStatuses(wfProcess);
	}

	private void updateStatuses(@NonNull final WFProcess wfProcess)
	{
		for (final WFActivity wfActivity : wfProcess.getActivitiesInOrder())
		{
			final WFState newActivityStatus = wfActivityHandlersRegistry
					.getHandler(wfActivity.getWfActivityType())
					.computeActivityState(wfProcess, wfActivity);

			wfProcess.setActivityStatus(wfActivity.getId(), newActivityStatus);
		}
	}
}
