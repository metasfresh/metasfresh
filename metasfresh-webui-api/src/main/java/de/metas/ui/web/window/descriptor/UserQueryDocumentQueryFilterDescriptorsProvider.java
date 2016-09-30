package de.metas.ui.web.window.descriptor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.adempiere.util.GuavaCollectors;
import org.compiere.apps.search.IUserQuery;
import org.compiere.apps.search.IUserQueryField;
import org.compiere.apps.search.IUserQueryRestriction;
import org.compiere.apps.search.IUserQueryRestriction.Join;
import org.compiere.apps.search.UserQueryRepository;
import org.compiere.util.ValueNamePair;

import de.metas.ui.web.window.model.DocumentQueryFilterParam;
import de.metas.ui.web.window.model.lookup.LookupDataSource;

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

public class UserQueryDocumentQueryFilterDescriptorsProvider implements DocumentQueryFilterDescriptorsProvider
{
	private final UserQueryRepository repository;

	public UserQueryDocumentQueryFilterDescriptorsProvider(final int adTabId, final int adTableId, final Collection<DocumentFieldDescriptor> fields)
	{
		super();

		final List<IUserQueryField> searchFields = fields
				.stream()
				.map(field -> UserQueryField.of(field))
				.collect(GuavaCollectors.toImmutableList());

		repository = UserQueryRepository.builder()
				.setAD_Tab_ID(adTabId)
				.setAD_Table_ID(adTableId)
				.setAD_User_ID_Any()
				.setSearchFields(searchFields)
				.build();
	}

	@Override
	public Collection<DocumentQueryFilterDescriptor> getAll()
	{
		return getAllByFilterId().values();
	}

	@Override
	public DocumentQueryFilterDescriptor getByFilterIdOrNull(final String filterId)
	{
		return getAllByFilterId().get(filterId);
	}

	private final Map<String, DocumentQueryFilterDescriptor> getAllByFilterId()
	{
		// TODO: caching
		return repository.getUserQueries()
				.stream()
				.map(userQuery -> createFilterDescriptor(userQuery))
				.collect(GuavaCollectors.toImmutableMapByKey(filter -> filter.getFilterId()));
	}

	private static final DocumentQueryFilterDescriptor createFilterDescriptor(final IUserQuery userQuery)
	{
		final DocumentQueryFilterDescriptor.Builder filter = DocumentQueryFilterDescriptor.builder()
				.setFilterId("userquery-" + userQuery.getId())
				.setDisplayName(userQuery.getCaption())
				.setFrequentUsed(false);

		for (final IUserQueryRestriction queryRestriction : userQuery.getSegments())
		{
			final boolean isParameter = true; // TODO queryRestriction.isParameter();

			final Join join = queryRestriction.getJoin(); // TODO join
			final IUserQueryField searchField = queryRestriction.getSearchField();
			final String fieldName = searchField.getColumnName();
			final boolean isRange = queryRestriction.isBinaryOperator();
			final ValueNamePair operator = queryRestriction.getOperator(); // TODO operator
			if (isParameter)
			{
				final String displayName = searchField.getDisplayName();
				final DocumentFieldWidgetType widgetType = UserQueryField.getWidgetType(searchField);

				filter.addParameter(DocumentQueryFilterParamDescriptor.builder()
						.setDisplayName(displayName)
						.setFieldName(fieldName)
						.setWidgetType(widgetType)
						.setRangeParameter(isRange)
						.build());
			}
			else
			{
				filter.addInternalParameter(DocumentQueryFilterParam.builder()
						.setFieldName(fieldName)
						.setRange(isRange)
						.setValue(queryRestriction.getValue())
						.setValueTo(queryRestriction.getValueTo())
						.build());
			}
		}

		return filter.build();
	}

	private static final class UserQueryField implements IUserQueryField
	{
		public static final UserQueryField of(final DocumentFieldDescriptor field)
		{
			return new UserQueryField(field);
		}

		public static final DocumentFieldWidgetType getWidgetType(final IUserQueryField userQueryField)
		{
			return ((UserQueryField)userQueryField).getWidgetType();
		}

		private final String fieldName;
		private final DocumentFieldWidgetType widgetType;
		private final Class<?> valueClass;

		private UserQueryField(final DocumentFieldDescriptor field)
		{
			super();
			// NOTE: don't store the reference to "field" because we want to make this class as light as possible
			fieldName = field.getFieldName();
			widgetType = field.getWidgetType();
			valueClass = field.getValueClass();
		}

		@Override
		public String getColumnName()
		{
			return fieldName;
		}

		@Override
		public String getDisplayName()
		{
			// TODO introduce field.getCaption()
			return null;
		}

		@Override
		public String getColumnSQL()
		{
			return null; // shall not be needed
		}

		public DocumentFieldWidgetType getWidgetType()
		{
			return widgetType;
		}

		@Override
		public String getValueDisplay(final Object value)
		{
			return null; // not needed
		}

		@Override
		public Object convertValueToFieldType(final Object valueObj)
		{
			final LookupDataSource lookupDataSource = null;
			return DocumentFieldDescriptor.convertToValueClass(fieldName, valueObj, valueClass, lookupDataSource);
		}
	}
}
