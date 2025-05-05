package org.adempiere.ad.persistence.custom_columns;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.compiere.model.POInfoColumn;

@Value
public class RESTApiTableInfo
{
	@NonNull String tableName;
	@NonNull @Getter(AccessLevel.NONE) ImmutableSet<String> customRestAPIColumnNamesUC;

	@Builder
	public RESTApiTableInfo(
			@NonNull final String tableName,
			@NonNull @Singular final ImmutableSet<String> customRestAPIColumnNames)
	{
		this.tableName = tableName;
		this.customRestAPIColumnNamesUC = customRestAPIColumnNames.stream()
				.map(String::toUpperCase)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static RESTApiTableInfoBuilder newBuilderForTableName(@NonNull final String tableName)
	{
		return builder().tableName(tableName);
	}

	public boolean isCustomRestAPIColumn(@NonNull final POInfoColumn poInfoColumn)
	{
		return isCustomRestAPIColumn(poInfoColumn.getColumnName());
	}

	public boolean isCustomRestAPIColumn(@NonNull final String columnName)
	{
		return customRestAPIColumnNamesUC.contains(columnName.toUpperCase());
	}

}
