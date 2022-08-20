package org.adempiere.ad.migration.validator.sql_migration_context_info.names;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;

import javax.annotation.Nullable;

@Value
@Builder
public class ADWindowName
{
	@NonNull AdWindowId adWindowId;
	@Nullable String name;
	@Nullable String entityType;
	boolean missing;

	public static ADWindowName missing(@NonNull final AdWindowId adWindowId)
	{
		return builder()
				.adWindowId(adWindowId)
				.missing(true)
				.build();
	}

	@Override
	public String toString() {return toShortString();}

	public String toShortString()
	{
		if (missing)
		{
			return "<" + adWindowId.getRepoId() + ">";
		}
		else
		{
			return "" + name + "(" + adWindowId.getRepoId() + "," + entityType + ")";
		}
	}
}
