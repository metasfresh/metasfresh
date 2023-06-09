package de.metas.copy_with_details;

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
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.security.TableAccessLevel;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author Cristina Ghita, METAS.RO
 */
public class GeneralCopyRecordSupport implements CopyRecordSupport
{
	private static final Logger log = LogManager.getLogger(GeneralCopyRecordSupport.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);

	private static final String COLUMNNAME_Value = "Value";
	private static final String COLUMNNAME_Name = "Name";
	private static final String COLUMNNAME_IsActive = "IsActive";

	private static final AdMessageKey MSG_CopiedOn = AdMessageKey.of("CopiedOn");

	private String parentLinkColumnName = null;
	private PO parentPO = null;

	@Nullable
	private AdWindowId _adWindowId = null;

	private final ArrayList<IOnRecordCopiedListener> recordCopiedListeners = new ArrayList<>();
	private final ArrayList<IOnRecordCopiedListener> childRecordCopiedListeners = new ArrayList<>();

	@Override
	public final Optional<PO> copyToNew(@NonNull PO fromPO)
	{
		if (!isCopyRecord(fromPO))
		{
			return Optional.empty();
		}

		final PO toPO = TableModelLoader.instance.newPO(fromPO.get_TableName());
		toPO.setCopying(true); // needed in case the is some before/after save logic which relies on this
		PO.copyValues(
				fromPO,
				toPO,
				true,
				this::getCalculatedColumnValueToCopy);

		// needs refresh
		// NOTE: not sure if this is still needed
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
		toPO.setCopiedFromRecordId(fromPO.get_ID()); // need this for changelog

		// Parent link:
		final int parentId;
		if (parentLinkColumnName != null && (parentId = getParentID()) > 0)
		{
			toPO.set_CustomColumn(parentLinkColumnName, parentId);
		}

		toPO.saveEx(ITrx.TRXNAME_ThreadInherited);
		toPO.setCopying(false);

		copyChildren(toPO, fromPO);

		return Optional.of(toPO);
	}

	@Override
	public final void copyChildren(@NonNull final PO toPO, @NonNull final PO fromPO)
	{
		if (!isCopyRecord(fromPO))
		{
			return;
		}

		for (final CopyRecordSupportTableInfo childTableInfo : getSuggestedChildren(fromPO))
		{
			for (final Iterator<Object> it = retrieveChildPOsForParent(childTableInfo, fromPO); it.hasNext(); )
			{
				final PO childPO = InterfaceWrapperHelper.getPO(it.next());

				CopyRecordFactory.getCopyRecordSupport(childTableInfo.getTableName())
						.setParentLink(toPO, childTableInfo.getLinkColumnName())
						.setAdWindowId(getAdWindowId())
						.onRecordCopied(childRecordCopiedListeners)
						.oChildRecordCopied(childRecordCopiedListeners)
						.copyToNew(childPO);
				log.debug("Copied {}", childPO);
			}
		}

		fireOnRecordAndChildrenCopied(toPO, fromPO);
	}

	/**
	 * Called after the record was copied, right before saving it (and before it's children are copied).
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

	private void fireOnRecordAndChildrenCopied(final PO to, final PO from)
	{
		onRecordAndChildrenCopied(to, from);
	}

	protected void onRecordAndChildrenCopied(final PO to, final PO from)
	{
		// nothing on this level
	}

	/**
	 * Updates given <code>po</code> and sets the right values for columns that needs special handling.
	 * <p>
	 * e.g. this method makes sure columns like Name or Value have a unique value (to not trigger the unique index).
	 */
	private void updateSpecialColumnsName(final PO po)
	{
		final POInfo poInfo = po.getPOInfo();
		if (poInfo.hasColumnName(COLUMNNAME_Name) && DisplayType.isText(poInfo.getColumnDisplayType(COLUMNNAME_Name)))
		{
			makeUnique(po, COLUMNNAME_Name);
		}

		if (poInfo.hasColumnName(COLUMNNAME_Value))
		{
			makeUnique(po, COLUMNNAME_Value);
		}
	}

	private void makeUnique(final PO to, final String columnName)
	{
		final String oldValue = (String)to.get_Value(columnName);

		//
		// If the values is not set but is generated, then just let the system generating it
		final POInfo poInfo = to.getPOInfo();
		if (oldValue == null && poInfo.isUseDocSequence(poInfo.getColumnIndex(columnName)))
		{
			return;
		}

		final String newValue = TranslatableStrings.builder()
				.append(StringUtils.nullToEmpty(oldValue))
				.append("(")
				.appendADMessage(MSG_CopiedOn, TranslatableStrings.date(SystemTime.asZonedDateTime(), DisplayType.DateTime))
				.append(" ")
				.append(Env.getLoggedUserIdIfExists().map(userDAO::retrieveUserFullName).orElse("-"))
				.append(")")
				.build()
				.translate(Env.getADLanguageOrBaseLanguage());

		to.set_CustomColumn(columnName, newValue);
	}

	private Iterator<Object> retrieveChildPOsForParent(final CopyRecordSupportTableInfo childInfo, final PO parentPO)
	{
		final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilder(childInfo.getTableName(), getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(childInfo.getLinkColumnName(), parentPO.get_ID());

		childInfo.getOrderByColumnNames().forEach(queryBuilder::orderBy);

		return queryBuilder
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.create()
				.iterate(Object.class);
	}

	protected List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po)
	{
		return getSuggestedChildren(po.getPOInfo(), null);
	}

	protected final List<CopyRecordSupportTableInfo> getSuggestedChildren(
			@NonNull final POInfo poInfo,
			@Nullable final Set<String> onlyChildTableNames)
	{
		//
		// Check if this record has a single primary key
		final String tableName = poInfo.getTableName();
		final String keyColumnName = poInfo.getKeyColumnName();
		// if we have multiple keys return empty list because there are no children for sure...
		if (keyColumnName == null)
		{
			return ImmutableList.of();
		}

		final Set<String> childTableNames = onlyChildTableNames != null && !onlyChildTableNames.isEmpty()
				? onlyChildTableNames.stream().filter(this::isCopyTable).collect(ImmutableSet.toImmutableSet())
				: retrieveChildTablesForParentColumn(keyColumnName);

		final ArrayList<CopyRecordSupportTableInfo> result = new ArrayList<>();
		for (final String childTableName : childTableNames)
		{
			final POInfo childPOInfo = POInfo.getPOInfoNotNull(childTableName);

			if (!childPOInfo.hasColumnName(keyColumnName))
			{
				continue;
			}

			final ImmutableList.Builder<String> orderByColumnNames = ImmutableList.builder();
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

			result.add(CopyRecordSupportTableInfo.builder()
					.tableName(childTableName)
					.linkColumnName(keyColumnName)
					.parentTableName(tableName)
					.orderByColumnNames(orderByColumnNames.build())
					.build());
		}

		return result;
	}

	private ImmutableSet<String> retrieveChildTablesForParentColumn(final String columnName)
	{
		return POInfo.getPOInfoMap().stream()
				.filter(poInfo -> !poInfo.isView() && poInfo.isParentLinkColumn(columnName) && !poInfo.hasColumnName("Processed"))
				.map(POInfo::getTableName)
				.filter(this::isCopyTable)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * @return true if the table can be copied
	 */
	@OverridingMethodsMustInvokeSuper
	protected boolean isCopyTable(final String tableName)
	{
		final String tableNameUC = tableName.toUpperCase();

		return !tableNameUC.endsWith("_ACCT") // acct table
				&& !tableNameUC.startsWith("I_") // import tables
				&& !tableNameUC.endsWith("_TRL") // translation tables
				&& !tableNameUC.startsWith("M_COST") // cost tables
				&& !tableNameUC.startsWith("T_") // temporary tables
				&& !tableNameUC.equals("M_PRODUCT_COSTING") // product costing
				&& !tableNameUC.equals("M_STORAGE") // storage table
				&& !tableNameUC.equals("C_BP_WITHHOLDING") // at Patrick's request, this was removed, because is not used
				&& !(tableNameUC.startsWith("M_")
				&& tableNameUC.endsWith("MA"));
	}

	/**
	 * @return true if the record shall be copied
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	protected boolean isCopyRecord(final PO fromPO)
	{
		return true;
	}

	private Properties getCtx() {return Env.getCtx();}

	@Override
	public final GeneralCopyRecordSupport setParentLink(@NonNull final PO parentPO, @NonNull final String parentLinkColumnName)
	{
		this.parentPO = parentPO;
		this.parentLinkColumnName = parentLinkColumnName;
		return this;
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
			if (columnName.equals("CreatedBy")
					|| columnName.equals("UpdatedBy")
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
			log.debug("[IsActive] {}=Y", columnName);
			return StringUtils.ofBoolean(true);
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
			if (sql == null || Check.isBlank(sql))
			{
				log.warn("({}) - Default SQL variable parse failed: {}", columnName, defaultLogic);
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
						log.warn("({}) - no Result: {}", columnName, sql);
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
				log.debug("[SQL] {}={}", columnName, defStr);
				return createDefault(defStr, po, columnName);
			}
		} // SQL Statement

		//
		//(c) Field DefaultValue === similar code in AStartRPDialog.getDefault ===
		//
		if (!Check.isBlank(defaultLogic) && !defaultLogic.startsWith("@SQL="))
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
					log.debug("[DefaultValue] {}={}", columnName, defStr);
					return createDefault(defStr, po, columnName);
				}
			} // while more Tokens
		} // Default value

		//
		//(d) Preference (user) - P|
		//
		defStr = Env.getPreference(po.getCtx(), getAdWindowId(), columnName, false);
		if (!defStr.equals(""))
		{
			log.debug("[UserPreference] {}={}", columnName, defStr);
			return createDefault(defStr, po, columnName);
		}

		//
		//(e) Preference (System) - # $
		//
		defStr = Env.getPreference(po.getCtx(), getAdWindowId(), columnName, true);
		if (!defStr.equals(""))
		{
			log.debug("[SystemPreference] {}={}", columnName, defStr);
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

	protected Object getCalculatedColumnValueToCopy(final PO to, final PO from, final String columnName)
	{
		return getDefault(to, columnName);
	}

	@Nullable
	private AdWindowId getAdWindowId()
	{
		return _adWindowId;
	}

	@Override
	public final GeneralCopyRecordSupport setAdWindowId(final @Nullable AdWindowId adWindowId)
	{
		this._adWindowId = adWindowId;
		return this;
	}

	@SuppressWarnings("SameParameterValue")
	@Nullable
	protected final <T> T getParentModel(final Class<T> modelType)
	{
		return parentPO != null ? InterfaceWrapperHelper.create(parentPO, modelType) : null;
	}

	private int getParentID()
	{
		return parentPO != null ? parentPO.get_ID() : -1;
	}

	/**
	 * Allows other modules to install customer code to be executed each time a record was copied.
	 */
	@Override
	public final GeneralCopyRecordSupport onRecordCopied(@NonNull final IOnRecordCopiedListener listener)
	{
		if (!recordCopiedListeners.contains(listener))
		{
			recordCopiedListeners.add(listener);
		}

		return this;
	}

	@Override
	public CopyRecordSupport onChildRecordCopied(@NonNull final IOnRecordCopiedListener listener)
	{
		if (!childRecordCopiedListeners.contains(listener))
		{
			childRecordCopiedListeners.add(listener);
		}

		return this;
	}
}
