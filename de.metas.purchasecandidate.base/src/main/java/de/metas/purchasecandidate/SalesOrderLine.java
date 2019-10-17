package de.metas.purchasecandidate;

import java.time.ZonedDateTime;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

@Value
public class SalesOrderLine
{
	OrderAndLineId id;

	@Getter(AccessLevel.NONE)
	SalesOrder order;

	@Getter(AccessLevel.NONE)
	OrderLine orderLine;

	Quantity deliveredQty;

	@Builder
	private SalesOrderLine(
			@NonNull final SalesOrder order,
			@NonNull final OrderLine orderLine,
			@NonNull final Quantity deliveredQty)
	{
		this.order = order;
		this.orderLine = orderLine;
		this.deliveredQty = deliveredQty;

		final OrderLineId orderLineId = orderLine.getId();
		this.id = orderLineId != null ? OrderAndLineId.of(orderLine.getOrderId(), orderLineId) : null;
	}

	public ZonedDateTime getPreparationDate()
	{
		return order.getPreparationDate();
	}

	public OrgId getOrgId()
	{
		return orderLine.getOrgId();
	}

	public WarehouseId getWarehouseId()
	{
		return orderLine.getWarehouseId();
	}

	public int getLine()
	{
		return orderLine.getLine();
	}

	public ZonedDateTime getDatePromised()
	{
		return orderLine.getDatePromised();
	}

	public ProductId getProductId()
	{
		return orderLine.getProductId();
	}

	public AttributeSetInstanceId getAsiId()
	{
		return orderLine.getAsiId();
	}

	public Quantity getOrderedQty()
	{
		return orderLine.getOrderedQty();
	}

	public Money getPriceActual()
	{
		return orderLine.getPriceActual();
	}
}
