/*
 * #%L
 * metas
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.grssignum.to_grs.bpartner;

import com.google.common.collect.ImmutableList;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class ExportLocationHelperTests
{

	@Test
	public void givenOneVisitorsAdrAmongMultipleAddresses_whenGetBPartnerMainLocation_thenReturnVisitorAddress()
	{
		//given
		final List<JsonResponseLocation> jsonResponseLocations = ImmutableList.of(
				JsonResponseLocation.builder()
						.metasfreshId(JsonMetasfreshId.of(1))
						.billToDefault(true)
						.active(true)
						.build(),
				JsonResponseLocation.builder()
						.metasfreshId(JsonMetasfreshId.of(2))
						.billTo(true)
						.active(true)
						.build(),
				JsonResponseLocation.builder()
						.metasfreshId(JsonMetasfreshId.of(3))
						.shipToDefault(true)
						.active(true)
						.build(),
				JsonResponseLocation.builder()
						.metasfreshId(JsonMetasfreshId.of(4))
						.shipTo(true)
						.active(true)
						.build(),
				JsonResponseLocation.builder()
						.metasfreshId(JsonMetasfreshId.of(5))
						.visitorsAddress(true)
						.active(true)
						.build()
		);
		//when
		final Optional<JsonResponseLocation> responseLocation = ExportLocationHelper.getBPartnerMainLocation(jsonResponseLocations);

		//then
		assertThat(responseLocation).isPresent();
		assertThat(responseLocation.get().getMetasfreshId().getValue()).isEqualTo(5);
	}
}
