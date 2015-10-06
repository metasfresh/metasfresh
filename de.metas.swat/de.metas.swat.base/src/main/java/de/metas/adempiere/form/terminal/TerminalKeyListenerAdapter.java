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


/**
 * Abstract adapter for {@link ITerminalKeyListener}.
 * 
 * Developer will extend this class and it will implement only the methods that are needed for his/her case.
 * 
 * @author tsa
 *
 */
public abstract class TerminalKeyListenerAdapter implements ITerminalKeyListener
{
	@Override
	public void keyReturning(ITerminalKey key)
	{
		// nothing
	}

	@Override
	public void keyReturned(ITerminalKey key)
	{
		// nothing
	}

}
