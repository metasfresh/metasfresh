/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.picking.job.model;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
@Builder
public class RenderedAddressProvider
{
	@NonNull IDocumentLocationBL documentLocationBL;
	@NonNull Map<BPartnerLocationId, String> locationId2RenderedAddress;

	@NonNull
	public static RenderedAddressProvider of(@NonNull final IDocumentLocationBL locationBL)
	{
		return RenderedAddressProvider.builder()
				.documentLocationBL(locationBL)
				.locationId2RenderedAddress(new HashMap<>())
				.build();
	}

	@NonNull
	public String getAddress(@NonNull final BPartnerLocationId locationId)
	{
		return locationId2RenderedAddress.computeIfAbsent(locationId, this::renderAddress);
	}

	@NonNull
	private String renderAddress(@NonNull final BPartnerLocationId locationId)
	{
		return documentLocationBL
				.computeRenderedAddress(DocumentLocation.ofBPartnerLocationId(locationId))
				.getRenderedAddress();
	}
}
