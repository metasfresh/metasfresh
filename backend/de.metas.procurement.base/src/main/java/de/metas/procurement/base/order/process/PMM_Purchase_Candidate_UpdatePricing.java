package de.metas.procurement.base.order.process;

import java.util.Date;
import java.util.Iterator;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IteratorUtils;
import org.compiere.util.Env;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.process.JavaProcess;
import de.metas.procurement.base.IPMMPricingAware;
import de.metas.procurement.base.IPMMPricingBL;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.order.IPMMPurchaseCandidateBL;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Updates selected {@link I_PMM_PurchaseCandidate} prices.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://metasfresh.atlassian.net/browse/FRESH-202
 */
public class PMM_Purchase_Candidate_UpdatePricing extends JavaProcess
{
	// services
	private final transient ILockManager lockManager = Services.get(ILockManager.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IPMMPurchaseCandidateBL pmmPurchaseCandidateBL = Services.get(IPMMPurchaseCandidateBL.class);
	private final transient IPMMPricingBL pmmPricingBL = Services.get(IPMMPricingBL.class);

	// Parameters
	//@Param(parameterName = "DatePromised", parameterTo = false)
	private final Date p_DatePromisedFrom = Env.getDate(Env.getCtx()); // default: today
	//@Param(parameterName = "DatePromised", parameterTo = true)
	private final Date p_DatePromisedTo = null;

	// State
	private ILock _lock;
	private int countProcessed = 0;
	private int countError = 0;

	@Override
	protected String doIt() throws Exception
	{
		Iterator<I_PMM_PurchaseCandidate> candidates = null;
		try
		{
			candidates = retrieveAndLock();
			while (candidates.hasNext())
			{
				final I_PMM_PurchaseCandidate candidate = candidates.next();
				updatePricing(candidate);
			}
		}
		finally
		{
			IteratorUtils.close(candidates);
			closeResources();
		}

		return "@Processed@: " + countProcessed + ", @IsError@: " + countError;
	}

	private void updatePricing(final I_PMM_PurchaseCandidate candidate)
	{
		try
		{
			final IPMMPricingAware pricingAware = pmmPurchaseCandidateBL.asPMMPricingAware(candidate);
			pmmPricingBL.updatePricing(pricingAware);
			
			InterfaceWrapperHelper.save(candidate);

			countProcessed++;
		}
		catch (final Exception e)
		{
			countError++;

			log.warn("Error while processing {}. Ignored", candidate, e);
			addLog("Error on {}: {}", candidate, e.getLocalizedMessage());
		}
	}

	private final Iterator<I_PMM_PurchaseCandidate> retrieveAndLock()
	{
		//
		// Create filter
		final ICompositeQueryFilter<I_PMM_PurchaseCandidate> filter = queryBL.createCompositeQueryFilter(I_PMM_PurchaseCandidate.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(getCtx())
				.addEqualsFilter(I_PMM_PurchaseCandidate.COLUMNNAME_Processed, false)
				.addFilter(lockManager.getNotLockedFilter(I_PMM_PurchaseCandidate.class));
		if (p_DatePromisedFrom != null)
		{
			filter.addCompareFilter(I_PMM_PurchaseCandidate.COLUMN_DatePromised, Operator.GREATER_OR_EQUAL, p_DatePromisedFrom);
		}
		if (p_DatePromisedTo != null)
		{
			filter.addCompareFilter(I_PMM_PurchaseCandidate.COLUMN_DatePromised, Operator.LESS_OR_EQUAL, p_DatePromisedTo);
		}
		log.info("Filter: {}", filter);

		//
		// Lock
		final LockOwner lockOwner = LockOwner.newOwner(getClass().getSimpleName());
		_lock = lockManager.lock()
				.setOwner(lockOwner)
				.setAutoCleanup(true)
				.setFailIfNothingLocked(false)
				.setSetRecordsByFilter(I_PMM_PurchaseCandidate.class, filter)
				.acquire();

		//
		// Retrieve candidates
		return queryBL.createQueryBuilder(I_PMM_PurchaseCandidate.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
				.filter(lockManager.getLockedByFilter(I_PMM_PurchaseCandidate.class, _lock))
				//
				.orderBy()
				.addColumn(I_PMM_PurchaseCandidate.COLUMN_PMM_PurchaseCandidate_ID)
				.endOrderBy()
				//
				.create()
				.iterate(I_PMM_PurchaseCandidate.class);
	}

	private void closeResources()
	{
		final ILock lock = _lock;
		if (lock != null)
		{
			try
			{
				lock.close();
				_lock = null;
			}
			catch (final Exception e)
			{
				log.warn("Failed closing {}. Ignored.", lock);
			}
		}
	}

}
