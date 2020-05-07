package de.metas.adempiere.form.terminal;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.beans.PropertyChangeListener;
import java.util.List;

import org.compiere.util.DisplayType;
import org.compiere.util.NamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.field.constraint.ITerminalFieldConstraint;
import de.metas.picking.legacy.form.IInputMethod;

public interface IPropertiesPanelModel
{
	/**
	 * Event fired when entire model content was changed
	 */
	String PROPERTY_ContentChanged = "ContentChanged";
	/**
	 * Event fired when a property was changed.
	 * <ul>
	 * <li>Old Value: null
	 * <li>New Value: property name
	 * </ul>
	 */
	String PROPERTY_ValueChanged = "ValueChanged";

	/**
	 * Event fired when model is asking for a validation on UI level.
	 *
	 * Use {@link #validateUI()}.
	 */
	String PROPERTY_ValidateUIRequest = "ValidateUIRequest";

	ITerminalContext getTerminalContext();

	void addPropertyChangeListener(PropertyChangeListener listener);

	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

	List<String> getPropertyNames();

	String getPropertyDisplayName(String propertyName);

	int getDisplayType(String propertyName);

	/**
	 * Returns a list of additional input methods for the given propertyNames's attribute.
	 */
	List<IInputMethod<?>> getAdditionalInputMethods(String propertyName);

	Object getPropertyValue(String propertyName);

	/**
	 * Gets available values in case <code>propertyName</code> is a a list (i.e. {@link #getDisplayType(int)} returns {@link DisplayType#List}).
	 *
	 * @param propertyName
	 * @return available values
	 */
	List<? extends NamePair> getPropertyAvailableValues(String propertyName);

	/**
	 * Gets {@link ITerminalLookup} in case <code>propertyName</code> is a lookup field (i.e. {@link #getDisplayType(int)} return {@link DisplayType#Search}).
	 *
	 * @param propertyName
	 * @return
	 */
	ITerminalLookup getPropertyLookup(String propertyName);

	void setPropertyValue(String propertyName, Object value);

	boolean isEditable(String propertyName);

	ITerminalFieldConstraint<Object> getConstraint(String propertyName);

	void commitEdit();

	void validateUI();
}
