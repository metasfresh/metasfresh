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

import de.metas.async.api.IWorkPackageQuery;
import de.metas.async.exceptions.PackageItemNotAvailableException;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.dao.impl.POJOQuery;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.compiere.model.IQuery;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PlainQueueDAO extends AbstractQueueDAO
{
	private static final transient Logger slogger = LogManager.getLogger(PlainQueueDAO.class);

	private final POJOLookupMap db = POJOLookupMap.get();

	public PlainQueueDAO()
	{
		this.filter_C_Queue_Element_SkipAlreadyScheduledItems = model -> {
			final List<I_C_Queue_Element> previousElements = retrievePreviousScheduledElements(model);
			return previousElements.isEmpty();
		};
	}

	@Override
	public List<I_C_Queue_Processor> retrieveAllProcessors()
	{
		return db.getRecords(I_C_Queue_Processor.class, I_C_Queue_Processor::isActive);
	}

	@Override
	protected Map<Integer, I_C_Queue_PackageProcessor> retrieveWorkpackageProcessorsMap(final Properties ctx)
	{
		final List<I_C_Queue_PackageProcessor> processors = db.getRecords(I_C_Queue_PackageProcessor.class);
		final Map<Integer, I_C_Queue_PackageProcessor> map = new HashMap<>(processors.size());
		for (final I_C_Queue_PackageProcessor p : processors)
		{
			map.put(p.getC_Queue_PackageProcessor_ID(), p);
		}
		return map;
	}

	@Override
	protected List<I_C_Queue_Processor_Assign> retrieveQueueProcessorAssignments(final I_C_Queue_Processor processor)
	{
		return db.getRecords(I_C_Queue_Processor_Assign.class, pojo -> {
			if (pojo.getC_Queue_Processor_ID() != processor.getC_Queue_Processor_ID())
			{
				return false;
			}
			if (!pojo.isActive())
			{
				return false;
			}
			return true;
		});
	}

	private List<I_C_Queue_Element> retrievePreviousScheduledElements(final I_C_Queue_Element element)
	{
		return db.getRecords(I_C_Queue_Element.class, pojo -> {
			// Same record
			if (pojo.getAD_Table_ID() != element.getAD_Table_ID())
			{
				return false;
			}
			if (pojo.getRecord_ID() != element.getRecord_ID())
			{
				return false;
			}
			// But on different queue element
			if (pojo.getC_Queue_Element_ID() == element.getC_Queue_Element_ID())
			{
				return false;
			}
			// Submitted in the past
			if (pojo.getC_Queue_WorkPackage_ID() > element.getC_Queue_WorkPackage_ID())
			{
				return false;
			}
			// and those workpackages were not processed yet
			if (pojo.getC_Queue_WorkPackage().isProcessed())
			{
				return false;
			}

			return true;
		});
	}

	@Override
	@NonNull
	protected <T> T retrieveItem(final I_C_Queue_Element element, final Class<T> clazz, final String trxName)
	{
		final int tableId = element.getAD_Table_ID();
		final int recordId = element.getRecord_ID();

		T item;
		try
		{
			item = db.lookup(tableId, recordId, clazz);
		}
		catch (final Exception e)
		{
			final String tableName = Services.get(IADTableDAO.class).retrieveTableName(tableId);
			throw new PackageItemNotAvailableException(tableName, recordId);
		}
		if (item == null)
		{
			final String tableName = Services.get(IADTableDAO.class).retrieveTableName(tableId);
			throw new PackageItemNotAvailableException(tableName, recordId);
		}

		return item;
	}

	@Override
	public IQuery<I_C_Queue_WorkPackage> createQuery(final Properties ctx, final IWorkPackageQuery packageQuery)
	{
		return new POJOQuery<>(ctx,
				I_C_Queue_WorkPackage.class,
				null,  // tableName=null => get it from the given model class
				ITrx.TRXNAME_None)
				.addFilter(new QueueFilter(packageQuery))
				.setLimit(QueryLimit.getQueryLimitOrNoLimit(packageQuery.getLimit()))
				.setOrderBy(queueOrderByComparator);
	}

	private static class QueueFilter implements IQueryFilter<I_C_Queue_WorkPackage>
	{
		private final IWorkPackageQuery packageQuery;

		public QueueFilter(final IWorkPackageQuery packageQuery)
		{
			this.packageQuery = packageQuery;
		}

		@Override
		public boolean accept(final I_C_Queue_WorkPackage workpackage)
		{
			if (packageQuery.getProcessed() != null && packageQuery.getProcessed() != workpackage.isProcessed())
			{
				return false;
			}
			if (packageQuery.getReadyForProcessing() != null && packageQuery.getReadyForProcessing() != workpackage.isReadyForProcessing())
			{
				return false;
			}
			if (packageQuery.getError() != null && packageQuery.getError() != workpackage.isError())
			{
				return false;
			}
			if (!workpackage.isActive())
			{
				return false;
			}

			// Only packages that have not been skipped,
			// or where 'retryTimeoutMillis' has already passed since they were skipped.
			final Timestamp nowMinusTimeOut = new Timestamp(SystemTime.millis() - packageQuery.getSkippedTimeoutMillis());
			final Timestamp skippedAt = workpackage.getSkippedAt();
			if (skippedAt != null && skippedAt.compareTo(nowMinusTimeOut) > 0)
			{
				return false;
			}

			// Only work packages for given process
			final Set<QueuePackageProcessorId> packageProcessorIds = packageQuery.getPackageProcessorIds();
			if (packageProcessorIds != null)
			{
				if (packageProcessorIds.isEmpty())
				{
					slogger.warn("There were no package processor Ids set in the package query. This could be a posible development error"
							+"\n Package query: "+packageQuery);
				}
				final QueuePackageProcessorId packageProcessorId = QueuePackageProcessorId.ofRepoId(workpackage.getC_Queue_PackageProcessor_ID());
				if (!packageProcessorIds.contains(packageProcessorId))
				{
					return false;
				}
			}

			final String priorityFrom = packageQuery.getPriorityFrom();
			if (priorityFrom != null && priorityFrom.compareTo(workpackage.getPriority()) > 0)
			{
				return false;
			}

			return true;
		}

	}

}
