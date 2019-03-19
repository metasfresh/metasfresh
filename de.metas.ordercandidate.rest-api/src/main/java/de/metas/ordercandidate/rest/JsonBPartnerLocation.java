package de.metas.ordercandidate.rest;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

//@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JsonBPartnerLocation
{
	@ApiModelProperty(allowEmptyValue = false, //
			value = "This translates to <code>C_BPartner_Location.ExternalId</code>.\n"
					+ "Needs to be unique over all business partners (not only the one this location belongs to).")
	private String externalId;

	private String address1;

	@JsonInclude(Include.NON_NULL)
	private String address2;

	private String postal;

	private String city;

	@JsonInclude(Include.NON_NULL)
	private String state;

	private String countryCode;

	@ApiModelProperty(allowEmptyValue = false, //
			value = "This translates to <code>C_BPartner_Location.GLN</code>.")
	private String gln;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonBPartnerLocation(
			@JsonProperty("externalId") final String externalId,
			@JsonProperty("address1") final String address1,
			@JsonProperty("address2") final String address2,
			@JsonProperty("postal") final String postal,
			@JsonProperty("city") final String city,
			@JsonProperty("state") final String state,
			@JsonProperty("countryCode") @NonNull final String countryCode,
			@JsonProperty("gln") @Nullable final String gln)
	{
		this.externalId = externalId;
		this.address1 = address1;
		this.address2 = address2;
		this.postal = postal;
		this.city = city;
		this.state = state;
		this.countryCode = countryCode;
		this.gln = gln;
	}
}
