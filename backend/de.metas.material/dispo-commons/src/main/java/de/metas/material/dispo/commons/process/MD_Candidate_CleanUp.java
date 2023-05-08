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

package de.metas.material.dispo.commons.process;

import de.metas.material.dispo.commons.SimulatedCandidateService;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.process.JavaProcess;
import de.metas.util.Loggables;
import org.compiere.SpringContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class MD_Candidate_CleanUp extends JavaProcess
{
	final SimulatedCandidateService candidateService = SpringContextHolder.instance.getBean(SimulatedCandidateService.class);

	@Override
	protected String doIt()
	{
		final Set<CandidateId> deletedCandidateIDs = candidateService.deleteAllSimulatedCandidates();

		final String deletedCandidatesString = deletedCandidateIDs.stream()
				.map(CandidateId::getRepoId)
				.map(String::valueOf)
				.collect(Collectors.joining(",", "[", "]"));

		Loggables.get().addLog("Deleted the following MD_Candidate_IDs: {}", deletedCandidatesString);

		return MSG_OK;
	}
}
