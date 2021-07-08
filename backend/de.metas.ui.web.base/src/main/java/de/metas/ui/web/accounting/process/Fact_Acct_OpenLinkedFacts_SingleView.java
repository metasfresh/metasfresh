package de.metas.ui.web.accounting.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;

public class Fact_Acct_OpenLinkedFacts_SingleView extends JavaProcess implements IProcessPrecondition
{

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final ProcessExecutionResult result = getResult();
		new Fact_Acct_OpenLinkedFacts_ProcessHelper().openLinkedFactAccounts(getRecord_ID(), result);

		return MSG_OK;
	}

}
