package org.adempiere.ad.column.model.interceptor;

import de.metas.i18n.po.POTrlRepository;
import de.metas.logging.LogManager;
import de.metas.security.impl.ParsedSql;
import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.column.ColumnSql;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.expression.api.impl.LogicExpressionCompiler;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Interceptor(I_AD_Column.class)
@Component
public class AD_Column
{
	private static final Logger logger = LogManager.getLogger(AD_Column.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_AD_Column.COLUMNNAME_ColumnSQL)
	public void onBeforeSave_lowerCaseWhereClause(final I_AD_Column column)
	{
		final String columnSql = StringUtils.trimBlankToNull(column.getColumnSQL());
		if (columnSql == null)
		{
			// nothing to do
			return;
		}

		final String columnSqlFixed = ParsedSql.rewriteWhereClauseWithLowercaseKeyWords(columnSql);
		column.setColumnSQL(columnSqlFixed);

		validateColumnSql(getTableName(column), columnSqlFixed);
	}

	private void validateColumnSql(
			@NonNull final String ctxTableName,
			@NonNull final String columnSqlString)
	{
		final String columnSqlStringAdapted = ColumnSql.ofSql(columnSqlString, ctxTableName)
				.withJoinOnTableNameOrAlias("master")
				.toSqlStringWrappedInBracketsIfNeeded();

		final String sql = "SELECT"
				+ " " + columnSqlStringAdapted
				+ " FROM " + ctxTableName + " master"
				+ " LIMIT 1";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			rs = pstmt.executeQuery();
		}
		catch (final SQLException ex)
		{
			throw new AdempiereException("Invalid Column SQL. Hints:"
					+ "\n 1. To reference context table name, always use the right case, e.g. C_Invoice instead of c_invoice"
					+ "\n 2. If in your sub-query you need to join again the context table name, consider using @JoinTableNameOrAliasIncludingDot@ to reference the context table name. Pls search for examples."
					+ "\n 3. If you think this validation is not correct, feel free to temporary deactivate this check.", ex)
					.setParameter("Test SQL", sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_AD_Column.COLUMNNAME_MandatoryLogic, I_AD_Column.COLUMNNAME_ReadOnlyLogic })
	public void onBeforeSave_ValidateLogicExpressions(final I_AD_Column column)
	{
		final String readOnlyLogic = StringUtils.trimBlankToNull(column.getReadOnlyLogic());
		if (readOnlyLogic != null)
		{
			LogicExpressionCompiler.instance.compile(readOnlyLogic);
		}

		final String mandatoryLogic = StringUtils.trimBlankToNull(column.getMandatoryLogic());
		if (mandatoryLogic != null)
		{
			LogicExpressionCompiler.instance.compile(mandatoryLogic);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = {
					I_AD_Field.COLUMNNAME_AD_Column_ID,
					I_AD_Field.COLUMNNAME_AD_Reference_ID,
					I_AD_Field.COLUMNNAME_AD_Reference_Value_ID })
	public void onBeforeSave_ValidateReference(final I_AD_Column column)
	{
		final int adReferenceId = column.getAD_Reference_ID();
		if (DisplayType.isAnyLookup(adReferenceId))
		{
			final MLookupInfo lookupInfo;
			try
			{
				final String ctxTableName = getTableName(column);
				lookupInfo = MLookupFactory.getLookupInfo(
						Integer.MAX_VALUE, // WindowNo
						adReferenceId,
						ctxTableName, // ctxTableName
						column.getColumnName(), // ctxColumnName
						column.getAD_Reference_Value_ID(),
						column.isParent(), // IsParent,
						column.getAD_Val_Rule_ID() //AD_Val_Rule_ID
				);
			}
			catch (final Exception ex)
			{
				fireExceptionInvalidLookup(logger, ex);
				return;
			}

			if (lookupInfo == null)
			{
				fireExceptionInvalidLookup(logger, null);
			}
		}
	}

	public static void fireExceptionInvalidLookup(@NonNull final Logger logger, @Nullable final Exception cause)
	{
		final String message = "Invalid AD_Reference_ID/AD_Reference_Value_ID values."
				+ "\n To avoid this issue you could:"
				+ "\n 1. Fix the underlying error. Check the console for more info."
				+ "\n 2. If you consider this is not an error then report the issue and comment this method to continue your work";

		//noinspection ThrowableNotThrown
		new AdempiereException(message, cause)
				.throwIfDeveloperModeOrLogWarningElse(logger);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, //
			ifColumnsChanged = { I_AD_Column.COLUMNNAME_AD_Element_ID })
	public void onAfterSave_WhenElementChanged(final I_AD_Column column)
	{
		updateTranslationsForElement(column);
	}

	private void updateTranslationsForElement(final I_AD_Column column)
	{
		final AdElementId elementId = AdElementId.ofRepoIdOrNull(column.getAD_Element_ID());
		if (elementId == null)
		{
			return;
		}

		final IElementTranslationBL elementTranslationBL = Services.get(IElementTranslationBL.class);
		elementTranslationBL.updateColumnTranslationsFromElement(elementId);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_AD_Column.COLUMNNAME_IsTranslated })
	public void onBeforeSave_assertTrlColumnExists(final I_AD_Column adColumn)
	{
		if (!adColumn.isTranslated())
		{
			return;
		}

		if (!DisplayType.isText(adColumn.getAD_Reference_ID()))
		{
			throw new AdempiereException("Only text columns are translatable");
		}

		if (tableDAO.isVirtualColumn(adColumn))
		{
			throw new AdempiereException("Virtual columns are not translatable");
		}

		final String tableName = getTableName(adColumn);
		final String trlTableName = POTrlRepository.toTrlTableName(tableName);
		final String columnName = adColumn.getColumnName();

		if (!DB.isDBColumnPresent(trlTableName, columnName))
		{
			throw new AdempiereException("Before marking the column as translatable make sure " + trlTableName + "." + columnName + " exists."
					+ "\n If not, please manually create the table and/or column.");
		}
	}

	private String getTableName(final I_AD_Column adColumn)
	{
		return tableDAO.retrieveTableName(AdTableId.ofRepoId(adColumn.getAD_Table_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_AD_Column.COLUMNNAME_ColumnName, I_AD_Column.COLUMNNAME_AD_Reference_ID })
	public void onBeforeSave_assertColumnNameValid(final I_AD_Column adColumn)
	{
		assertColumnNameValid(adColumn.getColumnName(), adColumn.getAD_Reference_ID());
	}

	private void assertColumnNameValid(@NonNull final String columnName, final int displayType)
	{
		if (DisplayType.isID(displayType) && displayType != DisplayType.Account)
		{
			if (!columnName.endsWith("_ID")
					&& !columnName.equals("CreatedBy")
					&& !columnName.equals("UpdatedBy"))
			{
				throw new AdempiereException("Lookup or ID columns shall have the name ending with `_ID`");
			}

			if (displayType == DisplayType.Locator && !columnName.contains("Locator"))
			{
				throw new AdempiereException("A Locator column name must contain the term `Locator`.");
			}
		}
		else if (displayType == DisplayType.Account)
		{
			if (!columnName.endsWith("_Acct"))
			{
				throw new AdempiereException("Account columns shall have the name ending with `_Acct`");
			}
		}
		else
		{
			if (columnName.endsWith("_ID"))
			{
				throw new AdempiereException("Ending a non lookup column wiht `_ID` is might be misleading");
			}
			if (columnName.endsWith("_Acct"))
			{
				throw new AdempiereException("Ending a non Account column wiht `_Acct` is might be misleading");
			}
		}
	}

}
