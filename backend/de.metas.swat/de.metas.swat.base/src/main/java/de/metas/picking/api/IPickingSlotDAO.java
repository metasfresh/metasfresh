package de.metas.picking.api;

import de.metas.picking.model.I_M_PickingSlot;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public interface IPickingSlotDAO extends ISingletonService
{
	PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull PickingSlotId pickingSlotId);

	Optional<PickingSlotIdAndCaption> getPickingSlotIdAndCaptionByCode(@NonNull String pickingSlotCode);

	I_M_PickingSlot getById(PickingSlotId pickingSlotId);

	<T extends I_M_PickingSlot> T getById(PickingSlotId pickingSlotId, Class<T> modelType);

	/**
	 * @return all picking slots for current tenant/client.
	 */
	List<I_M_PickingSlot> retrievePickingSlots(Properties ctx, String trxName);

	/**
	 * Retrieve all {@link I_M_PickingSlot}s and filter them according to the given {@code query}.<br>
	 */
	List<I_M_PickingSlot> retrievePickingSlots(PickingSlotQuery query);

	Set<PickingSlotId> retrievePickingSlotIds(PickingSlotQuery query);

	Set<PickingSlotIdAndCaption> retrievePickingSlotIdAndCaptions(@NonNull PickingSlotQuery query);

	void save(@NonNull I_M_PickingSlot slot);
}
