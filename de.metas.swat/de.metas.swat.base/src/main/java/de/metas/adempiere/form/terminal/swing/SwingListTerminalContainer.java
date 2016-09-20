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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import javax.swing.JList;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import net.miginfocom.swing.MigLayout;

/**
 * @author cg
 *
 */
/* package */class SwingListTerminalContainer implements IContainer
{

	private ITerminalContext tc;
	private JList panel;

	private boolean disposed = false;

	protected SwingListTerminalContainer(ITerminalContext tc)
	{
		this(tc, "fill, ins 0 0");
	}

	protected SwingListTerminalContainer(ITerminalContext tc, String constraints)
	{
		this(tc, new JList());
		this.panel.setLayout(new MigLayout(constraints));
	}

	public SwingListTerminalContainer(ITerminalContext tc, JList panel)
	{
		this.tc = tc;
		this.panel = panel;
		tc.addToDisposableComponents(this);
	}

	@Override
	public void add(IComponent component, Object constraints)
	{
		SwingTerminalFactory.addChild(this, component, constraints);
	}

	@Override
	public void addAfter(final IComponent component, final IComponent componentBefore, final Object constraints)
	{
		SwingTerminalFactory.addChildAfter(this, component, componentBefore, constraints);
	}

	@Override
	public JList getComponent()
	{
		return panel;
	}

	@Override
	public void remove(IComponent component)
	{
		SwingTerminalFactory.removeChild(this, component);
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return tc;
	}

	@Override
	public void removeAll()
	{
		if (panel != null)
		{
			panel.removeAll();
		}
	}

	@Override
	public void dispose()
	{
		removeAll();
		panel = null;
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

}
