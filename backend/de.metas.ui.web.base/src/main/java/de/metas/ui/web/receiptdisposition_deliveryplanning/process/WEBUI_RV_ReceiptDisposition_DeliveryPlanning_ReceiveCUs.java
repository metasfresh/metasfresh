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

import de.metas.Profiles;
import de.metas.deliveryplanning.ReceiptScheduleAndDeliveryPlanningId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * "CUs annehmen" on the receipt-disposition delivery-planning window: receive the selected row's remaining quantity as bare units.
 * <p>
 * The counterpart of {@code WEBUI_M_ReceiptSchedule_ReceiveCUs} - same quantity rule, same VHU - but it reads
 * its receipt schedule off the grid ROW rather than out of a record reference, which is what lets it also see
 * the row's delivery planning and hand it to the shared receive. Single-selection: the batch receive is its own
 * action.
 */
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveCUs extends ReceiptDispositionDeliveryPlanningReceiveProcess
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ProcessPreconditionsResolution shared = super.checkPreconditionsApplicable();
		if (!shared.isAccepted())
		{
			return shared;
		}

		if (!isQtyToReceiveKnownUpfront())
		{
			return shared;
		}

		// The ROW's own quantity, not the schedule's: on a planned row of a split the schedule can be exhausted
		// while this planning's share is still outstanding (and the other way round), so asking the schedule
		// would offer - or hide - the action against the wrong figure. Same rule the receive itself applies.
		return getQtyToReceive().signum() > 0
				? shared
				: ProcessPreconditionsResolution.rejectWithInternalReason("nothing to receive");
	}

	/**
	 * Whether the precondition can already tell how much would be received. False for the variant that takes the
	 * quantity as a parameter: there the operator has not typed anything yet, so an exhausted line must still
	 * offer the action (as {@code WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam} does with
	 * {@code setAllowNoQuantityAvailable(true)}).
	 */
	protected boolean isQtyToReceiveKnownUpfront()
	{
		return true;
	}

	@Override
	protected void receive(@NonNull final ReceiptScheduleAndDeliveryPlanningId sourceIds)
	{
		receiptFromReceiptScheduleService.receiveCUs(sourceIds, getQtyToReceiveOverrideOrNull());
	}

	/**
	 * What the selected row would receive with no quantity stated - resolved by the receive's own rule, so the
	 * precondition, the operator-facing default and the booking cannot drift apart.
	 */
	protected final Quantity getQtyToReceive()
	{
		return receiptFromReceiptScheduleService.getQtyToReceive(
				getSelectedReceiptSchedule(),
				getSelectedSourceIds().getDeliveryPlanningId());
	}

	/**
	 * {@code null} - this action states no quantity of its own, so the receive resolves it by the one rule
	 * {@link #getQtyToReceive()} also reports: where the row's delivery planning imposes a share, that share
	 * capped at what the schedule still has outstanding; otherwise (an unplanned row, or a planning that
	 * carries no discharge figure yet) the schedule's remainder. The variant that asks the operator
	 * overrides it.
	 */
	@Nullable
	protected BigDecimal getQtyToReceiveOverrideOrNull()
	{
		return null;
	}
}
