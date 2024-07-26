package org.adempiere.ad.wrapper;

import com.google.common.base.Strings;
import de.metas.common.util.CoalesceUtil;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.table.api.ColumnName;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
class ToTabularString
{
	public static String fromPOJOWrappers(final Collection<Object> records, int ident)
	{
		final Table table = toTable(records).orElse(null);
		if (table == null)
		{
			return "";
		}

		final StringBuilder result = new StringBuilder();
		table.removeColumnsWithSameValue().ifPresent(commonValueColumns -> result.append(commonValueColumns.toTabularString()));

		final String differentValueColumnsStr = table.toTabularString();
		if (!Check.isBlank(differentValueColumnsStr))
		{
			if (result.length() > 0)
			{
				result.append("\n\n");
			}
			result.append(differentValueColumnsStr);
		}

		String resultStr = result.toString();

		if (ident > 0)
		{
			final String identStr = Strings.repeat("\t", ident);
			resultStr = identStr + resultStr.replace("\n", "\n" + identStr);
		}

		return resultStr;
	}

	private static Optional<Table> toTable(final Collection<Object> records)
	{
		if (records.isEmpty())
		{
			return Optional.empty();
		}

		final Table table = new Table();
		String idColumnName = null;

		for (final Object recordObj : records)
		{
			final POJOWrapper record = POJOWrapper.getWrapper(recordObj);
			if (record == null)
			{
				throw new AdempiereException("Record " + record + " is not based on " + POJOWrapper.class);
			}

			table.addHeaderFromStrings(record.getColumnNames());
			table.addRow(toRow(record));

			if (idColumnName == null)
			{
				idColumnName = record.getIdColumnName();
			}
		}

		table.removeColumnsWithBlankValues();
		table.moveColumnsToStart(idColumnName);
		table.moveColumnsToEnd("AD_Client_ID", "AD_Org_ID", "IsActive", "Created", "CreatedBy", "Updated", "UpdatedBy");

		return Optional.of(table);
	}

	private static Row toRow(final POJOWrapper record)
	{
		final Row row = new Row();
		record.getValuesMap().forEach((columnName, valueObj) -> row.put(ColumnName.ofString(columnName), Cell.ofNullable(valueObj)));
		return row;
	}

	//
	//
	//
	//
	//

	@EqualsAndHashCode
	private static class Cell
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

	//
	//
	//
	//
	//

	@EqualsAndHashCode
	@ToString
	private static class Row
	{
		private final HashMap<ColumnName, Cell> map;

		private Row()
		{
			this.map = new HashMap<>();
		}

		@NonNull
		public Cell getCell(@NonNull final ColumnName columnName)
		{
			final Cell value = map.get(columnName);
			return value != null ? value : Cell.NULL;
		}

		private boolean isBlankColumn(final ColumnName columnName)
		{
			final Cell cell = getCell(columnName);
			return cell.isBlank();
		}

		public int getColumnWidth(final ColumnName columnName)
		{
			final Cell cell = getCell(columnName);
			return cell.getWidth();
		}

		public String getCellValue(@NonNull final ColumnName columnName)
		{
			final Cell cell = getCell(columnName);
			return cell.getAsString();
		}

		public void put(@NonNull final ColumnName columnName, @NonNull final Cell value)
		{
			map.put(columnName, value);
		}
	}

	//
	//
	//
	//
	//

	@EqualsAndHashCode
	@ToString
	private static class Table
	{
		private final ArrayList<ColumnName> header = new ArrayList<>();
		private final List<Row> rowsList = new ArrayList<>();
		private final HashMap<ColumnName, Integer> columnWidths = new HashMap<>(); // lazy

		public void addHeaderFromStrings(final Collection<String> columnNames)
		{
			columnNames.stream().map(ColumnName::ofString).forEach(this::addHeader);
		}

		public void addHeader(final ColumnName columnName)
		{
			if (!header.contains(columnName))
			{
				header.add(columnName);
			}
		}

		public void setCell(final int rowIndex, @NonNull final ColumnName columnName, @NonNull final Cell value)
		{
			while (rowIndex >= rowsList.size())
			{
				addRow(new Row());
			}

			rowsList.get(rowIndex).put(columnName, value);
			columnWidths.clear();
		}

		public boolean isEmpty() {return header.isEmpty() || rowsList.isEmpty();}

		public void addRow(@NonNull final Row row)
		{
			rowsList.add(row);
			columnWidths.clear();
		}

		public void removeColumnsWithBlankValues()
		{
			header.removeIf(this::isBlankColumn);
		}

		private boolean isBlankColumn(final ColumnName columnName)
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

				final ColumnName columnNameToMove = ColumnName.ofString(columnNamesToMove[i]);
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

			for (final String columnNameToMoveStr : columnNamesToMove)
			{
				if (columnNameToMoveStr == null)
				{
					continue;
				}

				final ColumnName columnNameToMove = ColumnName.ofString(columnNameToMoveStr);
				if (header.remove(columnNameToMove))
				{
					header.add(columnNameToMove);
				}
			}
		}

		private int getColumnWidth(final ColumnName columnName)
		{
			return columnWidths.computeIfAbsent(columnName, this::computeColumnWidth);
		}

		private int computeColumnWidth(final ColumnName columnName)
		{
			int maxWidth = columnName.getAsString().length();

			for (final Row row : rowsList)
			{
				maxWidth = Math.max(maxWidth, row.getColumnWidth(columnName));
			}

			return maxWidth;
		}

		public Optional<Table> removeColumnsWithSameValue()
		{
			if (rowsList.isEmpty())
			{
				return Optional.empty();
			}

			final Table removedTable = new Table();

			for (final ColumnName columnName : new ArrayList<>(header))
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

		private Optional<Cell> getCommonValue(@NonNull final ColumnName columnName)
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

		public String toTabularString()
		{
			if (header.isEmpty() || rowsList.isEmpty())
			{
				return "";
			}

			final TabularStringWriter writer = new TabularStringWriter();

			//
			// Header
			{
				for (final ColumnName columnName : header)
				{
					writer.appendCell(columnName.getAsString(), getColumnWidth(columnName));
				}

				writer.lineEnd();
			}

			//
			// Rows
			{
				for (final Row row : rowsList)
				{
					writer.lineEnd();

					for (final ColumnName columnName : header)
					{
						writer.appendCell(row.getCellValue(columnName), getColumnWidth(columnName));
					}

					writer.lineEnd();
				}
			}

			return writer.getAsString();

		}
	}

	//
	//
	//
	//
	//

	private static class TabularStringWriter
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

}
