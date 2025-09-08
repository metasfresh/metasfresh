package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADColumnNameFQ;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADTableName;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.AdTableId;
import org.compiere.model.I_AD_SQLColumn_SourceTableColumn;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_SQLColumn_SourceTableColumn.class)
@Component
public class AD_SQLColumn_SourceTableColumn
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_SQLColumn_SourceTableColumn record)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		final AdColumnId adColumnId = AdColumnId.ofRepoId(record.getAD_Column_ID());
		final ADColumnNameFQ columnNameFQ = Names.ADColumnNameFQ_Loader.retrieve(adColumnId);
		MigrationScriptFileLoggerHolder.logComment("Column: " + columnNameFQ.toShortString());

		final AdTableId sourceTableId = AdTableId.ofRepoId(record.getSource_Table_ID());
		final ADTableName sourceTableName = Names.ADTableName_Loader.retrieve(sourceTableId);
		MigrationScriptFileLoggerHolder.logComment("Source Table: " + sourceTableName.toShortString());
	}
}
