package de.metas.ui.web.vaadin.window.editor;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.vaadin.viritin.fields.LazyComboBox;
import org.vaadin.viritin.fields.LazyComboBox.FilterableCountProvider;
import org.vaadin.viritin.fields.LazyComboBox.FilterablePagingProvider;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.WindowConstants;
import de.metas.ui.web.vaadin.window.shared.datatype.LookupDataSourceServiceDTO;
import de.metas.ui.web.vaadin.window.shared.datatype.LookupValue;

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
public class SearchLookupValueEditor extends FieldEditor<LookupValue>
{
	private static final Logger logger = LogManager.getLogger(SearchLookupValueEditor.class);

	private PropertyName valuesPropertyName;

	private final int pageLength = 10;
	private ComboDataSource _comboDataSource;

	public SearchLookupValueEditor(final PropertyDescriptor descriptor)
	{
		super(descriptor);
	}
	
	@Override
	protected void collectWatchedPropertyNamesOnInit(final ImmutableSet.Builder<PropertyName> watchedPropertyNames)
	{
		super.collectWatchedPropertyNamesOnInit(watchedPropertyNames);
		
		valuesPropertyName = WindowConstants.lookupValuesName(getPropertyName());
		watchedPropertyNames.add(valuesPropertyName);
	}

	@Override
	protected LazyComboBox<LookupValue> createValueField()
	{
		final ComboDataSource comboDataSource = getComboDataSource();
		final int pageLength = comboDataSource.getPageLength();
		
		@SuppressWarnings("unchecked")
		final LazyComboBox<LookupValue> valueField = new LazyComboBox<LookupValue>(LookupValue.class, comboDataSource, comboDataSource, pageLength)
		{
			@Override
			protected void setInternalValue(Object newValue)
			{
				getComboDataSource().setCurrentValue(newValue);
				super.setInternalValue(newValue);
			}
		};
		
		return valueField;
	}

	private final ComboDataSource getComboDataSource()
	{
		if (_comboDataSource == null)
		{
			_comboDataSource = new ComboDataSource(pageLength);
		}
		return _comboDataSource;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected LazyComboBox<LookupValue> getValueField()
	{
		return (LazyComboBox<LookupValue>)super.getValueField();
	}

	@Override
	public void setValue(final PropertyName propertyName, Object value)
	{
		if (Objects.equals(getPropertyName(), propertyName))
		{
			getComboDataSource().setCurrentValue(value);
			super.setValue(propertyName, value);
		}
		else if (Objects.equals(valuesPropertyName, propertyName))
		{
			getComboDataSource().setLookupDataSourceFromObject(value);
		}
		else
		{
			super.setValue(propertyName, value);
		}
	}

	@Override
	protected LookupValue convertToView(Object valueObj)
	{
		return LookupValue.cast(valueObj);
	}

	private final class ComboDataSource implements FilterablePagingProvider<LookupValue>, FilterableCountProvider
	{
		private final int pageLength;
		private LookupDataSourceServiceDTO _lookupDataSourceService;
		private LookupValue _currentValue;

		public ComboDataSource(int pageLength)
		{
			super();
			if (pageLength <= 0)
			{
				throw new IllegalArgumentException("pageLength <= 0");
			}
			this.pageLength = pageLength;
		}

		public int getPageLength()
		{
			return pageLength;
		}

		public void setLookupDataSourceFromObject(Object value)
		{
			if (value instanceof LookupDataSourceServiceDTO)
			{
				final LookupDataSourceServiceDTO lookupDataSourceService = LookupDataSourceServiceDTO.cast(value);
				setLookupDataSourceService(lookupDataSourceService);
			}
			else
			{
				// TODO
				setLookupDataSourceService(null);
			}
		}

		public void setLookupDataSourceService(final LookupDataSourceServiceDTO lookupDataSource)
		{
			this._lookupDataSourceService = lookupDataSource;
		}
		
		private LookupDataSourceServiceDTO getLookupDataSourceService()
		{
			if (_lookupDataSourceService == null)
			{
				final ListenableFuture<Object> futureValue = getEditorListener().requestValue(valuesPropertyName);
				try
				{
					final Object valueObj = futureValue.get(10, TimeUnit.SECONDS);
					_lookupDataSourceService = LookupDataSourceServiceDTO.cast(valueObj);
					if(_lookupDataSourceService == null)
					{
						logger.warn("Got no lookupDataSource for {}", valuesPropertyName);
					}
				}
				catch (Exception e)
				{
					logger.warn("Failed retrieving future lookup data source", e);
				}
			}
			return _lookupDataSourceService;
		}
		
		public void setCurrentValue(final Object newValue)
		{
			if(newValue == null)
			{
				this._currentValue = null;
			}
			else if (newValue instanceof LookupValue)
			{
				this._currentValue = LookupValue.cast(newValue);
			}
			else if (newValue instanceof Integer)
			{
				final int id = (int)newValue;
				this._currentValue = LookupValue.unknownId(id);
			}
			else
			{
				logger.warn("Editor {} does not support value: {}", getPropertyName(), newValue);
			}
		}
		
		private LookupValue getCurrentValue()
		{
			return this._currentValue;
		}

		@Override
		public int size(final String filter)
		{
			final LookupDataSourceServiceDTO lookupDataSourceService = getLookupDataSourceService();
			if (lookupDataSourceService != null)
			{
				final int size = lookupDataSourceService.sizeIfValidFilter(filter);
				if(size != LookupDataSourceServiceDTO.SIZE_InvalidFilter)
				{
					return size;
				}
			}

			//
			// Fallback (when filter is not valid or there is no datasource service)
			final LookupValue currentValue = getCurrentValue();
			return currentValue == null ? 0 : 1;
		}

		@Override
		public List<LookupValue> findEntities(final int firstRow, final String filter)
		{
			final LookupDataSourceServiceDTO lookupDataSourceService = getLookupDataSourceService();
			if(lookupDataSourceService != null)
			{
				final List<LookupValue> values = lookupDataSourceService.findEntitiesIfValidFilter(filter, firstRow, pageLength);
				if(values != null)
				{
					return values; 
				}
			}

			//
			// Fallback (when filter is not valid or there is no datasource service)
			final LookupValue currentValue = getCurrentValue();
			return currentValue == null ? ImmutableList.of() : ImmutableList.of(currentValue);
		}
	}
}
