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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_ReceiptSchedule_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.deliveryplanning.M_Delivery_Planning_StepDefData;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.receipt.ReceiptFromReceiptScheduleService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.ApplyReceiptScheduleChangesRequest;
import de.metas.inoutcandidate.api.ApplyReceiptScheduleChangesRequest.ApplyReceiptScheduleChangesRequestBuilder;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class M_HU_CreateReceipt_StepDef
{
	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	@NonNull private final M_HU_StepDefData huTable;
	@NonNull private final M_HU_List_StepDefData huListTable;
	@NonNull private final M_ReceiptSchedule_StepDefData receiptScheduleTable;
	@NonNull private final M_InOut_StepDefData inOutTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_Delivery_Planning_StepDefData deliveryPlanningTable;

	/**
	 * Books the given handling units, the way the HU editor's confirm ({@code WEBUI_M_HU_CreateReceipt_*}) does.
	 * <p>
	 * {@code OPT.M_Delivery_Planning_ID} is the provenance the editor's confirm resolves off the row that launched
	 * it: given it, every selected HU is mapped to that planning through the SHARED
	 * {@link ReceiptFromReceiptScheduleService#deliveryPlanningIdByHuId} the confirm itself uses, so this step
	 * reproduces the confirm's call rather than restating its rule. What it does NOT cover is the resolution of a
	 * launching grid row into that planning - {@code de.metas.cucumber} excludes {@code de.metas.ui.web.base}, so
	 * the process class is not loadable here; that step is covered by {@code HUEditorReceiptSourcesTest}.
	 */
	@And("create material receipt")
	public void createMaterialReceipts(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createMaterialReceipt);
	}

	private void createMaterialReceipt(final DataTableRow row)
	{
		final I_M_ReceiptSchedule receiptSchedule = extractAndRefreshReceiptSchedule(row);

		extractApplyReceiptScheduleChangesRequest(row, ReceiptScheduleId.ofRepoId(receiptSchedule.getM_ReceiptSchedule_ID()))
				.ifPresent(applyReceiptScheduleChangesRequest -> {
					receiptScheduleBL.applyReceiptScheduleChanges(applyReceiptScheduleChangesRequest);
					InterfaceWrapperHelper.refresh(receiptSchedule);
				});

		final Set<HuId> selectedHuIds = extractHuIds(row);

		final I_M_InOut materialReceipt = createMaterialReceipt(
				IHUReceiptScheduleBL.CreateReceiptsParameters.builder()
						.movementDateRule(ReceiptMovementDateRule.CURRENT_DATE)
						.ctx(Env.getCtx())
						.receiptSchedules(ImmutableList.of(receiptSchedule))
						.selectedHuIds(selectedHuIds)
						.deliveryPlanningIdByHuId(extractDeliveryPlanningIdByHuId(row, selectedHuIds))
						.build()
		);

		row.getAsOptionalIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID).ifPresent(inoutIdentifier -> inOutTable.putOrReplace(inoutIdentifier, materialReceipt));
	}

	private ImmutableMap<HuId, DeliveryPlanningId> extractDeliveryPlanningIdByHuId(
			@NonNull final DataTableRow row,
			@Nullable final Set<HuId> selectedHuIds)
	{
		final DeliveryPlanningId deliveryPlanningId = row
				.getAsOptionalIdentifier(I_M_InOutLine.COLUMNNAME_M_Delivery_Planning_ID)
				.map(deliveryPlanningTable::get)
				.map(deliveryPlanning -> DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID()))
				.orElse(null);
		if (deliveryPlanningId == null)
		{
			return ImmutableMap.of();
		}

		assertThat(selectedHuIds)
				.as("receiving for a delivery planning requires the HUs to be named, because the map is keyed by them")
				.isNotNull();

		return ReceiptFromReceiptScheduleService.deliveryPlanningIdByHuId(selectedHuIds, deliveryPlanningId);
	}

	private Optional<ApplyReceiptScheduleChangesRequest> extractApplyReceiptScheduleChangesRequest(
			@NonNull final DataTableRow row,
			@NonNull final ReceiptScheduleId receiptScheduleId)
	{
		final AtomicBoolean requestIsEmpty = new AtomicBoolean(true);
		final ApplyReceiptScheduleChangesRequestBuilder requestBuilder = ApplyReceiptScheduleChangesRequest.builder()
				.receiptScheduleId(receiptScheduleId);

		row.getAsOptionalBigDecimal("QtyToMove_Override")
				.ifPresent(qty -> {
					requestBuilder.qtyInStockingUOM(qty);
					requestIsEmpty.set(false);
				});

		return requestIsEmpty.get() ? Optional.empty() : Optional.of(requestBuilder.build());
	}

	private @NonNull I_M_ReceiptSchedule extractAndRefreshReceiptSchedule(final DataTableRow row)
	{
		// Direct receipt schedule
		{
			final I_M_ReceiptSchedule receiptSchedule = row.getAsOptionalIdentifier(I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID)
					.map(receiptScheduleTable::get)
					.orElse(null);
			if (receiptSchedule != null)
			{
				InterfaceWrapperHelper.refresh(receiptSchedule);
				return receiptSchedule;
			}
		}

		//
		// via Order line
		final I_C_OrderLine orderLine = row.getAsOptionalIdentifier("C_OrderLine_ID")
				.map(orderLineTable::get)
				.orElse(null);
		if (orderLine != null)
		{
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleDAO.retrieveForRecord(orderLine);
			if (receiptSchedule == null)
			{
				throw new AdempiereException("Cannot determine the receipt schedule for " + orderLine);
			}
			return receiptSchedule;
		}

		throw new AdempiereException("Cannot determine the receipt schedule from " + row);
	}

	private Set<HuId> extractHuIds(final DataTableRow row)
	{
		final StepDefDataIdentifier huIdentifier = row.getAsOptionalIdentifier(I_M_HU.COLUMNNAME_M_HU_ID).orElse(null);
		final StepDefDataIdentifier huListIdentifier = row.getAsOptionalIdentifier("HUList").orElse(null);

		if (huIdentifier == null && huListIdentifier == null)
		{
			return null;
		}

		assertThat(huIdentifier != null /*XOR*/ ^ huListIdentifier != null)
				.as("Exactly one of %s or %s must be set", I_M_HU.COLUMNNAME_M_HU_ID, "HUList")
				.isTrue();

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
