/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl;

import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.organization.OrgId;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.dhl.model.DhlServiceType;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.load;

@Service
public class DhlDraftDeliveryOrderCreator implements DraftDeliveryOrderCreator
{
	private static final Logger logger = LoggerFactory.getLogger(DhlDraftDeliveryOrderCreator.class);

	@Override public String getShipperGatewayId()
	{
		return DhlConstants.SHIPPER_GATEWAY_ID;
	}

	@Override public DeliveryOrder createDraftDeliveryOrder(final CreateDraftDeliveryOrderRequest request)
	{
		final DeliveryOrderKey deliveryOrderKey = request.getDeliveryOrderKey();
		final Set<Integer> mpackageIds = request.getMpackageIds();

		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final I_C_BPartner pickupFromBPartner = bpartnerOrgBL.retrieveLinkedBPartner(deliveryOrderKey.getFromOrgId());
		final I_C_Location pickupFromLocation = bpartnerOrgBL.retrieveOrgLocation(OrgId.ofRepoId(deliveryOrderKey.getFromOrgId()));
		final LocalDate pickupDate = deliveryOrderKey.getPickupDate();

		final int deliverToBPartnerId = deliveryOrderKey.getDeliverToBPartnerId();
		final I_C_BPartner deliverToBPartner = load(deliverToBPartnerId, I_C_BPartner.class);

		final int deliverToBPartnerLocationId = deliveryOrderKey.getDeliverToBPartnerLocationId();
		final I_C_BPartner_Location deliverToBPLocation = load(deliverToBPartnerLocationId, I_C_BPartner_Location.class);
		final I_C_Location deliverToLocation = deliverToBPLocation.getC_Location();
		final String deliverToPhoneNumber = CoalesceUtil.firstNotEmptyTrimmed(deliverToBPLocation.getPhone(), deliverToBPLocation.getPhone2(), deliverToBPartner.getPhone2());

		// todo: implement DHL custom delivery order data, the rest of the code is similar to the 2 other shippers
		//		final GoDeliveryOrderData goDeliveryOrderData = GoDeliveryOrderData.builder()
		//				.receiptConfirmationPhoneNumber(null)
		//				.paidMode(GOPaidMode.Prepaid)
		//				.selfPickup(GOSelfPickup.Delivery)
		//				.selfDelivery(GOSelfDelivery.Pickup)
		//				.build();

		// todo when editing this don't forget to update de.metas.shipper.gateway.dhl.DhlDeliveryOrderRepository.toDeliveryOrderPO
		return DeliveryOrder.builder()
				//	todo what's this? what is it used for?			.shipperId(deliveryOrderKey.getShipperId())
				//	todo what's this? what is it used for?			.shipperTransportationId(deliveryOrderKey.getShipperTransportationId())
				//
				.serviceType(DhlServiceType.V01PAK) // todo how to change the service type?
				//				.customerReference() // todo this is not set in any place with any user-relevant value afaics!
				//				todo .customDeliveryData(goDeliveryOrderData)
				//
				// Pickup aka Shipper
				.pickupAddress(DeliveryOrderUtil.prepareAddressFromLocation(pickupFromLocation)
						.companyName1(pickupFromBPartner.getName())
						.companyName2(pickupFromBPartner.getName2())
						.build())
				.pickupDate(PickupDate.builder()
						.date(pickupDate)
						.build())
				//
				// Delivery aka Receiver
				.deliveryAddress(DeliveryOrderUtil.prepareAddressFromLocation(deliverToLocation)
						.companyName1(deliverToBPartner.getName())
						.companyName2(deliverToBPartner.getName2())
						.bpartnerId(deliverToBPartnerId) // afaics used only for logging
						.bpartnerLocationId(deliverToBPartnerLocationId) // afaics used only for logging
						.build())
				.deliveryContact(ContactPerson.builder()
						.emailAddress(deliverToBPartner.getEMail())
						.simplePhoneNumber(deliverToPhoneNumber)
						.build())
				//
				// Delivery content
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(mpackageIds.size())
						.packageIds(mpackageIds)
						.grossWeightKg(Math.max(request.getGrossWeightInKg(), 1))
//						.packageDimensions() // todo how to get these?
						.build())
				// todo if needed .customerReference(null)
				//
				.build();

	}
}
