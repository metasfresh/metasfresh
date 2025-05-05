package de.metas.ui.web.window.datatypes;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import de.metas.process.SelectionSize;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.concurrent.Immutable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * {@link DocumentId}s selection.
 * <p>
 * Basically consists of a set of {@link DocumentId}s but it all has the {@link #isAll()} flag.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Immutable
@ToString
@EqualsAndHashCode
public final class DocumentIdsSelection
{
	public static DocumentIdsSelection of(final Collection<DocumentId> documentIds)
	{
		if (documentIds == null || documentIds.isEmpty())
		{
			return EMPTY;
		}

		return new DocumentIdsSelection(false, ImmutableSet.copyOf(documentIds));
	}

	public static DocumentIdsSelection fromNullable(final DocumentId documentId)
	{
		return documentId != null ? new DocumentIdsSelection(false, ImmutableSet.of(documentId)) : EMPTY;
	}

	public static DocumentIdsSelection ofStringSet(final Collection<String> stringDocumentIds)
	{
		if (stringDocumentIds == null || stringDocumentIds.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableSet.Builder<DocumentId> documentIdsBuilder = ImmutableSet.builder();
		for (final String documentIdStr : stringDocumentIds)
		{
			if (ALL_String.equals(documentIdStr))
			{
				return ALL;
			}

			documentIdsBuilder.add(DocumentId.of(documentIdStr));
		}

		final ImmutableSet<DocumentId> documentIds = documentIdsBuilder.build();
		if (documentIds.isEmpty())
		{
			return EMPTY;
		}
		return new DocumentIdsSelection(false, documentIds);
	}

	public static DocumentIdsSelection ofIntSet(final Collection<Integer> intDocumentIds)
	{
		if (intDocumentIds == null || intDocumentIds.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableSet<DocumentId> documentIds = intDocumentIds
				.stream()
				.map(DocumentId::of)
				.collect(ImmutableSet.toImmutableSet());
		return new DocumentIdsSelection(false, documentIds);
	}

	public static DocumentIdsSelection ofCommaSeparatedString(final String string)
	{
		if (string == null || string.trim().isEmpty())
		{
			return DocumentIdsSelection.EMPTY;
		}

		if (ALL_String.equalsIgnoreCase(string))
		{
			return ALL;
		}

		final List<String> stringDocumentIds = SPLITTER_DocumentIds.splitToList(string);
		return ofStringSet(stringDocumentIds);
	}

	public static Collector<DocumentId, ?, DocumentIdsSelection> toDocumentIdsSelection()
	{
		final Supplier<Set<DocumentId>> supplier = LinkedHashSet::new;
		final BiConsumer<Set<DocumentId>, DocumentId> accumulator = Set::add;
		final BinaryOperator<Set<DocumentId>> combiner = (l, r) -> {
			l.addAll(r);
			return l;
		};
		final Function<Set<DocumentId>, DocumentIdsSelection> finisher = DocumentIdsSelection::of;
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static final DocumentIdsSelection EMPTY = new DocumentIdsSelection(false, ImmutableSet.of());

	/**
	 * Use this constant to get "all", but note that most methods implemented in this class, such as {@link #stream()}, {@link #forEach(Consumer)} are not supported.
	 * See issue <a href="https://github.com/metasfresh/metasfresh-webui-api/issues/509">#509</a> for more details.
	 */
	public static final DocumentIdsSelection ALL = new DocumentIdsSelection(true, ImmutableSet.of());
	private static final String ALL_String = "all";
	private static final ImmutableSet<String> ALL_StringSet = ImmutableSet.of(ALL_String);

	private static final Splitter SPLITTER_DocumentIds = Splitter.on(",").trimResults().omitEmptyStrings();

	private final boolean all;
	private final ImmutableSet<DocumentId> documentIds;

	private DocumentIdsSelection(final boolean all, final ImmutableSet<DocumentId> documentIds)
	{
		this.all = all;
		this.documentIds = documentIds;
	}

	public String toCommaSeparatedString()
	{
		if (all)
		{
			return ALL_String;
		}

		return documentIds.stream()
				.map(DocumentId::toJson)
				.collect(Collectors.joining(","));
	}

	public boolean isSingleDocumentId()
	{
		return !all && documentIds.size() == 1;
	}

	public boolean isMoreThanOneDocumentId()
	{
		return all || documentIds.size() > 1;
	}

	public DocumentId getSingleDocumentId()
	{
		if (!isSingleDocumentId())
		{
			throw new IllegalStateException("Not a single documentId selection: " + this);
		}
		return documentIds.iterator().next();
	}

	public boolean isEmpty()
	{
		return this == EMPTY;
	}

	public boolean isAll()
	{
		return this == ALL;
	}

	private void assertNotAll()
	{
		if (all)
		{
			throw new IllegalStateException("method not supported for ALL selection");
		}
	}

	public Stream<DocumentId> stream()
	{
		assertNotAll();
		return documentIds.stream();
	}

	public void forEach(final Consumer<DocumentId> action)
	{
		assertNotAll();
		documentIds.forEach(action);
	}

	public int size()
	{
		assertNotAll();
		return documentIds.size();
	}

	public boolean contains(final DocumentId documentId)
	{
		return all || documentIds.contains(documentId);
	}

	public Set<DocumentId> toSet()
	{
		assertNotAll();
		return documentIds;
	}

	public <T> ImmutableSet<T> toSet(@NonNull final Function<DocumentId, T> mapper)
	{
		assertNotAll();
		if (documentIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		return documentIds.stream().map(mapper).collect(ImmutableSet.toImmutableSet());
	}

	public Set<Integer> toIntSet()
	{
		return toSet(DocumentId::toInt);
	}

	public <ID extends RepoIdAware> ImmutableSet<ID> toIds(@NonNull final Function<Integer, ID> idMapper)
	{
		return toSet(idMapper.compose(DocumentId::toInt));
	}

	public Set<String> toJsonSet()
	{
		if (all)
		{
			return ALL_StringSet;
		}

		return toSet(DocumentId::toJson);
	}

	public SelectionSize toSelectionSize()
	{
		if (isAll())
		{
			return SelectionSize.ofAll();
		}
		return SelectionSize.ofSize(size());
	}

	public DocumentIdsSelection addAll(@NonNull final DocumentIdsSelection documentIdsSelection)
	{
		if (this.isEmpty())
		{
			return documentIdsSelection;
		}
		else if (documentIdsSelection.isEmpty())
		{
			return this;
		}

		if (this.all)
		{
			return this;
		}
		else if (documentIdsSelection.all)
		{
			return documentIdsSelection;
		}

		final ImmutableSet<DocumentId> combinedIds = Stream.concat(this.stream(), documentIdsSelection.stream()).collect(ImmutableSet.toImmutableSet());
		final DocumentIdsSelection result = DocumentIdsSelection.of(combinedIds);

		if (this.equals(result))
		{
			return this;
		}
		else if (documentIdsSelection.equals(result))
		{
			return documentIdsSelection;
		}
		else
		{
			return result;
		}
	}
}
