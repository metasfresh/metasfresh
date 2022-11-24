/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.compiere.model;

import com.google.common.collect.ImmutableList;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

public final class MakeUniqueLocationNameCommand
{
	private final ICountryDAO countriesRepo = Services.get(ICountryDAO.class);

	private final String nameInitial;
	private final String companyName;
	private final I_C_Location address;
	private final List<String> existingNames;
	private final int maxLength;

	@Builder
	private MakeUniqueLocationNameCommand(
			@Nullable final String name,
			@Nullable final String companyName,
			@NonNull final I_C_Location address,
			@Nullable final List<String> existingNames,
			final int maxLength)
	{
		this.companyName = companyName;
		this.address = address;
		this.existingNames = existingNames != null ? existingNames : ImmutableList.of();
		this.maxLength = maxLength > 0 ? maxLength : Integer.MAX_VALUE;

		if (Check.isEmpty(name, true) || ".".equals(name))
		{
			this.nameInitial = null;
		}
		else
		{
			this.nameInitial = name.trim();
		}
	}

	public String execute()
	{
		final String name = !Check.isEmpty(this.nameInitial, true)
				? this.nameInitial.trim()
				: buildDefaultName();

		return truncateAndMakeUnique(name);
	}

	private String buildDefaultName()
	{
		String defaultName = "";

		//
		// City
		defaultName = appendToName(defaultName, address.getCity());

		//
		// Address1
		defaultName = appendToName(defaultName, address.getAddress1());

		// Company Name
		defaultName = appendToName(defaultName, companyName);
		if (isValidUniqueName(defaultName))
		{
			return defaultName;
		}

		//
		// Address2
		{
			defaultName = appendToName(defaultName, address.getAddress2());
			if (isValidUniqueName(defaultName))
			{
				return defaultName;
			}
		}

		//
		// Address3
		{
			defaultName = appendToName(defaultName, address.getAddress3());
			if (isValidUniqueName(defaultName))
			{
				return defaultName;
			}
		}

		//
		// Address4
		{
			defaultName = appendToName(defaultName, address.getAddress4());
			if (isValidUniqueName(defaultName))
			{
				return defaultName;
			}
		}

		//
		// Country
		if (defaultName.isEmpty())
		{
			final CountryId countryId = CountryId.ofRepoId(address.getC_Country_ID());
			final String countryName = countriesRepo.getCountryNameById(countryId).getDefaultValue();
			defaultName = appendToName(defaultName, countryName);
		}

		return defaultName;
	}

	private static String appendToName(final String name, final String namePartToAppend)
	{
		if (name == null || name.isEmpty())
		{
			return namePartToAppend != null ? namePartToAppend.trim() : "";
		}
		else if (Check.isEmpty(namePartToAppend, true))
		{
			return name.trim();
		}
		else
		{
			return name.trim() + " " + namePartToAppend.trim();
		}
	}

	private boolean isValidUniqueName(final String name)
	{
		return !Check.isEmpty(name, true)
				&& !existingNames.contains(name);
	}

	private String truncateAndMakeUnique(@NonNull final String name)
	{
		Check.assumeNotEmpty(name, "name is not empty");

		int i = 2;
		String nameUnique = StringUtils.trunc(name, maxLength);
		while (existingNames.contains(nameUnique))
		{
			final String suffix = " (" + i + ")";
			nameUnique = StringUtils.trunc(name, maxLength - suffix.length()) + suffix;
			i++;
		}

		return nameUnique;
	}
}


