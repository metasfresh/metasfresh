package de.metas.procurement.webui.model;

import java.math.BigDecimal;
import java.util.Map;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;

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
	UP("U", "trend-up", FontAwesome.ARROW_UP)
	, DOWN("D", "trend-down", FontAwesome.ARROW_DOWN)
	, EVEN("E", "trend-even", FontAwesome.ARROW_RIGHT)
	, ZERO("Z", "trend-zero", FontAwesome.REMOVE)
	//
	;

	private final String code;
	private final String cssStyleName;
	private final Resource icon;

	Trend(final String code, final String cssStyleName, final Resource icon)
	{
		this.code = code;
		this.cssStyleName = cssStyleName;
		this.icon = icon;
	}
	
	public String getCode()
	{
		return code;
	}

	public String getCssStyleName()
	{
		return cssStyleName;
	}
	
	public Resource getIcon()
	{
		return icon;
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
	
	public static void applyStyleName(final Component comp, final Trend trend)
	{
		if (comp == null)
		{
			return;
		}
		
		for (final Trend t : values())
		{
			comp.removeStyleName(t.getCssStyleName());
		}
		//
		if (trend != null)
		{
			comp.addStyleName(trend.getCssStyleName());
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
