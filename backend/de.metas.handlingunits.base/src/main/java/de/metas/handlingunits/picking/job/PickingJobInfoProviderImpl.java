/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.job; // Or other appropriate package

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServicesFactory;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.PickingJobInfoProvider;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PickingJobInfoProviderImpl implements PickingJobInfoProvider
{

	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLoaderSupportingServicesFactory pickingJobLoaderSupportingServicesFactory;

	@Override
	public boolean isPickingInProgress(final ShipmentScheduleId id)
	{
		return pickingJobRepository.getPickingJobsByScheduleId(id, pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices())
				.stream()
				.map(PickingJob::getDocStatus)
				.anyMatch(PickingJobDocStatus::isNotProcessed);
	}
}
