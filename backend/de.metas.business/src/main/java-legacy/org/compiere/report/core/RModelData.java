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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.compiere.model.I_Fact_Acct;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.util.Check;

/**
 * Report Model Data - ValueObject. - Build SQL from RColumn info and Retrieve Data - owned by RModel
 *
 * @author Jorg Janke
 * @version $Id: RModelData.java,v 1.2 2006/07/30 00:51:06 jjanke Exp $
 */
class RModelData implements IRModelMetadata
{
	/**
	 * Constructor. Use query method to populate data
	 * 
	 * @param TableName
	 */
	public RModelData(String TableName)
	{
		super();

		m_TableName = TableName;
	}   // RModelData

	/** The Rows */
	public ArrayList<ArrayList<Object>> rows = new ArrayList<>();

	/** The Row MetaData */
	// NOTE: not actually used at the moment
	public ArrayList<Object> rowsMeta = new ArrayList<Object>();
	/** The Column Definitions */
	private final List<RColumn> cols = new ArrayList<RColumn>();

	/** Table Name */
	private String m_TableName;

	/** Functions: "column index" to "function" */
	private final Map<Integer, String> functions = new HashMap<Integer, String>();
	private final Map<Integer, String> functionsRO = Collections.unmodifiableMap(functions);
	/** Groups: column indexes */
	private final List<Integer> groups = new ArrayList<Integer>();

	/** Array with row numbers that are groups */
	private final List<Integer> m_groupRows = new ArrayList<Integer>();
	private List<Boolean> m_groupRowsIndicator = null;

	/** Logger */
	private static Logger log = LogManager.getLogger(RModelData.class);

	/**
	 * Dispose
	 */
	public void dispose()
	{
		rows.clear();
		rowsMeta.clear();
		cols.clear();
	}   // dispose

	/**************************************************************************
	 * Query
	 * 
	 * @param ctx
	 * @param whereClause the SQL where clause (w/o the WHERE)
	 * @param orderClause
	 */
	public void query(final Properties ctx, final String whereClause, final String orderClause)
	{
		//
		// Create SQL
		final StringBuilder sql = new StringBuilder("SELECT ");
		int columnsCount = cols.size();
		for (int i = 0; i < columnsCount; i++)
		{
			final RColumn rc = cols.get(i);
			if (i > 0)
				sql.append(",");
			sql.append(rc.getColSQL());
		}
		sql.append(" FROM ").append(m_TableName).append(" ").append(RModel.TABLE_ALIAS);
		if (whereClause != null && whereClause.length() > 0)
			sql.append(" WHERE ").append(whereClause);
		String finalSQL = Env.getUserRolePermissions(ctx).addAccessSQL(
				sql.toString(), RModel.TABLE_ALIAS, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ);

		//
		// Build ORDER BY clause
		final StringBuilder sqlOrderByFinal = new StringBuilder();
		if (!Check.isEmpty(orderClause, true))
		{
			sqlOrderByFinal.append(orderClause);
		}
		// Append Fact_Acct_ID to ORDER BY
		if (I_Fact_Acct.Table_Name.equals(m_TableName))
		{
			if (sqlOrderByFinal.length() > 0)
			{
				sqlOrderByFinal.append(", ");
			}
			sqlOrderByFinal.append(RModel.TABLE_ALIAS).append(".").append(I_Fact_Acct.COLUMNNAME_Fact_Acct_ID);
		}

		//
		// Append ORDER BY SQL to final SQL
		if (sqlOrderByFinal.length() > 0)
		{
			finalSQL += " ORDER BY " + sqlOrderByFinal;
		}

		log.debug(finalSQL);

		// FillData
		final ArrayList<ArrayList<Object>> sourceRows = new ArrayList<>();
		int columnIndex = 0;      // rowset index
		RColumn rc = null;
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.createStatement();
			rs = stmt.executeQuery(finalSQL);
			while (rs.next())
			{
				ArrayList<Object> row = new ArrayList<Object>(columnsCount);
				columnIndex = 1;
				// Columns
				for (int i = 0; i < columnsCount; i++)
				{
					rc = cols.get(i);
					// Get ID
					if (rc.isIDcol())
						row.add(new KeyNamePair(rs.getInt(columnIndex++), rs.getString(columnIndex++)));
					// Null check
					else if (rs.getObject(columnIndex) == null)
					{
						columnIndex++;
						row.add(null);
					}
					else if (rc.getColClass() == String.class)
						row.add(rs.getString(columnIndex++));
					else if (rc.getColClass() == BigDecimal.class)
						row.add(rs.getBigDecimal(columnIndex++));
					else if (rc.getColClass() == Double.class)
						row.add(new Double(rs.getDouble(columnIndex++)));
					else if (rc.getColClass() == Integer.class)
						row.add(new Integer(rs.getInt(columnIndex++)));
					else if (rc.getColClass() == Timestamp.class)
						row.add(rs.getTimestamp(columnIndex++));
					else if (rc.getColClass() == Boolean.class)
						row.add(new Boolean("Y".equals(rs.getString(columnIndex++))));
					else
					// should not happen
					{
						row.add(rs.getString(columnIndex++));
					}
				}
				sourceRows.add(row);
			}
		}
		catch (SQLException e)
		{
			if (columnIndex == 0)
				log.error(finalSQL, e);
			else
				log.error("Index=" + columnIndex + "," + rc, e);
		}
		finally
		{
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
		}

		//
		// Process loaded rows
		process(sourceRows);
	}   // query

	/**
	 * Process Data Copy data in m_rows to rows and perform functions
	 * 
	 * @param sourceRows
	 */
	private void process(ArrayList<ArrayList<Object>> sourceRows)
	{
		log.debug("Start Rows=" + sourceRows.size());

		// Row level Funcions
		// would come here

		// Group by Values
		final int groupCount = groups.size(); // i.e. levels
		final int[] groupLevel2ColumnIndex = new int[groupCount]; // level -> column index
		final Object[] groupLevel2Value = new Object[groupCount]; // level -> group's value
		final Map<Integer, Integer> columnIndex2groupLevel = new HashMap<>();
		final Object INITVALUE = new Object();
		for (int groupLevel = 0; groupLevel < groupCount; groupLevel++)
		{
			final int columnIndex = groups.get(groupLevel);
			groupLevel2ColumnIndex[groupLevel] = columnIndex;
			groupLevel2Value[groupLevel] = INITVALUE;
			columnIndex2groupLevel.put(groupLevel, columnIndex);
			log.debug("GroupBy level=" + groupLevel + " col=" + groupLevel2ColumnIndex[groupLevel]);
		}
		// Add additional row to force group change
		final ArrayList<Object> lastRowMarker;
		if (groupCount > 0)
		{
			lastRowMarker = new ArrayList<Object>();
			for (int c = 0; c < cols.size(); c++)
				lastRowMarker.add("");
			sourceRows.add(lastRowMarker);
		}
		else
		{
			lastRowMarker = null;
		}

		// Function Values
		final RModelAggregatedValues functionValues = new RModelAggregatedValues(this);

		//
		// Clear all rows
		rows.clear();

		//
		// Create rows from source rows
		for (int rowIndex = 0; rowIndex < sourceRows.size(); rowIndex++)
		{
			final ArrayList<Object> sourceRow = sourceRows.get(rowIndex);

			//
			// do we have a group break
			final boolean[] haveBreak = new boolean[groupLevel2ColumnIndex.length];
			for (int level = 0; level < groupLevel2ColumnIndex.length; level++)
			{
				final int columnIndex = groupLevel2ColumnIndex[level];
				if (groupLevel2Value[level] == INITVALUE)
				{
					haveBreak[level] = false;
				}
				else if (!groupLevel2Value[level].equals(sourceRow.get(columnIndex)))
				{
					haveBreak[level] = true;
				}
				else
				{
					haveBreak[level] = false;
				}

				// previous level had a break
				if (level > 0 && haveBreak[level - 1])
				{
					haveBreak[level] = true;
				}
			}
			//
			// create group levels - reverse order
			for (int level = groupLevel2ColumnIndex.length - 1; level >= 0; level--)
			{
				final int groupColumnIndex = groupLevel2ColumnIndex[level];
				if (groupLevel2Value[level] == INITVALUE)
				{
					groupLevel2Value[level] = sourceRow.get(groupColumnIndex);
				}
				else if (haveBreak[level])
				{
					final RModelCalculationContext calculationCtx = new RModelCalculationContext(this);
					calculationCtx.setGroupColumnIndex(groupColumnIndex);
					calculationCtx.setGroupLevel2Value(groupLevel2Value);
					calculationCtx.setLevel(level);

					// create new row
					ArrayList<Object> groupRow = new ArrayList<Object>(cols.size());
					for (int columnIndex = 0; columnIndex < cols.size(); columnIndex++)
					{
						calculationCtx.setColumnIndex(columnIndex);

						// Case: the GROUP BY column
						if (columnIndex == groupColumnIndex)
						{
							final Object groupLevelValue = groupLevel2Value[level];
							if (groupLevelValue == null || groupLevelValue.toString().isEmpty())
							{
								groupRow.add("=");
							}
							else
							{
								groupRow.add(groupLevel2Value[level]);
							}
						}
						// Case: Aggregated/Function column or other column
						else
						{
							Object functionValue = functionValues.getColumnAggregatedValue(calculationCtx, groupRow);
							groupRow.add(functionValue);
						}
					}   // for all columns
					//
					m_groupRows.add(new Integer(rows.size())); // group row indicator
					rows.add(groupRow);
					groupLevel2Value[level] = sourceRow.get(groupColumnIndex);
				}
			}   // for all groups

			//
			// Add current source row to be aggregated by our functions
			if (sourceRow != lastRowMarker)
			{
				final RModelCalculationContext calculationCtx = new RModelCalculationContext(this);
				functionValues.addSourceRow(calculationCtx, sourceRow);
			}

			//
			// Add computed row
			rows.add(sourceRow);
		}   // for all m_rows

		//
		// Totals row (last row)
		if (functions.size() > 0)
		{
			final RModelCalculationContext calculationCtx = new RModelCalculationContext(this);
			calculationCtx.setGroupColumnIndex(-1); // we are not grouping on a particular column because these are ending totals
			calculationCtx.setGroupLevel2Value(groupLevel2Value);
			// calculationCtx.setLevel(level); // N/A (i.e. it shall be the level for totals grouping)

			ArrayList<Object> totalsRow = new ArrayList<Object>();
			for (int columnIndex = 0; columnIndex < cols.size(); columnIndex++)
			{
				calculationCtx.setColumnIndex(columnIndex);

				final Object value = functionValues.getColumnTotal(calculationCtx, totalsRow);
				totalsRow.add(value);
			}   // for all columns

			// remove last empty row added earlier to force group change
			if (lastRowMarker != null)
			{
				rows.remove(rows.size() - 1);
			}

			m_groupRows.add(rows.size()); // totals group row indicator
			rows.add(totalsRow);
		} // total row

		log.debug("End Rows=" + rows.size());
		sourceRows.clear();
	}   // process

	/**************************************************************************
	 * Is Row a Group Row
	 * 
	 * @param row row index
	 * @return true, if group row
	 */
	public boolean isGroupRow(int row)
	{
		// build boolean Array
		if (m_groupRowsIndicator == null)
		{
			m_groupRowsIndicator = new ArrayList<Boolean>(rows.size());
			for (int r = 0; r < rows.size(); r++)
				m_groupRowsIndicator.add(new Boolean(m_groupRows.contains(new Integer(r))));
		}
		if (row < 0 || row >= m_groupRowsIndicator.size())
			return false;
		return m_groupRowsIndicator.get(row).booleanValue();
	}   // isGroupRow

	/**
	 * Move Row
	 * 
	 * @param from index
	 * @param to index
	 * @throws IllegalArgumentException if row index is invalid
	 */
	public void moveRow(int from, int to)
	{
		if (from < 0 || to >= rows.size())
			throw new IllegalArgumentException("Row from invalid");
		if (to < 0 || to >= rows.size())
			throw new IllegalArgumentException("Row to invalid");
		// Move Data
		ArrayList<Object> temp = rows.get(from);
		rows.remove(from);
		rows.add(to, temp);
		// Move Description indicator >>> m_groupRows is not in sync after row move !!
		if (m_groupRowsIndicator != null)
		{
			Boolean tempB = m_groupRowsIndicator.get(from);
			m_groupRowsIndicator.remove(from);
			m_groupRowsIndicator.add(to, tempB);
		}
	}   // moveRow

	public RColumn getRColumn(final int col)
	{
		if (col < 0 || col > cols.size())
			throw new java.lang.IllegalArgumentException("Column invalid");
		return cols.get(col);
	}

	public void addRColumn(final RColumn rc)
	{
		cols.add(rc);
	}

	public void addRColumn(final RColumn rc, final int index)
	{
		cols.add(index, rc);
	}

	public RColumn getRColumn(final String columnName)
	{
		if (columnName == null)
			return null;
		for (RColumn col : cols)
		{
			if (columnName.equals(col.getColumnName()))
			{
				return col;
			}
		}
		return null;
	}

	@Override
	public int getRColumnIndex(String columnName)
	{
		if (columnName == null || columnName.length() == 0)
		{
			return -1;
		}

		//
		// Search by ColSQL (backward compatibility)
		for (int i = 0; i < cols.size(); i++)
		{
			final RColumn rc = cols.get(i);
			// log.debug( "Column " + i + " " + rc.getColSQL() + " ? " + columnName);
			if (rc.getColSQL().startsWith(columnName))
			{
				log.debug("Column " + i + " " + rc.getColSQL() + " = " + columnName);
				return i;
			}
		}

		//
		// Search by ColumnName
		for (int i = 0; i < cols.size(); i++)
		{
			final RColumn rc = cols.get(i);
			if (columnName.equals(rc.getColumnName()))
			{
				return i;
			}
		}

		//
		// Not found => return -1
		return -1;
	}

	/**
	 * Get total number of columns
	 * 
	 * @return column count
	 */
	public int getRColumnCount()
	{
		return cols.size();
	}

	/**
	 * Get Column Display Name
	 * 
	 * @param col index
	 * @return ColumnName
	 */
	public String getRColumnDisplayName(final int col)
	{
		if (col < 0 || col > cols.size())
			throw new java.lang.IllegalArgumentException("Column invalid");
		final RColumn rc = cols.get(col);
		if (rc != null)
			return rc.getColHeader();
		return null;
	}

	/**
	 * Get Column Class
	 * 
	 * @param col index
	 * @return Column Class
	 */
	public Class<?> getRColumnClass(final int col)
	{
		if (col < 0 || col > cols.size())
			throw new java.lang.IllegalArgumentException("Column invalid");
		final RColumn rc = cols.get(col);
		if (rc != null)
			return rc.getColClass();
		return null;
	}

	/**
	 * Set a group column - if the group value changes, a new row in inserted performing the calculations set in setGroupFunction(). If you set multiple groups, start with the highest level (e.g.
	 * Country, Region, City) The data is assumed to be sorted to result in meaningful groups.
	 * 
	 * <pre>
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
	 * </pre>
	 * 
	 * @param col The Group BY Column
	 */
	public void setGroup(final int col)
	{
		log.info("RModel.setGroup col=" + col);
		if (col < 0 || col >= cols.size())
		{
			return;
		}

		if (!groups.contains(col))
		{
			groups.add(col);
		}
	}   // setGroup

	@Override
	public int getGroupsCount()
	{
		return groups.size();
	}

	/**
	 * Set Group Function - for the column, set a function like FUNCTION_SUM, FUNCTION_COUNT, ...
	 * 
	 * @param col The column on which the function is performed
	 * @param function The function
	 */
	public void setFunction(final int col, final String function)
	{
		log.info("RModel.setFunction col=" + col + " - " + function);
		if (col < 0 || col >= cols.size())
		{
			return;
		}

		functions.put(col, function);
	}   // setFunction

	@Override
	public Map<Integer, String> getFunctions()
	{
		return functionsRO;
	}
}   // RModelData
