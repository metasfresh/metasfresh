/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.ad.migration.validator.sql_migration_context_info;

import de.metas.util.StringUtils;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_UI_Element.class)
@Component
public class AD_UI_Element
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_UI_Element uiElement)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final String tabNameFQ = AD_Field.retrieveTabNameFQ(uiElement.getAD_Tab_ID());
		final String uiElementFQ = tabNameFQ + "." + uiElement.getName();
		MigrationScriptFileLoggerHolder.logComment("UI Element: " + uiElementFQ);

		final AdFieldId adFieldId = AdFieldId.ofRepoIdOrNull(uiElement.getAD_Field_ID());
		if(adFieldId != null)
		{
			MigrationScriptFileLoggerHolder.logComment("Column: " + retrieveColumnNameFQ(adFieldId));
		}
	}

	private static String retrieveColumnNameFQ(final AdFieldId adFieldId)
	{
		final String columnNameFQ = DB.getSQLValueStringEx(
				ITrx.TRXNAME_ThreadInherited,
				"SELECT t.TableName || '.' || c.ColumnName"
						+ " FROM AD_Field f"
						+ " LEFT OUTER JOIN AD_Column c on c.AD_Column_ID=f.AD_Column_ID"
						+ " LEFT OUTER JOIN AD_Table t on t.AD_Table_ID=c.AD_Table_ID "
						+ " WHERE AD_Field_ID=?",
				adFieldId);

		return StringUtils.trimBlankToOptional(columnNameFQ)
				.orElseGet(() -> "<" + adFieldId.getRepoId() + ">");
	}

}
