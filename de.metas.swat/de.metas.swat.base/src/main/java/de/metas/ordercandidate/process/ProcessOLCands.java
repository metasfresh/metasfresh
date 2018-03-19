package de.metas.ordercandidate.process;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;

import de.metas.adempiere.model.I_C_Order;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCandProcessorDescriptor;
import de.metas.ordercandidate.api.OLCandProcessorRepository;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult.ShowProcessLogs;

/**
 * Processes {@link I_C_OLCand}s into {@link I_C_Order}s. Currently, this process is mostly run from <code>AD_Scheduler</code>.
 * <p>
 * The actual work is done by {@link IOLCandBL#process(java.util.Properties, I_C_OLCandProcessor, org.adempiere.util.ILoggable, String)}
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ProcessOLCands extends JavaProcess
{
	//
	// Services
	private final IOLCandBL olCandBL = Services.get(IOLCandBL.class);
	private final OLCandProcessorRepository olCandProcessorRepo = Adempiere.getBean(OLCandProcessorRepository.class);

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
		Check.assume(olCandProcessorId > 0, "olCandProcessorId > 0");
		final OLCandProcessorDescriptor olCandProcessor = olCandProcessorRepo.getById(olCandProcessorId);

		try
		{
			olCandBL.process(olCandProcessor);
			return MSG_OK;
		}
		catch (final Exception ex)
		{
			addLog("@Error@: " + ex.getLocalizedMessage());
			addLog("@Rollback@");
			throw AdempiereException.wrapIfNeeded(ex);

		}
	}
}
