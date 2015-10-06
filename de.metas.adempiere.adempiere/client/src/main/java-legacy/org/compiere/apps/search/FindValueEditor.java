/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.apps.search;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.adempiere.util.Services;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VNumber;
import org.compiere.grid.ed.VString;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.GridField;
import org.compiere.model.MQuery;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.ValueNamePair;

/**
 * Cell editor for Find Value/ValueTo fields.
 * Editor depends on Column setting.
 * Has to save entries how they are used in the query, i.e. '' for strings.
 *
 * @author Jorg Janke
 * @version $Id: FindValueEditor.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class FindValueEditor extends AbstractCellEditor implements TableCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4819832280924881253L;

	/**
	 * Constructor
	 * 
	 * @param find find
	 * @param valueTo true if it is the "to" value column
	 */
	public FindValueEditor(Find find, boolean valueTo)
	{
		super();
		m_find = find;
		m_valueToColumn = valueTo;
	}	// FindValueEditor

	/** Find Window */
	private Find m_find;
	/** Is ValueTo Column? */
	private boolean m_valueToColumn;
	/** Between selected */
	private boolean m_between = false;
	/** Editor */
	private VEditor m_editor = null;
	/** Logger */
	private static CLogger log = CLogger.getCLogger(FindValueEditor.class);

	/**
	 * Get Value
	 * Need to convert to String
	 * 
	 * @return current value
	 */
	@Override
	public Object getCellEditorValue()
	{
		if (m_editor == null)
			return null;
		Object obj = m_editor.getValue();		// returns Integer, BidDecimal, String
		log.config("Obj=" + obj);
		return obj;
		/**
		 * if (obj == null)
		 * return null;
		 * //
		 * String retValue = obj.toString();
		 * log.config( "FindValueEditor.getCellEditorValue");
		 * return retValue;
		 **/
	}	// getCellEditorValue

	/**
	 * Get Editor
	 *
	 * @param table Table
	 * @param value Value
	 * @param isSelected cell is selected
	 * @param row row
	 * @param col column
	 * @return Editor component
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col)
	{
		// log.config( "FindValueEditor.getTableCellEditorComponent", "r=" + row + ", c=" + col);
		// Between - enables valueToColumn
		m_between = false;
		Object betweenValue = table.getModel().getValueAt(row, FindPanel.INDEX_OPERATOR);
		if (m_valueToColumn && betweenValue != null
				&& betweenValue.equals(MQuery.OPERATORS[MQuery.BETWEEN_INDEX]))
		{
			m_between = true;
		}

		boolean enabled = !m_valueToColumn || (m_valueToColumn && m_between);
		log.config("(" + value + ") - Enabled=" + enabled);

		String columnName = null;
		Object column = table.getModel().getValueAt(row, FindPanel.INDEX_COLUMNNAME);
		if (column != null)
		{
			if (column instanceof ValueNamePair)
			{
				columnName = ((ValueNamePair)column).getValue();
			}
			else
			{
				columnName = column.toString();
			}
		}

		// Create Editor
		GridField field = getMField(columnName);
		
		// 08880
		// restore column name (important in case of columnsqls)
		columnName = field.getColumnName();
		
		
		// log.fine( "Field=" + field.toStringX());
		if (field.isKey())
		{
			m_editor = new VNumber(columnName, false, false, true, DisplayType.Integer, columnName);
		}
		else
		{
			// metas: cg: start : task : 02354
			if (DisplayType.isLookup(field.getDisplayType()) && !field.isDisplayable() && field.getLookup() == null)
			{
				field.loadLookup(true);
			}
			// metas: cg: end : task : 02354
			m_editor = Services.get(ISwingEditorFactory.class).getEditor(field, true);
		}
		if (m_editor == null)
			m_editor = new VString();

		m_editor.setValue(value);
		m_editor.setReadWrite(enabled);
		m_editor.setBorder(null);
		//
		return (Component)m_editor;
	}   // getTableCellEditorComponent

	/**
	 * Cell Editable.
	 * Called before getTableCellEditorComponent
	 * 
	 * @param e event
	 * @return true if editable
	 */
	@Override
	public boolean isCellEditable(EventObject e)
	{
		// log.config( "FindValueEditor.isCellEditable");
		return true;
	}   // isCellEditable

	/**
	 * Cell Selectable.
	 * Called after getTableCellEditorComponent
	 * 
	 * @param e event
	 * @return true if selectable
	 */
	@Override
	public boolean shouldSelectCell(EventObject e)
	{
		boolean retValue = !m_valueToColumn || (m_valueToColumn && m_between);
		// log.config( "FindValueEditor.shouldSelectCell - " + retValue);
		return retValue;
	}	// shouldSelectCell

	/**
	 * Get MField
	 * 
	 * @return field
	 */
	protected GridField getMField(String columnName)
	{
		return m_find.getTargetMField(columnName);
	} // getMField

} // FindValueEditor
