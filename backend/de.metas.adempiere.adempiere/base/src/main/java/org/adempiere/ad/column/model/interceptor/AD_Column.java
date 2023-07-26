package org.adempiere.ad.column.model.interceptor;

import de.metas.i18n.po.POTrlRepository;
import de.metas.logging.LogManager;
import de.metas.security.impl.ParsedSql;
import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.expression.api.impl.LogicExpressionCompiler;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
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
		final String columnSQL = column.getColumnSQL();
		if (Check.isEmpty(columnSQL, true))
		{
			// nothing to do
			return;
		}

		final String adaptedWhereClause = ParsedSql.rewriteWhereClauseWithLowercaseKeyWords(columnSQL);

		column.setColumnSQL(adaptedWhereClause);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_AD_Column.COLUMNNAME_MandatoryLogic, I_AD_Column.COLUMNNAME_ReadOnlyLogic })
	public void onBeforeSave_ValidateLogicExpressions(final I_AD_Column column)
	{
		if (!Check.isEmpty(column.getReadOnlyLogic(), true))
		{
			LogicExpressionCompiler.instance.compile(column.getReadOnlyLogic());
		}

		if (!Check.isEmpty(column.getMandatoryLogic(), true))
		{
			LogicExpressionCompiler.instance.compile(column.getMandatoryLogic());
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
				final String ctxTableName = tableDAO.retrieveTableName(column.getAD_Table_ID());
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
				//noinspection ThrowableNotThrown
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

		final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);
		if (adTablesRepo.isVirtualColumn(adColumn))
		{
			throw new AdempiereException("Virtual columns are not translatable");
		}

		final String tableName = adTablesRepo.retrieveTableName(adColumn.getAD_Table_ID());
		final String trlTableName = POTrlRepository.toTrlTableName(tableName);
		final String columnName = adColumn.getColumnName();

		if (!DB.isDBColumnPresent(trlTableName, columnName))
		{
			throw new AdempiereException("Before marking the column as translatable make sure " + trlTableName + "." + columnName + " exists."
					+ "\n If not, please manually create the table and/or column.");
		}
	}
}
