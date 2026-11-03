package de.metas.handlingunits.picking;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IShipmentSchedulePickingInfoService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryFilter;
import org.springframework.stereotype.Service;

/**
 * Resolves {@link IShipmentSchedulePickingInfoService} for {@code de.metas.swat.base}, which does not depend on
 * this module (that's where {@code M_Picking_Job} lives).
 */
@Service
@RequiredArgsConstructor
public class ShipmentSchedulePickingInfoService implements IShipmentSchedulePickingInfoService
{
	@NonNull private final PickingJobRepository pickingJobRepository;

	@Override
	public IQueryFilter<I_M_ShipmentSchedule> newUnfinishedPickingFilter()
	{
		return pickingJobRepository.newUnfinishedPickingScheduleFilter();
	}
}
