package org.compiere.grid.ed.menu;

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


import org.adempiere.ui.AbstractContextMenuAction;
import org.adempiere.ui.editor.IRefreshableEditor;
import org.compiere.grid.ed.VEditor;

public class RefreshContextEditorAction extends AbstractContextMenuAction
{

	@Override
	public String getName()
	{
		return "Refresh";
	}

	@Override
	public String getIcon()
	{
		return "Refresh16";
	}

	@Override
	public boolean isAvailable()
	{
		final IRefreshableEditor editor = getRefreshableEditor();
		if (editor == null)
		{
			return false;
		}

		return true;
	}
	
	public IRefreshableEditor getRefreshableEditor()
	{
		final VEditor editor = getEditor();
		if (editor instanceof IRefreshableEditor)
		{
			return (IRefreshableEditor)editor;
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean isRunnable()
	{
		return true;
	}

	@Override
	public void run()
	{
		final IRefreshableEditor editor = (IRefreshableEditor)getEditor();
		editor.refreshValue();
	}

	@Override
	public boolean isLongOperation()
	{
		return true;
	}
}
