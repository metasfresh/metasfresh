package de.metas.currency;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Conversion Type
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public enum ConversionType
{
	Spot("S"),
	PeriodEnd("P"),
	Average("A"),
	Company("C");

	public static final ConversionType forCode(final String code)
	{
		final ConversionType type = _indexedByCode.get(code);
		if (type == null)
		{
			throw new IllegalArgumentException("No enum constant found for code: " + code);
		}
		return type;
	}

	private static final Map<String, ConversionType> _indexedByCode;
	static
	{
		final ImmutableMap.Builder<String, ConversionType> builder = ImmutableMap.builder();
		for (final ConversionType t : values())
		{
			builder.put(t.getCode(), t);
		}
		_indexedByCode = builder.build();
	}

	private final String code;

	private ConversionType(final String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}
}