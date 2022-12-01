package org.adempiere.ad.migration.validator.sql_migration_context_info.names;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;

import javax.annotation.Nullable;

@Value
@Builder
public class ADTableName
{
	@NonNull AdTableId adTableId;
	@NonNull String tableName;
	@Nullable String entityType;
	boolean missing;

	public static ADTableName missing(@NonNull final AdTableId adTableId)
	{
		return builder().adTableId(adTableId).missing(true).build();
	}

	@Override
	public String toString() {return toShortString();}

	public String toShortString()
	{
		if (missing)
		{
			return "<" + adTableId.getRepoId() + ">";
		}
		else
		{
			return tableName;
		}
	}
}
