package de.metas.adempiere.form.terminal.context;

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


import org.compiere.util.Env;

import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;

/**
 * Helper used to create {@link ITerminalContext} instances for testing.
 * 
 * The reason why we have this is because {@link TerminalContext} class cannot be instantiated from outside of the package.
 * 
 * @author tsa
 *
 */
public class TerminalContextTestFactory
{
	public static final TerminalContext newTeminalContext()
	{
		final TerminalContext terminalContext = new TerminalContext();
		terminalContext.setCtx(Env.getCtx());
		
		//
		// Setup components factory
		// FIXME: get rid of this, so far it's needed indirectly by some tests for using methods like getDefaultFont() ...
		final ITerminalFactory factory = new SwingTerminalFactory(terminalContext);
		terminalContext.setTerminalFactory(factory);

		
		return terminalContext;
	}
}
