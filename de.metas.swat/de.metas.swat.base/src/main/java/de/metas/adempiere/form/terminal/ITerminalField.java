/**
 * 
 */
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

import de.metas.adempiere.form.terminal.field.constraint.ITerminalFieldConstraint;

/**
 * @author tsa
 * @author al
 * 
 * @param <T>
 */
public interface ITerminalField<T> extends IFocusableComponent
{
	/**
	 * Generic message when an exception occured while parsing a specific text for various reasons<br>
	 * <code>e.g "abc"->BigDecimal</code>
	 */
	String MSG_ErrorParsingText = "ErrorParsingText";

	/**
	 * Value-changed action
	 */
	String ACTION_ValueChanged = "ValueChanged";

	/**
	 * Add {@link PropertyChangeListener}
	 * 
	 * @param listener
	 */
	void addListener(PropertyChangeListener listener);

	/**
	 * Add {@link PropertyChangeListener} with given name
	 * 
	 * @param propertyName
	 * @param listener
	 */
	void addListener(String propertyName, PropertyChangeListener listener);

	/**
	 * Remove {@link PropertyChangeListener}
	 * 
	 * @param listener
	 */
	void removeListener(PropertyChangeListener listener);

	/**
	 * Remove {@link PropertyChangeListener} with given name
	 * 
	 * @param propertyName
	 * @param listener
	 */
	void removeListener(String propertyName, PropertyChangeListener listener);

	/**
	 * Set the component's value.
	 * 
	 * @param value
	 * @throws WrongValueException if <i>any</i> constraint evaluation failed
	 */
	void setValue(Object value) throws WrongValueException;

	/**
	 * Set the component's value. Fire propertyChangeEvent if <code>fireEvent=true</code>.
	 * 
	 * @param value
	 * @param fireEvent
	 */
	void setValue(Object value, boolean fireEvent);

	/**
	 * Add a constraint to this field.
	 * 
	 * @param constraint
	 */
	void addConstraint(ITerminalFieldConstraint<T> constraint);

	/**
	 * @return field name
	 */
	String getName();

	/**
	 * Get the component's value
	 * 
	 * @return value
	 */
	T getValue();

	/**
	 * Request focus on this component from the implementing GUI framework.
	 */
	@Override
	void requestFocus();

	/**
	 * @return true if the component is editable
	 */
	boolean isEditable();

	/**
	 * Set if this component can be edited or not
	 * 
	 * @param editable
	 */
	void setEditable(boolean editable);
	
	/**
	 * 
	 * @return true if field value is valid
	 */
	boolean isValid();
}
