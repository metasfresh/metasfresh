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

import de.metas.adempiere.form.terminal.AbstractTerminalDateField;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class SwingTerminalDateField
		extends AbstractTerminalDateField
		implements IComponentSwing
{
	public SwingTerminalDateField(final ITerminalContext terminalContext, final String name)
	{
		super(terminalContext, name);
	}

	@Override
	public Component getComponent()
	{
		final IContainer component = super.getPanelComponent();
		return SwingTerminalFactory.getUI(component);
	}

	@Override
	protected void initUI()
	{
		// nothing atm
	}
}
