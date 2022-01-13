/*
 * #%L
 * de.metas.business
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

package de.metas.invoice;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class InvoiceVerificationRunId implements RepoIdAware
{
	int repoId;

	@NonNull
	InvoiceVerificationSetId invoiceVerificationSetId;

	@Nullable
	public static InvoiceVerificationRunId cast(@Nullable final RepoIdAware repoIdAware)
	{
		return (InvoiceVerificationRunId)repoIdAware;
	}

	public static InvoiceVerificationRunId ofRepoId(@NonNull final InvoiceVerificationSetId invoiceVerificationSetId, final int invoiceVerificationRunId)
	{
		return new InvoiceVerificationRunId(invoiceVerificationSetId, invoiceVerificationRunId);
	}

	public static InvoiceVerificationRunId ofRepoId(final int invoiceVerificationSetId, final int invoiceVerificationRunId)
	{
		return new InvoiceVerificationRunId(InvoiceVerificationSetId.ofRepoId(invoiceVerificationSetId), invoiceVerificationRunId);
	}

	@Nullable
	public static InvoiceVerificationRunId ofRepoIdOrNull(
			@Nullable final Integer invoiceVerificationSetId,
			@Nullable final Integer invoiceVerificationRunId)
	{
		return invoiceVerificationSetId != null && invoiceVerificationSetId > 0 && invoiceVerificationRunId != null && invoiceVerificationRunId > 0
				? ofRepoId(invoiceVerificationSetId, invoiceVerificationRunId)
				: null;
	}

	@Nullable
	public static InvoiceVerificationRunId ofRepoIdOrNull(
			@Nullable final InvoiceVerificationSetId bpartnerId,
			final int bpartnerLocationId)
	{
		return bpartnerId != null && bpartnerLocationId > 0 ? ofRepoId(bpartnerId, bpartnerLocationId) : null;
	}

	private InvoiceVerificationRunId(@NonNull final InvoiceVerificationSetId invoiceVerificationSetId, final int bpartnerLocationId)
	{
		this.repoId = Check.assumeGreaterThanZero(bpartnerLocationId, "bpartnerLocationId");
		this.invoiceVerificationSetId = invoiceVerificationSetId;
	}

	public static int toRepoId(final InvoiceVerificationRunId invoiceVerificationRunId)
	{
		return toRepoIdOr(invoiceVerificationRunId, -1);
	}

	public static int toRepoIdOr(final InvoiceVerificationRunId bpLocationId, final int defaultValue)
	{
		return bpLocationId != null ? bpLocationId.getRepoId() : defaultValue;
	}

	public static boolean equals(final InvoiceVerificationRunId id1, final InvoiceVerificationRunId id2)
	{
		return Objects.equals(id1, id2);
	}
}

