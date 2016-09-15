package de.metas.handlingunits.model.validator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Package;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.IHUPickingSlotBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.util.HUByIdComparator;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;

@Validator(I_M_ShipperTransportation.class)
public class M_ShipperTransportation
{
	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void removeHUsFromPickingSlot(final I_M_ShipperTransportation shipperTransportation)
	{
		final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
		final IHUPackageDAO huPackageDAO = Services.get(IHUPackageDAO.class);
		final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);

		//
		// Retrieve all HUs which we need to remove from their picking slots
		final Set<I_M_HU> husToRemove = new TreeSet<>(HUByIdComparator.instance);
		for (final I_M_ShippingPackage shippingPackage : shipperTransportationDAO.retrieveShippingPackages(shipperTransportation))
		{
			final I_M_Package mpackage = shippingPackage.getM_Package();

			final List<I_M_HU> hus = huPackageDAO.retrieveHUs(mpackage);
			husToRemove.addAll(hus);
		}

		//
		// Remove collected HUs from their picking slots
		for (final I_M_HU hu : husToRemove)
		{
			huPickingSlotBL.removeFromPickingSlotQueueRecursivelly(hu);
		}
	}

	// 08743
	// ported from other branch, but not required yet
	// @DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	// public void addHUsToPickingSlots(final I_M_ShipperTransportation shipperTransportation)
	// {
	// final IHUPackageBL huPackageBL = Services.get(IHUPackageBL.class);
	//
	// for (final I_M_ShippingPackage shippingPackage : Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(shipperTransportation))
	// {
	// final I_M_Package mpackage = shippingPackage.getM_Package();
	// huPackageBL.addHUsToPickingSlots(mpackage);
	// }
	// }
	//
	// @DocValidate(timings = ModelValidator.TIMING_BEFORE_VOID)
	// public void destroyHUPackages(final I_M_ShipperTransportation shipperTransportation)
	// {
	// throw new AdempiereException("Not allowed");
	// }

}
