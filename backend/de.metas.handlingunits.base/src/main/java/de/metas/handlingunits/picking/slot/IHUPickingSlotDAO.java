package de.metas.handlingunits.picking.slot;

import com.google.common.collect.SetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IHUPickingSlotDAO extends ISingletonService
{
	I_M_PickingSlot_HU retrievePickingSlotHU(de.metas.picking.model.I_M_PickingSlot pickingSlot, HuId huId);

	/**
	 * Retrieve {@link I_M_PickingSlot} where given HU is in queue or is the current one.
	 *
	 * @return picking slot or null
	 */
	@Nullable I_M_PickingSlot retrievePickingSlotForHU(HuId huId);

	List<I_M_PickingSlot_HU> retrievePickingSlotHUs(@NonNull PickingSlotId pickingSlotId, @NonNull Set<HuId> huIds);

	List<I_M_PickingSlot_HU> retrieveAllPickingSlotHUs();

	List<I_M_PickingSlot_HU> retrievePickingSlotHUs(@NonNull Set<PickingSlotId> pickingSlotIds);

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
