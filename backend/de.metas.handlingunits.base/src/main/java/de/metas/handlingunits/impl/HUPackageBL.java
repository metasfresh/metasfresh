package de.metas.handlingunits.impl;

import de.metas.handlingunits.IHUPackageBL;
import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.Check;
import de.metas.util.Services;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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

public class HUPackageBL implements IHUPackageBL
{
	// services
	private final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
	private final IHUPackageDAO huPackageDAO = Services.get(IHUPackageDAO.class);
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);

	@Override
	public void destroyHUPackage(final org.compiere.model.I_M_Package mpackage)
	{
		final List<I_M_Package_HU> mpackageHUs = huPackageDAO.retrievePackageHUs(mpackage);

		//
		// If it's a package build from a a collection of HUs, remove the assignment and inactivate the package
		if (!mpackageHUs.isEmpty())
		{
			for (final I_M_Package_HU mpackageHU : mpackageHUs)
			{
				delete(mpackageHU);
			}

			// Inactive the package (mark as deleted)
			mpackage.setIsActive(false);
			save(mpackage);
		}
	}

	@Override
	public I_M_Package createM_Package(final I_M_HU hu, final ShipperId shipperId)
	{
		Check.assumeNotNull(hu, HUException.class, "hu not null");
		Check.assumeNotNull(shipperId, HUException.class, "shipper not null");

		Check.errorIf(hu.getC_BPartner_ID() <= 0, HUException.class, "M_HU {} has C_BPartner_ID <= 0", hu);
		Check.errorIf(hu.getC_BPartner_Location_ID() <= 0, HUException.class, "M_HU {} has C_BPartner_Location_ID <= 0", hu);

		final I_M_Package mpackage = newInstance(I_M_Package.class);
		mpackage.setM_Shipper_ID(shipperId.getRepoId());
		mpackage.setShipDate(null);
		mpackage.setC_BPartner_ID(hu.getC_BPartner_ID());
		mpackage.setC_BPartner_Location_ID(hu.getC_BPartner_Location_ID());
		save(mpackage);

		final I_M_Package_HU mpackageHU = newInstance(I_M_Package_HU.class, mpackage);
		mpackageHU.setAD_Org_ID(mpackage.getAD_Org_ID());
		mpackageHU.setM_Package(mpackage);
		mpackageHU.setM_HU(hu);
		save(mpackageHU);

		return mpackage;
	}

	@Override
	public void assignShipmentToPackages(final I_M_HU hu, final I_M_InOut inout, final String trxName)
	{
		Check.assumeNotNull(hu, "hu not null");
		Check.assumeNotNull(inout, "inout not null");

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
			save(mpackage);

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
				save(shippingPackage);
			}
		}
	}

	@Override
	public void unassignShipmentFromPackages(final I_M_InOut shipment)
	{
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
				save(shippingPackage);
			}

			//
			// Update M_Package
			mpackage.setM_InOut_ID(-1);
			mpackage.setProcessed(false);
			save(mpackage);
		}
	}
}
