package de.metas.ui.web.vaadin.window.prototype.order.view;

import java.util.Set;

import com.google.common.util.concurrent.ListenableFuture;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants.OnChangesFound;

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
	void viewPropertyChanged(PropertyName propertyName, Object value);

	/** View notified a grid property value has been changed */
	void viewGridPropertyChanged(PropertyName gridPropertyName, Object rowId, PropertyName propertyName, Object value);
	
	/** View asked to move to next record */
	void viewNextRecord(OnChangesFound onChangesFound);
	
	/** View asked to move to previous record */
	void viewPreviousRecord(OnChangesFound onChangesFound);

	void viewNewRecord();

	/** View asked to save the current editing */
	void viewSaveEditing();

	/** View asked to cancel the current editing */
	void viewCancelEditing();

	/** View is asking for a value update */
	ListenableFuture<Object> viewRequestValue(PropertyName propertyName);

	ListenableFuture<Object> viewRequestGridValue(PropertyName gridPropertyName, Object rowId, PropertyName propertyName);
}
