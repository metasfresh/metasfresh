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


import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.service.ISponsorBL;

public class ValidateSponsors extends SvrProcess
{

	final static Logger logger = LogManager.getLogger(ValidateSponsors.class);

	private int orgId;

	@Override
	protected String doIt() throws Exception
	{

		final List<I_C_Sponsor> sponsors =
				new Query(getCtx(), I_C_Sponsor.Table_Name, I_C_Sponsor.COLUMNNAME_AD_Org_ID + "=?", get_TrxName())
						.setParameters(orgId)
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.setOrderBy(I_C_Sponsor.COLUMNNAME_SponsorNo)
						.list(I_C_Sponsor.class);

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		for (final I_C_Sponsor sponsor : sponsors)
		{
			final List<I_C_Sponsor_SalesRep> ssrs = new Query(getCtx(), I_C_Sponsor_SalesRep.Table_Name, I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID + "=?", get_TrxName())
					.setParameters(sponsor.getC_Sponsor_ID())
					.setOnlyActiveRecords(true)
					.setClient_ID()
					.setOrderBy(I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_SalesRep_ID)
					.list(I_C_Sponsor_SalesRep.class);
			for (final I_C_Sponsor_SalesRep ssr : ssrs)
			{
				final String errors = sponsorBL.validateSSR(ssr);
				if (Check.isEmpty(errors, true))
				{
					continue;
				}
				addLog("Sponsor " + sponsor.getSponsorNo() + ": " + errors);
				// output only the errors of one ssr per sponsor.
				break;
			}
		}
		return "@Success@";
	}

	@Override
	protected void prepare()
	{
		final Object[] params = readParameters(getCtx(), getParametersAsArray());
		orgId = (Integer)params[0];

	}

	static Object[] readParameters(final Properties ctx, final ProcessInfoParameter[] para)
	{
		int orgId = 0;

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				;
			}
			else if (name.equals("AD_Org_ID"))
			{
				orgId = ((BigDecimal)para[i].getParameter()).intValue();
			}

		}
		return new Object[] { orgId };
	}
}
