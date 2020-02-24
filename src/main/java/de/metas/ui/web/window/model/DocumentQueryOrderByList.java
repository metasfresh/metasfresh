package de.metas.ui.web.window.model;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentQueryOrderBy.FieldValueExtractor;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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
public class DocumentQueryOrderByList
{
	public static final DocumentQueryOrderByList ofList(@Nullable final List<DocumentQueryOrderBy> list)
	{
		return list != null && !list.isEmpty()
				? new DocumentQueryOrderByList(list)
				: EMPTY;
	}

	public static Collector<DocumentQueryOrderBy, ?, DocumentQueryOrderByList> toDocumentQueryOrderByList()
	{
		return GuavaCollectors.collectUsingListAccumulator(DocumentQueryOrderByList::ofList);
	}

	/**
	 * @param orderBysListStr Command separated field names. Use +/- prefix for ascending/descending. e.g. +C_BPartner_ID,-DateOrdered
	 */
	public static DocumentQueryOrderByList parse(final String orderBysListStr)
	{
		if (Check.isEmpty(orderBysListStr, true))
		{
			return EMPTY;
		}

		return Splitter.on(',')
				.trimResults()
				.omitEmptyStrings()
				.splitToList(orderBysListStr)
				.stream()
				.map(DocumentQueryOrderBy::parse)
				.collect(toDocumentQueryOrderByList());
	}

	public static final DocumentQueryOrderByList EMPTY = new DocumentQueryOrderByList(ImmutableList.of());

	private final ImmutableList<DocumentQueryOrderBy> list;

	private DocumentQueryOrderByList(@NonNull final List<DocumentQueryOrderBy> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	public ImmutableList<DocumentQueryOrderBy> toList()
	{
		return list;
	}

	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	public static boolean equals(final DocumentQueryOrderByList list1, final DocumentQueryOrderByList list2)
	{
		return Objects.equals(list1, list2);
	}

	public Stream<DocumentQueryOrderBy> stream()
	{
		return list.stream();
	}

	public void forEach(@NonNull final Consumer<DocumentQueryOrderBy> consumer)
	{
		list.forEach(consumer);
	}

	public <T extends IViewRow> Comparator<T> toComparator(
			@NonNull final FieldValueExtractor<T> fieldValueExtractor,
			@NonNull final JSONOptions jsonOpts)
	{

		// used in case orderBys is empty or whatever else goes wrong
		final Comparator<T> noopComparator = (o1, o2) -> 0;

		return stream()
				.map(orderBy -> orderBy.<T> asComparator(fieldValueExtractor, jsonOpts))
				.reduce(Comparator::thenComparing)
				.orElse(noopComparator);
	}

}
