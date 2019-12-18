package de.metas.shipper.gateway.go;

import java.util.List;
import java.util.Set;

import de.metas.mpackage.PackageId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder;
import de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder_Package;
import de.metas.shipper.gateway.go.schema.GOOrderStatus;
import de.metas.shipper.gateway.go.schema.GOPaidMode;
import de.metas.shipper.gateway.go.schema.GOSelfDelivery;
import de.metas.shipper.gateway.go.schema.GOSelfPickup;
import de.metas.shipper.gateway.go.schema.GOShipperProduct;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderRepository;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.HWBNumber;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.OrderStatus;
import de.metas.util.Check;
import de.metas.util.Services;
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

/**
 * Repository used to save and load {@link DeliveryOrder}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Repository
public class GODeliveryOrderRepository implements DeliveryOrderRepository
{
	/**
	 * NOTE to dev: keep in sync with {@link #toDeliveryOrderPO(DeliveryOrder)}
	 */
	private DeliveryOrder toDeliveryOrder(@NonNull final I_GO_DeliveryOrder orderPO)
	{
		final Set<PackageId> mpackageIds = retrieveGODeliveryOrderPackageIds(orderPO.getGO_DeliveryOrder_ID());

		final GoDeliveryOrderData goDeliveryOrderData = GoDeliveryOrderData.builder()
				.hwbNumber(HWBNumber.ofNullable(orderPO.getGO_HWBNumber()))
				.receiptConfirmationPhoneNumber(null)
				.paidMode(GOPaidMode.forCode(orderPO.getGO_PaidMode()))
				.selfPickup(GOSelfPickup.forCode(orderPO.getGO_SelfPickup()))
				.selfDelivery(GOSelfDelivery.forCode(orderPO.getGO_SelfDelivery()))
				.build();

		return DeliveryOrder.builder()
				.id(DeliveryOrderId.ofRepoId(orderPO.getGO_DeliveryOrder_ID()))
				.shipperId(ShipperId.ofRepoId(orderPO.getM_Shipper_ID()))
				.shipperTransportationId(ShipperTransportationId.ofRepoId(orderPO.getM_ShipperTransportation_ID()))
				//
				.orderId(GOUtils.createOrderIdOrNull(orderPO.getGO_AX4Number()))
				.customDeliveryData(goDeliveryOrderData)
				.orderStatus(GOOrderStatus.forNullableCode(orderPO.getGO_OrderStatus()))
				//
				.shipperProduct(GOShipperProduct.forCode(orderPO.getGO_ServiceType()))
				//
				// Pickup
				.pickupAddress(GODeliveryOrderConverters.pickupAddressFromPO(orderPO))
				.pickupDate(GODeliveryOrderConverters.pickupDateFromPO(orderPO))
				.pickupNote(orderPO.getGO_PickupNote())
				//
				// Delivery
				.deliveryAddress(GODeliveryOrderConverters.deliveryAddressFromPO(orderPO))
				.deliveryDate(GODeliveryOrderConverters.deliveryDateFromPO(orderPO))
				.deliveryContact(GODeliveryOrderConverters.deliveryContactFromPO(orderPO))
				.deliveryNote(orderPO.getGO_DeliverToNote())
				.customerReference(orderPO.getGO_CustomerReference())
				//
				// Delivery content
				.deliveryPosition(GODeliveryOrderConverters.deliveryPositionFromPO(orderPO, mpackageIds))
				//
				.build();
	}

	/**
	 * NOTE to dev: keep in sync with {@link #toDeliveryOrder(I_GO_DeliveryOrder)}
	 */
	private I_GO_DeliveryOrder toDeliveryOrderPO(@NonNull final DeliveryOrder order)
	{
		I_GO_DeliveryOrder orderPO = null;
		if (order.getId() != null)
		{
			orderPO = InterfaceWrapperHelper.load(order.getId(), I_GO_DeliveryOrder.class);
		}

		final OrderId orderId = order.getOrderId();
		if (orderPO == null && orderId != null)
		{
			orderPO = retrieveGODeliveryOrderPOById(orderId);
		}

		if (orderPO == null)
		{
			orderPO = InterfaceWrapperHelper.newInstance(I_GO_DeliveryOrder.class);
		}

		orderPO.setM_Shipper_ID(order.getShipperId().getRepoId());
		orderPO.setM_ShipperTransportation_ID(order.getShipperTransportationId().getRepoId());

		final GoDeliveryOrderData goDeliveryOrderData = GoDeliveryOrderData.ofDeliveryOrder(order);
		final HWBNumber hwbNumber = goDeliveryOrderData.getHwbNumber();
		final OrderStatus orderStatus = order.getOrderStatus();

		orderPO.setGO_AX4Number(orderId != null ? orderId.getOrderIdAsString() : null);
		orderPO.setGO_HWBNumber(hwbNumber != null ? hwbNumber.getAsString() : null);
		orderPO.setGO_OrderStatus(orderStatus != null ? orderStatus.getCode() : null);
		orderPO.setProcessed(orderStatus != null && orderStatus.isFinalState());

		orderPO.setGO_ServiceType(order.getShipperProduct().getCode());
		orderPO.setGO_PaidMode(goDeliveryOrderData.getPaidMode().getCode());

		//
		// Pickup
		GODeliveryOrderConverters.pickupAddressToPO(orderPO, order.getPickupAddress());
		GODeliveryOrderConverters.pickupDateToPO(orderPO, order.getPickupDate());
		orderPO.setGO_PickupNote(order.getPickupNote());
		orderPO.setGO_SelfPickup(goDeliveryOrderData.getSelfPickup().getCode());

		//
		// Delivery
		GODeliveryOrderConverters.deliveryAddressToPO(orderPO, order.getDeliveryAddress());
		GODeliveryOrderConverters.deliveryDateToPO(orderPO, order.getDeliveryDate());
		GODeliveryOrderConverters.deliveryContactToPO(orderPO, order.getDeliveryContact());
		orderPO.setGO_DeliverToNote(order.getDeliveryNote());
		orderPO.setGO_SelfDelivery(goDeliveryOrderData.getSelfDelivery().getCode());
		orderPO.setGO_CustomerReference(order.getCustomerReference());

		//
		// Delivery content
		GODeliveryOrderConverters.deliveryPositionToPO(orderPO, GOUtils.getSingleDeliveryPosition(order));

		return orderPO;
	}

	@Override
	public TableRecordReference toTableRecordReference(@NonNull final DeliveryOrder deliveryOrder)
	{
		final DeliveryOrderId deliveryOrderRepoId = deliveryOrder.getId();
		Check.assumeNotNull(deliveryOrderRepoId, "DeliveryOrder ID must not be null");
		return TableRecordReference.of(I_GO_DeliveryOrder.Table_Name, deliveryOrderRepoId);
	}

	@Override
	public DeliveryOrder getByRepoId(@NonNull final DeliveryOrderId deliveryOrderId)
	{
		final I_GO_DeliveryOrder orderPO = InterfaceWrapperHelper.load(deliveryOrderId, I_GO_DeliveryOrder.class);
		Check.assumeNotNull(orderPO, "GO delivery order shall exist for GO_DeliveryOrder_ID={}", deliveryOrderId);

		final DeliveryOrder deliveryOrder = toDeliveryOrder(orderPO);
		return deliveryOrder;
	}

	@Override
	public DeliveryOrder save(final DeliveryOrder order)
	{
		final I_GO_DeliveryOrder orderPO = toDeliveryOrderPO(order);
		InterfaceWrapperHelper.save(orderPO);

		saveAssignedPackageIds(orderPO.getGO_DeliveryOrder_ID(), GOUtils.getSingleDeliveryPosition(order).getPackageIds());

		return order.toBuilder()
				.id(DeliveryOrderId.ofRepoId(orderPO.getGO_DeliveryOrder_ID()))
				.build();
	}

	private I_GO_DeliveryOrder retrieveGODeliveryOrderPOById(@NonNull final OrderId orderId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_GO_DeliveryOrder.class)
				.addEqualsFilter(I_GO_DeliveryOrder.COLUMN_GO_AX4Number, orderId.getOrderIdAsString())
				.create()
				.firstOnly(I_GO_DeliveryOrder.class);
	}

	private void saveAssignedPackageIds(final int deliveryOrderRepoId, final Set<PackageId> packageIds)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Set<PackageId> prevPackageIds = retrieveGODeliveryOrderPackageIds(deliveryOrderRepoId);

		final Set<PackageId> packageIdsToDelete = Sets.difference(prevPackageIds, packageIds);
		if (!packageIdsToDelete.isEmpty())
		{
			queryBL.createQueryBuilder(I_GO_DeliveryOrder_Package.class)
					.addEqualsFilter(I_GO_DeliveryOrder_Package.COLUMN_GO_DeliveryOrder_ID, deliveryOrderRepoId)
					.addInArrayFilter(I_GO_DeliveryOrder_Package.COLUMN_M_Package_ID, packageIdsToDelete)
					.create()
					.delete();
		}

		final Set<PackageId> packageIdsToAdd = Sets.difference(packageIds, prevPackageIds);
		packageIdsToAdd.forEach(packageId -> createGODeliveryOrderPackage(deliveryOrderRepoId, packageId.getRepoId()));
	}

	private Set<PackageId> retrieveGODeliveryOrderPackageIds(final int deliveryOrderRepoId)
	{
		if (deliveryOrderRepoId <= 0)
		{
			return ImmutableSet.of();
		}

		final List<Integer> mpackageIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_GO_DeliveryOrder_Package.class)
				.addEqualsFilter(I_GO_DeliveryOrder_Package.COLUMN_GO_DeliveryOrder_ID, deliveryOrderRepoId)
				.create()
				.listDistinct(I_GO_DeliveryOrder_Package.COLUMNNAME_M_Package_ID, Integer.class);

		final ImmutableSet<PackageId> packageIds = mpackageIds.stream()
				.map(PackageId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
		return packageIds;
	}

	private void createGODeliveryOrderPackage(final int deliveryOrderRepoId, final int packageId)
	{
		final I_GO_DeliveryOrder_Package orderPackagePO = InterfaceWrapperHelper.newInstance(I_GO_DeliveryOrder_Package.class);
		orderPackagePO.setGO_DeliveryOrder_ID(deliveryOrderRepoId);
		orderPackagePO.setM_Package_ID(packageId);
		InterfaceWrapperHelper.save(orderPackagePO);
	}

	@Override
	public String getShipperGatewayId()
	{
		return GOConstants.SHIPPER_GATEWAY_ID;
	}
}
