package de.metas.acct.acct_simulation;

import de.metas.process.ProcessPreconditionsResolution;

class AcctSimulationView_AddRow extends AcctSimulationViewBasedAction
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return acceptIfViewEditable();
	}

	@Override
	protected String doIt()
	{
		getView().addNewRow();
		return MSG_OK;
	}
}
