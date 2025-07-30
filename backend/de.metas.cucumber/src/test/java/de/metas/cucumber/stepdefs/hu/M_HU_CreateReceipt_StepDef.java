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
<<<<<<< HEAD
import de.metas.cucumber.stepdefs.DataTableUtil;
=======
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
>>>>>>> 35b06f5141 (Fix Moving Average Invoice costing bugs + cucumber test (#21145))
import de.metas.cucumber.stepdefs.M_ReceiptSchedule_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class M_HU_CreateReceipt_StepDef
{
	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);

	private final M_HU_StepDefData huTable;
	private final M_HU_List_StepDefData huListTable;
	private final M_ReceiptSchedule_StepDefData receiptScheduleTable;
	private final M_InOut_StepDefData inOutTable;

	public M_HU_CreateReceipt_StepDef(
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final M_ReceiptSchedule_StepDefData receiptScheduleTable,
			@NonNull final M_InOut_StepDefData inOutTable,
			@NonNull final M_HU_List_StepDefData huListTable)
	{
		this.huTable = huTable;
		this.receiptScheduleTable = receiptScheduleTable;
		this.inOutTable = inOutTable;
		this.huListTable = huListTable;
	}

	@And("create material receipt")
	public void create_materialReceipt(@NonNull final DataTable dataTable)
	{
<<<<<<< HEAD
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
=======
		DataTableRows.of(dataTable)
				.forEach(row -> {
							final I_M_InOut materialReceipt = createMaterialReceipt(
									IHUReceiptScheduleBL.CreateReceiptsParameters.builder()
											.movementDateRule(ReceiptMovementDateRule.CURRENT_DATE)
											.ctx(Env.getCtx())
											.receiptSchedules(ImmutableList.of(extractAndRefreshReceiptSchedule(row)))
											.selectedHuIds(extractHuIds(row))
											.build()
							);

							row.getAsOptionalIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID).ifPresent(inoutIdentifier -> inOutTable.putOrReplace(inoutIdentifier, materialReceipt));
						}
				);
>>>>>>> 35b06f5141 (Fix Moving Average Invoice costing bugs + cucumber test (#21145))
	}

	private @NonNull I_M_ReceiptSchedule extractAndRefreshReceiptSchedule(final DataTableRow row)
	{
		final StepDefDataIdentifier receiptScheduleIdentifier = row.getAsIdentifier(I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID);
		final I_M_ReceiptSchedule receiptSchedule = receiptScheduleTable.get(receiptScheduleIdentifier);
		InterfaceWrapperHelper.refresh(receiptSchedule);
		return receiptSchedule;
	}

	private Set<HuId> extractHuIds(final DataTableRow row)
	{
		final StepDefDataIdentifier huIdentifier = row.getAsOptionalIdentifier(I_M_HU.COLUMNNAME_M_HU_ID).orElse(null);
		final StepDefDataIdentifier huListIdentifier = row.getAsOptionalIdentifier("HUList").orElse(null);

		assertThat(huIdentifier != null /*XOR*/ ^ huListIdentifier != null).isTrue();

		final Set<HuId> huIdSet;
		if (huIdentifier != null)
		{
			huIdSet = ImmutableSet.of(HuId.ofRepoId(huTable.get(huIdentifier).getM_HU_ID()));
		}
		else
		{
			assertThat(huListIdentifier).isNotNull();

			huIdSet = huListTable.get(huListIdentifier)
					.stream()
					.map(I_M_HU::getM_HU_ID)
					.map(HuId::ofRepoId)
					.collect(ImmutableSet.toImmutableSet());
		}
		return huIdSet;
	}

	public I_M_InOut createMaterialReceipt(final IHUReceiptScheduleBL.CreateReceiptsParameters parameters)
	{
		return huReceiptScheduleBL.processReceiptSchedules(parameters)
				.getSingleInOut(I_M_InOut.class);
	}

}
