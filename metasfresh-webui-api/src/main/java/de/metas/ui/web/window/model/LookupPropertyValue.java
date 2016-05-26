package de.metas.ui.web.window.model;

import java.util.List;
import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;

import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datasource.LookupDataSource;
import de.metas.ui.web.window.datasource.sql.SqlLazyLookupDataSource;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.descriptor.SqlLookupDescriptor;
import de.metas.ui.web.window.shared.command.LookupDataSourceQueryCommand;
import de.metas.ui.web.window.shared.command.ViewCommand;
import de.metas.ui.web.window.shared.command.ViewCommandResult;
import de.metas.ui.web.window.shared.datatype.LookupValue;

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
	/* package */ static final LookupPropertyValue cast(final PropertyValue propertyValue)
	{
		return (LookupPropertyValue)propertyValue;
	}

	private final PropertyName propertyName;
	private final LookupDataSource lookupDataSource;
	private final LookupDataSourceQueryCommand value;

	/* package */ LookupPropertyValue(final PropertyDescriptor descriptor)
	{
		super();
		final PropertyName propertyName = descriptor.getPropertyName();
		this.propertyName = WindowConstants.lookupValuesName(propertyName);

		final SqlLookupDescriptor sqlLookupDescriptor = descriptor.getSqlLookupDescriptor();
		lookupDataSource = new SqlLazyLookupDataSource(sqlLookupDescriptor);

		value = null; // new LookupDataSourceServiceDTOImpl(propertyName);
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
	public LookupDataSourceQueryCommand getValue()
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

	@Override
	public ListenableFuture<ViewCommandResult> executeCommand(final ViewCommand command) throws Exception
	{
		final String commandId = command.getCommandId();
		if (LookupDataSourceQueryCommand.COMMAND_Size.equals(commandId))
		{
			final String filter = command.getParameterAsString(LookupDataSourceQueryCommand.PARAMETER_Filter);
			final int size;
			if (!lookupDataSource.isValidFilter(filter))
			{
				size = LookupDataSourceQueryCommand.SIZE_InvalidFilter;
			}
			else
			{
				size = lookupDataSource.size(filter);
			}

			return ViewCommandHelper.result(size);
		}
		else if (LookupDataSourceQueryCommand.COMMAND_Find.equals(commandId))
		{
			final String filter = command.getParameterAsString(LookupDataSourceQueryCommand.PARAMETER_Filter);
			final List<LookupValue> entries;
			if (!lookupDataSource.isValidFilter(filter))
			{
				entries = null;
			}
			else
			{
				final int firstRow = command.getParameterAsInt(LookupDataSourceQueryCommand.PARAMETER_FirstRow, 0);
				final int pageLength = command.getParameterAsInt(LookupDataSourceQueryCommand.PARAMETER_PageLength, LookupDataSourceQueryCommand.DEFAULT_PageLength);
				entries = lookupDataSource.findEntities(filter, firstRow, pageLength);
			}

			return ViewCommandHelper.result(entries);
		}
		else
		{
			return ViewCommandHelper.notSupported(command, this);
		}
	}
}
