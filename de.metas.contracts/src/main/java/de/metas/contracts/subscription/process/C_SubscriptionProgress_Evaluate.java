package de.metas.contracts.subscription.process;

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
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;

import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.process.JavaProcess;

/**
 *
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Abonnement_Auftragsverwaltung_(2009_0015_G36)'>(2009 0015 G36)</a>"
 */
public class C_SubscriptionProgress_Evaluate extends JavaProcess
{
	private final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);

	@Override
	protected String doIt() throws Exception
	{

		final Timestamp startTime = SystemTime.asTimestamp();

		final Properties localCtx = Env.deriveCtx(getCtx());
		Env.setContext(localCtx, Env.CTXNAME_AD_Client_ID, getProcessInfo().getAD_Client_ID());

		final Timestamp currentDate = MiscUtils.removeTime(Env.getContextAsDate(getCtx(), Env.CTXNAME_Date));

		final Trx trx = Trx.get(get_TrxName(), false);
		int errorCount = 0;
		int count = 0;

		final Iterator<I_C_Flatrate_Term> termsToEval = Services.get(IQueryBL.class).createQueryBuilder(I_C_Flatrate_Term.class, new PlainContextAware(localCtx, getTrxName()))
				.addOnlyContextClient(localCtx)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, X_C_Flatrate_Term.TYPE_CONDITIONS_Abonnement)
				.addInArrayOrAllFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, DocAction.STATUS_Closed, DocAction.STATUS_Completed)
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_StartDate).addColumn(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID).endOrderBy()
				.create()
				.iterate(I_C_Flatrate_Term.class);

		while(termsToEval.hasNext())
		{
			final I_C_Flatrate_Term sc = termsToEval.next();

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
}
