package de.metas.handlingunits.picking.dd_order.reconcile.process;

import de.metas.handlingunits.picking.dd_order.reconcile.DDOrderPickingReconcileService;
import de.metas.process.JavaProcess;
import org.compiere.SpringContextHolder;

public class DD_Order_Picking_Rebuild extends JavaProcess
{
	private final DDOrderPickingReconcileService reconcileService = SpringContextHolder.instance.getBean(DDOrderPickingReconcileService.class);

	@Override
	protected String doIt()
	{
		reconcileService.rebuildDrift();
		return MSG_OK;
	}
}
