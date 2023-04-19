/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.project.workorder.resource;

import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.calendar.simulation.SimulationPlanService;
import de.metas.organization.OrgId;
import de.metas.project.workorder.calendar.WOProjectSimulationService;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_Project_WO_Resource.class)
public class C_Project_WO_Resource
{
	private final WOProjectSimulationService woProjectSimulationService;
	private final SimulationPlanService simulationService;

	public C_Project_WO_Resource(
			@NonNull final WOProjectSimulationService woProjectSimulationService,
			@NonNull final SimulationPlanService simulationService)
	{
		this.woProjectSimulationService = woProjectSimulationService;
		this.simulationService = simulationService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void createSimulationEntries(@NonNull final I_C_Project_WO_Resource record)
	{
		final WOProjectResource woProjectResource = WOProjectResourceRepository.ofRecord(record);
		if (woProjectResource.getDateRange() != null)
		{
			return;
		}

		final SimulationPlanRef editableMasterSimulationPlan = getEditableMasterSimulationPlan(record, woProjectResource.getOrgId());
		woProjectSimulationService.initializeSimulationForResource(woProjectResource, editableMasterSimulationPlan);
	}

	@NonNull
	private SimulationPlanRef getEditableMasterSimulationPlan(
			@NonNull final I_C_Project_WO_Resource resourceRecord,
			@NonNull final OrgId orgId)
	{
		final UserId responsibleUserId = UserId.ofRepoId(resourceRecord.getUpdatedBy());
		final SimulationPlanRef masterSimulationPlan = simulationService.getOrCreateMainSimulationPlan(responsibleUserId, orgId);

		masterSimulationPlan.assertEditable();

		return masterSimulationPlan;
	}
}
