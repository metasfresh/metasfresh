package de.metas.vertical.pharma.msv3.server.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateError;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponse;
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
import de.metas.vertical.pharma.msv3.server.peer.service.MSV3ServerPeerService;
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

	@Autowired
	private MSV3ServerPeerService msv3ServerPeerService;

	@Transactional
	public OrderCreateResponse createOrder(final OrderCreateRequest request)
	{
		final JpaOrder jpaOrder = createJpaOrder(request);
		// jpaOrdersRepo.save(jpaOrder);

		jpaOrder.markSyncSent();
		jpaOrdersRepo.save(jpaOrder);
		msv3ServerPeerService.publishOrderCreateRequest(request);

		return createOrderCreateResponse(jpaOrder);
	}

	public void confirmOrderSavedOnPeerServer(@NonNull final OrderCreateResponse response)
	{
		if (response.isError())
		{
			final OrderCreateError error = response.getError();
			final JpaOrder jpaOrder = getJpaOrder(error.getOrderId(), error.getBpartnerId());
			jpaOrder.markSyncError(error.getErrorMsg());
			jpaOrdersRepo.save(jpaOrder);
		}
		else
		{
			final OrderResponse order = response.getOrder();
			final JpaOrder jpaOrder = getJpaOrder(order.getOrderId(), order.getBpartnerId());
			jpaOrder.markSyncAck();

			final ImmutableMap<String, Integer> itemUuid2olCandId = order.getOrderPackages()
					.stream()
					.flatMap(orderResponsePackage -> orderResponsePackage.getItems().stream())
					.collect(ImmutableMap.toImmutableMap(
							item -> item.getId().getValueAsString(),
							OrderResponsePackageItem::getOlCandId));

			jpaOrder.visitItems(jpaOrderPackageItem -> {
				final Integer olCandId = itemUuid2olCandId.get(jpaOrderPackageItem.getUuid());
				if (olCandId != null && olCandId > 0)
				{
					jpaOrderPackageItem.setOl_cand_id(olCandId);
				}
			});

			jpaOrdersRepo.save(jpaOrder);
		}
	}

	private JpaOrder createJpaOrder(final OrderCreateRequest request)
	{
		final JpaOrder jpaOrder = new JpaOrder();
		jpaOrder.setBpartnerId(request.getBpartnerId().getBpartnerId());
		jpaOrder.setBpartnerLocationId(request.getBpartnerId().getBpartnerLocationId());
		jpaOrder.setDocumentNo(request.getOrderId().getValueAsString());
		jpaOrder.setSupportId(request.getSupportId().getValueAsInt());
		jpaOrder.setOrderStatus(OrderStatus.UNKNOWN_ID);
		jpaOrder.setNightOperation(false);
		jpaOrder.addOrderPackages(request.getOrderPackages().stream()
				.map(this::createJpaOrderPackage)
				.collect(ImmutableList.toImmutableList()));
		return jpaOrder;
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
		jpaOrderPackageItem.setUuid(orderPackageItem.getId().getValueAsString());
		jpaOrderPackageItem.setPzn(orderPackageItem.getPzn().getValueAsLong());
		jpaOrderPackageItem.setQty(orderPackageItem.getQty().getValueAsInt());
		jpaOrderPackageItem.setDeliverySpecifications(orderPackageItem.getDeliverySpecifications());
		return jpaOrderPackageItem;
	}

	private OrderCreateResponse createOrderCreateResponse(final JpaOrder jpaOrder)
	{
		return OrderCreateResponse.ok(OrderResponse.builder()
				.bpartnerId(BPartnerId.of(jpaOrder.getBpartnerId(), jpaOrder.getBpartnerLocationId()))
				.orderId(Id.of(jpaOrder.getDocumentNo()))
				.supportId(SupportIDType.of(jpaOrder.getSupportId()))
				.nightOperation(jpaOrder.getNightOperation())
				.orderPackages(jpaOrder.getOrderPackages().stream()
						.map(this::createOrderResponsePackage)
						.collect(ImmutableList.toImmutableList()))
				.build());
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
				.id(Id.of(jpaOrderPackageItem.getUuid()))
				.pzn(PZN.of(jpaOrderPackageItem.getPzn()))
				.qty(Quantity.of(jpaOrderPackageItem.getQty()))
				.deliverySpecifications(jpaOrderPackageItem.getDeliverySpecifications())
				.build();
	}

	public OrderStatusResponse getOrderStatus(@NonNull final Id orderId, @NonNull final BPartnerId bpartnerId)
	{
		final JpaOrder jpaOrder = getJpaOrder(orderId, bpartnerId);
		return createOrderStatusResponse(jpaOrder);
	}

	private JpaOrder getJpaOrder(@NonNull final Id orderId, @NonNull final BPartnerId bpartnerId)
	{
		final JpaOrder jpaOrder = jpaOrdersRepo.findByDocumentNoAndBpartnerId(orderId.getValueAsString(), bpartnerId.getBpartnerId());
		if (jpaOrder == null)
		{
			throw new RuntimeException("No order found for id='" + orderId + "' and bpartnerId='" + bpartnerId + "'");
		}
		return jpaOrder;
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

	public List<OrderCreateResponse> getOrders()
	{
		return jpaOrdersRepo.findAll()
				.stream()
				.map(this::createOrderCreateResponse)
				.collect(ImmutableList.toImmutableList());
	}
}
