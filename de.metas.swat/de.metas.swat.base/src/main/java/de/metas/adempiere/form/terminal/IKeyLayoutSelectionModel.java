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


import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;

public interface IKeyLayoutSelectionModel extends IDisposable
{
	String PROPERTY_AllowKeySelection = "AllowKeySelection";
	String PROPERTY_KeySelectionColor = "KeySelectionColor";
	String PROPERTY_SelectedKey = "SelectedKey";
	String PROPERTY_SelectedKeys = "SelectedKeys";
	String PROPERTY_AutoSelectIfOnlyOne = "AutoSelectIfOnlyOne";

	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	void addPropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * @return selected key or null
	 */
	ITerminalKey getSelectedKeyOrNull();

	/**
	 * Get selected key, converted to the given class
	 * 
	 * @param terminalKeyClass
	 * @return selected key or null
	 * 
	 * @throws ClassCastException if the key could not be converted to the given type (programmatic error)
	 */
	<T extends ITerminalKey> T getSelectedKeyOrNull(Class<T> terminalKeyClass) throws ClassCastException;

	/**
	 * Get selected key, converted to the given class
	 * 
	 * @param terminalKeyClass
	 * @param errorMsgNotSelected
	 * @return the selected key
	 * 
	 * @throws ClassCastException if the key could not be converted to the given type (programmatic error)
	 * @throws TerminalException if the selected key is null (no key selected)
	 */
	<T extends ITerminalKey> T getSelectedKey(Class<T> terminalKeyClass, String errorMsgNotSelected) throws ClassCastException, TerminalException;

	/**
	 * @return selected keys
	 */
	Collection<ITerminalKey> getSelectedKeys();

	/**
	 * @param terminalKeyClass
	 * @return selected keys, converted to given type
	 * 
	 * @throws ClassCastException if the keys could not be converted to the given type (programmatic error)
	 */
	<T extends ITerminalKey> List<T> getSelectedKeys(Class<T> terminalKeyClass) throws ClassCastException;

	/**
	 * Directly set selected key.<br>
	 * <br>
	 * <b>NOTE:</b> please use it only if you know what you are doing. Maybe you are looking for {@link #onKeySelected(ITerminalKey)}.
	 * 
	 * @param selectedKey
	 */
	void setSelectedKey(ITerminalKey selectedKey);

	/**
	 * Set terminal key, considering user-defined action; will apply toggling options.
	 * 
	 * @param selectedKey
	 */
	void onKeySelected(ITerminalKey selectedKey);

	void setKeySelectionColor(Color color);

	Color getKeySelectionColor();

	void setAllowKeySelection(boolean allowKeySelection);

	boolean isAllowKeySelection();

	void setAutoSelectIfOnlyOne(boolean autoSelectIfOnlyOne);

	/**
	 * @return true if a key shall be auto-selected if its the only one from panel
	 */
	boolean isAutoSelectIfOnlyOne();

	/**
	 * Set if selection can be toggled (second key press is deselect)<br>
	 * <b>Default:</b> <code>false</code>
	 * 
	 * @param isToggleableSelection
	 */
	void setToggleableSelection(boolean isToggleableSelection);

	/**
	 * @return true if selection can be toggled (second key press is deselect)
	 */
	boolean isToggleableSelection();

	/**
	 * Configures layout to allow or disallow the user to have multiple keys selected at one time<br>
	 * <b>Default:</b> <code>false</code>
	 * 
	 * @param allowMultipleSelection
	 */
	void setAllowMultipleSelection(boolean allowMultipleSelection);

	/**
	 * @return true if the user can have multiple keys selected at one time
	 */
	boolean isAllowMultipleSelection();

	/**
	 * Clear current selection
	 */
	void clearSelection();

	/**
	 * 
	 * @param key
	 * @return true if given key is selected
	 */
	boolean isSelected(ITerminalKey key);

	/**
	 * 
	 * @return true if selection is empty (i.e. nothing is selected)
	 */
	boolean isEmpty();

	@Override
	void dispose();
}
