package org.compiere.model;

<<<<<<< HEAD
import de.metas.adempiere.service.IColumnBL;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.TableRefInfo;
import org.compiere.model.copy.ColumnCloningStrategy;
=======
import de.metas.ad_reference.ReferenceId;
import de.metas.adempiere.service.IColumnBL;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.compiere.model.copy.ColumnCloningStrategy;
import org.compiere.util.DisplayType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Properties;

/**
 * PO Info Column Info Value Object
 *
 * @author Jorg Janke
 * @version $Id: POInfoColumn.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public final class POInfoColumn implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1667303121090497293L;

<<<<<<< HEAD
	private static final transient Logger logger = LogManager.getLogger(POInfoColumn.class);

	public POInfoColumn(
			final int ad_Column_ID,
=======
	private static final Logger logger = LogManager.getLogger(POInfoColumn.class);

	public POInfoColumn(
			@NonNull final AdColumnId AD_Column_ID,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			final String tableName,
			final String columnName,
			final String columnSQL,
			final int displayTypeParam,
			final boolean isMandatory,
			final boolean isUpdateable,
			final String defaultLogic,
			final String columnLabel,
			final String columnDescription,
			final boolean isKey,
			final boolean isParent,
<<<<<<< HEAD
			final int ad_Reference_Value_ID,
			final int AD_Val_Rule_ID,
=======
			@Nullable final ReferenceId ad_Reference_Value_ID,
			@Nullable final TableName AD_Reference_Value_TableName,
			final int ad_Reference_Value_KeyColumn_DisplayType,
			@Nullable final AdValRuleId AD_Val_Rule_ID,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			final int fieldLength,
			final String valueMin,
			final String valueMax,
			final boolean isTranslated,
			final boolean isEncrypted,
			final boolean isAllowLogging,
			final boolean isRestAPICustomColumn,
<<<<<<< HEAD
			@NonNull final ColumnCloningStrategy cloningStrategy)
	{
		AD_Column_ID = ad_Column_ID;
		ColumnName = columnName;
		TableName = tableName;
=======
			@NonNull final ColumnCloningStrategy cloningStrategy,
			final boolean isIdentifier)
	{
		this.AD_Column_ID = AD_Column_ID;
		ColumnName = columnName;
		this.tableName = tableName;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		this.virtualColumn = !Check.isEmpty(columnSQL, false); // trimWhitespaces=false to preserve back compatibility
		if (virtualColumn)
		{
			this.ColumnSQL = columnSQL;
			this.sqlColumnForSelect = ColumnSQL + " AS " + ColumnName;
		}
		else
		{
			this.ColumnSQL = null;
			this.sqlColumnForSelect = ColumnName;
		}

<<<<<<< HEAD
		if (isString(tableName, columnName, displayTypeParam, ad_Reference_Value_ID))
		{
			this.DisplayType = org.compiere.util.DisplayType.String;
=======
		if (isString(tableName, columnName, displayTypeParam, ad_Reference_Value_ID, ad_Reference_Value_KeyColumn_DisplayType))
		{
			this.displayType = org.compiere.util.DisplayType.String;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			this.ColumnClass = String.class;
		}
		else if (columnName.equals("Posted") || columnName.equals("Processed") || columnName.equals("Processing"))
		{
<<<<<<< HEAD
			this.DisplayType = displayTypeParam;
			this.ColumnClass = Boolean.class;
		}
		else if (Services.get(IColumnBL.class).isRecordIdColumnName(columnName))
		{
			this.DisplayType = org.compiere.util.DisplayType.ID;
=======
			this.displayType = displayTypeParam;
			this.ColumnClass = Boolean.class;
		}
		else if (IColumnBL.isRecordIdColumnName(columnName))
		{
			this.displayType = org.compiere.util.DisplayType.ID;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			this.ColumnClass = Integer.class;
		}
		else
		{
<<<<<<< HEAD
			this.DisplayType = displayTypeParam;
=======
			this.displayType = displayTypeParam;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			this.ColumnClass = org.compiere.util.DisplayType.getClass(displayTypeParam, true);
		}

		IsMandatory = isMandatory;
		IsUpdateable = isUpdateable;
		DefaultLogic = defaultLogic;
		//ColumnLabel = columnLabel;
		//ColumnDescription = columnDescription;
		IsKey = isKey;
		IsParent = isParent;
		//
		AD_Reference_Value_ID = ad_Reference_Value_ID;
		// ValidationCode = validationCode;
<<<<<<< HEAD
		this.AD_Val_Rule_ID = AD_Val_Rule_ID <= 0 ? -1 : AD_Val_Rule_ID;
		//
		FieldLength = fieldLength;
		ValueMin = valueMin;
		ValueMin_BD = toBigDecimalOrNull(ValueMin, "ValueMin");
		ValueMax = valueMax;
		ValueMax_BD = toBigDecimalOrNull(ValueMax, "ValueMax");
		IsTranslated = isTranslated;
		IsEncrypted = isEncrypted;
		IsAllowLogging = isAllowLogging;
		IsRestAPICustomColumn = isRestAPICustomColumn;
		this.cloningStrategy = cloningStrategy;
=======
		this.AD_Val_Rule_ID = AD_Val_Rule_ID;
		//
		FieldLength = fieldLength;
		ValueMin = StringUtils.trimBlankToNull(valueMin);
		ValueMin_BD = toBigDecimalOrNull(this.ValueMin, "ValueMin");
		ValueMax = StringUtils.trimBlankToNull(valueMax);
		ValueMax_BD = toBigDecimalOrNull(this.ValueMax, "ValueMax");
		IsTranslated = isTranslated;
		IsEncrypted = isEncrypted;
		IsAllowLogging = isAllowLogging;
		AD_Reference_Value_KeyColumn_DisplayType = ad_Reference_Value_KeyColumn_DisplayType;
		IsRestAPICustomColumn = isRestAPICustomColumn;
		IsIdentifier = isIdentifier;
		this.cloningStrategy = cloningStrategy;

		this._referencedTableName = computeReferencedTableName(this.displayType, AD_Reference_Value_TableName);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}   // Column

	private static boolean isString(
			final String tableName,
			final String columnName,
			final int displayType,
<<<<<<< HEAD
			final int ad_Reference_Value_ID)
=======
			@Nullable final ReferenceId ad_Reference_Value_ID,
			final int ad_Reference_Value_KeyColumn_DisplayType)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (org.compiere.util.DisplayType.String == displayType)
		{
			return true;
		}

		// FIXME: HARDCODED
		if (columnName.equals("AD_Language")
				|| columnName.equals("EntityType"))
		{
			return true;
		}

		// task #500: Also allow type String for non-numeric types with a reference value (Table and search)
<<<<<<< HEAD
		if (ad_Reference_Value_ID > 0
				&& (isTableDisplayType(displayType) || isSearchDisplayType(displayType)))
		{
			// The method org.adempiere.ad.service.ILookupDAO.retrieveTableRefInfo(int) logs warnings when the {@link ITableRefInfo} was not found.
			// Permit warnings here because we shouldn't have Table or Search types with no reference table IDs.
			final ILookupDAO lookupDAO = Services.get(ILookupDAO.class);
			final ExplainedOptional<TableRefInfo> tableRefInfo = lookupDAO.getTableRefInfo(ad_Reference_Value_ID);
			if (!tableRefInfo.isPresent())
			{
				// NOTE: avoid log WARN or ERROR because that one might trigger ErrorManager which might call POInfo => recursion
				System.err.println("Failed retrieving reference info for " + tableName + "." + columnName + " because " + tableRefInfo.getExplanation().getDefaultValue()
						+ ". Considering not a string reference.");
				return false;
			}

			return !tableRefInfo.get().isNumericKey();
		}

		return false;
=======
		if (ad_Reference_Value_ID != null
				&& (isTableDisplayType(displayType) || isSearchDisplayType(displayType)))
		{
			final boolean isNumericKey = org.compiere.util.DisplayType.isID(ad_Reference_Value_KeyColumn_DisplayType);
			return !isNumericKey;
		}

				return false;
			}

	public boolean isString()
	{
		return isString(tableName, ColumnName, displayType, AD_Reference_Value_ID, AD_Reference_Value_KeyColumn_DisplayType);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private static boolean isSearchDisplayType(final int displayType)
	{
		return org.compiere.util.DisplayType.Search == displayType;
	}

	private static boolean isTableDisplayType(final int displayType)
	{
		return org.compiere.util.DisplayType.Table == displayType;
	}

	/**
	 * Column ID
	 */
<<<<<<< HEAD
	final int AD_Column_ID;
=======
	final AdColumnId AD_Column_ID;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	/**
	 * Column Name
	 */
	private final String ColumnName;

<<<<<<< HEAD
	private final String TableName;
=======
	private final String tableName;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Virtual Column SQL
	 */
	private final String ColumnSQL;
	/**
	 * Is Virtual Column
	 */
	private final boolean virtualColumn;
	/**
	 * Is Lazy Loading
	 */
	boolean IsLazyLoading;
	/**
	 * Display Type
	 */
<<<<<<< HEAD
	final int DisplayType;
=======
	@Getter private final int displayType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	/**
	 * Data Type
	 */
	final Class<?> ColumnClass;
	/**
	 * Mandatory
	 */
	final boolean IsMandatory;
	/**
	 * Default Value
	 */
	final String DefaultLogic;
	/**
	 * Updateable
	 */
<<<<<<< HEAD
	boolean IsUpdateable;
=======
	final boolean IsUpdateable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	/**
	 * PK
	 */
	final boolean IsKey;
	/**
	 * FK to Parent
	 */
	private final boolean IsParent;
	/**
	 * Translated
	 */
	private final boolean IsTranslated;
	/**
	 * Encrypted
	 */
	final boolean IsEncrypted;
	/**
	 * Allow Logging
	 */
	final boolean IsAllowLogging;

	/**
	 * Reference Value
	 */
<<<<<<< HEAD
	final int AD_Reference_Value_ID;
=======
	final ReferenceId AD_Reference_Value_ID;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	/**
	 * Validation
	 */
	// public String ValidationCode;
<<<<<<< HEAD
	final int AD_Val_Rule_ID;
=======
	@Getter final AdValRuleId AD_Val_Rule_ID;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Field Length
	 */
	final int FieldLength;
	/**
	 * Min Value
	 */
	final String ValueMin;
	/**
	 * Max Value
	 */
	final String ValueMax;
	/**
	 * Min Value
	 */
	final BigDecimal ValueMin_BD;
	/**
	 * Max Value
	 */
	final BigDecimal ValueMax_BD;

<<<<<<< HEAD
=======
	final boolean IsIdentifier;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	final boolean IsRestAPICustomColumn;
	@Getter private final ColumnCloningStrategy cloningStrategy;

	/* package */ boolean IsCalculated = false;
	// metas: us215
	/* package */ boolean IsUseDocumentSequence = false;
	// metas: 05133
	/* package */ boolean IsStaleable = true;
	// metas: 01537
	/* package */ boolean IsSelectionColumn;

	private final String sqlColumnForSelect;

	/**
	 * Cached {@link MLookupInfo} for {@link Env#WINDOW_None} (most used case)
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType") @Nullable
	private Optional<MLookupInfo> _lookupInfoForWindowNone = null;
<<<<<<< HEAD

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType") @Nullable
	private Optional<String> _referencedTableName = null; // lazy, cached
=======
	private final Optional<String> _referencedTableName;

	private final int AD_Reference_Value_KeyColumn_DisplayType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * String representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		return "POInfo.Column["
				+ ColumnName
				+ ",ID=" + AD_Column_ID
<<<<<<< HEAD
				+ ",DisplayType=" + DisplayType
=======
				+ ",DisplayType=" + displayType
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				+ ",ColumnClass=" + ColumnClass
				+ "]";
	}    // toString

	@Nullable
	private static BigDecimal toBigDecimalOrNull(final String valueStr, final String name)
	{
<<<<<<< HEAD
		if (Check.isEmpty(valueStr, true))
=======
		final String valueNorm = StringUtils.trimBlankToNull(valueStr);
		if(valueNorm == null)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return null;
		}

		try
		{
<<<<<<< HEAD
			return new BigDecimal(valueStr.trim());
		}
		catch (final Exception ex) // i.e. NumberFormatException
		{
			logger.error("Cannot parse " + name + "=" + valueStr, ex);
		}

		return null;
	}
=======
			return new BigDecimal(valueNorm);
		}
		catch (final Exception ex) // i.e. NumberFormatException
		{
			logger.error("Cannot parse {}=`{}`. Returning null.", name, valueNorm, ex);
		return null;
	}
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	public String getColumnName()
	{
		return this.ColumnName;
	}

<<<<<<< HEAD
	public int getAD_Column_ID()
=======
	public AdColumnId getAD_Column_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return this.AD_Column_ID;
	}

	boolean isTranslated()
	{
		return this.IsTranslated;
	}

	public String getColumnSQL()
	{
		return this.ColumnSQL;
	}

	public boolean isVirtualColumn()
	{
		return this.virtualColumn;
	}

	public String getColumnSqlForSelect()
	{
		return sqlColumnForSelect;
	}

<<<<<<< HEAD
	public int getDisplayType()
	{
		return DisplayType;
	}

	public int getAD_Reference_Value_ID()
=======
	public ReferenceId getAD_Reference_Value_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return AD_Reference_Value_ID;
	}

	public boolean isSelectionColumn()
	{
		return IsSelectionColumn;
	}

	public boolean isMandatory()
	{
		return IsMandatory;
	}

	public boolean isKey()
	{
		return IsKey;
	}

	public boolean isParent()
	{
		return IsParent;
	}

	public boolean isStaleable()
	{
		return IsStaleable;
	}

<<<<<<< HEAD
	public int getAD_Val_Rule_ID()
	{
		return AD_Val_Rule_ID;
	}

	public boolean isLookup()
	{
		return org.compiere.util.DisplayType.isLookup(DisplayType);
=======
	public boolean isLookup()
	{
		return org.compiere.util.DisplayType.isLookup(displayType);
	}

	public boolean isIdentifier()
	{
		return this.IsIdentifier;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public boolean isRestAPICustomColumn()
	{
		return IsRestAPICustomColumn;
	}

	@Nullable
	public String getReferencedTableNameOrNull()
	{
<<<<<<< HEAD
		Optional<String> referencedTableName = _referencedTableName;
		if (referencedTableName == null)
		{
			_referencedTableName = referencedTableName = computeReferencedTableName();
		}

		return referencedTableName.orElse(null);
	}

	private Optional<String> computeReferencedTableName()
	{
		// Special lookups (Location, Locator etc)
		final String refTableName = org.compiere.util.DisplayType.getTableName(DisplayType);
=======
		return _referencedTableName.orElse(null);
	}

	private static Optional<String> computeReferencedTableName(
			final int displayType,
			@Nullable final TableName adReferenceValueTableName)
	{
		// Special lookups (Location, Locator etc)
		final String refTableName = DisplayType.getTableName(displayType);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		if (refTableName != null)
		{
			return Optional.of(refTableName);
		}

<<<<<<< HEAD
		// Regular lookups
		final MLookupInfo lookupInfo = getLookupInfo(Env.WINDOW_None);
		if (lookupInfo != null)
		{
			return Optional.ofNullable(lookupInfo.getTableName());
=======
		if (DisplayType.isLookup(displayType) && adReferenceValueTableName != null)
		{
			return Optional.of(adReferenceValueTableName.getAsString());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		return Optional.empty();
	}

	@Nullable
	public MLookupInfo getLookupInfo(final int windowNo)
	{
		//
		// List, Table, TableDir
		if (isLookup())
		{
			if (windowNo == Env.WINDOW_None)
			{
				if (_lookupInfoForWindowNone == null)
				{
					final MLookupInfo lookupInfoCached = MLookupFactory.getLookupInfo(
							Env.WINDOW_None
<<<<<<< HEAD
							, DisplayType
							, TableName
							, ColumnName
							, AD_Reference_Value_ID
							, IsParent
							, AD_Val_Rule_ID);
=======
							, displayType
							, tableName
							, ColumnName
							, AD_Reference_Value_ID.getRepoId()
							, IsParent
							, AD_Val_Rule_ID.getRepoId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					_lookupInfoForWindowNone = Optional.ofNullable(lookupInfoCached);
				}
				return _lookupInfoForWindowNone.orElse(null);
			}

			return MLookupFactory.getLookupInfo(
					windowNo
<<<<<<< HEAD
					, DisplayType
					, TableName
					, ColumnName
					, AD_Reference_Value_ID
					, IsParent
					, AD_Val_Rule_ID);
=======
					, displayType
					, tableName
					, ColumnName
					, AD_Reference_Value_ID.getRepoId()
					, IsParent
					, AD_Val_Rule_ID.getRepoId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		else
		{
			return null;
		}
	}

	@Nullable
	public Lookup getLookup(final Properties ctx, final int windowNo)
	{
		//
		// List, Table, TableDir
		if (isLookup())
		{
			try
			{
				final MLookupInfo lookupInfo = getLookupInfo(windowNo);
				if (lookupInfo == null)
				{
					return null;
				}

<<<<<<< HEAD
				return MLookupFactory.ofLookupInfo(ctx, lookupInfo, AD_Column_ID);
=======
				return MLookupFactory.ofLookupInfo(ctx, lookupInfo, AD_Column_ID.getRepoId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			}
			catch (final Exception e)
			{
				return null;
			}
		}
		else
		{
			return null;
		}

	}

	public boolean isPasswordColumn()
	{
<<<<<<< HEAD
		return org.compiere.util.DisplayType.isPassword(ColumnName, DisplayType);
=======
		return DisplayType.isPassword(ColumnName, displayType);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}    // POInfoColumn
