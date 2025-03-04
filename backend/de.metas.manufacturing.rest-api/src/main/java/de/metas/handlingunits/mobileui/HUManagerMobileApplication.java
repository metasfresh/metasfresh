package de.metas.handlingunits.mobileui;

import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplication;
import org.springframework.stereotype.Component;

@Component
public class HUManagerMobileApplication implements MobileApplication
{
	public static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("huManager");

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}
}
