/**
 *
 */
package de.metas.fresh.product.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Font;
import org.compiere.Adempiere.RunMode;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import de.metas.impexp.spreadsheet.excel.JdbcExcelExporter;
import de.metas.impexp.spreadsheet.service.SpreadsheetExporterService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductDAO;
import de.metas.util.Services;

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
public class ExportProductSpecifications extends JavaProcess implements IProcessPrecondition
{

	private final static String tableName = "\"de.metas.fresh\".product_specifications_v";
	private final SpreadsheetExporterService spreadsheetExporterService = SpringContextHolder.instance.getBean(SpreadsheetExporterService.class);

	@Override
	protected String doIt() throws Exception
	{
		final JdbcExcelExporter jdbcExcelExporter = JdbcExcelExporter.builder()
				.ctx(getCtx())
				.columnHeaders(getColumnHeaders())
				.build();

		jdbcExcelExporter.setFontCharset(Font.ANSI_CHARSET);

		spreadsheetExporterService.processDataFromSQL(getSql(), jdbcExcelExporter);

		final File tempFile = jdbcExcelExporter.getResultFile();

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

		final I_M_Product product = Services.get(IProductDAO.class).getById(getRecord_ID());

		final StringBuffer sb = new StringBuffer();
		sb.append("SELECT productName, CustomerLabelName, additional_produktinfos, productValue, UPC, weight, country, guaranteedaysmin, ")
				.append("warehouse_temperature, productDecription, componentName,  IsPackagingMaterial,componentIngredients, qtybatch, ")
				.append("allergen, nutritionName, nutritionqty FROM ")
				.append(tableName)
				.append(" WHERE ")
				.append(tableName).append(".productValue = '").append(product.getValue()).append("'")
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
		columnHeaders.add("ShelfLifeDays");
		columnHeaders.add("Warehouse_temperature");
		columnHeaders.add("ProductDescription");
		columnHeaders.add("M_BOMProduct_ID");
		columnHeaders.add("IsPackagingMaterial");
		columnHeaders.add("Ingredients");
		columnHeaders.add("QtyBatch");
		columnHeaders.add("Allergen");
		columnHeaders.add("M_Product_Nutrition_ID");
		columnHeaders.add("NutritionQty");

		return columnHeaders;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}
}
