package de.metas.ui.web.vaadin.window.editor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.compiere.util.DisplayType;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.DateField;

import de.metas.ui.web.vaadin.window.PropertyDescriptor;

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
public class DateEditor extends FieldEditor<Date>
{
	private final SimpleDateFormat dateFormat;

	public DateEditor(final PropertyDescriptor descriptor)
	{
		super(descriptor);
		
		final DateField valueField = getValueField();

		//
		// Display Type
		int displayType = getPropertyDescriptor().getSqlDisplayType();
		if (!DisplayType.isDate(displayType))
		{
			// Make sure the display type is about Dates
			displayType = DisplayType.Date;
		}
		
		//
		// Date resolution
		final Resolution resolution;
		if (displayType == DisplayType.Date)
		{
			resolution = Resolution.DAY;
		}
		else if (displayType == DisplayType.DateTime)
		{
			resolution = Resolution.MINUTE;
		}
		else if (displayType == DisplayType.Time)
		{
			// TODO: implement TimeEditor. Consider using https://vaadin.com/directory#!addon/timefield or something more nicer
			resolution = Resolution.SECOND;
		}
		else
		{
			throw new IllegalArgumentException("Cannot detect resolution for displayType="+displayType);
		}
		valueField.setResolution(resolution);
		
		//
		// Date format
		this.dateFormat = DisplayType.getDateFormat(displayType);
		final String dateFormatPattern = dateFormat.toPattern();
		valueField.setDateFormat(dateFormatPattern);
	}

	@Override
	protected AbstractField<Date> createValueField()
	{
		final DateField dateField = new DateField();
		return dateField;
	}
	
	@Override
	protected DateField getValueField()
	{
		return (DateField)super.getValueField();
	}

	@Override
	protected Date convertToView(final Object valueObj)
	{
		if (valueObj == null)
		{
			return null;
		}
		else if (valueObj instanceof java.util.Date)
		{
			return (java.util.Date)valueObj;
		}
		else if (valueObj instanceof String)
		{
			try
			{
				return dateFormat.parse(valueObj.toString());
			}
			catch (ParseException e)
			{
				throw new Converter.ConversionException("Invalid value: " + valueObj, e);
			}
		}
		else
		{
			throw new Converter.ConversionException("Invalid value: " + valueObj + "(" + valueObj.getClass() + ")");
		}
	}
}
