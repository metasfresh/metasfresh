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
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * What every action started from the receipt-disposition delivery-planning window - or, for the receive-HUs
 * pair, from the delivery-planning window - shares: how a selected grid row is turned back into the records it
 * stands for, and the preconditions they refuse on.
 * <p>
 * {@link IProcessPrecondition} is declared HERE, and is load-bearing: {@code ProcessPreconditionChecker} looks
 * a process' preconditions up by {@code IProcessPrecondition.class.isAssignableFrom(processClass)} and, finding
 * nothing, falls through to {@code accept()} - so without the interface every {@link
 * #checkPreconditionsApplicable()} override below this class is dead code and every action is offered on every
 * row and every selection size.
 */
public abstract class ReceiptDispositionDeliveryPlanningViewBasedProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@NonNull protected final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	@NonNull protected final transient IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);

	/**
	 * {@code init} is {@code protected final} on {@code ViewBasedProcessTemplate} and therefore unreachable from a
	 * test class.
	 */
	@VisibleForTesting
	final void initForTesting(@NonNull final ViewAsPreconditionsContext context)
	{
		init(context);
	}

	protected final ImmutableList<ReceiptScheduleAndDeliveryPlanningId> getReceiptScheduleAndPlanningIds()
	{
		return getView().streamByIds(getSelectedRowIds())
				.map(ReceiptDispositionDeliveryPlanningViewBasedProcess::extractReceiptScheduleAndPlanningId)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * The receipt schedule is read with {@code ofRepoId} - MANDATORY: every row this is called for selects
	 * {@code M_ReceiptSchedule}'s own primary key, and a change that stopped exposing the column would otherwise
	 * make every selected row vanish silently while the action reports success. The planning id is
	 * {@code ofRepoIdOrNull} because its absence is a real row shape, not a fault.
	 * <p>
	 * The two column names are taken from {@code M_Delivery_Planning} although the row may equally be a
	 * {@code RV_ReceiptDisposition_DeliveryPlanning} one: both tables spell the two columns identically, and the
	 * delivery-planning window is the only caller whose rows can lack a receipt schedule - see
	 * {@link #checkNoneOutgoing}.
	 */
	@VisibleForTesting
	static ReceiptScheduleAndDeliveryPlanningId extractReceiptScheduleAndPlanningId(@NonNull final IViewRow row)
	{
		return ReceiptScheduleAndDeliveryPlanningId.of(
				row.getFieldValueAsRepoId(I_M_Delivery_Planning.COLUMNNAME_M_ReceiptSchedule_ID, ReceiptScheduleId::ofRepoId),
				extractDeliveryPlanningIdOrNull(row));
	}

	@Nullable
	private static DeliveryPlanningId extractDeliveryPlanningIdOrNull(@NonNull final IViewRow row)
	{
		return row.getFieldValueAsNullableRepoId(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, DeliveryPlanningId::ofRepoId);
	}

	/**
	 * Loaded ONCE, so the precondition and the execution guard read the same in-memory list. Unplanned rows
	 * contribute nothing, so an all-unplanned selection yields {@link DeliveryPlanningList#EMPTY} with no query.
	 */
	protected final DeliveryPlanningList getSelectedDeliveryPlannings()
	{
		// Read straight off the rows rather than via getReceiptScheduleAndPlanningIds(): that one insists on a
		// receipt schedule, which an outgoing delivery planning has not got - and this list is what
		// checkNoneOutgoing asks the direction of, i.e. it runs BEFORE anything may insist.
		final ImmutableSet<DeliveryPlanningId> deliveryPlanningIds = getView().streamByIds(getSelectedRowIds())
				.map(ReceiptDispositionDeliveryPlanningViewBasedProcess::extractDeliveryPlanningIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		return deliveryPlanningIds.isEmpty()
				? DeliveryPlanningList.EMPTY
				: deliveryPlanningService.getProcessedStatePlannings(deliveryPlanningIds);
	}

	/**
	 * Refuses a selection holding an OUTGOING delivery planning - the shape only the delivery-planning window can
	 * produce, where the grid lists all three transport directions instead of the receipt-disposition view's
	 * incoming/dropship rows. Such a planning has no {@code M_ReceiptSchedule_ID}, so without this guard the
	 * receive would not merely be inapplicable: {@link #extractReceiptScheduleAndPlanningId} would fail its
	 * assumption and the frontend would show the action refused with THAT message.
	 * <p>
	 * INTERNAL reason: on an outgoing planning the action is not "temporarily unavailable" but nonsense, so it
	 * disappears rather than sitting disabled in the quick-action slot.
	 */
	protected final ProcessPreconditionsResolution checkNoneOutgoing(@NonNull final DeliveryPlanningList selectedDeliveryPlannings)
	{
		return selectedDeliveryPlannings.stream()
				.filter(deliveryPlanning -> !deliveryPlanning.getTransportDirection().isIncomingOrDropship())
				.findFirst()
				.map(deliveryPlanning -> ProcessPreconditionsResolution.rejectWithInternalReason(
						"outgoing delivery planning " + deliveryPlanning.getId().getRepoId() + " has nothing to receive"))
				.orElseGet(ProcessPreconditionsResolution::accept);
	}

	protected final ProcessPreconditionsResolution checkNoneProcessed(@NonNull final DeliveryPlanningList selectedDeliveryPlannings)
	{
		return deliveryPlanningService.getReceiveRejectionReason(selectedDeliveryPlannings)
				.map(ProcessPreconditionsResolution::reject)
				.orElseGet(ProcessPreconditionsResolution::accept);
	}

	/**
	 * The runtime backstop of {@link #checkNoneProcessed}, called BEFORE the action produces anything: a process
	 * can be invoked past its precondition, and discovering the problem halfway through would leave part of the
	 * selection received and part not.
	 */
	protected final void assertNoneProcessed(@NonNull final DeliveryPlanningList selectedDeliveryPlannings)
	{
		deliveryPlanningService.getReceiveRejectionReason(selectedDeliveryPlannings)
				.ifPresent(reason -> {
					throw new AdempiereException(reason);
				});
	}

	/**
	 * Single-selection: every action here except the multi-row receive rejects any other selection size as a programmer error.
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
	 * Read off the GRID ROW rather than out of a record reference, which on this window resolves as
	 * {@code RV_ReceiptDisposition_DeliveryPlanning}.
	 */
	protected final I_M_ReceiptSchedule getSelectedReceiptSchedule()
	{
		return huReceiptScheduleBL.getById(getSelectedSourceIds().getReceiptScheduleId());
	}

	/**
	 * {@code null} when nothing is selected - for the one action that is offered on an empty selection too.
	 */
	@Nullable
	protected final I_M_ReceiptSchedule getSelectedReceiptScheduleOrNull()
	{
		return getSelectedRowIds().isEmpty() ? null : getSelectedReceiptSchedule();
	}
}
