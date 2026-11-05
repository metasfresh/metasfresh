package de.metas.distribution.ddorder.replenishment.process;

import de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishmentService;
import de.metas.process.JavaProcess;
import org.compiere.SpringContextHolder;

public class DD_Order_Picking_Rebuild extends JavaProcess
{
	private final DDOrderPickingReplenishmentService replenishmentService = SpringContextHolder.instance.getBean(DDOrderPickingReplenishmentService.class);

	@Override
	protected String doIt()
	{
		replenishmentService.rebuildDrift();
		return MSG_OK;
	}
}
