package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import de.metas.process.AdProcessId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADProcessName;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Process_Trl;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Process_Trl.class)
@Component
public class AD_Process_Trl
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_Process_Trl record)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final AdProcessId adProcessId = AdProcessId.ofRepoId(record.getAD_Process_ID());
		final ADProcessName processName = Names.ADProcessName_Loader.retrieve(adProcessId);
		MigrationScriptFileLoggerHolder.logComment("Process: " + processName.toShortString());
	}
}
