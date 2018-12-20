package de.metas.impexp.excel;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Workbook;

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

/**
 * Excel 97 legacy format
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
final class Excel97Format implements ExcelFormat
{
	@Override
	public String getFileExtension()
	{
		return "xls";
	}

	@Override
	public String getName()
	{
		return "Excel 97";
	}

	@Override
	public Workbook createWorkbook(final boolean useStreamingImplementation_IGNORED)
	{
		return new HSSFWorkbook();
	}

	public String page()
	{
		return HSSFHeader.page();
	}

	@Override
	public String getCurrentPageMarkupTag()
	{
		return HSSFHeader.page();
	}

	@Override
	public String getTotalPagesMarkupTag()
	{
		return HSSFHeader.numPages();
	}

	@Override
	public int getLastRowIndex()
	{
		return SpreadsheetVersion.EXCEL97.getLastRowIndex();
	}
}
