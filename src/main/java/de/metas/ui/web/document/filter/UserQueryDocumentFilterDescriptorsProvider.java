package de.metas.ui.web.document.filter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.compiere.apps.search.IUserQuery;
import org.compiere.apps.search.IUserQueryField;
import org.compiere.apps.search.IUserQueryRestriction;
import org.compiere.apps.search.IUserQueryRestriction.Join;
import org.compiere.apps.search.UserQueryRepository;
import org.compiere.model.I_AD_UserQuery;
import org.compiere.util.CachedSuppliers;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.model.lookup.NullLookupDataSource;
import lombok.NonNull;

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

final class UserQueryDocumentFilterDescriptorsProvider implements DocumentFilterDescriptorsProvider
{
	private static final Logger logger = LogManager.getLogger(UserQueryDocumentFilterDescriptorsProvider.class);

	private final UserQueryRepository repository;
	private final Supplier<Map<String, DocumentFilterDescriptor>> filtersSupplier = CachedSuppliers.renewOnCacheReset(() -> retrieveAllByFilterId());

	public UserQueryDocumentFilterDescriptorsProvider(final int adTabId, final String tableName, final Collection<DocumentFieldDescriptor> fields)
	{
		Check.assumeNotEmpty(tableName, "tableName is not empty");
		Check.assume(adTabId > 0, "adTabId > 0");

		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		final List<IUserQueryField> searchFields = fields
				.stream()
				.map(field -> UserQueryField.of(field))
				.collect(GuavaCollectors.toImmutableList());

		repository = UserQueryRepository.builder()
				.setAD_Tab_ID(adTabId)
				.setAD_Table_ID(adTableId)
				.setAD_User_ID(100) // FIXME: hardcoded, see https://github.com/metasfresh/metasfresh-webui/issues/162
				.setSearchFields(searchFields)
				.build();
	}

	@Override
	public Collection<DocumentFilterDescriptor> getAll()
	{
		return filtersSupplier.get().values();
	}

	@Override
	public DocumentFilterDescriptor getByFilterIdOrNull(final String filterId)
	{
		return filtersSupplier.get().get(filterId);
	}

	private final Map<String, DocumentFilterDescriptor> retrieveAllByFilterId()
	{
		return repository.getUserQueries()
				.stream()
				.map(userQuery -> createFilterDescriptorOrNull(userQuery))
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toImmutableMapByKey(DocumentFilterDescriptor::getFilterId));
	}

	private final DocumentFilterDescriptor createFilterDescriptorOrNull(@NonNull final IUserQuery userQuery)
	{
		try
		{
			return createFilterDescriptor0(userQuery);
		}
		catch (final RuntimeException e)
		{
			final String message = StringUtils.formatMessage(
					"Unable to create a DocumentFilterDescriptor for the userQuery with {}={}; Deactivating this query; Exception={}",
					I_AD_UserQuery.Table_Name, userQuery.getId(), e);
			logger.error(message, e);

			repository.deactivateUserQuery(userQuery);
			return null;
		}
	}

	private static final DocumentFilterDescriptor createFilterDescriptor0(@NonNull final IUserQuery userQuery)
	{
		final DocumentFilterDescriptor.Builder filter = DocumentFilterDescriptor.builder()
				.setFilterId("userquery-" + userQuery.getId())
				.setDisplayName(userQuery.getCaption())
				.setFrequentUsed(false);

		if (WindowConstants.isProtocolDebugging())
		{
			filter.putDebugProperty("userQuery", userQuery.toString());
		}

		for (final IUserQueryRestriction queryRestriction : userQuery.getRestrictions())
		{
			final Join join = queryRestriction.getJoin();
			final UserQueryField searchField = UserQueryField.cast(queryRestriction.getSearchField());
			final String fieldName = searchField.getColumnName();
			final Operator operator = MQueryDocumentFilterHelper.fromMQueryOperator(queryRestriction.getOperator());
			final Object value = queryRestriction.getValue();
			final Object valueTo = queryRestriction.getValueTo();

			final boolean isParameter;
			if (operator.isRangeOperator())
			{
				isParameter = value == null || valueTo == null;
			}
			else
			{
				isParameter = value == null;
			}

			if (isParameter)
			{
				final ITranslatableString displayName = searchField.getDisplayName();
				final DocumentFieldWidgetType widgetType = searchField.getWidgetType();
				final LookupDescriptor lookupDescriptor = searchField.getLookupDescriptor();

				filter.addParameter(DocumentFilterParamDescriptor.builder()
						.setJoinAnd(join == Join.AND)
						.setDisplayName(displayName)
						.setFieldName(fieldName)
						.setWidgetType(widgetType)
						.setOperator(operator)
						.setDefaultValue(value)
						.setDefaultValueTo(valueTo)
						.setMandatory(value == null) // mandatory if there is no default value
						.setLookupDescriptor(lookupDescriptor));
			}
			else
			{
				filter.addInternalParameter(DocumentFilterParam.builder()
						.setJoinAnd(join == Join.AND)
						.setFieldName(fieldName)
						.setOperator(operator)
						.setValue(value)
						.setValueTo(valueTo)
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

		public static final UserQueryField cast(final IUserQueryField userQueryField)
		{
			return (UserQueryField)userQueryField;
		}

		private final String fieldName;
		private final ITranslatableString displayName;
		private final DocumentFieldWidgetType widgetType;
		private final Class<?> valueClass;
		private final LookupDescriptor lookupDescriptor;

		private UserQueryField(final DocumentFieldDescriptor field)
		{
			super();
			// NOTE: don't store the reference to "field" because we want to make this class as light as possible
			fieldName = field.getFieldName();
			displayName = field.getCaption();
			widgetType = field.getWidgetType();
			valueClass = field.getValueClass();
			lookupDescriptor = field.getLookupDescriptor(LookupDescriptorProvider.LookupScope.DocumentFilter);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper("documentField")
					.addValue(fieldName)
					.toString();
		}

		@Override
		public String getColumnName()
		{
			return fieldName;
		}

		@Override
		public ITranslatableString getDisplayName()
		{
			return displayName;
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
			return DocumentFieldDescriptor.convertToValueClass(fieldName, valueObj, widgetType, valueClass, NullLookupDataSource.instance);
		}

		public LookupDescriptor getLookupDescriptor()
		{
			return lookupDescriptor;
		}
	}
}
