package de.metas.common.ordercandidates.v1.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.bpartner.v1.response.JsonResponseBPartner;
import de.metas.common.bpartner.v1.response.JsonResponseContact;
import de.metas.common.bpartner.v1.response.JsonResponseLocation;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class JsonResponseBPartnerLocationAndContact
{
	JsonResponseBPartner bpartner;

	@JsonInclude(Include.NON_NULL)
	JsonResponseLocation location;

	@JsonInclude(Include.NON_NULL)
	JsonResponseContact contact;

	@JsonCreator
	@Builder
	private JsonResponseBPartnerLocationAndContact(
			@JsonProperty("bpartner") @NonNull final JsonResponseBPartner bpartner,
			@JsonProperty("location") final JsonResponseLocation location,
			@JsonProperty("contact") final JsonResponseContact contact)
	{
		this.bpartner = bpartner;
		this.location = location;
		this.contact = contact;
	}

}
