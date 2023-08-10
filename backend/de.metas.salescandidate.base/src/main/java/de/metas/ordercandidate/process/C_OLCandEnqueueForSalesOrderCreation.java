package de.metas.ordercandidate.process;

import de.metas.adempiere.model.I_C_Order;
import de.metas.ordercandidate.api.async.C_OLCandToOrderEnqueuer;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult.ShowProcessLogs;
import de.metas.util.Check;
import org.compiere.SpringContextHolder;

/**
 * Processes {@link I_C_OLCand}s into {@link I_C_Order}s. Currently, this process is manually triggered from AD_Window=540095
 * <p>
 * The actual work is done by {@link de.metas.ordercandidate.api.OLCandsProcessorExecutor#process()}
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class C_OLCandEnqueueForSalesOrderCreation extends JavaProcess
{
	public static final String PARAM_C_OLCandProcessor_ID = I_C_OLCandProcessor.COLUMNNAME_C_OLCandProcessor_ID;
	@Param(mandatory = true, parameterName = PARAM_C_OLCandProcessor_ID)
	private int olCandProcessorId;

	@Override
	protected void prepare()
	{
		// Display process logs only if the process failed.
		// NOTE: we do that because this process is called from window Gear and user shall only see the status line, and no popup shall be displayed.

		// gh #755 new note: this process is run manually from gear only rarely. So an (admin-) user who runs this should see what went on.
		// particularly if there were problems (and this process would still return "OK" in that case).
		setShowProcessLogs(ShowProcessLogs.Always);
	}

	@Override
	protected String doIt() throws Exception
	{
		final C_OLCandToOrderEnqueuer olCandToOrderEnqueuer = SpringContextHolder.instance.getBean(C_OLCandToOrderEnqueuer.class);

		Check.assume(olCandProcessorId > 0, "olCandProcessorId > 0");

		olCandToOrderEnqueuer.enqueue(olCandProcessorId, null);
		addLog("Created workpackage");

		return MSG_OK;
	}
}
