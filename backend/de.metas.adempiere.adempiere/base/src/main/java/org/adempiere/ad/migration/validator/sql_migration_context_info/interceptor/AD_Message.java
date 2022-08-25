package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Message;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Message.class)
@Component
public class AD_Message
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_Message record)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		MigrationScriptFileLoggerHolder.logComment("Value: " + record.getValue());
	}
}
