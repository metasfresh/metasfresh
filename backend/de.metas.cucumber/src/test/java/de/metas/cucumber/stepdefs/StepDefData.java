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

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
<<<<<<< HEAD
import org.compiere.model.PO;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
=======
import org.codehaus.commons.nullanalysis.NotNull;
import org.compiere.model.PO;
import org.compiere.util.TimeUtil;
>>>>>>> 01acf328a21 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public abstract class StepDefData<T>
{
	private final Map<String, RecordDataItem<T>> records = new HashMap<>();

	private final Class<T> clazz;

	/**
	 * @param clazz used if this stepdef is about model classes. In that case the record's {@link TableRecordReference} is stored, and the given clazz is then used when the record is loaded again.
	 */
	public StepDefData(@Nullable final Class<T> clazz)
	{
		this.clazz = clazz;
	}

	public void put(
			@NonNull final String identifier,
			@NonNull final T productRecord)
	{
		final RecordDataItem<T> recordDataItem = createRecordDataItem(productRecord);

		final RecordDataItem<T> oldRecord = records.put(identifier, recordDataItem);
		assertThat(oldRecord)
				.as("An identifier may be used just once, but %s was already used with %s", identifier, oldRecord)
				.isNull();
	}

	public void putOrReplace(
			@NonNull final String identifier,
			@NonNull final T productRecord)
	{
		final RecordDataItem<T> oldRecord = records.get(identifier);

		if (oldRecord == null)
		{
			put(identifier, productRecord);
		}
		else
		{
			records.replace(identifier, createRecordDataItem(productRecord));
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
			@NonNull final String identifier,
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
		final T record = getRecordDataItem(identifier).getRecord();

		if (record instanceof PO)
		{
			InterfaceWrapperHelper.refresh(record);
		}

		return record;
	}

	@NonNull
	public RecordDataItem<T> getRecordDataItem(@NonNull final String identifier)
	{
		final RecordDataItem<T> recordDataItem = records.get(identifier);
		assertThat(recordDataItem).as("Missing recordDataItem for identifier=%s", identifier).isNotNull();

		return recordDataItem;
<<<<<<< HEAD
	}

	@NonNull
	public Optional<T> getOptional(@NonNull final String identifier)
	{
		return Optional.ofNullable(records.get(identifier)).map(RecordDataItem::getRecord);
	}

	public ImmutableList<T> getRecords()
	{
		return records.values().stream().map(RecordDataItem::getRecord).collect(ImmutableList.toImmutableList());
	}

	/**
	 * @param productRecord the item to store.
	 *                      In case of a model interface, we just store its ID and class, to avoid problems with DB-transactions or other sorts of leaks.
	 */
	@NotNull
	private StepDefData.RecordDataItem<T> createRecordDataItem(final @NotNull T productRecord)
	{
		if (InterfaceWrapperHelper.isModelInterface(productRecord.getClass()) && clazz != null)
		{
			final Instant updated = InterfaceWrapperHelper
					.getValueOptional(productRecord, InterfaceWrapperHelper.COLUMNNAME_Updated)
					.map(TimeUtil::asInstant)
					.orElse(Instant.MIN);

			// just store ID and table name, to avoid any leaks
			final TableRecordReference tableRecordReference = TableRecordReference.of(InterfaceWrapperHelper.getModelTableName(productRecord), InterfaceWrapperHelper.getId(productRecord));

			return new RecordDataItem<>((T)null,
										tableRecordReference,
										clazz,
										Instant.now(),
										updated);

		}
		return new RecordDataItem<T>(productRecord, null, null, Instant.now(), Instant.MIN);
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

=======
>>>>>>> 01acf328a21 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
	}

	@NonNull
	public Optional<T> getOptional(@NonNull final String identifier)
	{
		return Optional.ofNullable(records.get(identifier)).map(RecordDataItem::getRecord);
	}

	public ImmutableList<T> getRecords()
	{
		return records.values().stream().map(RecordDataItem::getRecord).collect(ImmutableList.toImmutableList());
	}

	/**
	 * @param productRecord the item to store.
	 *                      In case of a model interface, we just store its ID and class, to avoid problems with DB-transactions or other sorts of leaks.
	 */
	@NotNull
	private StepDefData.RecordDataItem<T> createRecordDataItem(final @NotNull T productRecord)
	{
		if (InterfaceWrapperHelper.isModelInterface(productRecord.getClass()) && clazz != null)
		{
			final Instant updated = InterfaceWrapperHelper
					.getValueOptional(productRecord, InterfaceWrapperHelper.COLUMNNAME_Updated)
					.map(TimeUtil::asInstant)
					.orElse(Instant.MIN);

			// just store ID and table name, to avoid any leaks
			final TableRecordReference tableRecordReference = TableRecordReference.of(InterfaceWrapperHelper.getModelTableName(productRecord), InterfaceWrapperHelper.getId(productRecord));

			return new RecordDataItem<>((T)null,
										tableRecordReference,
										clazz,
										Instant.now(),
										updated);

		}
		return new RecordDataItem<T>(productRecord, null, null, Instant.now(), Instant.MIN);
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

	}
}