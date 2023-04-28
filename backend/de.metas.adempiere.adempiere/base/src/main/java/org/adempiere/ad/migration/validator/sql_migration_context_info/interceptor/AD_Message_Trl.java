package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import de.metas.i18n.AdMessageId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Message_Trl;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Message_Trl.class)
@Component
public class AD_Message_Trl
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_Message_Trl record)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final AdMessageId adMessageId = AdMessageId.ofRepoId(record.getAD_Message_ID());
		final String value = Names.ADMessageName_Loader.retrieveValue(adMessageId);
		MigrationScriptFileLoggerHolder.logComment("Value: " + value);
	}
}
