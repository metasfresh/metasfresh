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
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_WorkPackage;
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

	private final ReentrantLock lock = new ReentrantLock();

	@Override
	public IAsyncBatchBuilder newAsyncBatch()
	{
		return new AsyncBatchBuilder(this);
	}

	// FIXME: instead of synchronizing on AsyncBatchBL, consider increasing the value directly on the DB, in an "atomic" way, and then just refresh asyncBatch
	@Override
	public void increaseEnqueued(final I_C_Queue_WorkPackage workPackage)
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
				asyncBatch.setCountEnqueued(asyncBatch.getCountEnqueued() + 1);
				save(asyncBatch);
			}
			finally
			{
				lock.unlock();
			}
		}

	}

	// FIXME: instead of synchronizing on AsyncBatchBL, consider increasing the value directly on the DB, in an "atomic" way, and then just refresh asyncBatch
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

		final I_C_Queue_WorkPackage queueWorkpackage = queue.enqueueWorkPackage(queueBlock, prio);

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

//	@Override
//	public void sendEMail(final I_C_Async_Batch asyncBatch)
//	{
//
//		final Properties ctx = InterfaceWrapperHelper.getCtx(asyncBatch);
//		final String trxName = InterfaceWrapperHelper.getTrxName(asyncBatch);
//
//		final I_C_Async_Batch_Type asyncBatchType = asyncBatch.getC_Async_Batch_Type();
//		Check.assumeNotNull(asyncBatchType, "Async Batch type should not be null for async batch! ", asyncBatch.getC_Async_Batch_ID());
//
//		// do nothing is the flag for sending mail is not checked
//		if (!asyncBatchType.isSendMail())
//		{
//			return;
//		}
//
//		final I_AD_BoilerPlate boilerPlate = asyncBatchType.getAD_BoilerPlate();
//		Check.assumeNotNull(boilerPlate, "Boiler plate should not be null for async batch type ! ", asyncBatchType.getC_Async_Batch_Type_ID());
//
//		final MADBoilerPlate text = InterfaceWrapperHelper.create(boilerPlate, MADBoilerPlate.class);
//		if (text == null)
//		{
//			return; // nothing to send
//		}
//
//		MADBoilerPlate.sendEMail(new IEMailEditor()
//		{
//			@Override
//			public Object getBaseObject()
//			{
//				return InterfaceWrapperHelper.create(ctx, asyncBatch.getCreatedBy(), I_AD_User.class, trxName);
//			}
//
//			@Override
//			public int getAD_Table_ID()
//			{
//				return InterfaceWrapperHelper.getTableId(I_C_Async_Batch.class);
//			}
//
//			@Override
//			public int getRecord_ID()
//			{
//				return asyncBatch.getC_Async_Batch_ID();
//			}
//
//			@Override
//			public EMail sendEMail(MUser from, String toEmail, String subject, Map<String, Object> variables)
//			{
//				final MClient client = LegacyAdapters.convertToPO(Services.get(IClientDAO.class).retriveClient(ctx));
//
//				variables.put(MADBoilerPlate.VAR_UserPO, asyncBatch);
//
//				// try to set language; take first from partner; if does not exists, take it from client
//				final I_AD_User user = InterfaceWrapperHelper.create(ctx, asyncBatch.getCreatedBy(), I_AD_User.class, ITrx.TRXNAME_None);
//				final I_C_BPartner partner = user.getC_BPartner();
//				String language = "";
//				if (partner != null && partner.getC_BPartner_ID() > 0)
//				{
//					language = partner.getAD_Language();
//				}
//				if (Check.isEmpty(language, true))
//				{
//					language = client.getAD_Language();
//				}
//				variables.put(MADBoilerPlate.VAR_AD_Language, language);
//				//
//				final String message = text.getTextSnippetParsed(variables);
//				//
//				if (Check.isEmpty(message, true))
//					return null;
//				//
//
//				// prepare mail
//				final StringTokenizer st = new StringTokenizer(toEmail, " ,;", false);
//				String to = st.nextToken();
//
//				if (asyncBatch.getCreatedBy() > 0)
//					to = InterfaceWrapperHelper.create(ctx, asyncBatch.getCreatedBy(), I_AD_User.class, trxName).getEMail();
//				final EMail email = client.createEMail(null,
//						to, // to
//						text.getSubject(), // subject
//						message, // message
//						true);
//				if (email == null)
//				{
//					throw new AdempiereException("Cannot create email. Check log.");
//				}
//				while (st.hasMoreTokens())
//					email.addTo(st.nextToken());
//
//				// now send mail
//				final String status = email.send();
//
//				if (!email.isSentOK())
//				{
//					throw new AdempiereException(status);
//				}
//
//				return email;
//			}
//		}, false);
//	}
}
