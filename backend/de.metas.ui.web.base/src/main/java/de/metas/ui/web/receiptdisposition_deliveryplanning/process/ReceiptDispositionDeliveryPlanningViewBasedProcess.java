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
import org.compiere.model.I_RV_ReceiptDisposition_DeliveryPlanning;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * What every action started from the receipt-disposition delivery-planning window shares: how a selected grid
 * row is turned back into the records it stands for, and the ONE precondition all of them refuse on.
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
	 * The receipt schedule is read with {@code ofRepoId} - MANDATORY: both branches of the view select
	 * {@code M_ReceiptSchedule}'s own primary key, and a view change that stopped exposing the column would
	 * otherwise make every selected row vanish silently while the action reports success. The planning id is
	 * {@code ofRepoIdOrNull} because its absence is a real row shape, not a fault.
	 */
	@VisibleForTesting
	static ReceiptScheduleAndDeliveryPlanningId extractReceiptScheduleAndPlanningId(@NonNull final IViewRow row)
	{
		return ReceiptScheduleAndDeliveryPlanningId.of(
				row.getFieldValueAsRepoId(I_RV_ReceiptDisposition_DeliveryPlanning.COLUMNNAME_M_ReceiptSchedule_ID, ReceiptScheduleId::ofRepoId),
				row.getFieldValueAsNullableRepoId(I_RV_ReceiptDisposition_DeliveryPlanning.COLUMNNAME_M_Delivery_Planning_ID, DeliveryPlanningId::ofRepoId));
	}

	/**
	 * Loaded ONCE, so the precondition and the execution guard read the same in-memory list. Unplanned rows
	 * contribute nothing, so an all-unplanned selection yields {@link DeliveryPlanningList#EMPTY} with no query.
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
