/*
 * #%L
 * de.metas.manufacturing.rest-api
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

package de.metas.handlingunits.rest_api;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ExtractCounterAttributesCommand
{
	private static final Pattern PATTERN_NAME_ENDING_WITH_NUMBER = Pattern.compile("^([^\\d]*)(\\d+)$");
	private static final String COUNTER_ATTRIBUTE_SUFFIX = "_count";

	private final ImmutableAttributeSet attributes;

	public ExtractCounterAttributesCommand(@NonNull final ImmutableAttributeSet attributes)
	{
		this.attributes = attributes;
	}

	public List<CounterAttribute> execute()
	{
		return createCounterAttributes();
	}

	private List<CounterAttribute> createCounterAttributes()
	{
		final ImmutableListMultimap<String, ParsedAttributeCode> attributeCodesByPrefix = attributes
				.getAttributeCodes()
				.stream()
				.map(ExtractCounterAttributesCommand::parsedAttributeCode)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						ParsedAttributeCode::getPrefix,
						parsedAttributeCode -> parsedAttributeCode));

		final ArrayList<CounterAttribute> counterAttributes = new ArrayList<>();
		for (final String prefix : attributeCodesByPrefix.keySet())
		{
			final ImmutableList<ParsedAttributeCode> parsedAttributeCodes = attributeCodesByPrefix.get(prefix);
			final CounterAttribute counterAttribute = createCounterAttributeOrNull(prefix, parsedAttributeCodes);
			if (counterAttribute != null)
			{
				counterAttributes.add(counterAttribute);
			}
		}

		return counterAttributes;
	}

	@VisibleForTesting
	static ParsedAttributeCode parsedAttributeCode(@NonNull final AttributeCode attributeCode)
	{
		final Matcher matcher = PATTERN_NAME_ENDING_WITH_NUMBER.matcher(attributeCode.getCode());

		if (matcher.find())
		{
			if (matcher.groupCount() == 2)
			{
				final String prefix = matcher.group(1);
				final String suffix = matcher.group(2);
				if (!Check.isBlank(prefix) && !Check.isBlank(suffix))
				{
					return ParsedAttributeCode.builder()
							.attributeCode(attributeCode)
							.prefix(prefix)
							.suffix(suffix)
							.build();
				}
			}
		}

		// fallback
		return ParsedAttributeCode.builder()
				.attributeCode(attributeCode)
				.prefix(attributeCode.getCode())
				.build();
	}

	@Nullable
	private CounterAttribute createCounterAttributeOrNull(
			@NonNull final String prefix,
			@NonNull final ImmutableList<ParsedAttributeCode> parsedAttributeCodes)
	{
		// Avoid creating counter attributes for groups with less than 2 attributes
		if (parsedAttributeCodes.size() <= 1)
		{
			return null;
		}

		parsedAttributeCodes.forEach(parsedAttributeCode -> Check.assumeEquals(parsedAttributeCode.getPrefix(), prefix));

		int counter = 0;
		for (final ParsedAttributeCode parsedAttributeCode : parsedAttributeCodes)
		{
			if (!isEmptyAttributeValue(parsedAttributeCode.getAttributeCode()))
			{
				counter++;
			}
		}

		return CounterAttribute.builder()
				.attributeCode(prefix + COUNTER_ATTRIBUTE_SUFFIX)
				.counter(counter)
				.build();
	}

	private boolean isEmptyAttributeValue(@NonNull final AttributeCode attributeCode)
	{
		final Object value = attributes.getValue(attributeCode);
		return value == null
				|| "".equals(value);
	}

	@Value
	@Builder
	static class ParsedAttributeCode
	{
		@NonNull AttributeCode attributeCode;
		@NonNull String prefix;
		@Nullable String suffix;
	}

	@Value
	@Builder
	public static class CounterAttribute
	{
		@NonNull String attributeCode;
		int counter;
	}
}
