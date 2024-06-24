/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import de.metas.cache.CacheMgt;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.adempiere.util.GridRowCtx;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.GridTab.DataNewCopyMode;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.MSort;
import org.compiere.util.Trx;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import static org.compiere.model.GridTableUtils.isValueChanged;
import static org.compiere.model.GridTableUtils.readData;

/**
 * Grid Table Model for JDBC access including buffering.
 *
 * <pre>
 *		The following data types are handled
 *			Integer		for all IDs
 *			BigDecimal	for all Numbers
 *			Timestamp	for all Dates
 *			String		for all others
 *  The data is read via r/o resultset and cached in m_buffer. Writes/updates
 *  are via dynamically constructed SQL INSERT/UPDATE statements. The record
 *  is re-read via the resultset to get results of triggers.
 *
 * </pre>
 *
 * The model maintains and fires the requires TableModelEvent changes,
 * the DataChanged events (loading, changed, etc.)
 * as well as Vetoable Change event "RowChange"
 * (for row changes initiated by moving the row in the table grid).
 *
 * @author Jorg Janke
 * @version $Id: GridTable.java,v 1.9 2006/08/09 16:38:25 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1901192 ] LogMigrationScripts: GridTable.dataSave: manual update
 *         <li>BF [ 1943682 ] Copy Record should not copy IsApproved and IsGenerated
 *         <li>BF [ 1949543 ] Window freeze if there is a severe exception
 *         <li>BF [ 1984310 ] GridTable.getClientOrg() doesn't work for AD_Client/AD_Org
 * @author victor.perez@e-evolution.com,www.e-evolution.com
 *         <li>BF [ 2910358 ] Error in context when a field is found in different tabs.
 *         https://sourceforge.net/tracker/?func=detail&aid=2910358&group_id=176962&atid=879332
 *         <li>BF [ 2910368 ] Error in context when IsActive field is found in different
 *         https://sourceforge.net/tracker/?func=detail&aid=2910368&group_id=176962&atid=879332
 */
// metas: synched with rev 11113
public class GridTable extends AbstractTableModel
		implements Serializable
{
	/**
	 * generated
	 */
	private static final long serialVersionUID = -4397161719594270579L;

	/**
	 * JDBC Based Buffered Table
	 *
	 * @param ctx Properties
	 * @param AD_Table_ID table id
	 * @param TableName table name
	 * @param WindowNo window no
	 * @param TabNo tab no
	 * @param withAccessControl if true adds AD_Client/Org restrictuins
	 * @param virtual use virtual table mode if table is mark as high volume
	 */
	public GridTable(Properties ctx, int AD_Table_ID, String TableName, int WindowNo, int TabNo,
			boolean withAccessControl, boolean virtual)
	{
		super();
		m_ctx = ctx;
		m_AD_Table_ID = AD_Table_ID;
		setTableName(TableName);
		m_WindowNo = WindowNo;
		m_TabNo = TabNo;
		m_withAccessControl = withAccessControl;
		m_virtual = virtual && Services.get(IADTableDAO.class).retrieveTable(AD_Table_ID).isHighVolume(); // metas: is this metas change?
	}	// MTable

	private static final Logger log = LogManager.getLogger(GridTable.class);
	private final Properties m_ctx;
	private final int m_AD_Table_ID;
	private String m_tableName = "";
	private final int m_WindowNo;
	/** Tab No 0.. */
	private final int m_TabNo;
	private final boolean m_withAccessControl;
	private boolean m_readOnly = true;
	private boolean m_deleteable = true;
	// virtual table state variables
	private boolean m_virtual;
	private int m_cacheStart;
	private int m_cacheEnd;
	public static final String CTX_KeyColumnName = "KeyColumnName";
	//

	/** Rowcount */
	private int m_rowCount = 0;
	/** Has Data changed? */
	private boolean m_changed = false;
	/** Index of changed row via SetValueAt */
	private int m_rowChanged = -1;
	/** Insert mode active */
	private boolean m_inserting = false;
	/** Inserted Row number */
	private int m_newRow = -1;

	/** Is the Resultset open? */
	private boolean m_open = false;

	// The buffer for all data
	private volatile ArrayList<Object[]> m_buffer = new ArrayList<>(100);
	private volatile ArrayList<MSort> m_sort = new ArrayList<>(100);
	/** Original row data */
	private Object[] m_rowData = null;
	/** Original data [row,col,data] */
	private Object[] m_oldValue = null;
	//
	private Loader m_loader = null;

	/** Columns */
	private ArrayList<GridField> m_fields = new ArrayList<>(30);

	/** Complete SQL statement */
	private String m_SQL;
	/** SQL Statement for Row Count */
	private String m_SQL_Count;
	/** The SELECT clause with FROM */
	private String m_SQL_Select;

	/** The static where clause */
	private String m_whereClause = "";
	/** WhereClause including all filters(current row, current days) but without security filter */// metas
	private String m_whereClauseFinal = "";

	/** Show only Processed='N' and last 24h records */
	private boolean m_onlyCurrentRows = false;
	/** Show only Not processed and x days */
	private int m_onlyCurrentDays = 1;
	/** Static ORDER BY clause */
	private String m_orderClause = "";
	/** Max Rows to query or 0 for all */
	private int m_maxRows = 0;

	/** Index of Key Column */
	private int m_indexKeyColumn = -1;
	// /** Index of Color Column */
	// private int m_indexColorColumn = -1;
	/** Index of Processed Column */
	private int m_indexProcessedColumn = -1;
	/** Index of IsActive Column */
	private int m_indexActiveColumn = -1;
	/** Index of AD_Client_ID Column */
	private int m_indexClientColumn = -1;
	/** Index of AD_Org_ID Column */
	private int m_indexOrgColumn = -1;

	/** Vetoable Change Bean support */
	private VetoableChangeSupport m_vetoableChangeSupport = new VetoableChangeSupport(this);
	/** Property of Vetoable Bean support "RowChange" */
	public static final String PROPERTY = "MTable-RowSave";

	/**
	 * Set Table Name
	 *
	 * @param newTableName table name
	 */
	public void setTableName(String newTableName)
	{
		if (m_open)
		{
			log.error("Table already open - ignored");
			return;
		}
		if (newTableName == null || newTableName.length() == 0)
		{
			return;
		}
		m_tableName = newTableName;
	}	// setTableName

	/**
	 * Get Table Name
	 *
	 * @return table name
	 */
	public String getTableName()
	{
		return m_tableName;
	}	// getTableName

	// /**
	// * Set Where Clause (w/o the WHERE and w/o History).
	// * @param newWhereClause sql where clause
	// * @param onlyCurrentRows only current rows
	// * @param onlyCurrentDays how many days back for current
	// * @return true if where clase set
	// */
	// public boolean setSelectWhereClause(String newWhereClause, boolean onlyCurrentRows, int onlyCurrentDays)
	// {
	//// metas: begin
	// return setSelectWhereClause(newWhereClause, onlyCurrentRows, onlyCurrentDays, null);
	// }
	public boolean setSelectWhereClause(String newWhereClause, boolean onlyCurrentRows, int onlyCurrentDays, MQuery newQuery)
	{
		if (m_open)
		{
			log.error("Table already open - ignored");
			return false;
		}
		//
		query = newQuery; // metas
		m_whereClause = newWhereClause;
		m_onlyCurrentRows = onlyCurrentRows;
		m_onlyCurrentDays = onlyCurrentDays;
		if (m_whereClause == null)
		{
			m_whereClause = "";
		}
		return true;
	}	// setWhereClause

	public boolean setSelectWhereClause(String newWhereClause,
			String newDefaultWhereClause, boolean onlyCurrentRows,
			int onlyCurrentDays)
	{
		if (m_open)
		{
			log.error("Table already open - ignored");
			return false;
		}
		//
		m_whereClause = newWhereClause;
		m_defaultWhereClause = newDefaultWhereClause;
		m_onlyCurrentRows = onlyCurrentRows;
		m_onlyCurrentDays = onlyCurrentDays;
		if (m_whereClause == null)
		{
			m_whereClause = "";
		}
		return true;
	}	// setWhereClause

	/**
	 * Get record set Where Clause (w/o the WHERE and w/o History)
	 *
	 * @return where clause
	 */
	public String getSelectWhereClause()
	{
		return m_whereClause;
	}	// getWhereClause

	/**
	 * Get record set Where Clause (including recent records and history filterings). Mainly this is the where clause used to fetch the records in this GridTable.
	 *
	 * NOTE: the Where Clause does not contain security restrictions
	 *
	 * @return where clause
	 */
	// metas
	public String getSelectWhereClauseFinal()
	{
		return m_whereClauseFinal;
	}

	/**
	 * Is History displayed
	 *
	 * @return true if history displayed
	 */
	public boolean isOnlyCurrentRowsDisplayed()
	{
		return !m_onlyCurrentRows;
	}	// isHistoryDisplayed

	/**
	 * Set Order Clause (w/o the ORDER BY)
	 *
	 * @param newOrderClause sql order by clause
	 */
	public void setOrderClause(String newOrderClause)
	{
		m_orderClause = newOrderClause;
		if (m_orderClause == null)
		{
			m_orderClause = "";
		}
	}	// setOrderClause

	/**
	 * Get Order Clause (w/o the ORDER BY)
	 *
	 * @return order by clause
	 */
	public String getOrderClause()
	{
		return m_orderClause;
	}	// getOrderClause

	/**
	 * Assemble & store
	 * m_SQL, m_countSQL and m_WhereClauseFinal
	 *
	 * @return m_SQL
	 */
	private String createSelectSql()
	{
		if (m_fields.size() == 0 || m_tableName == null || m_tableName.equals(""))
		{
			return "";
		}

		// Create SELECT Part
		StringBuilder select = new StringBuilder("SELECT ");
		for (int i = 0; i < m_fields.size(); i++)
		{
			if (i > 0)
			{
				select.append(",");
			}

			final GridField field = m_fields.get(i);
			select.append(field.getColumnSQL(true));	// ColumnName or Virtual Column
		}
		//
		select.append(" FROM ").append(m_tableName);
		m_SQL_Select = select.toString();
		m_SQL_Count = "SELECT COUNT(*) FROM " + m_tableName;
		// BF [ 2910358 ]
		// Restore the Original Value for Key Column Name based in Tab Context Value
		// metas: Achtung, wenn dieses nicht auskommentiert ist, gibt es Probleme im Cockpit
		// int parentTabNo = getParentTabNo();
		// String parentKey = Env.getContext(m_ctx, m_WindowNo, parentTabNo,
		// GridTab.CTX_KeyColumnName, true);
		// String valueKey = null;
		// String currKey = null;
		// if (parentKey != null && parentKey.length() > 0) {
		// valueKey = Env.getContext(m_ctx, m_WindowNo, parentTabNo,
		// parentKey, true);
		// currKey = Env.getContext(m_ctx, m_WindowNo, parentKey);
		// if (currKey == null)
		// currKey = new String("");
		// if (valueKey != null && valueKey.length() > 0 && parentKey != null
		// && parentKey.length() > 0 && !currKey.equals(valueKey)) {
		// Env.setContext(m_ctx, m_WindowNo, parentKey, valueKey);
		// }
		// }
		// metas end

		m_whereClauseFinal = createSelectWhereClauseSql();

		// RO/RW Access
		m_SQL = m_SQL_Select + " WHERE " + m_whereClauseFinal.toString();
		m_SQL_Count += " WHERE " + m_whereClauseFinal.toString();
		if (m_withAccessControl)
		{
			// boolean ro = MRole.SQL_RO; // metas: commented out because variable is not used
			// if (!m_readOnly)
			// ro = MRole.SQL_RW;
			final IUserRolePermissions role = Env.getUserRolePermissions(m_ctx);
			m_SQL = role.addAccessSQL(m_SQL, m_tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ);
			m_SQL_Count = role.addAccessSQL(m_SQL_Count, m_tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ);
		}

		// ORDER BY
		if (!m_orderClause.equals(""))
		{
			m_SQL += " ORDER BY " + m_orderClause;
		}

		//
		log.debug(m_SQL_Count);
		Env.setContext(m_ctx, m_WindowNo, m_TabNo, GridTab.CTX_SQL, m_SQL);
		return m_SQL;
	}	// createSelectSql

	private String createSelectWhereClauseSql()
	{
		final StringBuilder where = new StringBuilder("");
		// WHERE
		if (m_whereClause.length() > 0)
		{
			where.append("(");
			if (m_whereClause.indexOf('@') == -1)
			{
				where.append(m_whereClause);
			}
			else    // replace variables
			{
				String context = Env.parseContext(m_ctx, m_WindowNo, m_whereClause, false);
				if (context != null && context.trim().length() > 0)
				{
					where.append(context);
				}
				else
				{
					log.warn("Failed to parse where clause. whereClause={}", m_whereClause);
					where.append(" 1 = 2 ");
				}
			}
			where.append(")");
		}

		// metas: cg: task 04771 : start
		// make sure that the new records are put it in the where clause
		final String whereNewCreated = getNewRecordsWhereClause();
		if (!Check.isEmpty(whereNewCreated, true))
		{
			if (where.length() > 0)
			{
				where.insert(0, "( ");
				where.append(" OR (");
				where.append(whereNewCreated);
				where.append(" ) ) ");
			}
		}
		// metas: cg: task 04771 : end

		if (m_onlyCurrentRows && m_TabNo == 0)
		{
			if (where.length() > 0)
			{
				where.append(" AND ");
			}

			// Show only unprocessed or the one updated within x days
			// metas: 03922: filter by Processed only if column exists
			if (findColumn("Processed") >= 0)
			{
				where.append("(Processed='N' OR Updated>now()-1)");
			}
			else
			{
				where.append("Updated>now()-1");
			}
		}

		if (where.length() <= 0)
		{
			where.append("1=1");
		}

		return where.toString();
	}

	/**
	 * Add Field to Table
	 *
	 * @param field field
	 */
	public void addField(GridField field)
	{
		if (m_open)
		{
			log.error("Table already open - ignored: {}", field.getColumnName());
			return;
		}

		final IUserRolePermissions role = Env.getUserRolePermissions(m_ctx);
		if (!role.isColumnAccess(m_AD_Table_ID, field.getAD_Column_ID(), Access.READ))
		{
			log.debug("No Column Access {}", field.getColumnName());
			return;
		}
		// Set Index for Key column
		if (field.isKey())
		{
			m_indexKeyColumn = m_fields.size();
		}
		// Set Index of other standard columns
		if (field.getColumnName().equals("IsActive"))
		{
			m_indexActiveColumn = m_fields.size();
		}
		else if (field.getColumnName().equals("Processed"))
		{
			m_indexProcessedColumn = m_fields.size();
		}
		else if (field.getColumnName().equals("AD_Client_ID"))
		{
			m_indexClientColumn = m_fields.size();
		}
		else if (field.getColumnName().equals("AD_Org_ID"))
		{
			m_indexOrgColumn = m_fields.size();
		}
		//
		m_fields.add(field);
	}	// addColumn

	/**
	 * Returns database column name
	 *
	 * @param index the column being queried
	 * @return column name
	 */
	@Override
	public String getColumnName(int index)
	{
		if (index < 0 || index > m_fields.size())
		{
			log.error("Invalid index={}", index);
			return "";
		}
		//
		final GridField field = m_fields.get(index);
		return field.getColumnName();
	}   // getColumnName

	/**
	 * Returns a column given its name.
	 *
	 * @param columnName string containing name of column to be located
	 * @return the column index with <code>columnName</code>, or -1 if not found
	 */
	@Override
	public int findColumn(final String columnName)
	{
		for (int i = 0; i < m_fields.size(); i++)
		{
			final GridField field = m_fields.get(i);
			if (columnName.equals(field.getColumnName()))
			{
				return i;
			}
		}
		return -1;
	}   // findColumn

	/**
	 * Returns Class of database column/field
	 *
	 * @param index the column being queried
	 * @return the class
	 */
	@Override
	public Class<?> getColumnClass(int index)
	{
		if (index < 0 || index >= m_fields.size())
		{
			log.error("Invalid index={}", index);
			return null;
		}
		final GridField field = m_fields.get(index);
		return DisplayType.getClass(field.getDisplayType(), false);
	}   // getColumnClass

	// /**
	// * Set Select Clause Parameter.
	// * Assumes that you set parameters starting from index zero
	// * @param index index
	// * @param parameter parameter
	// */
	// public void setParameterSELECT (int index, Object parameter)
	// {
	// if (index >= m_parameterSELECT.size())
	// m_parameterSELECT.add(parameter);
	// else
	// m_parameterSELECT.set(index, parameter);
	// } // setParameterSELECT

	// /**
	// * Set Where Clause Parameter.
	// * Assumes that you set parameters starting from index zero
	// * @param index index
	// * @param parameter parameter
	// */
	// public void setParameterWHERE (int index, Object parameter)
	// {
	// if (index >= m_parameterWHERE.size())
	// m_parameterWHERE.add(parameter);
	// else
	// m_parameterWHERE.set(index, parameter);
	// } // setParameterWHERE

	/**
	 * Get Column at index
	 *
	 * @param index index
	 * @return MField
	 */
	protected GridField getField(int index)
	{
		if (index < 0 || index >= m_fields.size())
		{
			return null;
		}
		return m_fields.get(index);
	}	// getColumn

	/**
	 * Return Columns with Identifier (ColumnName)
	 *
	 * @param columnName column name
	 * @return GridField or null
	 */
	protected GridField getField(final String columnName)
	{
		if (columnName == null || columnName.length() == 0)
		{
			return null;
		}
		int cols = m_fields.size();
		for (int i = 0; i < cols; i++)
		{
			final GridField field = m_fields.get(i);
			if (columnName.equalsIgnoreCase(field.getColumnName()))
			{
				return field;
			}
		}

		return null;
	}	// getField

	/**
	 * Get all Fields
	 *
	 * @return MFields
	 */
	public GridField[] getFields()
	{
		GridField[] retValue = new GridField[m_fields.size()];
		m_fields.toArray(retValue);
		return retValue;
	}   // getField

	/**************************************************************************
	 * Open Database.
	 * if already opened, data is refreshed
	 *
	 * @param maxRows maximum number of rows or 0 for all
	 * @return true if success
	 */
	public boolean open(int maxRows)
	{
		m_maxRows = maxRows;
		if (m_open)
		{
			log.debug("already open");
			dataRefreshAll();
			return true;
		}

		if (m_virtual)
		{
			verifyVirtual();
		}

		// create m_SQL and m_countSQL
		createSelectSql();
		if (m_SQL == null || m_SQL.equals(""))
		{
			log.error("No SQL");
			return false;
		}

		// Start Loading
		m_loader = new Loader();
		m_rowCount = m_loader.open(maxRows, query);
		if (m_virtual)
		{
			m_buffer = new ArrayList<>(210);
		}
		else
		{
			m_buffer = new ArrayList<>(m_rowCount + 10);
		}
		m_sort = new ArrayList<>(m_rowCount + 10);
		m_cacheStart = m_cacheEnd = -1;
		if (m_rowCount > 0)
		{
			if (m_rowCount < 1000)
			{
				m_loader.run();
			}
			else
			{
				m_loader.start();
			}
		}
		else
		{
			m_loader.close();
		}
		m_open = true;
		//
		m_changed = false;
		m_rowChanged = -1;
		m_inserting = false;
		return true;
	}	// open

	private void verifyVirtual()
	{
		if (m_indexKeyColumn == -1)
		{
			m_virtual = false;
			return;
		}
		GridField[] fields = getFields();
		for (int i = 0; i < fields.length; i++)
		{
			if (fields[i].isKey() && i != m_indexKeyColumn)
			{
				m_virtual = false;
				return;
			}
		}
	}

	/**
	 * Is Loading
	 *
	 * @return true if loading
	 */
	public boolean isLoading()
	{
		if (m_loader != null && m_loader.isAlive())
		{
			return true;
		}
		return false;
	}   // isLoading

	/**
	 * Is it open?
	 *
	 * @return true if opened
	 */
	public boolean isOpen()
	{
		return m_open;
	}	// isOpen

	/**
	 * Close Resultset
	 *
	 * @param finalCall final call
	 */
	public void close(boolean finalCall)
	{
		if (!m_open)
		{
			return;
		}
		log.debug("final={}", finalCall);

		// remove listeners
		if (finalCall)
		{
			DataStatusListener evl[] = listenerList.getListeners(DataStatusListener.class);
			for (DataStatusListener element : evl)
			{
				listenerList.remove(DataStatusListener.class, element);
			}
			TableModelListener ev2[] = listenerList.getListeners(TableModelListener.class);
			for (TableModelListener element : ev2)
			{
				listenerList.remove(TableModelListener.class, element);
			}
			VetoableChangeListener vcl[] = m_vetoableChangeSupport.getVetoableChangeListeners();
			for (VetoableChangeListener element : vcl)
			{
				m_vetoableChangeSupport.removeVetoableChangeListener(element);
			}
		}

		// Stop loader
		while (m_loader != null && m_loader.isAlive())
		{
			log.debug("Interrupting Loader ...");
			m_loader.interrupt();
			try
			{
				Thread.sleep(200);		// .2 second
			}
			catch (InterruptedException ie)
			{
			}
		}

		if (!m_inserting)
		{
			dataSave(false);	// not manual
		}

		if (m_buffer != null)
		{
			m_buffer.clear();
			m_buffer = null;
		}
		if (m_sort != null)
		{
			m_sort.clear();
			m_sort = null;
		}

		m_cacheStart = m_cacheEnd = -1;

		if (finalCall)
		{
			dispose();
		}

		// Fields are disposed from MTab
		log.debug("");
		m_open = false;
	}	// close

	/**
	 * Dispose MTable.
	 * Called by close-final
	 */
	private void dispose()
	{
		// MFields
		for (GridField m_field : m_fields)
		{
			m_field.dispose();
		}
		m_fields.clear();
		m_fields = null;
		//
		m_vetoableChangeSupport = null;
		//
		// m_parameterSELECT.clear();
		// m_parameterSELECT = null;
		// m_parameterWHERE.clear();
		// m_parameterWHERE = null;
		// clear data arrays
		m_buffer = null;
		m_sort = null;
		m_rowData = null;
		m_oldValue = null;
		m_loader = null;
	}   // dispose

	/**
	 * Get total database column count (displayed and not displayed)
	 *
	 * @return column count
	 */
	@Override
	public int getColumnCount()
	{
		return m_fields.size();
	}	// getColumnCount

	/**
	 * Get (displayed) field count
	 *
	 * @return field count
	 */
	public int getFieldCount()
	{
		return m_fields.size();
	}	// getFieldCount

	/**
	 * Return number of rows
	 *
	 * @return Number of rows or 0 if not opened
	 */
	@Override
	public int getRowCount()
	{
		return m_rowCount;
	}	// getRowCount

	/**
	 * Sort Entries by Column.
	 * actually the rows are not sorted, just the access pointer ArrayList
	 * with the same size as m_buffer with MSort entities
	 *
	 * @param col col
	 * @param ascending ascending
	 */
	@SuppressWarnings("unchecked")
	public void sort(final int col, final boolean ascending)
	{
		if (getRowCount() <= 0)
		{
			return;
		}

		final GridField field = getField(col);
		// RowIDs are not sorted
		if (field.getDisplayType() == DisplayType.RowID)
		{
			return;
		}
		final boolean isLookup = DisplayType.isLookup(field.getDisplayType());
		final boolean isASI = DisplayType.PAttribute == field.getDisplayType();

		// fill MSort entities with data entity
		for (int i = 0; i < m_sort.size(); i++)
		{
			final MSort sort = m_sort.get(i);
			Object[] rowData = getDataAtRow(i);
			if (rowData[col] == null)
			{
				sort.data = null;
			}
			else if (isLookup || isASI)
			{
				sort.data = field.getLookup().getDisplay(rowData[col]);	// lookup
			}
			else
			{
				sort.data = rowData[col];								// data
			}
		}

		// sort it
		MSort sort = new MSort(0, null);
		sort.setSortAsc(ascending);
		Collections.sort(m_sort, sort);
		if (m_virtual)
		{
			m_buffer.clear();
			m_cacheStart = m_cacheEnd = -1;

			// release sort memory
			for (MSort element : m_sort)
			{
				element.data = null;
			}
		}
		// update UI
		fireTableDataChanged();
		// Info detected by MTab.dataStatusChanged and current row set to 0
		fireDataStatusInfoEvent("Sorted", "#" + m_sort.size());
	}	// sort

	/**
	 * Get Key ID or -1 of none
	 *
	 * @param row row
	 * @return ID or -1
	 */
	public int getKeyID(int row)
	{
		if (m_indexKeyColumn != -1)
		{
			try
			{
				Integer ii = (Integer)getValueAt(row, m_indexKeyColumn);
				if (ii == null)
				{
					return -1;
				}
				return ii.intValue();
			}
			catch (Exception e)     // Alpha Key
			{
				return -1;
			}
		}
		return -1;
	}	// getKeyID

	/**
	 * Get Key ColumnName
	 *
	 * @return key column name
	 */
	public String getKeyColumnName()
	{
		if (m_indexKeyColumn != -1)
		{
			return getColumnName(m_indexKeyColumn);
		}
		return "";
	}	// getKeyColumnName

	/**************************************************************************
	 * Get Value in Resultset
	 *
	 * @param row row
	 * @param col col
	 * @return Object of that row/column
	 */
	@Override
	public Object getValueAt(int row, int col)
	{
		if (!m_open || row < 0 || col < 0 || row >= m_rowCount)
		{
			return null;
		}

		// need to wait for data read into buffer
		int loops = 0;
		while (row >= m_sort.size() && m_loader.isAlive() && loops < 15)
		{
			log.debug("Waiting for loader row={}, size={}", row, m_sort.size());
			try
			{
				Thread.sleep(500);		// 1/2 second
			}
			catch (InterruptedException ie)
			{
			}
			loops++;
		}

		// empty buffer
		if (row >= m_sort.size())
		{
			// log.debug( "Empty buffer");
			return null;
		}

		// return Data item
		Object[] rowData = getDataAtRow(row);
		// out of bounds
		if (rowData == null || col > rowData.length)
		{
			// log.debug( "No data or Column out of bounds");
			return null;
		}
		return rowData[col];
	}	// getValueAt

	public Object[] getDataAtRow(int row)
	{
		MSort sort = m_sort.get(row);
		Object[] rowData = null;
		if (m_virtual)
		{
			int bufferrow = -1;
			if (sort.index != -1 && (row < m_cacheStart || row > m_cacheEnd))
			{
				fillBuffer(row);
			}
			bufferrow = row - m_cacheStart;
			rowData = m_buffer.get(bufferrow);
		}
		else
		{
			rowData = m_buffer.get(sort.index);
		}
		return rowData;
	}

	private void setDataAtRow(int row, Object[] rowData)
	{
		MSort sort = m_sort.get(row);
		if (m_virtual)
		{
			int bufferrow = -1;
			if (sort.index != -1 && (row < m_cacheStart || row > m_cacheEnd))
			{
				fillBuffer(row);
			}
			bufferrow = row - m_cacheStart;
			m_buffer.set(bufferrow, rowData);
		}
		else
		{
			m_buffer.set(sort.index, rowData);
		}

	}

	private void fillBuffer(int start)
	{
		// adjust start if needed
		if (start > 0)
		{
			if (start + 200 >= m_sort.size())
			{
				start = start - (200 - (m_sort.size() - start));
				if (start < 0)
				{
					start = 0;
				}
			}
		}
		StringBuilder sql = new StringBuilder();
		sql.append(m_SQL_Select)
				.append(" WHERE ")
				.append(getKeyColumnName())
				.append(" IN (");
		m_cacheStart = start;
		m_cacheEnd = m_cacheStart - 1;
		Map<Integer, Integer> rowmap = new LinkedHashMap<>(200);
		for (int i = start; i < start + 200 && i < m_sort.size(); i++)
		{
			if (i > start)
			{
				sql.append(",");
			}
			sql.append(m_sort.get(i).index);
			rowmap.put(m_sort.get(i).index, i);
		}
		sql.append(")");
		m_buffer = new ArrayList<>(210);
		for (int i = 0; i < 200; i++)
		{
			m_buffer.add(null);
		}
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.prepareStatement(sql.toString(), null);
			rs = stmt.executeQuery();
			while (rs.next())
			{
				final Object[] data = readData(rs, m_fields);
				final int row = rowmap.remove(data[m_indexKeyColumn]);
				m_buffer.set(row - m_cacheStart, data);
				m_cacheEnd++;
			}
			if (!rowmap.isEmpty())
			{
				List<Integer> toremove = new ArrayList<>();
				for (Map.Entry<Integer, Integer> entry : rowmap.entrySet())
				{
					toremove.add(entry.getValue());
				}
				Collections.reverse(toremove);
				for (Integer row : toremove)
				{
					m_sort.remove((int)row);
					m_buffer.remove(row - m_cacheStart);
				}
			}
		}
		catch (SQLException e)
		{
			log.error(e.getLocalizedMessage(), e);
		}
		finally
		{
			DB.close(rs, stmt);
		}
	}

	/**
	 * Indicate that there will be a change
	 *
	 * @param changed changed
	 */
	public void setChanged(boolean changed)
	{
		// Can we edit?
		if (!m_open || m_readOnly)
		{
			return;
		}

		if (!isInserting() && changed == false && m_changed == true)
		{
			log.warn("Marking a record as not changed could be an issue");
		}

		// Indicate Change
		m_changed = changed;
		if (!changed)
		{
			m_rowChanged = -1;
		}
		// if (changed)
		// fireDataStatusIEvent("", "");
	}	// setChanged

	/**
	 * Set Value in data and update MField.
	 * (called directly or from JTable.editingStopped())
	 *
	 * @param value value to assign to cell
	 * @param row row index of cell
	 * @param col column index of cell
	 */
	@Override
	public final void setValueAt(Object value, int row, int col)
	{
		setValueAt(value, row, col, false);
	}	// setValueAt

	/**
	 * Set Value in data and update MField.
	 * (called directly or from JTable.editingStopped())
	 *
	 * @param value value to assign to cell
	 * @param row row index of cell
	 * @param col column index of cell
	 * @param force force setting new value
	 * @return true if value actually changed; false if ignored (metas)
	 */
	public final boolean setValueAt(Object value, int row, int col, boolean force)
	{
		// Can we edit?
		if (!m_open || m_readOnly       // not accessible
				|| row < 0 || col < 0   // invalid index
				|| m_rowCount == 0	// no rows
				|| row >= m_rowCount)     // invalid row
		{
			return false; // metas
		}

		dataSave(row, false);

		// Has anything changed?
		Object oldValue = getValueAt(row, col);
		value = convertValue(value, col); // metas: tsa: convert the value to it's main type
		if (!force && !isValueChanged(oldValue, value))
		{
			return false; // false
		}

		// Save old value
		m_oldValue = new Object[3];
		m_oldValue[0] = new Integer(row);
		m_oldValue[1] = new Integer(col);
		m_oldValue[2] = oldValue;

		// Set Data item

		Object[] rowData = getDataAtRow(row);
		m_rowChanged = row;

		/**
		 * Selection
		 * if (col == 0)
		 * {
		 * rowData[col] = value;
		 * m_buffer.set(sort.index, rowData);
		 * return;
		 * }
		 **/

		// save original value - shallow copy
		if (m_rowData == null)
		{
			int size = m_fields.size();
			m_rowData = new Object[size];
			System.arraycopy(rowData, 0, m_rowData, 0, size); // metas: optimization
			// for (int i = 0; i < size; i++)
			// m_rowData[i] = rowData[i];
		}

		// save & update
		rowData[col] = value;
		setDataAtRow(row, rowData);
		m_changed = true; // metas
		// update Table
		fireTableCellUpdated(row, col);
		// update MField
		GridField field = getField(col);
		field.setValue(value, m_inserting);
		// inform
		DataStatusEvent evt = createDSE();
		evt.setChangedColumn(col, field.getColumnName());
		fireDataStatusChanged(evt);
		return true; // metas
	}	// setValueAt

	/**
	 * Get Old Value
	 *
	 * @param row row
	 * @param col col
	 * @return old value
	 */
	public Object getOldValue(int row, int col)
	{
		if (m_oldValue == null)
		{
			return null;
		}
		if (((Integer)m_oldValue[0]).intValue() == row
				&& ((Integer)m_oldValue[1]).intValue() == col)
		{
			return m_oldValue[2];
		}
		return null;
	}   // getOldValue

	/**
	 * Check if the current row needs to be saved.
	 *
	 * @param onlyRealChange if true the value of a field was actually changed
	 *            (e.g. for new records, which have not been changed) - default false
	 * @return true it needs to be saved
	 */
	public boolean needSave(boolean onlyRealChange)
	{
		return needSave(m_rowChanged, onlyRealChange);
	}   // needSave

	/**
	 * Check if the row needs to be saved.
	 * - only if nothing was changed
	 *
	 * @return true it needs to be saved
	 */
	public boolean needSave()
	{
		return needSave(m_rowChanged, false);
	}   // needSave

	/**
	 * Check if the row needs to be saved.
	 * - only when row changed
	 * - only if nothing was changed
	 *
	 * @param newRow to check
	 * @return true it needs to be saved
	 */
	public boolean needSave(int newRow)
	{
		return needSave(newRow, false);
	}   // needSave

	/**
	 * Check if the row needs to be saved.
	 * - only when row changed
	 * - only if nothing was changed
	 *
	 * @param newRow to check
	 * @param onlyRealChange if true the value of a field was actually changed
	 *            (e.g. for new records, which have not been changed) - default false
	 * @return true it needs to be saved
	 */
	public boolean needSave(int newRow, boolean onlyRealChange)
	{
		// nothing done
		if (!m_changed && m_rowChanged == -1)
		{
			return false;
		}
		// E.g. New unchanged records
		if (m_changed && m_rowChanged == -1 && onlyRealChange)
		{
			return false;
		}
		// same row
		if (newRow == m_rowChanged)
		{
			return false;
		}

		return true;
	}	// needSave

	/*************************************************************************/

	/** Save OK - O */
	public static final char SAVE_OK = 'O';			// the only OK condition
	/** Save Error - E */
	public static final char SAVE_ERROR = 'E';
	/** Save Access Error - A */
	public static final char SAVE_ACCESS = 'A';
	/** Save Mandatory Error - M */
	public static final char SAVE_MANDATORY = 'M';
	/** Save Abort Error - U */
	public static final char SAVE_ABORT = 'U';

	/**
	 * Check if it needs to be saved and save it.
	 *
	 * @param newRow row
	 * @param manualCmd manual command to save
	 * @return true if not needed to be saved or successful saved
	 */
	public boolean dataSave(int newRow, boolean manualCmd)
	{
		// nothing done
		if (!m_changed && m_rowChanged == -1)
		{
			return true;
		}
		// same row, don't save yet
		if (newRow == m_rowChanged)
		{
			return true;
		}

		return (dataSave(manualCmd) == SAVE_OK);
	}   // dataSave

	/**
	 * Save unconditional.
	 *
	 * @param manualCmd if true, no vetoable PropertyChange will be fired for save confirmation
	 * @return OK Or Error condition
	 *         Error info (Access*, FillMandatory, SaveErrorNotUnique,
	 *         SaveErrorRowNotFound, SaveErrorDataChanged) is saved in the log
	 */
	public char dataSave(boolean manualCmd)
	{
		// cannot save
		if (!m_open)
		{
			log.warn("Cannot save because grid table is not open");
			return SAVE_ERROR;
		}
		// no need - not changed - row not positioned - no Value changed
		if (m_rowChanged == -1)
		{
			// return SAVE_ERROR;
			if (!manualCmd)
			{
				return SAVE_OK;
			}
		}
		// Value not changed
		if (m_rowData == null)
		{
			// reset out of sync variable
			m_rowChanged = -1;
			log.debug("No Changes");
			return SAVE_ERROR;
		}

		if (m_readOnly)
		// If Processed - not editable (Find always editable) -> ok for changing payment terms, etc.
		{
			log.warn("IsReadOnly - ignored");
			dataIgnore();
			return SAVE_ACCESS;
		}

		// row not positioned - no Value changed
		if (m_rowChanged == -1)
		{
			if (m_newRow != -1)     // new row and nothing changed - might be OK
			{
				m_rowChanged = m_newRow;
			}
			else
			{
				fireDataStatusEEvent("SaveErrorNoChange", "", true);
				return SAVE_ERROR;
			}
		}

		// Can we change?
		final ImmutablePair<ClientId, OrgId> adClientAndOrgId = getClientOrg(m_rowChanged);
		final ClientId clientId = adClientAndOrgId.getLeft();
		final OrgId orgId = adClientAndOrgId.getRight();
		final IUserRolePermissions role = Env.getUserRolePermissions(m_ctx);
		if (!role.canUpdate(clientId, orgId, m_AD_Table_ID, 0, true))
		{
			fireDataStatusEEvent(MetasfreshLastError.retrieveError());
			dataIgnore();
			return SAVE_ACCESS;
		}

		// inform about data save action, if not manually initiated
		try
		{
			if (!manualCmd)
			{
				m_vetoableChangeSupport.fireVetoableChange(PROPERTY, -1, m_rowChanged);
			}
		}
		catch (PropertyVetoException pve)
		{
			log.warn(pve.getMessage());
			// [ 2696732 ] Save changes dialog's cancel button shouldn't reset status
			// https://sourceforge.net/tracker/index.php?func=detail&aid=2696732&group_id=176962&atid=879332
			// dataIgnore();
			return SAVE_ABORT;
		}

		// get updated row data
		final Object[] rowData = getDataAtRow(m_rowChanged);

		// Check Mandatory
		final String missingColumns = getMandatory(rowData);
		if (missingColumns.length() != 0)
		{
			// Trace.printStack(false, false);
			fireDataStatusEEvent("FillMandatory", missingColumns + "\n", true);
			return SAVE_MANDATORY;
		}

		//
		// Update
		try
		{
			int recordId = 0;
			if (!m_inserting)
			{
				recordId = getKeyID(m_rowChanged);
			}
			return dataSavePO(recordId);
		}
		catch (final Throwable ex)
		{
			if (ex instanceof ClassNotFoundException)
			{
				log.warn(m_tableName + " - " + ex.getLocalizedMessage());
			}
			else
			{
				log.error("Persistency Issue - " + m_tableName + ": " + ex.getLocalizedMessage(), ex);
			}
			return SAVE_ERROR;
		}
	}

	/**
	 * Save via PO
	 *
	 * @param Record_ID
	 * @return SAVE_ERROR or SAVE_OK
	 * @throws Exception
	 */
	private char dataSavePO(final int Record_ID) throws Exception
	{
		log.debug("ID={}", Record_ID);
		//
		final Object[] rowData = getDataAtRow(m_rowChanged);
		//
		final PO po;
		if (Record_ID <= 0 && isInserting()) // New record
		{
			po = TableModelLoader.instance.newPO(m_ctx, m_tableName, ITrx.TRXNAME_None);
		}
		else if (Record_ID != -1 // not multi-key
				&& Record_ID != 0) // not ID=0 => that one has to be loaded using WHERE clause
		{
			// Disable cache checking because we want fresh records on which we save
			final boolean checkCache = false;
			po = TableModelLoader.instance.getPO(m_ctx, m_tableName, Record_ID, checkCache, ITrx.TRXNAME_None);
		}
		else	// Multi - Key
		{
			final String whereClause = getWhereClause(rowData);
			po = TableModelLoader.instance.getPO(m_ctx, m_tableName, whereClause, ITrx.TRXNAME_None);
		}

		// No Persistent Object
		if (po == null)
		{
			throw new ClassNotFoundException("No Persistent Object");
		}

		po.set_ManualUserAction(m_WindowNo); // metas: tsa: 02380: mark it as manual user action

		int size = m_fields.size();
		for (int col = 0; col < size; col++)
		{
			final GridField field = m_fields.get(col);
			if (field.isVirtualColumn())
			{
				continue;
			}
			String columnName = field.getColumnName();
			Object value = rowData[col];
			Object oldValue = m_rowData[col];
			// RowID
			if (field.getDisplayType() == DisplayType.RowID)
			{
				// ignore
			}

			// Nothing changed & null
			else if (oldValue == null && value == null)
			{
				// ignore
			}

			// *** Data changed ***
			else if (m_inserting || isValueChanged(oldValue, value))
			{
				// Check existence
				int poIndex = po.get_ColumnIndex(columnName);
				if (poIndex < 0)
				{
					// Custom Fields not in PO
					po.set_CustomColumn(columnName, value);
					// log.error("Column not found: " + columnName);
					continue;
				}

				Object dbValue = po.get_Value(poIndex);
				if (m_inserting
						// Original == DB
						|| (oldValue == null && dbValue == null)
						|| (oldValue != null && oldValue.equals(dbValue))
						// Target == DB (changed by trigger to new value already)
						|| (value == null && dbValue == null)
						|| (value != null && value.equals(dbValue)))
				{
					po.set_ValueNoCheck(columnName, value);
				}
				// Original != DB
				else
				{
					String msg = columnName
							+ "= " + oldValue
							+ (oldValue == null ? "" : "(" + oldValue.getClass().getName() + ")")
							+ " != DB: " + dbValue
							+ (dbValue == null ? "" : "(" + dbValue.getClass().getName() + ")")
							+ " -> New: " + value
							+ (value == null ? "" : "(" + value.getClass().getName() + ")");
					// CLogMgt.setLevel(Level.FINEST);
					// po.dump();
					fireDataStatusEEvent("SaveErrorDataChanged", msg, true);
					dataRefresh(m_rowChanged);
					return SAVE_ERROR;
				}
			}	// Data changed

		}	// for every column

		//
		// Copy-with-details
		if (isCopyWithDetails())
		{
			throw new UnsupportedOperationException();
		}

		try
		{
			po.saveEx();
		}
		catch (final Exception saveEx)
		{
			fireDataStatusSaveErrorEvent(saveEx);
			return SAVE_ERROR;
		}

		// //
		// // Copy-with-details: reset status on GridTab level
		// if (isRecordCopyingMode() && m_gridTab != null)
		// {
		// 	m_gridTab.resetSuggestedCopyWithDetailsList();
		// }

		m_oldValue = null;

		//
		// Refresh - update buffer
		String whereClause = po.get_WhereClause(true);
		log.debug("Reading ... {}", whereClause);
		StringBuilder refreshSQL = new StringBuilder(m_SQL_Select)
				.append(" WHERE ").append(whereClause);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(refreshSQL.toString(), ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				Object[] rowDataDB = readData(rs, m_fields);
				// update buffer
				setDataAtRow(m_rowChanged, rowDataDB);
				if (m_virtual)
				{
					MSort sort = m_sort.get(m_rowChanged);
					sort.index = getKeyID(m_rowChanged);
					// metas: also include virtual values
					// if (Services.get(IVirtualColumnProvider.class) != null) {
					//
					// computeVirtualColumns(Collections.singletonList(po
					// .get_ID()), sort.index, query);
					// }
				}
				// metas end
				fireTableRowsUpdated(m_rowChanged, m_rowChanged);
			}
		}
		catch (SQLException e)
		{
			String msg = "SaveError";
			log.error(refreshSQL.toString(), e);
			fireDataStatusEEvent(msg, e.getLocalizedMessage(), true);
			return SAVE_ERROR;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// everything ok
		m_rowData = null;
		m_changed = false;
		m_rowChanged = -1;
		m_newRow = -1;
		m_inserting = false;
		//
		ValueNamePair lastWarning = MetasfreshLastError.retrieveWarning();
		if (lastWarning != null)
		{
			String msg = lastWarning.getValue();
			String info = lastWarning.getName();
			fireDataStatusEEvent(msg, info, false);
		}
		else
		{
			fireDataStatusInfoEvent("Saved", "");
		}

		return SAVE_OK;
	}	// dataSavePO

	private final void fireDataStatusSaveErrorEvent(final Exception saveEx)
	{
		//
		// Extract message from exception
		String info = saveEx.getLocalizedMessage();
		if (Check.isEmpty(info, true))
		{
			info = saveEx.toString();
		}

		//
		// Get the AD_Message to be displayed as the error title/header.
		final String errorAD_Message;
		if (DBException.isUniqueContraintError(saveEx))
		{
			errorAD_Message = "SaveErrorNotUnique";
		}
		else
		{
			errorAD_Message = "SaveError"; // generic error message
		}

		// Log it, for audit/support purposes
		log.error("Error while saving on {}\n Type: {}", this, errorAD_Message, saveEx);

		//
		// Fire it
		final boolean isError = true;
		fireDataStatusEEvent(errorAD_Message, info, isError);
	}

	/**
	 * Get Record Where Clause from data (single key or multi-parent)
	 *
	 * @param rowData data
	 * @return where clause or null
	 */
	String getWhereClause(final Object[] rowData)
	{
		return GridTableUtils.getWhereClause(rowData, m_fields);
	}


	/**
	 * Get Mandatory empty columns
	 *
	 * @param rowData row data
	 * @return String with missing column headers/labels
	 */
	private String getMandatory(Object[] rowData)
	{
		// see also => ProcessParameter.saveParameter
		StringBuilder sb = new StringBuilder();

		// Check all columns
		int size = m_fields.size();
		for (int i = 0; i < size; i++)
		{
			GridField field = m_fields.get(i);
			if (field.isMandatory(true))        // check context
			{
				if (rowData[i] == null || rowData[i].toString().length() == 0)
				{
					field.setInserting(true);  // set editable otherwise deadlock
					field.setError(true);
					if (sb.length() > 0)
					{
						sb.append(", ");
					}
					sb.append(field.getHeader());
				}
				else
				{
					field.setError(false);
				}
			}
		}

		if (sb.length() == 0)
		{
			return "";
		}
		return sb.toString();
	}	// getMandatory

	/*************************************************************************/


	/**************************************************************************
	 * New Record after current Row
	 *
	 * @return true if success -
	 *         Error info (Access*, AccessCannotInsert) is saved in the log
	 */
	public boolean dataNew(final int currentRow, final DataNewCopyMode copyMode)
	{
		// Handle copy mode:
		setDataNewCopyMode(copyMode);

		//
		final Function<GridField, Object> fieldCalculatedValueSupplier;
		if (DataNewCopyMode.isCopyWithDetails(copyMode))
		{
			throw new UnsupportedOperationException();
		}
		else
		{
			fieldCalculatedValueSupplier = gridField -> gridField.getDefault();
		}

		// Read only
		if (m_readOnly)
		{
			fireDataStatusEEvent("AccessCannotInsert", "", true);
			return false;
		}

		/** @todo No TableLevel */
		// || !Access.canViewInsert(m_ctx, m_WindowNo, tableLevel, true, true))
		// fireDataStatusEvent(Log.retrieveError());

		// see if we need to save
		dataSave(-2, false);

		m_inserting = true;

		// Setup the buffer first so that event will be handle properly
		// Create default data
		int size = m_fields.size();
		m_rowData = new Object[size];	// "original" data
		Object[] rowData = new Object[size];

		m_changed = true;
		m_newRow = currentRow + 1;
		// if there is no record, the current row could be 0 (and not -1)
		if (m_sort.size() < m_newRow)
		{
			m_newRow = m_sort.size();
		}

		// add Data at end of buffer
		MSort newSort = m_virtual
				? new MSort(-1, null)
				: new MSort(m_sort.size(), null);	// index
		if (m_virtual)
		{
			m_buffer.add(m_newRow, rowData);
			if (m_cacheStart == -1)
			{
				m_cacheStart = m_cacheEnd = m_newRow;
			}
			else if (m_cacheEnd < m_newRow)
			{
				m_cacheEnd = m_newRow;
			}
		}
		else
		{
			m_buffer.add(rowData);
		}
		// add Sort pointer
		m_sort.add(m_newRow, newSort);
		m_rowCount++;

		// fill data
		if (isRecordCopyingMode())
		{
			// start: c.ghita@metas.ro
			final GridField docTypeTargetField = getField("C_DocTypeTarget_ID");
			Object docTypeTargetOriginalValue = null;
			final boolean hasDocTypeTargetField = (docTypeTargetField != null);
			// end: c.ghita@metas.ro
			final Object[] origData = getDataAtRow(currentRow);
			for (int i = 0; i < size; i++)
			{
				final GridField field = m_fields.get(i);
				final String columnName = field.getColumnName();
				if (field.isVirtualColumn())
				{
					// skip copying virtual columns
				}
				// metas: begin: us215
				else if (field.getVO().IsCalculated)
				{
					rowData[i] = fieldCalculatedValueSupplier.apply(field);
				}
				// metas: end: us215
				else if (field.isKey()
						|| columnName.equals("AD_Client_ID")
						//
						|| columnName.startsWith("Created") || columnName.startsWith("Updated")
						|| columnName.equals("EntityType") || columnName.equals("DocumentNo")
						|| columnName.equals("Processed") || columnName.equals("IsSelfService")
						|| columnName.equals("DocAction") || columnName.equals("DocStatus")
						|| columnName.equals("Posted") || columnName.equals("IsReconciled")
						|| columnName.equals("IsApproved") // BF [ 1943682 ]
						|| columnName.equals("IsGenerated") // BF [ 1943682 ]
						|| columnName.startsWith("Ref_")
						// Order/Invoice
						|| columnName.equals("GrandTotal") || columnName.equals("TotalLines")
						|| columnName.equals("C_CashLine_ID") || columnName.equals("C_Payment_ID")
						|| columnName.equals("IsPaid") || columnName.equals("IsAllocated")
						// Bug [ 1807947 ]
						|| (columnName.equals("C_DocType_ID") && hasDocTypeTargetField)
						|| (columnName.equals("Line")))
				{
					// start: c.ghita@metas.ro if copy with details retain the tab and the old PO ID
					if (isCopyWithDetails() && field.isKey())
					{
						m_oldPO_id = (Integer)origData[i];
						m_gridTab = field.getGridTab();
					}
					else if (isCopyWithDetails() && columnName.equals("C_DocTypeTarget_ID") && hasDocTypeTargetField)
					{
						docTypeTargetOriginalValue = origData[i]; // we need this in case we have a callout that changes this
						rowData[i] = origData[i];
					}
					else
					// end: c.ghita@metas.ro
					{
						rowData[i] = field.getDefault();
					}
					field.setValue(rowData[i], m_inserting);
				}
				else
				{
					// Don't copy Value and ASI columns because in most of the cases those are unique.
					if ("Value".equals(columnName) || "M_AttributeSetInstance_ID".equals(columnName))
					{
						rowData[i] = null;
					}
					// IsActive column: copy it only if the field is displayed in window/tab.
					// Because if it's not displayed, the copied record is inactive by default and user has no way to change that.
					else if ("IsActive".equals(columnName) && !field.isDisplayed())
					{
						rowData[i] = field.getDefault();
					}
					else
					{
						rowData[i] = origData[i];
					}
				}
			}
			// start: c.ghita@metas.ro
			if (isCopyWithDetails() && hasDocTypeTargetField)
			{
				docTypeTargetField.setValue(docTypeTargetOriginalValue, m_inserting);
			}
			// end: c.ghita@metas.ro
		}
		else	// new
		{
			for (int i = 0; i < size; i++)
			{
				GridField field = m_fields.get(i);
				rowData[i] = field.getDefault();
				field.setValue(rowData[i], m_inserting);
			}
		}

		m_rowChanged = -1;  // only changed in setValueAt

		// inform
		log.debug("Current={}, New={}", currentRow, m_newRow);
		fireTableRowsInserted(m_newRow, m_newRow);
		fireDataStatusInfoEvent(isRecordCopyingMode() ? "UpdateCopied" : "Inserted", "");
		log.debug("Current={}, New={} - complete", currentRow, m_newRow);
		return true;
	}	// dataNew

	/**************************************************************************
	 * Delete Data
	 *
	 * @param row row
	 * @return true if success -
	 *         Error info (Access*, AccessNotDeleteable, DeleteErrorDependent,
	 *         DeleteError) is saved in the log
	 */
	public boolean dataDelete(int row)
	{
		if (row < 0)
		{
			return false;
		}

		// Tab R/O
		if (m_readOnly)
		{
			fireDataStatusEEvent("AccessCannotDelete", "", true);	// previleges
			return false;
		}

		// Is this record deletable?
		if (!m_deleteable)
		{
			fireDataStatusEEvent("AccessNotDeleteable", "", true);	// audit
			return false;
		}

		// Processed Column and not an Import Table
		if (m_indexProcessedColumn > 0 && !m_tableName.startsWith("I_"))
		{
			Boolean processed = (Boolean)getValueAt(row, m_indexProcessedColumn);
			if (processed != null && processed.booleanValue())
			{
				fireDataStatusEEvent("CannotDeleteTrx", "", true);
				return false;
			}
		}

		/** @todo check Access */
		// fireDataStatusEvent(Log.retrieveError());

		MSort sort = m_sort.get(row);
		Object[] rowData = getDataAtRow(row);
		//
		final PO po;
		int Record_ID = getKeyID(m_rowChanged);
		if (Record_ID != -1)
		{
			po = TableModelLoader.instance.getPO(m_ctx, getTableName(), Record_ID, ITrx.TRXNAME_None);
		}
		else	// Multi - Key
		{
			po = TableModelLoader.instance.getPO(m_ctx, getTableName(), getWhereClause(rowData), ITrx.TRXNAME_None);
		}

		// Delete via PO
		if (po != null)
		{
			po.set_ManualUserAction(m_WindowNo); // mark delete as manual user action
			Throwable deleteError = null;
			try
			{
				po.deleteEx(false); // force = false;
			}
			catch (Throwable t)
			{
				log.error("Delete", t);
				deleteError = t;
			}

			if (deleteError != null)
			{
				fireDataStatusEEvent("DeleteError", deleteError.getLocalizedMessage(), true);
				return false;
			}
		}
		else	// Delete via SQL
		{
			StringBuilder sql = new StringBuilder("DELETE FROM ");
			sql.append(m_tableName).append(" WHERE ").append(getWhereClause(rowData));
			int no = 0;
			PreparedStatement pstmt = null;
			try
			{
				pstmt = DB.prepareStatement(sql.toString(),
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, null);
				no = pstmt.executeUpdate();
			}
			catch (SQLException e)
			{
				log.error(sql.toString(), e);
				String msg = "DeleteError";
				if (DBException.isChildRecordFoundError(e))
				{
					msg = "DeleteErrorDependent";
				}
				fireDataStatusEEvent(msg, e.getLocalizedMessage(), true);
				return false;
			}
			finally
			{
				DB.close(pstmt);
				pstmt = null;

				// Fire cache reset
				CacheMgt.get().reset(getTableName(), getKeyID(row));
			}
			// Check Result
			if (no != 1)
			{
				log.error("Number of deleted rows = {}", no);
				return false;
			}
		}

		// Get Sort
		int bufferRow = m_virtual ? row - m_cacheStart : sort.index;
		// Delete row in Buffer and shifts all below up
		m_buffer.remove(bufferRow);
		m_rowCount--;

		// Delete row in Sort
		m_sort.remove(row);
		if (!m_virtual)
		{
			// Correct pointer in Sort
			for (MSort element : m_sort)
			{
				MSort ptr = element;
				if (ptr.index > bufferRow)
				{
					ptr.index--;	// move up
				}
			}
		}
		else
		{
			if (m_cacheStart == row)
			{
				if (m_cacheStart < m_cacheEnd)
				{
					m_cacheStart++;
				}
				else
				{
					m_cacheStart = m_cacheEnd = -1;
				}
			}
			else
			{
				m_cacheEnd--;
				if (m_cacheStart > m_cacheEnd)
				{
					m_cacheStart = m_cacheEnd;
				}
			}
		}

		// inform
		m_changed = false;
		m_rowChanged = -1;
		fireTableRowsDeleted(row, row);
		fireDataStatusInfoEvent("Deleted", "");
		log.debug("Row={} complete", row);
		return true;
	}	// dataDelete

	/**************************************************************************
	 * Ignore changes
	 */
	public void dataIgnore()
	{
		if (!m_inserting && !m_changed && m_rowChanged < 0)
		{
			log.debug("Nothing to ignore");
			return;
		}

		// Inserting - delete new row
		if (m_inserting)
		{
			// Get Sort
			MSort sort = m_sort.get(m_newRow);
			int bufferRow = m_virtual ? m_buffer.size() - 1 : sort.index;
			// Delete row in Buffer and shifts all below up
			m_buffer.remove(bufferRow);
			m_rowCount--;
			// Delete row in Sort
			m_sort.remove(m_newRow); // pintint to the last column, so no adjustment
			if (m_virtual)
			{
				if (m_cacheEnd == m_newRow)
				{
					m_cacheEnd--;
					if (m_cacheStart > m_cacheEnd)
					{
						m_cacheStart = m_cacheEnd;
					}
				}
			}
			//
			m_changed = false;
			m_rowData = null;
			m_rowChanged = -1;
			m_inserting = false;
			// inform
			fireTableRowsDeleted(m_newRow, m_newRow);
		}
		else
		{
			// update buffer
			if (m_rowData != null)
			{
				setDataAtRow(m_rowChanged, m_rowData);
			}
			m_changed = false;
			m_rowData = null;
			m_rowChanged = -1;
			m_inserting = false;
			// inform
			// fireTableRowsUpdated(m_rowChanged, m_rowChanged); >> messes up display?? (clearSelection)
		}
		m_newRow = -1;

		//
		// Copy-with-details: reset status on GridTab level
		resetDataNewCopyMode();

		fireDataStatusInfoEvent("Ignored", "");
	}	// dataIgnore

	/**
	 * Refresh Row - ignore changes
	 *
	 * @param row row
	 */
	public void dataRefresh(int row)
	{
		if (row < 0 || m_sort.size() == 0 || m_inserting)
		{
			return;
		}

		// MSort sort = m_sort.get(row); // not used
		Object[] rowData = getDataAtRow(row);

		// ignore
		dataIgnore();

		// Create SQL
		String where = getWhereClause(rowData);
		if (where == null || where.length() == 0)
		{
			where = "1=2";
		}
		String sql = m_SQL_Select + " WHERE " + where;
		// sort = m_sort.get(row);
		Object[] rowDataDB = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			// only one row
			if (rs.next())
			{
				rowDataDB = readData(rs, m_fields);
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			fireTableRowsUpdated(row, row);
			fireDataStatusEEvent("RefreshError", sql, true);
			return;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// update buffer
		setDataAtRow(row, rowDataDB);
		// info
		m_rowData = null;
		m_changed = false;
		m_rowChanged = -1;
		m_inserting = false;
		fireTableRowsUpdated(row, row);
		fireDataStatusInfoEvent("Refreshed", "");
	}	// dataRefresh

	/**
	 * Refresh all Rows - ignore changes
	 */
	public void dataRefreshAll()
	{
		m_inserting = false;	// should not happen
		dataIgnore();
		close(false);
		open(m_maxRows);
		// Info
		m_rowData = null;
		m_changed = false;
		m_rowChanged = -1;
		m_inserting = false;
		fireTableDataChanged();
		fireDataStatusInfoEvent("Refreshed", "");
	}	// dataRefreshAll

	/**
	 * Requery with new whereClause
	 *
	 * @param whereClause sql where clause
	 * @param onlyCurrentRows only current rows
	 * @param onlyCurrentDays how many days back
	 * @return true if success
	 */
	public boolean dataRequery(String whereClause, boolean onlyCurrentRows, int onlyCurrentDays)
	{
		// metas: begin
		resetNewRecordWhereClause(); // task 04771
		return dataRequery(whereClause, onlyCurrentRows, onlyCurrentDays, null);
	}

	public boolean dataRequery(String whereClause, boolean onlyCurrentRows, int onlyCurrentDays, final MQuery newQuery)
	{
		// metas: end
		close(false);
		m_onlyCurrentDays = onlyCurrentDays;
		setSelectWhereClause(whereClause, onlyCurrentRows, m_onlyCurrentDays, newQuery); // metas
		open(m_maxRows);
		// Info
		m_rowData = null;
		m_changed = false;
		m_rowChanged = -1;
		m_inserting = false;
		fireTableDataChanged();
		fireDataStatusInfoEvent("Refreshed", "");
		return true;
	}	// dataRequery

	/**************************************************************************
	 * Is Cell Editable.
	 * Is queried from JTable before checking VCellEditor.isCellEditable
	 *
	 * @param row the row index being queried
	 * @param col the column index being queried
	 * @return true, if editable
	 */
	@Override
	public boolean isCellEditable(int row, int col)
	{
		// Make Rows selectable
		// if (col == 0)
		// return true;

		// Check column range
		// metas: moved above because we need to access the field
		if (col < 0 && col >= m_fields.size())
		{
			return false;
		}
		final GridField field = m_fields.get(col);

		// Entire Table not editable
		if (m_readOnly
				&& !(field.getDisplayType() == DisplayType.Button && field.isAlwaysUpdateable())) // metas: allow always updateable buttons
		{
			return false;
		}
		// Key not editable
		if (col == m_indexKeyColumn)
		{
			return false;
		}
		/** @todo check link columns */

		// IsActive Column always editable if no processed exists
		if (col == m_indexActiveColumn && m_indexProcessedColumn == -1)
		{
			return true;
		}
		// Row
		if (!isRowEditable(row, field.isAlwaysUpdateable()))
		{
			return false;
		}

		// Column
		// metas: use row context
		final GridRowCtx rowCtx = new GridRowCtx(m_ctx, this, m_WindowNo, row);
		if (!field.isDisplayed(rowCtx))
		{
			return false;
		}
		return field.isEditable(rowCtx);
	}	// IsCellEditable

	/**
	 * Is Current Row Editable
	 *
	 * @param row row
	 * @return true if editable
	 */
	public boolean isRowEditable(int row)
	{
		return isRowEditable(row, false);
	}

	private boolean isRowEditable(int row, boolean isAlwaysUpdateableColumn)
	{
		// Entire Table not editable or no row
		if ((m_readOnly && !isAlwaysUpdateableColumn) || row < 0) // metas: check !isAlwaysUpdateableColumn
		{
			return false;
		}
		// If not Active - not editable
		if (m_indexActiveColumn > 0)		// && m_TabNo != Find.s_TabNo)
		{
			Object value = getValueAt(row, m_indexActiveColumn);
			if (value instanceof Boolean)
			{
				if (!((Boolean)value).booleanValue())
				{
					return false;
				}
			}
			else if ("N".equals(value))
			{
				return false;
			}
		}
		// If Processed - not editable (Find always editable)
		if (m_indexProcessedColumn > 0 // && m_TabNo != Find.s_TabNo)
				&& !isAlwaysUpdateableColumn) // metas
		{
			Object processed = getValueAt(row, m_indexProcessedColumn);
			if (processed instanceof Boolean)
			{
				if (((Boolean)processed).booleanValue())
				{
					return false;
				}
			}
			else if ("Y".equals(processed))
			{
				return false;
			}
		}
		//
		final ImmutablePair<ClientId, OrgId> co = getClientOrg(row);
		final ClientId clientId = co.getLeft();
		final OrgId orgId = co.getRight();
		if(clientId == null || orgId == null)
		{
			// usually that's the case of a row for which the underlying record was deleted by some outside BL or process.
			return false;
		}
		
		final int Record_ID = getKeyID(row);
		final IUserRolePermissions role = Env.getUserRolePermissions(m_ctx);
		try
		{
			return role.canUpdate(clientId, orgId, m_AD_Table_ID, Record_ID, false);
		}
		catch (Exception ex)
		{
			log.warn("Failed checking role access. Considering row not editable.", ex);
			return false;
		}
	}	// isRowEditable

	/**
	 * @return Client and Org for row
	 */
	private ImmutablePair<ClientId, OrgId> getClientOrg(final int row)
	{
		ClientId clientId = null;
		if (m_indexClientColumn != -1)
		{
			final Integer ii = (Integer)getValueAt(row, m_indexClientColumn);
			if (ii != null)
			{
				clientId = ClientId.ofRepoIdOrSystem(ii.intValue());
			}
		}
		
		OrgId orgId = OrgId.ANY;
		if (m_indexOrgColumn != -1)
		{
			final Integer ii = (Integer)getValueAt(row, m_indexOrgColumn);
			if (ii != null)
			{
				orgId = OrgId.ofRepoIdOrAny(ii.intValue());
			}
		}
		
		return ImmutablePair.of(clientId, orgId);
	}	// getClientOrg

	/**
	 * Set entire table as read only
	 *
	 * @param value new read only value
	 */
	public void setReadOnly(boolean value)
	{
		log.debug("ReadOnly={}", value);
		m_readOnly = value;
	}	// setReadOnly

	/**
	 * Is entire Table Read/Only
	 *
	 * @return true if read only
	 */
	public boolean isReadOnly()
	{
		return m_readOnly;
	}   // isReadOnly

	/**
	 * Is inserting
	 *
	 * @return true if inserting
	 */
	public boolean isInserting()
	{
		return m_inserting;
	}   // isInserting

	/**
	 * Can Table rows be deleted
	 *
	 * @param value new deleteable value
	 */
	public void setDeleteable(boolean value)
	{
		log.debug("Deleteable={}", value);
		m_deleteable = value;
	}	// setDeleteable



	/**************************************************************************
	 * Remove Data Status Listener
	 *
	 * @param l listener
	 */
	public synchronized void removeDataStatusListener(DataStatusListener l)
	{
		listenerList.remove(DataStatusListener.class, l);
	}	// removeDataStatusListener

	/**
	 * Add Data Status Listener
	 *
	 * @param l listener
	 */
	public synchronized void addDataStatusListener(DataStatusListener l)
	{
		listenerList.add(DataStatusListener.class, l);
	}	// addDataStatusListener

	/**
	 * Inform Listeners
	 *
	 * @param e event
	 */
	private void fireDataStatusChanged(DataStatusEvent e)
	{
		DataStatusListener[] listeners = listenerList.getListeners(DataStatusListener.class);
		for (DataStatusListener listener : listeners)
		{
			listener.dataStatusChanged(e);
		}
	}	// fireDataStatusChanged

	/**
	 * Create Data Status Event
	 *
	 * @return data status event
	 */
	private DataStatusEvent createDSE()
	{
		final boolean changed = m_changed || m_rowChanged != -1;
		final DataStatusEvent dse = DataStatusEvent.builder()
				.source(this)
				.totalRows(m_rowCount)
				.changed(changed)
				.autoSave(Env.isAutoCommit(m_ctx, m_WindowNo))
				.inserting(m_inserting)
				.build();
		dse.setAdTableId(m_AD_Table_ID);
		return dse;
	}   // createDSE

	/**
	 * Create and fire Data Status Info Event
	 *
	 * @param AD_Message message
	 * @param info additional info
	 */
	protected void fireDataStatusInfoEvent(String AD_Message, String info)
	{
		DataStatusEvent e = createDSE();
		e.setInfo(AD_Message, info, false, false);
		fireDataStatusChanged(e);
	}   // fireDataStatusEvent

	/**
	 * Create and fire Data Status Error Event
	 *
	 * @param AD_Message message
	 * @param info info
	 * @param isError error
	 */
	protected void fireDataStatusEEvent(final String AD_Message, final String info, final boolean isError)
	{
		// org.compiere.util.Trace.printStack();
		//
		DataStatusEvent e = createDSE();
		e.setInfo(AD_Message, info, isError, !isError);
		if (isError)
		{
			MetasfreshLastError.saveWarning(log, AD_Message, info);
		}
		fireDataStatusChanged(e);
	}   // fireDataStatusEvent

	/**
	 * Create and fire Data Status Event (from Error Log)
	 *
	 * @param errorLog error log info
	 */
	protected void fireDataStatusEEvent(ValueNamePair errorLog)
	{
		if (errorLog != null)
		{
			fireDataStatusEEvent(errorLog.getValue(), errorLog.getName(), true);
		}
	}   // fireDataStatusEvent

	/**************************************************************************
	 * Remove Vetoable change listener for row changes
	 *
	 * @param l listener
	 */
	public synchronized void removeVetoableChangeListener(VetoableChangeListener l)
	{
		m_vetoableChangeSupport.removeVetoableChangeListener(l);
	}   // removeVetoableChangeListener

	/**
	 * Add Vetoable change listener for row changes
	 *
	 * @param l listener
	 */
	public synchronized void addVetoableChangeListener(VetoableChangeListener l)
	{
		m_vetoableChangeSupport.addVetoableChangeListener(l);
	}   // addVetoableChangeListener

	/**
	 * Fire Vetoable change listener for row changes
	 *
	 * @param e event
	 * @throws PropertyVetoException
	 */
	protected void fireVetoableChange(PropertyChangeEvent e) throws java.beans.PropertyVetoException
	{
		m_vetoableChangeSupport.fireVetoableChange(e);
	}   // fireVetoableChange

	/**
	 * toString
	 *
	 * @return String representation
	 */
	@Override
	public String toString()
	{
		return new StringBuilder("GridTable[").append(m_tableName)
				.append(",WindowNo=").append(m_WindowNo)
				.append(",Tab=").append(m_TabNo).append("]").toString();
	}   // toString

	public int getNewRow()
	{
		return m_newRow;
	}

	/**************************************************************************
	 * ASync Loader
	 */
	class Loader extends Thread implements Serializable
	{
		/**
		 *
		 */
		private static final long serialVersionUID = -8735217685095696892L;

		// private MQuery query; // metas // not used

		/**
		 * Construct Loader
		 */
		public Loader()
		{
			super("TLoader");
		}	// Loader

		private PreparedStatement m_pstmt = null;
		private ResultSet m_rs = null;
		private Trx trx = null;

		/**
		 * Open ResultSet
		 *
		 * @param maxRows maximum number of rows or 0 for all
		 * @return number of records
		 */
		protected int open(int maxRows, final MQuery myQuery)
		{
			// Get Number of Rows
			int rows = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(m_SQL_Count, null);
				setParameter(pstmt, true);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					rows = rs.getInt(1);
				}
			}
			catch (SQLException e0)
			{
				// Zoom Query may have invalid where clause
				if (DBException.isInvalidIdentifierError(e0))
				{
					log.warn("Count - " + e0.getLocalizedMessage() + "\nSQL=" + m_SQL_Count);
				}
				else
				{
					log.error("Count SQL=" + m_SQL_Count, e0);
				}
				return 0;
			}
			finally
			{
				DB.close(rs, pstmt);
			}
			StringBuilder info = new StringBuilder("Rows=");
			info.append(rows);
			if (rows == 0)
			{
				info.append(" - ").append(m_SQL_Count);
			}
			// postgresql need trx to use cursor based resultset
			String trxName = m_virtual ? Trx.createTrxName("Loader") : null;
			trx = trxName != null ? Trx.get(trxName, true) : null;
			// open Statement (closed by Loader.close)
			try
			{
				m_pstmt = DB.prepareStatement(m_SQL, trxName);
				if (maxRows > 0 && rows > maxRows)
				{
					m_pstmt.setMaxRows(maxRows);
					info.append(" - MaxRows=").append(maxRows);
					rows = maxRows;
				}
				// ensure not all row is fectch into memory for virtual table
				if (m_virtual)
				{
					m_pstmt.setFetchSize(100);
				}
				setParameter(m_pstmt, false);
				m_rs = m_pstmt.executeQuery();
			}
			catch (SQLException e)
			{
				log.error(m_SQL, e);
				close();
				return 0;
			}
			log.debug(info.toString());
			return rows;
		}	// open

		/**
		 * Close RS and Statement
		 */
		private void close()
		{
			DB.close(m_rs, m_pstmt);
			m_rs = null;
			m_pstmt = null;
			if (trx != null)
			{
				trx.close();
				trx = null;
			}
		}	// close

		/**
		 * Fill Buffer to include Row
		 */
		@Override
		public void run()
		{
			if (m_rs == null)
			{
				return;
			}

			try
			{
				while (m_rs.next())
				{
					if (this.isInterrupted())
					{
						log.debug("Interrupted");
						close();
						return;
					}
					// Get Data
					int recordId = 0;
					Object[] rowData = null;
					if (m_virtual)
					{
						recordId = m_rs.getInt(getKeyColumnName());
					}
					else
					{
						rowData = readData(m_rs, m_fields);
					}
					// add Data
					MSort sort = m_virtual
							? new MSort(recordId, null)
							: new MSort(m_buffer.size(), null);	// index
					if (!m_virtual)
					{
						m_buffer.add(rowData);
					}
					m_sort.add(sort);

					// Statement all 1000 rows & sleep
					if (m_sort.size() % 1000 == 0)
					{
						// give the other processes a chance
						try
						{
							yield();
							sleep(10); // .01 second
						}
						catch (InterruptedException ie)
						{
							log.debug("Interrupted while sleeping");
							close();
							return;
						}
						DataStatusEvent evt = createDSE();
						evt.setLoading(m_sort.size());
						fireDataStatusChanged(evt);
					}
				}	// while(rs.next())
			}
			catch (SQLException e)
			{
				log.error("run", e);
			}
			finally
			{
				close();
			}

			// metas: compute virtual column values if there are any and if
			// there is no preceding SQL-Column value.
			if (m_indexKeyColumn > -1)
			{
				final ArrayList<Integer> recordIds = new ArrayList<>(m_rowCount);
				for (final Object[] row : m_buffer)
				{

					final Object recordId = row[m_indexKeyColumn];

					if (recordId != null && recordId instanceof Integer)
					{
						// null values can occur with yet unsaved rows
						recordIds.add((Integer)recordId);
					}
				}
				// if (Services.get(IVirtualColumnProvider.class) != null) {
				// GridTable.this.computeVirtualColumns(recordIds, -1, query);
				// }
			}
			// metas end

			fireDataStatusInfoEvent("", "");
		}	// run

		/**
		 * Set Parameter for Query.
		 * elements must be Integer, BigDecimal, String (default)
		 *
		 * @param pstmt prepared statement
		 * @param countSQL count
		 */
		private void setParameter(PreparedStatement pstmt, boolean countSQL)
		{
			// if (m_parameterSELECT.size() == 0 && m_parameterWHERE.size() == 0)
			// return;
			// try
			// {
			// int pos = 1; // position in Statement
			// // Select Clause Parameters
			// for (int i = 0; !countSQL && i < m_parameterSELECT.size(); i++)
			// {
			// Object para = m_parameterSELECT.get(i);
			// if (para != null)
			// log.debug("Select " + i + "=" + para);
			// //
			// if (para == null)
			// ;
			// else if (para instanceof Integer)
			// {
			// Integer ii = (Integer)para;
			// pstmt.setInt (pos++, ii.intValue());
			// }
			// else if (para instanceof BigDecimal)
			// pstmt.setBigDecimal (pos++, (BigDecimal)para);
			// else
			// pstmt.setString(pos++, para.toString());
			// }
			// // Where Clause Parameters
			// for (int i = 0; i < m_parameterWHERE.size(); i++)
			// {
			// Object para = m_parameterWHERE.get(i);
			// if (para != null)
			// log.debug("Where " + i + "=" + para);
			// //
			// if (para == null)
			// ;
			// else if (para instanceof Integer)
			// {
			// Integer ii = (Integer)para;
			// pstmt.setInt (pos++, ii.intValue());
			// }
			// else if (para instanceof BigDecimal)
			// pstmt.setBigDecimal (pos++, (BigDecimal)para);
			// else
			// pstmt.setString(pos++, para.toString());
			// }
			// }
			// catch (SQLException e)
			// {
			// log.error("parameter", e);
			// }
		}	// setParameter

	}	// Loader

	/**
	 * Feature Request [1707462]
	 * Enable runtime change of VFormat
	 *
	 * @param identifier field ident
	 * @param strNewFormat new mask
	 * @author fer_luck
	 */
	protected void setFieldVFormat(String identifier, String strNewFormat)
	{
		int cols = m_fields.size();
		for (int i = 0; i < cols; i++)
		{
			GridField field = m_fields.get(i);
			if (identifier.equalsIgnoreCase(field.getColumnName()))
			{
				field.setVFormat(strNewFormat);
				m_fields.set(i, field);
				break;
			}
		}
	}	// setFieldVFormat

	// verify if the current record has changed
	public boolean hasChanged(int row)
	{
		// not so aggressive (it can has still concurrency problems)
		// compare Updated, IsProcessed

		// NOTE: this method works only on tables with single primary keys
		final String keyColumnName = getKeyColumnName();
		final int recordId = getKeyID(row);
		if (!Check.isEmpty(keyColumnName, true) && recordId >= 0)
		{
			int colUpdated = findColumn("Updated");
			int colProcessed = findColumn("Processed");

			boolean hasUpdated = (colUpdated > 0);
			boolean hasProcessed = (colProcessed > 0);

			String columns = null;
			if (hasUpdated && hasProcessed)
			{
				columns = new String("Updated, Processed");
			}
			else if (hasUpdated)
			{
				columns = new String("Updated");
			}
			else if (hasProcessed)
			{
				columns = new String("Processed");
			}
			else
			{
				// no columns updated or processed to commpare
				return false;
			}

			Timestamp dbUpdated = null;
			String dbProcessedS = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			final String sql = "SELECT " + columns + " FROM " + m_tableName + " WHERE " + keyColumnName + "=?";
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				pstmt.setInt(1, recordId);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					int idx = 1;
					if (hasUpdated)
					{
						dbUpdated = rs.getTimestamp(idx++);
					}
					if (hasProcessed)
					{
						dbProcessedS = rs.getString(idx++);
					}
				}
				else
				{
					log.info("No Value for sql={}", sql);
				}
			}
			catch (SQLException e)
			{
				throw new DBException(e, sql);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}

			if (hasUpdated)
			{
				Timestamp memUpdated = null;
				memUpdated = (Timestamp)getOldValue(row, colUpdated);
				if (memUpdated == null)
				{
					memUpdated = (Timestamp)getValueAt(row, colUpdated);
				}

				if (memUpdated != null && !memUpdated.equals(dbUpdated))
				{
					return true;
				}
			}

			if (hasProcessed)
			{
				Boolean memProcessed = null;
				memProcessed = (Boolean)getOldValue(row, colProcessed);
				if (memProcessed == null)
				{
					memProcessed = (Boolean)getValueAt(row, colProcessed);
				}

				Boolean dbProcessed = Boolean.TRUE;
				if (!"Y".equals(dbProcessedS))
				{
					dbProcessed = Boolean.FALSE;
				}
				if (memProcessed != null && !memProcessed.equals(dbProcessed))
				{
					return true;
				}
			}
		}

		// @TODO: configurable aggressive - compare each column with the DB
		return false;
	}

	/**
	 * get Parent Tab No
	 *
	 * @return Tab No
	 */
	private int getParentTabNo()
	{
		int tabNo = m_TabNo;
		int currentLevel = Env.getContextAsInt(m_ctx, m_WindowNo, tabNo, GridTab.CTX_TabLevel);
		int parentLevel = currentLevel - 1;
		if (parentLevel < 0)
		{
			return tabNo;
		}
		while (parentLevel != currentLevel)
		{
			tabNo--;
			currentLevel = Env.getContextAsInt(m_ctx, m_WindowNo, tabNo, GridTab.CTX_TabLevel);
		}
		return tabNo;
	}

	// metas: begin -------------------------------------------------------------------
	/** The static default where clause */
	private String m_defaultWhereClause = "";
	private MQuery query;

	public int getWindowNo()
	{
		return m_WindowNo;
	}

	public int getTabNo()
	{
		return m_TabNo;
	}

	public boolean isWithAccessControl()
	{
		return m_withAccessControl;
	}

	public boolean isDeleteable()
	{
		return m_deleteable;
	}

	// metas-2009_0021_AP1_G140: Setter and getter methods for GridTab
	private GridTab m_gridTab = null;

	protected void setGridTab(GridTab tab)
	{
		this.m_gridTab = tab;
	}

	public GridTab getGridTab()
	{
		return this.m_gridTab;
	}

	private String createSelectSql(int oneRow)
	{
		if (m_fields.size() == 0 || m_tableName == null
				|| m_tableName.equals(""))
		{
			return "";
		}

		// Create SELECT Part
		StringBuilder select = new StringBuilder("SELECT ");
		for (int i = 0; i < m_fields.size(); i++)
		{
			if (i > 0)
			{
				select.append(",");
			}
			GridField field = m_fields.get(i);
			select.append(field.getColumnSQL(true)); // ColumnName or Virtual
			// Column
		}
		//
		select.append(" FROM ").append(m_tableName);
		m_SQL_Select = select.toString();
		m_SQL_Count = "SELECT COUNT(*) FROM " + m_tableName;
		// BF [ 2910358 ]
		// Restore the Original Value for Key Column Name based in Tab Context
		// Value
		String parentKey = Env.getContext(m_ctx, m_WindowNo, getParentTabNo(), CTX_KeyColumnName);

		if (!Check.isEmpty(parentKey, true))
		{
			String valueKey = Env.getContext(m_ctx, m_WindowNo, getParentTabNo(), parentKey);

			if (valueKey != null && valueKey.length() > 0)
			{
				Env.setContext(m_ctx, m_WindowNo, parentKey, valueKey);
			}
		}

		StringBuilder where = new StringBuilder("");
		// WHERE
		if (m_whereClause.length() > 0)
		{
			where.append(" WHERE ");
			if (m_whereClause.indexOf('@') == -1)
			{
				where.append(m_whereClause);
			}
			else // replace variables
			{
				String context = Env.parseContext(m_ctx, m_WindowNo, m_whereClause, false);
				if (context != null && context.trim().length() > 0)
				{
					where.append(context);
				}
				else
				{
					log.warn("Failed to parse where clause. whereClause={}", m_whereClause);
					where.append(" 1 = 2 ");
				}
			}
		}

		//
		if (!Check.isEmpty(m_defaultWhereClause, true))
		{
			if (m_whereClause.length() > 0)
			{
				where.append(" AND ");
			}
			else
			{
				where.append(" WHERE ");
			}
			if (m_defaultWhereClause.indexOf('@') == -1)
			{
				where.append(m_defaultWhereClause);
			}
			else // replace variables
			{
				String ctx = Env.parseContext(m_ctx, m_WindowNo, m_defaultWhereClause, false);
				if (ctx != null && ctx.trim().length() > 0)
				{
					where.append(ctx);
				}
				else
				{
					log.warn("Failed to parse where clause. defaultwhereClause={}", m_defaultWhereClause);
					where.append(" 1 = 2 ");
				}
			}
		}
		if (m_onlyCurrentRows && m_TabNo == 0)
		{
			if (where.toString().indexOf(" WHERE ") == -1)
			{
				where.append(" WHERE ");
			}
			else
			{
				where.append(" AND ");
			}
			// Show only unprocessed or the one updated within x days
			if (findColumn("Processed") >= 0)
			{
				where.append("(Processed='N' OR Updated>");
				where.append("now()-1");
				where.append(")");
			}
			else
			{
				where.append("Updated>now()-1");
			}
		}

		// RO/RW Access
		m_SQL = m_SQL_Select + where.toString();
		m_SQL_Count += where.toString();

		if (m_withAccessControl)
		{
			// boolean ro = MRole.SQL_RO; // not used
			// if (!m_readOnly)
			// ro = MRole.SQL_RW;
			final IUserRolePermissions role = Env.getUserRolePermissions(m_ctx);
			m_SQL = role.addAccessSQL(m_SQL, m_tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ);
			m_SQL_Count = role.addAccessSQL(m_SQL_Count, m_tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ);
		}

		// ORDER BY
		if (!m_orderClause.equals(""))
		{
			m_SQL += " ORDER BY " + m_orderClause;
		}
		log.debug(m_SQL_Count);
		Env.setContext(m_ctx, m_WindowNo, m_TabNo, "SQL", m_SQL);
		return m_SQL;
	} // createSelectSql

	public boolean open(final int maxRows, final int oneRow)
	{
		m_maxRows = maxRows;
		if (m_open)
		{
			log.debug("already open");
			dataRefreshAll();
			return true;
		}

		// create m_SQL and m_countSQL
		createSelectSql(oneRow);
		if (m_SQL == null || m_SQL.equals(""))
		{
			log.error("No SQL");
			return false;
		}

		// Start Loading
		m_loader = new Loader();
		m_rowCount = m_loader.open(maxRows, query);
		m_buffer = new ArrayList<>(m_rowCount + 10);
		m_sort = new ArrayList<>(m_rowCount + 10);
		if (m_rowCount > 0)
		{
			m_loader.start();
		}
		else
		{
			m_loader.close();
		}
		m_open = true;
		//
		m_changed = false;
		m_rowChanged = -1;
		m_inserting = false;
		return true;
	} // open

	private Object convertValue(Object value, int col)
	{
		if (value == null)
		{
			return null;
		}

		GridField field = getField(col);
		if (field == null)
		{
			return value;
		}

		if (DisplayType.YesNo == field.getDisplayType() && value instanceof String)
		{
			return "Y".equals(value);
		}

		return value;
	}

	// start: c.ghita@metas.ro : US1207
	public void addNewRecordWhereClause(String whereClause)
	{
		if (Check.isEmpty(whereClause, true))
		{
			return;
		}
		if (m_whereClauseNewRecords == null)
		{
			m_whereClauseNewRecords = new ArrayList<>();
		}
		if (!m_whereClauseNewRecords.contains(whereClause))
		{
			m_whereClauseNewRecords.add(whereClause);
		}
	}

	private String getNewRecordsWhereClause()
	{
		if (m_whereClauseNewRecords == null || m_whereClauseNewRecords.isEmpty())
		{
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String whereClause : m_whereClauseNewRecords)
		{
			if (sb.length() > 0)
			{
				sb.append(" OR ");
			}
			sb.append("(").append(whereClause).append(")");
		}
		sb.insert(0, "(").append(")");
		return sb.toString();
	}

	public void resetNewRecordWhereClause()
	{
		m_whereClauseNewRecords = null;
	}

	private List<String> m_whereClauseNewRecords = null;
	// end: c.ghita@metas.ro : US1207

	/**
	 *
	 * @return true if values of current row were changed
	 */
	// metas
	public boolean isChanged()
	{
		return m_changed;
	}


	private DataNewCopyMode _dataNewCopyMode = null;
	/** variable for retaining the old po'ID for copy with details */
	private int m_oldPO_id = -1;

	private void setDataNewCopyMode(final DataNewCopyMode copyMode)
	{
		Check.assumeNotNull(copyMode, "copyMode not null");
		this._dataNewCopyMode = copyMode;
	}

	public void resetDataNewCopyMode()
	{
		this._dataNewCopyMode = null;

		// // Make sure the suggested child tables to be copied list is reset
		// if (m_gridTab != null)
		// {
		// 	m_gridTab.resetSuggestedCopyWithDetailsList();
		// }
	}

	/**
	 * Checks if we are currenty copying the current row <b>with details</b>.
	 * <p>
	 * NOTE: this information will be available even after {@link #dataNew(int, DataNewCopyMode)} until the record is saved or discarded.
	 *
	 * @return true if we are currenty copying the current row <b>with details</b>
	 */
	public boolean isCopyWithDetails()
	{
		return DataNewCopyMode.isCopyWithDetails(_dataNewCopyMode);
	}

	/**
	 * @return true if we are currenty copying the current row (with or without details)
	 */
	public boolean isRecordCopyingMode()
	{
		return DataNewCopyMode.isCopy(_dataNewCopyMode);
	}
}
