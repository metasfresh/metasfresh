package de.metas.process;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

@Process(requiresCurrentRecordWhenCalledFromGear = false)
public class ExecuteUpdateSQL extends SvrProcess
{
	private String sql;

	@Override
	protected void prepare()
	{
		// the rawSql will be transformed into a one-liner, so it's important to make sure that it will work even without line breaks
		final String rawSql = getProcessInfo()
				.getSQLStatement()
				.orElseThrow(() -> new AdempiereException("@FillMandatory@ @SQLStatement@"));
		sql = rawSql
				.replaceAll("--.*[\r\n\t]", "") // remove one-line-comments (comments within /* and */ are OK)
				.replaceAll("[\r\n\t]", " "); // replace line-breaks with spaces
	}

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
}
