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
	 * {@code true} if the given budget was exhausted while at least one more model was still available to process
	 * (i.e. there is more work remaining and a follow-up run is needed to finish it).
	 */
	boolean limitReached;
}
