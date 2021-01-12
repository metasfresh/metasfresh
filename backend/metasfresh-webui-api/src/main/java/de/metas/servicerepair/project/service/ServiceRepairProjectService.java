/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.servicerepair.project.service;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.order.OrderId;
import de.metas.project.ProjectAndLineId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectLine;
import de.metas.project.service.HUProjectService;
import de.metas.request.RequestId;
import de.metas.servicerepair.customerreturns.RepairCustomerReturnsService;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Service;

@Service
public class ServiceRepairProjectService
{
	public static final AdWindowId AD_WINDOW_ID = AdWindowId.ofRepoId(541015); // FIXME hardcoded

	private final HUProjectService projectService;
	private final RepairCustomerReturnsService repairCustomerReturnsService;

	public ServiceRepairProjectService(
			@NonNull final HUProjectService projectService,
			@NonNull final RepairCustomerReturnsService repairCustomerReturnsService)
	{
		this.projectService = projectService;
		this.repairCustomerReturnsService = repairCustomerReturnsService;
	}

	public I_C_Project getById(@NonNull final ProjectId projectId)
	{
		return projectService.getById(projectId);
	}

	public ProjectLine getLineById(final ProjectAndLineId projectLineId)
	{
		return projectService.getLineById(projectLineId);
	}

	public ProjectId createProjectFromRequest(final RequestId requestId)
	{
		return CreateServiceRepairProjectCommand.builder()
				.projectService(projectService)
				.repairCustomerReturnsService(repairCustomerReturnsService)
				.requestId(requestId)
				.build()
				.execute();
	}

	public OrderId createQuotationFromProject(final ProjectId projectId)
	{
		return CreateQuotationFromProjectCommand.builder()
				.projectService(projectService)
				.projectId(projectId)
				.build()
				.execute();
	}

	public void reserveHUs(final ProjectAndLineId projectLineId, final ImmutableSet<HuId> huIds)
	{
		projectService.reserveHUs(projectLineId, huIds);
	}

	public void releaseReservedHUs(final ImmutableSet<ProjectAndLineId> projectLineIds)
	{
		projectService.releaseReservedHUs(projectLineIds);
	}
}
