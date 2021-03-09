package de.metas.order;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

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
public class OrderLineId implements RepoIdAware
{
	@JsonCreator
	public static OrderLineId ofRepoId(final int repoId)
	{
		return new OrderLineId(repoId);
	}

	@Nullable
	public static OrderLineId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new OrderLineId(repoId) : null;
	}

	public static Optional<OrderLineId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final OrderLineId orderLineId)
	{
		return orderLineId != null ? orderLineId.getRepoId() : -1;
	}

	public static Set<Integer> toIntSet(final Collection<OrderLineId> orderLineIds)
	{
		return orderLineIds.stream().map(OrderLineId::getRepoId).collect(ImmutableSet.toImmutableSet());
	}

	int repoId;

	private OrderLineId(final int repoId)
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
