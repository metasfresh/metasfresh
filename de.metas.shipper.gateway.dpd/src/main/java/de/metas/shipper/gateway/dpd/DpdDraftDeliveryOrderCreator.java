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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;

@Service
public class DpdDraftDeliveryOrderCreator implements DraftDeliveryOrderCreator
{
	private static final Logger logger = LoggerFactory.getLogger(DpdDraftDeliveryOrderCreator.class);

	@Override public String getShipperGatewayId()
	{
		return DpdConstants.SHIPPER_GATEWAY_ID;
	}

	@Override public DeliveryOrder createDraftDeliveryOrder(final CreateDraftDeliveryOrderRequest request)
	{
		// TODO
//		final DeliveryOrderKey deliveryOrderKey = request.getDeliveryOrderKey();
//		final Set<Integer> mpackageIds = request.getMpackageIds();
//
//		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
//		final I_C_BPartner pickupFromBPartner = bpartnerOrgBL.retrieveLinkedBPartner(deliveryOrderKey.getFromOrgId());
//		final I_C_Location pickupFromLocation = bpartnerOrgBL.retrieveOrgLocation(OrgId.ofRepoId(deliveryOrderKey.getFromOrgId()));
//		final LocalDate pickupDate = deliveryOrderKey.getPickupDate();
//
//		final int deliverToBPartnerId = deliveryOrderKey.getDeliverToBPartnerId();
//		final I_C_BPartner deliverToBPartner = load(deliverToBPartnerId, I_C_BPartner.class);
//
//		final int deliverToBPartnerLocationId = deliveryOrderKey.getDeliverToBPartnerLocationId();
//		final I_C_BPartner_Location deliverToBPLocation = load(deliverToBPartnerLocationId, I_C_BPartner_Location.class);
//		final I_C_Location deliverToLocation = deliverToBPLocation.getC_Location();
//
//		// todo: implement DHL custom delivery order data, the rest of the code is similar to the 2 other shippers
//		//		final GoDeliveryOrderData goDeliveryOrderData = GoDeliveryOrderData.builder()
//		//				.receiptConfirmationPhoneNumber(null)
//		//				.paidMode(GOPaidMode.Prepaid)
//		//				.selfPickup(GOSelfPickup.Delivery)
//		//				.selfDelivery(GOSelfDelivery.Pickup)
//		//				.build();
//
//		return DeliveryOrder.builder()
//				.shipperId(deliveryOrderKey.getShipperId())
//				.shipperTransportationId(deliveryOrderKey.getShipperTransportationId())
//				//
//				//				todo .serviceType(GOServiceType.Overnight)
//				//				todo .customDeliveryData(goDeliveryOrderData)
//				//
//				// Pickup
//				.pickupAddress(DeliveryOrderUtil.prepareAddressFromLocation(pickupFromLocation)
//						.companyName1(pickupFromBPartner.getName())
//						.companyName2(pickupFromBPartner.getName2())
//						.build())
//				.pickupDate(PickupDate.builder()
//						.date(pickupDate)
//						.build())
//				//
//				// Delivery
//				.deliveryAddress(DeliveryOrderUtil.prepareAddressFromLocation(deliverToLocation)
//						.companyName1(deliverToBPartner.getName())
//						.companyName2(deliverToBPartner.getName2())
//						.companyDepartment("-") // N/A
//						.bpartnerId(deliverToBPartnerId)
//						.bpartnerLocationId(deliverToBPartnerLocationId)
//						.build())
//				//
//				// Delivery content
//				.deliveryPosition(DeliveryPosition.builder()
//						.numberOfPackages(mpackageIds.size())
//						.packageIds(mpackageIds)
//						.grossWeightKg(Math.max(request.getGrossWeightInKg(), 1))
//						.content(request.getPackageContentDescription())
//						.build())
//				// todo if needed .customerReference(null)
//				//
//				.build();
		return null;

	}
}
