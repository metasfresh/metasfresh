package de.metas.order;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Builder.Default;
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
@Builder(toBuilder = true)
public class OrderLinePriceUpdateRequest
{
	public static OrderLinePriceUpdateRequest ofOrderLine(final org.compiere.model.I_C_OrderLine orderLine)
	{
		return prepare(orderLine).build();
	}

	public static OrderLinePriceUpdateRequestBuilder prepare(final org.compiere.model.I_C_OrderLine orderLine)
	{
		return builder()
				.updateLineNetAmt(true)
				.orderLine(InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class))
				.resultUOM(ResultUOM.PRICE_UOM_IF_ORDERLINE_IS_NEW);
	}

	public enum ResultUOM
	{
		CONTEXT_UOM, PRICE_UOM, PRICE_UOM_IF_ORDERLINE_IS_NEW
	}

	@NonNull
	I_C_OrderLine orderLine;

	//
	// Pricing context overrides
	PricingSystemId pricingSystemIdOverride;
	PriceListId priceListIdOverride;
	Quantity qtyOverride;

	/** If not {@code null}, then the pricing engine is requested to not revalidate the PricingConditionsBreak, but go with this one*/
	PricingConditionsBreak pricingConditionsBreakOverride;

	//
	// Result options
	@NonNull
	ResultUOM resultUOM;

	//
	// Updating the order line options
	boolean updatePriceEnteredAndDiscountOnlyIfNotAlreadySet; // task 06727

	boolean updateLineNetAmt;

	@Default
	boolean applyPriceLimitRestrictions = true;
	
	boolean updateProfitPriceActual;

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
