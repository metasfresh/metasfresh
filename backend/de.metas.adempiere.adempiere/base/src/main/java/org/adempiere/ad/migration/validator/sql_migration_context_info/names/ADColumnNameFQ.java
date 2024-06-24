package org.adempiere.ad.migration.validator.sql_migration_context_info.names;

import lombok.Builder;
import lombok.Value;
import org.adempiere.ad.column.AdColumnId;

import javax.annotation.Nullable;

@Value
@Builder
public class ADColumnNameFQ
{
	@Nullable AdColumnId adColumnId;
	@Nullable String columnName;
	@Nullable String entityType;

	@Nullable ADTableName tableName;

	@Nullable String missing;

	public static ADColumnNameFQ missing(final String missing)
	{
		return builder().missing(missing).build();
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
			if (tableName != null)
			{
				sb.append(tableName.toShortString());
			}

			if (sb.length() > 0)
			{
				sb.append(".");
			}

			sb.append(columnName);

			return sb.toString();
		}
	}

}
