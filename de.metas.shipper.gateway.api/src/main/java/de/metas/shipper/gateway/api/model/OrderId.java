package de.metas.shipper.gateway.api.model;

import org.adempiere.util.Check;

import lombok.EqualsAndHashCode;

/*
 * #%L
 * de.metas.shipper.gateway.api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@EqualsAndHashCode
public final class OrderId
{
	public static final OrderId of(final String orderIdStr)
	{
		return new OrderId(orderIdStr);
	}

	private final String orderIdStr;

	private OrderId(final String orderIdStr)
	{
		Check.assumeNotEmpty(orderIdStr, "orderIdStr is not empty");
		this.orderIdStr = orderIdStr;
	}

	@Override
	public String toString()
	{
		return getAsString();
	}

	public String getAsString()
	{
		return orderIdStr;
	}
}
