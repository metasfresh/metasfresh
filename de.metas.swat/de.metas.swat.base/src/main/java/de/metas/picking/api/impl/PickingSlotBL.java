package de.metas.picking.api.impl;

import java.util.Objects;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.model.I_M_PickingSlot;
import lombok.NonNull;

public class PickingSlotBL implements IPickingSlotBL
{
	@Override
	public boolean isAvailableForAnyBPartner(@NonNull final I_M_PickingSlot pickingSlot)
	{
		final int pickingSlotBPartnerId = pickingSlot.getC_BPartner_ID();
		return pickingSlotBPartnerId <= 0;
	}

	@Override
	public boolean isAvailableForBPartnerId(@NonNull final I_M_PickingSlot pickingSlot, final BPartnerId bpartnerId)
	{
		//
		// General use Picking Slot, accept it right away
		if (isAvailableForAnyBPartner(pickingSlot))
		{
			return true;
		}

		//
		// Check BPartner
		final BPartnerId pickingSlotBPartnerId = BPartnerId.ofRepoIdOrNull(pickingSlot.getC_BPartner_ID());
		// Any BPartner Picking Slot
		if (pickingSlotBPartnerId == null)
		{
			// accept any partner
		}
		// Picking slot specific for BP
		else
		{
			if (bpartnerId == null)
			{
				// no particular partner was requested, (i.e. M_HU_PI_Item_Product does not have a BP set), accept it
			}
			else if (bpartnerId.equals(pickingSlotBPartnerId))
			{
				// same BP, accept it
			}
			else
			{
				// not same BP, don't accept it
				return false;
			}
		}

		// If we reach this point, we passed all validation rules
		return true;
	}

	@Override
	public boolean isAvailableForBPartnerAndLocation(
			@NonNull final I_M_PickingSlot pickingSlot,
			final BPartnerId bpartnerId,
			final BPartnerLocationId bpartnerLocationId)
	{
		//
		// General use Picking Slot, accept it right away
		if (isAvailableForAnyBPartner(pickingSlot))
		{
			return true;
		}

		//
		// Check if is available for BPartner
		if (!isAvailableForBPartnerId(pickingSlot, bpartnerId))
		{
			return false;
		}

		//
		// Check BPartner Location
		final BPartnerId pickingSlotBPartnerId = BPartnerId.ofRepoId(pickingSlot.getC_BPartner_ID());
		final BPartnerLocationId pickingSlotBPartnerLocationId = pickingSlotBPartnerId != null
				? BPartnerLocationId.ofRepoIdOrNull(pickingSlotBPartnerId, pickingSlot.getC_BPartner_Location_ID())
				: null;

		// Any BP Location Picking Slot
		if (pickingSlotBPartnerLocationId == null)
		{
			// accept any location
		}
		// Picking slot specific for BP Location
		else
		{
			if (bpartnerLocationId == null)
			{
				// no particular location was requested, accept it
			}
			else if (Objects.equals(bpartnerLocationId, pickingSlotBPartnerLocationId))
			{
				// same BP Location, accept it
			}
			else
			{
				// not same BP Location, don't accept it
				return false;
			}
		}

		// If we reach this point, we passed all validation rules
		return true;
	}
}
