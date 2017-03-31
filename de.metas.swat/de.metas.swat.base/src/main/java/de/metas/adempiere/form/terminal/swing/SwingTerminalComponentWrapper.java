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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Component;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.Check;

import de.metas.adempiere.form.terminal.DirectExecuteBeforePainingSupport;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IExecuteBeforePainingSupport;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class SwingTerminalComponentWrapper implements IComponent, IComponentSwing, IExecuteBeforePainingSupport
{
	private final Component _component;
	private final ITerminalContext _terminalContext;
	private IExecuteBeforePainingSupport _executeBeforePainingSupport;

	private boolean disposed = false;

	/**
	 *
	 * @param terminalContext
	 * @param component
	 */
	public SwingTerminalComponentWrapper(final ITerminalContext terminalContext, final Component component)
	{
		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this._terminalContext = terminalContext;

		Check.assumeNotNull(component, "component not null");
		this._component = component;

		terminalContext.addToDisposableComponents(this);
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public Component getComponent()
	{
		return _component;
	}

	@Override
	public final ITerminalContext getTerminalContext()
	{
		return _terminalContext;
	}

	public final ITerminalFactory getTerminalFactory()
	{
		return getTerminalContext().getTerminalFactory();
	}

	/**
	 * Does nothing besides setting the internal disposed flag.
	 */
	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		// nothing at this level
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	/**
	 * Gets {@link IExecuteBeforePainingSupport}.
	 *
	 * If underyling component does not have this support then {@link DirectExecuteBeforePainingSupport} will be returned.
	 *
	 * @return {@link IExecuteBeforePainingSupport}; never return <code>null</code>.
	 */
	private final IExecuteBeforePainingSupport getExecuteBeforePainingSupport()
	{
		if (_executeBeforePainingSupport == null)
		{
			final Component comp = getComponent();
			if (comp instanceof IExecuteBeforePainingSupport)
			{
				_executeBeforePainingSupport = (IExecuteBeforePainingSupport)comp;
			}
			else
			{
				_executeBeforePainingSupport = DirectExecuteBeforePainingSupport.instance;
			}
		}

		return _executeBeforePainingSupport;
	}

	@Override
	public void executeBeforePainting(final Runnable updater)
	{
		getExecuteBeforePainingSupport().executeBeforePainting(updater);
	}

	@Override
	public void clearExecuteBeforePaintingQueue()
	{
		getExecuteBeforePainingSupport().clearExecuteBeforePaintingQueue();
	}

	@Override
	public String toString()
	{
		return "SwingTerminalComponentWrapper [_component=" + _component
				+ ", disposed=" + disposed
				+ ", _terminalContext=" + _terminalContext
				+ ", _executeBeforePainingSupport=" + _executeBeforePainingSupport
				+ "]";
	}
}
