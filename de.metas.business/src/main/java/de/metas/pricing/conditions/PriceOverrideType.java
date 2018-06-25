package de.metas.pricing.conditions;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;

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

public enum PriceOverrideType
{
	NONE("N"), BASE_PRICING_SYSTEM("P"), FIXED_PRICE("F");

	public static final int AD_Reference_ID = 540862;

	@Getter
	private final String code;

	PriceOverrideType(final String code)
	{
		this.code = code;
	}

	public static PriceOverrideType ofCode(final String code)
	{
		final PriceOverrideType type = valuesByCode.get(code);
		if (type == null)
		{
			throw new NoSuchElementException("No " + PriceOverrideType.class + " for " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, PriceOverrideType> valuesByCode = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(PriceOverrideType::getCode, Function.identity()));
}
