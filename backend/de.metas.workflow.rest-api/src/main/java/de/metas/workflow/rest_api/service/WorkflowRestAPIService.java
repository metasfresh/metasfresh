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

import com.google.common.collect.ImmutableMap;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeRequest;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeSupport;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationRequest;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationSupport;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetQuery;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Service
public class WorkflowRestAPIService
{
	private static final Logger logger = LogManager.getLogger(WorkflowRestAPIService.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String SYSCONFIG_LaunchersLimit = "WorkflowRestAPIService.LaunchersLimit";
	private static final QueryLimit DEFAULT_LaunchersLimit = QueryLimit.ofInt(20);

	private final MobileApplicationsMap applications;
	private final WFActivityHandlersRegistry wfActivityHandlersRegistry;

	public WorkflowRestAPIService(
			@NonNull final Optional<List<MobileApplication>> applications,
			@NonNull final WFActivityHandlersRegistry wfActivityHandlersRegistry)
	{
		this.applications = MobileApplicationsMap.of(applications);
		logger.info("applications: {}", this.applications);

		this.wfActivityHandlersRegistry = wfActivityHandlersRegistry;
	}

	public Stream<MobileApplicationInfo> streamMobileApplicationInfos(final UserId loggedUserId)
	{
		return applications.stream()
				.map(application -> {
					try
					{
						return application.getApplicationInfo(loggedUserId);
					}
					catch (Exception ex)
					{
						logger.warn("Failed getting application info from {}. Skipped", application, ex);
						return null;
					}
				})
				.filter(Objects::nonNull);
	}

	public WorkflowLaunchersList getLaunchers(@NonNull final WorkflowLaunchersQuery query)
	{
		return getWorkflowBasedMobileApplication(query.getApplicationId())
				.provideLaunchers(query.withLimitIfNotSet(this::getLaunchersLimit));
	}

	public WorkflowLaunchersFacetGroupList getFacets(@NonNull WorkflowLaunchersFacetQuery query)
	{
		return getWorkflowBasedMobileApplication(query.getApplicationId()).getFacets(query);
	}

	private WorkflowBasedMobileApplication getWorkflowBasedMobileApplication(@NonNull final MobileApplicationId applicationId)
	{
		final MobileApplication mobileApplication = applications.getById(applicationId);
		if (mobileApplication instanceof WorkflowBasedMobileApplication)
		{
			return (WorkflowBasedMobileApplication)mobileApplication;
		}
		else
		{
			throw new AdempiereException("Application '" + applicationId + "' is not a workflow based application");
		}
	}

	private Stream<WorkflowBasedMobileApplication> streamWorkflowBasedMobileApplications()
	{
		return applications.stream()
				.filter(app -> app instanceof WorkflowBasedMobileApplication)
				.map(app -> (WorkflowBasedMobileApplication)app);
	}

	private QueryLimit getLaunchersLimit()
	{
		final int limitInt = sysConfigBL.getIntValue(SYSCONFIG_LaunchersLimit, -100);
		return limitInt == -100
				? DEFAULT_LaunchersLimit
				: QueryLimit.ofInt(limitInt);
	}

	public void logout(@NonNull final UserId userId)
	{
		applications.stream()
				.forEach(application -> {
					try
					{
						application.logout(userId);
					}
					catch (Exception ex)
					{
						logger.warn("Application {} failed to loggout. Skipped", application, ex);
					}
				});
	}

	public WFProcess getWFProcessById(@NonNull final WFProcessId wfProcessId)
	{
		return getWorkflowBasedMobileApplication(wfProcessId.getApplicationId())
				.getWFProcessById(wfProcessId);
	}

	public WFProcess continueWFProcess(@NonNull final WFProcessId wfProcessId, @NonNull UserId userId)
	{
		return getWorkflowBasedMobileApplication(wfProcessId.getApplicationId())
				.continueWorkflow(wfProcessId, userId);
	}

	public WFProcess changeWFProcessById(
			@NonNull final WFProcessId wfProcessId,
			@NonNull final UnaryOperator<WFProcess> remappingFunction)
	{
		return getWorkflowBasedMobileApplication(wfProcessId.getApplicationId())
				.changeWFProcessById(wfProcessId, remappingFunction);
	}

	public WFProcess startWorkflow(@NonNull final WorkflowStartRequest request)
	{
		return getWorkflowBasedMobileApplication(request.getApplicationId())
				.startWorkflow(request);
	}

	public void abortWFProcess(@NonNull final WFProcessId wfProcessId, @NonNull final UserId callerId)
	{
		getWorkflowBasedMobileApplication(wfProcessId.getApplicationId())
				.abort(wfProcessId, callerId);
	}

	public void abortAllWFProcesses(@NonNull final UserId callerId)
	{
		streamWorkflowBasedMobileApplications()
				.forEach(application -> abortAllNoFail(application, callerId));
	}

	private static void abortAllNoFail(@NonNull final WorkflowBasedMobileApplication application, final @NonNull UserId callerId)
	{
		try
		{
			application.abortAll(callerId);
		}
		catch (Exception ex)
		{
			logger.warn("Failed aborting all for {}", application, ex);
		}
	}

	@NonNull
	public WFProcessHeaderProperties getHeaderProperties(@NonNull final WFProcess wfProcess)
	{
		return getWorkflowBasedMobileApplication(wfProcess.getId().getApplicationId())
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
			@NonNull final String scannedBarcode)
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
			@NonNull final String scannedBarcode)
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
