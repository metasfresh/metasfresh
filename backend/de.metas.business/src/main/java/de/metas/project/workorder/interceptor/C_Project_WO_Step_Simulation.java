/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.project.workorder.interceptor;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.workorder.calendar.WOProjectSimulationRepository;
import de.metas.project.workorder.calendar.WOProjectSimulationService;
import de.metas.project.workorder.step.WOProjectStepSimulation;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Step_Simulation;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_Project_WO_Step_Simulation.class)
public class C_Project_WO_Step_Simulation
{
	@NonNull private final WOProjectSimulationService woProjectSimulationService;

	public C_Project_WO_Step_Simulation(@NonNull final WOProjectSimulationService woProjectSimulationService)
	{
		this.woProjectSimulationService = woProjectSimulationService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteWoStepSimulation(@NonNull final I_C_Project_WO_Step_Simulation record)
	{
		if (!InterfaceWrapperHelper.isUIAction(record))
		{
			return;
		}

		final WOProjectStepSimulation stepSimulation = WOProjectSimulationRepository.fromRecord(record);

		final SimulationPlanId selectedSimulationPlanId = SimulationPlanId.ofRepoId(record.getC_SimulationPlan_ID());

		woProjectSimulationService.deleteResourceForStepSimulation(stepSimulation, selectedSimulationPlanId);
	}
}
