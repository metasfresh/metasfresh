package org.adempiere.ad.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.service.IADProcessDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Table_Process;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

public class ADProcessDAO implements IADProcessDAO
{
	/**
	 * Map: AD_Table_ID to list of AD_Process_ID
	 */
	private final Map<Integer, List<Integer>> staticRegisteredProcesses = new HashMap<Integer, List<Integer>>();

	@Override
	public int retriveProcessIdByClassIfUnique(final Properties ctx, final Class<?> processClass)
	{
		List<Integer> processIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Process.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Process.COLUMN_Classname, processClass.getName())
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds();

		if (processIds.isEmpty())
		{
			return -1;
		}
		else if (processIds.size() == 1)
		{
			return processIds.get(0);
		}
		else
		{
			// more then one AD_Process_IDs matched => return -1
			return -1;
		}
	}

	@Override
	public int retriveProcessIdByValue(final Properties ctx, final String processValue)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Process.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Process.COLUMN_Value, processValue)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly();
	}

	@Override
	public I_AD_Process retriveProcessByValue(final Properties ctx, final String processValue)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Process.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Process.COLUMN_Value, processValue)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyNotNull(I_AD_Process.class);
	}

	@Override
	public void registerTableProcess(final int adTableId, final int adProcessId)
	{
		Check.assume(adTableId > 0, "adTableId > 0");
		Check.assume(adProcessId > 0, "adProcessId > 0");

		List<Integer> processIds = staticRegisteredProcesses.get(adTableId);
		if (processIds == null)
		{
			processIds = new ArrayList<Integer>();
			staticRegisteredProcesses.put(adTableId, processIds);
		}
		if (!processIds.contains(adProcessId))
		{
			processIds.add(adProcessId);
		}
	}

	@Override
	public void registerTableProcess(final String tableName, final int adProcessId)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");

		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		Check.assume(adTableId > 0, "adTableId > 0 (TableName={})", tableName);

		registerTableProcess(adTableId, adProcessId);
	}

	/**
	 * Gets a list of AD_Process_IDs which were statically registered for given table ID.
	 * 
	 * @param adTableId
	 * @return list of AD_Process_IDs
	 */
	protected List<Integer> getStaticRegisteredProcessIds(final int adTableId)
	{
		final List<Integer> processIds = staticRegisteredProcesses.get(adTableId);
		if (processIds == null || processIds.isEmpty())
		{
			return Collections.emptyList();
		}

		return new ArrayList<>(processIds);
	}

	// @Cached
	@Override
	public final List<I_AD_Process> retrieveProcessesForTable(final Properties ctx, final int adTableId)
	{
		return retrieveProcessesForTableQueryBuilder(ctx, adTableId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_AD_Process.class);
	}

	@Override
	public final List<Integer> retrieveProcessesIdsForTable(@CacheCtx final Properties ctx, final int adTableId)
	{
		return retrieveProcessesForTableQueryBuilder(ctx, adTableId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds();
	}

	@Override
	public final List<I_AD_Process> retrieveReportProcessesForTable(final Properties ctx, final String tableName)
	{
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final IQueryBuilder<I_AD_Process> queryBuilder = retrieveProcessesForTableQueryBuilder(ctx, adTableId);
		queryBuilder.filter(new EqualsQueryFilter<I_AD_Process>(I_AD_Process.COLUMNNAME_IsReport, true));
		return queryBuilder.create()
				.setOnlyActiveRecords(true)
				.list(I_AD_Process.class);
	}

	private IQueryBuilder<I_AD_Process> retrieveProcessesForTableQueryBuilder(final Properties ctx, final int adTableId)
	{
		final String trxName = ITrx.TRXNAME_NoneNotNull;

		final ICompositeQueryFilter<I_AD_Process> filters = Services.get(IQueryBL.class).createCompositeQueryFilter(I_AD_Process.class);
		filters.setJoinOr();

		//
		// Filter by assigned table processes
		{
			final IQuery<I_AD_Table_Process> queryTableProcessForTable = Services.get(IQueryBL.class)
					.createQueryBuilder(I_AD_Table_Process.class, ctx, trxName)
					.filter(new EqualsQueryFilter<I_AD_Table_Process>(I_AD_Table_Process.COLUMNNAME_AD_Table_ID, adTableId))
					.create()
					.setOnlyActiveRecords(true);
			filters.addInSubQueryFilter(I_AD_Process.COLUMNNAME_AD_Process_ID, I_AD_Table_Process.COLUMNNAME_AD_Process_ID, queryTableProcessForTable);
		}

		//
		// Or Filter by programatically registered processes
		{
			final List<Integer> processIds = getStaticRegisteredProcessIds(adTableId);
			if (!processIds.isEmpty())
			{
				// NOTE: we are adding only if is not empty because InArrayFilter on an empty array will always return true
				filters.addInArrayFilter(I_AD_Process.COLUMNNAME_AD_Process_ID, processIds);
			}
		}

		final IQueryBuilder<I_AD_Process> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Process.class, ctx, trxName)
				.filter(filters);

		queryBuilder.orderBy()
				.addColumn(I_AD_Process.COLUMNNAME_Name);
		return queryBuilder;
	}

	@Override
	public I_AD_Process retrieveProcessByForm(final Properties ctx, final int AD_Form_ID)
	{
		final String trxName = ITrx.TRXNAME_NoneNotNull;

		final IQueryBuilder<I_AD_Process> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Process.class, ctx, trxName)
				.filter(new EqualsQueryFilter<I_AD_Process>(I_AD_Process.COLUMNNAME_AD_Form_ID, AD_Form_ID));

		return queryBuilder.create()
				.setOnlyActiveRecords(true)
				.firstOnly(I_AD_Process.class);
	}

	@Override
	public int retrieveProcessParaLastSeqNo(final I_AD_Process process)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class).createQueryBuilder(I_AD_Process_Para.class, process)
				.addEqualsFilter(I_AD_Process_Para.COLUMNNAME_AD_Process_ID, process.getAD_Process_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_AD_Process_Para.COLUMNNAME_SeqNo, IQuery.AGGREGATE_MAX, Integer.class);
		return lastSeqNo == null ? 0 : lastSeqNo;
	}

	@Override
	public List<I_AD_Process_Para> retrieveProcessParameters(final I_AD_Process process)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(process);
		final String trxName = InterfaceWrapperHelper.getTrxName(process);
		final int adProcessId = process.getAD_Process_ID();
		return retrieveProcessParameters(ctx, adProcessId, trxName);
	}

	@Cached(cacheName = I_AD_Process_Para.Table_Name + "#by#" + I_AD_Process_Para.COLUMNNAME_AD_Process_ID)
	public List<I_AD_Process_Para> retrieveProcessParameters(@CacheCtx final Properties ctx, final int adProcessId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Process_Para.class, ctx, trxName)
				.addEqualsFilter(I_AD_Process_Para.COLUMNNAME_AD_Process_ID, adProcessId)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_AD_Process_Para.COLUMNNAME_SeqNo)
				.endOrderBy()
				.create()
				.listImmutable(I_AD_Process_Para.class);
	}
}
