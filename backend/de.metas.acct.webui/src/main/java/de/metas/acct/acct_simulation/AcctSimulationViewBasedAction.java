package de.metas.acct.acct_simulation;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

abstract class AcctSimulationViewBasedAction extends ViewBasedProcessTemplate implements IProcessPrecondition
{

	@Override
	protected AcctSimulationView getView()
	{
		return AcctSimulationView.cast(super.getView());
	}

	protected final ProcessPreconditionsResolution acceptIfViewEditable()
	{
		if (getView().isReadonly())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("View is readonly");
		}
		else
		{
			return ProcessPreconditionsResolution.accept();
		}
	}

	protected final ProcessPreconditionsResolution acceptIfHasSelectedRows()
	{
		final DocumentIdsSelection rowIds = getSelectedRowIds();
		if (rowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}
		else
		{
			return ProcessPreconditionsResolution.accept();
		}
	}

}
