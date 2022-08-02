/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.picking.workflow.handlers.activity_handlers;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.workflow.rest_api.model.WFProcess;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Stream;

@UtilityClass
public class PickingWFActivityHelper
{
	public static PickingJob getPickingJob(@NonNull final WFProcess wfProcess)
	{
		return wfProcess.getDocumentAs(PickingJob.class);
	}

	public static ImmutableSet<ShipmentScheduleId> extractShipmentScheduleIds(@NonNull final List<WFProcess> wfProcesses)
	{
		return wfProcesses.stream()
				.flatMap(PickingWFActivityHelper::streamShipmentScheduleIds)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static Stream<ShipmentScheduleId> streamShipmentScheduleIds(@NonNull final WFProcess wfProcess)
	{
		return getPickingJob(wfProcess).streamShipmentScheduleIds();
	}
}
