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

package de.metas.project.workorder.step;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.project.workorder.calendar.WOProjectSimulationPlan;
import de.metas.project.workorder.calendar.WOProjectSimulationService;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.apache.commons.lang3.tuple.Pair;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WOProjectStepService
{
	private static final String SYS_CONFIG_WOStepDueDateTimeConfig = "de.metas.project.workorder.step.C_Project_WO_Step.DueDateTimeConfig";
	private static final AdMessageKey ERROR_MSG_STEP_CANNOT_BE_LOCKED_SIMULATION_FOUND = AdMessageKey.of("de.metas.project.workorder.step.StepCannotBeLockedSimulationFound");
	private static final AdMessageKey ERROR_MSG_STEP_NO_START_DATE_END_DATE_SPECIFIED = AdMessageKey.of("de.metas.project.workorder.interceptor.WOStepNoStartDateEndDateSpecified");

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@NonNull
	private final WOProjectStepRepository woProjectStepRepository;
	@NonNull
	private final WOProjectService woProjectService;
	@NonNull
	private final WOProjectSimulationService woProjectSimulationService;

	public WOProjectStepService(
			@NonNull final WOProjectStepRepository woProjectStepRepository,
			@NonNull final WOProjectService woProjectService,
			final @NonNull WOProjectSimulationService woProjectSimulationService)
	{

		this.woProjectStepRepository = woProjectStepRepository;
		this.woProjectService = woProjectService;
		this.woProjectSimulationService = woProjectSimulationService;
	}

	public void removeObsoleteSimulationsForLockedStep(@NonNull final WOProjectStep step)
	{
		if (step.getDateRange() == null)
		{
			throw new AdempiereException(ERROR_MSG_STEP_NO_START_DATE_END_DATE_SPECIFIED)
					.markAsUserValidationError();
		}

		final ImmutableList<WOProjectSimulationPlan> simulationPlans = woProjectSimulationService.getSimulationPlansForStep(step.getWoProjectStepId());

		final ImmutableSet<SimulationPlanId> simulationIdsWithDiffDates = simulationPlans
				.stream()
				.map(simulationPlan -> Pair.of(simulationPlan.getSimulationPlanId(), simulationPlan.getProjectStepByIdOrNull(step.getWoProjectStepId())))
				.filter(planId2Step -> planId2Step.getValue() != null)
				.filter(planId2Step -> !planId2Step.getValue().getDateRange().equals(step.getDateRange()))
				.map(Pair::getKey)
				.collect(ImmutableSet.toImmutableSet());

		if (!simulationIdsWithDiffDates.isEmpty())
		{
			throw buildSimulationFoundException(simulationIdsWithDiffDates);
		}

		final WOStepResources stepResources = woProjectService.getWOStepResources(step.getWoProjectStepId());

		simulationPlans
				.stream()
				.map(plan -> plan.removeSimulationForStepAndResources(stepResources))
				.forEach(woProjectSimulationService::savePlan);
	}

	public void setStepDueDate(@NonNull final WOProjectStep woProjectStep)
	{
		computeStepDueDate(woProjectStep.getWoProjectStepId())
				.map(computedDueDate -> woProjectStep.toBuilder()
						.woDueDate(computedDueDate)
						.build())
				.map(updatedStep -> woProjectStepRepository.updateAll(ImmutableList.of(updatedStep)));
	}

	@NonNull
	private Optional<Instant> computeStepDueDate(@NonNull final WOProjectStepId stepId)
	{
		final WOProject project = woProjectService.getById(stepId.getProjectId());

		if (project.getDateOfProvisionByBPartner() == null)
		{
			return Optional.empty();
		}

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(project.getClientId(), project.getOrgId());

		return computeConfiguredDueDate(project.getDateOfProvisionByBPartner(), clientAndOrgId);
	}

	@NonNull
	private Optional<Instant> computeConfiguredDueDate(@NonNull final Instant dateOfProvisioning, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(clientAndOrgId.getOrgId());

		return getConfiguredDueDateOffset(clientAndOrgId)
				.map(dueDateOffset -> {
					final LocalDate provisioningDateOnly = TimeUtil.asLocalDate(dateOfProvisioning, zoneId);

					Check.assumeNotNull(provisioningDateOnly, "provisioningDateOnly is actually not null");

					final LocalDate dueDate = provisioningDateOnly.plus(dueDateOffset);

					return TimeUtil.asInstant(dueDate, zoneId);
				});
	}

	@NonNull
	private Optional<Period> getConfiguredDueDateOffset(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final String woStepDueDateConfiguration = sysConfigBL.getValue(SYS_CONFIG_WOStepDueDateTimeConfig, clientAndOrgId);

		return Optional.ofNullable(woStepDueDateConfiguration)
				.map(Period::parse);
	}

	@NonNull
	private static AdempiereException buildSimulationFoundException(@NonNull final ImmutableSet<SimulationPlanId> simulationPlanIds)
	{
		final String errorSimulationIds = simulationPlanIds.stream()
				.map(SimulationPlanId::getRepoId)
				.map(String::valueOf)
				.collect(Collectors.joining(","));

		return new AdempiereException(ERROR_MSG_STEP_CANNOT_BE_LOCKED_SIMULATION_FOUND, errorSimulationIds)
				.markAsUserValidationError();
	}
}
