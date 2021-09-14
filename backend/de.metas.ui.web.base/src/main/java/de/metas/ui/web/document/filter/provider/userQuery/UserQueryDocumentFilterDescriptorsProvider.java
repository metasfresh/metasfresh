package de.metas.ui.web.document.filter.provider.userQuery;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CachedSuppliers;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsConstants;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.service.IADReferenceDAO;
import org.compiere.apps.search.IUserQuery;
import org.compiere.apps.search.IUserQueryRestriction;
import org.compiere.apps.search.IUserQueryRestriction.Join;
import org.compiere.apps.search.UserQueryRepository;
import org.compiere.model.I_AD_UserQuery;
import org.compiere.model.MQuery;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

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

class UserQueryDocumentFilterDescriptorsProvider implements DocumentFilterDescriptorsProvider
{
	private static final Logger logger = LogManager.getLogger(UserQueryDocumentFilterDescriptorsProvider.class);
	private final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
	private final UserQueryRepository repository;

	private final Supplier<ImmutableMap<String, DocumentFilterDescriptor>> filtersSupplier = CachedSuppliers.renewOnCacheReset(this::retrieveAllByFilterId);

	public UserQueryDocumentFilterDescriptorsProvider(@NonNull final UserQueryRepository repository)
	{
		this.repository = repository;
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

	private ImmutableMap<String, DocumentFilterDescriptor> retrieveAllByFilterId()
	{
		final ArrayList<DocumentFilterDescriptor> filters = new ArrayList<>();
		for (final IUserQuery userQuery : repository.getUserQueries())
		{
			final int sortNo = DocumentFilterDescriptorsConstants.SORT_NO_USER_QUERY_START + filters.size() + 1;
			final DocumentFilterDescriptor filter = createFilterDescriptorOrNull(userQuery, sortNo);
			if (filter != null)
			{
				filters.add(filter);
			}
		}

		return Maps.uniqueIndex(filters, DocumentFilterDescriptor::getFilterId);
	}

	@Nullable
	private DocumentFilterDescriptor createFilterDescriptorOrNull(@NonNull final IUserQuery userQuery, final int sortNo)
	{
		try
		{
			return createFilterDescriptor0(userQuery, sortNo);
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

	private DocumentFilterDescriptor createFilterDescriptor0(
			@NonNull final IUserQuery userQuery,
			final int sortNo)
	{
		final DocumentFilterDescriptor.Builder filter = DocumentFilterDescriptor.builder()
				.setFilterId("userquery-" + userQuery.getId())
				.setSortNo(sortNo)
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

			if (!queryRestriction.isInternalParameter())
			{
				final DocumentFieldWidgetType widgetType = searchField.getWidgetType();
				final Optional<LookupDescriptor> lookupDescriptor = searchField.getLookupDescriptor();

				filter.addParameter(DocumentFilterParamDescriptor.builder()
											.setJoinAnd(join == Join.AND)
											.setDisplayName(computeParameterDisplayName(queryRestriction))
											.setFieldName(fieldName)
											.setWidgetType(widgetType)
											.setOperator(operator)
											.setDefaultValue(value)
											.setDefaultValueTo(valueTo)
											.setMandatory(queryRestriction.isMandatory())
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

	private ITranslatableString computeParameterDisplayName(@NonNull final IUserQueryRestriction queryRestriction)
	{
		final TranslatableStringBuilder displayName = TranslatableStrings.builder();

		return TranslatableStrings.join(
				" ",
				queryRestriction.getSearchField().getDisplayName(),
				computeOperatorDisplayName(queryRestriction.getOperator()));
	}

	private ITranslatableString computeOperatorDisplayName(@NonNull final MQuery.Operator operator)
	{
		if (operator == MQuery.Operator.NOT_EQUAL
				|| operator == MQuery.Operator.NOT_LIKE
				|| operator == MQuery.Operator.NOT_LIKE_I)
		{
			return TranslatableStrings.anyLanguage("\u26D4");
		}
		else
		{
			return TranslatableStrings.empty();
		}
	}
}
