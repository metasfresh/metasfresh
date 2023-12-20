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

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class InvoiceLineId implements RepoIdAware
{
	int repoId;

	@NonNull
	InvoiceId invoiceId;

	public static InvoiceLineId cast(@Nullable final RepoIdAware repoIdAware)
	{
		return (InvoiceLineId)repoIdAware;
	}

	public static InvoiceLineId ofRepoId(@NonNull final InvoiceId invoiceId, final int invoiceLineId)
	{
		return new InvoiceLineId(invoiceId, invoiceLineId);
	}

	public static InvoiceLineId ofRepoId(final int invoiceId, final int invoiceLineId)
	{
		return new InvoiceLineId(InvoiceId.ofRepoId(invoiceId), invoiceLineId);
	}

	public static InvoiceLineId ofRepoIdOrNull(
			@Nullable final Integer invoiceId,
			@Nullable final Integer invoiceLineId)
	{
		return invoiceId != null && invoiceId > 0 && invoiceLineId != null && invoiceLineId > 0
				? ofRepoId(invoiceId, invoiceLineId)
				: null;
	}

	public static InvoiceLineId ofRepoIdOrNull(
			@Nullable final InvoiceId bpartnerId,
			final int bpartnerLocationId)
	{
		return bpartnerId != null && bpartnerLocationId > 0 ? ofRepoId(bpartnerId, bpartnerLocationId) : null;
	}

	private InvoiceLineId(@NonNull final InvoiceId invoiceId, final int bpartnerLocationId)
	{
		this.repoId = Check.assumeGreaterThanZero(bpartnerLocationId, "bpartnerLocationId");
		this.invoiceId = invoiceId;
	}

	public static int toRepoId(final InvoiceLineId invoiceLineId)
	{
		return toRepoIdOr(invoiceLineId, -1);
	}

	public static int toRepoIdOr(final InvoiceLineId bpLocationId, final int defaultValue)
	{
		return bpLocationId != null ? bpLocationId.getRepoId() : defaultValue;
	}

	public static boolean equals(final InvoiceLineId id1, final InvoiceLineId id2)
	{
		return Objects.equals(id1, id2);
	}

	public void assertInvoiceId(@NonNull final InvoiceId expectedInvoiceId)
	{
		if (!InvoiceId.equals(this.invoiceId, expectedInvoiceId))
		{
			throw new AdempiereException("InvoiceId does not match for " + this + ". Expected invoiceId was " + expectedInvoiceId);
		}
	}
}
