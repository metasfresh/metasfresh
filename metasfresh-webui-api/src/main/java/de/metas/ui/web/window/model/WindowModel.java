package de.metas.ui.web.window.model;

import java.util.List;
import java.util.Set;

import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.WindowConstants.OnChangesFound;
import de.metas.ui.web.window.datasource.SaveResult;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.model.action.Action;
import de.metas.ui.web.window.shared.datatype.GridRowId;
import de.metas.ui.web.window.shared.datatype.PropertyValuesDTO;

/*
 * #%L
 * metasfresh-webui-api
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

public interface WindowModel
{
	void subscribe(Object subscriberObj);

	void unsubscribe(Object subscriberObj);

	void setRootPropertyDescriptor(PropertyDescriptor rootPropertyDescriptor);

	boolean hasPreviousRecord();

	void previousRecord(OnChangesFound onChangesFound);

	boolean hasNextRecord();

	void nextRecord(OnChangesFound onChangesFound);

	List<Action> getActions();

	/**
	 * Gets a map of all "selected" property name and their values.
	 *
	 * @param selectedPropertyNames
	 * @return
	 */
	PropertyValuesDTO getPropertyValuesDTO(Set<PropertyName> selectedPropertyNames);

	boolean hasProperty(PropertyName propertyName);

	void setProperty(PropertyName propertyName, Object value);

	Object getProperty(PropertyName propertyName);

	Object getPropertyOrNull(PropertyName propertyName);

	void newRecordAsCopyById(Object recordId);

	SaveResult saveRecord();

	/**
	 * Ask the model to create a new grid row.
	 *
	 * @param gridPropertyName
	 * @return the ID of newly created now
	 */
	GridRowId gridNewRow(PropertyName gridPropertyName);

	void setGridProperty(PropertyName gridPropertyName, Object rowId, PropertyName propertyName, Object value);

	Object getGridProperty(PropertyName gridPropertyName, Object rowId, PropertyName propertyName);

	void executeAction(Action action);
}
