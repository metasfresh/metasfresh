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

import de.metas.edi.esb.ordersimport.compudata.P100;
import de.metas.edi.esb.ordersimport.compudata.P110;

public class OrderLine
{
	private final P100 p100;

	private final List<P110> p110Lines = new ArrayList<P110>();

	public OrderLine(final P100 p100)
	{
		this.p100 = p100;
	}

	public void addP110(final P110 p110)
	{
		p110Lines.add(p110);
	}

	public P100 getP100()
	{
		return p100;
	}

	public List<P110> getP110Lines()
	{
		return p110Lines;
	}
}
