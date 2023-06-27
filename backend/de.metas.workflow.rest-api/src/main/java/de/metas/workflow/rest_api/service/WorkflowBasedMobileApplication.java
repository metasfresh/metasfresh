package de.metas.workflow.rest_api.service;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.function.UnaryOperator;

public interface WorkflowBasedMobileApplication extends MobileApplication
{
	@Override
	MobileApplicationId getApplicationId();

	@Override
	@NonNull
	MobileApplicationInfo getApplicationInfo(@NonNull UserId loggedUserId);

	WorkflowLaunchersList provideLaunchers(
			@NonNull final UserId userId,
			@Nullable final GlobalQRCode filterByQRCode,
			@NonNull final QueryLimit suggestedLimit,
			@NonNull final Duration maxStaleAccepted);

	WFProcess startWorkflow(WorkflowStartRequest request);

	WFProcess continueWorkflow(WFProcessId wfProcessId, UserId callerId);

	void abort(WFProcessId wfProcessId, UserId callerId);

	void abortAll(UserId callerId);

	WFProcess getWFProcessById(WFProcessId wfProcessId);

	WFProcess changeWFProcessById(WFProcessId wfProcessId, UnaryOperator<WFProcess> remappingFunction);

	WFProcessHeaderProperties getHeaderProperties(@NonNull final WFProcess wfProcess);
}