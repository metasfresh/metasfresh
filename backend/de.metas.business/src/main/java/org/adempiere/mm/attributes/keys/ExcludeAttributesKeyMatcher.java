/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package org.adempiere.mm.attributes.keys;

import java.util.Collection;
import java.util.Optional;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;

import de.metas.material.event.commons.AttributesKey;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@VisibleForTesting
public final class ExcludeAttributesKeyMatcher implements AttributesKeyMatcher
{
	public static AttributesKeyMatcher of(final Collection<AttributesKeyMatcher> matchersToExclude)
	{
		return !matchersToExclude.isEmpty()
				? new ExcludeAttributesKeyMatcher(matchersToExclude)
				: AcceptAllAttributesKeyMatcher.instance;
	}

	private final ImmutableSet<AttributesKeyMatcher> matchersToExclude;

	private ExcludeAttributesKeyMatcher(@NonNull final Collection<AttributesKeyMatcher> matchersToExclude)
	{
		this.matchersToExclude = ImmutableSet.copyOf(matchersToExclude);
	}

	@Override
	public boolean matches(@NonNull final AttributesKey attributesKey)
	{
		for (final AttributesKeyMatcher matcher : matchersToExclude)
		{
			if (matcher.matches(attributesKey))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public AttributesKey toAttributeKeys(final AttributesKey context)
	{
		return AttributesKey.OTHER;
	}

	@Override
	public Optional<AttributesKey> toAttributeKeys()
	{
		return Optional.of(AttributesKey.OTHER);
	}
}
