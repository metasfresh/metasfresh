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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

/**
 *  Report Model.
 *  Data is maintained in RModelData
 *
 *  @author Jorg Janke
 *  @version  $Id: RModel.java,v 1.2 2006/07/30 00:51:06 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 				<li>BF [ 1778373 ] AcctViewer: data is not sorted proper
 */
public class RModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1283407521250413019L;

	/**
	 *  Constructor. Creates RModelData
	 *  @param TableName
	 */
	public RModel (String TableName)
	{
		m_data = new RModelData (TableName);
	}   //  RModel

	/** Table Alias for SQL     */
	public static final String      TABLE_ALIAS = "zz";
	/** Function: Count         */
	public static final String      FUNCTION_COUNT = "Count";
	/** Function: Sum           */
	public static final String      FUNCTION_SUM = "Sum";
	/** Function: Account Balance */
	public static final String      FUNCTION_ACCOUNT_BALANCE = "ACCOUNT_BALANCE";
	/** Function: Account Balance */
	public static final String      FUNCTION_ProductQtySum = "ProductQtySum";

	/** Helper to retrieve Actual Data  */
	private RModelData      m_data = null;
	/** Is Content editable by user     */
	private boolean         m_editable = false;

//	/**	Logger			*/
//	private static Logger log = CLogMgt.getLogger(RModel.class);
	
	/**************************************************************************
	 *  Get Column Display Name
	 *  @param col
	 *  @return RColumn
	 */
	protected RColumn getRColumn (int col)
	{
		return m_data.getRColumn(col);
	}   //  getRColumn
	
	/**
	 * Get column by column name
	 * @param columnName
	 * @return column or null if not found
	 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
	 */
	public RColumn getRColumn (String columnName)
	{
		return m_data.getRColumn(columnName);
	}

	
	/**************************************************************************
	 *  Add Column
	 *  @param rc
	 */
	public void addColumn (final RColumn rc)
	{
		m_data.addRColumn(rc);
	}   //  addColumn

	/**
	 *  Add Column at Index
	 *  @param rc
	 *  @param index
	 */
	public void addColumn (final RColumn rc, final int index)
	{
		m_data.addRColumn(rc, index);
	}   //  addColumn

	/**
	 *  Add Row
	 */
	public void addRow ()
	{
		m_data.rows.add(new ArrayList<Object>());
		m_data.rowsMeta.add(null);
	}   //  addRow

	/**
	 *  Add Row at Index
	 *  @param index
	 */
	public void addRow (int index)
	{
		m_data.rows.add(index, new ArrayList<Object>());
		m_data.rowsMeta.add(index, null);
	}   //  addRow

	/**
	 *  Add Row
	 *  @param l
	 */
	public void addRow (ArrayList<Object> l)
	{
		m_data.rows.add(l);
		m_data.rowsMeta.add(null);
	}   //  addRow

	/**
	 *  Add Row at Index
	 *  @param l
	 *  @param index
	 */
	public void addRow (ArrayList<Object> l, int index)
	{
		m_data.rows.add(index, l);
		m_data.rowsMeta.add(index, null);
	}   //  addRow

	/**
	 *  Find index for ColumnName
	 *  @param columnName
	 *  @return index or -1 if not found
	 */
	public int getColumnIndex (String columnName)
	{
		return m_data.getRColumnIndex(columnName);
	}   //  getColumnIndex

	
	/**************************************************************************
	 *  Query
	 *  @param ctx
	 *  @param whereClause
	 *  @param orderClause
	 */
	public void query (Properties ctx, String whereClause, String orderClause)
	{
		m_data.query (ctx, whereClause, orderClause);
	}   //  query

	
	/**************************************************************************
	 *  Set a group column
	 *  @param columnName
	 */
	public void setGroup (String columnName)
	{
		setGroup(getColumnIndex(columnName));
	}   //  setGroup

	/**
	 *  Set a group column -
	 *  if the group value changes, a new row in inserted
	 *  performing the calculations set in setGroupFunction().
	 *  If you set multiple groups, start with the highest level
	 *  (e.g. Country, Region, City)
	 *  The data is assumed to be sorted to result in meaningful groups.
	 *  <pre>
	 *  A AA 1
	 *  A AB 2
	 *  B BA 3
	 *  B BB 4
	 *  after setGroup (0)
	 *  A AA 1
	 *  A AB 2
	 *  A
	 *  B BA 3
	 *  B BB 4
	 *  B
	 *  </pre>
	 *  @param col   The Group BY Column
	 */
	public void setGroup (int col)
	{
		m_data.setGroup(col);
	}   //  setGroup

	/**
	 *  Is Row a Group Row
	 *  @param row index
	 *  @return true if row is a group row
	 */
	public boolean isGroupRow (int row)
	{
		return m_data.isGroupRow(row);
	}   // isGroupRow

	/**
	 *  Set Group Function
	 *  @param columnName
	 *  @param function
	 */
	public void setFunction (String columnName, String function)
	{
		setFunction(getColumnIndex(columnName), function);
	}   //  setFunction

	/**
	 *  Set Group Function -
	 *  for the column, set a function like FUNCTION_SUM, FUNCTION_COUNT, ...
	 *  @param col   The column on which the function is performed
	 *  @param function  The function
	 */
	public void setFunction (int col, String function)
	{
		m_data.setFunction(col, function);
	}   //  setFunction

	/*************************************************************************/
	//  TableModel interface

	/**
	 *  Return Total mumber of rows
	 *  @return row count
	 */
	public int getRowCount()
	{
		return m_data.rows.size();
	}   //  getRowCount

	/**
	 *  Get total number of columns
	 *  @return column count
	 */
	public int getColumnCount()
	{
		return m_data.getRColumnCount();
	}   //  getColumnCount

	/**
	 *  Get Column Display Name
	 *  @param col index
	 *  @return ColumnName
	 */
	public String getColumnName (int col)
	{
		return m_data.getRColumnDisplayName(col);
	}   //  getColumnName

	/**
	 *  Get Column Class
	 *  @param col index
	 *  @return Column Class
	 */
	public Class<?> getColumnClass (final int col)
	{
		return m_data.getRColumnClass(col);
	}   //  getColumnClass

	/**
	 *  Is Cell Editable
	 *  @param rowIndex
	 *  @param columnIndex
	 *  @return true, if editable
	 */
	public boolean isCellEditable (int rowIndex, int columnIndex)
	{
		return m_editable;
	}   //  isCellEditable

	/**
	 *  Get Value At
	 *  @param row
	 *  @param col
	 *  @return value
	 */
	public Object getValueAt(int row, int col)
	{
		//  invalid row
		if (row < 0 || row >= m_data.rows.size())
			return null;
	//		throw new java.lang.IllegalArgumentException("Row invalid");
		if (col < 0 || col >= m_data.getRColumnCount())
			return null;
	//		throw new java.lang.IllegalArgumentException("Column invalid");
		//
		ArrayList<Object> myRow = (ArrayList<Object>)m_data.rows.get(row);
		//  invalid column
		if (myRow == null || col >= myRow.size())
			return null;
		//  setValue
		return myRow.get(col);
	}   //  getValueAt

	/**
	 *  Set Value At
	 *  @param aValue
	 *  @param row
	 *  @param col
	 *  @throws IllegalArgumentException if row/column is invalid or cell is read only
	 */
	public void setValueAt(Object aValue, int row, int col)
	{
		//  invalid row
		if (row < 0 || row >= m_data.rows.size())
			throw new IllegalArgumentException("Row invalid");
		if (col < 0 || col >= m_data.getRColumnCount())
			throw new IllegalArgumentException("Column invalid");
		if (!isCellEditable(row, col))
			throw new IllegalArgumentException("Cell is read only");
		//
		ArrayList<Object> myRow = m_data.rows.get(row);
		//  invalid row
		if (myRow == null)
			throw new java.lang.IllegalArgumentException("Row not initialized");
		//  not enough columns - add nulls
		if (col >= myRow.size())
			while (myRow.size() < m_data.getRColumnCount())
				myRow.add(null);
		//  setValue
		myRow.set(col, aValue);
	}   //  setValueAt

	/**
	 *  Move Row
	 *  @param from index
	 *  @param to index
	 */
	public void moveRow (int from, int to)
	{
		m_data.moveRow(from,to);
	}   //  moveRow
	
	/**
	 * Returns the ArrayList of ArrayLists that contains the table's data values.
	 * The ArrayLists contained in the outer vector are each a single row of values.
	 * @return the ArrayList of ArrayLists containing the tables data values
	 * @author Teo Sarca [ 1734327 ]
	 */
	public ArrayList<ArrayList<Object>> getRows()
	{
		return m_data.rows;
	}


	/*************************************************************************/

}   //  RModel
