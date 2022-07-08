package de.metas.process;

import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import com.google.common.base.Stopwatch;
import org.compiere.util.SQLUpdateResult;
import org.compiere.util.SQLValueStringResult;

@Process(requiresCurrentRecordWhenCalledFromGear = false)
public class ExecuteUpdateSQL extends JavaProcess
{
	@Override
	protected String doIt()
	{
		final String sqlParsed = StringExpressionCompiler.instance
				.compile(getSql())
				.evaluate(getEvalContext(), OnVariableNotFound.Fail);

		final Stopwatch stopwatch = Stopwatch.createStarted();

		final String msg;
		final List<String> warningMessages;
		addLog("Executing: " + sqlParsed);
		if (!sqlParsed.trim().toUpperCase().startsWith("SELECT"))
		{
			final SQLUpdateResult sqlUpdateResult = DB.executeUpdateWithWarningEx(sqlParsed, ITrx.TRXNAME_ThreadInherited);
			stopwatch.stop();

			msg = "Result: " + sqlUpdateResult.getReturnedValue() + "; Runtime: " + stopwatch;
			warningMessages = sqlUpdateResult.getWarningMessages();
		}
		else
		{
			// assuming that it is a select
			final SQLValueStringResult sqlValueStringResult = DB.getSQLValueStringWithWarningEx(get_TrxName(), sqlParsed);
			stopwatch.stop();

			msg = "Runtime: " + stopwatch;
			warningMessages = sqlValueStringResult.getWarningMessages();
		}

		final boolean isLogWarning = getProcessInfo().isLogWarning();
		if (isLogWarning)
		{
			addLog(warningMessages, msg);
		}
		else
		{
			addLog(msg);
		}
		return "@Success@: " + msg;
	}

	private String getSql()
	{
		// the rawSql will be transformed into a one-liner, so it's important to make sure that it will work even without line breaks
		final String rawSql = getProcessInfo()
				.getSQLStatement()
				.orElseThrow(() -> new AdempiereException("@FillMandatory@ @SQLStatement@"));
		return rawSql
				.replaceAll("--.*[\r\n\t]", "") // remove one-line-comments (comments within /* and */ are OK)
				.replaceAll("[\r\n\t]", " "); // replace line-breaks with spaces
	}

	private Evaluatee getEvalContext()
	{
		final List<Evaluatee> contexts = new ArrayList<>();

		//
		// 1: Add process parameters
		contexts.add(Evaluatees.ofRangeAwareParams(getParameterAsIParams()));

		//
		// 2: underlying record
		final String recordTableName = getTableName();
		final int recordId = getRecord_ID();
		if (recordTableName != null && recordId > 0)
		{
			final TableRecordReference recordRef = TableRecordReference.of(recordTableName, recordId);
			final Evaluatee evalCtx = Evaluatees.ofTableRecordReference(recordRef);
			if (evalCtx != null)
			{
				contexts.add(evalCtx);
			}
		}

		//
		// 3: global context
		contexts.add(Evaluatees.ofCtx(getCtx()));

		return Evaluatees.compose(contexts);
	}
}
