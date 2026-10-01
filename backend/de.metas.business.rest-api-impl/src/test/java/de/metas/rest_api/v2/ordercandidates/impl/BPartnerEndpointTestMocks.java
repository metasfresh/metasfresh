/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.rest_api.v2.ordercandidates.impl;

import de.metas.common.bpartner.v2.response.JsonResponseBPartner;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.rest_api.v2.bpartner.BpartnerRestController;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * Shared test helper: mocks the {@link BpartnerRestController} to understand only metasfresh-id identifiers
 * (the form {@link MasterdataProvider} passes on after resolving a GLN); any other identifier is "not found".
 */
final class BPartnerEndpointTestMocks
{
	private BPartnerEndpointTestMocks()
	{
	}

	static void mockForMetasfreshIdsOnly(final BpartnerRestController bpartnerRestController)
	{
		Mockito.doAnswer(invocation -> {
			final String bpartnerIdentifier = invocation.getArgument(1);
			if (!isMetasfreshId(bpartnerIdentifier))
			{
				return ResponseEntity.notFound().build();
			}
			final JsonResponseComposite composite = JsonResponseComposite.builder()
					.bpartner(JsonResponseBPartner.builder()
							.metasfreshId(JsonMetasfreshId.of(Integer.parseInt(bpartnerIdentifier)))
							.active(true).name("bp").vendor(false).customer(true).company(true)
							.build())
					.build();
			return ResponseEntity.ok(composite);
		}).when(bpartnerRestController).retrieveBPartner(any(), anyString());

		Mockito.doAnswer(invocation -> {
			final String bpartnerIdentifier = invocation.getArgument(1);
			final String locationIdentifier = invocation.getArgument(2);
			if (!isMetasfreshId(bpartnerIdentifier) || !isMetasfreshId(locationIdentifier))
			{
				return ResponseEntity.notFound().build();
			}
			final JsonResponseLocation location = JsonResponseLocation.builder()
					.metasfreshId(JsonMetasfreshId.of(Integer.parseInt(locationIdentifier)))
					.active(true)
					.build();
			return ResponseEntity.ok(location);
		}).when(bpartnerRestController).retrieveBPartnerLocation(any(), anyString(), anyString());
	}

	private static boolean isMetasfreshId(final String identifier)
	{
		return identifier.matches("^\\d+$");
	}
}
