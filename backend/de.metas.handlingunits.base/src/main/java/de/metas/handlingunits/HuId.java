package de.metas.handlingunits;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.handlingunits.base
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
public class HuId implements RepoIdAware
{
	@JsonCreator
	public static HuId ofRepoId(final int repoId)
	{
		return new HuId(repoId);
	}

	@Nullable
	public static HuId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(@Nullable final HuId huId)
	{
		return huId != null ? huId.getRepoId() : -1;
	}

	public static Set<HuId> ofRepoIds(@NonNull final Collection<Integer> repoIds)
	{
		return repoIds.stream().map(HuId::ofRepoId).collect(ImmutableSet.toImmutableSet());
	}

	public static Set<Integer> toRepoIds(@NonNull final Collection<HuId> huIds)
	{
		return huIds.stream().map(HuId::getRepoId).collect(ImmutableSet.toImmutableSet());
	}

	public static Set<HuId> fromRepoIds(@Nullable final Collection<Integer> huRepoIds)
	{
		if (huRepoIds == null || huRepoIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return huRepoIds.stream().map(HuId::ofRepoIdOrNull).filter(Objects::nonNull).collect(ImmutableSet.toImmutableSet());
	}

	int repoId;

	private HuId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_HU_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final HuId o1, @Nullable final HuId o2)
	{
		return Objects.equals(o1, o2);
	}
}
