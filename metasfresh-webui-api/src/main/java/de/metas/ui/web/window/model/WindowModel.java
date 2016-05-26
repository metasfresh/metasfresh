package de.metas.ui.web.window.model;

import java.util.Set;

import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.WindowConstants.OnChangesFound;
import de.metas.ui.web.window.datasource.SaveResult;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.model.action.ActionsList;
import de.metas.ui.web.window.shared.command.ViewCommand;
import de.metas.ui.web.window.shared.command.ViewCommandResult;
import de.metas.ui.web.window.shared.datatype.PropertyPath;
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

	/**
	 * Gets a map of all "selected" property name and their values.
	 *
	 * @param selectedPropertyNames
	 * @return
	 */
	PropertyValuesDTO getPropertyValuesDTO(Set<PropertyName> selectedPropertyNames);

	boolean hasProperty(PropertyPath propertyPath);

	void setProperty(PropertyPath propertyPath, Object value);

	Object getProperty(PropertyPath propertyPath);

	Object getPropertyOrNull(PropertyPath propertyPath);

	void newRecordAsCopyById(Object recordId);

	SaveResult saveRecord();

	ActionsList getActions();

	void executeAction(String actionId);

	ActionsList getChildActions(String actionId);

	ViewCommandResult executeCommand(ViewCommand command) throws Exception;
}
