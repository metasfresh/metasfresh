package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.ADColumnNameFQ;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_ViewSource;
import org.compiere.model.I_AD_ViewSource_Column;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Interceptor(I_AD_ViewSource_Column.class)
@Component
public class AD_ViewSource_Column
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_ViewSource_Column viewSourceColumn)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		MigrationScriptFileLoggerHolder.logComment("View Source: " + retrieveAdViewSourceInfoById(viewSourceColumn.getAD_ViewSource_ID()));

		final ADColumnNameFQ sourceColumnName = Names.ADColumnNameFQ_Loader.retrieve(AdColumnId.ofRepoId(viewSourceColumn.getAD_Column_ID()));
		MigrationScriptFileLoggerHolder.logComment("Source Column Name: " + sourceColumnName);
	}

	private static String retrieveAdViewSourceInfoById(final int adViewSourceId)
	{
		final String sql = "SELECT view_table.tablename AS view_tablename"
				+ ", source_table.tablename AS source_tablename"
				+ " FROM " + I_AD_ViewSource.Table_Name + " vs"
				+ " INNER JOIN ad_table view_table ON view_table.ad_table_id = vs." + I_AD_ViewSource.COLUMNNAME_AD_Table_ID
				+ " INNER JOIN ad_table source_table ON source_table.ad_table_id = vs." + I_AD_ViewSource.COLUMNNAME_Source_Table_ID
				+ " WHERE vs." + I_AD_ViewSource.COLUMNNAME_AD_ViewSource_ID + "=?";

		return DB.retrieveFirstRowOrNull(
				sql,
				Collections.singletonList(adViewSourceId),
				rs -> {
					final String viewTableName = rs.getString(1);
					final String sourceTableName = rs.getString(2);
					return viewTableName + " (source: " + sourceTableName + ")";
				});
	}

}
