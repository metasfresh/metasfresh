package de.metas.rest_api.bpartner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.metas.util.rest.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiParam;

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

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API + "/contact")
public interface ContactRestEndpoint
{
	@GetMapping("{contactIdentifier}")
	ResponseEntity<JsonBPartnerContact> retrieveBPartnerContact(
			@ApiParam(value = "Identifier of the bpartner's contact is question. Be a plain `AD_User_ID` or something like `ext-YourExternalId`", allowEmptyValue = false) //
			@PathVariable("contactIdentifier") //
			String contactIdentifier);

	@GetMapping
	ResponseEntity<JsonBPartnerContactList> retrieveBPartnerContactsSince(
			@RequestParam(name = "since", required = false, defaultValue = "0") //
			long epochTimestampMillis);

	@PostMapping
	ResponseEntity<JsonBPartnerUpsertResponse> createOrUpdateBPartnerContact(JsonBPartnerContactList contacts);
}
