package org.adempiere.ad.column.model.interceptor;

import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.expression.api.impl.LogicExpressionCompiler;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Column;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import de.metas.i18n.po.POTrlRepository;
import de.metas.security.impl.ParsedSql;
import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Check;
import de.metas.util.Services;

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
public class AD_Column
{
	public static final transient AD_Column instance = new AD_Column();

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_AD_Column.COLUMNNAME_ColumnSQL)
	public void lowerCaseWhereClause(final I_AD_Column column)
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
	public void validateLogicExpressions(final I_AD_Column column)
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
	public void assertTrlColumnExists(final I_AD_Column adColumn)
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
					+"\n If not, please manually create the table and/or column.");
		}
	}
}
