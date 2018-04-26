package de.metas.order;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
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
@Builder
public class OrderLinePriceUpdateRequest
{
	public static OrderLinePriceUpdateRequest of(final org.compiere.model.I_C_OrderLine orderLine)
	{
		return builder()
				.orderLine(InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class))
				.build();
	}

	public static enum ResultUOM
	{
		CONTEXT_UOM, PRICE_UOM, PRICE_UOM_IF_ORDERLINE_IS_NEW
	}

	@NonNull
	I_C_OrderLine orderLine;

	int priceListId;
	Quantity qty;

	ResultUOM resultUOM;

	boolean updatePriceEnteredAndDiscountOnlyIfNotAlreadySet; // task 06727

	boolean updateLineNetAmt;

	//
	//
	//
	public static class OrderLinePriceUpdateRequestBuilder
	{
		public OrderLinePriceUpdateRequestBuilder orderLine(final org.compiere.model.I_C_OrderLine orderLine)
		{
			this.orderLine = InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class);
			return this;
		}
	}
}
