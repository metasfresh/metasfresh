package de.metas.acct.process;

import java.util.Iterator;
import java.util.Map;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;

import de.metas.acct.model.I_Fact_Acct_ActivityChangeRequest;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.process.SvrProcess;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Processes all activity change requests.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task http://dewiki908/mediawiki/index.php/09110_Konten_KST_Pflicht_%28105477200774%29
 */
public class Fact_Acct_ActivityChangeRequest_Process extends SvrProcess
{
	// services
	private final transient IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final transient IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);

	/** Supported documents mapping: "header table name" to "line model class" */
	static final Map<String, Class<?>> headerTableName2lineModelClass = ImmutableMap.<String, Class<?>> builder()
			.put(I_C_Invoice.Table_Name, I_C_InvoiceLine.class)
			.put(I_M_InOut.Table_Name, I_M_InOutLine.class)
			.put(I_GL_Journal.Table_Name, I_GL_JournalLine.class)
			// NOTE: Other documents are not supported atm
			.build();

	@Override
	protected String doIt() throws Exception
	{
		int processedCount = 0;
		final Iterator<I_Fact_Acct_ActivityChangeRequest> requests = retrieveRequestsToProcess();
		while (requests.hasNext())
		{
			final I_Fact_Acct_ActivityChangeRequest request = requests.next();
			final boolean processed = process(request);
			if (processed)
			{
				processedCount++;
			}
		}

		return "@Processed@ #" + processedCount;
	}

	private final Iterator<I_Fact_Acct_ActivityChangeRequest> retrieveRequestsToProcess()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct_ActivityChangeRequest.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_Fact_Acct_ActivityChangeRequest.COLUMN_Processed, false)
				.addNotEqualsFilter(I_Fact_Acct_ActivityChangeRequest.COLUMN_C_Activity_Override_ID, null)
				.orderBy()
				.addColumn(I_Fact_Acct_ActivityChangeRequest.COLUMN_Fact_Acct_ActivityChangeRequest_ID)
				.endOrderBy()
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.iterate(I_Fact_Acct_ActivityChangeRequest.class);
	}

	private boolean process(final I_Fact_Acct_ActivityChangeRequest request)
	{
		// Already processed, shall not happen
		if (request.isProcessed())
		{
			return false;
		}

		final IActivityAware activityAware = getDocLineActivityAwareOrNull(request);
		if (activityAware == null)
		{
			// shall not happen
			addLog("Skip {0} because it does not provide an activity", request);
			return false;
		}

		//
		// Update document line
		final int activityIdToSet = request.getC_Activity_Override_ID();
		activityAware.setC_Activity_ID(activityIdToSet);
		InterfaceWrapperHelper.save(activityAware);

		//
		// Update all fact lines which are about our document line.
		factAcctDAO.updateActivityForDocumentLine(getCtx(), request.getAD_Table_ID(), request.getRecord_ID(), request.getLine_ID(), activityIdToSet);

		//
		// Delete this request because it was processed
		InterfaceWrapperHelper.delete(request);

		return true;
	}

	private final Class<?> getDocLineClass(final int adTableId)
	{
		final String tableName = tableDAO.retrieveTableName(adTableId);
		return headerTableName2lineModelClass.get(tableName);
	}

	private final IActivityAware getDocLineActivityAwareOrNull(final I_Fact_Acct_ActivityChangeRequest request)
	{
		final Class<?> lineClass = getDocLineClass(request.getAD_Table_ID());
		if (lineClass == null)
		{
			addLog("Skip {0} because it's not supported", request);
			return null;
		}

		final int lineId = request.getLine_ID();
		if (lineId <= 0)
		{
			addLog("Skip {0} because it does not have the Line_ID set", request);
			return null;
		}

		final Object line = InterfaceWrapperHelper.create(getCtx(), lineId, lineClass, ITrx.TRXNAME_ThreadInherited);
		final IActivityAware activityAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(line, IActivityAware.class);
		if (activityAware == null)
		{
			// no activity on line level
			addLog("Skip {0} because it does not provide an activity", line);
			return null;
		}

		return activityAware;
	}

	@VisibleForTesting
	static interface IActivityAware
	{
		String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

		int getC_Activity_ID();

		void setC_Activity_ID(final int activityId);
	}
}
