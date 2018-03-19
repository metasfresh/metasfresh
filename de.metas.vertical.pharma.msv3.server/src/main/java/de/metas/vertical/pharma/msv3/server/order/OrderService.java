package de.metas.vertical.pharma.msv3.server.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderStatus;
import de.metas.vertical.pharma.msv3.protocol.order.OrderStatusResponse;
import de.metas.vertical.pharma.msv3.protocol.order.SupportIDType;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.msv3.server.order.jpa.JpaOrder;
import de.metas.vertical.pharma.msv3.server.order.jpa.JpaOrderPackage;
import de.metas.vertical.pharma.msv3.server.order.jpa.JpaOrderPackageItem;
import de.metas.vertical.pharma.msv3.server.order.jpa.JpaOrderRepository;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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
public class OrderService
{
	@Autowired
	private JpaOrderRepository jpaOrdersRepo;

	@Transactional
	public OrderCreateResponse createOrder(final OrderCreateRequest request)
	{
		final JpaOrder jpaOrder = new JpaOrder();
		jpaOrder.setBpartnerId(request.getBpartnerId().getValueAsInt());
		jpaOrder.setDocumentNo(request.getOrderId().getValueAsString());
		jpaOrder.setSupportId(request.getSupportId().getValueAsInt());
		jpaOrder.setOrderStatus(OrderStatus.UNKNOWN_ID);
		jpaOrder.setNightOperation(false);
		jpaOrder.addOrderPackages(request.getOrderPackages().stream()
				.map(this::createJpaOrderPackage)
				.collect(ImmutableList.toImmutableList()));
		jpaOrdersRepo.save(jpaOrder);

		return createOrderCreateResponse(jpaOrder);
	}

	private JpaOrderPackage createJpaOrderPackage(final OrderCreateRequestPackage orderPackage)
	{
		final JpaOrderPackage jpaOrderPackage = new JpaOrderPackage();
		jpaOrderPackage.setDocumentNo(orderPackage.getId().getValueAsString());
		jpaOrderPackage.setOrderType(orderPackage.getOrderType());
		jpaOrderPackage.setOrderIdentification(orderPackage.getOrderIdentification());
		jpaOrderPackage.setSupportId(orderPackage.getSupportId().getValueAsInt());
		jpaOrderPackage.setPackingMaterialId(orderPackage.getPackingMaterialId());
		jpaOrderPackage.addItems(orderPackage.getItems().stream()
				.map(this::createJpaOrderPackageItem)
				.collect(ImmutableList.toImmutableList()));
		return jpaOrderPackage;
	}

	private JpaOrderPackageItem createJpaOrderPackageItem(final OrderCreateRequestPackageItem orderPackageItem)
	{
		JpaOrderPackageItem jpaOrderPackageItem = new JpaOrderPackageItem();
		jpaOrderPackageItem.setPzn(orderPackageItem.getPzn().getValueAsLong());
		jpaOrderPackageItem.setQty(orderPackageItem.getQty().getValueAsInt());
		jpaOrderPackageItem.setDeliverySpecifications(orderPackageItem.getDeliverySpecifications());
		return jpaOrderPackageItem;
	}

	private OrderCreateResponse createOrderCreateResponse(final JpaOrder jpaOrder)
	{
		return OrderCreateResponse.builder()
				.bpartnerId(BPartnerId.of(jpaOrder.getBpartnerId()))
				.orderId(Id.of(jpaOrder.getDocumentNo()))
				.supportId(SupportIDType.of(jpaOrder.getSupportId()))
				.nightOperation(jpaOrder.getNightOperation())
				.orderPackages(jpaOrder.getOrderPackages().stream()
						.map(this::createOrderResponsePackage)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private OrderResponsePackage createOrderResponsePackage(final JpaOrderPackage jpaOrderPackage)
	{
		return OrderResponsePackage.builder()
				.id(Id.of(jpaOrderPackage.getDocumentNo()))
				.orderType(jpaOrderPackage.getOrderType())
				.orderIdentification(jpaOrderPackage.getOrderIdentification())
				.supportId(SupportIDType.of(jpaOrderPackage.getSupportId()))
				.packingMaterialId(jpaOrderPackage.getPackingMaterialId())
				.items(jpaOrderPackage.getItems().stream()
						.map(this::createOrderResponsePackageItem)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private OrderResponsePackageItem createOrderResponsePackageItem(final JpaOrderPackageItem jpaOrderPackageItem)
	{
		return OrderResponsePackageItem.builder()
				.pzn(PZN.of(jpaOrderPackageItem.getPzn()))
				.qty(Quantity.of(jpaOrderPackageItem.getQty()))
				.deliverySpecifications(jpaOrderPackageItem.getDeliverySpecifications())
				.build();
	}

	private static OrderCreateResponse createMockResponse(final OrderCreateRequest request)
	{
		return OrderCreateResponse.builder()
				.orderId(request.getOrderId())
				.supportId(request.getSupportId())
				.nightOperation(false)
				.orderPackages(request.getOrderPackages().stream()
						.map(orderPackage -> OrderResponsePackage.builder()
								.id(orderPackage.getId())
								.orderType(orderPackage.getOrderType())
								.orderIdentification(orderPackage.getOrderIdentification())
								.supportId(orderPackage.getSupportId())
								.packingMaterialId(orderPackage.getPackingMaterialId())
								.items(orderPackage.getItems().stream()
										.map(item -> OrderResponsePackageItem.builder()
												.pzn(item.getPzn())
												.qty(item.getQty())
												.deliverySpecifications(item.getDeliverySpecifications())
												.build())
										.collect(ImmutableList.toImmutableList()))
								.build())
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public OrderStatusResponse getOrderStatus(@NonNull final Id orderId, @NonNull final BPartnerId bpartnerId)
	{
		final JpaOrder jpaOrder = jpaOrdersRepo.findByDocumentNoAndBpartnerId(orderId.getValueAsString(), bpartnerId.getValueAsInt());
		if (jpaOrder == null)
		{
			throw new RuntimeException("No order found for id='" + orderId + "' and bpartnerId='" + bpartnerId + "'");
		}

		return createOrderStatusResponse(jpaOrder);
	}

	private OrderStatusResponse createOrderStatusResponse(final JpaOrder jpaOrder)
	{
		return OrderStatusResponse.builder()
				.orderId(Id.of(jpaOrder.getDocumentNo()))
				.supportId(SupportIDType.of(jpaOrder.getSupportId()))
				.orderStatus(jpaOrder.getOrderStatus())
				.orderPackages(jpaOrder.getOrderPackages().stream()
						.map(this::createOrderResponsePackage)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
