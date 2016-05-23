package de.metas.ui.web.vaadin.window.editor;

import java.util.Map;

import org.slf4j.Logger;

import com.google.common.util.concurrent.ListenableFuture;
import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.shared.datatype.GridRowId;

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
	private final Map<PropertyName, PropertyDescriptor> descriptors;
	private final EditorFactory editorsFactory = new EditorFactory();

	private EditorListener editorListener = NullEditorListener.instance;

	public GridTableFieldFactory(final PropertyDescriptor propertyDescriptor)
	{
		super();
		gridPropertyName = propertyDescriptor.getPropertyName();
		descriptors = propertyDescriptor.getChildPropertyDescriptorsAsMap();
	}

	@Override
	public Field<?> createField(final Container container, final Object itemId, final Object propertyId, final Component uiContext)
	{
		final PropertyName propertyName = PropertyName.cast(propertyId);
		final PropertyDescriptor propertyDescriptor = descriptors.get(propertyName);
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

		@Override
		public void valueChange(final PropertyName propertyName, final Object value)
		{
			gridValueChanged(gridPropertyName, gridRowId, propertyName, value);
		}

		@Override
		public ListenableFuture<Object> requestValue(final PropertyName propertyName)
		{
			return requestGridValue(gridPropertyName, gridRowId, propertyName);
		}
	}

}