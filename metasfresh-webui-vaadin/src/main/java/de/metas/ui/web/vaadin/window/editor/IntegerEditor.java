package de.metas.ui.web.vaadin.window.editor;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.compiere.util.DisplayType;

import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.ui.AbstractField;

import de.metas.ui.web.window.shared.descriptor.ViewPropertyDescriptor;

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
public class IntegerEditor extends FieldEditor<Integer>
{
	public IntegerEditor(final ViewPropertyDescriptor descriptor)
	{
		super(descriptor);
	}

	@Override
	protected AbstractField<Integer> createValueField()
	{
		//
		// Create the converter
		final ViewPropertyDescriptor descriptor = getPropertyDescriptor();
		int displayType = descriptor == null ? DisplayType.Integer : descriptor.getDisplayType();
		if (!DisplayType.isNumeric(displayType))
		{
			displayType = DisplayType.Integer;
		}
		final IntegerConverter converter = new IntegerConverter(displayType);

		//
		// Create the field
		final IntegerField valueField = new IntegerField(converter);
		return valueField;
	}

	@Override
	protected Integer convertToView(final Object valueObj)
	{
		return (Integer)valueObj;
	}

	private static final class IntegerConverter extends StringToIntegerConverter
	{
		private final int displayType;

		public IntegerConverter(final int displayType)
		{
			super();
			this.displayType = displayType;
		}

		@Override
		protected NumberFormat getFormat(final Locale locale)
		{
			final DecimalFormat numberFormat = DisplayType.getNumberFormat(displayType);
			return numberFormat;
		}
	}

}