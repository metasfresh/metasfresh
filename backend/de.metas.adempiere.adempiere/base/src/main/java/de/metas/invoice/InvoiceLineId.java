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

<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

@Value
public class InvoiceLineId implements RepoIdAware
{
<<<<<<< HEAD
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
=======
	@JsonCreator
	public static InvoiceLineId ofRepoId(final int repoId)
	{
		return new InvoiceLineId(repoId);
	}

	public static InvoiceLineId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new InvoiceLineId(repoId) : null;
	}

	@NonNull
	public static Optional<InvoiceLineId> ofRepoIdOptional(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private InvoiceLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_InvoiceLine_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final InvoiceLineId invoiceLineId)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return toRepoIdOr(invoiceLineId, -1);
	}

<<<<<<< HEAD
	public static int toRepoIdOr(final InvoiceLineId bpLocationId, final int defaultValue)
	{
		return bpLocationId != null ? bpLocationId.getRepoId() : defaultValue;
	}

	public static boolean equals(final InvoiceLineId id1, final InvoiceLineId id2)
	{
		return Objects.equals(id1, id2);
	}
=======
	public static int toRepoIdOr(@Nullable final InvoiceLineId invoiceLineId, final int defaultValue)
	{
		return invoiceLineId != null ? invoiceLineId.getRepoId() : defaultValue;
	}

	public static boolean equals(@Nullable final InvoiceLineId id1, @Nullable final InvoiceLineId id2) {return Objects.equals(id1, id2);}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
