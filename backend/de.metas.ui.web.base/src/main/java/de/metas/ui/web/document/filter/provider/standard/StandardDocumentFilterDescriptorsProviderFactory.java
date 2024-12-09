package de.metas.ui.web.document.filter.provider.standard;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.AdMessageKey;
<<<<<<< HEAD
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
=======
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterInlineRenderMode;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsConstants;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
<<<<<<< HEAD
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
=======
import de.metas.ui.web.document.filter.sql.SqlDefaultDocumentFilterConverter;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.ui.web.window.descriptor.DocumentFieldDefaultFilterDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
<<<<<<< HEAD
=======
import de.metas.util.Check;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.Collection;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
<<<<<<< HEAD
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private final IViewsRepository viewsRepository;

	private static final String FILTER_ID_DefaultDate = "default-date";

	private static final String FILTER_ID_Default = "default";
	private static final AdMessageKey MSG_DefaultFilterName = AdMessageKey.of("default");

	private static final String INLINE_FILTER_ID_PREFIX = "inline-";

	private static final String FACET_FILTER_ID_PREFIX = "facet-";
	private static final String SYSCONFIG_MAX_FACETS_TO_FETCH = "webui.document.filters.MaxFacetsToFetch";
	private static final int SYSCONFIG_FACETS_TO_FETCH_DEFAULT = 10;

	public StandardDocumentFilterDescriptorsProviderFactory(@NonNull final IViewsRepository viewsRepository)
	{
		this.viewsRepository = viewsRepository;
	}

	@Override
<<<<<<< HEAD
	public DocumentFilterDescriptorsProvider createFiltersProvider(@NonNull final CreateFiltersProviderContext context,
			@NonNull final Collection<DocumentFieldDescriptor> fields)
	{
		return createFiltersProvider(fields, context.isAutodetectDefaultDateFilter());
	}

	private DocumentFilterDescriptorsProvider createFiltersProvider(@NonNull final Collection<DocumentFieldDescriptor> fields, final boolean isAutodetectDefaultDateFilter)
	{
=======
	public DocumentFilterDescriptorsProvider createFiltersProvider(@NonNull final CreateFiltersProviderContext context)
	{
		final ImmutableList<DocumentFieldDescriptor> fields = context.getFields();
		final boolean isAutodetectDefaultDateFilter = context.isAutodetectDefaultDateFilter();

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		DocumentFilterDescriptor.Builder defaultFilterBuilder = null;
=======
		final DocumentFilterDescriptor.Builder defaultFilterBuilder = DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID_Default)
				.setSortNo(DocumentFilterDescriptorsConstants.SORT_NO_DEFAULT_FILTERS_GROUP)
				.setDisplayName(TranslatableStrings.adMessage(MSG_DefaultFilterName))
				.setFrequentUsed(false);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		final ArrayList<DocumentFilterDescriptor> inlineFilters = new ArrayList<>();
		for (final DocumentFieldDescriptor field : fieldsForDefaultFiltering)
		{
			final DocumentFilterParamDescriptor.Builder filterParam = createFilterParam(field);

			if (isAutodetectDefaultDateFilter && defaultDateFilter == null && filterParam.getWidgetType().isDateOrTime())
			{
				defaultDateFilter = DocumentFilterDescriptor.builder()
						.setFilterId(FILTER_ID_DefaultDate)
						.setSortNo(DocumentFilterDescriptorsConstants.SORT_NO_DEFAULT_DATE)
						.setFrequentUsed(true)
						.setDisplayName(filterParam.getDisplayName())
						.addParameter(filterParam)
						.build();
			}
			else if (field.getDefaultFilterInfo().isShowFilterInline())
			{
				inlineFilters.add(DocumentFilterDescriptor.builder()
						.setFilterId(INLINE_FILTER_ID_PREFIX + filterParam.getFieldName())
						.setSortNo(DocumentFilterDescriptorsConstants.SORT_NO_INLINE_FILTERS)
						.setFrequentUsed(true)
						.setInlineRenderMode(DocumentFilterInlineRenderMode.INLINE_PARAMETERS)
						.setDisplayName(filterParam.getDisplayName())
						.addParameter(filterParam)
						.build());
			}
			else
			{
<<<<<<< HEAD
				if (defaultFilterBuilder == null)
				{
					defaultFilterBuilder = DocumentFilterDescriptor.builder()
							.setFilterId(FILTER_ID_Default)
							.setSortNo(DocumentFilterDescriptorsConstants.SORT_NO_DEFAULT_FILTERS_GROUP)
							.setDisplayName(msgBL.getTranslatableMsgText(MSG_DefaultFilterName))
							.setFrequentUsed(false);
				}
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				defaultFilterBuilder.addParameter(filterParam);
			}
		}

		//
<<<<<<< HEAD
=======
		// Filters of included tabs
		for (final DocumentEntityDescriptor includedEntity : context.getIncludedEntities())
		{
			final DetailId detailId = Check.assumeNotNull(includedEntity.getDetailId(), "detailId shall be not null for {}", includedEntity);
			for (final DocumentFilterDescriptor includedFilter : includedEntity.getFilterDescriptors().getAll())
			{
				final String includedFilterId = includedFilter.getFilterId();
				if (FILTER_ID_Default.equals(includedFilterId))
				{
					for (final DocumentFilterParamDescriptor includedFilterParam : includedFilter.getParameters())
					{
						defaultFilterBuilder.addParameter(
								includedFilterParam.toBuilder()
										.parameterName(SqlDefaultDocumentFilterConverter.includedFilterParameterName(detailId, includedFilterId, includedFilterParam.getParameterName()))
										.fieldName(SqlDefaultDocumentFilterConverter.includedFilterParameterName(detailId, includedFilterId, includedFilterParam.getFieldName()))
										.mandatory(false)
						);
					}
				}
			}
		}

		//
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		// Facet filters
		final ArrayList<DocumentFilterDescriptor> facetFilters = new ArrayList<>();
		for (final DocumentFieldDescriptor field : fieldsForFacetFiltering)
		{
			final int sortNo = facetFilters.size() + 1;
			final DocumentFilterDescriptor facetFilter = createFacetFilter(field, sortNo);
			facetFilters.add(facetFilter);
		}

<<<<<<< HEAD
=======
		//
		// Assemble final filter descriptors list
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		final ArrayList<DocumentFilterDescriptor> descriptors = new ArrayList<>();
		if (defaultDateFilter != null)
		{
			descriptors.add(defaultDateFilter);
		}
<<<<<<< HEAD
		if (defaultFilterBuilder != null)
=======
		if (defaultFilterBuilder.hasParameters())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			descriptors.add(defaultFilterBuilder.build());
		}
		descriptors.addAll(inlineFilters);
		descriptors.addAll(facetFilters);

		return ImmutableDocumentFilterDescriptorsProvider.of(descriptors);
	}

	private static DocumentFilterParamDescriptor.Builder createFilterParam(final DocumentFieldDescriptor field)
	{
		final ITranslatableString displayName = field.getCaption();
		final String fieldName = field.getFieldName();
		final DocumentFieldWidgetType widgetType = extractFilterWidgetType(field);
		final DocumentFieldDefaultFilterDescriptor filteringInfo = field.getDefaultFilterInfo();

		final Operator operator;
		final DocumentFieldWidgetType widgetTypeEffective;
		final Optional<LookupDescriptor> lookupDescriptor;
		switch (filteringInfo.getOperator())
		{
			case BETWEEN:
			{
				widgetTypeEffective = widgetType;
				operator = Operator.BETWEEN;
				lookupDescriptor = field.getLookupDescriptorForFiltering();
				break;
			}
			case IS_NOT_NULL:
			{
				widgetTypeEffective = DocumentFieldWidgetType.YesNo;
				operator = Operator.NOT_NULL_IF_TRUE;
				lookupDescriptor = Optional.empty();
				break;
			}
			case EQUALS_OR_ILIKE:
			default:
			{
				widgetTypeEffective = widgetType;
				operator = widgetType.isText()
						? Operator.LIKE_I
						: Operator.EQUAL;
				lookupDescriptor = field.getLookupDescriptorForFiltering();
				break;
			}
		}

		return DocumentFilterParamDescriptor.builder()
<<<<<<< HEAD
				.setDisplayName(displayName)
				.setFieldName(fieldName)
				.setWidgetType(widgetTypeEffective)
				.setOperator(operator)
				.setLookupDescriptor(lookupDescriptor)
				.setMandatory(false)
				.setShowIncrementDecrementButtons(filteringInfo.isShowFilterIncrementButtons())
				.setAutoFilterInitialValue(filteringInfo.getAutoFilterInitialValue());
=======
				.displayName(displayName)
				.fieldName(fieldName)
				.widgetType(widgetTypeEffective)
				.operator(operator)
				.lookupDescriptor(lookupDescriptor)
				.mandatory(false)
				.showIncrementDecrementButtons(filteringInfo.isShowFilterIncrementButtons())
				.autoFilterInitialValue(filteringInfo.getAutoFilterInitialValue());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
		if (widgetType == DocumentFieldWidgetType.Switch)
		{
			// to filter we can't work with the Y/N switch widget, but need the Y/N/empty tickbox instead 
			return DocumentFieldWidgetType.YesNo;
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
				.setSortNo(DocumentFilterDescriptorsConstants.SORT_NO_FACETS_START + sortNo)
				.setFrequentUsed(true)
				.setParametersLayoutType(PanelLayoutType.Panel)
				.setDisplayName(field.getCaption())
				.setFacetFilter(true)
				.addParameter(DocumentFilterParamDescriptor.builder()
<<<<<<< HEAD
						.setFieldName(facetsLookupDescriptor.getFieldName())
						.setOperator(Operator.IN_ARRAY)
						.setDisplayName(field.getCaption())
						.setMandatory(true)
						.setWidgetType(DocumentFieldWidgetType.MultiValuesList)
						.setLookupDescriptor(facetsLookupDescriptor))
=======
						.fieldName(facetsLookupDescriptor.getFieldName())
						.operator(Operator.IN_ARRAY)
						.displayName(field.getCaption())
						.mandatory(true)
						.widgetType(DocumentFieldWidgetType.MultiValuesList)
						.lookupDescriptor(facetsLookupDescriptor))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
