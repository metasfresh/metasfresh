package de.metas.handlingunits.shipmentschedule.api;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.IQuery;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.async.api.IWorkPackageBlockBuilder;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.impl.SizeBasedWorkpackagePrio;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromShipmentSchedules;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;

/**
 * Locks all the given shipments schedules into one big lock, then creates and enqueues workpackages, splitting off locks.
 *
 * TODO there is duplicated code from <code>de.metas.invoicecandidate.api.impl.InvoiceCandidateEnqueuer</code>. Please deduplicate it when there is time. my favorite solution would be to create a
 * "locking item-chump-processor" to do all the magic.
 *
 *
 * @author ts
 *
 */
public class ShipmentScheduleEnqueuer
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	private Properties _ctx;
	private String _trxNameInitial;

	public ShipmentScheduleEnqueuer setContext(final Properties ctx, final String trxName)
	{
		this._ctx = ctx;
		this.setTrxNameInitial(trxName);
		return this;
	}

	/**
	 * Creates async work packages for shipment schedule found by given filter.
	 *
	 * This method will group the shipment schedules by header aggregation key and it will enqueue a working package for each header aggregation key.
	 *
	 * @param ctx
	 * @param adPInstanceId
	 * @param useQtyPickedRecords
	 * @param adPinstanceId may be 0.
	 * @param completeShipments
	 */
	public Result createWorkpackages(
			final int adPInstanceId,
			final IQueryFilter<I_M_ShipmentSchedule> queryFilters,
			final boolean useQtyPickedRecords,
			final boolean completeShipments)
	{
		final String trxNameInitial = getTrxNameInitial();

		final Mutable<Result> result = new Mutable<>();

		trxManager.run(trxNameInitial, new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				final ILock mainLock = acquireLock(adPInstanceId, queryFilters);
				try (final ILockAutoCloseable l = mainLock.asAutocloseableOnTrxClose(localTrxName))
				{
					final Result result0 = createWorkpackages0(
							new PlainContextAware(_ctx, localTrxName),
							queryFilters,
							useQtyPickedRecords,
							completeShipments,
							adPInstanceId,
							mainLock);

					result.setValue(result0);
				}
			}
		});

		return result.getValue();
	}

	private Result createWorkpackages0(final IContextAware localCtx,
			final IQueryFilter<I_M_ShipmentSchedule> queryFilters,
			final boolean useQtyPickedRecords,
			final boolean completeShipments,
			final int adPinstanceId,
			final ILock mainLock)
	{
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule.class, localCtx.getCtx(), ITrx.TRXNAME_None)
				.filter(queryFilters);

		queryBuilder.orderBy()
				.addColumn(de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey, Direction.Ascending, Nulls.Last)
				.addColumn(de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID);

		final Iterator<I_M_ShipmentSchedule> shipmentSchedules = queryBuilder
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_M_ShipmentSchedule.class);

		if (!shipmentSchedules.hasNext())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(localCtx.getCtx(), GenerateInOutFromShipmentSchedules.class);

		final IWorkPackageBlockBuilder blockBuilder = queue
				.newBlock()
				.setAD_PInstance_Creator_ID(adPinstanceId)
				.setContext(localCtx.getCtx());
		IWorkPackageBuilder workpackageBuilder = null;
		String lastHeaderAggregationKey = null;

		boolean doEnqueueCurrentPackage = true;

		final Result result = new Result();

		while (shipmentSchedules.hasNext())
		{
			final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedules.next();

			if (shipmentSchedulePA.isInvalid(shipmentSchedule))
			{
				doEnqueueCurrentPackage = false;
			}

			//
			// Check if we shall close our current workpackage (if any)
			final String headerAggregationKey = shipmentSchedule.getHeaderAggregationKey();
			if (!Check.equals(headerAggregationKey, lastHeaderAggregationKey))
			{
				handleAllSchedsAdded(workpackageBuilder, lastHeaderAggregationKey, doEnqueueCurrentPackage, result);
				workpackageBuilder = null;
				doEnqueueCurrentPackage = true;
			}
			lastHeaderAggregationKey = headerAggregationKey;

			//
			// Create workpackage
			if (workpackageBuilder == null)
			{
				workpackageBuilder = blockBuilder
						.newWorkpackage()
						.setPriority(SizeBasedWorkpackagePrio.INSTANCE)
						.bindToTrxName(localCtx.getTrxName());

				workpackageBuilder
						.parameters()
						.setParameter(GenerateInOutFromShipmentSchedules.PARAM_IsUseQtyPicked, useQtyPickedRecords)
						.setParameter(GenerateInOutFromShipmentSchedules.PARAM_IsCompleteShipments, completeShipments);

				// Create a new locker which will grab the locked invoice candidates from 'mainLock'
				// and it will move them to a new owner which is created per workpackage
				final LockOwner workpackageElementsLockOwner = LockOwner.newOwner("ShipmentScheds_" + shipmentSchedule.getHeaderAggregationKey());
				final ILockCommand workpackageElementsLocker = mainLock
						.split()
						.setOwner(workpackageElementsLockOwner)
						.setAutoCleanup(false); // from this point own we don't want to allow the system to auto clean the locks
				workpackageBuilder.setElementsLocker(workpackageElementsLocker);
			}

			//
			// Enqueue shipmentSchedule to current workpackage
			workpackageBuilder.addElement(shipmentSchedule);
		}

		//
		// Close last workpackage (if any, and if there was no error)
		handleAllSchedsAdded(workpackageBuilder, lastHeaderAggregationKey, doEnqueueCurrentPackage, result);

		return result;
	}

	private void handleAllSchedsAdded(
			IWorkPackageBuilder workpackageBuilder,
			String lastHeaderAggregationKey,
			boolean noSchedsAreToRecompute,
			Result result)
	{
		if (workpackageBuilder == null)
		{
			return; // nothing to do
		}

		if (noSchedsAreToRecompute)
		{
			// while building, we also split the shipment scheduled from the main lock to the lock defined by 'workpackageElementsLocker' (see below)
			workpackageBuilder.build();
			result.incEnqueued();
		}
		else
		{
			Loggables.get().addLog(Services.get(IMsgBL.class).parseTranslation(
					_ctx,
					"@Skip@ @" + I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey + "@=" + lastHeaderAggregationKey + ": "
							+ "@" + I_M_ShipmentSchedule.COLUMNNAME_IsToRecompute + "@ = @Yes@"));
			workpackageBuilder.discard();
			result.incSkipped();
		}
	}

	/** Lock all invoice candidates for selection and return an auto-closable lock. */
	private final ILock acquireLock(final int adPInstanceId, IQueryFilter<I_M_ShipmentSchedule> queryFilters)
	{
		final LockOwner lockOwner = LockOwner.newOwner("ShipmentScheduleEnqueuer", adPInstanceId);
		return lockManager.lock()
				.setOwner(lockOwner)
				// allow these locks to be cleaned-up on server starts.
				// NOTE: when we will add the ICs to workpackages we will move the ICs to another owner and we will also set AutoCleanup=false
				.setAutoCleanup(true)
				.setFailIfAlreadyLocked(true)
				.setSetRecordsByFilter(I_M_ShipmentSchedule.class, queryFilters)
				.acquire();
	}

	public String getTrxNameInitial()
	{
		return _trxNameInitial;
	}

	private void setTrxNameInitial(String _trxNameInitial)
	{
		this._trxNameInitial = _trxNameInitial;
	}

	/**
	 * Contains the enqueuer's result. Right now it's just two counters, but might be extended in future.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 * @task https://metasfresh.atlassian.net/browse/FRESH-342
	 */
	public static class Result
	{
		private int eneuedPackagesCount;
		private int skippedPackagesCount;

		private Result()
		{
		}

		public int getEneuedPackagesCount()
		{
			return eneuedPackagesCount;
		}

		public int getSkippedPackagesCount()
		{
			return skippedPackagesCount;
		}

		private void incEnqueued()
		{
			eneuedPackagesCount++;
		}

		private void incSkipped()
		{
			skippedPackagesCount++;
		}
	}
}
