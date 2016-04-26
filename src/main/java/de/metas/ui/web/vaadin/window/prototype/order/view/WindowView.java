package de.metas.ui.web.vaadin.window.prototype.order.view;

import java.util.Map;

import com.vaadin.ui.Component;

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

public interface WindowView extends Component
{

	void setListener(WindowViewListener listener);

	void setNextRecordEnabled(boolean enabled);

	void setPreviousRecordEnabled(boolean enabled);

	void setProperties(Map<PropertyName, Object> propertiesAsMap);
	
	void setProperty(PropertyName propertyName, final Object value);

	void setGridProperty(PropertyName gridPropertyName, Object rowId, PropertyName propertyName, Object value);

	void gridNewRow(PropertyName gridPropertyName, Object rowId, Map<PropertyName, Object> rowValues);

	void commitChanges();

	void confirmDiscardChanges();

	void showError(String message);

}
