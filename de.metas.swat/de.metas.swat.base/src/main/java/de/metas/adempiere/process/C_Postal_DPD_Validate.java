/**
 * 
 */
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
import org.compiere.model.Query;

import de.metas.adempiere.model.I_C_Postal;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.adempiere.service.ILocationBL;
import de.metas.process.SvrProcess;

/**
 * @author tsa
 * 
 */
public class C_Postal_DPD_Validate extends SvrProcess
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
		String whereClause = "(" + I_C_Postal.COLUMNNAME_IsValidDPD + "<>?"
				+ " OR " + I_C_Postal.COLUMNNAME_IsValidDPD + " IS NULL)";
		Iterator<I_C_Postal> it = new Query(getCtx(), I_C_Postal.Table_Name, whereClause, null)
				.setParameters(true)
				.setOnlyActiveRecords(true)
				.setApplyAccessFilterRW(true) // rw=true
				.iterate(I_C_Postal.class);

		while (it.hasNext())
		{
			cnt_all++;
			validateDPD(it.next());

			if (cnt_all % 100 == 0)
			{
				log.info("Progress: OK/Error/Total = " + cnt_ok + "/" + cnt_err + "/" + cnt_all);
			}
		}

		return "@Updated@ OK/Error/Total = " + cnt_ok + "/" + cnt_err + "/" + cnt_all;
	}

	private void validateDPD(I_C_Postal postal)
	{
		try
		{
			validateDPD0(postal);
			InterfaceWrapperHelper.save(postal);
			cnt_ok++;
		}
		catch (Exception e)
		{
			addLog("Error on " + postal + ": " + e.getLocalizedMessage());
			cnt_err++;
		}
	}

	private void validateDPD0(I_C_Postal postal)
	{
		final String countryCode = Services.get(ICountryDAO.class).get(getCtx(), postal.getC_Country_ID()).getCountryCode();
		final String postalCode = postal.getPostal();
		boolean found = Services.get(ILocationBL.class).checkOnDPDRoute(countryCode, postalCode);
		if (found)
		{
			postal.setIsValidDPD(true);
		}
	}
}
