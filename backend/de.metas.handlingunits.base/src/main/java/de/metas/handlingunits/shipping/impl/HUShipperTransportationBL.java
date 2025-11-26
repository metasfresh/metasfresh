package de.metas.handlingunits.shipping.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHULockBL;
import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.impl.CreatePackagesRequest;
import de.metas.handlingunits.impl.CreateShipperTransportationRequest;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.slot.IHUPickingSlotBL;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromHU;
import de.metas.handlingunits.shipping.AddTrackingInfosForInOutWithoutHUReq;
import de.metas.handlingunits.shipping.CreatePackageForHURequest;
import de.metas.handlingunits.shipping.CreatePackagesForInOutRequest;
import de.metas.handlingunits.shipping.IHUPackageBL;
import de.metas.handlingunits.shipping.IHUShipperTransportationBL;
import de.metas.handlingunits.shipping.InOutPackageRepository;
import de.metas.handlingunits.shipping.weighting.ShippingWeightCalculator;
import de.metas.handlingunits.shipping.weighting.ShippingWeightSourceTypes;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.lang.SOTrx;
import de.metas.lock.api.LockOwner;
import de.metas.organization.OrgId;
import de.metas.product.PackageDimensions;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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

public class HUShipperTransportationBL implements IHUShipperTransportationBL
{
	private final static AdMessageKey MSG_CANNOT_DETERMINE_HU_PACKAGE_DIMENSIONS = AdMessageKey.of("CannotDetermineHUPackageDimensions");
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	private final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);
	private final IHUPackageBL huPackageBL = Services.get(IHUPackageBL.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

	@VisibleForTesting
	public static final String SYSCONFIG_WeightSourceTypes = "de.metas.shipping.WeightSourceTypes";
	private static final LockOwner transportationLockOwner = LockOwner.newOwner(HUShipperTransportationBL.class.getName());

	@Override
	public List<I_M_Package> addHUsToShipperTransportation(final ShipperTransportationId shipperTransportationId, final Collection<CreatePackageForHURequest> packageRequests)
	{
		//
		// Load Shipper transportation document and validate it
		final I_M_ShipperTransportation shipperTransportation = load(shipperTransportationId, I_M_ShipperTransportation.class);
		if (shipperTransportation == null)
		{
			throw new AdempiereException("@NotFound@ @M_ShipperTransportation_ID@")
					.appendParametersToMessage()
					.setParameter("M_ShipperTransportation_ID", shipperTransportationId.getRepoId());
		}
		// Make sure Shipper Transportation is still open
		if (shipperTransportation.isProcessed())
		{
			throw new AdempiereException("@M_ShipperTransportation_ID@: @Processed@=@Y@")
					.appendParametersToMessage()
					.setParameter("M_ShipperTransportation_ID", shipperTransportationId);
		}

		final ShipperId shipperId = ShipperId.ofRepoId(shipperTransportation.getM_Shipper_ID());

		// services
		final IHUPackageBL huPackageBL = Services.get(IHUPackageBL.class);
		final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
		final IShipperTransportationBL shipperTransportationBL = Services.get(IShipperTransportationBL.class);
		final IHULockBL huLockBL = Services.get(IHULockBL.class);
		final ShippingWeightCalculator weightCalculator = newWeightCalculator();

		//
		// Iterate HUs and:
		// * create M_Packages
		// * assign M_Packages them to Shipper Transportation document
		final List<I_M_Package> result = new ArrayList<>();
		for (CreatePackageForHURequest packageRequest : packageRequests)
		{
			final I_M_HU hu = packageRequest.getHu();

			//
			// Skip HUs which are not eligible for adding to shipper transportation
			// (i.e. it's not top level LU)
			if (!huPackageBL.isEligibleForAddingToShipperTransportation(hu))
			{
				continue;
			}

			packageRequest = packageRequest.withShipperId(shipperId);

			//
			// Weight it
			if (packageRequest.getWeightInKg() == null || packageRequest.getWeightInKg().signum() <= 0)
			{
				packageRequest = packageRequest.withWeightInKg(
						weightCalculator.calculateWeightInKg(hu)
								.map(weight -> weight.toBigDecimal())
								.orElse(null)
				);
			}

			//
			// Create M_Package
			final I_M_Package mpackage = huPackageBL.createM_Package(packageRequest);
			result.add(mpackage);

			//
			// Add M_Package to Shipper Transportation document
			shipperTransportationBL.createShippingPackage(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), mpackage);

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

	@NonNull
	@Override
	public ImmutableList<I_M_Package> addInOutWithoutHUToShipperTransportation(@NonNull final ShipperTransportationId shipperTransportationId, @NonNull final ImmutableList<CreatePackagesForInOutRequest> requests)
	{
		final I_M_ShipperTransportation shipperTransportation = load(shipperTransportationId, I_M_ShipperTransportation.class);

		// Make sure Shipper Transportation is still open
		if (shipperTransportation.isProcessed())
		{
			throw new AdempiereException("@M_ShipperTransportation_ID@: @Processed@=@Y@: " + shipperTransportation.getM_ShipperTransportation_ID());
		}

		final ShipperId shipperId = ShipperId.ofRepoId(shipperTransportation.getM_Shipper_ID());

		// services
		final InOutPackageRepository inOutPackageRepository = SpringContextHolder.instance.getBean(InOutPackageRepository.class);
		final IShipperTransportationBL shipperTransportationBL = Services.get(IShipperTransportationBL.class);
		final ShippingWeightCalculator weightCalculator = newWeightCalculator();

		//
		// Iterate InOuts and:
		// - create M_Packages
		// - assign M_Packages them to Shipper Transportation document
		// - assign ShipperTransportation to the InOut
		final ImmutableList.Builder<I_M_Package> result = ImmutableList.builder();
		for (final CreatePackagesForInOutRequest request : requests)
		{
			// Skip the InOuts which already have a Shipper Transportation
			if (request.getShipperTransportationId() != null)
			{
				continue;
			}

			//
			// Create M_Packages
			final List<CreatePackagesRequest> createPackagesRequestList = buildCreatePackageRequest(shipperId, request, weightCalculator);

			final List<I_M_Package> mPackages = inOutPackageRepository.createM_Packages(createPackagesRequestList);
			result.addAll(mPackages);

			//
			// Add M_Package to Shipper Transportation document
			mPackages.forEach(mpackage -> shipperTransportationBL.createShippingPackage(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), mpackage));

			linkTransportationToShipment(request.getShipment(), shipperTransportationId);
		}

		return result.build();
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

	@Nullable
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
		int generalShipperTransportationId = -1;
		for (final I_M_ShippingPackage shippingPackage : shippingPackages)
		{
			final int shipperTransportationId = shippingPackage.getM_ShipperTransportation_ID();
			if (generalShipperTransportationId < 0)
			{
				generalShipperTransportationId = shipperTransportationId;
			}
			Check.assumeEquals(generalShipperTransportationId, shipperTransportationId, "shipper transportations shall all match for any given HU");
		}

		final I_M_ShippingPackage firstPackage = shippingPackages.iterator().next();
		return firstPackage.getM_ShipperTransportation();
	}

	@NonNull
	public I_M_ShipperTransportation getById(@NonNull final ShipperTransportationId shipperTransportationId)
	{
		return load(shipperTransportationId, I_M_ShipperTransportation.class);
	}

	@NonNull
	public ShipperTransportationId addTrackingInfosForInOutWithoutHU(@NonNull final AddTrackingInfosForInOutWithoutHUReq req)
	{
		final I_M_InOut shipment = inOutDAO.getById(req.getInOutId());

		final BPartnerLocationAndCaptureId shipFromBPLocation = getShipFromBPartnerAndLocation(shipment);
		final I_M_Shipper shipper = shipperDAO.getById(req.getShipperId());

		final CreateShipperTransportationRequest createShipperTransportationRequest = CreateShipperTransportationRequest
				.builder()
				.shipperId(req.getShipperId())
				.shipperBPartnerAndLocationId(shipFromBPLocation.getBpartnerLocationId())
				.orgId(OrgId.ofRepoId(shipment.getAD_Org_ID()))
				.shipDate(TimeUtil.asLocalDate(shipment.getMovementDate()))
				.pickupTimeFrom(TimeUtil.asLocalTime(shipper.getPickupTimeFrom()))
				.pickupTimeTo(TimeUtil.asLocalTime(shipper.getPickupTimeTo()))
				.isSOTrx(SOTrx.SALES)
				.build();

		final ShipperTransportationId shipperTransportationId = shipperTransportationDAO.create(createShipperTransportationRequest);

		final CreatePackagesForInOutRequest createPackagesForInOutRequest = CreatePackagesForInOutRequest.builder()
				.shipment(InterfaceWrapperHelper.create(shipment, de.metas.inout.model.I_M_InOut.class))
				.packageInfos(req.getPackageInfos())
				.processed(true)// mark the M_Package records as processed
				.build();

		addInOutWithoutHUToShipperTransportation(shipperTransportationId, ImmutableList.of(createPackagesForInOutRequest));

		return shipperTransportationId;
	}

	public void processShipperTransportation(@NonNull final ShipperTransportationId shipperTransportationId)
	{
		final I_M_ShipperTransportation shipperTransportation = getById(shipperTransportationId);

		docActionBL.processEx(shipperTransportation, IDocument.ACTION_Complete);
	}

	private BPartnerLocationAndCaptureId getShipFromBPartnerAndLocation(final I_M_InOut shipment)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(shipment.getM_Warehouse_ID());
		return warehouseDAO.getWarehouseLocationById(warehouseId);
	}

	@NonNull
	private List<CreatePackagesRequest> buildCreatePackageRequest(
			@NonNull final ShipperId shipperId,
			@NonNull final CreatePackagesForInOutRequest request,
			@NonNull final ShippingWeightCalculator weightCalculator)
	{
		if (Check.isEmpty(request.getPackageInfos()))
		{
			return huInOutDAO.retrieveShippedHandlingUnits(request.getShipment())
					.stream()
					.map(hu -> CreatePackagesRequest.builder()
							.inOutId(request.getShipmentId())
							.shipperId(shipperId)
							.processed(request.isProcessed())
							.weightInKg(weightCalculator.calculateWeightInKg(hu)
									.map(weight -> weight.toBigDecimal())
									.orElse(null))
							.packageDimensions(extractPackageDimensions(hu))
							.build())
					.collect(Collectors.toList());
		}
		else
		{
			return request.getPackageInfos()
					.stream()
					.map(packageInfo -> CreatePackagesRequest.builder()
							.inOutId(request.getShipmentId())
							.shipperId(shipperId)
							.processed(request.isProcessed())
							//
							.trackingCode(packageInfo.getTrackingNumber())
							.trackingURL(packageInfo.getTrackingUrl())
							.weightInKg(packageInfo.getWeight())
							.packageDimensions(packageInfo.getPackageDimensions())
							.build()
					)
					.collect(ImmutableList.toImmutableList());
		}
	}

	private PackageDimensions extractPackageDimensions(final I_M_HU hu)
	{
		final PackageDimensions packageDimensions = huPackageBL.getPackageDimensions(hu);
		if (packageDimensions.isUnspecified())
		{
			throw new AdempiereException(MSG_CANNOT_DETERMINE_HU_PACKAGE_DIMENSIONS, hu.getM_HU_ID());
		}
		return packageDimensions;
	}

	private void linkTransportationToShipment(@NonNull final I_M_InOut shipment, @NonNull final ShipperTransportationId shipperTransportationId)
	{
		final de.metas.inout.model.I_M_InOut inOutShipment = InterfaceWrapperHelper.create(shipment, de.metas.inout.model.I_M_InOut.class);
		inOutShipment.setM_ShipperTransportation_ID(shipperTransportationId.getRepoId());
		inOutDAO.save(inOutShipment);
	}

	private ShippingWeightCalculator newWeightCalculator()
	{
		return ShippingWeightCalculator.builder()
				.weightSourceTypes(getWeightsSourceTypes())
				.build();
	}

	private ShippingWeightSourceTypes getWeightsSourceTypes()
	{
		return ShippingWeightSourceTypes.ofCommaSeparatedString(sysConfigBL.getValue(SYSCONFIG_WeightSourceTypes)).orElse(ShippingWeightSourceTypes.DEFAULT);
	}

}
