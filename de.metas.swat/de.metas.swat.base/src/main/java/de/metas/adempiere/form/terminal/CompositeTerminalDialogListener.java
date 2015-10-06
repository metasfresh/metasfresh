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


import java.util.ArrayList;
import java.util.List;

public class CompositeTerminalDialogListener implements ITerminalDialogListener, IDisposable
{
	private List<ITerminalDialogListener> listeners = null;

	public CompositeTerminalDialogListener()
	{
		super();

		listeners = new ArrayList<>();
	}

	public void addListener(final ITerminalDialogListener listener)
	{
		if (listeners == null)
		{
			return;
		}
		listeners.add(listener);
	}

	@Override
	public void dispose()
	{
		listeners = null; // clear listeners
	}

	@Override
	public void onDialogOk(final ITerminalDialog dialog)
	{
		if (listeners == null)
		{
			return;
		}
		for (final ITerminalDialogListener listener : listeners)
		{
			listener.onDialogOk(dialog);
		}
	}

	@Override
	public boolean onDialogCanceling(final ITerminalDialog dialog)
	{
		boolean cancelResult = true; // result will be considered true if all dialogs cancelled successfully
		if (listeners == null)
		{
			return cancelResult;
		}
		for (final ITerminalDialogListener listener : listeners)
		{
			cancelResult = cancelResult && listener.onDialogCanceling(dialog);
		}
		return cancelResult;
	}

	@Override
	public void onDialogOpened(final ITerminalDialog dialog)
	{
		if (listeners == null)
		{
			return;
		}
		for (final ITerminalDialogListener listener : listeners)
		{
			listener.onDialogOpened(dialog);
		}
	}

	@Override
	public void onDialogActivated(final ITerminalDialog dialog)
	{
		if (listeners == null)
		{
			return;
		}
		for (final ITerminalDialogListener listener : listeners)
		{
			listener.onDialogActivated(dialog);
		}
	}

	@Override
	public void onDialogDeactivated(final ITerminalDialog dialog)
	{
		if (listeners == null)
		{
			return;
		}
		for (final ITerminalDialogListener listener : listeners)
		{
			listener.onDialogDeactivated(dialog);
		}
	}

	@Override
	public void onDialogClosing(final ITerminalDialog dialog)
	{
		if (listeners == null)
		{
			return;
		}
		for (final ITerminalDialogListener listener : listeners)
		{
			listener.onDialogClosing(dialog);
		}
	}

	@Override
	public void onDialogClosed(final ITerminalDialog dialog)
	{
		if (listeners == null)
		{
			return;
		}
		for (final ITerminalDialogListener listener : listeners)
		{
			listener.onDialogClosed(dialog);
		}
	}
}
