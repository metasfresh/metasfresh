/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.material.dispo.commons.candidate;

import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

@Repository
public class MaterialDispoRecordRepository
{
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	public MaterialDispoRecordRepository(@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	@NonNull
	public MaterialDispoDataItem getBy(@NonNull final CandidatesQuery query)
	{
		if (query.getType().equals(CandidateType.STOCK))
		{
			throw new AdempiereException("The given candidatesQuery has an unsupported type=" + CandidateType.STOCK).appendParametersToMessage()
					.setParameter("candidatesQuery", query);
		}

		final Candidate candidate = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(query);
		if (candidate == null)
		{
			throw new AdempiereException("The given candidatesQuery does not match any candidate").appendParametersToMessage()
					.setParameter("candidatesQuery", query);
		}
		final Candidate stockCandidate;
		switch (candidate.getType())
		{
			case DEMAND:
			case INVENTORY_DOWN:
				stockCandidate = candidateRepositoryRetrieval
						.retrieveSingleChild(candidate.getId())
						.orElseThrow(() -> new AdempiereException("").appendParametersToMessage()
								.setParameter("candidatesQuery", query)
								.setParameter("candidate", candidate)
						);
				return MaterialDispoDataItem.of(candidate.withNegatedQuantity(), stockCandidate);
			case SUPPLY:
			case INVENTORY_UP:
				stockCandidate = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(CandidatesQuery.fromId(candidate.getParentId()));
				return MaterialDispoDataItem.of(candidate, stockCandidate);
			default:
				throw new AdempiereException("The CandidateType=" + candidate.getType() + " is not yet supported! Please add").appendParametersToMessage()
						.setParameter("candidatesQuery", query)
						.setParameter("candidate", candidate);
		}
	}
}
