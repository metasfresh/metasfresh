package de.metas.commission.process;

/*
 * #%L
 * de.metas.commission.base
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


import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.util.CommissionTools;

public class CheckTreeResetDepths extends JavaProcess
{

	final static Logger logger = LogManager.getLogger(CheckTreeResetDepths.class);

	@Override
	protected String doIt() throws Exception
	{
		if (findCycles() > 1)
		{
			return "@Error@";
		}

		return "@Success@";
	}

	private int findCycles()
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final List<I_C_Sponsor_SalesRep> ssrs =
				new Query(getCtx(), I_C_Sponsor_SalesRep.Table_Name, "C_Sponsor_Parent_ID>0", get_TrxName())
						.setOnlyActiveRecords(true)
						.list(I_C_Sponsor_SalesRep.class);

		int cylcesFound = 0;
		int ssrsChecked = 0;
		addLog("Checking " + ssrs.size() + " records for cycles.");

		for (final I_C_Sponsor_SalesRep ssr : ssrs)
		{
			final List<List<I_C_Sponsor_SalesRep>> pathsToSSR = sponsorBL.checkIfChildSponsor(ssr, ssr.getC_Sponsor_Parent_ID());

			if (!pathsToSSR.isEmpty())
			{
				addLog(CommissionTools.mkParentChildMessage(pathsToSSR, ssr, getCtx(), get_TrxName()));
				cylcesFound += 1;
			}

			ssrsChecked += 1;
			if (ssrsChecked % 1000 == 0)
			{
				CheckTreeResetDepths.logger.info("Checked " + ssrsChecked + " records of " + ssrs.size());
			}
		}
		return cylcesFound;
	}

	@Override
	protected void prepare()
	{
		// nothing to do
	}
}
