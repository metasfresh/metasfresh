package de.metas.ui.web.document.filter.provider.standard;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.view.DefaultView;
import de.metas.ui.web.view.IViewDataRepository;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.SimpleLookupDescriptorTemplate;
import de.metas.ui.web.window.model.lookup.IdsToFilter;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

final class FacetsFilterLookupDescriptor extends SimpleLookupDescriptorTemplate
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IViewsRepository viewsRepository;

	@Getter
	private final String filterId;
	@Getter
	private final String fieldName;
	private final DocumentFieldWidgetType fieldWidgetType;
	@Getter
	private final boolean numericKey;
	private final int maxFacetsToFetch2;
	private final LookupDescriptor fieldLookupDescriptor;

	@Builder
	private FacetsFilterLookupDescriptor(
			@NonNull final IViewsRepository viewsRepository,
			//
			@NonNull final String filterId,
			@NonNull final String fieldName,
			@NonNull final DocumentFieldWidgetType fieldWidgetType,
			final boolean numericKey,
			final int maxFacetsToFetch,
			@Nullable final LookupDescriptor fieldLookupDescriptor)
	{
		Check.assumeGreaterThanZero(maxFacetsToFetch, "maxFacetsToFetch");

		this.viewsRepository = viewsRepository;

		this.filterId = filterId;
		this.fieldName = fieldName;
		this.fieldWidgetType = fieldWidgetType;
		this.numericKey = numericKey;
		this.maxFacetsToFetch2 = maxFacetsToFetch;
		this.fieldLookupDescriptor = fieldLookupDescriptor;
	}

	@Override
	public Optional<String> getLookupTableName()
	{
		return fieldLookupDescriptor.getLookupDataSourceFetcher().getLookupTableName();
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return fieldLookupDescriptor.getDependsOnFieldNames();
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingById(final Object id)
	{
		final LookupDataSourceContext.Builder builder = fieldLookupDescriptor != null
				? fieldLookupDescriptor.getLookupDataSourceFetcher().newContextForFetchingById(id)
				: LookupDataSourceContext.builderWithoutTableName().putFilterById(IdsToFilter.ofSingleValue(id));

		return builder
				.requiresParameter(LookupDataSourceContext.PARAM_ViewId)
				.requiresParameter(LookupDataSourceContext.PARAM_ViewSize);
	}

	@Override
	public LookupValue retrieveLookupValueById(final @NonNull LookupDataSourceContext evalCtx)
	{
		return fieldLookupDescriptor.getLookupDataSourceFetcher().retrieveLookupValueById(evalCtx);
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingList()
	{
		final LookupDataSourceContext.Builder builder = fieldLookupDescriptor != null
				? fieldLookupDescriptor.getLookupDataSourceFetcher().newContextForFetchingList()
				: LookupDataSourceContext.builderWithoutTableName();

		return builder
				.requiresParameter(LookupDataSourceContext.PARAM_ViewId)
				.requiresParameter(LookupDataSourceContext.PARAM_ViewSize);
	}

	@Override
	public LookupValuesPage retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		final DefaultView view = getView(evalCtx);

		return view.getFacetFiltersCacheMap()
				.computeIfAbsent(filterId, () -> createFacetFilterViewCache(view))
				.pageByOffsetAndLimit(
						evalCtx.getOffset(0),
						evalCtx.getLimit(Integer.MAX_VALUE));
	}

	private FacetFilterViewCache createFacetFilterViewCache(final DefaultView view)
	{
		final IViewDataRepository viewDataRepository = view.getViewDataRepository();
		final ViewEvaluationCtx viewEvalCtx = view.getViewEvaluationCtx();
		final String selectionId = view.getDefaultSelectionBeforeFacetsFiltering().getSelectionId();

		final boolean isLimited;
		final int fetchLimit;
		if (this.maxFacetsToFetch2 > 0 && this.maxFacetsToFetch2 < Integer.MAX_VALUE)
		{
			isLimited = true;
			fetchLimit = this.maxFacetsToFetch2 + 1; // fetch one more to be able to determine if there are more values than we can fetch
		}
		else
		{
			isLimited = false;
			fetchLimit = Integer.MAX_VALUE;
		}

		List<Object> rawValues = viewDataRepository.retrieveFieldValues(
				viewEvalCtx,
				selectionId,
				fieldName,
				fetchLimit);

		boolean valuesAreOrdered = false;
		if (fieldWidgetType.isDateOrTime()
				|| fieldWidgetType.isNumeric()
				|| fieldWidgetType.isText())
		{
			// in case of date/time/numeric fields we shall order them by their value
			// and not alphabetically by their string representation
			rawValues = rawValues.stream()
					.sorted()
					.collect(ImmutableList.toImmutableList());
			valuesAreOrdered = true;
		}

		LookupValuesList lookupValues = rawValues.stream()
				.map(this::convertRawFieldValueToLookupValue)
				.filter(Objects::nonNull)
				.distinct()
				.collect(LookupValuesList.collect())
				.ordered(valuesAreOrdered);

		boolean hasMoreResults = false;
		if (isLimited && lookupValues.size() > this.maxFacetsToFetch2)
		{
			lookupValues = lookupValues.limit(this.maxFacetsToFetch2);
			hasMoreResults = true;
		}

		return FacetFilterViewCache.builder()
				.filterId(filterId)
				.availableValues(lookupValues)
				.hasMoreResults(hasMoreResults)
				.build();
	}

	private DefaultView getView(@NonNull final LookupDataSourceContext evalCtx)
	{
		final ViewId viewId = evalCtx.getViewId();
		return DefaultView.cast(viewsRepository.getView(viewId));
	}

	@Nullable
	private LookupValue convertRawFieldValueToLookupValue(final Object fieldValue)
	{
		if (fieldValue == null)
		{
			return null;
		}
		else if (fieldValue instanceof LookupValue)
		{
			return (LookupValue)fieldValue;
		}
		else if (fieldValue instanceof LocalDate)
		{
			final LocalDate date = (LocalDate)fieldValue;
			return StringLookupValue.of(
					Values.localDateToJson(date),
					TranslatableStrings.date(date));
		}
		else if (fieldValue instanceof Boolean)
		{
			final boolean booleanValue = StringUtils.toBoolean(fieldValue);
			return StringLookupValue.of(
					DisplayType.toBooleanString(booleanValue),
					msgBL.getTranslatableMsgText(booleanValue));
		}
		else if (fieldValue instanceof String)
		{
			final String stringValue = (String)fieldValue;
			return StringLookupValue.of(stringValue, stringValue);
		}
		else
		{
			throw new AdempiereException("Value not supported: " + fieldValue + " (" + fieldValue.getClass() + ")")
					.appendParametersToMessage()
					.setParameter("fieldName", fieldName);
		}
	}
}
