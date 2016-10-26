/**
 *
 */
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchBuilder;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Notified;
import de.metas.async.model.X_C_Async_Batch_Type;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.impl.CheckProcessedAsynBatchWorkpackageProcessor;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.async.spi.NullWorkpackagePrio;

/**
 * @author cg
 *
 */
public class AsyncBatchBL implements IAsyncBatchBL
{
	/* package */final static int PROCESSEDTIME_OFFSET_Min = Services.get(ISysConfigBL.class).getIntValue("de.metas.async.api.impl.AsyncBatchBL_ProcessedOffsetMin", 1);

	// services
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);

	private final ReentrantLock lock = new ReentrantLock();

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
		final int asyncBatchId = workPackage.getC_Async_Batch_ID();

		if (asyncBatchId > 0)
		{
			final I_C_Async_Batch asyncBatch = workPackage.getC_Async_Batch();
			final I_C_Async_Batch_Type asyncBatchType = asyncBatch.getC_Async_Batch_Type();
			if (X_C_Async_Batch_Type.NOTIFICATIONTYPE_WorkpackageProcessed.equals(asyncBatchType.getNotificationType()))
			{
				final Properties ctx = InterfaceWrapperHelper.getCtx(workPackage);
				final String trxName = InterfaceWrapperHelper.getTrxName(workPackage);
				
				final I_C_Queue_WorkPackage_Notified wpNotified = InterfaceWrapperHelper.create(ctx, I_C_Queue_WorkPackage_Notified.class, trxName);
				wpNotified.setC_Async_Batch_ID(asyncBatchId);
				wpNotified.setC_Queue_WorkPackage_ID(workPackage.getC_Queue_WorkPackage_ID());
				wpNotified.setBachWorkpackageSeqNo(workPackage.getBatchEnqueuedCount());
				wpNotified.setIsNotified(false);
				Services.get(IQueueDAO.class).saveInLocalTrx(wpNotified);
			}
		}
	}

	private int setAsyncBatchCountEnqueued(final I_C_Queue_WorkPackage workPackage, final int offset)
	{
		final int asyncBatchId = workPackage.getC_Async_Batch_ID();

		if (asyncBatchId > 0)
		{
			lock.lock();

			try
			{
				final I_C_Async_Batch asyncBatch = workPackage.getC_Async_Batch();
				InterfaceWrapperHelper.refresh(asyncBatch);
				final Timestamp enqueued = SystemTime.asTimestamp();
				if (asyncBatch.getFirstEnqueued() == null)
				{
					asyncBatch.setFirstEnqueued(enqueued);
				}

				asyncBatch.setLastEnqueued(enqueued);
				int countEnqueued = asyncBatch.getCountEnqueued() + offset;
				asyncBatch.setCountEnqueued(countEnqueued);
				save(asyncBatch);
				return countEnqueued;
			}
			finally
			{
				lock.unlock();
			}
		}
		else
		{
			return 0;
		}
	}


	@Override
	public void increaseProcessed(final I_C_Queue_WorkPackage workPackage)
	{
		final int asyncBatchId = workPackage.getC_Async_Batch_ID();

		if (asyncBatchId > 0)
		{
			lock.lock();

			try
			{
				final I_C_Async_Batch asyncBatch = workPackage.getC_Async_Batch();
				InterfaceWrapperHelper.refresh(asyncBatch);
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
	}

	private final void save(final I_C_Async_Batch asyncBatch)
	{
		Services.get(IQueueDAO.class).saveInLocalTrx(asyncBatch);
	}

	@Override
	public void enqueueAsyncBatch(final I_C_Async_Batch asyncBatch)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(asyncBatch);
		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, CheckProcessedAsynBatchWorkpackageProcessor.class);
		queue.setAsyncBatchForNewWorkpackages(asyncBatch);
		I_C_Queue_Block queueBlock = null;

		if (queueBlock == null)
		{
			queueBlock = queue.enqueueBlock(ctx);
		}

		final IWorkpackagePrioStrategy prio = NullWorkpackagePrio.INSTANCE; // don't specify a particular prio. this is OK because we assume that there is a dedicated queue/thread for CheckProcessedAsynBatchWorkpackageProcessor

		final I_C_Queue_WorkPackage queueWorkpackage = queue.newBlock()
														.setContext(ctx)
														.newWorkpackage()
														.setPriority(prio)
														.build();

		// Make sure that the watch processor is not in the same batch (because it will affect the counter which we are checking...)
		queueWorkpackage.setC_Async_Batch(null);
		Services.get(IQueueDAO.class).saveInLocalTrx(queueWorkpackage);

		queue.enqueueElement(queueWorkpackage, asyncBatch);
		queue.markReadyForProcessing(queueWorkpackage);
	}

	@Override
	public void updateProcessed(final I_C_Async_Batch asyncBatch)
	{
		if (asyncBatch.isProcessed())
		{
			return;
		}

		final boolean processed = checkProcessed(asyncBatch);
		if (!processed)
		{
			return;
		}

		asyncBatch.setProcessed(true);
		InterfaceWrapperHelper.save(asyncBatch);
		// save(asyncBatch);
	}

	// package level for testing purposes
	/* package */boolean checkProcessed(final I_C_Async_Batch asyncBatch)
	{
		Check.assumeNotNull(asyncBatch, "asyncBatch not null");

		if (asyncBatch.isProcessed())
		{
			return true;
		}

		final int countEnqueued = asyncBatch.getCountEnqueued();
		final int countProcessed = asyncBatch.getCountProcessed();

		// Case: in case enqueued counter or processed counter is zero, we cannot consider this as processed
		if (countEnqueued <= 0 || countProcessed <= 0)
		{
			return false;
		}
		// Case: we have more enqueued work packages than processed
		if (countEnqueued > countProcessed)
		{
			return false;
		}

		//
		final Timestamp firstEnqueued = asyncBatch.getFirstEnqueued();
		if (firstEnqueued == null)
		{
			// shall not happen
			return false;
		}

		// Case: when did not pass enough time between fist enqueue time and now
		final Timestamp now = SystemTime.asTimestamp();
		final Timestamp minTimeAfterFirstEnqueued = TimeUtil.addMinutess(now, -2 * PROCESSEDTIME_OFFSET_Min);
		if (firstEnqueued.compareTo(minTimeAfterFirstEnqueued) > 0)
		{
			return false;
		}

		//
		final Timestamp lastEnqueued = asyncBatch.getLastEnqueued();
		if (lastEnqueued == null)
		{
			// shall not happen
			return false;
		}

		final Timestamp lastProcessed = asyncBatch.getLastProcessed();
		if (lastProcessed == null)
		{
			// shall not happen
			return false;
		}
		// Case: when last processed time is before last enqueued time; this means that we still have packages to process
		if (lastProcessed.compareTo(lastEnqueued) < 0)
		{
			return false;
		}

		// Case: when did not pass enough time between last processed time and now - offset
		// take a bigger time for checking processed because thread could be locked by other thread and we could have some bigger delay
		final Timestamp minTimeAfterLastProcessed = TimeUtil.addMinutess(now, -3 * PROCESSEDTIME_OFFSET_Min);
		if (lastProcessed.compareTo(minTimeAfterLastProcessed) > 0)
		{
			return false;
		}

		//
		// If we reach this point, our batch can be considered processed
		return true;
	}

	@Override
	public boolean keepAliveTimeExpired(final I_C_Async_Batch asyncBatch)
	{
		final I_C_Async_Batch_Type asyncBatchType = asyncBatch.getC_Async_Batch_Type();
		final String keepAliveTimeHours = asyncBatchType.getKeepAliveTimeHours();

		// if null or empty, keep alive for ever
		if (Check.isEmpty(keepAliveTimeHours, true))
		{
			return false;
		}

		final int keepAlive = Integer.valueOf(keepAliveTimeHours);

		// if 0, keep alive for ever
		if (keepAlive == 0)
		{
			return false;
		}

		final Timestamp lastUpdated = asyncBatch.getUpdated();
		final Timestamp today = SystemTime.asTimestamp();

		final long diffHours = TimeUtil.getHoursBetween(lastUpdated, today);

		if (diffHours > keepAlive)
		{
			return true;
		}

		return false;
	}

	@Override
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
}
