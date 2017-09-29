package de.metas.contracts.flatrate.api.impl;

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

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.Query;

import de.metas.contracts.flatrate.exceptions.SubscriptionChangeException;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Contract_Change;

public class ContractChangeDAO extends AbstractContractChangeDAO
{

	@Override
	public I_C_Contract_Change retrieveChangeConditions(
			final I_C_Flatrate_Term term,
			final int newSubscriptionId,
			final Timestamp changeDate)
	{
		final String where =
				X_C_Contract_Change.COLUMNNAME_Action + "='" + X_C_Contract_Change.ACTION_Abowechsel + "' AND " +
						X_C_Contract_Change.COLUMNNAME_C_Flatrate_Transition_ID + "=? AND " +
						"COALESCE(" + X_C_Contract_Change.COLUMNNAME_C_Flatrate_Conditions_ID + ",0) IN (0,?) AND " +
						X_C_Contract_Change.COLUMNNAME_C_Flatrate_Conditions_Next_ID + "=?";

		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final List<I_C_Contract_Change> entries =
				new Query(ctx, I_C_Contract_Change.Table_Name, where, trxName)
						.setParameters(term.getC_Flatrate_Transition_ID(), term.getC_Flatrate_Conditions_ID(), newSubscriptionId)
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.setOrderBy(I_C_Contract_Change.COLUMNNAME_C_Contract_Change_ID)
						.list(I_C_Contract_Change.class);

		final I_C_Contract_Change earliestEntryForRefDate = getEarliestEntryForRefDate(entries, changeDate, term.getEndDate());

		if (earliestEntryForRefDate == null)
		{
			throw new SubscriptionChangeException(term.getC_Flatrate_Conditions_ID(), newSubscriptionId, changeDate);
		}
		return earliestEntryForRefDate;
	}

	@Override
	public I_C_Contract_Change retrieveChangeConditions(
			final I_C_Flatrate_Term term,
			final String newStatus, final Timestamp changeDate)
	{
		// first retrieve all conditions for the given term's C_Flatrate_Conditions and for the requested new status
		final String where =
				I_C_Contract_Change.COLUMNNAME_Action + "='" + X_C_Contract_Change.ACTION_Statuswechsel + "' AND " +
						I_C_Contract_Change.COLUMNNAME_C_Flatrate_Transition_ID + "=? AND " +
						"COALESCE(" + I_C_Contract_Change.COLUMNNAME_C_Flatrate_Conditions_ID + ",0) IN (0,?) AND " +
						I_C_Contract_Change.COLUMNNAME_ContractStatus + "=?";

		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final List<I_C_Contract_Change> entries =
				new Query(ctx, I_C_Contract_Change.Table_Name, where, trxName)
						.setParameters(term.getC_Flatrate_Transition_ID(), term.getC_Flatrate_Conditions_ID(), newStatus)
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.setOrderBy(I_C_Contract_Change.COLUMNNAME_C_Contract_Change_ID)
						.list(I_C_Contract_Change.class);

		final I_C_Contract_Change earliestEntryForRefDate = getEarliestEntryForRefDate(entries, changeDate, term.getEndDate());

		if (earliestEntryForRefDate == null)
		{
			throw new SubscriptionChangeException(term.getC_Flatrate_Conditions_ID(), newStatus, changeDate);
		}
		return earliestEntryForRefDate;
	}
}
