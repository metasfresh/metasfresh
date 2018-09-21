package de.metas.order.grossprofit;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.grossprofit.model.I_C_OrderLine;
import de.metas.util.Services;
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
	// services
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);

	public Optional<Money> getProfitBasePrice(@NonNull final OrderAndLineId orderLineId)
	{
		return getProfitMinBasePrice(ImmutableList.of(orderLineId));
	}

	/**
	 * Gets the minimum {@link I_C_OrderLine#getPriceGrossProfit()}, more or less
	 */
	public Optional<Money> getProfitMinBasePrice(@NonNull final Collection<OrderAndLineId> orderAndLineIds)
	{
		if (orderAndLineIds.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableSet<Money> profitBasePrices = ordersRepo.getOrderLinesByIds(orderAndLineIds, I_C_OrderLine.class)
				.stream()
				.map(this::getProfitBasePrice)
				.collect(ImmutableSet.toImmutableSet());
		if (profitBasePrices.isEmpty())
		{
			return Optional.empty();
		}
		else if (profitBasePrices.size() == 1)
		{
			return Optional.of(profitBasePrices.iterator().next());
		}
		else if (!Money.isSameCurrency(profitBasePrices))
		{
			return Optional.empty();
		}
		else
		{
			return profitBasePrices.stream().reduce(Money::min);
		}
	}

	private Money getProfitBasePrice(@NonNull final I_C_OrderLine orderLineRecord)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(orderLineRecord.getC_Currency_ID());
		return Money.of(orderLineRecord.getProfitPriceActual(), currencyId);
	}
}
