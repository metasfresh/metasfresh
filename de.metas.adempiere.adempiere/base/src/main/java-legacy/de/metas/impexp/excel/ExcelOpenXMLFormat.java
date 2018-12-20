package de.metas.impexp.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
 * Excel Open XML format (XLSX)
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
final class ExcelOpenXMLFormat implements ExcelFormat
{
	@Override
	public String getFileExtension()
	{
		return "xlsx";
	}

	@Override
	public String getName()
	{
		return "Excel (XML)";
	}

	@Override
	public Workbook createWorkbook()
	{
		return new XSSFWorkbook();
	}

	@Override
	public String getCurrentPageMarkupTag()
	{
		// see XSSFHeaderFooter javadoc
		return "&P";
	}

	@Override
	public String getTotalPagesMarkupTag()
	{
		// see XSSFHeaderFooter javadoc
		return "&N";
	}

}
