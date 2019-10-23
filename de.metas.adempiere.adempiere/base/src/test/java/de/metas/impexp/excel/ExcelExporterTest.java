package de.metas.impexp.excel;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ExcelExporterTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void exportMoreThanAllowedNumberOfRowsPerSheet() throws IOException
	{
		final ExcelFormat excelFormat = ExcelFormats.EXCEL97;

		final List<List<Object>> data = generateData(excelFormat.getLastRowIndex() * 2, 1);
		System.out.println("Generated " + data.size() + " rows");

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final Workbook workbook = ArrayExcelExporter.builder()
				.excelFormat(excelFormat)
				.constants(ExcelExportConstants.builder()
						// .maxRowsToAllowCellWidthAutoSize(0) // disable auto-sizing, to speed up
						.build())
				.data(data)
				.build()
				.exportToWorkbook();
		System.out.println("Exported to workbook in " + stopwatch.stop());

		assertThat(workbook.getNumberOfSheets()).isEqualTo(2);
		assertThat(workbook.getSheetAt(0).getLastRowNum()).isEqualTo(excelFormat.getLastRowIndex());
		assertThat(workbook.getSheetAt(1).getLastRowNum()).isEqualTo(excelFormat.getLastRowIndex() - 1);
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
