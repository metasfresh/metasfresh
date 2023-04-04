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
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.project.workorder.calendar.WOProjectSimulationPlan;
import de.metas.project.workorder.calendar.WOProjectSimulationRepository;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WOProjectStepService
{
	private static final String SYS_CONFIG_WOStepDueDateTimeConfig = "de.metas.project.workorder.step.C_Project_WO_Step.DueDateTimeConfig";
	private static final AdMessageKey ERROR_MSG_STEP_CANNOT_BE_LOCKED_SIMULATION_FOUND = AdMessageKey.of("WOProjectStepService.StepCannotBeLockeSimulationFound");

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@NonNull
	private final WOProjectSimulationRepository woProjectSimulationRepository;
	@NonNull
	private final WOProjectRepository woProjectRepository;
	@NonNull
	private final WOProjectStepRepository woProjectStepRepository;

	public WOProjectStepService(
			@NonNull final WOProjectSimulationRepository woProjectSimulationRepository,
			@NonNull final WOProjectRepository woProjectRepository,
			@NonNull final WOProjectStepRepository woProjectStepRepository)
	{
		this.woProjectSimulationRepository = woProjectSimulationRepository;
		this.woProjectRepository = woProjectRepository;
		this.woProjectStepRepository = woProjectStepRepository;
	}

	public void validateWOStep(@NonNull final WOProjectStep step)
	{
		final ImmutableList.Builder<SimulationPlanId> existingSimulationPlanIdsBuilder = ImmutableList.builder();

		final Collection<WOProjectSimulationPlan> simulationPlans = woProjectSimulationRepository.getSimulationPlansByStepIds(step.getWoProjectStepId());

		simulationPlans.forEach(simulationPlan -> {
			final CalendarDateRange simulationDateRange = Optional.ofNullable(simulationPlan.getProjectStepByIdOrNull(step.getWoProjectStepId()))
					.map(WOProjectStepSimulation::getDateRange)
					.orElse(null);

			if (CalendarDateRange.equals(simulationDateRange, step.getDateRange()))
			{
				return;
			}

			existingSimulationPlanIdsBuilder.add(simulationPlan.getSimulationPlanId());
		});

		final ImmutableList<SimulationPlanId> existingSimulationPlanIds = existingSimulationPlanIdsBuilder.build();

		if (existingSimulationPlanIds.isEmpty())
		{
			return;
		}

		final String errorSimulationIds = existingSimulationPlanIds.stream()
				.map(SimulationPlanId::getRepoId)
				.map(Object::toString)
				.collect(Collectors.joining(","));

		throw new AdempiereException(ERROR_MSG_STEP_CANNOT_BE_LOCKED_SIMULATION_FOUND, errorSimulationIds)
				.markAsUserValidationError();
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
		final WOProject existingProject = woProjectRepository.getById(stepId.getProjectId());

		if (existingProject.getDateOfProvisionByBPartner() == null)
		{
			return Optional.empty();
		}

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(existingProject.getClientId(), existingProject.getOrgId());

		return computeConfiguredDueDate(existingProject.getDateOfProvisionByBPartner(), clientAndOrgId);
	}

	@NonNull
	private Optional<Instant> computeConfiguredDueDate(@NonNull final Instant dateOfProvisioning, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(clientAndOrgId.getOrgId());

		return getConfiguredPeriod(clientAndOrgId)
				.map(period -> {
					final LocalDate localDate = TimeUtil.asLocalDate(dateOfProvisioning, zoneId);

					Check.assumeNotNull(localDate, "DateOfProvisioning cannot be null at this stage");

					final LocalDate computedLocalDate = localDate.plus(period);

					return TimeUtil.asInstant(computedLocalDate, zoneId);
				});
	}

	@NonNull
	private Optional<Period> getConfiguredPeriod(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final String woStepDueDateConfiguration = sysConfigBL.getValue(SYS_CONFIG_WOStepDueDateTimeConfig, clientAndOrgId);

		return Optional.ofNullable(woStepDueDateConfiguration)
				.map(Period::parse);
	}
}
