package de.metas.phonecall.service;

import java.time.LocalDate;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.phonecall.PhonecallSchemaVersion;
import de.metas.util.Check;
import de.metas.util.time.generator.DateSequenceGenerator;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class PhonecallSchemaVersionRange
{
	public PhonecallSchemaVersion phonecallSchemaVersion;
	private LocalDate startDate;
	private LocalDate endDate;
	private DateSequenceGenerator dateSequenceGenerator;

	@Builder
	private PhonecallSchemaVersionRange(
			@NonNull final PhonecallSchemaVersion phonecallSchemaVersion,
			@NonNull final LocalDate startDate,
			@NonNull final LocalDate endDate,
			final DateSequenceGenerator dateSequenceGenerator)
	{
		Check.assume(startDate.compareTo(endDate) <= 0, "StartDate({}) <= EndDate({})", startDate, endDate);

		this.phonecallSchemaVersion = phonecallSchemaVersion;
		this.startDate = startDate;
		this.endDate = endDate;
		this.dateSequenceGenerator = dateSequenceGenerator;
	}

	public Set<LocalDate> generatePhonecallDates()
	{
		if (dateSequenceGenerator == null)
		{
			return ImmutableSet.of();
		}

		return dateSequenceGenerator.generate();
	}
}
