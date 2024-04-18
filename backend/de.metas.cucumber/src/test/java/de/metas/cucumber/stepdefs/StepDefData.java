/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class StepDefData<T>
{
	protected final Logger logger = LogManager.getLogger(getClass());
	private final HashMap<StepDefDataIdentifier, RecordDataItem<T>> records = new HashMap<>();

	private final Class<T> clazz;

	/**
	 * @param clazz used if this stepdef is about model classes. In that case the record's {@link TableRecordReference} is stored, and the given clazz is then used when the record is loaded again.
	 */
	public StepDefData(@Nullable final Class<T> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.addValue(clazz != null ? clazz.getSimpleName() : null)
				.toString();
	}

	public void put(
			@NonNull final String identifier,
			@NonNull final T record)
	{
		put(StepDefDataIdentifier.ofString(identifier), record);
	}

	public void put(
			@NonNull final StepDefDataIdentifier identifier,
			@NonNull final T record)
	{
		final RecordDataItem<T> recordDataItem = newRecordDataItem(record);

		assertNotAlreadyMappedToOtherIdentifier(identifier, record);

		final RecordDataItem<T> oldRecord = records.put(identifier, recordDataItem);
		assertThat(oldRecord)
				.as("An identifier may be used just once, but %s was already used with %s", identifier, oldRecord)
				.isNull();

		logger.info("put: {}={}", identifier, record);
	}

	private void assertNotAlreadyMappedToOtherIdentifier(final @NonNull StepDefDataIdentifier identifier, final @NonNull T record)
	{
		if (!(this instanceof StepDefDataGetIdAware)) {return;}

		//noinspection unchecked
		final StepDefDataGetIdAware<RepoIdAware, T> thisIdAware = (StepDefDataGetIdAware<RepoIdAware, T>)this;

		final RepoIdAware id = thisIdAware.extractIdFromRecord(record);
		if (thisIdAware.isAllowDuplicateRecordsForSameIdentifier(id))
		{
			return;
		}

		final StepDefDataIdentifier otherIdentifier = thisIdAware.getFirstIdentifierById(id, identifier).orElse(null);
		if (otherIdentifier != null)
		{
			throw new AdempiereException("Cannot map `" + record + "` with ID `" + id + "` to identifier `" + identifier + "` because it was already mapped to `" + otherIdentifier + "`");
		}
	}

	public void putOrReplace(
			@NonNull final String identifier,
			@NonNull final T record)
	{
		putOrReplace(StepDefDataIdentifier.ofString(identifier), record);
	}

	public void putOrReplace(
			@NonNull final StepDefDataIdentifier identifier,
			@NonNull final T record)
	{
		final RecordDataItem<T> oldRecord = records.get(identifier);

		if (oldRecord == null)
		{
			put(identifier, record);
		}
		else
		{
			assertNotAlreadyMappedToOtherIdentifier(identifier, record);
			records.replace(identifier, newRecordDataItem(record));
			logger.info("replace: {}={}", identifier, record);
		}
	}

	public void putAll(
			@NonNull final Map<String, T> map)
	{
		for (final Map.Entry<String, T> entry : map.entrySet())
		{
			putOrReplace(entry.getKey(), entry.getValue());
		}
	}

	public void putIfMissing(
			@NonNull final StepDefDataIdentifier identifier,
			@NonNull final T record)
	{
		final RecordDataItem<T> oldRecord = records.get(identifier);

		if (oldRecord != null)
		{
			return;
		}

		put(identifier, record);
	}

	@NonNull
	public T get(@NonNull final String identifier)
	{
		return get(StepDefDataIdentifier.ofString(identifier));
	}

	@NonNull
	public T get(@NonNull final StepDefDataIdentifier identifier)
	{
		final T record = getRecordDataItem(identifier).getRecord();

		if (record instanceof PO)
		{
			InterfaceWrapperHelper.refresh(record);
		}

		return record;
	}

	@NonNull
	public <ET extends T> ET get(@NonNull final String identifier, @NonNull final Class<ET> type)
	{
		return InterfaceWrapperHelper.create(get(identifier), type);
	}

	@NonNull
	public RecordDataItem<T> getRecordDataItem(@NonNull final String identifier)
	{
		return getRecordDataItem(StepDefDataIdentifier.ofString(identifier));
	}

	@NonNull
	public RecordDataItem<T> getRecordDataItem(@NonNull final StepDefDataIdentifier identifier)
	{
		final RecordDataItem<T> recordDataItem = records.get(identifier);
		assertThat(recordDataItem)
				.as(() -> "Missing item for identifier `" + identifier + "` in " + this + ". Available identifiers are: " + records.keySet())
				.isNotNull();

		return recordDataItem;
	}

	@NonNull
	public Optional<T> getOptional(@NonNull final String identifier)
	{
		return getOptional(StepDefDataIdentifier.ofString(identifier));
	}

	@NonNull
	public Optional<T> getOptional(@NonNull final StepDefDataIdentifier identifier)
	{
		return Optional.ofNullable(records.get(identifier)).map(RecordDataItem::getRecord);
	}

	public ImmutableSet<StepDefDataIdentifier> getIdentifiers()
	{
		return records.keySet().stream().collect(ImmutableSet.toImmutableSet());
	}

	public ImmutableList<T> getRecords()
	{
		return streamRecords().collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public Stream<T> streamRecords()
	{
		return records.values().stream().map(RecordDataItem::getRecord);
	}

	/**
	 * @param record the item to store.
	 *               In case of a model interface, we just store its ID and class, to avoid problems with DB-transactions or other sorts of leaks.
	 */
	@NotNull
	private StepDefData.RecordDataItem<T> newRecordDataItem(final @NotNull T record)
	{
		if (InterfaceWrapperHelper.isModelInterface(record.getClass()) && clazz != null)
		{
			final Instant updated = InterfaceWrapperHelper
					.getValueOptional(record, InterfaceWrapperHelper.COLUMNNAME_Updated)
					.map(TimeUtil::asInstant)
					.orElse(Instant.MIN);

			// just store ID and table name, to avoid any leaks
			final TableRecordReference tableRecordReference = TableRecordReference.of(InterfaceWrapperHelper.getModelTableName(record), InterfaceWrapperHelper.getId(record));

			return new RecordDataItem<>(null,
					tableRecordReference,
					clazz,
					Instant.now(),
					updated);

		}
		return new RecordDataItem<>(record, null, null, Instant.now(), Instant.MIN);
	}

	@Value
	public static class RecordDataItem<T>
	{
		@Nullable
		T record;

		@Nullable
		TableRecordReference tableRecordReference;

		@Nullable
		Class<T> tableRecordReferenceClazz;

		@NonNull
		Instant recordAdded;
		@NonNull

		Instant recordUpdated;

		public T getRecord()
		{
			if (record != null)
			{
				return record;
			}

			if (tableRecordReference != null)
			{
				try
				{
					return tableRecordReference.getModel(tableRecordReferenceClazz);
				}
				catch (final RuntimeException e)
				{
					throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
							.setParameter("recordDataItem", this);
				}
			}

			throw new AdempiereException("Cannot get the record of " + this);
		}

	}
}
