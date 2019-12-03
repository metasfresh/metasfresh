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

package de.metas.banking.api;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;

import javax.annotation.Nullable;

public class BankAccountId implements RepoIdAware
{
	final int repoId;

	private BankAccountId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "BankAccountId");
	}

	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static BankAccountId ofRepoId(final int repoId)
	{
		return new BankAccountId(repoId);
	}

	public static BankAccountId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new BankAccountId(repoId) : null;
	}

}
