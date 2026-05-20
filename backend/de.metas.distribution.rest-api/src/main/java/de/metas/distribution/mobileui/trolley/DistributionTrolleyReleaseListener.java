package de.metas.distribution.mobileui.trolley;

import de.metas.distribution.mobileui.job.service.DistributionRestService;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.service.TrolleyReleaseListener;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DistributionTrolleyReleaseListener implements TrolleyReleaseListener
{
	@NonNull private final DistributionRestService distributionRestService;

	@Override
	public void onTrolleyReleased(@NonNull final UserId userId)
	{
		distributionRestService.abortAll(userId);
	}
}
