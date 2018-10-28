package de.metas.ordercandidate.rest;

import lombok.Builder;
import lombok.Value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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
public final class JsonBPartnerInfo
{
	JsonBPartner bpartner;
	JsonBPartnerLocation location;
	JsonBPartnerContact contact;

	@JsonCreator
	@Builder
	public JsonBPartnerInfo(
			@JsonProperty("bpartner") final JsonBPartner bpartner,
			@JsonProperty("location") final JsonBPartnerLocation location,
			@JsonProperty("contact") final JsonBPartnerContact contact)
	{
		this.bpartner = bpartner;
		this.location = location;
		this.contact = contact;
	}
}
