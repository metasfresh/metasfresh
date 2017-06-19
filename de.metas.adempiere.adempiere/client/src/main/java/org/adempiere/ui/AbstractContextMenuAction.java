package org.adempiere.ui;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
import java.util.Collections;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.swing.KeyStroke;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.grid.GridController;
import org.compiere.grid.VTable;
import org.compiere.grid.ed.VEditor;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.slf4j.Logger;

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

public abstract class AbstractContextMenuAction implements IContextMenuAction
{
	protected final Logger log = LogManager.getLogger(getClass());

	private IContextMenuActionContext context;

	@Override
	@OverridingMethodsMustInvokeSuper
	public void setContext(IContextMenuActionContext menuCtx)
	{
		this.context = menuCtx;
		final List<IContextMenuAction> children = getChildren();
		if (children != null && !children.isEmpty())
		{
			for (IContextMenuAction child : children)
			{
				child.setContext(menuCtx);
			}
		}
	}

	public final IContextMenuActionContext getContext()
	{
		return context;
	}
	
	@Override
	public String getTitle()
	{
		final String name = getName();
		if (Check.isEmpty(name, true))
		{
			return null;
		}
		return Services.get(IMsgBL.class).translate(Env.getCtx(), name);
	}

	/** @return action's name (not translated) */
	protected abstract String getName();
	
	public final VEditor getEditor()
	{
		if (context == null)
		{
			return null;
		}
		return context.getEditor();
	}

	public final GridField getGridField()
	{
		final VEditor editor = getEditor();
		if (editor == null)
		{
			return null;
		}
		final GridField gridField = editor.getField();
		return gridField;
	}

	public final GridTab getGridTab()
	{
		final GridField gridField = getGridField();
		if (gridField == null)
		{
			return null;
		}
		return gridField.getGridTab();
	}

	@Override
	public List<IContextMenuAction> getChildren()
	{
		return Collections.emptyList();
	}

	@Override
	public boolean isLongOperation()
	{
		return false;
	}
	
	@Override
	public boolean isHideWhenNotRunnable()
	{
		return true; // hide when not runnable - backward compatibility
	}

	protected final String getColumnName()
	{
		final GridField gridField = getGridField();
		if (gridField == null)
		{
			return null;
		}

		final String columnName = gridField.getColumnName();
		return columnName;
	}

	protected final Object getFieldValue()
	{
		final IContextMenuActionContext context = getContext();
		if (context == null)
		{
			return null;
		}

		//
		// Get value when in Grid Mode
		final VTable vtable = context.getVTable();
		final int row = context.getViewRow();
		final int column = context.getViewColumn();
		if (vtable != null && row >= 0 && column >= 0)
		{
			final Object value = vtable.getValueAt(row, column);
			return value;
		}
		// Get value when in single mode
		else
		{
			final GridField gridField = getGridField();
			if (gridField == null)
			{
				return null;
			}

			final Object value = gridField.getValue();
			return value;
		}
	}

	protected final GridController getGridController()
	{
		final IContextMenuActionContext context = getContext();
		if (context == null)
		{
			return null;
		}

		final VTable vtable = context.getVTable();

		final Component comp;
		if (vtable != null)
		{
			comp = vtable;
		}
		else
		{
			final VEditor editor = getEditor();
			if (editor instanceof Component)
			{
				comp = (Component)editor;
			}
			else
			{
				comp = null;
			}
		}

		Component p = comp;
		while (p != null)
		{
			if (p instanceof GridController)
			{
				return (GridController)p;
			}
			p = p.getParent();
		}
		return null;
	}

	protected final boolean isGridMode()
	{
		final GridController gc = getGridController();
		if (gc == null)
		{
			return false;
		}

		final boolean gridMode = !gc.isSingleRow();
		return gridMode;
	}
	
	@Override
	public KeyStroke getKeyStroke()
	{
		return null;
	}

}
