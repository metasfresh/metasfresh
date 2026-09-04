/*
 * #%L
 * de.metas.swat.base
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

package de.metas.deliveryplanning.interceptor;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningRepository;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.shipping.TransportDirection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static org.adempiere.model.InterfaceWrapperHelper.isUIAction;

@Interceptor(I_M_Delivery_Planning.class)
@Component
@RequiredArgsConstructor
public class M_Delivery_Planning
{
	@NonNull private final DeliveryPlanningService deliveryPlanningService;

	/**
	 * Refuses the delete while a live allocation still points here, then removes the retired allocation history.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDelete(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanningService.assertNotCurrentlyAllocated(deliveryPlanning);

		if (isUIAction(deliveryPlanning))
		{
			deliveryPlanningService.validateDeletion(deliveryPlanning);
		}

		// only retired history can still be pointing here, the assert above having refused every live booking
		deliveryPlanningService.deleteAllocationsFor(DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID()));
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_M_Delivery_Planning.COLUMNNAME_ATD)
	public void onActualLoadingDateChanged(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanningService.invalidateInvoiceCandidatesFor(deliveryPlanning);
	}

	/**
	 * Keeps an inbound (or dropship) planning's derived {@code ActualLoadQty} equal to its planned load
	 * whenever the plan is edited (D22/Task Q7c) - nothing ever reports the vendor's load, so the plan is
	 * its only source. {@code TYPE_BEFORE_CHANGE} so the mutation rides along in the same UPDATE the caller
	 * already triggered: no extra query, no extra save.
	 * <p>
	 * Never touches {@code ActualDischargeQuantity} - that end is the receipt's once booked (Task Q11), and
	 * is not watched here at all.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_M_Delivery_Planning.COLUMNNAME_PlannedLoadedQuantity)
	public void onPlannedLoadedQuantityChanged(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		final TransportDirection transportDirection = TransportDirection.ofCode(deliveryPlanning.getTransportDirection());
		if (!transportDirection.isIncomingOrDropship())
		{
			return;
		}

		deliveryPlanning.setActualLoadQty(deliveryPlanning.getPlannedLoadedQuantity());
	}

	/**
	 * Keeps {@code QtyTotalOpen} and {@code QtyTotalOpenPlanned} live (Task Q8) whenever a NEW planning joins an
	 * order line - a split's new plannings, or a schedule's first-generated one. {@code AFTER_NEW} rather than
	 * {@code BEFORE}: the recompute reads every planning of the line back out via a fresh query, which needs this
	 * row's own insert already flushed to be counted.
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void onNew(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanningService.recomputeOpenQuantitiesForOrderLine(deliveryPlanning);
	}

	/**
	 * Keeps {@code QtyTotalOpen} and {@code QtyTotalOpenPlanned} live (Task Q8) whenever any of the four figures
	 * they are computed from changes on ANY planning of the line - the split's rewrite of the target's own
	 * planned figure (unallocated branch), a direct planned/actual edit, or the {@code ActualLoadQty} an incoming
	 * planning's own {@link #onPlannedLoadedQuantityChanged} above moves in lockstep with its plan.
	 * <p>
	 * {@code AFTER_CHANGE}, not {@code BEFORE}: the recompute's own query must see this row's new value already
	 * committed within the transaction, same reason as {@link #onNew}. Both figures are order-line TOTALS
	 * redundantly displayed on every sibling row (see {@link
	 * DeliveryPlanningRepository#recomputeOpenQuantitiesForOrderLine}), so recomputing writes every sibling, not
	 * just this row - which is safe to re-enter from here because neither of the two columns it writes
	 * ({@code QtyTotalOpen}, {@code QtyTotalOpenPlanned}) is itself watched by this handler.
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = {
			I_M_Delivery_Planning.COLUMNNAME_PlannedLoadedQuantity,
			I_M_Delivery_Planning.COLUMNNAME_PlannedDischargeQuantity,
			I_M_Delivery_Planning.COLUMNNAME_ActualLoadQty,
			I_M_Delivery_Planning.COLUMNNAME_ActualDischargeQuantity })
	public void onQuantityChanged(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanningService.recomputeOpenQuantitiesForOrderLine(deliveryPlanning);

		// Task Q14 (TC11): the delivery instruction's Versandpaket line is a read-through of these four
		// figures, so this is also the moment an OPEN Lieferanweisungen document has to be told to re-read
		// that row. The AD_SQLColumn_SourceTableColumn rows the columns carry cannot do it - they invalidate
		// the model cache, not the document; see DeliveryInstructionLineCacheInvalidation.
		deliveryPlanningService.invalidateDeliveryInstructionLinesFor(deliveryPlanning);
	}

	/**
	 * Keeps {@code QtyTotalOpen} and {@code QtyTotalOpenPlanned} live (Task Q8, fix round) on a deleted
	 * planning's SURVIVING siblings - without this, a delete leaves every remaining planning of the line
	 * showing a stale total (the deleted row's own contribution never drops out) until some unrelated later
	 * write happens to refresh it: the same frozen-figure defect this plan exists to remove, reached by a
	 * different path, and worse - it under-reports open quantity with no signal to the operator, and a later
	 * split then distributes too little.
	 * <p>
	 * Three write paths reach this one hook, all going through the ordinary per-row PO delete lifecycle (never
	 * {@code deleteDirectly()}, confirmed against the local DB: every FK off {@code M_Delivery_Planning} is
	 * {@code ON DELETE NO ACTION}, so there is no DB-cascade path that could remove a row without firing this
	 * interceptor): a single planning deleted directly (this class's own {@code onDelete} above, which already
	 * refuses the case with no survivor - {@code MSG_M_Delivery_Planning_AtLeastOnePerOrderLine} - so THIS
	 * hook's line always has at least one row left when that path is the trigger); and the two bulk deletes,
	 * {@link DeliveryPlanningRepository#deleteForReceiptSchedule} / {@code #deleteForShipmentSchedule}, which
	 * are not routed through that same "at least one" guard and can legitimately empty a line entirely (every
	 * planning of a schedule sharing it, all removed together) - {@link
	 * DeliveryPlanningRepository#recomputeOpenQuantitiesForOrderLine} already no-ops on an empty line, so
	 * that case costs one query and nothing else.
	 * <p>
	 * {@code AFTER_DELETE}, not {@code BEFORE}: on {@code BEFORE_DELETE} this row still exists in the DB and
	 * the recompute's query would count its own soon-to-be-gone claim, needing a manual self-exclusion (the
	 * split's own {@code excludePlanningId} shape); on {@code AFTER_DELETE} the row is already gone from the
	 * table, so the same plain re-query {@link #onNew}/{@link #onQuantityChanged} already use naturally
	 * returns only the survivors - no exclusion parameter needed, one shape for all three "the line's
	 * membership or figures changed" triggers. The deleted record's own {@code C_OrderLine_ID} is still read
	 * off THIS in-memory Java object (its row is gone from the DB, not from this object), same as {@code
	 * M_Delivery_Planning_Alloc}'s own {@code TYPE_AFTER_DELETE} hook reads its FK the same way.
	 * <p>
	 * Cost: one {@code SELECT} plus one {@code UPDATE} per surviving planning of the line - the same shape as
	 * {@link #onNew}/{@link #onQuantityChanged} above, not a new pattern. A bulk schedule-cascade delete that
	 * removes K plannings off one line in a single call fires this hook K times, each against whatever
	 * currently remains, so the total cost is bounded by (a small, per-order-line count of plannings)² in the
	 * worst case - the whole-line-wiped case - never by anything outside this one order line.
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void onDeleted(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanningService.recomputeOpenQuantitiesForOrderLine(deliveryPlanning);
	}
}
