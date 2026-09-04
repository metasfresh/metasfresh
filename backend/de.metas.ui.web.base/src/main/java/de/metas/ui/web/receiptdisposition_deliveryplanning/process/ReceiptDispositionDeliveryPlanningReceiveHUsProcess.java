/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.receiptdisposition_deliveryplanning.process;

import com.google.common.collect.ImmutableSet;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.ReceiptScheduleAndDeliveryPlanningId;
import de.metas.deliveryplanning.receipt.CreateReceiptFromReceiptScheduleRequest;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.organization.ClientAndOrgId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.process.ReceiptScheduleLUTUConfigurations;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The "receive HUs" half of the receipt-logistics window's receive actions: goods arrive in a packing (TUs, and
 * usually LUs), not as bare units.
 * <p>
 * Mirrors {@code WEBUI_M_ReceiptSchedule_ReceiveHUs_Base} step for step - the same generator, the same LU/TU
 * configuration, the same infinite-quantity guard - with exactly one difference at the end: where that class
 * hands the generated HUs to the HU editor and lets a later, separate process turn them into a receipt, this one
 * books them through the shared receive right away, so the row's delivery planning id reaches the receipt. That
 * is the whole reason it cannot simply extend it: the HU-editor path drops the planning on the floor.
 */
abstract class ReceiptDispositionDeliveryPlanningReceiveHUsProcess extends ReceiptDispositionDeliveryPlanningReceiveProcess
{
	/** The effective LU/TU configuration to receive into, derived from the schedule's current one. */
	protected abstract I_M_HU_LUTU_Configuration createLUTUConfiguration(
			@NonNull I_M_HU_LUTU_Configuration template,
			@NonNull I_M_ReceiptSchedule receiptSchedule);

	/** Whether the configuration the operator just used becomes the schedule's new default. */
	protected abstract boolean isUpdateReceiptScheduleDefaultConfiguration();

	/**
	 * Whether the quantity in the LU/TU configuration is the OPERATOR's own statement rather than a default this
	 * process derived. Every subclass must answer, because it decides whether a planned row's share caps it (see
	 * {@link #getQtyToAllocate}).
	 */
	protected abstract boolean isQtyToReceiveOperatorStated();

	@Override
	protected final void receive(@NonNull final ReceiptScheduleAndDeliveryPlanningId sourceIds)
	{
		final I_M_ReceiptSchedule receiptSchedule = huReceiptScheduleBL.getById(sourceIds.getReceiptScheduleId());
		final IMutableHUContext huContextInitial = Services.get(IHUContextFactory.class)
				.createMutableHUContextForProcessing(
						getCtx(),
						ClientAndOrgId.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID()));

		final ReceiptScheduleHUGenerator huGenerator = ReceiptScheduleHUGenerator.newInstance(huContextInitial)
				.addM_ReceiptSchedule(receiptSchedule)
				.setUpdateReceiptScheduleDefaultConfiguration(isUpdateReceiptScheduleDefaultConfiguration());

		final I_M_HU_LUTU_Configuration lutuConfiguration =
				createLUTUConfiguration(ReceiptScheduleLUTUConfigurations.getCurrent(receiptSchedule), receiptSchedule);
		Services.get(ILUTUConfigurationFactory.class).save(lutuConfiguration);
		huGenerator.setM_HU_LUTU_Configuration(lutuConfiguration);

		final ILUTUProducerAllocationDestination lutuProducer = huGenerator.getLUTUProducerAllocationDestination();
		final Quantity qtyCUsTotal = lutuProducer.calculateTotalQtyCU();
		if (qtyCUsTotal.isInfinite())
		{
			throw new AdempiereException("LU/TU configuration is resulting to infinite quantity: " + lutuConfiguration);
		}
		final Quantity qtyToAllocate = getQtyToAllocate(qtyCUsTotal, receiptSchedule, sourceIds.getDeliveryPlanningId());
		huGenerator.setQtyToAllocateTarget(qtyToAllocate);

		final List<I_M_HU> hus = huGenerator.generateWithinOwnTransaction();
		receiptFromReceiptScheduleService.updatePlanningHUAttributes(hus, receiptSchedule);

		receiptFromReceiptScheduleService.createReceipt(CreateReceiptFromReceiptScheduleRequest.builder()
				.receiptScheduleId(sourceIds.getReceiptScheduleId())
				.deliveryPlanningId(sourceIds.getDeliveryPlanningId())
				.huIdsToReceive(hus.stream().map(hu -> HuId.ofRepoId(hu.getM_HU_ID())).collect(ImmutableSet.toImmutableSet()))
				.movementDateRule(ReceiptMovementDateRule.CURRENT_DATE)
				.build());

		receiptFromReceiptScheduleService.applyPlanningQuantityRules(sourceIds.getDeliveryPlanningId(), qtyToAllocate);
	}

	/**
	 * How many CUs are actually booked out of the packing the configuration describes.
	 * <p>
	 * <b>An operator-stated packing is booked as stated</b> - "HUs annehmen" asks for the LU/TU/CU counts and
	 * honours them, exactly as the CU receive honours a typed quantity and as
	 * {@code WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig} does; narrowing it behind the operator's back would
	 * be a different action than the one pressed.
	 * <p>
	 * <b>A DERIVED default on a PLANNED row is capped at that planning's share.</b> This is the same defect the
	 * single-row CU receive had: {@code ReceiptScheduleLUTUConfigurations.adjustToDefaults} derives its LU/TU
	 * quantities from the SCHEDULE ({@code getQtyToMoveTU} and {@code QtyToMove}), and a split copies
	 * {@code M_ReceiptSchedule_ID} onto every new planning, so the schedule's outstanding quantity is the whole
	 * order line's. One-click receiving one row of a split would otherwise consume the line and leave the sibling
	 * plannings unable to receive at all.
	 * <p>
	 * <b>An UNPLANNED row is left alone</b> - no planning, no share, and its receive must stay byte-for-byte what
	 * window 541954 produces, packing rounding over the schedule's remainder included.
	 */
	private Quantity getQtyToAllocate(
			@NonNull final Quantity qtyCUsFromPacking,
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@Nullable final DeliveryPlanningId deliveryPlanningId)
	{
		if (isQtyToReceiveOperatorStated())
		{
			return qtyCUsFromPacking;
		}

		return receiptFromReceiptScheduleService.getPlannedShareToReceive(receiptSchedule, deliveryPlanningId)
				.map(qtyCUsFromPacking::min)
				.orElse(qtyCUsFromPacking);
	}
}
