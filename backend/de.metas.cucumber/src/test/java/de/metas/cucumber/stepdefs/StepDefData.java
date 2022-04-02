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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public abstract class StepDefData<T>
{
	private final Map<String, RecordDataItem<T>> records = new HashMap<>();

	public void put(@NonNull final String identifier, @NonNull final T productRecord)
	{
		final RecordDataItem<T> recordDataItem = createRecordDataItem(productRecord);

		final RecordDataItem<T> oldRecord = records.put(identifier, recordDataItem);
		assertThat(oldRecord)
				.as("An identifier may be used just once, but %s was already used with %s", identifier, oldRecord)
				.isNull();
	}

	public void putOrReplace(@NonNull final String identifier, @NonNull final T productRecord)
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
	
	public void putAll(@NonNull final Map<String, T> map)
	{
		for (final Map.Entry<String, T> entry : map.entrySet())
		{
			putOrReplace(entry.getKey(), entry.getValue());
		}
	}

	public void putIfMissing(@NonNull final String identifier, @NonNull final T record)
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
		return getRecordDataItem(identifier).getRecord();
	}

	@NonNull
	public RecordDataItem<T> getRecordDataItem(@NonNull final String identifier)
	{
		final RecordDataItem<T> recordDataItem = records.get(identifier);
		assertThat(recordDataItem).as("Missing recordDataItem for identifier=%s", identifier).isNotNull();

		return recordDataItem;
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

	@NotNull
	private StepDefData.RecordDataItem<T> createRecordDataItem(final @NotNull T productRecord)
	{
		final Optional<Instant> updatedOpt;
		if (InterfaceWrapperHelper.isModelInterface(productRecord.getClass()))
		{
			updatedOpt = InterfaceWrapperHelper
					.getValueOptional(productRecord, InterfaceWrapperHelper.COLUMNNAME_Updated)
					.map(TimeUtil::asInstant);
		}
		else
		{
			updatedOpt = Optional.empty();
		}

		final Instant updated = updatedOpt.orElse(Instant.MIN);
		return new RecordDataItem<T>(productRecord, Instant.now(), updated);
	}
	
	@Value
	public static class RecordDataItem<T>
	{
		T record;
		Instant recordAdded;
		Instant recordUpdated;
	}
}
