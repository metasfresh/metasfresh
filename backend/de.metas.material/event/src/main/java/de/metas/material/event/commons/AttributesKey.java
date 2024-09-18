package de.metas.material.event.commons;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
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

@EqualsAndHashCode(doNotUseGetters = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class AttributesKey implements Comparable<AttributesKey>
{
	// first, declare the "static" constants that might be used within the "factory-method" constants

	/** The delimiter should not contain any character that has a "regexp" meaning and would interfere with {@link String#replaceAll(String, String)}. */
	@VisibleForTesting
	public static final String ATTRIBUTES_KEY_DELIMITER = "§&§";
	private static final Joiner ATTRIBUTEVALUEIDS_JOINER = Joiner.on(ATTRIBUTES_KEY_DELIMITER).skipNulls();
	private static final Splitter ATTRIBUTEVALUEIDS_SPLITTER = Splitter.on(ATTRIBUTES_KEY_DELIMITER).omitEmptyStrings().trimResults();

	public static final AttributesKey ALL = AttributesKey.ofAttributeValueIds(-1000);
	public static final AdMessageKey MSG_ATTRIBUTES_KEY_ALL = AdMessageKey.of("de.metas.material.dispo.<ALL_ATTRIBUTES_KEYS>");

	/** This key's meaning depends on the other keys it comes with. */
	public static final AttributesKey OTHER = AttributesKey.ofAttributeValueIds(-1001);
	public static final AdMessageKey MSG_ATTRIBUTES_KEY_OTHER = AdMessageKey.of("de.metas.material.dispo.<OTHER_ATTRIBUTES_KEYS>");

	public static final AttributesKey NONE = AttributesKey.ofAttributeValueIds(-1002);

	@JsonCreator
	@NonNull
	public static AttributesKey ofString(@Nullable final String attributesKeyString)
	{
		if (attributesKeyString == null || attributesKeyString.trim().isEmpty())
		{
			return NONE;
		}

		final ImmutableSet<AttributesKeyPart> parts = extractAttributeKeyParts(attributesKeyString);
		final String attributesKeyStringNorm = toAttributesKeyString(parts);
		if (attributesKeyStringNorm.isEmpty())
		{
			return NONE;
		}

		return new AttributesKey(attributesKeyStringNorm, parts);
	}

	@VisibleForTesting
	public static AttributesKey ofAttributeValueIds(final int... attributeValueIds)
	{
		if (attributeValueIds == null || attributeValueIds.length == 0)
		{
			return NONE;
		}

		return ofParts(IntStream.of(attributeValueIds)
				.mapToObj(AttributesKeyPart::ofInteger)
				.collect(ImmutableSet.toImmutableSet()));
	}

	@VisibleForTesting
	public static AttributesKey ofAttributeValueIds(final AttributeValueId... attributeValueIds)
	{
		if (attributeValueIds == null || attributeValueIds.length == 0)
		{
			return NONE;
		}

		return ofParts(Stream.of(attributeValueIds)
				.map(AttributesKeyPart::ofAttributeValueId)
				.collect(ImmutableSet.toImmutableSet()));
	}

	public static AttributesKey ofParts(final AttributesKeyPart... partsArray)
	{
		if (partsArray == null || partsArray.length == 0)
		{
			return NONE;
		}

		final ImmutableSet<AttributesKeyPart> parts = ImmutableSet.copyOf(partsArray);
		final String attributesKeyString = toAttributesKeyString(parts);
		return new AttributesKey(attributesKeyString, parts);
	}

	public static AttributesKey ofParts(final Collection<AttributesKeyPart> parts)
	{
		if (parts == null || parts.isEmpty())
		{
			return NONE;
		}

		final ImmutableSet<AttributesKeyPart> partsSet = ImmutableSet.copyOf(parts);
		final String attributesKeyString = toAttributesKeyString(partsSet);
		return new AttributesKey(attributesKeyString, partsSet);
	}

	private static String toAttributesKeyString(final Collection<AttributesKeyPart> parts)
	{
		if (parts == null || parts.isEmpty())
		{
			return NONE.getAsString();
		}

		return ATTRIBUTEVALUEIDS_JOINER.join(parts.stream().sorted().toArray());
	}

	private final String attributesKeyString;
	@Getter
	@JsonIgnore
	private final ImmutableSet<AttributesKeyPart> parts;

	private AttributesKey(
			@NonNull final String attributesKeyString,
			@NonNull final ImmutableSet<AttributesKeyPart> parts)
	{
		// don't allow NULL because within the DB we have an index on this and NULL values are trouble with indexes
		this.attributesKeyString = attributesKeyString;
		this.parts = parts;
	}

	/**
	 * @deprecated Please use {@link #getAsString()}.
	 */
	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@NonNull
	@JsonValue
	public String getAsString()
	{
		return attributesKeyString;
	}

	public boolean isNone()
	{
		return NONE.equals(this);
	}

	public boolean isAll()
	{
		return ALL.equals(this);
	}

	public boolean isOther()
	{
		return OTHER.equals(this);
	}

	public void assertNotAllOrOther()
	{
		Check.errorIf(isOther() || isAll(),
				"AttributesKeys.OTHER or .ALL of the given attributesKey is not supported; attributesKey={}", this);
	}

	private static ImmutableSet<AttributesKeyPart> extractAttributeKeyParts(final String attributesKeyString)
	{
		if (attributesKeyString.trim().isEmpty())
		{
			return ImmutableSet.of();
		}

		try
		{
			return ATTRIBUTEVALUEIDS_SPLITTER.splitToList(attributesKeyString.trim())
					.stream()
					.map(AttributesKeyPart::parseString)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(ImmutableSet.toImmutableSet());
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.appendParametersToMessage()
					.setParameter("attributesKeyString", attributesKeyString);
		}
	}

	public boolean contains(@NonNull final AttributesKey attributesKey)
	{
		return parts.containsAll(attributesKey.parts);
	}

	public AttributesKey getIntersection(@NonNull final AttributesKey attributesKey)
	{
		final HashSet<AttributesKeyPart> ownMutableParts = new HashSet<>(parts);
		ownMutableParts.retainAll(attributesKey.parts);
		return AttributesKey.ofParts(ownMutableParts);
	}

	/**
	 * @return {@code true} if ad least one attributeValueId from the given {@code attributesKey} is included in this instance.
	 */
	public boolean intersects(@NonNull final AttributesKey attributesKey)
	{
		return parts.stream().anyMatch(attributesKey.parts::contains);
	}

	public String getValueByAttributeId(@NonNull final AttributeId attributeId)
	{
		for (final AttributesKeyPart part : parts)
		{
			if (part.getType() == AttributeKeyPartType.AttributeIdAndValue
					&& AttributeId.equals(part.getAttributeId(), attributeId))
			{
				return part.getValue();
			}
		}

		throw new AdempiereException("Attribute `" + attributeId + "` was not found in `" + this + "`");
	}

	public static boolean equals(@Nullable final AttributesKey k1, @Nullable final AttributesKey k2)
	{
		return Objects.equals(k1, k2);
	}

	@Override
	public int compareTo(@Nullable final AttributesKey o)
	{
		if (o == null)
		{
			return 1; // we assume that null is less than not-null
		}
		return this.getAsString().compareTo(o.getAsString());
	}
}
