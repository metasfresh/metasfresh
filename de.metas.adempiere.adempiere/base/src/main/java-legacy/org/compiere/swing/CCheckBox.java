/********************************************************************* 
* This file is part of Adempiere ERP Bazaar                          * 
* http://www.adempiere.org                                           * 
*                                                                    * 
* Copyright (C) 1999 - 2006 Compiere Inc.                            * 
* Copyright (C) Contributors                                         * 
*                                                                    * 
* This program is free software; you can redistribute it and/or      * 
* modify it under the terms of the GNU General Public License        * 
* as published by the Free Software Foundation; either version 2     * 
* of the License, or (at your option) any later version.             * 
*                                                                    * 
* This program is distributed in the hope that it will be useful,    * 
* but WITHOUT ANY WARRANTY; without even the implied warranty of     * 
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       * 
* GNU General Public License for more details.                       * 
*                                                                    * 
* You should have received a copy of the GNU General Public License  * 
* along with this program; if not, write to the Free Software        * 
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         * 
* MA 02110-1301, USA.                                                * 
*                                                                    * 
* Contributors:                                                      * 
*  - Bahman Movaqar (bmovaqar@users.sf.net)                          * 
**********************************************************************/
package org.compiere.swing;

import java.awt.Color;
import java.awt.event.InputEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentInputMapUIResource;

/**
 * Adempiere CheckBox
 * 
 * @author Jorg Janke
 * @version $Id: CCheckBox.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class CCheckBox extends JCheckBox implements CEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6115543971487470944L;

	/**
	 * Creates an initially unselected check box button with no text, no icon.
	 */
	public CCheckBox() {
		super();
		init();
	}

	/**
	 * Creates an initially unselected check box with an icon.
	 * 
	 * @param icon
	 *            the Icon image to display
	 */
	public CCheckBox(Icon icon) {
		super(icon);
		init();
	}

	/**
	 * Creates a check box with an icon and specifies whether or not it is
	 * initially selected.
	 * 
	 * @param icon
	 *            the Icon image to display
	 * @param selected
	 *            a boolean value indicating the initial selection state. If
	 *            <code>true</code> the check box is selected
	 */
	public CCheckBox(Icon icon, boolean selected) {
		super(icon, selected);
		init();
	}

	/**
	 * Creates an initially unselected check box with text.
	 * 
	 * @param text
	 *            the text of the check box.
	 */
	public CCheckBox(String text) {
		super(text);
		init();
	}

	/**
	 * Creates a check box where properties are taken from the Action supplied.
	 * 
	 * @param a
	 */
	public CCheckBox(Action a) {
		super(a);
		init();
	}

	/**
	 * Creates a check box with text and specifies whether or not it is
	 * initially selected.
	 * 
	 * @param text
	 *            the text of the check box.
	 * @param selected
	 *            a boolean value indicating the initial selection state. If
	 *            <code>true</code> the check box is selected
	 */
	public CCheckBox(String text, boolean selected) {
		super(text, selected);
		init();
	}

	/**
	 * Creates an initially unselected check box with the specified text and
	 * icon.
	 * 
	 * @param text
	 *            the text of the check box.
	 * @param icon
	 *            the Icon image to display
	 */
	public CCheckBox(String text, Icon icon) {
		super(text, icon, false);
		init();
	}

	/**
	 * Creates a check box with text and icon, and specifies whether or not it
	 * is initially selected.
	 * 
	 * @param text
	 *            the text of the check box.
	 * @param icon
	 *            the Icon image to display
	 * @param selected
	 *            a boolean value indicating the initial selection state. If
	 *            <code>true</code> the check box is selected
	 */
	public CCheckBox(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
		init();
	}

	/**
	 * Common Init
	 */
	private void init() {
		// Default to transparent, works better under windows look and feel
		setOpaque(false);
	} // init

	/** ********************************************************************** */

	/** Mandatory (default false) */
	private boolean m_mandatory = false;

	/** Read-Write */
	private boolean m_readWrite = true;

	/**
	 * Set Editor Mandatory
	 * 
	 * @param mandatory
	 *            true, if you have to enter data
	 */
	@Override
	public void setMandatory(boolean mandatory) {
		m_mandatory = mandatory;
		setBackground(false);
	} // setMandatory

	/**
	 * Is Field mandatory
	 * 
	 * @return true, if mandatory
	 */
	@Override
	public boolean isMandatory() {
		return m_mandatory;
	} // isMandatory

	/**
	 * Enable Editor
	 * 
	 * @param rw
	 *            true, if you can enter/select data
	 */
	@Override
	public void setReadWrite(boolean rw) {
		if (super.isEnabled() != rw)
			super.setEnabled(rw);
		setBackground(false);
		m_readWrite = rw;
	} // setEditable

	/**
	 * Is it possible to edit
	 * 
	 * @return true, if editable
	 */
	@Override
	public boolean isReadWrite() {
		return m_readWrite;
	} // isEditable

	/**
	 * Set Background based on editable/mandatory/error - ignored -
	 * 
	 * @param error
	 *            if true, set background to error color, otherwise
	 *            mandatory/editable
	 */
	@Override
	public void setBackground(boolean error) {
	} // setBackground

	/**
	 * Set Background
	 * 
	 * @param bg
	 */
	@Override
	public void setBackground(Color bg) {
		if (bg.equals(getBackground()))
			return;
		super.setBackground(bg);
	} // setBackground

	/** Retain value */
	private Object m_value = null;

	/**
	 * Set Editor to value. Interpret Y/N and Boolean
	 * 
	 * @param value
	 *            value of the editor
	 */
	@Override
	public void setValue(Object value) {
		m_value = value;
		boolean sel = false;
		if (value == null)
			sel = false;
		else if (value.toString().equals("Y"))
			sel = true;
		else if (value.toString().equals("N"))
			sel = false;
		else if (value instanceof Boolean)
			sel = ((Boolean) value).booleanValue();
		else {
			try {
				sel = Boolean.getBoolean(value.toString());
			} catch (Exception e) {
			}
		}
		this.setSelected(sel);
	} // setValue

	/**
	 * Return Editor value
	 * 
	 * @return current value as String or Boolean
	 */
	@Override
	public Object getValue() {
		if (m_value instanceof String)
			return super.isSelected() ? "Y" : "N";
		return new Boolean(isSelected());
	} // getValue

	/**
	 * Return Display Value
	 * 
	 * @return displayed String value
	 */
	@Override
	public String getDisplay() {
		if (m_value instanceof String)
			return super.isSelected() ? "Y" : "N";
		return Boolean.toString(super.isSelected());
	} // getDisplay

	/**
	 * Set Text
	 * 
	 * @param mnemonicLabel
	 *            text
	 */
	@Override
	public void setText(String mnemonicLabel) {
		super.setText(createMnemonic(mnemonicLabel));
	} // setText

	/**
	 * Create Mnemonics of text containing "&". Based on MS notation of &Help =>
	 * H is Mnemonics Creates ALT_
	 * 
	 * @param text
	 *            test with Mnemonics
	 * @return text w/o &
	 */
	private String createMnemonic(String text) {
		if (text == null)
			return text;
		int pos = text.indexOf('&');
		if (pos != -1) // We have a nemonic
		{
			char ch = text.charAt(pos + 1);
			if (ch != ' ') // &_ - is the & character
			{
				setMnemonic(ch);
				return text.substring(0, pos) + text.substring(pos + 1);
			}
		}
		return text;
	} // createMnemonic

	/**
	 * Overrides the JCheckBox.setMnemonic() method, setting modifier keys to
	 * CTRL+SHIFT.
	 * 
	 * @param mnemonic
	 *            The mnemonic character code.
	 */
	@Override
	public void setMnemonic(int mnemonic) {
		super.setMnemonic(mnemonic);

		InputMap map = SwingUtilities.getUIInputMap(this,
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		if (map == null) {
			map = new ComponentInputMapUIResource(this);
			SwingUtilities.replaceUIInputMap(this,
					JComponent.WHEN_IN_FOCUSED_WINDOW, map);
		}
		map.clear();
		String className = this.getClass().getName();
		int mask = InputEvent.ALT_MASK; // Default Buttons
		if (this instanceof JCheckBox // In Tab
				|| className.indexOf("VButton") != -1)
			mask = InputEvent.SHIFT_MASK + InputEvent.CTRL_MASK;
		map.put(KeyStroke.getKeyStroke(mnemonic, mask, false), "pressed");
		map.put(KeyStroke.getKeyStroke(mnemonic, mask, true), "released");
		map.put(KeyStroke.getKeyStroke(mnemonic, 0, true), "released");
		setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, map);
	} // setMnemonic

} // CCheckBox
