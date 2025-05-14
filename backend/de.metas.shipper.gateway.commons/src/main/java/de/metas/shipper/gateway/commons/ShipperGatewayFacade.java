package de.metas.shipper.gateway.commons;

import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.commons.async.DeliveryOrderWorkpackageProcessor;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.CreateDraftDeliveryOrderRequest;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.CreateDraftDeliveryOrderRequest.PackageInfo;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.DeliveryOrderKey;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderCreateRequest;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMPrecision;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final UOMPrecision kgPrecision = uomDAO.getStandardPrecision(uomDAO.getUomIdByX12DE355(X12DE355.KILOGRAM));

	public ShipperGatewayFacade(@NonNull final ShipperGatewayServicesRegistry shipperRegistry)
	{
		this.shipperRegistry = shipperRegistry;
	}

	public void createAndSendDeliveryOrdersForPackages(@NonNull final DeliveryOrderCreateRequest request)
	{
		final LocalDate pickupDate = request.getPickupDate();
		final ShipperTransportationId shipperTransportationId = request.getShipperTransportationId();
		final LocalTime timeFrom = request.getTimeFrom();
		final LocalTime timeTo = request.getTimeTo();
		final AsyncBatchId asyncBatchId = request.getAsyncBatchId();

		retrievePackagesByIds(request.getPackageIds())
				.stream()
				.collect(GuavaCollectors.toImmutableListMultimap(mpackage -> createDeliveryOrderKey(
						mpackage,
						shipperTransportationId,
						pickupDate,
						timeFrom,
						timeTo,
						asyncBatchId)))
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

	@NonNull
	private static DeliveryOrderKey createDeliveryOrderKey(
			@NonNull final I_M_Package mpackage,
			final ShipperTransportationId shipperTransportationId,
			@NonNull final LocalDate pickupDate,
			@NonNull final LocalTime timeFrom,
			@NonNull final LocalTime timeTo,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		return DeliveryOrderKey.builder()
				.shipperId(ShipperId.ofRepoId(mpackage.getM_Shipper_ID()))
				.shipperTransportationId(shipperTransportationId)
				.fromOrgId(mpackage.getAD_Org_ID())
				.deliverToBPartnerId(mpackage.getC_BPartner_ID())
				.deliverToBPartnerLocationId(mpackage.getC_BPartner_Location_ID())
				.pickupDate(pickupDate)
				.timeFrom(timeFrom)
				.timeTo(timeTo)
				.asyncBatchId(asyncBatchId)
				.build();
	}

	private Optional<BigDecimal> extractWeightInKg(@NonNull final I_M_Package mpackage)
	{
		if (InterfaceWrapperHelper.isNull(mpackage, I_M_Package.COLUMNNAME_PackageWeight))
		{
			return Optional.empty();
		}

		final BigDecimal weightInKg = kgPrecision.round(mpackage.getPackageWeight()); // we assume it's in Kg
		return weightInKg.signum() > 0 ? Optional.of(weightInKg) : Optional.empty();
	}

	private void createAndSendDeliveryOrder(
			@NonNull final DeliveryOrderKey deliveryOrderKey,
			@NonNull final Collection<I_M_Package> mpackages)
	{
		final ShipperId shipperId = deliveryOrderKey.getShipperId();
		final String shipperGatewayId = retrieveShipperGatewayId(shipperId);
		final DeliveryOrderService deliveryOrderRepository = shipperRegistry.getDeliveryOrderService(shipperGatewayId);

		final ImmutableSet<PackageInfo> packageInfos = mpackages.stream()
				.map(mpackage -> PackageInfo.builder()
						.packageId(PackageId.ofRepoId(mpackage.getM_Package_ID()))
						.poReference(mpackage.getPOReference())
						.description(StringUtils.trimBlankToNull(mpackage.getDescription()))
						.weightInKg(extractWeightInKg(mpackage).orElse(null))
						.build())
				.collect(ImmutableSet.toImmutableSet());

		final CreateDraftDeliveryOrderRequest request = CreateDraftDeliveryOrderRequest.builder()
				.deliveryOrderKey(deliveryOrderKey)
				.packageInfos(packageInfos)
				.build();

		final DraftDeliveryOrderCreator shipperGatewayService = shipperRegistry.getShipperGatewayService(shipperGatewayId);

		DeliveryOrder deliveryOrder = shipperGatewayService.createDraftDeliveryOrder(request);

		deliveryOrder = deliveryOrderRepository.save(deliveryOrder);
		DeliveryOrderWorkpackageProcessor.enqueueOnTrxCommit(deliveryOrder.getId().getRepoId(), shipperGatewayId, deliveryOrderKey.getAsyncBatchId());
	}

	private String retrieveShipperGatewayId(final ShipperId shipperId)
	{
		final I_M_Shipper shipper = Services.get(IShipperDAO.class).getById(shipperId);
		return Check.assumeNotEmpty(
				shipper.getShipperGateway(),
				"The given shipper with M_Shipper_ID={} has an empty ShipperGateway value; shipper={}", shipperId, shipper);
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean hasServiceSupport(@NonNull final String shipperGatewayId)
	{
		return shipperRegistry.hasServiceSupport(shipperGatewayId);
	}

}
