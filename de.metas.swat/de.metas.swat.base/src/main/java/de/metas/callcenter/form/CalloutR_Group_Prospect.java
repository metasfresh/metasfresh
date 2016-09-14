/**
 * 
 */
package de.metas.callcenter.form;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;

import de.metas.callcenter.model.I_R_Group_Prospect;

/**
 * @author Teo Sarca, teo.sarca@gmail.com
 *
 */
public class CalloutR_Group_Prospect extends CalloutEngine
{
	public String bPartner (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		I_R_Group_Prospect gp = InterfaceWrapperHelper.create(mTab, I_R_Group_Prospect.class);
		
		int AD_User_ID = 0;
		if (gp.getC_BPartner_ID() == Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID"))
		{
			AD_User_ID = Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "AD_User_ID");
		}
		if (AD_User_ID > 0)
			gp.setAD_User_ID(AD_User_ID);
		
		return "";
	}

}
