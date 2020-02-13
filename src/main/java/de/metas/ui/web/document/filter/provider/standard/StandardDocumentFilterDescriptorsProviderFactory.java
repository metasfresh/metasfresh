package de.metas.ui.web.document.filter.provider.standard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.descriptor.DocumentFieldDefaultFilterDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Component
public class StandardDocumentFilterDescriptorsProviderFactory implements DocumentFilterDescriptorsProviderFactory
{
	// services
	private final ISysConfigBL sysConfigs = Services.get(ISysConfigBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IViewsRepository viewsRepository;

	private static final String FILTER_ID_DefaultDate = "default-date";
	private static final int SORT_NO_DefaultDate = Integer.MIN_VALUE;

	private static final String FILTER_ID_Default = "default";
	private static final String MSG_DefaultFilterName = "default";
	private static final int SORT_NO_Default = 10000;

	private static final String FACET_FILTER_ID_PREFIX = "facet-";
	private static final String SYSCONFIG_MAX_FACETS_TO_FETCH = "webui.document.filters.MaxFacetsToFetch";
	private static final int SYSCONFIG_FACETS_TO_FETCH_DEFAULT = 10;
	private static final int SORT_NO_Facets = Integer.MAX_VALUE / 10000 * 10000;

	public StandardDocumentFilterDescriptorsProviderFactory(@NonNull final IViewsRepository viewsRepository)
	{
		this.viewsRepository = viewsRepository;
	}

	/**
	 * Creates standard filters, i.e. from document fields which are flagged with {@link Characteristic#AllowFiltering}.
	 */
	@Override
	public DocumentFilterDescriptorsProvider createFiltersProvider(
			@Nullable final AdTabId adTabId_NOTUSED,
			@Nullable final String tableName_NOTUSED,
			@NonNull final Collection<DocumentFieldDescriptor> fields)
	{
		return createFiltersProvider(fields);
	}

	private DocumentFilterDescriptorsProvider createFiltersProvider(@NonNull final Collection<DocumentFieldDescriptor> fields)
	{
		final List<DocumentFieldDescriptor> fieldsForDefaultFiltering = fields.stream()
				.filter(DocumentFieldDescriptor::hasFileringInfo)
				.filter(field -> field.getDefaultFilterInfo().isDefaultFilter())
				.sorted(Comparator.comparing(field -> field.getDefaultFilterInfo().getDefaultFilterSeqNo()))
				.collect(ImmutableList.toImmutableList());

		final List<DocumentFieldDescriptor> fieldsForFacetFiltering = fields.stream()
				.filter(DocumentFieldDescriptor::hasFileringInfo)
				.filter(field -> field.getDefaultFilterInfo().isFacetFilter())
				.sorted(Comparator.comparing(field -> field.getDefaultFilterInfo().getFacetFilterSeqNo()))
				.collect(ImmutableList.toImmutableList());

		//
		// Default filters
		DocumentFilterDescriptor defaultDateFilter = null;
		DocumentFilterDescriptor.Builder defaultFilterBuilder = null;
		for (final DocumentFieldDescriptor field : fieldsForDefaultFiltering)
		{
			final DocumentFilterParamDescriptor.Builder filterParam = createFilterParam(field);

			if (defaultDateFilter == null && filterParam.getWidgetType().isDateOrTime())
			{
				defaultDateFilter = DocumentFilterDescriptor.builder()
						.setFilterId(FILTER_ID_DefaultDate)
						.setSortNo(SORT_NO_DefaultDate)
						.setFrequentUsed(true)
						.setDisplayName(filterParam.getDisplayName())
						.addParameter(filterParam)
						.build();
			}
			else
			{
				if (defaultFilterBuilder == null)
				{
					defaultFilterBuilder = DocumentFilterDescriptor.builder()
							.setFilterId(FILTER_ID_Default)
							.setSortNo(SORT_NO_Default)
							.setDisplayName(msgBL.getTranslatableMsgText(MSG_DefaultFilterName))
							.setFrequentUsed(false);
				}
				defaultFilterBuilder.addParameter(filterParam);
			}
		}

		//
		// Facet filters
		final ArrayList<DocumentFilterDescriptor> facetFilters = new ArrayList<>();
		for (DocumentFieldDescriptor field : fieldsForFacetFiltering)
		{
			final int sortNo = facetFilters.size() + 1;
			final DocumentFilterDescriptor facetFilter = createFacetFilter(field, sortNo);
			facetFilters.add(facetFilter);
		}

		final ArrayList<DocumentFilterDescriptor> descriptors = new ArrayList<>();
		if (defaultDateFilter != null)
		{
			descriptors.add(defaultDateFilter);
		}
		if (defaultFilterBuilder != null)
		{
			descriptors.add(defaultFilterBuilder.build());
		}
		descriptors.addAll(facetFilters);

		return ImmutableDocumentFilterDescriptorsProvider.of(descriptors);
	}

	private static final DocumentFilterParamDescriptor.Builder createFilterParam(final DocumentFieldDescriptor field)
	{
		final ITranslatableString displayName = field.getCaption();
		final String fieldName = field.getFieldName();
		final DocumentFieldWidgetType widgetType = extractFilterWidgetType(field);
		final DocumentFieldDefaultFilterDescriptor filteringInfo = field.getDefaultFilterInfo();

		final Optional<LookupDescriptor> lookupDescriptor = field.getLookupDescriptorForFiltering();

		final Operator operator;
		if (widgetType.isText())
		{
			operator = Operator.LIKE_I;
		}
		else if (filteringInfo.isRangeFilter())
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
				.setMandatory(false)
				.setShowIncrementDecrementButtons(filteringInfo.isShowFilterIncrementButtons())
				.setAutoFilterInitialValue(filteringInfo.getAutoFilterInitialValue());
	}

	private static DocumentFieldWidgetType extractFilterWidgetType(final DocumentFieldDescriptor field)
	{
		final DocumentFieldWidgetType widgetType = field.getWidgetType();
		if (widgetType == DocumentFieldWidgetType.ZonedDateTime)
		{
			// frontend cannot handle ZonedDateTime filter params
			return DocumentFieldWidgetType.LocalDate;
		}
		if (widgetType == DocumentFieldWidgetType.Timestamp)
		{
			// frontend cannot handle Timestamp filter params
			return DocumentFieldWidgetType.LocalDate;
		}
		else
		{
			return widgetType;
		}
	}

	private DocumentFilterDescriptor createFacetFilter(@NonNull final DocumentFieldDescriptor field, final int sortNo)
	{
		final FacetsFilterLookupDescriptor facetsLookupDescriptor = createFacetsFilterLookupDescriptor(field);

		return DocumentFilterDescriptor.builder()
				.setFilterId(facetsLookupDescriptor.getFilterId())
				.setSortNo(SORT_NO_Facets + sortNo)
				.setFrequentUsed(true)
				.setParametersLayoutType(PanelLayoutType.Panel)
				.setDisplayName(field.getCaption())
				.setFacetFilter(true)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(facetsLookupDescriptor.getFieldName())
						.setOperator(Operator.IN_ARRAY)
						.setDisplayName(field.getCaption())
						.setMandatory(true)
						.setWidgetType(DocumentFieldWidgetType.MultiValuesList)
						.setLookupDescriptor(facetsLookupDescriptor))
				.build();
	}

	private FacetsFilterLookupDescriptor createFacetsFilterLookupDescriptor(final DocumentFieldDescriptor field)
	{
		final String columnName = field.getDataBinding().get().getColumnName();
		final String filterId = FACET_FILTER_ID_PREFIX + columnName;

		final DocumentFieldDefaultFilterDescriptor fieldFilteringInfo = field.getDefaultFilterInfo();
		final DocumentFieldWidgetType fieldWidgetType = extractFilterWidgetType(field);
		final LookupDescriptor fieldLookupDescriptor = field.getLookupDescriptorForFiltering().orElse(null);

		final boolean numericKey;
		if (fieldWidgetType.isLookup())
		{
			numericKey = fieldLookupDescriptor.isNumericKey();
		}
		else
		{
			numericKey = fieldWidgetType.isNumeric();
		}

		return FacetsFilterLookupDescriptor.builder()
				.viewsRepository(viewsRepository)
				.filterId(filterId)
				.fieldName(columnName)
				.fieldWidgetType(fieldWidgetType)
				.numericKey(numericKey)
				.maxFacetsToFetch(fieldFilteringInfo.getMaxFacetsToFetch().orElse(getMaxFacetsToFetch()))
				.fieldLookupDescriptor(fieldLookupDescriptor)
				.build();
	}

	private int getMaxFacetsToFetch()
	{
		return sysConfigs.getIntValue(SYSCONFIG_MAX_FACETS_TO_FETCH, SYSCONFIG_FACETS_TO_FETCH_DEFAULT);
	}
}
