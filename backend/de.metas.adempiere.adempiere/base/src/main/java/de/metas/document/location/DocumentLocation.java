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

package de.metas.document.location;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.location.LocationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.w3c.dom.Document;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class DocumentLocation
{
	public static final DocumentLocation EMPTY = builder().build();

	@Nullable
	BPartnerId bpartnerId;
	@Nullable
	BPartnerLocationId bpartnerLocationId;
	@Nullable
	LocationId locationId;
	@Nullable
	BPartnerContactId contactId;
	@Nullable
	String bpartnerAddress;

	@Builder(toBuilder = true)
	private DocumentLocation(
			@Nullable final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bpartnerLocationId,
			@Nullable final LocationId locationId,
			@Nullable final BPartnerContactId contactId,
			@Nullable final String bpartnerAddress)
	{
		if (bpartnerLocationId != null && !bpartnerLocationId.getBpartnerId().equals(bpartnerId))
		{
			throw new AdempiereException("" + bpartnerId + " and " + bpartnerLocationId + " shall match");
		}
		if (contactId != null && !contactId.getBpartnerId().equals(bpartnerId))
		{
			throw new AdempiereException("" + bpartnerId + " and " + contactId + " shall match");
		}

		this.bpartnerId = bpartnerId;
		this.bpartnerLocationId = bpartnerLocationId;
		this.locationId = locationId;
		this.contactId = contactId;
		this.bpartnerAddress = bpartnerAddress;
	}

	public static DocumentLocation ofBPartnerLocationId(@NonNull BPartnerLocationId bpartnerLocationId)
	{
		return builder()
				.bpartnerId(bpartnerLocationId.getBpartnerId())
				.bpartnerLocationId(bpartnerLocationId)
				.contactId(null)
				.locationId(null)
				.bpartnerAddress(null)
				.build();
	}

	public DocumentLocation withLocationId(@Nullable final LocationId locationId)
	{
		return !Objects.equals(this.locationId, locationId)
				? toBuilder().locationId(locationId).build()
				: this;
	}
}
