package de.metas.handlingunits.picking.dd_order.reconcile.process;

import de.metas.handlingunits.picking.dd_order.reconcile.DDOrderPickingReconcileBL;
import de.metas.process.JavaProcess;
import org.compiere.SpringContextHolder;

public class DD_Order_Picking_Rebuild extends JavaProcess
{
	@Override
	protected String doIt()
	{
		SpringContextHolder.instance.getBean(DDOrderPickingReconcileBL.class).rebuildDrift();
		return MSG_OK;
	}
}
