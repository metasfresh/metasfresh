package de.metas.calendar.simulation;

import de.metas.common.util.time.SystemTime;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SimulationPlanService
{
	private static final Logger logger = LogManager.getLogger(SimulationPlanService.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final SimulationPlanRepository simulationPlanRepository;
	private final CompositeSimulationPlanServiceHook hooks;

	public SimulationPlanService(
			final SimulationPlanRepository simulationPlanRepository,
			final Optional<List<SimulationPlanServiceHook>> hooks)
	{
		this.simulationPlanRepository = simulationPlanRepository;

		this.hooks = CompositeSimulationPlanServiceHook.of(hooks);
		logger.info("Hooks: {}", this.hooks);
	}

	public SimulationPlanRef createNewSimulation(
			@Nullable String name,
			@Nullable SimulationPlanId copyFromSimulationId,
			@NonNull UserId responsibleUserId)
	{
		String nameToUse = StringUtils.trimBlankToNull(name);
		if (nameToUse == null)
		{
			nameToUse = generateName(responsibleUserId);
		}

		final SimulationPlanRef simulationRef = simulationPlanRepository.createNewSimulation(nameToUse, responsibleUserId);
		hooks.onNewSimulationPlan(simulationRef, copyFromSimulationId);
		return simulationRef;
	}

	private String generateName(final @NonNull UserId responsibleUserId)
	{
		final String name = TranslatableStrings.builder()
				.append(userDAO.retrieveUserFullName(responsibleUserId))
				.append(" - ")
				.appendDateTime(SystemTime.asZonedDateTime())
				.build()
				.translate(Env.getADLanguageOrBaseLanguage())
				.trim();
		if (simulationPlanRepository.isNameUnique(name))
		{
			return name;
		}

		for (int i = 1; i < 100; i++)
		{
			final String nameAndSuffix = name + " (" + i + ")";
			if (simulationPlanRepository.isNameUnique(nameAndSuffix))
			{
				return nameAndSuffix;
			}
		}

		// shall not happen
		throw new AdempiereException("Could not find a unique name after 100 tries");
	}

	public SimulationPlanRef getById(@NonNull SimulationPlanId id)
	{
		return simulationPlanRepository.getById(id);
	}

	public Collection<SimulationPlanRef> getAllNotProcessed()
	{
		return simulationPlanRepository.getAllNotProcessed();
	}
}
