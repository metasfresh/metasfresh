package org.adempiere.ad.table;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Null;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@ToString(of = "keysByColumnName", includeFieldNames = false)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ComposedRecordId
{
	public static final ComposedRecordId singleKey(final String singleKeyColumnName, final int recordId)
	{
		return new ComposedRecordId(singleKeyColumnName, recordId);
	}

	public static final ComposedRecordId composedKey(final Map<String, Object> keysByColumnName)
	{
		return new ComposedRecordId(keysByColumnName);
	}

	@Getter(AccessLevel.NONE)
	private final ImmutableSet<String> keyColumnNames;
	@Getter(AccessLevel.NONE)
	private final ImmutableMap<String, Object> keysByColumnName;

	private ComposedRecordId(@NonNull final String singleKeyColumnName, final int recordId)
	{
		Check.assumeGreaterOrEqualToZero(recordId, "recordId");

		keyColumnNames = ImmutableSet.of(singleKeyColumnName);
		keysByColumnName = ImmutableMap.of(singleKeyColumnName, recordId);
	}

	private ComposedRecordId(final Map<String, Object> keysByColumnName)
	{
		Check.assumeNotEmpty(keysByColumnName, "map is not empty");

		keyColumnNames = ImmutableSet.copyOf(keysByColumnName.keySet());
		this.keysByColumnName = keysByColumnName.entrySet()
				.stream()
				.filter(e -> !Null.isNull(e.getValue()))
				.collect(GuavaCollectors.toImmutableMap());
	}

	public String toInfoString()
	{
		return keysByColumnName.entrySet()
				.stream()
				.map(entry -> entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining(", "));
	}

	public boolean isSingleKey()
	{
		return keyColumnNames.size() == 1;
	}

	public OptionalInt getSingleRecordId()
	{
		if (!isSingleKey())
		{
			return OptionalInt.empty();
		}

		final Object recordIdObj = keysByColumnName.values().iterator().next();
		if (recordIdObj instanceof Integer)
		{
			return OptionalInt.of((int)recordIdObj);
		}
		else
		{
			return OptionalInt.empty();
		}
	}

	public List<Object> getKeysAsList(final List<String> columnNames)
	{
		Check.assumeNotEmpty(columnNames, "columnNames is not empty");
		return columnNames.stream()
				.map(this::getKeyValue)
				.collect(Collectors.toList());
	}

	public Object getKeyValue(final String keyColumnName)
	{
		if (!keyColumnNames.contains(keyColumnName))
		{
			throw new AdempiereException("Column " + keyColumnName + " is not a key column in " + this);
		}

		return keysByColumnName.get(keyColumnName);
	}
}
