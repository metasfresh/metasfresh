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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningList;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.ReceiptScheduleAndDeliveryPlanningId;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_RV_ReceiptDisposition_DeliveryPlanning;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * What every action started from the receipt-logistics window shares: how a selected grid row is turned back
 * into the records it stands for, and the ONE precondition all of them refuse on.
 * <p>
 * {@code RV_ReceiptDisposition_DeliveryPlanning} is a UNION of two branches - a <b>planned</b> row (an active {@code Incoming}
 * delivery planning carrying a receipt schedule) and an <b>unplanned</b> one (a receipt schedule no active
 * planning refers to) - so a selection routinely spans both. Every action therefore needs the same two things
 * from a row: the receipt schedule it is always about, and the planning that plans it when there is one.
 * <p>
 * <b>The precondition lives here, not in each action</b> (owner, 2026-09-02). A planning may hold AT MOST ONE
 * receipt or shipment, and the actions this window adds are NEW callers of the receive path - a guard each
 * action re-implemented would be a guard one of them could forget, and booking a second receipt against one
 * planning is exactly what that would allow. So the rule is asked once, of the whole selection, before anything
 * is produced.
 * <p>
 * Deliberately NOT extending {@code PickingJobScheduleViewBasedProcess}: that class is the shape this one is
 * modelled on, not a base to inherit - it resolves picking-job schedules and injects a picking service this
 * window has no use for.
 */
public abstract class ReceiptDispositionDeliveryPlanningViewBasedProcess extends ViewBasedProcessTemplate
{
	@NonNull protected final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	@NonNull protected final transient IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);

	/**
	 * Puts a view and a row selection in front of this process the way the platform does, so a unit test can
	 * assert WHICH records a selected row resolves to - the one thing these adapters own. {@code init} itself is
	 * {@code protected final} on {@code ViewBasedProcessTemplate} and therefore unreachable from a test class.
	 */
	@VisibleForTesting
	final void initForTesting(@NonNull final ViewAsPreconditionsContext context)
	{
		init(context);
	}

	/**
	 * The source ids of every selected row, in the view's own order, one entry per row - planned rows carrying
	 * their planning id, unplanned ones carrying {@code null} for it.
	 */
	protected final ImmutableList<ReceiptScheduleAndDeliveryPlanningId> getReceiptScheduleAndPlanningIds()
	{
		return getView().streamByIds(getSelectedRowIds())
				.map(ReceiptDispositionDeliveryPlanningViewBasedProcess::extractReceiptScheduleAndPlanningId)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * The two ids ONE row stands for. The receipt schedule is read with {@code ofRepoId} - MANDATORY, not
	 * skipped: both branches of the view select {@code M_ReceiptSchedule}'s own primary key, so a row without one
	 * cannot legitimately occur, and a later change to the view or to the window's field set that stopped
	 * exposing the column would otherwise make every selected row vanish silently and the action produce nothing
	 * while reporting success. The planning id is read with {@code ofRepoIdOrNull} because its absence is a real
	 * row shape - the unplanned branch - not a fault.
	 */
	@VisibleForTesting
	static ReceiptScheduleAndDeliveryPlanningId extractReceiptScheduleAndPlanningId(@NonNull final IViewRow row)
	{
		return ReceiptScheduleAndDeliveryPlanningId.of(
				row.getFieldValueAsRepoId(I_RV_ReceiptDisposition_DeliveryPlanning.COLUMNNAME_M_ReceiptSchedule_ID, ReceiptScheduleId::ofRepoId),
				row.getFieldValueAsNullableRepoId(I_RV_ReceiptDisposition_DeliveryPlanning.COLUMNNAME_M_Delivery_Planning_ID, DeliveryPlanningId::ofRepoId));
	}

	/**
	 * The plannings behind the selected rows, loaded ONCE so the precondition and the execution guard both read
	 * the same in-memory list instead of firing a query each.
	 * <p>
	 * Unplanned rows contribute nothing - they have no planning, and a receipt schedule nobody has planned yet
	 * carries no state that could refuse. An all-unplanned selection therefore yields
	 * {@link DeliveryPlanningList#EMPTY} without touching the database.
	 */
	protected final DeliveryPlanningList getSelectedDeliveryPlannings()
	{
		final ImmutableSet<DeliveryPlanningId> deliveryPlanningIds = getReceiptScheduleAndPlanningIds().stream()
				.map(ReceiptScheduleAndDeliveryPlanningId::getDeliveryPlanningId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		return deliveryPlanningIds.isEmpty()
				? DeliveryPlanningList.EMPTY
				: deliveryPlanningService.getProcessedStatePlannings(deliveryPlanningIds);
	}

	/**
	 * The shared precondition: accepted unless some planning in the selection is already processed, in which case
	 * the whole selection is refused and every offending row named. Shown on the disabled button, so the planner
	 * reads which rows to deselect before pressing anything.
	 */
	protected final ProcessPreconditionsResolution checkNoneProcessed(@NonNull final DeliveryPlanningList selectedDeliveryPlannings)
	{
		return deliveryPlanningService.getReceiveRejectionReason(selectedDeliveryPlannings)
				.map(ProcessPreconditionsResolution::reject)
				.orElseGet(ProcessPreconditionsResolution::accept);
	}

	/**
	 * The runtime backstop of {@link #checkNoneProcessed}, to be called BEFORE the action produces anything: a
	 * process can be invoked past its precondition, and an action that discovered the problem halfway through
	 * would leave part of the selection received and part not.
	 * <p>
	 * Raises the very message the precondition rejects with, so a planner who reaches this far reads the same
	 * sentence naming the same rows - the pattern
	 * {@code ReceiptScheduleDeliveryStopGuard#assertNoneBlocked} sets for the receipt-schedule path, which the
	 * actions on this window converge with rather than duplicate.
	 */
	protected final void assertNoneProcessed(@NonNull final DeliveryPlanningList selectedDeliveryPlannings)
	{
		deliveryPlanningService.getReceiveRejectionReason(selectedDeliveryPlannings)
				.ifPresent(reason -> {
					throw new AdempiereException(reason).markAsUserValidationError();
				});
	}

	/**
	 * The two source ids of the ONE selected row - every action on this window except the multi-row receive is
	 * single-selection, so a selection of any other size is a programmer error rather than something to iterate.
	 */
	@VisibleForTesting
	protected final ReceiptScheduleAndDeliveryPlanningId getSelectedSourceIds()
	{
		final ImmutableSet<ReceiptScheduleAndDeliveryPlanningId> sourceIds = ImmutableSet.copyOf(getReceiptScheduleAndPlanningIds());
		if (sourceIds.size() != 1)
		{
			throw new AdempiereException("Exactly one selected row is expected but got: " + sourceIds);
		}
		return sourceIds.iterator().next();
	}

	/**
	 * The receipt schedule of the ONE selected row - the record every pass-through action acts on, read off the
	 * GRID ROW rather than out of a record reference (which on this window resolves as {@code RV_ReceiptDisposition_DeliveryPlanning}).
	 */
	protected final I_M_ReceiptSchedule getSelectedReceiptSchedule()
	{
		return huReceiptScheduleBL.getById(getSelectedSourceIds().getReceiptScheduleId());
	}

	/**
	 * As {@link #getSelectedReceiptSchedule()}, but {@code null} when nothing is selected - for the one action
	 * that is offered on an empty selection too ("Leergut Ausgabe" / "Leergut Rücknahme", which then create an
	 * empty draft rather than one derived from a row).
	 */
	@Nullable
	protected final I_M_ReceiptSchedule getSelectedReceiptScheduleOrNull()
	{
		return getSelectedRowIds().isEmpty() ? null : getSelectedReceiptSchedule();
	}
}
