package de.metas.acct.acct_simulation;

import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

class AcctSimulationView_RemoveRows extends AcctSimulationViewBasedAction
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection rowIds = getSelectedRowIds();
		if (rowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		getView().removeRowsById(getSelectedRowIds());
		return MSG_OK;
	}
}
