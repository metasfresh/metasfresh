/**
 * 
 */
package de.metas.fresh.product.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Font;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import de.metas.impexp.excel.ArrayExcelExporter;
import de.metas.impexp.excel.service.ExcelExporterService;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.fresh.base
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ExportProductSpecifications extends JavaProcess
{

	private final static String tableName = "\"de.metas.fresh\".product_specifications_v";
	final ExcelExporterService excelExporterService = Adempiere.getBean(ExcelExporterService.class);

	@Override
	protected String doIt() throws Exception
	{

		final List<List<Object>> data = excelExporterService.getDataFromSQL(getSql());
		final File tempFile = ArrayExcelExporter.builder()
				.ctx(getCtx())
				.data(data)
				.columnHeaders(getColumnHeaders())
				.build()
				.setFontCharset(Font.ANSI_CHARSET)
				.exportToTempFile();

		final boolean backEndOrSwing = Ini.getRunMode() == RunMode.BACKEND || Ini.isSwingClient();

		if (backEndOrSwing)
		{
			Env.startBrowser(tempFile.toURI().toString());
		}
		else
		{
			getResult().setReportData(tempFile);
		}

		return MSG_OK;
	}

	private String getSql()
	{
		final StringBuffer sb = new StringBuffer();
		sb.append("SELECT productName, CustomerLabelName, additional_produktinfos, productValue, UPC, weight, country, piName, piQty, ")
				.append("guaranteedaysmin, warehouse_temperature, productDecription, componentName, componentIngredients, qtybatch, ")
				.append("allergen, nutritionName, nutritionqty FROM ")
				.append(tableName)
				.append(" ORDER BY productValue ");

		return sb.toString();
	}

	private List<String> getColumnHeaders()
	{
		final List<String> columnHeaders = new ArrayList<>();
		columnHeaders.add("ProductName");
		columnHeaders.add("CustomerLabelName");
		columnHeaders.add("Additional_produktinfos");
		columnHeaders.add("ProductValue");
		columnHeaders.add("UPC");
		columnHeaders.add("NetWeight");
		columnHeaders.add("Country");
		columnHeaders.add("IsPackagingMaterial");
		columnHeaders.add("NumberOfEvents");
		columnHeaders.add("ShelfLifeDays");
		columnHeaders.add("Warehouse_temperature");
		columnHeaders.add("ProductDescription");
		columnHeaders.add("M_BOMProduct_ID");
		columnHeaders.add("Ingredients");
		columnHeaders.add("QtyBatch");
		columnHeaders.add("Allergen");
		columnHeaders.add("M_Product_Nutrition_ID");
		columnHeaders.add("NutritionQty");

		return columnHeaders;
	}
}
