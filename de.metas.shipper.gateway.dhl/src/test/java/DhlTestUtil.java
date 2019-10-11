/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

import com.google.common.collect.ImmutableList;
import de.metas.shipper.gateway.dhl.model.DhlServiceType;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.CountryCode;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class DhlTestUtil
{

	private static final CountryCode COUNTRY_CODE_DE = CountryCode.builder().alpha2("DE").alpha3("DEU").build();

	static DeliveryOrder createDummyDeliveryOrder()
	{
		final DeliveryOrder deliveryOrderCreateRequest = DeliveryOrder.builder()
				// shipper
				.pickupAddress(Address.builder()
						.companyName1("TheBestPessimist Inc.")
						.companyName2("The Second Shipper Company Name")
						.street1("Eduard-Otto-Straße")
						.street2("Street Name 2 ")
						.houseNo("10")
						.zipCode("53129")
						.city("Bonn")
						.country(COUNTRY_CODE_DE)
						.build())
				.pickupDate(PickupDate.builder()
						.date(LocalDate.now().plusDays(1)) // always tomorrow!
						.build())
				// receiver
				.deliveryAddress(Address.builder()
						.companyName1("DHL Packet gmbh")
						.companyName2("The Second Receiver Company Name")
						.street1("Charles-de-Gaulle-Str.")
						.houseNo("20")
						.zipCode("53113")
						.city("Bonn")
						.country(COUNTRY_CODE_DE)
						.build())
				.deliveryContact(ContactPerson.builder()
						.emailAddress("tbp@tbp.com")
						.simplePhoneNumber("012345689")
						.build())
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(5)
						.packageDimensions(PackageDimensions.builder()
								.heightInCM(10)
								.lengthInCM(10)
								.widthInCM(10)
								.build())
						.packageIds(ImmutableList.of(1, 2, 3, 4, 5))
						.grossWeightKg(1)
						.content("dildos")
						.build())
				.customerReference("the helpful customer reference")
				.serviceType(DhlServiceType.V01PAK)
				.build();
		return deliveryOrderCreateRequest;
	}
}
