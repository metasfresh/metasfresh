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


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import org.adempiere.util.Services;
import org.compiere.util.Util;

import de.metas.adempiere.form.terminal.AbstractTerminalCheckboxField;
import de.metas.adempiere.form.terminal.ITerminalField;
import de.metas.adempiere.form.terminal.WrongValueException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.i18n.IMsgBL;

/**
 * Swing terminal checkbox
 *
 * @author al
 */
public class SwingTerminalCheckboxField extends AbstractTerminalCheckboxField
{
	//
	// Services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private JCheckBox checkboxSwing;
	private ActionListener checkboxSwingListener;

	public SwingTerminalCheckboxField(final ITerminalContext terminalContext, final String name)
	{
		super(terminalContext, name);

		checkboxSwing = new JCheckBox();
		checkboxSwing.setSelected(false);
		checkboxSwing.setFocusPainted(false); // no border when focusing

		//
		// Terminal checkboxes shall be large
		checkboxSwing.putClientProperty("JComponent.sizeVariant", "large");

		final float fontSize = terminalContext.getDefaultFontSize();
		setFontSize(fontSize);

		checkboxSwingListener = new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				firePropertyChange(ITerminalField.ACTION_ValueChanged, null, getValue());
			}
		};
		checkboxSwing.addActionListener(checkboxSwingListener);
	}

	@Override
	public void setText(final String text)
	{
		checkboxSwing.setText(text);
	}

	@Override
	public void setTextAndTranslate(final String text)
	{
		String textTrl;
		if (text == null)
		{
			textTrl = null;
		}
		else
		{
			if (text.indexOf("@") >= 0)
			{
				textTrl = msgBL.parseTranslation(getCtx(), text);
			}
			else
			{
				textTrl = msgBL.translate(getCtx(), text);
			}
		}
		
		// remove the "&" if any because looks ugly
		textTrl = Util.cleanAmp(textTrl);

		setText(textTrl);
	}

	@Override
	public void setFontSize(final float fontSize)
	{
		final Font fontOld = checkboxSwing.getFont();
		final Font fontNew = fontOld.deriveFont(fontSize); // NOTE: if it's not float then the wrong "deriveFont" method will be invoked
		checkboxSwing.setFont(fontNew);
	}

	@Override
	public void setVisible(final boolean visible)
	{
		checkboxSwing.setVisible(visible);
	}

	@Override
	public boolean isVisible()
	{
		return checkboxSwing.isVisible();
	}

	@Override
	public void requestFocus()
	{
		checkboxSwing.requestFocus();
	}

	@Override
	public boolean isEditable()
	{
		return checkboxSwing.isEnabled();
	}

	@Override
	public void setEditable(final boolean editable)
	{
		checkboxSwing.setEnabled(editable);
	}

	@Override
	public Object getComponent()
	{
		return checkboxSwing;
	}

	@Override
	protected void setFieldValue(final Boolean value, final boolean fireEvent)
	{
		checkboxSwing.setSelected(value);
	}

	@Override
	protected Boolean getFieldValue() throws WrongValueException
	{
		return checkboxSwing.isSelected();
	}

	@Override
	public void dispose()
	{
		super.dispose();

		if (checkboxSwing != null)
		{
			checkboxSwing.removeActionListener(checkboxSwingListener);
			checkboxSwing = null;
		}

		checkboxSwingListener = null;
	}
}
