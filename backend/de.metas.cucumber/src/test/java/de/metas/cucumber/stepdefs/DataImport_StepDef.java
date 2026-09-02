/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.pricing.M_PriceList_Version_StepDefData;
import de.metas.impexp.DataImportRequest;
import de.metas.impexp.DataImportService;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_ImpFormat;
import org.compiere.model.I_AD_ImpFormat_Row;
import org.compiere.model.I_C_DataImport;
import org.compiere.model.X_C_DataImport;
import org.compiere.model.I_M_PriceList_Version;
import org.springframework.core.io.ByteArrayResource;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Cucumber step definitions for generic data import infrastructure:
 * AD_ImpFormat, C_DataImport, and CSV-based import via DataImportService.
 */
@RequiredArgsConstructor
public class DataImport_StepDef
{
	@NonNull private final AD_ImpFormat_StepDefData impFormatTable;
	@NonNull private final C_DataImport_StepDefData dataImportTable;
	@NonNull private final M_PriceList_Version_StepDefData priceListVersionTable;
	@NonNull private final M_Product_StepDefData productTable;

	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final SpringContextHolder.Lazy<DataImportService> dataImportServiceSupplier = SpringContextHolder.lazyBean(DataImportService.class);

	/**
	 * Creates an AD_ImpFormat record (CSV format, UTF-8).
	 *
	 * @cucumber.columns Identifier (required), TableName (required, e.g. I_Product)
	 */
	@And("metasfresh contains AD_ImpFormat:")
	public void metasfresh_contains_ad_impformat(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createAD_ImpFormat);
	}

	private void createAD_ImpFormat(@NonNull final DataTableRow row)
	{
		final String tableName = row.getAsString("TableName");
		final int adTableId = tableDAO.retrieveTableId(tableName);

		final I_AD_ImpFormat format = InterfaceWrapperHelper.newInstance(I_AD_ImpFormat.class);
		format.setAD_Table_ID(adTableId);
		format.setFormatType("C"); // CSV
		format.setFileCharset("utf-8"); // must match AD_Ref_List value (lowercase)
		format.setName("ImpFormat_" + tableName + "_" + System.nanoTime());

		saveRecord(format);

		row.getAsIdentifier().putOrReplace(impFormatTable, format);
	}

	/**
	 * Creates AD_ImpFormat_Row records for a previously created AD_ImpFormat.
	 * Columns are mapped in the order they appear in the data table (StartNo = 1, 2, 3, ...).
	 *
	 * @cucumber.columns ColumnName (required, I_Product column name), DataType (required, S=String, N=Number, D=Date)
	 */
	@And("AD_ImpFormat identified by {string} has columns:")
	public void ad_impformat_has_columns(@NonNull final String formatIdentifier, @NonNull final DataTable dataTable)
	{
		final I_AD_ImpFormat format = impFormatTable.get(StepDefDataIdentifier.ofString(formatIdentifier));
		final String tableName = tableDAO.retrieveTableName(format.getAD_Table_ID());

		// Delete any pre-existing rows that may collide with the newly assigned format ID
		// (the preloaded DB may contain AD_ImpFormat_Row records at this ID range)
		queryBL.createQueryBuilder(I_AD_ImpFormat_Row.class)
				.addEqualsFilter(I_AD_ImpFormat_Row.COLUMNNAME_AD_ImpFormat_ID, format.getAD_ImpFormat_ID())
				.create()
				.delete();

		final List<DataTableRow> rows = DataTableRows.of(dataTable).toList();
		for (int i = 0; i < rows.size(); i++)
		{
			final DataTableRow row = rows.get(i);
			final String columnName = row.getAsString("ColumnName");
			final String dataType = row.getAsString("DataType");
			final int adColumnId = tableDAO.retrieveColumn(tableName, columnName).getAD_Column_ID();

			final I_AD_ImpFormat_Row formatRow = InterfaceWrapperHelper.newInstance(I_AD_ImpFormat_Row.class);
			formatRow.setAD_ImpFormat_ID(format.getAD_ImpFormat_ID());
			formatRow.setAD_Column_ID(adColumnId);
			formatRow.setName(columnName);
			formatRow.setDataType(dataType);
			formatRow.setStartNo(i + 1); // 1-based CSV column position
			formatRow.setSeqNo((i + 1) * 10); // SeqNo for ordering
			formatRow.setDecimalPoint("."); // mandatory column

			saveRecord(formatRow);
		}
	}

	/**
	 * Creates a C_DataImport configuration record.
	 *
	 * @cucumber.columns Identifier (required), AD_ImpFormat_ID (required, identifier ref to AD_ImpFormat)
	 */
	@And("metasfresh contains C_DataImport:")
	public void metasfresh_contains_c_dataimport(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createC_DataImport);
	}

	private void createC_DataImport(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier formatIdentifier = row.getAsIdentifier(I_C_DataImport.COLUMNNAME_AD_ImpFormat_ID);
		final I_AD_ImpFormat format = impFormatTable.get(formatIdentifier);

		final I_C_DataImport config = InterfaceWrapperHelper.newInstance(I_C_DataImport.class);
		config.setAD_ImpFormat_ID(format.getAD_ImpFormat_ID());
		config.setDataImport_ConfigType(X_C_DataImport.DATAIMPORT_CONFIGTYPE_Standard);
		config.setInternalName("DataImport_" + System.nanoTime());

		saveRecord(config);

		row.getAsIdentifier().putOrReplace(dataImportTable, config);
	}

	/**
	 * Imports CSV data using the full DataImportService pipeline (CSV → AD_ImpFormat parsing → staging table → import process → target table).
	 * <p>
	 * The data table columns must match the AD_ImpFormat column names (I_Product column names).
	 * For columns ending with {@code _ID}, identifier references are resolved to actual DB IDs
	 * from the appropriate StepDefData.
	 * <p>
	 * Missing columns (columns defined in the format but not in the data table) are left empty in the CSV.
	 */
	@When("the following CSV data is imported using C_DataImport identified by {string}:")
	public void import_csv_data(@NonNull final String dataImportIdentifier, @NonNull final DataTable dataTable)
	{
		final I_C_DataImport config = dataImportTable.get(StepDefDataIdentifier.ofString(dataImportIdentifier));
		final DataImportConfigId configId = DataImportConfigId.ofRepoId(config.getC_DataImport_ID());

		// Load the format columns ordered by StartNo
		final List<I_AD_ImpFormat_Row> formatColumns = queryBL.createQueryBuilder(I_AD_ImpFormat_Row.class)
				.addEqualsFilter(I_AD_ImpFormat_Row.COLUMNNAME_AD_ImpFormat_ID, config.getAD_ImpFormat_ID())
				.orderBy()
				.addColumn(I_AD_ImpFormat_Row.COLUMNNAME_StartNo)
				.endOrderBy()
				.create()
				.list();

		// Build CSV from data table
		final List<DataTableRow> rows = DataTableRows.of(dataTable).toList();
		final List<String> csvLines = new ArrayList<>();

		for (final DataTableRow row : rows)
		{
			final List<String> csvValues = formatColumns.stream()
					.map(col -> resolveCSVValue(row, col.getName()))
					.collect(Collectors.toList());
			csvLines.add(String.join(",", csvValues));
		}

		final String csvContent = String.join("\n", csvLines);

		// Run the full import pipeline
		dataImportServiceSupplier.get().importDataFromResource(DataImportRequest.builder()
				.data(new ByteArrayResource(csvContent.getBytes(StandardCharsets.UTF_8)))
				.dataImportConfigId(configId)
				.clientId(ClientId.METASFRESH)
				.orgId(ORG_ID)
				.userId(UserId.METASFRESH)
				.completeDocuments(false)
				.processImportRecordsSynchronously(true)
				.stopOnFirstError(true)
				.build());
	}

	private String resolveCSVValue(@NonNull final DataTableRow row, @NonNull final String columnName)
	{
		final String value = row.getAsOptionalString(columnName).orElse("");
		if (value.isEmpty())
		{
			return "";
		}

		// For FK columns ending with _ID, try to resolve identifier references
		if (columnName.endsWith("_ID"))
		{
			return resolveIdentifierToId(columnName, value);
		}

		return value;
	}

	private String resolveIdentifierToId(@NonNull final String columnName, @NonNull final String value)
	{
		// If it's already a number, return as-is
		try
		{
			Integer.parseInt(value);
			return value;
		}
		catch (final NumberFormatException ignored)
		{
			// Not a number — try to resolve as identifier
		}

		final StepDefDataIdentifier identifier = StepDefDataIdentifier.ofString(value);

		if (columnName.equals("M_PriceList_Version_ID"))
		{
			final I_M_PriceList_Version plv = priceListVersionTable.get(identifier);
			return String.valueOf(plv.getM_PriceList_Version_ID());
		}

		if (columnName.equals("M_Product_ID"))
		{
			final org.compiere.model.I_M_Product product = productTable.get(identifier);
			return String.valueOf(product.getM_Product_ID());
		}

		// Unknown FK column — return value as-is (might be a text lookup value)
		return value;
	}
}
