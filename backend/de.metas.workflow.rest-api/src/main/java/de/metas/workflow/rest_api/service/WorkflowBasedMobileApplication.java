package de.metas.workflow.rest_api.service;

import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;

import java.time.Duration;
import java.util.function.UnaryOperator;

public interface WorkflowBasedMobileApplication extends MobileApplication
{
	@Override
	@NonNull
	MobileApplicationInfo getApplicationInfo();

	WorkflowLaunchersList provideLaunchers(
			@NonNull final UserId userId,
			@NonNull final QueryLimit suggestedLimit,
			@NonNull final Duration maxStaleAccepted);

	WFProcess startWorkflow(WorkflowStartRequest request);

	void abort(WFProcessId wfProcessId, UserId callerId);

	void abortAll(UserId callerId);

	WFProcess getWFProcessById(WFProcessId wfProcessId);

	WFProcess changeWFProcessById(WFProcessId wfProcessId, UnaryOperator<WFProcess> remappingFunction);

	WFProcessHeaderProperties getHeaderProperties(@NonNull final WFProcess wfProcess);
}
