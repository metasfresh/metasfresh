package de.metas.calendar.simulation;

import de.metas.common.util.time.SystemTime;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarSimulationService
{
	private static final Logger logger = LogManager.getLogger(CalendarSimulationService.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final CalendarSimulationRepository simulationRepository;
	private final CompositeCalendarSimulationServiceHook hooks;

	public CalendarSimulationService(
			final CalendarSimulationRepository simulationRepository,
			final Optional<List<CalendarSimulationServiceHook>> hooks)
	{
		this.simulationRepository = simulationRepository;

		this.hooks = CompositeCalendarSimulationServiceHook.of(hooks);
		logger.info("Hooks: {}", this.hooks);
	}

	public CalendarSimulationRef createNewSimulation(
			@Nullable String name,
			@Nullable CalendarSimulationId copyFromSimulationId,
			@NonNull UserId createdByUserId)
	{
		String nameToUse = StringUtils.trimBlankToNull(name);
		if (nameToUse == null)
		{
			nameToUse = TranslatableStrings.builder()
					.append(userDAO.retrieveUserFullName(createdByUserId))
					.append(" - ")
					.appendDateTime(SystemTime.asZonedDateTime())
					.build()
					.translate(Env.getADLanguageOrBaseLanguage());
		}

		final CalendarSimulationRef simulationRef = simulationRepository.createNewSimulation(nameToUse, createdByUserId);
		hooks.onNewSimulation(simulationRef, copyFromSimulationId);
		return simulationRef;
	}

	public CalendarSimulationRef getById(@NonNull CalendarSimulationId id)
	{
		return simulationRepository.getById(id);
	}

	public Collection<CalendarSimulationRef> getAll()
	{
		return simulationRepository.getAll();
	}
}
