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


import org.adempiere.ui.AbstractContextMenuAction;
import org.compiere.apps.search.Find;
import org.compiere.grid.GridController;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTabMaxRows;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;

/**
 * Filter grid records and keep only those who have same value as it has current field.
 * 
 * This feature is active only in grid mode
 * 
 * @author tsa
 * 
 */
public class FilterContextEditorAction extends AbstractContextMenuAction
{
	public FilterContextEditorAction()
	{
		super();
	}

	@Override
	public String getName()
	{
		return "Filter";
	}

	@Override
	public String getIcon()
	{
		return null; // no icon
	}

	@Override
	public boolean isAvailable()
	{
		return isGridMode();
	}

	@Override
	public boolean isRunnable()
	{
		return isAvailable();
	}

	@Override
	public void run()
	{
		final GridTab gridTab = getGridTab();
		if (gridTab == null)
		{
			return;
		}

		final GridController gc = getGridController();
		if (gc == null)
		{
			return;
		}

		final GridField gridField = getGridField();
		if (gridField == null)
		{
			return;
		}
		
		final String m_columnName = getColumnName();
		final Object m_value = getFieldValue();
		final VEditor editor = getEditor();
		final String columnDisplayName = gridField.getHeader();
		final Object valueDisplay = editor == null ? m_value : editor.getDisplay();

		final MQuery queryInitial = new MQuery(gridTab.getTableName());
		queryInitial.addRestriction(m_columnName, Operator.EQUAL, m_value);
		
		Find find = Find.builder()
				.setParentFrame(null)
				.setGridTab(gridTab)
				.setTitle("" + columnDisplayName + "=" + valueDisplay)
				.setWhereExtended("")
				.setQuery(queryInitial)
				.setMinRecords(1)
				.buildFindDialog();
		//
		final MQuery query = find.getQuery();
		find.dispose();
		find = null;

		// Confirmed query
		if (query != null)
		{
			// m_onlyCurrentRows = false; // search history too
			gridTab.setQuery(query);
			gc.query(false, 0, GridTabMaxRows.NO_RESTRICTION);   // autoSize
		}
	}
}
