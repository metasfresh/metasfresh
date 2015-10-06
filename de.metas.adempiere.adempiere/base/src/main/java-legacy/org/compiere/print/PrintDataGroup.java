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
package org.compiere.print;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *	Group By Management
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: PrintDataGroup.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 */
public class PrintDataGroup
{
	/**
	 *	Constructor
	 */
	public PrintDataGroup ()
	{
	}	//	PrintDataGroup

	/**	Column-Function Delimiter		*/
	static public final String	DELIMITER = "~";
	/**	Grand Total Indicator			*/
	static public final String 	TOTAL = "=TOTAL=";
	/**	NULL substitute value			*/
	static private final Object	NULL = new String();

	/**	List of group columns			*/
	private ArrayList<String>		m_groups = new ArrayList<String>();
	/** Map of group column & value		*/
	private HashMap<String,Object> 	m_groupMap = new HashMap<String,Object>();
	/**	List of column_function			*/
	private ArrayList<String>		m_functions = new ArrayList<String>();
	/** Map of group_function column & function	*/
	private HashMap<String,PrintDataFunction>	m_groupFunction = new HashMap<String,PrintDataFunction>();

	
	/**************************************************************************
	 * 	Add Group Column
	 * 	@param groupColumnName group column
	 */
	public void addGroupColumn (String groupColumnName)
	{
		m_groups.add(groupColumnName);
	}	//	addGroup

	/**
	 * 	Get Grouyp Column Count.
	 *  TOTAL is included as a column
	 * 	@return number of groups
	 */
	public int getGroupColumnCount()
	{
		return m_groups.size();
	}	//	getGroupColumnCount

	/**
	 * 	Column has a function
	 * 	@param columnName column name or TOTAL
	 * 	@return true if column has function
	 */
	public boolean isGroupColumn (String columnName)
	{
		if (columnName == null || m_groups.size() == 0)
			return false;
		for (int i = 0; i < m_groups.size(); i++)
		{
			if (columnName.equals(m_groups.get(i)))
				return true;
		}
		return false;
	}	//	isGroupColumn

	/**
	 * 	Check for Group Change
	 * 	@param groupColumnName column name
	 * 	@param value column value
	 * 	@return null if no group change otherwise old value
	 */
	public Object groupChange (String groupColumnName, Object value)
	{
		if (!isGroupColumn(groupColumnName))
			return null;
		Object newValue = value;
		if (newValue == null)
			newValue = NULL;
		//
		if (m_groupMap.containsKey(groupColumnName))
		{
			Object oldValue = m_groupMap.get(groupColumnName);
			if (newValue.equals(oldValue))
				return null;
			m_groupMap.put(groupColumnName, newValue);
			return oldValue;
		}
		m_groupMap.put(groupColumnName, newValue);
		return null;
	}	//	groupChange

	
	/**************************************************************************
	 * 	Add Function Column
	 * 	@param functionColumnName column name
	 * 	@param function function
	 */
	public void addFunction (String functionColumnName, char function)
	{
		m_functions.add(functionColumnName + DELIMITER + function);
		if (!m_groups.contains(TOTAL))
			m_groups.add(TOTAL);
	}	//	addFunction

	/**
	 * 	Column has a function
	 * 	@param columnName column name
	 * 	@return true if column has function
	 */
	public boolean isFunctionColumn (String columnName)
	{
		if (columnName == null || m_functions.size() == 0)
			return false;
		for (int i = 0; i < m_functions.size(); i++)
		{
			String f = (String)m_functions.get(i);
			if (f.startsWith(columnName))
				return true;
		}
		return false;
	}	//	isFunctionColumn

	/**
	 * 	Get calculated functions of column
	 *  @param columnName column name or TOTAL
	 * 	@return array of functions
	 */
	public char[] getFunctions(String columnName)
	{
		ArrayList<String> list = new ArrayList<String>();	//	the final function List
		Iterator it = m_groupFunction.keySet().iterator();
		while(it.hasNext())
		{
			String group_function = (String)it.next();	//	=TOTAL=~LoadSeq
			if (group_function.startsWith(columnName))
			{
				group_function = group_function.substring(group_function.lastIndexOf(DELIMITER)+1);	//	LoadSeq
				for (int i = 0; i < m_functions.size(); i++)
				{
					String col_function = ((String)m_functions.get(i));	//	LoadSeq~A
					if (col_function.startsWith(group_function))
					{
						String function = col_function.substring(col_function.lastIndexOf(DELIMITER)+1);
						if (!list.contains(function))
							list.add(function);
					}
				}
			}
		}
		//	Return Value
		char[] retValue = new char[list.size()];
		for (int i = 0; i < retValue.length; i++)
			retValue[i] = ((String)list.get(i)).charAt(0);
	//	log.finest( "PrintDataGroup.getFunctions for " + columnName + "/" + retValue.length, new String(retValue));
		return retValue;
	}	//	getFunctions

	/**
	 * 	Column has a function
	 * 	@param columnName column name
	 *  @param function function
	 * 	@return true if column has function
	 */
	public boolean isFunctionColumn (String columnName, char function)
	{
		if (columnName == null || m_functions.size() == 0)
			return false;
		String key = columnName + DELIMITER + function;
		for (int i = 0; i < m_functions.size(); i++)
		{
			String f = (String)m_functions.get(i);
			if (f.equals(key))
				return true;
		}
		return false;
	}	//	isFunctionColumn

	
	/**************************************************************************
	 * 	Add Value to groups
	 * 	@param functionColumnName column name
	 * 	@param functionValue value
	 */
	public void addValue (String functionColumnName, BigDecimal functionValue)
	{
		if (!isFunctionColumn(functionColumnName))
			return;
		//	Group Breaks
		for (int i = 0; i < m_groups.size(); i++)
		{
			String groupColumnName = (String)m_groups.get(i);
			String key = groupColumnName + DELIMITER + functionColumnName;
			PrintDataFunction pdf = (PrintDataFunction)m_groupFunction.get(key);
			if (pdf == null)
				pdf = new PrintDataFunction();
			pdf.addValue(functionValue);
			m_groupFunction.put(key, pdf);
		}
	}	//	addValue

	/**
	 * 	Get Value
	 * 	@param groupColumnName group column name (or TOTAL)
	 * 	@param functionColumnName function column name
	 * 	@param function function
	 * 	@return value
	 */
	public BigDecimal getValue (String groupColumnName, String functionColumnName,
		char function)
	{
		String key = groupColumnName + DELIMITER + functionColumnName;
		PrintDataFunction pdf = (PrintDataFunction)m_groupFunction.get(key);
		if (pdf == null)
			return null;
		return pdf.getValue(function);
	}	//	getValue

	/**
	 * 	Reset Function values
	 * 	@param groupColumnName group column name (or TOTAL)
	 * 	@param functionColumnName function column name
	 */
	public void reset (String groupColumnName, String functionColumnName)
	{
		String key = groupColumnName + DELIMITER + functionColumnName;
		PrintDataFunction pdf = (PrintDataFunction)m_groupFunction.get(key);
		if (pdf != null)
			pdf.reset();
	}	//	reset
	
	/**************************************************************************
	 * 	String Representation
	 * 	@return info
	 */
	public String toString ()
	{
		return toString(false);
	}	//	toString

	/**
	 * 	String Representation
	 *  @param withData with data
	 * 	@return info
	 */
	public String toString (boolean withData)
	{
		StringBuffer sb = new StringBuffer("PrintDataGroup[");
		sb.append("Groups=");
		for (int i = 0; i < m_groups.size(); i++)
		{
			if (i != 0)
				sb.append(",");
			sb.append(m_groups.get(i));
		}
		if (withData)
		{
			Iterator it = m_groupMap.keySet().iterator();
			while(it.hasNext())
			{
				Object key = it.next();
				Object value = m_groupMap.get(key);
				sb.append(":").append(key).append("=").append(value);
			}
		}
		sb.append(";Functions=");
		for (int i = 0; i < m_functions.size(); i++)
		{
			if (i != 0)
				sb.append(",");
			sb.append(m_functions.get(i));
		}
		if (withData)
		{
			Iterator it = m_groupFunction.keySet().iterator();
			while(it.hasNext())
			{
				Object key = it.next();
				Object value = m_groupFunction.get(key);
				sb.append(":").append(key).append("=").append(value);
			}
		}
		sb.append("]");
		return sb.toString();
	}	//	toString

}	//	PrintDataGroup

