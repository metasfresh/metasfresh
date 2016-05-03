package de.metas.ui.web.vaadin.window.prototype.order.editor;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.vaadin.viritin.fields.LazyComboBox;
import org.vaadin.viritin.fields.LazyComboBox.FilterableCountProvider;
import org.vaadin.viritin.fields.LazyComboBox.FilterablePagingProvider;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants;
import de.metas.ui.web.vaadin.window.prototype.order.model.SqlLazyLookupDataSource;

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

	private final PropertyName valuesPropertyName;
	private final ImmutableSet<PropertyName> watchedPropertyNames;

	private final int pageLength = 10;
	private ComboDataSource _comboDataSource;

	public SearchLookupValueEditor(final PropertyDescriptor descriptor)
	{
		super(descriptor);

		final ImmutableSet.Builder<PropertyName> watchedPropertyNames = ImmutableSet.builder();

		valuesPropertyName = WindowConstants.lookupValuesName(getPropertyName());
		watchedPropertyNames.add(valuesPropertyName);

		this.watchedPropertyNames = watchedPropertyNames.build();

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
	public Set<PropertyName> getWatchedPropertyNames()
	{
		return watchedPropertyNames;
	}

	@Override
	public void setValue(final PropertyName propertyName, Object value)
	{
		if (valuesPropertyName != null && Objects.equal(valuesPropertyName, propertyName))
		{
			getComboDataSource().setLookupDataSourceFromObject(value);
		}
		else
		{
			getComboDataSource().setCurrentValue(value);
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
		private SqlLazyLookupDataSource _lookupDataSource;
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
			if (value instanceof SqlLazyLookupDataSource)
			{
				setLookupDataSource((SqlLazyLookupDataSource)value);
			}
			else
			{
				// TODO
				setLookupDataSource(null);
			}
		}

		public void setLookupDataSource(final SqlLazyLookupDataSource lookupDataSource)
		{
			this._lookupDataSource = lookupDataSource;
		}
		
		private SqlLazyLookupDataSource getLookupDataSource()
		{
			if (_lookupDataSource == null)
			{
				final ListenableFuture<Object> futureValue = getEditorListener().requestValue(valuesPropertyName);
				try
				{
					_lookupDataSource = (SqlLazyLookupDataSource)futureValue.get(10, TimeUnit.SECONDS);
					if(_lookupDataSource == null)
					{
						logger.warn("Got no lookupDataSource for {}", valuesPropertyName);
					}
				}
				catch (Exception e)
				{
					logger.warn("Failed retrieving future lookup data source", e);
				}
			}
			return _lookupDataSource;
		}
		
		public void setCurrentValue(final Object newValue)
		{
			if(newValue == null)
			{
				this._currentValue = null;
			}
			else if (newValue instanceof LookupValue)
			{
				this._currentValue = (LookupValue)newValue;
			}
			else if (newValue instanceof Integer)
			{
				this._currentValue = LookupValue.of(newValue, "<" + newValue + ">");
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
			final SqlLazyLookupDataSource lookupDataSource = getLookupDataSource();
			final boolean askDataSource = lookupDataSource != null && lookupDataSource.isValidFilter(filter);
			if (askDataSource)
			{
				return lookupDataSource.size(filter);
			}
			
			final LookupValue currentValue = getCurrentValue();
			return currentValue == null ? 0 : 1;
		}

		@Override
		public List<LookupValue> findEntities(final int firstRow, final String filter)
		{
			final SqlLazyLookupDataSource lookupDataSource = getLookupDataSource();
			final boolean askDataSource = lookupDataSource != null && lookupDataSource.isValidFilter(filter);
			if (askDataSource)
			{
				return lookupDataSource.findEntities(filter, firstRow, pageLength);
			}
			
			final LookupValue currentValue = getCurrentValue();
			return currentValue == null ? ImmutableList.of() : ImmutableList.of(currentValue);
		}

	}
}
