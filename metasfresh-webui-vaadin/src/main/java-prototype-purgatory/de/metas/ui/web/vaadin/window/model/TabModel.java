package de.metas.ui.web.vaadin.window.model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;

import de.metas.ui.web.vaadin.window.WindowContext;
import de.metas.ui.web.vaadin.window.data.Query;
import de.metas.ui.web.vaadin.window.data.TableRowDataSource;
import de.metas.ui.web.vaadin.window.descriptor.DataFieldPropertyDescriptor;
import de.metas.ui.web.vaadin.window.descriptor.DataRowDescriptor;
import de.metas.ui.web.vaadin.window.model.TabToolbarModel.ActionExecutor;

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

public class TabModel
{
	private final WindowContext windowContext;
	private final DataRowDescriptor descriptor;

	//
	// Parent link
	private TabModel parentTabModel;
	private final Set<TabModel> childTabModels = new LinkedHashSet<>();
	private Set<DataFieldProperty> linkParentFields = ImmutableSet.of();

	private final TableRowDataSource dataSourceProvider;

	private final TabToolbarModel toolbarModel = new TabToolbarModel();

	private final DataRowContainer rowsContainer;
	private Object currentRowKey;
	private final DataRowItem currentRow;

	@SuppressWarnings("serial")
	private final Property.ValueChangeListener linkParentFieldChangeListener = new Property.ValueChangeListener()
	{

		@Override
		public void valueChange(final ValueChangeEvent event)
		{
			final DataFieldProperty linkParentField = (DataFieldProperty)event.getProperty();
			onLinkParentFieldChanged(linkParentField);
		}
	};

	public TabModel(final WindowContext windowContext, final DataRowDescriptor descriptor)
	{
		super();
		this.windowContext = windowContext;
		this.descriptor = descriptor;

		dataSourceProvider = new TableRowDataSource(descriptor);

		rowsContainer = new DataRowContainer(descriptor);

		currentRow = new DataRowItem(descriptor);

		setupToolbarActions();
	}

	private void setupToolbarActions()
	{
		toolbarModel.setActionExecutor(TabToolbarModel.ACTION_PreviousRecord, new TabToolbarModel.ActionExecutor()
		{

			@Override
			public void executeAction(String action)
			{
				onPreviousRecord();
			}
		});
		toolbarModel.setActionExecutor(TabToolbarModel.ACTION_NextRecord, new TabToolbarModel.ActionExecutor()
		{

			@Override
			public void executeAction(String action)
			{
				onNextRecord();
			}
		});
	}
	
	public WindowContext getWindowContext()
	{
		return windowContext;
	}

	public TabToolbarModel getToolbarModel()
	{
		return toolbarModel;
	}

	public DataRowDescriptor getDescriptor()
	{
		return descriptor;
	}

	public String getName()
	{
		return descriptor.getName();
	}

	public int getTabNo()
	{
		return descriptor.getTabNo();
	}

	public int getTabLevel()
	{
		return descriptor.getTabLevel();
	}

	void setParentTabModel(final TabModel parentTabModelNew)
	{
		if (parentTabModel == parentTabModelNew)
		{
			return;
		}

		parentTabModel = parentTabModelNew;

		//
		// Un-bind previous parent fields
		for (final DataFieldProperty linkParentField : linkParentFields)
		{
			linkParentField.removeValueChangeListener(linkParentFieldChangeListener);
		}

		//
		// Binding to new parent fields
		linkParentFields = extractLinkingFieldsFromParent(parentTabModelNew);
		for (final DataFieldProperty linkParentField : linkParentFields)
		{
			linkParentField.addValueChangeListener(linkParentFieldChangeListener);
		}
	}

	private final Set<DataFieldProperty> extractLinkingFieldsFromParent(final TabModel parentTabModel)
	{
		final DataRowDescriptor parentTabDescriptor = parentTabModel.getDescriptor();
		final Set<DataFieldPropertyDescriptor> linkingParentFieldDescriptors = descriptor.getLinkingFieldsFromParent(parentTabDescriptor);
		if (linkingParentFieldDescriptors.isEmpty())
		{
			return ImmutableSet.of();
		}

		final DataRowItem parentCurrentRow = parentTabModel.getCurrentRow();
		final Set<DataFieldProperty> parentFields = new HashSet<>();

		for (final DataFieldPropertyDescriptor parentFieldDescriptor : linkingParentFieldDescriptors)
		{
			final Object columnName = parentFieldDescriptor.getColumnName();
			final DataFieldProperty parentField = parentCurrentRow.getItemProperty(columnName);
			parentFields.add(parentField);
		}

		return ImmutableSet.copyOf(parentFields);
	}

	void addChildTabModel(final TabModel childTabModel)
	{
		Preconditions.checkNotNull(childTabModel, "childTabModel is null");
		childTabModels.add(childTabModel);
	}

	public void query(final Query query)
	{
		final Object currentRowKey = this.currentRowKey;
		
		dataSourceProvider.refresh(rowsContainer, query);

		DataRowItem currentRowNew = rowsContainer.getItem(currentRowKey);
		if(currentRowNew == null && !rowsContainer.isEmpty())
		{
			currentRowNew = rowsContainer.firstItem();
		}
		
		setCurrentRow(currentRowNew);
	}
	
	private void setCurrentRow(final DataRowItem row)
	{
		if (row == null)
		{
			currentRowKey = null;
			currentRow.setFrom(null);
		}
		else
		{
			currentRowKey = row.getKey();
			currentRow.setFrom(row);
			
			toolbarModel.setActionEnabled(TabToolbarModel.ACTION_PreviousRecord, true);
			toolbarModel.setActionEnabled(TabToolbarModel.ACTION_NextRecord, true);
		}
	}

	public void query()
	{
		final Query query = null;
		query(query);
	}

	public DataRowContainer getRowsContainer()
	{
		return rowsContainer;
	}

	public DataRowItem getCurrentRow()
	{
		return currentRow;
	}

	private final void onLinkParentFieldChanged(final DataFieldProperty linkParentField)
	{

		// TODO Auto-generated method stub

	}

	private void onPreviousRecord()
	{
		final Object prevRowKey = rowsContainer.prevItemId(currentRowKey);
		final DataRowItem prevRow = rowsContainer.getItem(prevRowKey);
		if (prevRow == null)
		{
			throw new IllegalStateException("No previous");
		}
		setCurrentRow(prevRow);
	}

	private void onNextRecord()
	{
		final Object nextRowKey = rowsContainer.nextItemId(currentRowKey);
		final DataRowItem nextRow = rowsContainer.getItem(nextRowKey);
		if (nextRow == null)
		{
			throw new IllegalStateException("No next");
		}
		setCurrentRow(nextRow);
	}

}
