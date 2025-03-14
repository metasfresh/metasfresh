/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.greeting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
@ToString
class GreetingsMap
{
	private final ImmutableMap<GreetingId, Greeting> greetingsById;
	private final ImmutableListMultimap<GreetingStandardType, Greeting> greetingsByStandardType;

	public GreetingsMap(@NonNull final List<Greeting> greetings)
	{
		greetingsById = Maps.uniqueIndex(greetings, Greeting::getId);
		greetingsByStandardType = greetings.stream()
				.filter(greeting -> greeting.getStandardType() != null)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						Greeting::getStandardType,
						greeting -> greeting));
	}

	public Greeting getById(@NonNull final GreetingId id)
	{
		final Greeting greeting = greetingsById.get(id);
		if (greeting == null)
		{
			throw new AdempiereException("No greeting found for " + id);
		}
		return greeting;
	}

	public Optional<Greeting> getComposite(
			@Nullable final GreetingId greetingId1,
			@Nullable final GreetingId greetingId2)
	{
		if (greetingId1 == null)
		{
			return greetingId2 != null ? Optional.of(getById(greetingId2)) : Optional.empty();
		}
		else if (greetingId2 == null)
		{
			return Optional.of(getById(greetingId1));
		}
		else
		{
			final Greeting greeting1 = getById(greetingId1);
			final GreetingStandardType greetingStandardType1 = greeting1.getStandardType();
			if (greetingStandardType1 == null)
			{
				return Optional.empty();
			}

			final Greeting greeting2 = getById(greetingId2);
			final GreetingStandardType greetingStandardType2 = greeting2.getStandardType();
			if (greetingStandardType2 == null)
			{
				return Optional.empty();
			}

			final GreetingStandardType composedStandardType = greetingStandardType1.composeWith(greetingStandardType2);

			final ImmutableList<Greeting> composedGreetings = greetingsByStandardType.get(composedStandardType);
			if (composedGreetings.isEmpty())
			{
				return Optional.empty();
			}
			else
			{
				return Optional.of(composedGreetings.get(0));
			}
		}
	}

	public Optional<Greeting> getByName(@NonNull final String name)
	{
		return greetingsById.values()
				.stream()
				.filter(greeting -> name.equals(greeting.getName()))
				.findFirst();
	}
}
