package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_C_Invoice_Rejection_Detail;

/*
 * #%L
 * metasfresh-pharma
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
// im not sure where to place this to also have visibility
@Value
public class InvoiceRejectionDetailId implements RepoIdAware
{
	@JsonCreator
	public static InvoiceRejectionDetailId ofRepoId(final int repoId)
	{
		return new InvoiceRejectionDetailId(repoId);
	}

	public static InvoiceRejectionDetailId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new InvoiceRejectionDetailId(repoId) : null;
	}

	int repoId;

	private InvoiceRejectionDetailId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_Invoice_Rejection_Detail.COLUMNNAME_C_Invoice_Rejection_Detail_ID);
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(final InvoiceRejectionDetailId invoiceId)
	{
		return toRepoIdOr(invoiceId, -1);
	}

	public static int toRepoIdOr(final InvoiceRejectionDetailId invoiceId, final int defaultValue)
	{
		return invoiceId != null ? invoiceId.getRepoId() : defaultValue;
	}
}
