package de.metas.handlingunits.trace.interceptor;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HUTraceModuleInterceptor extends AbstractModuleInterceptor
{
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final boolean enabled = sysConfigBL.getBooleanValue("de.metas.handlingunits.trace.interceptor.enabled", false);
		if (!enabled)
		{
			return;
		}
		engine.addModelValidator(new PP_CostCollector(), client);
		engine.addModelValidator(new M_HU_Trx_Hdr(), client);
		engine.addModelValidator(new M_InOut(), client);
		engine.addModelValidator(new M_Movement(), client);
		engine.addModelValidator(new M_ShipmentSchedule_QtyPicked(), client);
	}
}
