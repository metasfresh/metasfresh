package de.metas.product;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Check;
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

/** Product Price / UOM */
@Value
@Builder(toBuilder = true)
public class ProductPrice
{
	@NonNull
	ProductId productId;

	@NonNull
	UomId uomId;

	@NonNull
	@Getter(AccessLevel.NONE)
	Money money;

	public Money toMoney()
	{
		return money;
	}

	public BigDecimal toBigDecimal()
	{
		return money.toBigDecimal();
	}

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
		Check.assumeEquals(quantity.getUomId(), uomId);

		final BigDecimal amount = quantity.toBigDecimal().multiply(money.toBigDecimal());
		return Money.of(amount, money.getCurrencyId());
	}
}
