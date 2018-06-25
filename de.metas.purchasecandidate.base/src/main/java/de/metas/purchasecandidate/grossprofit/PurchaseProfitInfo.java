package de.metas.purchasecandidate.grossprofit;

import java.math.BigDecimal;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;

import de.metas.lang.Percent;
import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
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
	Optional<Money> salesNetPrice;

	/** {@link #purchaseGrossPrice} minus cash discount minus refund/bonus (if any); should better be less than {@link #salesNetPrice}.. */
	Optional<Money> purchaseNetPrice;

	Optional<Money> purchaseGrossPrice;

	@Builder(toBuilder = true)
	private PurchaseProfitInfo(
			@NonNull final Optional<Money> salesNetPrice,
			@NonNull final Optional<Money> purchaseNetPrice,
			@NonNull final Optional<Money> purchaseGrossPrice)
	{
		if (!salesNetPrice.isPresent()
				&& !purchaseNetPrice.isPresent()
				&& !purchaseGrossPrice.isPresent())
		{
			throw new AdempiereException("At least one price shall be present")
					.setParameter("salesNetPrice", salesNetPrice)
					.setParameter("purchaseNetPrice", purchaseNetPrice)
					.setParameter("purchaseGrossPrice", purchaseGrossPrice);
		}
		this.salesNetPrice = salesNetPrice;
		this.purchaseNetPrice = purchaseNetPrice;
		this.purchaseGrossPrice = purchaseGrossPrice;
	}

	public Currency getCommonCurrency()
	{
		return Money.getCommonCurrencyOfAll(salesNetPrice.orElse(null), purchaseNetPrice.orElse(null), purchaseGrossPrice.orElse(null));
	}

	public int getCommonCurrencyRepoIdOr(final int defaultValue)
	{
		final Currency currency = getCommonCurrency();
		final CurrencyId currencyId = currency.getId();
		return currencyId != null ? currencyId.getRepoId() : defaultValue;
	}

	public Optional<Percent> getProfitPercent()
	{
		return calculateProfitPercent(getSalesNetPrice().orElse(null), getPurchaseNetPrice().orElse(null));
	}

	private static Optional<Percent> calculateProfitPercent(final Money salesNetPrice, final Money purchaseNetPrice)
	{
		if (salesNetPrice == null || purchaseNetPrice == null)
		{
			return Optional.empty();
		}

		// If not the same currency then we cannot calculate the profit percentage
		if (!Money.isSameCurrency(purchaseNetPrice, salesNetPrice))
		{
			return Optional.empty();
		}

		final Percent profitPercent = Percent.ofDelta(purchaseNetPrice.getValue(), salesNetPrice.getValue());
		return Optional.of(profitPercent);
	}

	public BigDecimal getSalesNetPriceAsBigDecimalOr(final BigDecimal defaultValue)
	{
		return salesNetPrice.map(Money::getValue).orElse(defaultValue);
	}

	public BigDecimal getPurchaseNetPriceAsBigDecimalOr(final BigDecimal defaultValue)
	{
		return purchaseNetPrice.map(Money::getValue).orElse(defaultValue);
	}

	public BigDecimal getPurchaseGrossPriceAsBigDecimalOr(final BigDecimal defaultValue)
	{
		return purchaseGrossPrice.map(Money::getValue).orElse(defaultValue);
	}

	//
	//
	//
	//
	//
	public static class PurchaseProfitInfoBuilder
	{
		public PurchaseProfitInfoBuilder salesNetPrice(final Money salesNetPrice)
		{
			return salesNetPrice(Optional.ofNullable(salesNetPrice));
		}

		public PurchaseProfitInfoBuilder salesNetPrice(@NonNull final Optional<Money> salesNetPrice)
		{
			this.salesNetPrice = salesNetPrice;
			return this;
		}

		public PurchaseProfitInfoBuilder purchaseNetPrice(final Money purchaseNetPrice)
		{
			return purchaseNetPrice(Optional.ofNullable(purchaseNetPrice));
		}

		public PurchaseProfitInfoBuilder purchaseNetPrice(@NonNull final Optional<Money> purchaseNetPrice)
		{
			this.purchaseNetPrice = purchaseNetPrice;
			return this;
		}

		public PurchaseProfitInfoBuilder purchaseGrossPrice(final Money purchaseGrossPrice)
		{
			return purchaseGrossPrice(Optional.ofNullable(purchaseGrossPrice));
		}

		public PurchaseProfitInfoBuilder purchaseGrossPrice(@NonNull final Optional<Money> purchaseGrossPrice)
		{
			this.purchaseGrossPrice = purchaseGrossPrice;
			return this;
		}
	}
}
