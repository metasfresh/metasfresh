package de.metas.shipper.gateway.go;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.time.LocalDate;
import java.util.Set;

import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.springframework.stereotype.Service;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.go.schema.GOPaidMode;
import de.metas.shipper.gateway.go.schema.GOSelfDelivery;
import de.metas.shipper.gateway.go.schema.GOSelfPickup;
import de.metas.shipper.gateway.go.schema.GOServiceType;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.go
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class GODraftDeliveryOrderCreator implements DraftDeliveryOrderCreator
{
	@Override
	public String getShipperGatewayId()
	{
		return GOConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public DeliveryOrder createDraftDeliveryOrder(@NonNull final CreateDraftDeliveryOrderRequest request)
	{
		final DeliveryOrderKey deliveryOrderKey = request.getDeliveryOrderKey();
		final Set<Integer> mpackageIds = request.getMpackageIds();

		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final I_C_BPartner pickupFromBPartner = bpartnerOrgBL.retrieveLinkedBPartner(deliveryOrderKey.getFromOrgId());
		final I_C_Location pickupFromLocation = bpartnerOrgBL.retrieveOrgLocation(deliveryOrderKey.getFromOrgId());
		final LocalDate pickupDate = deliveryOrderKey.getPickupDate();

		final int deliverToBPartnerId = deliveryOrderKey.getDeliverToBPartnerId();
		final I_C_BPartner deliverToBPartner = load(deliverToBPartnerId, I_C_BPartner.class);

		final int deliverToBPartnerLocationId = deliveryOrderKey.getDeliverToBPartnerLocationId();
		final I_C_BPartner_Location deliverToBPLocation = load(deliverToBPartnerLocationId, I_C_BPartner_Location.class);
		final I_C_Location deliverToLocation = deliverToBPLocation.getC_Location();

		final GoDeliveryOrderData goDeliveryOrderData = GoDeliveryOrderData.builder()
				.receiptConfirmationPhoneNumber(null)
				.paidMode(GOPaidMode.Prepaid)
				.selfPickup(GOSelfPickup.Delivery)
				.selfDelivery(GOSelfDelivery.Pickup)
				.build();

		return DeliveryOrder.builder()
				.shipperId(deliveryOrderKey.getShipperId())
				.shipperTransportationId(deliveryOrderKey.getShipperTransportationId())
				//
				.serviceType(GOServiceType.Overnight)
				.customDeliveryData(goDeliveryOrderData)
				//
				// Pickup
				.pickupAddress(DeliveryOrderUtil.prepareAddressFromLocation(pickupFromLocation)
						.companyName1(pickupFromBPartner.getName())
						.companyName2(pickupFromBPartner.getName2())
						.build())
				.pickupDate(PickupDate.builder()
						.date(pickupDate)
						.build())
				//
				// Delivery
				.deliveryAddress(DeliveryOrderUtil.prepareAddressFromLocation(deliverToLocation)
						.companyName1(deliverToBPartner.getName())
						.companyName2(deliverToBPartner.getName2())
						.companyDepartment("-") // N/A
						.bpartnerId(deliverToBPartnerId)
						.bpartnerLocationId(deliverToBPartnerLocationId)
						.build())
				//
				// Delivery content
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(mpackageIds.size())
						.packageIds(mpackageIds)
						.grossWeightKg(Math.max(request.getGrossWeightInKg(), 1))
						.content(request.getPackageContentDescription())
						.build())
				// .customerReference(null)
				//
				.build();
	}

}
