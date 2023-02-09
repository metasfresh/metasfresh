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

package de.metas.material.dispo.commons.repository.query;

import de.metas.material.dispo.commons.candidate.CandidateId;
import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

@Builder
@Value
public class DeleteCandidatesQuery
{
	@Nullable
	Boolean isActive;

	@Nullable
	String status;

	@Nullable
	CandidateId candidateId;

	public DeleteCandidatesQuery(
			@Nullable final Boolean isActive,
			@Nullable final String status,
			@Nullable final CandidateId candidateId)
	{
		final boolean allNull = Stream.of(isActive, status, candidateId).noneMatch(Objects::nonNull);

		if (allNull)
		{
			throw new AdempiereException("At least one criteria must be set!");
		}

		this.isActive = isActive;
		this.status = status;
		this.candidateId = candidateId;
	}
}