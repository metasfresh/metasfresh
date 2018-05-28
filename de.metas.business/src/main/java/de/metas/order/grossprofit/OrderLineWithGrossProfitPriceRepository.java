package de.metas.order.grossprofit;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.springframework.stereotype.Repository;

import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.CurrencyRepository;
import de.metas.money.Money;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
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

@Repository
public class OrderLineWithGrossProfitPriceRepository
{
	private final CurrencyRepository currencyRepository;
	private final OrderLineRepository orderLineRepository;

	public OrderLineWithGrossProfitPriceRepository(
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final OrderLineRepository orderLineRepository)
	{
		this.orderLineRepository = orderLineRepository;
		this.currencyRepository = currencyRepository;
	}

	public OrderLineWithGrossProfitPrice getForOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLineRecord = load(orderLineId.getRepoId(), I_C_OrderLine.class);

		final OrderLine orderLine = orderLineRepository.getById(orderLineId);

		final Currency currency = currencyRepository.getById(CurrencyId.ofRepoId(orderLineRecord.getC_Currency_ID()));
		final Money customerGrossProfit = Money.of(orderLineRecord.getPriceGrossProfit(), currency);

		return new OrderLineWithGrossProfitPrice(orderLine, customerGrossProfit);
	}
}
