/**
 * 
 */
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


import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.adempiere.util.Services;

import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.i18n.IMsgBL;

/**
 * @author tsa
 * 
 */
/* package */class SwingTerminalNumericField
		extends de.metas.adempiere.form.terminal.AbstractTerminalNumericField
		implements IComponentSwing
{
	protected SwingTerminalNumericField(final ITerminalContext tc, final String name, final int displayType, final boolean withButtons, final boolean withLabel)
	{
		this(tc, name, displayType, withButtons, withLabel, null); // no constraints
	}

	protected SwingTerminalNumericField(final ITerminalContext tc, final String name, final int displayType, final boolean withButtons, final boolean withLabel, final String constr)
	{
		super(tc, name, displayType, withButtons, withLabel, constr);
	}

	/**
	 * 
	 * @param tc
	 * @param name
	 * @param displayType
	 * @param fontSize
	 * @param withButtons
	 * @param withLabel
	 * @param constr plus/minus buttons constraints
	 */
	protected SwingTerminalNumericField(final ITerminalContext tc, final String name, final int displayType, final float fontSize, final boolean withButtons, final boolean withLabel,
			final String constr)
	{
		super(tc, name, displayType, fontSize, withButtons, withLabel, constr);
	}

	@Override
	protected void initUI()
	{
		final ITerminalFactory factory = getTerminalContext().getTerminalFactory();
		if (isWithButtons())
		{
			final String btnConstraints = constraints
					 // make sure all components will have the same height. As an effect, components will also be vertically centered
					+", pushy" 
					;
			panel.add(bMinus, btnConstraints);
		}
		if (isWithLabel())
		{
			panel.add(factory.createLabel(Services.get(IMsgBL.class).translate(getCtx(), getName())), "split 2, flowy, h 15");
		}
		panel.add(fNumber, constraints);
		if (isWithButtons())
		{
			panel.add(bPlus, constraints);
		}
		if (!isWithButtons() && !isWithLabel() && getComponent() instanceof JTextField)
		{
			((JTextField)getComponent()).setHorizontalAlignment(SwingConstants.LEFT);
		}
	}

	@Override
	public Component getComponent()
	{
		if (!isWithButtons() && !isWithLabel())
		{
			return (Component)fNumber.getComponent();
		}
		return SwingTerminalFactory.getUI(panel);
	}

	@Override
	public Color getBackgroundColor()
	{
		return getComponent().getBackground();
	}

	@Override
	public void setBackgroundColor(final Color color)
	{
		getComponent().setBackground(color);
	}
}
