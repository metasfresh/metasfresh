package de.metas.pos.mobile;

import de.metas.mobile.application.MobileApplicationId;
import de.metas.workflow.rest_api.service.MobileApplication;
import org.springframework.stereotype.Component;

@Component
public class POSMobileApplication implements MobileApplication
{
	private static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("pos");

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}
}
