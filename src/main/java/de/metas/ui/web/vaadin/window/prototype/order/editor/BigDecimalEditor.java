package de.metas.ui.web.vaadin.window.prototype.order.editor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.compiere.util.DisplayType;

import com.vaadin.data.util.converter.StringToBigDecimalConverter;
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
		//
		// Create the converter
		final PropertyDescriptor descriptor = getPropertyDescriptor();
		int displayType = descriptor == null ? DisplayType.Number : descriptor.getSqlDisplayType();
		if (!DisplayType.isNumeric(displayType))
		{
			displayType = DisplayType.Number;
		}
		final BigDecimalConverter converter = new BigDecimalConverter(displayType);

		//
		// Create the field
		final BigDecimalField valueField = new BigDecimalField(converter);
		return valueField;
	}

	@Override
	protected BigDecimal convertToView(final Object valueObj)
	{
		return (BigDecimal)valueObj;
	}

	private static final class BigDecimalConverter extends StringToBigDecimalConverter
	{
		private final int displayType;

		public BigDecimalConverter(final int displayType)
		{
			super();
			this.displayType = displayType;
		}

		@Override
		protected NumberFormat getFormat(final Locale locale)
		{
			final DecimalFormat numberFormat = DisplayType.getNumberFormat(displayType);
			numberFormat.setParseBigDecimal(true);
			return numberFormat;
		}
	}
}
