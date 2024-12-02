/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.invoice;

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
import java.util.Optional;

@Value
public class InvoiceId implements RepoIdAware
{
	@JsonCreator
	public static InvoiceId ofRepoId(final int repoId)
	{
		return new InvoiceId(repoId);
	}

	@Nullable
	public static InvoiceId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new InvoiceId(repoId) : null;
	}

	@NonNull
	public static Optional<InvoiceId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private InvoiceId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Invoice_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final InvoiceId invoiceId)
	{
		return toRepoIdOr(invoiceId, -1);
	}

	public static int toRepoIdOr(@Nullable final InvoiceId invoiceId, final int defaultValue)
	{
		return invoiceId != null ? invoiceId.getRepoId() : defaultValue;
	}

	public static ImmutableSet<InvoiceId> fromIntSet(@NonNull final Collection<Integer> repoIds)
	{
		if (repoIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return repoIds.stream().map(InvoiceId::ofRepoIdOrNull).filter(Objects::nonNull).collect(ImmutableSet.toImmutableSet());
	}

	public static ImmutableSet<Integer> toIntSet(@NonNull final Collection<InvoiceId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableSet.of();
		}

		return ids.stream().map(InvoiceId::getRepoId).collect(ImmutableSet.toImmutableSet());
	}

	public static boolean equals(@Nullable final InvoiceId id1, @Nullable final InvoiceId id2) {return Objects.equals(id1, id2);}
}
