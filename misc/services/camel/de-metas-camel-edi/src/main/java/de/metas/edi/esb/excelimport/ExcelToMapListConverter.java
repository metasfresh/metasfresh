/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.edi.esb.excelimport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.google.common.io.Closeables;

import lombok.NonNull;

/**
 * Immutable XLS to {@link Map}s list converter.
 *
 * To create a new instance, use {@link #builder()}.
 *
 * @author tsa
 * @task 08839
 */
public class ExcelToMapListConverter
{
	private static final String DEFAULT_UnknownHeaderPrefix = "Unknown_";
	private final String noNameHeaderPrefix;

	private final boolean considerNullStringAsNull;
	public static final String NULLStringMarker = "null";
	private final boolean considerEmptyStringAsNull;

	private final String rowNumberMapKey;
	private final boolean discardRepeatingHeaders;
	private final boolean useTypeColumn;

	public static final Builder builder()
	{
		return new Builder();
	}

	private ExcelToMapListConverter(@NonNull final Builder builder)
	{
		noNameHeaderPrefix = builder.noNameHeaderPrefix;
		considerNullStringAsNull = builder.considerNullStringAsNull;
		considerEmptyStringAsNull = builder.considerEmptyStringAsNull;
		rowNumberMapKey = builder.rowNumberMapKey;
		discardRepeatingHeaders = builder.discardRepeatingHeaders;
		useTypeColumn = builder.useTypeColumn;
	}

	public List<Map<String, Object>> convert(final File file) throws IOException
	{
		InputStream in = null;
		try
		{
			in = new FileInputStream(file);
			return convert(in);
		}
		finally
		{
			Closeables.closeQuietly(in);
		}
	}

	/**
	 * Converts given XLS input stream to a list of maps (each row it's a map of HeaderName to Value).
	 *
	 * NOTE: this method it's also closing the stream.
	 */
	public List<Map<String, Object>> convert(final InputStream in) throws IOException
	{
		HSSFWorkbook workbook = null;
		try
		{
			workbook = new HSSFWorkbook(in);
			final HSSFSheet sheet = workbook.getSheetAt(0);

			Map<Integer, String> columnIndex2headerName = null;
			final List<Map<String, Object>> rowsList = new ArrayList<Map<String, Object>>();
			final Map<String, Object> name2valuePairs = new HashMap<String, Object>();
			for (final Iterator<Row> rowIt = sheet.rowIterator(); rowIt.hasNext();)
			{
				final Row row = rowIt.next();
				final Iterator<Cell> cellIt = row.cellIterator();

				//
				// Detect row type
				RowType rowType = null;
				if (useTypeColumn)
				{
					while (cellIt.hasNext())
					{
						final Cell cell = cellIt.next();
						final String value = getCellValueAsString(cell);
						rowType = RowType.forCodeOrNull(value);
						if (rowType != null)
						{
							break;
						}
					}
				}
				// Autodetect row type
				else
				{
					if (columnIndex2headerName == null)
					{
						rowType = RowType.TableHeader;
					}
					else
					{
						rowType = RowType.TableRow;
					}
				}
				// Skip row if row type was not detected
				if (rowType == null)
				{
					continue;
				}

				//
				// Getheader names
				if (rowType == RowType.TableHeader)
				{
					if (columnIndex2headerName == null)
					{
						columnIndex2headerName = readRow_TableHeader(cellIt);
					}
				}
				//
				// Get row
				else if (rowType == RowType.TableRow)
				{
					final Map<String, Object> rowAsMap = readRow_TableRow(row, columnIndex2headerName);
					if (rowAsMap != null)
					{
						rowsList.add(rowAsMap);
					}
				}
				//
				// Read "Name: Value" pair
				else if (rowType == RowType.NameValuePair)
				{
					final Map<String, Object> row_NameValuePairs = readRow_NameValuePair(cellIt);
					name2valuePairs.putAll(row_NameValuePairs);

				}
				else
				{
					throw new IllegalStateException("Unknown rowtype: " + rowType);
				}
			} // for each row

			//
			// If we collected name2value pairs then add them to each row
			if (!name2valuePairs.isEmpty())
			{
				for (final Map<String, Object> row : rowsList)
				{
					row.putAll(name2valuePairs);
				}
			}

			return rowsList;
		}
		finally
		{
			Closeables.closeQuietly(in);

			final boolean swallowIOException = true;
			Closeables.close(workbook, swallowIOException);
		}
	}

	/**
	 * Read rows of type {@link RowType#TableHeader}.
	 *
	 * @return column index to header name map
	 */
	private Map<Integer, String> readRow_TableHeader(final Iterator<Cell> cellIt)
	{
		final Map<Integer, String> columnIndex2headerName = new LinkedHashMap<Integer, String>();

		while (cellIt.hasNext())
		{
			final Cell cell = cellIt.next();
			final int columnIndex = cell.getColumnIndex();
			final String headerName = getHeaderName(cell);

			columnIndex2headerName.put(columnIndex, headerName);
		}

		return columnIndex2headerName;
	}

	/**
	 * Read rows of type {@link RowType#TableRow}.
	 *
	 * @return header name to value map
	 */
	private Map<String, Object> readRow_TableRow(final Row row, final Map<Integer, String> columnIndex2headerName)
	{
		final Map<String, Object> rowAsMap = new LinkedHashMap<String, Object>(columnIndex2headerName.size());
		for (final Map.Entry<Integer, String> headerColumn : columnIndex2headerName.entrySet())
		{
			final int columnIndex = headerColumn.getKey();
			final String headerName = headerColumn.getValue();
			if (headerName == null)
			{
				continue;
			}

			final Cell cell = row.getCell(columnIndex, Row.RETURN_BLANK_AS_NULL);
			final Object value = getCellValue(cell);

			rowAsMap.put(headerName, value);
		}
		//
		// If we were asked to discard repeating headers, check if this row it's actually a header,
		// and it case it is, discard it.
		// NOTE: we need to do this before adding more entries to our map
		if (discardRepeatingHeaders && isRepeatingHeaderRow(rowAsMap))
		{
			return null;
		}
		//
		// Add RowNumber
		if (rowNumberMapKey != null)
		{
			final int rowNumber = row.getRowNum() + 1; // convert from ZERO based to ONE based (like you see it in Excel)
			rowAsMap.put(rowNumberMapKey, rowNumber);
		}

		return rowAsMap;
	}

	/**
	 * Read rows of type {@link RowType#NameValuePair}.
	 *
	 * @return key name to value map
	 */
	private Map<String, Object> readRow_NameValuePair(final Iterator<Cell> cellIt)
	{
		String name = null;
		Object value = null;
		while (cellIt.hasNext())
		{
			final Cell cell = cellIt.next();
			final Object cellValue = getCellValue(cell);

			// Skip null cells
			if (cellValue == null)
			{
				continue;
			}

			//
			// Case: we are searching for Name
			if (name == null)
			{
				String nameCandidate = cellValue == null ? "" : cellValue.toString().trim();
				if (nameCandidate.isEmpty())
				{
					continue;
				}

				// If the name ends with ":", get rid of that
				// TODO: hardcoded
				if (nameCandidate.endsWith(":"))
				{
					nameCandidate = nameCandidate.substring(0, nameCandidate.length() - 1).trim();
				}
				name = nameCandidate;
			}
			//
			// Case: we are search for Value
			else
			{
				value = cellValue;
				break;
			}
		}

		// If name was not found, return nothing
		if (name == null)
		{
			return Collections.emptyMap();
		}

		return Collections.singletonMap(name, value);
	}

	private boolean isRepeatingHeaderRow(final Map<String, Object> rowAsMap)
	{
		for (final Map.Entry<String, Object> entry : rowAsMap.entrySet())
		{
			final String headerName = entry.getKey();
			final Object value = entry.getValue();

			if (!isRepeatingHeaderValue(headerName, value))
			{
				return false;
			}
		}

		return true;
	}

	private boolean isRepeatingHeaderValue(final String headerName, final Object value)
	{
		if (isNoNameHeaderName(headerName) && isEmptyValue(value))
		{
			return true;
		}

		final String valueStr = value == null ? "" : value.toString().trim();
		return headerName.equals(valueStr);
	}

	private String getHeaderName(final Cell cell)
	{
		final Object headerNameObj = getCellValue(cell);
		if (headerNameObj == null)
		{
			return buildNoNameHeaderName(cell.getColumnIndex());
		}

		final String headerName = headerNameObj.toString().trim();
		if (headerName.isEmpty())
		{
			return buildNoNameHeaderName(cell.getColumnIndex());
		}

		return headerName;
	}

	private final String buildNoNameHeaderName(final int columnIndex)
	{
		if (noNameHeaderPrefix == null)
		{
			return null;
		}
		final String headerName = noNameHeaderPrefix + columnIndex;
		return headerName;
	}

	private final boolean isNoNameHeaderName(final String headerName)
	{
		if (noNameHeaderPrefix == null)
		{
			return false;
		}

		return headerName.startsWith(noNameHeaderPrefix);
	}

	private static final boolean isEmptyValue(final Object value)
	{
		return value == null || value.toString().trim().isEmpty();
	}

	private Object getCellValue(final Cell cell)
	{
		if (cell == null)
		{
			return null;
		}

		final Object value;
		switch (cell.getCellType())
		{
			case Cell.CELL_TYPE_BLANK:
				value = null;
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell))
				{
					value = cell.getDateCellValue();
				}
				else
				{
					value = BigDecimal.valueOf(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				final String valueStr = cell.getCellFormula();
				value = valueStr;
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				final boolean valueBoolean = cell.getBooleanCellValue();
				value = valueBoolean;
				break;
			case Cell.CELL_TYPE_ERROR:
				// TODO: handle the error?!
				value = null;
				break;
			default:
				// shall not happen
				value = null;
				break;
		}

		// Consider null string values as NULL
		if (value instanceof String)
		{
			final String valueStr = (String)value;
			if (considerNullStringAsNull && NULLStringMarker.equals(valueStr))
			{
				return null;
			}
			if (considerEmptyStringAsNull && valueStr.trim().isEmpty())
			{
				return null;
			}
		}

		return value;
	}

	private String getCellValueAsString(final Cell cell)
	{
		final Object value = getCellValue(cell);
		return value == null ? null : value.toString();
	}

	public enum RowType
	{
		TableHeader("H"),
		TableRow("R"),
		NameValuePair("V");

		private final String code;

		private RowType(final String code)
		{
			this.code = code;
		}

		public String getCode()
		{
			return code;
		}

		public static RowType forCodeOrNull(final String code)
		{
			if (code == null)
			{
				return null;
			}
			for (final RowType d : values())
			{
				if (d.getCode().equals(code))
				{
					return d;
				}
			}

			return null;
		}
	}

	public static class Builder
	{
		private String noNameHeaderPrefix = DEFAULT_UnknownHeaderPrefix;
		private boolean considerNullStringAsNull = false;
		private boolean considerEmptyStringAsNull = false;
		private String rowNumberMapKey = null;
		private boolean discardRepeatingHeaders = false;
		private boolean useTypeColumn = true;

		private Builder()
		{
			super();
		}

		public ExcelToMapListConverter build()
		{
			return new ExcelToMapListConverter(this);
		}

		/**
		 * Sets the header prefix to be used if the header columns is null or black.
		 *
		 * Set it to <code>null</code> to turn it off and get rid of columns without header.
		 *
		 * @param noNameHeaderPrefix
		 * @see #setDiscardNoNameHeaders()
		 */
		public Builder setNoNameHeaderPrefix(final String noNameHeaderPrefix)
		{
			this.noNameHeaderPrefix = noNameHeaderPrefix;
			return this;
		}

		public Builder setDiscardNoNameHeaders()
		{
			setNoNameHeaderPrefix(null);
			return this;
		}

		/**
		 * Sets if a cell value which is a null string (i.e. {@link ExcelToMapListConverter#NULLStringMarker}) shall be considered as <code>null</code>.
		 *
		 * @param considerNullStringAsNull
		 */
		public Builder setConsiderNullStringAsNull(final boolean considerNullStringAsNull)
		{
			this.considerNullStringAsNull = considerNullStringAsNull;
			return this;
		}

		public Builder setConsiderEmptyStringAsNull(final boolean considerEmptyStringAsNull)
		{
			this.considerEmptyStringAsNull = considerEmptyStringAsNull;
			return this;
		}

		public Builder setRowNumberMapKey(final String rowNumberMapKey)
		{
			this.rowNumberMapKey = rowNumberMapKey;
			return this;
		}

		/**
		 * Sets if we shall detect repeating headers and discard them.
		 *
		 * @param discardRepeatingHeaders
		 */
		public Builder setDiscardRepeatingHeaders(final boolean discardRepeatingHeaders)
		{
			this.discardRepeatingHeaders = discardRepeatingHeaders;
			return this;
		}

		/**
		 * If enabled, the XLS converter will look for first not null column and it will expect to have one of the codes from {@link RowType}.
		 * If no row type would be found, the row would be ignored entirely.
		 *
		 * @param useTypeColumn
		 * @task 09045
		 */
		public Builder setUseRowTypeColumn(final boolean useTypeColumn)
		{
			this.useTypeColumn = useTypeColumn;
			return this;
		}
	}
}
