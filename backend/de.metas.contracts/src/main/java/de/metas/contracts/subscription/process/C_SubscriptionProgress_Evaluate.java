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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.document.engine.IDocument;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;

/**
 *
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Abonnement_Auftragsverwaltung_(2009_0015_G36)'>(2009 0015 G36)</a>"
 */
public class C_SubscriptionProgress_Evaluate extends JavaProcess
{
	private final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);

	private final Timestamp currentDate = TimeUtil.truncToDay(Env.getContextAsDate(getCtx(), Env.CTXNAME_Date));

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final Timestamp startTime = SystemTime.asTimestamp();
		final int countProcessedTerms = createOrUpdateSubscriptionProgress();

		addLog("Updating shipment schedules");
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(() -> {
			subscriptionBL.evalDeliveries(getCtx(), ITrx.TRXNAME_ThreadInherited);
		});

		addLog("Done after {}" + " (evaluated {} terms)", TimeUtil.formatElapsed(startTime), countProcessedTerms);

		return "@Success@";
	}

	private int createOrUpdateSubscriptionProgress()
	{

		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		try (final IAutoCloseable createMissingScheds = shipmentScheduleBL.postponeMissingSchedsCreationUntilClose())
		{
			return createOrUpdateSubscriptionProgress0();
		}
	}

	private int createOrUpdateSubscriptionProgress0()
	{
		int errorCount = 0;
		int count = 0;

		final Iterator<I_C_Flatrate_Term> termsToEvaluate = retrieveTermsToEvaluate();

		while (termsToEvaluate.hasNext())
		{
			final I_C_Flatrate_Term term = termsToEvaluate.next();

			if (invokeEvalCurrentSPs(term))
			{
				count++;
			}
			else
			{
				errorCount++;
			}
		}
		addLog(errorCount + " errors on updating subscriptions");
		return count;
	}

	private Iterator<I_C_Flatrate_Term> retrieveTermsToEvaluate()
	{
		final Iterator<I_C_Flatrate_Term> termsToEval = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyContextClient(getCtx())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, IDocument.STATUS_Closed, IDocument.STATUS_Completed)
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_StartDate).addColumn(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID).endOrderBy()
				.create()
				.iterate(I_C_Flatrate_Term.class);
		return termsToEval;
	}

	private boolean invokeEvalCurrentSPs(final I_C_Flatrate_Term flatrateTerm)
	{
		final Mutable<Boolean> success = new Mutable<>(false);

		Services.get(ITrxManager.class).run(() -> {
			try
			{
				subscriptionBL.evalCurrentSPs(flatrateTerm, currentDate);
				success.setValue(true);
			}
			catch (final AdempiereException e)
			{
				addLog("Error updating " + flatrateTerm + ": " + e.getMessage());
			}
		});

		return success.getValue();
	}
}
