package de.metas.ui.web.vaadin.window.view;

import de.metas.ui.web.vaadin.window.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.shared.datatype.PropertyValuesDTO;

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

public interface WindowView
{
	com.vaadin.ui.Component getComponent();
	
	void setListener(WindowViewListener listener);

	void setRootPropertyDescriptor(PropertyDescriptor rootPropertyDescriptor);

	void setNextRecordEnabled(boolean enabled);

	void setPreviousRecordEnabled(boolean enabled);

	void setProperties(PropertyValuesDTO propertiesAsMap);
	
	void setProperty(PropertyName propertyName, final Object value);

	void setGridProperty(PropertyName gridPropertyName, Object rowId, PropertyName propertyName, Object value);

	void gridNewRow(PropertyName gridPropertyName, Object rowId, PropertyValuesDTO rowValues);

	void commitChanges();

	void confirmDiscardChanges();

	void showError(String message);
}
