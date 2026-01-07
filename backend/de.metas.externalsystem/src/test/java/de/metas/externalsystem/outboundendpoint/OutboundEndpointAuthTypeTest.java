/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.outboundendpoint;

import de.metas.common.externalsystem.endpoint.JsonEndpointAuthType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class OutboundEndpointAuthTypeTest
{
	@Test
	void toJson_ensureAllEnumValuesAreMapped()
	{
		// Given: all enum values from OutboundEndpointAuthType
		final Set<String> outboundAuthTypeCodes = Arrays.stream(OutboundEndpointAuthType.values())
				.map(OutboundEndpointAuthType::getCode)
				.collect(Collectors.toSet());

		// And: all enum values from JsonEndpointAuthType
		final Set<String> jsonAuthTypeNames = Arrays.stream(JsonEndpointAuthType.values())
				.map(Enum::name)
				.collect(Collectors.toSet());

		// Then: all OutboundEndpointAuthType codes should have corresponding JsonEndpointAuthType entries
		assertThat(outboundAuthTypeCodes)
				.as("All OutboundEndpointAuthType codes must have corresponding JsonEndpointAuthType entries")
				.isSubsetOf(jsonAuthTypeNames);

		// And: verify toJson() works for each value without throwing an exception
		Arrays.stream(OutboundEndpointAuthType.values())
				.forEach(authType -> {
					final JsonEndpointAuthType json = authType.toJson();
					assertThat(json).isNotNull();
					assertThat(json.name()).isEqualTo(authType.getCode());
				});
	}
}