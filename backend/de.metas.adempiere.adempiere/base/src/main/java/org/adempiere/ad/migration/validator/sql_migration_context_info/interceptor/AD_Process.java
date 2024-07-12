package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import de.metas.util.StringUtils;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Process;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Process.class)
@Component
public class AD_Process
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_Process record)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		MigrationScriptFileLoggerHolder.logComment("Value: " + record.getValue());

		final String classname = StringUtils.trimBlankToNull(record.getClassname());
		if (classname != null)
		{
			MigrationScriptFileLoggerHolder.logComment("Classname: " + classname);
		}

		final String jasperReport = StringUtils.trimBlankToNull(record.getJasperReport());
		if (jasperReport != null)
		{
			MigrationScriptFileLoggerHolder.logComment("JasperReport: " + jasperReport);
		}
	}
}
