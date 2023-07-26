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
package org.compiere.report.core;

import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

/**
 *  The TableModel for JTable to present RModel information
 *
 *  @author Jorg Janke
 *  @version  $Id: ResultTableModel.java,v 1.2 2006/07/30 00:51:06 jjanke Exp $
 */
class ResultTableModel extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9197559914473854935L;

	/**
	 *  Create a JTable Model from ReportModel
	 *  @param reportModel
	 */
	public ResultTableModel (RModel reportModel)
	{
		m_model = reportModel;
	}   //  ResultTableModel

	/** The Report Model        */
	private RModel      m_model;

	/**
	 *  Get Row Count
	 *  @return row count
	 */
	public int getRowCount()
	{
		return m_model.getRowCount();
	}   //  getRowCount

	/**
	 *  Get ColumnCount
	 *  @return column count
	 */
	public int getColumnCount()
	{
		return m_model.getColumnCount();
	}   //  getColumnCount

	/**
	 *  Get Column Name
	 *  @param columnIndex
	 *  @return Column Name
	 */
	public String getColumnName(int columnIndex)
	{
		return m_model.getColumnName(columnIndex);
	}   //  getColumnIndex

	/**
	 *  Get Column Class
	 *  @param columnIndex
	 *  @return Column Class
	 */
	public Class<?> getColumnClass(int columnIndex)
	{
		return m_model.getColumnClass(columnIndex);
	}   //  getColumnClass

	/**
	 *  Is Cell Editable
	 *  @param rowIndex
	 *  @param columnIndex
	 *  @return true, if editable
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}   //  isCellEditable

	/**
	 *  Get Value At
	 *  @param row
	 *  @param col
	 *  @return value
	 */
	public Object getValueAt(int row, int col)
	{
		return m_model.getValueAt(row, col);
	}   //  getValueAt

	/**
	 *  Set Value At
	 *  @param aValue
	 *  @param row
	 *  @param col
	 */
	public void setValueAt(Object aValue, int row, int col)
	{
		m_model.setValueAt(aValue, row, col);
		fireTableChanged(new TableModelEvent (this, row, row, col, TableModelEvent.UPDATE));
	}   //  setValueAt

	/**
	 *  Move Row
	 *  @param from index
	 *  @param to index
	 */
	public void moveRow (int from, int to)
	{
		m_model.moveRow (from, to);
	}   //  moveRow

	/**
	 * Returns the ArrayList of ArrayLists that contains the table's data values.
	 * The ArrayLists contained in the outer vector are each a single row of values.
	 * @return the ArrayList of ArrayLists containing the tables data values
	 * @author Teo Sarca [ 1734327 ]
	 */
	protected ArrayList<ArrayList<Object>> getDataList() {
		return m_model.getRows();
	}
}   //  ResultTableModel
