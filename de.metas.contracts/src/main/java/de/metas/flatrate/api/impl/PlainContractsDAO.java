package de.metas.flatrate.api.impl;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.wrapper.IPOJOFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.util.TypedAccessor;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.MTable;
import org.compiere.util.Env;

import de.metas.flatrate.api.IContractsDAO;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_SubscriptionProgress;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

public class PlainContractsDAO implements IContractsDAO
{
	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveCFlatrateTermsWithMissingCandidates(final Properties ctx, final int limit, final String trxName)
	{
		final List<I_C_Flatrate_Term> result = db.getRecords(I_C_Flatrate_Term.class, new IPOJOFilter<I_C_Flatrate_Term>()
		{

			@Override
			public boolean accept(I_C_Flatrate_Term pojo)
			{
				if (!X_C_Flatrate_Term.DOCSTATUS_Completed.equals(pojo.getDocStatus()))
				{
					return false;
				}

				if (!X_C_Flatrate_Term.TYPE_CONDITIONS_Abonnement.equals(pojo.getType_Conditions()))
				{
					return false;
				}

				if (pojo.getC_OrderLine_Term_ID() > 0)
				{
					return false;
				}

				if (notExistsInvoiceCandidate(pojo) == false)
				{
					return false;
				}

				final Timestamp now = SystemTime.asTimestamp();
				if (!isCorrectNoticeDate(pojo, now))
				{
					return false;
				}

				if (!pojo.isActive())
				{
					return false;
				}

				if (pojo.getAD_Client_ID() != 0 && pojo.getAD_Client_ID() != Env.getAD_Client_ID(ctx))
				{
					return false;
				}

				return true;
			}

		});

		Collections.sort(result, new AccessorComparator<I_C_Flatrate_Term, Integer>(
				new ComparableComparator<Integer>(),
				new TypedAccessor<Integer>()
				{

					@Override
					public Integer getValue(Object o)
					{
						return ((I_C_Flatrate_Term)o).getC_Flatrate_Term_ID();
					}
				}));

		if (result.size() >= limit)
		{
			return result.subList(0, limit);
		}

		return result;
	}

	private boolean notExistsInvoiceCandidate(final I_C_Flatrate_Term term)
	{
		final List<I_C_Invoice_Candidate> candidates = db.getRecords(I_C_Invoice_Candidate.class, new IPOJOFilter<I_C_Invoice_Candidate>()
		{

			@Override
			public boolean accept(I_C_Invoice_Candidate pojo)
			{
				if (!pojo.isActive())
				{
					return false;
				}

				if (pojo.getAD_Client_ID() != term.getAD_Client_ID())
				{
					return false;
				}

				if (MTable.getTable_ID(I_C_Flatrate_Term.Table_Name) != pojo.getAD_Table_ID())
				{
					return false;
				}

				if (pojo.getRecord_ID() != term.getC_Flatrate_Term_ID())
				{
					return false;
				}

				return true;
			}
		});

		if (candidates.isEmpty())
		{
			return true; // does not exist
		}

		return false;
	}

	private boolean isCorrectNoticeDate(final I_C_Flatrate_Term term, final Timestamp now)
	{
		if ((now).after(term.getStartDate()))
		{
			return true;
		}
		final I_C_Flatrate_Term predecessor = db.getFirstOnly(I_C_Flatrate_Term.class, new IPOJOFilter<I_C_Flatrate_Term>()
		{

			@Override
			public boolean accept(I_C_Flatrate_Term pojo)
			{
				if (pojo.getC_FlatrateTerm_Next_ID() != term.getC_Flatrate_Term_ID())
				{
					return false;
				}

				return true;
			}

		});

		if (predecessor == null)
		{
			return false;
		}

		if (now.after(predecessor.getNoticeDate()))
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean termHasAPredecessor(final I_C_Flatrate_Term term)
	{
		final int termID = term.getC_Flatrate_Term_ID();

		final I_C_Flatrate_Term predecessor = db.getFirst(I_C_Flatrate_Term.class, new IPOJOFilter<I_C_Flatrate_Term>()
		{

			@Override
			public boolean accept(I_C_Flatrate_Term pojo)
			{
				if (pojo.getC_FlatrateTerm_Next_ID() != termID)
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
		}, new AccessorComparator<I_C_Flatrate_Term, Integer>(
				new ComparableComparator<Integer>(),
				new TypedAccessor<Integer>()
				{

					@Override
					public Integer getValue(Object o)
					{
						return ((I_C_Flatrate_Term)o).getC_Flatrate_Term_ID();
					}
				}));

		if (predecessor != null)
		{
			return true;
		}

		return false;
	}

	@Override
	public BigDecimal retrieveSubscriptionProgressQtyForTerm(final I_C_Flatrate_Term term)
	{
		BigDecimal qty = Env.ZERO;

		final List<I_C_SubscriptionProgress> progressList = db.getRecords(I_C_SubscriptionProgress.class, new IPOJOFilter<I_C_SubscriptionProgress>()
		{

			@Override
			public boolean accept(I_C_SubscriptionProgress pojo)
			{
				if (pojo.getC_Flatrate_Term_ID() != term.getC_Flatrate_Term_ID())
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
		for (I_C_SubscriptionProgress progress : progressList)
		{
			qty = qty.add(progress.getQty());
		}

		return qty;
	}
}
