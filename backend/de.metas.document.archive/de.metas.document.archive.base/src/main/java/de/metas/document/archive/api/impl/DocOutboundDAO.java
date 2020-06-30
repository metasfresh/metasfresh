package de.metas.document.archive.api.impl;

/*
 * #%L
 * de.metas.document.archive.base
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

import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Env;

import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log_Line;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

public class DocOutboundDAO implements IDocOutboundDAO
{

	private final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);

	// note that this method doesn't directly access the DB. Therefore, a unit test DAO implementation can extend this
	// class without problems.
	@Override
	public final I_C_Doc_Outbound_Config retrieveConfig(final Properties ctx, final int tableId)
	{
		Check.assume(tableId > 0, "tableId > 0");

		final int adClientId = Env.getAD_Client_ID(ctx);

		I_C_Doc_Outbound_Config configSys = null;
		I_C_Doc_Outbound_Config config = null;
		for (final I_C_Doc_Outbound_Config currentConfig : retrieveAllConfigs())
		{
			if (currentConfig.getAD_Table_ID() == tableId)
			{
				if (currentConfig.getAD_Client_ID() == adClientId)
				{
					throwExceptionIfNotNull(config, tableId, adClientId, currentConfig);
					config = currentConfig;
				}
				else if (currentConfig.getAD_Client_ID() == 0) // system
				{
					throwExceptionIfNotNull(configSys, tableId, adClientId, currentConfig);
					configSys = currentConfig;
				}
			}
		}
		return coalesce(config, configSys);
	}

	private void throwExceptionIfNotNull(
			@Nullable final I_C_Doc_Outbound_Config alreadyFoundConfig,
			final int tableId,
			final int adClientId,
			@NonNull final I_C_Doc_Outbound_Config currentConfig)
	{
		if (alreadyFoundConfig == null)
		{
			return;
		}
		final String msg = StringUtils.formatMessage(
				"Only one configuration shall exist for tableId '{}' on client '{}' but we found: {}, {}",
				tableId, adClientId, alreadyFoundConfig, currentConfig);

		throw new AdempiereException(msg)
				.markAsUserValidationError(); // this error message is not exactly nice, but we still need to inform the user
	}

	@Override
	public final I_C_Doc_Outbound_Config retrieveConfigForModel(@NonNull final Object model)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);

		return retrieveConfig(ctx, adTableId);
	}

	@Override
	public final I_C_Doc_Outbound_Log_Line retrieveCurrentPDFArchiveLogLineOrNull(final I_C_Doc_Outbound_Log log)
	{
		if (log == null)
		{
			return null;
		}

		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Object contextProvider = log;

		final IQueryBuilder<I_C_Doc_Outbound_Log_Line> queryBuilder = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log_Line.class, contextProvider)
				.addEqualsFilter(I_C_Doc_Outbound_Log_Line.COLUMN_C_Doc_Outbound_Log_ID, log.getC_Doc_Outbound_Log_ID());
		addPDFArchiveLogLineFilters(queryBuilder);

		//
		// We're interested in the latest log line matching PDF Export only
		return queryBuilder.create()
				.first(I_C_Doc_Outbound_Log_Line.class);
	}

	@Override
	public final void addPDFArchiveLogLineFilters(final IQueryBuilder<I_C_Doc_Outbound_Log_Line> queryBuilder)
	{
		Check.assumeNotNull(queryBuilder, "queryBuilder not null");

		queryBuilder
				.addOnlyActiveRecordsFilter()
				//
				// Filter records which actually have an AD_Archive
				//
				.addCompareFilter(I_C_Doc_Outbound_Log_Line.COLUMN_AD_Archive_ID, Operator.GREATER, 0)
				//
				// Filter the ones with PDF export
				//
				.addEqualsFilter(I_C_Doc_Outbound_Log_Line.COLUMN_Action, X_C_Doc_Outbound_Log_Line.ACTION_PdfExport);

		//
		// Order by latest log line first
		queryBuilder.orderBy()
				.addColumn(I_C_Doc_Outbound_Log_Line.COLUMN_C_Doc_Outbound_Log_Line_ID, Direction.Descending, Nulls.Last);
	}

	@Override
	public I_C_Doc_Outbound_Log retrieveLog(final IContextAware contextProvider, int bpartnerId, int AD_Table_ID)
	{
		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Doc_Outbound_Log> queryBuilder = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class, contextProvider)
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMN_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMN_AD_Table_ID, AD_Table_ID);

		// Order by
		final IQueryOrderBy queryOrderBy = Services.get(IQueryBL.class)
				.createQueryOrderByBuilder(I_C_Doc_Outbound_Log.class)
				.addColumnDescending(I_C_Doc_Outbound_Log.COLUMNNAME_Created)
				.createQueryOrderBy();

		return queryBuilder
				.create()
				.setOrderBy(queryOrderBy)
				.first(I_C_Doc_Outbound_Log.class);
	}

	@Override
	public final I_C_Doc_Outbound_Log retrieveLog(@NonNull final ArchiveId archiveId)
	{
		final I_AD_Archive archiveRecord = archiveDAO.retrieveArchive(archiveId);
		final TableRecordReference tableRecordReference = TableRecordReference.ofReferenced(archiveRecord);
		return retrieveLog(tableRecordReference);
	}

	@Override
	public I_C_Doc_Outbound_Log retrieveLog(@NonNull final TableRecordReference tableRecordReference)
	{
		final I_C_Doc_Outbound_Log docExchange = Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMN_AD_Table_ID, tableRecordReference.getAdTableId())
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMN_Record_ID, tableRecordReference.getRecord_ID())
				.create()
				.firstOnly(I_C_Doc_Outbound_Log.class);

		return docExchange;
	}

	@Override
	@Cached(cacheName = I_C_Doc_Outbound_Config.Table_Name + "#All")
	public List<I_C_Doc_Outbound_Config> retrieveAllConfigs()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_Doc_Outbound_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}
}
