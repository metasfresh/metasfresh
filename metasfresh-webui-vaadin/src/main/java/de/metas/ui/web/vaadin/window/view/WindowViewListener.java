package de.metas.ui.web.vaadin.window.view;

import java.util.Set;

import com.google.common.util.concurrent.ListenableFuture;

import de.metas.ui.web.vaadin.window.editor.EditorListener.ViewCommandCallback;
import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.shared.action.ActionsList;
import de.metas.ui.web.window.shared.command.ViewCommand;
import de.metas.ui.web.window.shared.datatype.PropertyPath;

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

public interface WindowViewListener
{
	void viewSubscribeToValueChanges(Set<PropertyName> propertyNames);

	/** View notified a property value has been changed */
	void viewPropertyChanged(PropertyPath propertyPath, Object value);
	
	/** View is asking for a value update */
	ListenableFuture<Object> viewRequestValue(PropertyPath propertyPath);

	/** View requested an action to be executed */
	void onActionClicked(String actionId);
	
	ActionsList viewRequestChildActions(final String actionId);

	void viewCommandExecute(ViewCommand command, ViewCommandCallback callback);
}
