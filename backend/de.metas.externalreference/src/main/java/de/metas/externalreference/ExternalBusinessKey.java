/*
 * #%L
 * de.metas.externalreference
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

package de.metas.externalreference;

import de.metas.util.Check;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class ExternalBusinessKey
{
	@NonNull
	Type type;

	@NonNull
	String rawValue;

	@Nullable
	ExternalReferenceValueAndSystem externalReferenceValueAndSystem;

	private ExternalBusinessKey(
			@NonNull final Type type,
			@NonNull final String rawValue,
			@Nullable final ExternalReferenceValueAndSystem externalReferenceValueAndSystem)
	{
		if (Type.EXTERNAL_REFERENCE.equals(type) && externalReferenceValueAndSystem == null)
		{
			throw new AdempiereException("ExternalReferenceValueAndSystem cannot be null for type: EXTERNAL_REFERENCE!")
					.appendParametersToMessage()
					.setParameter("rawValue", rawValue)
					.setParameter("type", type);
		}

		this.type = type;
		this.rawValue = rawValue;
		this.externalReferenceValueAndSystem = externalReferenceValueAndSystem;
	}

	@NonNull
	public static ExternalBusinessKey of(@NonNull final String identifier)
	{

		final Matcher externalBusinessKeyMatcher = Type.EXTERNAL_REFERENCE.pattern.matcher(identifier);

		if (externalBusinessKeyMatcher.matches())
		{
			final ExternalReferenceValueAndSystem valueAndSystem = ExternalReferenceValueAndSystem.builder()
					.externalSystem(externalBusinessKeyMatcher.group(1))
					.value(externalBusinessKeyMatcher.group(2))
					.build();

			return new ExternalBusinessKey(Type.EXTERNAL_REFERENCE, identifier, valueAndSystem);
		}
		else
		{
			return new ExternalBusinessKey(Type.VALUE, identifier, null);
		}
	}

	@NonNull
	public ExternalReferenceValueAndSystem asExternalValueAndSystem()
	{
		Check.assume(Type.EXTERNAL_REFERENCE.equals(type),
					 "The type of this instance needs to be {}; this={}", Type.EXTERNAL_REFERENCE, this);
		Check.assumeNotNull(externalReferenceValueAndSystem,
							"externalReferenceValueAndSystem cannot be null to EXTERNAL_REFERENCE type!");

		return externalReferenceValueAndSystem;
	}

	@NonNull
	public String asValue()
	{
		Check.assume(Type.VALUE.equals(type),
					 "The type of this instance needs to be {}; this={}", Type.VALUE, this);

		return rawValue;
	}

	@AllArgsConstructor
	@Getter
	public enum Type
	{
		VALUE(Pattern.compile("(.*?)")),
		EXTERNAL_REFERENCE(Pattern.compile("(?:^ext-)([a-zA-Z0-9]+)-(.+)"));

		private final Pattern pattern;
	}
}
