package de.metas.handlingunits.picking;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.handlingunits.model.I_M_PickingSlot_Trx;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.requests.RetrieveAvailableHUIdsToPickRequest;
import de.metas.i18n.BooleanWithReason;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.ISingletonService;
import lombok.Builder.Default;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Note: Please use this interface in this module instead of {@link IPickingSlotBL}.
 */
public interface IHUPickingSlotBL extends IPickingSlotBL, ISingletonService
{
	/**
	 * Creates a new HU and sets it to the given <code>pickingSlot</code>.
	 * <p>
	 * Note: the method does not set the new HU's <code>C_BPartner_ID</code> and <code>C_BPartner_Location_ID</code>. Setting them is the job of the business logic which associates the HU with the
	 * <code>M_ShipmentSchedule</code> for which we are doing all this.
	 *
	 * @param itemProduct the blueprint to use for the new HU.
	 * @return the result with the created picking slot trx (the trx will have ACTION_Set_Current_HU)
	 */
	IQueueActionResult createCurrentHU(I_M_PickingSlot pickingSlot, I_M_HU_PI_Item_Product itemProduct);

	/**
	 * Adds current picking slot HU to HUs queue.
	 *
	 * @return the result: if the picking slot had a current HU assigned, the result contains a trx to document the HU being closed
	 */
	IQueueActionResult closeCurrentHU(I_M_PickingSlot pickingSlot);

	/**
	 * Adds given Handling Units to picking slot queue.
	 * <p>
	 * NOTEs:
	 * <ul>
	 * <li>It will also change HU's status to {@link X_M_HU#HUSTATUS_Picked}.
	 * <li>the method <b>does not</b> set the HU's <code>C_BPartner_ID</code> and <code>C_BPartner_Lcoation_ID</code>. Setting them is the job of the business logic which associates the HU with the
	 * <code>M_ShipmentSchedule</code> for which we are doing all this.
	 * <li>if any of the given HUs are included in some other HU, they will be taken out and parent HUs will be destroyed if they become empty
	 * </ul>
	 *
	 * @return the results with the created picking slot trx and the picking-slot-hu-assignment that was created or updated
	 */
	IQueueActionResult addToPickingSlotQueue(de.metas.picking.model.I_M_PickingSlot pickingSlot, I_M_HU hu);

	IQueueActionResult addToPickingSlotQueue(PickingSlotId pickingSlotId, HuId huId);

	/**
	 * Removes the given <code>hu</code> from the picking slot queue by deleting its associating {@link I_M_PickingSlot_HU} record.<br>
	 * If the given hu was the slot's current HU, it is unset as current HU as well.<br>
	 * Finally, if the given <code>pickingSlot</code> is dynamic, it also releases the slot from its current BPartner.
	 * <p>
	 * TODO: i think it should check if there queue is *really* empty before releasing form the BPartner.
	 *
	 * @return the result with the created picking slot trx
	 */
	IQueueActionResult removeFromPickingSlotQueue(de.metas.picking.model.I_M_PickingSlot pickingSlot, I_M_HU hu);

	IQueueActionResult removeFromPickingSlotQueue(PickingSlotId pickingSlotId, HuId huId);

	/**
	 * @see #removeFromPickingSlotQueue(de.metas.picking.model.I_M_PickingSlot, I_M_HU).
	 */
	void removeFromPickingSlotQueue(I_M_HU hu);

	/**
	 * Removes the given <code>hu</code> all of it's children (recursively) from any picking slot (current picking slot HU or in picking slot queue).
	 *
	 * @see #removeFromPickingSlotQueue(de.metas.picking.model.I_M_PickingSlot, I_M_HU)
	 */
	void removeFromPickingSlotQueueRecursivelly(I_M_HU hu);

	/**
	 * @return <code>true</code> if the given <code>itemProduct</code> references no <code>C_BPartner</code> or if the referenced BPartner fits with the given <code>pickingSlot</code>.
	 * @see #isAvailableForBPartnerId(de.metas.picking.model.I_M_PickingSlot, BPartnerId)
	 */
	boolean isAvailableForProduct(I_M_PickingSlot pickingSlot, I_M_HU_PI_Item_Product itemProduct);

	/**
	 * Allocate dynamic picking slot to selected partner and location if the picking slot was not already allocated for a partner.
	 * If the picking slot is not dynamic, the method does nothing.
	 * <p>
	 * Note: Even if the given <code>pickingSlot</code> already has a HU assigned to itself, this method does not set that HU's <code>C_BPartner_ID</code> and <code>C_BPartner_Lcoation_ID</code>.
	 * Setting them is the job of the business logic which associates the HU with the <code>M_ShipmentSchedule</code> for which we are doing all this.
	 */
	BooleanWithReason allocatePickingSlotIfPossible(@NonNull PickingSlotAllocateRequest request);

	/**
	 * Release the given dynamic picking slot.<br>
	 * By releasing, we mean "resetting the slot's C_BPartner to <code>null</code>". If the picking slot is not dynamic or not allocated to any partner, the method does nothing.<br>
	 * <b>Important:</b> Picking slot will NOT be released if there still are any {@link I_M_PickingSlot_HU} assignments.
	 */
	void releasePickingSlotIfPossible(I_M_PickingSlot pickingSlot);

	void releasePickingSlotIfPossible(PickingSlotId pickingSlotId);

	void releasePickingSlotsIfPossible(Collection<PickingSlotId> pickingSlotIds);

	/**
	 * Ad-Hoc and simple return type for above methods
	 *
	 * @author ts
	 */
	interface IQueueActionResult
	{
		I_M_PickingSlot_Trx getM_PickingSlot_Trx();

		I_M_PickingSlot_HU getI_M_PickingSlot_HU();
	}

	/**
	 * Search for available (top level) HUs to be picked. Picking in this case means that the whole HU is assigned to a picking slot.<br>
	 * Available HUs are not yet picked and are not yet selected to be source HUs
	 *
	 * @return matching HUs
	 */
	List<I_M_HU> retrieveAvailableHUsToPick(@NonNull PickingHUsQuery query);

	ImmutableList<HuId> retrieveAvailableHUIdsToPick(PickingHUsQuery query);

	ImmutableList<HuId> retrieveAvailableHUIdsToPickForShipmentSchedule(RetrieveAvailableHUIdsToPickRequest retrieveAvailableHUIdsToPickRequest);

	void releasePickingSlotFromJob(@NonNull PickingSlotId pickingSlotId, @NonNull PickingJobId pickingJobId);

	/**
	 * Search for available fine picking source HUs.<br>
	 * Those HUs are referenced by {@link I_M_Source_HU} records and are available<br>
	 * to serve as source HU from which stuff is loaded into the picking-HUs. That means that they may not yet be empty.
	 */
	List<I_M_HU> retrieveAvailableSourceHUs(PickingHUsQuery query);

	/**
	 * @return true, if all enqueued HUs have been removed from the slot , false otherwise
	 */
	boolean clearPickingSlotQueue(PickingSlotId pickingSlotId, boolean removeQueuedHUsFromSlot);

	@lombok.Builder
	@lombok.Value
	class PickingHUsQuery
	{
		/**
		 * If true we shall consider the HU attributes while searching for matching HUs.
		 */
		@Default
		boolean onlyIfAttributesMatchWithShipmentSchedules = true;

		/**
		 * ShipmentSchedules for which the HUs shall be picked. Needed to filter by the HUs' product and location and may therefore not be {@code null}.
		 */
		@NonNull
		@lombok.Singular
		ImmutableList<I_M_ShipmentSchedule> shipmentSchedules;

		/**
		 * {@code true} by default, for backwards compatibility.
		 */
		@Default
		boolean onlyTopLevelHUs = true;

		/**
		 * If {@code true}, then even exclude HUs that are reserved to the given {@code shipmentSchedule}'s order line itself.
		 */
		@Default
		boolean excludeAllReserved = false;
	}
}
