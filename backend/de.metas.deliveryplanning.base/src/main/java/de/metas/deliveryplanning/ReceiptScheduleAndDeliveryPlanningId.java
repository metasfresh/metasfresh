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

package de.metas.deliveryplanning;

import de.metas.inoutcandidate.ReceiptScheduleId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * The two source ids ONE receipt-logistics row stands for: the receipt schedule it is always about, and the
 * delivery planning that plans it - when one exists.
 * <p>
 * The nullable half IS the window's two branches. {@code RV_ReceiptDisposition_DeliveryPlanning} unions a <b>planned</b> row (an
 * active {@code Incoming} planning carrying a receipt schedule) with an <b>unplanned</b> one (a receipt schedule
 * no active planning refers to), so a receive action started from the grid gets a planning id for some selected
 * rows and none for others. Carrying that as {@code null} rather than as two collections keeps the two branches
 * one selection, which is what lets a single action serve both.
 * <p>
 * The receipt schedule is MANDATORY, on both branches: it is what the receipt is ultimately generated from, and
 * both branches of the view read it off {@code M_ReceiptSchedule}'s own primary key.
 */
@Value(staticConstructor = "of")
public class ReceiptScheduleAndDeliveryPlanningId
{
	@NonNull ReceiptScheduleId receiptScheduleId;

	/** {@code null} on an unplanned row - the receipt schedule nobody has planned yet. */
	@Nullable DeliveryPlanningId deliveryPlanningId;

	public static ReceiptScheduleAndDeliveryPlanningId ofReceiptScheduleId(@NonNull final ReceiptScheduleId receiptScheduleId)
	{
		return of(receiptScheduleId, null);
	}

	/** Whether this row is a PLANNED one - i.e. whether {@link #getDeliveryPlanningId()} is present. */
	public boolean isPlanned() {return deliveryPlanningId != null;}
}
