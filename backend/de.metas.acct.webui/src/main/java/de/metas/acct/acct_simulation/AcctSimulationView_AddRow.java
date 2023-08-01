package de.metas.acct.acct_simulation;

public class AcctSimulationView_AddRow extends AcctSimulationViewBasedAction
{
	@Override
	protected String doIt()
	{
		getView().addNewRow();
		return MSG_OK;
	}
}
