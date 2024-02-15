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

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class PostFinanceBPartnerConfigId implements RepoIdAware
{
	int repoId;

	public static PostFinanceBPartnerConfigId ofRepoId(final int repoId)
	{
		return new PostFinanceBPartnerConfigId(repoId);
	}

	@Nullable
	public static PostFinanceBPartnerConfigId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new PostFinanceBPartnerConfigId(repoId) : null;
	}

	private PostFinanceBPartnerConfigId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PostFinance_BPartner_Config");
	}

	public static int toRepoId(@Nullable final PostFinanceBPartnerConfigId id)
	{
		return id != null ? id.getRepoId() : -1;
	}
}
