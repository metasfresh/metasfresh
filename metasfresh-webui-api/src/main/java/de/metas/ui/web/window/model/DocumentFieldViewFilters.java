package de.metas.ui.web.window.model;

import java.util.Set;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Contains miscellaneous factory methods for {@link IDocumentFieldViewFilter}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DocumentFieldViewFilters
{
	public static final IDocumentFieldViewFilter any()
	{
		return ANY;
	}
	
	public static final IDocumentFieldViewFilter publicFields()
	{
		return PUBLIC_FIELDS; 
	}

	/**
	 *
	 * @param fieldNamesListStr comma separated field names
	 * @return filter
	 */
	public static final IDocumentFieldViewFilter fromFieldsNameSetString(final String fieldNamesSetStr)
	{
		Check.assumeNotEmpty(fieldNamesSetStr, "fieldNamesSetStr is not empty");
		final Set<String> fieldNamesSet = ImmutableSet.copyOf(FIELDS_LIST_SPLITTER.splitToList(fieldNamesSetStr));
		return fromFieldsNameSet(fieldNamesSet);
	}

	public static final IDocumentFieldViewFilter fromFieldsNameSet(final Set<String> fieldNamesSet)
	{
		Check.assumeNotNull(fieldNamesSet, "Parameter fieldNamesSet is not null");
		if (fieldNamesSet.contains("*"))
		{
			return PUBLIC_FIELDS;
		}

		if (fieldNamesSet.size() == 1)
		{
			return new SingleFieldNameFilter(fieldNamesSet.iterator().next());
		}

		return new FieldNamesListFilter(fieldNamesSet);
	}

	private static final transient Splitter FIELDS_LIST_SPLITTER = Splitter.on(",")
			.trimResults()
			.omitEmptyStrings();

	private static final IDocumentFieldViewFilter ANY = new IDocumentFieldViewFilter()
	{
		@Override
		public String toString()
		{
			return "all fields";
		};

		@Override
		public boolean test(final IDocumentFieldView field)
		{
			return true;
		}
	};
	
	private static final IDocumentFieldViewFilter PUBLIC_FIELDS = new IDocumentFieldViewFilter()
	{
		@Override
		public String toString()
		{
			return "public fields";
		};
		
		@Override
		public boolean test(IDocumentFieldView field)
		{
			return field.isPublicField();
		}
	};

	private static final class FieldNamesListFilter implements IDocumentFieldViewFilter
	{
		private final Set<String> fieldNames;

		private FieldNamesListFilter(final Set<String> fieldNames)
		{
			super();
			Check.assumeNotEmpty(fieldNames, "fieldNames is not empty");
			this.fieldNames = ImmutableSet.copyOf(fieldNames);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("fields-count", fieldNames.size())
					.add("fields", fieldNames)
					.toString();
		}

		@Override
		public boolean test(final IDocumentFieldView field)
		{
			if (field == null)
			{
				return false;
			}

			return fieldNames.contains(field.getFieldName());
		}
	}

	private static final class SingleFieldNameFilter implements IDocumentFieldViewFilter
	{
		private final String fieldName;

		private SingleFieldNameFilter(final String fieldName)
		{
			super();
			Check.assumeNotEmpty(fieldName, "fieldName is not empty");
			this.fieldName = fieldName;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.addValue(fieldName)
					.toString();
		}

		@Override
		public boolean test(final IDocumentFieldView field)
		{
			if (field == null)
			{
				return false;
			}

			return this.fieldName.equals(field.getFieldName());
		}
	}

}
