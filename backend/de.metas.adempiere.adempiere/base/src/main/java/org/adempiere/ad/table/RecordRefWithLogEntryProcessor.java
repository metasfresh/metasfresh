package org.adempiere.ad.table;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.LogEntriesRepository.LogEntriesQuery;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Location;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Builder
@Value
public class RecordRefWithLogEntryProcessor
{
	@NonNull
	Predicate<RecordRefWithLogEntry> referencesLocationTablePredicate;

	@NonNull
	BiFunction<TableRecordReference, ImmutableList<I_C_Location>, ImmutableList<RecordRefWithLogEntry>> derivedLocationEntriesProvider;

	@NonNull
	Boolean changeLogActiveForLocationTable;

	private static final Logger logger = LogManager.getLogger(RecordRefWithLogEntryProcessor.class);

	private static final Predicate<RecordRefWithLogEntry> LOCATION_ID_OLD_VALUE_NOT_NULL = recordRefWithLogEntry -> {
		if (recordRefWithLogEntry.getRecordChangeLogEntry().getValueOld() != null)
		{
			return true; // all is well
		}
		try (final MDCCloseable valueMDC = TableRecordMDC.putTableRecordReference(recordRefWithLogEntry.getRecordRef()))
		{
			logger.debug("Found a recordChangeLogEntry for C_Location_ID that has ValueOld=NULL; -> excluding it; recordChangeLogEntry={}", recordRefWithLogEntry.getRecordChangeLogEntry());
			return false;
		}
	};

	@VisibleForTesting
	public ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> processRecordRefsWithLogEntries(
			@NonNull final LogEntriesQuery logEntriesQuery,
			@NonNull final List<RecordRefWithLogEntry> recordRefWithLogEntries)
	{
		final Map<TableRecordReference, TreeSet<RecordChangeLogEntry>> intermediateResult = new HashMap<>();

		if (logEntriesQuery.isFollowLocationIdChanges() && changeLogActiveForLocationTable)
		{
			// create two groups: those change log entries that do and those that do not reference C_Location
			final Map<Boolean, List<RecordRefWithLogEntry>> partition = recordRefWithLogEntries.stream()
					.collect(Collectors.partitioningBy(referencesLocationTablePredicate));

			final List<RecordRefWithLogEntry> entriesWithoutLocationId = partition.get(false);
			addAllToIntermediateResult(entriesWithoutLocationId, intermediateResult);

			final List<RecordRefWithLogEntry> entriesWithLocationId = partition.get(true);
			// instead of adding entriesWithLocationId, we derive C_Location-ChangeLog-Entries and add those
			final ImmutableListMultimap<TableRecordReference, LocationId> locationIds = extractLocationIds(entriesWithLocationId);
			final ImmutableListMultimap<TableRecordReference, I_C_Location> ref2LocationRecords = extractLocationRecords(locationIds);

			for (final TableRecordReference recordRef : ref2LocationRecords.keySet())
			{
				final List<RecordRefWithLogEntry> derivedLocationEntries = derivedLocationEntriesProvider.apply(recordRef, ref2LocationRecords.get(recordRef));
				addAllToIntermediateResult(derivedLocationEntries, intermediateResult);
			}
		}
		else
		{
			addAllToIntermediateResult(recordRefWithLogEntries, intermediateResult);
		}

		final ImmutableListMultimap.Builder<TableRecordReference, RecordChangeLogEntry> result = ImmutableListMultimap.builder();
		for (final Entry<TableRecordReference, TreeSet<RecordChangeLogEntry>> entry : intermediateResult.entrySet())
		{
			result.putAll(entry.getKey(), entry.getValue());
		}
		return result.build();
	}

	private void addAllToIntermediateResult(
			final List<RecordRefWithLogEntry> recordRefWithLogEntries,
			final Map<TableRecordReference, TreeSet<RecordChangeLogEntry>> intermediateResult)
	{
		for (final RecordRefWithLogEntry recordRefWithLogEntry : recordRefWithLogEntries)
		{
			final TableRecordReference recordRef = recordRefWithLogEntry.getRecordRef();
			final RecordChangeLogEntry logEntry = recordRefWithLogEntry.getRecordChangeLogEntry();

			final TreeSet<RecordChangeLogEntry> entriesForRecordRef = intermediateResult.computeIfAbsent(recordRef, k -> createNewTreeSet());
			entriesForRecordRef.add(logEntry);
		}
	}

	private TreeSet<RecordChangeLogEntry> createNewTreeSet()
	{
		final Comparator<RecordChangeLogEntry> comparator = Comparator
				.comparing(RecordChangeLogEntry::getChangedTimestamp)
				.thenComparing(RecordChangeLogEntry::getColumnName)
				.thenComparing(r -> Objects.hashCode(r.getValueNew())) // changeTimestamp and columnName should always differ, but let's make unit-testing a bit easier
				.thenComparing(r -> Objects.hashCode(r.getValueOld()));
		return new TreeSet<RecordChangeLogEntry>(comparator);
	}

	private ImmutableListMultimap<TableRecordReference, LocationId> extractLocationIds(
			@NonNull final List<RecordRefWithLogEntry> entriesWithLocationId)
	{
		final ImmutableList<RecordRefWithLogEntry> entriesWithLocationIdToUse = entriesWithLocationId.stream()
				.collect(ImmutableList.toImmutableList());

		final ImmutableListMultimap.Builder<TableRecordReference, LocationId> recordRef2LocationIds = ImmutableListMultimap.builder();
		if (entriesWithLocationIdToUse.isEmpty())
		{
			return recordRef2LocationIds.build();
		}

		// We need have the first changelog's *old* value for each C_Location-referencing column of every TableRecordRef!
		final HashSet<LocationId> alreadySeen = new HashSet<>();
		final ImmutableListMultimap<ArrayKey, RecordRefWithLogEntry> index = Multimaps.index(
				entriesWithLocationIdToUse,
				entry -> ArrayKey.of(entry.getRecordRef(), entry.getRecordChangeLogEntry().getColumnName()));
		for (final Collection<RecordRefWithLogEntry> recordRefWithLogEntrys : index.asMap().values())
		{
			final Comparator<RecordRefWithLogEntry> comparator = Comparator
					.comparing(e -> e.getRecordChangeLogEntry().getChangedTimestamp());
			final Optional<RecordRefWithLogEntry> firstRecordRefWithLogEntry = recordRefWithLogEntrys.stream()
					.filter(LOCATION_ID_OLD_VALUE_NOT_NULL)
					.min(comparator);
			if (!firstRecordRefWithLogEntry.isPresent())
			{
				continue;
			}
			final LocationId locationId = extractValueOldAsLocationId(firstRecordRefWithLogEntry.get()).orElseThrow(() -> new AdempiereException("Can't happend because we filtered it out"));
			alreadySeen.add(locationId);
			recordRef2LocationIds.put(firstRecordRefWithLogEntry.get().getRecordRef(), locationId);
		}

		// for all change logs including the first ones, we extract their new values
		// note that the order doesn't really matter; we will make sure the correct order later, after we got the actual C_Location records
		boolean useValueOldOfNextEntry = false;
		for (final RecordRefWithLogEntry recordRefWithLogEntry : entriesWithLocationIdToUse)
		{
			try (final MDCCloseable mdc = TableRecordMDC.putTableRecordReference(recordRefWithLogEntry.getRecordRef()))
			{
				final Optional<LocationId> newValueLocationId = extractValueNewAsLocationId(recordRefWithLogEntry);
				if (!newValueLocationId.isPresent())
				{
					useValueOldOfNextEntry = true;
					continue;
				}

				if (useValueOldOfNextEntry)
				{
					Optional<LocationId> oldValueLocationId = extractValueOldAsLocationId(recordRefWithLogEntry);
					if (!oldValueLocationId.isPresent())
					{
						continue;
					}
					if (alreadySeen.add(oldValueLocationId.get()))
					{
						recordRef2LocationIds.put(recordRefWithLogEntry.getRecordRef(), oldValueLocationId.get());
					}
					useValueOldOfNextEntry = false;
				}

				if (alreadySeen.add(newValueLocationId.get()))
				{
					recordRef2LocationIds.put(recordRefWithLogEntry.getRecordRef(), newValueLocationId.get());
				}
			}
		}
		return recordRef2LocationIds.build();
	}

	private Optional<LocationId> extractValueOldAsLocationId(@NonNull final RecordRefWithLogEntry recordRefWithLogEntry)
	{
		final RecordChangeLogEntry logEntry = recordRefWithLogEntry.getRecordChangeLogEntry();

		final Object valueOld = logEntry.getValueOld();
		if (valueOld == null) // might be a bug
		{
			logger.debug("logEntry has valueOld=null; -> return Optional.empty(); logEntry={}", logEntry);
			return Optional.empty();
		}

		if (!(valueOld instanceof KeyNamePair)) // might be a bug
		{
			throw new AdempiereException("The RecordChangeLogEntry's column references C_Location, so its valueOld needs to be KeyNamePair")
					.appendParametersToMessage()
					.setParameter("valueOld", valueOld)
					.setParameter("recordRefWithLogEntry", recordRefWithLogEntry);
		}

		// because C_Location is not mutated by the system, we can also safely assume that no locationId will show up more than once.
		final LocationId locationId = LocationId.ofRepoId(((KeyNamePair)valueOld).getKey());
		return Optional.of(locationId);
	}

	private Optional<LocationId> extractValueNewAsLocationId(@NonNull final RecordRefWithLogEntry recordRefWithLogEntry)
	{
		final RecordChangeLogEntry logEntry = recordRefWithLogEntry.getRecordChangeLogEntry();

		final Object valueNew = logEntry.getValueNew();
		if (valueNew == null)
		{
			logger.debug("logEntry has valueNew=null; -> return Optional.empty(); logEntry={}", logEntry);
			return Optional.empty();
		}

		if (!(valueNew instanceof KeyNamePair))
		{
			throw new AdempiereException("The RecordChangeLogEntry's column references C_Location, so its valueNew needs to be KeyNamePair")
					.appendParametersToMessage()
					.setParameter("valueNew", valueNew)
					.setParameter("recordRefWithLogEntry", recordRefWithLogEntry);
		}

		// because C_Location is not mutated by the system, we can also safely assume that no locationId will show up more than once.
		final LocationId locationId = LocationId.ofRepoId(((KeyNamePair)valueNew).getKey());
		return Optional.of(locationId);
	}

	/** @return location table record references with {@code C_Location} records */
	private ImmutableListMultimap<TableRecordReference, I_C_Location> extractLocationRecords(
			@NonNull final ImmutableListMultimap<TableRecordReference, LocationId> locationIds)
	{
		final ImmutableListMultimap.Builder<TableRecordReference, I_C_Location> recordRef2LocationRecords = ImmutableListMultimap.builder();
		if (locationIds.isEmpty())
		{
			return recordRef2LocationRecords.build(); // don't bother the database
		}

		final ImmutableList<LocationId> allLocationIds = locationIds.entries().stream().map(Entry::getValue).collect(ImmutableList.toImmutableList());

		final List<I_C_Location> locationRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Location.class)
				// .addOnlyActiveRecordsFilter() we also deal with records' "inactive" flag, at least in the REST-API; therefore we here also need to load inactive C_Locations
				.addInArrayFilter(I_C_Location.COLUMN_C_Location_ID, allLocationIds)
				.create()
				.list();
		final ImmutableMap<Integer, I_C_Location> repoId2LocationRecord = Maps.uniqueIndex(locationRecords, I_C_Location::getC_Location_ID);

		for (final Entry<TableRecordReference, LocationId> recordRefAndLocationId : locationIds.entries())
		{
			final I_C_Location locationRecord = repoId2LocationRecord.get(recordRefAndLocationId.getValue().getRepoId());
			recordRef2LocationRecords.put(recordRefAndLocationId.getKey(), locationRecord);
		}
		return recordRef2LocationRecords.build();
	}

	@Value
	static class RecordRefWithLogEntry
	{
		@NonNull
		TableRecordReference recordRef;
		@NonNull
		RecordChangeLogEntry recordChangeLogEntry;
	}
}
