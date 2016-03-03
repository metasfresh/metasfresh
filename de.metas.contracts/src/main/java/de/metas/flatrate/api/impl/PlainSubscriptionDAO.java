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


import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.util.TypedAccessor;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.compiere.util.Env;

import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.flatrate.interfaces.I_C_OLCand;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_SubscriptionProgress;

public class PlainSubscriptionDAO implements ISubscriptionDAO
{
	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
	}

	@Override
	public I_C_SubscriptionProgress retrieveNextSP(I_C_Flatrate_Term control, Timestamp date, int seqNo)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<I_C_SubscriptionProgress> retrieveNextSPs(I_C_Flatrate_Term term, Timestamp date)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<I_C_SubscriptionProgress> retrieveSubscriptionProgress(final I_C_Flatrate_Term term)
	{
		final List<I_C_SubscriptionProgress> result = db.getRecords(I_C_SubscriptionProgress.class, new IQueryFilter<I_C_SubscriptionProgress>()
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

		Collections.sort(result, new AccessorComparator<I_C_SubscriptionProgress, Integer>(
				new ComparableComparator<Integer>(),
				new TypedAccessor<Integer>()
				{

					@Override
					public Integer getValue(Object o)
					{
						return ((I_C_SubscriptionProgress)o).getSeqNo();
					}
				}));

		Collections.reverse(result); // order by desc

		return result;
	}

	@Override
	public List<I_C_SubscriptionProgress> retrievePlannedAndDelayedDeliveries(Properties ctx, Timestamp date, String trxName)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public I_C_Flatrate_Term retrieveTermForOl(I_C_OrderLine ol)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveTermsForOLCand(I_C_OLCand olCand)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public I_C_Flatrate_Conditions retrieveSubscription(Timestamp date, int bPartnerId, int productId, int locationId, String trxName)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public I_C_SubscriptionProgress insertNewDelivery(I_C_SubscriptionProgress predecessor)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends I_C_OLCand> List<T> retrieveOLCands(I_C_Flatrate_Term term, Class<T> clazz)
	{
		throw new UnsupportedOperationException();
	}

}
