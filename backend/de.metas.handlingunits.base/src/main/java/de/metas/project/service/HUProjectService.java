/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.project.service;

import de.metas.project.ProjectId;
import lombok.NonNull;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Service;

@Service
public class HUProjectService
{
	private final ProjectService projectService;

	public HUProjectService(
			@NonNull final ProjectService projectService)
	{
		this.projectService = projectService;
	}

	public I_C_Project getById(@NonNull final ProjectId id)
	{
		return projectService.getById(id);
	}

	public ProjectId createProject(final CreateProjectRequest request)
	{
		return projectService.createProject(request);
	}

	// public void transferHUReservationsFromProjectToOrder(@NonNull final OrderId orderId)
	// {
	// 	for (final ProjectLine projectLine : projectService.getLinesByOrderId(orderId))
	// 	{
	// 		if (projectLine.getSalesOrderLineId() == null)
	// 		{
	// 			continue;
	// 		}
	//
	// 		final HUReservationDocRef from = HUReservationDocRef.ofProjectAndLineId(projectLine.getId());
	// 		final HUReservationDocRef to = HUReservationDocRef.ofSalesOrderLineId(projectLine.getSalesOrderLineId().getOrderLineId());
	// 		huReservationService.transferReservation(from, to);
	// 	}
	//
	// }
	//
	// public void transferHUReservationsFromOrderToProject(@NonNull final OrderId orderId)
	// {
	// 	for (final ProjectLine projectLine : projectService.getLinesByOrderId(orderId))
	// 	{
	// 		if (projectLine.getSalesOrderLineId() == null)
	// 		{
	// 			continue;
	// 		}
	//
	// 		final HUReservationDocRef from = HUReservationDocRef.ofSalesOrderLineId(projectLine.getSalesOrderLineId().getOrderLineId());
	// 		final HUReservationDocRef to = HUReservationDocRef.ofProjectAndLineId(projectLine.getId());
	// 		huReservationService.transferReservation(from, to);
	// 	}
	//
	// }
}
