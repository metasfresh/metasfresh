package de.metas.async.api.impl;



/*
 * #%L
 * de.metas.async
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

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import de.metas.async.AsyncBatchId;
import de.metas.async.Async_Constants;
import de.metas.async.api.AsyncBatchType;
import de.metas.async.api.AsyncBatchTypeId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchBuilder;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Notified;
import de.metas.async.model.X_C_Async_Batch_Type;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.impl.CheckProcessedAsynBatchWorkpackageProcessor;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.async.spi.NullWorkpackagePrio;
import de.metas.cache.CCache;
import de.metas.common.util.Check;
import de.metas.common.util.time.SystemTime;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import de.metas.common.util.pair.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import static org.compiere.util.Env.getCtx;

public class AsyncBatchBL implements IAsyncBatchBL
{
	private static final String DYN_ATTR_TEMPORARY_BATCH_ID = "TemporaryBatchId";

	// services
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ReentrantLock lock = new ReentrantLock();

	private final CCache<AsyncBatchTypeId, AsyncBatchType> asyncBatchTypesById = CCache.<AsyncBatchTypeId, AsyncBatchType>builder()
			.tableName(I_C_Async_Batch_Type.Table_Name)
			.build();

	/**
	 * See {@link #computeNowTimestamp()}
	 */
	@Setter
	private boolean useMetasfreshSystemTime;

	@Override
	public IAsyncBatchBuilder newAsyncBatch()
	{
		return new AsyncBatchBuilder(this);
	}

	@Override
	public int increaseEnqueued(final I_C_Queue_WorkPackage workPackage)
	{
		return setAsyncBatchCountEnqueued(workPackage, +1);
	}

	@Override
	public int decreaseEnqueued(final I_C_Queue_WorkPackage workPackage)
	{
		return setAsyncBatchCountEnqueued(workPackage, -1);
	}

	@Override
	public void createNotificationRecord(final I_C_Queue_WorkPackage workPackage)
	{
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(workPackage.getC_Async_Batch_ID());
		if (asyncBatchId == null)
		{
			return;
		}

		final I_C_Async_Batch asyncBatch = asyncBatchDAO.retrieveAsyncBatchRecordOutOfTrx(asyncBatchId);
		final AsyncBatchType asyncBatchType = getAsyncBatchType(asyncBatch).orElse(null);
		if (asyncBatchType != null && X_C_Async_Batch_Type.NOTIFICATIONTYPE_WorkpackageProcessed.equals(asyncBatchType.getNotificationType()))
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(workPackage);
			final String trxName = InterfaceWrapperHelper.getTrxName(workPackage);

			final I_C_Queue_WorkPackage_Notified wpNotified = InterfaceWrapperHelper.create(ctx, I_C_Queue_WorkPackage_Notified.class, trxName);
			wpNotified.setC_Async_Batch_ID(asyncBatchId.getRepoId());
			wpNotified.setC_Queue_WorkPackage_ID(workPackage.getC_Queue_WorkPackage_ID());
			wpNotified.setBachWorkpackageSeqNo(workPackage.getBatchEnqueuedCount());
			wpNotified.setIsNotified(false);
			Services.get(IQueueDAO.class).save(wpNotified);
		}

	}

	@Override
	public void increaseProcessed(final I_C_Queue_WorkPackage workPackage)
	{
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(workPackage.getC_Async_Batch_ID());
		if (asyncBatchId == null)
		{
			return;
		}

		lock.lock();

		try
		{
			final I_C_Async_Batch asyncBatch = asyncBatchDAO.retrieveAsyncBatchRecordOutOfTrx(asyncBatchId);
			final Timestamp processed = SystemTime.asTimestamp();
			asyncBatch.setLastProcessed(processed);
			asyncBatch.setLastProcessed_WorkPackage_ID(workPackage.getC_Queue_WorkPackage_ID());
			asyncBatch.setCountProcessed(asyncBatch.getCountProcessed() + 1);

			save(asyncBatch);
		}
		finally
		{
			lock.unlock();
		}
	}

	@Override
	public void enqueueAsyncBatch(@NonNull final AsyncBatchId asyncBatchId)
	{
		final Properties ctx = Env.getCtx();
		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(ctx, CheckProcessedAsynBatchWorkpackageProcessor.class);
		queue.setAsyncBatchIdForNewWorkpackages(asyncBatchId);

		final IWorkpackagePrioStrategy prio = NullWorkpackagePrio.INSTANCE; // don't specify a particular prio. this is OK because we assume that there is a dedicated queue/thread for CheckProcessedAsynBatchWorkpackageProcessor

		final I_C_Queue_WorkPackage queueWorkpackage = queue
				.newWorkPackage()
				.setPriority(prio)
				.buildAndEnqueue();

		// Make sure that the watch processor is not in the same batch (because it will affect the counter which we are checking...)
		queueWorkpackage.setC_Async_Batch(null);
		queueDAO.save(queueWorkpackage);

		queue.enqueueElement(
				queueWorkpackage,
				TableRecordReference.of(I_C_Async_Batch.Table_Name, asyncBatchId));
		queue.markReadyForProcessing(queueWorkpackage);
	}

	@Override
	public boolean updateProcessedOutOfTrx(@NonNull final AsyncBatchId asyncBatchId)
	{
		final I_C_Async_Batch asyncBatchRecord = asyncBatchDAO.retrieveAsyncBatchRecordOutOfTrx(asyncBatchId);
		if (asyncBatchRecord.isProcessed())
		{
			return true;
		}

		final Duration millisUntilReadyForChecking = getTimeUntilProcessedRecheck(asyncBatchRecord);

		if (millisUntilReadyForChecking.toMillis() > 0)
		{
			return false;
		}

		updateProcessedFlag(asyncBatchRecord);

		queueDAO.save(asyncBatchRecord);

		return asyncBatchRecord.isProcessed();
	}

	@Override
	@NonNull
	public Duration getTimeUntilProcessedRecheck(@NonNull final I_C_Async_Batch asyncBatch)
	{
		final int processedTimeOffsetMillis = getProcessedTimeOffsetMillis();

		//
		final Timestamp firstEnqueued = asyncBatch.getFirstEnqueued();
		if (firstEnqueued == null)
		{
			// shall not happen
			return Duration.ofMillis(processedTimeOffsetMillis);
		}

		//
		final Timestamp lastEnqueued = asyncBatch.getLastEnqueued();
		if (lastEnqueued == null)
		{
			// shall not happen
			return Duration.ofMillis(processedTimeOffsetMillis);
		}

		final Timestamp lastProcessed = asyncBatch.getLastProcessed();
		if (lastProcessed == null)
		{
			// shall not happen
			return Duration.ofMillis(processedTimeOffsetMillis);
		}

		// Case: when did not pass enough time between fist enqueue time and now.
		final Timestamp now = computeNowTimestamp();

		final Timestamp minTimeAfterFirstEnqueued = TimeUtil.addMillis(firstEnqueued, processedTimeOffsetMillis);

		if (minTimeAfterFirstEnqueued.compareTo(now) > 0)
		{
			final long millisToWait = TimeUtil.getMillisBetween(now, minTimeAfterFirstEnqueued);

			return Duration.ofMillis(millisToWait);
		}

		// Case: when did not pass enough time between last processed time and now - offset
		// take a bigger time for checking processed because thread could be locked by other thread and we could have some bigger delay
		final Timestamp minTimeAfterLastProcessed = TimeUtil.addMillis(lastProcessed, processedTimeOffsetMillis);
		if (minTimeAfterLastProcessed.compareTo(now) > 0)
		{
			final long millisToWait = TimeUtil.getMillisBetween(now, minTimeAfterLastProcessed);
			return Duration.ofMillis(millisToWait);
		}

		//
		// If we reach this point, we can move on and check if the async batch is processed
		return Duration.ZERO;
	}

	/**
	 * When running some unit-tests, we need to use {@link SystemTime}.
	 * But otherwise, don't use our de.metas.common.util.time.SystemTime, because it might be set to a fixed value when cucumber-testing which might lead to inter-overflows
	 */
	@Nullable
	private Timestamp computeNowTimestamp()
	{
		if(useMetasfreshSystemTime)
		{
			return SystemTime.asTimestamp();
		}
		return TimeUtil.asTimestamp(Instant.now());
	}

	@Override
	public boolean keepAliveTimeExpired(@NonNull final AsyncBatchId asyncBatchId)
	{
		final I_C_Async_Batch asyncBatchRecord = asyncBatchDAO.retrieveAsyncBatchRecordOutOfTrx(asyncBatchId);

		final AsyncBatchType asyncBatchType = getAsyncBatchType(asyncBatchRecord).orElse(null);
		if (asyncBatchType == null)
		{
			return false;
		}

		final Duration keepAlive = asyncBatchType.getKeepAlive();

		// if 0, keep alive for ever
		if (keepAlive.isZero())
		{
			return false;
		}

		final Timestamp lastUpdated = asyncBatchRecord.getUpdated();
		final Timestamp today = de.metas.common.util.time.SystemTime.asTimestamp();

		final long diffHours = TimeUtil.getHoursBetween(lastUpdated, today);

		return diffHours > keepAlive.toHours();
	}

	@Override
	@Nullable
	public I_C_Queue_WorkPackage notify(final I_C_Async_Batch asyncBatch, final I_C_Queue_WorkPackage workpackage)
	{
		//
		// retrieves not notified workpackages in order of the seqNo
		final List<I_C_Queue_WorkPackage_Notified> unNotifiedWPS = asyncBatchDAO.retrieveWorkPackagesNotified(asyncBatch, false);

		//
		// if there is not package not notified below the current one, do not notify
		int count = 0;
		for (final I_C_Queue_WorkPackage_Notified unNotifiedWP : unNotifiedWPS)
		{
			// if the given workpackage is the first one and is not notified, notify
			if (unNotifiedWP.getC_Queue_WorkPackage_ID() == workpackage.getC_Queue_WorkPackage_ID() && count == 0 && !unNotifiedWP.isNotified())
			{
				return workpackage;
			}

			// if the first workpackage is not notified, notify
			if (!unNotifiedWP.isNotified() && count == 0)
			{
				return unNotifiedWP.getC_Queue_WorkPackage();
			}

			count++;
		}

		return null;
	}

	@Override
	public void markWorkpackageNotified(final I_C_Queue_WorkPackage_Notified workpackageNotified)
	{
		workpackageNotified.setIsNotified(true);
		InterfaceWrapperHelper.save(workpackageNotified);
	}

	@NonNull
	public Optional<AsyncBatchId> getAsyncBatchId(@Nullable final Object modelRecord)
	{
		if (modelRecord == null)
		{
			return Optional.empty();
		}

		if (!InterfaceWrapperHelper.isModelInterface(modelRecord.getClass()))
		{
			return Optional.empty();
		}

		final AsyncBatchId temporaryBatchId = InterfaceWrapperHelper.getDynAttribute(modelRecord, DYN_ATTR_TEMPORARY_BATCH_ID);
		if (temporaryBatchId != null)
		{
			return Optional.of(temporaryBatchId);
		}

		// final Optional<Integer> asyncBatchId = InterfaceWrapperHelper.getValueOptional(modelRecord, I_C_Async_Batch.COLUMNNAME_C_Async_Batch_ID);
		// return asyncBatchId.map(AsyncBatchId::ofRepoIdOrNull);
		return Optional.empty();
	}

	@Override
	public @NonNull <T> ImmutablePair<AsyncBatchId, T> assignPermAsyncBatchToModelIfMissing(
			@NonNull final T modelRecord,
			@NonNull final String asyncBatchInternalName)
	{
		final Optional<AsyncBatchId> asyncBatchId = getAsyncBatchId(modelRecord);
		if (asyncBatchId.isPresent())
		{
			return ImmutablePair.of(asyncBatchId.get(), modelRecord);
		}

		return trxManager.callInNewTrx(() -> {

			final AsyncBatchId newAsyncBatchId = newAsyncBatch(asyncBatchInternalName);
			InterfaceWrapperHelper.setValue(modelRecord, I_C_Async_Batch.COLUMNNAME_C_Async_Batch_ID, newAsyncBatchId.getRepoId());

			InterfaceWrapperHelper.save(modelRecord);

			return ImmutablePair.of(newAsyncBatchId, modelRecord);
		});
	}

	@Override
	public @NonNull <T> Multimap<AsyncBatchId, T> assignTempAsyncBatchToModelsIfMissing(
			@NonNull final List<T> models,
			@NonNull final String asyncBatchInternalName)
	{
		final ImmutableListMultimap.Builder<AsyncBatchId, T> result = ImmutableListMultimap.builder();

		for (final T model : models)
		{
			final Optional<AsyncBatchId> asyncBatchId = getAsyncBatchId(model);
			if (asyncBatchId.isPresent())
			{
				result.put(asyncBatchId.get(), model);
			}
			else
			{
				final AsyncBatchId newAsyncBatchId = newAsyncBatch(asyncBatchInternalName);
				InterfaceWrapperHelper.setDynAttribute(model, DYN_ATTR_TEMPORARY_BATCH_ID, newAsyncBatchId);
				result.put(newAsyncBatchId, model);
			}
		}
		return result.build();
	}

	@Override
	public IAutoCloseable assignTempAsyncBatchIdToModel(@NonNull final Object model, @Nullable final AsyncBatchId asyncBatchId)
	{
		InterfaceWrapperHelper.setDynAttribute(model, DYN_ATTR_TEMPORARY_BATCH_ID, asyncBatchId);

		return () -> InterfaceWrapperHelper.setDynAttribute(model, DYN_ATTR_TEMPORARY_BATCH_ID, null);
	}

	public I_C_Async_Batch getAsyncBatchById(@NonNull final AsyncBatchId asyncBatchId)
	{
		return asyncBatchDAO.retrieveAsyncBatchRecordOutOfTrx(asyncBatchId);
	}

	@NonNull
	@Override
	public AsyncBatchId newAsyncBatch(@NonNull final String asyncBatchType)
	{
		final I_C_Async_Batch asyncBatch = trxManager.callInNewTrx(() -> newAsyncBatch()
				.setContext(getCtx())
				.setC_Async_Batch_Type(asyncBatchType)
				.setName(asyncBatchType)
				.build());
		return AsyncBatchId.ofRepoId(asyncBatch.getC_Async_Batch_ID());
	}

	@Override
	public Optional<String> getAsyncBatchTypeInternalName(@NonNull final I_C_Async_Batch asyncBatch)
	{
		return getAsyncBatchType(asyncBatch).map(AsyncBatchType::getInternalName);
	}

	@Override
	public boolean isAsyncBatchTypeInternalName(@NonNull final I_C_Async_Batch asyncBatch, @NonNull final String expectedInternalName)
	{
		final String internalName = getAsyncBatchTypeInternalName(asyncBatch).orElse(null);
		return internalName != null && internalName.equals(expectedInternalName);
	}

	@Override
	public boolean isAsyncBatchForAutomaticallyInvoicePDFPrinting(@NonNull I_C_Async_Batch asyncBatch)
	{
		final String internalName = getAsyncBatchTypeInternalName(asyncBatch).orElse(null);
		return internalName != null	&& internalName.equals(Async_Constants.C_Async_Batch_InternalName_AutomaticallyInvoicePdfPrinting);
	}

	@Override
	public boolean isAsyncBatchForAutomaticallyDunningPDFPrinting(@NonNull I_C_Async_Batch asyncBatch)
	{
		final String internalName = getAsyncBatchTypeInternalName(asyncBatch).orElse(null);
		return internalName != null && internalName.equals(Async_Constants.C_Async_Batch_InternalName_AutomaticallyDunningPdfPrinting);
	}

	@Override
	public Optional<AsyncBatchType> getAsyncBatchType(@NonNull final I_C_Async_Batch asyncBatch)
	{
		return AsyncBatchTypeId.optionalOfRepoId(asyncBatch.getC_Async_Batch_Type_ID())
				.map(this::getAsyncBatchTypeById);
	}

	@Override
	public AsyncBatchType getAsyncBatchTypeById(@NonNull final AsyncBatchTypeId asyncBatchTypeId)
	{
		return asyncBatchTypesById.getOrLoad(asyncBatchTypeId, this::retrieveAsyncBatchTypeById);
	}

	private AsyncBatchType retrieveAsyncBatchTypeById(@NonNull final AsyncBatchTypeId asyncBatchTypeId)
	{
		final I_C_Async_Batch_Type record = InterfaceWrapperHelper.load(asyncBatchTypeId, I_C_Async_Batch_Type.class);
		return AsyncBatchType.builder()
				.id(asyncBatchTypeId)
				.internalName(record.getInternalName())
				.notificationType(record.getNotificationType())
				.keepAlive(extractKeepAlive(record))
				.skipTimeout(extractSkipTimeout(record))
				.adBoilderPlateId(record.getAD_BoilerPlate_ID())
				.build();
	}

	private static Duration extractKeepAlive(@NonNull final I_C_Async_Batch_Type asyncBatchType)
	{
		final String keepAliveTimeHoursStr = StringUtils.trimBlankToNull(asyncBatchType.getKeepAliveTimeHours());

		// if null or empty, keep alive forever
		if (keepAliveTimeHoursStr == null)
		{
			return Duration.ZERO;
		}

		final int keepAliveTimeHours = Integer.parseInt(keepAliveTimeHoursStr);
		return keepAliveTimeHours > 0 ? Duration.ofHours(keepAliveTimeHours) : Duration.ZERO;
	}

	private static Duration extractSkipTimeout(final I_C_Async_Batch_Type asyncBatchType)
	{
		final int skipTimeoutMillis = asyncBatchType.getSkipTimeoutMillis();
		return skipTimeoutMillis > 0 ? Duration.ofMillis(skipTimeoutMillis) : Duration.ZERO;
	}

	private void updateProcessedFlag(@NonNull final I_C_Async_Batch asyncBatch)
	{
		final List<I_C_Queue_WorkPackage> workPackages = asyncBatchDAO.retrieveWorkPackages(asyncBatch, null);

		if (Check.isEmpty(workPackages))
		{
			return;
		}

		final int workPackagesProcessedCount = (int)workPackages.stream()
				.filter(I_C_Queue_WorkPackage::isProcessed)
				.count();

		final int workPackagesWithErrorCount = (int)workPackages.stream()
				.filter(I_C_Queue_WorkPackage::isError)
				.count();

		final int workPackagesFinalized = workPackagesProcessedCount + workPackagesWithErrorCount;

		final boolean allWorkPackagesAreDone = workPackagesFinalized >= workPackages.size();

		final boolean isProcessed = asyncBatch.getCountExpected() > 0
				? allWorkPackagesAreDone && workPackagesFinalized >= asyncBatch.getCountExpected()
				: allWorkPackagesAreDone;

		asyncBatch.setProcessed(isProcessed);
		asyncBatch.setIsProcessing(!allWorkPackagesAreDone);
	}

	private int getProcessedTimeOffsetMillis()
	{
		return Services.get(ISysConfigBL.class).getIntValue("de.metas.async.api.impl.AsyncBatchBL_ProcessedOffsetMillis", 1);
	}

	private void save(final I_C_Async_Batch asyncBatch)
	{
		Services.get(IQueueDAO.class).save(asyncBatch);
	}

	private int setAsyncBatchCountEnqueued(final I_C_Queue_WorkPackage workPackage, final int offset)
	{
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(workPackage.getC_Async_Batch_ID());
		if (asyncBatchId == null)
		{
			return 0;
		}

		lock.lock();
		try
		{
			final I_C_Async_Batch asyncBatch = asyncBatchDAO.retrieveAsyncBatchRecordOutOfTrx(asyncBatchId);
			final Timestamp enqueued = de.metas.common.util.time.SystemTime.asTimestamp();
			if (asyncBatch.getFirstEnqueued() == null)
			{
				asyncBatch.setFirstEnqueued(enqueued);
			}

			asyncBatch.setLastEnqueued(enqueued);
			final int countEnqueued = asyncBatch.getCountEnqueued() + offset;
			asyncBatch.setCountEnqueued(countEnqueued);
			// we just enqueued something, so we are clearly not done yet
			asyncBatch.setIsProcessing(true);
			asyncBatch.setProcessed(false);
			save(asyncBatch);
			return countEnqueued;
		}
		finally
		{
			lock.unlock();
		}
	}
}
