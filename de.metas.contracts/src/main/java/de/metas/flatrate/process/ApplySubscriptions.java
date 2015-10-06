package de.metas.flatrate.process;

/*
 * #%L
 * de.metas.contracts
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


import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;

import de.metas.flatrate.api.ISubscriptionBL;
import de.metas.flatrate.model.I_C_Flatrate_Term;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Abonnement_Auftragsverwaltung_(2009_0015_G36)'>(2009 0015 G36)</a>"
 */
public class ApplySubscriptions extends SvrProcess
{
	@Override
	protected String doIt() throws Exception
	{

		final Timestamp startTime = SystemTime.asTimestamp();

		final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);

		final Properties localCtx = Env.deriveCtx(getCtx());
		Env.setContext(localCtx, Env.CTXNAME_AD_Client_ID, getProcessInfo().getAD_Client_ID());

		final Timestamp currentDate = MiscUtils.removeTime(Env.getContextAsDate(getCtx(), Env.CTXNAME_Date));

		final Trx trx = Trx.get(get_TrxName(), false);
		int errorCount = 0;
		int count = 0;

		final List<I_C_Flatrate_Term> scsToEval = new Query(localCtx, I_C_Flatrate_Term.Table_Name, "", get_TrxName())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_Flatrate_Term.COLUMNNAME_StartDate + "," + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.list(I_C_Flatrate_Term.class);

		addLog("Updating " + scsToEval.size() + " subscription controls");
		for (final I_C_Flatrate_Term sc : scsToEval)
		{

			// addLog("Updating " + sc);
			try
			{
				subscriptionBL.evalCurrentSPs(sc, currentDate);
			}
			catch (AdempiereException e)
			{
				addLog("Error updating " + sc + ": " + e.getMessage());
				errorCount += 1;
			}

			trx.commit(true);
			count += 1;
		}
		addLog(errorCount + " errors on updating subscriptions");
		Trx.get(get_TrxName(), false).commit(true);
		addLog("Updating shipment schedule");
		subscriptionBL.evalDeliveries(getCtx(), get_TrxName());

		addLog("Done after " + TimeUtil.formatElapsed(startTime) + " (evaluated " + count + " terms)");

		return "@Success@";
	}

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{

			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
			{
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
		}
	}
}
