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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.text.Format;
import java.text.ParseException;

public interface ITerminalTextField extends ITerminalField<String>
{
	int TYPE_Password = 900000;

	// using class.SimpleName for the property names to make log messages more readable

	String PROPERTY_ActionPerformed = ITerminalTextField.class.getSimpleName() + "#ActionPerformed";
	String PROPERTY_FocusLost = ITerminalTextField.class.getSimpleName() + "#FocusLost";
	String PROPERTY_FocusGained = ITerminalTextField.class.getSimpleName() + "#FocusGained";
	String PROPERTY_TextChanged = ITerminalTextField.class.getSimpleName() + "#TextChanged";

	String ACTION_Nothing = "CreateField";

	String getAction();

	void setAction(String action);

	String getTitle();

	/**
	 * Set the component's (displayed) text, call {@link #commitEdit()} and fire a {@link #PROPERTY_TextChanged} event.
	 * If {@link #commitEdit()} fails, then ignore the failure and carry on.
	 *
	 * @param name
	 */
	void setText(String name);

	String getText();

	String getTextHead();

	String getTextTail();

	@Override
	void setEditable(boolean editable);

	@Override
	boolean isEditable();

	@Override
	void addListener(PropertyChangeListener listener);

	void setKeyLayout(IKeyLayout keyLayout);

	IKeyLayout getKeyLayout();

	/**
	 * Invoke a formatter or validator (if there is any) and if successful, update the "inner" value with the currently displayed text.
	 * <p>
	 * Note: see the <a href="http://docs.oracle.com/javase/tutorial/uiswing/components/formattedtextfield.html">How to Use Formatted Text Fields</a> tutorial for details.<br>
	 *
	 * @throws ParseException
	 */
	void commitEdit() throws ParseException;

	int getDisplayType();

	void setCaretPosition(int position);

	void setBackground(Color color);

	void processPendingEvents();

	<T> void registerFactory(Class<T> clazz, IFactory<T> factory);

	/**
	 *
	 * @return true if keyboard button is displayed
	 */
	boolean isShowKeyboardButton();

	/**
	 * Configures if user shall see the keyboard button or not.
	 *
	 * NOTE: setting this value to false does not prevent the keyboard from popping up. If you don't want to the keyboard to be displayed please {@link #setKeyLayout(IKeyLayout)} with
	 * <code>null</code> parameter.
	 *
	 * @param isShowKeyboardButton
	 */
	void setShowKeyboardButton(boolean isShowKeyboardButton);

	Format getFormat();

	/**
	 * Automatically commit the value if valid.
	 *
	 * As an effect, {@link ITerminalField#ACTION_ValueChanged} event will be fired.
	 *
	 * @param commit
	 */
	void setCommitOnValidEdit(boolean commit);

	/**
	 * @return currently active keyboard or null if there is no keyboard active
	 */
	ITerminalKeyDialog getActiveKeyboard();

	/**
	 * @return true if there is a active on screen keyboard
	 */
	boolean isKeyboardActive();

	/**
	 * Request focus. Same as {@link #requestFocus()} but this method allows caller to specify if contained text shall be selected too or not.
	 *
	 * @param selectAllText if true all text contained by this compoment will be also selected
	 */
	void requestFocus(boolean selectAllText);

	/**
	 * @return true if
	 *         <ul>
	 *         <li>this component has focus
	 *         <li>this compoent has the on screen keyboard active which is the focus owner (also because the active keyboard is modal)
	 *         </ul>
	 */
	boolean isFocusOwner();

	/**
	 * Enable: handle the Tab key programatically and trigger an {@link #PROPERTY_FocusLost} event.
	 *
	 * WARNING: usually tab key is automatically handled by FocusTraversalPolicy, but in case there is no other component to focus,
	 * no focus lost event will be triggered.
	 * Mainly that's the case where you shall use this method.
	 */
	void enableHandleTabKeyAsFocusLost();
}
