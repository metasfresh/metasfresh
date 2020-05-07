/**
 *
 */
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


import java.beans.PropertyChangeListener;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.Check;
import org.adempiere.util.beans.WeakPropertyChangeSupport;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * @author tsa
 *
 */
public abstract class ConfirmPanel implements IConfirmPanel
{
	private final ITerminalContext terminalContext;
	private final WeakPropertyChangeSupport listeners;

	private boolean disposed= false;

	protected ConfirmPanel(final ITerminalContext terminalContext)
	{
		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalContext = terminalContext;
		this.listeners = terminalContext.createPropertyChangeSupport(this);

		terminalContext.addToDisposableComponents(this);
	}

	@Override
	public final ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	protected final ITerminalFactory getTerminalFactory()
	{
		return getTerminalContext().getTerminalFactory();
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	@Override
	public final void addListener(final PropertyChangeListener listener)
	{
		listeners.addPropertyChangeListener(listener);
	}

	protected final void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		listeners.firePropertyChange(propertyName, oldValue, newValue);
	}

}
