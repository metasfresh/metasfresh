/**
 * 
 */
package de.metas.adempiere.form.terminal.zk;

/*
 * #%L
 * de.metas.swat.zkwebui
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


import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;

/**
 * @author tsa
 * 
 */
public class ZKTerminalContainer implements IContainer
{
	private final ITerminalContext tc;
	private final Component panel;

	protected ZKTerminalContainer(final ITerminalContext tc)
	{
		this(tc, "fill, ins 0 0");
	}

	protected ZKTerminalContainer(final ITerminalContext tc, final Component container)
	{
		this.tc = tc;
		panel = container;
	}

	protected ZKTerminalContainer(final ITerminalContext tc, final String constraints)
	{
		this.tc = tc;
		panel = new Div();
		// this.panel.setLayout(new MigLayout(constraints));
	}

	@Override
	public void add(final IComponent component, final Object constraints)
	{
		ZKTerminalFactory.addChild(this, component, constraints);
	}

	@Override
	public void addAfter(final IComponent component, final IComponent componentBefore, final Object constraints)
	{
		ZKTerminalFactory.addChildAfter(this, component, componentBefore, constraints);
	}

	@Override
	public Component getComponent()
	{
		return panel;
	}

	@Override
	public void remove(final IComponent component)
	{
		SwingTerminalFactory.removeChild(this, component);
	}

	@Override
	public void removeAll()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return tc;
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

}
