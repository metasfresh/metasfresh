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
import de.metas.common.externalreference.JsonExternalReferenceItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalreference.JsonSingleExternalReferenceCreateReq;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.rest_api.utils.MetasfreshId;
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

import static de.metas.util.Check.isEmpty;

@Value
public class ExternalIdentifier
{
	@NonNull
	Type type;

	@NonNull
	String rawValue;

	@Nullable
	GLN gln;

	@Nullable
	ExternalReferenceValueAndSystem externalReferenceValueAndSystem;

	private ExternalIdentifier(
			@NonNull final Type type,
			@NonNull final String rawValue,
			@Nullable final GLN gln,
			@Nullable final ExternalReferenceValueAndSystem externalReferenceValueAndSystem)
	{
		if (Type.EXTERNAL_REFERENCE.equals(type) && externalReferenceValueAndSystem == null)
		{
			throw new AdempiereException("ExternalReferenceValueAndSystem cannot be null for type: EXTERNAL_REFERENCE!")
					.appendParametersToMessage()
					.setParameter("rawValue", rawValue)
					.setParameter("type", type);
		}

		if (Type.GLN.equals(type) && gln == null)
		{
			throw new AdempiereException("gln cannot be null for type: GLN!")
					.appendParametersToMessage()
					.setParameter("rawValue", rawValue)
					.setParameter("type", type);
		}

		this.gln = gln;
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
	public static ExternalIdentifier of(@NonNull final String identifier)
	{
		if (Type.METASFRESH_ID.pattern.matcher(identifier).matches())
		{
			return new ExternalIdentifier(Type.METASFRESH_ID, identifier, null, null);
		}

		final Matcher externalReferenceMatcher = Type.EXTERNAL_REFERENCE.pattern.matcher(identifier);

		if (externalReferenceMatcher.matches())
		{

			final ExternalReferenceValueAndSystem valueAndSystem = ExternalReferenceValueAndSystem.builder()
					.externalSystem(externalReferenceMatcher.group(1))
					.value(externalReferenceMatcher.group(2))
					.build();

			return new ExternalIdentifier(Type.EXTERNAL_REFERENCE, identifier, null, valueAndSystem);
		}

		final Matcher glnMatcher = Type.GLN.pattern.matcher(identifier);
		if (glnMatcher.matches())
		{
			return new ExternalIdentifier(Type.GLN, identifier, GLN.ofString(glnMatcher.group(1)), null);
		}

		throw new AdempiereException("Unknown externalId type!")
				.appendParametersToMessage()
				.setParameter("externalId", identifier);
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

		return gln;
	}

	@NonNull
	public JsonExternalReferenceLookupRequest asJsonExternalReferenceLookupRequest(@NonNull final IExternalReferenceType externalReferenceType)
	{
		Check.assume(Type.EXTERNAL_REFERENCE.equals(type),
					 "The type of this instance needs to be {}; this={}", Type.EXTERNAL_REFERENCE, this);
		Check.assumeNotNull(externalReferenceValueAndSystem,
							"externalReferenceValueAndSystem cannot be null to EXTERNAL_REFERENCE type!");

		final JsonExternalReferenceLookupItem jsonExternalReferenceLookupItem =
			JsonExternalReferenceLookupItem.builder()
					.id(externalReferenceValueAndSystem.getValue())
					.type(externalReferenceType.getCode())
					.build();

		return JsonExternalReferenceLookupRequest.builder()
				.systemName(JsonExternalSystemName.of(externalReferenceValueAndSystem.getExternalSystem()))
				.item(jsonExternalReferenceLookupItem)
				.build();
	}

	@NonNull
	public JsonSingleExternalReferenceCreateReq asJsonSingleExternalReferenceCreateReq(
			@NonNull final IExternalReferenceType externalReferenceType,
			@NonNull final JsonMetasfreshId metasfreshId)
	{
		Check.assume(Type.EXTERNAL_REFERENCE.equals(type),
					 "The type of this instance needs to be {}; this={}", Type.EXTERNAL_REFERENCE, this);
		Check.assumeNotNull(externalReferenceValueAndSystem,
							"externalReferenceValueAndSystem cannot be null to EXTERNAL_REFERENCE type!");

		final JsonExternalReferenceLookupItem externalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.id(externalReferenceValueAndSystem.getValue())
				.type(externalReferenceType.getCode())
				.build();

		final JsonExternalReferenceItem externalReferenceItem = JsonExternalReferenceItem.of(externalReferenceLookupItem, metasfreshId);

		return JsonSingleExternalReferenceCreateReq.builder()
				.systemName(JsonExternalSystemName.of(externalReferenceValueAndSystem.getExternalSystem()))
				.externalReferenceItem(externalReferenceItem)
				.build();
	}

	@AllArgsConstructor
	@Getter
	public enum Type
	{
		METASFRESH_ID(Pattern.compile("^\\d+$")),
		EXTERNAL_REFERENCE(Pattern.compile("(?:^ext-)([a-zA-Z0-9]+)-(.+)")),
		GLN(Pattern.compile("(?:^gln)-(.+)"));

		private final Pattern pattern;
	}

	@Value
	@Builder
	public static class ExternalReferenceValueAndSystem
	{
		@NonNull
		String externalSystem;

		@NonNull
		String value;
	}
}
