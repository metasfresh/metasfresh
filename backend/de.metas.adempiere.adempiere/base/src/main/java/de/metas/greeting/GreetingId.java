package de.metas.greeting;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

/*
 * #%L
 * de.metas.business
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class GreetingId implements RepoIdAware
{
	@JsonCreator
	public static GreetingId ofRepoId(final int repoId)
	{
		return new GreetingId(repoId);
	}

	public static GreetingId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new GreetingId(repoId) : null;
	}

	@NonNull
	public static Optional<GreetingId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final GreetingId greetingId)
	{
		return toRepoIdOr(greetingId, -1);
	}

	public static int toRepoIdOr(
			@Nullable final GreetingId greetingId,
			final int defaultValue)
	{
		return greetingId != null ? greetingId.getRepoId() : defaultValue;
	}

	int repoId;

	private GreetingId(final int greetingRepoId)
	{
		this.repoId = Check.assumeGreaterThanZero(greetingRepoId, "greetingRepoId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
