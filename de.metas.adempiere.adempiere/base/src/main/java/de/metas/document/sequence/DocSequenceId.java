package de.metas.document.sequence;

import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class DocSequenceId implements RepoIdAware
{
	@JsonCreator
	public static DocSequenceId ofRepoId(final int repoId)
	{
		return new DocSequenceId(repoId);
	}

	public static DocSequenceId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new DocSequenceId(repoId) : null;
	}

	public static Optional<DocSequenceId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private DocSequenceId(final int repoId)
	{
		Check.assumeGreaterThanZero(repoId, "AD_Sequence_ID");
		this.repoId = repoId;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(final DocSequenceId id1, final DocSequenceId id2)
	{
		return Objects.equals(id1, id2);
	}
}
