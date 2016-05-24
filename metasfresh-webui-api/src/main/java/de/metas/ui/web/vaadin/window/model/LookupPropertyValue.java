package de.metas.ui.web.vaadin.window.model;

import java.util.List;
import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.WindowConstants;
import de.metas.ui.web.vaadin.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.descriptor.SqlLookupDescriptor;
import de.metas.ui.web.vaadin.window.shared.datatype.LookupDataSourceServiceDTO;
import de.metas.ui.web.vaadin.window.shared.datatype.LookupValue;

/*
 * #%L
 * metasfresh-webui
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
 * Lookup values provider as {@link PropertyValue} node.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class LookupPropertyValue implements PropertyValue
{
	public static final LookupPropertyValue cast(PropertyValue propertyValue)
	{
		return (LookupPropertyValue)propertyValue;
	}

	private final PropertyName propertyName;
	private final LookupDataSource lookupDataSource;
	private final LookupDataSourceServiceDTO value;

	LookupPropertyValue(final PropertyDescriptor descriptor)
	{
		super();
		final PropertyName propertyName = descriptor.getPropertyName();
		this.propertyName = WindowConstants.lookupValuesName(propertyName);

		final SqlLookupDescriptor sqlLookupDescriptor = descriptor.getSqlLookupDescriptor();
		this.lookupDataSource = new SqlLazyLookupDataSource(sqlLookupDescriptor);

		this.value = LookupDataSourceServiceDTOImpl.of(lookupDataSource);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name", propertyName)
				.toString();
	}

	@Override
	public PropertyName getName()
	{
		return propertyName;
	}

	@Override
	public PropertyNameDependenciesMap getDependencies()
	{
		return PropertyNameDependenciesMap.EMPTY;
	}

	@Override
	public void onDependentPropertyValueChanged(final DependencyValueChangedEvent event)
	{
		// TODO: update lookup datasource in case it's filtering depends on some other properties
	}

	@Override
	public LookupDataSourceServiceDTO getValue()
	{
		return value;
	}

	/* package */LookupDataSource getLookupDataSource()
	{
		return lookupDataSource;
	}

	@Override
	public Optional<String> getValueAsString()
	{
		return Optional.absent();
	}

	@Override
	public void setValue(final Object value)
	{
		throw new UnsupportedOperationException("setting " + this + "'s value is not allowed");
	}

	public Object getInitialValue()
	{
		return null;
	}

	@Override
	public Map<PropertyName, PropertyValue> getChildPropertyValues()
	{
		return ImmutableMap.of();
	}

	@Override
	public String getComposedValuePartName()
	{
		return null;
	}

	@Override
	public boolean isChanged()
	{
		return false;
	}

	@Override
	public boolean isReadOnlyForUser()
	{
		return true;
	}

	@Override
	public final boolean isCalculated()
	{
		return true;
	}
	
	private static class LookupDataSourceServiceDTOImpl extends LookupDataSourceServiceDTO
	{
		// TODO: the implementation of LookupDataSourceServiceDTO shall be simple serializable POJO and not an LookupDataSource wrapper 
		
		public static final LookupDataSourceServiceDTO of(final LookupDataSource lookupDataSource)
		{
			return new LookupDataSourceServiceDTOImpl(lookupDataSource);
		}

		public static final int SIZE_InvalidFilter = -100;

		private final LookupDataSource lookupDataSource;

		private LookupDataSourceServiceDTOImpl(final LookupDataSource lookupDataSource)
		{
			super();
			this.lookupDataSource = lookupDataSource;
		}

		/**
		 * @param filter
		 * @return size or {@link #SIZE_InvalidFilter} if the filter is not valid
		 */
		@Override
		public int sizeIfValidFilter(final String filter)
		{
			if (!lookupDataSource.isValidFilter(filter))
			{
				return SIZE_InvalidFilter;
			}
			return lookupDataSource.size(filter);
		}

		/**
		 * 
		 * @param filter
		 * @param firstRow
		 * @param pageLength
		 * @return {@link LookupValue}s list or <code>null</code> if the filter is not valid
		 */
		@Override
		public List<LookupValue> findEntitiesIfValidFilter(final String filter, final int firstRow, final int pageLength)
		{
			if (!lookupDataSource.isValidFilter(filter))
			{
				return null;
			}
			return lookupDataSource.findEntities(filter, firstRow, pageLength);
		}
	}
}
