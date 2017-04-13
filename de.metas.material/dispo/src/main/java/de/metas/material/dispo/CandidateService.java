package de.metas.material.dispo;

import java.util.stream.Stream;

import com.google.common.annotations.VisibleForTesting;

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

public class CandidateService
{
	/**
	 * Invalidate the candidates that directly match the given segment. Their invalidation will create events that can lead to the invalidation of further candidates.
	 *
	 * @param segment
	 */
	public void invalidateMatches(final CandidatesSegment segment)
	{
		final Stream<Candidate> directMatches = retrieveDirectMatches(segment);
		directMatches
				.forEach(candidate -> {

					// fire an invalidation event for 'candidate'

					retrieveChildren(candidate)
							.forEach(childCandidate -> {
								// fire an invalidation event for childCandidate
							});;
				});
	}

	private Stream<Candidate> retrieveChildren(final Candidate candidate)
	{
		// TODO Auto-generated method stub
		return null;

	}

	@VisibleForTesting
	/* package */ Stream<Candidate> retrieveDirectMatches(final CandidatesSegment segment)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
