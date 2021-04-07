package de.metas.bpartner;

import java.util.Objects;

import javax.annotation.Nullable;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/** C_BP_BankAccount_ID */
@Value
public class BPartnerBankAccountId implements RepoIdAware
{
	int repoId;

	@NonNull
	BPartnerId bpartnerId;

	public static BPartnerBankAccountId ofRepoId(@NonNull final BPartnerId bpartnerId, final int bpBankAccountId)
	{
		return new BPartnerBankAccountId(bpartnerId, bpBankAccountId);
	}

	public static BPartnerBankAccountId ofRepoId(final int bpartnerId, final int bpBankAccountId)
	{
		return new BPartnerBankAccountId(BPartnerId.ofRepoId(bpartnerId), bpBankAccountId);
	}

	public static BPartnerBankAccountId ofRepoIdOrNull(
			@Nullable final Integer bpartnerId,
			@Nullable final Integer bpBankAccountId)
	{
		return bpartnerId != null && bpartnerId > 0 && bpBankAccountId != null && bpBankAccountId > 0
				? ofRepoId(bpartnerId, bpBankAccountId)
				: null;
	}

	public static BPartnerBankAccountId ofRepoIdOrNull(
			@Nullable final BPartnerId bpartnerId,
			final int bpBankAccountId)
	{
		return bpartnerId != null && bpBankAccountId > 0 ? ofRepoId(bpartnerId, bpBankAccountId) : null;
	}

	private BPartnerBankAccountId(@NonNull final BPartnerId bpartnerId, final int bpBankAccountId)
	{
		this.repoId = Check.assumeGreaterThanZero(bpBankAccountId, "C_BP_BankAccount_ID");
		this.bpartnerId = bpartnerId;
	}

	public static int toRepoId(final BPartnerBankAccountId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(final BPartnerBankAccountId id1, final BPartnerBankAccountId id2)
	{
		return Objects.equals(id1, id2);
	}
	
	
}
