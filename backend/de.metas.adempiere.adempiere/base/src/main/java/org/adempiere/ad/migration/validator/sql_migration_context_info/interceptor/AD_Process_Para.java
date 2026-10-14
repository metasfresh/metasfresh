package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import de.metas.process.AdProcessId;
import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Services;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADProcessName;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Process_Para.class)
@Component
public class AD_Process_Para
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_Process_Para record)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final AdProcessId adProcessId = AdProcessId.ofRepoId(record.getAD_Process_ID());
		final ADProcessName processName = Names.ADProcessName_Loader.retrieve(adProcessId);
		MigrationScriptFileLoggerHolder.logComment("Process: " + processName.toShortString());
		MigrationScriptFileLoggerHolder.logComment("ParameterName: " + record.getColumnName());
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, //
			ifColumnsChanged = { I_AD_Column.COLUMNNAME_AD_Element_ID })
	public void onAfterSave_WhenElementChanged(final I_AD_Process_Para para)
	{
		updateTranslationsForElement(para);
	}

	private void updateTranslationsForElement(final I_AD_Process_Para column)
	{
		final AdElementId elementId = AdElementId.ofRepoIdOrNull(column.getAD_Element_ID());
		if (elementId == null)
		{
			return;
		}

		final IElementTranslationBL elementTranslationBL = Services.get(IElementTranslationBL.class);
		elementTranslationBL.updateProcessParaTranslationsFromElement(elementId);
	}
}
