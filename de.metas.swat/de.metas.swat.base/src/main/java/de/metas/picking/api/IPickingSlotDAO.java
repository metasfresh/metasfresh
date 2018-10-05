package de.metas.picking.api;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import de.metas.picking.model.I_M_PickingSlot;
import de.metas.util.ISingletonService;

public interface IPickingSlotDAO extends ISingletonService
{
	I_M_PickingSlot getById(PickingSlotId pickingSlotId);

	<T extends I_M_PickingSlot> T getById(PickingSlotId pickingSlotId, Class<T> modelType);

	/**
	 * Retrieve all picking slots for current tenant/client.
	 * 
	 * @param ctx
	 * @param trxName
	 * @return list of picking slots
	 */
	List<I_M_PickingSlot> retrievePickingSlots(Properties ctx, String trxName);

	/**
	 * Retrieve all {@link I_M_PickingSlot}s and filter them according to the given {@code query}.<br>
	 * The query's bPartner and location properties are forwarded to {@link IPickingSlotBL#isAvailableForBPartnerAndLocation(I_M_PickingSlot, int, int)}.
	 * 
	 * @param query
	 * @return
	 */
	List<I_M_PickingSlot> retrievePickingSlots(PickingSlotQuery query);

	Set<PickingSlotId> retrievePickingSlotIds(PickingSlotQuery query);
}
