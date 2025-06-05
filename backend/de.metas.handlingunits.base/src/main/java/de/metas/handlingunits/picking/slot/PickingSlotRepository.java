package de.metas.handlingunits.picking.slot;

import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class PickingSlotRepository
{
	@NonNull private final IPickingSlotDAO pickingSlotDAO = Services.get(IPickingSlotDAO.class);

	public I_M_PickingSlot getById(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotDAO.getById(pickingSlotId, I_M_PickingSlot.class);
	}

	public List<I_M_PickingSlot> getByIds(@NonNull final Set<PickingSlotId> pickingSlotIds)
	{
		return pickingSlotDAO.getByIds(pickingSlotIds, I_M_PickingSlot.class);
	}

	public void save(final @NonNull I_M_PickingSlot pickingSlot)
	{
		pickingSlotDAO.save(pickingSlot);
	}

	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotDAO.getPickingSlotIdAndCaption(pickingSlotId);
	}

	public Set<PickingSlotIdAndCaption> getPickingSlotIdAndCaptions(@NonNull final Set<PickingSlotId> pickingSlotIds)
	{
		return pickingSlotDAO.getPickingSlotIdAndCaptions(pickingSlotIds);
	}

}
