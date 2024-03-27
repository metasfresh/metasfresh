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

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IQueueDAO;
import de.metas.async.exceptions.PackageItemNotAvailableException;
import de.metas.async.model.*;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.cache.CCache;
import de.metas.lock.api.ILockManager;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.*;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractQueueDAO implements IQueueDAO
{
	protected final Logger logger = LogManager.getLogger(getClass());

	/**
	 * Filter used to skip all elements which are pointing to records (AD_Table_ID/Record_ID) which were already enqueued, even if in another working package
	 * <p>
	 * NOTE: to be set by extending classes
	 */
	protected IQueryFilter<I_C_Queue_Element> filter_C_Queue_Element_SkipAlreadyScheduledItems;
	private final boolean DEFAULT_skipAlreadyScheduledItems = true;

	// Order by (very important): Priority (0 to 9), and ID
	protected final IQueryOrderBy queueOrderByComparator = Services.get(IQueryBL.class)
			.createQueryOrderByBuilder(I_C_Queue_WorkPackage.class)
			.addColumn(I_C_Queue_WorkPackage.COLUMNNAME_Priority) /* 1 is the most urgent */
			.addColumn(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID)
			.createQueryOrderBy();

	protected abstract Map<Integer, I_C_Queue_PackageProcessor> retrieveWorkpackageProcessorsMap(Properties ctx);

	protected abstract List<I_C_Queue_Processor_Assign> retrieveQueueProcessorAssignments(I_C_Queue_Processor processor);

	/**
	 * Retrieve object from given <code>element</code>
	 *
	 * @param clazz note that {@link TableRecordReference} is supported as well
	 * @throws PackageItemNotAvailableException if underlying object was not found at this moment. Never return <code>null</code>.
	 */
	@NonNull
	protected abstract <T> T retrieveItem(@NonNull I_C_Queue_Element element, @NonNull Class<T> clazz, @Nullable String trxName);

	@Override
	public List<I_C_Queue_PackageProcessor> retrieveWorkpackageProcessors(final I_C_Queue_Processor processor)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(processor);

		final Map<Integer, I_C_Queue_PackageProcessor> packageProcessors = retrieveWorkpackageProcessorsMap(ctx);
		final List<I_C_Queue_Processor_Assign> assignments = retrieveQueueProcessorAssignments(processor);
		final List<I_C_Queue_PackageProcessor> result = new ArrayList<>();
		for (final I_C_Queue_Processor_Assign assignment : assignments)
		{
			if (!assignment.isActive())
			{
				continue;
			}

			final int packageProcessorId = assignment.getC_Queue_PackageProcessor_ID();
			final I_C_Queue_PackageProcessor packageProcessor = packageProcessors.get(packageProcessorId);
			if (packageProcessor == null)
			{
				final AdempiereException ex = new AdempiereException("No package processor found for C_Queue_PackageProcessor_ID=" + packageProcessorId);
				logger.warn(ex.getLocalizedMessage(), ex);
				continue;
			}

			if (!packageProcessor.isActive())
			{
				continue;
			}

			result.add(packageProcessor);
		}

		return result;
	}

	@Override
	public I_C_Queue_PackageProcessor retrievePackageProcessorDefByClass(@NonNull final Properties ctx, @NonNull final Class<? extends IWorkpackageProcessor> packageProcessorClass)
	{
		Check.assumeNotNull(packageProcessorClass, "packageProcessorClass not null");

		final String packageProcessorClassname = packageProcessorClass.getCanonicalName();
		return retrievePackageProcessorDefByClassname(ctx, packageProcessorClassname);
	}

	@Override
	public int retrievePackageProcessorIdByClass(@NonNull final Class<? extends IWorkpackageProcessor> packageProcessorClass)
	{
		return retrievePackageProcessorDefByClass(Env.getCtx(), packageProcessorClass).getC_Queue_PackageProcessor_ID();
	}

	@Override
	public I_C_Queue_PackageProcessor retrievePackageProcessorDefByClassname(final Properties ctx, final String packageProcessorClassname)
	{
		Check.assumeNotEmpty(packageProcessorClassname, "packageProcessorClassname is not empty");

		final Collection<I_C_Queue_PackageProcessor> packageProcessors = retrieveWorkpackageProcessorsMap(ctx).values();
		for (final I_C_Queue_PackageProcessor packageProcessor : packageProcessors)
		{
			if (packageProcessorClassname.equals(packageProcessor.getClassname()))
			{
				return packageProcessor;
			}
		}

		//
		// If we can auto-create this workpackage processor record then do it
		// Note: check the javadoc of isAutocreateWorkpackageProcessorRecordForClassname()
		if (isAutocreateWorkpackageProcessorRecordForClassname())
		{
			Adempiere.assertUnitTestMode();

			final IContextAware context = new PlainContextAware(ctx);
			final I_C_Queue_PackageProcessor packageProcessorNew = InterfaceWrapperHelper.newInstance(I_C_Queue_PackageProcessor.class, context);
			packageProcessorNew.setClassname(packageProcessorClassname);
			packageProcessorNew.setEntityType("U");
			packageProcessorNew.setDescription("Generated by " + getClass());
			packageProcessorNew.setIsActive(true);
			InterfaceWrapperHelper.save(packageProcessorNew);

			final I_C_Queue_Processor queueProcessor = InterfaceWrapperHelper.newInstance(I_C_Queue_Processor.class);
			queueProcessor.setName("Auto-generated for " + AbstractQueueDAO.class.getCanonicalName());
			queueProcessor.setKeepAliveTimeMillis(1000);
			queueProcessor.setIsActive(true);
			queueProcessor.setPoolSize(1);

			InterfaceWrapperHelper.saveRecord(queueProcessor);

			final I_C_Queue_Processor_Assign assignPackageProcessor = InterfaceWrapperHelper.newInstance(I_C_Queue_Processor_Assign.class);
			assignPackageProcessor.setC_Queue_PackageProcessor_ID(packageProcessorNew.getC_Queue_PackageProcessor_ID());
			assignPackageProcessor.setC_Queue_Processor_ID(queueProcessor.getC_Queue_Processor_ID());
			assignPackageProcessor.setIsActive(true);

			InterfaceWrapperHelper.saveRecord(assignPackageProcessor);
			return packageProcessorNew;
		}

		Check.errorIf(true, "Missing C_Queue_PackageProcessor for classname {}", packageProcessorClassname);
		return null; // won't be reached
	}

	/**
	 * For easy testing, we are auto-creating the workpackage processor record. Will return <code>true</code> if we are in unit-test mode
	 * <p>
	 * <b>IMPOTRANT:</b> Be careful returning true outside of the unit test mode! The package might be created with a wrong AD_Client_ID and AD_Org_ID, might not be found later-on, then the system
	 * might attempt to create it again and fail because of UCs.
	 *
	 * @return true if we shall create {@link I_C_Queue_PackageProcessor} record for a given classname.
	 */
	private boolean isAutocreateWorkpackageProcessorRecordForClassname()
	{
		if (Adempiere.isUnitTestMode())
		{
			return true;
		}

		return false;
	}

	@Override
	public I_C_Queue_PackageProcessor retrievePackageProcessorDefById(final Properties ctx, final int packageProcessorId)
	{
		final Map<Integer, I_C_Queue_PackageProcessor> packageProcessors = retrieveWorkpackageProcessorsMap(ctx);
		final I_C_Queue_PackageProcessor packageProcessorDef = packageProcessors.get(packageProcessorId);
		if (packageProcessorDef == null)
		{
			throw new AdempiereException("@NotFound@ @C_Queue_PackageProcessor_ID@ (ID=" + packageProcessorId + ")");
		}

		return packageProcessorDef;
	}

	@Override
	public void save(final I_C_Async_Batch asyncBatch)
	{
		InterfaceWrapperHelper.save(asyncBatch);
	}

	@Override
	public void save(final I_C_Queue_WorkPackage workpackage)
	{
		InterfaceWrapperHelper.save(workpackage);
	}

	@Override
	public void save(final I_C_Queue_WorkPackage_Notified wpNotified)
	{
		InterfaceWrapperHelper.save(wpNotified);
	}

	@Override
	public void save(final I_C_Queue_Element element)
	{
		InterfaceWrapperHelper.save(element);
	}

	/**
	 * Creates a {@link IQueryBuilder} which selects all {@link I_C_Queue_Element}s for given work package.
	 *
	 * @param skipAlreadyScheduledItems true if we shall skip all elements which are pointing to records (AD_Table_ID/Record_ID) which were already enqueued earlier and not yet processed.
	 * @param trxName                   transaction name
	 * @return query builder already configured
	 */
	protected final IQueryBuilder<I_C_Queue_Element> createQueueElementsQueryBuilder(
			@NonNull final I_C_Queue_WorkPackage workPackage,
			final boolean skipAlreadyScheduledItems,
			@Nullable final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workPackage);
		final IQueryBuilder<I_C_Queue_Element> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Queue_Element.class, ctx, trxName);

		// All Queue elements for given work-package
		queryBuilder.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_C_Queue_WorkPackage_ID, workPackage.getC_Queue_WorkPackage_ID());

		// Skip already enqueued, but not processed items
		if (skipAlreadyScheduledItems)
		{
			Check.assumeNotNull(filter_C_Queue_Element_SkipAlreadyScheduledItems, "member filter_C_Queue_Element_SkipAlreadyScheduledItems was set in the constructor");
			queryBuilder.filter(filter_C_Queue_Element_SkipAlreadyScheduledItems);
		}

		queryBuilder.orderBy()
				.addColumn(I_C_Queue_Element.COLUMNNAME_C_Queue_Element_ID);

		return queryBuilder;
	}

	@Override
	public final List<I_C_Queue_Element> retrieveQueueElements(final I_C_Queue_WorkPackage workPackage, final boolean skipAlreadyScheduledItems)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(workPackage);
		final IQueryBuilder<I_C_Queue_Element> queryBuilder = createQueueElementsQueryBuilder(workPackage, skipAlreadyScheduledItems, trxName);
		return queryBuilder.create().list();
	}

	@Override
	public final <T> IQueryBuilder<T> createElementsQueryBuilder(final I_C_Queue_WorkPackage workPackage, final Class<T> clazz, final boolean skipAlreadyScheduledItems, final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workPackage);
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(clazz);
		final int adTableId = InterfaceWrapperHelper.getTableId(clazz);

		final IQueryBuilder<I_C_Queue_Element> elementsQueryBuilder = createQueueElementsQueryBuilder(workPackage, skipAlreadyScheduledItems, trxName);
		elementsQueryBuilder.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_AD_Table_ID, adTableId);
		final IQuery<I_C_Queue_Element> elementsQuery = elementsQueryBuilder.create();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(clazz, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(keyColumnName, I_C_Queue_Element.COLUMNNAME_Record_ID, elementsQuery);
	}

	@Override
	public final <T> IQueryBuilder<T> createElementsQueryBuilder(final I_C_Queue_WorkPackage workPackage, final Class<T> clazz, final String trxName)
	{
		return createElementsQueryBuilder(workPackage, clazz, DEFAULT_skipAlreadyScheduledItems, trxName);
	}

	@Override
	@Deprecated
	public final <T> List<T> retrieveItems(final I_C_Queue_WorkPackage workPackage, final Class<T> clazz, final String trxName)
	{
		return retrieveItems(workPackage, clazz, DEFAULT_skipAlreadyScheduledItems, false, trxName);
	}

	@NonNull
	@Override
	public final <T> List<T> retrieveAllItemsSkipMissing(@NonNull final I_C_Queue_WorkPackage workPackage, @NonNull final Class<T> clazz)
	{
		final boolean skipAlreadyScheduledItems = false;
		final boolean skipMissingItems = true;
		return retrieveItems(workPackage, clazz, skipAlreadyScheduledItems, skipMissingItems, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	public final <T> List<T> retrieveAllItems(final I_C_Queue_WorkPackage workPackage, final Class<T> clazz)
	{
		return retrieveItems(workPackage, clazz, false, false, ITrx.TRXNAME_ThreadInherited);
	}

	private <T> List<T> retrieveItems(
			@NonNull final I_C_Queue_WorkPackage workPackage,
			@NonNull final Class<T> clazz,
			final boolean skipAlreadyScheduledItems,
			final boolean skipMissingItems,
			@Nullable final String trxName)
	{
		final List<I_C_Queue_Element> queueElements = retrieveQueueElements(workPackage, skipAlreadyScheduledItems);

		final List<T> result = new ArrayList<>(queueElements.size());
		for (final I_C_Queue_Element e : queueElements)
		{
			try
			{
				final T item = retrieveItem(e, clazz, trxName); // if the item can't be retrieved, a PackageItemNotAvailableException is thrown
				Check.assumeNotNull(item, "Item for {} is not null", e);
				result.add(item);
			}
			catch (final PackageItemNotAvailableException pie)
			{
				if (skipMissingItems)
				{
					Loggables.withLogger(logger, Level.DEBUG)
							.addLog("Skip C_Queue_Element_ID={} (C_Queue_WorkPackage_ID={}) because referenced AD_Table_ID={}/Record_ID={} does not exist and skipMissingItems=true",
									e.getC_Queue_Element_ID(), e.getC_Queue_WorkPackage_ID(), e.getAD_Table_ID(), e.getRecord_ID());
				}
				else
				{
					throw pie; // caller expected the item to be there, so throw the exception
				}
			}
		}

		return result;
	}

	@Override
	public final Set<Integer> retrieveAllItemIds(final I_C_Queue_WorkPackage workPackage)
	{
		final List<I_C_Queue_Element> queueElements = retrieveQueueElements(workPackage, false/* skipAlreadyScheduledItems */);
		return queueElements.stream()
				.map(I_C_Queue_Element::getRecord_ID)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public IQueryOrderBy getQueueOrderBy()
	{
		return queueOrderByComparator;
	}

	@Override
	public boolean isWorkpackageProcessorEnabled(final Class<? extends IWorkpackageProcessor> packageProcessorClass)
	{
		final String packageProcessorClassname = packageProcessorClass.getCanonicalName();
		Check.assumeNotEmpty(packageProcessorClassname, "packageProcessorClassname is not empty");

		return retrieveWorkpackageProcessorsMap(Env.getCtx())
				.values()
				.stream()
				.anyMatch(packageProcessor -> packageProcessorClassname.equals(packageProcessor.getClassname()));
	}

	@Override
	public List<I_C_Queue_WorkPackage> retrieveUnprocessedWorkPackagesByEnqueuedRecord(
			@NonNull final Class<? extends IWorkpackageProcessor> packageProcessorClass,
			@NonNull final TableRecordReference recordRef)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final int workpackageProcessorId = retrievePackageProcessorIdByClass(packageProcessorClass);

		return queryBL.createQueryBuilder(I_C_Queue_Element.class)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.andCollect(I_C_Queue_Element.COLUMN_C_Queue_WorkPackage_ID)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_PackageProcessor_ID, workpackageProcessorId)
				.addNotEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_Processed, true)
				.create()
				.list();
	}

	@Override
	public int assignAsyncBatchForProcessing(
			@NonNull final Set<QueuePackageProcessorId> queuePackageProcessorIds,
			@NonNull final AsyncBatchId asyncBatchId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ILockManager lockManager = Services.get(ILockManager.class);

		final ICompositeQueryUpdater<I_C_Queue_WorkPackage> updater = queryBL.createCompositeQueryUpdater(I_C_Queue_WorkPackage.class)
				.addSetColumnValue(I_C_Queue_WorkPackage.COLUMNNAME_C_Async_Batch_ID, asyncBatchId.getRepoId());

		final IQuery<I_C_Queue_WorkPackage> updateQuery = queryBL.createQueryBuilder(I_C_Queue_WorkPackage.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_PackageProcessor_ID, queuePackageProcessorIds)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Async_Batch_ID, null)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_IsReadyForProcessing, true)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_IsError, false)
				.create();

		return lockManager.addNotLockedClause(updateQuery)
				.updateDirectly(updater);
	}

	private final CCache<String, QueuePackageProcessorId> classname2QueuePackageProcessorId = CCache
			.<String, QueuePackageProcessorId>builder()
			.tableName(I_C_Queue_PackageProcessor.Table_Name)
			.build(); // we will only ever have a handful of processors, so there is nothing much to worry about wrt cache-size

	@Nullable
	@Override
	public QueuePackageProcessorId retrieveQueuePackageProcessorIdFor(@NonNull final String classname)
	{
		return classname2QueuePackageProcessorId.getOrLoad(classname, AbstractQueueDAO::retrieveQueuePackageProcessorIdFor0);
	}

	@Nullable
	private static QueuePackageProcessorId retrieveQueuePackageProcessorIdFor0(@NonNull final String classname)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_C_Queue_PackageProcessor.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Queue_PackageProcessor.COLUMNNAME_Classname, classname)
				.create()
				.firstId(QueuePackageProcessorId::ofRepoIdOrNull);
	}
}
