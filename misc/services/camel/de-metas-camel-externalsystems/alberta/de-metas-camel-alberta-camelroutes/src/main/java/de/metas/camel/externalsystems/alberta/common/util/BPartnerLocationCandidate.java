/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.externalsystems.alberta.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Joiner;
import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteConstants;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import io.swagger.client.model.OrderDeliveryAddress;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BPartnerLocationCandidate
{
	@Nullable
	String bpartnerName;

	@Nullable
	String name;

	@Nullable
	String address1;

	@Nullable
	String address2;

	@Nullable
	String address3;

	@Nullable
	String postal;

	@Nullable
	String city;

	@Nullable
	String countryCode;

	@Nullable
	Boolean billTo;

	@Nullable
	Boolean shipTo;

	@Nullable
	Boolean ephemeral;

	@Builder(toBuilder = true)
	public BPartnerLocationCandidate(
			@Nullable final String bpartnerName,
			@Nullable final String name,
			@Nullable final String address1,
			@Nullable final String address2,
			@Nullable final String address3,
			@Nullable final String postal,
			@Nullable final String city,
			@Nullable final String countryCode,
			@Nullable final Boolean billTo,
			@Nullable final Boolean shipTo,
			@Nullable final Boolean ephemeral)
	{
		this.bpartnerName = bpartnerName;
		this.name = name;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.postal = postal;
		this.city = city;
		this.countryCode = countryCode;
		this.billTo = billTo;
		this.shipTo = shipTo;
		this.ephemeral = ephemeral;
	}

	@NonNull
	public static BPartnerLocationCandidate fromDeliveryAddress(@NonNull final OrderDeliveryAddress orderDeliveryAddress)
	{
		final String addressName = Joiner.on(", ").skipNulls()
				.join(orderDeliveryAddress.getAddress(), orderDeliveryAddress.getAdditionalAddress(), orderDeliveryAddress.getAdditionalAddress2());

		return BPartnerLocationCandidate.builder()
				.bpartnerName(orderDeliveryAddress.getName())
				.name(addressName)
				.address1(orderDeliveryAddress.getAddress())
				.address2(orderDeliveryAddress.getAdditionalAddress())
				.address3(orderDeliveryAddress.getAdditionalAddress2())
				.postal(orderDeliveryAddress.getPostalCode())
				.city(orderDeliveryAddress.getCity())
				.countryCode(GetPatientsRouteConstants.COUNTRY_CODE_DE)
				.billTo(false)
				.shipTo(true)
				.ephemeral(true)
				.build();
	}

	@NonNull
	public JsonRequestLocation toJsonRequestLocation()
	{
		final JsonRequestLocation requestLocation = new JsonRequestLocation();

		requestLocation.setBpartnerName(getBpartnerName());
		requestLocation.setName(getName());
		requestLocation.setAddress1(getAddress1());
		requestLocation.setAddress2(getAddress2());
		requestLocation.setAddress3(getAddress3());
		requestLocation.setPostal(getPostal());
		requestLocation.setCity(getCity());
		requestLocation.setCountryCode(getCountryCode());
		requestLocation.setShipTo(getShipTo());
		requestLocation.setBillTo(getBillTo());
		requestLocation.setEphemeral(getEphemeral());

		return requestLocation;
	}
}
