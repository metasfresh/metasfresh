package de.metas.handlingunits.shipping.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.inout.InOutId;
import de.metas.handlingunits.HuPackingMaterialId;
import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
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
import de.metas.product.PackageDimensionCalcMethod;
import de.metas.product.PackageDimensionItem;
import de.metas.product.PackageDimensions;
import de.metas.product.Product;
import de.metas.product.ProductId;
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
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Package;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
	private static final String SYSCONFIG_CHECK_IS_SELF_PACKED = "de.metas.handlingunits.PackageDimensions.CheckIsSelfPacked";

	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
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

		final Optional<I_M_InOut> shipmentForHU = getShipmentForHU(hu);
		shipmentForHU.ifPresent(inOut -> updateFromInOut(mpackage, inOut));

		if (request.getWeightInKg() != null)
		{
			mpackage.setPackageWeight(request.getWeightInKg());
		}

		final PackageDimensions packageDimensions = request.getPackageDimensions() != null
				? request.getPackageDimensions()
				: getPackageDimensions(hu);
		mpackage.setLengthInCm(packageDimensions.getLengthInCM());
		mpackage.setWidthInCm(packageDimensions.getWidthInCM());
		mpackage.setHeightInCm(packageDimensions.getHeightInCM());

		save(mpackage);

		final I_M_Package_HU mpackageHU = newInstance(I_M_Package_HU.class, mpackage);
		mpackageHU.setAD_Org_ID(mpackage.getAD_Org_ID());
		mpackageHU.setM_Package(mpackage);
		mpackageHU.setM_HU(hu);
		save(mpackageHU);

		// When the shipment already exists at package-creation time, record its line(s) now: the other
		// M_InOut-assignment path (assignShipmentToPackages) skips a package that is already linked.
		shipmentForHU.ifPresent(inOut -> createPackageLines(mpackage, inOut));

		return mpackage;
	}

	@Override
	public List<I_M_Package> createM_Packages(@NonNull final CreatePackageForHURequest request)
	{
		final I_M_HU hu = request.getHu();

		// A loose CU (a top-level VIRTUAL HU) ships 1 label per unit: split a single-product, integer-quantity
		// loose HU into N single-unit M_Packages (each linked to the same HU via M_Package_HU). Everything else
		// — a carton (LU/TU), an aggregate HU, multi-product, or a non-integer quantity — yields exactly ONE
		// M_Package (unchanged behaviour). NOTE: gate on isVirtual, NOT on "no packing material": a top-level LU
		// can also have no packing-material row, and must stay one package.
		if (!handlingUnitsBL.isVirtual(hu))
		{
			return ImmutableList.of(createM_Package(request));
		}
		final List<IHUProductStorage> productStorages = handlingUnitsBL.getStorageFactory().getProductStorages(hu);
		if (productStorages.size() != 1)
		{
			return ImmutableList.of(createM_Package(request));
		}
		final IHUProductStorage productStorage = productStorages.get(0);
		final Quantity qty = productStorage.getQtyInStockingUOM();
		final int parcelCount;
		try
		{
			parcelCount = qty.toBigDecimal().intValueExact();
		}
		catch (final ArithmeticException nonIntegerLooseQty)
		{
			return ImmutableList.of(createM_Package(request));
		}
		if (parcelCount <= 1)
		{
			return ImmutableList.of(createM_Package(request));
		}

		// One parcel per unit: split the HU weight evenly across the N identical units, and use the
		// product's SINGLE-unit dimensions when present, else UNSPECIFIED (IsSelfPacked gate is SysConfig-controlled, default off).
		final BigDecimal huWeightInKg = request.getWeightInKg();
		final BigDecimal perUnitWeightInKg = huWeightInKg != null
				? huWeightInKg.divide(BigDecimal.valueOf(parcelCount), 3, RoundingMode.HALF_UP)
				: null;

		final ProductRepository productRepository = SpringContextHolder.instance.getBean(ProductRepository.class);
		final Product product = productRepository.getById(productStorage.getProductId());
		final PackageDimensions singleUnitDimensions = resolveSingleUnitDimensions(product);

		final CreatePackageForHURequest perUnitRequest = request
				.withWeightInKg(perUnitWeightInKg)
				.withPackageDimensions(singleUnitDimensions);

		final ImmutableList.Builder<I_M_Package> packages = ImmutableList.builder();
		for (int i = 0; i < parcelCount; i++)
		{
			packages.add(createM_Package(perUnitRequest));
		}
		return packages.build();
	}

	/**
	 * {@code true} if the {@value #SYSCONFIG_CHECK_IS_SELF_PACKED} SysConfig requires
	 * the self-packed flag for dimension resolution (default {@code false} = flag-independent).
	 */
	private boolean isCheckSelfPacked()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_CHECK_IS_SELF_PACKED, false);
	}

	/**
	 * Single-unit dimensions: each parcel carries the product's named dimensions verbatim
	 * (no qty-based sort/scale). Returns {@link PackageDimensions#UNSPECIFIED} when the product
	 * has no dims. When {@value #SYSCONFIG_CHECK_IS_SELF_PACKED}='Y', also returns
	 * {@link PackageDimensions#UNSPECIFIED} for a non-self-packed product.
	 */
	private PackageDimensions resolveSingleUnitDimensions(@NonNull final Product product)
	{
		if (isCheckSelfPacked() && !product.isSelfPacked())
		{
			return PackageDimensions.UNSPECIFIED;
		}
		// dims already equals UNSPECIFIED when unspecified (value object) — return it directly.
		return product.getPackageDimensions();
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
		mpackage.setAD_User_ID(inOut.getAD_User_ID());
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
			// Record which shipment line(s) this package actually contains (M_PackageLine).
			createPackageLines(mpackage, inout);

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

	/**
	 * Record which shipment line(s) each of {@code mpackage}'s HUs was shipped as, as {@code M_PackageLine}
	 * rows ({@code M_InOutLine_ID} + summed {@code Qty}). This gives the carrier-advise path an exact
	 * package&rarr;shipment-line link, so it resolves a package to the schedules of the lines it actually holds
	 * rather than to every line of the whole {@code M_InOut} (a mixed LU correctly yields one row per line).
	 * <p>
	 * Must run after {@code M_ShipmentSchedule_QtyPicked.M_InOutLine_ID} is set — hence the call from
	 * {@link #assignShipmentToPackages} (via {@code ShipmentScheduleWithHU.setM_InOut}, after
	 * {@code createUpdateShipmentLineAlloc}). Idempotent: clears existing lines first, so a reverse&rarr;re-ship
	 * does not duplicate rows.
	 */
	private void createPackageLines(@NonNull final I_M_Package mpackage, @NonNull final I_M_InOut inout)
	{
		// Idempotency: drop any lines from a previous assignment (e.g. after a reverse&rarr;re-ship).
		huPackageDAO.deletePackageLines(PackageId.ofRepoId(mpackage.getM_Package_ID()));

		// This shipment's line ids, to scope the pick-ledger rows without a per-row relation-load.
		final Set<InOutLineId> shipmentLineIds = inOutDAO.retrieveLines(inout).stream()
				.map(line -> InOutLineId.ofRepoId(line.getM_InOutLine_ID()))
				.collect(ImmutableSet.toImmutableSet());

		// One M_PackageLine per shipment line, from the pick ledger. Every shipment line — including each line an
		// attribute-mixed TU is split into — has its own M_ShipmentSchedule_QtyPicked row
		// (HUShipmentScheduleBL.createCandidatesForQtyPicked splits a whole-TU pick per attribute group), so the
		// ledger is line-complete; group its rows for the package's HUs by M_InOutLine and sum the picked qty.
		final Map<InOutLineId, BigDecimal> qtyByInOutLineId = new LinkedHashMap<>();
		for (final I_M_Package_HU packageHU : huPackageDAO.retrievePackageHUs(mpackage))
		{
			for (final I_M_ShipmentSchedule_QtyPicked qtyPicked : huShipmentScheduleDAO.retrieveSchedsQtyPickedForHU(packageHU.getM_HU()))
			{
				final InOutLineId inOutLineId = InOutLineId.ofRepoIdOrNull(qtyPicked.getM_InOutLine_ID());
				// Scope to THIS shipment's lines (an HU's active picked rows should belong to it, but be defensive).
				if (inOutLineId == null || !shipmentLineIds.contains(inOutLineId))
				{
					continue;
				}
				// BigDecimal (not Quantity): a group is one M_InOutLine → one product → one stock UOM, so there is no
				// UOM to reconcile, and M_PackageLine.Qty is a bare NUMERIC column. Resolving a UOM for a Quantity
				// would require a per-row M_ShipmentSchedule/product relation-traversal we deliberately avoid.
				qtyByInOutLineId.merge(inOutLineId, qtyPicked.getQtyPicked(), BigDecimal::add);
			}
		}

		qtyByInOutLineId.forEach((inOutLineId, qty) -> huPackageDAO.createPackageLine(mpackage, inOutLineId, qty));
	}

	@Override
	public void unassignShipmentFromPackages(final I_M_InOut shipment)
	{
		final InOutId inoutId = InOutId.ofRepoId(shipment.getM_InOut_ID());
		final List<I_M_Package> mpackages = huPackageDAO.retrievePackagesForShipment(inoutId);
		for (final I_M_Package mpackage : mpackages)
		{
			//
			// Update Shipping Packages (i.e. the link between M_Package and M_ShipperTransportation)
			final List<I_M_ShippingPackage> shippingPackages = shipperTransportationDAO.retrieveShippingPackages(mpackage);
			for (final I_M_ShippingPackage shippingPackage : shippingPackages)
			{
				// Skip Shipping packages which are not about our shipment
				// shall not happen, but better prevent it
				if (!InOutId.equals(InOutId.ofRepoIdOrNull(shippingPackage.getM_InOut_ID()), inoutId))
				{
					continue;
				}

				// Make sure the shipping package is not processed
				if (shippingPackage.isProcessed())
				{
					throw new HUException("@M_ShipperTransportation_ID@ @Processed@=@Y@: " + shippingPackage.getM_ShipperTransportation());
				}

				shippingPackage.setM_InOut_ID(InOutId.toRepoId(null));
				save(shippingPackage);
			}

			//
			// Update M_Package
			mpackage.setM_InOut_ID(-1);
			mpackage.setPOReference(null);
			mpackage.setProcessed(false);
			save(mpackage);

			// Drop the package's lines: they point at the now-void M_InOutLines. They are rebuilt from the current
			// pick ledger when the package is re-assigned to a shipment (createPackageLines).
			huPackageDAO.deletePackageLines(PackageId.ofRepoId(mpackage.getM_Package_ID()));
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
			// Loaded here to avoid recursion
			final ProductRepository productRepository = SpringContextHolder.instance.getBean(ProductRepository.class);

			final List<IHUProductStorage> productStorages = handlingUnitsBL.getStorageFactory().getProductStorages(hu);
			if (productStorages.size() > 1)
			{
				// Multi-product TU: dispatch via the pi-version's calc method (only when HU_UnitType=TU).
				// If no mode is configured (not a TU, or TU with no mode set), fall back to UNSPECIFIED.
				if (handlingUnitsBL.isTransportUnit(hu))
				{
					final I_M_HU_PI_Version piVersion = handlingUnitsBL.getEffectivePIVersion(hu);
					if (piVersion != null)
					{
						final PackageDimensionCalcMethod calcMethod = PackageDimensionCalcMethod.ofNullableCode(piVersion.getPackageDimensionCalcMethod());
						if (calcMethod != null)
						{
							// Batch-load all products in one query to avoid an N+1 DB round-trip.
							final Set<ProductId> productIds = productStorages.stream()
									.map(IHUProductStorage::getProductId)
									.collect(ImmutableSet.toImmutableSet());
							final ImmutableMap<ProductId, Product> productsById = productRepository.getByIdsAsMap(productIds);

							final List<PackageDimensionItem> items = new ArrayList<>();
							for (final IHUProductStorage storage : productStorages)
							{
								final Product product = productsById.get(storage.getProductId());
								if (product == null)
								{
									// Product not found (inactive/deleted) — degrade gracefully.
									return PackageDimensions.UNSPECIFIED;
								}
								items.add(PackageDimensionItem.of(product.getPackageDimensions(), storage.getQtyInStockingUOM()));
							}
							return PackageDimensions.ofItems(calcMethod, items);
						}
					}
				}
				return PackageDimensions.UNSPECIFIED;
			}

			// Single-product: use product dims (IsSelfPacked gate is SysConfig-controlled, default off).
			final IHUProductStorage singleHUProductStorage = productStorages.iterator().next();
			final Product product = productRepository.getById(singleHUProductStorage.getProductId());
			if (isCheckSelfPacked() && !product.isSelfPacked())
			{
				return PackageDimensions.UNSPECIFIED;
			}
			final PackageDimensions dimensions = product.getPackageDimensions();
			if (dimensions.isUnspecified())
			{
				return PackageDimensions.UNSPECIFIED;
			}
			final Quantity qtyInStockingUOM = singleHUProductStorage.getQtyInStockingUOM();
			return PackageDimensions.ofProductDimensionsAndQty(dimensions, qtyInStockingUOM);
		}
	}
}
