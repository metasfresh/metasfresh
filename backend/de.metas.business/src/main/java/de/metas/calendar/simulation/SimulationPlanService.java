package de.metas.calendar.simulation;

import de.metas.common.util.time.SystemTime;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
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
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final SimulationPlanRepository simulationPlanRepository;
	private final CompositeSimulationPlanServiceHook hooks;
	private final SimulationPlanChangesDispatcher changesDispatcher = new SimulationPlanChangesDispatcher();

	public SimulationPlanService(
			final SimulationPlanRepository simulationPlanRepository,
			final Optional<List<SimulationPlanServiceHook>> hooks)
	{
		this.simulationPlanRepository = simulationPlanRepository;

		this.hooks = CompositeSimulationPlanServiceHook.of(hooks);
		logger.info("Hooks: {}", this.hooks);
	}

	public void subscribe(@NonNull final SimulationPlanId simulationId, @NonNull final SimulationPlanChangesListener listener)
	{
		changesDispatcher.subscribe(simulationId, listener);
	}

	public void unsubscribe(@NonNull final SimulationPlanId simulationId, @NonNull final SimulationPlanChangesListener listener)
	{
		changesDispatcher.unsubscribe(simulationId, listener);
	}

	@NonNull
	public SimulationPlanRef createNewSimulation(@NonNull final SimulationPlanCreateRequest simulationPlanCreateRequest)
	{
		return trxManager.callInThreadInheritedTrx(() -> createNewSimulationInTrx(simulationPlanCreateRequest));
	}

	public SimulationPlanRef createNewSimulationInTrx(@NonNull final SimulationPlanCreateRequest simulationPlanCreateRequest)
	{
		final SimulationPlanRef simulationRef = simulationPlanRepository.createNewSimulation(
				simulationPlanCreateRequest,
				() -> generateName(simulationPlanCreateRequest));

		hooks.onNewSimulationPlan(simulationRef, simulationPlanCreateRequest.getCopyFromSimulationId());
		return simulationRef;
	}

	private String generateName(final @NonNull SimulationPlanCreateRequest request)
	{
		final String name = TranslatableStrings.builder()
				.append(request.isMainSimulationPlan() ? "* " : "")
				.append(userDAO.retrieveUserFullName(request.getResponsibleUserId()))
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

	public Collection<SimulationPlanRef> getAllDrafts(@Nullable final SimulationPlanId alwaysIncludeId)
	{
		return simulationPlanRepository.getAllDrafts(alwaysIncludeId);
	}

	public void complete(@NonNull final SimulationPlanId simulationPlanId)
	{
		trxManager.runInThreadInheritedTrx(() -> completeInTrx(simulationPlanId));
	}

	@NonNull
	public SimulationPlanRef getOrCreateMainSimulationPlan(
			@NonNull final UserId responsibleUserId,
			@NonNull final OrgId orgId)
	{
		final Optional<SimulationPlanRef> currentMainSimulationPlan = simulationPlanRepository.getCurrentMainSimulationPlan(orgId);
		if (currentMainSimulationPlan.isPresent())
		{
			return currentMainSimulationPlan.get();
		}
		else
		{
			// dev-note: we want to avoid race condition
			synchronized (this)
			{
				return simulationPlanRepository.getCurrentMainSimulationPlan(orgId)
						// dev-note: callInNewTrx so the just created simulation is visible across all ongoing transactions
						.orElseGet(() -> trxManager.callInNewTrx(() -> createNewSimulation(SimulationPlanCreateRequest.builder()
																								   .responsibleUserId(responsibleUserId)
																								   .orgId(orgId)
																								   .isMainSimulationPlan(true)
																								   .build())));
			}
		}
	}
	
	private void completeInTrx(@NonNull final SimulationPlanId simulationPlanId)
	{
		SimulationPlanRef simulationRef = simulationPlanRepository.getById(simulationPlanId);
		if (!simulationRef.getDocStatus().isDrafted())
		{
			throw new AdempiereException("Only Drafted simulations can be completed");
		}

		hooks.onComplete(simulationRef);

		simulationRef = simulationPlanRepository.changeDocStatus(simulationPlanId, SimulationPlanDocStatus.Completed);

		changesDispatcher.notifyOnAfterComplete(simulationRef);
	}
}
