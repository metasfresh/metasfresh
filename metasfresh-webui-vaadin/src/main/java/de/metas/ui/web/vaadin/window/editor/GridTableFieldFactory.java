package de.metas.ui.web.vaadin.window.editor;

import java.util.Map;

import org.slf4j.Logger;

import com.google.common.util.concurrent.ListenableFuture;
import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.shared.command.ViewCommand;
import de.metas.ui.web.window.shared.datatype.GridRowId;
import de.metas.ui.web.window.shared.datatype.PropertyPath;
import de.metas.ui.web.window.shared.descriptor.ViewPropertyDescriptor;

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

@SuppressWarnings("serial")
final class GridTableFieldFactory implements TableFieldFactory
{
	private static final Logger logger = LogManager.getLogger(GridTableFieldFactory.class);

	private final PropertyName gridPropertyName;
	private final Map<PropertyName, ViewPropertyDescriptor> descriptors;
	private final EditorFactory editorsFactory = new EditorFactory();

	private EditorListener editorListener = NullEditorListener.instance;

	public GridTableFieldFactory(final ViewPropertyDescriptor propertyDescriptor)
	{
		super();
		gridPropertyName = propertyDescriptor.getPropertyName();
		descriptors = propertyDescriptor.getChildDescriptors();
	}

	@Override
	public Field<?> createField(final Container container, final Object itemId, final Object propertyId, final Component uiContext)
	{
		final PropertyName propertyName = PropertyName.cast(propertyId);
		final ViewPropertyDescriptor propertyDescriptor = descriptors.get(propertyName);
		if (propertyDescriptor == null)
		{
			logger.warn("No descriptor found for {}", propertyName);
			return null;
		}

		final Editor editor = editorsFactory.createEditor(propertyDescriptor);
		if (!(editor instanceof Field<?>))
		{
			logger.warn("No suitable editor found ({}) for {}", editor, propertyDescriptor);
			return null;
		}

		final GridRowId gridRowId = GridRowId.of(itemId);
		editor.setEditorListener(new GridEditorListener(gridRowId));

		final Field<?> field = (Field<?>)editor;
		return field;
	}

	public void setEditorListener(final EditorListener listener)
	{
		editorListener = listener != null ? listener : NullEditorListener.instance;
	}

	private final class GridEditorListener extends ForwardingEditorListener
	{
		private final GridRowId gridRowId;

		private GridEditorListener(final GridRowId gridRowId)
		{
			super();
			this.gridRowId = gridRowId;
		}

		@Override
		protected EditorListener getDelegate()
		{
			return editorListener;
		}

		private final PropertyPath toGridPropertyPath(final PropertyPath propertyPath)
		{
			return PropertyPath.of(gridPropertyName, gridRowId, propertyPath.getPropertyName());
		}

		@Override
		public void valueChange(final PropertyPath propertyPath, final Object value)
		{
			final PropertyPath gridPropertyPath = toGridPropertyPath(propertyPath);
			getDelegate().valueChange(gridPropertyPath, value);
		}

		@Override
		public ListenableFuture<Object> requestValue(final PropertyPath propertyPath)
		{
			final PropertyPath gridPropertyPath = toGridPropertyPath(propertyPath);
			return getDelegate().requestValue(gridPropertyPath);
		}

		private final ViewCommand toGridViewCommand(final ViewCommand command)
		{
			return command.changePropertyPath(toGridPropertyPath(command.getPropertyPath()));
		}

		@Override
		public void executeCommand(final ViewCommand command)
		{
			super.executeCommand(toGridViewCommand(command));
		}

		@Override
		public void executeCommand(final ViewCommand command, final ViewCommandCallback callback)
		{
			super.executeCommand(toGridViewCommand(command), callback);
		}
	}

}