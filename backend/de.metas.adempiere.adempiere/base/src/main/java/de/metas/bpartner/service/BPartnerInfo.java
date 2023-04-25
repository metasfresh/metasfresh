package de.metas.bpartner.service;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.location.DocumentLocation;
import de.metas.location.LocationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Composition of bpartner-id, location-id and contact-id. These three in conjunction are used in many documents.
 * So, actually just a bunch of IDs. Not DDD at all.
 */
@Value
public class BPartnerInfo
{
	@NonNull BPartnerId bpartnerId;

	@Nullable
	BPartnerLocationId bpartnerLocationId;

	@Nullable
	LocationId locationId;

	@Nullable
	BPartnerContactId contactId;

	@Builder(toBuilder = true)
	private BPartnerInfo(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bpartnerLocationId,
			@Nullable final LocationId locationId,
			@Nullable final BPartnerContactId contactId)
	{
		if (bpartnerLocationId != null && !bpartnerId.equals(bpartnerLocationId.getBpartnerId()))
		{
			throw new AdempiereException("" + bpartnerId + " and " + bpartnerLocationId + " not matching");
		}

		if (contactId != null && !bpartnerId.equals(contactId.getBpartnerId()))
		{
			throw new AdempiereException("" + bpartnerId + " and " + contactId + " not matching");
		}

		this.bpartnerId = bpartnerId;
		this.bpartnerLocationId = bpartnerLocationId;
		this.locationId = locationId;
		this.contactId = contactId;
	}

	public static BPartnerInfo ofLocationAndContact(
			@NonNull final BPartnerLocationAndCaptureId bpartnerLocationAndCaptureId,
			@Nullable final BPartnerContactId contactId)
	{
		return builder()
				.bpartnerId(bpartnerLocationAndCaptureId.getBpartnerId())
				.bpartnerLocationId(bpartnerLocationAndCaptureId.getBpartnerLocationId())
				.locationId(bpartnerLocationAndCaptureId.getLocationCaptureId())
				.contactId(contactId)
				.build();
	}

	public static boolean equals(@Nullable final BPartnerInfo o1, @Nullable final BPartnerInfo o2)
	{
		return Objects.equals(o1, o2);
	}

	public DocumentLocation toDocumentLocation()
	{
		return DocumentLocation.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(bpartnerLocationId)
				.contactId(contactId)
				.locationId(locationId)
				.build();
	}

	public BPartnerInfo withLocationId(@Nullable final LocationId locationId)
	{
		return !LocationId.equals(this.locationId, locationId)
				? toBuilder().locationId(locationId).build()
				: this;
	}


	public BPartnerInfo withContactId(@Nullable final BPartnerContactId	contactId)
	{
		return !BPartnerContactId.equals(this.contactId, contactId)
				? toBuilder().contactId(contactId).build()
				: this;
	}

	public BPartnerLocationAndCaptureId toBPartnerLocationAndCaptureId()
	{
		if (bpartnerLocationId == null)
		{
			throw new AdempiereException("Cannot convert " + this + " to " + BPartnerLocationAndCaptureId.class.getSimpleName() + " because bpartnerLocationId is null");
		}
		return BPartnerLocationAndCaptureId.of(bpartnerLocationId, locationId);
	}
}
