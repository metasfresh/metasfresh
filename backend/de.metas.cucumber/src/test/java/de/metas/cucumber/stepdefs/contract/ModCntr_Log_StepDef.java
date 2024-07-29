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

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.model.I_ModCntr_InvoicingGroup;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogsRecomputationService;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.ScenarioLifeCycleStepDef;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.cucumber.stepdefs.inventory.M_InventoryLine_StepDefData;
import de.metas.cucumber.stepdefs.inventory.M_Inventory_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_InvoiceLine_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.cucumber.stepdefs.shippingNotification.M_Shipping_NotificationLine_StepDefData;
import de.metas.cucumber.stepdefs.shippingNotification.M_Shipping_Notification_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.cucumber.stepdefs.StepDefUtil.writeRowAsString;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ModCntr_Log_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final LogsRecomputationService recomputeLogsService = SpringContextHolder.instance.getBean(LogsRecomputationService.class);

	@NonNull
	private final C_BPartner_StepDefData bpartnerTable;
	@NonNull
	private final M_Warehouse_StepDefData warehouseTable;
	@NonNull
	private final M_Product_StepDefData productTable;
	@NonNull
	private final ModCntr_Log_StepDefData modCntrLogTable;
	@NonNull
	private final C_Flatrate_Term_StepDefData flatrateTermTable;
	@NonNull
	private final C_Invoice_Candidate_StepDefData invoiceCandidateTable;
	@NonNull
	private final ModCntr_Type_StepDefData modCntrTypeTable;
	@NonNull
	private final C_Year_StepDefData yearTable;
	@NonNull
	private final C_OrderLine_StepDefData orderLineTable;
	@NonNull
	private final M_Shipping_NotificationLine_StepDefData shippingNotificationLineStepDefData;
	@NonNull
	private final M_Shipping_Notification_StepDefData shippingNotificationStepDefData;
	@NonNull
	private final M_InventoryLine_StepDefData inventoryLineTable;

	@NonNull
	private final M_InOutLine_StepDefData inOutLineTable;
	@NonNull
	private final PP_Order_StepDefData manufacturingOrderTable;
	@NonNull
	private final C_InvoiceLine_StepDefData invoiceLineTable;
	@NonNull
	private final ScenarioLifeCycleStepDef scenarioLifeCycleStepDef;
	@NonNull
	private final ModCntr_Module_StepDefData modCntrModuleTable;

	@NonNull
	private final C_Invoice_StepDefData invoiceTable;
	@NonNull
	private final C_Order_StepDefData orderTable;
	@NonNull
	private final M_Inventory_StepDefData inventoryTable;
	@NonNull
	private final M_InOut_StepDefData inOutTable;
	@NonNull
	private final ModCntr_InvoicingGroup_StepDefData invoicingGroupTable;
	@NonNull
	private final I_ModCntr_Log_StepDefData importModCntrTable;

	@And("^after not more than (.*)s, ModCntr_Logs are found:$")
	public void validateModCntr_Logs(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			validateModCntr_Log(timeoutSec, row);
		}
	}

	@And("recompute modular logs for record:")
	public void recomputeLogsForRecord(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			recomputeLogsForRecord(row);
		}
	}

	@And("recompute modular log for record expecting error")
	public void recomputeLogsForRecordExpectingError(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		assertThat(tableRows.size())
				.as("Expecting only one row!")
				.isEqualTo(1);

		final Map<String, String> row = tableRows.get(0);

		try
		{
			recomputeLogsForRecord(row);

			Assertions.fail("Expecting error to be thrown");
		}
		catch (final Exception e)
		{
			final String messageKey = DataTableUtil.extractStringForColumnName(row, "ErrorMessage");

			Assertions.assertThat(e.getMessage()).contains(msgBL.getMsg(Env.getCtx(), AdMessageKey.of(messageKey)));
		}
	}

	@And("^after not more than (.*)s, no ModCntr_Logs are found:$")
	public void noModCntr_Logs_found(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		Thread.sleep(timeoutSec);

		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			validateNoModCntr_LogFound(row);
		}
	}

	@And("update ModCntr_Logs:")
	public void update_ModCntr_Logs(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final String logIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_Log.COLUMNNAME_ModCntr_Log_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_ModCntr_Log log = modCntrLogTable.get(logIdentifier);

			final Boolean processed = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_ModCntr_Log.COLUMNNAME_Processed);

			if (processed != null)
			{
				log.setProcessed(processed);
			}

			InterfaceWrapperHelper.saveRecord(log);
		}
	}

	private void validateModCntr_Log(final int timeoutSec, @NonNull final Map<String, String> tableRow) throws InterruptedException
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
			case I_PP_Order.Table_Name -> recordId = manufacturingOrderTable.get(recordIdentifier).getPP_Order_ID();
			case I_C_InvoiceLine.Table_Name -> recordId = invoiceLineTable.get(recordIdentifier).getC_InvoiceLine_ID();
			case I_C_Flatrate_Term.Table_Name -> recordId = flatrateTermTable.get(recordIdentifier).getC_Flatrate_Term_ID();
			case I_M_Shipping_NotificationLine.Table_Name -> recordId = shippingNotificationLineStepDefData.get(recordIdentifier).getM_Shipping_NotificationLine_ID();
			case I_I_ModCntr_Log.Table_Name -> recordId = importModCntrTable.get(recordIdentifier).getI_ModCntr_Log_ID();
			default -> throw new AdempiereException("Unsupported TableName !")
					.appendParametersToMessage()
					.setParameter("TableName", tableName);
		}

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Log.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String modCntrTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Log.COLUMNNAME_ModCntr_Type_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_ModCntr_Type modCntrTypeRecord = modCntrTypeTable.get(modCntrTypeIdentifier);

		final ItemProvider<I_ModCntr_Log> locateLog = () -> queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_Record_ID, recordId)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermRecord.getC_Flatrate_Term_ID())
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_Qty, quantity)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_ModCntr_Type_ID, modCntrTypeRecord.getModCntr_Type_ID())
				.create()
				.firstOnlyOptional()
				.map(ItemProvider.ProviderResult::resultWasFound)
				.orElseGet(() -> ItemProvider.ProviderResult.resultWasNotFound(buildMessageWitAllLogs(tableRow)));

		final I_ModCntr_Log modCntrLogRecord = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, locateLog);

		final SoftAssertions softly = new SoftAssertions();

		final String contractType = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_ModCntr_Log.COLUMNNAME_ContractType);
		softly.assertThat(modCntrLogRecord.getContractType()).as(I_ModCntr_Log.COLUMNNAME_ContractType).isEqualTo(contractType);

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

		final Boolean isSoTrx = DataTableUtil.extractBooleanForColumnNameOrNull(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_IsSOTrx);
		if (isSoTrx != null)
		{
			softly.assertThat(modCntrLogRecord.isSOTrx()).as(I_ModCntr_Log.COLUMNNAME_IsSOTrx).isEqualTo(isSoTrx);
		}

		final String modCntrModuleIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_ModCntr_Module_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(modCntrModuleIdentifier))
		{
			final I_ModCntr_Module modCntrModuleRecord = modCntrModuleTable.get(modCntrModuleIdentifier);
			softly.assertThat(modCntrLogRecord.getModCntr_Module_ID()).as(I_ModCntr_Log.COLUMNNAME_ModCntr_Module_ID).isEqualTo(modCntrModuleRecord.getModCntr_Module_ID());
		}

		final BigDecimal priceActual = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_PriceActual);
		if (priceActual != null)
		{
			softly.assertThat(modCntrLogRecord.getPriceActual()).as(I_ModCntr_Log.COLUMNNAME_PriceActual).isEqualByComparingTo(priceActual);
		}
		else
		{
			final String nullPriceActual = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_PriceActual);
			if (Check.isNotBlank(nullPriceActual) && DataTableUtil.nullToken2Null(nullPriceActual) == null)
			{
				softly.assertThat(modCntrLogRecord.getPriceActual()).as(I_ModCntr_Log.COLUMNNAME_PriceActual).isEqualByComparingTo(BigDecimal.ZERO);
			}
		}

		final String priceUOMCode = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_Price_UOM_ID + "." + X12DE355.class.getSimpleName());
		if (Check.isNotBlank(priceUOMCode))
		{
			final String nullablePriceUOMCode = DataTableUtil.nullToken2Null(priceUOMCode);
			if (nullablePriceUOMCode == null)
			{
				softly.assertThat(modCntrLogRecord.getPrice_UOM_ID()).as(I_ModCntr_Log.COLUMNNAME_Price_UOM_ID + "." + X12DE355.class.getSimpleName()).isEqualByComparingTo(0);
			}
			else
			{
				final UomId priceUOMId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(priceUOMCode));
				softly.assertThat(modCntrLogRecord.getPrice_UOM_ID()).as(I_ModCntr_Log.COLUMNNAME_Price_UOM_ID + "." + X12DE355.class.getSimpleName()).isEqualTo(priceUOMId.getRepoId());
			}
		}

		final String invoicingGroupIdentifier = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_ModCntr_InvoicingGroup_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(invoicingGroupIdentifier))
		{
			final String nullableInvoiceGroupIdentifier = DataTableUtil.nullToken2Null(invoicingGroupIdentifier);
			if (nullableInvoiceGroupIdentifier == null)
			{
				softly.assertThat(modCntrLogRecord.getModCntr_InvoicingGroup_ID()).as(I_ModCntr_Log.COLUMNNAME_ModCntr_InvoicingGroup_ID).isEqualByComparingTo(0);
			}
			else
			{
				final I_ModCntr_InvoicingGroup invoicingGroupRecord = invoicingGroupTable.get(invoicingGroupIdentifier);
				softly.assertThat(modCntrLogRecord.getModCntr_InvoicingGroup_ID()).as(I_ModCntr_Log.COLUMNNAME_ModCntr_InvoicingGroup_ID).isEqualTo(invoicingGroupRecord.getModCntr_InvoicingGroup_ID());
			}
		}

		final String productName = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_ProductName);
		if (Check.isNotBlank(productName))
		{
			softly.assertThat(modCntrLogRecord.getProductName()).as(I_ModCntr_Log.COLUMNNAME_ProductName).isEqualTo(productName);
		}

		final Boolean isBillable = DataTableUtil.extractBooleanForColumnNameOrNull(tableRow, "OPT." + I_ModCntr_Log.COLUMNNAME_IsBillable);
		if (isBillable != null)
		{
			softly.assertThat(modCntrLogRecord.isBillable()).as(I_ModCntr_Log.COLUMNNAME_IsBillable).isEqualTo(isBillable);
		}

		softly.assertAll();

		final String modCntrLogIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Log.COLUMNNAME_ModCntr_Log_ID + "." + TABLECOLUMN_IDENTIFIER);
		modCntrLogTable.putOrReplace(modCntrLogIdentifier, modCntrLogRecord);
	}

	private void validateNoModCntr_LogFound(@NonNull final Map<String, String> tableRow)
	{
		final String tableName = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Table.COLUMNNAME_TableName);
		final int tableId = tableDAO.retrieveTableId(tableName);

		final String recordIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Log.COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int recordId;
		switch (tableName)
		{
			case I_C_OrderLine.Table_Name -> recordId = orderLineTable.get(recordIdentifier).getC_OrderLine_ID();
			case I_M_InOutLine.Table_Name -> recordId = inOutLineTable.get(recordIdentifier).getM_InOutLine_ID();
			case I_C_InvoiceLine.Table_Name -> recordId = invoiceLineTable.get(recordIdentifier).getC_InvoiceLine_ID();
			case I_M_InventoryLine.Table_Name -> recordId = inventoryLineTable.get(recordIdentifier).getM_InventoryLine_ID();
			case I_C_Flatrate_Term.Table_Name -> recordId = flatrateTermTable.get(recordIdentifier).getC_Flatrate_Term_ID();
			case I_PP_Order.Table_Name -> recordId = manufacturingOrderTable.get(recordIdentifier).getPP_Order_ID();
			case I_M_Shipping_NotificationLine.Table_Name -> recordId = shippingNotificationLineStepDefData.get(recordIdentifier).getM_Shipping_NotificationLine_ID();
			default -> throw new AdempiereException("Unsupported TableName !")
					.appendParametersToMessage()
					.setParameter("TableName", tableName);
		}

		final I_ModCntr_Log modCntrLogRecord = queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_Record_ID, recordId)
				.create()
				.firstOnlyOrNull(I_ModCntr_Log.class);

		assertThat(modCntrLogRecord).isNull();
	}

	@NonNull
	private String buildMessageWitAllLogs(@NonNull final Map<String, String> row)
	{
		final StringBuilder messageBuilder = new StringBuilder("No logs found for row: " + writeRowAsString(row) + "! See currently created messages:");

		queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.addCompareFilter(I_ModCntr_Log.COLUMNNAME_Created, CompareQueryFilter.Operator.GREATER_OR_EQUAL, scenarioLifeCycleStepDef.getScenarioStartTimeOr(Instant.ofEpochMilli(0)))
				.create()
				.forEach(logRecord -> messageBuilder
						.append("\n")
						.append(I_ModCntr_Log.COLUMNNAME_Record_ID).append("=").append(logRecord.getRecord_ID()).append(", ")
						.append(I_ModCntr_Log.COLUMNNAME_AD_Table_ID).append("=").append(logRecord.getAD_Table_ID()).append(", ")
						.append(I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID).append("=").append(logRecord.getC_Flatrate_Term_ID()).append(", ")
						.append(I_ModCntr_Log.COLUMNNAME_Qty).append("=").append(logRecord.getQty()).append(", ")
						.append(I_ModCntr_Log.COLUMNNAME_C_UOM_ID).append("=").append(logRecord.getC_UOM_ID()).append(", ")
						.append(I_ModCntr_Log.COLUMNNAME_M_Product_ID).append("=").append(logRecord.getM_Product_ID()).append(", ")
						.append(I_ModCntr_Log.COLUMNNAME_Processed).append("=").append(logRecord.isProcessed()).append(";")
				);

		return messageBuilder.toString();
	}

	private void recomputeLogsForRecord(@NonNull final Map<String, String> tableRow)
	{
		final String tableName = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Table.COLUMNNAME_TableName);
		final String recordIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Log.COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);

		switch (tableName)
		{
			case I_C_Order.Table_Name ->
			{
				final IQueryFilter<I_C_Order> filter = queryBL.createCompositeQueryFilter(I_C_Order.class)
						.addEqualsFilter(I_C_Order.COLUMNNAME_C_Order_ID, orderTable.get(recordIdentifier).getC_Order_ID());

				recomputeLogsService.recomputeForOrder(filter);
			}
			case I_M_Inventory.Table_Name ->
			{
				final IQueryFilter<I_M_Inventory> filter = queryBL.createCompositeQueryFilter(I_M_Inventory.class)
						.addEqualsFilter(I_M_Inventory.COLUMNNAME_M_Inventory_ID, inventoryTable.get(recordIdentifier).getM_Inventory_ID());

				recomputeLogsService.recomputeForInventory(filter);

			}
			case I_M_InOut.Table_Name ->
			{
				final IQueryFilter<I_M_InOut> filter = queryBL.createCompositeQueryFilter(I_M_InOut.class)
						.addEqualsFilter(I_M_InOut.COLUMNNAME_M_InOut_ID, inOutTable.get(recordIdentifier).getM_InOut_ID());

				recomputeLogsService.recomputeForInOut(filter);
			}
			case I_PP_Order.Table_Name ->
			{
				final IQueryFilter<I_PP_Order> filter = queryBL.createCompositeQueryFilter(I_PP_Order.class)
						.addEqualsFilter(I_PP_Order.COLUMNNAME_PP_Order_ID, manufacturingOrderTable.get(recordIdentifier).getPP_Order_ID());

				recomputeLogsService.recomputeForPPOrder(filter);
			}
			case I_C_Invoice.Table_Name ->
			{
				final IQueryFilter<I_C_Invoice> filter = queryBL.createCompositeQueryFilter(I_C_Invoice.class)
						.addEqualsFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, invoiceTable.get(recordIdentifier).getC_Invoice_ID());

				recomputeLogsService.recomputeForInvoice(filter);
			}
			case I_C_Flatrate_Term.Table_Name ->
			{
				final IQueryFilter<I_C_Flatrate_Term> filter = queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
						.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermTable.get(recordIdentifier).getC_Flatrate_Term_ID());

				recomputeLogsService.recomputeForFlatrate(filter);
			}
			case I_M_Shipping_Notification.Table_Name ->
			{
				final IQueryFilter<I_M_Shipping_Notification> filter = queryBL.createCompositeQueryFilter(I_M_Shipping_Notification.class)
						.addEqualsFilter(I_M_Shipping_Notification.COLUMNNAME_M_Shipping_Notification_ID, shippingNotificationStepDefData.get(recordIdentifier).getM_Shipping_Notification_ID());

				recomputeLogsService.recomputeForShippingNotification(filter);
			}
			default -> throw new AdempiereException("Unsupported TableName!")
					.appendParametersToMessage()
					.setParameter("TableName", tableName);
		}

	}
}
