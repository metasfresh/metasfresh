package de.metas.contracts.subscription.impl;

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

import static de.metas.flatrate.model.I_C_SubscriptionProgress.COLUMNNAME_C_Flatrate_Term_ID;
import static de.metas.flatrate.model.I_C_SubscriptionProgress.COLUMNNAME_EventDate;
import static de.metas.flatrate.model.I_C_SubscriptionProgress.COLUMNNAME_EventType;
import static de.metas.flatrate.model.I_C_SubscriptionProgress.COLUMNNAME_SeqNo;
import static de.metas.flatrate.model.X_C_SubscriptionProgress.EVENTTYPE_Lieferung;
import static de.metas.flatrate.model.X_C_SubscriptionProgress.STATUS_Geplant;
import static de.metas.flatrate.model.X_C_SubscriptionProgress.STATUS_Verzoegert;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.Query;
import org.compiere.util.DB;

import de.metas.flatrate.api.impl.AbstractSubscriptionDAO;
import de.metas.flatrate.interfaces.I_C_OLCand;
import de.metas.flatrate.model.I_C_Contract_Term_Alloc;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_SubscriptionProgress;

public class SubscriptionDAO extends AbstractSubscriptionDAO
{
	public static final String SUBSCRIPTION_NO_SP_AT_DATE_1P = "Subscription_NoSPAtDate_1P";
	
	@Override
	public List<I_C_SubscriptionProgress> retrievePlannedAndDelayedDeliveries(
			final Properties ctx,
			final Timestamp date,
			final String trxName)
	{

		final String where = COLUMNNAME_EventType + "='" + EVENTTYPE_Lieferung + "'"
				+ " AND " + I_C_SubscriptionProgress.COLUMNNAME_Status + " IN ('" + STATUS_Geplant + "', '" + STATUS_Verzoegert + "')"
				+ " AND " + COLUMNNAME_EventDate + "<=?";

		final String orderBy = COLUMNNAME_EventDate + ", " + COLUMNNAME_C_Flatrate_Term_ID + ", " + COLUMNNAME_SeqNo;

		return new Query(ctx, I_C_SubscriptionProgress.Table_Name, where, trxName)
				.setParameters(date)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(orderBy)
				.list(I_C_SubscriptionProgress.class);
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveTermsForOLCand(final I_C_OLCand olCand)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

		final String wc = I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + " IN (\n" +
				"  select " + I_C_Contract_Term_Alloc.COLUMNNAME_C_Flatrate_Term_ID + "\n" +
				"  from " + I_C_Contract_Term_Alloc.Table_Name + " cta \n" +
				"  where \n" +
				"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_IsActive + "=" + DB.TO_STRING("Y") + " AND \n" +
				"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_AD_Client_ID + "=" + I_C_Flatrate_Term.Table_Name + "." + I_C_Flatrate_Term.COLUMNNAME_AD_Client_ID + " AND \n" +
				"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_C_OLCand_ID + "=? \n" +
				")";

		return new Query(ctx, I_C_Flatrate_Term.Table_Name, wc, trxName) //
				.setParameters(olCand.getC_OLCand_ID())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.list(I_C_Flatrate_Term.class);

	}

	@Override
	public <T extends I_C_OLCand> List<T> retrieveOLCands(final I_C_Flatrate_Term term, final Class<T> clazz)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final String wc = I_C_OLCand.COLUMNNAME_C_OLCand_ID + " IN (\n" +
				"  select " + I_C_Contract_Term_Alloc.COLUMNNAME_C_OLCand_ID + "\n" +
				"  from " + I_C_Contract_Term_Alloc.Table_Name + " cta \n" +
				"  where \n" +
				"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_IsActive + "=" + DB.TO_STRING("Y") + " AND \n" +
				"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_AD_Client_ID + "=" + I_C_OLCand.Table_Name + "." + I_C_OLCand.COLUMNNAME_AD_Client_ID + " AND \n" +
				"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_C_Flatrate_Term_ID + "=? \n" +
				")";

		return new Query(ctx, I_C_OLCand.Table_Name, wc, trxName) //
				.setParameters(term.getC_Flatrate_Term_ID())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_OLCand.COLUMNNAME_C_OLCand_ID)
				.list(clazz);
	}

	@Override
	public List<I_C_SubscriptionProgress> retrieveNextSPs(
			final I_C_Flatrate_Term term,
			final Timestamp date)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final String where = I_C_SubscriptionProgress.COLUMNNAME_C_Flatrate_Term_ID + "=? AND " +
				I_C_SubscriptionProgress.COLUMNNAME_EventDate + ">=?";

		return new Query(ctx, I_C_SubscriptionProgress.Table_Name, where, trxName)
				.setParameters(term.getC_Flatrate_Term_ID(), date)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_SubscriptionProgress.COLUMNNAME_SeqNo)
				.list(I_C_SubscriptionProgress.class);
	}
}
