package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADTabNameFQ;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_UI_Section.class)
@Component
public class AD_UI_Section
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_UI_Section uiSection)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final AdTabId adTabId = AdTabId.ofRepoId(uiSection.getAD_Tab_ID());
		final ADTabNameFQ tabNameFQ = Names.ADTabNameFQ_Loader.retrieve(adTabId);
		MigrationScriptFileLoggerHolder.logComment("Tab: " + tabNameFQ.toShortString());
		MigrationScriptFileLoggerHolder.logComment("UI Section: " + uiSection.getValue());
	}
}
