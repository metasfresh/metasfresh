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

import com.google.common.annotations.VisibleForTesting;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningList;
import de.metas.deliveryplanning.ReceiptScheduleAndDeliveryPlanningId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.process.ReceiptScheduleLUTUConfigurations;
import de.metas.ui.web.receiptSchedule.HUsToReceiveViewFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * The "receive HUs" half of the receipt-disposition delivery-planning window's receive actions.
 * <p>
 * Mirrors {@code WEBUI_M_ReceiptSchedule_ReceiveHUs_Base} step for step, hand-off to the HU EDITOR included: the
 * operator repacks, weighs and completes attributes in there and confirms, and only that confirm books. It cannot
 * simply EXTEND that class - the platform resolves a view row's record through
 * {@code IView#getTableRecordReferenceOrNull}, which on this window yields
 * {@code RV_ReceiptDisposition_DeliveryPlanning}, while every {@code WEBUI_M_ReceiptSchedule_*} class asks for its
 * record as {@code M_ReceiptSchedule} and {@code JavaProcess#getRecord} is {@code protected final}.
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

		final List<I_M_HU> hus = generatePlanningHUs(receiptSchedule, sourceIds.getDeliveryPlanningId());
		receiptFromReceiptScheduleService.updatePlanningHUAttributes(hus, receiptSchedule);

		// ... and that is where this action stops. The goods are booked by the CONFIRM inside the editor
		// (WEBUI_M_HU_CreateReceipt_*), which resolves this window's row back to its receipt schedule and its
		// delivery planning - see HUEditorReceiptSources.
		getResult().setRecordsToOpen(TableRecordReference.ofCollection(hus), HUsToReceiveViewFactory.WINDOW_ID_STRING);
	}

	/**
	 * The PLANNING HUs of one row, packed as the action's LU/TU configuration says - the same generation the
	 * receipt-schedule window's "HUs annehmen" runs.
	 * <p>
	 * Split out from {@link #receive} so that what the action does with the generated HUs can be exercised
	 * without driving the real generator, which needs an HU context and a saved LU/TU configuration.
	 */
	@VisibleForTesting
	protected List<I_M_HU> generatePlanningHUs(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@Nullable final DeliveryPlanningId deliveryPlanningId)
	{
		final IMutableHUContext huContextInitial = Services.get(IHUContextFactory.class)
				.createMutableHUContextForProcessing(
						getCtx(),
						ClientAndOrgId.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID()));

		final ReceiptScheduleHUGenerator huGenerator = ReceiptScheduleHUGenerator.newInstance(huContextInitial)
				.addM_ReceiptSchedule(receiptSchedule)
				.setUpdateReceiptScheduleDefaultConfiguration(isUpdateReceiptScheduleDefaultConfiguration());

		final I_M_HU_LUTU_Configuration lutuConfiguration =
				createLUTUConfiguration(ReceiptScheduleLUTUConfigurations.getCurrent(receiptSchedule), receiptSchedule);
		capToPlannedShare(lutuConfiguration, receiptSchedule, deliveryPlanningId);
		Services.get(ILUTUConfigurationFactory.class).save(lutuConfiguration);
		huGenerator.setM_HU_LUTU_Configuration(lutuConfiguration);

		final ILUTUProducerAllocationDestination lutuProducer = huGenerator.getLUTUProducerAllocationDestination();
		final Quantity qtyCUsTotal = lutuProducer.calculateTotalQtyCU();
		if (qtyCUsTotal.isInfinite())
		{
			throw new AdempiereException("LU/TU configuration is resulting to infinite quantity: " + lutuConfiguration);
		}
		final Quantity qtyToAllocate = getQtyToAllocate(qtyCUsTotal, receiptSchedule, deliveryPlanningId);
		huGenerator.setQtyToAllocateTarget(qtyToAllocate);

		return huGenerator.generateWithinOwnTransaction();
	}

	/**
	 * The planning of a single-row selection, or {@code null} when the selection is not a single PLANNED row.
	 * The receive quick actions are single-row, and an unplanned row has no planning to cap by.
	 */
	@Nullable
	protected final DeliveryPlanningId getSelectedDeliveryPlanningIdOrNull()
	{
		final DeliveryPlanningList selected = getSelectedDeliveryPlannings();
		return selected.size() == 1 ? selected.getIdsInAllocationOrder().get(0) : null;
	}

	/**
	 * Caps a SCHEDULE-derived LU/TU configuration to the selected planning's share.
	 * <p>
	 * {@code ReceiptScheduleLUTUConfigurations.adjustToDefaults} derives QtyTU from
	 * {@code getQtyToMoveTU(receiptSchedule)}, and a split copies {@code M_ReceiptSchedule_ID} onto every new
	 * planning - so on a planned row the derived configuration describes the WHOLE order line. That number is
	 * what the quick-action caption prints and what the operator's config form is pre-filled with, so without
	 * this a row planned for 50 out of a 100 schedule offers "100" while the allocation books 50: the user is
	 * shown one quantity and gets another.
	 * <p>
	 * Capping the CONFIGURATION rather than only the allocation fixes the caption, the pre-filled form and the
	 * booked quantity together, because all three read it. An UNPLANNED row has no share and is left alone -
	 * there the schedule-derived value is the correct one.
	 */
	protected final void capToPlannedShare(
			@NonNull final I_M_HU_LUTU_Configuration lutuConfig,
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@Nullable final DeliveryPlanningId deliveryPlanningId)
	{
		capToPlannedShare(lutuConfig, receiptFromReceiptScheduleService
				.getPlannedShareToReceive(receiptSchedule, deliveryPlanningId)
				.orElse(null));
	}

	/**
	 * The capping itself, split from the share lookup so it can be exercised without constructing a
	 * process - {@code ViewBasedProcessTemplate}'s constructor pulls a whole Spring graph that has nothing
	 * to do with this arithmetic.
	 *
	 * @param plannedShare {@code null} for an UNPLANNED row, which is left alone: there the
	 *                     schedule-derived configuration is the correct one.
	 */
	@VisibleForTesting
	static void capToPlannedShare(
			@NonNull final I_M_HU_LUTU_Configuration lutuConfig,
			@Nullable final Quantity plannedShare)
	{
		if (plannedShare == null)
		{
			return;
		}

		final BigDecimal qtyCUsPerTU = lutuConfig.getQtyCUsPerTU();
		if (lutuConfig.isInfiniteQtyCU() || qtyCUsPerTU == null || qtyCUsPerTU.signum() <= 0)
		{
			// Nothing to divide by: leave the configuration as derived rather than guess a TU count.
			return;
		}

		// UP, not HALF_UP: a partial TU still has to be received, so 55 CUs at 10 per TU needs 6 TUs.
		final BigDecimal cappedQtyTU = plannedShare.toBigDecimal().divide(qtyCUsPerTU, 0, RoundingMode.UP);
		if (cappedQtyTU.signum() <= 0 || cappedQtyTU.compareTo(lutuConfig.getQtyTU()) >= 0)
		{
			// The share is not the binding limit - the packing already fits inside it.
			return;
		}

		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		lutuConfig.setIsInfiniteQtyTU(false);
		lutuConfig.setQtyTU(cappedQtyTU);
		if (!lutuConfigurationFactory.isNoLU(lutuConfig))
		{
			lutuConfig.setIsInfiniteQtyLU(false);
			lutuConfig.setQtyLU(BigDecimal.valueOf(
					lutuConfigurationFactory.calculateQtyLUForTotalQtyTUs(lutuConfig, cappedQtyTU)));
		}
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
