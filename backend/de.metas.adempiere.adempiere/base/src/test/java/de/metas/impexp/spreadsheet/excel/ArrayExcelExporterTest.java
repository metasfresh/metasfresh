/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.impexp.spreadsheet.excel;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.metas.impexp.spreadsheet.excel.ArrayExcelExporter;
import de.metas.impexp.spreadsheet.excel.ExcelExportConstants;
import de.metas.impexp.spreadsheet.excel.ExcelFormat;
import de.metas.impexp.spreadsheet.excel.ExcelFormats;
import org.adempiere.test.AdempiereTestHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class ArrayExcelExporterTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/** Create twice the size of records supported by one EXCEL97 sheet. Then verify that two sheets are created */
	@Test
	public void exportMoreThanAllowedNumberOfRowsPerSheet() throws IOException
	{
		final ExcelFormat excelFormat = ExcelFormats.EXCEL97;

		assertThat(excelFormat.getLastRowIndex()).isEqualByComparingTo(65535); // guard
		final List<List<Object>> data = generateData(excelFormat.getLastRowIndex() * 2, 1);
		assertThat(data).hasSize(131070); // 65535 * 2
		assertThat(data.get(data.size() - 1).get(0)).isEqualTo("cell 131069 x 0");
		// System.out.println("Generated " + data.size() + " rows");
		// final Stopwatch stopwatch = Stopwatch.createStarted();

		final Workbook workbook = ArrayExcelExporter.builder()
				.excelFormat(excelFormat)
				.constants(ExcelExportConstants.builder()
						// .maxRowsToAllowCellWidthAutoSize(0) // disable auto-sizing, to speed up
						.build())
				.data(data)
				.build()
				.exportToWorkbook();
		// System.out.println("Exported to workbook in " + stopwatch.stop());

		final int firstSheetLastRowNum = workbook.getSheetAt(0).getLastRowNum();
		assertThat(firstSheetLastRowNum).isEqualTo(65535);

		final int secondSheetLastRowNum = workbook.getSheetAt(1).getLastRowNum();

		assertThat(workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue()).isEqualTo("cell 0 x 0");
		assertThat(workbook.getSheetAt(0).getRow(1).getCell(0).getStringCellValue()).isEqualTo("cell 1 x 0");
		assertThat(workbook.getSheetAt(0).getRow(firstSheetLastRowNum - 1).getCell(0).getStringCellValue()).isEqualTo("cell 65534 x 0");
		assertThat(workbook.getSheetAt(0).getRow(firstSheetLastRowNum).getCell(0).getStringCellValue()).isEqualTo("cell 65535 x 0");

		assertThat(workbook.getSheetAt(1).getRow(0).getCell(0).getStringCellValue()).isEqualTo("cell 65536 x 0");
		assertThat(workbook.getSheetAt(1).getRow(1).getCell(0).getStringCellValue()).isEqualTo("cell 65537 x 0");
		assertThat(workbook.getSheetAt(1).getRow(secondSheetLastRowNum - 1).getCell(0).getStringCellValue()).isEqualTo("cell 131068 x 0");
		assertThat(workbook.getSheetAt(1).getRow(secondSheetLastRowNum).getCell(0).getStringCellValue()).isEqualTo("cell 131069 x 0");

		assertThat(workbook.getNumberOfSheets()).isEqualTo(2);
	}

	@Test
	public void exportToWorkbook_3rows_header() throws IOException
	{
		final ExcelFormat excelFormat = ExcelFormats.EXCEL97;

		final List<List<Object>> data = generateData(3, 1);

		final Workbook workbook = ArrayExcelExporter.builder()
				.excelFormat(excelFormat)
				.columnHeaders(ImmutableList.of("header"))
				.constants(ExcelExportConstants.builder().build())
				.data(data)
				.build()
				.exportToWorkbook();

		assertThat(workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue()).isEqualTo("header");
		assertThat(workbook.getSheetAt(0).getRow(1).getCell(0).getStringCellValue()).isEqualTo("cell 0 x 0");
		assertThat(workbook.getSheetAt(0).getRow(2).getCell(0).getStringCellValue()).isEqualTo("cell 1 x 0");
		assertThat(workbook.getSheetAt(0).getRow(3).getCell(0).getStringCellValue()).isEqualTo("cell 2 x 0");

		assertThat(workbook.getNumberOfSheets()).isEqualTo(1);
		assertThat(workbook.getSheetAt(0).getLastRowNum()).isEqualTo(3);
	}

	@Test
	public void exportToWorkbook_3rows_no_header() throws IOException
	{
		final ExcelFormat excelFormat = ExcelFormats.EXCEL97;

		final List<List<Object>> data = generateData(3, 1);

		final Workbook workbook = ArrayExcelExporter.builder()
				.excelFormat(excelFormat)
				.constants(ExcelExportConstants.builder().build())
				.data(data)
				.build()
				.exportToWorkbook();

		assertThat(workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue()).isEqualTo("cell 0 x 0");
		assertThat(workbook.getSheetAt(0).getRow(1).getCell(0).getStringCellValue()).isEqualTo("cell 1 x 0");
		assertThat(workbook.getSheetAt(0).getRow(2).getCell(0).getStringCellValue()).isEqualTo("cell 2 x 0");

		assertThat(workbook.getNumberOfSheets()).isEqualTo(1);
		assertThat(workbook.getSheetAt(0).getLastRowNum()).isEqualTo(2);
	}

	private List<List<Object>> generateData(final int rowsCount, final int colsCount)
	{
		final ArrayList<List<Object>> rowsList = new ArrayList<>(rowsCount);
		for (int rowNo = 0; rowNo < rowsCount; rowNo++)
		{
			final ArrayList<Object> row = new ArrayList<>(colsCount);
			for (int colNo = 0; colNo < colsCount; colNo++)
			{
				row.add("cell " + rowNo + " x " + colNo);
			}

			rowsList.add(row);
		}

		return rowsList;
	}

	@Test
	public void test_useStreamingWorkbookImplementation_true()
	{
		final ArrayExcelExporter exporter = ArrayExcelExporter.builder()
				.excelFormat(ExcelFormats.EXCEL_OPEN_XML)
				.constants(ExcelExportConstants.builder()
						.useStreamingWorkbookImplementation(true)
						.build())
				.data(ImmutableList.of())
				.build();

		assertThat(exporter.getWorkbook()).isInstanceOf(SXSSFWorkbook.class);
	}

	@Test
	public void test_useStreamingWorkbookImplementation_false()
	{
		final ArrayExcelExporter exporter = ArrayExcelExporter.builder()
				.excelFormat(ExcelFormats.EXCEL_OPEN_XML)
				.constants(ExcelExportConstants.builder()
						.useStreamingWorkbookImplementation(false)
						.build())
				.data(ImmutableList.of())
				.build();

		assertThat(exporter.getWorkbook()).isInstanceOf(XSSFWorkbook.class);
	}

}
