package de.metas.acct.acct_simulation;

import de.metas.process.ProcessPreconditionsResolution;

class AcctSimulationView_UpdateSimulation extends AcctSimulationViewBasedAction
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return acceptIfViewEditable();
	}

	@Override
	protected String doIt()
	{
		getView().updateSimulation();
		return MSG_OK;
	}
}
