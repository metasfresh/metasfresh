/**
 * 
 */
package org.adempiere.webui.panel;

/*
 * #%L
 * ADempiere ERP - ZkWebUI Lib
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


import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.editor.WStringEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.editor.WYesNoEditor;
import org.compiere.apps.search.AbstractInfoQueryCriteriaGeneral;
import org.compiere.model.Lookup;
import org.compiere.swing.CEditor;
import org.compiere.util.DisplayType;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;

/**
 * @author tsa
 * 
 */
public class InfoQueryCriteriaGeneral extends AbstractInfoQueryCriteriaGeneral
{
	private final EventListener fieldChangedListener = new EventListener()
	{
		@Override
		public void onEvent(Event event) throws Exception
		{
			onFieldChanged();
		}
	};

	@Override
	protected CEditor createCheckboxEditor(final String columnName, final String label)
	{
		WYesNoEditor cb = new WYesNoEditor(columnName, label, null, false, false, true);
		cb.setValue(true);
		cb.getComponent().addActionListener(fieldChangedListener);
		return cb;
	}

	@Override
	protected CEditor createLookupEditor(String columnName, Lookup lookup, Object defaultValue)
	{
		final WEditor vl;
		if (DisplayType.Search == lookup.getDisplayType())
		{
			vl = new WSearchEditor(columnName, false, false, true, lookup);
		}
		else
		{
			vl = new WTableDirEditor(columnName, false, false, true, lookup);
		}

		// vl.setName(columnName);
		if (defaultValue != null)
		{
			vl.setValue(defaultValue);
		}
		vl.getComponent().addEventListener(Events.ON_SELECT, fieldChangedListener);

		return vl;
	}

	@Override
	protected CEditor createStringEditor(String columnName, String defaultValue)
	{
		final WStringEditor field = new WStringEditor();
		if (defaultValue != null)
		{
			field.setValue(defaultValue);
		}

		field.getComponent().addEventListener(Events.ON_OK, fieldChangedListener);

		return field;
	}

	@Override
	protected CEditor createNumberEditor(String columnName, String title, int displayType)
	{
		final WNumberEditor vn = new WNumberEditor(columnName, false, false, true, displayType, title);
		// vn.setName(columnName);
		vn.getComponent().addEventListener(Events.ON_SELECT, fieldChangedListener);
		return vn;
	}

	@Override
	protected CEditor createDateEditor(String columnName, String title, int displayType)
	{
		final WDateEditor vd = new WDateEditor(columnName, false, false, true, title);
		// vd.setName(columnName);
		vd.getComponent().addEventListener(Events.ON_SELECT, fieldChangedListener);
		return vd;
	}
}
