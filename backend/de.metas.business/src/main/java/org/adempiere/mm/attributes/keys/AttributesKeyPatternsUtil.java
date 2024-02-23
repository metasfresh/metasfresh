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

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.material.event.commons.AttributesKey;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Set;

@UtilityClass
public class AttributesKeyPatternsUtil
{
	public static Set<AttributesKeyPattern> parseCommaSeparatedString(final String string)
	{
		return Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(string)
				.stream()
				.map(attributesKeyStr -> parseSinglePartPattern(attributesKeyStr))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static AttributesKeyPattern parseSinglePartPattern(final String string)
	{
		if ("<ALL_STORAGE_ATTRIBUTES_KEYS>".equals(string))
		{
			return AttributesKeyPattern.ALL;
		}
		else if ("<OTHER_STORAGE_ATTRIBUTES_KEYS>".equals(string))
		{
			return AttributesKeyPattern.OTHER;
		}
		else
		{
			final AttributesKeyPartPattern partPattern = AttributesKeyPartPattern.parseString(string);
			return AttributesKeyPattern.ofPart(partPattern);
		}
	}

	public static AttributesKeyPattern ofAttributeKey(@NonNull final AttributesKey attributesKey)
	{
		if (attributesKey.isAll())
		{
			return AttributesKeyPattern.ALL;
		}
		else if (attributesKey.isOther())
		{
			return AttributesKeyPattern.OTHER;
		}
		else if (attributesKey.isNone())
		{
			return AttributesKeyPattern.NONE;
		}
		else
		{
			final ImmutableList<AttributesKeyPartPattern> partPatterns = attributesKey.getParts()
					.stream()
					.map(AttributesKeyPartPattern::ofAttributesKeyPart)
					.collect(ImmutableList.toImmutableList());

			return AttributesKeyPattern.ofParts(partPatterns);
		}
	}

	public static List<AttributesKeyMatcher> extractAttributesKeyMatchers(@NonNull final List<AttributesKeyPattern> patterns)
	{
		if (patterns.isEmpty())
		{
			return ImmutableList.of(matchingAll());
		}
		else if (!patterns.contains(AttributesKeyPattern.OTHER))
		{
			return patterns.stream()
					.map(AttributesKeyPatternsUtil::matching)
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			final ImmutableSet<AttributesKeyMatcher> otherMatchers = patterns.stream()
					.filter(AttributesKeyPattern::isSpecific)
					.map(AttributesKeyPatternsUtil::matching)
					.collect(ImmutableSet.toImmutableSet());

			final AttributesKeyMatcher othersMatcher = ExcludeAttributesKeyMatcher.of(otherMatchers);

			return patterns.stream()
					.map(pattern -> pattern.isOther() ? othersMatcher : matching(pattern))
					.collect(ImmutableList.toImmutableList());
		}
	}

	public static AttributesKeyMatcher matchingAll()
	{
		return AcceptAllAttributesKeyMatcher.instance;
	}

	public static AttributesKeyMatcher matching(@NonNull final AttributesKeyPattern pattern)
	{
		if (pattern.isAll())
		{
			return AcceptAllAttributesKeyMatcher.instance;
		}

		return StandardAttributesKeyPatternMatcher.of(pattern);
	}

	public static AttributesKeyMatcher matching(@NonNull final AttributesKey attributesKey)
	{
		return matching(ofAttributeKey(attributesKey));
	}

}
