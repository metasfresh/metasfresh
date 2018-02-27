/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.impexp;

import java.util.HashMap;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTabLayoutMode;
import org.compiere.model.Lookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.adempiere.service.IColumnBL;

/**
 * Excel Exporter Adapter for GridTab
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 1943731 ] Window data export functionality
 */
public class GridTabExcelExporter extends AbstractExcelExporter
{
	private GridTab m_tab = null;
	
	public GridTabExcelExporter(final Properties ctx, final GridTab tab)
	{
		m_tab = tab;
		setFreezePane(0, 1);
	}
	
	@Override
	public int getColumnCount()
	{
		return m_tab.getFieldCount();
	}

	@Override
	public int getDisplayType(final int row, final int col)
	{
		return m_tab.getField(col).getDisplayType();
	}

	@Override
	public String getHeaderName(final int col)
	{
		return m_tab.getField(col).getHeader();
	}

	@Override
	public int getRowCount()
	{
		return m_tab.getRowCount();
	}

	@Override
	public CellValue getValueAt(final int row, final int col)
	{
		final GridField f = m_tab.getField(col);
		final Object key = m_tab.getValue(row, f.getColumnName());
		Object value = key;
		Lookup lookup = f.getLookup();
		if (lookup != null)
		{
			;
		}
		else if (f.getDisplayType() == DisplayType.Button)
		{
			lookup = getButtonLookup(f);
		}
		//
		if (lookup != null)
		{
			value = lookup.getDisplay(key);
		}
		
		final int displayType = f.getDisplayType();
		return CellValues.toCellValue(value, displayType);
	}

	@Override
	public boolean isColumnPrinted(final int col)
	{
		GridField f = m_tab.getField(col);
		// Hide not displayed fields
		if (!f.isDisplayed(GridTabLayoutMode.Grid))
			return false;
		// Hide encrypted fields
		if (f.isEncrypted())
			return false;
		// Hide simple button fields without a value
		if (f.getDisplayType() == DisplayType.Button && f.getAD_Reference_Value_ID() == 0)
			return false;
		// Hide included tab fields (those are only placeholders for included tabs, always empty)
		if (f.getIncluded_Tab_ID() > 0)
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean isFunctionRow(final int row)
	{
		return false;
	}

	@Override
	public boolean isPageBreak(final int row, final int col)
	{
		return false;
	}
	
	private HashMap<String, MLookup> m_buttonLookups = new HashMap<>();
	
	private MLookup getButtonLookup(final GridField mField)
	{		
		MLookup lookup = m_buttonLookups.get(mField.getColumnName());
		if (lookup != null)
		{
			return lookup;
		}

		// TODO: refactor with org.compiere.grid.ed.VButton.setField(GridField)
		if (mField.getColumnName().endsWith("_ID") && ! Services.get(IColumnBL.class).isRecordIdColumnName(mField.getColumnName()))
		{
			lookup = MLookupFactory.get(Env.getCtx(), mField.getWindowNo(), 0,
					mField.getAD_Column_ID(), DisplayType.Search);
		}
		else if (mField.getAD_Reference_Value_ID() != 0)
		{
			// Assuming List
			lookup = MLookupFactory.get(Env.getCtx(), mField.getWindowNo(), 0,
					mField.getAD_Column_ID(), DisplayType.List);
		}
		//
		m_buttonLookups.put(mField.getColumnName(), lookup);
		return lookup;
	}
}
