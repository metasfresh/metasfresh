/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.window.datatypes;

import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Function;

@Value
@Builder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class LookupValuesPage
{
	public static final LookupValuesPage EMPTY = builder()
			.totalRows(OptionalInt.of(0))
			.firstRow(-1)
			.values(LookupValuesList.EMPTY)
			.hasMoreResults(OptionalBoolean.FALSE)
			.build();

	@NonNull
	@Builder.Default
	@With
	OptionalInt totalRows = OptionalInt.empty();

	int firstRow;

	@NonNull
	LookupValuesList values;

	@NonNull
	@Builder.Default
	@With
	OptionalBoolean hasMoreResults = OptionalBoolean.UNKNOWN;

	public static LookupValuesPage ofValuesAndHasMoreFlag(@NonNull final List<LookupValue> values, boolean hasMoreRecords)
	{
		return ofValuesAndHasMoreFlag(LookupValuesList.fromCollection(values), hasMoreRecords);
	}

	public static LookupValuesPage ofValuesAndHasMoreFlag(@NonNull final LookupValuesList values, boolean hasMoreRecords)
	{
		return builder()
				.totalRows(OptionalInt.empty()) // N/A
				.firstRow(-1) // N/A
				.values(values)
				.hasMoreResults(OptionalBoolean.ofBoolean(hasMoreRecords))
				.build();
	}

	public static LookupValuesPage allValues(@NonNull final LookupValuesList values)
	{
		return builder()
				.totalRows(OptionalInt.of(values.size())) // N/A
				.firstRow(0) // N/A
				.values(values)
				.hasMoreResults(OptionalBoolean.FALSE)
				.build();
	}

	public static LookupValuesPage ofNullable(@Nullable final LookupValue lookupValue)
	{
		if (lookupValue == null)
		{
			return EMPTY;
		}

		return allValues(LookupValuesList.fromNullable(lookupValue));
	}

	public <T> T transform(@NonNull final Function<LookupValuesPage, T> transformation)
	{
		return transformation.apply(this);
	}

	public boolean isEmpty()
	{
		return values.isEmpty();
	}
}
