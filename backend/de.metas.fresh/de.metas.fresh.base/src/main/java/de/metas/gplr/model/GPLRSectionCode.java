package de.metas.gplr.model;

import de.metas.sectionCode.SectionCode;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRSectionCode
{
	@NonNull String code;
	@Nullable String name;

	public static GPLRSectionCode of(SectionCode sectionCode)
	{
		return builder()
				.code(sectionCode.getValue())
				.name(sectionCode.getName())
				.build();
	}

	@Override
	public String toString()
	{
		return toRenderedString();
	}

	public String toRenderedString()
	{
		StringBuilder result = new StringBuilder();
		result.append(code.trim());
		if (name != null && !Check.isBlank(name))
		{
			if (result.length() > 0)
			{
				result.append(" ");
			}
			result.append(name.trim());
		}

		return result.toString();
	}
}
