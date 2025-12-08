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

import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
final class StandardAttributesKeyPatternMatcher implements AttributesKeyMatcher
{
	public static StandardAttributesKeyPatternMatcher of(@NonNull final AttributesKeyPattern pattern)
	{
		return new StandardAttributesKeyPatternMatcher(pattern);
	}

	private final AttributesKeyPattern pattern;

	private StandardAttributesKeyPatternMatcher(@NonNull final AttributesKeyPattern pattern)
	{
		this.pattern = pattern;
	}

	@Override
	public boolean matches(final AttributesKey attributesKey)
	{
		return pattern.matches(attributesKey);
	}

	@Override
	public AttributesKey toAttributeKeys(@Nullable final AttributesKey context)
	{
		final ImmutableList<AttributesKeyPart> parts = pattern.getPartPatterns()
				.stream()
				.map(partPattern -> partPattern.toAttributeKeyPart(context))
				.collect(ImmutableList.toImmutableList());

		return AttributesKey.ofParts(parts);
	}

	@Override
	public Optional<AttributesKey> toAttributeKeys()
	{
		final boolean wildcardMatching = pattern.getPartPatterns()
				.stream()
				.anyMatch(AttributesKeyPartPattern::isWildcardMatching);

		return !wildcardMatching
				? Optional.of(toAttributeKeys(null))
				: Optional.empty();
	}
}
