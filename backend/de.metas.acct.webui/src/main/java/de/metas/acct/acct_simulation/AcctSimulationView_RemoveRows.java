package de.metas.acct.acct_simulation;

import de.metas.process.ProcessPreconditionsResolution;

class AcctSimulationView_RemoveRows extends AcctSimulationViewBasedAction
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return acceptIfViewEditable().and(this::acceptIfHasSelectedRows);
	}

	@Override
	protected String doIt()
	{
		getView().removeRowsById(getSelectedRowIds());
		return MSG_OK;
	}
}
