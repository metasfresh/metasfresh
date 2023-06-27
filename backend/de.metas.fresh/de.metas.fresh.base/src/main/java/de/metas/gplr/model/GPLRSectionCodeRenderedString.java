package de.metas.gplr.model;

import de.metas.sectionCode.SectionCode;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class GPLRSectionCodeRenderedString
{
	String renderedString;

	public static GPLRSectionCodeRenderedString of(SectionCode sectionCode)
	{
		return new GPLRSectionCodeRenderedString(toRenderedString(sectionCode.getValue(), sectionCode.getName()));
	}

	@Nullable
	public static GPLRSectionCodeRenderedString ofNullableRenderedString(@Nullable String renderedString)
	{
		return StringUtils.trimBlankToOptional(renderedString).map(GPLRSectionCodeRenderedString::new).orElse(null);
	}

	private static String toRenderedString(@NonNull final String code, final String name)
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

	@Override
	public String toString()
	{
		return toRenderedString();
	}

	public String toRenderedString() {return renderedString;}
}
