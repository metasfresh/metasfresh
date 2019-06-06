package de.metas.rest_api.bpartner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import de.metas.util.rest.MetasfreshRestAPIConstants;

/*
 * #%L
 * de.metas.business.rest-api
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

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API + "/bpartner")
public interface BPartnerRestEndpoint
{

	ResponseEntity<JsonBPartnerComposite> retrieveBPartner(String bpartnerIdentifier);

	ResponseEntity<JsonBPartnerLocation> retrieveBPartnerLocation(
			String bpartnerIdentifier,
			String locationIdentifier);

	ResponseEntity<JsonContact> retrieveBPartnerContact(
			String bpartnerIdentifier,
			String contactIdentifier);

	ResponseEntity<JsonBPartnerCompositeList> retrieveBPartnersSince(
			Long epochTimestampMillis,
			String next);

	ResponseEntity<JsonUpsertResponse> createOrUpdateBPartner(JsonBPartnerUpsertRequest bpartners);

	ResponseEntity<JsonUpsertResponseItem> createOrUpdateLocation(
			String bpartnerIdentifier,
			JsonBPartnerLocation location);

	ResponseEntity<JsonUpsertResponseItem> createOrUpdateContact(
			String bpartnerIdentifier,
			JsonContact contact);
}
