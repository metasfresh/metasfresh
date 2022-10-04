package org.adempiere.ad.migration.validator.sql_migration_context_info.names;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdUISectionId;

@Value
@Builder
public class ADUISectionNameFQ
{
	@NonNull AdUISectionId adUISectionId;
	String value;

	ADTabNameFQ tabName;

	boolean missing;

	public static ADUISectionNameFQ missing(@NonNull final AdUISectionId adUISectionId)
	{
		return builder()
				.adUISectionId(adUISectionId)
				.missing(true)
				.build();
	}

	@Override
	public String toString() {return toShortString();}

	public String toShortString()
	{
		if (missing)
		{
			return "<" + adUISectionId.getRepoId() + ">";
		}
		else
		{
			final StringBuilder sb = new StringBuilder();
			if (tabName != null)
			{
				sb.append(tabName.toShortString());
			}

			if (sb.length() > 0)
			{
				sb.append(" -> ");
			}

			sb.append(value);

			return sb.toString();
		}
	}

}
