/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning.receipt;

import com.google.common.collect.ImmutableSet;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.handlingunits.HuId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/**
 * The ONE request every receive in this domain is made of: a receipt schedule, the planning HUs to book against
 * it, and - <b>nullable</b> - the delivery planning the receipt belongs to.
 * <p>
 * <b>The nullable planning id is the whole design.</b> There is one receive path, not two:
 * <ul>
 *     <li><b>present</b> - the planning-aware path. The id travels all the way into
 *     {@code CreateReceiptsParameters#deliveryPlanningId}, so the receipt header carries it while still a draft;
 *     the completion that happens inside the same call then fires {@code de.metas.deliveryplanning}'s
 *     {@code TIMING_AFTER_COMPLETE} interceptor, which is what derives the planning's delivered state, its
 *     actual discharge quantity, its {@code Processed} flag and the receipt back-link. Setting the id on the
 *     finished receipt instead is too late and silently produces an unlinked planning.</li>
 *     <li><b>absent</b> - the plain receipt against the schedule, exactly as the receipt-schedule window
 *     produces it. Nothing is stamped, nothing is derived.</li>
 * </ul>
 * That is why the receipt-disposition delivery-planning window, whose grid unions planned and unplanned rows, can serve both row
 * types with a single action: the row decides whether the id is there, not the action.
 */
@Value
public class CreateReceiptFromReceiptScheduleRequest
{
	@NonNull ReceiptScheduleId receiptScheduleId;

	/**
	 * {@code null} for a receipt that belongs to no delivery planning - the unplanned row, and every caller
	 * outside the delivery-planning domain.
	 */
	@Nullable DeliveryPlanningId deliveryPlanningId;

	/** The planning HUs to book. Never empty: a receipt with no HUs would complete with no lines. */
	@NonNull ImmutableSet<HuId> huIdsToReceive;

	/** How the created receipt's movement date is decided - a fixed date, or the current one. */
	@NonNull ReceiptMovementDateRule movementDateRule;

	@Builder
	private CreateReceiptFromReceiptScheduleRequest(
			@NonNull final ReceiptScheduleId receiptScheduleId,
			@Nullable final DeliveryPlanningId deliveryPlanningId,
			@NonNull final ImmutableSet<HuId> huIdsToReceive,
			@NonNull final ReceiptMovementDateRule movementDateRule)
	{
		if (huIdsToReceive.isEmpty())
		{
			throw new AdempiereException("At least one HU to receive is required for " + receiptScheduleId);
		}

		this.receiptScheduleId = receiptScheduleId;
		this.deliveryPlanningId = deliveryPlanningId;
		this.huIdsToReceive = huIdsToReceive;
		this.movementDateRule = movementDateRule;
	}
}
