/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.bpartner.postfinance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class PostFinanceOrgConfigId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static PostFinanceOrgConfigId ofRepoId(final int repoId)
	{
		return new PostFinanceOrgConfigId(repoId);
	}

	@Nullable
	public static PostFinanceOrgConfigId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new PostFinanceOrgConfigId(repoId) : null;
	}

	private PostFinanceOrgConfigId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PostFinance_Org_Config");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final PostFinanceOrgConfigId postFinanceOrgConfigId)
	{
		return toRepoIdOr(postFinanceOrgConfigId, -1);
	}

	public static int toRepoIdOr(@Nullable final PostFinanceOrgConfigId postFinanceOrgConfigId, final int defaultValue)
	{
		return postFinanceOrgConfigId != null ? postFinanceOrgConfigId.getRepoId() : defaultValue;
	}

	public static boolean equals(@Nullable final PostFinanceOrgConfigId id1, @Nullable final PostFinanceOrgConfigId id2)
	{
		return Objects.equals(id1, id2);
	}
}
