package de.metas.phonecall;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
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
public class PhonecallSchema
{
	@NonNull
	PhonecallSchemaId id;
	@NonNull
	String name;

	@Getter(AccessLevel.PRIVATE)
	ImmutableList<PhonecallSchemaVersion> versions;

	@Builder
	private PhonecallSchema(
			@NonNull final PhonecallSchemaId id,
			@NonNull final String name,
			@NonNull final List<PhonecallSchemaVersion> versions)
	{
		this.id = id;
		this.name = name;
		this.versions = versions.stream()
				.sorted(Comparator.comparing(PhonecallSchemaVersion::getValidFrom))
				.collect(ImmutableList.toImmutableList());
	}

	public List<PhonecallSchemaVersion> getChronologicallyOrderedPhonecallSchemaVersions(@NonNull final LocalDate endDate)
	{
		ImmutableList<PhonecallSchemaVersion> phonecallVersionsForDateRange = versions.stream()
				.filter(version -> version.getValidFrom().compareTo(endDate) <= 0)
				.collect(ImmutableList.toImmutableList());

		if (phonecallVersionsForDateRange.isEmpty())
		{
			throw new AdempiereException("No version found before " + endDate);
		}

		return phonecallVersionsForDateRange;
	}

	public Optional<PhonecallSchemaVersion> getVersionByValidFrom(@NonNull final LocalDate validFrom)
	{
		return getVersions()
				.stream()
				.filter(version -> version.getValidFrom().equals(validFrom))
				.findFirst();
	}
}
