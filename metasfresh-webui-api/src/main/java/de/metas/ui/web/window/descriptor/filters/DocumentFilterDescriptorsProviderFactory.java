package de.metas.ui.web.window.descriptor.filters;

import java.util.Collection;

import org.compiere.model.MQuery.Operator;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
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

	private DocumentFilterDescriptorsProviderFactory()
	{
		super();
	}

	public DocumentFilterDescriptorsProvider createFiltersProvider(final int adTabId, final String tableName, final Collection<DocumentFieldDescriptor> fields)
	{
		//
		// Standard field filters: filters created from document fields which are flagged with AllowFiltering
		final ImmutableDocumentFilterDescriptorsProvider standardFieldFilters = fields
				.stream()
				.filter(field -> field.hasCharacteristic(Characteristic.AllowFiltering))
				.map(field -> createFilter(field))
				.collect(ImmutableDocumentFilterDescriptorsProvider.collector());

		//
		// User query filters: filters created from user queries
		final UserQueryDocumentFilterDescriptorsProvider userQueryFilters = new UserQueryDocumentFilterDescriptorsProvider(adTabId, tableName, fields);

		//
		return CompositeDocumentFilterDescriptorsProvider.of(userQueryFilters, standardFieldFilters);
	}

	private final DocumentFilterDescriptor createFilter(final DocumentFieldDescriptor field)
	{
		final ITranslatableString displayName = field.getCaption();
		final String fieldName = field.getFieldName();
		final DocumentFieldWidgetType widgetType = field.getWidgetType();
		
		final DocumentFieldDataBindingDescriptor dataBinding = field.getDataBinding().orElse(null);
		final LookupDescriptor lookupDescriptor = dataBinding == null ? null : dataBinding.getLookupDescriptor(LookupScope.DocumentFilter);

		return DocumentFilterDescriptor.builder()
				.setFilterId(fieldName)
				.setDisplayName(displayName)
				.setFrequentUsed(false)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setDisplayName(displayName)
						.setFieldName(fieldName)
						.setWidgetType(widgetType)
						.setOperator(Operator.EQUAL)
						.setLookupDescriptor(lookupDescriptor))
				.build();
	}

}
