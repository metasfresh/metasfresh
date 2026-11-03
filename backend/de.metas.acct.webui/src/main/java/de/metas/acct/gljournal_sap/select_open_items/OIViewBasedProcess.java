package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.process.IProcessPrecondition;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;

import java.util.stream.Stream;

abstract class OIViewBasedProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	protected OIView getView() {return OIView.cast(super.getView());}

	@Override
	protected Stream<OIRow> streamSelectedRows()
	{
		return super.streamSelectedRows().map(OIRow::cast);
	}
}
