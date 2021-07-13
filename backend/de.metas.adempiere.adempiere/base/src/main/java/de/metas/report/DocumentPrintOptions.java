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

package de.metas.report;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.util.OptionalBoolean;
import de.metas.util.StringUtils;
import de.metas.util.collections.IdentityHashSet;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public final class DocumentPrintOptions
{
	public static final DocumentPrintOptions NONE = builder().sourceName("none").build();

	public static final String OPTION_IsPrintLogo = DocumentPrintOptionDescriptor.PROCESS_PARAM_PRINTER_OPTIONS_PREFIX + "IsPrintLogo";
	public static final String OPTION_IsPrintTotals = DocumentPrintOptionDescriptor.PROCESS_PARAM_PRINTER_OPTIONS_PREFIX + "IsPrintTotals";

	private final String sourceName;

	@NonNull
	private final ImmutableMap<String, Boolean> options;

	@Nullable
	private final DocumentPrintOptions fallback;

	@Builder(toBuilder = true)
	private DocumentPrintOptions(
			@Nullable final String sourceName,
			@NonNull @Singular final ImmutableMap<String, Boolean> options,
			@Nullable final DocumentPrintOptions fallback)
	{
		this.sourceName = sourceName;
		this.options = options;
		this.fallback = fallback != null && !fallback.isNone() ? fallback : null;

		assertNoCycles();
	}

	private void assertNoCycles()
	{
		if (fallback == null)
		{
			return;
		}

		final IdentityHashSet<DocumentPrintOptions> trace = new IdentityHashSet<>();
		DocumentPrintOptions current = this;
		while (current != null)
		{
			if (!trace.add(current))
			{
				throw new AdempiereException("Cycle in printing options fallback detected: " + trace);
			}
			current = current.fallback;
		}
	}

	public static DocumentPrintOptions ofMap(
			@NonNull final Map<String, String> map,
			@Nullable final String sourceName)
	{
		if (map.isEmpty() && sourceName == null)
		{
			return NONE;
		}

		final HashMap<String, Boolean> options = new HashMap<>();
		for (final String name : map.keySet())
		{
			final Boolean value = StringUtils.toBoolean(map.get(name), null);
			if (value == null)
			{
				continue;
			}

			options.put(name, value);
		}

		return builder()
				.sourceName(sourceName)
				.options(options)
				.build();
	}

	public boolean isEmpty()
	{
		return options.isEmpty();
	}

	private boolean isNone()
	{
		return this.equals(NONE);
	}

	public ImmutableSet<String> getOptionNames()
	{
		if (fallback != null && !fallback.isEmpty())
		{
			return ImmutableSet.<String>builder()
					.addAll(options.keySet())
					.addAll(fallback.getOptionNames())
					.build();
		}
		else
		{
			return options.keySet();
		}
	}

	public DocumentPrintOptionValue getOption(@NonNull final String name)
	{
		final Boolean value = options.get(name);
		if (value != null)
		{
			return DocumentPrintOptionValue.builder()
					.value(OptionalBoolean.ofBoolean(value))
					.sourceName(sourceName)
					.build();
		}
		else if (fallback != null)
		{
			return fallback.getOption(name);
		}
		else
		{
			return DocumentPrintOptionValue.MISSING;
		}
	}

	public DocumentPrintOptions mergeWithFallback(@NonNull final DocumentPrintOptions fallback)
	{
		if (fallback.isNone())
		{
			return this;
		}
		else if (isNone())
		{
			return fallback;
		}
		else
		{
			if (this == fallback)
			{
				throw new IllegalArgumentException("Merging with itself is not allowed");
			}

			final DocumentPrintOptions newFallback;
			if (this.fallback != null)
			{
				newFallback = this.fallback.mergeWithFallback(fallback);
			}
			else
			{
				newFallback = fallback;
			}

			return !Objects.equals(this.fallback, newFallback)
					? toBuilder().fallback(newFallback).build()
					: this;
		}
	}

}
