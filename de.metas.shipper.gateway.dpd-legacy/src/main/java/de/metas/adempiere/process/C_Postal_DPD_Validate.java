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
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Postal;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.dpd.model.I_DPD_Route;
import de.metas.process.JavaProcess;

/**
 * @author tsa
 * 
 */
public class C_Postal_DPD_Validate extends JavaProcess
{
	private static final String SQL_WhereClause_DPD_Route_ByCountryAndPostal =
			I_DPD_Route.COLUMNNAME_CountryCode + "=?"
					+ " AND " + I_DPD_Route.COLUMNNAME_BeginPostCode + " IS NOT NULL"
					+ " AND LPAD(?, 20, '0') BETWEEN LPAD(" + I_DPD_Route.COLUMNNAME_BeginPostCode + ", 20, '0')"
					+ " AND LPAD(COALESCE(" + I_DPD_Route.COLUMNNAME_EndPostCode + ", " + I_DPD_Route.COLUMNNAME_BeginPostCode + "), 20, '0')";

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
		boolean found = checkOnDPDRoute(countryCode, postalCode);
		if (found)
		{
			postal.setIsValidDPD(true);
		}
	}
	
	/**
	 * Check if given country/postal code exists in DPD_Route table
	 * 
	 * @param countryCode
	 * @param postal
	 * @return
	 */
	private boolean checkOnDPDRoute(String countryCode, String postal)
	{
		if (postal == null)
			return false;

		postal = postal.trim();
		log.debug("Checking: postal=" + postal + ", countryCode=" + countryCode);

		boolean found = new Query(Env.getCtx(), I_DPD_Route.Table_Name, SQL_WhereClause_DPD_Route_ByCountryAndPostal, null)
				.setParameters(countryCode, postal)
				.match();
		log.debug("Found: " + found);
		return found;
	}

}
