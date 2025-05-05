/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class InvoiceLineAllocId implements RepoIdAware
{
	@JsonCreator
	@NonNull
	public static InvoiceLineAllocId ofRepoId(final int repoId)
	{
		return new InvoiceLineAllocId(repoId);
	}

	@Nullable
	public static InvoiceLineAllocId ofRepoIdOrNull(final int repoId)
	{
		if (repoId <= 0)
		{
			return null;
		}
		else
		{
			return ofRepoId(repoId);
		}
	}

	public static int toRepoId(final InvoiceLineAllocId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private InvoiceLineAllocId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_Invoice_Line_Alloc.COLUMNNAME_C_Invoice_Line_Alloc_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(final InvoiceLineAllocId id1, final InvoiceLineAllocId id2)
	{
		return Objects.equals(id1, id2);
	}
}
