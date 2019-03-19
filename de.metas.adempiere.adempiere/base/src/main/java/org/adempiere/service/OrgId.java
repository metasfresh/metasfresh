package org.adempiere.service;

import java.util.Optional;

import org.compiere.util.Env;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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

@Value
public class OrgId implements RepoIdAware
{
	@JsonCreator
	public static OrgId ofRepoId(final int repoId)
	{
		if (repoId == ANY.repoId)
		{
			return ANY;
		}
		return new OrgId(repoId);
	}

	/** @return {@link #ANY} if the given {@code repoId} is zero; {@code null} if it is less than zero. */
	public static OrgId ofRepoIdOrNull(final int repoId)
	{
		if (repoId == ANY.repoId)
		{
			return ANY;
		}
		else if (repoId < 0)
		{
			return null;
		}
		else
		{
			return ofRepoId(repoId);
		}
	}

	/** @return {@link #ANY} even if the given {@code repoId} is less than zero. */
	public static OrgId ofRepoIdOrAny(final int repoId)
	{
		if (repoId == ANY.repoId)
		{
			return ANY;
		}
		else if (repoId < 0)
		{
			return ANY;
		}
		else
		{
			return ofRepoId(repoId);
		}
	}

	public static Optional<OrgId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(final OrgId orgId)
	{
		return orgId != null ? orgId.getRepoId() : -1;
	}

	public static int toRepoIdOrAny(final OrgId orgId)
	{
		return orgId != null ? orgId.getRepoId() : ANY.repoId;
	}

	public static final OrgId ANY = new OrgId();

	int repoId;

	private OrgId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "orgId");
	}

	private OrgId()
	{
		this.repoId = Env.CTXVALUE_AD_Org_ID_Any;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public boolean isAny()
	{
		return repoId == Env.CTXVALUE_AD_Org_ID_Any;
	}

	public boolean isRegular()
	{
		return !isAny();
	}
}
