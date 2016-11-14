package de.metas.adempiere.process;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.trxConstraints.api.IOpenTrxBL;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;

import de.metas.adempiere.service.ISweepTableBL;

public class SweepTable extends SvrProcess
{
	private String p_TableName = null;
	private String p_WhereClause = "1=1";
	private boolean p_IsTest = true;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				// do nothing

			}
			else if (name.equals("TableName"))
			{
				p_TableName = (String)para.getParameter();
			}
			else if (name.equals("WhereClause"))
			{
				p_WhereClause = (String)para.getParameter();
			}
			else if (name.equals("IsTest"))
			{
				p_IsTest = para.getParameterAsBoolean();
			}
			else
			{
				log.debug("Unknown Parameter: " + name);
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		Check.assume(p_WhereClause != null, "WhereClause is not null");

		final long startTime = System.currentTimeMillis();

		final ISweepTableBL sweepTableBL = Services.get(ISweepTableBL.class);

		// clean log data
		db_delete_logs();

		DB.saveConstraints();
		final String trxName = get_TrxName();
		try
		{
			DB.getConstraints().setTrxTimeoutSecs(-1, false);
			// workaround, the API should do
			Services.get(IOpenTrxBL.class).onTimeOutChange(Trx.get(trxName, false));

			// final int targetClientId = 100;
			final int targetClientId = -1;
			final boolean result = sweepTableBL.sweepTable(getCtx(), p_TableName, p_WhereClause, targetClientId, this, trxName);
			addLog("Finished after " + TimeUtil.formatElapsed(System.currentTimeMillis() - startTime) + " (Everything deleted: " + result + ")");

			//
			// Do a second run, just for safety and to check the results
			{
				addLog("--------------- SECOND RUN ------------------------------------------");
				final long startTime2 = System.currentTimeMillis();
				final boolean result2 = sweepTableBL.sweepTable(getCtx(), p_TableName, p_WhereClause, targetClientId, this, trxName);
				addLog("Finished(2) after " + TimeUtil.formatElapsed(System.currentTimeMillis() - startTime2) + " (Everything deleted: " + result2 + ")");
				if (result2 == false)
				{
					// NOTE: because in de.metas.adempiere.service.impl.SweepTableBL.retrieveRecordIds(RuntimeContext,
					// String, String, int)
					// we are setting references to NULL, is absolutely mandatory to make sure that everything was
					// deleted, because else we will leave the database in a inconsistent and irreversible state
					throw new AdempiereException("There is remaining data! ROLLBACK!");
				}
			}

			if (p_IsTest)
			{
				throw new AdempiereException("ROLLBACK!");
			}
			return "Everything could be deleted: " + result;
		}
		finally
		{
			DB.restoreConstraints();
			// workaround, the API should do this automatically
			Services.get(IOpenTrxBL.class).onTimeOutChange(Trx.get(trxName, false));
		}
		
	}

	private int db_delete_logs()
	{
		final String sql = "select db_delete_logs(?, ?)";
		final int adSessionId = Env.getAD_Session_ID(getCtx());
		final int no = DB.getSQLValueEx(get_TrxName(), sql, adSessionId, getAD_PInstance_ID());
		addLog("Deleted log data: " + no + " records");
		return no;
	}
}
