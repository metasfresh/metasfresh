package de.metas.ui.web.window.descriptor.filters;

import java.util.Collection;
import java.util.stream.Stream;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.MQuery.Operator;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptor.LookupScope;

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
	public static final transient DocumentFilterDescriptorsProviderFactory instance = new DocumentFilterDescriptorsProviderFactory();

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private DocumentFilterDescriptorsProviderFactory()
	{
		super();
	}

	public DocumentFilterDescriptorsProvider createFiltersProvider(final int adTabId, final String tableName, final Collection<DocumentFieldDescriptor> fields)
	{
		//
		// Standard field filters: filters created from document fields which are flagged with AllowFiltering
		final ImmutableDocumentFilterDescriptorsProvider standardFieldFilters;
		{
			final DocumentFilterDescriptor.Builder defaultFilter = DocumentFilterDescriptor.builder()
					.setFilterId("default")
					.setDisplayName(msgBL.getTranslatableMsgText("default"))
					.setFrequentUsed(false);
			final DocumentFilterDescriptor.Builder defaultDateFilter = DocumentFilterDescriptor.builder()
					.setFilterId("default-date")
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

			standardFieldFilters = Stream.of(defaultDateFilter, defaultFilter)
					.filter(filterBuilder -> filterBuilder.hasParameters())
					.map(filterBuilder -> filterBuilder.build())
					.collect(ImmutableDocumentFilterDescriptorsProvider.collector());
		}

		//
		// User query filters: filters created from user queries
		final UserQueryDocumentFilterDescriptorsProvider userQueryFilters = new UserQueryDocumentFilterDescriptorsProvider(adTabId, tableName, fields);

		//
		return CompositeDocumentFilterDescriptorsProvider.of(userQueryFilters, standardFieldFilters);
	}

	private final DocumentFilterParamDescriptor.Builder createFilterParam(final DocumentFieldDescriptor field)
	{
		final ITranslatableString displayName = field.getCaption();
		final String fieldName = field.getFieldName();
		final DocumentFieldWidgetType widgetType = field.getWidgetType();
		
		final LookupDescriptor lookupDescriptor = field.getLookupDescriptor(LookupScope.DocumentFilter);
		
		final Operator operator;
		if (widgetType.isRangeFilteringSupported())
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
				.setRequired(false);
	}

}
