/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cache;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public final class CCacheStatsOrderBy implements Comparator<CCacheStats>
{
	@NonNull
	private final ImmutableList<Part> parts;
	private transient Comparator<CCacheStats> _actualComparator = null; // lazy

	@Builder
	private CCacheStatsOrderBy(@NonNull @Singular final ImmutableList<Part> parts)
	{
		Check.assumeNotEmpty(parts, "at least one order by criteria shall be specified");
		this.parts = parts;
	}

	@SuppressWarnings("unused")
	public static class CCacheStatsOrderByBuilder
	{
		public CCacheStatsOrderByBuilder ascending(@NonNull final Field field)
		{
			return part(Part.of(field, true));
		}

		public CCacheStatsOrderByBuilder descending(@NonNull final Field field)
		{
			return part(Part.of(field, false));
		}
	}

	public static Optional<CCacheStatsOrderBy> parse(@Nullable final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		if (stringNorm == null)
		{
			return Optional.empty();
		}

		try
		{
			final ImmutableList<Part> parts = Splitter.on(",")
					.trimResults()
					.omitEmptyStrings()
					.splitToStream(stringNorm)
					.map(Part::parse)
					.collect(ImmutableList.toImmutableList());

			return Optional.of(new CCacheStatsOrderBy(parts));
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Invalid order by string `" + string + "`", ex);
		}
	}

	@Override
	public int compare(final CCacheStats o1, final CCacheStats o2)
	{
		return getActualComparator().compare(o1, o2);
	}

	private Comparator<CCacheStats> getActualComparator()
	{
		Comparator<CCacheStats> comparator = this._actualComparator;
		if (comparator == null)
		{
			comparator = this._actualComparator = createActualComparator();
		}
		return comparator;
	}

	private Comparator<CCacheStats> createActualComparator()
	{
		Comparator<CCacheStats> result = parts.get(0).toComparator();
		for (int i = 1; i < parts.size(); i++)
		{
			final Comparator<CCacheStats> partComparator = parts.get(i).toComparator();
			result = result.thenComparing(partComparator);
		}

		return result;
	}

	//
	//
	//

	public enum Field
	{
		name,
		size,
		hitRate,
		missRate,
	}

	@NonNull
	@Value(staticConstructor = "of")
	public static class Part
	{
		@NonNull Field field;
		boolean ascending;

		Comparator<CCacheStats> toComparator()
		{
			Comparator<CCacheStats> comparator;
			switch (field)
			{
				case name:
					comparator = Comparator.comparing(CCacheStats::getName);
					break;
				case size:
					comparator = Comparator.comparing(CCacheStats::getSize);
					break;
				case hitRate:
					comparator = Comparator.comparing(CCacheStats::getHitRate);
					break;
				case missRate:
					comparator = Comparator.comparing(CCacheStats::getMissRate);
					break;
				default:
					throw new AdempiereException("Unknown field type!");
			}

			if (!ascending)
			{
				comparator = comparator.reversed();
			}

			return comparator;
		}

		static Part parse(@NonNull final String string)
		{
			final String stringNorm = StringUtils.trimBlankToNull(string);
			if (stringNorm == null)
			{
				throw new AdempiereException("Invalid part: `" + string + "`");
			}

			final String fieldName;
			final boolean ascending;
			if (stringNorm.charAt(0) == '+')
			{
				fieldName = stringNorm.substring(1);
				ascending = true;
			}
			else if (stringNorm.charAt(0) == '-')
			{
				fieldName = stringNorm.substring(1);
				ascending = false;
			}
			else
			{
				fieldName = stringNorm;
				ascending = true;
			}

			final Field field = Field.valueOf(fieldName);

			return of(field, ascending);
		}
	}
}
