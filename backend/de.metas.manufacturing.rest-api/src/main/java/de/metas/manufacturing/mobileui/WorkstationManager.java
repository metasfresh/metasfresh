package de.metas.manufacturing.mobileui;

import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplication;
import org.springframework.stereotype.Component;

@Component
public class WorkstationManager implements MobileApplication
{
	public static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("workstationManager");

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}
}
