package org.adempiere.ad.migration.validator.sql_migration_context_info.names;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdUIColumnId;

@Value
@Builder
public class ADUIColumnNameFQ
{
	int seqNo;

	ADUISectionNameFQ uiSectionName;

	String missing;

	public static ADUIColumnNameFQ missing(@NonNull final AdUIColumnId uiColumnId)
	{
		return builder().missing("<" + uiColumnId.getRepoId() + ">").build();
	}

	@Override
	public String toString() {return toShortString();}

	public String toShortString()
	{
		if (missing != null)
		{
			return missing;
		}
		else
		{
			final StringBuilder sb = new StringBuilder();
			if (uiSectionName != null)
			{
				sb.append(uiSectionName.toShortString());
			}

			if (sb.length() > 0)
			{
				sb.append(" -> ");
			}

			sb.append(seqNo);

			return sb.toString();
		}
	}
}
