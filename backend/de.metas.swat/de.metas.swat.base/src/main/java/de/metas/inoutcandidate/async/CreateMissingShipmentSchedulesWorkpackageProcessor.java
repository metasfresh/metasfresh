/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.inoutcandidate.async;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IEnqueueResult;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.inoutcandidate.api.CreateMissingCandidatesResult;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

/**
 * Workpackage used to create missing shipment schedules.
 * <p>
 * Concurrency: a run creates its batch of shipment schedules in one transaction, without explicit row locks. Concurrent (e.g. user)
 * transactions are therefore not blocked by it, beyond the standard foreign key locks that its new rows take on the records they
 * reference (which only conflict with deleting such a record or changing its key) until the batch commits. The other way round, the
 * batch waits at most for the {@code lock_timeout} (sysconfig {@value #SYSCONFIG_LockTimeoutMillis}, default 500 ms) for a concurrent
 * transaction. If the batch collides with a concurrent transaction that deletes one of its order lines (see
 * {@link #isCollisionWithConcurrentTransaction(Throwable)}), the whole batch is rolled back and the run is retried after
 * {@value #SYSCONFIG_RetryMillis} (default 5 s), at most {@value #SYSCONFIG_MaxRetries} (default 10) times; after that, the
 * collision is an error like any other.
 *
 * @author tsa
 */
public class CreateMissingShipmentSchedulesWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private static final Logger logger = LogManager.getLogger(CreateMissingShipmentSchedulesWorkpackageProcessor.class);

	private static final String SYSCONFIG_MaxToProcess = "de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor.MaxToProcess";
	private static final int DEFAULT_MaxToProcess = 500;

	/**
	 * PostgreSQL {@code lock_timeout} of a run's batch transaction; {@code <= 0} means no timeout. See {@link #setLockTimeout()}.
	 */
	private static final String SYSCONFIG_LockTimeoutMillis = "de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor.LockTimeoutMillis";
	private static final int DEFAULT_LockTimeoutMillis = 500;

	/**
	 * How long to wait before a run whose batch collided with a concurrent transaction is retried. See {@link #isCollisionWithConcurrentTransaction(Throwable)}.
	 */
	private static final String SYSCONFIG_RetryMillis = "de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor.RetryMillis";
	private static final int DEFAULT_RetryMillis = 5000;

	/**
	 * How often a workpackage is retried because of such collisions at most; after that, the collision is an error like any other.
	 */
	private static final String SYSCONFIG_MaxRetries = "de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor.MaxRetries";
	private static final int DEFAULT_MaxRetries = 10;

	private static final String PG_SQLSTATE_lock_not_available = "55P03";
	private static final String PG_SQLSTATE_foreign_key_violation = "23503";
	/**
	 * The (deferred) foreign key from {@code M_ShipmentSchedule.C_OrderLine_ID} to {@code C_OrderLine}.
	 * The other handler's schedules (subscription progress) reference their source record via {@code AD_Table_ID/Record_ID}, without a foreign key.
	 */
	private static final String FK_M_ShipmentSchedule_C_OrderLine = "corderline_corderline";

	public static void scheduleIfNotPostponed(final IContextAware ctxAware)
	{
		final AsyncBatchId asyncBatchId = null;
		_scheduleIfNotPostponed(ctxAware, asyncBatchId);
	}

	public static IEnqueueResult scheduleIfNotPostponed(@NonNull final Object model)
	{
		final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
		final AsyncBatchId asyncBatchId = asyncBatchBL
				.getAsyncBatchId(model)
				.orElse(null);

		final boolean scheduled = _scheduleIfNotPostponed(InterfaceWrapperHelper.getContextAware(model), asyncBatchId);
		final int workpackageCount = scheduled ? 1 : 0;

		return () -> workpackageCount;
	}

	/**
	 * Schedules a new "create missing shipment schedules" run, <b>unless</b> the processor is disabled or all scheds would be created later.<br>
	 * See {@link IShipmentScheduleBL#allMissingSchedsWillBeCreatedLater()}.
	 *
	 * @param ctxAware if it has a not-null trxName, then the workpackage will be marked as ready for processing when the given transaction is committed.
	 */
	private static boolean _scheduleIfNotPostponed(final IContextAware ctxAware, @Nullable final AsyncBatchId asyncBatchId)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		if (shipmentScheduleBL.allMissingSchedsWillBeCreatedLater())
		{
			loggable.addLog("Not scheduling WP because IShipmentScheduleBL.allMissingSchedsWillBeCreatedLater() returned true: {}", CreateMissingShipmentSchedulesWorkpackageProcessor.class.getSimpleName());
			return false;
		}

		// don't try to enqueue it if is not active
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		if (!queueDAO.isWorkpackageProcessorEnabled(CreateMissingShipmentSchedulesWorkpackageProcessor.class))
		{
			loggable.addLog("Not scheduling WP because this workpackage processor is disabled: {}", CreateMissingShipmentSchedulesWorkpackageProcessor.class.getSimpleName());
			return false;
		}

		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
		final IWorkPackageQueue queueForEnqueuing = workPackageQueueFactory.getQueueForEnqueuing(ctxAware.getCtx(), CreateMissingShipmentSchedulesWorkpackageProcessor.class);
		final int alreadyEnqueuedWPs = queueForEnqueuing.size();
		if (alreadyEnqueuedWPs > 1)
		{ // why >1 and not >0? i checked the code and >0 should be fine. still, i feel more comfortable with >1
			loggable.addLog("Not scheduling WP because there are {} processable workpackages, and we just need one to create all missing schedules!: {}",
					alreadyEnqueuedWPs,
					CreateMissingShipmentSchedulesWorkpackageProcessor.class.getSimpleName());
			return false;
		}

		queueForEnqueuing
				.newWorkPackage()
				.setAsyncBatchId(asyncBatchId)
				.bindToTrxName(ctxAware.getTrxName())
				.buildAndEnqueue();
		return true;
	}

	// services
	private final transient IShipmentScheduleHandlerBL inOutCandHandlerBL = Services.get(IShipmentScheduleHandlerBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient IShipmentScheduleInvalidateBL invalidSchedulesService = Services.get(IShipmentScheduleInvalidateBL.class);
	private final transient IShipmentSchedulePA shipmentScheduleDAO = Services.get(IShipmentSchedulePA.class);
	private final transient IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	@Override
	public final boolean isRunInTransaction()
	{
		return false; // run out of transaction; we bound our own batch to an explicit, short-lived trx below instead
	}

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		trxManager.assertTrxNameNull(localTrxName);

		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);
		final QueryLimit maxToProcess = QueryLimit.ofInt(getMaxToProcess());

		// Create+invalidate one bounded batch of missing shipment schedules in its own transaction.
		// Why callInThreadInheritedTrx here: this processor runs out-of-transaction (isRunInTransaction()==false), so
		// there is NO ambient trx; callInThreadInheritedTrx then starts a new trx (and commits/closes it) -- giving us
		// ONE short, bounded transaction per batch instead of a single unbounded transaction for the whole backlog
		// (which OOMs on a large backlog) -- that bounded batching is the whole point of this processor. The two
		// effects (create + by-id invalidation) MUST share this one transaction (see processOneBatch below).
		// This is NOT removable: the batching design requires exactly one bounded, atomic trx per batch; removing it
		// would either restore the unbounded-single-trx OOM, or split creation and by-id flagging across transactions
		// and break the same-trx invalidation invariant documented in de/metas/inoutcandidate/CLAUDE.md.
		final CreateMissingCandidatesResult result;
		try
		{
			result = trxManager.callInThreadInheritedTrx(() -> processOneBatch(ctx, maxToProcess));
		}
		catch (final RuntimeException e)
		{
			// The batch transaction was rolled back, i.e. no schedule of this batch was created.
			if (isCollisionWithConcurrentTransaction(e) && workpackage.getSkipped_Count() < getMaxRetries())
			{
				throw WorkpackageSkipRequestException.createWithTimeoutAndThrowable(
						"The batch collided with a concurrent transaction; retrying later",
						getRetryMillis(),
						e);
			}
			throw e;
		}

		Loggables.addLog("Created " + result.getCreatedShipmentScheduleIds().size() + " candidates");

		if (result.isLimitReached())
		{
			enqueueFollowUpWorkpackage(ctx, workpackage);
		}

		return Result.SUCCESS;
	}

	private int getMaxToProcess()
	{
		return sysConfigBL.getIntValue(SYSCONFIG_MaxToProcess, DEFAULT_MaxToProcess);
	}

	private int getRetryMillis()
	{
		return sysConfigBL.getIntValue(SYSCONFIG_RetryMillis, DEFAULT_RetryMillis);
	}

	private int getMaxRetries()
	{
		return sysConfigBL.getIntValue(SYSCONFIG_MaxRetries, DEFAULT_MaxRetries);
	}

	/**
	 * Whether the batch failed because a concurrent transaction changed the records it was creating shipment schedules for, so that
	 * retrying the run later succeeds:
	 * <ul>
	 * <li>The candidate order lines are selected from committed data, but a concurrent, not yet committed transaction may delete one
	 * of them, e.g. a sales order's reactivation deleting the discount lines of a compensation group. If that transaction commits
	 * first, the batch's commit fails on the deferred foreign key {@value #FK_M_ShipmentSchedule_C_OrderLine} (SQLSTATE 23503).</li>
	 * <li>If that transaction is still running when the batch commits, the foreign key check waits for it (locking an order line that a
	 * concurrent transaction updated or deleted waits for that transaction), until the lock timeout (SQLSTATE 55P03); see
	 * {@link #setLockTimeout()}.</li>
	 * </ul>
	 * A retried run selects the candidates again, so it no longer sees the deleted lines. Any other error, also a violation of another
	 * foreign key, is not retried, so that a genuine bug is not retried over and over.
	 */
	@VisibleForTesting
	static boolean isCollisionWithConcurrentTransaction(@NonNull final Throwable e)
	{
		return DBException.findSQLExceptionInCauseChain(e, CreateMissingShipmentSchedulesWorkpackageProcessor::isCollisionWithConcurrentTransaction).isPresent();
	}

	private static boolean isCollisionWithConcurrentTransaction(@NonNull final SQLException e)
	{
		final String sqlState = e.getSQLState();
		if (PG_SQLSTATE_lock_not_available.equals(sqlState))
		{
			return true;
		}
		return PG_SQLSTATE_foreign_key_violation.equals(sqlState)
				&& FK_M_ShipmentSchedule_C_OrderLine.equalsIgnoreCase(DBException.extractConstraintNameOrNull(e));
	}

	/**
	 * Sets a {@code lock_timeout} for the current (batch) transaction, so that the batch never waits long for a lock held by a concurrent
	 * (e.g. a user's) transaction: it gives up, is rolled back, and the run is retried later (see {@link #isCollisionWithConcurrentTransaction(Throwable)}).
	 * The batch itself takes no explicit row locks, so concurrent transactions don't wait for it, apart from the usual commit-time
	 * foreign key checks of its new rows.
	 */
	private void setLockTimeout()
	{
		final int lockTimeoutMillis = sysConfigBL.getIntValue(SYSCONFIG_LockTimeoutMillis, DEFAULT_LockTimeoutMillis);
		if (lockTimeoutMillis <= 0)
		{
			return;
		}
		DB.executeUpdateAndThrowExceptionOnFail("SET LOCAL lock_timeout = " + lockTimeoutMillis, ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * Creates one bounded batch of missing shipment schedules and, in the same (batch) transaction, invalidates them
	 * by id. See the invocation site in {@link #processWorkPackage(I_C_Queue_WorkPackage, String)} for why both effects
	 * must share that one transaction.
	 */
	private CreateMissingCandidatesResult processOneBatch(@NonNull final Properties ctx, @NonNull final QueryLimit maxToProcess)
	{
		setLockTimeout();

		final CreateMissingCandidatesResult batchResult = inOutCandHandlerBL.createMissingCandidates(ctx, maxToProcess);

		// After shipment schedules were created, invalidate them (by id, in THIS same batch trx) because we want to
		// make sure they are up2date. By-id flagging in the creating transaction is mandatory: the segment-based
		// invalidation channel flushes on TRXNAME_None and cannot see this transaction's uncommitted inserts
		// (invariant: de/metas/inoutcandidate/CLAUDE.md).
		final Collection<I_M_ShipmentSchedule> scheduleRecords = shipmentScheduleDAO.getByIds(batchResult.getCreatedShipmentScheduleIds()).values();
		for (final I_M_ShipmentSchedule scheduleRecord : scheduleRecords)
		{
			invalidSchedulesService.notifySegmentChangedForShipmentScheduleInclSched(scheduleRecord);
		}

		return batchResult;
	}

	/**
	 * Enqueues a fresh workpackage (carrying over the current one's async batch) to create the shipment schedules that
	 * remained after this run's bounded batch. Enqueues it directly instead of going through {@link #scheduleIfNotPostponed},
	 * because that method's dedup guard (skip if &gt;1 processable workpackage already queued) does not apply here: we
	 * KNOW there is more work left for this exact run and must not skip it.
	 */
	private void enqueueFollowUpWorkpackage(@NonNull final Properties ctx, @NonNull final I_C_Queue_WorkPackage workpackage)
	{
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(workpackage.getC_Async_Batch_ID());

		// Deliberately NOT bound to any trx (unlike _scheduleIfNotPostponed, which does bindToTrxName): this run's batch
		// already committed inside the callInThreadInheritedTrx above, so there is nothing left to defer the follow-up's
		// readiness to -- it must become ready-for-processing immediately.
		workPackageQueueFactory
				.getQueueForEnqueuing(ctx, CreateMissingShipmentSchedulesWorkpackageProcessor.class)
				.newWorkPackage()
				.setAsyncBatchId(asyncBatchId)
				.buildAndEnqueue();

		Loggables.addLog("Limit reached; enqueued a follow-up workpackage to create the remaining missing shipment schedules");
	}
}
