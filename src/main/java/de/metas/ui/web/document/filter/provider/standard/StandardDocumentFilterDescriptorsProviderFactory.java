package de.metas.ui.web.document.filter.provider.standard;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdTabId;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
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
	private static final String FILTER_ID_Default = "default";

	private static final String FILTER_ID_DefaultDate = "default-date";
	private static final String MSG_DefaultFilterName = "default";

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	public StandardDocumentFilterDescriptorsProviderFactory()
	{
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
		final DocumentFilterDescriptor.Builder defaultFilter = DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID_Default)
				.setDisplayName(msgBL.getTranslatableMsgText(MSG_DefaultFilterName))
				.setFrequentUsed(false);
		final DocumentFilterDescriptor.Builder defaultDateFilter = DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID_DefaultDate)
				.setFrequentUsed(true);

		final List<DocumentFieldDescriptor> filteringFields = fields.stream()
				.filter(DocumentFieldDescriptor::isDefaultFilterField)
				.sorted(Ordering.natural().onResultOf(field -> field.getDefaultFilterInfo().getSeqNo()))
				.collect(ImmutableList.toImmutableList());

		for (final DocumentFieldDescriptor field : filteringFields)
		{
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

}
