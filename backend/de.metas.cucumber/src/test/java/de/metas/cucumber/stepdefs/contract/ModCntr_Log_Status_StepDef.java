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
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.model.I_ModCntr_Log_Status;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.ScenarioLifeCycleStepDef;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.inventory.M_InventoryLine_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_InvoiceLine_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Cost_Collector_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.cucumber.stepdefs.shippingNotification.M_Shipping_NotificationLine_StepDefData;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InventoryLine;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.cucumber.stepdefs.StepDefUtil.writeRowAsString;
import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
public class ModCntr_Log_Status_StepDef
{
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Flatrate_Term_StepDefData flatrateTermTable;
	private final M_Shipping_NotificationLine_StepDefData shippingNotificationLineTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_InventoryLine_StepDefData inventoryLineTable;
	private final M_InOutLine_StepDefData inOutLineTable;
	private final C_InvoiceLine_StepDefData invoiceLineTable;
	private final PP_Cost_Collector_StepDefData costCollectorTable;
	private final ScenarioLifeCycleStepDef scenarioLifeCycleStepDef;

	@And("^after not more than (.*)s, validate ModCntr_Log_Statuses:$")
	public void validateModCntr_Log_Statuses(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{

			final ItemProvider<List<I_ModCntr_Log_Status>> locateStatuses = () -> {
				final List<I_ModCntr_Log_Status> modCntrLogStatuses = fetchModCntrLogStatusList(row);

				final Integer noOfLogStatuses = Optional.ofNullable(DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT.noOfLogStatuses"))
						.orElse(1);

				if (modCntrLogStatuses.size() == noOfLogStatuses)
				{
					return ItemProvider.ProviderResult.resultWasFound(modCntrLogStatuses);
				}

				return ItemProvider.ProviderResult.resultWasNotFound("Found " + modCntrLogStatuses.size() + " records for criteria!");
			};

			StepDefUtil.tryAndWaitForItem(timeoutSec,
										  500,
										  locateStatuses,
										  () -> buildMessageWitAllLogStatuses(row));
		}
	}

	@And("^after not more than (.*)s, no ModCntr_Log_Statuses are found:$")
	public void noModCntr_Log_Statuses_found(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		Thread.sleep(timeoutSec);

		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			validateNoModCntr_Log_Status_Found(row);
		}
	}

	private void validateNoModCntr_Log_Status_Found(@NonNull final Map<String, String> tableRow)
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
			default -> throw new AdempiereException("Unsupported TableName !")
					.appendParametersToMessage()
					.setParameter("TableName", tableName);
		}

		final I_ModCntr_Log_Status modCntrLogStatusRecord = queryBL.createQueryBuilder(I_ModCntr_Log_Status.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Log_Status.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_ModCntr_Log_Status.COLUMNNAME_Record_ID, recordId)
				.create()
				.firstOnlyOrNull(I_ModCntr_Log_Status.class);

		assertThat(modCntrLogStatusRecord).isNull();
	}

	private List<I_ModCntr_Log_Status> fetchModCntrLogStatusList(@NonNull final Map<String, String> tableRow)
	{
		final String tableName = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Table.COLUMNNAME_TableName);
		final int tableId = tableDAO.retrieveTableId(tableName);

		final String processingStatus = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Log_Status.COLUMNNAME_ProcessingStatus);

		final String recordIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Log.COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int recordId = resolveRepoId(tableName, recordIdentifier);

		return queryBL.createQueryBuilder(I_ModCntr_Log_Status.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Log_Status.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_ModCntr_Log_Status.COLUMNNAME_Record_ID, recordId)
				.addEqualsFilter(I_ModCntr_Log_Status.COLUMNNAME_ProcessingStatus, processingStatus)
				.create()
				.listImmutable();
	}

	private int resolveRepoId(@NonNull final String tableName, @NonNull final String stepDefDataIdentifier)
	{
		return switch (tableName)
				{
					case I_C_OrderLine.Table_Name -> orderLineTable.get(stepDefDataIdentifier).getC_OrderLine_ID();
					case I_M_InventoryLine.Table_Name -> inventoryLineTable.get(stepDefDataIdentifier).getM_InventoryLine_ID();
					case I_M_InOutLine.Table_Name -> inOutLineTable.get(stepDefDataIdentifier).getM_InOutLine_ID();
					case I_PP_Cost_Collector.Table_Name -> costCollectorTable.get(stepDefDataIdentifier).getPP_Cost_Collector_ID();
					case I_C_InvoiceLine.Table_Name -> invoiceLineTable.get(stepDefDataIdentifier).getC_InvoiceLine_ID();
					case I_C_Flatrate_Term.Table_Name -> flatrateTermTable.get(stepDefDataIdentifier).getC_Flatrate_Term_ID();
					case I_M_Shipping_NotificationLine.Table_Name -> shippingNotificationLineTable.get(stepDefDataIdentifier).getM_Shipping_NotificationLine_ID();
					default -> throw new AdempiereException("Unsupported TableName !")
							.appendParametersToMessage()
							.setParameter("TableName", tableName);
				};
	}

	@NonNull
	private String buildMessageWitAllLogStatuses(@NonNull final Map<String, String> row)
	{
		final String tableName = DataTableUtil.extractStringForColumnName(row, I_AD_Table.COLUMNNAME_TableName);
		final int tableId = tableDAO.retrieveTableId(tableName);

		final String recordIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_Log.COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int recordId = resolveRepoId(tableName, recordIdentifier);

		final StringBuilder messageBuilder = new StringBuilder("Row: " + writeRowAsString(row) + " tableID: " + tableId + " recordID: " + recordId + "! See currently created message statuses:");

		queryBL.createQueryBuilder(I_ModCntr_Log_Status.class)
				.addCompareFilter(I_ModCntr_Log_Status.COLUMNNAME_Created, CompareQueryFilter.Operator.GREATER_OR_EQUAL, scenarioLifeCycleStepDef.getScenarioStartTimeOr(Instant.ofEpochMilli(0)))
				.create()
				.forEach(logRecord -> messageBuilder
						.append("\n")
						.append(I_ModCntr_Log_Status.COLUMNNAME_Record_ID).append("=").append(logRecord.getRecord_ID()).append(", ")
						.append(I_ModCntr_Log_Status.COLUMNNAME_AD_Table_ID).append("=").append(logRecord.getAD_Table_ID()).append(", ")
						.append(I_ModCntr_Log_Status.COLUMNNAME_ProcessingStatus).append("=").append(logRecord.getProcessingStatus()).append(", ")
						.append(I_ModCntr_Log_Status.COLUMNNAME_AD_Issue_ID).append("=").append(logRecord.getAD_Issue_ID()).append(";")
				);

		return messageBuilder.toString();
	}
}
