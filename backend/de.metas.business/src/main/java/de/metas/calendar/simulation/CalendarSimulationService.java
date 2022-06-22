package de.metas.calendar.simulation;

import de.metas.common.util.time.SystemTime;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;

@Service
public class CalendarSimulationService
{
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final CalendarSimulationRepository simulationRepository;

	public CalendarSimulationService(
			final CalendarSimulationRepository simulationRepository)
	{
		this.simulationRepository = simulationRepository;
	}

	public CalendarSimulationRef createNewSimulation(
			@Nullable String name,
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

		return simulationRepository.createNewSimulation(nameToUse, createdByUserId);
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
