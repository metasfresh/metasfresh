package de.metas.acct.acct_simulation;

class AcctSimulationView_Save extends AcctSimulationViewBasedAction
{
	@Override
	protected String doIt()
	{
		getView().save();
		return MSG_OK;
	}
}
