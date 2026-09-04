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

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * What {@link DeliveryPlanningService#cancelDelivery} did with a selection, per row: cancelled, or skipped because
 * it was already closed ({@code Cancel} refuses only the closed rows, not the whole selection).
 * <p>
 * {@link #getSkippedAllocatedIds()} is a NARROWER skip than {@link #getSkippedClosedIds()}: a planning listed
 * there is still fully cancelled - voided, and its {@code IsClosed}/{@code Processed}/{@code OrderStatus} set
 * same as any other cancelled row - it is named only because it was still allocated to a delivery instruction
 * when the cancel ran, so its {@code PlannedLoadedQuantity}/{@code PlannedDischargeQuantity} are committed
 * cargo and cancel leaves them untouched rather than zeroing them. A planning can appear in both
 * {@link #getCancelledIds()} and here.
 */
@Value
@Builder
public class DeliveryPlanningCancelResult
{
	@NonNull ImmutableList<DeliveryPlanningId> cancelledIds;
	@NonNull ImmutableList<DeliveryPlanningId> skippedClosedIds;
	@NonNull ImmutableList<DeliveryPlanningId> skippedAllocatedIds;
}
