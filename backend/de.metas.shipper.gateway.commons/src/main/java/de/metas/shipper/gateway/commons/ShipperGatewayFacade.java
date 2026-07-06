package de.metas.shipper.gateway.commons;

import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.shipping.CarrierProductId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.product.PackageDimensions;
import de.metas.shipper.gateway.commons.async.DeliveryOrderWorkpackageProcessor;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.CreateDraftDeliveryOrderRequest;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.CreateDraftDeliveryOrderRequest.PackageInfo;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.DeliveryOrderKey;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderCreateRequest;
import de.metas.shipper.gateway.spi.model.ResolvedCarrier;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.mpackage.PackageId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMPrecision;
import de.metas.uom.X12DE355;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Package;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
@RequiredArgsConstructor
public class ShipperGatewayFacade
{
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final ShipperRepository shipperRepository;
	@NonNull private final ShipperGatewayServicesRegistry shipperRegistry;
	@NonNull private final ShipmentScheduleRepository shipmentScheduleRepository;

	private final UOMPrecision kgPrecision = uomDAO.getStandardPrecision(uomDAO.getUomIdByX12DE355(X12DE355.KILOGRAM));

	public void createAndSendDeliveryOrdersForPackages(@NonNull final DeliveryOrderCreateRequest request)
	{
		final LocalDate pickupDate = request.getPickupDate();
		final ShipperTransportationId shipperTransportationId = request.getShipperTransportationId();
		final LocalTime timeFrom = request.getTimeFrom();
		final LocalTime timeTo = request.getTimeTo();
		final AsyncBatchId asyncBatchId = request.getAsyncBatchId();

		final Map<ShipmentScheduleId, ResolvedCarrier> carrierByScheduleId = request.getCarrierByScheduleId();

		retrievePackagesByIds(request.getPackageIds())
				.stream()
				.collect(GuavaCollectors.toImmutableListMultimap(mpackage -> createDeliveryOrderKey(
						mpackage,
						shipperTransportationId,
						pickupDate,
						timeFrom,
						timeTo,
						asyncBatchId,
						carrierByScheduleId)))
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
	private DeliveryOrderKey createDeliveryOrderKey(
			@NonNull final I_M_Package mpackage,
			final ShipperTransportationId shipperTransportationId,
			@NonNull final LocalDate pickupDate,
			@NonNull final LocalTime timeFrom,
			@NonNull final LocalTime timeTo,
			@Nullable final AsyncBatchId asyncBatchId,
			@NonNull final Map<ShipmentScheduleId, ResolvedCarrier> carrierByScheduleId)
	{
		final List<ShipmentSchedule> shipmentSchedules = retrieveShipmentSchedulesByPackageId(PackageId.ofRepoId(mpackage.getM_Package_ID()));
		if (shipmentSchedules.isEmpty())
		{
			throw new ShipperGatewayException("No shipment schedules found for package " + mpackage);
		}

		// Carrier values come from the request, where they were resolved from the shipment schedule
		// (SCHEDULE-SOURCED) in de.metas.handlingunits.base. Commons must not depend on the handlingunits
		// module (dependency cycle).
		final List<ResolvedCarrier> resolvedCarriers = shipmentSchedules.stream()
				.map(ShipmentSchedule::getId)
				.map(carrierByScheduleId::get)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		return DeliveryOrderKey.builder()
				.shipperId(ShipperId.ofRepoId(mpackage.getM_Shipper_ID()))
				.shipperTransportationId(shipperTransportationId)
				.fromOrgId(mpackage.getAD_Org_ID())
				.deliverToBPartnerId(mpackage.getC_BPartner_ID())
				.deliverToBPartnerLocationId(mpackage.getC_BPartner_Location_ID())
				.deliverToContactId(UserId.ofRepoIdOrNull(mpackage.getAD_User_ID()))
				.pickupDate(pickupDate)
				.timeFrom(timeFrom)
				.timeTo(timeTo)
				.carrierProductId(getCommonCarrierProductIdOrNull(resolvedCarriers))
				.carrierGoodsTypeId(getCommonCarrierGoodsTypeIdOrNull(resolvedCarriers))
				.carrierServices(getCarrierServices(resolvedCarriers))
				.asyncBatchId(asyncBatchId)
				.build();
	}

	@Nullable
	private CarrierGoodsTypeId getCommonCarrierGoodsTypeIdOrNull(final List<ResolvedCarrier> resolvedCarriers)
	{
		final Set<CarrierGoodsTypeId> goodsTypeIds = resolvedCarriers.stream()
				.map(ResolvedCarrier::getCarrierGoodsTypeId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		if (goodsTypeIds.size() > 1)
		{
			throw new ShipperGatewayException("No common CarrierGoodsTypeId found for resolved carriers: " + resolvedCarriers);
		}
		return goodsTypeIds.stream().findFirst().orElse(null);
	}

	@Nullable
	private CarrierProductId getCommonCarrierProductIdOrNull(final List<ResolvedCarrier> resolvedCarriers)
	{
		final Set<CarrierProductId> carrierProductIds = resolvedCarriers.stream()
				.map(ResolvedCarrier::getCarrierProductId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		if (carrierProductIds.size() > 1)
		{
			throw new ShipperGatewayException("No common CarrierProductId found for resolved carriers: " + resolvedCarriers);
		}
		return carrierProductIds.stream().findFirst().orElse(null);
	}

	private Set<CarrierServiceId> getCarrierServices(final List<ResolvedCarrier> resolvedCarriers)
	{
		return resolvedCarriers.stream()
				.flatMap(resolvedCarrier -> resolvedCarrier.getCarrierServices().stream())
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	private List<ShipmentSchedule> retrieveShipmentSchedulesByPackageId(@NonNull final PackageId packageId)
	{
		return shipmentScheduleRepository.loadByPackageId(packageId);
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
		final ShipperGatewayId shipperGatewayId = getShipperGatewayId(shipperId);
		final DeliveryOrderService deliveryOrderRepository = shipperRegistry.getDeliveryOrderService(shipperGatewayId);

		final ImmutableSet<PackageInfo> packageInfos = mpackages.stream()
				.map(mpackage -> PackageInfo.builder()
						.packageId(PackageId.ofRepoId(mpackage.getM_Package_ID()))
						.poReference(mpackage.getPOReference())
						.description(StringUtils.trimBlankToNull(mpackage.getDescription()))
						.weightInKg(extractWeightInKg(mpackage).orElse(null))
						.packageDimension(extractPackageDimensions(mpackage))
						.build())
				.collect(ImmutableSet.toImmutableSet());

		final CreateDraftDeliveryOrderRequest request = CreateDraftDeliveryOrderRequest.builder()
				.deliveryOrderKey(deliveryOrderKey)
				.packageInfos(packageInfos)
				.build();

		final DraftDeliveryOrderCreator shipperGatewayService = shipperRegistry.getShipperGatewayService(shipperGatewayId);

		DeliveryOrder deliveryOrder = shipperGatewayService.createDraftDeliveryOrder(request);

		deliveryOrder = deliveryOrderRepository.save(deliveryOrder);
		DeliveryOrderWorkpackageProcessor.enqueueOnTrxCommit(deliveryOrder.getId(), shipperGatewayId, deliveryOrderKey.getAsyncBatchId());
	}

	private static PackageDimensions extractPackageDimensions(@NonNull final I_M_Package mpackage)
	{
		return PackageDimensions.builder()
				.lengthInCM(mpackage.getLengthInCm())
				.widthInCM(mpackage.getWidthInCm())
				.heightInCM(mpackage.getHeightInCm())
				.build();
	}

	private ShipperGatewayId getShipperGatewayId(final ShipperId shipperId)
	{
		return shipperRepository.getShipperGatewayId(shipperId).orElseThrow();
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean hasServiceSupport(@NonNull final ShipperGatewayId shipperGatewayId)
	{
		return shipperRegistry.hasServiceSupport(shipperGatewayId);
	}

}
