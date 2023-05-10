package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import de.metas.ad_reference.ADRefListId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Ref_List_Trl;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Ref_List_Trl.class)
@Component
public class AD_Ref_List_Trl
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_Ref_List_Trl record)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final ADRefListId refListId = ADRefListId.ofRepoId(record.getAD_Ref_List_ID());
		final String referenceItemName = Names.ADRefListName_Loader.retrieve(refListId);
		MigrationScriptFileLoggerHolder.logComment("Reference Item: " + referenceItemName);
	}
}
