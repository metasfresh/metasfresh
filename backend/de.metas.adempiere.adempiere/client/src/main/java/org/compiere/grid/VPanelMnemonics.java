package org.compiere.grid;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.Component;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.compiere.grid.ed.VButton;
import org.compiere.grid.ed.VCheckBox;
import org.compiere.grid.ed.VEditor;
import org.compiere.model.GridField;
import org.compiere.swing.CLabel;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 * Class used to assist {@link VPanel} managing the label and fields mnemonics.
 *
 * @author tsa
 *
 */
class VPanelMnemonics
{
	private final transient Logger log = VPanel.log;

	/** Used Mnemonics */
	private final List<Character> m_mnemonics = new ArrayList<>(30);
	/** Mnemonic Fields */
	private final List<Component> m_fields = new ArrayList<>(30);

	/**
	 * Set Field Mnemonic
	 *
	 * @param mField field
	 */
	public void setMnemonic(final GridField mField)
	{
		if (mField.isCreateMnemonic())
		{
			return;
		}

		final String text = mField.getHeader();
		final int pos = text.indexOf('&');
		if (pos != -1 && text.length() > pos)	// We have a mnemonic - creates Ctrl_Shift_
		{
			final char mnemonic = text.toUpperCase().charAt(pos + 1);
			if (mnemonic != ' ')
			{
				if (!m_mnemonics.contains(mnemonic))
				{
					mField.setMnemonic(mnemonic);
					m_mnemonics.add(mnemonic);
				}
				else
				{
					log.warn(mField.getColumnName() + " - Conflict - Already exists: " + mnemonic + " (" + text + ")");
				}
			}
		}
	}	// setMnemonic

	/**
	 * Set Mnemonic for Label CTRL_SHIFT_x
	 *
	 * @param label label
	 * @param predefinedMnemonic predefined Mnemonic
	 */
	void setMnemonic(final CLabel label, final char predefinedMnemonic)
	{
		String text = label.getText();
		final int pos = text.indexOf('&');
		if (pos != -1 && predefinedMnemonic != 0)
		{
			text = text.substring(0, pos) + text.substring(pos + 1);
			label.setText(text);
			label.setSavedMnemonic(predefinedMnemonic);
			m_fields.add(label);

			if (log.isTraceEnabled())
			{
				log.trace(predefinedMnemonic + " - " + label.getName());
			}
		}
		else
		{
			final char mnemonic = getMnemonic(text, label);
			if (mnemonic != 0)
			{
				label.setSavedMnemonic(mnemonic);
				// label.setDisplayedMnemonic(mnemonic);
			}
		}
	}	// setMnemonic

	/**
	 * Set Mnemonic for Check Box or Button
	 *
	 * @param editor check box or button - other ignored
	 * @param predefinedMnemonic predefined Mnemonic
	 */
	private void setMnemonic(final VEditor editor, final char predefinedMnemonic)
	{
		if (editor instanceof VCheckBox)
		{
			final VCheckBox cb = (VCheckBox)editor;
			String text = cb.getText();
			final int pos = text.indexOf('&');
			if (pos != -1 && predefinedMnemonic != 0)
			{
				text = text.substring(0, pos) + text.substring(pos + 1);
				cb.setText(text);
				cb.setSavedMnemonic(predefinedMnemonic);
				m_fields.add(cb);

				if (log.isTraceEnabled())
				{
					log.trace(predefinedMnemonic + " - " + cb.getName());
				}
			}
			else
			{
				final char mnemonic = getMnemonic(text, cb);
				if (mnemonic != 0)
				{
					cb.setSavedMnemonic(mnemonic);
					// cb.setMnemonic(mnemonic);
				}
			}
		}
		// Button
		else if (editor instanceof VButton)
		{
			final VButton b = (VButton)editor;
			String text = b.getText();
			final int pos = text.indexOf('&');
			if (pos != -1 && predefinedMnemonic != 0)
			{
				text = text.substring(0, pos) + text.substring(pos + 1);
				b.setText(text);
				b.setSavedMnemonic(predefinedMnemonic);
				m_fields.add(b);

				if (log.isTraceEnabled())
				{
					log.trace(predefinedMnemonic + " - " + b.getName());
				}
			}
			else if (b.getColumnName().equals("DocAction"))
			{
				b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
						.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.SHIFT_MASK, false), "pressed");
				b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
						.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.SHIFT_MASK, true), "released");
				// Util.printActionInputMap(b);
			}
			else if (b.getColumnName().equals("Posted"))
			{
				b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
						.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, Event.SHIFT_MASK, false), "pressed");
				b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
						.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, Event.SHIFT_MASK, true), "released");
				// Util.printActionInputMap(b);
			}
			else
			{
				final char mnemonic = getMnemonic(text, b);
				if (mnemonic != 0)
				{
					b.setSavedMnemonic(mnemonic);
				}
			}
		}
	}	// setMnemonic

	/**
	 * Get Mnemonic from text
	 *
	 * @param text text
	 * @param source component
	 * @return Mnemonic or 0 if not unique
	 */
	private char getMnemonic(String text, final Component source)
	{
		if (text == null || text.length() == 0)
		{
			return 0;
		}
		final String oText = text;
		text = text.trim().toUpperCase();
		char mnemonic = text.charAt(0);
		if (m_mnemonics.contains(mnemonic))
		{
			mnemonic = 0;
			// Beginning new word
			int index = text.indexOf(' ');
			while (index != -1 && text.length() > index)
			{
				final char c = text.charAt(index + 1);
				if (Character.isLetterOrDigit(c) && !m_mnemonics.contains(c))
				{
					mnemonic = c;
					break;
				}
				index = text.indexOf(' ', index + 1);
			}
			// Any character
			if (mnemonic == 0)
			{
				for (int i = 1; i < text.length(); i++)
				{
					final char c = text.charAt(i);
					if (Character.isLetterOrDigit(c) && !m_mnemonics.contains(c))
					{
						mnemonic = c;
						break;
					}
				}
			}
			// Nothing found
			if (mnemonic == 0)
			{
				log.trace("None for: {}", oText);
				return 0;	// if first char would be returned, the first occurrence is invalid.
			}
		}
		m_mnemonics.add(mnemonic);
		m_fields.add(source);

		if (log.isTraceEnabled())
		{
			log.trace(mnemonic + " - " + source.getName());
		}

		return mnemonic;
	}	// getMnemonic

	/**
	 * Set Window level Mnemonics
	 *
	 * @param set true if set otherwise unregister
	 */
	public void setMnemonics(final boolean set)
	{
		final int size = m_fields.size();
		for (int i = 0; i < size; i++)
		{
			final Component c = m_fields.get(i);
			if (c instanceof CLabel)
			{
				final CLabel l = (CLabel)c;
				if (set)
				{
					l.setDisplayedMnemonic(l.getSavedMnemonic());
				}
				else
				{
					l.setDisplayedMnemonic(0);
				}
			}
			else if (c instanceof VCheckBox)
			{
				final VCheckBox cb = (VCheckBox)c;
				if (set)
				{
					cb.setMnemonic(cb.getSavedMnemonic());
				}
				else
				{
					cb.setMnemonic(0);
				}
			}
			else if (c instanceof VButton)
			{
				final VButton b = (VButton)c;
				if (set)
				{
					b.setMnemonic(b.getSavedMnemonic());
				}
				else
				{
					b.setMnemonic(0);
				}
			}
		}
	}	// setMnemonics
}
