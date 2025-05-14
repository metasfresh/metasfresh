package org.adempiere.util.lang.impl;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
public final class TableRecordReferenceSet implements Iterable<TableRecordReference>
{
	@NonNull
	public static TableRecordReferenceSet of(final Collection<TableRecordReference> recordRefs)
	{
		if (recordRefs.isEmpty())
		{
			return EMPTY;
		}

		return new TableRecordReferenceSet(recordRefs);
	}

	public static TableRecordReferenceSet of(@Nullable final TableRecordReference recordRef)
	{
		return recordRef != null ? new TableRecordReferenceSet(ImmutableSet.of(recordRef)) : EMPTY;
	}

	public static TableRecordReferenceSet of(final String tableName, final int recordId)
	{
		return of(TableRecordReference.of(tableName, recordId));
	}

	public static <T extends RepoIdAware> TableRecordReferenceSet of(final String tableName, final Collection<T> recordIds)
	{
		if (recordIds.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableSet<TableRecordReference> recordRefs = recordIds.stream()
				.map(recordId -> TableRecordReference.of(tableName, recordId))
				.collect(ImmutableSet.toImmutableSet());

		return of(recordRefs);
	}

	public static Collector<TableRecordReference, ?, TableRecordReferenceSet> collect()
	{
		final Supplier<Set<TableRecordReference>> supplier = LinkedHashSet::new;
		final BiConsumer<Set<TableRecordReference>, TableRecordReference> accumulator = Set::add;
		final BinaryOperator<Set<TableRecordReference>> combiner = (l, r) -> {
			l.addAll(r);
			return l;
		};
		final Function<Set<TableRecordReference>, TableRecordReferenceSet> finisher = TableRecordReferenceSet::of;
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static final TableRecordReferenceSet EMPTY = new TableRecordReferenceSet(ImmutableSet.of());

	@Getter
	private final ImmutableSet<TableRecordReference> recordRefs;

	private TableRecordReferenceSet(final Collection<TableRecordReference> recordRefs)
	{
		this.recordRefs = ImmutableSet.copyOf(recordRefs);
	}

	@Override
	@NonNull
	public Iterator<TableRecordReference> iterator()
	{
		return recordRefs.iterator();
	}

	public Stream<TableRecordReference> stream() {return recordRefs.stream();}

	public TableRecordReferenceSet filter(@NonNull final Predicate<TableRecordReference> filter)
	{
		if (recordRefs.isEmpty())
		{
			return this;
		}

		final ImmutableSet<TableRecordReference> recordRefsFiltered = recordRefs.stream().filter(filter).collect(ImmutableSet.toImmutableSet());
		if (recordRefs.size() == recordRefsFiltered.size())
		{
			return this; // nothing filtered
		}

		return of(recordRefsFiltered);
	}

	public boolean isEmpty()
	{
		return recordRefs.isEmpty();
	}

	public boolean containsRecordIds(@NonNull final String tableName, @NonNull final Collection<Integer> recordIds)
	{
		if (recordIds.isEmpty())
		{
			return false;
		}

		return matches(recordRef -> recordRef.getTableName().equals(tableName) && recordIds.contains(recordRef.getRecord_ID()));
	}

	public boolean matches(@NonNull final Predicate<TableRecordReference> predicate)
	{
		if (recordRefs.isEmpty())
		{
			return false;
		}

		return recordRefs.stream().anyMatch(predicate);
	}

	public boolean matchesTableName(@NonNull final String tableName)
	{
		return matches(recordRef -> tableName.equals(recordRef.getTableName()));
	}

	public Stream<TableRecordReference> streamByTableName(@NonNull final String tableName)
	{
		return recordRefs.stream().filter(recordRef -> tableName.equals(recordRef.getTableName()));
	}

	public ListMultimap<AdTableId, Integer> extractTableId2RecordIds()
	{
		final ImmutableListMultimap<AdTableId, TableRecordReference> tableName2References = Multimaps.index(recordRefs, TableRecordReference::getAdTableId);
		return Multimaps.transformValues(tableName2References, TableRecordReference::getRecord_ID);
	}

	public <T extends RepoIdAware> Stream<T> streamIds(@NonNull final String tableName, @NonNull final IntFunction<T> idMapper)
	{
		return streamByTableName(tableName)
				.mapToInt(TableRecordReference::getRecord_ID)
				.mapToObj(idMapper);
	}

	public String getSingleTableName()
	{
		final ImmutableSet<String> tableNames = recordRefs.stream()
				.map(TableRecordReference::getTableName)
				.collect(ImmutableSet.toImmutableSet());
		if (tableNames.isEmpty())
		{
			throw new AdempiereException("No tablename");
		}
		else if (tableNames.size() == 1)
		{
			return tableNames.iterator().next();
		}
		else
		{
			throw new AdempiereException("More than one tablename found: " + tableNames);
		}
	}

	public Set<Integer> toIntSet()
	{
		// just to make sure that our records are from a single table
		getSingleTableName();

		return recordRefs.stream()
				.map(TableRecordReference::getRecord_ID)
				.collect(ImmutableSet.toImmutableSet());
	}

	public int size()
	{
		return recordRefs.size();
	}

	@NonNull
	public AdTableId getSingleTableId()
	{
		final ImmutableSet<AdTableId> tableIds = getTableIds();

		if (tableIds.isEmpty())
		{
			throw new AdempiereException("No AD_Table_ID");
		}
		else if (tableIds.size() == 1)
		{
			return tableIds.iterator().next();
		}
		else
		{
			throw new AdempiereException("More than one AD_Table_ID found: " + tableIds);
		}
	}

	public void assertSingleTableName()
	{
		final ImmutableSet<AdTableId> tableIds = getTableIds();

		if (tableIds.isEmpty())
		{
			throw new AdempiereException("No AD_Table_ID");
		}
		else if (tableIds.size() != 1)
		{
			throw new AdempiereException("More than one AD_Table_ID found: " + tableIds);
		}
	}

	public Stream<TableRecordReference> streamReferences()
	{
		return recordRefs.stream();
	}

	@NonNull
	private ImmutableSet<AdTableId> getTableIds()
	{
		return recordRefs.stream()
				.map(TableRecordReference::getAD_Table_ID)
				.map(AdTableId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
