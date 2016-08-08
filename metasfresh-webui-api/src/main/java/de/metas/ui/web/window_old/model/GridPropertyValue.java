package de.metas.ui.web.window_old.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;

import de.metas.ui.web.window_old.PropertyName;
import de.metas.ui.web.window_old.descriptor.PropertyDescriptor;
import de.metas.ui.web.window_old.shared.command.GridCommands;
import de.metas.ui.web.window_old.shared.command.ViewCommandResult;
import de.metas.ui.web.window_old.shared.datatype.GridRowId;
import de.metas.ui.web.window_old.shared.datatype.LazyPropertyValuesListDTO;
import de.metas.ui.web.window_old.shared.datatype.PropertyPath;
import de.metas.ui.web.window_old.shared.datatype.PropertyValuesDTO;
import de.metas.ui.web.window_old.shared.datatype.PropertyValuesListDTO;
import de.metas.ui.web.window_old.shared.event.GridRowAddedModelEvent;

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

public class GridPropertyValue extends ObjectPropertyValue
{
	public static final GridPropertyValue cast(final PropertyValue propertyValue)
	{
		return (GridPropertyValue)propertyValue;
	}

	// private static final Logger logger = LogManager.getLogger(GridPropertyValue.class);

	private final Collection<PropertyDescriptor> columnDescriptors;

	private final LinkedHashMap<GridRowId, GridRow> rows = new LinkedHashMap<>();

	/* package */ GridPropertyValue(final PropertyValueBuilder builder)
	{
		super(builder);

		this.columnDescriptors = builder.getPropertyDescriptor().getChildPropertyDescriptors();
	}

	private GridRow newRow(final PropertyValuesDTO values)
	{
		final GridRow row = GridRow.of(columnDescriptors, values);
		rows.put(row.getRowId(), row);
		return row;
	}

	public int getRowsCount()
	{
		return rows.size();
	}

	@Override
	public Map<PropertyName, PropertyValue> getChildPropertyValues()
	{
		return ImmutableMap.of();
	}

	@Override
	public void setValue(final Object value)
	{
		final PropertyValuesListDTO rowValuesList = LazyPropertyValuesListDTO.getPropertyValuesListDTO(value);

		//
		//
		rows.clear();
		for (final PropertyValuesDTO rowValues : rowValuesList.getList())
		{
			final GridRow row = GridRow.of(columnDescriptors, rowValues);
			final GridRowId rowId = row.getRowId();
			final GridRow rowOld = rows.put(rowId, row);
			if (rowOld != null)
			{
				// shall not happen
				throw new RuntimeException("Rows with same id '" + rowId + "' resulted from data: " + value);
			}
		}
	}

	@Override
	public Object getValue()
	{
		final PropertyValuesListDTO.Builder rowValuesList = PropertyValuesListDTO.builder();
		for (final GridRow row : rows.values())
		{
			final PropertyValuesDTO rowValues = row.getValuesAsMap();
			rowValuesList.add(rowValues);
		}
		return rowValuesList.build();
	}

	public Object setValueAt(final GridRowId rowId, final PropertyName propertyName, final Object value)
	{
		final GridRow row = rows.get(rowId);
		if (row == null)
		{
			return null;
		}
		return row.setValue(propertyName, value);
	}

	public Object getValueAt(final GridRowId rowId, final PropertyName propertyName)
	{
		final GridRow row = rows.get(rowId);
		if (row == null)
		{
			return null;
		}

		return row.getValue(propertyName);
	}

	public boolean hasProperty(final GridRowId rowId, final PropertyName propertyName)
	{
		final GridRow row = rows.get(rowId);
		if (row == null)
		{
			return false;
		}

		return row.hasProperty(propertyName);
	}

	private PropertyValue getPropertyValue(final PropertyPath propertyPath)
	{
		final GridRowId rowId = propertyPath.getRowId();
		final GridRow row = rows.get(rowId);
		if (row == null)
		{
			throw new IllegalArgumentException("Row not found for " + propertyPath + " in " + this + "."
					+ "\n Existing rows are: " + rows.keySet());
		}

		final PropertyName propertyName = propertyPath.getPropertyName();
		final PropertyValue propertyValue = row.getPropertyValue(propertyName);
		return propertyValue;
	}

	@Override
	public ListenableFuture<ViewCommandResult> executeCommand(final ModelCommand command) throws Exception
	{
		final PropertyPath propertyPath = command.getPropertyPath();
		if (propertyPath.isGridProperty())
		{
			final PropertyValue propertyValue = getPropertyValue(propertyPath);
			return propertyValue.executeCommand(command);
		}

		final String commandId = command.getCommandId();
		if (GridCommands.COMMAND_GridNewRow.equals(commandId))
		{
			final GridRow row = newRow(PropertyValuesDTO.of());
			final GridRowId rowId = row.getRowId();
			final PropertyValuesDTO rowValues = row.getValuesAsMap();

			command.postEvent(GridRowAddedModelEvent.of(getName(), rowId, rowValues));

			return ModelCommandHelper.noResult();
		}

		return ModelCommandHelper.notSupported(command, this);
	}
}
