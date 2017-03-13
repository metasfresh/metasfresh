package de.metas.ui.web.window.descriptor.filters;

import java.util.Collection;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.MQuery.Operator;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;

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
 * {@link DocumentFilterDescriptorsProvider}s factory.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class DocumentFilterDescriptorsProviderFactory
{
	private static final String FILTER_ID_Default = "default";
	private static final String FILTER_ID_DefaultDate = "default-date";
	private static final String MSG_DefaultFilterName = "default";

	public static final transient DocumentFilterDescriptorsProviderFactory instance = new DocumentFilterDescriptorsProviderFactory();

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private DocumentFilterDescriptorsProviderFactory()
	{
		super();
	}

	public DocumentFilterDescriptorsProvider createFiltersProvider(final int adTabId, @Nullable final String tableName, final Collection<DocumentFieldDescriptor> fields)
	{
		//
		// Standard field filters: filters created from document fields which are flagged with AllowFiltering
		final ImmutableDocumentFilterDescriptorsProvider standardFieldFilters = createFiltersProvider_Defaults(fields);

		//
		// User query filters: filters created from user queries
		final DocumentFilterDescriptorsProvider userQueryFilters;
		if (tableName != null && adTabId > 0)
		{
			userQueryFilters = new UserQueryDocumentFilterDescriptorsProvider(adTabId, tableName, fields);
		}
		else
		{
			userQueryFilters = NullDocumentFilterDescriptorsProvider.instance;
		}

		//
		return CompositeDocumentFilterDescriptorsProvider.compose(userQueryFilters, standardFieldFilters);
	}

	/**
	 * Creates standard filters, i.e. from document fields which are flagged with {@link Characteristic#AllowFiltering}.
	 * 
	 * @param fields
	 */
	private ImmutableDocumentFilterDescriptorsProvider createFiltersProvider_Defaults(final Collection<DocumentFieldDescriptor> fields)
	{
		final DocumentFilterDescriptor.Builder defaultFilter = DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID_Default)
				.setDisplayName(msgBL.getTranslatableMsgText(MSG_DefaultFilterName))
				.setFrequentUsed(false);
		final DocumentFilterDescriptor.Builder defaultDateFilter = DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID_DefaultDate)
				.setFrequentUsed(true);
		for (final DocumentFieldDescriptor field : fields)
		{
			if (!field.hasCharacteristic(Characteristic.AllowFiltering))
			{
				continue;
			}

			final DocumentFilterParamDescriptor.Builder filterParam = createFilterParam(field);
			if (!defaultDateFilter.hasParameters() && filterParam.getWidgetType().isDateOrTime())
			{
				defaultDateFilter.setDisplayName(filterParam.getDisplayName());
				defaultDateFilter.addParameter(filterParam);
			}
			else
			{
				defaultFilter.addParameter(filterParam);
			}
		}

		return Stream.of(defaultDateFilter, defaultFilter)
				.filter(filterBuilder -> filterBuilder.hasParameters())
				.map(filterBuilder -> filterBuilder.build())
				.collect(ImmutableDocumentFilterDescriptorsProvider.collector());
	}

	private final DocumentFilterParamDescriptor.Builder createFilterParam(final DocumentFieldDescriptor field)
	{
		final ITranslatableString displayName = field.getCaption();
		final String fieldName = field.getFieldName();
		final DocumentFieldWidgetType widgetType = field.getWidgetType();

		final LookupDescriptor lookupDescriptor = field.getLookupDescriptor(LookupDescriptorProvider.LookupScope.DocumentFilter);

		final Operator operator;
		if(widgetType.isText())
		{
			operator = Operator.LIKE_I;
		}
		else if (widgetType.isRangeFilteringSupported())
		{
			operator = Operator.BETWEEN;
		}
		else
		{
			operator = Operator.EQUAL;
		}

		return DocumentFilterParamDescriptor.builder()
				.setDisplayName(displayName)
				.setFieldName(fieldName)
				.setWidgetType(widgetType)
				.setOperator(operator)
				.setLookupDescriptor(lookupDescriptor)
				.setMandatory(false);
	}

}
