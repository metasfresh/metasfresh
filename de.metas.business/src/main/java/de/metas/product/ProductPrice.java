package de.metas.product;

import java.math.BigDecimal;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.uom.UomId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

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
	Money value;

	public Money toMoney()
	{
		return value;
	}

	public BigDecimal toBigDecimal()
	{
		return value.getValue();
	}

	public CurrencyId getCurrencyId()
	{
		return value.getCurrencyId();
	}

	public ProductPrice withValueAndUomId(@NonNull final BigDecimal value, @NonNull final UomId uomId)
	{
		return toBuilder()
				.value(Money.of(value, getCurrencyId()))
				.uomId(uomId)
				.build();
	}
}
