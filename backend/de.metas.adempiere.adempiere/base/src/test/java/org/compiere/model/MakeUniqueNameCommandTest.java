package org.compiere.model;

import com.google.common.collect.ImmutableList;
import de.metas.location.CountryId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.MBPartnerLocation.MakeUniqueNameCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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

public class MakeUniqueNameCommandTest
{
	private static final String companyName= "Company1";
	private CountryId countryId_DE;
	private CountryId countryId_RO;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		countryId_DE = createCountry("Germany");
		countryId_RO = createCountry("Romania");
	}

	private CountryId createCountry(final String name)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setName(name);
		saveRecord(country);
		return CountryId.ofRepoId(country.getC_Country_ID());
	}

	@Test
	public void presetName_no_duplicates()
	{
		final String nameUnique = MakeUniqueNameCommand.builder()
				.name("test")
				.address(newInstance(I_C_Location.class)) // not important
				.build()
				.execute();
		assertThat(nameUnique).isEqualTo("test");
	}

	@Test
	public void presetName_with_one_duplicates()
	{
		final String nameUnique = MakeUniqueNameCommand.builder()
				.name("test")
				.address(newInstance(I_C_Location.class)) // not important
				.existingNames(ImmutableList.of("test"))
				.build()
				.execute();
		assertThat(nameUnique).isEqualTo("test (2)");
	}

	@Test
	public void presetName_with_couple_of_duplicates()
	{
		final String nameUnique = MakeUniqueNameCommand.builder()
				.name("test")
				.address(newInstance(I_C_Location.class)) // not important
				.existingNames(ImmutableList.of("test", "test (2)", "test (3)"))
				.build()
				.execute();
		assertThat(nameUnique).isEqualTo("test (4)");
	}

	@Test
	public void noPresetName_no_duplicates()
	{
		final I_C_Location address = newInstance(I_C_Location.class);
		address.setAddress1("address1");
		address.setAddress2("address2");
		address.setCity("city");
		address.setC_Country_ID(countryId_DE.getRepoId());

		final String nameUnique = MakeUniqueNameCommand.builder()
				.name(".")
				.address(address)
				.build()
				.execute();

		assertThat(nameUnique).isEqualTo("city address1");
	}

	@Test
	public void noPresetName_no_duplicates_withCompanyName()
	{
		final I_C_Location address = newInstance(I_C_Location.class);
		address.setAddress1("address1");
		address.setAddress2("address2");
		address.setCity("city");
		address.setC_Country_ID(countryId_DE.getRepoId());

		final String nameUnique = MakeUniqueNameCommand.builder()
				.name(".")
				.companyName(companyName)
				.address(address)
				.build()
				.execute();

		assertThat(nameUnique).isEqualTo("city address1 Company1");
	}

	@Test
	public void noPresetName_with_existing_city()
	{
		final I_C_Location address = newInstance(I_C_Location.class);
		address.setAddress1("address1");
		address.setAddress2("address2");
		address.setCity("city");
		address.setC_Country_ID(countryId_DE.getRepoId());

		final String nameUnique = MakeUniqueNameCommand.builder()
				.name(".")
				.address(address)
				.existingNames(ImmutableList.of("city"))
				.build()
				.execute();

		assertThat(nameUnique).isEqualTo("city address1");
	}

	@Test
	public void noPresetName_addressWithOnlyCountrySet()
	{
		final I_C_Location address = newInstance(I_C_Location.class);
		address.setC_Country_ID(countryId_DE.getRepoId());

		final String nameUnique = MakeUniqueNameCommand.builder()
				.name(".")
				.address(address)
				.existingNames(ImmutableList.of("Germany"))
				.build()
				.execute();

		assertThat(nameUnique).isEqualTo("Germany (2)");
	}

	@Test
	public void noPresetName_addressWithOnlyCountryAndCompanyNameSet()
	{
		final I_C_Location address = newInstance(I_C_Location.class);
		address.setC_Country_ID(countryId_DE.getRepoId());

		final String nameUnique = MakeUniqueNameCommand.builder()
				.name(".")
				.address(address)
				.companyName(companyName)
				.existingNames(ImmutableList.of("Company1"))
				.build()
				.execute();
		//The Country name is ignored because the companyName is not empty
		assertThat(nameUnique).isEqualTo("Company1 (2)");
	}

	@Test
	public void noPresetName_withOnlyCompanyNameSet_with_existing_name()
	{
		final I_C_Location address = newInstance(I_C_Location.class);

		final String nameUnique = MakeUniqueNameCommand.builder()
				.name(".")
				.address(address)
				.companyName(companyName)
				.existingNames(ImmutableList.of("Company1"))
				.build()
				.execute();

		assertThat(nameUnique).isEqualTo("Company1 (2)");
	}

}
