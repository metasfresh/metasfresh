package de.metas.ui.web.vaadin.window.model;

import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;

import de.metas.ui.web.vaadin.window.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.SqlLookupDescriptor;
import de.metas.ui.web.vaadin.window.WindowConstants;
import de.metas.ui.web.vaadin.window.shared.datatype.LookupDataSourceServiceDTO;

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

		this.value = LookupDataSourceServiceDTO.of(lookupDataSource);
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
}
