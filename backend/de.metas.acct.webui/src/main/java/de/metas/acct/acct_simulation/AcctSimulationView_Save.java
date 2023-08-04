package de.metas.acct.acct_simulation;

import de.metas.process.ProcessPreconditionsResolution;

class AcctSimulationView_Save extends AcctSimulationViewBasedAction
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return acceptIfViewEditable();
	}

	@Override
	protected String doIt()
	{
		getView().save();
		getResult().setCloseWebuiModalView(true);

		return MSG_OK;
	}
}
