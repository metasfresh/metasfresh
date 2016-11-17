package org.adempiere.ad.process;

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
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.GenericPO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.Query;
import org.compiere.util.TrxRunnable2;

import de.metas.document.engine.IDocActionBL;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Select all records from AD_Table_ID, with given optional WhereClause for each record, call processIt with given DocAction
 * 
 * @task 05502, copied over from uat
 */
public class ProcessDocuments extends JavaProcess
{
	//
	// services
	final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	final ITrxManager trxManager = Services.get(ITrxManager.class);
	final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	
	private int p_AD_Table_ID = -1;
	public static final String PARAM_AD_Table_ID = "AD_Table_ID";

	private String p_WhereClause = null;
	public static final String PARAM_WhereClause = "WhereClause";

	private String p_DocAction = null;
	public static final String PARAM_DocAction = "DocAction";

	private String p_DocStatus = null;
	public static final String PARAM_DocStatus = "DocStatus";

	//
	// Statistics
	private int countOK = 0;
	private int countError = 0;

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
			else if (name.equals(ProcessDocuments.PARAM_AD_Table_ID))
			{
				this.p_AD_Table_ID = para.getParameterAsInt();
			}
			else if (name.equals(ProcessDocuments.PARAM_WhereClause))
			{
				this.p_WhereClause = para.getParameter().toString();
			}
			else if (name.equals(ProcessDocuments.PARAM_DocAction))
			{
				this.p_DocAction = para.getParameter().toString();
			}
			else if (name.equals(ProcessDocuments.PARAM_DocStatus))
			{
				this.p_DocStatus = para.getParameter().toString();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		this.countOK = 0;
		this.countError = 0;

		final Iterator<GenericPO> documents = retriveDocumentsToProcess(getCtx(), this.p_AD_Table_ID, this.p_DocStatus, this.p_WhereClause);
		processDocuments(documents, this.p_DocAction);

		return "@Processed@ (OK=#" + this.countOK + ", Error=#" + this.countError + ")";
	}

	private void processDocuments(final Iterator<GenericPO> documents, final String docAction)
	{
		while (documents.hasNext())
		{
			final Object doc = documents.next();
			trxManager.run(new TrxRunnable2()
			{
				@Override
				public void run(final String localTrxName) throws Exception
				{
					InterfaceWrapperHelper.refresh(doc, localTrxName);
					docActionBL.processEx(doc, docAction, null); // expectedDocStatus=null because it is *not* always equal to the docAaction (Prepay-Order)

					addLog("Document " + doc + ": Processed");
					ProcessDocuments.this.countOK++;
				}

				@Override
				public boolean doCatch(final Throwable e) throws Throwable
				{
					final String msg = "Processing of document " + doc
							+ ": Failed - ProccessMsg: " + Services.get(IDocActionBL.class).getDocAction(doc).getProcessMsg()
							+ "; ExceptionMsg: " + e.getMessage();
					addLog(msg);
					ProcessDocuments.this.log.warn(msg, e);
					ProcessDocuments.this.countError++;
					return true; // rollback
				}

				@Override
				public void doFinally()
				{
					// nothing
				}
			});
		}
	}

	private Iterator<GenericPO> retriveDocumentsToProcess(final Properties ctx,
			final int adTableId,
			final String docStatus,
			final String whereClause)
	{
		Check.assume(adTableId > 0, "adTableId > 0");
		Check.assumeNotNull(docStatus, "docStatus not null");

		final List<Object> params = new ArrayList<Object>();
		final StringBuilder finalWhereClause = new StringBuilder();

		finalWhereClause.append(ProcessDocuments.PARAM_DocStatus).append("=?");
		params.add(docStatus);

		if (!Check.isEmpty(whereClause, true))
		{
			finalWhereClause.append(" AND (").append(whereClause).append(")");
		}

		final String tableName = adTableDAO.retrieveTableName(adTableId);

		return new Query(ctx, tableName, finalWhereClause.toString(), ITrx.TRXNAME_None)
				.setParameters(params)
				// buffer size 1 was arbitrarily chosen
				.setOnlyActiveRecords(true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 1)
				.iterate();
	}
}
