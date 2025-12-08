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

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.async.AsyncBatchId;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.api.IEnqueueResult;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.impl.SizeBasedWorkpackagePrio;
import de.metas.common.util.EmptyUtil;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromShipmentSchedules;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAwares;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.InArrayQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import static de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWorkPackageParameters.PARAM_PREFIX_AdvisedShipmentDocumentNo;
import static de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWorkPackageParameters.PARAM_QtyToDeliver_Override;

/**
 * Locks all the given shipments schedules into one big lock, then creates and enqueues workpackages, splitting off locks.
 * <p>
 * TODO there is duplicated code from <code>de.metas.invoicecandidate.api.impl.InvoiceCandidateEnqueuer</code>. Please deduplicate it when there is time. my favorite solution would be to create a
 * "locking item-chump-processor" to do all the magic.
 */
public class ShipmentScheduleEnqueuer
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleEnqueuer.class);
	private static final AdMessageKey MSG_NO_VALID_RECORDS = AdMessageKey.of("de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords");

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IShipmentScheduleInvalidateBL invalidSchedulesService = Services.get(IShipmentScheduleInvalidateBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	private final Properties _ctx;
	private final String _trxNameInitial;

	private ShipmentScheduleEnqueuer(@NonNull final Properties ctx, @Nullable final String trxName)
	{
		this._ctx = ctx;
		this._trxNameInitial = trxName;
	}

	public static ShipmentScheduleEnqueuer newInstance()
	{
		return new ShipmentScheduleEnqueuer(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
	}

	public static ShipmentScheduleEnqueuer newInstance(@NonNull final Properties ctx, @Nullable final String trxName)
	{
		return new ShipmentScheduleEnqueuer(ctx, trxName);
	}

	/**
	 * Creates async work packages for shipment schedule found by given filter.
	 * <p>
	 * This method will group the shipment schedules by header aggregation key and it will enqueue a working package for each header aggregation key.
	 */
	public Result createWorkpackages(@NonNull final ShipmentScheduleWorkPackageParameters workPackageParameters)
	{
		final String trxNameInitial = getTrxNameInitial();

		final Mutable<Result> result = new Mutable<>();

		trxManager.run(trxNameInitial, new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName)
			{
				final ILock mainLock = acquireMainLock(workPackageParameters);
				try (final ILockAutoCloseable ignore = mainLock.asAutoCloseable())
				{
					final Result result0 = createWorkpackages0(
							PlainContextAware.newWithTrxName(_ctx, localTrxName),
							workPackageParameters,
							mainLock);
					result.setValue(result0);
				}
			}
		});

		return result.getValue();
	}

	@NonNull
	private Result createWorkpackages0(
			@NonNull final IContextAware localCtx,
			@NonNull final ShipmentScheduleWorkPackageParameters workPackageParameters,
			@NonNull final ILock mainLock)
	{
		final Iterator<I_M_ShipmentSchedule> shipmentSchedules = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class, localCtx.getCtx(), ITrx.TRXNAME_None)
				.filter(workPackageParameters.getShipmentSchedulesQueryFiltersEffective())
				//
				.orderBy()
				.addColumn(de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey, Direction.Ascending, Nulls.Last)
				.addColumn(de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID)
				.endOrderBy()
				//
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_M_ShipmentSchedule.class);

		if (!shipmentSchedules.hasNext())
		{
			throw new AdempiereException(MSG_NO_VALID_RECORDS);
		}

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(localCtx.getCtx(), GenerateInOutFromShipmentSchedules.class);

		IWorkPackageBuilder workpackageBuilder = null;
		String lastHeaderAggregationKey = null;

		boolean doEnqueueCurrentPackage = true;

		final Result result = new Result();

		while (shipmentSchedules.hasNext())
		{
			final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedules.next();
			try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(shipmentSchedule))
			{
				final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());

				if (invalidSchedulesService.isFlaggedForRecompute(shipmentScheduleId))
				{
					// we can't just not enqueue those workpackages and only write a debug log message about it
					// => enqueue them, log if and collect experience about what the practical impact is.
					// 	doEnqueueCurrentPackage = false;
					Loggables.withLogger(logger, Level.INFO).addLog("shipmentScheduleId is flagged for recompute; -> still enqueue the workpackage!");
				}

				//
				// Check if we shall close our current workpackage (if any)
				final String headerAggregationKey = shipmentSchedule.getHeaderAggregationKey();
				if (!Objects.equals(headerAggregationKey, lastHeaderAggregationKey))
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
					workpackageBuilder = queue
							.newWorkPackage()
							.setAD_PInstance_ID(workPackageParameters.getAdPInstanceId())
							.setUserInChargeId(Env.getLoggedUserIdIfExists().orElse(null))
							.setPriority(SizeBasedWorkpackagePrio.INSTANCE)
							.bindToTrxName(localCtx.getTrxName())
							.setC_Async_Batch_ID(AsyncBatchId.ofRepoIdOrNull(shipmentSchedule.getC_Async_Batch_ID()));

					workpackageBuilder
							.parameters()
							.setParameter(ShipmentScheduleWorkPackageParameters.PARAM_OnlyLUIds, RepoIdAwares.toCommaSeparatedString(workPackageParameters.getOnlyLUIds()))
							.setParameter(ShipmentScheduleWorkPackageParameters.PARAM_QuantityType, workPackageParameters.getQuantityType())
							.setParameter(ShipmentScheduleWorkPackageParameters.PARAM_IsOnTheFlyPickToPackingInstructions, workPackageParameters.isOnTheFlyPickToPackingInstructions())
							.setParameter(ShipmentScheduleWorkPackageParameters.PARAM_IsCompleteShipments, workPackageParameters.isCompleteShipments())
							.setParameter(ShipmentScheduleWorkPackageParameters.PARAM_IsCloseShipmentSchedules, workPackageParameters.isCloseShipmentSchedules())
							.setParameter(ShipmentScheduleWorkPackageParameters.PARAM_IsShipmentDateToday, workPackageParameters.isShipmentDateToday());

					// Create a new locker which will grab the locked shipment candidates from 'mainLock'
					// and it will move them to a new owner which is created per workpackage
					final LockOwner workpackageElementsLockOwner = LockOwner.newOwner("ShipmentScheds_" + shipmentSchedule.getHeaderAggregationKey());
					final ILockCommand workpackageElementsLocker = mainLock
							.split()
							.setOwner(workpackageElementsLockOwner)
							.setAutoCleanup(false); // from this point on we don't want to allow the system to auto clean the locks
					workpackageBuilder.setElementsLocker(workpackageElementsLocker);
				}

				addAdvisedShipmentDocumentNo(workPackageParameters, workpackageBuilder, shipmentScheduleId);

				// Enqueue shipmentSchedule to the current workpackage
				final Set<PickingJobScheduleId> jobScheduleIds = workPackageParameters.getPickingJobScheduleIds(shipmentScheduleId);
				if (jobScheduleIds.isEmpty())
				{
					workpackageBuilder.addElement(shipmentSchedule);
				}
				else
				{
					workpackageBuilder.addElement(shipmentSchedule);
					workpackageBuilder.addElements(PickingJobScheduleId.toTableRecordReferenceSet(jobScheduleIds));
				}
			}
		}

		//
		// Close last workpackage (if any, and if there was no error)
		handleAllSchedsAdded(workpackageBuilder, lastHeaderAggregationKey, doEnqueueCurrentPackage, result);
		return result;
	}

	private static void addAdvisedShipmentDocumentNo(
			@NonNull final ShipmentScheduleWorkPackageParameters workPackageParameters,
			@NonNull final IWorkPackageBuilder workpackageBuilder,
			@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final ImmutableMap<ShipmentScheduleId, String> advisedShipmentDocumentNos = workPackageParameters.getAdvisedShipmentDocumentNos();
		if (advisedShipmentDocumentNos != null)
		{
			final String advisedShipmentDocumentNo = advisedShipmentDocumentNos.get(shipmentScheduleId);
			if (EmptyUtil.isNotBlank(advisedShipmentDocumentNo))
			{
				workpackageBuilder.parameters()
						.setParameter(PARAM_PREFIX_AdvisedShipmentDocumentNo + shipmentScheduleId.getRepoId(), advisedShipmentDocumentNo);
			}
		}

		final QtyToDeliverMap qtysToDeliverOverride = workPackageParameters.getQtysToDeliverOverride();
		if (qtysToDeliverOverride != null)
		{
			workpackageBuilder.parameters().setParameter(PARAM_QtyToDeliver_Override, qtysToDeliverOverride.toJsonString());
		}
	}

	private void handleAllSchedsAdded(
			@Nullable final IWorkPackageBuilder workpackageBuilder,
			@Nullable final String lastHeaderAggregationKey,
			final boolean noSchedsAreToRecompute,
			final Result result)
	{
		if (workpackageBuilder == null)
		{
			return; // nothing to do
		}

		if (noSchedsAreToRecompute)
		{
			// while building, we also split the shipment scheduled from the main lock to the lock defined by 'workpackageElementsLocker' (see below)
			final I_C_Queue_WorkPackage workPackage = workpackageBuilder.buildAndEnqueue();
			result.addEnqueuedWorkPackageId(QueueWorkPackageId.ofRepoId(workPackage.getC_Queue_WorkPackage_ID()));
		}
		else
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog(
					msgBL.parseTranslation(
							_ctx,
							"@Skip@ @" + I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey + "@=" + lastHeaderAggregationKey + ": "
									+ "@" + I_M_ShipmentSchedule.COLUMNNAME_IsToRecompute + "@ = @Yes@"));
			workpackageBuilder.discard();
			result.incSkipped();
		}
	}

	/**
	 * Lock all invoice candidates for selection and return an auto-closable lock.
	 */
	private ILock acquireMainLock(@NonNull final ShipmentScheduleWorkPackageParameters workPackageParameters)
	{
		final PInstanceId adPInstanceId = workPackageParameters.getAdPInstanceId();
		final IQueryFilter<I_M_ShipmentSchedule> queryFilters = workPackageParameters.getShipmentSchedulesQueryFiltersEffective();

		final ILockCommand lockCommand = lockManager.lock()
				.setOwner(LockOwner.newOwner("ShipmentScheduleEnqueuer", adPInstanceId.getRepoId()))
				// allow these locks to be cleaned-up on server starts.
				// NOTE: when we will add the scheds to workpackages we will move the ICs to another owner and we will also set AutoCleanup=false
				.setAutoCleanup(true)
				.setFailIfAlreadyLocked(true)
				.retryOnFailure(3)
				.setRecordsByFilter(I_M_ShipmentSchedule.class, queryFilters);

		final Set<PickingJobScheduleId> jobScheduleIds = workPackageParameters.getPickingJobScheduleIds();
		if (!jobScheduleIds.isEmpty())
		{
			// IMPORTANT: mixing different type of records source (e.g. by filters and by record refs) is not supported  
			lockCommand.addRecordsByFilter(
					I_M_Picking_Job_Schedule.class,
					new InArrayQueryFilter<>(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID, jobScheduleIds)
			);
		}

		return lockCommand.acquire();
	}

	public String getTrxNameInitial()
	{
		return _trxNameInitial;
	}

	/**
	 * Contains the enqueuer's result. Right now it's just two counters, but might be extended in future.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 * @implSpec <a href="https://metasfresh.atlassian.net/browse/FRESH-342">task</a>
	 */
	public static class Result implements IEnqueueResult
	{
		@Getter
		private int skippedPackagesCount;

		private final List<QueueWorkPackageId> enqueuedWorkpackageIds = new ArrayList<>();

		public int getEnqueuedPackagesCount()
		{
			return enqueuedWorkpackageIds.size();
		}

		public ImmutableList<QueueWorkPackageId> getEnqueuedPackageIds()
		{
			return ImmutableList.copyOf(enqueuedWorkpackageIds);
		}

		private void addEnqueuedWorkPackageId(@NonNull final QueueWorkPackageId workPackageId)
		{
			enqueuedWorkpackageIds.add(workPackageId);
		}

		private void incSkipped()
		{
			skippedPackagesCount++;
		}

		@Override
		public int getWorkpackageEnqueuedCount()
		{
			return enqueuedWorkpackageIds.size();
		}
	}

}
