package de.metas.manufacturing.mobileui;

import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.service.MobileApplication;
import org.springframework.stereotype.Component;

@Component
public class WorkstationManager implements MobileApplication
{
	public static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("workstationManager");

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}
}
