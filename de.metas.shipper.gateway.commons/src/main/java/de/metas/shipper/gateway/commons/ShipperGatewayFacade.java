package de.metas.shipper.gateway.commons;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;

import de.metas.shipper.gateway.commons.async.DeliveryOrderWorkpackageProcessor;
import de.metas.shipper.gateway.spi.DeliveryOrderRepository;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.CreateDraftDeliveryOrderRequest;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.DeliveryOrderKey;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderCreateRequest;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.commons
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
public class ShipperGatewayFacade
{
	private final ShipperGatewayServicesRegistry shipperRegistry;

	public ShipperGatewayFacade(@NonNull final ShipperGatewayServicesRegistry shipperRegistry)
	{
		this.shipperRegistry = shipperRegistry;
	}

	public void createAndSendDeliveryOrdersForPackages(@NonNull final DeliveryOrderCreateRequest request)
	{
		final LocalDate pickupDate = request.getPickupDate();
		final int shipperTransportationId = request.getShipperTransportationId();

		retrievePackagesByIds(request.getPackageIds())
				.stream()
				.collect(GuavaCollectors.toImmutableListMultimap(mpackage -> createDeliveryOrderKey(
						mpackage,
						shipperTransportationId,
						pickupDate)))
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

	private static DeliveryOrderKey createDeliveryOrderKey(
			@NonNull final I_M_Package mpackage,
			final int shipperTransportationId,
			@NonNull final LocalDate pickupDate)
	{
		return DeliveryOrderKey.builder()
				.shipperId(mpackage.getM_Shipper_ID())
				.shipperTransportationId(shipperTransportationId)
				.fromOrgId(mpackage.getAD_Org_ID())
				.deliverToBPartnerId(mpackage.getC_BPartner_ID())
				.deliverToBPartnerLocationId(mpackage.getC_BPartner_Location_ID())
				.pickupDate(pickupDate)
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

	private void createAndSendDeliveryOrder(
			final DeliveryOrderKey deliveryOrderKey,
			final Collection<I_M_Package> mpackages)
	{
		final String shipperGatewayId = retrieveShipperGatewayId(deliveryOrderKey.getShipperId());
		final DeliveryOrderRepository deliveryOrderRepository = shipperRegistry.getDeliveryOrderRepository(shipperGatewayId);

		final Set<Integer> mpackageIds = mpackages.stream().map(I_M_Package::getM_Package_ID).collect(ImmutableSet.toImmutableSet());

		final CreateDraftDeliveryOrderRequest request = CreateDraftDeliveryOrderRequest.builder()
				.deliveryOrderKey(deliveryOrderKey)
				.grossWeightInKg(computeGrossWeightInKg(mpackages))
				.mpackageIds(mpackageIds)
				.packageContentDescription(computePackageContentDescription(mpackages))
				.build();

		final DraftDeliveryOrderCreator shipperGatewayService = shipperRegistry.getShipperGatewayService(shipperGatewayId);

		DeliveryOrder deliveryOrder = shipperGatewayService.createDraftDeliveryOrder(request);

		deliveryOrder = deliveryOrderRepository.save(deliveryOrder);
		DeliveryOrderWorkpackageProcessor.enqueueOnTrxCommit(deliveryOrder.getRepoId(), shipperGatewayId);
	}

	private String retrieveShipperGatewayId(final int shipperId)
	{
		final I_M_Shipper shipper = loadOutOfTrx(shipperId, I_M_Shipper.class);
		return Check.assumeNotEmpty(
				shipper.getShipperGateway(),
				"The given shipper with M_Shipper_ID={} has an empty ShipperGateway value; shipper={}", shipperId, shipper);
	}

	public boolean hasServiceSupport(String shipperGatewayId)
	{
		return shipperRegistry.hasServiceSupport(shipperGatewayId);
	}

}
