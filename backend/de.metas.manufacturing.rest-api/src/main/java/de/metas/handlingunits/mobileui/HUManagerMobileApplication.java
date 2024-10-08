package de.metas.handlingunits.mobileui;

import de.metas.mobile.application.MobileApplicationId;
import de.metas.workflow.rest_api.service.MobileApplication;
import org.springframework.stereotype.Component;

@Component
public class HUManagerMobileApplication implements MobileApplication
{
	private static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("huManager");

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}
}
