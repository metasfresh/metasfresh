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
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_ReceiptSchedule_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.Assertions.*;

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
		DataTableRows.of(dataTable).forEach(row ->
				{
					final StepDefDataIdentifier huIdentifier = row.getAsOptionalIdentifier(I_M_HU.COLUMNNAME_M_HU_ID).orElse(null);
					final StepDefDataIdentifier huListIdentifier = row.getAsOptionalIdentifier("HUList").orElse(null);

					assertThat(huIdentifier != null /*XOR*/ ^ huListIdentifier != null).isEqualTo(true);

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

					final StepDefDataIdentifier receiptScheduleIdentifier = row.getAsIdentifier(I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID);
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

					final StepDefDataIdentifier inoutIdentifier = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID);
					inOutTable.putOrReplace(inoutIdentifier, huInOut);
				}
		);
	}
}
