/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.serviceprovider.everhour.task;

import de.metas.externalreference.IExternalSystem;
import de.metas.serviceprovider.external.ExternalSystem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum Source
{
	GITHUB(Pattern.compile("^gh:(?<ghIssueNo>[0-9]+)$"), ExternalSystem.GITHUB, "ghIssueNo"),
	UNKNOWN(Pattern.compile(".*"), null, "");

	private final Pattern pattern;
	private final IExternalSystem externalSystem;
	private final String groupName;

	public boolean matches(@NonNull final String timeBookingTaskId)
	{
		final Matcher matcher = pattern.matcher(timeBookingTaskId);

		return matcher.matches();
	}

	@NonNull
	public Optional<String> getId(@NonNull final String timeBookingTaskId)
	{
		if (this == UNKNOWN)
		{
			return Optional.of(timeBookingTaskId);
		}

		final Matcher matcher = pattern.matcher(timeBookingTaskId);

		final boolean matches = matcher.matches();

		if (!matches)
		{
			return Optional.empty();
		}

		return Optional.of(matcher.group(this.groupName));
	}

	public boolean isUnknown()
	{
		return this == UNKNOWN;
	}

	public boolean isGithub()
	{
		return this == Source.GITHUB;
	}

	@NonNull
	public static Optional<Source> getSourceForId(@NonNull final String timeBookingTaskId)
	{
		return Stream.of(values())
				.filter(source -> !UNKNOWN.equals(source))
				.filter(source -> source.matches(timeBookingTaskId))
				.findFirst();
	}
}