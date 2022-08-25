package de.metas.organization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

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
	public static final OrgId ANY = new OrgId();
	public static final OrgId MAIN = new OrgId(1000000);

	int repoId;

	private OrgId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_Org_ID");
	}

	private OrgId()
	{
		this.repoId = Env.CTXVALUE_AD_Org_ID_Any;
	}

	@JsonCreator
	public static OrgId ofRepoId(final int repoId)
	{
		final OrgId orgId = ofRepoIdOrNull(repoId);
		if (orgId == null)
		{
			throw new AdempiereException("Invalid AD_Org_ID: " + repoId);
		}
		return orgId;
	}

	/**
	 * @return {@link #ANY} if the given {@code repoId} is zero; {@code null} if it is less than zero.
	 */
	@Nullable
	public static OrgId ofRepoIdOrNull(final int repoId)
	{
		if (repoId == ANY.repoId)
		{
			return ANY;
		}
		else if (repoId == MAIN.repoId)
		{
			return MAIN;
		}
		else if (repoId < 0)
		{
			return null;
		}
		else
		{
			return new OrgId(repoId);
		}
	}

	/**
	 * @return {@link #ANY} even if the given {@code repoId} is less than zero.
	 */
	public static OrgId ofRepoIdOrAny(final int repoId)
	{
		final OrgId orgId = ofRepoIdOrNull(repoId);
		return orgId != null ? orgId : ANY;
	}

	public static Optional<OrgId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final OrgId orgId)
	{
		return orgId != null ? orgId.getRepoId() : -1;
	}

	public static int toRepoIdOrAny(@Nullable final OrgId orgId)
	{
		return orgId != null ? orgId.getRepoId() : ANY.repoId;
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

	/**
	 * @return {@code true} if the org in question is not {@code *} (i.e. "ANY"), but a specific organisation's ID
	 */
	public boolean isRegular()
	{
		return !isAny();
	}

	@Nullable
	public OrgId asRegularOrNull() {return isRegular() ? this : null;}

	public static boolean equals(@Nullable final OrgId id1, @Nullable final OrgId id2)
	{
		return Objects.equals(id1, id2);
	}
}
