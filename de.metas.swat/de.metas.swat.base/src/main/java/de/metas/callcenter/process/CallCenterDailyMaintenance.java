/**
 * 
 */
package de.metas.callcenter.process;

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


import org.compiere.model.Query;
import org.compiere.model.X_R_Group;
import org.compiere.util.DB;

import de.metas.callcenter.model.BundleUtil;
import de.metas.callcenter.model.I_R_Group_Prospect;
import de.metas.callcenter.model.MRGroupProspect;
import de.metas.process.SvrProcess;

/**
 * Call Center - Run Daily Maintenance Tasks
 * @author Teo Sarca, teo.sarca@gmail.com
 *
 */
public class CallCenterDailyMaintenance extends SvrProcess
{
	@Override
	protected void prepare()
	{
	}
	
	@Override
	protected String doIt() throws Exception
	{
		expireLocks();
		updateCCM_Bundle_Status();
		return "Ok";
	}
	
	private int expireLocks()
	{
		final String sql = "UPDATE "+I_R_Group_Prospect.Table_Name+" SET "
			+" "+I_R_Group_Prospect.COLUMNNAME_Locked+"=?"
			+","+I_R_Group_Prospect.COLUMNNAME_LockedBy+"=?"
			+","+I_R_Group_Prospect.COLUMNNAME_LockedDate+"=?"
			+" WHERE "
			+" "+I_R_Group_Prospect.COLUMNNAME_AD_Client_ID+"=?"
			+" AND "+I_R_Group_Prospect.COLUMNNAME_Locked+"=?"
			+" AND addDays("+I_R_Group_Prospect.COLUMNNAME_LockedDate+",?) > getDate()"
		;
		int expireDays = (int)Math.round( (double)MRGroupProspect.LOCK_EXPIRE_MIN / (double)60 + 0.5 );
		int count = DB.executeUpdateEx(sql,
				new Object[]{false, null, null, getAD_Client_ID(), true, expireDays},
				get_TrxName());
		addLog("Unlocked #"+count);
		return count;
	}
	
	private void updateCCM_Bundle_Status()
	{
		int[] ids = new Query(getCtx(), X_R_Group.Table_Name, null, get_TrxName())
		//.setOnlyActiveRecords(true) // check all bundles
		.setClient_ID()
		.getIDs();
		int count = 0;
		for (int R_Group_ID : ids)
		{
			boolean updated = BundleUtil.updateCCM_Bundle_Status(R_Group_ID, get_TrxName());
			if (updated)
				count++;
		}
		addLog("@Updated@ @CCM_Bundle_Status@ #"+count);
	}
}
