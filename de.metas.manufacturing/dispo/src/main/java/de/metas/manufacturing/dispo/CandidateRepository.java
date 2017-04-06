package de.metas.manufacturing.dispo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.manufacturing.dispo.Candidate.Type;

/*
 * #%L
 * metasfresh-manufacturing-dispo
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
@Service
public class CandidateRepository
{
	private final List<Candidate> candidates = new ArrayList<>();

	public void add(final Candidate candidate)
	{
		final Optional<Candidate> retrieveExact = retrieveExact(candidate);
		if (retrieveExact.isPresent())
		{
			candidates.remove(retrieveExact.get());
		}
		candidates.add(candidate);
	}

	private Optional<Candidate> retrieveExact(final Candidate candidate)
	{
		return candidates.stream()
				.filter(c -> c.getLocator().getM_Locator_ID() == candidate.getLocator().getM_Locator_ID()
						&& c.getProduct().getM_Product_ID() == candidate.getProduct().getM_Product_ID()
						&& Objects.equals(c.getProjectedDate(), candidate.getProjectedDate())
						&& c.getType() == candidate.getType()
						&& Objects.equals(c.getSupplyType(), candidate.getSupplyType()))
				.findFirst();

	}

	/**
	 * Returns the "oldest" stock candidate that is not after the given query's date.
	 *
	 * @param query
	 * @return
	 */
	public Optional<Candidate> retrieveStockAt(final CandidatesSegment query)
	{
		final Optional<Candidate> firstCandiateAfterQueryDate = candidates.stream()
				.filter(c -> c.getType() == Type.STOCK
						&& c.getProduct().getM_Product_ID() == query.getProduct().getM_Product_ID()
						&& c.getLocator().getM_Locator_ID() == query.getLocator().getM_Locator_ID())

				.filter(c -> !c.getProjectedDate().after(query.getProjectedDate())) // no candidates that are after the date!
				.max(Comparator.comparing(Candidate::getProjectedDate));

		return firstCandiateAfterQueryDate;
	}

	public List<Candidate> retrieveStockFrom(final CandidatesSegment query)
	{
		final List<Candidate> candiatesAfterQueryDate = candidates.stream()
				.filter(c -> c.getType() == Type.STOCK
						&& c.getProduct().getM_Product_ID() == query.getProduct().getM_Product_ID()
						&& c.getLocator().getM_Locator_ID() == query.getLocator().getM_Locator_ID())

				.sorted(Comparator.comparing(Candidate::getProjectedDate))
				.filter(c -> !c.getProjectedDate().before(query.getProjectedDate())) // no candidates that are before the date!
				.sorted(Comparator.comparing(Candidate::getProjectedDate))
				.collect(Collectors.toList());

		return candiatesAfterQueryDate;
	}

	/**
	 * simple method to clear the repo between tests.
	 */
	@VisibleForTesting
	/* package */ void reset()
	{
		candidates.clear();
	}
}
