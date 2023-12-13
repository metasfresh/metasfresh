package org.adempiere.ad.persistence.custom_columns;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.compiere.model.POInfoColumn;

@Value
@Builder
class RESTApiTableInfo
{
	@NonNull String tableName;
	@NonNull @Singular ImmutableSet<String> customRestAPIColumnNames;

	public static RESTApiTableInfoBuilder newBuilderForTableName(@NonNull final String tableName)
	{
		return builder().tableName(tableName);
	}

	public boolean isCustomRestAPIColumn(final POInfoColumn poInfoColumn)
	{
		return isCustomRestAPIColumn(poInfoColumn.getColumnName());
	}

	public boolean isCustomRestAPIColumn(final String columnName)
	{
		return customRestAPIColumnNames.contains(columnName);
	}

}
