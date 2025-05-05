package de.metas.ui.web.split_shipment;

import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

public class SplitShipmentView_RemoveRows extends SplitShipmentView_ProcessTemplate
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getRowIdsToDelete().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		getView().deleteRowsByIds(getRowIdsToDelete());
		return MSG_OK;
	}

	public DocumentIdsSelection getRowIdsToDelete()
	{
		return streamSelectedRows()
				.filter(SplitShipmentRow::isDeletable)
				.map(SplitShipmentRow::getId)
				.collect(DocumentIdsSelection.toDocumentIdsSelection());
	}
}
