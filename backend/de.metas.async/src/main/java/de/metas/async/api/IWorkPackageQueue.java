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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.async.AsyncBatchId;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessorListener;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.IWorkpackageProcessorExecutionResult;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.QueueProcessorId;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.async.spi.impl.SizeBasedWorkpackagePrio;
import de.metas.lock.exceptions.UnlockFailedException;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * Use {@link IWorkPackageQueueFactory} to get an instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IWorkPackageQueue
{
	/**
	 * task http://dewiki908/mediawiki/index.php/09049_Priorit%C3%A4ten_Strategie_asynch_%28105016248827%29
	 */
	IWorkpackagePrioStrategy PRIORITY_AUTO = SizeBasedWorkpackagePrio.INSTANCE;

	/**
	 * Unlocks given package
	 *
	 * @throws UnlockFailedException if unlocking fails
	 */
	void unlock(I_C_Queue_WorkPackage workPackage) throws UnlockFailedException;

	/**
	 * Unlocks given package.
	 *
	 * Any unlock exceptions will be logged but not propagated.
	 *
	 * @return true if unlocked
	 */
	boolean unlockNoFail(I_C_Queue_WorkPackage workPackage);

	/**
	 * Retrieve the global queue size (i.e. number of unprocessed workpackages). This includes a DB query.
	 */
	int size();

	/**
	 *
	 * @return the number of packages enqueued to this instance
	 * task http://dewiki908/mediawiki/index.php/09049_Priorit%C3%A4ten_Strategie_asynch_%28105016248827%29
	 */
	int getLocalPackageCount();

	/**
	 * The context this instance is currently operating with. Changes made to the returned value shall not reflect in this queue.<br>
	 * However, changes make in the queue shall reflect in the returned instance.
	 */
	Properties getCtx();

	/**
	 * Adds a work package to the respective queue.
	 *
	 * NOTE: the workpackage WILL NOT be marked as ready for processing.
	 *
	 * @param priority priority strategy to be used. <b>But</b> note that if the queue will also invoke {@link IWorkpackageProcessorContextFactory#getThreadInheritedPriority()} and will prefer that
	 *            priority (if any!) over this parameter.
	 * @return I_C_Queue_WorkPackage (created workPackage)
	 * @deprecated Please consider using {@link #newWorkPackage()}
	 */
	@Deprecated
	I_C_Queue_WorkPackage enqueueWorkPackage(I_C_Queue_WorkPackage workPackage, IWorkpackagePrioStrategy priority);

	/**
	 * Adds an element to the given <code>workPackage</code>.
	 *
	 * @return I_C_Queue_Element (created element)
	 */
	I_C_Queue_Element enqueueElement(I_C_Queue_WorkPackage workPackage, int adTableId, int recordId);

	/**
	 * Adds elements to the given <code>workPackage</code>.
	 */
	void enqueueElements(I_C_Queue_WorkPackage workPackage, int adTableId, List<Integer> recordIds);

	I_C_Queue_Element enqueueElement(I_C_Queue_WorkPackage workPackage, TableRecordReference model);

	void enqueueElements(I_C_Queue_WorkPackage workPackage, Iterable<TableRecordReference> models);

	/**
	 * Convenient method for quickly enqueuing an element. This method automatically creates a new {@link I_C_Queue_WorkPackage}. After creating the
	 * {@link I_C_Queue_Element}, the work package is marked as ready for processing.
	 */
	I_C_Queue_Element enqueueElement(Object modelReference);

	/**
	 * Convenient method for quickly enqueuing an element
	 *
	 * @see #enqueueElement(Object)
	 */
	I_C_Queue_Element enqueueElement(Properties ctx, int adTableId, int recordId);

	/**
	 * Indicates that all queue elements have been added to the given <code>workPackge</code>.
	 */
	void markReadyForProcessing(I_C_Queue_WorkPackage workPackage);

	/**
	 * Indicates that all queue elements have been added to the given <code>workPackge</code>.
	 *
	 * @param callback callback to be called after workpackage gets processed (sucessful or not).
	 */
	void markReadyForProcessing(I_C_Queue_WorkPackage workPackage, IQueueProcessorListener callback);

	/**
	 * Indicates that all queue elements have been added to the given <code>workpackge</code> and it's ready for processing.
	 *
	 * @return future {@link IWorkpackageProcessorExecutionResult}
	 */
	Future<IWorkpackageProcessorExecutionResult> markReadyForProcessingAndReturn(I_C_Queue_WorkPackage workPackage);

	/**
	 * Same as {@link #markReadyForProcessing(I_C_Queue_WorkPackage)} but it will do this after given transaction was committed.
	 *
	 * If transaction is {@link ITrx#TRXNAME_None} the marking will be performed immediately.
	 *
	 * @return future {@link IWorkpackageProcessorExecutionResult}
	 */
	Future<IWorkpackageProcessorExecutionResult> markReadyForProcessingAfterTrxCommit(I_C_Queue_WorkPackage workPackage, String trxName);

	/**
	 * Set the async batch Id every new workpackage will be associated with.
	 */
	IWorkPackageQueue setAsyncBatchIdForNewWorkpackages(AsyncBatchId asyncBatchId);

	/**
	 * For queues that were created with {@link IWorkPackageQueueFactory#getQueueForEnqueuing(Properties, Class)}, this method returns the <code>InternalName</code> value of the queue's
	 * {@link I_C_Queue_PackageProcessor}. If the queue doesn't have an internal name, it returns the <code>ClassName</code> value.
	 *
	 * @throws UnsupportedOperationException if this queue was not created with {@link IWorkPackageQueueFactory#getQueueForEnqueuing(Properties, Class)}.
	 */
	String getEnquingPackageProcessorInternalName();

	IWorkPackageBuilder newWorkPackage();

	IWorkPackageBuilder newWorkPackage(Properties ctx);

	Optional<IQuery<I_C_Queue_WorkPackage>> createQuery(Properties workPackageCtx, QueryLimit limit);

	Set<QueuePackageProcessorId> getQueuePackageProcessorIds();

	QueueProcessorId getQueueProcessorId();
}
