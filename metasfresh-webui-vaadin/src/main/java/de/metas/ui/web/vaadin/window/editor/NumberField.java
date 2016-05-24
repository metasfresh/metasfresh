package de.metas.ui.web.vaadin.window.editor;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.TextField;

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

/**
 * 
 * @author https://vaadin.com/forum/#!/thread/1402046/2842284
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
public class NumberField<T extends Number> extends CustomField<T>
{
	private final Class<T> typeClass;
	private final Converter<String, T> converter;
	
	private TextField content;
	private int recursions = 0;

	public NumberField(final Class<T> typeClass, final Converter<String, T> converter)
	{
		super();
		this.typeClass = typeClass;
		this.converter = converter;

		init();
	}

	private void init()
	{
		content = new TextField();
		content.setNullSettingAllowed(true);
		content.setNullRepresentation("");
		content.addValueChangeListener(new ValueChangeListener()
		{
			@Override
			public void valueChange(final com.vaadin.data.Property.ValueChangeEvent event)
			{
				if (recursions == 0)
				{
					recursions++;
					try
					{
						if (event.getProperty().getValue() == null)
						{
							NumberField.this.setValue(null);
						}
						else
						{
							try
							{
								final Object valueObj = event.getProperty().getValue();
								final String valueStr = valueObj == null ? null : valueObj.toString();
								final T valueNumber = converter.convertToModel(valueStr, typeClass, getLocale());
								NumberField.this.setValue(valueNumber);
							}
							catch (com.vaadin.data.Property.ReadOnlyException | Converter.ConversionException e)
							{
							}

							if (NumberField.this.getValue() == null)
							{
								content.setValue(null);
							}
							else
							{
								final T valueNumber = NumberField.this.getValue();
								final String valueStr = converter.convertToPresentation(valueNumber, String.class, getLocale());
								content.setValue(valueStr);
							}
						}
					}
					finally
					{
						recursions--;
					}
				}
			}
		});

		setImmediate(true);
	}

	@Override
	protected Component initContent()
	{
		return content;
	}

	@Override
	public Class<? extends T> getType()
	{
		return typeClass;
	}

	@Override
	protected void setInternalValue(final T newValue)
	{
		super.setInternalValue(newValue);

		recursions++;
		try
		{
			if (newValue == null)
			{
				content.setValue(null);
			}
			else
			{
				final String valueStr = converter.convertToPresentation(newValue, String.class, getLocale());
				content.setValue(valueStr);
			}
		}
		finally
		{
			recursions--;
		}
	}

	@Override
	public void setImmediate(final boolean immediate)
	{
		super.setImmediate(immediate);
		content.setImmediate(immediate);
	}

	@Override
	public void setSizeFull()
	{
		super.setSizeFull();
		content.setSizeFull();
	}

	@Override
	public void setSizeUndefined()
	{
		super.setSizeUndefined();
		content.setSizeUndefined();
	}

	@Override
	public void setWidth(final String width)
	{
		super.setWidth(width);
		content.setWidth(width);
	}

	@Override
	public void setHeight(final String height)
	{
		super.setHeight(height);
		content.setHeight(height);
	}

	public void selectAll()
	{
		content.selectAll();
	}
	
	@Override
	public void setReadOnly(final boolean readOnly)
	{
		content.setReadOnly(readOnly);
	}
}
