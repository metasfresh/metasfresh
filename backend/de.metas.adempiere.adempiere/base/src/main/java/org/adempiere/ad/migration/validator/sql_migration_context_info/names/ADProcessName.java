package org.adempiere.ad.migration.validator.sql_migration_context_info.names;

import de.metas.process.AdProcessId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ADProcessName
{
	String value;
	String classname;

	String missing;

	public static ADProcessName missing(@NonNull final AdProcessId adProcessId)
	{
		return builder().missing("<" + adProcessId.getRepoId() + ">").build();
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
			sb.append(value);

			if (!Check.isBlank(classname))
			{
				sb.append("(").append(classname).append(")");
			}

			return sb.toString();
		}
	}

}
