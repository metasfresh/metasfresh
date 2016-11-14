package de.metas.ordercandidate.process;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.process.ProcessExecutionResult.ShowProcessLogs;
import org.compiere.process.SvrProcess;
import org.compiere.util.TrxRunnable2;

import de.metas.adempiere.model.I_C_Order;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;
import de.metas.process.Param;

/**
 * Processes {@link I_C_OLCand}s into {@link I_C_Order}s. Currently, this process is mostly run from <code>AD_Scheduler</code>.
 * <p>
 * The actual work is done by {@link IOLCandBL#process(java.util.Properties, I_C_OLCandProcessor, org.adempiere.util.ILoggable, String)}
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ProcessOLCands extends SvrProcess
{
	@Param(mandatory = true, parameterName = I_C_OLCandProcessor.COLUMNNAME_C_OLCandProcessor_ID)
	private int olCandProcessorId;

	@Override
	protected void prepare()
	{
		// Display process logs only if the process failed.
		// NOTE: we do that because this process is called from window Gear and user shall only see the status line, and no popup shall be displayed.
		setShowProcessLogs(ShowProcessLogs.OnError);
	}

	@Override
	protected String doIt() throws Exception
	{
		//
		// Services
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final IOLCandBL olCandBL = Services.get(IOLCandBL.class);

		Check.assume(olCandProcessorId > 0, "olCandProcessorId > 0");

		final Throwable[] error = new Throwable[1];

		trxManager.run(get_TrxName(), new TrxRunnable2()
		{
			@Override
			public void run(final String trxName)
			{
				final I_C_OLCandProcessor olCandProcessor = InterfaceWrapperHelper.create(getCtx(), olCandProcessorId, I_C_OLCandProcessor.class, trxName);
				olCandBL.process(getCtx(), olCandProcessor, ProcessOLCands.this, trxName);
				error[0] = null;
			}

			@Override
			public boolean doCatch(final Throwable e) throws Exception
			{
				error[0] = e;

				// returning 'true' to roll back the transaction
				return true;
			}

			@Override
			public void doFinally()
			{
				// nothing to do
			}
		});
		if (error[0] != null)
		{
			addLog("@Error@: " + error[0].getMessage());
			addLog("@Rollback@");

			// returning success, because "error" would roll back the complete process-trx, including our log messages
			return error[0].getMessage();
		}
		return "@Success@";
	}
}
