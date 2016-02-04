package de.metas.edi.esb.bean.order;

/*
 * #%L
 * de.metas.edi.esb
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
import java.util.List;

import de.metas.edi.esb.pojo.order.compudata.H000;

public class OrderEDI
{
	private final H000 h000;

	private final List<OrderHeader> orderHeaders = new ArrayList<OrderHeader>();

	public OrderEDI(final H000 h000)
	{
		super();

		this.h000 = h000;
	}

	public void addOrderHeader(final OrderHeader orderHeader)
	{
		orderHeaders.add(orderHeader);
	}

	public H000 getH000()
	{
		return h000;
	}

	public List<OrderHeader> getOrderHeaders()
	{
		return orderHeaders;
	}
}
