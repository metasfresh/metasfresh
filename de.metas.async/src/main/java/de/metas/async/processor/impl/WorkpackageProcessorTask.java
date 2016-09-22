package de.metas.async.processor.impl;

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

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.service.IErrorManager;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.exceptions.DBDeadLockDetectedException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.logging.LoggingHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Issue;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import com.google.common.base.Optional;

import ch.qos.logback.classic.Level;
import de.metas.async.Async_Constants;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageBL;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.api.IWorkpackageProcessorContextFactory;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IWorkpackageSkipRequest;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.IWorkpackageProcessor.Result;
import de.metas.async.spi.IWorkpackageProcessor2;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.exceptions.LockFailedException;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;

/* package */class WorkpackageProcessorTask implements Runnable
{
	private static final String MSG_PROCESSING_ERROR_NOTIFICATION_TEXT = "de.metas.async.WorkpackageProcessorTask.ProcessingErrorNotificationText";
	private static final String MSG_PROCESSING_ERROR_NOTIFICATION_TITLE = "de.metas.async.WorkpackageProcessorTask.ProcessingErrorNotificationTitle";
	// services
	private static final transient Logger logger = LogManager.getLogger(WorkpackageProcessorTask.class);
	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final transient IWorkpackageParamDAO workpackageParamDAO = Services.get(IWorkpackageParamDAO.class);
	private final IWorkpackageProcessorContextFactory contextFactory = Services.get(IWorkpackageProcessorContextFactory.class);
	private final IAsyncBatchBL iAsyncBatchBL = Services.get(IAsyncBatchBL.class);

	private IQueueProcessor queueProcessor;
	/** Workpackage processor (that we got as parameter) */
	private final IWorkpackageProcessor workPackageProcessorOriginal;
	/** Workpackage processor which is wrapped with all features that we have */
	private final IWorkpackageProcessor2 workPackageProcessorWrapped;
	private final I_C_Queue_WorkPackage workPackage;
	private final String trxNamePrefix;

	// task 09933 just adding this member for now, because it's unclear if in future we want to or have to extend on it or not.
	private boolean retryOnDeadLock = true;

	public WorkpackageProcessorTask(final IQueueProcessor queueProcessor,
			final IWorkpackageProcessor workPackageProcessor,
			final I_C_Queue_WorkPackage workPackage)
	{
		Check.assumeNotNull(workPackage, "workPackage not null");

		this.queueProcessor = queueProcessor;
		this.workPackage = workPackage;

		this.workPackageProcessorOriginal = workPackageProcessor;
		this.workPackageProcessorWrapped = WorkpackageProcessor2Wrapper.wrapIfNeeded(workPackageProcessor);
		this.trxNamePrefix = workPackageProcessorOriginal.getClass().getSimpleName(); // use work processor's name as trx name prefix
	}

	/**
	 * Creates the context to be used in this processing thread.
	 *
	 * @return processing context
	 */
	private final Properties createProcessingCtx()
	{
		// NOTE: we assume de.metas.async.api.impl.WorkPackageQueue.setupWorkpackageContext(Properties, I_C_Queue_WorkPackage) is correctly setting the workpackage ctx
		final Properties processingCtx = InterfaceWrapperHelper.getCtx(workPackage);
		return processingCtx;
	}

	@Override
	public void run()
	{
		final Properties processingCtx = createProcessingCtx();
		final ILoggable loggable = Services.get(IWorkPackageBL.class).createLoggable(workPackage);

		boolean finallyReleaseElementLockIfAny = true; // task 08999: only release the lock if there is no skip request.

		try (final IAutoCloseable contextRestorer = Env.switchContext(processingCtx);
				final IAutoCloseable loggableRestorer = ILoggable.THREADLOCAL.temporarySetLoggable(loggable))
		{
			final IMutable<Result> resultRef = new Mutable<>(null);

			markStartProcessing(workPackage);

			//
			// Process WorkPackage in it's own transaction
			if (workPackageProcessorWrapped.isRunInTransaction())
			{
				final ITrxManager trxManager = Services.get(ITrxManager.class);
				trxManager.run(
						trxNamePrefix,
						trxManager.createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ROLLBACK),
						new TrxRunnable()
						{
							@Override
							public void run(final String trxName) throws Exception
							{
								// ignore the concrete trxName param,
								// by default everything shall use the thread inherited trx
								final Result result = processWorkpackage(ITrx.TRXNAME_ThreadInherited);
								resultRef.setValue(result);
							}
						});
			}
			//
			// Process the WorkPackage out of transaction
			else
			{
				final Result result = processWorkpackage(ITrx.TRXNAME_None);
				logger.debug("Processing result = {} for work package {}", result, workPackage);
				resultRef.setValue(result);
			}

			//
			// Mark as processed if success
			if (Result.SUCCESS.equals(resultRef.getValue()))
			{
				// only mark the package as processed, when the processor indicates that it has been processed
				markProcessed(workPackage);

				//
				// create notification record if needed
				iAsyncBatchBL.createNotificationRecord(workPackage);

				// increase processed counter
				iAsyncBatchBL.increaseProcessed(workPackage);
			}
			else
			{
				throw new IllegalStateException("Result " + resultRef.getValue() + " not supported for " + workPackage);
			}
		}
		catch (final DBDeadLockDetectedException e)
		{
			if (retryOnDeadLock)
			{
				// task 08999: if there is a deadlock, retry in five seconds
				// task 09933: allow retry-on-deadlock for all tasks
				final int retryms = 5000;
				final String msg = "Deadlock detected; Will retry in " + retryms + " ms. Deadlock-Message: " + e.getMessage();
				loggable.addLog(msg);

				final WorkpackageSkipRequestException skipRequest = WorkpackageSkipRequestException.createWithTimeoutAndThrowable(msg, retryms, e);
				finallyReleaseElementLockIfAny = false; // task 08999: don't release the lock yet, because we are going to retry later
				markSkipped(workPackage, skipRequest, loggable);
			}
			else
			{
				markError(workPackage, e, loggable);
			}
		}
		catch (final Exception e)
		{
			final IWorkpackageSkipRequest skipRequest = getWorkpackageSkipRequest(e);
			if (skipRequest != null)
			{
				finallyReleaseElementLockIfAny = false; // task 08999: don't release the lock yet, because we are going to retry later
				markSkipped(workPackage, skipRequest, loggable);
			}
			else
			{
				markError(workPackage, e, loggable);
			}
		}
		finally
		{
			afterWorkpackageProcessed(finallyReleaseElementLockIfAny, loggable);
		}
	}

	/**
	 * Method called before we actually start to process the workpackage, but after the transaction is created.
	 */
	private final void beforeWorkpackageProcessing()
	{
		// If the current workpackage's processor creates a follow-up-workpackage, the asyncBatch and priority will be forwarded.
		final I_C_Async_Batch asyncBatch = workPackage.getC_Async_Batch();
		contextFactory.setThreadInheritedAsyncBatch(asyncBatch);

		final String priority = workPackage.getPriority();
		contextFactory.setThreadInheritedPriority(priority);
	}

	/**
	 * Prepare and execute {@link IWorkpackageProcessor#processWorkPackage(I_C_Queue_WorkPackage, String)} now.
	 *
	 * @param trxName transaction name to be used
	 * @return result
	 */
	private final Result processWorkpackage(final String trxName)
	{
		// Setup context and everything that needs to be setup before actually starting to process the workpackage
		beforeWorkpackageProcessing();

		// Prepare: set workpackage parameters
		final IParams workpackageParameters = workpackageParamDAO.retrieveWorkpackageParams(workPackage);
		workPackageProcessorWrapped.setParameters(workpackageParameters);

		// Prepare: set workpackage definition
		workPackageProcessorWrapped.setC_Queue_WorkPackage(workPackage);

		// 09216
		// get the WPs that are currently locked for async-processing, and check if one of them shall cause *our* workPackage to be postponed.
		{
			final IQueryBuilder<I_C_Queue_WorkPackage> queryBuilder = Services.get(ILockManager.class)
					.getLockedRecordsQueryBuilder(I_C_Queue_WorkPackage.class, workPackage)
					// do not exclude the current WP; see the ILatchstragegy javadoc for background info.
					// .addNotEqualsFilter(I_C_Queue_WorkPackage.COLUMN_C_Queue_WorkPackage_ID, workPackage.getC_Queue_WorkPackage_ID())
			;
			workPackageProcessorWrapped
					.getLatchStrategy()
					.postponeIfNeeded(workPackage, queryBuilder);
		}

		//
		// Execute the processor
		final Result result = workPackageProcessorWrapped.processWorkPackage(workPackage, trxName);
		return result;
	}

	/**
	 * Method invoked after workpackage is processed. The method is invoked in any case (success, skip, error).
	 *
	 * NOTE: this method is protected to be easily to unit test (i.e. decouple queueProcessor)
	 *
	 * @param releaseElementLockIfAny if <code>true</code> (which is usually the case) and there is a lock on the work package elements, that lock is released in this method.
	 */
	protected void afterWorkpackageProcessed(final boolean releaseElementLockIfAny,
			final ILoggable loggable)
	{
		// get rid of inherited AsyncBatchId and priority
		// actually it's not necessary, but we are doing it for safety reasons
		contextFactory.setThreadInheritedAsyncBatch(null);
		contextFactory.setThreadInheritedPriority(null);

		try
		{
			// task 08999 unlock our WP-elements, if there is a lock and if there was no skip request
			final Optional<ILock> elementsLock = workPackageProcessorWrapped.getElementsLock();
			if (releaseElementLockIfAny && elementsLock.isPresent())
			{
				elementsLock.get().close();
			}

			queueProcessor.getQueue().unlock(workPackage);
		}
		catch (final LockFailedException e)
		{
			// in developer mode, fail, because the unlocking was already done somewhere in the code, which we don't expect.
			e.throwOrLogSevere(Services.get(IDeveloperModeBL.class).isEnabled(), logger);
		}
		catch (final Exception e)
		{
			markError(workPackage, e, loggable);
		}

		// NOTE: when notifying, we shall use the original workpackage processor, because that one is known in exterior
		queueProcessor.notifyWorkpackageProcessed(workPackage, workPackageProcessorOriginal);
	}

	/**
	 * Mark {@link I_C_Queue_WorkPackage} as started to process.
	 *
	 * @param workPackage
	 */
	private void markStartProcessing(final I_C_Queue_WorkPackage workPackage)
	{
		workPackage.setLastStartTime(SystemTime.asTimestamp());
		queueDAO.saveInLocalTrx(workPackage);
	}

	/**
	 * Sets workpackage's LastEndTime and LastDurationMillis.
	 *
	 * @param workPackage
	 */
	private void setLastEndTime(final I_C_Queue_WorkPackage workPackage)
	{
		final Timestamp lastStartTime = workPackage.getLastStartTime();
		final Timestamp lastEndTime = SystemTime.asTimestamp();
		final int lastDurationMillis;
		if (lastStartTime != null)
		{
			lastDurationMillis = (int)(lastEndTime.getTime() - lastStartTime.getTime());
		}
		else
		{
			// shall not happen because LastStartTime shall be set at this point, but let's guard
			lastDurationMillis = 0;
		}
		workPackage.setLastEndTime(lastEndTime);
		workPackage.setLastDurationMillis(lastDurationMillis);
	}

	/**
	 * Unlock and mark {@link I_C_Queue_WorkPackage} as processed
	 *
	 * @param workPackage
	 */
	private void markProcessed(final I_C_Queue_WorkPackage workPackage)
	{
		Check.assumeNotNull(workPackage, "workPackage not null");

		workPackage.setIsError(false); // just in case it was true

		// clear it up; we don't have any issues this time
		workPackage.setErrorMsg(null);
		workPackage.setAD_Issue(null);

		workPackage.setProcessed(true);

		setLastEndTime(workPackage); // update statistics

		queueDAO.saveInLocalTrx(workPackage);
	}

	/**
	 * Sets the current workpagage's skipped-at timestamp.
	 *
	 * @param workPackage
	 */
	private void markSkipped(final I_C_Queue_WorkPackage workPackage,
			final IWorkpackageSkipRequest skipRequest,
			final ILoggable loggable)
	{
		Check.assumeNotNull(workPackage, "Param 'workPackage' not null");
		Check.assumeNotNull(skipRequest, "Param 'skipRequest' not null");

		final Timestamp skippedAt = SystemTime.asTimestamp();
		final Exception skipException = skipRequest.getException();
		final I_AD_Issue issue = skipException == null ? null : Services.get(IErrorManager.class).createIssue(null, skipException);
		final int skippedCount = workPackage.getSkipped_Count();

		final int skipTimeoutMillis = skipRequest.getSkipTimeoutMillis() > 0 ? skipRequest.getSkipTimeoutMillis() : Async_Constants.DEFAULT_RETRY_TIMEOUT_MILLIS;

		workPackage.setProcessed(false); // just in case it was true
		workPackage.setIsError(false); // just in case it was true
		workPackage.setSkipped_Last_Reason(skipRequest.getSkipReason());
		workPackage.setAD_Issue(issue);
		workPackage.setSkippedAt(skippedAt);
		workPackage.setSkipTimeoutMillis(skipTimeoutMillis);
		workPackage.setSkipped_Count(skippedCount + 1);

		if (skippedCount <= 0 || workPackage.getSkipped_First_Time() == null)
		{
			workPackage.setSkipped_First_Time(skippedAt);
		}

		setLastEndTime(workPackage); // update statistics

		queueDAO.saveInLocalTrx(workPackage);

		// log error to console (for later audit):
		logger.info("Skipped while processing workpackage: " + workPackage, skipException);
		loggable.addLog("Skipped while processing workpackage: {0}", workPackage);
	}

	private void markError(final I_C_Queue_WorkPackage workPackage,
			final Exception ex,
			final ILoggable loggable)
	{
		final I_AD_Issue issue = Services.get(IErrorManager.class).createIssue(null, ex);

		//
		// Allow retry processing this workpackage?
		if (workPackageProcessorWrapped.isAllowRetryOnError())
		{
			workPackage.setProcessed(false); // just in case it was true
		}
		else
		{
			// Flag the workpackage as processed in order to:
			// * not allow future retries
			// * avoid discarding items from this workpackage on future workpackages because they were enqueued here
			workPackage.setProcessed(true);
		}

		workPackage.setIsError(true);
		workPackage.setErrorMsg(ex.getLocalizedMessage());
		workPackage.setAD_Issue(issue);

		setLastEndTime(workPackage); // update statistics

		queueDAO.saveInLocalTrx(workPackage);

		// log error to console (for later audit):
		final Level logLevel = Services.get(IDeveloperModeBL.class).isEnabled() ? Level.WARN : Level.INFO;
		LoggingHelper.log(logger, logLevel, "Error while processing workpackage: " + workPackage, ex);
		loggable.addLog("Error while processing workpackage: {0}", workPackage);

		// 09700: notify the user in charge, if one was set
		if (workPackage.getAD_User_InCharge_ID() > 0)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(workPackage);
			final int workPackageID = workPackage.getC_Queue_WorkPackage_ID();
			final String trxName = InterfaceWrapperHelper.getTrxName(workPackage);

			// do the notification after commit, because e.g. if we send a mail, and even if that fails, we don't want this method to fail.
			notifyErrorAfterCommit(ctx, workPackageID, trxName);
		}
	}

	/**
	 *
	 * @param ctx
	 * @param workPackageID
	 * @param trxName
	 * @task http://dewiki908/mediawiki/index.php/09700_Counter_Documents_%28100691234288%29
	 */
	private void notifyErrorAfterCommit(final Properties ctx,
			final int workPackageID,
			final String trxName)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final INotificationBL notificationBL = Services.get(INotificationBL.class);

		trxManager.getTrxListenerManagerOrAutoCommit(trxName)
				.registerListener(new TrxListenerAdapter()
				{
					@Override
					public void afterCommit(final ITrx trx)
					{
						final IMsgBL msgBL = Services.get(IMsgBL.class);

						final I_C_Queue_WorkPackage wpReloaded = InterfaceWrapperHelper.create(ctx, workPackageID, I_C_Queue_WorkPackage.class, ITrx.TRXNAME_None);

						notificationBL.notifyUser(
								wpReloaded.getAD_User_InCharge(),
								MSG_PROCESSING_ERROR_NOTIFICATION_TITLE,
								msgBL.getMsg(ctx,
										MSG_PROCESSING_ERROR_NOTIFICATION_TEXT,
										new Object[] { workPackageID, wpReloaded.getErrorMsg() }),
								TableRecordReference.of(wpReloaded));
					}
				});
	}

	/**
	 * Extracts the {@link IWorkpackageSkipRequest} from given exception
	 *
	 * @param ex
	 * @return {@link IWorkpackageSkipRequest} or null
	 */
	private static final IWorkpackageSkipRequest getWorkpackageSkipRequest(final Throwable ex)
	{
		// internal method, "ex" is never null
		// if (ex == null)
		// {
		// return null;
		// }

		if (ex instanceof IWorkpackageSkipRequest)
		{
			final IWorkpackageSkipRequest skipRequest = (IWorkpackageSkipRequest)ex;
			if (!skipRequest.isSkip())
			{
				return null;
			}

			return skipRequest;
		}

		return null;
	}
}
