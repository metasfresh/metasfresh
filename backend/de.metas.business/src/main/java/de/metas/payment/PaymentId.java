package de.metas.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

/*
 * #%L
 * de.metas.business
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
public class PaymentId implements RepoIdAware
{
	@JsonCreator
	public static PaymentId ofRepoId(final int repoId)
	{
		return new PaymentId(repoId);
	}

	@Nullable
	public static PaymentId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PaymentId(repoId) : null;
	}

	public static int toRepoId(final PaymentId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static ImmutableSet<PaymentId> fromIntSet(@NonNull final Collection<Integer> repoIds)
	{
		if (repoIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return repoIds.stream().map(PaymentId::ofRepoIdOrNull).filter(Objects::nonNull).collect(ImmutableSet.toImmutableSet());
	}

	public static ImmutableSet<Integer> toIntSet(@NonNull final Collection<PaymentId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableSet.of();
		}

		return ids.stream().map(PaymentId::getRepoId).collect(ImmutableSet.toImmutableSet());
	}

	int repoId;

	private PaymentId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Payment_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable PaymentId id1, @Nullable PaymentId id2) {return Objects.equals(id1, id2);}
}
