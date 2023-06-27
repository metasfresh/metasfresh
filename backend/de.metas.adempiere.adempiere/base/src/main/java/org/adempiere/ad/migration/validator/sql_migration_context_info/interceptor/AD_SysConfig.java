package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_SysConfig.class)
@Component
public class AD_SysConfig
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_SysConfig sysConfig, final ModelChangeType modelChangeType)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		MigrationScriptFileLoggerHolder.logComment("SysConfig Name: " + sysConfig.getName());
		MigrationScriptFileLoggerHolder.logComment("SysConfig Value: " + sysConfig.getValue());

		if (modelChangeType.isChange() && InterfaceWrapperHelper.isValueChanged(sysConfig, I_AD_SysConfig.COLUMNNAME_Value))
		{
			final I_AD_SysConfig sysConfigOld = InterfaceWrapperHelper.createOld(sysConfig, I_AD_SysConfig.class);
			MigrationScriptFileLoggerHolder.logComment("SysConfig Value (old): " + sysConfigOld.getValue());
		}
	}
}
