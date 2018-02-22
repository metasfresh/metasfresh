package de.metas.picking.service;

import java.util.Map;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.quantity.Quantity;

public interface IPackingService extends ISingletonService
{
	void removeProductQtyFromHU(Properties ctx, I_M_HU hu, Map<I_M_ShipmentSchedule, Quantity> schedules2qty);

	IPackingContext createPackingContext(Properties ctx);

	/**
	 * From <code>itemToPack</code> take out the <code>qtyToPack</code>, create a new packed item for that qty and send it to <code>itemPackedProcessor</code>.
	 *
	 * @param packingContext
	 * @param itemToPack unpacked item that needs to be packed
	 * @param qtyToPack how much qty we need to take out
	 * @param itemPackedProcessor processor used to process our resulting packed item
	 */
	void packItem(IPackingContext packingContext,
			IFreshPackingItem itemToPack,
			Quantity qtyToPack,
			IPackingHandler itemPackedProcessor);
}
