package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import org.adempiere.ad.element.api.AdUIColumnId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADUIColumnNameFQ;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_UI_ElementGroup.class)
@Component
public class AD_UI_ElementGroup
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_UI_ElementGroup uiElementGroup)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final AdUIColumnId uiColumnId = AdUIColumnId.ofRepoId(uiElementGroup.getAD_UI_Column_ID());
		final ADUIColumnNameFQ uiColumnFQ = Names.ADUIColumnNameFQ_Loader.retrieve(uiColumnId);
		MigrationScriptFileLoggerHolder.logComment("UI Column: " + uiColumnFQ.toShortString());
		MigrationScriptFileLoggerHolder.logComment("UI Element Group: " + uiElementGroup.getName());
	}
}
