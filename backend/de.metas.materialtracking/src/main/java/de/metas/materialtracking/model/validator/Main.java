package de.metas.materialtracking.model.validator;

/*
 * #%L
 * de.metas.materialtracking
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

import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.util.Services;

public class Main extends AbstractModuleInterceptor
{
	@Override
	protected void onAfterInit()
	{
		// Enable material tracking module
		Services.get(IMaterialTrackingBL.class).setEnabled(true);
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		engine.addModelValidator(new M_Material_Tracking());
		engine.addModelValidator(new M_Material_Tracking_Ref());

		engine.addModelValidator(new C_Order());
		engine.addModelValidator(new M_InOut());
		engine.addModelValidator(new M_InOutLine()); // task 07734
		engine.addModelValidator(new C_Invoice_Candidate());
		engine.addModelValidator(new C_Invoice_Detail()); // task 09533
		engine.addModelValidator(new C_Invoice());
		engine.addModelValidator(new C_AllocationHdr());
		engine.addModelValidator(new M_PriceList_Version());
		engine.addModelValidator(PP_Order.instance);
		engine.addModelValidator(new PP_Cost_Collector());
	}
}
