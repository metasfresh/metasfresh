/*
 * #%L
 * metasfresh-material-dispo-commons
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

package de.metas.material.dispo.commons;

import de.metas.material.commons.disposition.SimulatedCleanUpService;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.DeleteCandidatesQuery;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SimulatedCandidateService
{
	@NonNull
	private final CandidateRepositoryWriteService candidateService;

	@NonNull
	private final List<SimulatedCleanUpService> simulatedCleanUpServiceList;

	public SimulatedCandidateService(
			final @NonNull CandidateRepositoryWriteService candidateService,
			final @NonNull List<SimulatedCleanUpService> simulatedCleanUpServiceList)
	{
		this.candidateService = candidateService;
		this.simulatedCleanUpServiceList = simulatedCleanUpServiceList;
	}

	@NonNull
	public Set<CandidateId> deleteAllSimulatedCandidates()
	{
		cleanUpSimulatedRelatedRecords();

		final DeleteCandidatesQuery deleteCandidatesQuery = DeleteCandidatesQuery.builder()
				.status(X_MD_Candidate.MD_CANDIDATE_STATUS_Simulated)
				.isActive(false)
				.build();

		return candidateService.deleteCandidatesAndDetailsByQuery(deleteCandidatesQuery);
	}

	public void deactivateAllSimulatedCandidates()
	{
		candidateService.deactivateSimulatedCandidates();
	}

	private void cleanUpSimulatedRelatedRecords()
	{
		for (final SimulatedCleanUpService cleanUpService : simulatedCleanUpServiceList)
		{
			try
			{
				cleanUpService.cleanUpSimulated();
			}
			catch (final Exception e)
			{
				Loggables.get().addLog("{0} failed to cleanup, see error: {1}", cleanUpService.getClass().getName(), e);
			}
		}
	}
}
