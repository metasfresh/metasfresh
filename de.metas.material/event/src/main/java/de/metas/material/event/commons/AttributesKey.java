package de.metas.material.event.commons;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Nullable;

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

	public static final AttributesKey NONE = new AttributesKey("", ImmutableList.of());

	/** The delimiter should not contain any character that has a "regexp" meaning and would interfere with {@link String#replaceAll(String, String)}. */
	private static final String ATTRIBUTES_KEY_DELIMITER = "§&§";
	private static final Joiner ATTRIBUTEVALUEIDS_JOINER = Joiner.on(ATTRIBUTES_KEY_DELIMITER).skipNulls();
	private static final Splitter ATTRIBUTEVALUEIDS_SPLITTER = Splitter.on(ATTRIBUTES_KEY_DELIMITER).omitEmptyStrings();

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
		return this == NONE;
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
}
