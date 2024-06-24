/**
 *
 */
package de.metas.copy_with_details;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class CopyRecordSupportTableInfo
{
	@NonNull String tableName;
	@NonNull String linkColumnName;
	@NonNull String parentTableName;
	@NonNull ImmutableList<String> orderByColumnNames;

	@Builder
	private CopyRecordSupportTableInfo(
			@NonNull final String tableName,
			@NonNull final String linkColumnName,
			@NonNull final String parentTableName,
			@Nullable final List<String> orderByColumnNames)
	{
		this.tableName = tableName;
		this.linkColumnName = linkColumnName;
		this.parentTableName = parentTableName;
		this.orderByColumnNames = orderByColumnNames != null ? ImmutableList.copyOf(orderByColumnNames) : ImmutableList.of();
	}
}
