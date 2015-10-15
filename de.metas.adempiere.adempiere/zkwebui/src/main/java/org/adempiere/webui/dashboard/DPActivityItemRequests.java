package org.adempiere.webui.dashboard;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_R_Request;
import org.compiere.model.MTable;
import org.compiere.util.Env;

public class DPActivityItemRequests extends AbstractDPActivityItem
{
	public static final String SYSCONFIG_ShowRequests = "org.adempiere.webui.dashboard.DPActivities.ShowRequests";
	public final static String SYSCONFIG_RequestsWindowId = "org.adempiere.webui.dashboard.DPActivities.Requests.AD_Window_ID";
	
	@Override
	public boolean isAvailable()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_ShowRequests, true);
	}

	public Properties getCtx()
	{
		return Env.getCtx();
	}

	@Override
	public String getLabel()
	{
		return I_R_Request.COLUMNNAME_R_Request_ID;
	}

	@Override
	public boolean isDynamicLabel()
	{
		return false;
	}

	@Override
	public String getDescription()
	{
		return I_R_Request.COLUMNNAME_R_Request_ID;
	}

	@Override
	public String getImage()
	{
		return "/images/Request16.png";
	}

	@Override
	public int getAD_Window_ID()
	{
		final int adClientId = Env.getAD_Client_ID(getCtx());
		final int adOrgId = Env.getAD_Org_ID(getCtx());
		int adWindowId = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_RequestsWindowId, -1, adClientId, adOrgId);
		if (adWindowId > 0)
		{
			return adWindowId;
		}
		
		adWindowId = MTable.get(getCtx(), I_R_Request.Table_Name).getAD_Window_ID();
		if (adWindowId > 0)
		{
			return adWindowId;
		}
		
		return -1;
	}

	@Override
	public String getTableName()
	{
		return I_R_Request.Table_Name;
	}

	@Override
	public String getWhereClause()
	{
		final Properties ctx = getCtx();
//		final int adUserId = Env.getAD_User_ID(ctx);
//		final int adRoleId = Env.getAD_Role_ID(ctx);

		final String requestsWhereClause =
				// 03414: Display all requests for login org, no matter to whom they are assigned
				// "(SalesRep_ID=" + adUserId + " OR AD_Role_ID=" + adRoleId + ")"
				" AD_Org_ID="+Env.getAD_Org_ID(ctx)
				//
				+ " AND Processed='N'"
				+ " AND (DateNextAction IS NULL OR TRUNC(DateNextAction) <= TRUNC(now()))"
				+ " AND (R_Status_ID IS NULL OR R_Status_ID IN (SELECT R_Status_ID FROM R_Status WHERE IsClosed='N'))";
		return requestsWhereClause;
	}

	@Override
	public boolean isFilterLoginOrgOnly()
	{
		// 04051 Dashboard Button Konsistenzprüfung (2013031510000021)
		return true;
	}
}
