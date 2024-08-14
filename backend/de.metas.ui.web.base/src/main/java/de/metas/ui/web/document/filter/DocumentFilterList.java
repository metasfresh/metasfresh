package de.metas.ui.web.document.filter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

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

@EqualsAndHashCode
@ToString
public class DocumentFilterList
{
	@NonNull
	public static DocumentFilterList ofList(@Nullable final Collection<DocumentFilter> list)
	{
		return list != null && !list.isEmpty()
				? new DocumentFilterList(Maps.uniqueIndex(list, DocumentFilter::getFilterId))
				: EMPTY;
	}

	private static DocumentFilterList ofMap(@NonNull final Map<String, DocumentFilter> filtersById)
	{
		return !filtersById.isEmpty()
				? new DocumentFilterList(ImmutableMap.copyOf(filtersById))
				: EMPTY;
	}

	public static DocumentFilterList of(@NonNull final DocumentFilter filter)
	{
		return ofList(ImmutableList.of(filter));
	}

	@NonNull
	public static DocumentFilterList of(@NonNull final DocumentFilter... filters)
	{
		return ofList(Arrays.asList(filters));
	}

	public static Collector<DocumentFilter, ?, DocumentFilterList> toDocumentFilterList()
	{
		return GuavaCollectors.collectUsingListAccumulator(DocumentFilterList::ofList);
	}

	public static final DocumentFilterList EMPTY = new DocumentFilterList(ImmutableMap.of());

	private final ImmutableMap<String, DocumentFilter> filtersById;

	private DocumentFilterList(@NonNull final ImmutableMap<String, DocumentFilter> filtersById)
	{
		this.filtersById = filtersById;
	}

	public static boolean equals(final DocumentFilterList list1, final DocumentFilterList list2)
	{
		return Objects.equals(list1, list2);
	}

	public boolean isEmpty()
	{
		return filtersById.isEmpty();
	}

	public ImmutableList<DocumentFilter> toList()
	{
		return ImmutableList.copyOf(filtersById.values());
	}

	public Stream<DocumentFilter> stream()
	{
		return filtersById.values().stream();
	}

	public DocumentFilterList mergeWith(@NonNull final DocumentFilterList other)
	{
		if (isEmpty())
		{
			return other;
		}
		else if (other.isEmpty())
		{
			return this;
		}
		else
		{
			final LinkedHashMap<String, DocumentFilter> filtersByIdNew = new LinkedHashMap<>(this.filtersById);
			filtersByIdNew.putAll(other.filtersById);

			return ofMap(filtersByIdNew);
		}
	}

	public DocumentFilterList mergeWith(@NonNull final DocumentFilter filter)
	{
		if (isEmpty())
		{
			return of(filter);
		}
		else
		{
			final LinkedHashMap<String, DocumentFilter> filtersByIdNew = new LinkedHashMap<>(this.filtersById);
			filtersByIdNew.put(filter.getFilterId(), filter);

			return ofMap(filtersByIdNew);
		}
	}

	public DocumentFilterList retainOnlyNonFacetFilters()
	{
		return filtering(filter -> !filter.isFacetFilter());
	}

	public DocumentFilterList retainOnlyFacetFilters()
	{
		return filtering(DocumentFilter::isFacetFilter);
	}

	private DocumentFilterList filtering(final Predicate<DocumentFilter> predicate)
	{
		if (isEmpty())
		{
			return this;
		}

		final ImmutableMap<String, DocumentFilter> newFiltersById = filtersById.entrySet()
				.stream()
				.filter(entry -> predicate.test(entry.getValue()))
				.collect(GuavaCollectors.toImmutableMap());

		if (newFiltersById.isEmpty())
		{
			return EMPTY;
		}
		else if (newFiltersById.size() == filtersById.size())
		{
			return this;
		}
		else
		{
			return new DocumentFilterList(newFiltersById);
		}

	}

	public Optional<DocumentFilter> getFilterById(@NonNull final String filterId)
	{
		final DocumentFilter filter = getFilterByIdOrNull(filterId);
		return Optional.ofNullable(filter);
	}

	public boolean containsFilterById(final String filterId)
	{
		return getFilterByIdOrNull(filterId) != null;
	}

	private DocumentFilter getFilterByIdOrNull(@NonNull final String filterId)
	{
		return filtersById.get(filterId);
	}

	public void forEach(@NonNull final Consumer<DocumentFilter> consumer)
	{
		filtersById.values().forEach(consumer);
	}

	@Nullable
	public String getParamValueAsString(final String filterId, final String parameterName)
	{
		final DocumentFilter filter = getFilterByIdOrNull(filterId);
		if (filter == null)
		{
			return null;
		}

		return filter.getParameterValueAsString(parameterName);
	}

	public int getParamValueAsInt(final String filterId, final String parameterName, final int defaultValue)
	{
		final DocumentFilter filter = getFilterByIdOrNull(filterId);
		if (filter == null)
		{
			return defaultValue;
		}

		return filter.getParameterValueAsInt(parameterName, defaultValue);
	}

	public boolean getParamValueAsBoolean(final String filterId, final String parameterName, final boolean defaultValue)
	{
		final DocumentFilter filter = getFilterByIdOrNull(filterId);
		if (filter == null)
		{
			return defaultValue;
		}

		return filter.getParameterValueAsBoolean(parameterName, defaultValue);
	}
}
