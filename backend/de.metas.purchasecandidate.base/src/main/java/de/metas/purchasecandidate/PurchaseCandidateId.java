package de.metas.purchasecandidate;

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
 * de.metas.purchasecandidate.base
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
public class PurchaseCandidateId implements RepoIdAware
{
	@JsonCreator
	public static PurchaseCandidateId ofRepoId(final int repoId)
	{
		return new PurchaseCandidateId(repoId);
	}

	public static PurchaseCandidateId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PurchaseCandidateId(repoId) : null;
	}

	public static Set<PurchaseCandidateId> ofRepoIds(final Set<Integer> repoIds)
	{
		if (repoIds == null || repoIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return repoIds.stream().map(PurchaseCandidateId::ofRepoId).collect(ImmutableSet.toImmutableSet());
	}

	public static int getRepoIdOr(final PurchaseCandidateId id, final int defaultValue)
	{
		return id != null ? id.getRepoId() : defaultValue;
	}

	public static Set<Integer> toIntSet(final Collection<PurchaseCandidateId> purchaseCandidateIds)
	{
		if (purchaseCandidateIds == null || purchaseCandidateIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		else
		{
			return purchaseCandidateIds.stream().map(PurchaseCandidateId::getRepoId).collect(ImmutableSet.toImmutableSet());
		}
	}

	int repoId;

	private PurchaseCandidateId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
