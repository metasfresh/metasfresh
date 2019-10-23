package de.metas.costing;

import java.util.Collection;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

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
public class ChargeId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ChargeId ofRepoId(final int repoId)
	{
		return new ChargeId(repoId);
	}

	public static ChargeId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ChargeId(repoId) : null;
	}

	public static Set<ChargeId> ofRepoIds(final Collection<Integer> repoIds)
	{
		return repoIds.stream()
				.filter(repoId -> repoId != null && repoId > 0)
				.map(ChargeId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static int toRepoId(final ChargeId productId)
	{
		return productId != null ? productId.getRepoId() : -1;
	}

	private ChargeId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "productId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

}
