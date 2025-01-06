/*
 * #%L
 * de.metas.salescandidate.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ordercandidate.api.async;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.async.AsyncBatchId;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.api.OLCandProcessorDescriptor;
import de.metas.ordercandidate.api.OLCandProcessorRepository;
import de.metas.ordercandidate.api.source.GetEligibleOLCandRequest;
import de.metas.ordercandidate.api.source.OLCandIterator;
import de.metas.ordercandidate.api.source.OLCandProcessingHelper;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PInstance;
import org.compiere.util.Env;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiFunction;

import static de.metas.async.Async_Constants.C_OlCandProcessor_ID_Default;
import static de.metas.ordercandidate.api.async.C_OLCandToOrderWorkpackageProcessor.OLCandProcessor_ID;
import static org.compiere.util.Env.getCtx;

@Service
public class C_OLCandToOrderEnqueuer
{
	private static final Logger logger = LogManager.getLogger(C_OLCandToOrderEnqueuer.class);

	private static final String LOCK_OWNER_PREFIX = "C_OLCandToOrderEnqueuer";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);

	private final OLCandProcessorRepository olCandProcessorRepo;
	private final OLCandProcessingHelper olCandProcessingHelper;

	public C_OLCandToOrderEnqueuer(
			@NonNull final OLCandProcessorRepository olCandProcessorRepo,
			@NonNull final OLCandProcessingHelper olCandProcessingHelper)
	{
		this.olCandProcessorRepo = olCandProcessorRepo;
		this.olCandProcessingHelper = olCandProcessingHelper;
	}

	public OlCandEnqueueResult enqueueBatch(@NonNull final AsyncBatchId asyncBatchId)
	{
		// IMPORTANT: we shall create the selection out of transaction because
		// else the selection (T_Selection) won't be available when creating the main lock for records of this selection.
		final PInstanceId batchSelectionId = queryBL.createQueryBuilderOutOfTrx(I_C_OLCand.class)
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_C_Async_Batch_ID, asyncBatchId)
				.create()
				.createSelection();

		return lockAndEnqueueSelection(batchSelectionId, asyncBatchId);
	}

	@NonNull
	public OlCandEnqueueResult enqueueSelection(@NonNull final PInstanceId selectionId)
	{
		final AsyncBatchId asyncBatchId = null;
		return lockAndEnqueueSelection(selectionId, asyncBatchId);
	}

	@NonNull
	private OlCandEnqueueResult lockAndEnqueueSelection(@NonNull final PInstanceId selectionId, @Nullable final AsyncBatchId asyncBatchId)
	{
		final ILock mainLock = lockSelection(selectionId);
		try (final ILockAutoCloseable l = mainLock.asAutocloseableOnTrxClose(ITrx.TRXNAME_ThreadInherited))
		{
			return enqueueSelectionInTrx(mainLock, selectionId, asyncBatchId);
		}
	}

	@NonNull
	private OlCandEnqueueResult enqueueSelectionInTrx(
			@NonNull final ILock mainLock,
			@NonNull final PInstanceId selectionId,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		final OLCandProcessorDescriptor descriptor = olCandProcessorRepo.getById(C_OlCandProcessor_ID_Default);

		final EnqueueWorkPackageRequest request = EnqueueWorkPackageRequest.builder()
				.mainLock(mainLock)
				.orderLineCandSelectionId(selectionId)
				.olCandProcessorDescriptor(descriptor)
				.asyncBatchId(asyncBatchId)
				.build();

		final OlCandEnqueueResult result = new OlCandEnqueueResult(enqueueWorkPackages(request));

		Loggables.withLogger(logger, Level.DEBUG).addLog("*** C_OLCandToOrderEnqueuer.OlCandEnqueueResult : {}", result);

		return result;
	}

	@NonNull
	private ImmutableList<QueueWorkPackageId> enqueueWorkPackages(@NonNull final C_OLCandToOrderEnqueuer.EnqueueWorkPackageRequest request)
	{
		setHeaderAggregationKey(request);

		final Iterator<OLCand> olCandIterator = getOrderedOLCandIterator(request);

		if (!olCandIterator.hasNext())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("*** No candidate pulled to be processed from C_OLCandToOrderEnqueuer.EnqueueWorkPackageRequest: {}", request);
			return ImmutableList.of();
		}

		final IWorkPackageQueue workPackageQueue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), C_OLCandToOrderWorkpackageProcessor.class);

		final BiFunction<OLCand, OLCand, Boolean> shouldSplitOrder = (previousCand, currentCand) ->
				previousCand != null && OLCandProcessingHelper.isOrderSplit(currentCand, previousCand, request.getOlCandProcessorDescriptor().getAggregationInfo());

		IWorkPackageBuilder workPackageBuilder = null;
		OLCand previousCandidate = null;

		final ImmutableList.Builder<QueueWorkPackageId> enqueuedWorkPackageCollector = ImmutableList.builder();

		while (olCandIterator.hasNext())
		{
			final OLCand currentCandidate = olCandIterator.next();

			final TableRecordReference candidateRecordReference = TableRecordReference.of(I_C_OLCand.Table_Name, OLCandId.ofRepoId(currentCandidate.getId()));

			try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(candidateRecordReference))
			{
				if (!olCandProcessingHelper.checkEligibleAndLog(currentCandidate, request.getAsyncBatchId()))
				{
					continue;
				}

				if (shouldSplitOrder.apply(previousCandidate, currentCandidate))
				{
					enqueuedWorkPackageCollector.add(workPackageBuilder.buildAndGetId());
					workPackageBuilder = null;
				}

				previousCandidate = currentCandidate;

				if (workPackageBuilder == null)
				{
					workPackageBuilder = initiateWorkPackageBuilder(workPackageQueue, request.getMainLock(), request.getAsyncBatchId());
				}

				workPackageBuilder.addElement(candidateRecordReference);
			}
		}

		//dev-note: make sure to enqueue last work package
		Optional.ofNullable(workPackageBuilder)
				.map(IWorkPackageBuilder::buildAndGetId)
				.ifPresent(enqueuedWorkPackageCollector::add);

		return enqueuedWorkPackageCollector.build();
	}

	@NonNull
	private OLCandIterator getOrderedOLCandIterator(@NonNull final C_OLCandToOrderEnqueuer.EnqueueWorkPackageRequest request)
	{
		final GetEligibleOLCandRequest eligibleOLCandRequest = GetEligibleOLCandRequest.builder()
				.selection(request.getOrderLineCandSelectionId())
				.orderDefaults(request.getOlCandProcessorDescriptor().getDefaults())
				.aggregationInfo(request.getOlCandProcessorDescriptor().getAggregationInfo())
				.asyncBatchId(request.getAsyncBatchId())
				.build();

		return olCandProcessingHelper.getOrderedOLCandIterator(eligibleOLCandRequest);
	}

	@NonNull
	private IWorkPackageBuilder initiateWorkPackageBuilder(
			@NonNull final IWorkPackageQueue workPackageQueue,
			@NonNull final ILock mainLock,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		final IWorkPackageBuilder workpackageBuilder = workPackageQueue.newWorkPackage()
				// we want the enqueuing user to be notified on problems
				.setUserInChargeId(Env.getLoggedUserIdIfExists().orElse(null))
				.parameter(OLCandProcessor_ID, C_OlCandProcessor_ID_Default);

		Optional.ofNullable(asyncBatchId)
				.map(asyncBatchDAO::retrieveAsyncBatchRecordOutOfTrx)
				.ifPresent(workpackageBuilder::setC_Async_Batch);

		workpackageBuilder.setElementsLocker(splitLock(mainLock));

		return workpackageBuilder;
	}

	@NonNull
	private ILockCommand splitLock(@NonNull final ILock mainLock)
	{
		// Create a new locker which will grab the locked candidate from initial lock
		// and it will move them to a new owner which is created per work package
		final LockOwner workpackageElementsLockOwner = LockOwner.newOwner("ProcessOLCand_" + Instant.now().getMillis());
		return mainLock
				.split()
				.setOwner(workpackageElementsLockOwner)
				.setAutoCleanup(false);
	}

	@NonNull
	private ILock lockSelection(@NonNull final PInstanceId selectionId)
	{
		final LockOwner lockOwner = LockOwner.newOwner(LOCK_OWNER_PREFIX, I_AD_PInstance.COLUMN_AD_PInstance_ID + "=" + selectionId.getRepoId());

		return lockManager.lock()
				.setOwner(lockOwner)
				.setFailIfAlreadyLocked(true)
				.setRecordsBySelection(I_C_OLCand.class, selectionId)
				.acquire();
	}

	private void setHeaderAggregationKey(@NonNull final C_OLCandToOrderEnqueuer.EnqueueWorkPackageRequest request)
	{
		final IOLCandBL olCandBL = Services.get(IOLCandBL.class);

		final OLCandIterator olCandIterator = getOrderedOLCandIterator(request);
		final OLCandProcessorDescriptor olCandProcessorDescriptor = request.getOlCandProcessorDescriptor();

		while (olCandIterator.hasNext())
		{
			final OLCand candidate = olCandIterator.next();

			final String headerAggregationKey = olCandProcessorDescriptor.getAggregationInfo().computeHeaderAggregationKey(candidate);

			candidate.setHeaderAggregationKey(headerAggregationKey);

			olCandBL.saveCandidate(candidate);
		}
	}

	@Builder
	@Value
	private static class EnqueueWorkPackageRequest
	{
		@NonNull
		ILock mainLock;

		@NonNull
		PInstanceId orderLineCandSelectionId;

		@NonNull
		OLCandProcessorDescriptor olCandProcessorDescriptor;

		@Nullable
		AsyncBatchId asyncBatchId;
	}
}
