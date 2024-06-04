package de.metas.product;

import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.quantity.UOMConversionRateProvider;
import de.metas.uom.UOMConversionRate;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UomId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.function.Function;

/*
 * #%L
 * de.metas.business
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

/**
 * Product Price / UOM
 */
@Value
@Builder(toBuilder = true)
public class ProductPrice
{
	@NonNull ProductId productId;
	@NonNull UomId uomId;
	@NonNull @Getter(AccessLevel.NONE) Money money;

	public Money toMoney()
	{
		return money;
	}

	@NonNull
	public BigDecimal toBigDecimal()
	{
		return money.toBigDecimal();
	}

	@NonNull
	public CurrencyId getCurrencyId()
	{
		return money.getCurrencyId();
	}

	public ProductPrice withValueAndUomId(@NonNull final BigDecimal moneyAmount, @NonNull final UomId uomId)
	{
		return toBuilder()
				.money(Money.of(moneyAmount, getCurrencyId()))
				.uomId(uomId)
				.build();
	}

	public ProductPrice negate()
	{
		return this.toBuilder().money(money.negate()).build();
	}

	public ProductPrice negateIf(final boolean condition)
	{
		return condition ? negate() : this;
	}

	public boolean isEqualByComparingTo(@Nullable final ProductPrice other)
	{
		if (other == null)
		{
			return false;
		}

		return other.uomId.equals(uomId) && other.money.isEqualByComparingTo(money);
	}

	public <T> T transform(@NonNull final Function<ProductPrice, T> mapper)
	{
		return mapper.apply(this);
	}

	@NonNull
	public Money computeAmount(@NonNull final Quantity quantity)
	{
		final BigDecimal amount = quantity
				.toBigDecimalAssumingUOM(uomId)
				.multiply(money.toBigDecimal());

		return Money.of(amount, money.getCurrencyId());
	}

	@NonNull
	public Money computeAmount(@NonNull final Quantity quantity, @NonNull final QuantityUOMConverter uomConverter)
	{
		final Quantity quantityInPriceUOM = uomConverter.convertQuantityTo(quantity, productId, uomId);
		final BigDecimal amount = quantityInPriceUOM.toBigDecimal()
				.multiply(money.toBigDecimal());

		return Money.of(amount, money.getCurrencyId());
	}

	public ProductPrice convertToUom(
			@NonNull final UomId toUomId,
			@NonNull final CurrencyPrecision pricePrecision,
			@NonNull final UOMConversionRateProvider uomConversionProvider)
	{
		if (this.uomId.equals(toUomId))
		{
			return this;
		}

		final UOMConversionRate rate = uomConversionProvider.getRate(productId, toUomId, uomId);
		final BigDecimal priceConv = pricePrecision.round(rate.convert(money.toBigDecimal(), UOMPrecision.TWELVE));

		return withValueAndUomId(priceConv, toUomId);
	}

	public ProductPrice toZero()
	{
		return toBuilder()
				.money(money.toZero())
				.build();
	}
}
