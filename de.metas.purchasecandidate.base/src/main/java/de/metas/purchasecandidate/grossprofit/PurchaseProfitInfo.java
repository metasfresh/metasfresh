package de.metas.purchasecandidate.grossprofit;

import java.math.BigDecimal;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation,
 * either version 2 of the
 * License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not,
 * see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
public class PurchaseProfitInfo
{
	/** sales priceActual minus cash discount minus refund/bonus (if any) */
	Optional<Money> profitSalesPriceActual;

	/** {@link #purchasePriceActual} minus cash discount minus refund/bonus (if any); should better be less than {@link #profitSalesPriceActual}.. */
	Optional<Money> profitPurchasePriceActual;

	Optional<Money> purchasePriceActual;

	@Builder(toBuilder = true)
	private PurchaseProfitInfo(
			@NonNull final Optional<Money> profitSalesPriceActual,
			@NonNull final Optional<Money> profitPurchasePriceActual,
			@NonNull final Optional<Money> purchasePriceActual)
	{
		if (!profitSalesPriceActual.isPresent()
				&& !profitPurchasePriceActual.isPresent()
				&& !purchasePriceActual.isPresent())
		{
			throw new AdempiereException("At least one price shall be present")
					.setParameter("profitSalesPriceActual", profitSalesPriceActual)
					.setParameter("profitPurchasePriceActual", profitPurchasePriceActual)
					.setParameter("purchasePriceActual", purchasePriceActual);
		}
		this.profitSalesPriceActual = profitSalesPriceActual;
		this.profitPurchasePriceActual = profitPurchasePriceActual;
		this.purchasePriceActual = purchasePriceActual;
	}

	public CurrencyId getCommonCurrency()
	{
		return Money.getCommonCurrencyIdOfAll(
				profitSalesPriceActual.orElse(null),
				profitPurchasePriceActual.orElse(null),
				purchasePriceActual.orElse(null));
	}

	public int getCommonCurrencyRepoIdOr(final int defaultValue)
	{
		final CurrencyId currencyId = getCommonCurrency();
		return currencyId != null ? currencyId.getRepoId() : defaultValue;
	}

	public Optional<Percent> getProfitPercent()
	{
		return calculateProfitPercent(
				getProfitSalesPriceActual().orElse(null),
				getProfitPurchasePriceActual().orElse(null));
	}

	private static Optional<Percent> calculateProfitPercent(
			@Nullable final Money profitSalesPriceActual,
			@Nullable final Money profitPurchasePriceActual)
	{
		if (profitSalesPriceActual == null || profitPurchasePriceActual == null)
		{
			return Optional.empty();
		}

		// If not the same currency then we cannot calculate the profit percentage
		if (!Money.isSameCurrency(profitPurchasePriceActual, profitSalesPriceActual))
		{
			return Optional.empty();
		}

		final Percent profitPercent = Percent.ofDelta(profitPurchasePriceActual.toBigDecimal(), profitSalesPriceActual.toBigDecimal());
		return Optional.of(profitPercent);
	}

	public BigDecimal getProfitSalesPriceActualAsBigDecimalOr(final BigDecimal defaultValue)
	{
		return profitSalesPriceActual.map(Money::toBigDecimal).orElse(defaultValue);
	}

	public BigDecimal getProfitPurchasePriceActualAsBigDecimalOr(@Nullable final BigDecimal defaultValue)
	{
		return profitPurchasePriceActual.map(Money::toBigDecimal).orElse(defaultValue);
	}

	public BigDecimal getPurchasePriceActualAsBigDecimalOr(@Nullable final BigDecimal defaultValue)
	{
		return purchasePriceActual.map(Money::toBigDecimal).orElse(defaultValue);
	}

	//
	//
	public static class PurchaseProfitInfoBuilder
	{
		public PurchaseProfitInfoBuilder profitSalesPriceActual(@Nullable final Money profitSalesPriceActual)
		{
			return profitSalesPriceActual(Optional.ofNullable(profitSalesPriceActual));
		}

		public PurchaseProfitInfoBuilder profitSalesPriceActual(@NonNull final Optional<Money> profitSalesPriceActual)
		{
			this.profitSalesPriceActual = profitSalesPriceActual;
			return this;
		}

		public PurchaseProfitInfoBuilder profitPurchasePriceActual(@Nullable final Money profitPurchasePriceActual)
		{
			return profitPurchasePriceActual(Optional.ofNullable(profitPurchasePriceActual));
		}

		public PurchaseProfitInfoBuilder profitPurchasePriceActual(@NonNull final Optional<Money> profitPurchasePriceActual)
		{
			this.profitPurchasePriceActual = profitPurchasePriceActual;
			return this;
		}

		public PurchaseProfitInfoBuilder purchasePriceActual(@Nullable final Money purchasePriceActual)
		{
			return purchasePriceActual(Optional.ofNullable(purchasePriceActual));
		}

		public PurchaseProfitInfoBuilder purchasePriceActual(@NonNull final Optional<Money> purchasePriceActual)
		{
			this.purchasePriceActual = purchasePriceActual;
			return this;
		}
	}
}
