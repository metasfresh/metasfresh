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


import java.sql.SQLException;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.service.ISponsorDAO;

public class CreateSponsors extends JavaProcess
{
	private static final Logger logger = LogManager.getLogger(CreateSponsors.class);

	final static String WHERE_BPARTNERS = //
	" not exists (select * from c_sponsor s where s.c_bpartner_id=C_BPartner.c_bpartner_id)";

	final static String WHERE_SALESREPS = //
	I_C_BPartner.COLUMNNAME_IsSalesRep
			+ "='Y' AND not exists (select * from c_sponsor_salesrep s where s.C_BPartner_ID=C_BPartner.c_bpartner_id)";

	@Override
	protected String doIt() throws Exception
	{

		int count = 0;
		final List<I_C_BPartner> bPartnersWithoutSponsor = new Query(getCtx(), org.compiere.model.I_C_BPartner.Table_Name, CreateSponsors.WHERE_BPARTNERS, get_TrxName())
				.list(I_C_BPartner.class);

		log.info("Going to create " + bPartnersWithoutSponsor.size()
				+ " sponsors for bPartners");

		for (final I_C_BPartner bPartner : bPartnersWithoutSponsor)
		{

			try
			{
				final I_C_Sponsor newSponsor = Services.get(ISponsorDAO.class).createNewForCustomer(bPartner);
				InterfaceWrapperHelper.save(newSponsor);
				CreateSponsors.logger.debug("Created " + newSponsor + " for " + bPartner);
				count++;
				commitIf(count);

			}
			catch (final RuntimeException e)
			{
				addLog("Problem with sponsor for BPartner "
						+ bPartner.getValue() + ": " + e.getMessage());
			}
		}

		addLog(0, SystemTime.asTimestamp(), null, "Created " + count
				+ " sponsors");

		count = 0;
		final List<I_C_BPartner> salesRepsWithoutSsr = new Query(getCtx(), org.compiere.model.I_C_BPartner.Table_Name, CreateSponsors.WHERE_SALESREPS, get_TrxName())
				.list(I_C_BPartner.class);

		log.info("Going to create " + salesRepsWithoutSsr.size()
				+ " ssrs for sales reps");

		for (final I_C_BPartner salesRep : salesRepsWithoutSsr)
		{

			try
			{
				final I_C_Sponsor sponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(salesRep, true);

				final I_C_Sponsor_SalesRep newSsr = InterfaceWrapperHelper.create(getCtx(), I_C_Sponsor_SalesRep.class, get_TrxName());
				newSsr.setC_Sponsor_ID(sponsor.getC_Sponsor_ID());
				newSsr.setC_BPartner_ID(salesRep.getC_BPartner_ID());

				InterfaceWrapperHelper.save(newSsr);
				CreateSponsors.logger.debug("Created " + newSsr + " for " + sponsor);
				count++;
				commitIf(count);

			}
			catch (final RuntimeException e)
			{
				addLog("Problem with SSR for BPartner " + salesRep.getValue()
						+ ": " + e.getMessage());
			}
		}

		addLog(0, SystemTime.asTimestamp(), null, "Created " + count
				+ " sponsor-salesrep mappings");

		return "@Success@";
	}

	/**
	 * Commits transaction if count is a multiple of 1000.
	 * 
	 * @param count
	 * @throws SQLException
	 */
	private void commitIf(final int count) throws SQLException
	{

		if (count % 1000 == 0)
		{
			CreateSponsors.logger.info("Commit trx; count=" + count);
			commitEx();
		}
	}

	@Override
	protected void prepare()
	{
		// nothing to do
	}

}
