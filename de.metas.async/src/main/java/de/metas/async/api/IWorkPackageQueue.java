package de.metas.async.api;

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


import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import org.compiere.util.Trx;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessorListener;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.IWorkpackageProcessorExecutionResult;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.impl.SizeBasedWorkpackagePrio;
import de.metas.lock.exceptions.UnlockFailedException;

/**
 * Use {@link IWorkPackageQueueFactory} to get an instance.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IWorkPackageQueue
{
	/**
	 * Timeout: wait forever, until we get next item
	 */
	public static final int TIMEOUT_Infinite = 0;

	/**
	 * Timeout: if we did not get the item first, don't retry at all
	 */
	public static final int TIMEOUT_OneTimeOnly = -1;

	/**
	 * @task http://dewiki908/mediawiki/index.php/09049_Priorit%C3%A4ten_Strategie_asynch_%28105016248827%29
	 */
	public static final IWorkpackagePrioStrategy PRIORITY_AUTO = SizeBasedWorkpackagePrio.INSTANCE;

	/**
	 * Retrieves the oldest work package with the highest priority that is supposed to be handled by the <code>AD_Process</code> with the given adProcessId.
	 * <p>
	 * In addition, locks the work package so that other calling processes won't see it when selecting the next package using this method.
	 * 
	 * Notes:
	 * <ul>
	 * <li>in order to process the returned package, it's recommended to use {@link #processLockedPackage(I_C_Queue_WorkPackage, IWorkpackageProcessor)}</li>
	 * <li>the returned package's <code>ctx</code> contains the <code>#AD_Client_ID</code>, <code>#AD_Org_ID</code> and <code>#AD_User_ID</code> of the package's AD_Client_ID, AD_Org_ID and CreatedBy
	 * respectively</li>
	 * <li>if a package has previously been skipped then it won't be returned before the timeout of {@link #DEFAULT_RETRY_TIMEOUT_MILLIS} milliseconds has passed</li>
	 * <li>the context which is incorporated in returned package (i.e. InterfaceWrapperHelper.getCtx(workpackage)) is not the same as the context given as argument. That context is modified in order
	 * to have the right AD_Client_ID, AD_Org_ID and AD_User_ID.
	 * </ul>
	 * 
	 * @param ctx
	 * @param timeoutMillis if there is no workpackage available and timeoutMillis > 0 this method will try to poll for given timeout until it will return null (nothing found)
	 * @return {@link I_C_Queue_WorkPackage} or <code>null</code> if there either is no package or the calling process is too slow (compared with other concurrent processes also looking for work with
	 *         the same adProcessId) to select and then lock an available package after *many* tries.
	 */
	I_C_Queue_WorkPackage pollAndLock(long timeoutMillis);

	/**
	 * Unlocks given package
	 * 
	 * @param workPackage
	 * @throws UnlockFailedException if unlocking fails
	 */
	void unlock(I_C_Queue_WorkPackage workPackage) throws UnlockFailedException;

	/**
	 * Unlocks given package.
	 * 
	 * Any unlock exceptions will be logged but not propagated.
	 * 
	 * @param workPackage
	 * @return true if unlocked
	 */
	boolean unlockNoFail(I_C_Queue_WorkPackage workPackage);

	/**
	 * Retrieve the global queue size (i.e. number of unprocessed workpackages). This includes a DB query.
	 * 
	 * @return
	 */
	int size();

	/**
	 * 
	 * @return the number of packages enqueued to this instance
	 * @task http://dewiki908/mediawiki/index.php/09049_Priorit%C3%A4ten_Strategie_asynch_%28105016248827%29
	 */
	int getLocalPackageCount();

	/**
	 * The context this instance is currently operating with. Changes made to the returned value shall not reflect in this queue.<br>
	 * However, changes make in the queue shall reflect in the returned instance.
	 * 
	 * @return
	 */
	Properties getCtx();

	String getPriorityFrom();

	int getSkipRetryTimeoutMillis();

	/**
	 * Start creating a new block to be enqueued
	 */
	IWorkPackageBlockBuilder newBlock();

	/**
	 * Convenient method to quickly create and enqueue a new block to the queue.
	 * 
	 * @param ctx
	 * @return I_C_Queue_Block (created block)
	 * @see #newBlock()
	 */
	I_C_Queue_Block enqueueBlock(Properties ctx);

	/**
	 * Adds a work package to to the given block.
	 * 
	 * NOTE: the workpackage WILL NOT be marked as ready for processing.
	 * 
	 * @param block
	 * @param priority priority strategy to be used. <b>But</b> note that if the queue will also invoke {@link IWorkpackageProcessorContextFactory#getThreadInheritedPriority()} and will prefer that
	 *            priority (if any!) over this parameter.
	 * @return I_C_Queue_WorkPackage (created workPackage)
	 * @deprecated Please consider using {@link #newBlock()}
	 */
	@Deprecated
	I_C_Queue_WorkPackage enqueueWorkPackage(I_C_Queue_Block block, IWorkpackagePrioStrategy priority);

	/**
	 * Adds an element to the given <code>workPackage</code>.
	 * 
	 * @param workPackage
	 * @param adTableId
	 * @param recordId
	 * @return I_C_Queue_Element (created element)
	 */
	I_C_Queue_Element enqueueElement(I_C_Queue_WorkPackage workPackage, int adTableId, int recordId);

	/**
	 * Adds elements to the given <code>workPackage</code>.
	 * 
	 * @param workPackage
	 * @param adTableId
	 * @param recordIds
	 */
	void enqueueElements(I_C_Queue_WorkPackage workPackage, int adTableId, List<Integer> recordIds);

	I_C_Queue_Element enqueueElement(I_C_Queue_WorkPackage workPackage, Object model);

	void enqueueElements(I_C_Queue_WorkPackage workPackage, Iterable<?> models);

	/**
	 * Convenient method for quickly enqueuing an element. This method automatically creates a new {@link I_C_Queue_Block} and {@link I_C_Queue_WorkPackage}. After creating the
	 * {@link I_C_Queue_Element}, the work package is marked as ready for processing.
	 * 
	 * @param model
	 * @return
	 */
	I_C_Queue_Element enqueueElement(Object model);

	/**
	 * Convenient method for quickly enqueuing an element
	 * 
	 * @param ctx
	 * @param adTableId
	 * @param recordId
	 * @return element
	 * @see #enqueueElement(Object)
	 */
	I_C_Queue_Element enqueueElement(Properties ctx, int adTableId, int recordId);

	/**
	 * Indicates that all queue elements have been added to the given <code>workPackge</code>.
	 * 
	 * @param workPackge
	 */
	void markReadyForProcessing(I_C_Queue_WorkPackage workPackage);

	/**
	 * Indicates that all queue elements have been added to the given <code>workPackge</code>.
	 * 
	 * @param workPackge
	 * @param callback callback to be called after workpackage gets processed (sucessful or not).
	 */
	void markReadyForProcessing(I_C_Queue_WorkPackage workPackage, IQueueProcessorListener callback);

	/**
	 * Indicates that all queue elements have been added to the given <code>workpackge</code> and it's ready for processing.
	 * 
	 * @param workPackge
	 * @return future {@link IWorkpackageProcessorExecutionResult}
	 */
	Future<IWorkpackageProcessorExecutionResult> markReadyForProcessingAndReturn(I_C_Queue_WorkPackage workPackage);

	/**
	 * Same as {@link #markReadyForProcessing(I_C_Queue_WorkPackage)} but it will do this after given transaction was committed.
	 * 
	 * If transaction is {@link Trx#TRXNAME_None} the marking will be performed immediately.
	 * 
	 * @param workPackage
	 * @param trxName
	 * @return future {@link IWorkpackageProcessorExecutionResult}
	 */
	Future<IWorkpackageProcessorExecutionResult> markReadyForProcessingAfterTrxCommit(I_C_Queue_WorkPackage workPackage, String trxName);
	
		/**
	 * Set the async batch every new workpackage will be associated with
	 * 
	 * @param asyncBatch
	 * @return this
	 */
	IWorkPackageQueue setAsyncBatchForNewWorkpackages(I_C_Async_Batch asyncBatch);
	/**
	 * For queues that were created with {@link IWorkPackageQueueFactory#getQueueForEnqueuing(Properties, Class)}, this method returns the <code>InternalName</code> value of the queue's
	 * {@link I_C_Queue_PackageProcessor}. If the queue doesn't have an internal name, it returns the <code>ClassName</code> value.
	 * 
	 * @return
	 * @throws {@link UnsupportedOperationException} if this queue was not created with {@link IWorkPackageQueueFactory#getQueueForEnqueuing(Properties, Class)}.
	 */
	String getEnquingPackageProcessorInternalName();
}
