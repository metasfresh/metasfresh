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

import de.metas.edi.esb.ordersimport.compudata.H000;
import lombok.NonNull;
import lombok.Value;

@Value
public class OrderEDI
{
	private final H000 h000;

	private final List<OrderHeader> orderHeaders = new ArrayList<OrderHeader>();

	public OrderEDI(@NonNull final H000 h000)
	{
		this.h000 = h000;
	}

	public void addOrderHeader(@NonNull final OrderHeader orderHeader)
	{
		orderHeaders.add(orderHeader);
	}
}
