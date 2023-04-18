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

package de.metas.banking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Value
public class BankStatementId implements RepoIdAware
{
	int repoId;

	@NonNull
	@JsonCreator
	public static BankStatementId ofRepoId(final int repoId)
	{
		return new BankStatementId(repoId);
	}

	@Nullable
	public static BankStatementId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Set<Integer> toIntSet(final Collection<BankStatementId> bankStatementIds)
	{
		if (bankStatementIds == null || bankStatementIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		else
		{
			return bankStatementIds
					.stream()
					.map(BankStatementId::getRepoId)
					.collect(ImmutableSet.toImmutableSet());
		}
	}

	private BankStatementId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_BankStatement_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable BankStatementId id1, @Nullable BankStatementId id2)
	{
		return Objects.equals(id1, id2);
	}
}
