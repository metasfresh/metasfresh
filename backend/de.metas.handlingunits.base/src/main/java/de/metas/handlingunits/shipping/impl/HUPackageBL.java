package de.metas.handlingunits.shipping.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUPackageDAO;
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
import de.metas.handlingunits.shipping.IHUShipperTransportationBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.mpackage.Package;
import de.metas.shipping.mpackage.PackageId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.ArrayList;
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
	// services
	private final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
	private final IHUPackageDAO huPackageDAO = Services.get(IHUPackageDAO.class);
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	private final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

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
	public PackageDimensions retrievePackageDimensions(final @NonNull PackageId packageId, final @NonNull UomId toUomId)
	{
		final I_M_HU_PackingMaterial packingMaterial = packingMaterialDAO.retrievePackingMaterialOrNull(packageId);
		if (packingMaterial != null)
		{
			return packingMaterialDAO.retrievePackageDimensions(packingMaterial, toUomId);
		}
		final Set<I_M_Product> productsInPackage = handlingUnitsDAO.getProductsInPackage(packageId);

		if (productsInPackage.size() == 1)
		{
			final I_M_Product product = productsInPackage.iterator().next();
			if (!product.isSelfPacked())
			{
				throw new AdempiereException("Cannot calculate package dimensions for package " + packageId + " because it contains a product which is not self-packed.");
			}
			final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
			final Quantity totalQuantity = handlingUnitsDAO.getTotalQtyOfProductInPackage(packageId, productId);
			Check.assumeEquals(totalQuantity.getUomId(), UomId.ofRepoId(product.getC_UOM_ID()), "totalQtyInProductUOM's UOM shall be the same as product's UOM");
			return createPackageDimensions(product, totalQuantity);
		}
		else
		{
			throw new AdempiereException("Cannot calculate package dimensions for package " + packageId + " because it contains more than one product.");
		}
	}

	/**
	 * This method assumes the products don't have a dedicated packing material, and instead are stacked upon each other, on its smallest dimension (Like books in a library).
	 * It is always sorting dimensions to try to maintain the size as small as possible.
	 */
	private PackageDimensions createPackageDimensions(final I_M_Product product, final Quantity quantityToDeliver)
	{
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		final UomId productUomId = UomId.ofRepoId(product.getC_UOM_ID());

		final BigDecimal height = uomConversionBL.convertQty(productId, BigDecimal.valueOf(product.getHeightInCm()), productUomId, quantityToDeliver.getUomId());
		final BigDecimal length = uomConversionBL.convertQty(productId, BigDecimal.valueOf(product.getLengthInCm()), productUomId, quantityToDeliver.getUomId());
		final BigDecimal width = uomConversionBL.convertQty(productId, BigDecimal.valueOf(product.getHeightInCm()), productUomId, quantityToDeliver.getUomId());

		final List<BigDecimal> dimensions = new ArrayList<>();
		dimensions.add(length);
		dimensions.add(width);
		dimensions.add(height);
		dimensions.sort(BigDecimal::compareTo);

		return PackageDimensions.builder()
				.lengthInCM(dimensions.get(0).multiply(quantityToDeliver.toBigDecimal()).intValue())
				.widthInCM(dimensions.get(2).intValue())
				.heightInCM(dimensions.get(1).intValue())
				.build();
	}

}
