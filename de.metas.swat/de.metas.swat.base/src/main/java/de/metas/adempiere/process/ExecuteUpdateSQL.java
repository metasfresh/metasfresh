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


import org.adempiere.util.CustomColNames;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.MProcess;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

public class ExecuteUpdateSQL extends SvrProcess
{

	private String sql;

	@Override
	protected String doIt() throws Exception
	{
		final long startTime = SystemTime.millis();

		final String msg;
		addLog("Executing: " + sql);
		if (sql.trim().toUpperCase().startsWith("UPDATE"))
		{
			final int no = DB.executeUpdateEx(sql, get_TrxName());
			msg = "Result: " + no + "; Runtime: " + TimeUtil.formatElapsed(System.currentTimeMillis() - startTime);
		}
		else
		{
			// assuming that it is a select
			DB.getSQLValueStringEx(get_TrxName(), sql);
			msg = "Runtime: " + TimeUtil.formatElapsed(System.currentTimeMillis() - startTime);
		}
		addLog(msg);
		return "@Success@: " + msg;
	}

	@Override
	protected void prepare()
	{

		final int adProcessID = getProcessInfo().getAD_Process_ID();
		final MProcess process = MProcess.get(getCtx(), adProcessID);

		// the rawSql will be transformed into a one-liner, so it's important to make sure that it will work even without line breaks
		final String rawSql = process.get_ValueAsString(CustomColNames.AD_Process_SQL_STATEMENT);
		sql = rawSql
				.replaceAll("--.*[\r\n\t]", "") // remove one-line-comments (comments within /* and */ are OK)
				.replaceAll("[\r\n\t]", " "); // replace line-breaks with spaces
	}

}
