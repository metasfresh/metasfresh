package org.compiere.model.copy;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.security.TableAccessLevel;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.DBException;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.StringTokenizer;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class ValueToCopy
{
	private static final Logger log = LogManager.getLogger(ValueToCopy.class);

	public static ValueToCopy DIRECT_COPY = _builder().type(ValueToCopyType.DIRECT_COPY).build();
	public static ValueToCopy COMPUTE_DEFAULT = _builder().type(ValueToCopyType.COMPUTE_DEFAULT).build();
	public static ValueToCopy APPEND_UNIQUE_SUFFIX = appendSuffix(null);
	public static ValueToCopy SKIP = _builder().type(ValueToCopyType.SKIP).build();
	public static ValueToCopy NOT_SPECIFIED = _builder().type(ValueToCopyType.NOT_SPECIFIED).build();

	public static ValueToCopy explicitValueToSet(@Nullable final Object value) {return _builder().type(ValueToCopyType.SET_EXPLICIT_VALUE).explicitValueToSet(value).build();}

	public static ValueToCopy computeFunction(@NonNull final Function<ValueToCopyResolveContext, Object> computeFunction) {return _builder().type(ValueToCopyType.COMPUTE_WITH_FUNCTION).computeFunction(computeFunction).build();}

	public static ValueToCopy appendSuffix(@Nullable final String suffix) {return _builder().type(ValueToCopyType.APPEND_SUFFIX).appendSuffix(suffix).build();}

	private static final AdMessageKey MSG_CopiedOn = AdMessageKey.of("CopiedOn");
	private static final String COLUMNNAME_IsActive = "IsActive";
	private static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
	private static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	@Getter @NonNull private final ValueToCopyType type;
	@Getter @Nullable private final Object explicitValueToSet;
	@Getter @Nullable private final String appendSuffix;
	@Getter @Nullable private final Function<ValueToCopyResolveContext, Object> computeFunction;

	@Builder(builderMethodName = "_builder")
	private ValueToCopy(
			@NonNull final ValueToCopyType type,
			@Nullable final Object explicitValueToSet,
			@Nullable final String appendSuffix,
			@Nullable final Function<ValueToCopyResolveContext, Object> computeFunction)
	{
		this.type = type;
		this.explicitValueToSet = explicitValueToSet;
		this.appendSuffix = appendSuffix;
		this.computeFunction = computeFunction;
	}

	public boolean isSpecified() {return !isNotSpecified();}

	public boolean isNotSpecified() {return ValueToCopyType.NOT_SPECIFIED.equals(type);}

	public ValueToCopyResolved resolve(ValueToCopyResolveContext context)
	{
		switch (type)
		{
			case DIRECT_COPY:
				return ValueToCopyResolved.value(context.getFromValue());
			case COMPUTE_DEFAULT:
				return ValueToCopyResolved.value(resolveComputeDefaultValue(context));
			case COMPUTE_WITH_FUNCTION:
				//noinspection DataFlowIssue
				return ValueToCopyResolved.value(computeFunction.apply(context));
			case APPEND_SUFFIX:
				return ValueToCopyResolved.value(resolveAppendSuffix(context, appendSuffix));
			case SET_EXPLICIT_VALUE:
				return ValueToCopyResolved.value(explicitValueToSet);
			case SKIP:
			case NOT_SPECIFIED:
			default:
				return ValueToCopyResolved.SKIP;
		}
	}

	/**
	 * Similar method to {@link org.compiere.model.GridField#getDefault()}, with one difference: the <code>AccessLevel</code> is only applied if the column has <code>IsCalculated='N'</code>.
	 */
	@Nullable
	private static Object resolveComputeDefaultValue(final ValueToCopyResolveContext context)
	{
		// TODO: until refactoring, keep in sync with org.compiere.model.GridField.getDefaultNoCheck()

		@NonNull final PO po = context.getTo();
		@NonNull final String columnName = context.getColumnName();
		@Nullable final PO parentPO = context.getParentPO();
		@Nullable final AdWindowId adWindowId = context.getAdWindowId();

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

	private static String resolveAppendSuffix(final ValueToCopyResolveContext context, @Nullable final String suffixToAppend)
	{
		if (suffixToAppend == null || suffixToAppend.isEmpty())
		{
			String valueUnique = TranslatableStrings.builder()
					.append(StringUtils.nullToEmpty(context.getFromValueAsString()))
					.append("(")
					.appendADMessage(MSG_CopiedOn, TranslatableStrings.date(context.getTimestamp(), DisplayType.DateTime))
					.append(" ")
					.append(context.getLoggedUserName().orElse("-"))
					.append(")")
					.build()
					.translate(Env.getADLanguageOrBaseLanguage());

			final int fieldLength = context.getToFieldLength();
			if (fieldLength > 0 && valueUnique.length() > fieldLength)
			{
				valueUnique = valueUnique.substring(0, fieldLength);
			}

			return valueUnique;
		}
		else
		{
			final String oldValue = String.valueOf(context.getFromValue());
			return oldValue.concat(suffixToAppend);
		}
	}
}
