package de.metas.procurement.webui.widgetset;

import java.util.Locale;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import com.vaadin.shared.util.SharedUtil;
import com.vaadin.ui.Component;

import de.metas.procurement.webui.widgetset.client.TextOverlayState;

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

@SuppressWarnings("serial")
public class TextOverlay extends AbstractExtension implements Property.Viewer
{
	public static TextOverlay extend(final Component comp)
	{
		final TextOverlay overlay = new TextOverlay();
		final AbstractClientConnector compConnector = (AbstractClientConnector)comp;
		overlay.extend(compConnector);
		return overlay;
	}

	public static final Converter<String, Integer> CONVERTER_PositiveCounterOrNull = new Converter<String, Integer>()
	{

		@Override
		public Integer convertToModel(final String value, final Class<? extends Integer> targetType, final Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public String convertToPresentation(final Integer value, final Class<? extends String> targetType, final Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException
		{
			if (value == null || value <= 0)
			{
				return null;
			}

			return value.toString();
		}

		@Override
		public Class<Integer> getModelType()
		{
			return Integer.class;
		}

		@Override
		public Class<String> getPresentationType()
		{
			return String.class;
		}

	};

	private Property<Object> dataSource = null;

	/** A converter used to convert from the data model type to the field type. */
	private Converter<String, Object> converter = null;

	private final Property.ValueChangeListener propertyListener = new Property.ValueChangeListener()
	{

		@Override
		public void valueChange(final ValueChangeEvent event)
		{
			updateTextFromDataSource();
		}

	};

	private TextOverlay()
	{
		super();
	}

	public void setText(final String text)
	{
		if (dataSource == null)
		{
			getState().text = text;
		}
		else
		{
			throw new IllegalStateException(getClass() + " is only a Property.Viewer and cannot update its data source.");
		}
	}

	@Override
	protected TextOverlayState getState()
	{
		return (TextOverlayState)super.getState();
	}

	@Override
	protected TextOverlayState getState(final boolean markAsDirty)
	{
		return (TextOverlayState)super.getState(markAsDirty);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setPropertyDataSource(final Property newDataSource)
	{
		// Stops listening the old data source changes
		if (dataSource instanceof Property.ValueChangeNotifier)
		{
			((Property.ValueChangeNotifier)dataSource).removeValueChangeListener(propertyListener);
		}

		dataSource = newDataSource;
		updateTextFromDataSource();

		// Listens the new data source if possible
		if (dataSource instanceof Property.ValueChangeNotifier)
		{
			((Property.ValueChangeNotifier)dataSource).addValueChangeListener(propertyListener);
		}

		markAsDirty();
	}

	private final void updateTextFromDataSource()
	{
		if (dataSource == null)
		{
			return;
		}

		// Update the internal value from the data source
		final String newValue = getDataSourceValue();
		if (SharedUtil.equals(newValue, getState(false).text))
		{
			return;
		}

		getState().text = newValue;
	}

	private String getDataSourceValue()
	{
		final Object valueObj = getPropertyDataSource().getValue();
		final Converter<String, Object> converter = getConverter();
		if (converter == null)
		{
			return valueObj != null ? valueObj.toString() : null;
		}

		final Locale locale = null;
		return converter.convertToPresentation(valueObj, String.class, locale);
	}

	@Override
	public Property<?> getPropertyDataSource()
	{
		return dataSource;
	}

	@SuppressWarnings("unchecked")
	public void setConverter(final Converter<String, ?> converter)
	{
		this.converter = (Converter<String, Object>)converter;
		updateTextFromDataSource();
		markAsDirty();
	}

	public Converter<String, Object> getConverter()
	{
		return converter;
	}

}
