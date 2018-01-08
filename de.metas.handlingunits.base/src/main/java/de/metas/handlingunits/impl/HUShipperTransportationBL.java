package de.metas.handlingunits.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHULockBL;
import de.metas.handlingunits.IHUPackageBL;
import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromHU;
import de.metas.lock.api.LockOwner;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;

public class HUShipperTransportationBL implements IHUShipperTransportationBL
{
	private static final LockOwner transportationLockOwner = LockOwner.forOwnerName(HUShipperTransportationBL.class.getName());

	@Override
	public List<I_M_Package> addHUsToShipperTransportation(final int shipperTransportationId, final Collection<I_M_HU> hus)
	{
		//
		// Load Shipper transportation document and validate it
		final I_M_ShipperTransportation shipperTransportation = load(shipperTransportationId, I_M_ShipperTransportation.class);
		if (shipperTransportation == null)
		{
			throw new AdempiereException("@NotFound@ @M_ShipperTransportation_ID@");
		}
		// Make sure Shipper Transportation is still open
		if (shipperTransportation.isProcessed())
		{
			throw new AdempiereException("@M_ShipperTransportation_ID@: @Processed@=@Y@");
		}

		final I_M_Shipper shipper = shipperTransportation.getM_Shipper();

		// services
		final IHUPackageBL huPackageBL = Services.get(IHUPackageBL.class);
		final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
		final IShipperTransportationBL shipperTransportationBL = Services.get(IShipperTransportationBL.class);
		final IHULockBL huLockBL = Services.get(IHULockBL.class);
		
		//
		// Iterate HUs and:
		// * create M_Packages
		// * assign M_Packages them to Shipper Transportation document
		final List<I_M_Package> result = new ArrayList<>();
		for (final I_M_HU hu : hus)
		{
			//
			// Skip HUs which are not eligible for adding to shipper transportation
			// (i.e. it's not top level LU)
			if (!isEligibleForAddingToShipperTransportation(hu))
			{
				continue;
			}

			//
			// Create M_Package
			final I_M_Package mpackage = huPackageBL.createM_Package(hu, shipper);
			result.add(mpackage);

			//
			// Add M_Package to Shipper Transportation document
			shipperTransportationBL.createShippingPackage(shipperTransportation, mpackage);

			//
			// Update HU related things
			// NOTE: because most of the methods are getting the trxName from HU, we will set the HU's trxName to our current transaction and then we will restore the trxName at the end.
			final String huTrxNameOld = InterfaceWrapperHelper.getTrxName(hu);
			try
			{
				InterfaceWrapperHelper.setTrxName(hu, ITrx.TRXNAME_ThreadInherited);

				//
				// Mark the top level HU as Locked & save it
				huLockBL.lockAfterCommit(hu, transportationLockOwner);

				//
				// Remove HU from picking slots, if any (07499).
				// As an effect we expect that picking slot to be released (if it's dynamic).
				// NOTE: We need to navigate HU and it's children because it could be that one of it's children is a HU from a picking slot
				// which was later joined to a LU. (08157)
				huPickingSlotBL.removeFromPickingSlotQueueRecursivelly(hu);
			}
			finally
			{
				// Restore HUs transaction
				InterfaceWrapperHelper.setTrxName(hu, huTrxNameOld);
			}
		}
		
		//
		return ImmutableList.copyOf(result);
	}

	@Override
	public boolean isEligibleForAddingToShipperTransportation(final I_M_HU hu)
	{
		// guard against null
		if (hu == null)
		{
			return false;
		}

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Only Top Level HUs can be added to shipper transportation
		//
		// NOTE: the method which is retrieving the HUs to generate shipment from them is getting only the LUs:
		// de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromHU.retrieveCandidates(I_C_Queue_WorkPackage, String)
		if (!handlingUnitsBL.isTopLevel(hu))
		{
			return false;
		}

		return true;
	}

	@Override
	public void generateShipments(final Properties ctx, final IHUQueryBuilder husQueryBuilder)
	{
		Check.assumeNotNull(husQueryBuilder, "husQueryBuilder not null");

		final List<I_M_HU> hus = husQueryBuilder.onlyLocked().list();
		if (hus.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @M_HU_ID@ @Locked@");
		}

		GenerateInOutFromHU.enqueueWorkpackage(hus);
	}

	@Override
	public List<I_M_ShippingPackage> getShippingPackagesForHU(final I_M_HU hu)
	{
		if (hu == null)
		{
			return Collections.emptyList();
		}

		//
		// Services
		final IHUPackageDAO huPackageDAO = Services.get(IHUPackageDAO.class);
		final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);

		final I_M_Package huPackage = huPackageDAO.retrievePackage(hu);
		if (huPackage == null)
		{
			//
			// No packages were made
			return Collections.emptyList();
		}

		int generalShippertTransportationId = -1;
		final List<I_M_ShippingPackage> shippingPackagesMatchingHU = new ArrayList<>();

		final List<I_M_ShippingPackage> shippingPackages = shipperTransportationDAO.retrieveShippingPackages(huPackage);
		for (final I_M_ShippingPackage shippingPackage : shippingPackages)
		{
			if (shippingPackage.isProcessed() || !shippingPackage.isActive())
			{
				//
				// Only active, not processed packages
				continue;
			}

			final int packagePartnerId = shippingPackage.getC_BPartner_ID();
			final int packageLocationId = shippingPackage.getC_BPartner_Location_ID();
			if (hu.getC_BPartner_ID() != packagePartnerId
					|| hu.getC_BPartner_Location_ID() != packageLocationId)
			{
				//
				// Shipper package must match the HU's partner and location
				continue;
			}

			final int shipperTransportationId = shippingPackage.getM_ShipperTransportation_ID();
			if (generalShippertTransportationId < 0)
			{
				generalShippertTransportationId = shipperTransportationId;
			}
			Check.assume(generalShippertTransportationId == shipperTransportationId, "shipper transportations shall all match for any given HU");

			shippingPackagesMatchingHU.add(shippingPackage);
		}
		return shippingPackagesMatchingHU;
	}

	@Override
	public I_M_ShipperTransportation getCommonM_ShipperTransportationOrNull(final Collection<I_M_HU> hus)
	{
		if (hus == null || hus.isEmpty())
		{
			return null;
		}

		final List<I_M_ShippingPackage> shippingPackages = new ArrayList<>();
		for (final I_M_HU hu : hus)
		{
			shippingPackages.addAll(getShippingPackagesForHU(hu));
		}

		if (shippingPackages.isEmpty())
		{
			//
			// We must have shipping packages on this shipment schedule already to retrieve the shipper transportation
			return null;
		}

		//
		// Make sure all of the HUs in this structure have the exact same shipper transportation
		int generalShippertTransportationId = -1;
		for (final I_M_ShippingPackage shippingPackage : shippingPackages)
		{
			final int shipperTransportationId = shippingPackage.getM_ShipperTransportation_ID();
			if (generalShippertTransportationId < 0)
			{
				generalShippertTransportationId = shipperTransportationId;
			}
			Check.assume(generalShippertTransportationId == shipperTransportationId, "shipper transportations shall all match for any given HU");
		}

		final I_M_ShippingPackage firstPackage = shippingPackages.iterator().next();
		final I_M_ShipperTransportation shipperTransportation = firstPackage.getM_ShipperTransportation(); // get the common shipper transportation document of the HUs
		return shipperTransportation;
	}
}
