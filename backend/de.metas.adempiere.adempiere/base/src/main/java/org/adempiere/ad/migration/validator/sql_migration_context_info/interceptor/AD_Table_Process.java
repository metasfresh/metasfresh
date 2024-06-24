package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import de.metas.process.AdProcessId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADProcessName;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADTabNameFQ;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADTableName;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADWindowName;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.AdTableId;
import org.compiere.model.I_AD_Table_Process;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Table_Process.class)
@Component
public class AD_Table_Process
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_Table_Process record)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final AdProcessId adProcessId = AdProcessId.ofRepoId(record.getAD_Process_ID());
		final ADProcessName processName = Names.ADProcessName_Loader.retrieve(adProcessId);
		MigrationScriptFileLoggerHolder.logComment("Process: " + processName.toShortString());

		final AdTableId adTableId = AdTableId.ofRepoId(record.getAD_Table_ID());
		final ADTableName tableName = Names.ADTableName_Loader.retrieve(adTableId);
		MigrationScriptFileLoggerHolder.logComment("Table: " + tableName.toShortString());

		final AdTabId adTabId = AdTabId.ofRepoIdOrNull(record.getAD_Tab_ID());
		if (adTabId != null)
		{
			final ADTabNameFQ tabNameFQ = Names.ADTabNameFQ_Loader.retrieve(adTabId);
			MigrationScriptFileLoggerHolder.logComment("Tab: " + tabNameFQ.toShortString());
		}

		final AdWindowId adWindowId = AdWindowId.ofRepoIdOrNull(record.getAD_Window_ID());
		if (adWindowId != null)
		{
			final ADWindowName windowName = Names.ADWindowName_Loader.retrieve(adWindowId);
			MigrationScriptFileLoggerHolder.logComment("Window: " + windowName.toShortString());
		}

		MigrationScriptFileLoggerHolder.logComment("EntityType: " + record.getEntityType());
	}
}
