package de.metas.picking.api.impl;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.Check;

import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.model.I_M_PickingSlot;

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
