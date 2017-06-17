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


import java.awt.Container;
import java.awt.Frame;
import java.util.Properties;

import org.adempiere.ui.AbstractContextMenuAction;
import org.adempiere.util.Services;
import org.compiere.apps.ScriptEditor;
import org.compiere.grid.ed.Editor;
import org.compiere.grid.ed.HTMLEditor;
import org.compiere.grid.ed.VEditor;
import org.compiere.model.GridField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;

public class TextEditorContextMenuAction extends AbstractContextMenuAction
{
	private Boolean scriptEditor = null;
	public boolean isScriptEditor()
	{
		if (scriptEditor != null)
		{
			return scriptEditor;
		}
		
		final VEditor editor = getEditor();
		if (editor == null)
		{
			return false;
		}

		final GridField gridField = editor.getField();
		final String columnName = gridField.getColumnName();
		if (columnName == null)
		{
			return false;
		}

		if (columnName.equals("Script") || columnName.endsWith("_Script"))
		{
			scriptEditor = true;
		}
		else
		{
			scriptEditor = false;
		}

		return scriptEditor;
	}

	@Override
	public String getName()
	{
		return isScriptEditor() ? "Script" : "Editor";
	}

	@Override
	public String getIcon()
	{
		return isScriptEditor() ? "Script16" : "Editor16";
	}

	@Override
	public boolean isAvailable()
	{
		final GridField gridField = getEditor().getField();
		if (gridField == null)
		{
			return false;
		}

		final int displayType = gridField.getDisplayType();

		if (displayType == DisplayType.Text || displayType == DisplayType.URL)
		{
			if (gridField.getFieldLength() > gridField.getDisplayLength())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (displayType == DisplayType.TextLong
				|| displayType == DisplayType.Memo)
		{
			return true;
		}
		else
		{
			return false;
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
		final VEditor editor = getEditor();
		final GridField gridField = editor.getField();

		final Object value = editor.getValue();
		final String text = value == null ? null : value.toString();
		final boolean editable = editor.isReadWrite();


		final String textNew = startEditor(gridField, text, editable);
		if (editable)
		{
			gridField.getGridTab().setValue(gridField, textNew);
		}
		// TODO: i think is handled above
		// Data Binding
		// try
		// {
		// fireVetoableChange(m_columnName, m_oldText, getText());
		// }
		// catch (PropertyVetoException pve)
		// {
		// }
	}
	
	protected String startEditor(final GridField gridField, final String text, final boolean editable)
	{
		final Properties ctx = Env.getCtx();

		final String columnName = gridField.getColumnName();
		final String title = Services.get(IMsgBL.class).translate(ctx, columnName);

		final int windowNo = gridField.getWindowNo();
		final Frame frame = Env.getWindow(windowNo);
		
		final String textNew;
		if (gridField.getDisplayType() == DisplayType.TextLong)
		{
			//	Start it
			HTMLEditor ed = new HTMLEditor (frame, title, text, editable);
			textNew = ed.getHtmlText();
			ed = null;
		}
		else if (isScriptEditor())
		{
			textNew = ScriptEditor.start(frame, title, text, editable, windowNo);
		}
		else
		{
			final Container comp = (Container)getEditor();
			final int fieldLength = gridField.getFieldLength();
			textNew = Editor.startEditor(comp, title, text, editable, fieldLength);
		}
		
		return textNew;
	}
}
