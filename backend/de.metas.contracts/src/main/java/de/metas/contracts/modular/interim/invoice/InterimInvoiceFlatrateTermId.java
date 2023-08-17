/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.interim.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class InterimInvoiceFlatrateTermId implements RepoIdAware
{
	@JsonCreator
	public static InterimInvoiceFlatrateTermId ofRepoId(final int repoId)
	{
		return new InterimInvoiceFlatrateTermId(repoId);
	}

	@Nullable
	public static InterimInvoiceFlatrateTermId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new InterimInvoiceFlatrateTermId(repoId) : null;
	}

	public static Optional<InterimInvoiceFlatrateTermId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final InterimInvoiceFlatrateTermId interimInvoiceFlatrateTermId)
	{
		return interimInvoiceFlatrateTermId != null ? interimInvoiceFlatrateTermId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final InterimInvoiceFlatrateTermId o1, @Nullable final InterimInvoiceFlatrateTermId o2)
	{
		return Objects.equals(o1, o2);
	}

	int repoId;

	private InterimInvoiceFlatrateTermId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_InterimInvoice_Overview_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
