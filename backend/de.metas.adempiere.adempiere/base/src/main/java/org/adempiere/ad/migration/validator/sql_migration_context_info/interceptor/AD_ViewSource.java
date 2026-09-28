package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADTableName;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.AdTableId;
import org.compiere.model.I_AD_ViewSource;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_ViewSource.class)
@Component
public class AD_ViewSource
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_ViewSource viewSource)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final ADTableName viewName = Names.ADTableName_Loader.retrieve(AdTableId.ofRepoId(viewSource.getAD_Table_ID()));
		MigrationScriptFileLoggerHolder.logComment("View Name: " + viewName);

		final ADTableName sourceTableName = Names.ADTableName_Loader.retrieve(AdTableId.ofRepoId(viewSource.getSource_Table_ID()));
		MigrationScriptFileLoggerHolder.logComment("Source Table Name: " + sourceTableName);
	}
}
