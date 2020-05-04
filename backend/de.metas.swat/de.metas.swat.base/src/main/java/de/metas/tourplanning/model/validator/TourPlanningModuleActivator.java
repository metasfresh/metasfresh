package de.metas.tourplanning.model.validator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;

import lombok.NonNull;

/**
 * (MAIN) Tour Planning Module Activator
 * 
 * @author tsa
 *
 */
public class TourPlanningModuleActivator extends AbstractModuleInterceptor
{
	@Override
	protected void registerInterceptors(@NonNull IModelValidationEngine engine)
	{
		engine.addModelValidator(new M_Tour_Instance());
		engine.addModelValidator(new M_DeliveryDay());
		engine.addModelValidator(new M_DeliveryDay_Alloc());

		engine.addModelValidator(new M_TourVersionLine());

		//
		// Main documents integration
		engine.addModelValidator(new de.metas.tourplanning.model.validator.C_Order());
		engine.addModelValidator(new de.metas.tourplanning.model.validator.M_ShipmentSchedule());
		engine.addModelValidator(new de.metas.tourplanning.model.validator.M_ShipperTransportation());
	}

}
