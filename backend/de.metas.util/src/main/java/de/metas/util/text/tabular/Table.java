package de.metas.util.text.tabular;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;

@EqualsAndHashCode
public class Table
{
	@Getter(AccessLevel.PACKAGE) private final ArrayList<String> header = new ArrayList<>();
	@Getter(AccessLevel.PACKAGE) private final ArrayList<Row> rowsList = new ArrayList<>();

	public void addHeaderFromStrings(final Collection<String> columnNames)
	{
		columnNames.forEach(this::addHeader);
	}

	public void addHeader(final String columnName)
	{
		if (!header.contains(columnName))
		{
			header.add(columnName);
		}
	}

	public void updateHeaderFromRows()
	{
		final LinkedHashSet<String> headerNew = new LinkedHashSet<>();
		for (final Row row : rowsList)
		{
			headerNew.addAll(row.getColumnNames());
		}

		header.clear();
		header.addAll(headerNew);
	}

	public void setCell(final int rowIndex, @NonNull final String columnName, @NonNull final Cell value)
	{
		while (rowIndex >= rowsList.size())
		{
			addRow(new Row());
		}

		rowsList.get(rowIndex).put(columnName, value);
	}

	public boolean isEmpty() {return header.isEmpty() || rowsList.isEmpty();}

	public void addRow(@NonNull final Row row)
	{
		rowsList.add(row);
	}

	public void addRows(@NonNull final Collection<Row> rows)
	{
		rowsList.addAll(rows);
	}

	public void addRows(@NonNull final Table other)
	{
		rowsList.addAll(other.rowsList);
	}

	public void removeColumnsWithBlankValues()
	{
		header.removeIf(this::isBlankColumn);
	}

	private boolean isBlankColumn(final String columnName)
	{
		return rowsList.stream().allMatch(row -> row.isBlankColumn(columnName));
	}

	public void moveColumnsToStart(final String... columnNamesToMove)
	{
		if (columnNamesToMove == null || columnNamesToMove.length == 0)
		{
			return;
		}

		for (int i = columnNamesToMove.length - 1; i >= 0; i--)
		{
			if (columnNamesToMove[i] == null)
			{
				continue;
			}

			final String columnNameToMove = columnNamesToMove[i];
			if (header.remove(columnNameToMove))
			{
				header.add(0, columnNameToMove);
			}
		}
	}

	public void moveColumnsToEnd(final String... columnNamesToMove)
	{
		if (columnNamesToMove == null)
		{
			return;
		}

		for (final String columnNameToMove : columnNamesToMove)
		{
			if (columnNameToMove == null)
			{
				continue;
			}

			if (header.remove(columnNameToMove))
			{
				header.add(columnNameToMove);
			}
		}
	}

	public Optional<Table> removeColumnsWithSameValue()
	{
		if (rowsList.isEmpty())
		{
			return Optional.empty();
		}

		final Table removedTable = new Table();

		for (final String columnName : new ArrayList<>(header))
		{
			final Cell commonValue = getCommonValue(columnName).orElse(null);
			if (commonValue != null)
			{
				header.remove(columnName);
				removedTable.addHeader(columnName);
				removedTable.setCell(0, columnName, commonValue);
			}
		}

		if (removedTable.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(removedTable);
	}

	private Optional<Cell> getCommonValue(@NonNull final String columnName)
	{
		if (rowsList.isEmpty())
		{
			return Optional.empty();
		}

		final Cell firstValue = rowsList.get(0).getCell(columnName);

		for (int i = 1; i < rowsList.size(); i++)
		{
			final Cell value = rowsList.get(i).getCell(columnName);
			if (!Objects.equals(value, firstValue))
			{
				return Optional.empty();
			}
		}

		return Optional.of(firstValue);
	}

	public TablePrinter toPrint()
	{
		return new TablePrinter(this);
	}

	public String toTabularString()
	{
		return toPrint().toString();
	}

	@Override
	@Deprecated
	public String toString() {return toTabularString();}
}
