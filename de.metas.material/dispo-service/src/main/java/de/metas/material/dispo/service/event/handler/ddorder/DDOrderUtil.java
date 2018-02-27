package de.metas.material.dispo.service.event.handler.ddorder;

import java.util.List;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2017 metas GmbH
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

@UtilityClass
public class DDOrderUtil
{
	public List<Candidate> retrieveCandidatesForDDOrderId(
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			final int ddOrderId)
	{
		final CandidatesQuery query = CandidatesQuery.builder()
				.distributionDetailsQuery(DistributionDetailsQuery.builder()
						.ddOrderId(ddOrderId).build())
				.build();
		final List<Candidate> candidatesToUpdate = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query);
		return candidatesToUpdate;
	}
}
