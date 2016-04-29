package de.metas.ui.web.vaadin.window.prototype.order.editor;

import java.util.Map;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;

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
		if (propertyDescriptor == null)
		{
			GridEditor.logger.warn("No descriptor found for {}", propertyName);
			return null;
		}

		final Editor editor = editorsFactory.createEditor(propertyDescriptor);
		if (editor instanceof Field<?>)
		{
			final Field<?> field = (Field<?>)editor;
			return field;
		}

		GridEditor.logger.warn("No suitable editor found for {}", propertyDescriptor);
		return null;
	}

}