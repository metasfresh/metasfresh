package de.metas.distribution.rest_api;

import de.metas.Profiles;
import de.metas.distribution.workflows_api.DistributionMobileApplication;
import de.metas.mobile.application.service.MobileApplicationService;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/distribution")
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class DistributionRestController
{
	@NonNull private final MobileApplicationService mobileApplicationService;
	@NonNull private final DistributionMobileApplication distributionMobileApplication;

	private void assertApplicationAccess()
	{
		mobileApplicationService.assertAccess(distributionMobileApplication.getApplicationId(), Env.getUserRolePermissions());
	}

	@PostMapping("/event")
	public void postEvent(@RequestBody @NonNull final JsonDistributionEvent event)
	{
		assertApplicationAccess();
		distributionMobileApplication.processEvent(event, Env.getLoggedUserId());
	}
}
