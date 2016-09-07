package org.adempiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.security.TableAccessLevel;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridWindow;
import org.compiere.model.MTab;
import org.compiere.model.MTable;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.Evaluator;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * @author Cristina Ghita, METAS.RO
 *
 */
public class GeneralCopyRecordSupport implements CopyRecordSupport
{
	public static final String COLUMNNAME_Value = "Value";
	public static final String COLUMNNAME_Name = "Name";
	public static final String COLUMNNAME_IsActive = "IsActive";

	private String m_keyColumn = null;
	private int m_parent_ID = -1;
	private PO parentPO = null;

	private GridTab m_gTab = null;
	private int m_oldPO_ID = -1;
	private boolean m_base = false;
	private int AD_Window_ID = -1;

	private static final transient Logger log = LogManager.getLogger(GeneralCopyRecordSupport.class);

	@Override
	public void copyRecord(PO po, String trxName)
	{
		if (getFromPO_ID() > 0)
		{
			po = TableModelLoader.instance.getPO(getCtx(), po.get_TableName(), getFromPO_ID(), trxName);
		}

		m_base = true;
		if (getParentKeyColumn() != null && getParentID() > 0)
		{
			final PO to = TableModelLoader.instance.newPO(getCtx(), po.get_TableName(), trxName);
			to.setDynAttribute(PO.DYNATTR_CopyRecordSupport, this); // need this for getting defaultValues at copy in PO
			PO.copyValues(po, to, true);
			// reset for avoiding copy same object twice
			to.setDynAttribute(PO.DYNATTR_CopyRecordSupport, null);
			m_base = false;

			to.set_CustomColumn(getParentKeyColumn(), getParentID());
			// needs refresh
			for (final String columnName : to.get_KeyColumns())
			{
				to.set_CustomColumn(columnName, to.get_Value(columnName));
			}
			// needs to set IsActive because is not copied
			if (to.get_ColumnIndex(COLUMNNAME_IsActive) >= 0)
			{
				to.set_CustomColumn(COLUMNNAME_IsActive, po.get_Value(COLUMNNAME_IsActive));
			}
			//
			setSpecialColumnsName(to);
			onRecordCopied(to, po);
			//
			to.setDynAttribute(PO.DYNATTR_CopyRecordSupport_OldValue, po.get_ID()); // need this for changelog
			//
			to.saveEx(trxName);
			setParentID(to.get_ID());
			setParentPO(to);
		}

		for (final TableInfoVO childTableInfo : getSuggestedChildren(po, getGridTab()))
		{
			for (Iterator<?> it = getPOForParent(childTableInfo.tableName, po); it.hasNext();)
			{
				final PO childPO = (PO)it.next();
				CopyRecordSupport childCRS = CopyRecordFactory.getCopyRecordSupport(childTableInfo.tableName);
				childCRS.setGridTab(childTableInfo.gridTab);
				childCRS.setAD_Window_ID(getAD_Window_ID());
				childCRS.setParentID(getParentID());
				childCRS.setParentPO(getParentPO());
				childCRS.setParentKeyColumn(childTableInfo.linkColumnName);
				childCRS.copyRecord(childPO, trxName);
				log.info("Copied " + childPO);
			}
		}
	}

	/**
	 * Called after the record was copied, right before saving it. This default implementation makes sure that <b>if</b> both <code>to</code> and <code>from</code> have an ASI, then the ASI is cloned (see {@task http://dewiki908/mediawiki/index.php/08789_CopyWithDetails:_clone_ASI_instead_of_just_coyping_its_ID_%28100318477643%29}).
	 *
	 * Can be overridden by extending classes, but generally, please call this super-method.
	 *
	 * @param to the copy
	 * @param from the source
	 * @see IAttributeSetInstanceBL#cloneASI(Object, Object)
	 */
	@OverridingMethodsMustInvokeSuper
	protected void onRecordCopied(final PO to, final PO from)
	{
		for(final IOnRecordCopiedListener listener: onRecordCopiedListeners)
		{
			listener.onRecordCopied(to, from);
		}
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public void setSpecialColumnsName(PO to)
	{
		if (this.isBase() == false)
		{
			int idxName = to.get_ColumnIndex(COLUMNNAME_Name);
			if (idxName >= 0)
			{
				POInfo poInfo = POInfo.getPOInfo(to.getCtx(), to.get_Table_ID());
				if (DisplayType.isText(poInfo.getColumnDisplayType(idxName)))
					makeUnique(to, COLUMNNAME_Name);
			}
			if (to.get_ColumnIndex(COLUMNNAME_Value) >= 0)
			{
				makeUnique(to, COLUMNNAME_Value);
			}
		}

	}

	@Override
	public final boolean isBase()
	{
		return m_base;
	}

	@Override
	public final void setBase(boolean base)
	{
		m_base = base;
	}

	void makeUnique(PO to, String column)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final Format formatter = new SimpleDateFormat("yyyyMMdd:HH:mm:ss");

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String s = formatter.format(ts);
		String name = MUser.getNameOfUser(Env.getAD_User_ID(getCtx()));

		final String language = Env.getAD_Language(getCtx());
		String msg = "(" + msgBL.getMsg(language, "CopiedOn", new String[] { s }) + " " + name + ")";

		String oldValue = (String)to.get_Value(column);
		to.set_CustomColumn(column, oldValue + msg);
	}

	private Iterator<? extends PO> getPOForParent(String childTableName, PO po)
	{
		String whereClause = getParentKeyColumn() + " = ? ";
		return new Query(getCtx(), childTableName, whereClause, null)
				.setParameters(new Object[] { po.get_ID() })
				.iterate(null, false); // guaranteed = false because we are just fetching without changing
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public List<TableInfoVO> getSuggestedChildren(PO po, GridTab gt)
	{
		final List<TableInfoVO> listFromTables = new ArrayList<TableInfoVO>();

		//
		// Check user suggested list (if any)
		final List<TableInfoVO> suggestedCopyWithDetailsList = gt == null ? null : gt.getSuggestedCopyWithDetailsList();
		if (suggestedCopyWithDetailsList != null && !suggestedCopyWithDetailsList.isEmpty())
		{
			setParentKeyColumn(gt.getKeyColumnName());
			return suggestedCopyWithDetailsList;
		}

		//
		MTable table = MTable.get(getCtx(), po.get_Table_ID());
		String[] keyColumns = table.getKeyColumns();
		if (keyColumns == null || keyColumns.length > 1)
		{
			// if we have multiple keys return empty list
			return listFromTables;
		}

		// search tables where exist the key column
		setParentKeyColumn(keyColumns[0]);
		List<MTable> tableList = getTablesForColumn(getParentKeyColumn());
		for (MTable tableSuggested : tableList)
		{
			final TableInfoVO ti = new TableInfoVO();
			ti.setName(tableSuggested.get_Translation(MTable.COLUMNNAME_Name));
			ti.tableName = tableSuggested.getTableName();
			ti.linkColumnName = getParentKeyColumn();
			ti.parentTableName = table.getTableName();
			ti.parentColumnName = getParentKeyColumn();
			ti.gridTab = null;
			listFromTables.add(ti);
		}

		final Set<TableInfoVO> set = new HashSet<TableInfoVO>(listFromTables);

		GridTab currentGridTab = gt;
		if (currentGridTab != null)
		{
			GridWindow gridWindow = GridWindow.get(getCtx(), currentGridTab.getWindowNo(), currentGridTab.getAD_Window_ID());

			// search in the window, in tabs, where you find the link column
			for (int t = 0; t < gridWindow.getTabCount(); t++)
			{
				GridTab gridTab = gridWindow.getTab(t);
				if (gridTab.getAD_Tab_ID() == currentGridTab.getAD_Tab_ID())
					continue;

				int tab_id = gridTab.getAD_Tab_ID();
				MTab tab = new MTab(getCtx(), tab_id, null);
				String parentColumnName = "";
				String linkColumnName = "";
				if (gridTab != null)
				{
					parentColumnName = tab.getParent_Column() == null ? "" : tab.getParent_Column().getColumnName();
					linkColumnName = tab.getAD_Column() == null ? "" : tab.getAD_Column().getColumnName();
					if (parentColumnName == null)
						parentColumnName = "";
					if (linkColumnName == null)
						linkColumnName = "";
				}
				if (gridTab != null && (getParentKeyColumn().compareTo(linkColumnName) == 0
						|| (getParentKeyColumn().compareTo(linkColumnName) != 0
						&& getParentKeyColumn().compareTo(parentColumnName) == 0)

						)
						&& gridTab.isDisplayed()
						&& isCopyTable(gridTab.getTableName())
						&& gridTab.getField("Processed") == null
						&& !gridTab.isReadOnly()
						&& gridTab.isInsertRecord())
				{
					final TableInfoVO ti = new TableInfoVO();
					ti.setName(gridTab.getName());
					// ti.name = (MTable.get(getCtx(),
					// parent.getAD_Table_ID())).get_Translation(MTable.COLUMNNAME_Name);
					ti.tableName = gridTab.getTableName();
					ti.linkColumnName = linkColumnName;
					ti.parentTableName = table.getTableName();
					ti.parentColumnName = parentColumnName;
					ti.gridTab = gridTab;
					if (set.contains(ti))
						continue;
					set.add(ti);
				}
			}
		}
		return new ArrayList<TableInfoVO>(set);
	}

	protected final List<MTable> getTablesForColumn(String column)
	{
		final String whereClause = " EXISTS (SELECT 1 FROM AD_Column c WHERE c.columnname = ? "
				+ " AND c.ad_table_id = ad_table.ad_table_id "
				+ " AND c.IsParent = 'Y')"
				+ " AND NOT EXISTS (SELECT 1 FROM AD_Column c WHERE c.columnname = ? "
				+ " AND c.ad_table_id = ad_table.ad_table_id "
				+ " AND c.ColumnName = ?)"
				+ " AND " + MTable.COLUMNNAME_IsView + " = 'N' "
				+ " AND IsActive='Y'";

		List<MTable> tableList = new Query(getCtx(), MTable.Table_Name, whereClause, null)
				.setOnlyActiveRecords(true)
				.setParameters(new Object[] { column, column, "Processed" })
				.list();
		List<MTable> finalList = new ArrayList<MTable>();
		for (MTable t : tableList)
		{
			if (isCopyTable(t.getTableName()))
				finalList.add(t);
		}

		return finalList;
	}

	/**
	 * verify if a table can or not be copied
	 *
	 * @param tableName
	 * @return true if the table can be copied
	 */
	boolean isCopyTable(String tableName)
	{
		String upperTableName = tableName.toUpperCase();
		boolean isCopyTable = !upperTableName.endsWith("_ACCT") // acct table
				&& !upperTableName.startsWith("I_") // import tables
				&& !upperTableName.endsWith("_TRL") // translation tables
				&& !upperTableName.startsWith("M_COST") // cost tables
				&& !upperTableName.startsWith("T_") // temporary tables
				&& !upperTableName.equals("M_PRODUCT_COSTING") // product costing
				&& !upperTableName.equals("M_STORAGE") // storage table
				&& !upperTableName.equals("C_BP_WITHHOLDING") // at Patrick's request, this was removed, because is not
				// used
				&& !(upperTableName.startsWith("M_") && upperTableName.endsWith("MA"));

		return isCopyTable;
	}

	protected final Properties getCtx()
	{
		return Env.getCtx();
	}

	@Override
	public final GridTab getGridTab()
	{
		return m_gTab;
	}

	@Override
	public final int getParentID()
	{
		return m_parent_ID;
	}

	@Override
	public final String getParentKeyColumn()
	{
		return m_keyColumn;
	}

	@Override
	public final void setGridTab(GridTab gt)
	{
		m_gTab = gt;
	}

	@Override
	public final void setParentID(int parentId)
	{
		m_parent_ID = parentId;
	}

	@Override
	public final void setParentKeyColumn(String parentKeyColumn)
	{
		m_keyColumn = parentKeyColumn;
	}

	@Override
	public final int getFromPO_ID()
	{
		return m_oldPO_ID;
	}

	@Override
	public final void setFromPO_ID(int oldPOId)
	{
		m_oldPO_ID = oldPOId;
	}

	/**
	 * metas: same method in GridField. TODO: refactoring
	 *
	 * @param value
	 * @param po
	 * @param columnName
	 * @return
	 */
	private Object createDefault(final String value, final PO po, final String columnName)
	{
		// true NULL
		if (value == null || value.toString().length() == 0)
			return null;
		// see also MTable.readData
		int index = po.get_ColumnIndex(columnName);
		int displayType = po.getPOInfo().getColumnDisplayType(index);

		try
		{
			// IDs & Integer & CreatedBy/UpdatedBy
			if (columnName.endsWith("atedBy")
					|| (columnName.endsWith("_ID") && DisplayType.isID(displayType))) // teo_sarca [ 1672725 ] Process
			// parameter that ends with _ID
			// but is boolean
			{
				try
				// defaults -1 => null
				{
					int ii = Integer.parseInt(value);
					if (ii < 0)
						return null;
					return new Integer(ii);
				}
				catch (Exception e)
				{
					log.warn("Cannot parse: " + value + " - " + e.getMessage());
				}
				return new Integer(0);
			}
			// Integer
			if (DisplayType.Integer == displayType)
				return new Integer(value);

			// Number
			if (DisplayType.isNumeric(displayType))
				return new BigDecimal(value);

			// Timestamps
			if (DisplayType.isDate(displayType))
			{
				// try timestamp format - then date format -- [ 1950305 ]
				java.util.Date date = null;
				try
				{
					date = DisplayType.getTimestampFormat_Default().parse(value);
				}
				catch (java.text.ParseException e)
				{
					date = DisplayType.getDateFormat_JDBC().parse(value);
				}
				return new Timestamp(date.getTime());
			}

			// Boolean
			if (DisplayType.YesNo == displayType)
				return Boolean.valueOf("Y".equals(value));

			// Default
			return value;
		}
		catch (Exception e)
		{
			log.error(columnName + " - " + e.getMessage());
		}
		return null;
	}

	/**
	 * Similar method to {@link org.compiere.model.GridField#getDefault()}, with one difference: the <code>AccessLevel</code> is only applied if the column has <code>IsCalculated='N'</code>.
	 *
	 * <pre>
	 * 	(a) Key/Parent/IsActive/SystemAccess
	 *      (b) SQL Default
	 * 	(c) Column Default		//	system integrity
	 *      (d) User Preference
	 * 	(e) System Preference
	 * 	(f) DataType Defaults
	 *
	 *  Don't default from Context => use explicit defaultValue
	 *  (would otherwise copy previous record)
	 * </pre>
	 *
	 * @return default value or null
	 */
	protected Object getDefault(final PO po, final String columnName)
	{
		// TODO: until refactoring, keep in sync with org.compiere.model.GridField.getDefaultNoCheck()
		// Object defaultValue = null;

		/**
		 * (a) Key/Parent/IsActive/SystemAccess
		 */

		final int index = po.get_ColumnIndex(columnName);
		final POInfo poInfo = po.getPOInfo();
		final String defaultLogic = poInfo.getDefaultLogic(index);
		final int displayType = poInfo.getColumnDisplayType(index);

		if (defaultLogic == null)
			return null;

		if (poInfo.isKey(index) || DisplayType.RowID == displayType
				|| DisplayType.isLOB(displayType))
			return null;
		// Always Active
		if (columnName.equals("IsActive"))
		{
			log.debug("[IsActive] " + columnName + "=Y");
			return "Y";
		}

		// TODO: NOTE!! This is out of sync with org.compiere.model.GridField.getDefaultNoCheck()
		//
		// 07896: If PO column is considered calculated for AD_Org and AD_Client, consider using the System
		// Otherwise, treat them like any other column
		//
		if (poInfo.isCalculated(index))
		{
			// Set Client & Org to System, if System access
			final TableAccessLevel accessLevel = poInfo.getAccessLevel();
			if (accessLevel.isSystemOnly()
					&& (columnName.equals("AD_Client_ID") || columnName.equals("AD_Org_ID")))
			{
				log.debug("[SystemAccess] {}=0", columnName);
				return 0;
			}
			// Set Org to System, if Client access
			else if (accessLevel == TableAccessLevel.SystemPlusClient
					&& columnName.equals("AD_Org_ID"))
			{
				log.debug("[ClientAccess] {}=0", columnName);
				return 0;
			}
		}

		/**
		 * (b) SQL Statement (for data integity & consistency)
		 */
		String defStr = "";
		if (defaultLogic.startsWith("@SQL="))
		{
			String sql = defaultLogic.substring(5); // w/o tag

			final Evaluatee evaluatee = Evaluatees.composeNotNulls(po, getParentPO());
			sql = Evaluator.parseContext(evaluatee, sql);
			if (sql.equals(""))
			{
				log.warn("(" + columnName + ") - Default SQL variable parse failed: " + defaultLogic);
			}
			else
			{
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try
				{
					pstmt = DB.prepareStatement(sql, po.get_TrxName());
					rs = pstmt.executeQuery();
					if (rs.next())
						defStr = rs.getString(1);
					else
						log.warn("(" + columnName + ") - no Result: " + sql);
				}
				catch (SQLException e)
				{
					log.warn("(" + columnName + ") " + sql, e);
				}
				finally
				{
					DB.close(rs, pstmt);
					rs = null;
					pstmt = null;
				}
			}
			if (defStr == null && getParentPO() != null && getParentPO().get_ColumnIndex(columnName) >= 0)
			{
				return getParentPO().get_Value(columnName);
			}
			if (defStr != null && defStr.length() > 0)
			{
				log.debug("[SQL] " + columnName + "=" + defStr);
				return createDefault(defStr, po, columnName);
			}
		} // SQL Statement

		/**
		 * (c) Field DefaultValue === similar code in AStartRPDialog.getDefault ===
		 */
		if (!defaultLogic.equals("") && !defaultLogic.startsWith("@SQL="))
		{
			defStr = ""; // problem is with texts like 'sss;sss'
			// It is one or more variables/constants
			StringTokenizer st = new StringTokenizer(defaultLogic, ",;", false);
			while (st.hasMoreTokens())
			{
				defStr = st.nextToken().trim();
				if (defStr.equals("@SysDate@")) // System Time
					return new Timestamp(System.currentTimeMillis());
				else if (defStr.indexOf('@') != -1) // it is a variable
				{
					final Evaluatee evaluatee = Evaluatees.composeNotNulls(po, getParentPO());
					defStr = Evaluator.parseContext(evaluatee, defStr.trim());
				}
				else if (defStr.indexOf("'") != -1) // it is a 'String'
					defStr = defStr.replace('\'', ' ').trim();

				if (!defStr.equals(""))
				{
					log.debug("[DefaultValue] " + columnName + "=" + defStr);
					return createDefault(defStr, po, columnName);
				}
			} // while more Tokens
		} // Default value

		/**
		 * (d) Preference (user) - P|
		 */
		defStr = Env.getPreference(po.getCtx(), AD_Window_ID, columnName, false);
		if (!defStr.equals(""))
		{
			log.debug("[UserPreference] " + columnName + "=" + defStr);
			return createDefault(defStr, po, columnName);
		}

		/**
		 * (e) Preference (System) - # $
		 */
		defStr = Env.getPreference(po.getCtx(), AD_Window_ID, columnName, true);
		if (!defStr.equals(""))
		{
			log.debug("[SystemPreference] " + columnName + "=" + defStr);
			return createDefault(defStr, po, columnName);
		}

		/**
		 * (f) DataType defaults
		 */

		// Button to N
		if (DisplayType.Button == displayType && !columnName.endsWith("_ID"))
		{
			log.debug("[Button=N] " + columnName);
			return "N";
		}
		// CheckBoxes default to No
		if (displayType == DisplayType.YesNo)
		{
			log.debug("[YesNo=N] " + columnName);
			return "N";
		}
		// IDs remain null
		if (columnName.endsWith("_ID"))
		{
			log.debug("[ID=null] " + columnName);
			return null;
		}
		// actual Numbers default to zero
		if (DisplayType.isNumeric(displayType))
		{
			log.debug("[Number=0] " + columnName);
			return createDefault("0", po, columnName);
		}

		if (getParentPO() != null)
			return getParentPO().get_Value(columnName);
		return null;
	}

	@Override
	public Object getValueToCopy(final PO to, final PO from, final String columnName)
	{
		return getDefault(to, columnName);
	}

	@Override
	public Object getValueToCopy(final GridField gridField)
	{
		return gridField.getDefault();
	}

	@Override
	public final int getAD_Window_ID()
	{
		return AD_Window_ID;
	}

	@Override
	public final void setAD_Window_ID(int aDWindowID)
	{
		AD_Window_ID = aDWindowID;
	}

	@Override
	public final PO getParentPO()
	{
		return parentPO;
	}

	@Override
	public final void setParentPO(PO parentPO)
	{
		this.parentPO = parentPO;
	}

	private final List<IOnRecordCopiedListener> onRecordCopiedListeners = new ArrayList<>();

	/**
	 * Allows other modules to install customer code to be executed each time a record was copied.
	 *
	 * @param listener
	 */
	@Override
	public void addOnRecordCopiedListener(IOnRecordCopiedListener listener)
	{
		onRecordCopiedListeners.add(listener);
	}
}
