package de.metas.adempiere.process;

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


import java.util.Iterator;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Location;
import org.compiere.model.Query;

import de.metas.adempiere.service.ILocationBL;
import de.metas.process.SvrProcess;

public class C_Location_Postal_Validate extends SvrProcess
{
	private int cnt_all = 0;
	private int cnt_ok = 0;
	private int cnt_err = 0;

	@Override
	protected void prepare()
	{
	}

	@Override
	protected String doIt() throws Exception
	{
		String whereClause = I_C_Location.COLUMNNAME_IsPostalValidated + "=?";
		Iterator<I_C_Location> it = new Query(getCtx(), I_C_Location.Table_Name, whereClause, null)
				.setParameters(false)
				.setApplyAccessFilterRW(true) // rw=true
				.iterate(I_C_Location.class);

		while (it.hasNext())
		{
			cnt_all++;
			validate(it.next());

			if (cnt_all % 100 == 0)
			{
				log.info("Progress: OK/Error/Total = " + cnt_ok + "/" + cnt_err + "/" + cnt_all);
			}
		}

		return "@Updated@ OK/Error/Total = " + cnt_ok + "/" + cnt_err + "/" + cnt_all;
	}

	private void validate(I_C_Location location)
	{
		try
		{
			de.metas.adempiere.model.I_C_Location loc = InterfaceWrapperHelper.create(location, de.metas.adempiere.model.I_C_Location.class);
			Services.get(ILocationBL.class).validatePostal(loc);
			InterfaceWrapperHelper.save(loc);
			cnt_ok++;
		}
		catch (Exception e)
		{
			addLog("Error on " + location + ": " + e.getLocalizedMessage());
			cnt_err++;
		}
	}

}
