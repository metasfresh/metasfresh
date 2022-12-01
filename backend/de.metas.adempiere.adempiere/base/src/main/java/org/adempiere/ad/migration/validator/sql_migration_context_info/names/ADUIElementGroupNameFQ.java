package org.adempiere.ad.migration.validator.sql_migration_context_info.names;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.window.api.UIElementGroupId;

@Value
@Builder
public class ADUIElementGroupNameFQ
{
	@NonNull UIElementGroupId uiElementGroupId;
	String name;

	ADUIColumnNameFQ uiColumnName;

	boolean missing;

	public static ADUIElementGroupNameFQ missing(@NonNull final UIElementGroupId uiElementGroupId)
	{
		return builder().uiElementGroupId(uiElementGroupId).missing(true).build();
	}

	@Override
	public String toString() {return toShortString();}

	public String toShortString()
	{
		if (missing)
		{
			return "<" + uiElementGroupId.getRepoId() + ">";
		}
		else
		{
			final StringBuilder sb = new StringBuilder();
			if (uiColumnName != null)
			{
				sb.append(uiColumnName.toShortString());
			}

			if (sb.length() > 0)
			{
				sb.append(" -> ");
			}

			sb.append(name);

			return sb.toString();
		}
	}
}
