package org.adempiere.ad.migration.validator.sql_migration_context_info.names;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdTabId;

import javax.annotation.Nullable;

@Value
@Builder
public class ADTabNameFQ
{
	@NonNull AdTabId adTabId;
	@Nullable String name;
	@Nullable String entityType;
	@Nullable ADWindowName windowName;
	boolean missing;

	public static ADTabNameFQ missing(@NonNull final AdTabId adTabId)
	{
		return builder()
				.adTabId(adTabId)
				.missing(true)
				.build();
	}

	@Override
	public String toString() {return toShortString();}

	public String toShortString()
	{
		if (missing)
		{
			return "<" + adTabId.getRepoId() + ">";
		}
		else
		{
			final StringBuilder sb = new StringBuilder();
			if (windowName != null)
			{
				sb.append(windowName.toShortString());
			}

			if (sb.length() > 0)
			{
				sb.append(" -> ");
			}

			sb.append(name).append("(").append(adTabId.getRepoId()).append(",").append(entityType).append(")");

			return sb.toString();
		}
	}

}
