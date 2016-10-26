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


import java.util.Properties;

import org.adempiere.ui.AbstractContextMenuAction;
import org.adempiere.ui.editor.IZoomableEditor;
import org.adempiere.util.Check;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.grid.ed.VEditor;
import org.compiere.model.GridField;
import org.compiere.model.Lookup;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.ValueNamePair;

import de.metas.logging.MetasfreshLastError;

public class ZoomContextEditorAction extends AbstractContextMenuAction
{

	@Override
	public String getName()
	{
		return "Zoom";
	}

	@Override
	public String getIcon()
	{
		return "Zoom16";
	}

	@Override
	public boolean isAvailable()
	{
		final Properties ctx = Env.getCtx();
		final VEditor editor = getEditor();
		final GridField gridField = editor.getField();

		// only system admins can change lists, so no need to zoom for others
		final int roleId = Env.getAD_Role_ID(ctx);
		if (gridField.getDisplayType() == DisplayType.List && roleId != 0)
		{
			return false;
		}

		if (editor instanceof IZoomableEditor)
		{
			return true;
		}

		if (!gridField.isLookup())
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isRunnable()
	{
		final VEditor editor = getEditor();
		if (editor instanceof IZoomableEditor)
		{
			return true;
		}

		final GridField gridField = editor.getField();
		final Lookup lookup = gridField.getLookup();
		if (lookup == null || lookup.getZoom() == 0)
		{
			return false;
		}
		return true;
	}

	@Override
	public void run()
	{
		final VEditor editor = getEditor();
		if (editor instanceof IZoomableEditor)
		{
			final IZoomableEditor zoomableEditor = (IZoomableEditor)editor;
			zoomableEditor.actionZoom();
		}
		else
		{
			zoomGeneric(editor);
		}
	}

	private void zoomGeneric(VEditor editor)
	{
		final GridField gridField = editor.getField();
		final Lookup lookup = gridField.getLookup();
		if (lookup == null)
		{
			log.info("No lookup found for " + gridField);
			return;
		}
		//
		MQuery zoomQuery = lookup.getZoomQuery();
		final Object value = editor.getValue();
		final String columnName = gridField.getColumnName();

		// If not already exist or exact value
		if (zoomQuery == null || value != null)
		{
			zoomQuery = new MQuery(); // ColumnName might be changed in MTab.validateQuery
			String keyTableName = null;
			String keyColumnName = null;

			// Check if it is a Table Lookup
			if (lookup != null)
			{
				keyColumnName = lookup.getColumnName();
				keyTableName = lookup.getTableName();
			}
			
			// If KeyColumnName is fully qualified, remove the tableName prefix
			if (!Check.isEmpty(keyColumnName, true))
			{
				final int idx = keyColumnName.lastIndexOf('.');
				if (idx > 0)
				{
					keyColumnName = keyColumnName.substring(idx + 1);
				}
			}

			if (!Check.isEmpty(keyColumnName, true))
			{
				zoomQuery.addRestriction(keyColumnName, Operator.EQUAL, value);
				zoomQuery.setZoomColumnName(keyColumnName);
				zoomQuery.setZoomTableName(keyTableName);
			}
			else
			{
				zoomQuery.addRestriction(columnName, Operator.EQUAL, value);
				if (columnName.indexOf(".") > 0)
				{
					zoomQuery.setZoomColumnName(columnName.substring(columnName.indexOf(".") + 1));
					zoomQuery.setZoomTableName(columnName.substring(0, columnName.indexOf(".")));
				}
				else
				{
					zoomQuery.setZoomColumnName(columnName);
					// remove _ID to get table name
					zoomQuery.setZoomTableName(columnName.substring(0, columnName.length() - 3));
				}
			}
			zoomQuery.setZoomValue(value);

			zoomQuery.setRecordCount(1); // guess
		}

		int AD_Window_ID = lookup.getZoom(zoomQuery);
		//
		log.info(columnName + " - AD_Window_ID=" + AD_Window_ID + " - Query=" + zoomQuery + " - Value=" + value);
		//
		zoom(lookup.getWindowNo(), AD_Window_ID, zoomQuery);
	}

	private void zoom(int parentWindowNo, int AD_Window_ID, MQuery zoomQuery)
	{
		// setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		//
		AWindow frame = new AWindow();
		if (!frame.initWindow(AD_Window_ID, zoomQuery))
		{
			// setCursor(Cursor.getDefaultCursor());
			ValueNamePair pp = MetasfreshLastError.retrieveError();
			String msg = pp == null ? "AccessTableNoView" : pp.getValue();

			// Services.get(IClientUI.class).error(parentWindowNo, this, msg, pp==null ? "" : pp.getName()); // TODO
			// Services.get(IClientUI.class).warn(parentWindowNo, msg, pp == null ? "" : pp.getName()); // TODO
			ADialog.error(parentWindowNo, null, msg, pp == null ? "" : pp.getName());
		}
		else
		{
			AEnv.addToWindowManager(frame);
			if (Ini.isPropertyBool(Ini.P_OPEN_WINDOW_MAXIMIZED))
			{
				AEnv.showMaximized(frame);
			}
			else
			{
				AEnv.showCenterScreen(frame);
			}
		}
		// async window - not able to get feedback
		frame = null;
		//
		// setCursor(Cursor.getDefaultCursor());
	}

}
