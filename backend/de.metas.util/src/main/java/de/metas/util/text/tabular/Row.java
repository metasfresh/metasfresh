package de.metas.util.text.tabular;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode
@ToString
public class Row
{
	private final LinkedHashMap<String, Cell> map;

	public Row()
	{
		this.map = new LinkedHashMap<>();
	}

	Set<String> getColumnNames()
	{
		return map.keySet();
	}

	@NonNull
	public Cell getCell(@NonNull final String columnName)
	{
		final Cell value = map.get(columnName);
		return value != null ? value : Cell.NULL;
	}

	public boolean isBlankColumn(final String columnName)
	{
		final Cell cell = getCell(columnName);
		return cell.isBlank();
	}

	public int getColumnWidth(final String columnName)
	{
		final Cell cell = getCell(columnName);
		return cell.getWidth();
	}

	public String getCellValue(@NonNull final String columnName)
	{
		final Cell cell = getCell(columnName);
		return cell.getAsString();
	}

	public void put(@NonNull final String columnName, @NonNull final Cell value)
	{
		map.put(columnName, value);
	}

	public void put(@NonNull final String columnName, @Nullable final Object valueObj)
	{
		map.put(columnName, Cell.ofNullable(valueObj));
	}

	public void putAll(@NonNull final Map<String, ?> map)
	{
		map.forEach((columnName, value) -> this.map.put(columnName, Cell.ofNullable(value)));
	}

	public boolean containsColumn(final String columnName)
	{
		return map.containsKey(columnName);
	}

}
