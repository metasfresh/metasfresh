/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.util.time;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.Optional;

@Value
@Builder
public class InstantInterval
{
	@NonNull
	Instant from;

	@NonNull
	Instant to;

	public static InstantInterval of(@NonNull final Instant from, @NonNull final Instant to)
	{
		if (from.isAfter(to))
		{
			throw new RuntimeException("'from' cannot be after 'to'!");
		}

		return InstantInterval.builder()
				.from(from)
				.to(to)
				.build();
	}

	@NonNull
	public Optional<InstantInterval> getIntersectionWith(@NonNull final InstantInterval intervalToIntersect)
	{
		if (intervalToIntersect.getFrom().compareTo(from) >= 0 && intervalToIntersect.getTo().compareTo(to) <= 0)
		{
			return Optional.of(intervalToIntersect);
		}
		else if (intervalToIntersect.getFrom().compareTo(from) >= 0
				&& intervalToIntersect.getFrom().compareTo(to) <= 0
				&& intervalToIntersect.getTo().compareTo(to) >= 0)
		{
			return Optional.of(InstantInterval.of(intervalToIntersect.getFrom(), to));
		}
		else if (intervalToIntersect.getFrom().compareTo(from) <= 0
				&& intervalToIntersect.getTo().compareTo(to) <= 0
				&& intervalToIntersect.getTo().compareTo(from) >= 0)
		{
			return Optional.of(InstantInterval.of(from, intervalToIntersect.getTo()));
		}
		else if (intervalToIntersect.getFrom().compareTo(from) <= 0
				&& intervalToIntersect.getTo().compareTo(to) >= 0)
		{
			return Optional.of(this);
		}

		return Optional.empty();
	}
}
