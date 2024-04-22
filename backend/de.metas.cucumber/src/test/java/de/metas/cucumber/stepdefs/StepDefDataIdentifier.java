/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;

@EqualsAndHashCode
public final class StepDefDataIdentifier
{
	public static final StepDefDataIdentifier NULL = new StepDefDataIdentifier(DataTableUtil.NULL_STRING);

	public static final String SUFFIX = "Identifier";

	private static final String PREFIX_Unnamed = "unnamed-";
	private static final AtomicInteger nextUnnamedIdentifierId = new AtomicInteger(1);

	@NonNull private final String value;

	private StepDefDataIdentifier(@NonNull final String value)
	{
		this.value = value;
	}

	@NonNull
	public static StepDefDataIdentifier ofString(@NonNull String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("Invalid identifier `" + value + "`");
		}
		else if (valueNorm.equalsIgnoreCase(NULL.value) || "-".equals(value))
		{
			return NULL;
		}
		else
		{
			return new StepDefDataIdentifier(valueNorm);
		}
	}

	@Nullable
	public static StepDefDataIdentifier ofNullableString(@Nullable String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? ofString(valueNorm) : null;
	}

	public static StepDefDataIdentifier nextUnnamed()
	{
		return ofString(PREFIX_Unnamed + nextUnnamedIdentifierId.getAndIncrement());
	}

	public static boolean equals(@Nullable StepDefDataIdentifier id1, @Nullable StepDefDataIdentifier id2) {return Objects.equals(id1, id2);}

	@Override
	public String toString() {return getAsString();}

	public boolean isNullPlaceholder() {return this.equals(NULL);}

	public String getAsString() {return value;}

	public <T extends RepoIdAware> T getAsId(@NonNull final Class<T> idType) {return RepoIdAwares.ofObject(value, idType);}

	public int getAsInt() {return NumberUtils.asInt(value);}

	public <T> T lookupIn(@NonNull final StepDefData<T> table) {return table.get(this);}

	public <ID extends RepoIdAware> ID lookupIdIn(@NonNull final StepDefDataGetIdAware<ID, ?> table) {return table.getId(this);}

	public <T> T lookupOrLoadById(@NonNull final StepDefData<T> table, @NonNull IntFunction<T> loader)
	{
		return table.getOptional(this)
				.orElseGet(() -> loader.apply(getAsInt()));
	}

	public <T> void put(@NonNull final StepDefData<T> table, @NonNull T record) {table.put(this, record);}

	public <T> void putOrReplace(@NonNull final StepDefData<T> table, @NonNull T record) {table.putOrReplace(this, record);}

	@NonNull
	public List<StepDefDataIdentifier> toCommaSeparatedList()
	{
		if (!value.contains(","))
		{
			return ImmutableList.of(this);
		}
		else
		{
			return Splitter.on(",")
					.trimResults()
					.omitEmptyStrings()
					.splitToList(value)
					.stream()
					.map(StepDefDataIdentifier::ofString)
					.collect(ImmutableList.toImmutableList());
		}
	}
}
