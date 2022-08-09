/*
 * #%L
 * de.metas.contracts
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

package de.metas.contacts.invoice.interim;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class InterimInvoiceOverviewId implements RepoIdAware
{
	@JsonCreator
	public static InterimInvoiceOverviewId ofRepoId(final int repoId)
	{
		return new InterimInvoiceOverviewId(repoId);
	}

	@Nullable
	public static InterimInvoiceOverviewId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new InterimInvoiceOverviewId(repoId) : null;
	}

	public static Optional<InterimInvoiceOverviewId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final InterimInvoiceOverviewId interimInvoiceOverviewId)
	{
		return interimInvoiceOverviewId != null ? interimInvoiceOverviewId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final InterimInvoiceOverviewId o1, @Nullable final InterimInvoiceOverviewId o2)
	{
		return Objects.equals(o1, o2);
	}

	int repoId;

	private InterimInvoiceOverviewId(final int repoId)
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
