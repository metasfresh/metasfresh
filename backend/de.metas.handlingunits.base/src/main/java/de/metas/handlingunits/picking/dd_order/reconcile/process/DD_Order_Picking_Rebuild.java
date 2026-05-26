package de.metas.handlingunits.picking.dd_order.reconcile.process;

import de.metas.handlingunits.picking.dd_order.reconcile.DDOrderPickingReconcileBL;
import de.metas.process.JavaProcess;
import de.metas.util.Services;

public class DD_Order_Picking_Rebuild extends JavaProcess
{
	@Override
	protected String doIt()
	{
		Services.get(DDOrderPickingReconcileBL.class).rebuildDrift();
		return MSG_OK;
	}
}
