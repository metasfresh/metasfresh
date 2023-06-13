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
import org.compiere.model.copy.ValueToCopy;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.Evaluator;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;

public class GeneralCopyRecordSupport implements CopyRecordSupport
{
	private static final Logger log = LogManager.getLogger(GeneralCopyRecordSupport.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);

	private static final String COLUMNNAME_Value = "Value";
	private static final String COLUMNNAME_Name = "Name";
	private static final String COLUMNNAME_IsActive = "IsActive";
	private static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
	private static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	private static final AdMessageKey MSG_CopiedOn = AdMessageKey.of("CopiedOn");

	@Nullable private String parentLinkColumnName = null;
	@Nullable private PO parentPO = null;
	@Nullable private AdWindowId adWindowId = null;

	private final ArrayList<OnRecordCopiedListener> recordCopiedListeners = new ArrayList<>();
	private final ArrayList<OnRecordCopiedListener> childRecordCopiedListeners = new ArrayList<>();

	@Override
	public final Optional<PO> copyToNew(@NonNull PO fromPO)
	{
		if (!isCopyRecord(fromPO))
		{
			return Optional.empty();
		}

		final PO toPO = TableModelLoader.instance.newPO(fromPO.get_TableName());
		toPO.setCopying(true); // needed in case the is some before/after save logic which relies on this
		PO.copyValues(fromPO, toPO, this::getValueToCopy);

		// needs to set IsActive because is not copied
		if (toPO.get_ColumnIndex(COLUMNNAME_IsActive) >= 0)
		{
			toPO.set_CustomColumn(COLUMNNAME_IsActive, fromPO.get_Value(COLUMNNAME_IsActive));
		}

		//
		toPO.setCopiedFromRecordId(fromPO.get_ID()); // need this for changelog

		// Parent link:
		final int parentId;
		if (parentLinkColumnName != null && (parentId = getParentID()) > 0)
		{
			toPO.set_CustomColumn(parentLinkColumnName, parentId);
		}

		// Notify listeners
		fireOnRecordCopied(toPO, fromPO);

		toPO.saveEx(ITrx.TRXNAME_ThreadInherited);
		toPO.setCopying(false);

		copyChildren(toPO, fromPO);

		return Optional.of(toPO);
	}

	private ValueToCopy getValueToCopy(@NonNull final PO to, @NonNull final PO from, @NonNull final String columnName)
	{
		final ValueToCopy valueToCopy = getValueToCopy_Before(to, from, columnName);
		if (valueToCopy.isSpecified())
		{
			return valueToCopy;
		}

		final POInfo poInfo = to.getPOInfo();
		if (poInfo.isCalculated(columnName))
		{
			return ValueToCopy.explicitValueToSet(computeDefaultValue(to, columnName, parentPO, adWindowId));
		}
		else if (COLUMNNAME_IsActive.equals(columnName))
		{
			return ValueToCopy.DIRECT_COPY;
		}
		else if ((COLUMNNAME_Name.equals(columnName) || COLUMNNAME_Value.equals(columnName))
				&& DisplayType.isText(poInfo.getColumnDisplayType(columnName)))
		{
			return makeUnique(to, columnName, from.get_ValueAsString(columnName));
		}
		else
		{
			return ValueToCopy.NOT_SPECIFIED;
		}
	}

	protected ValueToCopy getValueToCopy_Before(@NonNull final PO to, @NonNull final PO from, @NonNull final String columnName)
	{
		return ValueToCopy.NOT_SPECIFIED;
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
						.setAdWindowId(adWindowId)
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
		recordCopiedListeners.forEach(listener -> listener.onRecordCopied(to, from));
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

	private ValueToCopy makeUnique(@NonNull final PO to, @NonNull final String columnName, @Nullable final String value)
	{
		final POInfo poInfo = to.getPOInfo();
		final int columnIndex = poInfo.getColumnIndex(columnName);

		//
		// Only String columns are handled
		if (!DisplayType.isText(poInfo.getColumnDisplayType(columnIndex)))
		{
			return ValueToCopy.SKIP;
		}

		//
		// If the values is not set but is generated, then just let the system generating it
		if (value == null && poInfo.isUseDocSequence(columnIndex))
		{
			return ValueToCopy.SKIP;
		}

		String valueUnique = TranslatableStrings.builder()
				.append(StringUtils.nullToEmpty(value))
				.append("(")
				.appendADMessage(MSG_CopiedOn, TranslatableStrings.date(SystemTime.asZonedDateTime(), DisplayType.DateTime))
				.append(" ")
				.append(Env.getLoggedUserIdIfExists().map(userDAO::retrieveUserFullName).orElse("-"))
				.append(")")
				.build()
				.translate(Env.getADLanguageOrBaseLanguage());

		final int fieldLength = poInfo.getFieldLength(columnIndex);
		if (fieldLength > 0 && valueUnique.length() > fieldLength)
		{
			valueUnique = valueUnique.substring(0, fieldLength);
		}

		return ValueToCopy.explicitValueToSet(valueUnique);
	}

	private Iterator<Object> retrieveChildPOsForParent(final CopyRecordSupportTableInfo childInfo, final PO parentPO)
	{
		final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilder(childInfo.getTableName())
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

	@Override
	public final GeneralCopyRecordSupport setParentLink(@NonNull final PO parentPO, @NonNull final String parentLinkColumnName)
	{
		this.parentPO = parentPO;
		this.parentLinkColumnName = parentLinkColumnName;
		return this;
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
	private static Object computeDefaultValue(
			@NonNull final PO po,
			@NonNull final String columnName,
			@Nullable final PO parentPO,
			@Nullable final AdWindowId adWindowId)
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
		if (columnName.equals(COLUMNNAME_IsActive))
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
					&& (columnName.equals(COLUMNNAME_AD_Client_ID) || columnName.equals(COLUMNNAME_AD_Org_ID)))
			{
				log.debug("[SystemAccess] {}=0", columnName);
				return 0;
			}
			// Set Org to System, if Client access
			else if (accessLevel == TableAccessLevel.SystemPlusClient
					&& columnName.equals(COLUMNNAME_AD_Org_ID))
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
				return convertToPOValue(defStr, po, columnName);
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
					return convertToPOValue(defStr, po, columnName);
				}
			} // while more Tokens
		} // Default value

		//
		//(d) Preference (user) - P|
		//
		defStr = Env.getPreference(po.getCtx(), adWindowId, columnName, false);
		if (!defStr.equals(""))
		{
			log.debug("[UserPreference] {}={}", columnName, defStr);
			return convertToPOValue(defStr, po, columnName);
		}

		//
		//(e) Preference (System) - # $
		//
		defStr = Env.getPreference(po.getCtx(), adWindowId, columnName, true);
		if (!defStr.equals(""))
		{
			log.debug("[SystemPreference] {}={}", columnName, defStr);
			return convertToPOValue(defStr, po, columnName);
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
			return convertToPOValue("0", po, columnName);
		}

		if (parentPO != null)
		{
			return parentPO.get_Value(columnName);
		}

		return null;
	}

	@Nullable
	private static Object convertToPOValue(final String value, final PO po, final String columnName)
	{
		final int displayType = po.getPOInfo().getColumnDisplayType(po.get_ColumnIndex(columnName));
		return DisplayType.convertToDisplayType(value, columnName, displayType);
	}

	@Override
	public final GeneralCopyRecordSupport setAdWindowId(final @Nullable AdWindowId adWindowId)
	{
		this.adWindowId = adWindowId;
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
	public final GeneralCopyRecordSupport onRecordCopied(@NonNull final OnRecordCopiedListener listener)
	{
		if (!recordCopiedListeners.contains(listener))
		{
			recordCopiedListeners.add(listener);
		}

		return this;
	}

	@Override
	public CopyRecordSupport onChildRecordCopied(@NonNull final OnRecordCopiedListener listener)
	{
		if (!childRecordCopiedListeners.contains(listener))
		{
			childRecordCopiedListeners.add(listener);
		}

		return this;
	}
}
