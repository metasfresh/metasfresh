/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.incoterms;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class IncotermsId implements RepoIdAware
{
	int repoId;

	public static IncotermsId ofRepoId(final int repoId)
	{
		return new IncotermsId(repoId);
	}

	@Nullable
	public static IncotermsId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new IncotermsId(repoId) : null;
	}

	@Nullable
	public static IncotermsId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new IncotermsId(repoId) : null;
	}

	public static Optional<IncotermsId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	@JsonCreator
	public static IncotermsId ofObject(@NonNull final Object object)
	{
		return RepoIdAwares.ofObject(object, IncotermsId.class, IncotermsId::ofRepoId);
	}

	public static int toRepoId(@Nullable final IncotermsId incotermsId)
	{
		return toRepoIdOr(incotermsId, -1);
	}

	public static int toRepoIdOr(@Nullable final IncotermsId incotermsId, final int defaultValue)
	{
		return incotermsId != null ? incotermsId.getRepoId() : defaultValue;
	}

	private IncotermsId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Incoterms_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(@Nullable final IncotermsId o1, @Nullable final IncotermsId o2)
	{
		return Objects.equals(o1, o2);
	}
}
