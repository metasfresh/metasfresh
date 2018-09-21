package de.metas.picking.api.impl;

import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.util.Check;

public class PickingSlotBL implements IPickingSlotBL
{
	@Override
	public boolean isAvailableForAnyBPartner(final I_M_PickingSlot pickingSlot)
	{
		Check.assumeNotNull(pickingSlot, "pickingSlot not null");
		final int pickingSlotBPartnerId = pickingSlot.getC_BPartner_ID();
		if (pickingSlotBPartnerId <= 0)
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean isAvailableForBPartnerID(final I_M_PickingSlot pickingSlot, final int bpartnerId)
	{
		Check.assumeNotNull(pickingSlot, "pickingSlot not null");

		//
		// General use Picking Slot, accept it right away
		if (isAvailableForAnyBPartner(pickingSlot))
		{
			return true;
		}

		//
		// Check BPartner
		final int pickingSlotBPartnerId = pickingSlot.getC_BPartner_ID();
		// Any BPartner Picking Slot
		if (pickingSlotBPartnerId <= 0)
		{
			// accept any partner
		}
		// Picking slot specific for BP
		else
		{
			if (bpartnerId <= 0)
			{
				// no particular partner was requested, (i.e. M_HU_PI_Item_Product does not have a BP set), accept it
			}
			else if (bpartnerId == pickingSlotBPartnerId)
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
	public boolean isAvailableForBPartnerAndLocation(final I_M_PickingSlot pickingSlot,
			final int bpartnerId,
			final int bpartnerLocationId)
	{
		Check.assumeNotNull(pickingSlot, "pickingSlot not null");

		//
		// General use Picking Slot, accept it right away
		if (isAvailableForAnyBPartner(pickingSlot))
		{
			return true;
		}

		//
		// Check if is available for BPartner
		if (!isAvailableForBPartnerID(pickingSlot, bpartnerId))
		{
			return false;
		}

		//
		// Check BPartner Location
		final int pickingSlotBPartnerLocationId = pickingSlot.getC_BPartner_Location_ID();
		// Any BP Location Picking Slot
		if (pickingSlotBPartnerLocationId <= 0)
		{
			// accept any location
		}
		// Picking slot specific for BP Location
		else
		{
			if (bpartnerLocationId <= 0)
			{
				// no particular location was requested, accept it
			}
			else if (bpartnerLocationId == pickingSlotBPartnerLocationId)
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
