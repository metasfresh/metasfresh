/*
 * #%L
 * de-metas-camel-leichundmehl
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.util;

import de.metas.common.util.Check;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.apache.camel.RuntimeCamelException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class Replacement
{
	@NonNull
	Type type;

	@NonNull
	String value;

	@NonNull
	@Getter(AccessLevel.NONE)
	String replacementValue;

	private Replacement(
			@NonNull final Type type,
			@NonNull final String value,
			@NonNull final String replacementValue)
	{
		this.type = type;
		this.value = value;
		this.replacementValue = replacementValue;
	}

	@NonNull
	public static Replacement of(@NonNull final String value)
	{
		final Matcher jsonPathMatcher = Type.JSON_PATH.pattern.matcher(value);
		if (jsonPathMatcher.matches())
		{
			final String jsonPathReplacementValue = jsonPathMatcher.group(1);
			return new Replacement(Type.JSON_PATH, value, jsonPathReplacementValue);
		}

		throw new RuntimeCamelException("Unknown Replacement type!");
	}

	@NonNull
	public String getJsonPath()
	{
		Check.assume(this.type.equals(Type.JSON_PATH), "Type should be JSON_PATH at this stage, but instead is {}", this.type);

		return replacementValue;
	}

	@AllArgsConstructor
	@Getter
	public enum Type
	{
		JSON_PATH(Pattern.compile("\\@JsonPath=(.*)\\@"));

		private final Pattern pattern;
	}
}
