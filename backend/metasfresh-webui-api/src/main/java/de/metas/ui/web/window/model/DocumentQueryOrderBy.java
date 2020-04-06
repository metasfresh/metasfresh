package de.metas.ui.web.window.model;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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
public final class DocumentQueryOrderBy
{
	public static DocumentQueryOrderBy byFieldName(final String fieldName)
	{
		final boolean ascending = true;
		final boolean nullsLast = getDefaultNullsLastByAscending(ascending);
		return new DocumentQueryOrderBy(fieldName, ascending, nullsLast);
	}

	public static DocumentQueryOrderBy byFieldName(final String fieldName, final boolean ascending)
	{
		final boolean nullsLast = getDefaultNullsLastByAscending(ascending);
		return new DocumentQueryOrderBy(fieldName, ascending, nullsLast);
	}

	/**
	 * @param orderByStr field name with optional +/- prefix for ascending/descending. e.g. +C_BPartner_ID
	 */
	static DocumentQueryOrderBy parse(final String orderByStr)
	{
		if (orderByStr.charAt(0) == '+')
		{
			final String fieldName = orderByStr.substring(1);
			final boolean ascending = true;
			final boolean nullsLast = getDefaultNullsLastByAscending(ascending);
			return new DocumentQueryOrderBy(fieldName, ascending, nullsLast);
		}
		else if (orderByStr.charAt(0) == '-')
		{
			final String fieldName = orderByStr.substring(1);
			final boolean ascending = false;
			final boolean nullsLast = getDefaultNullsLastByAscending(ascending);
			return new DocumentQueryOrderBy(fieldName, ascending, nullsLast);
		}
		else
		{
			final String fieldName = orderByStr;
			final boolean ascending = true;
			final boolean nullsLast = getDefaultNullsLastByAscending(ascending);
			return new DocumentQueryOrderBy(fieldName, ascending, nullsLast);
		}
	}

	private static boolean getDefaultNullsLastByAscending(final boolean ascending)
	{
		return true; // always nulls last
	}

	private final String fieldName;
	private final boolean ascending;
	private final boolean nullsLast;

	@Builder
	private DocumentQueryOrderBy(final String fieldName, final Boolean ascending, final Boolean nullsLast)
	{
		Check.assumeNotEmpty(fieldName, "fieldName is not empty");
		this.fieldName = fieldName;
		this.ascending = ascending != null ? ascending : true;
		this.nullsLast = nullsLast != null ? nullsLast : getDefaultNullsLastByAscending(this.ascending);
	}

	public DocumentQueryOrderBy copyOverridingFieldName(final String fieldName)
	{
		if (Objects.equals(this.fieldName, fieldName))
		{
			return this;
		}
		return new DocumentQueryOrderBy(fieldName, ascending, nullsLast);
	}

	public <T> Comparator<T> asComparator(@NonNull final FieldValueExtractor<T> fieldValueExtractor, @NonNull final JSONOptions jsonOpts)
	{
		final Function<T, Object> keyExtractor = obj -> fieldValueExtractor.getFieldValue(obj, fieldName, jsonOpts);
		Comparator<? super Object> keyComparator = ValueComparator.ofAscendingAndNullsLast(ascending, nullsLast);
		return Comparator.comparing(keyExtractor, keyComparator);
	}

	@FunctionalInterface
	public interface FieldValueExtractor<T>
	{
		Object getFieldValue(T object, String fieldName, JSONOptions jsonOpts);
	}

	@ToString
	private static final class ValueComparator implements Comparator<Object>
	{
		public static ValueComparator ofAscendingAndNullsLast(final boolean ascending, final boolean nullsLast)
		{
			if (ascending)
			{
				return nullsLast ? ASCENDING_NULLS_LAST : ASCENDING_NULLS_FIRST;
			}
			else
			{
				return nullsLast ? DESCENDING_NULLS_LAST : DESCENDING_NULLS_FIRST;
			}
		}

		public static final transient ValueComparator ASCENDING_NULLS_FIRST = new ValueComparator(true, false);
		public static final transient ValueComparator ASCENDING_NULLS_LAST = new ValueComparator(true, true);
		public static final transient ValueComparator DESCENDING_NULLS_FIRST = new ValueComparator(false, false);
		public static final transient ValueComparator DESCENDING_NULLS_LAST = new ValueComparator(false, true);

		private final boolean ascending;
		private final boolean nullsLast;

		private ValueComparator(final boolean ascending, final boolean nullsLast)
		{
			this.ascending = ascending;
			this.nullsLast = nullsLast;
		}

		@Override
		public int compare(final Object o1, final Object o2)
		{
			if (o1 == o2)
			{
				return 0;
			}
			else if (o1 == null || o1 instanceof JSONNullValue)
			{
				return nullsLast ? +1 : -1;
			}
			else if (o2 == null || o2 instanceof JSONNullValue)
			{
				return nullsLast ? -1 : +1;
			}
			else if (o1 instanceof Comparable)
			{
				@SuppressWarnings("unchecked")
				final Comparable<Object> o1cmp = (Comparable<Object>)o1;
				return o1cmp.compareTo(o2) * (ascending ? +1 : -1);
			}
			else
			{
				return o1.toString().compareTo(o2.toString()) * (ascending ? +1 : -1);
			}
		}
	}
}
