package de.metas.order.grossprofit.interceptor;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.grossprofit.GrossProfitPrice;
import de.metas.order.grossprofit.GrossProfitPriceFactory;
import de.metas.order.grossprofit.model.I_C_OrderLine;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

@Component
@Service
public class C_OrderLine
{
	private final GrossProfitPriceFactory grossProfitPriceFactory;

	public C_OrderLine(
			@NonNull final GrossProfitPriceFactory grossProfitPriceFactory)
	{
		this.grossProfitPriceFactory = grossProfitPriceFactory;
	}

	@ModelChange(//
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_C_OrderLine.COLUMNNAME_PriceActual)
	public void updateGrossMarginPrice(@NonNull final I_C_OrderLine orderLine)
	{
		final OrderLineId orderLineId = orderLineIdOfRecord(orderLine);
		final Money money = moneyOfRecordsPriceActual(orderLine);

		final GrossProfitPrice grossProfitPrice = grossProfitPriceFactory.createGrossProfitPrice(money, orderLineId);
		final Money profitPrice = grossProfitPrice.computeProfitPrice();

		orderLine.setPriceGrossProfit(profitPrice.getValue());
	}

	private static Money moneyOfRecordsPriceActual(@NonNull final I_C_OrderLine orderLineRecord)
	{
		final Currency currency = Currency.builder()
				.id(CurrencyId.ofRepoId(orderLineRecord
						.getC_Currency_ID()))
				.precision(orderLineRecord
						.getC_Currency()
						.getStdPrecision())
				.build();

		return Money.of(
				orderLineRecord.getPriceActual(),
				currency);
	}

	private static OrderLineId orderLineIdOfRecord(@NonNull final I_C_OrderLine orderLineRecord)
	{
		return OrderLineId.builder()
				.orderId(OrderId.ofRepoId(orderLineRecord.getC_Order_ID()))
				.repoId(orderLineRecord.getC_OrderLine_ID())
				.build();
	}
}
