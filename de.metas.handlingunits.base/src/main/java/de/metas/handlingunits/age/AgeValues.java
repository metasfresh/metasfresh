package de.metas.handlingunits.age;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.util.time.SystemTime;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@ToString
@EqualsAndHashCode
public final class AgeValues
{
	private final ImmutableSet<Integer> agesInMonths;

	@NonNull static AgeValues ofAgeInMonths(final Set<Integer> agesInMonths)
	{
		return new AgeValues(agesInMonths);
	}

	private AgeValues(final Set<Integer> ageInMonths)
	{
		this.agesInMonths = ImmutableSet.copyOf(ageInMonths);
	}

	/**
	 * Find the highest Age AttributeValue which is <= than the productionDate.
	 */
	public int computeAgeInMonths(@NonNull final LocalDateTime productionDate)
	{
		final LocalDateTime now = SystemTime.asLocalDateTime();
		final long months = ChronoUnit.MONTHS.between(productionDate, now);

		int age = 0;
		for (final Integer value : agesInMonths)
		{
			if (months < value)
			{
				break;
			}
			age = value;
		}

		return age;
	}
}
