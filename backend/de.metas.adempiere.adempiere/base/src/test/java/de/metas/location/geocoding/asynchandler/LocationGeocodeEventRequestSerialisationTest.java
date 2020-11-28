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

package de.metas.location.geocoding.asynchandler;

import de.metas.location.LocationId;
import de.metas.util.JSONObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocationGeocodeEventRequestSerialisationTest
{

	@Test
	void testSerialisationDeserialisation()
	{
		final JSONObjectMapper<LocationGeocodeEventRequest> jsonObjectMapper = JSONObjectMapper.forClass(LocationGeocodeEventRequest.class);

		final String json = jsonObjectMapper.writeValueAsString(LocationGeocodeEventRequest.of(LocationId.ofRepoId(6)));
		final LocationGeocodeEventRequest deserialisedRequest = jsonObjectMapper.readValue(json);
		assertThat(deserialisedRequest).isEqualTo(LocationGeocodeEventRequest.of(LocationId.ofRepoId(6)));
	}

}
