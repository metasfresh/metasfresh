package de.metas.procurement.webui.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public final class StringToPriceConverter implements Converter<String, BigDecimal>
{
	private static final int DEFAULT_PRECISION = 2;
	private int precision = DEFAULT_PRECISION;
	private String currencyCode = null;

	public StringToPriceConverter()
	{
		super();
	}

	public void setPrecision(final int precision)
	{
		if (precision < 0)
		{
			throw new IllegalArgumentException("precision < 0");
		}
		this.precision = precision;
	}

	public void setCurrencyCode(final String currencyCode)
	{
		if (currencyCode == null || currencyCode.trim().isEmpty())
		{
			this.currencyCode = null;
		}
		else
		{
			this.currencyCode = currencyCode.trim();
		}
	}

	@Override
	public BigDecimal convertToModel(final String value, final Class<? extends BigDecimal> targetType, final Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException
	{
		return convertToModel(value);
	}

	private BigDecimal convertToModel(String priceStr)
	{
		if (priceStr == null)
		{
			return BigDecimal.ZERO;
		}

		priceStr = priceStr.trim();
		if (priceStr.isEmpty())
		{
			return BigDecimal.ZERO;
		}

		return new BigDecimal(priceStr);
	}

	@Override
	public String convertToPresentation(final BigDecimal value, final Class<? extends String> targetType, final Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException
	{
		return convertToPresentation(value);
	}

	public String convertToPresentation(BigDecimal price)
	{
		if (price == null)
		{
			price = BigDecimal.ZERO;
		}

		String priceStr = price.setScale(precision, RoundingMode.HALF_UP).toString();
		if (currencyCode != null && !currencyCode.isEmpty())
		{
			priceStr += " " + currencyCode;
		}
		
		return priceStr;
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

}
