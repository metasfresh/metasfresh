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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;

import javax.swing.JSplitPane;

import de.metas.adempiere.form.terminal.ITerminalSplitPane;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * @author cg
 * 
 */
public class TerminalSplitPane implements ITerminalSplitPane
{
	private final JSplitPane splitPane;
	private final ITerminalContext tc;
	static final public int HORIZONTAL_SPLIT = JSplitPane.HORIZONTAL_SPLIT;
	static final public int VERTICAL_SPLIT = JSplitPane.VERTICAL_SPLIT;

	protected TerminalSplitPane(ITerminalContext tc, int orientation, Component left, Component right)
	{
		this.tc = tc;
		this.splitPane = new JSplitPane(orientation, left, right);
	}

	public void setResizeWeight(double value)
	{
		splitPane.setResizeWeight(value);
	}

	public void setDividerLocation(Double loc)
	{
		splitPane.setDividerLocation(loc);
	}

	public void setDividerLocation(int loc)
	{
		splitPane.setDividerLocation(loc);
	}

	public void setMinimumSize(Dimension dim)
	{
		splitPane.getLeftComponent().setMinimumSize(dim);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		splitPane.addPropertyChangeListener(listener);
	}

	public void setDividerSize(int size)
	{
		splitPane.setDividerSize(size);
	}

	public void setContinuousLayout(boolean cont)
	{
		splitPane.setContinuousLayout(cont);
	}

	public void setEnabled(boolean en)
	{
		splitPane.setEnabled(en);
	}

	public void setOneTouchExpandable(boolean exp)
	{
		splitPane.setOneTouchExpandable(exp);
	}

	@Override
	public Object getComponent()
	{
		return splitPane;
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return tc;
	}

	@Override
	public void dispose()
	{
		splitPane.removeAll();
	}

}
