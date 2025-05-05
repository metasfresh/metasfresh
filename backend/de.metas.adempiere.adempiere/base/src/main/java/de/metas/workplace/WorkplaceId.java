/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workplace;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class WorkplaceId implements RepoIdAware
{
	int repoId;

	private WorkplaceId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Workplace_ID");
	}

	public static WorkplaceId ofRepoId(final int repoId) {return new WorkplaceId(repoId);}

	@Nullable
	public static WorkplaceId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new WorkplaceId(repoId) : null;}

	public static Optional<WorkplaceId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static int toRepoId(@Nullable final WorkplaceId workplaceId) {return workplaceId != null ? workplaceId.getRepoId() : -1;}

	@Nullable
	@JsonCreator
	public static WorkplaceId ofNullableObject(@Nullable final Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		return RepoIdAwares.ofObject(obj, WorkplaceId.class, WorkplaceId::ofRepoId);
	}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}

	public static boolean equals(@Nullable final WorkplaceId id1, @Nullable final WorkplaceId id2) {return Objects.equals(id1, id2);}
}
