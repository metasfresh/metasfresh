package de.metas.printing.api.impl;

/*
 * #%L
 * de.metas.printing.base
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
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.POInfo;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.lock.api.ILockManager;
import de.metas.printing.api.IPrintPackageBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.model.I_AD_Printer;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaSize;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_AD_Printer_Tray;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Print_Job_Instructions;

public class PrintingDAO extends AbstractPrintingDAO
{
	// NOTE: before changing the SeqNo ORDER BY clause, please check were it is used
	@Override
	protected Iterator<I_C_Print_Job_Line> retrievePrintJobLines0(final I_C_Print_Job job, final int fromSeqNo, final int toSeqNo)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(job);
		final String trxName = InterfaceWrapperHelper.getTrxName(job);

		final StringBuilder whereClause = new StringBuilder();
		final List<Object> params = new ArrayList<>();

		whereClause.append(I_C_Print_Job_Line.COLUMNNAME_C_Print_Job_ID).append("=?");
		params.add(job.getC_Print_Job_ID());

		if (fromSeqNo != SEQNO_First)
		{
			Check.assume(fromSeqNo > 0, "fromSeqNo > 0");
			whereClause.append(" AND ").append(I_C_Print_Job_Line.COLUMNNAME_SeqNo).append(">=?");
			params.add(fromSeqNo);
		}

		if (toSeqNo != SEQNO_Last)
		{
			Check.assume(toSeqNo > 0, "toSeqNo > 0");
			whereClause.append(" AND ").append(I_C_Print_Job_Line.COLUMNNAME_SeqNo).append("<=?");
			params.add(toSeqNo);
		}

		return new Query(ctx, I_C_Print_Job_Line.Table_Name, whereClause.toString(), trxName)
				.setOnlyActiveRecords(true)
				.setParameters(params)
				.setOrderBy(I_C_Print_Job_Line.COLUMNNAME_SeqNo)
				.iterate(I_C_Print_Job_Line.class);
	}

	@Override
	protected int resolveSeqNo(final I_C_Print_Job job, final int seqNo)
	{
		if (seqNo > 0)
		{
			return seqNo;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(job);
		final String trxName = InterfaceWrapperHelper.getTrxName(job);
		final Query query = new Query(ctx, I_C_Print_Job_Line.Table_Name, I_C_Print_Job_Line.COLUMNNAME_C_Print_Job_ID + "=?", trxName)
				.setParameters(job.getC_Print_Job_ID())
				.setOnlyActiveRecords(true);

		if (seqNo == SEQNO_First)
		{
			final Integer min = query.aggregate(I_C_Print_Job_Line.COLUMNNAME_SeqNo, Aggregate.MIN, Integer.class);
			return min == null ? -1 : min.intValue();
		}
		else if (seqNo == SEQNO_Last)
		{
			final Integer max = query.aggregate(I_C_Print_Job_Line.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
			return max == null ? -1 : max.intValue();
		}
		else
		{
			// should not happen, it seems seqNo is not valid, but this will be handled by other layers
			return seqNo;
		}
	}

	@Override
	public I_AD_PrinterTray_Matching retrievePrinterTrayMatchingOrNull(final I_AD_Printer_Matching matching, final int AD_Printer_Tray_ID)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(matching);
		final String trxName = InterfaceWrapperHelper.getTrxName(matching);

		final String whereClause = I_AD_PrinterTray_Matching.COLUMNNAME_AD_Printer_Matching_ID + " = ?"
				+ " AND " + I_AD_PrinterTray_Matching.COLUMNNAME_AD_Printer_Tray_ID + " = ?";

		return new Query(ctx, I_AD_PrinterTray_Matching.Table_Name, whereClause, trxName)
				.setOnlyActiveRecords(true)
				.setParameters(matching.getAD_Printer_Matching_ID(), AD_Printer_Tray_ID)
				.firstOnly(I_AD_PrinterTray_Matching.class);
	}

	@Override
	public List<I_AD_PrinterTray_Matching> retrievePrinterTrayMatchings(final I_AD_Printer_Matching matching)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(matching);
		final String trxName = InterfaceWrapperHelper.getTrxName(matching);

		return new Query(ctx, I_AD_PrinterTray_Matching.Table_Name, I_AD_PrinterTray_Matching.COLUMNNAME_AD_Printer_Matching_ID + "=?", trxName)
				.setOnlyActiveRecords(true)
				.setParameters(matching.getAD_Printer_Matching_ID())
				.setOrderBy(I_AD_PrinterTray_Matching.COLUMNNAME_AD_PrinterTray_Matching_ID)
				.list(I_AD_PrinterTray_Matching.class);
	}

	@Override
	public I_C_Print_Job_Instructions retrieveAndLockNextPrintJobInstructions(final Properties ctx, final String trxName)
	{
		final StringBuilder whereClause = new StringBuilder();
		final List<Object> params = new ArrayList<>();

		// Only Pending (i.e. not printed jobs)
		whereClause.append(I_C_Print_Job_Instructions.COLUMNNAME_Status).append("=?");
		params.add(X_C_Print_Job_Instructions.STATUS_Pending);

		// Only for current user
		whereClause.append(" AND ").append(I_C_Print_Job_Instructions.COLUMNNAME_AD_User_ToPrint_ID).append("=?");
		params.add(Env.getAD_User_ID(ctx));

		//
		// Only for current HostKey, if one is specified
		final String hostKey = Services.get(IPrintPackageBL.class).getHostKeyOrNull(ctx);
		if (!Check.isEmpty(hostKey, true))
		{
			whereClause.append(" AND (").append(I_C_Print_Job_Instructions.COLUMNNAME_HostKey).append("=?")
					.append(" OR ").append(I_C_Print_Job_Instructions.COLUMNNAME_HostKey).append(" IS NULL")
					.append(")");
			params.add(hostKey);
		}

		final IQuery<I_C_Print_Job_Instructions> query = new TypedSqlQuery<>(ctx, I_C_Print_Job_Instructions.class, whereClause.toString(), trxName)
				.setOnlyActiveRecords(true)
				.setParameters(params)
				.setOrderBy(I_C_Print_Job_Instructions.COLUMNNAME_C_Print_Job_Instructions_ID)
				.setClient_ID()
				.setApplyAccessFilterRW(true);

		return Services.get(ILockManager.class).retrieveAndLock(query, I_C_Print_Job_Instructions.class);
	}

	@Override
	public IQuery<I_C_Printing_Queue> createQuery(final Properties ctx,
			final IPrintingQueueQuery queueQuery,
			final String trxName)
	{
		final StringBuilder whereClause = new StringBuilder("1=1");
		final List<Object> params = new ArrayList<>();

		boolean guaranteedIteratorRequired = false;

		if (queueQuery.getIsPrinted() != null)
		{
			whereClause.append(" AND ").append(I_C_Printing_Queue.COLUMNNAME_Processed).append("=?");
			params.add(queueQuery.getIsPrinted().booleanValue());

			// The Processed column is likely to be changed after retrieving and processing. That's why we need a guaranteed iterator.
			guaranteedIteratorRequired = true;
		}

		if (queueQuery.getAD_Client_ID() >= 0)
		{
			whereClause.append(" AND ").append(I_C_Printing_Queue.COLUMNNAME_AD_Client_ID).append("=?");
			params.add(queueQuery.getAD_Client_ID());
		}

		if (queueQuery.getAD_Org_ID() >= 0)
		{
			whereClause.append(" AND ").append(I_C_Printing_Queue.COLUMNNAME_AD_Org_ID).append("=?");
			params.add(queueQuery.getAD_Org_ID());
		}

		if (queueQuery.getAD_User_ID() >= 0)
		{
			whereClause.append(" AND (").append(I_C_Printing_Queue.COLUMNNAME_AD_User_ID).append("=?")
					.append(" OR ").append(I_C_Printing_Queue.COLUMNNAME_AD_User_ID).append(" IS NULL")
					.append(")");
			params.add(queueQuery.getAD_User_ID());
		}

		if (queueQuery.getIgnoreC_Printing_Queue_ID() > 0)
		{
			whereClause.append(" AND ").append(I_C_Printing_Queue.COLUMNNAME_C_Printing_Queue_ID).append("<>?");
			params.add(queueQuery.getIgnoreC_Printing_Queue_ID());
		}

		if (queueQuery.getFilter() != null)
		{
			final ISqlQueryFilter filter = queueQuery.getFilter();
			whereClause.append(" AND (").append(filter.getSql()).append(")");
			params.addAll(filter.getSqlParams(ctx));
		}

		final String archiveTableAlias = "ar";
		final StringBuilder archiveWhereClause = new StringBuilder();
		final List<Object> archiveParams = new ArrayList<>();
		final int modelTableId = queueQuery.getModelTableId();
		if (modelTableId > 0)
		{
			archiveWhereClause.append(" AND ").append(archiveTableAlias).append(".").append(I_AD_Archive.COLUMNNAME_AD_Table_ID).append("=?");
			archiveParams.add(modelTableId);
		}
		if (queueQuery.getModelFromRecordId() > 0)
		{
			Check.assume(modelTableId > 0, "modelTableId shall be specified when filtering by modelFromRecordId");
			archiveWhereClause.append(" AND ").append(archiveTableAlias).append(".").append(I_AD_Archive.COLUMNNAME_Record_ID).append(">=?");
			archiveParams.add(queueQuery.getModelFromRecordId());
		}
		if (queueQuery.getModelToRecordId() > 0)
		{
			Check.assume(modelTableId > 0, "modelTableId shall be specified when filtering by modelToRecordId");
			archiveWhereClause.append(" AND ").append(archiveTableAlias).append(".").append(I_AD_Archive.COLUMNNAME_Record_ID).append("<=?");
			archiveParams.add(queueQuery.getModelToRecordId());
		}

		if (queueQuery.getModelFilter() != null)
		{
			Check.assume(modelTableId > 0, "ModelTableId shall be specified when ModelFilter is specified");
			final POInfo modelPOInfo = POInfo.getPOInfo(ctx, modelTableId);
			final String modelTableName = modelPOInfo.getTableName();
			final String modelKeyColumName = modelPOInfo.getKeyColumnName();
			Check.assumeNotEmpty(modelKeyColumName, "No single primary key column found for {}", modelPOInfo);

			final ISqlQueryFilter modelFilter = queueQuery.getModelFilter();

			archiveWhereClause.append(" AND EXISTS (SELECT 1 FROM ").append(modelTableName)
					.append(" WHERE ")
					// Link between AD_Archive.Record_ID and model table
					.append(modelTableName).append(".").append(modelKeyColumName)
					.append("=").append(archiveTableAlias).append(".").append(I_AD_Archive.COLUMNNAME_Record_ID)
					// Model filter
					.append(" AND ").append(modelFilter.getSql())
					.append(")");
			archiveParams.addAll(modelFilter.getSqlParams(ctx));
		}

		if (archiveWhereClause.length() > 0)
		{
			whereClause.append(" AND EXISTS (SELECT 1 FROM ").append(I_AD_Archive.Table_Name).append(" ").append(archiveTableAlias)
					.append(" WHERE ")
					// Join link between parent and AD_Archive
					.append(archiveTableAlias).append(".").append(I_AD_Archive.COLUMNNAME_AD_Archive_ID)
					.append("=").append(I_C_Printing_Queue.Table_Name).append(".").append(I_C_Printing_Queue.COLUMNNAME_AD_Archive_ID)
					// actual AD_Archive where clause
					// .append(" AND ")// NOTE: archiveWhereClause already starts with "AND"
					.append(" ").append(archiveWhereClause)
					.append(")");
			params.addAll(archiveParams);
		}

		if (queueQuery.getCopies() != null)
		{
			whereClause.append(" AND ").append(I_C_Printing_Queue.COLUMNNAME_Copies).append("=?");
			params.add(queueQuery.getCopies());
		}

		// task 09028
		if (queueQuery.getAggregationKey() != null)
		{
			whereClause.append(" AND ").append(I_C_Printing_Queue.COLUMNNAME_PrintingQueueAggregationKey).append("=?");
			params.add(queueQuery.getAggregationKey());
		}
		//
		// Create the Query
		final TypedSqlQuery<I_C_Printing_Queue> query = new TypedSqlQuery<>(ctx, I_C_Printing_Queue.class, whereClause.toString(), trxName)
				.setParameters(params);

		if (queueQuery.getOnlyAD_PInstance_ID() > 0)
		{
			query.setOnlySelection(queueQuery.getOnlyAD_PInstance_ID());
		}

		if (queueQuery.getApplyAccessFilterRW() != null)
		{
			query.setApplyAccessFilterRW(queueQuery.getApplyAccessFilterRW());
		}

		query.setOnlyActiveRecords(true);

		if (guaranteedIteratorRequired)
		{
			query.setOption(Query.OPTION_GuaranteedIteratorRequired, true);
		}

		// IMPORTANT: we shall poll the queue respecting the FIFO order
		final StringBuilder orderBy = new StringBuilder();
		orderBy
				.append(I_C_Printing_Queue.COLUMNNAME_Copies)
				.append(",")
				.append(I_C_Printing_Queue.COLUMNNAME_C_Printing_Queue_ID);
		query.setOrderBy(orderBy.toString());

		return query;
	}

	@Override
	public List<I_AD_PrinterHW_MediaSize> retrieveMediaSizes(final I_AD_PrinterHW printerHW)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printerHW);
		final String trxName = InterfaceWrapperHelper.getTrxName(printerHW);

		final String whereClause = I_AD_PrinterHW_MediaSize.COLUMNNAME_AD_PrinterHW_ID + " =?";

		final List<I_AD_PrinterHW_MediaSize> result = new Query(ctx, I_AD_PrinterHW_MediaSize.Table_Name, whereClause, trxName)
				.setOnlyActiveRecords(true)
				.setParameters(printerHW.getAD_PrinterHW_ID())
				.list(I_AD_PrinterHW_MediaSize.class);

		return result;
	}

	@Override
	public I_AD_PrinterHW_Calibration retrieveCalibration(final I_AD_PrinterHW_MediaSize hwMediaSize, final I_AD_PrinterHW_MediaTray hwTray)
	{
		Check.assume(hwMediaSize != null, "Param 'hwMediaSize' is not null");
		Check.assume(hwTray != null, "Param 'hwTray' is not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(hwMediaSize);
		final String trxName = InterfaceWrapperHelper.getTrxName(hwMediaSize);

		return new Query(ctx, I_AD_PrinterHW_Calibration.Table_Name, I_AD_PrinterHW_Calibration.COLUMNNAME_AD_PrinterHW_MediaSize_ID + "=? AND "
				+ I_AD_PrinterHW_Calibration.COLUMNNAME_AD_PrinterHW_MediaTray_ID + "=?", trxName)
						.setParameters(hwMediaSize.getAD_PrinterHW_MediaSize_ID(), hwTray.getAD_PrinterHW_MediaTray_ID())
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.firstOnly(I_AD_PrinterHW_Calibration.class);
	}

	@Override
	public List<I_AD_PrinterHW_Calibration> retrieveCalibrations(final Properties ctx, final int printerID, final String trxName)
	{
		final String whereClause = I_AD_PrinterHW_Calibration.COLUMNNAME_AD_PrinterHW_ID + " =?";

		final List<I_AD_PrinterHW_Calibration> result = new Query(ctx, I_AD_PrinterHW_Calibration.Table_Name, whereClause, trxName)
				.setParameters(printerID)
				.setOnlyActiveRecords(true)
				.list(I_AD_PrinterHW_Calibration.class);

		return result;
	}

	@Override
	public List<I_AD_PrinterHW_MediaTray> retrieveMediaTrays(final I_AD_PrinterHW printerHW)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printerHW);
		final String trxName = InterfaceWrapperHelper.getTrxName(printerHW);

		final String whereClause = I_AD_PrinterHW_MediaTray.COLUMNNAME_AD_PrinterHW_ID + " =?";

		final List<I_AD_PrinterHW_MediaTray> result = new Query(ctx, I_AD_PrinterHW_MediaTray.Table_Name, whereClause, trxName)
				.setOnlyActiveRecords(true)
				.setParameters(printerHW.getAD_PrinterHW_ID())
				.list(I_AD_PrinterHW_MediaTray.class);

		return result;
	}

	@Override
	public void runWithTrxName(final String trxName, final Runnable runnable)
	{
		Check.assumeNotNull(runnable, "runnable not null");

		final String allowedTrxNamePrefix = Util.same(trxName, ITrx.TRXNAME_None) ? ITrx.TRXNAME_PREFIX_LOCAL : trxName;

		DB.saveConstraints();
		try
		{
			DB.getConstraints().addAllowedTrxNamePrefix(allowedTrxNamePrefix);
			runnable.run();
		}
		finally
		{
			DB.restoreConstraints();
		}
	}

	@Override
	public List<I_AD_Printer> retrievePrinters(final Properties ctx, final int adOrgId)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final String whereClause = I_AD_Printer.COLUMNNAME_AD_Client_ID + " IN (0, ?)"
				+ " AND " + I_AD_Printer.COLUMNNAME_AD_Org_ID + " IN (0, ?)";

		return new Query(ctx, I_AD_Printer.Table_Name, whereClause, ITrx.TRXNAME_None)
				.setParameters(adClientId, adOrgId)
				.setOnlyActiveRecords(true)
				.setOrderBy(
						I_AD_Printer.COLUMNNAME_AD_Client_ID + " DESC"
								+ ", " + I_AD_Printer.COLUMNNAME_AD_Org_ID + " DESC"
								+ ", " + I_AD_Printer.COLUMNNAME_PrinterName)
				.list(I_AD_Printer.class);
	}

	@Override
	public List<I_AD_Printer_Tray> retrieveTrays(final I_AD_Printer printer)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printer);
		final String trxName = InterfaceWrapperHelper.getTrxName(printer);

		final String whereClause = I_AD_Printer_Tray.COLUMNNAME_AD_Printer_ID + "=?";
		return new Query(ctx, I_AD_Printer_Tray.Table_Name, whereClause, trxName)
				.setParameters(printer.getAD_Printer_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.list(I_AD_Printer_Tray.class);
	}
}
