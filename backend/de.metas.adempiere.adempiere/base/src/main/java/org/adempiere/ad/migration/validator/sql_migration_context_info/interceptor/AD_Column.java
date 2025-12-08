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

package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import de.metas.util.StringUtils;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADTableName;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Column.class)
@Component
public class AD_Column
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_Column adColumn)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final AdTableId adTableId = AdTableId.ofRepoId(adColumn.getAD_Table_ID());
		final ADTableName tableName = Names.ADTableName_Loader.retrieve(adTableId);
		final String columnNameFQ = tableName.toShortString() + "." + adColumn.getColumnName();
		MigrationScriptFileLoggerHolder.logComment("Column: " + columnNameFQ);

		if (InterfaceWrapperHelper.isValueChanged(adColumn, I_AD_Column.COLUMNNAME_ColumnSQL))
		{
			final I_AD_Column adColumnOld = InterfaceWrapperHelper.createOld(adColumn, I_AD_Column.class);
			final String columnSQL = StringUtils.trimBlankToNull(adColumnOld.getColumnSQL());
			if (columnSQL != null)
			{
				MigrationScriptFileLoggerHolder.logComment("Column SQL (old): " + columnSQL);
			}
		}
	}
}
