/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.contract;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.cucumber.stepdefs.inventory.M_InventoryLine_StepDefData;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class ModCntr_Log_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	private final C_BPartner_StepDefData bpartnerTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final M_Product_StepDefData productTable;
	private final ModCntr_Log_StepDefData modCntrLogTable;
	private final C_Flatrate_Term_StepDefData flatrateTermTable;
	private final C_Invoice_Candidate_StepDefData invoiceCandidateTable;
	private final ModCntr_Type_StepDefData modCntrTypeTable;
	private final C_Year_StepDefData yearTable;
	private final C_OrderLine_StepDefData orderLineTable;
<<<<<<< HEAD
=======
	private final M_InventoryLine_StepDefData inventoryLineTable;

>>>>>>> 8e91cdc6e3a (Inner silence uat gh15819 (#15836))
	private final M_InOutLine_StepDefData inOutLineTable;

	public ModCntr_Log_StepDef(
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final ModCntr_Log_StepDefData modCntrLogTable,
			@NonNull final C_Flatrate_Term_StepDefData flatrateTermTable,
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandidateTable,
			@NonNull final ModCntr_Type_StepDefData modCntrTypeTable,
			@NonNull final C_Year_StepDefData yearTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_InventoryLine_StepDefData inventoryLineTable,
			@NonNull final M_InOutLine_StepDefData inOutLineTable)
	{
		this.bpartnerTable = bpartnerTable;
		this.warehouseTable = warehouseTable;
		this.productTable = productTable;
		this.modCntrLogTable = modCntrLogTable;
		this.flatrateTermTable = flatrateTermTable;
		this.invoiceCandidateTable = invoiceCandidateTable;
		this.modCntrTypeTable = modCntrTypeTable;
		this.yearTable = yearTable;
		this.orderLineTable = orderLineTable;
		this.inventoryLineTable = inventoryLineTable;
		this.inOutLineTable = inOutLineTable;
	}

	@And("ModCntr_Logs are found:")
	public void validateModCntr_Logs(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			validateModCntr_Log(row);
		}
	}

	private void validateModCntr_Log(@NonNull final Map<String, String> tableRow)
	{
		final String tableName = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Table.COLUMNNAME_TableName);
		final int tableId = tableDAO.retrieveTableId(tableName);

		final BigDecimal quantity = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_ModCntr_Log.COLUMNNAME_Qty);

		final String flatrateTermIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Flatrate_Term flatrateTermRecord = flatrateTermTable.get(flatrateTermIdentifier);

		final String recordIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Log.COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int recordId;
		switch (tableName)
		{
			case I_C_OrderLine.Table_Name -> recordId = orderLineTable.get(recordIdentifier).getC_OrderLine_ID();
			case I_M_InventoryLine.Table_Name -> recordId = inventoryLineTable.get(recordIdentifier).getM_InventoryLine_ID();
			case I_M_InOutLine.Table_Name -> recordId = inOutLineTable.get(recordIdentifier).getM_InOutLine_ID();
			default -> throw new AdempiereException("Unsupported TableName !")
					.appendParametersToMessage()
					.setParameter("TableName", tableName);
		}

		final I_ModCntr_Log modCntrLogRecord = queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_Record_ID, recordId)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermRecord.getC_Flatrate_Term_ID())
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_Qty, quantity)
				.create()
				.firstOnlyOptional()
				.orElseThrow(() -> new AdempiereException("\n Context: " + getLogContextForWarehouseId(FlatrateTermId.ofRepoId(flatrateTermRecord.getC_Flatrate_Term_ID()))));

		final SoftAssertions softly = new SoftAssertions();

		final String collectionPointBPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_CollectionPoint_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(collectionPointBPartnerIdentifier))
		{
			final I_C_BPartner collectionPointBPartnerRecord = bpartnerTable.get(collectionPointBPartnerIdentifier);
			softly.assertThat(modCntrLogRecord.getCollectionPoint_BPartner_ID()).as(I_ModCntr_Log.COLUMNNAME_CollectionPoint_BPartner_ID).isEqualTo(collectionPointBPartnerRecord.getC_BPartner_ID());
		}

		final String warehouseIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(warehouseIdentifier))
		{
			final I_M_Warehouse warehouseRecord = warehouseTable.get(warehouseIdentifier);
			softly.assertThat(modCntrLogRecord.getM_Warehouse_ID()).as(I_ModCntr_Log.COLUMNNAME_M_Warehouse_ID).isEqualTo(warehouseRecord.getM_Warehouse_ID());
		}

		final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(productIdentifier))
		{
			final I_M_Product productRecord = productTable.get(productIdentifier);
			softly.assertThat(modCntrLogRecord.getM_Product_ID()).as(I_ModCntr_Log.COLUMNNAME_M_Product_ID).isEqualTo(productRecord.getM_Product_ID());
		}

		final String producerBPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_Producer_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(producerBPartnerIdentifier))
		{
			final I_C_BPartner producerBPartnerRecord = bpartnerTable.get(producerBPartnerIdentifier);
			softly.assertThat(modCntrLogRecord.getProducer_BPartner_ID()).as(I_ModCntr_Log.COLUMNNAME_Producer_BPartner_ID).isEqualTo(producerBPartnerRecord.getC_BPartner_ID());
		}

		final String billBPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(billBPartnerIdentifier))
		{
			final I_C_BPartner billBPartnerRecord = bpartnerTable.get(billBPartnerIdentifier);
			softly.assertThat(modCntrLogRecord.getBill_BPartner_ID()).as(I_ModCntr_Log.COLUMNNAME_Bill_BPartner_ID).isEqualTo(billBPartnerRecord.getC_BPartner_ID());
		}

		final String invoiceCandidateIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(invoiceCandidateIdentifier))
		{
			final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandidateTable.get(invoiceCandidateIdentifier);
			softly.assertThat(modCntrLogRecord.getC_Invoice_Candidate_ID()).as(I_ModCntr_Log.COLUMNNAME_C_Invoice_Candidate_ID).isEqualTo(invoiceCandidateRecord.getC_Invoice_Candidate_ID());
		}

		final String modCntrTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_ModCntr_Type_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(modCntrTypeIdentifier))
		{
			final I_ModCntr_Type modCntrTypeRecord = modCntrTypeTable.get(modCntrTypeIdentifier);
			softly.assertThat(modCntrLogRecord.getModCntr_Type_ID()).as(I_ModCntr_Log.COLUMNNAME_ModCntr_Type_ID).isEqualTo(modCntrTypeRecord.getModCntr_Type_ID());
		}

		final Boolean isProcessed = DataTableUtil.extractBooleanForColumnNameOrNull(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_Processed);
		if (isProcessed != null)
		{
			softly.assertThat(modCntrLogRecord.isProcessed()).as(I_ModCntr_Log.COLUMNNAME_Processed).isEqualTo(isProcessed);
		}

		final String modCntrLogDocumentType = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_ModCntr_Log_DocumentType);
		if (Check.isNotBlank(modCntrLogDocumentType))
		{
			final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.ofCode(modCntrLogDocumentType);
			softly.assertThat(modCntrLogRecord.getModCntr_Log_DocumentType()).as(I_ModCntr_Log.COLUMNNAME_ModCntr_Log_DocumentType).isEqualTo(logEntryDocumentType.getCode());
		}

		final String currencyIsoCode = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_C_Currency_ID + ".ISO_Code");
		if (Check.isNotBlank(currencyIsoCode))
		{
			final Currency currency = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyIsoCode));
			softly.assertThat(modCntrLogRecord.getC_Currency_ID()).as(I_ModCntr_Log.COLUMNNAME_C_Currency_ID + ".ISO_Code").isEqualTo(currency.getId().getRepoId());
		}

		final String uomCode = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(uomCode))
		{
			final UomId bPartnerUOMId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(uomCode));
			softly.assertThat(modCntrLogRecord.getC_UOM_ID()).as(I_ModCntr_Log.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName()).isEqualTo(bPartnerUOMId.getRepoId());
		}

		final BigDecimal amount = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_Amount);
		if (amount != null)
		{
			softly.assertThat(modCntrLogRecord.getAmount()).as(I_ModCntr_Log.COLUMNNAME_Amount).isEqualTo(amount);
		}

		final String yearIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_Harvesting_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(yearIdentifier))
		{
			final I_C_Year yearRecord = yearTable.get(yearIdentifier);
			softly.assertThat(modCntrLogRecord.getHarvesting_Year_ID()).as(I_ModCntr_Log.COLUMNNAME_Harvesting_Year_ID).isEqualTo(yearRecord.getC_Year_ID());
		}

		final String description = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_Description);
		if (Check.isNotBlank(description))
		{
			softly.assertThat(modCntrLogRecord.getDescription()).as(I_ModCntr_Log.COLUMNNAME_Description).isEqualTo(description);
		}


		softly.assertAll();

		final String modCntrLogIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Log.COLUMNNAME_ModCntr_Log_ID + "." + TABLECOLUMN_IDENTIFIER);
		modCntrLogTable.putOrReplace(modCntrLogIdentifier, modCntrLogRecord);
	}

	@NonNull
	private String getLogContextForWarehouseId(@NonNull final FlatrateTermId flatrateTermId)
	{
		final Stream<I_ModCntr_Log> logsFoundForFlatrateTermId = queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermId)
				.create()
				.iterateAndStream();

		final StringBuilder messageBuilder = new StringBuilder("\n");
		logsFoundForFlatrateTermId
				.forEach(log ->
								 messageBuilder.append(" -> ModCntr_Log_ID: ")
										 .append(log.getModCntr_Log_ID())
										 .append(" M_Product_ID: ")
										 .append(log.getM_Product_ID())
										 .append(" CollectionPoint_BPartner_ID: ")
										 .append(log.getCollectionPoint_BPartner_ID())
										 .append(" Producer_BPartner_ID: ")
										 .append(log.getProducer_BPartner_ID())
										 .append(" Bill_BPartner_ID: ")
										 .append(log.getBill_BPartner_ID())
										 .append("\n"));

		return "Current ModCntr_Logs available for C_Flatrate_Term_ID:\n\n" + messageBuilder;
	}
}
