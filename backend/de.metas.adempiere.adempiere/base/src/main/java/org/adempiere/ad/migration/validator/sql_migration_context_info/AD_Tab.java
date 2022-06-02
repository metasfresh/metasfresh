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
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;
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

		final String tabNameFQ = retrieveWindowNameFQ(tab.getAD_Window_ID()) + " -> " + tab.getName();
		MigrationScriptFileLoggerHolder.logComment("Tab: " + tabNameFQ);

		final AdTableId adTableId = AdTableId.ofRepoId(tab.getAD_Table_ID());
		final String tableName = TableIdsCache.instance.getTableNameIfPresent(adTableId).orElseGet(() -> "<" + adTableId + ">");
		MigrationScriptFileLoggerHolder.logComment("Table: " + tableName);
	}

	private static String retrieveWindowNameFQ(final int adWindowId)
	{
		final String windowName = DB.getSQLValueStringEx(
				ITrx.TRXNAME_ThreadInherited,
				"SELECT Name FROM AD_Window WHERE AD_Window_ID=?",
				adWindowId);

		return StringUtils.trimBlankToOptional(windowName)
				.orElseGet(() -> "<" + adWindowId + ">");
	}
}
