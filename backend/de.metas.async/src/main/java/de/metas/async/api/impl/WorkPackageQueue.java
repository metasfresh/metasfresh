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

import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.Async_Constants;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageBL;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.IWorkpackageProcessorContextFactory;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IMutableQueueProcessorStatistics;
import de.metas.async.processor.IQueueProcessorEventDispatcher;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IQueueProcessorListener;
import de.metas.async.processor.IWorkpackageProcessorExecutionResult;
import de.metas.async.processor.IWorkpackageProcessorFactory;
import de.metas.async.processor.NullQueueProcessorListener;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.QueueProcessorId;
import de.metas.async.processor.impl.SyncQueueProcessorListener;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.async.spi.NullWorkpackagePrio;
import de.metas.lock.api.ILockManager;
import de.metas.lock.exceptions.UnlockFailedException;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

public class WorkPackageQueue implements IWorkPackageQueue
{
	private static final transient Logger logger = LogManager.getLogger(WorkPackageQueue.class);

	private final transient IQueueDAO dao;
	private final transient IWorkpackageProcessorContextFactory contextFactory = Services.get(IWorkpackageProcessorContextFactory.class);
	private final transient IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final transient IWorkPackageBL workPackageBL = Services.get(IWorkPackageBL.class);
	private final transient IWorkpackageProcessorFactory workpackageProcessorFactory = Services.get(IWorkpackageProcessorFactory.class);
	private final transient QueueProcessorDAO queueProcessorDAO = QueueProcessorDAO.getInstance();

	private final Properties ctx;
	private final ImmutableSet<QueuePackageProcessorId> packageProcessorIds;
	private final QueueProcessorId queueProcessorId;
	private final String priorityFrom;
	private final int skipRetryTimeoutMillis;

	/**
	 * C_Queue_PackageProcessor_ID used for enquing
	 */
	private final QueuePackageProcessorId enquingPackageProcessorId;

	/**
	 * Task http://dewiki908/mediawiki/index.php/09049_Priorit%C3%A4ten_Strategie_asynch_%28105016248827%29
	 */
	private final String enquingPackageProcessorInternalName;

	/**
	 * {@link I_C_Async_Batch} to be used when enquing new workpackages
	 */
	private AsyncBatchId asyncBatchForNewWorkpackages;

	private boolean asyncBatchForNewWorkpackagesSet = false;

	private final ReentrantLock mainLock = new ReentrantLock();

	private WorkPackageQueue(
			@NonNull final Properties ctx,
			@NonNull final ImmutableSet<QueuePackageProcessorId> packageProcessorIds,
			@NonNull final QueueProcessorId queueProcessorId,
			final String enquingPackageProcessorInternalName,
			final String priorityFrom,
			final boolean forEnqueing)
	{
		Check.assume(!packageProcessorIds.isEmpty(), "packageProcessorIds not empty");
		// Check.assume(retryTimeoutMillis >= 0, "retryTimeoutMillis={} >= 0", retryTimeoutMillis);

		dao = Services.get(IQueueDAO.class);

		this.ctx = ctx;
		this.packageProcessorIds = packageProcessorIds;
		this.queueProcessorId = queueProcessorId;
		this.priorityFrom = priorityFrom;
		this.skipRetryTimeoutMillis = Async_Constants.DEFAULT_RETRY_TIMEOUT_MILLIS;

		if (forEnqueing)
		{
			enquingPackageProcessorId = packageProcessorIds.iterator().next();
			this.enquingPackageProcessorInternalName = enquingPackageProcessorInternalName;
		}
		else
		{
			enquingPackageProcessorId = null;
			this.enquingPackageProcessorInternalName = null;
		}
	}

	public static WorkPackageQueue createForEnqueuing(
			@NonNull final Properties ctx,
			@NonNull final QueuePackageProcessorId packageProcessorId,
			@NonNull final QueueProcessorId queueProcessorId,
			final String enquingPackageProcessorInternalName)
	{
		return new WorkPackageQueue(ctx,
									ImmutableSet.of(packageProcessorId),
									queueProcessorId,
									enquingPackageProcessorInternalName,
									null,
									true);
	}

	public static WorkPackageQueue createForQueueProcessing(
			@NonNull final Properties ctx,
			@NonNull final ImmutableSet<QueuePackageProcessorId> packageProcessorIds,
			@NonNull final QueueProcessorId queueProcessorId,
			final String priorityFrom)
	{
		return new WorkPackageQueue(ctx,
				packageProcessorIds,
				queueProcessorId,
				null, // enquingPackageProcessorInternalName
				priorityFrom,
				false);
	}

	@Override
	public String toString()
	{
		return "WorkPackageQueue ["
				+ "packageProcessorIds=" + packageProcessorIds
				+ ", priorityFrom=" + priorityFrom
				+ ", skipRetryTimeoutMillis=" + skipRetryTimeoutMillis
				+ ", enquingPackageProcessorId=" + enquingPackageProcessorId
				+ "]";
	}

	/**
	 * Update context from work package (AD_Client_ID, AD_Org_ID, AD_User_ID, AD_Role_ID etc).
	 *
	 * NOTE: this will be the context that work package processors will use on processing
	 */
	public static void setupWorkPackageContext(final Properties workPackageCtx, final I_C_Queue_WorkPackage workPackage)
	{
		//
		// AD_Client_ID/AD_Org_ID
		final ClientId clientId = ClientId.ofRepoId(workPackage.getAD_Client_ID());
		final OrgId orgId = OrgId.ofRepoId(workPackage.getAD_Org_ID());
		Env.setContext(workPackageCtx, Env.CTXNAME_AD_Client_ID, clientId.getRepoId());
		Env.setContext(workPackageCtx, Env.CTXNAME_AD_Org_ID, orgId.getRepoId());

		//
		// User
		final UserId userId;
		if (!InterfaceWrapperHelper.isNull(workPackage, I_C_Queue_WorkPackage.COLUMNNAME_AD_User_ID))
		{
			userId = UserId.ofRepoId(workPackage.getAD_User_ID());
		}
		else
		{
			userId = UserId.ofRepoIdOrSystem(workPackage.getCreatedBy());
		}
		Env.setContext(workPackageCtx, Env.CTXNAME_AD_User_ID, userId.getRepoId());
		Env.setContext(workPackageCtx, Env.CTXNAME_SalesRep_ID, userId.getRepoId());

		//
		// Role
		final RoleId roleId;
		if (!InterfaceWrapperHelper.isNull(workPackage, I_C_Queue_WorkPackage.COLUMNNAME_AD_Role_ID))
		{
			roleId = RoleId.ofRepoId(workPackage.getAD_Role_ID());
		}
		else
		{
			final IUserRolePermissions role = Services.get(IUserRolePermissionsDAO.class)
					.retrieveFirstUserRolesPermissionsForUserWithOrgAccess(
							clientId,
							orgId,
							userId,
							Env.getLocalDate(workPackageCtx))
					.orElse(null);
			roleId = role == null ? null : role.getRoleId();
		}
		Env.setContext(workPackageCtx, Env.CTXNAME_AD_Role_ID, RoleId.toRepoId(roleId, Env.CTXVALUE_AD_Role_ID_NONE));

		// FRESH-314: also store #AD_PInstance_ID, we might want to access this information (currently in AD_ChangeLog)
		Env.setContext(workPackageCtx, Env.CTXNAME_AD_PInstance_ID, workPackage.getAD_PInstance_ID());

		//
		// Session: N/A
		Env.setContext(workPackageCtx, Env.CTXNAME_AD_Session_ID, Env.CTXVALUE_AD_SESSION_ID_NONE);
	}

	@Override
	public int size()
	{
		mainLock.lock();
		try
		{
			return createQuery(ctx)
					.map(IQuery::count)
					.orElse(0);
		}
		finally
		{
			mainLock.unlock();
		}
	}

	private int localPackagecount = 0; // task 09049

	@Override
	public int getLocalPackageCount()
	{
		return localPackagecount;
	}

	@Override
	public Properties getCtx()
	{
		return new Properties(ctx);
	}

	@Override
	public void unlock(final I_C_Queue_WorkPackage workPackage)
	{
		// NOTE: unlocking shall not be synchronized with mainLock because else we can get dead-locks or unlocked workPackages will be left on shutdown

		try
		{
			final boolean success = Services.get(ILockManager.class).unlock(workPackage);
			if (!success)
			{
				throw new UnlockFailedException("Cannot unlock");
			}
		}
		catch (final Exception e)
		{
			throw UnlockFailedException.wrapIfNeeded(e)
					.setParameter("Workpackage", workPackage);
		}
	}

	@Override
	public boolean unlockNoFail(final I_C_Queue_WorkPackage workPackage)
	{
		try
		{
			unlock(workPackage);
			return true;
		}
		catch (final Exception e)
		{
			logger.warn("Got exception while unlocking " + workPackage, e);
			return false;
		}
	}

	@Override
	public I_C_Queue_WorkPackage enqueueWorkPackage(
			@NonNull final I_C_Queue_WorkPackage workPackage,
			@NonNull final IWorkpackagePrioStrategy priority)
	{
		// TODO: please really consider to move this method somewhere inside de.metas.async.api.impl.WorkPackageBuilder.build()

		workPackage.setProcessed(false);
		workPackage.setIsReadyForProcessing(false);

		if (priority == NullWorkpackagePrio.INSTANCE)
		{
			final String priorityDefault = getPriorityForNewWorkpackage(PRIORITY_AUTO);
			workPackage.setPriority(priorityDefault);
		}
		else
		{
			final String priorityStr = getPriorityForNewWorkpackage(priority);
			workPackage.setPriority(priorityStr);
		}

		//

		// C_Async_Batch_ID - get it from context if available
		// set only if is not new workpackage; the first new one is always for the async batch itself and we do want to track it
		if (!asyncBatchForNewWorkpackagesSet)
		{
			final AsyncBatchId asyncBatchId = getAsyncBatchIdForNewWorkpackage();
			workPackage.setC_Async_Batch_ID(AsyncBatchId.toRepoId(asyncBatchId));
		}

		// increase enqueued counter
		final int enqueuedCount = asyncBatchBL.increaseEnqueued(workPackage);
		workPackage.setBatchEnqueuedCount(enqueuedCount);

		// Set User/Role
		workPackage.setAD_User_ID(Env.getAD_User_ID(ctx));
		workPackage.setAD_Role_ID(Env.getAD_Role_ID(ctx));

		// task 09700
		final UserId userIdInCharge = workPackageBL.getUserIdInCharge(workPackage).orElse(null);
		if (userIdInCharge != null)
		{
			workPackage.setAD_User_InCharge_ID(userIdInCharge.getRepoId());
		}

		saveWorkPackage(workPackage);
		localPackagecount++; // task 09049

		//
		// Statistics
		final I_C_Queue_PackageProcessor queuePackageProcessor = queueProcessorDAO.getPackageProcessor(QueuePackageProcessorId.ofRepoId(workPackage.getC_Queue_PackageProcessor_ID()));
		final IMutableQueueProcessorStatistics workpackageProcessorStatistics = workpackageProcessorFactory.getWorkpackageProcessorStatistics(queuePackageProcessor);
		workpackageProcessorStatistics.incrementQueueSize();

		return workPackage;
	}

	@Override
	public I_C_Queue_Element enqueueElement(
			@NonNull final I_C_Queue_WorkPackage workPackage,
			final int adTableId,
			final int recordId)
	{
		try
		{
			Check.assume(adTableId > 0, "AD_Table_ID > 0");
			Check.assume(recordId > 0, "Record_ID > 0");

			final Properties ctx = InterfaceWrapperHelper.getCtx(workPackage);
			final String trxName = InterfaceWrapperHelper.getTrxName(workPackage);

			final I_C_Queue_Element element = InterfaceWrapperHelper.create(ctx, I_C_Queue_Element.class, trxName);

			// Make sure we are not registering on other AD_Client_ID
			final int elementClientId = element.getAD_Client_ID();
			final int workPackageClientId = workPackage.getAD_Client_ID();
			Check.assume(
					elementClientId == workPackageClientId,
					"Element's AD_Client_ID({}) shall be the same as WorkPackage's AD_Client_ID({})",
					elementClientId, workPackageClientId);

			element.setC_Queue_WorkPackage(workPackage);
			element.setAD_Org_ID(workPackage.getAD_Org_ID());
			element.setAD_Table_ID(adTableId);
			element.setRecord_ID(recordId);

			dao.save(element);
			return element;
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("workPackage", workPackage)
					.setParameter("adTableId", adTableId)
					.setParameter("recordId", recordId);
		}
	}

	private void saveWorkPackage(@NonNull final I_C_Queue_WorkPackage workPackage)
	{
		try
		{
			dao.save(workPackage);
		}
		catch (final Throwable e)
		{
			asyncBatchBL.decreaseEnqueued(workPackage);
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@Override
	public void enqueueElements(final I_C_Queue_WorkPackage workPackage, final int adTableId, final List<Integer> recordIds)
	{
		Check.assumeNotEmpty(recordIds, "recordIds not empty");
		for (final int recordId : recordIds)
		{
			enqueueElement(workPackage, adTableId, recordId);
		}
	}

	@Override
	public I_C_Queue_Element enqueueElement(
			@NonNull final I_C_Queue_WorkPackage workPackage,
			@NonNull final TableRecordReference modelReference)
	{
		return enqueueElement(workPackage, modelReference.getAD_Table_ID(), modelReference.getRecord_ID());
	}

	@Override
	public void enqueueElements(final I_C_Queue_WorkPackage workPackage, @NonNull final Iterable<TableRecordReference> models)
	{
		boolean empty = true;
		for (final TableRecordReference model : models)
		{
			enqueueElement(workPackage, model);
			empty = false;
		}

		Check.assume(!empty, "models not empty");
	}

	@Override
	public I_C_Queue_Element enqueueElement(final Object model)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);

		final I_C_Queue_WorkPackage workPackage = newWorkPackage(ctx).buildWithPackageProcessor();

		enqueueWorkPackage(workPackage, PRIORITY_AUTO); // default priority

		final I_C_Async_Batch asyncBatch = InterfaceWrapperHelper.getDynAttribute(model, Async_Constants.C_Async_Batch);
		if (asyncBatch != null)
		{
			workPackage.setC_Async_Batch(asyncBatch);
		}

		final I_C_Queue_Element element = enqueueElement(workPackage, TableRecordReference.of(model));

		final String trxName = InterfaceWrapperHelper.getTrxName(model);
		markReadyForProcessingAfterTrxCommit(workPackage, trxName); // 04265

		return element;
	}

	@Override
	public I_C_Queue_Element enqueueElement(final Properties ctx, final int adTableId, final int recordId)
	{
		final I_C_Queue_WorkPackage workPackage = newWorkPackage(ctx).buildWithPackageProcessor();
		enqueueWorkPackage(workPackage, PRIORITY_AUTO); // default priority
		final I_C_Queue_Element element = enqueueElement(workPackage, adTableId, recordId);

		final String trxName = ITrx.TRXNAME_None;
		markReadyForProcessingAfterTrxCommit(workPackage, trxName);

		return element;
	}

	@Override
	public Future<IWorkpackageProcessorExecutionResult> markReadyForProcessingAfterTrxCommit(
			final I_C_Queue_WorkPackage workPackage,
			final String trxName)
	{
		final SyncQueueProcessorListener callback = new SyncQueueProcessorListener();

		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final ITrx trx = trxManager.get(trxName, OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isNull(trx))
		{
			// Running out of transaction - marking ready for processing immediately
			markReadyForProcessing(workPackage, callback);
			return callback.getFutureResult();
		}

		final WorkpackageTrxListener workpackageTrxListener = new WorkpackageTrxListener(workPackage, callback);

		final ITrxListenerManager trxListenerManager = trxManager.getTrxListenerManager(trxName);
		trxListenerManager
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(workpackageTrxListener::afterCommit);
		trxListenerManager
				.newEventListener(TrxEventTiming.AFTER_ROLLBACK)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(workpackageTrxListener::afterRollback);

		return callback.getFutureResult();
	}

	private class WorkpackageTrxListener
	{
		private final ReentrantLock sync = new ReentrantLock();

		// since this var is read and written within locks, it should probably be multi-thread-safe => make it vialtile
		private volatile boolean hit = false;

		private final I_C_Queue_WorkPackage workPackage;
		private final SyncQueueProcessorListener callback;

		private WorkpackageTrxListener(
				final I_C_Queue_WorkPackage workPackage,
				final SyncQueueProcessorListener callback)
		{
			this.workPackage = workPackage;
			this.callback = callback;
		}

		public void afterCommit(final ITrx trx)
		{
			sync.lock();
			try
			{
				if (hit)
				{
					// transaction was previously commited or rollbacked
					// now, there is nothing we can do
					return;
				}
				markReadyForProcessing(workPackage, callback);
				hit = true;
			}
			finally
			{
				sync.unlock();
			}
		}

		public void afterRollback(final ITrx trx)
		{
			sync.lock();
			try
			{
				if (hit)
				{
					return;
				}

				final AdempiereException error = new AdempiereException("Transaction '" + (trx != null ? trx.getTrxName() : "<null>") + "' was rollback");
				callback.cancelWithError(error);
				hit = true;
			}
			finally
			{
				sync.unlock();
			}
		}
	}

	@Override
	public Future<IWorkpackageProcessorExecutionResult> markReadyForProcessingAndReturn(final I_C_Queue_WorkPackage workPackage)
	{
		final SyncQueueProcessorListener callback = new SyncQueueProcessorListener();
		markReadyForProcessing(workPackage, callback);

		return callback.getFutureResult();
	}

	@Override
	public void markReadyForProcessing(final I_C_Queue_WorkPackage workPackage)
	{
		markReadyForProcessing(workPackage, NullQueueProcessorListener.instance);
	}

	@Override
	public void markReadyForProcessing(@NonNull final I_C_Queue_WorkPackage workPackage, @NonNull final IQueueProcessorListener callback)
	{
		try (final MDCCloseable ignore = TableRecordMDC.putTableRecordReference(workPackage))
		{
			final IQueueProcessorEventDispatcher queueProcessorEventDispatcher = Services.get(IQueueProcessorFactory.class).getQueueProcessorEventDispatcher();

			boolean success = false;

			mainLock.lock();
			try
			{
				// Callback: Register the callback before marking the workpackage as ready for processing
				queueProcessorEventDispatcher.registerListener(callback, workPackage.getC_Queue_WorkPackage_ID());

				// Mark the workpackage as ready for processing and save it
				workPackage.setIsReadyForProcessing(true);
				dao.save(workPackage);
				logger.debug("C_Queue_WorkPackage.IsReadyForProcessing is now set to true");
				success = true;
			}
			finally
			{
				try
				{
					// Callback: if something went wrong we need to unregister the callback
					if (!success)
					{
						queueProcessorEventDispatcher.unregisterListener(callback, workPackage.getC_Queue_WorkPackage_ID());
					}
				}
				finally
				{
					mainLock.unlock(); // make sure we unlock, even if unregisterListener failed
				}
			}
		}
	}

	@NonNull
	public Optional<IQuery<I_C_Queue_WorkPackage>> createQuery(final Properties workPackageCtx, @Nullable final QueryLimit limit)
	{
		//
		// Filter out processors which were temporary blacklisted
		final Set<QueuePackageProcessorId> availablePackageProcessorIds = packageProcessorIds
				.stream()
				.filter(packageProcessorId -> !workpackageProcessorFactory.isWorkpackageProcessorBlacklisted(packageProcessorId.getRepoId()))
				.collect(ImmutableSet.toImmutableSet());

		if (availablePackageProcessorIds.isEmpty())
		{
			return Optional.empty();
		}

		final WorkPackageQuery workPackageQuery = new WorkPackageQuery();
		workPackageQuery.setProcessed(false);
		workPackageQuery.setReadyForProcessing(true);
		workPackageQuery.setError(false);
		workPackageQuery.setSkippedTimeoutMillis(skipRetryTimeoutMillis);
		workPackageQuery.setPackageProcessorIds(availablePackageProcessorIds);
		workPackageQuery.setPriorityFrom(priorityFrom);
		workPackageQuery.setLimit(limit);

		return Optional.of(dao.createQuery(workPackageCtx, workPackageQuery));
	}

	@Override
	public WorkPackageQueue setAsyncBatchIdForNewWorkpackages(final AsyncBatchId asyncBatchId)
	{
		asyncBatchForNewWorkpackages = asyncBatchId;
		asyncBatchForNewWorkpackagesSet = true;

		// set also in thread
		contextFactory.setThreadInheritedAsyncBatch(asyncBatchId);
		return this;
	}

	@NonNull
	private Optional<IQuery<I_C_Queue_WorkPackage>> createQuery(final Properties workPackageCtx)
	{
		return createQuery(workPackageCtx, QueryLimit.NO_LIMIT);
	}

	private AsyncBatchId getAsyncBatchIdForNewWorkpackage()
	{
		//
		// Use the preconfigured C_Async_Batch (if any)
		if (asyncBatchForNewWorkpackagesSet)
		{
			return asyncBatchForNewWorkpackages;
		}

		//
		// Use the one from thread context (if any)
		return contextFactory.getThreadInheritedAsyncBatchId();
	}

	/**
	 * Gets the priority to be used for new workpackages.<br>
	 * If there is a thread inherited priority available, then that one is returned (task 06283). Otherwise, the given <code>defaultPrio</code> is returned.
	 *
	 * @return default priority; never returns null
	 */
	private String getPriorityForNewWorkpackage(final IWorkpackagePrioStrategy defaultPrio)
	{

		//
		// Use the one from thread context (if any)
		final String priority = contextFactory.getThreadInheritedPriority();
		if (!Check.isEmpty(priority, true))
		{
			return priority;
		}

		//
		// No priority set => return automatic priority
		return defaultPrio.getPrioriy(this);
	}

	@Override
	public String getEnquingPackageProcessorInternalName()
	{
		Check.errorIf(Check.isEmpty(enquingPackageProcessorInternalName, true),
				UnsupportedOperationException.class,
				"Queue {} has no EnqueuingProcessorInternalName. It was problably not intended for enqueuing, but for queue processing",
				this);

		return enquingPackageProcessorInternalName;
	}

	@Override
	public IWorkPackageBuilder newWorkPackage()
	{
		return newWorkPackage(ctx);
	}

	@Override
	public IWorkPackageBuilder newWorkPackage(final Properties context)
	{
		if (enquingPackageProcessorId == null)
		{
			throw new IllegalStateException("Enquing not allowed");
		}

		return new WorkPackageBuilder(context, this, enquingPackageProcessorId);
	}

	@NonNull
	public Set<QueuePackageProcessorId> getQueuePackageProcessorIds()
	{
		return packageProcessorIds;
	}

	@Override
	public QueueProcessorId getQueueProcessorId()
	{
		return queueProcessorId;
	}
}
