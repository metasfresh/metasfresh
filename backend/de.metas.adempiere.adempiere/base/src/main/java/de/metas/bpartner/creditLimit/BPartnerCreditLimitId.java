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

package de.metas.bpartner.creditLimit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * C_BPartner_CreditLimit_ID
 */
@Value
public class BPartnerCreditLimitId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static BPartnerCreditLimitId ofRepoId(final int bpCreditLimitId)
	{
		return new BPartnerCreditLimitId(bpCreditLimitId);
	}

	public static int toRepoId(@Nullable final BPartnerCreditLimitId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@Nullable
	public static BPartnerCreditLimitId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	private BPartnerCreditLimitId(final int bpCreditLimitId)
	{
		this.repoId = Check.assumeGreaterThanZero(bpCreditLimitId, "C_BPartner_CreditLimit_ID");
	}
}
