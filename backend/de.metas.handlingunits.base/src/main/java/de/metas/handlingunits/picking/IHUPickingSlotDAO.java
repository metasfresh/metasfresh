package de.metas.handlingunits.picking;

import com.google.common.collect.SetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.ISingletonService;
import org.adempiere.ad.dao.IQueryFilter;

import java.util.Collection;
import java.util.List;

public interface IHUPickingSlotDAO extends ISingletonService
{
	I_M_PickingSlot_HU retrievePickingSlotHU(de.metas.picking.model.I_M_PickingSlot pickingSlot, I_M_HU hu);

	/**
	 * Retrieve {@link I_M_PickingSlot} where given HU is in queue or is the current one.
	 *
	 * @return picking slot or null
	 */
	I_M_PickingSlot retrievePickingSlotForHU(I_M_HU hu);

	List<I_M_PickingSlot_HU> retrieveAllPickingSlotHUs();

	/**
	 * Retrieves all HUs that are assigned to the given <code>pickingSlot</code> via {@link I_M_PickingSlot_HU} (i.e. which are in the queue),<br>
	 * and also the one which is currently open within the picking slot.
	 */
	List<I_M_HU> retrieveAllHUs(de.metas.picking.model.I_M_PickingSlot pickingSlot);

	/**
	 * @return true if there are no {@link I_M_PickingSlot_HU} assignments
	 */
	boolean isEmpty(de.metas.picking.model.I_M_PickingSlot pickingSlot);

	SetMultimap<PickingSlotId, HuId> retrieveAllHUIdsIndexedByPickingSlotId(Collection<? extends de.metas.picking.model.I_M_PickingSlot> pickingSlots);

	/**
	 * Creates an {@link I_M_HU} query filter which will select only those HUs which are currently on a picking slot or are in a picking slot queue.
	 */
	IQueryFilter<I_M_HU> createHUOnPickingSlotQueryFilter(final Object contextProvider);
}
