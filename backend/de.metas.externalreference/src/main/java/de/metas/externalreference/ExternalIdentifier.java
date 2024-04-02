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

import de.metas.bpartner.GLN;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.util.Check;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.metas.util.Check.isEmpty;

@Value
public class ExternalIdentifier
{
	@NonNull
	Type type;

	@NonNull
	String rawValue;

	@Nullable
	ExternalReferenceValueAndSystem externalReferenceValueAndSystem;

	private ExternalIdentifier(
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

	public static ExternalIdentifier ofOrNull(@Nullable final String rawIdentifierString)
	{
		if (isEmpty(rawIdentifierString, true))
		{
			return null;
		}
		return of(rawIdentifierString);
	}

	@NonNull
	public static Optional<ExternalIdentifier> ofOptional(@Nullable final String identifier)
	{
		if (Check.isBlank(identifier))
		{
			return Optional.empty();
		}

		try
		{
			return Optional.of(of(identifier));
		}
		catch (final Exception exception)
		{
			return Optional.empty();
		}
	}

	@NonNull
	public static Optional<ExternalIdentifier> ofIdentifierCandidate(@NonNull final String identifier)
	{
		if (Type.METASFRESH_ID.pattern.matcher(identifier).matches())
		{
			return Optional.of(new ExternalIdentifier(Type.METASFRESH_ID, identifier, null));
		}

		final Matcher externalReferenceMatcher = Type.EXTERNAL_REFERENCE.pattern.matcher(identifier);

		if (externalReferenceMatcher.matches())
		{

			final ExternalReferenceValueAndSystem valueAndSystem = ExternalReferenceValueAndSystem.builder()
					.externalSystem(externalReferenceMatcher.group(1))
					.value(externalReferenceMatcher.group(2))
					.build();

			return Optional.of(new ExternalIdentifier(Type.EXTERNAL_REFERENCE, identifier, valueAndSystem));
		}

		final Matcher glnMatcher = Type.GLN.pattern.matcher(identifier);
		if (glnMatcher.matches())
		{
			return Optional.of(new ExternalIdentifier(Type.GLN, identifier, null));
		}

		final Matcher valMatcher = Type.VALUE.pattern.matcher(identifier);
		if (valMatcher.matches())
		{
			return Optional.of(new ExternalIdentifier(Type.VALUE, identifier, null));
		}

		final Matcher internalNameMatcher = Type.INTERNAL_NAME.pattern.matcher(identifier);
		if (internalNameMatcher.matches())
		{
			return Optional.of(new ExternalIdentifier(Type.INTERNAL_NAME, identifier, null));
		}

		final Matcher nameMatcher = Type.NAME.pattern.matcher(identifier);
		if (nameMatcher.matches())
		{
			return Optional.of(new ExternalIdentifier(Type.NAME, identifier, null));
		}

		final Matcher ibanMatcher = Type.IBAN.pattern.matcher(identifier);
		if (ibanMatcher.matches())
		{
			return Optional.of(new ExternalIdentifier(Type.IBAN, identifier, null));
		}

		final Matcher qrIbanMatcher = Type.QR_IBAN.pattern.matcher(identifier);
		if (qrIbanMatcher.matches())
		{
			return Optional.of(new ExternalIdentifier(Type.QR_IBAN, identifier, null));
		}

		return Optional.empty();
	}

	@NonNull
	public static ExternalIdentifier of(@NonNull final String identifier)
	{
		return ofIdentifierCandidate(identifier)
				.orElseThrow(() -> new AdempiereException("Unknown externalId type!")
						.appendParametersToMessage()
						.setParameter("externalId", identifier));
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
	public MetasfreshId asMetasfreshId()
	{
		Check.assume(Type.METASFRESH_ID.equals(type),
					 "The type of this instance needs to be {}; this={}", Type.METASFRESH_ID, this);

		return MetasfreshId.of(Integer.parseInt(rawValue));
	}

	@NonNull
	public GLN asGLN()
	{
		Check.assume(Type.GLN.equals(type),
					 "The type of this instance needs to be {}; this={}", Type.GLN, this);

		final Matcher glnMatcher = Type.GLN.pattern.matcher(rawValue);

		if (glnMatcher.find())
		{
			return GLN.ofString(glnMatcher.group(1));
		}
		else
		{
			throw new AdempiereException("External identifier of GLN parsing failed. External Identifier:" + rawValue);
		}

	}

	@NonNull
	public String asValue()
	{
		Check.assume(Type.VALUE.equals(type),
					 "The type of this instance needs to be {}; this={}", Type.VALUE, this);

		final Matcher valueMatcher = Type.VALUE.pattern.matcher(rawValue);

		if (!valueMatcher.matches())
		{
			throw new AdempiereException("External identifier of Value parsing failed. External Identifier:" + rawValue);
		}

		return valueMatcher.group(1);
	}

	@NonNull
	public String asInternalName()
	{
		Check.assume(Type.INTERNAL_NAME.equals(type),
					 "The type of this instance needs to be {}; this={}", Type.INTERNAL_NAME, this);

		final Matcher valueMatcher = Type.INTERNAL_NAME.pattern.matcher(rawValue);

		if (!valueMatcher.matches())
		{
			throw new AdempiereException("External identifier of InternalName parsing failed. External Identifier:" + rawValue);
		}

		return valueMatcher.group(1);
	}

	@NonNull
	public String asName()
	{
		Check.assume(Type.NAME.equals(type),
					 "The type of this instance needs to be {}; this={}", Type.NAME, this);

		final Matcher nameMatcher = Type.NAME.pattern.matcher(rawValue);

		if (!nameMatcher.matches())
		{
			throw new AdempiereException("External identifier of Name parsing failed. External Identifier:" + rawValue);
		}

		return nameMatcher.group(1);
	}

	@NonNull
	public String asIban()
	{
		Check.assume(Type.IBAN.equals(type),
					 "The type of this instance needs to be {}; this={}", Type.IBAN, this);

		final Matcher ibanMatcher = Type.IBAN.pattern.matcher(rawValue);

		if (!ibanMatcher.matches())
		{
			throw new AdempiereException("External identifier of IBAN parsing failed. External Identifier:" + rawValue);
		}

		return ibanMatcher.group(1);
	}

	@NonNull
	public String asQrIban()
	{
		Check.assume(Type.QR_IBAN.equals(type),
					 "The type of this instance needs to be {}; this={}", Type.QR_IBAN, this);

		final Matcher qrIbanMatcher = Type.QR_IBAN.pattern.matcher(rawValue);

		if (!qrIbanMatcher.matches())
		{
			throw new AdempiereException("External identifier of QR_IBAN parsing failed. External Identifier:" + rawValue);
		}

		return qrIbanMatcher.group(1);
	}

	@AllArgsConstructor
	@Getter
	public enum Type
	{
		METASFRESH_ID(Pattern.compile("^\\d+$")),
		EXTERNAL_REFERENCE(Pattern.compile("(?:^ext-)([a-zA-Z0-9]+)-(.+)")),
		GLN(Pattern.compile("(?:^gln)-(.+)")),
		VALUE(Pattern.compile("(?:^val)-(.+)")),
		INTERNAL_NAME(Pattern.compile("(?:^int)-(.+)")),
		NAME(Pattern.compile("(?:^name)-(.+)")),
		IBAN(Pattern.compile("(?:^iban)-(.+)")),
		QR_IBAN(Pattern.compile("(?:^qr_iban)-(.+)")),
		;

		private final Pattern pattern;
	}
}
