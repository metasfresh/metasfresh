/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.acct;

import de.metas.acct.api.AccountId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.With;

import javax.annotation.Nullable;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public final class Account
{
	@Getter @NonNull private final AccountId accountId;
	@Getter @With @Nullable private final AccountConceptualName accountConceptualName;

	private Account(
			@NonNull final AccountId accountId,
			@Nullable final AccountConceptualName accountConceptualName)
	{
		this.accountId = accountId;
		this.accountConceptualName = accountConceptualName;
	}

	@NonNull
	public static Account of(@NonNull final AccountId accountId, @NonNull final AccountConceptualName accountConceptualName)
	{
		return new Account(accountId, accountConceptualName);
	}

	@NonNull
	public static Account of(@NonNull final AccountId accountId, @NonNull final String accountConceptualName)
	{
		return new Account(accountId, AccountConceptualName.ofString(accountConceptualName));
	}

	@NonNull
	public static Optional<Account> optionalOfRepoId(final int accountRepoId, @NonNull final String accountConceptualName)
	{
		final AccountId accountId = AccountId.ofRepoIdOrNull(accountRepoId);
		return accountId != null ? Optional.of(of(accountId, accountConceptualName)) : Optional.empty();
	}

	@NonNull
	public static Account ofId(@NonNull final AccountId accountId)
	{
		return new Account(accountId, null);
	}

}
