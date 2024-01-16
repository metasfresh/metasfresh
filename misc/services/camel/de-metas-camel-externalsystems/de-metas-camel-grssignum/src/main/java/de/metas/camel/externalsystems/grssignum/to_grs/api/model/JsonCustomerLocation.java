/*
 * #%L
 * de-metas-camel-grssignum
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.grssignum.to_grs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@JsonDeserialize(builder = JsonCustomerLocation.JsonCustomerLocationBuilder.class)
public class JsonCustomerLocation
{
	@NonNull
	@JsonProperty("METASFRESHID")
	JsonMetasfreshId metasfreshId;

	@JsonProperty("NAME")
	String name;

	@JsonProperty("ADRESSE 1")
	String address1;

	@JsonProperty("ADRESSE 2")
	String address2;

	@JsonProperty("ADRESSE 3")
	String address3;

	@JsonProperty("ADRESSE 4")
	String address4;

	@JsonProperty("PLZ")
	String postal;

	@JsonProperty("ORT")
	String city;

	@JsonProperty("LANDESCODE")
	String countryCode;

	@JsonProperty("GLN")
	String gln;

	@JsonProperty("INAKTIV")
	Integer inactiveBit;

	@JsonProperty("HAUPTADDRESSE")
	Integer mainAddress;

	@JsonProperty("LIEFERADRESSE")
	boolean shipTo;

	@JsonProperty("RECHNUNGSADDRESSE")
	boolean billTo;

	@Builder
	public JsonCustomerLocation(
			@JsonProperty("METASFRESHID") @NonNull final JsonMetasfreshId metasfreshId,
			@JsonProperty("NAME") @Nullable final String name,
			@JsonProperty("ADRESSE 1") @Nullable final String address1,
			@JsonProperty("ADRESSE 2") @Nullable final String address2,
			@JsonProperty("ADRESSE 3") @Nullable final String address3,
			@JsonProperty("ADRESSE 4") @Nullable final String address4,
			@JsonProperty("PLZ") @Nullable final String postal,
			@JsonProperty("ORT") @Nullable final String city,
			@JsonProperty("LANDESCODE") @Nullable final String countryCode,
			@JsonProperty("GLN") @Nullable final String gln,
			@JsonProperty("INAKTIV") final int inactive,
			@JsonProperty("HAUPTADDRESSE") final Integer mainAddress,
			@JsonProperty("LIEFERADRESSE") final boolean shipTo,
			@JsonProperty("RECHNUNGSADDRESSE") final boolean billTo)
	{
		this.metasfreshId = metasfreshId;
		this.name = name;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.address4 = address4;
		this.postal = postal;
		this.city = city;
		this.countryCode = countryCode;
		this.gln = gln;
		this.inactiveBit = inactive;
		this.shipTo = shipTo;
		this.billTo = billTo;
		this.mainAddress = mainAddress;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonCustomerLocationBuilder
	{
	}

	@JsonIgnore
	public boolean isActive()
	{
		return inactiveBit != 1;
	}
}