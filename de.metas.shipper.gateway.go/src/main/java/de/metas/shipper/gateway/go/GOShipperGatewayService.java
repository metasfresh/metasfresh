package de.metas.shipper.gateway.go;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_Package;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.shipper.gateway.api.ShipperGatewayService;
import de.metas.shipper.gateway.api.model.DeliveryOrder;
import de.metas.shipper.gateway.api.model.DeliveryOrderCreateRequest;
import de.metas.shipper.gateway.api.model.DeliveryPosition;
import de.metas.shipper.gateway.api.model.PickupDate;
import de.metas.shipper.gateway.go.async.GODeliveryOrderWorkpackageProcessor;
import de.metas.shipper.gateway.go.schema.GOPaidMode;
import de.metas.shipper.gateway.go.schema.GOSelfDelivery;
import de.metas.shipper.gateway.go.schema.GOSelfPickup;
import de.metas.shipper.gateway.go.schema.GOServiceType;
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

public class GOShipperGatewayService implements ShipperGatewayService
{
	private final GODeliveryOrderRepository deliveryOrderRepository;

	public GOShipperGatewayService(@NonNull final GODeliveryOrderRepository deliveryOrderRepository)
	{
		this.deliveryOrderRepository = deliveryOrderRepository;
	}

	@Override
	public String getShipperGatewayId()
	{
		return GOConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public void createAndSendDeliveryOrdersForPackages(@NonNull DeliveryOrderCreateRequest request)
	{
		final LocalDate pickupDate = request.getPickupDate();
		retrievePackagesByIds(request.getPackageIds())
				.stream()
				.collect(GuavaCollectors.toImmutableListMultimap(mpackage -> createDeliveryOrderKey(mpackage, pickupDate)))
				.asMap()
				.forEach(this::createAndSendDeliveryOrder);
	}

	private List<I_M_Package> retrievePackagesByIds(final Set<Integer> mpackageIds)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Package.class)
				.addInArrayFilter(I_M_Package.COLUMN_M_Package_ID, mpackageIds)
				.create()
				.list(I_M_Package.class);
	}

	private static final DeliveryOrderKey createDeliveryOrderKey(final I_M_Package mpackage, final LocalDate pickupDate)
	{
		return DeliveryOrderKey.builder()
				.shipperId(mpackage.getM_Shipper_ID())
				.fromOrgId(mpackage.getAD_Org_ID())
				.deliverToBPartnerId(mpackage.getC_BPartner_ID())
				.deliverToBPartnerLocationId(mpackage.getC_BPartner_Location_ID())
				.pickupDate(pickupDate)
				.build();
	}

	private void createAndSendDeliveryOrder(final DeliveryOrderKey deliveryOrderKey, final Collection<I_M_Package> mpackages)
	{
		DeliveryOrder deliveryOrder = createDraftDeliveryOrder(deliveryOrderKey, mpackages);
		deliveryOrder = deliveryOrderRepository.save(deliveryOrder);
		GODeliveryOrderWorkpackageProcessor.enqueueOnTrxCommit(deliveryOrder.getRepoId());
	}

	private DeliveryOrder createDraftDeliveryOrder(final DeliveryOrderKey deliveryOrderKey, final Collection<I_M_Package> mpackages)
	{
		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final I_C_BPartner pickupFromBPartner = bpartnerOrgBL.retrieveLinkedBPartner(deliveryOrderKey.getFromOrgId());
		final I_C_Location pickupFromLocation = bpartnerOrgBL.retrieveOrgLocation(deliveryOrderKey.getFromOrgId());
		final LocalDate pickupDate = deliveryOrderKey.getPickupDate();

		final int deliverToBPartnerId = deliveryOrderKey.getDeliverToBPartnerId();
		final I_C_BPartner deliverToBPartner = load(deliverToBPartnerId, I_C_BPartner.class);

		final int deliverToBPartnerLocationId = deliveryOrderKey.getDeliverToBPartnerLocationId();
		final I_C_BPartner_Location deliverToBPLocation = load(deliverToBPartnerLocationId, I_C_BPartner_Location.class);
		final I_C_Location deliverToLocation = deliverToBPLocation.getC_Location();

		final Set<Integer> mpackageIds = mpackages.stream().map(I_M_Package::getM_Package_ID).collect(ImmutableSet.toImmutableSet());

		return DeliveryOrder.builder()
				.shipperId(deliveryOrderKey.getShipperId())
				//
				.serviceType(GOServiceType.Overnight)
				.paidMode(GOPaidMode.Prepaid)
				.receiptConfirmationPhoneNumber(null)
				//
				// Pickup
				.pickupAddress(GODeliveryOrderConverters.prepareAddressFromLocation(pickupFromLocation)
						.companyName1(pickupFromBPartner.getName())
						.companyName2(pickupFromBPartner.getName2())
						.build())
				.pickupDate(PickupDate.builder()
						.date(pickupDate)
						.build())
				.selfPickup(GOSelfPickup.Delivery)
				//
				// Delivery
				.deliveryAddress(GODeliveryOrderConverters.prepareAddressFromLocation(deliverToLocation)
						.companyName1(deliverToBPartner.getName())
						.companyName2(deliverToBPartner.getName2())
						.companyDepartment("-") // N/A
						.bpartnerId(deliverToBPartnerId)
						.bpartnerLocationId(deliverToBPartnerLocationId)
						.build())
				.selfDelivery(GOSelfDelivery.Pickup)
				//
				// Delivery content
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(mpackageIds.size())
						.packageIds(mpackageIds)
						.grossWeightKg(Math.max(computeGrossWeightInKg(mpackages), 1))
						.content(computePackageContentDescription(mpackages))
						.build())
				// .customerReference(null)
				//
				.build();
	}

	private static int computeGrossWeightInKg(final Collection<I_M_Package> mpackages)
	{
		return mpackages.stream()
				.map(I_M_Package::getPackageWeight) // TODO: we assume it's in Kg
				.filter(weight -> weight != null && weight.signum() > 0)
				.reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(0, RoundingMode.UP)
				.intValueExact();
	}

	private static String computePackageContentDescription(final Collection<I_M_Package> mpackages)
	{
		final String content = mpackages.stream()
				.map(I_M_Package::getDescription)
				.filter(desc -> !Check.isEmpty(desc, true))
				.map(String::trim)
				.collect(Collectors.joining(", "));
		return !Check.isEmpty(content, true) ? content : "-";
	}

	@lombok.Value
	private static final class DeliveryOrderKey
	{
		int shipperId;
		int fromOrgId;
		int deliverToBPartnerId;
		int deliverToBPartnerLocationId;
		LocalDate pickupDate;

		@lombok.Builder
		public DeliveryOrderKey(
				final int shipperId,
				final int fromOrgId,
				final int deliverToBPartnerId,
				final int deliverToBPartnerLocationId,
				@NonNull final LocalDate pickupDate)
		{
			Check.assume(shipperId > 0, "shipperId > 0");
			Check.assume(fromOrgId > 0, "fromOrgId > 0");
			Check.assume(deliverToBPartnerId > 0, "deliverToBPartnerId > 0");
			Check.assume(deliverToBPartnerLocationId > 0, "deliverToBPartnerLocationId > 0");

			this.shipperId = shipperId;
			this.fromOrgId = fromOrgId;
			this.deliverToBPartnerId = deliverToBPartnerId;
			this.deliverToBPartnerLocationId = deliverToBPartnerLocationId;
			this.pickupDate = pickupDate;
		}
	}

}
