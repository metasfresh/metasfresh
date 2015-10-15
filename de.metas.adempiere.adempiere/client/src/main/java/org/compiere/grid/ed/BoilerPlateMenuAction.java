package org.compiere.grid.ed;

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


import java.awt.Container;
import java.util.List;

import javax.swing.JMenuItem;

import org.adempiere.ui.AbstractContextMenuAction;
import org.compiere.grid.ed.menu.IEditorContextPopupMenuComposer;
import org.compiere.model.GridField;
import org.compiere.util.DisplayType;

public class BoilerPlateMenuAction extends AbstractContextMenuAction
		implements IEditorContextPopupMenuComposer
{
	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public String getIcon()
	{
		return null;
	}

	@Override
	public boolean isAvailable()
	{
		final VEditor editor = getEditor();
		final GridField gridField = editor.getField();
		if (gridField == null)
		{
			return false;
		}

		final int displayType = gridField.getDisplayType();
		if (displayType == DisplayType.String || displayType == DisplayType.Text || displayType == DisplayType.TextLong)
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean isRunnable()
	{
		return true;
	}

	@Override
	public void run()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean createUI(java.awt.Container parent)
	{
		final VEditor editor = getEditor();
		final GridField gridField = editor.getField();

		final Container textComponent = (Container)editor;

		final List<JMenuItem> items = BoilerPlateMenu.createMenuElements(textComponent, gridField);
		if (items == null || items.isEmpty())
		{
			return false;
		}
		
		for (JMenuItem item : items)
		{
			parent.add(item);
		}
		
		return true;
	}
}
