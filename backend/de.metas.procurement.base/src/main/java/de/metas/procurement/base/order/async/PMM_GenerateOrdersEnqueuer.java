package de.metas.procurement.base.order.async;

import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.util.List;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Mass enqueue {@link I_PMM_PurchaseCandidate} records to be processed and purchase orders to be generated.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class PMM_GenerateOrdersEnqueuer
{
	@FunctionalInterface
	public static interface ConfirmationCallback
	{
		boolean confirmRecordsToProcess(final int count);
	}

	private final transient IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final transient ILockManager lockManager = Services.get(ILockManager.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	//
	// Parameters
	private IQueryFilter<I_PMM_PurchaseCandidate> candidatesFilter = null;
	private ConfirmationCallback confirmationCallback = null;

	/* package */ PMM_GenerateOrdersEnqueuer()
	{
	}

	public PMM_GenerateOrdersEnqueuer filter(final IQueryFilter<I_PMM_PurchaseCandidate> candidatesFilter)
	{
		this.candidatesFilter = candidatesFilter;
		return this;
	}

	public PMM_GenerateOrdersEnqueuer confirmRecordsToProcess(final ConfirmationCallback confirmationCallback)
	{
		this.confirmationCallback = confirmationCallback;
		return this;
	}

	/**
	 * @return number of records enqueued
	 */
	public int enqueue()
	{
		final IQuery<I_PMM_PurchaseCandidate> query = createRecordsToProcessQuery();
		if (!confirmRecordsToProcess(query))
		{
			return 0;
		}

		final List<I_PMM_PurchaseCandidate> candidates = query.list();
		if (candidates.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final LockOwner lockOwner = LockOwner.newOwner(getClass().getSimpleName());
		final ILockCommand elementsLocker = lockManager.lock()
				.setOwner(lockOwner)
				.setAutoCleanup(false);

		workPackageQueueFactory
				.getQueueForEnqueuing(Env.getCtx(), PMM_GenerateOrders.class)
				.newBlock()
				.newWorkpackage()
				.setElementsLocker(elementsLocker)
				.addElements(candidates)
				.build();

		return candidates.size();
	}

	private boolean confirmRecordsToProcess(final IQuery<I_PMM_PurchaseCandidate> query)
	{
		if (confirmationCallback == null)
		{
			return true; // OK, autoconfirmed
		}

		//
		// Fail if there is nothing to update
		final int countToProcess = query.count();
		if (countToProcess <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}

		//
		// Ask the callback if we shall process
		return confirmationCallback.confirmRecordsToProcess(countToProcess);
	}

	private final IQuery<I_PMM_PurchaseCandidate> createRecordsToProcessQuery()
	{
		final IQueryBuilder<I_PMM_PurchaseCandidate> queryBuilder = queryBL.createQueryBuilder(I_PMM_PurchaseCandidate.class);

		if (candidatesFilter != null)
		{
			queryBuilder.filter(candidatesFilter);
		}

		return queryBuilder
				.addOnlyActiveRecordsFilter()
				.filter(lockManager.getNotLockedFilter(I_PMM_PurchaseCandidate.class))
				.addCompareFilter(I_PMM_PurchaseCandidate.COLUMNNAME_QtyToOrder, CompareQueryFilter.Operator.GREATER, BigDecimal.ZERO)
				.orderBy()
				.addColumn(I_PMM_PurchaseCandidate.COLUMNNAME_AD_Org_ID)
				.addColumn(I_PMM_PurchaseCandidate.COLUMNNAME_M_Warehouse_ID)
				.addColumn(I_PMM_PurchaseCandidate.COLUMNNAME_C_BPartner_ID)
				.addColumn(I_PMM_PurchaseCandidate.COLUMNNAME_DatePromised)
				.addColumn(I_PMM_PurchaseCandidate.COLUMNNAME_M_PricingSystem_ID)
				.addColumn(I_PMM_PurchaseCandidate.COLUMNNAME_C_Currency_ID)
				.endOrderBy()
				//
				.create();
	}

}
