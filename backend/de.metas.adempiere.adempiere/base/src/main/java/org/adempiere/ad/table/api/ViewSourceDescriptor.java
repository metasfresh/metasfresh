package org.adempiere.ad.table.api;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ViewSourceDescriptor
{
	@NonNull AdTableId viewId;
	@NonNull TableName viewName;

	@NonNull TableName sourceTableName;

	@NonNull ColumnName sourceLinkColumnName;
	@NonNull ColumnName viewLinkColumnName;

	@NonNull ImmutableSet<ModelCacheInvalidationTiming> invalidateOnTimings;

	@NonNull ImmutableSet<ColumnName> invalidateOnChangeOnlyForColumnNames;
}
