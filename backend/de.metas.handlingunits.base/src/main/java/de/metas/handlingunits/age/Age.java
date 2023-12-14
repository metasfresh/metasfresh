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

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode
public final class Age
{
	private final Integer ageInMonths;

	public static Age ZERO = ofAgeInMonths(0);

	@NonNull
	public static Age ofAgeInMonths(final Integer ageInMonths)
	{
		return new Age(ageInMonths);
	}

	private Age(final Integer ageInMonths)
	{
		this.ageInMonths = ageInMonths;
	}

	@NonNull
	public Age add(final @NonNull Age offset)
	{
		if (offset.isZero())
		{
			return this;
		}
		if (isZero())
		{
			return offset;
		}

		return new Age(getAgeInMonths() + offset.getAgeInMonths());
	}

	public boolean isZero()
	{
		return ageInMonths.intValue() == 0;
	}

	public int toIntValue()
	{
		return ageInMonths.intValue();
	}

	public String toStringValue()
	{
		return String.valueOf(ageInMonths.intValue());
	}
}
