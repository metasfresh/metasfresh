package de.metas.inoutcandidate.api;

/*
 * #%L
 * de.metas.swat.base
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

import lombok.Builder;
import lombok.Value;

/**
 * Result of {@link IShipmentScheduleUpdater#updateShipmentSchedules(ShipmentScheduleUpdateInvalidRequest)}.
 */
@Value
@Builder
public class ShipmentScheduleUpdateInvalidResult
{
	/** number of shipment schedules that were actually recomputed by this run. */
	int updatedCount;

	/**
	 * {@code true} if, after this run's (possibly whole-product-bounded) tagging pass, more untagged
	 * {@code M_ShipmentSchedule_Recompute} markers remain -- i.e. the backlog is NOT drained and a follow-up
	 * run is needed.
	 * <p>
	 * <b>Deliberately NOT derived from {@code updatedCount} vs. the request's {@code maxToProcess}</b>: the
	 * tagging unit is a whole PRODUCT (see {@code ShipmentScheduleInvalidateRepository.markAllToRecomputeOutOfTrx}),
	 * so a single bounded pass can recompute fewer, exactly as many, or MORE schedules than {@code maxToProcess}
	 * (whenever the boundary product itself carries many schedules) -- an {@code updatedCount >= maxToProcess}
	 * check would misclassify both an over-sized boundary product (false positive: more work reported than
	 * remains) and a drained-but-large-first-product backlog (false negative). The reliable signal is instead
	 * whether untagged markers still exist after the pass.
	 * <p>
	 * Always {@code false} for an unlimited ({@link org.adempiere.ad.dao.QueryLimit#NO_LIMIT}) request,
	 * regardless of that signal -- the manual {@code M_ShipmentSchedule_Update} process must stay single-shot
	 * (no regression).
	 */
	boolean limitReached;
}
