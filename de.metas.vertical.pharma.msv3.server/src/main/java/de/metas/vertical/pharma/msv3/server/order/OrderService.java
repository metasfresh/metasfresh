package de.metas.vertical.pharma.msv3.server.order;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderStatus;
import de.metas.vertical.pharma.msv3.protocol.order.OrderStatusResponse;
import de.metas.vertical.pharma.msv3.protocol.types.Id;

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
	private final ConcurrentHashMap<Id, OrderCreateResponse> mockedOrdersById = new ConcurrentHashMap<>();

	public OrderCreateResponse createOrder(final OrderCreateRequest request)
	{
		// TODO
		// throw new UnsupportedOperationException();
		return getMockResponse(request);
	}

	private OrderCreateResponse getMockResponse(final OrderCreateRequest request)
	{
		return mockedOrdersById.compute(request.getOrderId(), (orderId, existingOrder) -> {
			if (existingOrder != null)
			{
				throw new RuntimeException("An order with ID " + orderId + " already exists");
			}
			return createMockResponse(request);
		});
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

	public OrderStatusResponse getOrderStatus(final Id orderId)
	{
		final OrderCreateResponse order = mockedOrdersById.get(orderId);
		if (order == null)
		{
			throw new RuntimeException("No order found for ID " + orderId);
		}

		return OrderStatusResponse.builder()
				.orderId(order.getOrderId())
				.supportId(order.getSupportId())
				.orderStatus(OrderStatus.RESPONSE_AVAILABLE)
				.orderPackages(order.getOrderPackages())
				.build();
	}
}
