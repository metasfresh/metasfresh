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

import lombok.NonNull;
import org.testcontainers.shaded.com.google.common.collect.ImmutableCollection;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public abstract class StepDefData<T>
{
	private final Map<String, T> records = new HashMap<>();

	public void put(@NonNull final String identifier, @NonNull final T productRecord)
	{
		final T oldRecord = records.put(identifier, productRecord);

		assertThat(oldRecord)
				.as("An identifier may be used just once, but %s was already used with %s", identifier, oldRecord)
				.isNull();
	}

	public void putOrReplace(@NonNull final String identifier, @NonNull final T productRecord)
	{
		final T oldRecord = records.get(identifier);

		if (oldRecord == null)
		{
			put(identifier, productRecord);
		}
		else
		{
			records.replace(identifier, productRecord);
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
		final T oldRecord = records.get(identifier);

		if (oldRecord != null)
		{
			return;
		}

		put(identifier, record);
	}

	@NonNull
	public T get(@NonNull final String identifier)
	{
		final T record = records.get(identifier);
		assertThat(record).as("Missing record for identifier=%s", identifier).isNotNull();

		return record;
	}

	@NonNull
	public Optional<T> getOptional(@NonNull final String identifier)
	{
		return Optional.ofNullable(records.get(identifier));
	}

	public ImmutableCollection<T> getRecords()
	{
		return ImmutableList.copyOf(records.values());
	}
}
