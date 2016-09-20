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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import de.metas.adempiere.form.terminal.ITerminalTree;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * @author cg
 *
 */
public class TerminalTree implements ITerminalTree, IComponentSwing
{
	private final ITerminalContext tc;
	private final JTree tree;

	private boolean disposed = false;

	public TerminalTree(ITerminalContext tc)
	{
		this.tc = tc;
		this.tree = new JTree();
		tc.addToDisposableComponents(this);
	}

	public Dimension getSize()
	{
		return tree.getSize();
	}

	public void setModel(TreeModel newModel)
	{
		tree.setModel(newModel);
	}

	@Override
	public Component getComponent()
	{
		return tree;
	}

	@Override
	public void setRootVisible(boolean visible)
	{
		tree.setRootVisible(visible);
	}

	public void setCellRenderer(TreeCellRenderer x)
	{
		tree.setCellRenderer(x);
	}

	@Override
	public int getRowCount()
	{
		return tree.getRowCount();
	}

	@Override
	public void expandRow(int row)
	{
		tree.expandRow(row);
	}

	public void setSelectionRow(int row)
	{
		tree.setSelectionRow(row);
	}

	@Override
	public void collapseRow(int row)
	{
		tree.collapseRow(row);
	}

	@Override
	public final void expandTree(final boolean expand)
	{
		int row = 0;
		while (row < tree.getRowCount())
		{
			if (expand)
			{
				tree.expandRow(row);
			}
			else
			{
				tree.collapseRow(row);
			}
			row++;
		}
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return tc;
	}

	/**
	 * Does nothing, only sets our internal disposed flag.
	 */
	@Override
	public void dispose()
	{
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}
}
