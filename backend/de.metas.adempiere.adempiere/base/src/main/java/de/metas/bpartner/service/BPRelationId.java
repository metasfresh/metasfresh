/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.bpartner.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class BPRelationId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static BPRelationId ofRepoId(final int repoId)
	{
		return new BPRelationId(repoId);
	}

	@Nullable
	public static BPRelationId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new BPRelationId(repoId) : null;
	}

	@Nullable
	public static BPRelationId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new BPRelationId(repoId) : null;
	}

	public static Optional<BPRelationId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final BPRelationId bpartnerId)
	{
		return toRepoIdOr(bpartnerId, -1);
	}

	public static int toRepoIdOr(@Nullable final BPRelationId bpartnerId, final int defaultValue)
	{
		return bpartnerId != null ? bpartnerId.getRepoId() : defaultValue;
	}

	private BPRelationId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_BP_Relation_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(final BPRelationId o1, final BPRelationId o2)
	{
		return Objects.equals(o1, o2);
	}
}
