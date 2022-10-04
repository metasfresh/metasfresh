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

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADTableName;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADWindowName;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.AdTableId;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Tab.class)
@Component
public class AD_Tab
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_Tab tab)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final AdWindowId adWindowId = AdWindowId.ofRepoId(tab.getAD_Window_ID());
		final ADWindowName windowName = Names.ADWindowName_Loader.retrieve(adWindowId);
		final String tabNameFQ = windowName.toShortString() + " -> " + tab.getName();
		MigrationScriptFileLoggerHolder.logComment("Tab: " + tabNameFQ);

		final AdTableId adTableId = AdTableId.ofRepoId(tab.getAD_Table_ID());
		final ADTableName tableName = Names.ADTableName_Loader.retrieve(adTableId);
		MigrationScriptFileLoggerHolder.logComment("Table: " + tableName.toShortString());
	}
}
