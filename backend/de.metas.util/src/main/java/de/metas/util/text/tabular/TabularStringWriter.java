package de.metas.util.text.tabular;

import com.google.common.base.Strings;
import de.metas.common.util.CoalesceUtil;

class TabularStringWriter
{
	private final StringBuilder result = new StringBuilder();
	private StringBuilder line = null;

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
