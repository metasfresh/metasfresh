package de.metas.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class UserId implements RepoIdAware
{
	public static final UserId SYSTEM = new UserId(0);
	public static final UserId METASFRESH = new UserId(100);

	/**
	 * Used by the reports service when it accesses the REST-API
	 */
	public static final UserId JSON_REPORTS = new UserId(540057);

	@JsonCreator
	public static UserId ofRepoId(final int repoId)
	{
		final UserId userId = ofRepoIdOrNull(repoId);
		return Check.assumeNotNull(userId, "Unable to create a userId for repoId={}", repoId);
	}

	@Nullable
	public static UserId ofRepoIdOrNull(final int repoId)
	{
		if (repoId == SYSTEM.getRepoId())
		{
			return SYSTEM;
		}
		else if (repoId == METASFRESH.getRepoId())
		{
			return METASFRESH;
		}
		else if (repoId == JSON_REPORTS.getRepoId())
		{
			return JSON_REPORTS;
		}
		else
		{
			return repoId >= 0 ? new UserId(repoId) : null;
		}
	}

	public static UserId ofRepoIdOrSystem(final int repoId)
	{
		final UserId id = ofRepoIdOrNull(repoId);
		return id != null ? id : UserId.SYSTEM;
	}

	@Nullable
	public static UserId ofRepoIdOrNullIfSystem(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<UserId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final UserId userId)
	{
		return toRepoIdOr(userId, -1);
	}

	public static int toRepoIdOr(
			@Nullable final UserId userId,
			final int defaultValue)
	{
		return userId != null ? userId.getRepoId() : defaultValue;
	}

	public static boolean equals(@Nullable final UserId userId1, @Nullable final UserId userId2)
	{
		return Objects.equals(userId1, userId2);
	}

	int repoId;

	private UserId(final int userRepoId)
	{
		this.repoId = Check.assumeGreaterOrEqualToZero(userRepoId, "userRepoId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public boolean isSystemUser()
	{
		return repoId == SYSTEM.repoId;
	}

	public boolean isRegularUser()
	{
		return !isSystemUser();
	}
}
