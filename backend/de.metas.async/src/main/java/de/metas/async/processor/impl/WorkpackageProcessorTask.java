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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import ch.qos.logback.classic.Level;
import de.metas.async.AsyncBatchId;
import de.metas.async.Async_Constants;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkpackageLogsRepository;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.api.IWorkpackageProcessorContextFactory;
import de.metas.async.event.WorkpackageProcessedEvent;
import de.metas.async.event.WorkpackageProcessedEvent.Status;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IWorkpackageSkipRequest;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.descriptor.QueueProcessorDescriptorRepository;
import de.metas.async.processor.descriptor.model.QueuePackageProcessor;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.IWorkpackageProcessor.Result;
import de.metas.async.spi.IWorkpackageProcessor2;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.event.IEventBusFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.exceptions.LockFailedException;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService.TransactionMetadata;
import de.metas.monitoring.adapter.PerformanceMonitoringService.Type;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.exceptions.ServiceConnectionException;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBDeadLockDetectedException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.logging.LoggingHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import static de.metas.async.event.WorkpackageProcessedEvent.Status.DONE;
import static de.metas.async.event.WorkpackageProcessedEvent.Status.ERROR;
import static de.metas.async.event.WorkpackageProcessedEvent.Status.SKIPPED;

@ToString(exclude = { "queueDAO", "workpackageParamDAO", "contextFactory", "asyncBatchBL", "logsRepository", "workPackageProcessorOriginal" })
class WorkpackageProcessorTask implements Runnable
{
	private static final AdMessageKey MSG_PROCESSING_ERROR_NOTIFICATION_TEXT = AdMessageKey.of("de.metas.async.WorkpackageProcessorTask.ProcessingErrorNotificationText");
	private static final AdMessageKey MSG_PROCESSING_ERROR_NOTIFICATION_TITLE = AdMessageKey.of("de.metas.async.WorkpackageProcessorTask.ProcessingErrorNotificationTitle");
	// services
	private static final transient Logger logger = LogManager.getLogger(WorkpackageProcessorTask.class);

	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final transient IEventBusFactory eventBusFactory = Services.get(IEventBusFactory.class);
	private final transient IWorkpackageParamDAO workpackageParamDAO = Services.get(IWorkpackageParamDAO.class);
	private final transient IWorkpackageProcessorContextFactory contextFactory = Services.get(IWorkpackageProcessorContextFactory.class);
	private final transient IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	private final transient QueueProcessorDescriptorRepository queueProcessorDescriptorRepository = QueueProcessorDescriptorRepository.getInstance();

	private final IWorkpackageLogsRepository logsRepository;

	private final IQueueProcessor queueProcessor;
	/**
	 * Workpackage processor (that we got as parameter)
	 */
	private final IWorkpackageProcessor workPackageProcessorOriginal;
	/**
	 * Workpackage processor which is wrapped with all features that we have
	 */
	private final IWorkpackageProcessor2 workPackageProcessorWrapped;
	private final I_C_Queue_WorkPackage workPackage;
	private final String trxNamePrefix;

	// task 09933 just adding this member for now, because it's unclear if in future we want to or have to extend on it or not.
	private final boolean retryOnDeadLock = true;

	public WorkpackageProcessorTask(
			final IQueueProcessor queueProcessor,
			final IWorkpackageProcessor workPackageProcessor,
			@NonNull final I_C_Queue_WorkPackage workPackage,
			@NonNull final IWorkpackageLogsRepository logsRepository)
	{
		this.logsRepository = logsRepository;

		this.queueProcessor = queueProcessor;
		this.workPackage = workPackage;

		workPackageProcessorOriginal = workPackageProcessor;
		workPackageProcessorWrapped = WorkpackageProcessor2Wrapper.wrapIfNeeded(workPackageProcessor);
		trxNamePrefix = workPackageProcessorOriginal.getClass().getSimpleName(); // use work processor's name as trx name prefix
	}

	/**
	 * Creates the context to be used in this processing thread.
	 *
	 * @return processing context
	 */
	private Properties createProcessingCtx()
	{
		// NOTE: we assume de.metas.async.api.impl.WorkPackageQueue.setupWorkpackageContext(Properties, I_C_Queue_WorkPackage) is correctly setting the workpackage ctx
		return InterfaceWrapperHelper.getCtx(workPackage);
	}

	@Override
	public void run()
	{
		final PerformanceMonitoringService service = SpringContextHolder.instance.getBeanOr(
				PerformanceMonitoringService.class,
				NoopPerformanceMonitoringService.INSTANCE);

		service.monitorTransaction(
				this::run0,
				TransactionMetadata.builder()
						.type(Type.ASYNC_WORKPACKAGE)
						.name("Workpackage-Processor - " + queueProcessor.getName())
						.label("de.metas.async.queueProcessor.name", queueProcessor.getName())
						.label(PerformanceMonitoringService.LABEL_WORKPACKAGE_ID, Integer.toString(workPackage.getC_Queue_WorkPackage_ID()))
						.build());
	}

	private void run0()
	{
		final Properties processingCtx = createProcessingCtx();
		final WorkpackageLoggable loggable = createLoggable(workPackage);

		boolean finallyReleaseElementLockIfAny = true; // task 08999: only release the lock if there is no skip request.

		try (final IAutoCloseable contextRestorer = Env.switchContext(processingCtx);
				final IAutoCloseable loggableRestorer = Loggables.temporarySetLoggable(loggable);
				final MDCCloseable workPackageMDC = TableRecordMDC.putTableRecordReference(workPackage);
				final MDCCloseable queueProcessorMDC = MDC.putCloseable("queueProcessor.name", queueProcessor.getName()))
		{
			final IMutable<Result> resultRef = new Mutable<>(null);

			markStartProcessing(workPackage);

			//
			// Process WorkPackage in it's own transaction
			if (workPackageProcessorWrapped.isRunInTransaction())
			{
				final ITrxManager trxManager = Services.get(ITrxManager.class);

				final ITrxRunConfig trxRunConfig = trxManager.newTrxRunConfigBuilder()
						.setTrxPropagation(TrxPropagation.REQUIRES_NEW).setOnRunnableSuccess(OnRunnableSuccess.COMMIT).setOnRunnableFail(OnRunnableFail.ROLLBACK)
						.build();

				trxManager.run(
						trxNamePrefix,
						trxRunConfig,
						trxName_IGNORED -> {
							// ignore the concrete trxName param,
							// by default everything shall use the thread inherited trx
							final Result result = processWorkpackage(ITrx.TRXNAME_ThreadInherited);
							resultRef.setValue(result);
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
				asyncBatchBL.createNotificationRecord(workPackage);

				// increase processed counter
				asyncBatchBL.increaseProcessed(workPackage);
			}
			else
			{
				markError(workPackage, new AdempiereException("Result " + resultRef.getValue() + " not supported for workPackage=" + workPackage));
				throw new IllegalStateException("Result " + resultRef.getValue() + " not supported for workPackage=" + workPackage);
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
				markSkipped(workPackage, skipRequest);
			}
			else
			{
				markError(workPackage, e);
			}
		}
		catch (final Throwable ex)
		{
			final IWorkpackageSkipRequest skipRequest = getWorkpackageSkipRequest(ex);
			if (skipRequest != null)
			{
				finallyReleaseElementLockIfAny = false; // task 08999: don't release the lock yet, because we are going to retry later
				markSkipped(workPackage, skipRequest);
			}
			else
			{
				markError(workPackage, AdempiereException.wrapIfNeeded(ex));
			}
		}
		finally
		{
			afterWorkpackageProcessed(finallyReleaseElementLockIfAny);
			loggable.flush();
		}
	}

	/** Get the workpackage's correlation-uuid-parameter */
	@Nullable
	private UUID extractCorrelationIdOrNull()
	{
		final UUID correlationId;
		final String correlationIdStr = workpackageParamDAO
				.retrieveWorkpackageParams(workPackage)
				.getParameterAsString(Async_Constants.ASYNC_PARAM_CORRELATION_UUID);

		if (EmptyUtil.isNotBlank(correlationIdStr))
		{
			correlationId = UUID.fromString(correlationIdStr);
		}
		else
		{
			correlationId = null;
		}
		return correlationId;
	}

	private WorkpackageLoggable createLoggable(@NonNull final I_C_Queue_WorkPackage workPackage)
	{
		final UserId userId = UserId.ofRepoIdOrNull(workPackage.getAD_User_ID()); // NOTE: in junit tests this is -1/null when it's not saved

		return WorkpackageLoggable.builder()
				.logsRepository(logsRepository)
				.workpackageId(QueueWorkPackageId.ofRepoId(workPackage.getC_Queue_WorkPackage_ID()))
				.adClientId(ClientId.ofRepoId(workPackage.getAD_Client_ID()))
				.userId(userId != null ? userId : UserId.SYSTEM)
				.bufferSize(100)
				.build();
	}

	/**
	 * Method called before we actually start to process the workpackage, but after the transaction is created.
	 */
	private void beforeWorkpackageProcessing()
	{
		// If the current workpackage's processor creates a follow-up-workpackage, the asyncBatch and priority will be forwarded.
		contextFactory.setThreadInheritedAsyncBatch(AsyncBatchId.ofRepoIdOrNull(workPackage.getC_Async_Batch_ID()));
		contextFactory.setThreadInheritedWorkpackageAsyncBatch(AsyncBatchId.ofRepoIdOrNull(workPackage.getC_Async_Batch_ID()));

		final String priority = workPackage.getPriority();
		contextFactory.setThreadInheritedPriority(priority);
	}

	/**
	 * Prepare and execute {@link IWorkpackageProcessor#processWorkPackage(I_C_Queue_WorkPackage, String)} now.
	 *
	 * @param trxName transaction name to be used
	 * @return result
	 */
	private Result processWorkpackage(@Nullable final String trxName)
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
		return invokeProcessorAndHandleException(trxName);
	}

	private Result invokeProcessorAndHandleException(@Nullable final String trxName)
	{
		try
		{
			return workPackageProcessorWrapped.processWorkPackage(workPackage, trxName);
		}
		catch (final ServiceConnectionException e)
		{
			throw handleServiceConnectionException(trxName, e);
		}
		catch (final AdempiereException e)
		{
			final Throwable cause = e.getCause();
			if (cause instanceof ServiceConnectionException)
			{
				throw handleServiceConnectionException(trxName, (ServiceConnectionException)cause);
			}
			throw appendParameters(AdempiereException.wrapIfNeeded(e), trxName);
		}
		catch (final RuntimeException e)
		{
			throw appendParameters(AdempiereException.wrapIfNeeded(e), trxName);
		}
	}

	private RuntimeException handleServiceConnectionException(
			@Nullable final String trxName,
			@NonNull final ServiceConnectionException e)
	{
		final int retryAdvisedInMillis = e.getRetryAdvisedInMillis();
		if (retryAdvisedInMillis > 0)
		{
			Loggables.addLog("Caught a {} with an advise to retry in {}ms; ServiceURL={}",
							 e.getClass().getSimpleName(), retryAdvisedInMillis, e.getServiceURL());

			final WorkpackageSkipRequestException //
					workpackageSkipRequestException = WorkpackageSkipRequestException
					.createWithTimeoutAndThrowable(
							e.getMessage(),
							retryAdvisedInMillis,
							e);
			return appendParameters(workpackageSkipRequestException, trxName);
		}
		return appendParameters(AdempiereException.wrapIfNeeded(e), trxName);
	}

	private AdempiereException appendParameters(@NonNull final AdempiereException e, @Nullable final String trxName)
	{
		return e.appendParametersToMessage()
				.setParameter("I_C_Queue_WorkPackage", workPackage)
				.setParameter("IQueueProcessor", queueProcessor)
				.setParameter("trxName", trxName);
	}

	/**
	 * Method invoked after workpackage is processed. The method is invoked in any case (success, skip, error).
	 * <p>
	 * NOTE: this method is protected to be easily to unit test (i.e. decouple queueProcessor)
	 *
	 * @param releaseElementLockIfAny if <code>true</code> (which is usually the case) and there is a lock on the work package elements, that lock is released in this method.
	 */
	protected void afterWorkpackageProcessed(final boolean releaseElementLockIfAny)
	{
		// get rid of inherited AsyncBatchId and priority
		// actually it's not necessary, but we are doing it for safety reasons
		contextFactory.setThreadInheritedAsyncBatch(null);
		contextFactory.setThreadInheritedWorkpackageAsyncBatch(null);
		contextFactory.setThreadInheritedPriority(null);

		try
		{
			// task 08999 unlock our WP-elements, if there is a lock and if there was no skip request
			final Optional<ILock> elementsLock = workPackageProcessorWrapped.getElementsLock();
			if (releaseElementLockIfAny && elementsLock.isPresent())
			{
				elementsLock.get().close();
			}

		}
		catch (final LockFailedException e)
		{
			// in developer mode, fail, because the unlocking was already done somewhere in the code, which we don't expect.
			e.throwOrLogSevere(Services.get(IDeveloperModeBL.class).isEnabled(), logger);
		}
		catch (final Exception e)
		{
			markError(workPackage, AdempiereException.wrapIfNeeded(e));
		}
		finally
		{
			try
			{
				queueProcessor.getQueue().unlock(workPackage);
			}
			catch (final Exception e)
			{
				markError(workPackage, AdempiereException.wrapIfNeeded(e));
			}
		}

		// NOTE: when notifying, we shall use the original workpackage processor, because that one is known in exterior
		queueProcessor.notifyWorkpackageProcessed(workPackage, workPackageProcessorOriginal);
	}

	/**
	 * Mark {@link I_C_Queue_WorkPackage} as started to process.
	 */
	private void markStartProcessing(final I_C_Queue_WorkPackage workPackage)
	{
		workPackage.setLastStartTime(SystemTime.asTimestamp());
		queueDAO.save(workPackage);
	}

	/**
	 * Sets workpackage's LastEndTime and LastDurationMillis.
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
	 */
	private void markProcessed(@NonNull final I_C_Queue_WorkPackage workPackage)
	{
		workPackage.setIsError(false); // just in case it was true

		// clear it up; we don't have any issues this time
		workPackage.setErrorMsg(null);
		workPackage.setAD_Issue(null);

		workPackage.setProcessed(true);

		setLastEndTime(workPackage); // update statistics

		queueDAO.save(workPackage);

		createAndFireEventWithStatus(workPackage, DONE);
	}

	/**
	 * Sets the current workpagage's skipped-at timestamp.
	 */
	private void markSkipped(
			@NonNull final I_C_Queue_WorkPackage workPackage,
			@NonNull final IWorkpackageSkipRequest skipRequest)
	{
		final Timestamp skippedAt = SystemTime.asTimestamp();
		final Exception skipException = skipRequest.getException();
		final int skippedCount = workPackage.getSkipped_Count();

		final int skipTimeoutMillis = skipRequest.getSkipTimeoutMillis() > 0 ? skipRequest.getSkipTimeoutMillis() : Async_Constants.DEFAULT_RETRY_TIMEOUT_MILLIS;

		workPackage.setProcessed(false); // just in case it was true
		workPackage.setIsError(false); // just in case it was true
		workPackage.setSkipped_Last_Reason(skipRequest.getSkipReason());
		workPackage.setSkippedAt(skippedAt);
		workPackage.setSkipTimeoutMillis(skipTimeoutMillis);
		workPackage.setSkipped_Count(skippedCount + 1);

		if (skippedCount <= 0 || workPackage.getSkipped_First_Time() == null)
		{
			workPackage.setSkipped_First_Time(skippedAt);
		}

		setLastEndTime(workPackage); // update statistics

		queueDAO.save(workPackage);

		final String processorName;
		final QueuePackageProcessorId packageProcessorId = QueuePackageProcessorId.ofRepoIdOrNull(workPackage.getC_Queue_PackageProcessor_ID());
		if (packageProcessorId == null)
		{
			processorName = "<null>"; // might happen in unit tests.
		}
		else
		{
			final QueuePackageProcessor packageProcessor = queueProcessorDescriptorRepository.getPackageProcessor(packageProcessorId);

			processorName = CoalesceUtil.coalesce(packageProcessor.getInternalName(), packageProcessor.getClassname());
		}
		final String msg = StringUtils.formatMessage("Skipped while processing workpackage by processor {}; workpackage={}", processorName, workPackage);

		// log error to console (for later audit):
		Loggables.withLogger(logger, Level.DEBUG).addLog(msg, skipException);

		createAndFireEventWithStatus(workPackage, SKIPPED);
	}

	private void markError(final I_C_Queue_WorkPackage workPackage, final AdempiereException ex)
	{
		final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(ex);

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
			// TODO shall we also release the elements lock if any?
			workPackage.setProcessed(true);
		}

		workPackage.setIsError(true);
		workPackage.setErrorMsg(ex.getLocalizedMessage());
		workPackage.setAD_Issue_ID(issueId.getRepoId());

		setLastEndTime(workPackage); // update statistics

		queueDAO.save(workPackage);

		// log error to console (for later audit):
		final Level logLevel = Services.get(IDeveloperModeBL.class).isEnabled() ? Level.WARN : Level.INFO;
		LoggingHelper.log(logger, logLevel, "Error while processing workpackage: {}", workPackage, ex);
		Loggables.addLog("Error while processing workpackage: {0}", workPackage);

		notifyErrorAfterCommit(workPackage, ex);

		createAndFireEventWithStatus(workPackage, ERROR);
	}

	private void createAndFireEventWithStatus(
			@NonNull final I_C_Queue_WorkPackage workPackage,
			@NonNull final Status status)
	{
		final UUID correlationId = extractCorrelationIdOrNull();
		if (correlationId == null)
		{
			return; // nothing to do
		}

		final WorkpackageProcessedEvent processingDoneEvent = WorkpackageProcessedEvent.builder()
				.correlationId(correlationId)
				.workPackageId(QueueWorkPackageId.ofRepoId(workPackage.getC_Queue_WorkPackage_ID()))
				.status(status)
				.build();
		eventBusFactory.getEventBus(Async_Constants.WORKPACKAGE_LIFECYCLE_TOPIC).enqueueObject(processingDoneEvent);
	}

	private void notifyErrorAfterCommit(final I_C_Queue_WorkPackage workpackage, final AdempiereException ex)
	{
		final UserId userInChargeId = workpackage.getAD_User_InCharge_ID() > 0 ? UserId.ofRepoId(workpackage.getAD_User_InCharge_ID()) : null;
		if (userInChargeId == null)
		{
			return;
		}

		if (ex.isUserNotified())
		{
			return;
		}
		ex.markUserNotified();
		final String errorMsg = ex.getLocalizedMessage();

		final int workpackageId = workpackage.getC_Queue_WorkPackage_ID();

		final INotificationBL notificationBL = Services.get(INotificationBL.class);
		notificationBL.sendAfterCommit(UserNotificationRequest.builder()
											   .topic(Async_Constants.WORKPACKAGE_ERROR_USER_NOTIFICATIONS_TOPIC)
											   .recipientUserId(userInChargeId)
											   .contentADMessage(MSG_PROCESSING_ERROR_NOTIFICATION_TEXT)
											   .contentADMessageParam(workpackageId)
											   .contentADMessageParam(errorMsg)
											   .targetAction(TargetRecordAction.of(I_C_Queue_WorkPackage.Table_Name, workpackageId))
											   .build());
	}

	/**
	 * Extracts the {@link IWorkpackageSkipRequest} from given exception
	 *
	 * @return {@link IWorkpackageSkipRequest} or null
	 */
	@Nullable
	private static IWorkpackageSkipRequest getWorkpackageSkipRequest(final Throwable ex)
	{
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
