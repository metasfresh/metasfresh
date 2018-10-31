package de.metas.handlingunits.client.terminal.select.api.impl;

/*
 * #%L
 * de.metas.handlingunits.client
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;

import de.metas.handlingunits.client.terminal.select.api.IPOSFiltering;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.util.Services;

public abstract class AbstractFiltering implements IPOSFiltering
{
	protected final Comparator<I_C_Order> ordersComparator = Services.get(IQueryBL.class)
			.createQueryOrderByBuilder(I_C_Order.class)
			.addColumn(I_C_Order.COLUMNNAME_DocumentNo, Direction.Ascending, Nulls.Last)
			.createQueryOrderBy()
			.getComparator(I_C_Order.class);

	protected final Comparator<I_C_BPartner> bpartnersComparator = Services.get(IQueryBL.class)
			.createQueryOrderByBuilder(I_C_BPartner.class)
			.addColumn(I_C_BPartner.COLUMNNAME_Name, Direction.Ascending, Nulls.Last)
			.createQueryOrderBy()
			.getComparator(I_C_BPartner.class);

	@Override
	public List<I_C_BPartner> getBPartners(final List<IPOSTableRow> rows)
	{
		if (rows == null || rows.isEmpty())
		{
			return Collections.emptyList();
		}

		final Set<Integer> seenBpIds = new HashSet<Integer>();
		final List<I_C_BPartner> bpartners = new ArrayList<I_C_BPartner>();

		for (final IPOSTableRow row : rows)
		{
			final I_C_BPartner bpartner = row.getC_BPartner();
			if (bpartner == null)
			{
				continue;
			}
			final int bpartnerId = bpartner.getC_BPartner_ID();
			if (!seenBpIds.add(bpartnerId))
			{
				// already added
				continue;
			}

			bpartners.add(bpartner);
		}

		Collections.sort(bpartners, bpartnersComparator);

		return bpartners;
	}

	@Override
	public List<I_C_Order> getOrders(final List<IPOSTableRow> rows)
	{
		if (rows == null || rows.isEmpty())
		{
			return Collections.emptyList();
		}

		final Set<Integer> seenIds = new HashSet<Integer>();
		final List<I_C_Order> orders = new ArrayList<I_C_Order>();

		for (final IPOSTableRow row : rows)
		{
			final I_C_Order order = row.getC_Order();
			final int orderId = order.getC_Order_ID();
			if (!seenIds.add(orderId))
			{
				// already added
				continue;
			}

			orders.add(order);
		}

		Collections.sort(orders, ordersComparator);

		return orders;
	}

	@Override
	public List<IPOSTableRow> filter(final List<IPOSTableRow> rows, final Predicate<IPOSTableRow> filter)
	{
		if (rows == null || rows.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<IPOSTableRow> result = new ArrayList<IPOSTableRow>();
		for (final IPOSTableRow row : rows)
		{
			if (!filter.test(row))
			{
				continue;
			}
			result.add(row);
		}

		return result;
	}
}
