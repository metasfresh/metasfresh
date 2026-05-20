package de.metas.distribution.mobileui.trolley;

import de.metas.distribution.mobileui.DistributionMobileApplication;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.service.DistributionRestService;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.controller.v2.json.JsonTrolleyPendingWorkContribution;
import de.metas.workflow.rest_api.service.TrolleyPendingWorkProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DistributionTrolleyPendingWorkProvider implements TrolleyPendingWorkProvider
{
	@NonNull private final DistributionRestService distributionRestService;

	@Override
	public JsonTrolleyPendingWorkContribution getPendingWork(@NonNull final UserId userId)
	{
		final List<DistributionJob> jobs = distributionRestService.loadActiveJobsAssignedToUser(userId);
		return JsonTrolleyPendingWorkContribution.builder()
				.applicationId(DistributionMobileApplication.APPLICATION_ID.getAsString())
				.openJobsCount(jobs.size())
				.build();
	}
}
