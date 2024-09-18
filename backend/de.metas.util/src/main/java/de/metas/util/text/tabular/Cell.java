package de.metas.util.text.tabular;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;

@EqualsAndHashCode
public class Cell
{
	public static final Cell NULL = new Cell(null, true);
	public static final String NULL_DISPLAY_STRING = "<null>";

	@Nullable private final String valueStr;
	private final boolean isNull;

	private Cell(@Nullable final String valueStr, final boolean isNull)
	{
		this.valueStr = valueStr;
		this.isNull = isNull;
	}

	public static Cell ofNullable(@Nullable final Object valueObj)
	{
		if (valueObj instanceof Cell)
		{
			return (Cell)valueObj;
		}

		final String valueStr = valueObj != null ? valueObj.toString() : null;
		return valueStr != null
				? new Cell(valueStr, false)
				: NULL;
	}

	public String getAsString()
	{
		return isNull ? NULL_DISPLAY_STRING : valueStr;
	}

	public int getWidth()
	{
		return getAsString().length();
	}

	public boolean isBlank()
	{
		return isNull || Check.isBlank(valueStr);
	}
}
