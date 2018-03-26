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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.async.api.IQueueDAO;
import de.metas.async.exceptions.PackageItemNotAvailableException;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Notified;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.logging.LogManager;

public abstract class AbstractQueueDAO implements IQueueDAO
{
	protected final Logger logger = LogManager.getLogger(getClass());

	/**
	 * Filter used to skip all elements which are pointing to records (AD_Table_ID/Record_ID) which were already enqueued, even if in another working package
	 *
	 * NOTE: to be set by extending classes
	 */
	protected IQueryFilter<I_C_Queue_Element> filter_C_Queue_Element_SkipAlreadyScheduledItems;
	private final boolean DEFAULT_skipAlreadyScheduledItems = true;

	// Order by (very important): Priority (0 to 9), and ID
	protected final IQueryOrderBy queueOrderByComparator = Services.get(IQueryBL.class)
			.createQueryOrderByBuilder(I_C_Queue_WorkPackage.class)
			.addColumn(I_C_Queue_WorkPackage.COLUMNNAME_Priority)
			.addColumn(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID)
			.createQueryOrderBy();

	protected abstract Map<Integer, I_C_Queue_PackageProcessor> retrieveWorkpackageProcessorsMap(Properties ctx);

	protected abstract List<I_C_Queue_Processor_Assign> retrieveQueueProcessorAssignments(I_C_Queue_Processor processor);

	/**
	 * Retrieve object from given <code>element</code>
	 *
	 * @param element
	 * @param clazz
	 * @param trxName
	 * @return object
	 * @throws PackageItemNotAvailableException if underlying object was not found at this moment. Never return <code>null</code>.
	 */
	protected abstract <T> T retrieveItem(I_C_Queue_Element element, Class<T> clazz, String trxName);

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
	public I_C_Queue_PackageProcessor retrievePackageProcessorDefByClass(final Properties ctx, final Class<? extends IWorkpackageProcessor> packageProcessorClass)
	{
		Check.assumeNotNull(packageProcessorClass, "packageProcessorClass not null");

		final String packageProcessorClassname = packageProcessorClass.getCanonicalName();
		return retrievePackageProcessorDefByClassname(ctx, packageProcessorClassname);
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
			final IContextAware context = new PlainContextAware(ctx);
			final I_C_Queue_PackageProcessor packageProcessorNew = InterfaceWrapperHelper.newInstance(I_C_Queue_PackageProcessor.class, context);
			packageProcessorNew.setClassname(packageProcessorClassname);
			packageProcessorNew.setEntityType("U");
			packageProcessorNew.setDescription("Generated by " + getClass());
			packageProcessorNew.setIsActive(true);
			InterfaceWrapperHelper.save(packageProcessorNew);
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
	public void save(final I_C_Queue_Block block)
	{
		InterfaceWrapperHelper.save(block);
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
	public void save(I_C_Queue_Element element)
	{
		InterfaceWrapperHelper.save(element);
	}

	/**
	 * Creates a {@link IQueryBuilder} which selects all {@link I_C_Queue_Element}s for given work package.
	 *
	 * @param workPackage
	 * @param skipAlreadyScheduledItems true if we shall skip all elements which are pointing to records (AD_Table_ID/Record_ID) which were already enqueued earlier and not yet processed.
	 * @param trxName transaction name
	 * @return query builder already configured
	 */
	protected final IQueryBuilder<I_C_Queue_Element> createQueueElementsQueryBuilder(final I_C_Queue_WorkPackage workPackage, final boolean skipAlreadyScheduledItems, final String trxName)
	{
		Check.assume(workPackage != null, "workPackage not null");

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
	public final <T> List<T> retrieveItems(final I_C_Queue_WorkPackage workPackage, final Class<T> clazz, final String trxName)
	{
		return retrieveItems(workPackage, clazz, DEFAULT_skipAlreadyScheduledItems, false, trxName);
	}

	@Override
	public final <T> List<T> retrieveItemsSkipMissing(final I_C_Queue_WorkPackage workPackage, final Class<T> clazz, final String trxName)
	{
		final boolean skipMissingItems = true;
		return retrieveItems(workPackage, clazz, DEFAULT_skipAlreadyScheduledItems, skipMissingItems, trxName);
	}

	@Override
	public final <T> List<T> retrieveAllItems(final I_C_Queue_WorkPackage workPackage, final Class<T> clazz)
	{
		return retrieveItems(workPackage, clazz, false, false, ITrx.TRXNAME_ThreadInherited);
	}

	private final <T> List<T> retrieveItems(final I_C_Queue_WorkPackage workPackage, final Class<T> clazz, final boolean skipAlreadyScheduledItems, final boolean skipMissingItems, final String trxName)
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
					continue; // just skip, no stress
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
}
