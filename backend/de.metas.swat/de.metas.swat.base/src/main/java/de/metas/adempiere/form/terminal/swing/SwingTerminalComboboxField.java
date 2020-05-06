package de.metas.adempiere.form.terminal.swing;

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


import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.compiere.util.NamePair;

import de.metas.adempiere.form.terminal.AbstractTerminalComboboxField;
import de.metas.adempiere.form.terminal.ITerminalField;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class SwingTerminalComboboxField extends AbstractTerminalComboboxField
		implements IComponentSwing
{
	private DefaultComboBoxModel<NamePair> comboboxModel;
	private JComboBox<NamePair> comboboxSwing;
	private ActionListener comboboxSwingListener;

	public SwingTerminalComboboxField(final ITerminalContext terminalContext, final String name)
	{
		super(terminalContext, name);

		comboboxModel = new DefaultComboBoxModel<>();

		comboboxSwing = new JComboBox<>(comboboxModel);
		comboboxSwing.setEditable(false);

		final float fontSize = terminalContext.getDefaultFontSize();
		setFontSize(fontSize);

		comboboxSwingListener = new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				firePropertyChange(ITerminalField.ACTION_ValueChanged, null, getValue());
			}
		};
		comboboxSwing.addActionListener(comboboxSwingListener);
	}

	@Override
	public Component getComponent()
	{
		return comboboxSwing;
	}

	@Override
	public void requestFocus()
	{
		comboboxSwing.requestFocusInWindow();
	}

	@Override
	public void setEditable(final boolean editable)
	{
		comboboxSwing.setEnabled(editable);
	}

	@Override
	public boolean isEditable()
	{
		return comboboxSwing.isEnabled();
	}

	@Override
	public void setVisible(final boolean visible)
	{
		comboboxSwing.setVisible(visible);
	}

	@Override
	public boolean isVisible()
	{
		return comboboxSwing.isVisible();
	}

	@Override
	protected NamePair getFieldValue()
	{
		return getSelectedValue();
	}

	@Override
	public NamePair getSelectedValue()
	{
		final NamePair selectedItem = (NamePair)comboboxModel.getSelectedItem();
		return selectedItem;
	}

	/**
	 * Sets current selected value.
	 *
	 * Not implemented with <code>fireEvent</code>
	 */
	@Override
	protected void setFieldValue(final NamePair value, final boolean fireEvent)
	{
		// FIXME: i think here we shall disable subsequent events firing if fireEvents=false
		// see comboboxSwing's action listener

		//
		// Make sure given value is in our combobox model.
		// If it's not, add it.
		if (value != null)
		{
			if (comboboxModel.getIndexOf(value) < 0)
			{
				comboboxModel.addElement(value);
			}
		}

		comboboxModel.setSelectedItem(value);
	}

	@Override
	public NamePair getValueByKeyOrNull(final String key)
	{
		final int size = comboboxModel.getSize();
		for (int i = 0; i < size; i++)
		{
			final NamePair item = comboboxModel.getElementAt(i);
			if (key.equals(item.getID()))
			{
				return item;
			}
		}

		return null;
	}

	@Override
	public void setValues(final Collection<? extends NamePair> values)
	{
		comboboxModel.removeAllElements();

		if (values != null && !values.isEmpty())
		{
			for (final NamePair value : values)
			{
				comboboxModel.addElement(value);
			}
		}
	}

	@Override
	public void setFontSize(final float fontSize)
	{
		final Font fontOld = comboboxSwing.getFont();
		final Font fontNew = fontOld.deriveFont(fontSize); // NOTE: if it's not float then the wrong "deriveFont" method will be invoked
		comboboxSwing.setFont(fontNew);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		if (comboboxSwing != null)
		{
			comboboxSwing.removeActionListener(comboboxSwingListener);
			// comboboxSwing.setModel(null); // will throw NPE
			comboboxSwing.removeAllItems();
			comboboxSwing = null;
		}

		comboboxSwingListener = null;
		comboboxModel = null;
	}
}
