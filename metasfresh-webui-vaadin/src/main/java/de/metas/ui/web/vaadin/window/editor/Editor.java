package de.metas.ui.web.vaadin.window.editor;

import java.util.List;
import java.util.Set;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

import de.metas.ui.web.vaadin.window.PropertyLayoutInfo;
import de.metas.ui.web.vaadin.window.PropertyName;

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

public interface Editor
{
	Component getComponent();

	String getCaption();

	PropertyName getPropertyName();

	Set<PropertyName> getWatchedPropertyNames();

	void setValue(PropertyName propertyName, Object value);

	void setEditorListener(EditorListener listener);

	boolean isAddingChildEditorsAllowed();

	void addChildEditor(Editor editor);

	List<Editor> getChildEditors();

	Label getLabel();

	PropertyLayoutInfo getLayoutInfo();

	void setAttribute(final String name, final Object value);

	<T> T getAttribute(final String name);
}
