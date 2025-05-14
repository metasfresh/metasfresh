package de.metas.workflow.rest_api.service;

import de.metas.mobile.application.MobileApplication;
import de.metas.user.UserId;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplicationInfo;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetQuery;
import lombok.NonNull;

import java.util.function.UnaryOperator;

public interface WorkflowBasedMobileApplication extends MobileApplication
{
	@Override
	MobileApplicationId getApplicationId();

	@Override
	@NonNull
	default MobileApplicationInfo customizeApplicationInfo(@NonNull MobileApplicationInfo applicationInfo, @NonNull UserId loggedUserId)
	{
		return MobileApplication.super.customizeApplicationInfo(applicationInfo, loggedUserId);
	}

	WorkflowLaunchersList provideLaunchers(WorkflowLaunchersQuery query);

	WFProcess startWorkflow(WorkflowStartRequest request);

	WFProcess continueWorkflow(WFProcessId wfProcessId, UserId callerId);

	void abort(WFProcessId wfProcessId, UserId callerId);

	void abortAll(UserId callerId);

	default WorkflowLaunchersFacetGroupList getFacets(WorkflowLaunchersFacetQuery query) {return WorkflowLaunchersFacetGroupList.EMPTY;}

	WFProcess getWFProcessById(WFProcessId wfProcessId);

	WFProcess changeWFProcessById(WFProcessId wfProcessId, UnaryOperator<WFProcess> remappingFunction);

	WFProcessHeaderProperties getHeaderProperties(@NonNull final WFProcess wfProcess);
}