package de.metas.material.event.commons;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@EqualsAndHashCode
public final class AttributesKey
{
	// first, declare the "static" constants that might be used within the "factory-method" constants

	/** The delimiter should not contain any character that has a "regexp" meaning and would interfere with {@link String#replaceAll(String, String)}. */
	private static final String ATTRIBUTES_KEY_DELIMITER = "§&§";
	private static final Joiner ATTRIBUTEVALUEIDS_JOINER = Joiner.on(ATTRIBUTES_KEY_DELIMITER).skipNulls();
	private static final Splitter ATTRIBUTEVALUEIDS_SPLITTER = Splitter.on(ATTRIBUTES_KEY_DELIMITER).omitEmptyStrings();

	public static final AttributesKey ALL = AttributesKey.ofAttributeValueIds(-1000);
	public static final String MSG_ATTRIBUTES_KEY_ALL = "de.metas.material.dispo.<ALL_ATTRIBUTES_KEYS>";

	/** This key's meaning depends on the other keys it comes with. */
	public static final AttributesKey OTHER = AttributesKey.ofAttributeValueIds(-1001);
	public static final String MSG_ATTRIBUTES_KEY_OTHER = "de.metas.material.dispo.<OTHER_ATTRIBUTES_KEYS>";

	public static final AttributesKey NONE = AttributesKey.ofAttributeValueIds(-1002);

	@JsonCreator
	public static final AttributesKey ofString(final String attributesKeyString)
	{
		if (attributesKeyString == null)
		{
			return NONE;
		}
		final String attributesKeyStringNorm = attributesKeyString.trim();
		if (attributesKeyStringNorm.isEmpty())
		{
			return NONE;
		}

		final ImmutableList<Integer> attributeValueIdsList = null;
		return new AttributesKey(attributesKeyStringNorm, attributeValueIdsList);
	}

	public static final AttributesKey ofAttributeValueIds(final int... attributeValueIds)
	{
		if (attributeValueIds == null || attributeValueIds.length == 0)
		{
			return NONE;
		}

		final ImmutableList<Integer> attributeValueIdsList = IntStream.of(attributeValueIds)
				.boxed()
				.collect(ImmutableList.toImmutableList());
		return ofAttributeValueIds(attributeValueIdsList);
	}

	public static AttributesKey ofAttributeValueIds(final Collection<Integer> attributeValueIdsList)
	{
		if (attributeValueIdsList.isEmpty())
		{
			return NONE;
		}

		final String attributesKeyString = ATTRIBUTEVALUEIDS_JOINER.join(attributeValueIdsList);
		return new AttributesKey(attributesKeyString, attributeValueIdsList);
	}

	private final String attributesKeyString;

	@JsonIgnore
	private transient ImmutableList<Integer> attributeValueIds; // lazy
	private transient String sqlLikeString; // lazy

	private AttributesKey(
			@NonNull final String attributesKeyString,
			@Nullable Collection<Integer> attributeValueIds)
	{
		// don't allow NULL because within the DB we have an index on this and NULL values are trouble with indexes
		this.attributesKeyString = attributesKeyString;

		this.attributeValueIds = attributeValueIds == null
				? null
				: ImmutableList.copyOf(attributeValueIds);
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

	@JsonValue
	@NonNull
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

	public List<Integer> getAttributeValueIds()
	{
		if (attributeValueIds == null)
		{
			attributeValueIds = ATTRIBUTEVALUEIDS_SPLITTER.splitToList(attributesKeyString)
					.stream()
					.map(Integer::parseInt)
					.collect(ImmutableList.toImmutableList());
		}
		return attributeValueIds;
	}

	public String getSqlLikeString()
	{
		if (sqlLikeString == null)
		{
			sqlLikeString = getAttributeValueIds()
					.stream()
					.map(String::valueOf)
					.collect(Collectors.joining("%"));
		}

		return sqlLikeString;
	}

	public boolean contains(@NonNull final AttributesKey attributesKey)
	{
		return getAttributeValueIds().containsAll(attributesKey.getAttributeValueIds());
	}

	/**
	 * @return {@code true} if ad least one attributeValueId from the given {@code attributesKey} is included in this instance.
	 */
	public boolean intersects(@NonNull final AttributesKey attributesKey)
	{
		final Predicate<? super Integer> givenAttributesKeyContainsCurrentId = //
				attributeValueId -> attributesKey.getAttributeValueIds().contains(attributeValueId);

		return getAttributeValueIds().stream().anyMatch(givenAttributesKeyContainsCurrentId);
	}
}
