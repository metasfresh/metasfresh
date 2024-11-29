package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

public class OIView_Select extends OIViewBasedProcess
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final long countToBeSelected = streamSelectedRows()
				.filter(row -> !row.isSelected())
				.count();
		if (countToBeSelected <= 0)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final DocumentIdsSelection rowIds = streamSelectedRows()
				.filter(row -> !row.isSelected())
				.map(OIRow::getId)
				.collect(DocumentIdsSelection.toDocumentIdsSelection());

		final OIView view = getView();
		view.markRowsAsSelected(rowIds);
		return MSG_OK;
	}
}
