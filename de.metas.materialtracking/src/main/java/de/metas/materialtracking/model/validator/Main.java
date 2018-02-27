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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;

import de.metas.materialtracking.IMaterialTrackingBL;

public class Main extends AbstractModuleInterceptor
{
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		super.onInit(engine, client);

		// Enable material tracking module
		Services.get(IMaterialTrackingBL.class).setEnabled(true);
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelValidator(new M_Material_Tracking(), client);
		engine.addModelValidator(new M_Material_Tracking_Ref(), client);

		engine.addModelValidator(new C_Order(), client);
		engine.addModelValidator(new M_InOut(), client);
		engine.addModelValidator(new M_InOutLine(), client); // task 07734
		engine.addModelValidator(new C_Invoice_Candidate(), client);
		engine.addModelValidator(new C_Invoice_Detail(), client); // task 09533
		engine.addModelValidator(new C_Invoice(), client);
		engine.addModelValidator(new C_AllocationHdr(), client);
		engine.addModelValidator(new M_PriceList_Version(), client);
		engine.addModelValidator(PP_Order.instance, client);
		engine.addModelValidator(new PP_Cost_Collector(), client);
	}
}
