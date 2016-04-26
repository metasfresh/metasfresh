package de.metas.ui.web.vaadin.window.prototype.order.editor;

import java.math.BigDecimal;

import com.vaadin.ui.AbstractField;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

@SuppressWarnings("serial")
public class BigDecimalEditor extends FieldEditor<BigDecimal>
{
	public BigDecimalEditor(final PropertyDescriptor descriptor)
	{
		super(descriptor);
	}

	@Override
	protected AbstractField<BigDecimal> createValueField()
	{
		final BigDecimalField valueField = new BigDecimalField();
		return valueField;
	}

	@Override
	protected BigDecimal convertToView(final Object valueObj)
	{
		return (BigDecimal)valueObj;
	}

//	private static final class NumberConverter implements Converter<String, Object>
//	{
//		public static final transient NumberEditor.NumberConverter instance = new NumberEditor.NumberConverter();
//		
//		@Override
//		public BigDecimal convertToModel(String value, Class<? extends Object> targetType, Locale locale) throws Converter.ConversionException
//		{
//			return convertToBigDecimal(value);
//		}
//
//		@Override
//		public String convertToPresentation(Object valueObj, Class<? extends String> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException
//		{
//			BigDecimal valueBD = convertToBigDecimal(valueObj);
//			if (valueBD == null)
//			{
//				return "";
//			}
//			valueBD = NumberUtils.stripTrailingDecimalZeros(valueBD);
//			return valueBD.toString();
//		}
//
//		@Override
//		public Class<Object> getModelType()
//		{
//			return Object.class;
//		}
//
//		@Override
//		public Class<String> getPresentationType()
//		{
//			return String.class;
//		}
//		
//		private final BigDecimal convertToBigDecimal(final Object valueObj)
//		{
//			if (valueObj == null)
//			{
//				return null;
//			}
//			else if (valueObj instanceof BigDecimal)
//			{
//				return (BigDecimal)valueObj;
//			}
//			else if (valueObj instanceof Integer)
//			{
//				return BigDecimal.valueOf((Integer)valueObj);
//			}
//			else if (valueObj instanceof String)
//			{
//				final String valueStr = valueObj.toString().trim();
//				if (valueStr.isEmpty())
//				{
//					return null;
//				}
//				else
//				{
//					try
//					{
//						return new BigDecimal(valueStr);
//					}
//					catch (Exception e)
//					{
//						throw new Converter.ConversionException("Invalid number: " + valueStr, e);
//					}
//				}
//			}
//			else
//			{
//				throw new Converter.ConversionException("Invalid value: " + valueObj);
//			}
//		}
//	}
}
