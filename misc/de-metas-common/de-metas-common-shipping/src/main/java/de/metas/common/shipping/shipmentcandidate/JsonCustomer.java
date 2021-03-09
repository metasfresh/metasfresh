/*
 * #%L
 * de-metas-common-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.shipping.shipmentcandidate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class JsonCustomer
{
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String companyName;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String contactName;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String contactEmail;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String contactPhone;

	String street;

	String streetNo;

	String addressSuffix1;
	String addressSuffix2;
	String addressSuffix3;

	String postal;

	String city;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String countryCode;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String deliveryInfo;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String language;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String shipmentAllocationBestBeforePolicy;

	boolean company;

	@JsonCreator
	@Builder
	public JsonCustomer(
			@JsonProperty("companyName") @Nullable final String companyName,
			@JsonProperty("contactName") @Nullable final String contactName,
			@JsonProperty("contactEmail") @Nullable final String contactEmail,
			@JsonProperty("contactPhone") @Nullable final String contactPhone,
			@JsonProperty("street") @NonNull final String street,
			@JsonProperty("streetNo") @NonNull final String streetNo,
			@JsonProperty("addressSuffix1") final String addressSuffix1,
			@JsonProperty("addressSuffix2") final String addressSuffix2,
			@JsonProperty("addressSuffix3") final String addressSuffix3,
			@JsonProperty("postal") @NonNull final String postal,
			@JsonProperty("city") @NonNull final String city,
			@JsonProperty("countryCode") @Nullable final String countryCode,
			@JsonProperty("deliveryInfo") @Nullable final String deliveryInfo,
			@JsonProperty("language") @Nullable final String language,
			@JsonProperty("shipmentAllocationBestBeforePolicy") @Nullable final String shipmentAllocationBestBeforePolicy,
			@JsonProperty("company") @Nullable final boolean company
	)
	{
		this.companyName = companyName;
		this.contactName = contactName;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
		this.street = street;
		this.streetNo = streetNo;
		this.addressSuffix1 = addressSuffix1;
		this.addressSuffix2 = addressSuffix2;
		this.addressSuffix3 = addressSuffix3;
		this.postal = postal;
		this.city = city;
		this.countryCode = countryCode;
		this.deliveryInfo = deliveryInfo;
		this.language = language;
		this.shipmentAllocationBestBeforePolicy = shipmentAllocationBestBeforePolicy;
		this.company = company;
	}
}
