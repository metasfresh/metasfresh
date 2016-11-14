package de.metas.printing.process;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.dao.impl.SqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.model.I_C_Printing_Queue;

/**
 * Re-enqueue {@link I_C_Printing_Queue} items.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/04468_Belege_erneut_in_Druckerwarteschlange_%282013062710000119%29
 */
public class C_Printing_Queue_ReEnqueue extends SvrProcess
{
	public static final String PARAM_IsSelected = "IsSelected";
	private boolean p_IsSelected = false;

	public static final String PARAM_IsPrinted = "IsPrinted";
	private Boolean p_IsPrinted = null;

	public static final String PARAM_AD_Table_ID = "AD_Table_ID";
	private int p_AD_Table_ID = -1;

	public static final String PARAM_Record_ID = "Record_ID";
	private int p_Record_ID = -1;
	private int p_Record_ID_To = -1;

	public static final String PARAM_WhereClause = "WhereClause";
	private String p_WhereClause = null;

	public static final String PARAM_IsRecreatePrintout = "IsRecreatePrintout";
	private boolean p_IsRecreatePrintout = true;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				continue;
			}

			if (PARAM_IsSelected.equals(name))
			{
				p_IsSelected = para.getParameterAsBoolean();
			}
			if (PARAM_IsPrinted.equals(name))
			{
				p_IsPrinted = para.getParameterAsBooleanOrNull();
			}
			else if (PARAM_AD_Table_ID.equals(name))
			{
				p_AD_Table_ID = para.getParameterAsInt();
			}
			else if (PARAM_Record_ID.equals(name))
			{
				p_Record_ID = para.getParameterAsInt();
				p_Record_ID_To = para.getParameter_ToAsInt();
			}
			else if (PARAM_WhereClause.equals(name))
			{
				p_WhereClause = (String)para.getParameter();
			}
			else if (PARAM_IsRecreatePrintout.equals(name))
			{
				p_IsRecreatePrintout = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		int countAll = 0;
		int countOk = 0;
		int countError = 0;

		final Iterator<I_C_Printing_Queue> queueItems = retrievePrintingQueueItems();

		DB.saveConstraints();
		try
		{
			DB.getConstraints().addAllowedTrxNamePrefix("POSave");

			while (queueItems.hasNext())
			{
				final I_C_Printing_Queue item = queueItems.next();

				try
				{
					countAll++;

					Services.get(IPrintingQueueBL.class).renqueue(item, p_IsRecreatePrintout);

					countOk++;
				}
				catch (Exception e)
				{
					countError++;
					log.warn(e.getLocalizedMessage(), e);
					addLog("Error on @C_Printing_Queue@#" + item.getC_Printing_Queue_ID() + ": " + e.getLocalizedMessage());
				}
			}
		}
		finally
		{
			DB.restoreConstraints();
		}

		return "@IsPrinted@ (All/OK/Error): " + countAll + "/" + countOk + "/" + countError;
	}

	private Iterator<I_C_Printing_Queue> retrievePrintingQueueItems()
	{
		final Properties ctx = getCtx();

		final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
		final IPrintingQueueQuery queueQuery = printingQueueBL.createPrintingQueueQuery();

		queueQuery.setApplyAccessFilterRW(true);

		if (p_IsSelected)
		{
			final int selectionId = getAD_PInstance_ID();
			createWindowSelectionId(selectionId);
			queueQuery.setOnlyAD_PInstance_ID(selectionId);
		}

		if (p_IsPrinted != null)
		{
			queueQuery.setIsPrinted(p_IsPrinted);
		}

		queueQuery.setModelTableId(p_AD_Table_ID);
		queueQuery.setModelFromRecordId(p_Record_ID);
		queueQuery.setModelToRecordId(p_Record_ID_To);

		if (!Check.isEmpty(p_WhereClause, true))
		{
			final ISqlQueryFilter modelFilter = new SqlQueryFilter(p_WhereClause);
			queueQuery.setModelFilter(modelFilter);
		}

		log.info("Queue query: {}", queueQuery);

		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
		final IQuery<I_C_Printing_Queue> query = printingDAO.createQuery(ctx, queueQuery, ITrx.TRXNAME_None);
		query.setOption(Query.OPTION_IteratorBufferSize, null); // use standard IteratorBufferSize

		if (log.isInfoEnabled())
		{
			log.info("SQL Query: {}", query);
			log.info("SQL Query count: " + query.count());
		}

		final Iterator<I_C_Printing_Queue> it = query.iterate(I_C_Printing_Queue.class);
		return it;
	}

	private int createWindowSelectionId(int selectionId)
	{
		final String trxName = ITrx.TRXNAME_None;
		final StringBuilder sqlWhere = new StringBuilder();
		final List<Object> params = new ArrayList<Object>();

		final String gtWhereClause = getProcessInfo().getWhereClause();
		if (!Check.isEmpty(gtWhereClause, true))
		{
			sqlWhere.append(gtWhereClause);
		}

		final Query query = new Query(getCtx(), I_C_Printing_Queue.Table_Name, sqlWhere.toString(), trxName)
				.setParameters(params)
				.setClient_ID()
				.setApplyAccessFilterRW(true)
				.setOnlyActiveRecords(true);
		final int count = query.createSelection(selectionId);

		log.info("Window Selection Query: {}", query);
		log.info("Window Selection Count: {}", count);

		return count;
	}

}
