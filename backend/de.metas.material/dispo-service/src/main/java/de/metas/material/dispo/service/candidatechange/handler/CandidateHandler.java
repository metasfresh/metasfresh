package de.metas.material.dispo.service.candidatechange.handler;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Collection;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface CandidateHandler
{
	Collection<CandidateType> getHandeledTypes();

	Candidate onCandidateNewOrChange(@NonNull Candidate candidate, @NonNull OnNewOrChangeAdvise advise);

	void onCandidateDelete(Candidate candidate);

	@Value
	@Builder
	class OnNewOrChangeAdvise
	{
		public static final OnNewOrChangeAdvise DEFAULT = OnNewOrChangeAdvise.builder().build();

		public static OnNewOrChangeAdvise attemptUpdate(final boolean attemptUpdate)
		{
			return OnNewOrChangeAdvise.builder().attemptUpdate(attemptUpdate).build();
		}

		@Builder.Default
		boolean attemptUpdate = true;
	}
}
