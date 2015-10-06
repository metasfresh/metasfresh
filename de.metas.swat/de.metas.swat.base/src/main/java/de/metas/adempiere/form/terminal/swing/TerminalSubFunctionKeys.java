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


import net.miginfocom.swing.MigLayout;
import de.metas.adempiere.form.terminal.ITerminalBasePanel;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyListener;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.POSKeyLayout;

/**
 * Function Key Sub Panel
 * 
 * @author Teo Sarca
 * @author Comunidad de Desarrollo OpenXpertya *Basado en Codigo Original Modificado, Revisado y Optimizado de:
 *         *Copyright (c) Jorg Janke
 */
public abstract class TerminalSubFunctionKeys
		extends TerminalSubPanel
		implements ITerminalKeyListener
{
	public TerminalSubFunctionKeys(ITerminalBasePanel basePanel)
	{
		super(basePanel);
	}

	@Override
	public void init()
	{
		int C_POSKeyLayout_ID = getC_POSKeyLayout_ID();
		if (C_POSKeyLayout_ID <= 0)
			return;

		final ITerminalFactory factory = getTerminalFactory();

		POSKeyLayout keyLayout = new POSKeyLayout(getTerminalContext(), C_POSKeyLayout_ID);

		ITerminalKeyPanel panel = factory.createTerminalKeyPanel(keyLayout, this);
		getUI().setLayout(new MigLayout("fill, ins 0"));
		add(panel, "growx, growy");

	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void keyReturned(ITerminalKey key)
	{
		getTerminalBasePanel().keyPressed(key);
	}

	public abstract int getC_POSKeyLayout_ID();

	public void setCurrentLine(ITerminalKey key)
	{
		getTerminalBasePanel().updateInfo();
	}
}
