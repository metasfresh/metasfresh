package de.metas.workflow.execution.approval.strategy.type_handlers;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.job.JobId;
import de.metas.job.JobService;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyLine;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyService;
import de.metas.workflow.execution.approval.strategy.UsersToApproveList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobHandler implements DocApprovalStrategyTypeHandler
{
	private static final AdMessageKey MSG_NoUsersFoundForJob = AdMessageKey.of("DocApprovalStrategyTypeHandler.NoUsersFoundForJob");

	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final JobService jobService;

	@Override
	public UsersToApproveList getUsersToApprove(
			@NonNull final DocApprovalStrategyService.GetUsersToApproveRequest request_NOTUSED,
			@NonNull final DocApprovalStrategyLine strategyLine)
	{
		final JobId jobId = strategyLine.getJobIdNotNull();
		final ImmutableSet<UserId> userIds = userBL.getUserIdsByJobId(jobId);
		if (userIds.isEmpty())
		{
			final String jobName = jobService.getById(jobId).getName();
			throw new AdempiereException(MSG_NoUsersFoundForJob, jobName);
		}

		return UsersToApproveList.ofCollection(userIds);
	}
}
