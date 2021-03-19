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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.security.TableAccessLevel;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.GridField;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Table;
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

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * @author Cristina Ghita, METAS.RO
 */
public class GeneralCopyRecordSupport implements CopyRecordSupport
{
	private static final transient Logger log = LogManager.getLogger(GeneralCopyRecordSupport.class);

	public static final String COLUMNNAME_Value = "Value";
	public static final String COLUMNNAME_Name = "Name";
	public static final String COLUMNNAME_IsActive = "IsActive";

	private static final AdMessageKey MSG_CopiedOn = AdMessageKey.of("CopiedOn");

	private String _keyColumn = null;
	private PO _parentPO = null;

	private List<CopyRecordSupportTableInfo> _suggestedChildrenToCopy = ImmutableList.of();
	private int _fromPOId = -1;
	private boolean _base = false;
	@Nullable
	private AdWindowId _adWindowId = null;

	private final ArrayList<IOnRecordCopiedListener> recordCopiedListeners = new ArrayList<>();
	private final ArrayList<IOnRecordCopiedListener> childRecordCopiedListeners = new ArrayList<>();

	@Override
	public void copyRecord(final PO po, final String trxName)
	{
		final PO fromPO;
		if (getFromPO_ID() > 0)
		{
			fromPO = TableModelLoader.instance.getPO(getCtx(), po.get_TableName(), getFromPO_ID(), trxName);
		}
		else
		{
			fromPO = po;
		}

		//
		// Copy this level
		setBase(true);
		final PO toPO;
		if (getParentKeyColumn() != null && getParentID() > 0)
		{
			toPO = TableModelLoader.instance.newPO(getCtx(), fromPO.get_TableName(), trxName);
			toPO.setDynAttribute(PO.DYNATTR_CopyRecordSupport, this); // need this for getting defaultValues at copy in PO
			PO.copyValues(fromPO, toPO, true);
			// reset for avoiding copy same object twice
			toPO.setDynAttribute(PO.DYNATTR_CopyRecordSupport, null);
			setBase(false);

			// Parent link:
			toPO.set_CustomColumn(getParentKeyColumn(), getParentID());

			// needs refresh
			// not sure if this is still needed
			for (final String columnName : toPO.get_KeyColumns())
			{
				toPO.set_CustomColumn(columnName, toPO.get_Value(columnName));
			}

			// needs to set IsActive because is not copied
			if (toPO.get_ColumnIndex(COLUMNNAME_IsActive) >= 0)
			{
				toPO.set_CustomColumn(COLUMNNAME_IsActive, fromPO.get_Value(COLUMNNAME_IsActive));
			}

			// Make sure the columns which are required to be unique they have unique values.
			updateSpecialColumnsName(toPO);

			// Notify listeners
			fireOnRecordCopied(toPO, fromPO);

			//
			toPO.setDynAttribute(PO.DYNATTR_CopyRecordSupport_OldValue, fromPO.get_ID()); // need this for changelog
			toPO.saveEx(trxName);
			// setParentPO(toPO); // TODO: remove it, not needed
		}
		else
		{
			toPO = getParentPO();
		}

		//
		// Copy children
		for (final CopyRecordSupportTableInfo childTableInfo : getSuggestedChildren(fromPO, getSuggestedChildrenToCopy()))
		{
			for (final Iterator<Object> it = retrieveChildPOsForParent(childTableInfo, fromPO); it.hasNext(); )
			{
				final PO childPO = InterfaceWrapperHelper.getPO(it.next());

				final CopyRecordSupport childCRS = CopyRecordFactory.getCopyRecordSupport(childTableInfo.getTableName());
				childCRS.setParentKeyColumn(childTableInfo.getLinkColumnName());
				childCRS.setAdWindowId(getAdWindowId());
				childCRS.setParentPO(toPO);
				childRecordCopiedListeners.forEach(childCRS::addOnRecordCopiedListener);
				childRecordCopiedListeners.forEach(childCRS::addChildRecordCopiedListener);

				childCRS.copyRecord(childPO, trxName);
				log.debug("Copied {}", childPO);
			}
		}
	}

	/**
	 * Called after the record was copied, right before saving it.
	 *
	 * @param to   the copy
	 * @param from the source
	 */
	private void fireOnRecordCopied(final PO to, final PO from)
	{
		onRecordCopied(to, from);

		for (final IOnRecordCopiedListener listener : recordCopiedListeners)
		{
			listener.onRecordCopied(to, from);
		}
	}

	protected void onRecordCopied(final PO to, final PO from)
	{
		// nothing on this level
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public void updateSpecialColumnsName(final PO to)
	{
		if (!isBase())
		{
			final POInfo poInfo = to.getPOInfo();
			if (poInfo.hasColumnName(COLUMNNAME_Name) && DisplayType.isText(poInfo.getColumnDisplayType(COLUMNNAME_Name)))
			{
				makeUnique(to, COLUMNNAME_Name);
			}

			if (poInfo.hasColumnName(COLUMNNAME_Value))
			{
				makeUnique(to, COLUMNNAME_Value);
			}
		}

	}

	private boolean isBase()
	{
		return _base;
	}

	@Override
	public final GeneralCopyRecordSupport setBase(final boolean base)
	{
		_base = base;
		return this;
	}

	private static void makeUnique(final PO to, final String columnName)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = Env.getCtx();

		final Format formatter = new SimpleDateFormat("yyyyMMdd:HH:mm:ss");

		final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		final String timestampStr = formatter.format(timestamp);
		final UserId loggedUserId = Env.getLoggedUserIdIfExists(ctx).orElse(null);
		final String username = loggedUserId != null
				? Services.get(IUserDAO.class).retrieveUserFullname(loggedUserId)
				: "-";

		final String language = Env.getADLanguageOrBaseLanguage();
		final String msg = "(" + msgBL.getMsg(language, MSG_CopiedOn, new String[] { timestampStr }) + " " + username + ")";

		final String oldValue = (String)to.get_Value(columnName);
		to.set_CustomColumn(columnName, oldValue + msg);
	}

	private Iterator<Object> retrieveChildPOsForParent(final CopyRecordSupportTableInfo childInfo, final PO parentPO)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilder(childInfo.getTableName(), getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(childInfo.getLinkColumnName(), parentPO.get_ID());

		childInfo.getOrderByColumnNames().forEach(queryBuilder::orderBy);

		return queryBuilder
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.create()
				.iterate(Object.class);
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po, final List<CopyRecordSupportTableInfo> suggestedChildren)
	{
		//
		// Check if this record has a single primary key
		final POInfo poInfo = po.getPOInfo();
		final String tableName = poInfo.getTableName();
		final String keyColumnName = poInfo.getKeyColumnName();
		// if we have multiple keys return empty list because there are no children for sure...
		if (keyColumnName == null)
		{
			return ImmutableList.of();
		}
		setParentKeyColumn(keyColumnName);

		//
		// Check user suggested list (if any)
		if (suggestedChildren != null && !suggestedChildren.isEmpty())
		{
			return suggestedChildren;
		}
		else
		{
			final List<CopyRecordSupportTableInfo> listFromTables = new ArrayList<>();

			// search tables where exist the key column
			for (final I_AD_Table childTableSuggested : retrieveChildTablesForParentColumn(keyColumnName))
			{
				final ImmutableList.Builder<String> orderByColumnNames = ImmutableList.builder();
				final POInfo childPOInfo = POInfo.getPOInfo(childTableSuggested.getTableName());
				if (childPOInfo.hasColumnName("Line"))
				{
					orderByColumnNames.add("Line");
				}
				if (childPOInfo.hasColumnName("SeqNo"))
				{
					orderByColumnNames.add("SeqNo");
				}
				if (childPOInfo.getKeyColumnName() != null)
				{
					orderByColumnNames.add(childPOInfo.getKeyColumnName());
				}

				final IModelTranslationMap trlMap = InterfaceWrapperHelper.getModelTranslationMap(childTableSuggested);

				listFromTables.add(CopyRecordSupportTableInfo.builder()
						.name(trlMap.getColumnTrl(I_AD_Table.COLUMNNAME_Name, childTableSuggested.getName()))
						.tableName(childTableSuggested.getTableName())
						.linkColumnName(keyColumnName)
						.parentTableName(tableName)
						.orderByColumnNames(orderByColumnNames.build())
						.build());
			}

			return listFromTables;
		}
	}

	private static List<I_AD_Table> retrieveChildTablesForParentColumn(final String columnName)
	{
		final String whereClause = " EXISTS (SELECT 1 FROM AD_Column c WHERE c.ColumnName = ? AND c.ad_table_id = ad_table.ad_table_id  AND c.IsParent = 'Y')"
				+ " AND NOT EXISTS (SELECT 1 FROM AD_Column c WHERE c.ColumnName = ? AND c.ad_table_id = ad_table.ad_table_id AND c.ColumnName = ?)"
				+ " AND " + I_AD_Table.COLUMNNAME_IsView + " = 'N' "
				+ " AND IsActive='Y'";

		return new Query(Env.getCtx(), I_AD_Table.Table_Name, whereClause, ITrx.TRXNAME_None)
				.setOnlyActiveRecords(true)
				.setParameters(columnName, columnName, "Processed")
				.stream(I_AD_Table.class)
				.filter(table -> isCopyTable(table.getTableName()))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * verify if a table can or not be copied
	 *
	 * @return true if the table can be copied
	 */
	private static boolean isCopyTable(final String tableName)
	{
		final String upperTableName = tableName.toUpperCase();

		return !upperTableName.endsWith("_ACCT") // acct table
				&& !upperTableName.startsWith("I_") // import tables
				&& !upperTableName.endsWith("_TRL") // translation tables
				&& !upperTableName.startsWith("M_COST") // cost tables
				&& !upperTableName.startsWith("T_") // temporary tables
				&& !upperTableName.equals("M_PRODUCT_COSTING") // product costing
				&& !upperTableName.equals("M_STORAGE") // storage table
				&& !upperTableName.equals("C_BP_WITHHOLDING") // at Patrick's request, this was removed, because is not used
				&& !(upperTableName.startsWith("M_") && upperTableName.endsWith("MA"));
	}

	protected final Properties getCtx()
	{
		return Env.getCtx();
	}

	private String getParentKeyColumn()
	{
		return _keyColumn;
	}

	@Override
	public final void setParentKeyColumn(final String parentKeyColumn)
	{
		_keyColumn = parentKeyColumn;
	}

	@Override
	public void setSuggestedChildrenToCopy(final List<CopyRecordSupportTableInfo> suggestedChildrenToCopy)
	{
		this._suggestedChildrenToCopy = suggestedChildrenToCopy != null ? ImmutableList.copyOf(suggestedChildrenToCopy) : ImmutableList.of();
	}

	private List<CopyRecordSupportTableInfo> getSuggestedChildrenToCopy()
	{
		return _suggestedChildrenToCopy;
	}

	private int getFromPO_ID()
	{
		return _fromPOId;
	}

	@Override
	public final void setFromPO_ID(final int fromPOId)
	{
		_fromPOId = fromPOId;
	}

	/**
	 * metas: same method in GridField. TODO: refactoring
	 */
	@Nullable
	private static Object createDefault(final String value, final PO po, final String columnName)
	{
		// true NULL
		if (value == null || value.isEmpty())
		{
			return null;
		}
		// see also MTable.readData
		final int index = po.get_ColumnIndex(columnName);
		final int displayType = po.getPOInfo().getColumnDisplayType(index);

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
					final int ii = Integer.parseInt(value);
					if (ii < 0)
					{
						return null;
					}
					return ii;
				}
				catch (final Exception e)
				{
					log.warn("Cannot parse: " + value + " - " + e.getMessage());
				}
				return 0;
			}
			// Integer
			if (DisplayType.Integer == displayType)
			{
				return new Integer(value);
			}

			// Number
			if (DisplayType.isNumeric(displayType))
			{
				return new BigDecimal(value);
			}

			// Timestamps
			if (DisplayType.isDate(displayType))
			{
				// try timestamp format - then date format -- [ 1950305 ]
				java.util.Date date;
				try
				{
					date = DisplayType.getTimestampFormat_Default().parse(value);
				}
				catch (final java.text.ParseException e)
				{
					date = DisplayType.getDateFormat_JDBC().parse(value);
				}
				return new Timestamp(date.getTime());
			}

			// Boolean
			if (DisplayType.YesNo == displayType)
			{
				return StringUtils.toBoolean(value);
			}

			// Default
			return value;
		}
		catch (final Exception e)
		{
			log.error("Failed creating default for {}. Returning null.", columnName, e);
			return null;
		}
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
	@Nullable
	private Object getDefault(final PO po, final String columnName)
	{
		@Nullable final PO parentPO = getParentPO();
		final AdWindowId adWindowId = getAdWindowId();

		// TODO: until refactoring, keep in sync with org.compiere.model.GridField.getDefaultNoCheck()
		// Object defaultValue = null;

		//
		//(a) Key/Parent/IsActive/SystemAccess
		//

		final int index = po.get_ColumnIndex(columnName);
		final POInfo poInfo = po.getPOInfo();
		final String defaultLogic = poInfo.getDefaultLogic(index);
		final int displayType = poInfo.getColumnDisplayType(index);

		if (defaultLogic == null)
		{
			return null;
		}

		if (poInfo.isKey(index) || DisplayType.RowID == displayType
				|| DisplayType.isLOB(displayType))
		{
			return null;
		}
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

		//
		//(b) SQL Statement (for data integity & consistency)
		//
		String defStr = "";
		if (defaultLogic.startsWith("@SQL="))
		{
			String sql = defaultLogic.substring(5); // w/o tag

			final Evaluatee evaluatee = Evaluatees.composeNotNulls(po, parentPO);
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
					{
						defStr = rs.getString(1);
					}
					else
					{
						log.warn("(" + columnName + ") - no Result: " + sql);
					}
				}
				catch (final SQLException e)
				{
					throw new DBException(e, sql)
							.setParameter("columnName", columnName)
							.appendParametersToMessage();
				}
				finally
				{
					DB.close(rs, pstmt);
				}
			}
			if (defStr == null && parentPO != null && parentPO.get_ColumnIndex(columnName) >= 0)
			{
				return parentPO.get_Value(columnName);
			}
			if (defStr != null && defStr.length() > 0)
			{
				log.debug("[SQL] " + columnName + "=" + defStr);
				return createDefault(defStr, po, columnName);
			}
		} // SQL Statement

		//
		//(c) Field DefaultValue === similar code in AStartRPDialog.getDefault ===
		//
		if (!defaultLogic.equals("") && !defaultLogic.startsWith("@SQL="))
		{
			// It is one or more variables/constants
			final StringTokenizer st = new StringTokenizer(defaultLogic, ",;", false);
			while (st.hasMoreTokens())
			{
				defStr = st.nextToken().trim();
				if (defStr.equals("@SysDate@"))
				{
					return new Timestamp(System.currentTimeMillis());
				}
				else if (defStr.indexOf('@') != -1) // it is a variable
				{
					final Evaluatee evaluatee = Evaluatees.composeNotNulls(po, parentPO);
					defStr = Evaluator.parseContext(evaluatee, defStr.trim());
				}
				else if (defStr.contains("'"))
				{
					defStr = defStr.replace('\'', ' ').trim();
				}

				if (!defStr.equals(""))
				{
					log.debug("[DefaultValue] " + columnName + "=" + defStr);
					return createDefault(defStr, po, columnName);
				}
			} // while more Tokens
		} // Default value

		//
		//(d) Preference (user) - P|
		//
		defStr = Env.getPreference(po.getCtx(), adWindowId, columnName, false);
		if (!defStr.equals(""))
		{
			log.debug("[UserPreference] " + columnName + "=" + defStr);
			return createDefault(defStr, po, columnName);
		}

		//
		//(e) Preference (System) - # $
		//
		defStr = Env.getPreference(po.getCtx(), adWindowId, columnName, true);
		if (!defStr.equals(""))
		{
			log.debug("[SystemPreference] " + columnName + "=" + defStr);
			return createDefault(defStr, po, columnName);
		}

		//
		//(f) DataType defaults
		//

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

		if (parentPO != null)
		{
			return parentPO.get_Value(columnName);
		}
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

	@Nullable
	private AdWindowId getAdWindowId()
	{
		return _adWindowId;
	}

	@Override
	public final void setAdWindowId(final @Nullable AdWindowId adWindowId)
	{
		this._adWindowId = adWindowId;
	}

	@Override
	public final GeneralCopyRecordSupport setParentPO(final PO parentPO)
	{
		this._parentPO = parentPO;
		return this;
	}

	private PO getParentPO()
	{
		return _parentPO;
	}

	@Nullable
	protected final <T> T getParentModel(final Class<T> modelType)
	{
		final PO parentPO = getParentPO();
		return parentPO != null ? InterfaceWrapperHelper.create(parentPO, modelType) : null;
	}

	private int getParentID()
	{
		final PO parentPO = getParentPO();
		return parentPO != null ? parentPO.get_ID() : -1;
	}

	/**
	 * Allows other modules to install customer code to be executed each time a record was copied.
	 */
	@Override
	public final void addOnRecordCopiedListener(@NonNull final IOnRecordCopiedListener listener)
	{
		if (!recordCopiedListeners.contains(listener))
		{
			recordCopiedListeners.add(listener);
		}
	}

	@Override
	public CopyRecordSupport addChildRecordCopiedListener(@NonNull final IOnRecordCopiedListener listener)
	{
		if (!childRecordCopiedListeners.contains(listener))
		{
			childRecordCopiedListeners.add(listener);
		}

		return this;
	}
}
