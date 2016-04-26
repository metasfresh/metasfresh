package de.metas.ui.web.vaadin.window.prototype.order.editor;

import java.util.List;
import java.util.Map;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.HasChildMeasurementHint.ChildMeasurementHint;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;

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
public class GridEditor extends DocumentSectionEditorsContainer
{

	private GridEditorDataContainer containerDataSource;

	public GridEditor(final PropertyDescriptor propertyDescriptor)
	{
		super(propertyDescriptor);

		final Table table = getContent();
		table.setEditable(true);

		table.setTableFieldFactory(new GridTableFieldFactory(propertyDescriptor));

		containerDataSource = new GridEditorDataContainer(propertyDescriptor);
		table.setContainerDataSource(containerDataSource);
		for (Object propertyId : containerDataSource.getContainerPropertyIds())
		{
			table.setColumnExpandRatio(propertyId, -1);
		}

	}

	@Override
	protected Component createPanelContent()
	{
		final Table table = new Table();
		table.setChildMeasurementHint(ChildMeasurementHint.MEASURE_NEVER);
		return table;
	}

	@Override
	protected Table getContent()
	{
		return (Table)super.getContent();
	}

	@Override
	public void setEditorListener(final EditorListener listener)
	{
		super.setEditorListener(listener);
		containerDataSource.setEditorListener(listener);
	}

	@Override
	public void addChildEditor(final Editor editor)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAddingChildEditorsAllowed()
	{
		return false;
	}

	@Override
	public void setValue(final Object value)
	{
		@SuppressWarnings("unchecked")
		final List<Map<PropertyName, Object>> rowValuesList = (List<Map<PropertyName, Object>>)value;

		// FIXME: this is NOT optimum at all
		{
			final GridEditorDataContainer containerDataSourceNew = new GridEditorDataContainer(getPropertyDescriptor());
			containerDataSourceNew.setContent(rowValuesList);
			containerDataSourceNew.setEditorListener(getEditorListener());
			//
			containerDataSource = containerDataSourceNew;
			getContent().setContainerDataSource(containerDataSourceNew);

			// containerDataSource.setContent(rowValuesList);
		}
	}

	public void setValueAt(final Object rowId, final PropertyName propertyName, final Object value)
	{
		final GridRowItem row = containerDataSource.getItem(rowId);
		if (row == null)
		{
			// TODO
			return;
		}

		row.setValue(propertyName, value);
	}

	public void newRow(final Object rowId, final Map<PropertyName, Object> rowValues)
	{
		final GridRowItem row = containerDataSource.addItem(rowId);
		row.setValues(rowValues);
	}

	private static final class GridTableFieldFactory implements TableFieldFactory
	{
		private final EditorFactory editorsFactory = new EditorFactory();
		private final Map<PropertyName, PropertyDescriptor> descriptors;

		public GridTableFieldFactory(final PropertyDescriptor propertyDescriptor)
		{
			super();
			descriptors = propertyDescriptor.getChildPropertyDescriptorsAsMap();
		}

		@Override
		public Field<?> createField(final Container container, final Object itemId, final Object propertyId, final Component uiContext)
		{
			final PropertyName propertyName = (PropertyName)propertyId;
			final PropertyDescriptor propertyDescriptor = descriptors.get(propertyName);

			final Editor editor = editorsFactory.createEditor(propertyDescriptor);
			if (editor instanceof Field<?>)
			{
				final Field<?> field = (Field<?>)editor;
				return field;
			}

			// TODO Auto-generated method stub
			return null;
		}

	}
}
