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


public class TerminalDialogListenerAdapter implements ITerminalDialogListener
{
	@Override
	public void onDialogOk(final ITerminalDialog dialog)
	{
		// nothing
	}

	@Override
	public boolean onDialogCanceling(final ITerminalDialog dialog)
	{
		// nothing
		return true; // allow cancelling
	}

	@Override
	public void onDialogActivated(final ITerminalDialog dialog)
	{
		// nothing
	}

	@Override
	public void onDialogDeactivated(final ITerminalDialog dialog)
	{
		// nothing
	}

	@Override
	public void onDialogOpened(final ITerminalDialog dialog)
	{
		// nothing
	}

	@Override
	public void onDialogClosing(final ITerminalDialog dialog)
	{
		// nothing
	}

	@Override
	public void onDialogClosed(final ITerminalDialog dialog)
	{
		// nothing
	}
}
