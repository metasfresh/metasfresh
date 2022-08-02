package de.metas.shipper.gateway.derkurier;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import de.metas.common.util.CoalesceUtil;
import de.metas.mpackage.PackageId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.springframework.stereotype.Service;

import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.organization.OrgId;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperProduct;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfig;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfigRepository;
import de.metas.shipper.gateway.derkurier.misc.ParcelNumberGenerator;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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
public class DerKurierDraftDeliveryOrderCreator implements DraftDeliveryOrderCreator
{
	private final DerKurierShipperConfigRepository derKurierShipperConfigRepository;

	public DerKurierDraftDeliveryOrderCreator(
			@NonNull final DerKurierShipperConfigRepository derKurierShipperConfigRepository)
	{
		this.derKurierShipperConfigRepository = derKurierShipperConfigRepository;

	}

	@Override
	public String getShipperGatewayId()
	{
		return DerKurierConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public DeliveryOrder createDraftDeliveryOrder(
			@NonNull final CreateDraftDeliveryOrderRequest request)
	{
		final DeliveryOrderKey deliveryOrderKey = request.getDeliveryOrderKey();
		final Set<PackageId> mpackageIds = request.getMpackageIds();

		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final I_C_BPartner pickupFromBPartner = bpartnerOrgBL.retrieveLinkedBPartner(deliveryOrderKey.getFromOrgId());
		final I_C_Location pickupFromLocation = bpartnerOrgBL.retrieveOrgLocation(OrgId.ofRepoId(deliveryOrderKey.getFromOrgId()));
		final LocalDate pickupDate = deliveryOrderKey.getPickupDate();

		final int deliverToBPartnerId = deliveryOrderKey.getDeliverToBPartnerId();
		final I_C_BPartner deliverToBPartner = load(deliverToBPartnerId, I_C_BPartner.class);

		final int deliverToBPartnerLocationId = deliveryOrderKey.getDeliverToBPartnerLocationId();
		final I_C_BPartner_Location deliverToBPLocation = load(deliverToBPartnerLocationId, I_C_BPartner_Location.class);
		final I_C_Location deliverToLocation = deliverToBPLocation.getC_Location();

		final DerKurierShipperConfig config = derKurierShipperConfigRepository
				.retrieveConfigForShipperId(request.getDeliveryOrderKey().getShipperId().getRepoId());

		final ParcelNumberGenerator parcelNumberGenerator = config.getParcelNumberGenerator();

		final DerKurierDeliveryData derKurierDeliveryData = //
				DerKurierDeliveryData.builder()
						.customerNumber(config.getCustomerNumber())
						.parcelNumber(parcelNumberGenerator.getNextParcelNumber())
						.collectorCode(config.getCollectorCode())
						.customerCode(config.getCustomerCode())
						.desiredTimeFrom(config.getDesiredTimeFrom())
						.desiredTimeTo(config.getDesiredTimeTo())
						.build();

		return DeliveryOrder.builder()
				.shipperProduct(DerKurierShipperProduct.OVERNIGHT)
				.shipperId(deliveryOrderKey.getShipperId())
				.shipperTransportationId(deliveryOrderKey.getShipperTransportationId())
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
						//.companyDepartment("-") // N/A
						.bpartnerId(deliverToBPartnerId)
						.build())
				.deliveryContact(ContactPerson.builder()
						.emailAddress(deliverToBPartner.getEMail())
						.build())
				//
				// Delivery content
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(mpackageIds.size())
						.packageIds(mpackageIds)
						.grossWeightKg(CoalesceUtil.firstGreaterThanZero(request.getAllPackagesGrossWeightInKg(), BigDecimal.ONE))
						.content(request.getAllPackagesContentDescription())
						.customDeliveryData(derKurierDeliveryData)
						.build())
				// .customerReference(null)
				.build();
	}

}
