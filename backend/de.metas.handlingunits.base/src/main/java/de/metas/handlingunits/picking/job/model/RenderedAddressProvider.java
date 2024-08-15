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
import de.metas.location.AddressDisplaySequence;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Value
@Builder
public class RenderedAddressProvider
{
	@NonNull IDocumentLocationBL documentLocationBL;
	@NonNull Map<AddressToRender, String> locationId2RenderedAddress;

	@NonNull
	public static RenderedAddressProvider newInstance(@NonNull final IDocumentLocationBL locationBL)
	{
		return RenderedAddressProvider.builder()
				.documentLocationBL(locationBL)
				.locationId2RenderedAddress(new HashMap<>())
				.build();
	}

	@NonNull
	public String getAddress(@NonNull final BPartnerLocationId locationId)
	{
		return getAddress(locationId, null);
	}

	@NonNull
	public String getAddress(@NonNull final BPartnerLocationId bpLocationId, @Nullable AddressDisplaySequence displaySequence)
	{
		return locationId2RenderedAddress.computeIfAbsent(AddressToRender.of(bpLocationId, displaySequence), this::renderAddress);
	}

	@NonNull
	private String renderAddress(@NonNull final AddressToRender addressToRender)
	{
		final String renderedAddress = documentLocationBL
				.computeRenderedAddress(DocumentLocation.ofBPartnerLocationId(addressToRender.getBpLocationId()), addressToRender.getDisplaySequence())
				.getRenderedAddress();
		return renderedAddress != null ? renderedAddress : "";
	}

	@Value(staticConstructor = "of")
	private static class AddressToRender
	{
		@NonNull BPartnerLocationId bpLocationId;
		@Nullable AddressDisplaySequence displaySequence;
	}
}
