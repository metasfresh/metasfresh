package de.metas.handlingunits.mobileui;

import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.service.MobileApplication;
import org.springframework.stereotype.Component;

@Component
public class HUManagerMobileApplication implements MobileApplication
{
	private static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("huManager");

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}
}
