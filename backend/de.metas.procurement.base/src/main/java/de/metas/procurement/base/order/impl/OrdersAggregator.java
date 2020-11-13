package de.metas.procurement.base.order.impl;

import java.util.List;

import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.adempiere.model.I_C_Order;
import de.metas.util.collections.MapReduceAggregator;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class OrdersAggregator extends MapReduceAggregator<OrderHeaderAggregation, PurchaseCandidate>
{
	public static final OrdersAggregator newInstance(final IOrdersCollector collector)
	{
		return new OrdersAggregator(collector);
	}

	private final IOrdersCollector collector;

	private OrdersAggregator(final IOrdersCollector collector)
	{
		super();
		this.collector = collector;

		setItemAggregationKeyBuilder(new IAggregationKeyBuilder<PurchaseCandidate>()
		{
			@Override
			public String buildKey(final PurchaseCandidate item)
			{
				return item.getHeaderAggregationKey().toString();
			}

			@Override
			public boolean isSame(final PurchaseCandidate item1, final PurchaseCandidate item2)
			{
				throw new UnsupportedOperationException(); // shall not be called
			}

			@Override
			public List<String> getDependsOnColumnNames()
			{
				throw new UnsupportedOperationException(); // shall not be called
			}
		});
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	protected OrderHeaderAggregation createGroup(final Object itemHashKey, final PurchaseCandidate candidate)
	{
		return new OrderHeaderAggregation();
	}

	@Override
	protected void closeGroup(final OrderHeaderAggregation orderHeader)
	{
		I_C_Order order = orderHeader.build();
		if (order != null)
		{
			collector.add(order);
		}
	}

	@Override
	protected void addItemToGroup(final OrderHeaderAggregation orderHeader, final PurchaseCandidate candidate)
	{
		orderHeader.add(candidate);
	}
}
