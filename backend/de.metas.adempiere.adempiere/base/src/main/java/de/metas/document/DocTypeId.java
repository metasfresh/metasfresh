package de.metas.document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class DocTypeId implements RepoIdAware
{
	@JsonCreator
	public static DocTypeId ofRepoId(final int repoId)
	{
		return new DocTypeId(repoId);
	}

	@Nullable
	public static DocTypeId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<DocTypeId> optionalOfRepoId(@Nullable final Integer repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private DocTypeId(final int docTypeRepoId)
	{
		this.repoId = Check.assumeGreaterThanZero(docTypeRepoId, "docTypeRepoId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final DocTypeId docTypeId)
	{
		return docTypeId != null ? docTypeId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final DocTypeId id1, @Nullable final DocTypeId id2)
	{
		return Objects.equals(id1, id2);
	}
}
