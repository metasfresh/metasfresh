/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.hu;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Calendar_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.cucumber.stepdefs.receiptschedule.M_ReceiptSchedule_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_Product_ID;

public class M_HU_CreateReceipt_StepDef
{
	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_HU_StepDefData huTable;
	private final M_HU_List_StepDefData huListTable;
	private final M_ReceiptSchedule_StepDefData receiptScheduleTable;
	private final M_InOut_StepDefData inOutTable;
	private final M_Product_StepDefData productTable;
	private final M_InOutLine_StepDefData receiptLineTable;
	private final C_Flatrate_Term_StepDefData flatrateTermTable;
	private final C_Calendar_StepDefData calendarTable;
	private final C_Year_StepDefData yearTable;

	public M_HU_CreateReceipt_StepDef(
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final M_ReceiptSchedule_StepDefData receiptScheduleTable,
			@NonNull final M_InOut_StepDefData inOutTable,
			@NonNull final M_HU_List_StepDefData huListTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_InOutLine_StepDefData receiptLineTable,
			@NonNull final C_Flatrate_Term_StepDefData flatrateTermTable,
			@NonNull final C_Calendar_StepDefData calendarTable,
			@NonNull final C_Year_StepDefData yearTable)
	{
		this.huTable = huTable;
		this.receiptScheduleTable = receiptScheduleTable;
		this.inOutTable = inOutTable;
		this.huListTable = huListTable;
		this.productTable = productTable;
		this.receiptLineTable = receiptLineTable;
		this.flatrateTermTable = flatrateTermTable;
		this.calendarTable = calendarTable;
		this.yearTable = yearTable;
	}

	@And("create material receipt and the following exception is thrown")
	public void create_materialReceipt_expect_exception(@NonNull final DataTable dataTable)
	{
		try
		{
			create_materialReceipt(dataTable);
			assertThat(1).as("An Exception should have been thrown !").isEqualTo(2);
		}
		catch (final AdempiereException exception)
		{
			final Map<String, String> tableRow = dataTable.asMaps().get(0);
			final String errorCode = DataTableUtil.extractStringForColumnName(tableRow, "ErrorCode");

			assertThat(exception.getErrorCode()).as("ErrorCode of %s", exception).isEqualTo(errorCode);
		}
	}
	
	@And("create material receipt")
	public void create_materialReceipt(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String huIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, I_M_HU.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String huListIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.HUList." + TABLECOLUMN_IDENTIFIER);

			assertThat(huIdentifier != null /*XOR*/ ^ huListIdentifier != null).isEqualTo(true);

			final Set<HuId> huIdSet;
			if (huIdentifier != null)
			{
				huIdSet = ImmutableSet.of(HuId.ofRepoId(huTable.get(huIdentifier).getM_HU_ID()));
			}
			else
			{
				assertThat(huListIdentifier).isNotBlank();

				huIdSet = huListTable.get(huListIdentifier)
						.stream()
						.map(I_M_HU::getM_HU_ID)
						.map(HuId::ofRepoId)
						.collect(ImmutableSet.toImmutableSet());
			}

			final String receiptScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleTable.get(receiptScheduleIdentifier);
			InterfaceWrapperHelper.refresh(receiptSchedule);

			final IHUReceiptScheduleBL.CreateReceiptsParameters parameters = IHUReceiptScheduleBL.CreateReceiptsParameters.builder()
					.movementDateRule(ReceiptMovementDateRule.CURRENT_DATE)
					.ctx(Env.getCtx())
					.receiptSchedules(ImmutableList.of(receiptSchedule))
					.selectedHuIds(huIdSet)
					.build();

			final InOutGenerateResult inOutGenerateResult = huReceiptScheduleBL.processReceiptSchedules(parameters);
			assertThat(inOutGenerateResult).isNotNull();
			assertThat(inOutGenerateResult.getInOuts()).isNotEmpty();
			assertThat(inOutGenerateResult.getInOuts().size()).isEqualTo(1);

			final de.metas.inout.model.I_M_InOut inOut = inOutGenerateResult.getInOuts().get(0);

			final I_M_InOut huInOut = load(inOut.getM_InOut_ID(), I_M_InOut.class);
			assertThat(huInOut).isNotNull();

			final String inoutIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			inOutTable.putOrReplace(inoutIdentifier, huInOut);
		}
	}

	@And("^validate the created receipt lines")
	public void validate_created_M_InOutLine(@NonNull final DataTable table)
	{
		final SoftAssertions softly = new SoftAssertions();

		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, "M_InOut_ID.Identifier");

			final org.compiere.model.I_M_InOut shipmentRecord = inOutTable.get(shipmentIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer expectedProductId = productTable.getOptional(productIdentifier)
					.map(I_M_Product::getM_Product_ID)
					.orElseGet(() -> Integer.parseInt(productIdentifier));

			//dev-note: we assume the tests are not using the same product on different lines
			final IQueryBuilder<I_M_InOutLine> lineQueryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class)
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, shipmentRecord.getM_InOut_ID())
					.addEqualsFilter(COLUMNNAME_M_Product_ID, expectedProductId);

			final I_M_InOutLine receiptLineRecord = lineQueryBuilder
					.create()
					.firstOnlyNotNull(I_M_InOutLine.class);

			final String flatrateTermIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(flatrateTermIdentifier))
			{
				final I_C_Flatrate_Term flatrateTermRecord = flatrateTermTable.get(flatrateTermIdentifier);
				softly.assertThat(receiptLineRecord.getC_Flatrate_Term_ID()).as(I_M_InOutLine.COLUMNNAME_C_Flatrate_Term_ID).isEqualTo(flatrateTermRecord.getC_Flatrate_Term_ID());
			}

			final String harvestingCalendarIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_InOutLine.COLUMNNAME_C_Harvesting_Calendar_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (de.metas.common.util.Check.isNotBlank(harvestingCalendarIdentifier))
			{
				final I_C_Calendar harvestingCalendarRecord = calendarTable.get(harvestingCalendarIdentifier);
				softly.assertThat(receiptLineRecord.getC_Harvesting_Calendar_ID()).as("C_Harvesting_Calendar_ID").isEqualTo(harvestingCalendarRecord.getC_Calendar_ID());
			}

			final String harvestingYearIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_InOutLine.COLUMNNAME_Harvesting_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (de.metas.common.util.Check.isNotBlank(harvestingYearIdentifier))
			{
				final String harvestingYearIdentifierValue = DataTableUtil.nullToken2Null(harvestingYearIdentifier);
				if (harvestingYearIdentifierValue == null)
				{
					softly.assertThat(receiptLineRecord.getHarvesting_Year_ID()).as("Harvesting_Year_ID").isNull();
				}
				else
				{
					final I_C_Year harvestingYearRecord = yearTable.get(harvestingYearIdentifier);
					softly.assertThat(receiptLineRecord.getHarvesting_Year_ID()).as("Harvesting_Year_ID").isEqualTo(harvestingYearRecord.getC_Year_ID());
				}
			}

			softly.assertAll();
			final String shipmentLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOutLine.COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			receiptLineTable.putOrReplace(shipmentLineIdentifier, receiptLineRecord);
		}
	}
}
