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
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.process.ReceiptScheduleLUTUConfigurations;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The "receive HUs" half of the receipt-disposition delivery-planning window's receive actions.
 * <p>
 * Mirrors {@code WEBUI_M_ReceiptSchedule_ReceiveHUs_Base} step for step with one difference at the end: it books
 * the generated HUs through the shared receive right away instead of handing them to the HU editor, which is why
 * it cannot simply extend that class - the HU-editor path never sets the planning id.
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
	 * process derived - it decides whether a planned row's share caps it (see {@link #getQtyToAllocate}).
	 */
	protected abstract boolean isQtyToReceiveOperatorStated();

	/**
	 * The direction guard runs BEFORE the shared receive preconditions, and the order is the point: those start by
	 * loading the selected row's receipt schedule, which an outgoing delivery planning has not got. Only these two
	 * actions are reachable from the delivery-planning window, so only they need it.
	 */
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ProcessPreconditionsResolution noneOutgoing = checkNoneOutgoing(getSelectedDeliveryPlannings());
		if (!noneOutgoing.isAccepted())
		{
			return noneOutgoing;
		}

		return super.checkPreconditionsApplicable();
	}

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
	}

	/**
	 * An operator-stated packing is booked as stated. A DERIVED default on a PLANNED row is capped at that
	 * planning's share: {@code ReceiptScheduleLUTUConfigurations.adjustToDefaults} derives its LU/TU quantities
	 * from the SCHEDULE, and a split copies {@code M_ReceiptSchedule_ID} onto every new planning - so one-click
	 * receiving one row of a split would otherwise consume the whole order line and leave its siblings unable to
	 * receive. An UNPLANNED row is left alone.
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
