/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.banking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * Our own (organization) bank account
 */
@Value
public class BankAccountId implements RepoIdAware
{
	int repoId;

	private BankAccountId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_BP_BankAccount_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	@NonNull
	@JsonCreator
	public static BankAccountId ofRepoId(final int repoId)
	{
		return new BankAccountId(repoId);
	}

	@Nullable
	public static BankAccountId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new BankAccountId(repoId) : null;
	}

	public static Optional<BankAccountId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static boolean equals(@Nullable final BankAccountId id1, @Nullable final BankAccountId id2)
	{
		return Objects.equals(id1, id2);
	}

	public static int toRepoId(@Nullable final BankAccountId id)
	{
		return id != null ? id.getRepoId() : -1;
	}
}
