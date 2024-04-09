package de.metas.ui.web.split_shipment;

import de.metas.process.IProcessPrecondition;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;

abstract class SplitShipmentView_ProcessTemplate extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	protected final SplitShipmentView getView()
	{
		return SplitShipmentView.cast(super.getView());
	}
}
