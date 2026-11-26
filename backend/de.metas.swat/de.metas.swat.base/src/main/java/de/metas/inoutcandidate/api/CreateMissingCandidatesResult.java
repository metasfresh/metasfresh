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

import de.metas.inout.ShipmentScheduleId;
import lombok.NonNull;
import lombok.Value;

import java.util.Set;

/**
 * Result of a (possibly budget-limited) {@link IShipmentScheduleHandlerBL#createMissingCandidates(java.util.Properties, org.adempiere.ad.dao.QueryLimit)} run.
 */
@Value
public class CreateMissingCandidatesResult
{
	@NonNull Set<ShipmentScheduleId> createdShipmentScheduleIds;

	/**
	 * {@code true} if the given budget was fully consumed (all handlers combined processed exactly {@code maxToProcess}
	 * models), signalling that a follow-up run should be enqueued. Note this can be {@code true} with zero actual work
	 * remaining in the rare case the backlog was an exact multiple of the budget — that follow-up run then finds and
	 * processes nothing and reports {@code false}, so the re-enqueue chain terminates. Always {@code false} for an
	 * unlimited ({@link org.adempiere.ad.dao.QueryLimit#NO_LIMIT}) budget.
	 */
	boolean limitReached;
}
