package de.metas.costing;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.TemporalUnit;

import com.google.common.annotations.VisibleForTesting;

import de.metas.money.CurrencyId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.time.DurationUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class CostPrice
{
	public static CostPrice ownCostPrice(@NonNull final CostAmount ownCostPrice)
	{
		final CostAmount zero = ownCostPrice.toZero();
		return CostPrice.builder()
				.ownCostPrice(ownCostPrice)
				.componentsCostPrice(zero)
				.build();
	}

	public static CostPrice zero(final CurrencyId currencyId)
	{
		final CostAmount zero = CostAmount.zero(currencyId);
		return CostPrice.builder()
				.ownCostPrice(zero)
				.componentsCostPrice(zero)
				.build();
	}

	CurrencyId currenyId;
	CostAmount ownCostPrice;
	CostAmount componentsCostPrice;

	@Builder(toBuilder = true)
	private CostPrice(
			@NonNull final CostAmount ownCostPrice,
			@NonNull final CostAmount componentsCostPrice)
	{
		Check.assumeEquals(ownCostPrice.getCurrencyId(), componentsCostPrice.getCurrencyId());

		currenyId = ownCostPrice.getCurrencyId();

		this.ownCostPrice = ownCostPrice;
		this.componentsCostPrice = componentsCostPrice;
	}

	public CostAmount toCostAmount()
	{
		return getOwnCostPrice().add(getComponentsCostPrice());
	}

	@VisibleForTesting
	public BigDecimal toBigDecimal()
	{
		return toCostAmount().getValue();
	}

	public CostPrice addToOwnCostPrice(@NonNull final CostAmount ownCostPriceToAdd)
	{
		if (ownCostPriceToAdd.isZero())
		{
			return this;
		}

		return withOwnCostPrice(getOwnCostPrice().add(ownCostPriceToAdd));
	}

	public CostPrice withOwnCostPrice(final CostAmount ownCostPrice)
	{
		return toBuilder().ownCostPrice(ownCostPrice).build();
	}

	public CostPrice withZeroOwnCostPrice()
	{
		final CostAmount ownCostPrice = getOwnCostPrice();
		if (ownCostPrice.isZero())
		{
			return this;
		}

		return withOwnCostPrice(ownCostPrice.toZero());
	}

	public CostPrice withZeroComponentsCostPrice()
	{
		final CostAmount componentsCostPrice = getComponentsCostPrice();
		if (componentsCostPrice.isZero())
		{
			return this;
		}

		return withComponentsCostPrice(componentsCostPrice.toZero());
	}

	public CostPrice withComponentsCostPrice(final CostAmount componentsCostPrice)
	{
		return toBuilder().componentsCostPrice(componentsCostPrice).build();
	}

	public CostPrice add(final CostPrice costPrice)
	{
		return builder()
				.ownCostPrice(getOwnCostPrice().add(costPrice.getOwnCostPrice()))
				.componentsCostPrice(getComponentsCostPrice().add(costPrice.getComponentsCostPrice()))
				.build();
	}

	public CostAmount multiply(@NonNull final Quantity quantity)
	{
		return toCostAmount().multiply(quantity);
	}

	public CostAmount multiply(@NonNull final Duration duration, @NonNull final TemporalUnit durationUnit)
	{
		final BigDecimal durationBD = DurationUtils.toBigDecimal(duration, durationUnit);
		return toCostAmount().multiply(durationBD);
	}
}
