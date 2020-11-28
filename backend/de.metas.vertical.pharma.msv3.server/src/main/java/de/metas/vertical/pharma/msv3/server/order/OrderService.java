package de.metas.vertical.pharma.msv3.server.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItemId;
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
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3OrderSyncRequest;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3OrderSyncRequestPackage;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3OrderSyncRequestPackageItem;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3OrderSyncResponse;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3OrderSyncResponseItem;
import de.metas.vertical.pharma.msv3.server.peer.service.MSV3ServerPeerService;
import de.metas.vertical.pharma.msv3.server.stockAvailability.StockAvailabilityService;
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

	@Autowired
	private StockAvailabilityService stockAvailabilityService;

	@Transactional
	public OrderCreateResponse createOrder(final OrderCreateRequest request)
	{
		final OrderResponse order = createOrderResponse(request);

		final JpaOrder jpaOrder = createJpaOrder(order);
		jpaOrder.markSyncSent();
		jpaOrdersRepo.save(jpaOrder);

		msv3ServerPeerService.publishSyncOrderRequest(MSV3OrderSyncRequest.builder()
				.orderId(order.getOrderId())
				.bpartner(order.getBpartnerId())
				.orderPackages(order.getOrderPackages().stream()
						.map(orderPackage -> MSV3OrderSyncRequestPackage.builder()
								.items(orderPackage.getItems().stream()
										.map(orderPackageItem -> MSV3OrderSyncRequestPackageItem.builder()
												.id(orderPackageItem.getRequestId())
												.pzn(orderPackageItem.getPzn())
												.qty(orderPackageItem.getQty())
												.build())
										.collect(ImmutableList.toImmutableList()))
								.build())
						.collect(ImmutableList.toImmutableList()))
				.build());

		return OrderCreateResponse.ok(order);
	}

	@Transactional
	public void confirmOrderSavedOnPeerServer(@NonNull final MSV3OrderSyncResponse response)
	{
		if (response.isError())
		{
			final JpaOrder jpaOrder = getJpaOrder(response.getOrderId(), response.getBpartnerId());
			jpaOrder.markSyncError(response.getErrorMsg());
			jpaOrdersRepo.save(jpaOrder);
		}
		else
		{
			final JpaOrder jpaOrder = getJpaOrder(response.getOrderId(), response.getBpartnerId());
			jpaOrder.markSyncAck();

			final Map<String, Integer> olCandIdsByPackageItemId = response.getItems()
					.stream().collect(ImmutableMap.toImmutableMap(MSV3OrderSyncResponseItem::getOrderPackageItemIdAsString, MSV3OrderSyncResponseItem::getOlCandId));

			jpaOrder.visitItems(jpaOrderPackageItem -> {
				final Integer olCandId = olCandIdsByPackageItemId.get(jpaOrderPackageItem.getUuid());
				if (olCandId != null && olCandId > 0)
				{
					jpaOrderPackageItem.setMfOlCandId(olCandId);
				}
			});

			jpaOrdersRepo.save(jpaOrder);
		}
	}

	private JpaOrder createJpaOrder(final OrderResponse order)
	{
		final String documentNo = order.getOrderId().getValueAsString();
		final int bpartnerId = order.getBpartnerId().getBpartnerId();

		if (jpaOrdersRepo.existsByDocumentNoAndMfBpartnerId(documentNo, bpartnerId))
		{
			throw new RuntimeException("An order with same documentNo (" + documentNo + ") was already recorded");
		}

		final JpaOrder jpaOrder = new JpaOrder();
		jpaOrder.setMfBpartnerId(bpartnerId);
		jpaOrder.setMfBpartnerLocationId(order.getBpartnerId().getBpartnerLocationId());
		jpaOrder.setDocumentNo(documentNo);
		jpaOrder.setSupportId(order.getSupportId().getValueAsInt());
		jpaOrder.setOrderStatus(OrderStatus.UNKNOWN_ID);
		jpaOrder.setNightOperation(false);
		jpaOrder.addOrderPackages(order.getOrderPackages().stream()
				.map(this::createJpaOrderPackage)
				.collect(ImmutableList.toImmutableList()));
		return jpaOrder;
	}

	private JpaOrderPackage createJpaOrderPackage(final OrderResponsePackage orderPackage)
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

	private JpaOrderPackageItem createJpaOrderPackageItem(final OrderResponsePackageItem orderPackageItem)
	{
		JpaOrderPackageItem jpaOrderPackageItem = new JpaOrderPackageItem();
		jpaOrderPackageItem.setUuid(orderPackageItem.getRequestId().getValueAsString());
		jpaOrderPackageItem.setPzn(orderPackageItem.getPzn().getValueAsLong());
		jpaOrderPackageItem.setQty(orderPackageItem.getQty().getValueAsInt());
		jpaOrderPackageItem.setDeliverySpecifications(orderPackageItem.getDeliverySpecifications());
		return jpaOrderPackageItem;
	}

	private OrderResponse createOrderResponse(final OrderCreateRequest request)
	{
		return OrderResponse.builder()
				.bpartnerId(request.getBpartnerId())
				.orderId(request.getOrderId())
				.supportId(request.getSupportId())
				.nightOperation(false) // TODO
				.orderPackages(request.getOrderPackages().stream()
						.map(requestPackage -> createOrderResponsePackage(requestPackage, request.getBpartnerId()))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private OrderResponsePackage createOrderResponsePackage(final OrderCreateRequestPackage requestPackage, final BPartnerId bpartner)
	{
		return OrderResponsePackage.builder()
				.id(requestPackage.getId())
				.orderType(requestPackage.getOrderType())
				.orderIdentification(requestPackage.getOrderIdentification())
				.supportId(requestPackage.getSupportId())
				.packingMaterialId(requestPackage.getPackingMaterialId())
				.items(requestPackage.getItems().stream()
						.map(requestPackageItem -> createOrderResponsePackageItem(requestPackageItem, bpartner))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private OrderResponsePackageItem createOrderResponsePackageItem(final OrderCreateRequestPackageItem requestPackageItem, final BPartnerId bpartner)
	{
		final PZN pzn = requestPackageItem.getPzn();
		final Quantity qtyAvailable = stockAvailabilityService.getQtyAvailable(pzn, bpartner)
				.orElseThrow(() -> new RuntimeException("PZN not found: " + pzn));

		final Quantity qtyRequired = requestPackageItem.getQty();
		if (qtyRequired.compareTo(qtyAvailable) > 0)
		{
			throw new RuntimeException("Not available: PZN=" + pzn.getValueAsString() + ", Qty=" + qtyRequired.toJson());
		}

		return OrderResponsePackageItem.builder()
				.requestId(requestPackageItem.getId())
				.pzn(pzn)
				.qty(qtyRequired)
				.deliverySpecifications(requestPackageItem.getDeliverySpecifications())
				.build();
	}

	private OrderResponse createOrderResponse(final JpaOrder jpaOrder)
	{
		return OrderResponse.builder()
				.bpartnerId(BPartnerId.of(jpaOrder.getMfBpartnerId(), jpaOrder.getMfBpartnerLocationId()))
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
				.requestId(OrderCreateRequestPackageItemId.of(jpaOrderPackageItem.getUuid()))
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
		final JpaOrder jpaOrder = jpaOrdersRepo.findByDocumentNoAndMfBpartnerId(orderId.getValueAsString(), bpartnerId.getBpartnerId());
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

	public List<OrderResponse> getOrders()
	{
		return jpaOrdersRepo.findAll()
				.stream()
				.map(this::createOrderResponse)
				.collect(ImmutableList.toImmutableList());
	}
}
