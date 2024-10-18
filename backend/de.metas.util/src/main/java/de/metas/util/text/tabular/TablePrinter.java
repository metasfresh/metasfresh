package de.metas.util.text.tabular;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class TablePrinter
{
	@NonNull private final Table table;
	private int ident = 0;
	@NonNull private final HashMap<String, Integer> columnWidths = new HashMap<>(); // lazy

	TablePrinter(@NonNull final Table table)
	{
		this.table = table;
	}

	public TablePrinter ident(int ident)
	{
		this.ident = ident;
		return this;
	}

	@Override
	public String toString()
	{
		final ArrayList<String> header = table.getHeader();
		final ArrayList<Row> rowsList = table.getRowsList();
		if (header.isEmpty() || rowsList.isEmpty())
		{
			return "";
		}

		final TabularStringWriter writer = TabularStringWriter.builder()
				.ident(ident)
				.build();

		//
		// Header
		{
			for (final String columnName : header)
			{
				writer.appendCell(columnName, getColumnWidth(columnName));
			}

			writer.lineEnd();
		}

		//
		// Rows
		{
			for (final Row row : rowsList)
			{
				writer.lineEnd();

				for (final String columnName : header)
				{
					writer.appendCell(row.getCellValue(columnName), getColumnWidth(columnName));
				}

				writer.lineEnd();
			}
		}

		return writer.getAsString();

	}

	int getColumnWidth(final String columnName)
	{
		return columnWidths.computeIfAbsent(columnName, this::computeColumnWidth);
	}

	private int computeColumnWidth(final String columnName)
	{
		int maxWidth = columnName.length();

		for (final Row row : table.getRowsList())
		{
			maxWidth = Math.max(maxWidth, row.getColumnWidth(columnName));
		}

		return maxWidth;
	}

}
