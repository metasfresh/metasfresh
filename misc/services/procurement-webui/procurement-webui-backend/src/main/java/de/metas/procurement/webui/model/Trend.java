package de.metas.procurement.webui.model;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Map;






/*
 * #%L
 * de.metas.procurement.webui
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

public enum Trend
{
	UP("U", "trend-up")
	, DOWN("D", "trend-down")
	, EVEN("E", "trend-even")
	, ZERO("Z", "trend-zero")
	//
	;

	private final String code;
	private final String cssStyleName;

	Trend(final String code, final String cssStyleName)
	{
		this.code = code;
		this.cssStyleName = cssStyleName;
	}
	
	public String getCode()
	{
		return code;
	}

	public String getCssStyleName()
	{
		return cssStyleName;
	}
	
	public static Trend forQtyAndTarget(final BigDecimal qty, final BigDecimal targetQty)
	{
		final int cmp = qty.compareTo(targetQty);
		if (cmp > 0)
		{
			return UP;
		}
		else if (cmp == 0)
		{
			return EVEN;
		}
		else
		{
			return DOWN;
		}
	}
	

	private static final Map<String, Trend> code2value;
	static
	{
		final ImmutableMap.Builder<String, Trend> code2valueBuilder = ImmutableMap.builder();
		for (final Trend trend : values())
		{
			code2valueBuilder.put(trend.getCode(), trend);
		}
		code2value = code2valueBuilder.build();
	}
	
	public static Trend ofCodeOrNull(final String code)
	{
		if (code == null)
		{
			return null;
		}
		return code2value.get(code);
	}
}
