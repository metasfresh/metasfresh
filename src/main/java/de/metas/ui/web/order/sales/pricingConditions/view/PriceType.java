package de.metas.ui.web.order.sales.pricingConditions.view;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;

/*
 * #%L
 * metasfresh-webui-api
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

public enum PriceType
{
	NONE("N"), BASE_PRICING_SYSTEM("P"), FIXED_PRICE("F");

	public static final int AD_Reference_ID = 540862;

	@Getter
	private final String code;

	PriceType(final String code)
	{
		this.code = code;
	}

	public boolean isFixedPrice()
	{
		return this == PriceType.FIXED_PRICE;
	}

	public boolean isBasePricingSystem()
	{
		return this == PriceType.BASE_PRICING_SYSTEM;
	}

	public static PriceType ofCode(final String code)
	{
		final PriceType type = priceTypesByCode.get(code);
		if (type == null)
		{
			throw new NoSuchElementException("No " + PriceType.class + " for " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, PriceType> priceTypesByCode = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(PriceType::getCode, Function.identity()));
}
