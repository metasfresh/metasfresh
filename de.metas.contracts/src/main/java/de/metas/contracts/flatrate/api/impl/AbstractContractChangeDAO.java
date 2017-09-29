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

import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.contracts.flatrate.api.IContractChangeDAO;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.subscription.ISubscriptionBL;

public abstract class AbstractContractChangeDAO implements IContractChangeDAO
{
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
