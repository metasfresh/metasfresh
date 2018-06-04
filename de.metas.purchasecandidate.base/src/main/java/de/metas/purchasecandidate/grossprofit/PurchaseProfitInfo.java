package de.metas.purchasecandidate.grossprofit;

import java.util.Optional;

import de.metas.lang.Percent;
import de.metas.money.Currency;
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
	Money purchaseNetPrice;

	Money purchaseGrossPrice;

	@Builder(toBuilder = true)
	private PurchaseProfitInfo(
			@NonNull final Optional<Money> salesNetPrice,
			@NonNull final Money purchaseNetPrice,
			@NonNull final Money purchaseGrossPrice)
	{
		this.salesNetPrice = salesNetPrice;
		this.purchaseNetPrice = purchaseNetPrice;
		this.purchaseGrossPrice = purchaseGrossPrice;
	}

	public Currency getCommonCurrency()
	{
		return Money.getCommonCurrencyOfAll(salesNetPrice.orElse(null), purchaseNetPrice, purchaseGrossPrice);
	}

	public Optional<Percent> getProfitPercent()
	{
		return calculateProfitPercent(getSalesNetPrice().orElse(null), getPurchaseNetPrice());
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

	}
}
