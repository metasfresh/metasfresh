package org.compiere.grid.ed;

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
import java.awt.Frame;

import org.adempiere.util.Services;
import org.compiere.apps.AEnv;
import org.compiere.grid.ed.menu.TextEditorContextMenuAction;
import org.compiere.model.GridField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;

public class MetasTextEditorContextMenuAction extends TextEditorContextMenuAction
{
	@Override
	protected String startEditor(final GridField gridField, final String text, final boolean editable)
	{
		final VEditor editor = getEditor();
		if ((gridField.getDisplayType() == DisplayType.TextLong) && (editor instanceof Component))
		{
			final Component editorComp = (Component)editor;
			final String columnName = gridField.getColumnName();
			final String title = Services.get(IMsgBL.class).translate(Env.getCtx(), columnName);
			
			final int windowNo = gridField.getWindowNo();
			final Frame frame = Env.getWindow(windowNo);
			
			final RichTextEditorDialog ed = new RichTextEditorDialog(editorComp, frame, title, text, editable);
			AEnv.showCenterWindow(frame, ed);
			final String textNew = ed.getHtmlText();
			return textNew;
		}
		else
		{
			return super.startEditor(gridField, text, editable);
		}

	}

}
