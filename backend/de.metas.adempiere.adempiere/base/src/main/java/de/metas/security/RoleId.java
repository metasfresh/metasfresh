package de.metas.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

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

@Value
public class RoleId implements RepoIdAware
{
	public static final RoleId SYSTEM = new RoleId(0);

	/**
	 * Used by the reports service when it accesses the REST-API
	 */
	public static final RoleId JSON_REPORTS = new RoleId(540078);

	@JsonCreator
	public static RoleId ofRepoId(final int repoId)
	{
		final RoleId roleId = ofRepoIdOrNull(repoId);
		return Check.assumeNotNull(roleId, "Unable to create a roleId for repoId={}", repoId);
	}

	public static RoleId ofRepoIdOrNull(final int repoId)
	{
		if (repoId == SYSTEM.getRepoId())
		{
			return SYSTEM;
		}
		else if (repoId == JSON_REPORTS.getRepoId())
		{
			return JSON_REPORTS;
		}
		else
		{
			return repoId > 0 ? new RoleId(repoId) : null;
		}
	}

	public static int toRepoId(@Nullable final RoleId id)
	{
		return toRepoId(id, -1);
	}

	public static int toRepoId(@Nullable final RoleId id, final int defaultValue)
	{
		return id != null ? id.getRepoId() : defaultValue;
	}

	public static boolean equals(final RoleId id1, final RoleId id2)
	{
		return Objects.equals(id1, id2);
	}

	int repoId;

	private RoleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterOrEqualToZero(repoId, "AD_Role_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public boolean isSystem() {return isSystem(repoId);}

	public static boolean isSystem(final int repoId) {return repoId == SYSTEM.repoId;}

	public boolean isRegular() {return isRegular(repoId);}

	public static boolean isRegular(final int repoId) {return !isSystem(repoId);}
}
