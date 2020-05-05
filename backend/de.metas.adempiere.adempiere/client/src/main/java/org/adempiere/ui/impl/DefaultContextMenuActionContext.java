package org.adempiere.ui.impl;

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


import java.util.Properties;

import org.adempiere.ui.IContextMenuActionContext;
import org.compiere.grid.VTable;
import org.compiere.grid.ed.VEditor;
import org.compiere.util.Env;

import de.metas.util.Check;

/* package */final class DefaultContextMenuActionContext implements IContextMenuActionContext
{
	private final VEditor editor;
	private final VTable vtable;
	private final int viewRow;
	private final int viewColumn;

	public DefaultContextMenuActionContext(final VEditor editor, final VTable vtable, final int viewRow, final int viewColumn)
	{
		super();
		
		Check.assumeNotNull(editor, "editor not null");
		this.editor = editor;
		
		this.vtable = vtable;
		this.viewRow = viewRow;
		this.viewColumn = viewColumn;
	}
	
	@Override
	public Properties getCtx()
	{
		return Env.getCtx();
	}

	@Override
	public VEditor getEditor()
	{
		return editor;
	}

	@Override
	public VTable getVTable()
	{
		return vtable;
	}

	@Override
	public int getViewRow()
	{
		return viewRow;
	}

	@Override
	public int getViewColumn()
	{
		return viewColumn;
	}
}
