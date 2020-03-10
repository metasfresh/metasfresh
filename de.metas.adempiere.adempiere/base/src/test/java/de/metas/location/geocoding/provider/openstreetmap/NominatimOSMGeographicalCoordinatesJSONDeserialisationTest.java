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

package de.metas.location.geocoding.provider.openstreetmap;

import de.metas.util.JSONObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NominatimOSMGeographicalCoordinatesJSONDeserialisationTest
{
	@Test
	void canDeserializeGoodResponse()
	{
		final String serialisedResponse = "  {\n"
				+ "    \"place_id\": 237626930,\n"
				+ "    \"licence\": \"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\n"
				+ "    \"boundingbox\": [\n"
				+ "      \"45.758244872052\",\n"
				+ "      \"45.758344872052\",\n"
				+ "      \"21.224939377961\",\n"
				+ "      \"21.225039377961\"\n"
				+ "    ],\n"
				+ "    \"lat\": \"45.75829487205204\",\n"
				+ "    \"lon\": \"21.224989377961297\",\n"
				+ "    \"display_name\": \"Tipografilor, Timișoara, Timiș, 300078, Romania\",\n"
				+ "    \"class\": \"place\",\n"
				+ "    \"type\": \"postcode\",\n"
				+ "    \"importance\": 0.33499999999999996\n"
				+ "  }\n";

		final NominatimOSMGeographicalCoordinatesJSON expected = NominatimOSMGeographicalCoordinatesJSON.builder()
				.display_name("Tipografilor, Timișoara, Timiș, 300078, Romania")
				.lat("45.75829487205204")
				.lon("21.224989377961297")
				.build();

		final NominatimOSMGeographicalCoordinatesJSON deserializedInvoiceCandidate = JSONObjectMapper.forClass(NominatimOSMGeographicalCoordinatesJSON.class).readValue(serialisedResponse);
		assertThat(deserializedInvoiceCandidate).isEqualTo(expected);
	}

	@Test
	void canDeserializeEmptyResponse()
	{
		final String serialisedResponse = "{}";

		final NominatimOSMGeographicalCoordinatesJSON expected = NominatimOSMGeographicalCoordinatesJSON.builder()
				.build();

		final NominatimOSMGeographicalCoordinatesJSON deserializedInvoiceCandidate = JSONObjectMapper.forClass(NominatimOSMGeographicalCoordinatesJSON.class).readValue(serialisedResponse);
		assertThat(deserializedInvoiceCandidate).isEqualTo(expected);
	}

}
