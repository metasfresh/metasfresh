package de.metas.inoutcandidate.spi;

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

import java.util.Set;

/**
 * Reports whether a shipment schedule still has picking work in progress.
 */
public interface IShipmentSchedulePickingInfoService
{
	/**
	 * @return the subset of {@code scheduleIds} that have an unfinished (Drafted) picking job.
	 */
	Set<ShipmentScheduleId> retrieveScheduleIdsWithUnfinishedPicking(@NonNull Set<ShipmentScheduleId> scheduleIds);
}
