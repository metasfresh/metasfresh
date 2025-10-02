/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.converters.v1;

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderParcel;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.shipper.gateway.commons.model.ShipperConfig;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class JsonShipperConverter
{

	public JsonDeliveryRequest toJson(@NonNull final ShipperConfig config, @NonNull final DeliveryOrder order)
	{
		return JsonDeliveryRequest.builder()
				.id(order.getId() != null ? order.getId().toString() : null)
				.pickupAddress(toJsonAddress(order.getPickupAddress()))
				.pickupDate(order.getPickupDate() != null ? order.getPickupDate().getDate().toString() : null)
				.pickupNote(order.getPickupNote())
				.deliveryAddress(toJsonAddress(order.getDeliveryAddress()))
				.deliveryContact(toJsonContactOrNull(order.getDeliveryContact()))
				.deliveryDate(order.getDeliveryDate() != null ? order.getDeliveryDate().getDate().toString() : null)
				.deliveryNote(order.getDeliveryNote())
				.customerReference(order.getCustomerReference())
				.deliveryOrderLines(order.getDeliveryOrderLines().stream().map(this::toJsonDeliveryOrderLine).collect(ImmutableList.toImmutableList()))
				.shipperProduct(order.getShipperProduct() != null ? order.getShipperProduct().getCode() : null)
				.shipperEORI(order.getShipperEORI())
				.receiverEORI(order.getReceiverEORI())
				.shipperConfig(toJsonShipperConfig(config))
				.build();
	}

	private @NonNull JsonShipperConfig toJsonShipperConfig(final @NonNull ShipperConfig config)
	{
		return JsonShipperConfig.builder()
				.url(config.getUrl())
				.username(config.getUsername())
				.password(config.getPassword())
				.clientId(config.getClientId())
				.clientSecret(config.getClientSecret())
				.gatewayId(config.getGatewayId())
				.trackingUrl(config.getTrackingUrl())
				.build();
	}

	private JsonAddress toJsonAddress(@NonNull final Address address)
	{
		final Integer bpartnerId = address.getBpartnerId() > 0 ? address.getBpartnerId() : null;
		return JsonAddress.builder()
				.companyName1(address.getCompanyName1())
				.companyName2(address.getCompanyName2())
				.companyDepartment(address.getCompanyDepartment())
				.street1(address.getStreet1())
				.street2(address.getStreet2())
				.houseNo(address.getHouseNo())
				.country(address.getCountry().getAlpha2())
				.zipCode(address.getZipCode())
				.city(address.getCity())
				.bpartnerId(bpartnerId)
				.build();
	}

	@Nullable
	private JsonContact toJsonContactOrNull(final @Nullable ContactPerson contact)
	{
		if (contact == null) {return null;}
		return JsonContact.builder()
				.phone(contact.getPhoneAsStringOrNull())
				.simplePhoneNumber(contact.getSimplePhoneNumber())
				.emailAddress(contact.getEmailAddress())
				.build();
	}

	private JsonDeliveryOrderParcel toJsonDeliveryOrderLine(final DeliveryOrderLine line)
	{
		return JsonDeliveryOrderParcel.builder()
				.id(line.getId() != null ? String.valueOf(line.getId().getRepoId()) : null)
				.content(line.getContent())
				.grossWeightKg(line.getGrossWeightKg())
				.packageDimensions(toJsonPackageDimensions(line.getPackageDimensions()))
				.packageId(line.getPackageId().toString())
				.contents(java.util.Collections.emptyList())
				.build();
	}

	private JsonPackageDimensions toJsonPackageDimensions(final PackageDimensions dims)
	{
		if (dims == null) {return JsonPackageDimensions.builder().lengthInCM(0).widthInCM(0).heightInCM(0).build();}
		return JsonPackageDimensions.builder()
				.lengthInCM(dims.getLengthInCM())
				.widthInCM(dims.getWidthInCM())
				.heightInCM(dims.getHeightInCM())
				.build();
	}
}
