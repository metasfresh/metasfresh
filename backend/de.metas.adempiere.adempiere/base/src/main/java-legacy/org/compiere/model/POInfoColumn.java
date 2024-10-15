package org.compiere.model;

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

	private static final transient Logger logger = LogManager.getLogger(POInfoColumn.class);

	public POInfoColumn(
			final int ad_Column_ID,
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
			final int ad_Reference_Value_ID,
			final int AD_Val_Rule_ID,
			final int fieldLength,
			final String valueMin,
			final String valueMax,
			final boolean isTranslated,
			final boolean isEncrypted,
			final boolean isAllowLogging,
			final boolean isRestAPICustomColumn,
			@NonNull final ColumnCloningStrategy cloningStrategy)
	{
		AD_Column_ID = ad_Column_ID;
		ColumnName = columnName;
		TableName = tableName;
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

		if (isString(tableName, columnName, displayTypeParam, ad_Reference_Value_ID))
		{
			this.DisplayType = org.compiere.util.DisplayType.String;
			this.ColumnClass = String.class;
		}
		else if (columnName.equals("Posted") || columnName.equals("Processed") || columnName.equals("Processing"))
		{
			this.DisplayType = displayTypeParam;
			this.ColumnClass = Boolean.class;
		}
		else if (Services.get(IColumnBL.class).isRecordIdColumnName(columnName))
		{
			this.DisplayType = org.compiere.util.DisplayType.ID;
			this.ColumnClass = Integer.class;
		}
		else
		{
			this.DisplayType = displayTypeParam;
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
	}   // Column

	private static boolean isString(
			final String tableName,
			final String columnName,
			final int displayType,
			final int ad_Reference_Value_ID)
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
	final int AD_Column_ID;
	/**
	 * Column Name
	 */
	private final String ColumnName;

	private final String TableName;

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
	final int DisplayType;
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
	boolean IsUpdateable;
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
	final int AD_Reference_Value_ID;
	/**
	 * Validation
	 */
	// public String ValidationCode;
	final int AD_Val_Rule_ID;

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

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType") @Nullable
	private Optional<String> _referencedTableName = null; // lazy, cached

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
				+ ",DisplayType=" + DisplayType
				+ ",ColumnClass=" + ColumnClass
				+ "]";
	}    // toString

	@Nullable
	private static BigDecimal toBigDecimalOrNull(final String valueStr, final String name)
	{
		if (Check.isEmpty(valueStr, true))
		{
			return null;
		}

		try
		{
			return new BigDecimal(valueStr.trim());
		}
		catch (final Exception ex) // i.e. NumberFormatException
		{
			logger.error("Cannot parse " + name + "=" + valueStr, ex);
		}

		return null;
	}

	public String getColumnName()
	{
		return this.ColumnName;
	}

	public int getAD_Column_ID()
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

	public int getDisplayType()
	{
		return DisplayType;
	}

	public int getAD_Reference_Value_ID()
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

	public int getAD_Val_Rule_ID()
	{
		return AD_Val_Rule_ID;
	}

	public boolean isLookup()
	{
		return org.compiere.util.DisplayType.isLookup(DisplayType);
	}

	public boolean isRestAPICustomColumn()
	{
		return IsRestAPICustomColumn;
	}

	@Nullable
	public String getReferencedTableNameOrNull()
	{
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
		if (refTableName != null)
		{
			return Optional.of(refTableName);
		}

		// Regular lookups
		final MLookupInfo lookupInfo = getLookupInfo(Env.WINDOW_None);
		if (lookupInfo != null)
		{
			return Optional.ofNullable(lookupInfo.getTableName());
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
							, DisplayType
							, TableName
							, ColumnName
							, AD_Reference_Value_ID
							, IsParent
							, AD_Val_Rule_ID);
					_lookupInfoForWindowNone = Optional.ofNullable(lookupInfoCached);
				}
				return _lookupInfoForWindowNone.orElse(null);
			}

			return MLookupFactory.getLookupInfo(
					windowNo
					, DisplayType
					, TableName
					, ColumnName
					, AD_Reference_Value_ID
					, IsParent
					, AD_Val_Rule_ID);
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

				return MLookupFactory.ofLookupInfo(ctx, lookupInfo, AD_Column_ID);
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
		return org.compiere.util.DisplayType.isPassword(ColumnName, DisplayType);
	}
}    // POInfoColumn
