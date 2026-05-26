/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.split;

import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import de.metas.inoutcandidate.qty_reservation.QtyReservationService;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Bridges {@link OrderLineSplitCommand} (in de.metas.business) to the swat-side side-effect services.
 *
 * <p>On split:
 * <ul>
 *   <li>{@link #onNewLineSaved} — enqueues shipment-schedule creation and invalidates invoice candidates for the new line.</li>
 *   <li>{@link #onOriginalLineReduced} — shrinks qty-reservations on the original (now-reduced) line.</li>
 * </ul>
 *
 * me03 #29261 — Order Line Split.
 */
@Service
@RequiredArgsConstructor
public class OrderLineSplitListenerImpl implements IOrderLineSplitListener
{
	@NonNull private final QtyReservationService qtyReservationService;

	@Override
	public void onNewLineSaved(@NonNull final I_C_OrderLine newLine)
	{
		CreateMissingShipmentSchedulesWorkpackageProcessor.scheduleIfNotPostponed(newLine);
		Services.get(IInvoiceCandidateHandlerBL.class).invalidateCandidatesFor(newLine);
	}

	@Override
	public void onOriginalLineReduced(@NonNull final OrderLineId originalOrderLineId)
	{
		qtyReservationService.shrinkToFitOpenQty(originalOrderLineId);
	}
}
