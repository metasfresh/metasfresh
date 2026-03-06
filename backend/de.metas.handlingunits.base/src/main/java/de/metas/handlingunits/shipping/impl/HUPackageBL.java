package de.metas.handlingunits.shipping.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingMaterialId;
import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.shipping.CreatePackageForHURequest;
import de.metas.handlingunits.shipping.IHUPackageBL;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.organization.OrgId;
import de.metas.product.PackageDimensions;
import de.metas.product.Product;
import de.metas.product.ProductRepository;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.mpackage.Package;
import de.metas.shipping.mpackage.PackageId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
	private final static AdMessageKey MSG_SELF_PACKED_PRODUCT_WITH_NO_DEFINED_SIZES = AdMessageKey.of("SelfPackedProductWithNoDefinedSizes");
	private final IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	// services
	private final IHUPackageDAO huPackageDAO = Services.get(IHUPackageDAO.class);
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	private final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

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
	public void destroyHUPackages(@NonNull final Set<HuId> huIds)
	{
		final List<I_M_Package_HU> packageHus = huPackageDAO.retrievePackageHUs(huIds);
		for (final I_M_Package_HU packageHu : packageHus)
		{
			delete(packageHu);
		}
	}

	@Override
	public List<PackageId> retrievePackageIds(final HuId huId)
	{
		return huPackageDAO.retrievePackageIds(huId);
	}

	@Override
	public I_M_Package createM_Package(@NonNull final CreatePackageForHURequest request)
	{
		final I_M_HU hu = request.getHu();
		Check.errorIf(hu.getC_BPartner_ID() <= 0, HUException.class, "M_HU {} has C_BPartner_ID <= 0", hu);
		Check.errorIf(hu.getC_BPartner_Location_ID() <= 0, HUException.class, "M_HU {} has C_BPartner_Location_ID <= 0", hu);

		final ShipperId shipperId = Check.assumeNotNull(request.getShipperId(), HUException.class, "Parameter shipperId is not null");

		final I_M_Package mpackage = newInstance(I_M_Package.class);
		mpackage.setM_Shipper_ID(shipperId.getRepoId());
		mpackage.setShipDate(null);
		mpackage.setC_BPartner_ID(hu.getC_BPartner_ID());
		mpackage.setC_BPartner_Location_ID(hu.getC_BPartner_Location_ID());

		getShipmentForHU(hu).ifPresent(inOut -> updateFromInOut(mpackage, inOut));

		if (request.getWeightInKg() != null)
		{
			mpackage.setPackageWeight(request.getWeightInKg());
		}

		final PackageDimensions packageDimensions = getPackageDimensions(hu);
		mpackage.setLengthInCm(packageDimensions.getLengthInCM());
		mpackage.setWidthInCm(packageDimensions.getWidthInCM());
		mpackage.setHeightInCm(packageDimensions.getHeightInCM());

		save(mpackage);

		final I_M_Package_HU mpackageHU = newInstance(I_M_Package_HU.class, mpackage);
		mpackageHU.setAD_Org_ID(mpackage.getAD_Org_ID());
		mpackageHU.setM_Package(mpackage);
		mpackageHU.setM_HU(hu);
		save(mpackageHU);

		return mpackage;
	}

	@Override
	public void assignPackageToHuId(@NonNull final Package aPackage, @NonNull final HuId huId)
	{
		final I_M_Package_HU mpackageHU = newInstance(I_M_Package_HU.class);
		mpackageHU.setAD_Org_ID(OrgId.toRepoId(aPackage.getOrgId()));
		mpackageHU.setM_Package_ID(PackageId.toRepoId(aPackage.getId()));
		mpackageHU.setM_HU_ID(HuId.toRepoId(huId));
		save(mpackageHU);
	}

	private static void updateFromInOut(@NonNull final I_M_Package mpackage, @NonNull final I_M_InOut inOut)
	{
		mpackage.setM_InOut_ID(inOut.getM_InOut_ID());
		mpackage.setPOReference(inOut.getPOReference());
	}

	@Override
	public void assignShipmentToPackages(final I_M_HU hu, final I_M_InOut inout, final String trxName)
	{
		Check.assumeNotNull(hu, "hu not null");
		Check.assumeNotNull(inout, "inout not null");

		// Make sure our HU is eligible for shipper transportation.
		// We do this check and we throw exception because it could be an internal development error.
		if (!isEligibleForAddingToShipperTransportation(hu))
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
			updateFromInOut(mpackage, inout);
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
			mpackage.setPOReference(null);
			mpackage.setProcessed(false);
			save(mpackage);
		}
	}

	private Optional<I_M_InOut> getShipmentForHU(@NonNull final I_M_HU hu)
	{
		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList = huShipmentScheduleDAO.retrieveSchedsQtyPickedForHU(hu);

		if (qtyPickedList == null || qtyPickedList.isEmpty())
		{
			return Optional.empty();
		}

		final Set<InOutLineId> shipmentLineIds = qtyPickedList.stream()
				.map(de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked::getM_InOutLine_ID)
				.map(InOutLineId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		if (shipmentLineIds.isEmpty())
		{
			return Optional.empty();
		}

		final Map<InOutLineId, I_M_InOut> shipmentByLineId = inOutDAO.retrieveInOutByLineIds(shipmentLineIds);

		final Set<I_M_InOut> inOutIds = ImmutableSet.copyOf(shipmentByLineId.values());

		if (inOutIds.size() != 1)
		{
			return Optional.empty();
		}

		return Optional.of(inOutIds.iterator().next());
	}

	@Override
	public boolean isEligibleForAddingToShipperTransportation(@NonNull final I_M_HU hu)
	{
		//
		// Only Top Level HUs can be added to shipper transportation
		//
		// NOTE: the method which is retrieving the HUs to generate shipment from them is getting only the LUs:
		// de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromHU.retrieveCandidates(I_C_Queue_WorkPackage, String)
		return handlingUnitsBL.isTopLevel(hu);
	}

	@Override
	public @NonNull PackageDimensions getPackageDimensions(@NonNull final I_M_HU hu)
	{
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		final Set<HuPackingMaterialId> packingMaterialIds = handlingUnitsBL.getHUPackingMaterialIds(huId);

		if (!packingMaterialIds.isEmpty())
		{
			// this needs to blow up if multiple packing materials are found.
			final I_M_HU_PackingMaterial packingMaterial = packingMaterialDAO.getById(CollectionUtils.singleElement(packingMaterialIds));
			final UomId toUomId = uomDAO.getUomIdByX12DE355(X12DE355.CENTIMETRE);
			return packingMaterialDAO.retrievePackageDimensions(packingMaterial, toUomId);
		}
		else
		{
			//Loaded here to avoid recursion
			final ProductRepository productRepository = SpringContextHolder.instance.getBean(ProductRepository.class);

			final List<IHUProductStorage> productStorages = handlingUnitsBL.getStorageFactory().getProductStorages(hu);
			if (productStorages.size() > 1)
			{
				//Multiple products stored in HU, there's no chance we can infer the dimensions of the package.
				return PackageDimensions.UNSPECIFIED;
			}
			final IHUProductStorage singleHUProductStorage = productStorages.iterator().next();
			final Product product = productRepository.getById(singleHUProductStorage.getProductId());
			final PackageDimensions dimensions = product.getPackageDimensions();
			if (!product.isSelfPacked())
			{
				return PackageDimensions.UNSPECIFIED;
			}
			if (dimensions.isUnspecified())
			{
				throw new AdempiereException(MSG_SELF_PACKED_PRODUCT_WITH_NO_DEFINED_SIZES, product.getValue());
			}
			final Quantity qtyInStockingUOM = singleHUProductStorage.getQtyInStockingUOM();
			return PackageDimensions.ofProductDimensionsAndQty(dimensions, qtyInStockingUOM);
		}
	}
}
