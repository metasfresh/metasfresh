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

package de.metas.cucumber.stepdefs.material.dispo;

import com.google.common.collect.ImmutableList;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MaterialDispoRecordRepository
{
	@NonNull private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	@NonNull
	public MaterialDispoDataItem getBy(@NonNull final CandidatesQuery query)
	{
		assertNotStockQuery(query);

		return candidateRepositoryRetrieval.retrieveLatestMatch(query)
				.map(this::asMaterialDispoDataItem)
				.orElseThrow(() -> new AdempiereException("The given candidatesQuery does not match any candidate")
						.appendParametersToMessage()
						.setParameter("candidatesQuery", query));
	}

	@NonNull
	public ImmutableList<MaterialDispoDataItem> getAllBy(@NonNull final CandidatesQuery query)
	{
		final List<Candidate> candidates = getAllByQuery(query);
		return asMaterialDispoDataItems(candidates);
	}

	public List<Candidate> getAllByQuery(final @NonNull CandidatesQuery query)
	{
		assertNotStockQuery(query);
		return candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query);
	}

	public List<Candidate> getAllByProduct(final @NonNull ProductId productId)
	{
		return candidateRepositoryRetrieval.retrieveAllNotStockOrderedByDateAndSeqNoFor(productId);
	}

	private ImmutableList<MaterialDispoDataItem> asMaterialDispoDataItems(final @NonNull List<Candidate> candidates)
	{
		return candidates.stream()
				.map(this::asMaterialDispoDataItem)
				.collect(ImmutableList.toImmutableList());
	}

	private MaterialDispoDataItem asMaterialDispoDataItem(final @NonNull Candidate candidate)
	{
		final Candidate stockCandidate = getStockCandidate(candidate)
				.orElseThrow(() -> new AdempiereException("No stock candidate found for " + candidate));

		return MaterialDispoDataItem.of(candidate, stockCandidate);
	}

	public Optional<Candidate> getStockCandidate(final @NonNull Candidate candidate)
	{
		switch (candidate.getType())
		{
			case DEMAND:
			case INVENTORY_DOWN:
			case UNEXPECTED_DECREASE:
			{
				return candidateRepositoryRetrieval.retrieveSingleChild(candidate.getId());
			}
			case SUPPLY:
			case INVENTORY_UP:
			case UNEXPECTED_INCREASE:
			{
				return candidateRepositoryRetrieval.retrieveLatestMatch(CandidatesQuery.fromId(candidate.getParentId()));
			}
			default:
			{
				return Optional.empty();
			}
		}
	}

	private void assertNotStockQuery(final @NonNull CandidatesQuery query)
	{
		if (query.getType().equals(CandidateType.STOCK))
		{
			throw new AdempiereException("The given candidatesQuery has an unsupported type=" + CandidateType.STOCK).appendParametersToMessage()
					.setParameter("candidatesQuery", query);
		}
	}
}
