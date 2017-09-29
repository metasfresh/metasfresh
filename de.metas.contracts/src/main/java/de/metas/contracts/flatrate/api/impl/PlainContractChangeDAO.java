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
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.wrapper.IPOJOFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.util.TypedAccessor;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.compiere.util.Env;

import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Contract_Change;

public class PlainContractChangeDAO extends AbstractContractChangeDAO
{
	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
	}

	@Override
	public I_C_Contract_Change retrieveChangeConditions(I_C_Flatrate_Term term, int newSubscriptionId, Timestamp changeDate)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public I_C_Contract_Change retrieveChangeConditions(final I_C_Flatrate_Term term, final String newStatus, final Timestamp changeDate)
	{
		/*
		 * 
		 * final I_C_Contract_Change earliestEntryForRefDate = getEarliestEntryForRefDate(entries, changeDate, term.getEndDate());
		 * 
		 * if (earliestEntryForRefDate == null) { throw new SubscriptionChangeException(term.getC_Flatrate_Conditions_ID(), newStatus, changeDate); } return earliestEntryForRefDate;
		 */
		final List<I_C_Contract_Change> contractChangeEntries = db.getRecords(I_C_Contract_Change.class, new IPOJOFilter<I_C_Contract_Change>()
		{

			@Override
			public boolean accept(I_C_Contract_Change pojo)
			{
				if (!X_C_Contract_Change.ACTION_Statuswechsel.equals(pojo.getAction()))
				{
					return false;
				}

				if (pojo.getC_Flatrate_Transition_ID() != term.getC_Flatrate_Transition_ID())
				{
					return false;
				}

				final int conditionsID = pojo.getC_Flatrate_Conditions_ID();
				if ((conditionsID != 0) && (conditionsID != term.getC_Flatrate_Conditions_ID()))
				{
					return false;
				}

				if (newStatus != pojo.getContractStatus())
				{
					return false;
				}

				if (!pojo.isActive())
				{
					return false;
				}

				if (pojo.getAD_Client_ID() != 0 && pojo.getAD_Client_ID() != Env.getAD_Client_ID(Env.getCtx()))
				{
					return false;
				}

				return true;
			}

		});

		Collections.sort(contractChangeEntries, new AccessorComparator<I_C_Contract_Change, Integer>(
				new ComparableComparator<Integer>(),
				new TypedAccessor<Integer>()
				{

					@Override
					public Integer getValue(Object o)
					{
						return ((I_C_Contract_Change)o).getC_Contract_Change_ID();
					}
				}));

		final I_C_Contract_Change earliestEntryForRefDate = getEarliestEntryForRefDate(contractChangeEntries, changeDate, term.getEndDate());

//		if (earliestEntryForRefDate == null)
//		{
//			throw new SubscriptionChangeException(term.getC_Flatrate_Conditions_ID(), newStatus, changeDate);
//		}
		return earliestEntryForRefDate;
	}
}
