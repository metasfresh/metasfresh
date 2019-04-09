package org.adempiere.location.geocoding.openstreetmap;

import org.adempiere.location.geocoding.GeographicalCoordinates;
import org.adempiere.location.geocoding.GeographicalCoordinatesRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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

class OSMGeographicalCoordinatesProviderImplTest
{
	private OSMGeographicalCoordinatesProviderImpl coordinatesProvider;

	@BeforeEach
	void beforeEach()
	{
		coordinatesProvider = new OSMGeographicalCoordinatesProviderImpl();
	}

	@Test
	@DisplayName("singleCoordinate; countryCode: no; postal: no; address: 'zzzzzzzzzz'")
	void singleCoordinate_CountryCodeNo_PostalNo_Addresszzzzzzzzzzzzzz()
	{
		final Optional<GeographicalCoordinates> coord = coordinatesProvider.findBestCoordinates(
				GeographicalCoordinatesRequest.builder()
						.address("zzzzzzzzzzzzzzzzzzzzzzzzzzz")
						.countryCode("")
						.build());

		assertThat(coord).isEmpty();
	}

	@Test
	@DisplayName("bestCoordinates finds metasRO only by postal")
	void bestCoordinatesFindsMetasROOnlyByPostal()
	{
		final Optional<GeographicalCoordinates> coord = coordinatesProvider.findBestCoordinates(
				GeographicalCoordinatesRequest.builder()
						.postal("300078")
						.countryCode("")
						.build());

		final GeographicalCoordinates expectedCoordinates = new GeographicalCoordinates("45.758301112052", "21.2249884579613");

		assertThat(coord)
				.isNotEmpty()
				.contains(expectedCoordinates);
	}

	@Test
	@DisplayName("bestCoordinates cannot find metasRO by postal and wrong country")
	void bestCoordinatesCannotFindMetasROByPostalAndWrongCountry()
	{
		final Optional<GeographicalCoordinates> coord = coordinatesProvider.findBestCoordinates(
				GeographicalCoordinatesRequest.builder()
						.postal("300078")
						.countryCode("UK")
						.build());

		assertThat(coord).isEmpty();
	}

	@Test
	@DisplayName("bestCoordinates finds metasRO by postal and RO country")
	void bestCoordinatesFindsMetasROByPostalAndCorrectCountry()
	{
		final Optional<GeographicalCoordinates> coord = coordinatesProvider.findBestCoordinates(
				GeographicalCoordinatesRequest.builder()
						.postal("300078")
						.countryCode("RO")
						.build());

		final GeographicalCoordinates expectedCoordinates = new GeographicalCoordinates("45.758301112052", "21.2249884579613");

		assertThat(coord)
				.isNotEmpty()
				.contains(expectedCoordinates);
	}

	@Test
	@DisplayName("Should ignore the address if a postal code exists")
	void shouldIgnoreTheAddressIfAPostalCodeExists()
	{
		final List<GeographicalCoordinates> coord = coordinatesProvider.findAllCoordinates(
				GeographicalCoordinatesRequest.builder()
						.postal("5081")
						.countryCode("AT")
						.address("gfvgdggsdfsdfgsdfgsdfgsdfgnull")
						.build());

		final List<GeographicalCoordinates> expectedCoordinates = Arrays.asList(new GeographicalCoordinates("47.7587073", "13.0612349838947"));

		assertThat(coord)
				.isNotEmpty()
				.containsAll(expectedCoordinates);
	}

	@Test
	@DisplayName("Should ignore the address if a postal code exists2")
	void shouldIgnoreTheAddressIfAPostalCodeExists2()
	{
		final List<GeographicalCoordinates> coord = coordinatesProvider.findAllCoordinates(
				GeographicalCoordinatesRequest.builder()
						.postal("5081")
						.countryCode("AT")
						.build());

		final List<GeographicalCoordinates> expectedCoordinates = Arrays.asList(new GeographicalCoordinates("47.7587073", "13.0612349838947"));

		assertThat(coord)
				.isNotEmpty()
				.containsAll(expectedCoordinates);
	}

	@Test
	@DisplayName("Search for postal code in wrong country has no result")
	void searchForPostalCodeInWrongCountryHasNoResult()
	{
		final Optional<GeographicalCoordinates> coord = coordinatesProvider.findBestCoordinates(
				GeographicalCoordinatesRequest.builder()
						.postal("5081")
						.countryCode("RO")
						.build());

		assertThat(coord)
				.isEmpty();
	}

}
