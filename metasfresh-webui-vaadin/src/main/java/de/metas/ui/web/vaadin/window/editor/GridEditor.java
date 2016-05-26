package de.metas.ui.web.vaadin.window.editor;

import java.util.List;

import org.slf4j.Logger;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasChildMeasurementHint.ChildMeasurementHint;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.shared.command.GridCommands;
import de.metas.ui.web.window.shared.command.ViewCommand;
import de.metas.ui.web.window.shared.datatype.PropertyValuesDTO;
import de.metas.ui.web.window.shared.datatype.PropertyValuesListDTO;
import de.metas.ui.web.window.shared.descriptor.ViewPropertyDescriptor;

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
	static final Logger logger = LogManager.getLogger(GridEditor.class);

	private GridTableFieldFactory fieldFactory;
	private GridEditorDataContainer containerDataSource;

	// UI
	private Table table;


	public GridEditor(final ViewPropertyDescriptor propertyDescriptor)
	{
		super(propertyDescriptor);

	}

	@Override
	protected Component createPanelContent()
	{
		final Button btnNewRow = new Button("New", event->fireNewRecordCommand());
		
		final HorizontalLayout toolbarPanel = new HorizontalLayout(btnNewRow);
		this.table = createTable();
		
		final VerticalLayout content = new VerticalLayout(toolbarPanel, table);
		
		return content;
	}
	
	final Table createTable()
	{
		final ViewPropertyDescriptor propertyDescriptor = getPropertyDescriptor();
		fieldFactory = new GridTableFieldFactory(propertyDescriptor);
		containerDataSource = new GridEditorDataContainer(propertyDescriptor);

		final Table table = new Table();
		table.setChildMeasurementHint(ChildMeasurementHint.MEASURE_NEVER);
		table.setEditable(true);

		table.setTableFieldFactory(fieldFactory);

		table.setContainerDataSource(containerDataSource);
		for (Object propertyId : containerDataSource.getContainerPropertyIds())
		{
			table.setColumnHeader(propertyId, containerDataSource.getHeader(propertyId));
			table.setColumnExpandRatio(propertyId, -1);
		}
		
		table.setChildMeasurementHint(ChildMeasurementHint.MEASURE_NEVER);
		table.setColumnCollapsingAllowed(true);
		table.setPageLength(5);
		
		return table;
	}

	private Table getTable()
	{
		return table;
	}

	@Override
	public void setEditorListener(final EditorListener listener)
	{
		super.setEditorListener(listener);
		fieldFactory.setEditorListener(listener);
		containerDataSource.setEditorListener(listener);
	}

	@Override
	public boolean isAddingChildEditorsAllowed()
	{
		return false;
	}
	
	@Override
	public void addChildEditor(final Editor editor)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Editor> getChildEditors()
	{
		return ImmutableList.of();
	}

	@Override
	public void setValue(final PropertyName propertyName, final Object value)
	{
		if (getPropertyName().equals(propertyName))
		{
			final PropertyValuesListDTO rowValuesList = PropertyValuesListDTO.cast(value);
	
			// FIXME: this is NOT optimum at all
			{
				final GridEditorDataContainer containerDataSourceNew = new GridEditorDataContainer(getPropertyDescriptor());
				containerDataSourceNew.setContent(rowValuesList);
				containerDataSourceNew.setEditorListener(getEditorListener());
				//
				containerDataSource = containerDataSourceNew;
				getTable().setContainerDataSource(containerDataSourceNew);
	
				// containerDataSource.setContent(rowValuesList);
			}
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

	public void newRow(final Object rowId, final PropertyValuesDTO rowValues)
	{
		containerDataSource.addItem(rowId, rowValues);
	}
	
	private void fireNewRecordCommand()
	{
		getEditorListener().executeCommand(ViewCommand.builder()
				.setPropertyPath(getPropertyPath())
				.setCommandId(GridCommands.COMMAND_GridNewRow)
				.build());
	}

}
