package de.metas.contracts.impl;

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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.contracts.IContractChangeDAO;
import de.metas.contracts.flatrate.exceptions.SubscriptionChangeException;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Contract_Change;
import de.metas.contracts.subscription.ISubscriptionBL;
import lombok.NonNull;

public abstract class AbstractContractChangeDAO implements IContractChangeDAO
{
	@Override
	public final I_C_Contract_Change retrieveChangeConditions(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final String newStatus,
			@NonNull final Timestamp changeDate)
	{
		final List<I_C_Contract_Change> entries = retrieveAllForTermAndNewStatus(term, newStatus);

		final I_C_Contract_Change earliestEntryForRefDate = getEarliestEntryForRefDate(entries, changeDate, term.getEndDate());

		if (earliestEntryForRefDate == null)
		{
			throw new SubscriptionChangeException(term.getC_Flatrate_Conditions_ID(), newStatus, changeDate);
		}
		return earliestEntryForRefDate;
	}

	private List<I_C_Contract_Change> retrieveAllForTermAndNewStatus(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final String newStatus)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_C_Contract_Change> entries = queryBL.createQueryBuilder(I_C_Contract_Change.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Contract_Change.COLUMN_C_Flatrate_Transition_ID, term.getC_Flatrate_Conditions().getC_Flatrate_Transition_ID())
				.addEqualsFilter(I_C_Contract_Change.COLUMN_Action, X_C_Contract_Change.ACTION_Statuswechsel)
				.addEqualsFilter(I_C_Contract_Change.COLUMN_ContractStatus, newStatus)
				.addInArrayFilter(I_C_Contract_Change.COLUMN_C_Flatrate_Conditions_ID, term.getC_Flatrate_Conditions_ID(), 0, null)
				.orderBy().addColumn(I_C_Contract_Change.COLUMN_C_Contract_Change_ID).endOrderBy()
				.create()
				.list();
		return entries;
	}
	
	/**
	 * If the given list is empty or if there is no entry that has a deadline before the given
	 * <code>referenceDate</code>, then the method returns <code>null</code>.
	 * 
	 * @param entries
	 *            the entries to evaluate. May be empty, but is assumed to be not <code>null</code>
	 * @param referenceDate
	 * @param termEndDate
	 * @return
	 */
	protected final  I_C_Contract_Change getEarliestEntryForRefDate(
			final List<I_C_Contract_Change> entries,
			final Timestamp referenceDate,
			final Timestamp termEndDate)
	{
		Check.assume(entries != null, "Param 'entries' is not null");

		I_C_Contract_Change applicableEntry = null;

		for (final I_C_Contract_Change entry : entries)
		{
			final Timestamp deadLineForEntry =
					Services.get(ISubscriptionBL.class).mkNextDate(entry.getDeadLineUnit(), entry.getDeadLine() * -1, termEndDate);
			if (referenceDate.after(deadLineForEntry))
			{
				// 'changeDate' is after the deadline set for 'entry'
				continue;
			}
			if (applicableEntry == null)
			{
				// we found the first entry that is still before the deadline
				applicableEntry = entry;
				continue;
			}

			final Timestamp deadLineForCurrentApplicableEntry =
					Services.get(ISubscriptionBL.class).mkNextDate(entry.getDeadLineUnit(), entry.getDeadLine() * -1, termEndDate);

			if (deadLineForEntry.before(deadLineForCurrentApplicableEntry))
			{
				applicableEntry = entry;
			}
		}
		return applicableEntry;
	}

}
