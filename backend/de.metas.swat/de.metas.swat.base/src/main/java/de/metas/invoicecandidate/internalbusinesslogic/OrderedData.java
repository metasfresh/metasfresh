package de.metas.invoicecandidate.internalbusinesslogic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class OrderedData
{
	Quantity qty;

	Quantity qtyInStockUom;

	boolean orderFullyDelivered;

	@JsonCreator
	@Builder
	private OrderedData(
			@JsonProperty("qty") @NonNull final Quantity qty,
			@JsonProperty("qtyInStockUom") @NonNull final Quantity qtyInStockUom,
			@JsonProperty("orderFullyDelivered") final boolean orderFullyDelivered)
	{
		this.qty = qty;
		this.qtyInStockUom = qtyInStockUom;
		this.orderFullyDelivered = orderFullyDelivered;
	}

	@JsonIgnore
	public boolean isNegative()
	{
		final boolean negativeUomQty = getQty().signum() < 0;
		if (negativeUomQty)
		{
			return true;
		}

		// not sure if this happens in real live (it might, who knows!), but it does happen if a lazy dev sets up unit tests without uomQtys
		final boolean zeroUomQty = getQty().signum() == 0;
		final boolean negativeStockQty = getQtyInStockUom().signum() < 0;

		return zeroUomQty && negativeStockQty;
	}

}
