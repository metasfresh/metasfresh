package org.adempiere.minventory.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;

import org.adempiere.minventory.api.IInventoryBL;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.util.Env;

public class InventoryBL implements IInventoryBL
{

	public static final String SYSCONFIG_QuickInput_Charge_ID = "de.metas.adempiere.callout.M_Inventory.QuickInput.C_Charge_ID";

	@Override
	public int getDefaultInternalChargeId(Properties ctx)
	{
		return Services.get(ISysConfigBL.class).getIntValue(
				SYSCONFIG_QuickInput_Charge_ID,
				-1, // defaultValue
				Env.getAD_Client_ID(ctx),
				Env.getAD_Org_ID(ctx));
	}
}
