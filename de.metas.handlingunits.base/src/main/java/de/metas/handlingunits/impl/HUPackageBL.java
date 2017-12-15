package de.metas.handlingunits.impl;

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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Shipper;

import de.metas.handlingunits.IHUPackageBL;
import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.interfaces.I_M_Package;
import de.metas.shipping.model.I_M_ShippingPackage;

public class HUPackageBL implements IHUPackageBL
{
	@Override
	public void destroyHUPackage(final org.compiere.model.I_M_Package mpackage)
	{
		final List<I_M_Package_HU> mpackageHUs = Services.get(IHUPackageDAO.class).retrievePackageHUs(mpackage);

		//
		// If it's a package build from a a collection of HUs, remove the assignment and inactivate the package
		if (!mpackageHUs.isEmpty())
		{
			for (final I_M_Package_HU mpackageHU : mpackageHUs)
			{
				InterfaceWrapperHelper.delete(mpackageHU);
			}

			// Inactive the package (mark as deleted)
			mpackage.setIsActive(false);
			InterfaceWrapperHelper.save(mpackage);
		}
	}

	@Override
	public I_M_Package createM_Package(final I_M_HU hu, final I_M_Shipper shipper)
	{
		Check.assumeNotNull(hu, HUException.class, "hu not null");
		Check.assumeNotNull(shipper, HUException.class, "shipper not null");

		Check.errorIf(hu.getC_BPartner_ID() <= 0, HUException.class, "M_HU {} has C_BPartner_ID <= 0", hu);
		Check.errorIf(hu.getC_BPartner_Location_ID() <= 0, HUException.class, "M_HU {} has C_BPartner_Location_ID <= 0", hu);

		final I_M_Package mpackage = InterfaceWrapperHelper.newInstance(I_M_Package.class);
		mpackage.setM_Shipper_ID(shipper.getM_Shipper_ID());
		mpackage.setShipDate(null);
		mpackage.setC_BPartner_ID(hu.getC_BPartner_ID());
		mpackage.setC_BPartner_Location_ID(hu.getC_BPartner_Location_ID());
		InterfaceWrapperHelper.save(mpackage);

		final I_M_Package_HU mpackageHU = InterfaceWrapperHelper.newInstance(I_M_Package_HU.class, mpackage);
		mpackageHU.setAD_Org_ID(mpackage.getAD_Org_ID());
		mpackageHU.setM_Package(mpackage);
		mpackageHU.setM_HU(hu);
		InterfaceWrapperHelper.save(mpackageHU);

		return mpackage;
	}

	@Override
	public void assignShipmentToPackages(final I_M_HU hu, final I_M_InOut inout, final String trxName)
	{
		Check.assumeNotNull(hu, "hu not null");
		Check.assumeNotNull(inout, "inout not null");

		// services
		final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
		final IHUPackageDAO huPackageDAO = Services.get(IHUPackageDAO.class);
		final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);

		// Make sure our HU is eligible for shipper transportation.
		// We do this check and we throw exception because it could be an internal development error.
		if (!huShipperTransportationBL.isEligibleForAddingToShipperTransportation(hu))
		{
			Check.errorIf(true, HUException.class,
					"Internal error: The HU used to search the M_Package is not eligible for shipper transportation." + "\n @M_InOut_ID@: {}", hu);
		}

		final List<I_M_Package> mpackages = huPackageDAO.retrievePackages(hu, trxName);
		for (final I_M_Package mpackage : mpackages)
		{
			// Skip M_Packages which were already delivered
			if (mpackage.getM_InOut_ID() > 0)
			{
				// This shall not happen, but skip it for now
				continue;
			}

			//
			// Update M_Package
			mpackage.setM_InOut_ID(inout.getM_InOut_ID());
			mpackage.setProcessed(true);
			InterfaceWrapperHelper.save(mpackage);

			//
			// Update Shipping Packages (i.e. the link between M_Package and M_ShipperTransportation)
			final List<I_M_ShippingPackage> shippingPackages = shipperTransportationDAO.retrieveShippingPackages(mpackage);
			for (final I_M_ShippingPackage shippingPackage : shippingPackages)
			{
				// Skip Shipping packages which were already delivered
				if (shippingPackage.getM_InOut_ID() > 0)
				{
					// shall not happen
					continue;
				}
				shippingPackage.setM_InOut_ID(inout.getM_InOut_ID());
				InterfaceWrapperHelper.save(shippingPackage);
			}
		}
	}

	@Override
	public void unassignShipmentFromPackages(final I_M_InOut shipment)
	{
		final IHUPackageDAO huPackageDAO = Services.get(IHUPackageDAO.class);
		final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);

		final int inoutId = shipment.getM_InOut_ID();
		final List<I_M_Package> mpackages = huPackageDAO.retrievePackagesForShipment(shipment);
		for (final I_M_Package mpackage : mpackages)
		{
			//
			// Update Shipping Packages (i.e. the link between M_Package and M_ShipperTransportation)
			final List<I_M_ShippingPackage> shippingPackages = shipperTransportationDAO.retrieveShippingPackages(mpackage);
			for (final I_M_ShippingPackage shippingPackage : shippingPackages)
			{
				// Skip Shipping packages which are not about our shipment
				// shall not happen, but better prevent it
				if (shippingPackage.getM_InOut_ID() != inoutId)
				{
					continue;
				}

				// Make sure the shipping package is not processed
				if (shippingPackage.isProcessed())
				{
					throw new HUException("@M_ShipperTransportation_ID@ @Processed@=@Y@: " + shippingPackage.getM_ShipperTransportation());
				}

				shippingPackage.setM_InOut_ID(-1);
				InterfaceWrapperHelper.save(shippingPackage);
			}

			//
			// Update M_Package
			mpackage.setM_InOut_ID(-1);
			mpackage.setProcessed(false);
			InterfaceWrapperHelper.save(mpackage);
		}
	}
}
