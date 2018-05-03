package de.metas.shipper.gateway.go;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
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
import de.metas.shipper.gateway.go.schema.GOServiceType;
import de.metas.shipper.gateway.spi.DeliveryOrderRepository;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.HWBNumber;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.OrderStatus;
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
 *
 */
@Repository
public class GODeliveryOrderRepository implements DeliveryOrderRepository
{
	/**
	 * NOTE to dev: keep in sync with {@link #toDeliveryOrderPO(DeliveryOrder)}
	 */
	private DeliveryOrder toDeliveryOrder(@NonNull final I_GO_DeliveryOrder orderPO)
	{
		final Set<Integer> mpackageIds = retrieveGODeliveryOrderPackageIds(orderPO.getGO_DeliveryOrder_ID());

		GoDeliveryOrderData goDeliveryOrderData = GoDeliveryOrderData.builder()
				.hwbNumber(HWBNumber.ofNullable(orderPO.getGO_HWBNumber()))
				.receiptConfirmationPhoneNumber(null)
				.build();

		return DeliveryOrder.builder()
				.repoId(orderPO.getGO_DeliveryOrder_ID())
				.shipperId(orderPO.getM_Shipper_ID())
				//
				.orderId(GOUtils.createOrderIdOrNull(orderPO.getGO_AX4Number()))
				.customDeliveryOrderData(goDeliveryOrderData)
				.orderStatus(GOOrderStatus.forNullableCode(orderPO.getGO_OrderStatus()))
				//
				.serviceType(GOServiceType.forCode(orderPO.getGO_ServiceType()))
				.paidMode(GOPaidMode.forCode(orderPO.getGO_PaidMode()))
				//
				// Pickup
				.pickupAddress(GODeliveryOrderConverters.pickupAddressFromPO(orderPO))
				.pickupDate(GODeliveryOrderConverters.pickupDateFromPO(orderPO))
				.pickupNote(orderPO.getGO_PickupNote())
				.selfPickup(GOSelfPickup.forCode(orderPO.getGO_SelfPickup()))
				//
				// Delivery
				.deliveryAddress(GODeliveryOrderConverters.deliveryAddressFromPO(orderPO))
				.deliveryDate(GODeliveryOrderConverters.deliveryDateFromPO(orderPO))
				.deliveryContact(GODeliveryOrderConverters.deliveryContactFromPO(orderPO))
				.deliveryNote(orderPO.getGO_DeliverToNote())
				.selfDelivery(GOSelfDelivery.forCode(orderPO.getGO_SelfDelivery()))
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
	private I_GO_DeliveryOrder toDeliveryOrderPO(final DeliveryOrder order)
	{
		I_GO_DeliveryOrder orderPO = null;
		if (order.getRepoId() > 0)
		{
			orderPO = InterfaceWrapperHelper.load(order.getRepoId(), I_GO_DeliveryOrder.class);
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

		orderPO.setM_Shipper_ID(order.getShipperId());

		final GoDeliveryOrderData goDeliveryOrderData = GoDeliveryOrderData.ofDeliveryOrder(order);
		final HWBNumber hwbNumber = goDeliveryOrderData.getHwbNumber();
		final OrderStatus orderStatus = order.getOrderStatus();

		orderPO.setGO_AX4Number(orderId != null ? orderId.getOrderIdAsString() : null);
		orderPO.setGO_HWBNumber(hwbNumber != null ? hwbNumber.getAsString() : null);
		orderPO.setGO_OrderStatus(orderStatus != null ? orderStatus.getCode() : null);
		orderPO.setProcessed(orderStatus != null && orderStatus.isFinalState());

		orderPO.setGO_ServiceType(order.getServiceType().getCode());
		orderPO.setGO_PaidMode(order.getPaidMode().getCode());

		//
		// Pickup
		GODeliveryOrderConverters.pickupAddressToPO(orderPO, order.getPickupAddress());
		GODeliveryOrderConverters.pickupDateToPO(orderPO, order.getPickupDate());
		orderPO.setGO_PickupNote(order.getPickupNote());
		orderPO.setGO_SelfPickup(order.getSelfPickup().getCode());

		//
		// Delivery
		GODeliveryOrderConverters.deliveryAddressToPO(orderPO, order.getDeliveryAddress());
		GODeliveryOrderConverters.deliveryDateToPO(orderPO, order.getDeliveryDate());
		GODeliveryOrderConverters.deliveryContactToPO(orderPO, order.getDeliveryContact());
		orderPO.setGO_DeliverToNote(order.getDeliveryNote());
		orderPO.setGO_SelfDelivery(order.getSelfDelivery().getCode());
		orderPO.setGO_CustomerReference(order.getCustomerReference());

		//
		// Delivery content
		GODeliveryOrderConverters.deliveryPositionToPO(orderPO, GOUtils.getSingleDeliveryPosition(order));

		return orderPO;
	}

	@Override
	public TableRecordReference toTableRecordReference(final DeliveryOrder deliveryOrder)
	{
		final int deliveryOrderRepoId = deliveryOrder.getRepoId();
		Check.assume(deliveryOrderRepoId > 0, "deliveryOrderRepoId > 0 for {}", deliveryOrder);
		return TableRecordReference.of(I_GO_DeliveryOrder.Table_Name, deliveryOrderRepoId);
	}

	@Override
	public DeliveryOrder getByRepoId(final int deliveryOrderRepoId)
	{
		Check.assume(deliveryOrderRepoId > 0, "deliveryOrderRepoId > 0");
		final I_GO_DeliveryOrder orderPO = InterfaceWrapperHelper.load(deliveryOrderRepoId, I_GO_DeliveryOrder.class);
		Check.assumeNotNull(orderPO, "GO delivery order shall exist for GO_DeliveryOrder_ID={}", deliveryOrderRepoId);

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
				.repoId(orderPO.getGO_DeliveryOrder_ID())
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

	private void saveAssignedPackageIds(final int deliveryOrderRepoId, final Set<Integer> packageIds)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Set<Integer> prevPackageIds = retrieveGODeliveryOrderPackageIds(deliveryOrderRepoId);

		final Set<Integer> packageIdsToDelete = Sets.difference(prevPackageIds, packageIds);
		if (!packageIdsToDelete.isEmpty())
		{
			queryBL.createQueryBuilder(I_GO_DeliveryOrder_Package.class)
					.addEqualsFilter(I_GO_DeliveryOrder_Package.COLUMN_GO_DeliveryOrder_ID, deliveryOrderRepoId)
					.addInArrayFilter(I_GO_DeliveryOrder_Package.COLUMN_M_Package_ID, packageIdsToDelete)
					.create()
					.delete();
		}

		final Set<Integer> packageIdsToAdd = Sets.difference(packageIds, prevPackageIds);
		packageIdsToAdd.forEach(packageId -> createGODeliveryOrderPackage(deliveryOrderRepoId, packageId));
	}

	private Set<Integer> retrieveGODeliveryOrderPackageIds(final int deliveryOrderRepoId)
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

		return ImmutableSet.copyOf(mpackageIds);
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
