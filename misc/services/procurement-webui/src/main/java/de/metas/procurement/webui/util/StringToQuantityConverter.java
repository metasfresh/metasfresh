package de.metas.procurement.webui.util;

import java.math.BigDecimal;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

/*
 * #%L
 * metasfresh-procurement-webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
public final class StringToQuantityConverter implements Converter<String, BigDecimal>
{
	private String uom;

	public StringToQuantityConverter()
	{
		this(null);
	}

	public StringToQuantityConverter(final String uom)
	{
		super();
		this.uom = uom;
	}

	@Override
	public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException
	{
		return QuantityUtils.toQuantity(value);
	}

	@Override
	public String convertToPresentation(BigDecimal value, Class<? extends String> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException
	{
		String qtyStr = QuantityUtils.toString(value);
		if (uom != null)
		{
			qtyStr += " " + uom;
		}
		return qtyStr;
	}

	@Override
	public Class<BigDecimal> getModelType()
	{
		return BigDecimal.class;
	}

	@Override
	public Class<String> getPresentationType()
	{
		return String.class;
	}

	public void setUom(String uom)
	{
		this.uom = uom;
	}

}
