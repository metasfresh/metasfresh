package de.metas.ui.web.split_shipment;

import de.metas.process.ProcessPreconditionsResolution;

public class SplitShipmentView_RemoveRows extends SplitShipmentView_ProcessTemplate
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		getView().deleteRowsByIds(getSelectedRowIds());
		return MSG_OK;
	}
}
