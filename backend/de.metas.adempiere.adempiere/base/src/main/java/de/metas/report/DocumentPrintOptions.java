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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public final class DocumentPrintOptions
{
	public static final DocumentPrintOptions NONE = builder().build();

	public static final String OPTION_IsPrintLogo = DocumentPrintOptionDescriptor.PROCESS_PARAM_PRINTER_OPTIONS_PREFIX + "IsPrintLogo";
	public static final String OPTION_IsPrintTotals = DocumentPrintOptionDescriptor.PROCESS_PARAM_PRINTER_OPTIONS_PREFIX + "IsPrintTotals";

	@NonNull
	private final ImmutableMap<String, Boolean> options;

	@Builder
	private DocumentPrintOptions(
			@NonNull @Singular final ImmutableMap<String, Boolean> options)
	{
		this.options = options;
	}

	public static DocumentPrintOptions ofMap(final Map<String, String> map)
	{
		if (map.isEmpty())
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

		if (options.isEmpty())
		{
			return NONE;
		}

		return new DocumentPrintOptions(ImmutableMap.copyOf(options));
	}

	public boolean isNone()
	{
		return options.isEmpty();
	}

	public ImmutableSet<String> getOptionNames()
	{
		return options.keySet();
	}

	public OptionalBoolean getOption(@NonNull final String name)
	{
		final Boolean value = options.get(name);
		return OptionalBoolean.ofNullableBoolean(value);
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
			final LinkedHashMap<String, Boolean> newOptions = new LinkedHashMap<>(this.options);
			for (final String name : fallback.options.keySet())
			{
				newOptions.computeIfAbsent(name, fallback.options::get);
			}

			if (newOptions.equals(this.options))
			{
				return this;
			}

			return new DocumentPrintOptions(ImmutableMap.copyOf(newOptions));
		}
	}

}
