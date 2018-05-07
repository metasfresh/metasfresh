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


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.async.api.IWorkPackageQuery;
import de.metas.async.exceptions.PackageItemNotAvailableException;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.async.model.I_C_Queue_WorkPackage;

public class QueueDAO extends AbstractQueueDAO
{
	public QueueDAO()
	{
		super();

		//
		// Create C_Queue_Element filter: Skip already enqueued, but not processed items
		{
			final String parentAlias = I_C_Queue_Element.Table_Name;
			final String wc = "NOT EXISTS (SELECT 1 "
					+ " FROM \"de.metas.async\".C_Queue_Element_Preceeding_Unprocessed_V v "
					+ " WHERE v.C_Queue_Element_ID=" + parentAlias + ".C_Queue_Element_ID" + ")";

			final List<Object> params = Collections.emptyList();
			this.filter_C_Queue_Element_SkipAlreadyScheduledItems = TypedSqlQueryFilter.of(wc, params);
		}

	}

	@Override
	// @Cached(cacheName = I_C_Queue_Processor.Table_Name + "#All") // no caching, it's much more safe
	public List<I_C_Queue_Processor> retrieveAllProcessors()
	{
		final Properties ctx = Env.getCtx();
		final String whereClause = null;
		return new Query(ctx, I_C_Queue_Processor.Table_Name, whereClause, ITrx.TRXNAME_None)
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_Queue_Processor.COLUMNNAME_C_Queue_Processor_ID)
				.list(I_C_Queue_Processor.class);
	}

	@Override
	@Cached(cacheName = I_C_Queue_PackageProcessor.Table_Name + "#ForClient")
	protected Map<Integer, I_C_Queue_PackageProcessor> retrieveWorkpackageProcessorsMap(@CacheCtx final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final String whereClause = I_C_Queue_PackageProcessor.COLUMNNAME_AD_Client_ID + " IN (0, ?)";

		final List<I_C_Queue_PackageProcessor> list = new Query(ctx, I_C_Queue_PackageProcessor.Table_Name, whereClause, ITrx.TRXNAME_None)
				.setParameters(adClientId)
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_Queue_PackageProcessor.COLUMNNAME_C_Queue_PackageProcessor_ID)
				.list(I_C_Queue_PackageProcessor.class);

		final Map<Integer, I_C_Queue_PackageProcessor> map = new HashMap<Integer, I_C_Queue_PackageProcessor>(list.size());
		for (final I_C_Queue_PackageProcessor packageProcessor : list)
		{
			map.put(packageProcessor.getC_Queue_PackageProcessor_ID(), packageProcessor);
		}
		return Collections.unmodifiableMap(map);
	}

	@Override
	protected List<I_C_Queue_Processor_Assign> retrieveQueueProcessorAssignments(I_C_Queue_Processor processor)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(processor);
		final String trxName = InterfaceWrapperHelper.getTrxName(processor);
		final int queueProcessorId = processor.getC_Queue_Processor_ID();
		return retrieveQueueProcessorAssignments(ctx, queueProcessorId, trxName);
	}

	@Cached(cacheName = I_C_Queue_Processor_Assign.Table_Name + "#By#" + I_C_Queue_Processor_Assign.COLUMNNAME_C_Queue_Processor_ID)
	/* package */List<I_C_Queue_Processor_Assign> retrieveQueueProcessorAssignments(
			@CacheCtx final Properties ctx,
			final int queueProcessorId,
			@CacheTrx final String trxName
			)
	{
		final String whereClause = I_C_Queue_Processor_Assign.COLUMNNAME_C_Queue_Processor_ID + "=?";
		final List<I_C_Queue_Processor_Assign> assignments = new Query(ctx, I_C_Queue_Processor_Assign.Table_Name, whereClause, trxName)
				.setParameters(queueProcessorId)
				.setOnlyActiveRecords(true)
				.list(I_C_Queue_Processor_Assign.class);
		return assignments;
	}

	@Override
	protected <T> T retrieveItem(final I_C_Queue_Element element, final Class<T> clazz, final String trxName)
	{
		final int adTableId = element.getAD_Table_ID();
		final int recordId = element.getRecord_ID();
		if (adTableId <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Table_ID@ (ID:" + adTableId + ")");
		}
		
		final Properties ctx = InterfaceWrapperHelper.getCtx(element);
		final IContextAware context = new PlainContextAware(ctx, trxName);
		final TableRecordReference itemRef = new TableRecordReference(adTableId, recordId);
		final T item = itemRef.getModel(context, clazz);
		if (item == null)
		{
			throw new PackageItemNotAvailableException(itemRef.getTableName(), itemRef.getRecord_ID());
		}
		
		return item;
	}

	@Override
	public IQuery<I_C_Queue_WorkPackage> createQuery(final Properties ctx, final IWorkPackageQuery packageQuery)
	{
		final List<Object> params = new ArrayList<Object>();
		final StringBuilder wc = new StringBuilder("1=1");

		// Only not processed packages
		if (packageQuery.getProcessed() != null)
		{
			wc.append(" AND ");
			wc.append(I_C_Queue_WorkPackage.COLUMNNAME_Processed).append("=?");
			params.add(packageQuery.getProcessed());
		}

		// Only packages that are ready
		if (packageQuery.getReadyForProcessing() != null)
		{
			wc.append(" AND ");
			wc.append(I_C_Queue_WorkPackage.COLUMNNAME_IsReadyForProcessing).append("=?");
			params.add(packageQuery.getReadyForProcessing());
		}

		// Only packages with IsError=N
		if (packageQuery.getError() != null)
		{
			wc.append(" AND ");
			wc.append(I_C_Queue_WorkPackage.COLUMNNAME_IsError).append("=?");
			params.add(packageQuery.getError());
		}

		// Only packages that have not been skipped,
		// or where 'retryTimeoutMillis' has already passed since they were skipped.
		// NOTE: because we are not creating the query before each polling, we cannot rely on SystemTime.millis()
		// final Timestamp nowMinusTimeOut = new Timestamp(SystemTime.millis() - packageQuery.getSkippedTimeoutMillis());
		final String nowMinusTimeOutSql = "now() - CAST("
				+ " (CASE WHEN " + I_C_Queue_WorkPackage.COLUMNNAME_SkipTimeoutMillis + " > 0"
				+ " THEN " + I_C_Queue_WorkPackage.COLUMNNAME_SkipTimeoutMillis
				+ " ELSE " + packageQuery.getSkippedTimeoutMillis()
				+ " END)"
				+ " ||' milliseconds' AS Interval)";

		wc.append(" AND ");
		wc.append(" COALESCE(" + I_C_Queue_WorkPackage.COLUMNNAME_SkippedAt + ", " + nowMinusTimeOutSql + ")").append("<=").append(nowMinusTimeOutSql);

		// Only work packages for given process
		final List<Integer> packageProcessorIds = packageQuery.getPackageProcessorIds();
		if (packageProcessorIds != null)
		{
			wc.append(" AND ");
			wc.append(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_Block_ID).append(" IN (")
					.append(" SELECT ").append(I_C_Queue_Block.COLUMNNAME_C_Queue_Block_ID)
					.append(" FROM ").append(I_C_Queue_Block.Table_Name).append(" b ")
					.append(" WHERE ")
					.append("b.").append(I_C_Queue_Block.COLUMNNAME_C_Queue_PackageProcessor_ID).append(" IN ").append(DB.buildSqlList(packageQuery.getPackageProcessorIds(), params))
					.append(")");
		}

		//
		if (packageQuery.getPriorityFrom() != null)
		{
			wc.append("AND ").append(I_C_Queue_WorkPackage.COLUMNNAME_Priority).append(">=?");
			params.add(packageQuery.getPriorityFrom());
		}

		// NOTE: don't filter by AD_Client_ID because it might be that it's not available
		return new TypedSqlQuery<I_C_Queue_WorkPackage>(ctx, I_C_Queue_WorkPackage.class, wc.toString(), ITrx.TRXNAME_None)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setOrderBy(queueOrderByComparator.getSql());

	}
}
