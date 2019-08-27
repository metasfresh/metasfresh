package de.metas.rest_api.bpartner.impl.bpartnercomposite.jsonpersister;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocation.BPartnerLocationBuilder;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.InvalidIdentifierException;
import de.metas.util.rest.ExternalId;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

@Value
public class ShortTermLocationIndex
{
	Map<BPartnerLocationId, BPartnerLocation> id2Location;
	Map<ExternalId, BPartnerLocation> externalId2Location;
	Map<String, BPartnerLocation> gln2Location;
	BPartnerId bpartnerId;
	BPartnerComposite bpartnerComposite;

	public ShortTermLocationIndex(@NonNull final BPartnerComposite bpartnerComposite)
	{
		this.bpartnerComposite = bpartnerComposite;
		this.bpartnerId = bpartnerComposite.getBpartner().getId(); // might be null; we synched to BPartner, but didn't yet save it
		this.id2Location = new HashMap<>();
		this.externalId2Location = new HashMap<>();
		this.gln2Location = new HashMap<>();

		for (final BPartnerLocation bpartnerLocation : bpartnerComposite.getLocations())
		{
			this.id2Location.put(bpartnerLocation.getId(), bpartnerLocation);
			this.externalId2Location.put(bpartnerLocation.getExternalId(), bpartnerLocation);
			this.gln2Location.put(bpartnerLocation.getGln(), bpartnerLocation);
		}
	}

	public BPartnerLocation extract(@NonNull final IdentifierString locationIdentifier)
	{
		switch (locationIdentifier.getType())
		{
			case METASFRESH_ID:
				if (bpartnerId != null)
				{
					final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, locationIdentifier.asMetasfreshId().getValue());
					return id2Location.get(bpartnerLocationId);
				}
				else
				{
					return null;
				}
			case GLN:
				return gln2Location.get(locationIdentifier.getValue());
			case EXTERNAL_ID:
				return externalId2Location.get(locationIdentifier.asExternalId());
			default:
				throw new InvalidIdentifierException(locationIdentifier.toString());
		}
	}

	public BPartnerLocation newLocation(@NonNull final IdentifierString locationIdentifier)
	{
		final BPartnerLocationBuilder locationBuilder = BPartnerLocation.builder();
		final BPartnerLocation location;

		switch (locationIdentifier.getType())
		{
			case METASFRESH_ID:
				if (bpartnerId != null)
				{
					final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, locationIdentifier.asMetasfreshId().getValue());
					location = locationBuilder.id(bpartnerLocationId).build();
					id2Location.put(bpartnerLocationId, location);
				}
				else
				{
					location = locationBuilder.build();
				}
				break;
			case GLN:
				location = locationBuilder.gln(locationIdentifier.getValue()).build();
				gln2Location.put(locationIdentifier.getValue(), location);
				break;
			case EXTERNAL_ID:
				location = locationBuilder.externalId(locationIdentifier.asExternalId()).build();
				externalId2Location.put(locationIdentifier.asExternalId(), location);
				break;
			default:
				throw new InvalidIdentifierException(locationIdentifier.toString());
		}

		bpartnerComposite
				.getLocations()
				.add(location);
		return location;
	}

	public Collection<BPartnerLocation> getRemainingLocations()
	{
		return id2Location.values();
	}

	public void resetBillToDefaultFlags()
	{
		for (final BPartnerLocation bpartnerLocation : bpartnerComposite.getLocations())
		{
			bpartnerLocation.getLocationType().setBillToDefault(false);
		}
	}

	public void resetShipToDefaultFlags()
	{
		for (final BPartnerLocation bpartnerLocation : bpartnerComposite.getLocations())
		{
			bpartnerLocation.getLocationType().setShipToDefault(false);
		}
	}
}
