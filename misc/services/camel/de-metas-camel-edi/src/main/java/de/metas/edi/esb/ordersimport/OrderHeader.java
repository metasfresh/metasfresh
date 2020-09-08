/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.ordersimport;

import java.util.ArrayList;
import java.util.List;

import de.metas.edi.esb.ordersimport.compudata.H100;
import de.metas.edi.esb.ordersimport.compudata.H110;
import de.metas.edi.esb.ordersimport.compudata.H120;
import de.metas.edi.esb.ordersimport.compudata.H130;
import de.metas.edi.esb.ordersimport.compudata.T100;

public class OrderHeader
{
	private final H100 h100;

	private final List<H110> h110Lines = new ArrayList<H110>();
	private final List<H120> h120Lines = new ArrayList<H120>();
	private final List<H130> h130Lines = new ArrayList<H130>();
	private final List<OrderLine> orderLines = new ArrayList<OrderLine>();
	private final List<T100> t100Lines = new ArrayList<T100>();

	public OrderHeader(final H100 h100)
	{
		this.h100 = h100;
	}

	public void addH110(final H110 h110)
	{
		h110Lines.add(h110);
	}

	public void addH120(final H120 h120)
	{
		h120Lines.add(h120);
	}

	public void addH130(final H130 h130)
	{
		h130Lines.add(h130);
	}

	public void addOrderLine(final OrderLine orderLine)
	{
		orderLines.add(orderLine);
	}

	public void addT100(final T100 t100)
	{
		t100Lines.add(t100);
	}

	public H100 getH100()
	{
		return h100;
	}

	public List<H110> getH110Lines()
	{
		return h110Lines;
	}

	public List<H120> getH120Lines()
	{
		return h120Lines;
	}

	public List<H130> getH130Lines()
	{
		return h130Lines;
	}

	public List<OrderLine> getOrderLines()
	{
		return orderLines;
	}

	public List<T100> getT100Lines()
	{
		return t100Lines;
	}
}
