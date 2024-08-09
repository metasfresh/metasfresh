package de.metas.util.text.tabular;

import com.google.common.base.Strings;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;

class TabularStringWriter
{
	@NonNull private final String identString;
	private final StringBuilder result = new StringBuilder();
	private StringBuilder line = null;

	@Builder
	private TabularStringWriter(final int ident)
	{
		this.identString = ident > 0 ? Strings.repeat("\t", ident) : "";
	}

	public String getAsString()
	{
		lineEnd();
		return result.toString();
	}

	public void appendCell(final String value, int width)
	{
		final String valueNorm = CoalesceUtil.coalesceNotNull(value, "");

		if (line == null)
		{
			line = new StringBuilder();
			line.append(identString);
		}
		if (line.length() == 0)
		{
			line.append("|");
		}

		line.append(" ").append(Strings.padEnd(valueNorm, width, ' ')).append(" |");
	}

	public void lineEnd()
	{
		if (line != null)
		{
			if (result.length() > 0)
			{
				result.append("\n");
			}
			result.append(line);
		}

		line = null;
	}
}
