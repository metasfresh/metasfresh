package de.metas.rest_api.bpartner.impl;

import java.util.Optional;

import de.metas.rest_api.bpartner.JsonBPartnerComposite;
import de.metas.rest_api.bpartner.JsonBPartnerCompositeList;
import de.metas.rest_api.bpartner.JsonBPartnerLocation;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.rest_api.bpartner.JsonContactList;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public interface IBPartnerEndpointService
{

	Optional<JsonBPartnerComposite> retrieveBPartner(String bpartnerIdentifier);

	Optional<JsonBPartnerLocation> retrieveBPartnerLocation(String bpartnerIdentifier, String locationIdentifier);

	Optional<JsonContact> retrieveBPartnerContact(String bpartnerIdentifier, String contactIdentifier);

	Optional<JsonBPartnerCompositeList> retrieveBPartnersSince(Long epochTimestampMillis, String next);

	JsonContact retrieveContact(String contactIdentifier);

	Optional<JsonContactList> retrieveContactsSince(Long epochTimestampMillis, String next);

}
