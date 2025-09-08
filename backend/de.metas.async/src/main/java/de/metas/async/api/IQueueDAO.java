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
import de.metas.async.model.*;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Async Queue related DAO
 *
 * @author tsa
 */
public interface IQueueDAO extends ISingletonService
{
	void save(I_C_Async_Batch asyncBatch);

	void save(I_C_Queue_WorkPackage workpackage);

	void save(I_C_Queue_WorkPackage_Notified wpNotified);

	void save(I_C_Queue_Element element);

	List<I_C_Queue_Processor> retrieveAllProcessors();

	List<I_C_Queue_PackageProcessor> retrieveWorkpackageProcessors(I_C_Queue_Processor processor);

	int retrievePackageProcessorIdByClass(Class<? extends IWorkpackageProcessor> packageProcessorClass);

	I_C_Queue_PackageProcessor retrievePackageProcessorDefByClass(Properties ctx, Class<? extends IWorkpackageProcessor> packageProcessorClass);

	I_C_Queue_PackageProcessor retrievePackageProcessorDefByClassname(Properties ctx, String packageProcessorClassname);

	I_C_Queue_PackageProcessor retrievePackageProcessorDefById(Properties ctx, int packageProcessorId);

	/**
	 * Retrieves all {@link I_C_Queue_Element}s for given workPackage
	 *
	 * @param skipAlreadyScheduledItems if true, those items that were already enqueued in the past, in another workpackage which hasn't yet been proceed, are skipped
	 * @return list of queue elements
	 */
	List<I_C_Queue_Element> retrieveQueueElements(I_C_Queue_WorkPackage workPackage, boolean skipAlreadyScheduledItems);

	/**
	 * Retrieves the POs that are referenced by the given workPackage's {@link I_C_Queue_Element}s.
	 * <p>
	 * NOTE: this method is returning all those items which <b>were not already scheduled in a previous not-yet-processed work-package.</b>
	 *
	 * @param clazz note that {@link TableRecordReference} is supported as well
	 * @throws de.metas.async.exceptions.PackageItemNotAvailableException if one of the work package's elements references a record which no longer exists.
	 * @deprecated Please Consider using {@link #retrieveAllItems(I_C_Queue_WorkPackage, Class)} instead.
	 * Filtering out items which were already enqueued in an older, not-yet-processed workpackage costs a lot of performance.
	 * Since today we have just one thread per processor, this can't happen. Therefore AFAIS we don't need this anymore.
	 */
	@Deprecated
	<T> List<T> retrieveItems(I_C_Queue_WorkPackage workPackage, Class<T> clazz, String trxName);

	/**
	 * Similar to {@link #retrieveAllItems(I_C_Queue_WorkPackage, Class)}, but
	 * <li>does not make a fuzz about elements whose referenced records do no longer exist.</li>
	 *
	 * @param clazz note that {@link TableRecordReference} is supported as well
	 */
	<T> List<T> retrieveAllItemsSkipMissing(I_C_Queue_WorkPackage workPackage, Class<T> clazz);

	/**
	 * Return all active POs, even the ones that are caught in other packages
	 *
	 * @param clazz note that {@link TableRecordReference} is supported as well
	 */
	<T> List<T> retrieveAllItems(I_C_Queue_WorkPackage workPackage, Class<T> clazz);

	/**
	 * Creates a query builder which is used to retrieve all records of given <code>clazz</code>.
	 *
	 * @param clazz                     model class
	 * @param skipAlreadyScheduledItems true if we shall skip all elements which are pointing to records (AD_Table_ID/Record_ID) which were already enqueued
	 * @return query builder
	 */
	<T> IQueryBuilder<T> createElementsQueryBuilder(I_C_Queue_WorkPackage workPackage, Class<T> clazz, boolean skipAlreadyScheduledItems, String trxName);

	/**
	 * Same as {@link #createElementsQueryBuilder(I_C_Queue_WorkPackage, Class, boolean, String)} called with skipAlreadyScheduledItems=true.
	 */
	<T> IQueryBuilder<T> createElementsQueryBuilder(I_C_Queue_WorkPackage workPackage, Class<T> clazz, String trxName);

	IQuery<I_C_Queue_WorkPackage> createQuery(Properties ctx, IWorkPackageQuery packageQuery);

	/**
	 * Return the ordering used when the next work package is retrieved from the queue. Can be used where it is required to have the same ordering as the queue.
	 */
	IQueryOrderBy getQueueOrderBy();

	/**
	 * Checks if the workpackage processor is enabled
	 */
	boolean isWorkpackageProcessorEnabled(Class<? extends IWorkpackageProcessor> packageProcessorClass);

	Set<Integer> retrieveAllItemIds(I_C_Queue_WorkPackage workPackage);

	/**
	 * @return queue-workpackages that have a {@code C_Queue_Element} referencing the given {@code recordRef}.
	 */
	List<I_C_Queue_WorkPackage> retrieveUnprocessedWorkPackagesByEnqueuedRecord(@NonNull Class<? extends IWorkpackageProcessor> packageProcessorClass, @NonNull TableRecordReference recordRef);

	int assignAsyncBatchForProcessing(Set<QueuePackageProcessorId> queuePackageProcessorId, AsyncBatchId asyncBatchId);

	/**
	 * @param classname {@link I_C_Queue_PackageProcessor#COLUMNNAME_Classname}.
	 */
	@Nullable QueuePackageProcessorId retrieveQueuePackageProcessorIdFor(@NonNull String classname);
}
