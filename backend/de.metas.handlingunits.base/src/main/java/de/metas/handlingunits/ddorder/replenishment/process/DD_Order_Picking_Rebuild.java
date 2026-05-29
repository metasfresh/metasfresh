package de.metas.handlingunits.ddorder.replenishment.process;

import de.metas.handlingunits.ddorder.replenishment.DDOrderPickingReplenishmentService;
import de.metas.process.JavaProcess;
import org.compiere.SpringContextHolder;

public class DD_Order_Picking_Rebuild extends JavaProcess
{
	private final DDOrderPickingReplenishmentService reconcileService = SpringContextHolder.instance.getBean(DDOrderPickingReplenishmentService.class);

	@Override
	protected String doIt()
	{
		reconcileService.rebuildDrift();
		return MSG_OK;
	}
}
