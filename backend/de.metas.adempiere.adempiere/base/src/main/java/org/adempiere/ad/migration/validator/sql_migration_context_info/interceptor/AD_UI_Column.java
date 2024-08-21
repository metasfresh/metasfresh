package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import org.adempiere.ad.element.api.AdUISectionId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADUISectionNameFQ;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names.ADUISectionNameFQ_Loader;

@Interceptor(I_AD_UI_Column.class)
@Component
public class AD_UI_Column
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_UI_Column uiColumn)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final AdUISectionId uiSectionId = AdUISectionId.ofRepoId(uiColumn.getAD_UI_Section_ID());
		final ADUISectionNameFQ uiSectionNameFQ = ADUISectionNameFQ_Loader.retrieve(uiSectionId);
		MigrationScriptFileLoggerHolder.logComment("UI Section: " + uiSectionNameFQ.toShortString());
		MigrationScriptFileLoggerHolder.logComment("UI Column: " + uiColumn.getSeqNo());
	}
}
