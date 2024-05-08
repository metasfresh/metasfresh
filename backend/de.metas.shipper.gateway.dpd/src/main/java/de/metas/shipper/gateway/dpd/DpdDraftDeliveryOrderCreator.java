/*
 * #%L
 * de.metas.shipper.gateway.dpd
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.shipper.gateway.dpd;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.mpackage.PackageId;
import de.metas.organization.OrgId;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.dpd.model.DpdClientConfigRepository;
import de.metas.shipper.gateway.dpd.model.DpdNotificationChannel;
import de.metas.shipper.gateway.dpd.model.DpdOrderCustomDeliveryData;
import de.metas.shipper.gateway.dpd.model.DpdOrderType;
import de.metas.shipper.gateway.dpd.model.DpdShipperProduct;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipper.gateway.spi.model.ShipperProduct;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_Package;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
public class DpdDraftDeliveryOrderCreator implements DraftDeliveryOrderCreator
{
	private final DpdClientConfigRepository clientConfigRepository;

	public DpdDraftDeliveryOrderCreator(final DpdClientConfigRepository clientConfigRepository)
	{
		this.clientConfigRepository = clientConfigRepository;
	}

	@Override
	public String getShipperGatewayId()
	{
		return DpdConstants.SHIPPER_GATEWAY_ID;
	}

	/**
	 * Create the initial DTO.
	 */
	@NonNull
	@Override
	public DeliveryOrder createDraftDeliveryOrder(@NonNull final CreateDraftDeliveryOrderRequest request)
	{
		final DeliveryOrderKey deliveryOrderKey = request.getDeliveryOrderKey();
		final Set<PackageId> mpackageIds = request.getMpackageIds();

		final String customerReference = ""; // there's no customer reference for now. maybe in the future?

		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final I_C_BPartner pickupFromBPartner = bpartnerOrgBL.retrieveLinkedBPartner(deliveryOrderKey.getFromOrgId());
		final I_C_Location pickupFromLocation = bpartnerOrgBL.retrieveOrgLocation(OrgId.ofRepoId(deliveryOrderKey.getFromOrgId()));
		final LocalDate pickupDate = deliveryOrderKey.getPickupDate();
		final LocalTime timeFrom = deliveryOrderKey.getTimeFrom();
		final LocalTime timeTo = deliveryOrderKey.getTimeTo();

		final int deliverToBPartnerId = deliveryOrderKey.getDeliverToBPartnerId();
		final I_C_BPartner deliverToBPartner = InterfaceWrapperHelper.load(deliverToBPartnerId, I_C_BPartner.class);

		final int deliverToBPartnerLocationId = deliveryOrderKey.getDeliverToBPartnerLocationId();
		final I_C_BPartner_Location deliverToBPLocation = InterfaceWrapperHelper.load(deliverToBPartnerLocationId, I_C_BPartner_Location.class);
		final I_C_Location deliverToLocation = deliverToBPLocation.getC_Location();
		final String deliverToPhoneNumber = CoalesceUtil.firstNotEmptyTrimmed(deliverToBPLocation.getPhone(), deliverToBPLocation.getPhone2(), deliverToBPartner.getPhone2());

		final ShipperId shipperId = deliveryOrderKey.getShipperId();
		final ShipperTransportationId shipperTransportationId = deliveryOrderKey.getShipperTransportationId();

		// inside same country we want "next-day" delivery
		// while international shipping only works with classic delivery (or express).
		// it's up to the customer to select the proper shipper (which has the correct ShipperProduct)
		final DpdShipperProduct serviceType = clientConfigRepository.getByShipperId(shipperId).getShipperProduct();
		if (pickupFromLocation.getC_Country_ID() != deliverToLocation.getC_Country_ID() && !serviceType.equals(DpdShipperProduct.DPD_CLASSIC))
		{
			throw new ShipperGatewayException("Please use product " + DpdShipperProduct.DPD_CLASSIC.getCode() + " for international orders.");
		}

		final DpdOrderCustomDeliveryData customDeliveryData = DpdOrderCustomDeliveryData.builder()
				.orderType(DpdOrderType.CONSIGNMENT)
				// .sendingDepot()// this is null and only set in the client, after login is done
				.paperFormat(clientConfigRepository.getByShipperId(shipperId).getPaperFormat())
				.printerLanguage(DpdConstants.DEFAULT_PRINTER_LANGUAGE)
				.notificationChannel(DpdNotificationChannel.EMAIL)
				.build();

		// Order lines
		final ImmutableList.Builder<DeliveryOrderLine> deliveryOrderLinesBuilder = ImmutableList.builder();
		for (final PackageId packageId : mpackageIds)
		{
			final I_M_Package mPackage = InterfaceWrapperHelper.load(packageId, I_M_Package.class);

			final DeliveryOrderLine deliveryOrderLine = DeliveryOrderLine.builder()
					// .repoId()
					.content(mPackage.getDescription())
					.grossWeightKg(getPackageGrossWeightKg(mPackage, 1)) // same as in de.metas.shipper.gateway.commons.ShipperGatewayFacade.computeGrossWeightInKg: we assume it's in Kg
					.packageDimensions(getPackageDimensions(packageId))
					// .customDeliveryData()
					.packageId(packageId)
					.build();

			deliveryOrderLinesBuilder.add(deliveryOrderLine);
		}

		return createDeliveryOrderFromParams(
				pickupFromBPartner,
				pickupFromLocation,
				pickupDate,
				timeFrom,
				timeTo,
				deliverToBPartner,
				deliverToLocation,
				deliverToPhoneNumber,
				serviceType,
				shipperId,
				shipperTransportationId,
				customerReference,
				customDeliveryData,
				deliveryOrderLinesBuilder.build());
	}

	private int getPackageGrossWeightKg(@NonNull final I_M_Package mPackage, @SuppressWarnings("SameParameterValue") final int defaultValue)
	{
		final int weight = mPackage.getPackageWeight().intValue();
		if (weight == 0)
		{
			return defaultValue;
		}
		else
		{
			return weight;
		}
	}

	@VisibleForTesting
	DeliveryOrder createDeliveryOrderFromParams(
			@NonNull final I_C_BPartner pickupFromBPartner,
			@NonNull final I_C_Location pickupFromLocation,
			@NonNull final LocalDate pickupDate,
			@NonNull final LocalTime timeFrom,
			@NonNull final LocalTime timeTo,
			@NonNull final I_C_BPartner deliverToBPartner,
			@NonNull final I_C_Location deliverToLocation,
			@Nullable final String deliverToPhoneNumber,
			@NonNull final ShipperProduct shipperProduct,
			@NonNull final ShipperId shipperId,
			@NonNull final ShipperTransportationId shipperTransportationId,
			@Nullable final String customerReference,
			@NonNull final DpdOrderCustomDeliveryData customDeliveryData,
			@NonNull final List<DeliveryOrderLine> deliveryOrderLines)
	{

		return DeliveryOrder.builder()
				.shipperId(shipperId)
				.shipperTransportationId(shipperTransportationId)
				//
				//
				.shipperProduct(shipperProduct)
				.customerReference(customerReference)
				.customDeliveryData(customDeliveryData)

				//
				// Pickup aka Sender
				.pickupAddress(DeliveryOrderUtil.prepareAddressFromLocation(pickupFromLocation)
						.companyName1(pickupFromBPartner.getName())
						.companyName2(pickupFromBPartner.getName2())
						.build())
				.pickupDate(PickupDate.builder()
						.date(pickupDate)
						.timeFrom(timeFrom)
						.timeTo(timeTo)
						.build())
				//
				// Delivery aka Recipient
				.deliveryAddress(DeliveryOrderUtil.prepareAddressFromLocation(deliverToLocation)
						.companyName1(deliverToBPartner.getName())
						.companyName2(deliverToBPartner.getName2())
						.bpartnerId(deliverToBPartner.getC_BPartner_ID()) // afaics used only for logging
						.build())
				.deliveryContact(ContactPerson.builder()
						.emailAddress(deliverToBPartner.getEMail())
						.simplePhoneNumber(deliverToPhoneNumber)
						.build())
				//
				// Delivery content
				.deliveryOrderLines(deliveryOrderLines)
				.build();
	}

	@NonNull
	private PackageDimensions getPackageDimensions(@NonNull final PackageId packageId)
	{
		final IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
		final I_M_HU_PackingMaterial packingMaterial = packingMaterialDAO.retrievePackingMaterialOrNull(packageId);

		if (packingMaterial == null)
		{
			throw new AdempiereException("There is no packing material for the package: " + packageId + ". Please create a packing material and set its correct dimensions.");
		}

		final UomId toUomId = Services.get(IUOMDAO.class).getUomIdByX12DE355(DpdConstants.DEFAULT_PACKAGE_DIMENSIONS_UOM);
		return packingMaterialDAO.retrievePackageDimensions(packingMaterial, toUomId);
	}
}
